package essentialcraft.common.tile;

import java.util.ArrayList;

import essentialcraft.common.inventory.InventoryMagicFilter;
import essentialcraft.common.item.ItemFilter;
import essentialcraft.common.item.ItemsCore;
import essentialcraft.utils.common.ECUtils;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class TileMIMExportNode extends TileMRUGeneric {
	final Capability<IItemHandler> ITEM_HANDLER_CAPABILITY = CapabilityItemHandler.ITEM_HANDLER_CAPABILITY;

	public TileMIMExportNode() {
		setMaxMRU(0);
		setSlotsNum(1);
		slot0IsBoundGem = false;
	}

	public EnumFacing getRotation() {
		int metadata = this.getBlockMetadata();
		metadata %= 6;
		return EnumFacing.getFront(metadata);
	}

	@Override
	public boolean isItemValidForSlot(int p_94041_1_, ItemStack p_94041_2_) {
		return p_94041_2_.getItem() == ItemsCore.filter;
	}

	@Override
	public int[] getOutputSlots() {
		return new int[0];
	}

	public IItemHandler getConnectedInventory() {
		EnumFacing side = getRotation();
		if(getWorld().getTileEntity(pos.offset(side)) != null) {
			TileEntity tile = getWorld().getTileEntity(pos.offset(side));
			if(tile.hasCapability(ITEM_HANDLER_CAPABILITY, side.getOpposite()))
				return tile.getCapability(ITEM_HANDLER_CAPABILITY, side.getOpposite());
		}

		return null;
	}

	public IItemHandler getConnectedInventoryNonSided() {
		EnumFacing side = getRotation();
		if(getWorld().getTileEntity(pos.offset(side)) != null) {
			TileEntity tile = getWorld().getTileEntity(pos.offset(side));
			if(tile.hasCapability(ITEM_HANDLER_CAPABILITY, null))
				return tile.getCapability(ITEM_HANDLER_CAPABILITY, null);
		}

		return null;
	}

	public void exportAllPossibleItems(TileMIM parent) {
		if(getWorld().isBlockIndirectlyGettingPowered(pos) > 0)
			return;

		IItemHandler inv = getConnectedInventory();
		if(inv == null) {
			IItemHandler iinv = getConnectedInventoryNonSided();
		}
		ArrayList<ItemStack> itemsToExport = parent.getAllItems();
		int slots = inv.getSlots();

		if(slots <= 0)
			return;

		for(int i = 0; i < itemsToExport.size(); ++i) {
			for(int j = 0; j < slots; ++j) {
				if(inv.insertItem(j, itemsToExport.get(i), true).getCount() < itemsToExport.get(i).getCount()) {
					if(inv.getStackInSlot(j).isEmpty() || inv.getStackInSlot(j).isItemEqual(itemsToExport.get(i)) && ItemStack.areItemStackTagsEqual(inv.getStackInSlot(j), itemsToExport.get(i))) {
						if(getStackInSlot(0).isEmpty() || !(getStackInSlot(0).getItem() instanceof ItemFilter)) {
							if(inv.getStackInSlot(j).isEmpty()) {
								ItemStack copied = itemsToExport.get(i).copy();
								if(copied.getCount() >= inv.getSlotLimit(j))
									copied.setCount(inv.getSlotLimit(j));

								if(parent.retrieveItemStackFromSystem(copied, false, true) == 0)
									inv.insertItem(j, copied, false);

							}
							else {
								ItemStack copied = itemsToExport.get(i).copy();
								if(copied.getCount() >= inv.getSlotLimit(j))
									copied.setCount(inv.getSlotLimit(j));

								if(inv.getStackInSlot(j).getCount() + copied.getCount() <= inv.getSlotLimit(j) && inv.getStackInSlot(j).getCount() + copied.getCount() <= copied.getMaxStackSize()) {
									if(parent.retrieveItemStackFromSystem(copied, false, true) == 0)
										inv.insertItem(j, copied, false);
								}
								else {
									int reduceBy = copied.getCount() - inv.getStackInSlot(j).getCount();
									if(reduceBy > 0) {
										copied.setCount(reduceBy);
										if(parent.retrieveItemStackFromSystem(copied, false, true) == 0)
											inv.insertItem(j, copied, false);
									}
								}
							}
						}
						else {
							ItemStack copied = itemsToExport.get(i).copy();
							if(ECUtils.canFilterAcceptItem(new InventoryMagicFilter(getStackInSlot(0)), copied, getStackInSlot(0))) {
								if(copied.getCount() >= inv.getSlotLimit(j))
									copied.setCount(inv.getSlotLimit(j));

								if(inv.getStackInSlot(j).isEmpty()) {
									if(parent.retrieveItemStackFromSystem(copied, false, true) == 0)
										inv.insertItem(j, copied, false);

								}
								else if(inv.getStackInSlot(j).getCount() + copied.getCount() <= inv.getSlotLimit(j) && inv.getStackInSlot(j).getCount() + copied.getCount() <= copied.getMaxStackSize())
								{
									if(parent.retrieveItemStackFromSystem(copied, false, true) == 0)
										inv.insertItem(j, copied, false);
								}
								else {
									int reduceBy = copied.getCount() - inv.getStackInSlot(j).getCount();
									if(reduceBy > 0) {
										copied.setCount(reduceBy);
										if(parent.retrieveItemStackFromSystem(copied, false, true) == 0)
											inv.insertItem(j, copied, false);
									}
								}
							}
						}
					}
				}
			}
		}
	}
}
