package essentialcraft.client.render.tile;

import DummyCore.Client.AdvancedModelLoader;
import DummyCore.Client.IModelCustom;
import essentialcraft.common.tile.TileMIM;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderMIM extends TileEntitySpecialRenderer<TileMIM>
{
	public static final ResourceLocation textures = new ResourceLocation("essentialcraft:textures/blocks/mimcube.png");
	public static final ResourceLocation vtextures = new ResourceLocation("essentialcraft:textures/blocks/voidstone.png");
	public static final IModelCustom model = AdvancedModelLoader.loadModel(new ResourceLocation("essentialcraft:models/block/mim.obj"));

	public void doRender(TileMIM t, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_)
	{
		RenderHelper.disableStandardItemLighting();
		GlStateManager.pushMatrix();
		GlStateManager.translate((float)p_76986_2_+0.5F, (float)p_76986_4_, (float)p_76986_6_+0.5F);
		GlStateManager.scale(0.5F, 0.5F, 0.5F);
		this.bindTexture(vtextures);
		model.renderPart("Cube.001_Cube.002");
		model.renderPart("Cube_Cube.001");

		GlStateManager.rotate(t.innerRotation+p_76986_8_, 0, 1, 0);

		this.bindTexture(textures);
		model.renderPart("Cube.002_Cube.003");

		GlStateManager.popMatrix();
		RenderHelper.enableStandardItemLighting();
	}

	@Override
	public void render(TileMIM p_147500_1_, double p_147500_2_, double p_147500_4_, double p_147500_6_, float p_147500_8_, int destroyStage, float alpha) {
		this.doRender(p_147500_1_, p_147500_2_, p_147500_4_, p_147500_6_, p_147500_8_, 0);
	}
}