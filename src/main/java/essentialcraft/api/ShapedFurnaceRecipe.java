package essentialcraft.api;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;
import net.minecraftforge.registries.IForgeRegistryEntry.Impl;

public class ShapedFurnaceRecipe extends Impl<IRecipe> implements IRecipe {

	public ItemStack result = ItemStack.EMPTY;
	public ItemStack smelted = ItemStack.EMPTY;

	public ShapedFurnaceRecipe(ItemStack i, ItemStack i_1)
	{
		smelted = i;
		result = i_1;
	}

	@Override
	public boolean matches(InventoryCrafting inv, World worldIn) {
		return inv.getStackInSlot(0).isItemEqual(smelted) && inv.getStackInSlot(1).isItemEqual(result);
	}

	@Override
	public ItemStack getCraftingResult(InventoryCrafting inv) {
		return result;
	}

	@Override
	public boolean canFit(int width, int height) {
		return width*height>=1;
	}

	@Override
	public ItemStack getRecipeOutput() {
		return result;
	}
}
