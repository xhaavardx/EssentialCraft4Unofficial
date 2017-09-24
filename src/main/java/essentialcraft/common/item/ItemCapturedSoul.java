package essentialcraft.common.item;

import DummyCore.Client.IModelRegisterer;
import DummyCore.Client.ModelUtils;
import essentialcraft.api.DemonTrade;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.fml.common.registry.EntityEntry;

public class ItemCapturedSoul extends Item implements IModelRegisterer {

	public ItemCapturedSoul() {
		setHasSubtypes(true);
		setMaxDamage(0);
	}

	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> lst) {
		if(this.isInCreativeTab(tab))
			for(int i = 0; i < DemonTrade.allMobs.size(); ++i) {
				lst.add(new ItemStack(this,1,i));
			}
	}

	@Override
	public String getItemStackDisplayName(ItemStack stack) {
		EntityEntry e = stack.getItemDamage() < DemonTrade.allMobs.size() ? DemonTrade.allMobs.get(stack.getItemDamage()) : null;
		String s = e != null ? "entity." + e.getName() + ".name" : "";
		return super.getItemStackDisplayName(stack) + " - " + I18n.translateToLocal(s);
	}

	@Override
	public void registerModels() {
		ModelUtils.setItemModelSingleIcon(this, "essentialcraft:item/soul");
	}
}
