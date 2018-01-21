package essentialcraft.common.inventory;

import DummyCore.Utils.ContainerGeneric;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;

public class ContainerMRUInfo extends ContainerGeneric {

	public ContainerMRUInfo(InventoryPlayer invPlayer) {
		super(invPlayer);
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2) {
		return ItemStack.EMPTY;
	}
}
