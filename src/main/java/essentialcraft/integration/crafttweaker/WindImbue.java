package essentialcraft.integration.crafttweaker;

import java.util.ArrayList;
import java.util.List;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.IAction;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import essentialcraft.api.WindImbueRecipe;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.Optional;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenRegister
@ZenClass("mods.essentialcraft.WindImbue")
public class WindImbue {

	@ZenMethod
	public static void addRecipe(IItemStack input, IItemStack output, int energy) {
		if(input == null || output == null) {
			CraftTweakerAPI.logError("Cannot turn "+input+" into a Wind Imbue Recipe");
			return;
		}

		boolean flag = true;
		for(WindImbueRecipe rec : WindImbueRecipe.RECIPES) {
			if(input.matches(CraftTweakerMC.getIItemStack(rec.transforming)))
				flag = false;
		}

		if(flag)
			CraftTweakerAPI.apply(new AddRecipeAction(CraftTweakerMC.getItemStack(input),CraftTweakerMC.getItemStack(output),energy));
		else
			CraftTweakerAPI.logWarning("Recipe already exists!");
	}

	@ZenMethod
	public static void removeRecipe(IItemStack input, @Optional IItemStack output) {
		if(input == null) {
			CraftTweakerAPI.logError("Cannot remove "+input+" from Wind Imbue Recipes");
			return;
		}

		final ArrayList<WindImbueRecipe> toRemove = new ArrayList<WindImbueRecipe>();
		WindImbueRecipe.RECIPES.stream().
		filter(entry->input.matches(CraftTweakerMC.getIItemStack(entry.transforming)) && (output == null || output.matches(CraftTweakerMC.getIItemStack(entry.result)))).
		forEach(entry->toRemove.add(entry));

		if(toRemove.isEmpty())
			CraftTweakerAPI.logWarning("No recipe for "+input.toString());
		else
			CraftTweakerAPI.apply(new RemoveRecipeAction(toRemove));
	}

	private static class AddRecipeAction implements IAction {
		ItemStack input;
		ItemStack output;
		int energy;
		WindImbueRecipe rec;

		public AddRecipeAction(ItemStack input, ItemStack output, int energy) {
			this.input = input;
			this.output = output;
			this.energy = energy;
		}

		@Override
		public void apply() {
			rec = new WindImbueRecipe(input, output, energy);
		}

		@Override
		public String describe() {
			return "Adding Wind Imbue Recipe for "+output.getDisplayName();
		}
	}

	private static class RemoveRecipeAction implements IAction {
		List<WindImbueRecipe> rec;

		public RemoveRecipeAction(List<WindImbueRecipe> rec) {
			this.rec = rec;
		}

		@Override
		public void apply() {
			for(WindImbueRecipe entry : rec) {
				WindImbueRecipe.removeRecipe(entry);
			}
		}

		@Override
		public String describe() {
			return "Removing "+rec.size()+" Wind Imbue Recipes";
		}
	}
}
