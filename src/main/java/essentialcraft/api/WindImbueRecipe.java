package essentialcraft.api;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.ItemStack;

public class WindImbueRecipe {

	public ItemStack result = ItemStack.EMPTY;
	public ItemStack transforming = ItemStack.EMPTY;
	public int enderEnergy;

	public static final List<WindImbueRecipe> RECIPES = new ArrayList<WindImbueRecipe>();

	public static WindImbueRecipe findRecipeByComponent(ItemStack c) {
		if(c.isEmpty())
			return null;

		for(int i = 0; i < RECIPES.size(); ++i) {
			if(c.isItemEqual(RECIPES.get(i).transforming))
				return RECIPES.get(i);
		}

		return null;
	}

	public static WindImbueRecipe findRecipeByResult(ItemStack r) {
		if(r.isEmpty())
			return null;

		for(int i = 0; i < RECIPES.size(); ++i)
		{
			if(r.isItemEqual(RECIPES.get(i).result))
				return RECIPES.get(i);
		}

		return null;
	}

	public WindImbueRecipe(ItemStack s1, ItemStack s2, int i) {
		transforming = s1;
		result = s2;
		enderEnergy = i;
		RECIPES.add(this);
	}

	public static void removeRecipe(WindImbueRecipe rec) {
		RECIPES.remove(rec);
	}

	public static void removeRecipeByComponent(ItemStack c) {
		removeRecipe(findRecipeByComponent(c));
	}

	public static void removeRecipeByResult(ItemStack r) {
		removeRecipe(findRecipeByResult(r));
	}

	public static void removeRecipe(ItemStack s, ItemStack r) {
		for(int i = 0; i < RECIPES.size(); ++i) {
			WindImbueRecipe rec = RECIPES.get(i);
			if(s.isItemEqual(rec.transforming) && r.isItemEqual(rec.result)) {
				removeRecipe(rec);
				return;
			}
		}
	}
}
