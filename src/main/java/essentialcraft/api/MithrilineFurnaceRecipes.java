package essentialcraft.api;

import java.util.ArrayList;
import java.util.List;

import DummyCore.Utils.UnformedItemStack;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class MithrilineFurnaceRecipes {

	public static final List<MithrilineFurnaceRecipe> RECIPES = new ArrayList<MithrilineFurnaceRecipe>();

	public static void addRecipe(ItemStack component, ItemStack result, float cost, int req) {
		UnformedItemStack is = new UnformedItemStack(component);
		addRecipe(new MithrilineFurnaceRecipe(is,result,cost,req));
	}

	public static void addRecipe(String oreDictName, ItemStack result, float cost, int req) {
		UnformedItemStack is = new UnformedItemStack(oreDictName);
		if(!is.isEmpty())
			addRecipe(new MithrilineFurnaceRecipe(is,result,cost,req));
	}

	public static void addRecipe(MithrilineFurnaceRecipe rec) {
		if(RECIPES.contains(rec))
			return;
		else
			RECIPES.add(rec);
	}

	public static void removeRecipeByComponent(ItemStack component) {
		removeRecipe(findRecipeByComponent(component));
	}

	public static void removeRecipeByComponent(String oreDictName) {
		try {
			removeRecipe(findRecipeByComponent(OreDictionary.getOres(oreDictName).get(0)));
		}
		catch(Exception e) {}
	}

	public static void removeRecipeByResult(ItemStack result) {
		removeRecipe(findRecipeByResult(result));
	}

	public static void removeRecipe(ItemStack component, ItemStack result) {
		for(int i = 0; i < RECIPES.size(); ++i) {
			MithrilineFurnaceRecipe rec = RECIPES.get(i);
			if(rec != null && rec.smelted.itemStackMatches(component) && rec.result.isItemEqual(result)) {
				removeRecipe(rec);
				return;
			}
		}
	}

	public static void removeRecipe(String oreDictName, ItemStack result) {
		try {
			for(int i = 0; i < RECIPES.size(); ++i) {
				MithrilineFurnaceRecipe rec = RECIPES.get(i);
				if(rec != null && rec.smelted.itemStackMatches(OreDictionary.getOres(oreDictName).get(0)) && rec.result.isItemEqual(result)) {
					removeRecipe(rec);
					return;
				}
			}
		}
		catch(Exception e) {}
	}

	public static void removeRecipe(MithrilineFurnaceRecipe rec) {
		RECIPES.remove(rec);
	}

	public static MithrilineFurnaceRecipe findRecipeByComponent(ItemStack is) {
		return RECIPES.stream().filter(recipe->recipe.smelted.itemStackMatches(is)).findAny().orElse(null);
	}

	public static MithrilineFurnaceRecipe findRecipeByResult(ItemStack is) {
		return RECIPES.stream().filter(recipe->recipe.result.isItemEqual(is)).findAny().orElse(null);
	}
}
