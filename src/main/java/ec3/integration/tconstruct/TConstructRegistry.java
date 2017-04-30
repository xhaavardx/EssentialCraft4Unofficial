package ec3.integration.tconstruct;

import org.apache.logging.log4j.LogManager;

import net.minecraftforge.fml.common.Loader;
import slimeknights.tconstruct.library.TinkerRegistry;

public class TConstructRegistry {
	public static void register() {
		if(Loader.isModLoaded("tconstruct")) {
			try {
				//Directly registering the modifier in this class causes a NoClassDefFoundError
				ModMRUReinforced.register();
				LogManager.getLogger().trace("Successfully registered TConstruct integration!");
			}
			catch(Exception e) {
				LogManager.getLogger().error("Unable to add TConstruct Integration.");
			}
		}
	}
}
