package essentialcraft.integration.crafttweaker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import DummyCore.Utils.UnformedItemStack;
import crafttweaker.CraftTweakerAPI;
import crafttweaker.IAction;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import essentialcraft.api.RadiatingChamberRecipe;
import essentialcraft.api.RadiatingChamberRecipes;
import stanhebben.zenscript.annotations.Optional;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.essentialcraft.RadiatingChamber")
public class RadiatingChamber {

	@ZenMethod
	public static void addRecipe(IItemStack[] ingredients, IItemStack output, int mru) {
		addRecipe(ingredients, output, mru, Float.MAX_VALUE, -Float.MAX_VALUE, 1F);
	}

	@ZenMethod
	public static void addRecipe(IItemStack[] ingredients, IItemStack output, int mru, float modifier) {
		addRecipe(ingredients, output, mru, Float.MAX_VALUE, -Float.MAX_VALUE, modifier);
	}

	@ZenMethod
	public static void addRecipe(IItemStack[] ingredients, IItemStack output, int mru, float upperBalance, float lowerBalance) {
		addRecipe(ingredients, output, mru, upperBalance, lowerBalance, 1F);
	}

	@ZenMethod
	public static void addRecipe(IItemStack[] ingredients, IItemStack output, int mru, float upperBalance, float lowerBalance, float modifier) {
		if(ingredients == null || ingredients.length > 2 || output == null) {
			CraftTweakerAPI.logError("Cannot turn "+Arrays.toString(ingredients)+" into a Radiating Chamber Recipe");
			return;
		}

		boolean allNull = true;

		UnformedItemStack[] input = new UnformedItemStack[ingredients.length];
		for(int i = 0; i < ingredients.length; i++) {
			if(ingredients[i] != null)
				allNull = false;
			input[i] = CraftTweakerUtils.toUnformedIS(ingredients[i]);
		}

		if(allNull) {
			CraftTweakerAPI.logError("Cannot turn "+Arrays.toString(ingredients)+" into a Magician Table Recipe");
			return;
		}

		if(RadiatingChamberRecipes.CRAFT_MATRIX_LIST.contains(Arrays.asList(input)))
			CraftTweakerAPI.logWarning("Recipe already exists!");
		else
			CraftTweakerAPI.apply(new AddRecipeAction(new RadiatingChamberRecipe(input, CraftTweakerMC.getItemStack(output), mru, new float[] {upperBalance, lowerBalance}, modifier)));
	}

	@ZenMethod
	public static void removeRecipe(IIngredient[] ingredients, float balance) {
		removeRecipe(ingredients, null, balance);
	}

	@ZenMethod
	public static void removeRecipe(IIngredient[] ingredients, @Optional IIngredient output) {
		removeRecipe(ingredients, output, Float.NaN);
	}

	@ZenMethod
	public static void removeRecipe(IIngredient[] ingredients, IIngredient output, float balance) {
		if(ingredients == null || ingredients.length > 2) {
			CraftTweakerAPI.logError("Cannot remove "+Arrays.toString(ingredients)+" from Radiating Chamber Recipes");
			return;
		}

		boolean hasNull = false;
		for(int i = 0; i < ingredients.length; i++) {
			if(ingredients[i] == null)
				hasNull = true;
		}

		if(hasNull) {
			CraftTweakerAPI.logError("Cannot remove "+Arrays.toString(ingredients)+" from Radiating Chamber Recipes");
			return;
		}

		ArrayList<RadiatingChamberRecipe> toRemove = new ArrayList<RadiatingChamberRecipe>();
		for(List<RadiatingChamberRecipe> entry : RadiatingChamberRecipes.RECIPES.values()) {
			for(RadiatingChamberRecipe rec : entry) {
				if(
						ingredients[0].matches(CraftTweakerUtils.getIItemStack(rec.recipeItems[0])) &&
						(rec.recipeItems[1].isEmpty() || ingredients.length == 2 && ingredients[1].matches(CraftTweakerUtils.getIItemStack(rec.recipeItems[2])) &&
						(output == null || output.matches(CraftTweakerMC.getIItemStack(rec.result))) &&
						(Float.isNaN(balance) || balance <= rec.upperBalanceLine && balance >= rec.lowerBalanceLine)))
					toRemove.add(rec);
			}
		}

		if(toRemove.isEmpty())
			CraftTweakerAPI.logWarning("No recipe for "+Arrays.toString(ingredients));
		else
			CraftTweakerAPI.apply(new RemoveRecipeAction(toRemove));
	}

	private static class AddRecipeAction implements IAction {
		RadiatingChamberRecipe rec;

		public AddRecipeAction(RadiatingChamberRecipe rec) {
			this.rec = rec;
		}

		@Override
		public void apply() {
			RadiatingChamberRecipes.addRecipe(rec);
		}

		@Override
		public String describe() {
			return "Adding Radiating Chamber Recipe for "+rec.result.getDisplayName();
		}
	}

	private static class RemoveRecipeAction implements IAction {
		List<RadiatingChamberRecipe> rec;

		public RemoveRecipeAction(List<RadiatingChamberRecipe> rec) {
			this.rec = rec;
		}

		@Override
		public void apply() {
			for(RadiatingChamberRecipe entry : rec) {
				RadiatingChamberRecipes.removeRecipe(entry);
			}
		}

		@Override
		public String describe() {
			return "Removing "+rec.size()+" Radiating Chamber Recipe";
		}
	}
}
