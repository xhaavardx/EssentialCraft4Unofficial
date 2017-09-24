package essentialcraft.common.item;

import DummyCore.Client.IModelRegisterer;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.ItemAxe;
import net.minecraftforge.client.model.ModelLoader;

public class ItemAxeEC extends ItemAxe implements IModelRegisterer {

	public ItemAxeEC(ToolMaterial p_i45347_1_) {
		super(p_i45347_1_, p_i45347_1_.getDamageVsEntity(), p_i45347_1_.getEfficiencyOnProperMaterial());
	}

	@Override
	public void registerModels() {
		ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation("essentialcraft:item/" + getRegistryName().getResourcePath(), "inventory"));
	}
}
