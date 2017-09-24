package essentialcraft.client.render.tile;

import DummyCore.Client.AdvancedModelLoader;
import DummyCore.Client.IModelCustom;
import DummyCore.Utils.DrawUtils;
import essentialcraft.common.tile.TileMagicalRepairer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderMagicalRepairer extends TileEntitySpecialRenderer<TileMagicalRepairer> {

	public static final IModelCustom model = AdvancedModelLoader.loadModel(new ResourceLocation("essentialcraft:models/block/magicalrepairer.obj"));
	public static final ResourceLocation textures = new ResourceLocation("essentialcraft:textures/models/magicalrepairer.png");

	public void doRender(TileMagicalRepairer tile, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_) {
		RenderHelper.disableStandardItemLighting();
		{
			GlStateManager.pushMatrix();
			GlStateManager.translate(p_76986_2_,  p_76986_4_,  p_76986_6_);
			Minecraft.getMinecraft().renderEngine.bindTexture(textures);
			model.renderAll();
			GlStateManager.popMatrix();
		}

		float rotation = (tile.getWorld().getWorldTime()+p_76986_8_) % 360;

		GlStateManager.pushMatrix();
		DrawUtils.renderItemStack_Full(tile.getStackInSlot(1), p_76986_2_, p_76986_4_, p_76986_6_, rotation,0F, 1, 1, 1, 0.5F, 0.65F, 0.5F);
		GlStateManager.popMatrix();
		RenderHelper.enableStandardItemLighting();
	}

	@Override
	public void render(TileMagicalRepairer p_147500_1_, double p_147500_2_, double p_147500_4_, double p_147500_6_, float p_147500_8_, int destroyStage, float alpha) {
		this.doRender(p_147500_1_, p_147500_2_, p_147500_4_, p_147500_6_, p_147500_8_, 0);
	}
}