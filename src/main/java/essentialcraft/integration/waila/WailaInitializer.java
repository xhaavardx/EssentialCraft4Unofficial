package essentialcraft.integration.waila;

import net.minecraftforge.fml.common.event.FMLInterModComms;

public class WailaInitializer {

	public static void sendIMC() {
		FMLInterModComms.sendMessage("waila", "register", "essentialcraft.integration.waila.WailaDataProvider.callbackRegister");
	}
}
