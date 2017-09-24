package essentialcraft.client.render.tile;

import org.lwjgl.opengl.GL11;

import DummyCore.Client.AdvancedModelLoader;
import DummyCore.Client.IModelCustom;
import essentialcraft.common.tile.TileMithrilineCrystal;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderMithrilineCrystal extends TileEntitySpecialRenderer<TileMithrilineCrystal>
{
	public static final ResourceLocation textures_mithriline = new ResourceLocation("essentialcraft:textures/models/mithrilinecrystal.png");
	public static final ResourceLocation textures_pale = new ResourceLocation("essentialcraft:textures/models/palecrystal.png");
	public static final ResourceLocation textures_void = new ResourceLocation("essentialcraft:textures/models/voidcrystal.png");
	public static final ResourceLocation textures_demonic = new ResourceLocation("essentialcraft:textures/models/demoniccrystal.png");
	public static final ResourceLocation textures_shade = new ResourceLocation("essentialcraft:textures/models/shadecrystal.png");
	public static final IModelCustom model = AdvancedModelLoader.loadModel(new ResourceLocation("essentialcraft:models/block/mithrilinecrystal.obj"));

	public void doRender(TileMithrilineCrystal p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_)
	{
		int meta = p_76986_1_.getBlockMetadata();
		RenderHelper.disableStandardItemLighting();
		GlStateManager.pushMatrix();
		GlStateManager.disableLighting();
		GlStateManager.disableAlpha();
		GlStateManager.enableBlend();
		GlStateManager.blendFunc(GL11.GL_ONE, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GlStateManager.color(1, 1, 1, 0.9F);
		float time = (p_76986_1_.getWorld().getWorldTime()+p_76986_8_)%45*8;
		float movement = (p_76986_1_.getWorld().getWorldTime()+p_76986_8_)%60;

		if(movement > 30)
			movement = 30 - movement+30F;

		GlStateManager.translate((float)p_76986_2_+0.5F, (float)p_76986_4_+movement/30, (float)p_76986_6_+0.5F);
		GlStateManager.rotate(time, 0, 1, 0);
		GlStateManager.scale(2, 2, 2);
		this.bindTexture(meta == 0 ? textures_mithriline : meta == 3 ? textures_pale : meta == 6 ? textures_void : meta == 9 ? textures_demonic : textures_shade);
		model.renderAll();
		GlStateManager.enableLighting();
		GlStateManager.enableAlpha();
		GlStateManager.popMatrix();

		RenderHelper.enableStandardItemLighting();
	}

	@Override
	public void render(TileMithrilineCrystal p_147500_1_, double p_147500_2_, double p_147500_4_, double p_147500_6_, float p_147500_8_, int destroyStage, float alpha) {
		if(p_147500_1_.getBlockMetadata()%3 == 0)
			this.doRender(p_147500_1_, p_147500_2_, p_147500_4_, p_147500_6_, p_147500_8_, 0);
	}
}