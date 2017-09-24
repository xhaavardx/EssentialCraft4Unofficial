package essentialcraft.common.tile;

import DummyCore.Utils.MathUtils;
import DummyCore.Utils.MiscUtils;
import essentialcraft.api.IMRUHandler;
import essentialcraft.api.IMRUHandlerTransfers;
import essentialcraft.common.item.ItemBoundGem;
import essentialcraft.utils.common.ECUtils;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TileMRUDimensionalTransciever extends TileMRUGeneric implements IMRUHandlerTransfers {

	public TileMRUDimensionalTransciever() {
		setMaxMRU(5000F);
		setSlotsNum(1);
	}

	@Override
	public void update() {
		super.update();
		mruIn();
	}

	public void mruIn() {
		IInventory inv = this;
		int slotNum = 0;
		TileEntity tile = this;
		if(inv.getSizeInventory() > 0 && inv.getStackInSlot(slotNum).getItem() instanceof ItemBoundGem && inv.getStackInSlot(slotNum).getTagCompound() != null) {
			ItemStack s = inv.getStackInSlot(slotNum);
			int[] o = MiscUtils.getStackTag(s).getIntArray("pos");
			if(MathUtils.getDifference(tile.getPos().getX(), o[0]) <= 16 && MathUtils.getDifference(tile.getPos().getY(), o[1]) <= 16 && MathUtils.getDifference(tile.getPos().getZ(), o[2]) <= 16) {
				if(tile.getWorld().getTileEntity(new BlockPos(o[0], o[1], o[2])) != null && tile.getWorld().getTileEntity(new BlockPos(o[0], o[1], o[2])) instanceof IMRUHandler)
					setBalance(((IMRUHandler)tile.getWorld().getTileEntity(new BlockPos(o[0], o[1], o[2]))).getBalance());
			}
		}
		if(inv.getSizeInventory() > 0) {
			mruIn(this, 0);
			ECUtils.spawnMRUParticles(this, 0);
		}
	}

	public static void mruIn(TileEntity tile, int slotNum) {
		if(tile instanceof IInventory && tile instanceof IMRUHandler && !tile.getWorld().isRemote) {
			IInventory inv = (IInventory)tile;
			IMRUHandler mrut = (IMRUHandler)tile;
			if(!inv.getStackInSlot(slotNum).isEmpty() && inv.getStackInSlot(slotNum).getTagCompound() != null) {
				ItemStack s = inv.getStackInSlot(slotNum);
				int[] o = MiscUtils.getStackTag(s).getIntArray("pos");
				World wrldObj = tile.getWorld().getMinecraftServer().getWorld(getDimension(s));
				if(wrldObj != null && wrldObj.getTileEntity(new BlockPos(o[0], o[1], o[2])) != null && wrldObj.getTileEntity(new BlockPos(o[0], o[1], o[2])) instanceof IMRUHandler) {
					IMRUHandler t = (IMRUHandler)wrldObj.getTileEntity(new BlockPos(o[0], o[1], o[2]));
					if(t != tile && t != null) {
						if(mrut.getMRU() < mrut.getMaxMRU()) {
							int mru = t.getMRU();
							if(mru > mrut.getMaxMRU() - mrut.getMRU()) {
								if(t.setMRU(mru - (mrut.getMaxMRU() - mrut.getMRU()))) {
									mrut.setMRU(mrut.getMaxMRU());
								}
							}
							else {
								if(t.setMRU(0)) {
									mrut.setMRU(mrut.getMRU() + mru);
								}
							}
							mrut.setBalance((mrut.getBalance() + t.getBalance())/2);
						}
					}
				}
			}
		}
	}


	public static int getDimension(ItemStack stack) {
		return MiscUtils.getStackTag(stack).getInteger("dim");
	}

	@Override
	public boolean isItemValidForSlot(int p_94041_1_, ItemStack p_94041_2_) {
		return isBoundGem(p_94041_2_);
	}

	@Override
	public int[] getOutputSlots() {
		return new int[0];
	}
}
