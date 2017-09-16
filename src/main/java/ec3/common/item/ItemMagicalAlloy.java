package ec3.common.item;

import java.util.List;

import DummyCore.Client.IItemColor;
import DummyCore.Client.IModelRegisterer;
import DummyCore.Client.ModelUtils;
import DummyCore.Utils.MiscUtils;
import ec3.api.OreSmeltingRecipe;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class ItemMagicalAlloy extends Item implements IItemColor, IModelRegisterer {

	public ItemMagicalAlloy() {
		setMaxDamage(0);
		setHasSubtypes(true);
	}

	@Override
	public void getSubItems(Item p_150895_1_, CreativeTabs p_150895_2_, List<ItemStack> p_150895_3_) {
		for(OreSmeltingRecipe recipe : OreSmeltingRecipe.RECIPES) {
			ItemStack toAdd = new ItemStack(p_150895_1_, 1, 0);
			NBTTagCompound tag = MiscUtils.getStackTag(toAdd);
			tag.setString("ore", recipe.oreName);
			p_150895_3_.add(toAdd);
		}
	}

	@Override
	public void addInformation(ItemStack p_77624_1_, EntityPlayer p_77624_2_, List<String> p_77624_3_, boolean p_77624_4_) {
		p_77624_3_.add(OreSmeltingRecipe.getLocalizedOreName(p_77624_1_));
	}

	@Override
	public int getColorFromItemstack(ItemStack p_82790_1_, int p_82790_2_) {
		return p_82790_2_ == 0 ? 0xFFFFFF : OreSmeltingRecipe.getColorFromItemStack(p_82790_1_);
	}

	@Override
	public void registerModels() {
		ModelUtils.setItemModelSingleIcon(this, "essentialcraft:item/magicalAlloy");
	}
}
