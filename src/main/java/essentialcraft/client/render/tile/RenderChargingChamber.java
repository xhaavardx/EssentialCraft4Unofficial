package essentialcraft.client.render.tile;

import DummyCore.Utils.DrawUtils;
import essentialcraft.common.tile.TileChargingChamber;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderChargingChamber extends TileEntitySpecialRenderer<TileChargingChamber> {

	public void doRender(TileChargingChamber tile, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_) {
		RenderHelper.disableStandardItemLighting();

		float rotation = (tile.getWorld().getWorldTime()+p_76986_8_) % 360;
		float upperIndex = (tile.getWorld().getWorldTime()+p_76986_8_) % 360;

		if(upperIndex < 180) {
			upperIndex = 180 - upperIndex;
		}
		else {
			upperIndex -= 180;
		}

		rotation = rotation + 360F/(tile.getWorld().getWorldTime()+p_76986_8_) % 360;

		GlStateManager.pushMatrix();
		DrawUtils.renderItemStack_Full(tile.getStackInSlot(1), p_76986_2_, p_76986_4_, p_76986_6_, rotation, 0F, 1, 1, 1, 0.5F, 0.65F+upperIndex/500F,0.5F);
		GlStateManager.popMatrix();
		RenderHelper.enableStandardItemLighting();
	}

	@Override
	public void render(TileChargingChamber p_147500_1_, double p_147500_2_, double p_147500_4_, double p_147500_6_, float p_147500_8_, int destroyStage, float alpha) {
		this.doRender(p_147500_1_, p_147500_2_, p_147500_4_, p_147500_6_, p_147500_8_, 0);
	}
}