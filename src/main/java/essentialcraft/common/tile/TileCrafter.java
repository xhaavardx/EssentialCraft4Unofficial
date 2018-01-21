package essentialcraft.common.tile;

import java.util.Arrays;

import essentialcraft.common.inventory.InventoryCraftingFrame;
import essentialcraft.common.item.ItemCraftingFrame;
import essentialcraft.utils.common.ECUtils;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;

public class TileCrafter extends TileMRUGeneric {

	public TileCrafter() {
		super();
		mruStorage.setMaxMRU(0);
		setSlotsNum(11);
		slot0IsBoundGem = false;
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public int[] getOutputSlots() {
		return new int[] {9};
	}

	@Override
	public void update() {
		if(getWorld().isBlockIndirectlyGettingPowered(pos) > 0 || getWorld().getStrongPower(pos) > 0) {
			if(!hasFrame()) {
				makeRecipe();
			}
			else if(hasSufficientForCraftWithFrame()) {
				makeRecipe();
			}
		}
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack) {
		if(stack.isEmpty())
			return false;

		return slot == 10 ? isFrame(stack) : slot == 9 ? false : isItemFineForSlot(stack, slot);
	}

	public boolean isFrame(ItemStack is) {
		return !is.isEmpty() && is.getItem() instanceof ItemCraftingFrame && new InventoryCraftingFrame(is).inventory[9] != null;
	}


	public boolean isItemFineForSlot(ItemStack compared, int slotNum) {
		if(hasFrame()) {
			return areStacksTheSame(getRecipeFromFrame()[slotNum], compared, hasOreDict());
		}
		else {
			return true;
		}
	}

	public boolean hasOreDict() {
		return !getStackInSlot(10).isEmpty() && getStackInSlot(10).getItem() instanceof ItemCraftingFrame && getStackInSlot(10).getTagCompound() != null && !getStackInSlot(10).getTagCompound().getBoolean("ignoreOreDict");
	}

	public boolean hasFrame() {
		return !getStackInSlot(10).isEmpty() && getStackInSlot(10).getItem() instanceof ItemCraftingFrame && new InventoryCraftingFrame(getStackInSlot(10)).inventory[9] != null;
	}

	public boolean areStacksTheSame(ItemStack stk1, ItemStack stk2, boolean oreDict) {
		if(stk1.isEmpty() && stk2.isEmpty())
			return true;

		if(stk1.isEmpty() || stk2.isEmpty())
			return false;

		if(!oreDict) {
			if(!stk1.isItemEqual(stk2))
				return false;

			if(!ItemStack.areItemStacksEqual(stk1, stk2))
				return false;
		}
		else {
			if(!ECUtils.oreDictionaryCompare(stk1, stk2))
				return false;
		}

		return true;
	}

	public boolean hasSufficientForCraftWithFrame() {
		ItemStack[] frame = getRecipeFromFrame();
		for(int i = 0; i < 9; ++i) {
			ItemStack stk = getStackInSlot(i);
			if(!areStacksTheSame(frame[i],stk,hasOreDict()))
				return false;
		}

		return true;
	}

	public ItemStack[] getRecipeFromFrame() {
		if(!getStackInSlot(10).isEmpty() && getStackInSlot(10).getItem() instanceof ItemCraftingFrame) {
			InventoryCraftingFrame cInv = new InventoryCraftingFrame(getStackInSlot(10));
			if(!cInv.inventory[9].isEmpty()) {
				ItemStack[] arrayStk = new ItemStack[9];

				arrayStk[0] = cInv.inventory[0];
				arrayStk[1] = cInv.inventory[3];
				arrayStk[2] = cInv.inventory[6];
				arrayStk[3] = cInv.inventory[1];
				arrayStk[4] = cInv.inventory[4];
				arrayStk[5] = cInv.inventory[7];
				arrayStk[6] = cInv.inventory[2];
				arrayStk[7] = cInv.inventory[5];
				arrayStk[8] = cInv.inventory[8];

				return arrayStk;
			}
		}
		return null;
	}

	public static class InventoryCraftingNoContainer extends InventoryCrafting {

		private ItemStack[] stackList;
		private int inventoryWidth;

		public InventoryCraftingNoContainer(int p_i1807_2_, int p_i1807_3_) {
			super(null,p_i1807_2_,p_i1807_3_);
			int k = p_i1807_2_ * p_i1807_3_;
			stackList = new ItemStack[k];
			Arrays.fill(stackList, ItemStack.EMPTY);
			inventoryWidth = p_i1807_2_;
		}

		@Override
		public int getSizeInventory() {
			return stackList.length;
		}

		@Override
		public ItemStack getStackInSlot(int p_70301_1_) {
			return p_70301_1_ >= getSizeInventory() ? ItemStack.EMPTY : stackList[p_70301_1_];
		}

		@Override
		public ItemStack getStackInRowAndColumn(int p_70463_1_, int p_70463_2_) {
			if(p_70463_1_ >= 0 && p_70463_1_ < inventoryWidth) {
				int k = p_70463_1_ + p_70463_2_ * inventoryWidth;
				return getStackInSlot(k);
			}
			else {
				return ItemStack.EMPTY;
			}
		}

		@Override
		public ItemStack removeStackFromSlot(int p_70304_1_) {
			if(!stackList[p_70304_1_].isEmpty()) {
				ItemStack itemstack = stackList[p_70304_1_];
				stackList[p_70304_1_] = ItemStack.EMPTY;
				return itemstack;
			}
			else {
				return ItemStack.EMPTY;
			}
		}

		@Override
		public ItemStack decrStackSize(int p_70298_1_, int p_70298_2_) {
			if(!stackList[p_70298_1_].isEmpty()) {
				ItemStack itemstack;

				if(stackList[p_70298_1_].getCount() <= p_70298_2_) {
					itemstack = stackList[p_70298_1_];
					stackList[p_70298_1_] = ItemStack.EMPTY;
					return itemstack;
				}
				else {
					itemstack = stackList[p_70298_1_].splitStack(p_70298_2_);

					if(stackList[p_70298_1_].getCount() == 0) {
						stackList[p_70298_1_] = ItemStack.EMPTY;
					}

					return itemstack;
				}
			}
			else {
				return ItemStack.EMPTY;
			}
		}

		@Override
		public void setInventorySlotContents(int p_70299_1_, ItemStack p_70299_2_) {
			stackList[p_70299_1_] = p_70299_2_;
		}

	}

	public void makeRecipe() {
		InventoryCraftingNoContainer craftingInv = new InventoryCraftingNoContainer(3, 3);

		for(int i = 0; i < 9; ++i) {
			craftingInv.setInventorySlotContents(i, getStackInSlot(i));
		}

		ItemStack result = CraftingManager.findMatchingResult(craftingInv, getWorld());


		if(!result.isEmpty()) {
			if(!getStackInSlot(9).isEmpty()) {
				setInventorySlotContents(9, result.copy());
				decreaseStacks();
			}
			else if(getStackInSlot(9).isItemEqual(result) && ItemStack.areItemStackTagsEqual(result, getStackInSlot(9))) {
				if(getStackInSlot(9).getCount() + result.getCount() <= getInventoryStackLimit() && getStackInSlot(9).getCount() + result.getCount() <= result.getMaxStackSize()) {
					getStackInSlot(9).grow(result.getCount());
					decreaseStacks();
				}
			}
		}
	}

	public void decreaseStacks() {
		for(int i = 0; i < 9; ++i) {
			ItemStack is = getStackInSlot(i);
			if(!is.isEmpty()) {
				ItemStack container = is.getItem().getContainerItem(is);
				decrStackSize(i, 1);
				if((!getStackInSlot(i).isEmpty() || getStackInSlot(i).getCount() <= 0) && container != null) {
					setInventorySlotContents(i, container.copy());
				}
			}
		}
	}

}
