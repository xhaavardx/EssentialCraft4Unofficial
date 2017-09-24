package essentialcraft.client.render.tile;

import DummyCore.Utils.MathUtils;
import essentialcraft.client.model.ModelElementalCrystal;
import essentialcraft.common.tile.TileElementalCrystal;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderElementalCrystal extends TileEntitySpecialRenderer<TileElementalCrystal>
{
	public static final ResourceLocation textures = new ResourceLocation("essentialcraft:textures/models/mcrystaltex.png");
	public static final ResourceLocation neutral = new ResourceLocation("essentialcraft:textures/models/mcrystaltex.png");
	public static final ResourceLocation fire = new ResourceLocation("essentialcraft:textures/models/fcrystaltex.png");
	public static final ResourceLocation water = new ResourceLocation("essentialcraft:textures/models/wcrystaltex.png");
	public static final ResourceLocation earth = new ResourceLocation("essentialcraft:textures/models/ecrystaltex.png");
	public static final ResourceLocation air = new ResourceLocation("essentialcraft:textures/models/acrystaltex.png");
	public static final ModelElementalCrystal crystal = new ModelElementalCrystal();

	public void doRender(TileElementalCrystal crystal_tile, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_)
	{
		int metadata = crystal_tile.getBlockMetadata();

		GlStateManager.pushMatrix();
		float scale = MathUtils.getPercentage((int)crystal_tile.size, 100)/100F;

		if(metadata == 1)
		{
			GlStateManager.translate((float)p_76986_2_+0.5F, (float)p_76986_4_+1.4F-(1.0F-scale)*1.4F, (float)p_76986_6_+0.5F);
			GlStateManager.scale(scale, scale, scale);
			GlStateManager.rotate(180, 1, 0, 0);
		}

		if(metadata == 0)
		{
			GlStateManager.translate((float)p_76986_2_+0.5F, (float)p_76986_4_-0.4F+(1.0F-scale)*1.4F, (float)p_76986_6_+0.5F);
			GlStateManager.scale(scale, scale, scale);
			GlStateManager.rotate(0, 1, 0, 0);
		}

		if(metadata == 2)
		{
			GlStateManager.translate((float)p_76986_2_+0.5F, (float)p_76986_4_+0.5F, (float)p_76986_6_-0.5F+(1.0F-scale)*1.5F);
			GlStateManager.scale(scale, scale, scale);
			GlStateManager.rotate(90, 1, 0, 0);
		}

		if(metadata == 4)
		{
			GlStateManager.translate((float)p_76986_2_-0.5F+(1.0F-scale)*1.5F, (float)p_76986_4_+0.5F, (float)p_76986_6_+0.5F);
			GlStateManager.scale(scale, scale, scale);
			GlStateManager.rotate(90, 1, 0, 0);
			GlStateManager.rotate(270, 0, 0, 1);
		}

		if(metadata == 3)
		{
			GlStateManager.translate((float)p_76986_2_+0.5F, (float)p_76986_4_+0.5F, (float)p_76986_6_+1.5F-(1.0F-scale)*1.5F);
			GlStateManager.scale(scale, scale, scale);
			GlStateManager.rotate(-90, 1, 0, 0);

		}

		if(metadata == 5)
		{
			GlStateManager.translate((float)p_76986_2_+1.5F-(1.0F-scale)*1.5F, (float)p_76986_4_+0.5F, (float)p_76986_6_+0.5F);
			GlStateManager.scale(scale, scale, scale);
			GlStateManager.rotate(90, 1, 0, 0);
			GlStateManager.rotate(90, 0, 0, 1);
		}

		this.bindTexture(textures);
		RenderHelper.disableStandardItemLighting();
		GlStateManager.disableLighting();
		GlStateManager.disableCull();
		GlStateManager.disableBlend();
		GlStateManager.color(1, 1, 1, 0.5F);
		GlStateManager.enableBlend();
		OpenGlHelper.glBlendFunc(770, 771, 1, 0);
		crystal.renderModel(0.0625F);

		this.bindTexture(fire);
		GlStateManager.color(1, 1, 1, crystal_tile.fire/100F);
		crystal.renderModel(0.0625F);

		this.bindTexture(water);
		GlStateManager.color(1, 1, 1, crystal_tile.water/100F);
		crystal.renderModel(0.0625F);

		this.bindTexture(earth);
		GlStateManager.color(1, 1, 1, crystal_tile.earth/100F);
		crystal.renderModel(0.0625F);

		this.bindTexture(air);
		GlStateManager.color(1, 1, 1, crystal_tile.air/100F);
		crystal.renderModel(0.0625F);

		GlStateManager.enableLighting();
		GlStateManager.popMatrix();
		RenderHelper.enableStandardItemLighting();
	}

	@Override
	public void render(TileElementalCrystal p_147500_1_, double p_147500_2_, double p_147500_4_, double p_147500_6_, float p_147500_8_, int destroyStage, float alpha) {
		this.doRender(p_147500_1_, p_147500_2_, p_147500_4_, p_147500_6_, p_147500_8_, 0);
	}
}