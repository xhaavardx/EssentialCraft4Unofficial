package essentialcraft.api;

/**
 * @author Modbder
 * @Description Usage of this interface to create tile entities is not recommended, in case the 3 interfaces based on this one needs to be distinguished.
 * Not yet a capability.
 */
public interface IMRUHandler {

	/**
	 * this is used to get current MRU of your tile entities
	 * @return current amount of MRU
	 */
	public int getMRU();

	/**
	 * this is used to get max MRU of your tile entities
	 * @return max amount of MRU the device can store
	 */
	public int getMaxMRU();

	/**
	 * this is used to set current MRU of your tile entities
	 * @param i - the amount to set.
	 * @return true if was successful, false if not
	 */
	public boolean setMRU(int i);

	/**
	 * this is used to get the balance in the device.
	 * @return the amount of balance there is
	 */
	public float getBalance();

	/**
	 * this is used to set current balance of your tile entities
	 * @param f - the amount to set.
	 * @return true if was successful, false if not
	 */
	public boolean setBalance(float f);

	/**
	 * this is used to set maxMRU of your tile entities
	 * please don't use
	 * @param f - the amount to set.
	 * @return true if was successful, false if not
	 */
	@Deprecated
	public boolean setMaxMRU(float f);
}
