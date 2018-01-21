package essentialcraft.api;

/**
 *
 * @author Modbder
 * @Description use this interface to create new items, which may require MRU to act with
 */
public interface IMRUHandlerItem extends IMRUHandler {

	public boolean getStorage();

	public void setStorage(boolean storage);
}
