package essentialcraft.client.render.tile;

import essentialcraft.client.model.ModelFloatingCube;
import essentialcraft.common.tile.TileMRUCoil;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderMRUCoil extends TileEntitySpecialRenderer<TileMRUCoil>
{
	private static final ResourceLocation enderCrystalTextures = new ResourceLocation("essentialcraft:textures/entities/raycrystal.png");
	private ModelFloatingCube model;

	public RenderMRUCoil()
	{
		this.model = new ModelFloatingCube(0.0F, true);
	}

	public void doRender(TileMRUCoil p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_)
	{
		RenderHelper.disableStandardItemLighting();
		float f2 = p_76986_1_.innerRotation + p_76986_8_;
		GlStateManager.pushMatrix();
		GlStateManager.translate((float)p_76986_2_+0.5F, (float)p_76986_4_+0.6F, (float)p_76986_6_+0.5F);
		this.bindTexture(enderCrystalTextures);
		float f3 = MathHelper.sin(f2 * 0.2F) / 2.0F + 0.5F;
		f3 += f3 * f3;
		GlStateManager.scale(0.2F, 0.2F, 0.2F);
		this.model.render(p_76986_1_, 0.0F, f2 * 3.0F, 0.35F, 0.0F, 0.0F, 0.0625F);
		if(p_76986_1_.localLightning != null)
			p_76986_1_.localLightning.render(p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_);
		GlStateManager.popMatrix();
		GlStateManager.pushMatrix();
		if(p_76986_1_.monsterLightning != null)
			p_76986_1_.monsterLightning.render(p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_);
		GlStateManager.popMatrix();

		RenderHelper.enableStandardItemLighting();
	}

	@Override
	public void render(TileMRUCoil p_147500_1_, double p_147500_2_, double p_147500_4_, double p_147500_6_, float p_147500_8_, int destroyStage, float alpha) {
		this.doRender(p_147500_1_, p_147500_2_, p_147500_4_, p_147500_6_, p_147500_8_, 0);
	}

	@Override
	public boolean isGlobalRenderer(TileMRUCoil te) {
		return true;
	}
}