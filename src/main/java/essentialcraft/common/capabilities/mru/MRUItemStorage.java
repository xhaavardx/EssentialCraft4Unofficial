package essentialcraft.common.capabilities.mru;

import DummyCore.Utils.MiscUtils;
import essentialcraft.api.IMRUHandlerItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

public class MRUItemStorage extends MRUStorage implements IMRUHandlerItem, ICapabilityProvider {

	protected boolean storage = false;
	protected ItemStack storageStack;
	protected final boolean storageSettable;

	protected MRUItemStorage() {
		super();
		this.storageSettable = true;
	}

	public MRUItemStorage(ItemStack storageStack) {
		super();
		this.storageStack = storageStack;
		this.storageSettable = true;
		MiscUtils.createNBTTag(this.storageStack);
	}

	public MRUItemStorage(ItemStack storageStack, int maxMRU) {
		super(maxMRU);
		this.storageStack = storageStack;
		this.storageSettable = true;
		MiscUtils.createNBTTag(this.storageStack);
	}

	public MRUItemStorage(ItemStack storageStack, boolean storage) {
		super();
		this.storageStack = storageStack;
		this.storage = storage;
		this.storageSettable = false;
		MiscUtils.createNBTTag(this.storageStack);
	}

	public MRUItemStorage(ItemStack storageStack, int maxMRU, boolean storage) {
		super(maxMRU);
		this.storageStack = storageStack;
		this.storage = storage;
		this.storageSettable = false;
		MiscUtils.createNBTTag(this.storageStack);
	}

	@Override
	public int getMaxMRU() {
		this.readIfChanged();
		return super.getMaxMRU();
	}

	@Override
	public void setMaxMRU(int amount) {
		this.readIfChanged();
		super.setMaxMRU(amount);
		this.writeIfChanged();
	}

	@Override
	public int getMRU() {
		this.readIfChanged();
		return super.getMRU();
	}

	@Override
	public void setMRU(int amount) {
		this.readIfChanged();
		super.setMRU(amount);
		this.writeIfChanged();
	}

	@Override
	public int addMRU(int amount, boolean doAdd) {
		this.readIfChanged();
		int ret = super.addMRU(amount, doAdd);
		this.writeIfChanged();
		return ret;
	}

	@Override
	public int extractMRU(int amount, boolean doExtract) {
		this.readIfChanged();
		int ret = super.extractMRU(amount, doExtract);
		this.writeIfChanged();
		return ret;
	}

	@Override
	public float getBalance() {
		this.readIfChanged();
		return super.getBalance();
	}

	@Override
	public void setBalance(float balance) {
		this.readIfChanged();
		super.setBalance(balance);
		this.writeIfChanged();
	}

	@Override
	public boolean getShade() {
		this.readIfChanged();
		return super.getShade();
	}

	@Override
	public void setShade(boolean shade) {
		this.readIfChanged();
		super.setShade(shade);
		this.writeIfChanged();
	}

	@Override
	public boolean getStorage() {
		this.readIfChanged();
		return storage;
	}

	@Override
	public void setStorage(boolean storage) {
		this.readIfChanged();
		if(storageSettable) {
			this.storage = storage;
		}
		this.writeIfChanged();
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		if(storageSettable) {
			nbt.setBoolean("storage", storage);
		}
		return nbt;
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
