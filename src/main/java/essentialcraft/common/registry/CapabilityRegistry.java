package essentialcraft.common.registry;

import essentialcraft.common.capabilities.espe.CapabilityESPEHandler;
import essentialcraft.common.capabilities.mru.CapabilityMRUHandler;

public class CapabilityRegistry {

	public static void register() {
		CapabilityMRUHandler.register();
		CapabilityESPEHandler.register();
	}
}
