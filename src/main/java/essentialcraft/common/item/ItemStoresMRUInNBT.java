package essentialcraft.common.item;

import java.util.List;

import DummyCore.Utils.MiscUtils;
import essentialcraft.api.IMRUHandlerItem;
import essentialcraft.utils.common.ECUtils;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;

public class ItemStoresMRUInNBT extends Item implements IMRUHandlerItem {
	int maxMRU = 5000;

	public ItemStoresMRUInNBT() {
		super();
	}

	public ItemStoresMRUInNBT setMaxMRU(int max) {
		maxMRU = max;
		return this;
	}

	@Override
	public boolean increaseMRU(ItemStack stack, int amount) {
		if(MiscUtils.getStackTag(stack).getInteger("mru")+amount >= 0 && MiscUtils.getStackTag(stack).getInteger("mru")+amount<=MiscUtils.getStackTag(stack).getInteger("maxMRU")) {
			MiscUtils.getStackTag(stack).setInteger("mru", MiscUtils.getStackTag(stack).getInteger("mru")+amount);
			return true;
		}
		return false;
	}

	@Override
	public int getMRU(ItemStack stack) {
		return MiscUtils.getStackTag(stack).getInteger("mru");
	}

	@Override
	public void onUpdate(ItemStack itemStack, World world, Entity entity, int indexInInventory, boolean isCurrentItem)
	{
		ECUtils.initMRUTag(itemStack, maxMRU);
	}

	@Override
	public void addInformation(ItemStack par1ItemStack, World par2EntityPlayer, List<String> par3List, ITooltipFlag par4)
	{
		par3List.add(ECUtils.getStackTag(par1ItemStack).getInteger("mru") + "/" + ECUtils.getStackTag(par1ItemStack).getInteger("maxMRU") + " MRU");
	}

	@Override
	public void getSubItems(CreativeTabs par2CreativeTabs, NonNullList<ItemStack> par3List) {
		if(this.isInCreativeTab(par2CreativeTabs))
			for(int var4 = 0; var4 < 1; ++var4) {
				ItemStack min = new ItemStack(this, 1, 0);
				ECUtils.initMRUTag(min, maxMRU);
				ItemStack max = new ItemStack(this, 1, 0);
				ECUtils.initMRUTag(max, maxMRU);
				ECUtils.getStackTag(max).setInteger("mru", ECUtils.getStackTag(max).getInteger("maxMRU"));
				par3List.add(min);
				par3List.add(max);
			}
	}

	@Override
	public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
		return !oldStack.getItem().equals(newStack.getItem());
	}

	@Override
	public int getMaxMRU(ItemStack stack) {
		return this.maxMRU;
	}

	@Override
	public boolean isStorage(ItemStack stack) {
		return false;
	}
}
