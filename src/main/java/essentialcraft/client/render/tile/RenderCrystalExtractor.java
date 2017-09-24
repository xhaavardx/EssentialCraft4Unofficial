package essentialcraft.client.render.tile;

import DummyCore.Client.AdvancedModelLoader;
import DummyCore.Client.IModelCustom;
import essentialcraft.common.tile.TileCrystalExtractor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.ResourceLocation;

public class RenderCrystalExtractor extends TileEntitySpecialRenderer<TileCrystalExtractor> {
	public static final IModelCustom model = AdvancedModelLoader.loadModel(new ResourceLocation("essentialcraft:models/block/crystalextractor.obj"));
	public static final ResourceLocation textures = new ResourceLocation("essentialcraft:textures/models/crystalextractor.png");

	@Override
	public void render(TileCrystalExtractor te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
		RenderHelper.disableStandardItemLighting();
		GlStateManager.pushMatrix();
		GlStateManager.translate(x, y, z);
		Minecraft.getMinecraft().renderEngine.bindTexture(textures);
		model.renderAll();
		GlStateManager.popMatrix();
		RenderHelper.enableStandardItemLighting();
	}
}
