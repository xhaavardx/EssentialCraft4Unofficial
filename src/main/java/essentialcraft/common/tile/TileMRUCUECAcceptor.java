package essentialcraft.common.tile;

import DummyCore.Utils.MiscUtils;
import essentialcraft.api.EnumStructureType;
import essentialcraft.api.IMRUHandlerRequires;
import essentialcraft.api.IStructurePiece;
import essentialcraft.common.item.ItemsCore;
import essentialcraft.utils.common.ECUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;

public class TileMRUCUECAcceptor extends TileEntity implements IMRUHandlerRequires, IStructurePiece, IInventory, ITickable {
	public TileMRUCUECController controller;
	public int syncTick;
	public ItemStack[] items = {ItemStack.EMPTY};

	@Override
	public void readFromNBT(NBTTagCompound i) {
		super.readFromNBT(i);
		MiscUtils.loadInventory(this, i);
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound i) {
		super.writeToNBT(i);
		MiscUtils.saveInventory(this, i);
		return i;
	}

	@Override
	public int getMRU() {
		return controller != null ? controller.getMRU() : 0;
	}

	@Override
	public int getMaxMRU() {
		return controller != null ? controller.getMaxMRU() : 0;
	}

	@Override
	public boolean setMRU(int i) {
		return controller != null && i >= getMRU() ? controller.setMRU(i) : false;
	}

	@Override
	public float getBalance() {
		return controller != null ? controller.getBalance() : 1;
	}

	@Override
	public boolean setBalance(float f) {
		return controller != null ? controller.setBalance(f) : false;
	}

	@Override
	public EnumStructureType getStructure() {
		return EnumStructureType.MRUCUEC;
	}

	@Override
	public TileEntity structureController() {
		return controller;
	}

	@Override
	public void setStructureController(TileEntity tile, EnumStructureType structure) {
		if(tile instanceof TileMRUCUECController && structure == getStructure())
			controller = (TileMRUCUECController)tile;
	}

	@Override
	public void update() {
		if(structureController() != null)
			ECUtils.mruIn(this, 0);
		ECUtils.spawnMRUParticles(this,0);
		if(syncTick == 0) {
			if(!getWorld().isRemote)
				MiscUtils.sendPacketToAllAround(getWorld(), getUpdatePacket(), pos.getX(), pos.getY(), pos.getZ(), getWorld().provider.getDimension(), 16);
			syncTick = 10;
		}
		else
			--syncTick;
	}

	@Override
	public SPacketUpdateTileEntity getUpdatePacket() {
		NBTTagCompound nbttagcompound = new NBTTagCompound();
		writeToNBT(nbttagcompound);
		return new SPacketUpdateTileEntity(pos, -10, nbttagcompound);
	}

	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
		if(pkt.getTileEntityType() == -10)
			readFromNBT(pkt.getNbtCompound());
	}

	@Override
	public int getSizeInventory() {
		return items.length;
	}

	@Override
	public ItemStack getStackInSlot(int par1) {
		return items[par1];
	}

	@Override
	public ItemStack decrStackSize(int par1, int par2) {
		if(!items[par1].isEmpty()) {
			ItemStack itemstack;

			if(items[par1].getCount() <= par2) {
				itemstack = items[par1];
				items[par1] = ItemStack.EMPTY;
				return itemstack;
			}
			else {
				itemstack = items[par1].splitStack(par2);

				if(items[par1].getCount() == 0)
					items[par1] = ItemStack.EMPTY;

				return itemstack;
			}
		}
		else
			return ItemStack.EMPTY;
	}

	@Override
	public ItemStack removeStackFromSlot(int par1) {
		if(!items[par1].isEmpty()) {
			ItemStack itemstack = items[par1];
			items[par1] = ItemStack.EMPTY;
			return itemstack;
		}
		else {
			return ItemStack.EMPTY;
		}
	}

	@Override
	public void setInventorySlotContents(int par1, ItemStack par2ItemStack) {
		items[par1] = par2ItemStack;

		if(!par2ItemStack.isEmpty() && par2ItemStack.getCount() > getInventoryStackLimit())
			par2ItemStack.setCount(getInventoryStackLimit());
	}

	@Override
	public String getName() {
		return "essentialcraft.container.mruAcceptor";
	}

	@Override
	public boolean hasCustomName() {
		return false;
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public boolean isUsableByPlayer(EntityPlayer player) {
		return getWorld().getTileEntity(getPos()) == this && player.dimension == getWorld().provider.getDimension() && getPos().distanceSqToCenter(player.posX, player.posY, player.posZ) <= 64D;
	}

	@Override
	public void openInventory(EntityPlayer p) {}

	@Override
	public void closeInventory(EntityPlayer p) {}

	@Override
	public boolean isItemValidForSlot(int p_94041_1_, ItemStack p_94041_2_) {
		return p_94041_2_.getItem() == ItemsCore.bound_gem;
	}

	@Override
	public boolean setMaxMRU(float f) {
		return controller != null ? controller.setMaxMRU(f) : false;
	}

	@Override
	public int getField(int id) {
		return 0;
	}

	@Override
	public void setField(int id, int value) {}

	@Override
	public int getFieldCount() {
		return 0;
	}

	@Override
	public void clear() {
		for(int i = 0; i < getSizeInventory(); i++)
			setInventorySlotContents(i, ItemStack.EMPTY);
	}

	public IItemHandler itemHandler = new InvWrapper(this);

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY || super.hasCapability(capability, facing);
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY ? (T)itemHandler : super.getCapability(capability, facing);
	}

	@Override
	public boolean isEmpty() {
		boolean ret = true;
		for(ItemStack stk : items) {
			ret &= stk.isEmpty();
		}
		return ret;
	}
}
