package essentialcraft.integration.jei;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.Lists;

import essentialcraft.api.DemonTrade;
import essentialcraft.common.block.BlocksCore;
import essentialcraft.common.item.ItemsCore;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeCategory;
import mezz.jei.api.recipe.IRecipeHandler;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.translation.I18n;

public class DemonTrading {

	public static final String UID = "essentialcraft:demonTrade";

	public static List<DemonTrading.Wrapper> getRecipes() {
		ArrayList<DemonTrading.Wrapper> ret = Lists.<DemonTrading.Wrapper>newArrayList();
		for(DemonTrade rec : DemonTrade.trades) {
			ret.add(new DemonTrading.Wrapper(rec));
		}
		return ret;
	}

	public static class Wrapper implements IRecipeWrapper {

		private DemonTrade rec;

		public Wrapper(DemonTrade rec) {
			this.rec = rec;
		}

		@Override
		public void getIngredients(IIngredients arg0) {
			ItemStack ret;
			if(rec.entityType != null)
				ret = new ItemStack(ItemsCore.soul, 1, DemonTrade.allMobs.indexOf(rec));
			else
				ret = rec.desiredItem;
			arg0.setInputs(ItemStack.class, Lists.newArrayList(ret, new ItemStack(BlocksCore.demonicPentacle)));
		}
	}

	public static class Handler implements IRecipeHandler<DemonTrading.Wrapper> {

		@Override
		public String getRecipeCategoryUid(DemonTrading.Wrapper arg0) {
			return UID;
		}

		@Override
		public Class<DemonTrading.Wrapper> getRecipeClass() {
			return DemonTrading.Wrapper.class;
		}

		@Override
		public DemonTrading.Wrapper getRecipeWrapper(DemonTrading.Wrapper arg0) {
			return arg0;
		}

		@Override
		public boolean isRecipeValid(DemonTrading.Wrapper recipe) {
			return !recipe.rec.desiredItem.isEmpty();
		}
	}

	public static class Category implements IRecipeCategory<DemonTrading.Wrapper> {

		private final IDrawable BG;

		public Category(IGuiHelper gh) {
			BG = gh.createDrawable(new ResourceLocation("essentialcraft:textures/gui/demon.png"), 0, 0, 176, 76);
		}

		@Override
		public IDrawable getBackground() {
			return BG;
		}

		@Override
		public String getTitle() {
			return I18n.translateToLocal("jei.essentialcraft.recipe.demonTrade");
		}

		@Override
		public String getUid() {
			return UID;
		}

		@Override
		public void setRecipe(IRecipeLayout arg0, DemonTrading.Wrapper arg1, IIngredients arg2) {
			arg0.getItemStacks().init(0, true, 79, 29);
			arg0.getItemStacks().init(1, true, 79, 47);
			arg0.getItemStacks().set(0, arg2.getInputs(ItemStack.class).get(0));
			arg0.getItemStacks().set(1, arg2.getInputs(ItemStack.class).get(1));
		}

		@Override
		public String getModName() {
			return "essentialcraft";
		}
	}
}
