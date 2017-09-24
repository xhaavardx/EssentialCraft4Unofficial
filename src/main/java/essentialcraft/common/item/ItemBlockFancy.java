package essentialcraft.common.item;

import java.util.List;

import essentialcraft.common.block.BlockFancy;
import net.minecraft.block.Block;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;

public class ItemBlockFancy extends ItemBlock {

	public ItemBlockFancy(Block p_i45328_1_) {
		super(p_i45328_1_);
		this.setHasSubtypes(true);
		this.setMaxDamage(0);
	}

	@Override
	public int getMetadata(int par1)
	{
		return par1;
	}

	@Override
	public void addInformation(ItemStack par1ItemStack, World par2EntityPlayer, List<String> par3List, ITooltipFlag par4)
	{
		par3List.add(I18n.translateToLocal("essentialcraft.desc.fancy."+BlockFancy.overlays[par1ItemStack.getItemDamage()]));
	}
}
