package essentialcraft.integration.crafttweaker;

import java.util.ArrayList;
import java.util.List;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.IAction;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import essentialcraft.api.MithrilineFurnaceRecipe;
import essentialcraft.api.MithrilineFurnaceRecipes;
import stanhebben.zenscript.annotations.Optional;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.essentialcraft.MithrilineFurnace")
public class MithrilineFurnace {

	@ZenMethod
	public static void addRecipe(IIngredient input, IItemStack output, float enderpower) {
		if(input == null || output == null) {
			CraftTweakerAPI.logError("Cannot turn "+input+" into a Mithriline Furnace Recipe");
			return;
		}

		boolean flag = true;
		for(MithrilineFurnaceRecipe rec : MithrilineFurnaceRecipes.RECIPES) {
			if(input.matches(CraftTweakerUtils.getIItemStack(rec.smelted)))
				flag = false;
		}

		if(flag)
			CraftTweakerAPI.apply(new AddRecipeAction(new MithrilineFurnaceRecipe(CraftTweakerUtils.toUnformedIS(input), CraftTweakerMC.getItemStack(output), enderpower, input.getAmount())));
		else
			CraftTweakerAPI.logWarning("Recipe already exists!");
	}

	@ZenMethod
	public static void removeRecipe(IIngredient input, @Optional IItemStack output) {
		if(input == null) {
			CraftTweakerAPI.logError("Cannot remove "+input+" from Mithriline Furnace Recipes");
			return;
		}

		final ArrayList<MithrilineFurnaceRecipe> toRemove = new ArrayList<MithrilineFurnaceRecipe>();
		MithrilineFurnaceRecipes.RECIPES.stream().
		filter(entry->input.matches(CraftTweakerUtils.getIItemStack(entry.smelted)) && (output == null || output.matches(CraftTweakerMC.getIItemStack(entry.result)))).
		forEach(entry->toRemove.add(entry));

		if(toRemove.isEmpty())
			CraftTweakerAPI.logWarning("No recipe for "+input.toString());
		else
			CraftTweakerAPI.apply(new RemoveRecipeAction(toRemove));
	}

	private static class AddRecipeAction implements IAction {
		MithrilineFurnaceRecipe rec;

		public AddRecipeAction(MithrilineFurnaceRecipe rec) {
			this.rec = rec;
		}

		@Override
		public void apply() {
			MithrilineFurnaceRecipes.addRecipe(rec);
		}

		@Override
		public String describe() {
			return "Adding Mithriline Furnace Recipe for "+rec.result.getDisplayName();
		}
	}

	private static class RemoveRecipeAction implements IAction {
		List<MithrilineFurnaceRecipe> rec;

		public RemoveRecipeAction(List<MithrilineFurnaceRecipe> rec) {
			this.rec = rec;
		}

		@Override
		public void apply() {
			for(MithrilineFurnaceRecipe entry : rec) {
				MithrilineFurnaceRecipes.removeRecipe(entry);
			}
		}

		@Override
		public String describe() {
			return "Removing "+rec.size()+" Mithriline Furnace Recipes";
		}
	}
}
