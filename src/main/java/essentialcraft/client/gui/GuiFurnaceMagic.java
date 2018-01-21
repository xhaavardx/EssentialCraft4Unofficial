package essentialcraft.client.gui;

import DummyCore.Client.GuiCommon;
import essentialcraft.client.gui.element.GuiBalanceState;
import essentialcraft.client.gui.element.GuiBoundGemState;
import essentialcraft.client.gui.element.GuiMRUState;
import essentialcraft.client.gui.element.GuiMRUStorage;
import essentialcraft.client.gui.element.GuiProgressBar_FurnaceMagic;
import essentialcraft.common.tile.TileFurnaceMagic;
import net.minecraft.inventory.Container;

public class GuiFurnaceMagic extends GuiCommon{

	public GuiFurnaceMagic(Container c, TileFurnaceMagic tile) {
		super(c,tile);
		this.elementList.add(new GuiMRUStorage(7, 4, tile));
		this.elementList.add(new GuiMRUState(25, 58, tile, 0));
		this.elementList.add(new GuiBalanceState(25, 22, tile));
		this.elementList.add(new GuiBoundGemState(25, 40, tile, 0));
		this.elementList.add(new GuiProgressBar_FurnaceMagic(126, 4, tile));
	}
}
