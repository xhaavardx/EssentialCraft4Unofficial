package essentialcraft.client.render.tile;

import DummyCore.Utils.DrawUtils;
import essentialcraft.common.tile.TileRadiatingChamber;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderRadiatingChamber extends TileEntitySpecialRenderer<TileRadiatingChamber> {

	public void doRender(TileRadiatingChamber tile, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_)
	{
		RenderHelper.disableStandardItemLighting();

		GlStateManager.pushMatrix();

		DrawUtils.renderItemStack_Full(tile.getStackInSlot(1), p_76986_2_, p_76986_4_, p_76986_6_, 0F, 90F, 1, 1, 1, 0.5F, 0.875F, 0.5F);
		GlStateManager.popMatrix();
		RenderHelper.enableStandardItemLighting();
	}

	@Override
	public void render(TileRadiatingChamber p_147500_1_, double p_147500_2_, double p_147500_4_, double p_147500_6_, float p_147500_8_, int destroyStage, float alpha) {
		this.doRender(p_147500_1_, p_147500_2_, p_147500_4_, p_147500_6_, p_147500_8_, 0);
	}
}