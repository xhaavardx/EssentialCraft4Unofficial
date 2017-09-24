package essentialcraft.api;

import DummyCore.Utils.UnformedItemStack;
import net.minecraft.item.ItemStack;

public class MithrilineFurnaceRecipe {

	public final UnformedItemStack smelted;
	public final ItemStack result;
	public final float enderStarPulsesRequired;
	public final int requiredRecipeSize;

	public MithrilineFurnaceRecipe(UnformedItemStack is, ItemStack is_1, float f1, int reqStack)
	{
		smelted = is;
		result = is_1;
		enderStarPulsesRequired = f1;
		requiredRecipeSize = reqStack;
	}
}
