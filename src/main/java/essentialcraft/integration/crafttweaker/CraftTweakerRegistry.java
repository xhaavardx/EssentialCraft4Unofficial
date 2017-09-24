package essentialcraft.integration.crafttweaker;

import org.apache.logging.log4j.LogManager;

import crafttweaker.CraftTweakerAPI;
import net.minecraftforge.fml.common.Loader;

public class CraftTweakerRegistry {
	public static void register() {
		if(Loader.isModLoaded("crafttweaker")) {
			try {
				CraftTweakerAPI.registerClass(DemonTrading.class);
				CraftTweakerAPI.registerClass(MagicianTable.class);
				CraftTweakerAPI.registerClass(MithrilineFurnace.class);
				CraftTweakerAPI.registerClass(RadiatingChamber.class);
				CraftTweakerAPI.registerClass(WindImbue.class);
				LogManager.getLogger().trace("Successfully registered CraftTweaker integration!");
			}
			catch(Exception e) {
				LogManager.getLogger().error("Unable to add CraftTweaker Integration.");
			}
		}
	}
}
