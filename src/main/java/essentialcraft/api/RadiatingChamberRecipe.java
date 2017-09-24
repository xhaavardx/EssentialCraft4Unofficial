package essentialcraft.api;

import java.util.List;

import com.google.common.collect.Lists;

import DummyCore.Utils.UnformedItemStack;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;
import net.minecraftforge.registries.IForgeRegistryEntry.Impl;

public class RadiatingChamberRecipe extends Impl<IRecipe> implements IRecipe {

	public UnformedItemStack[] recipeItems = {};
	public ItemStack result = ItemStack.EMPTY;
	public int mruRequired;
	public float upperBalanceLine,lowerBalanceLine;
	public float costModifier;

	public RadiatingChamberRecipe(UnformedItemStack[] ingred, ItemStack res, int mruReq, float[] balancePoints)
	{
		List<UnformedItemStack> l = Lists.<UnformedItemStack>newArrayList(ingred);
		l.removeIf(is->is==null||is.isEmpty());
		ingred = l.toArray(new UnformedItemStack[0]);
		recipeItems = ingred;
		result = res;
		mruRequired = mruReq;
		upperBalanceLine = balancePoints[0];
		lowerBalanceLine = balancePoints[1];
		costModifier = 1.0F;
	}

	public RadiatingChamberRecipe(UnformedItemStack[] ingred, ItemStack res, int mruReq, float[] balancePoints, float modifier)
	{
		List<UnformedItemStack> l = Lists.<UnformedItemStack>newArrayList(ingred);
		l.removeIf(is->is==null||is.isEmpty());
		ingred = l.toArray(new UnformedItemStack[0]);
		recipeItems = ingred;
		result = res;
		mruRequired = mruReq;
		upperBalanceLine = balancePoints[0];
		lowerBalanceLine = balancePoints[1];
		costModifier = modifier;
	}

	public RadiatingChamberRecipe(RadiatingChamberRecipe recipeByResult) {
		recipeItems = recipeByResult.recipeItems;
		result = recipeByResult.result;
		mruRequired = recipeByResult.mruRequired;
		upperBalanceLine = recipeByResult.upperBalanceLine;
		lowerBalanceLine = recipeByResult.lowerBalanceLine;
		costModifier = recipeByResult.costModifier;
	}

	@Override
	public String toString()
	{
		String retStr = super.toString();
		for(int i = 0; i < this.recipeItems.length; ++i)
		{
			retStr+="||item_"+i+":"+recipeItems[i];
		}
		retStr+="||output:"+result;
		retStr+="||mru:"+mruRequired;
		retStr+="||upperBalance:"+upperBalanceLine;
		retStr+="||lowerBalance:"+lowerBalanceLine;
		return retStr;
	}

	@Override
	public boolean matches(InventoryCrafting p_77569_1_, World p_77569_2_) {
		if(p_77569_1_.getSizeInventory() >= 2)
		{
			boolean ret = true;
			if(!recipeItems[0].itemStackMatches(p_77569_1_.getStackInSlot(0)))
				ret = false;
			for(int i = 1; i < 5; ++i)
			{
				if(!recipeItems[i].itemStackMatches(p_77569_1_.getStackInSlot(i)))
				{
					ret = false;
				}
			}
			return ret;
		}
		return false;
	}

	@Override
	public ItemStack getCraftingResult(InventoryCrafting inv) {
		return result;
	}

	@Override
	public boolean canFit(int width, int height) {
		return width*height>=2;
	}

	@Override
	public ItemStack getRecipeOutput() {
		return result;
	}
}
