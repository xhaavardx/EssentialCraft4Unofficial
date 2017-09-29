package ec3.common.tile;

import java.util.ArrayList;

import ec3.common.inventory.InventoryMagicFilter;
import ec3.common.item.ItemFilter;
import ec3.utils.common.ECUtils;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;

public class TileNewMIMExportNode_Persistant extends TileNewMIMExportNode {

	public TileNewMIMExportNode_Persistant() {
		super();
	}

	@Override
	public void exportAllPossibleItems(TileNewMIM parent) {
		if(getWorld().isBlockIndirectlyGettingPowered(pos) > 0)
			return;

		IItemHandler inv = getConnectedInventory();
		if(inv == null) {
			inv = getConnectedInventoryNonSided();
		}
		ArrayList<ItemStack> itemsToExport = parent.getAllItems();
		int slots = inv.getSlots();

		if(slots <= 0)
			return;

		for(int i = 0; i < itemsToExport.size(); ++i) {
			for(int j = 0; j < slots; ++j) {
				if(inv.insertItem(j, itemsToExport.get(i), true).stackSize < itemsToExport.get(i).stackSize) {
					if(inv.getStackInSlot(j) == null || inv.getStackInSlot(j).isItemEqual(itemsToExport.get(i)) && ItemStack.areItemStackTagsEqual(inv.getStackInSlot(j), itemsToExport.get(i))) {
						if(getStackInSlot(0) == null || !(getStackInSlot(0).getItem() instanceof ItemFilter)) {
							if(inv.getStackInSlot(j) == null) {
								ItemStack copied = itemsToExport.get(i).copy();
								if(copied.stackSize >= 2)
									copied.stackSize = 1;

								if(parent.retrieveItemStackFromSystem(copied, false, true) == 0)
									inv.insertItem(j, copied, true);
							}
						}
						else {
							ItemStack copied = itemsToExport.get(i).copy();
							if(ECUtils.canFilterAcceptItem(new InventoryMagicFilter(getStackInSlot(0)), copied, getStackInSlot(0))) {
								if(copied.stackSize >= 2)
									copied.stackSize = 1;

								if(inv.getStackInSlot(j) == null) {
									if(parent.retrieveItemStackFromSystem(copied, false, true) == 0)
										inv.insertItem(j, copied, true);
								}
							}
						}
					}
				}
			}
		}
	}
}
