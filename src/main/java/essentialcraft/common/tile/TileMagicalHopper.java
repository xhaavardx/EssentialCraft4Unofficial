package essentialcraft.common.tile;

import java.util.List;

import DummyCore.Utils.DataStorage;
import DummyCore.Utils.DummyData;
import essentialcraft.common.inventory.InventoryMagicFilter;
import essentialcraft.common.item.ItemFilter;
import essentialcraft.common.item.ItemsCore;
import essentialcraft.utils.common.ECUtils;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.common.config.Configuration;

public class TileMagicalHopper extends TileMRUGeneric {
	public static int itemHopRadius = 3;
	public static int itemDelay = 20;

	public int delay = 0;

	public EnumFacing getRotation() {
		int metadata = this.getBlockMetadata();
		metadata %= 6;
		return EnumFacing.getFront(metadata);
	}

	public TileMagicalHopper() {
		super();
		setSlotsNum(1);
		mruStorage.setMaxMRU(0);
		slot0IsBoundGem = false;
	}

	@Override
	public void readFromNBT(NBTTagCompound par1NBTTagCompound) {
		super.readFromNBT(par1NBTTagCompound);
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound par1NBTTagCompound) {
		return super.writeToNBT(par1NBTTagCompound);
	}

	@Override
	public void update() {
		super.update();
		if(delay <= 0 && getWorld().isBlockIndirectlyGettingPowered(pos) == 0) {
			EnumFacing r = getRotation();
			AxisAlignedBB teleportBB = new AxisAlignedBB(pos.offset(r));
			delay = itemDelay;
			List<EntityItem> items = getWorld().getEntitiesWithinAABB(EntityItem.class, new AxisAlignedBB(pos).expand(itemHopRadius, itemHopRadius, itemHopRadius));
			List<EntityItem> doNotTouch = getWorld().getEntitiesWithinAABB(EntityItem.class, teleportBB);

			for(int i = 0; i < items.size(); ++i) {
				EntityItem item = items.get(i);
				if(canTeleport(item) && !doNotTouch.contains(item))
					item.setPositionAndRotation(pos.getX()+0.5D+r.getFrontOffsetX(), pos.getY()+0.5D+r.getFrontOffsetY(), pos.getZ()+0.5D+r.getFrontOffsetZ(), 0, 0);
			}
		}
		else {
			--delay;
		}
	}

	public boolean canTeleport(EntityItem item) {
		if(item.getItem().isEmpty())
			return false;

		if(getStackInSlot(0).isEmpty() || !(getStackInSlot(0).getItem() instanceof ItemFilter))
			return true;

		return ECUtils.canFilterAcceptItem(new InventoryMagicFilter(getStackInSlot(0)), item.getItem(), getStackInSlot(0));
	}

	public static void setupConfig(Configuration cfg) {
		try {
			cfg.load();
			String[] cfgArrayString = cfg.getStringList("MagicalHopperSettings", "tileentities", new String[] {
					"Radius in which the hopper will detect items(int):3",
					"Delay between item detection in ticks:20"
			}, "");
			String dataString = "";

			for(int i = 0; i < cfgArrayString.length; ++i)
				dataString += "||" + cfgArrayString[i];

			DummyData[] data = DataStorage.parseData(dataString);

			itemHopRadius = Integer.parseInt(data[0].fieldValue);
			itemDelay = Integer.parseInt(data[1].fieldValue);

			cfg.save();
		}
		catch(Exception e) {
			return;
		}
	}

	@Override
	public boolean isItemValidForSlot(int p_94041_1_, ItemStack p_94041_2_) {
		return p_94041_2_.getItem() == ItemsCore.filter;
	}

	@Override
	public int[] getOutputSlots() {
		return new int[0];
	}
}
