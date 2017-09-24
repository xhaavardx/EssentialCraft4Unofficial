package essentialcraft.client.gui;

import DummyCore.Client.GuiCommon;
import essentialcraft.api.IMRUHandler;
import essentialcraft.client.gui.element.GuiBoundGemState;
import essentialcraft.client.gui.element.GuiMRUState;
import essentialcraft.client.gui.element.GuiMRUStorage;
import net.minecraft.inventory.Container;
import net.minecraft.tileentity.TileEntity;

public class GuiCrystalExtractor extends GuiCommon{

	public GuiCrystalExtractor(Container c, TileEntity tile) {
		super(c,tile);
		this.elementList.add(new GuiMRUStorage(7, 4, (IMRUHandler) tile));
		this.elementList.add(new GuiBoundGemState(25, 4, tile, 0));
		this.elementList.add(new GuiMRUState(25, 58, (IMRUHandler) tile, 0));
	}



}
