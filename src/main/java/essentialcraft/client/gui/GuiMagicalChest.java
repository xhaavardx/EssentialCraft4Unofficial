package essentialcraft.client.gui;

import DummyCore.Utils.DrawUtils;
import essentialcraft.common.inventory.ContainerMagicalChest;
import essentialcraft.common.tile.TileMagicalChest;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;

public class GuiMagicalChest extends GuiContainer{

	TileMagicalChest tile;

	public GuiMagicalChest(InventoryPlayer inventoryPlayer, TileMagicalChest chest)
	{
		super(new ContainerMagicalChest(inventoryPlayer, chest));
		tile = chest;

		if (this.tile.getBlockMetadata() == 0)
		{
			xSize = 176;
			ySize = 222;
		}
		else if (this.tile.getBlockMetadata() == 1)
		{
			xSize = 256;
			ySize = 256;
		}
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		this.drawDefaultBackground();
		super.drawScreen(mouseX, mouseY, partialTicks);
		this.renderHoveredToolTip(mouseX, mouseY);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks,int mX, int mY) {
		int k = (this.width - this.xSize)/2;
		int l = (this.height - this.ySize)/2;
		if(this.tile.getBlockMetadata() == 0)
			DrawUtils.bindTexture("essentialcraft", "textures/gui/magical_chest.png");
		else
			DrawUtils.bindTexture("essentialcraft", "textures/gui/void_chest.png");

		this.drawTexturedModalRect(k, l, 0, 0, xSize, ySize);
	}
}
