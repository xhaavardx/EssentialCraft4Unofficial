package essentialcraft.client.gui;

import DummyCore.Client.GuiCommon;
import DummyCore.Client.GuiElement;
import essentialcraft.client.gui.element.GuiMRUStorage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

public class GuiMIM extends GuiCommon{

	public GuiMIM(Container c, TileEntity tile) {
		super(c,tile);
		guiGenLocation = new ResourceLocation("essentialcraft","textures/gui/mim.png");
		this.elementList.add(new GuiMRUStorage(4, 72, tile));
		this.xSize = 196;
		this.ySize = 256;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f1,int i1, int i2) {
		GlStateManager.color(1, 1, 1);
		int k = (this.width - this.xSize) / 2;
		int l = (this.height - this.ySize) / 2;
		this.mc.renderEngine.bindTexture(guiGenLocation);
		this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);
		for(int i = 0; i < this.inventorySlots.inventorySlots.size(); ++i)
		{
			Slot slt = this.inventorySlots.inventorySlots.get(i);
			renderSlot(slt);
			GlStateManager.color(1, 1, 1);
		}
		for(int i = 0; i < this.elementList.size(); ++i)
		{
			GuiElement element = elementList.get(i);
			Minecraft.getMinecraft().renderEngine.bindTexture(element.getElementTexture());
			element.draw(k+element.getX(),l+element.getY(),i1,i2);
			GlStateManager.color(1, 1, 1);
		}
	}

}
