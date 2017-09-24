package essentialcraft.common.item;

import java.util.List;

import DummyCore.Client.IModelRegisterer;
import DummyCore.Utils.MiscUtils;
import essentialcraft.common.mod.EssentialCraftCore;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;

public class ItemKnowledgeBook extends Item implements IModelRegisterer {

	public ItemKnowledgeBook() {
		super();
		this.maxStackSize = 1;
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World par2World, EntityPlayer par3EntityPlayer, EnumHand hand)
	{
		EssentialCraftCore.proxy.openBookGUIForPlayer();
		return super.onItemRightClick(par2World, par3EntityPlayer, hand);
	}

	@Override
	public void addInformation(ItemStack par1ItemStack, World par2EntityPlayer, List<String> par3List, ITooltipFlag par4)
	{
		NBTTagCompound theTag = MiscUtils.getStackTag(par1ItemStack);
		par3List.add("\u00a76" + I18n.translateToLocal("essentialcraft.txt.book.containedKnowledge"));
		int tier = theTag.getInteger("tier");
		for(int i = 0; i <= tier; ++i)
		{
			par3List.add("\u00a77-\u00a7o" + I18n.translateToLocal("essentialcraft.txt.book.tier_"+i));
		}
	}

	@Override
	public void getSubItems(CreativeTabs p_150895_2_, NonNullList<ItemStack> p_150895_3_)
	{
		if(this.isInCreativeTab(p_150895_2_))
			for(int i = 0; i < 5; ++i)
			{
				ItemStack book = new ItemStack(this);
				NBTTagCompound bookTag = new NBTTagCompound();
				bookTag.setInteger("tier", i);
				book.setTagCompound(bookTag);
				p_150895_3_.add(book);
			}
	}

	@Override
	public void registerModels() {
		ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation("essentialcraft:item/research_book", "inventory"));
	}
}
