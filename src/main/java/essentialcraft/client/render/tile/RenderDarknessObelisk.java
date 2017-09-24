package essentialcraft.client.render.tile;

import DummyCore.Client.AdvancedModelLoader;
import DummyCore.Client.IModelCustom;
import essentialcraft.common.tile.TileDarknessObelisk;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderDarknessObelisk extends TileEntitySpecialRenderer<TileDarknessObelisk> {
	public static final ResourceLocation rune = new ResourceLocation("essentialcraft:textures/models/darknessrune.png");
	public static final IModelCustom model = AdvancedModelLoader.loadModel(new ResourceLocation("essentialcraft:models/block/rune.obj"));

	public static final ResourceLocation obelisk = new ResourceLocation("essentialcraft:textures/models/darknessobelisk.png");
	public static final IModelCustom modelObelisk = AdvancedModelLoader.loadModel(new ResourceLocation("essentialcraft:models/block/darknessobelisk.obj"));

	public RenderDarknessObelisk() {}

	public void doRender(TileEntity tile, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_) {
		RenderHelper.disableStandardItemLighting();
		GlStateManager.pushMatrix();
		float lightLevel = tile.getWorld().getLightBrightness(tile.getPos());
		GlStateManager.enableBlend();
		GlStateManager.color(1, 1, 1, 1-lightLevel/15F);
		Minecraft.getMinecraft().renderEngine.bindTexture(rune);
		GlStateManager.translate(p_76986_2_+0.5F, p_76986_4_, p_76986_6_+0.5F);

		float upperRotationIndex = (tile.getWorld().getWorldTime()+p_76986_8_)%100;
		if(upperRotationIndex > 50)
			upperRotationIndex = 50-upperRotationIndex+50;

		GlStateManager.translate(0, upperRotationIndex/200-0.1F, 0);
		GlStateManager.rotate(System.currentTimeMillis()/50F%360, 0, 1, 0);
		model.renderPart("pPlane1");
		GlStateManager.disableBlend();
		GlStateManager.popMatrix();
		RenderHelper.enableStandardItemLighting();
	}

	@Override
	public void render(TileDarknessObelisk p_147500_1_, double p_147500_2_, double p_147500_4_, double p_147500_6_, float p_147500_8_, int destroyStage, float alpha) {
		this.doRender(p_147500_1_, p_147500_2_, p_147500_4_, p_147500_6_, p_147500_8_, 0);
	}
}