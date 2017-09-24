package essentialcraft.client.render.tile;

import essentialcraft.common.tile.TileCrystalFormer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderCrystalFormer extends TileEntitySpecialRenderer<TileCrystalFormer>
{
	public void doRender(TileCrystalFormer p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_)
	{
		RenderHelper.disableStandardItemLighting();

		float scale = 0.5F;

		GlStateManager.pushMatrix();
		GlStateManager.translate((float)p_76986_2_+0.8F, (float)p_76986_4_+1.1F, (float)p_76986_6_+0.5F);
		GlStateManager.scale(scale, scale, scale);
		GlStateManager.rotate(180, 1, 0, 0);
		this.bindTexture(RenderElementalCrystal.neutral);
		RenderElementalCrystal.crystal.renderModel(0.0625F);
		GlStateManager.popMatrix();

		GlStateManager.pushMatrix();
		GlStateManager.translate((float)p_76986_2_+0.2F, (float)p_76986_4_+1.1F, (float)p_76986_6_+0.5F);
		GlStateManager.scale(scale, scale, scale);
		GlStateManager.rotate(180, 1, 0, 0);
		this.bindTexture(RenderElementalCrystal.neutral);
		RenderElementalCrystal.crystal.renderModel(0.0625F);
		GlStateManager.popMatrix();

		GlStateManager.pushMatrix();
		GlStateManager.translate((float)p_76986_2_+0.5F, (float)p_76986_4_+1.1F, (float)p_76986_6_+0.8F);
		GlStateManager.scale(scale, scale, scale);
		GlStateManager.rotate(180, 1, 0, 0);
		this.bindTexture(RenderElementalCrystal.neutral);
		RenderElementalCrystal.crystal.renderModel(0.0625F);
		GlStateManager.popMatrix();

		GlStateManager.pushMatrix();
		GlStateManager.translate((float)p_76986_2_+0.5F, (float)p_76986_4_+1.1F, (float)p_76986_6_+0.2F);
		GlStateManager.scale(scale, scale, scale);
		GlStateManager.rotate(180, 1, 0, 0);
		this.bindTexture(RenderElementalCrystal.neutral);
		RenderElementalCrystal.crystal.renderModel(0.0625F);
		GlStateManager.popMatrix();

		RenderHelper.enableStandardItemLighting();
	}

	@Override
	public void render(TileCrystalFormer p_147500_1_, double p_147500_2_, double p_147500_4_, double p_147500_6_, float p_147500_8_, int destroyStage, float alpha) {
		if(p_147500_1_.getBlockMetadata() == 0)
			this.doRender(p_147500_1_, p_147500_2_, p_147500_4_, p_147500_6_, p_147500_8_, 0);
	}
}