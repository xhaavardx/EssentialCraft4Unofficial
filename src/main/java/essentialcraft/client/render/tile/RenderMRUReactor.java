package essentialcraft.client.render.tile;

import DummyCore.Client.AdvancedModelLoader;
import DummyCore.Client.IModelCustom;
import DummyCore.Utils.Lightning;
import essentialcraft.common.tile.TileMRUReactor;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderMRUReactor extends TileEntitySpecialRenderer<TileMRUReactor>
{

	public static final ResourceLocation textures = new ResourceLocation("essentialcraft:textures/models/flowerburner.png");
	public static final IModelCustom model = AdvancedModelLoader.loadModel(new ResourceLocation("essentialcraft:models/block/mrureactor_btm.obj"));

	public static final ResourceLocation stextures = new ResourceLocation("essentialcraft:textures/models/sphere.png");
	public static final IModelCustom smodel = AdvancedModelLoader.loadModel(new ResourceLocation("essentialcraft:models/block/sphere.obj"));

	public void doRender(TileMRUReactor p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_)
	{
		RenderHelper.disableStandardItemLighting();

		GlStateManager.pushMatrix();
		GlStateManager.translate((float)p_76986_2_+0.5F, (float)p_76986_4_, (float)p_76986_6_+0.5F);
		this.bindTexture(stextures);
		if(!p_76986_1_.isStructureCorrect)
		{
			GlStateManager.color(0.4F, 0.4F, 0.4F);
			GlStateManager.scale(0.55F, 0.55F, 0.55F);
			smodel.renderAll();
		}else
		{
			float wTime = 0F;
			GlStateManager.translate(0, 0.5F+wTime, 0);
			GlStateManager.scale(0.55F, 0.55F, 0.55F);
			smodel.renderAll();
		}

		GlStateManager.popMatrix();

		GlStateManager.pushMatrix();
		if(p_76986_1_.isStructureCorrect())
			for(int i = 0; i < p_76986_1_.lightnings.size(); ++i)
			{
				Lightning l = p_76986_1_.lightnings.get(i);
				l.render(p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_);
			}
		GlStateManager.popMatrix();

		RenderHelper.enableStandardItemLighting();
	}

	@Override
	public void render(TileMRUReactor p_147500_1_, double p_147500_2_, double p_147500_4_, double p_147500_6_, float p_147500_8_, int destroyStage, float alpha) {
		this.doRender(p_147500_1_, p_147500_2_, p_147500_4_, p_147500_6_, p_147500_8_, 0);
	}
}