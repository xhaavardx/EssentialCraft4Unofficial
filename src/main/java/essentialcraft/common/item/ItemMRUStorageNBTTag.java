package essentialcraft.common.item;

import java.util.List;
import java.util.Locale;

import DummyCore.Client.IItemColor;
import DummyCore.Client.IModelRegisterer;
import DummyCore.Utils.MathUtils;
import baubles.api.BaubleType;
import baubles.api.IBauble;
import essentialcraft.api.IMRUHandlerItem;
import essentialcraft.utils.common.ECUtils;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;

public class ItemMRUStorageNBTTag extends Item implements IMRUHandlerItem, IBauble, IItemColor, IModelRegisterer {
	public int[] maxMRU = new int[6];
	public static int[] colors = {0x555555, 0x665555, 0x775555, 0x885555, 0x995555, 0xaa5555, 0xbb5555, 0xcc5555, 0xdd5555, 0xee6666, 0xff6666};
	public static String[] dropNames = {"SoulShard", "SoulSphere", "DarkSoulMatter", "RedSoulMatter", "MatterOfEternity", "Unknown"};
	public ItemMRUStorageNBTTag(int[] maxMRU) {
		super();
		this.maxMRU = maxMRU;
		setMaxStackSize(1);
	}

	@Override
	public boolean increaseMRU(ItemStack stack, int amount) {
		if(ECUtils.getStackTag(stack).getInteger("mru")+amount >= 0 && ECUtils.getStackTag(stack).getInteger("mru")+amount<=ECUtils.getStackTag(stack).getInteger("maxMRU")) {
			ECUtils.getStackTag(stack).setInteger("mru", ECUtils.getStackTag(stack).getInteger("mru")+amount);
			return true;
		}
		return false;
	}

	@Override
	public int getMRU(ItemStack stack) {
		return ECUtils.getStackTag(stack).getInteger("mru");
	}

	@Override
	public void onUpdate(ItemStack itemStack, World world, Entity entity, int indexInInventory, boolean isCurrentItem) {
		ECUtils.initMRUTag(itemStack, maxMRU[itemStack.getItemDamage()]);
	}

	@Override
	public void addInformation(ItemStack par1ItemStack, World par2EntityPlayer, List<String> par3List, ITooltipFlag par4) {
		par3List.add(ECUtils.getStackTag(par1ItemStack).getInteger("mru") + "/" + ECUtils.getStackTag(par1ItemStack).getInteger("maxMRU") + " MRU");
	}

	@Override
	public void getSubItems(CreativeTabs par2CreativeTabs, NonNullList<ItemStack> par3List) {
		if(this.isInCreativeTab(par2CreativeTabs))
			for(int var4 = 0; var4 < 5; ++var4) {
				ItemStack min = new ItemStack(this, 1, var4);
				ECUtils.initMRUTag(min, getMaxMRU(min));
				ItemStack max = new ItemStack(this, 1, var4);
				ECUtils.initMRUTag(max, getMaxMRU(max));
				ECUtils.getStackTag(max).setInteger("mru", ECUtils.getStackTag(max).getInteger("maxMRU"));
				par3List.add(min);
				par3List.add(max);
			}
	}

	@Override
	public String getUnlocalizedName(ItemStack p_77667_1_) {
		return getUnlocalizedName()+dropNames[Math.min(p_77667_1_.getItemDamage(), dropNames.length-1)];
	}

	@Override
	public int getMaxMRU(ItemStack stack) {
		int dam = stack.getItemDamage();
		return maxMRU[dam];
	}

	@Override
	public int getColorFromItemstack(ItemStack par1ItemStack, int par2) {
		int dam = par1ItemStack.getItemDamage();
		if(dam != -1) {
			int currentMRU = ((IMRUHandlerItem)par1ItemStack.getItem()).getMRU(par1ItemStack);
			int maxMRU = ((IMRUHandlerItem)par1ItemStack.getItem()).getMaxMRU(par1ItemStack);
			int percentage = MathUtils.getPercentage(currentMRU, maxMRU);
			percentage /= 10;
			return colors[percentage];
		}
		return 16777215;
	}

	@Override
	public BaubleType getBaubleType(ItemStack itemstack) {
		return BaubleType.RING;
	}

	@Override
	public void onWornTick(ItemStack itemstack, EntityLivingBase player) {}

	@Override
	public void onEquipped(ItemStack itemstack, EntityLivingBase player) {}

	@Override
	public void onUnequipped(ItemStack itemstack, EntityLivingBase player) {}

	@Override
	public boolean canEquip(ItemStack itemstack, EntityLivingBase player) {
		return true;
	}

	@Override
	public boolean canUnequip(ItemStack itemstack, EntityLivingBase player) {
		return true;
	}

	@Override
	public void registerModels() {
		for(int i = 0; i < dropNames.length-1; i++) {
			ModelLoader.setCustomModelResourceLocation(this, i, new ModelResourceLocation("essentialcraft:item/storage", "type=" + dropNames[i].toLowerCase(Locale.ENGLISH)));
		}
	}

	@Override
	public boolean isStorage(ItemStack stack) {
		return true;
	}
}
