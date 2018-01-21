package essentialcraft.api;

/**
 *
 * @author Modbder
 * @Description use this interface to create new items, which may require ESPE to act with
 */
public interface IESPEHandlerItem extends IESPEHandler {

	public boolean getStorage();

	public void setStorage(boolean storage);
}
