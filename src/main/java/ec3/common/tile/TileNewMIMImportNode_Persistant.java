package ec3.common.tile;

import ec3.common.inventory.InventoryMagicFilter;
import ec3.common.item.ItemFilter;
import ec3.utils.common.ECUtils;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;

public class TileNewMIMImportNode_Persistant extends TileNewMIMImportNode {

	public TileNewMIMImportNode_Persistant() {
		super();
	}

	@Override
	public void importAllPossibleItems(TileNewMIM parent) {
		if(getWorld().isBlockIndirectlyGettingPowered(pos) > 0)
			return;

		IItemHandler inv = getConnectedInventory();
		if(inv == null) {
			IItemHandler iinv = getConnectedInventoryNonSided();
		}
		int slots = inv.getSlots();

		if(slots <= 0)
			return;

		for(int j = 0; j < slots; ++j) {
			ItemStack stk = inv.getStackInSlot(j);
			if(stk != null) {
				if(getStackInSlot(0) == null || !(getStackInSlot(0).getItem() instanceof ItemFilter)) {
					ItemStack current = stk.copy();
					current.stackSize--;

					if(current.stackSize > 0)
						if(parent.addItemStackToSystem(current))
							inv.extractItem(j, current.stackSize, false);
				}
				else if(ECUtils.canFilterAcceptItem(new InventoryMagicFilter(getStackInSlot(0)), stk, getStackInSlot(0))) {
					ItemStack current = stk.copy();
					current.stackSize--;

					if(current.stackSize > 0)
						if(parent.addItemStackToSystem(current))
							inv.extractItem(j, current.stackSize, false);
				}
			}
		}
	}
}
