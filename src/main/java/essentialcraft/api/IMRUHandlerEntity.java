package essentialcraft.api;

/**
 *
 * @author Modbder
 * @Description use this interface to interact with MRUCUs.
 * Not yet a capability.
 */
public interface IMRUHandlerEntity extends IMRUHandler {

	/**
	 * A method to know can the MRUPressence increase it's MRU by it's own.
	 * @return true if can, false if can't
	 */
	public boolean getFlag();

	/**
	 * You can change will the MRUPressence create corruption
	 * @param b : boolean - true if you do not want, false if you do
	 */
	public void setFlag(boolean b);


	/**
	 * This is a chesk, if can the MRUPressence leave in the world, if it has 0 mru left.
	 * @return true if it stays, false if it will be removed from the world.
	 */
	public boolean canAlwaysStay();

	/**
	 * You can change will the MRUPressence stay in the world if it has 0 energy left.
	 * @param b : boolean - true if you want it to stay, false if you want it to dissapear
	 */
	public void setAlwaysStay(boolean b);
}
