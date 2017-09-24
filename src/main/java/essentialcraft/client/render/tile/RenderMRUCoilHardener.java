package essentialcraft.client.render.tile;

import DummyCore.Client.AdvancedModelLoader;
import DummyCore.Client.IModelCustom;
import essentialcraft.common.tile.TileMRUCoilHardener;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderMRUCoilHardener extends TileEntitySpecialRenderer<TileMRUCoilHardener>
{
	public static final ResourceLocation textures = new ResourceLocation("essentialcraft:textures/models/mrucoilhardener.png");
	public static final IModelCustom model = AdvancedModelLoader.loadModel(new ResourceLocation("essentialcraft:models/block/mrucoilhardener.obj"));

	public void doRender(TileMRUCoilHardener tile, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_)
	{
		RenderHelper.disableStandardItemLighting();
		GlStateManager.pushMatrix();
		if(tile.localLightning != null)
			tile.localLightning.render(p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_);
		GlStateManager.popMatrix();
		RenderHelper.enableStandardItemLighting();
	}

	/**
	 * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
	 */
	protected ResourceLocation getEntityTexture(TileEntity p_110775_1_)
	{
		return textures;
	}

	@Override
	public void render(TileMRUCoilHardener p_147500_1_, double p_147500_2_, double p_147500_4_, double p_147500_6_, float p_147500_8_, int destroyStage, float alpha) {
		if(p_147500_1_.getBlockMetadata() == 0)
			this.doRender(p_147500_1_, p_147500_2_, p_147500_4_, p_147500_6_, p_147500_8_, 0);
	}

	@Override
	public boolean isGlobalRenderer(TileMRUCoilHardener te) {
		return true;
	}
}