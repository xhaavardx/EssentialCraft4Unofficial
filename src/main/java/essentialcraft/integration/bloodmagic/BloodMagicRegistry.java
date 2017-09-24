package essentialcraft.integration.bloodmagic;

import org.apache.logging.log4j.LogManager;

import essentialcraft.common.item.ItemsCore;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Loader;

public class BloodMagicRegistry {

	public static void register() {
		try {
			if(Loader.isModLoaded("BloodMagic")) {
				ItemStack emptySoulStone = new ItemStack(ItemsCore.soulStone,1,0);
				ItemStack filledSoulStone = new ItemStack(ItemsCore.soulStone,1,1);
				//WayofTime.bloodmagic.api.registry.AltarRecipeRegistry.AltarRecipe recipe = new WayofTime.bloodmagic.api.registry.AltarRecipeRegistry.AltarRecipe(emptySoulStone,filledSoulStone,WayofTime.bloodmagic.api.altar.EnumAltarTier.ONE,250,2,1,false);
				//WayofTime.bloodmagic.api.registry.AltarRecipeRegistry.registerRecipe(recipe);
				LogManager.getLogger().trace("Successfully registered BloodMagic integration!");
			}
		}
		catch(Exception e) {
			LogManager.getLogger().error("Unable to add BloodMagic Integration.");
		}
	}
}
