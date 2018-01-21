package essentialcraft.common.capabilities.espe;

import essentialcraft.api.IESPEHandler;
import net.minecraft.nbt.NBTTagCompound;

public class ESPEStorage implements IESPEHandler {

	protected double maxESPE = 10000D;
	protected double espe = 0D;
	protected int tier = 0;

	protected final boolean maxESPESettable;
	protected final boolean tierSettable;

	public ESPEStorage() {
		this.maxESPESettable = true;
		this.tierSettable = true;
	}

	public ESPEStorage(double maxESPE) {
		this.maxESPE = maxESPE;
		this.maxESPESettable = false;
		this.tierSettable = true;
	}
	public ESPEStorage(int tier) {
		this.tier = tier;
		this.maxESPESettable = true;
		this.tierSettable = false;
	}

	public ESPEStorage(double maxESPE, int tier) {
		this.maxESPE = maxESPE;
		this.tier = tier;
		this.maxESPESettable = false;
		this.tierSettable = false;
	}

	@Override
	public double getMaxESPE() {
		return maxESPE;
	}

	@Override
	public void setMaxESPE(double amount) {
		if(this.maxESPESettable) {
			this.maxESPE = amount;
		}
	}

	@Override
	public double getESPE() {
		return this.espe;
	}

	@Override
	public void setESPE(double amount) {
		this.espe = amount;
	}

	@Override
	public double addESPE(double amount, boolean doAdd) {
		if(amount <= 0) {
			return amount;
		}
		if(espe + amount >= maxESPE) {
			double ret = espe + amount - maxESPE;
			if(doAdd) {
				espe = maxESPE;
			}
			return ret;
		}
		if(doAdd) {
			espe += amount;
		}
		return 0;
	}

	@Override
	public double extractESPE(double amount, boolean doExtract) {
		if(amount <= 0) {
			return 0;
		}
		if(espe - amount <= 0) {
			double ret = espe;
			if(doExtract) {
				espe = 0;
			}
			return ret;
		}
		if(doExtract) {
			espe -= amount;
		}
		return amount;
	}

	@Override
	public int getTier() {
		return this.tier;
	}

	@Override
	public void setTier(int tier) {
		if(this.tierSettable) {
			this.tier = tier;
		}
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		if(this.maxESPESettable) {
			nbt.setDouble("maxESPE", this.maxESPE);
		}
		nbt.setDouble("espe", this.espe);
		if(this.tierSettable) {
			nbt.setInteger("tier", this.tier);
		}
		return nbt;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		if(this.maxESPESettable) {
			this.maxESPE = nbt.getDouble("maxESPE");
		}
		this.espe = nbt.getDouble("espe");
		if(this.tierSettable) {
			this.tier = nbt.getInteger("tier");
		}

		//backwards compatibility
		if(nbt.hasKey("energy")) {
			this.espe = nbt.getFloat("energy");
		}
	}
}
