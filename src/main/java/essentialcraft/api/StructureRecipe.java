package essentialcraft.api;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;
import net.minecraftforge.registries.IForgeRegistryEntry.Impl;

public class StructureRecipe extends Impl<IRecipe> implements IRecipe {

	public List<StructureBlock> structure = new ArrayList<StructureBlock>();
	public ItemStack referal = ItemStack.EMPTY;

	public StructureRecipe(ItemStack ref, StructureBlock... positions)
	{
		referal = ref;
		structure = Arrays.<StructureBlock>asList(positions);
	}

	@Override
	public boolean matches(InventoryCrafting inv, World worldIn) {
		return false;
	}

	@Override
	public ItemStack getCraftingResult(InventoryCrafting inv) {
		return referal;
	}

	@Override
	public boolean canFit(int width, int height) {
		return width*height>=structure.size();
	}

	@Override
	public ItemStack getRecipeOutput() {
		return referal;
	}
}
