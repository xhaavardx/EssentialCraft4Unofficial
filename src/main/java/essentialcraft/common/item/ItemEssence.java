package essentialcraft.common.item;

import java.util.List;
import java.util.Locale;

import DummyCore.Client.IModelRegisterer;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;

public class ItemEssence extends Item implements IModelRegisterer {
	public static String[] dropNames = {"Fire", "Water", "Earth", "Air"};

	public ItemEssence() {
		setMaxDamage(0);
		setHasSubtypes(true);
	}

	@Override
	public String getUnlocalizedName(ItemStack p_77667_1_) {
		return getUnlocalizedName()+dropNames[p_77667_1_.getItemDamage()%4];
	}

	@Override
	public void getSubItems(CreativeTabs p_150895_2_, NonNullList<ItemStack> p_150895_3_) {
		if(this.isInCreativeTab(p_150895_2_))
			for(int var4 = 0; var4 < 16; ++var4) {
				ItemStack min = new ItemStack(this, 1, var4);
				p_150895_3_.add(min);
			}
	}

	@Override
	public void addInformation(ItemStack par1ItemStack, World par2EntityPlayer, List<String> par3List, ITooltipFlag par4) {
		int t = par1ItemStack.getItemDamage()/4;
		if(t == 0)
			par3List.add("Rarity: \247f"+"Common");
		if(t == 1)
			par3List.add("Rarity: \247e"+"Uncommon");
		if(t == 2)
			par3List.add("Rarity: \247b"+"Rare");
		if(t == 3)
			par3List.add("Rarity: \247d"+"Exceptional");
	}

	@Override
	public EnumRarity getRarity(ItemStack par1ItemStack) {
		int t = par1ItemStack.getItemDamage()/4;
		if(t == 1)
			return EnumRarity.UNCOMMON;
		if(t == 2)
			return EnumRarity.RARE;
		if(t == 3)
			return EnumRarity.EPIC;
		return EnumRarity.COMMON;
	}

	@Override
	public void registerModels() {
		for(int i = 0; i < 16; i++) {
			ModelLoader.setCustomModelResourceLocation(this, i, new ModelResourceLocation("essentialcraft:item/essence", "type=" + dropNames[i%4].toLowerCase(Locale.ENGLISH)));
		}
	}
}
