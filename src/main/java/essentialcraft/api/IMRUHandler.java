package essentialcraft.api;

import net.minecraft.nbt.NBTTagCompound;

/**
 * @author Modbder
 * @Description Usage of this interface to create tile entities is not recommended, in case the 3 interfaces based on this one needs to be distinguished.
 * Not yet a capability.
 */
public interface IMRUHandler {

	/**
	 * @return max amount of MRU the device can store
	 */
	public int getMaxMRU();

	public void setMaxMRU(int amount);

	/**
	 * @return current amount of MRU
	 */
	public int getMRU();

	/**
	 * Use only for internal manipulation.
	 */
	public void setMRU(int amount);

	/**
	 * @return the amount that was left over
	 */
	public int addMRU(int amount, boolean doAdd);

	/**
	 * @return the amount that was extracted
	 */
	public int extractMRU(int amount, boolean doExtract);

	/**
	 * @return the amount of balance there is
	 */
	public float getBalance();

	/**
	 * @param balance - the amount to set.
	 */
	public void setBalance(float balance);

	public boolean getShade();

	public void setShade(boolean shade);

	public NBTTagCompound writeToNBT(NBTTagCompound nbt);

	public void readFromNBT(NBTTagCompound nbt);
}
