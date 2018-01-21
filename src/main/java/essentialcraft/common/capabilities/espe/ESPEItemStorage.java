package essentialcraft.common.capabilities.espe;

import DummyCore.Utils.MiscUtils;
import essentialcraft.api.IESPEHandlerItem;
import essentialcraft.common.capabilities.mru.CapabilityMRUHandler;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

public class ESPEItemStorage extends ESPEStorage implements IESPEHandlerItem, ICapabilityProvider {

	protected boolean storage = false;
	protected ItemStack storageStack;
	protected final boolean storageSettable;

	protected ESPEItemStorage() {
		super();
		this.storageSettable = true;
	}

	public ESPEItemStorage(ItemStack storageStack) {
		super();
		this.storageStack = storageStack;
		this.storageSettable = true;
		MiscUtils.createNBTTag(this.storageStack);
	}

	public ESPEItemStorage(ItemStack storageStack, double maxESPE) {
		super(maxESPE);
		this.storageStack = storageStack;
		this.storageSettable = true;
		MiscUtils.createNBTTag(this.storageStack);
	}

	public ESPEItemStorage(ItemStack storageStack, int tier) {
		super(tier);
		this.storageStack = storageStack;
		this.storageSettable = true;
		MiscUtils.createNBTTag(this.storageStack);
	}

	public ESPEItemStorage(ItemStack storageStack, double maxESPE, int tier) {
		super(maxESPE, tier);
		this.storageStack = storageStack;
		this.storageSettable = true;
		MiscUtils.createNBTTag(this.storageStack);
	}

	public ESPEItemStorage(ItemStack storageStack, boolean storage) {
		super();
		this.storageStack = storageStack;
		this.storage = storage;
		this.storageSettable = false;
		MiscUtils.createNBTTag(this.storageStack);
	}

	public ESPEItemStorage(ItemStack storageStack, double maxESPE, boolean storage) {
		super(maxESPE);
		this.storageStack = storageStack;
		this.storage = storage;
		this.storageSettable = false;
		MiscUtils.createNBTTag(this.storageStack);
	}
	public ESPEItemStorage(ItemStack storageStack, int tier, boolean storage) {
		super(tier);
		this.storageStack = storageStack;
		this.storage = storage;
		this.storageSettable = false;
		MiscUtils.createNBTTag(this.storageStack);
	}

	public ESPEItemStorage(ItemStack storageStack, double maxESPE, int tier, boolean storage) {
		super(maxESPE, tier);
		this.storageStack = storageStack;
		this.storage = storage;
		this.storageSettable = false;
		MiscUtils.createNBTTag(this.storageStack);
	}

	@Override
	public double getMaxESPE() {
		this.readIfChanged();
		return super.getMaxESPE();
	}

	@Override
	public void setMaxESPE(double amount) {
		this.readIfChanged();
		super.setMaxESPE(amount);
		this.writeIfChanged();
	}

	@Override
	public double getESPE() {
		this.readIfChanged();
		return super.getESPE();
	}

	@Override
	public void setESPE(double amount) {
		this.readIfChanged();
		super.setESPE(amount);
		this.writeIfChanged();
	}

	@Override
	public double addESPE(double amount, boolean doAdd) {
		this.readIfChanged();
		double ret = super.addESPE(amount, doAdd);
		this.writeIfChanged();
		return ret;
	}

	@Override
	public double extractESPE(double amount, boolean doExtract) {
		this.readIfChanged();
		double ret = super.extractESPE(amount, doExtract);
		this.writeIfChanged();
		return ret;
	}

	@Override
	public int getTier() {
		this.readIfChanged();
		return super.getTier();
	}

	@Override
	public void setTier(int tier) {
		this.readIfChanged();
		super.setTier(tier);
		this.writeIfChanged();
	}

	@Override
	public boolean getStorage() {
		this.readIfChanged();
		return this.storage;
	}

	@Override
	public void setStorage(boolean storage) {
		this.readIfChanged();
		if(this.storageSettable) {
			this.storage = storage;
		}
		this.writeIfChanged();
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		if(storageSettable) {
			this.storage = nbt.getBoolean("storage");
		}
	}

	protected NBTTagCompound prevNBT = null;

	protected void writeIfChanged() {
		NBTTagCompound nbt = this.writeToNBT(this.storageStack.getTagCompound().copy());
		if(!nbt.equals(prevNBT)) {
			this.storageStack.setTagCompound(nbt);
			this.prevNBT = nbt.copy();
		}
	}

	protected void readIfChanged() {
		NBTTagCompound nbt = this.storageStack.getTagCompound();
		if(!nbt.equals(prevNBT)) {
			this.readFromNBT(nbt);
			this.prevNBT = nbt.copy();
		}
	}

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		return capability == CapabilityMRUHandler.MRU_HANDLER_ITEM_CAPABILITY;
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		return capability == CapabilityMRUHandler.MRU_HANDLER_ITEM_CAPABILITY ? (T)this : null;
	}
}
