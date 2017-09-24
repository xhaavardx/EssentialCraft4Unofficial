package essentialcraft.common.item;

import DummyCore.Client.IModelRegisterer;
import essentialcraft.utils.common.WindRelations;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;

public class ItemWindKeeper extends Item implements IModelRegisterer {

	public int rel;

	public ItemWindKeeper(int windReleased)
	{
		super();
		rel = windReleased;
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World p_77659_2_, EntityPlayer p_77659_3_, EnumHand p_77659_4_)
	{
		ItemStack p_77659_1_ = p_77659_3_.getHeldItem(p_77659_4_);
		p_77659_1_.shrink(1);
		p_77659_3_.playSound(SoundEvents.BLOCK_GLASS_BREAK, 1, 1);
		if(!p_77659_2_.isRemote)
		{
			WindRelations.increasePlayerWindRelations(p_77659_3_, rel);
			p_77659_3_.sendMessage(new TextComponentString(TextFormatting.DARK_AQUA+""+TextFormatting.ITALIC+"You hear a very pale 'Thank you'..."));
		}
		return super.onItemRightClick(p_77659_2_, p_77659_3_, p_77659_4_);
	}

	@Override
	public void registerModels() {
		ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation("essentialcraft:item/" + getRegistryName().getResourcePath(), "inventory"));
	}
}
