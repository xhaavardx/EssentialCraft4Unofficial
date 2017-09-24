package essentialcraft.integration.crafttweaker;

import DummyCore.Utils.UnformedItemStack;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.oredict.IOreDictEntry;

public class CraftTweakerUtils {

	public static UnformedItemStack toUnformedIS(IIngredient ingredient) {
		if(ingredient == null)
			return null;
		else {
			if(ingredient instanceof IOreDictEntry) {
				return new UnformedItemStack(((IOreDictEntry)ingredient).getName());
			}
			else if(ingredient instanceof IItemStack) {
				return new UnformedItemStack(CraftTweakerMC.getItemStack((IItemStack)ingredient));
			}
			else
				return null;
		}
	}

	public static IItemStack getIItemStack(UnformedItemStack uis) {
		return CraftTweakerMC.getIItemStack(uis.getISToDraw(0));
	}
}
