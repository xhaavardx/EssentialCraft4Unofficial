package ec3.integration.minetweaker;

import DummyCore.Utils.UnformedItemStack;
import minetweaker.api.item.IIngredient;
import minetweaker.api.item.IItemStack;
import minetweaker.api.minecraft.MineTweakerMC;
import minetweaker.api.oredict.IOreDictEntry;

public class MineTweakerUtils {

	public static UnformedItemStack toUnformedIS(IIngredient ingredient) {
		if(ingredient == null)
			return null;
		else {
			if(ingredient instanceof IOreDictEntry) {
				return new UnformedItemStack(((IOreDictEntry)ingredient).getName());
			}
			else if(ingredient instanceof IItemStack) {
				return new UnformedItemStack(MineTweakerMC.getItemStack((IItemStack)ingredient));
			}
			else
				return null;
		}
	}

	public static IItemStack getIItemStack(UnformedItemStack uis) {
		return MineTweakerMC.getIItemStack(uis.getISToDraw(0));
	}
}
