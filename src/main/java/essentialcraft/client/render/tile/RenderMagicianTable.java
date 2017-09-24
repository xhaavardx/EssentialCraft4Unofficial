package essentialcraft.client.render.tile;

import DummyCore.Client.AdvancedModelLoader;
import DummyCore.Client.IModelCustom;
import essentialcraft.api.MagicianTableUpgrades;
import essentialcraft.common.tile.TileMagicianTable;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderMagicianTable extends TileEntitySpecialRenderer<TileMagicianTable>
{
	public static final IModelCustom cube = AdvancedModelLoader.loadModel(new ResourceLocation("essentialcraft:models/block/cube.obj"));

	public void doRender(TileMagicianTable table, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_)
	{
		RenderHelper.disableStandardItemLighting();
		GlStateManager.pushMatrix();
		GlStateManager.translate((float)p_76986_2_+0.5F, (float)p_76986_4_, (float)p_76986_6_+0.5F);
		if(table.upgrade != -1)
		{
			this.bindTexture(MagicianTableUpgrades.UPGRADE_TEXTURES.get(table.upgrade));
			float scale = 0.99F;
			GlStateManager.translate(0, 0.005F, 0);
			GlStateManager.scale(scale, scale, scale);
			cube.renderAll();
		}
		GlStateManager.popMatrix();
		RenderHelper.enableStandardItemLighting();
	}

	@Override
	public void render(TileMagicianTable p_147500_1_, double p_147500_2_, double p_147500_4_, double p_147500_6_, float p_147500_8_, int destroyStage, float alpha) {
		if(p_147500_1_.getBlockMetadata() == 0)
			this.doRender(p_147500_1_, p_147500_2_, p_147500_4_, p_147500_6_, p_147500_8_, 0);
	}
}