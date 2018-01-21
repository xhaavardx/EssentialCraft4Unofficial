package essentialcraft.common.capabilities.mru;

import essentialcraft.api.IMRUHandler;
import essentialcraft.api.IMRUHandlerEntity;
import essentialcraft.api.IMRUHandlerItem;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

public class CapabilityMRUHandler {

	@CapabilityInject(IMRUHandler.class)
	public static Capability<IMRUHandler> MRU_HANDLER_CAPABILITY = null;
	@CapabilityInject(IMRUHandlerItem.class)
	public static Capability<IMRUHandlerItem> MRU_HANDLER_ITEM_CAPABILITY = null;
	@CapabilityInject(IMRUHandlerEntity.class)
	public static Capability<IMRUHandlerEntity> MRU_HANDLER_ENTITY_CAPABILITY = null;

	public static void register() {
		CapabilityManager.INSTANCE.register(IMRUHandler.class, new DefaultMRUHandlerStorage<>(), MRUStorage::new);
		CapabilityManager.INSTANCE.register(IMRUHandlerItem.class, new DefaultMRUHandlerStorage<>(), MRUItemStorage::new);
		CapabilityManager.INSTANCE.register(IMRUHandlerEntity.class, new DefaultMRUHandlerStorage<>(), MRUEntityStorage::new);
	}

	private static class DefaultMRUHandlerStorage<T extends IMRUHandler> implements Capability.IStorage<T> {

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
