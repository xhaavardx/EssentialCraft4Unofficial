package essentialcraft.common.capabilities.espe;

import essentialcraft.api.IESPEHandler;
import essentialcraft.api.IESPEHandlerItem;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

public class CapabilityESPEHandler {

	@CapabilityInject(IESPEHandler.class)
	public static Capability<IESPEHandler> ESPE_HANDLER_CAPABILITY = null;
	@CapabilityInject(IESPEHandlerItem.class)
	public static Capability<IESPEHandlerItem> ESPE_HANDLER_ITEM_CAPABILITY = null;

	public static void register() {
		CapabilityManager.INSTANCE.register(IESPEHandler.class, new DefaultESPEHandlerStorage<>(), ESPEStorage::new);
		CapabilityManager.INSTANCE.register(IESPEHandlerItem.class, new DefaultESPEHandlerStorage<>(), ESPEItemStorage::new);
	}

	private static class DefaultESPEHandlerStorage<T extends IESPEHandler> implements Capability.IStorage<T> {

		@Override
		public NBTBase writeNBT(Capability<T> capability, T instance, EnumFacing side) {
			NBTTagCompound nbt = new NBTTagCompound();
			return instance.writeToNBT(nbt);
		}

		@Override
		public void readNBT(Capability<T> capability, T instance, EnumFacing side, NBTBase base) {
			instance.readFromNBT((NBTTagCompound)base);
		}
	}
}
