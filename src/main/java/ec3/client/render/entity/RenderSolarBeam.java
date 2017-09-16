package ec3.client.render.entity;

import org.lwjgl.opengl.GL11;

import DummyCore.Utils.DrawUtils;
import ec3.common.entity.EntitySolarBeam;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntityBeaconRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class RenderSolarBeam extends Render<EntitySolarBeam> {

	public RenderSolarBeam(RenderManager renderManager) {
		super(renderManager);
	}

	private static final ResourceLocation field_147523_b = new ResourceLocation("textures/entity/beacon_beam.png");

	@Override
	public void doRender(EntitySolarBeam p_76986_1_, double screenX, double screenY, double screenZ, float rotationYaw, float rotationPitch) {
		GlStateManager.pushMatrix();

		//GL11.glScaled(eos.delay/3, 1, eos.delay/3);

		GlStateManager.alphaFunc(GL11.GL_GREATER, 0.1F);
		GlStateManager.disableFog();
		GlStateManager.disableLighting();
		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240F, 240F);
		DrawUtils.bindTexture("minecraft", "textures/entity/beacon_beam.png");
		GlStateManager.translate(-0.5F, 0, -0.5F);
		TileEntityBeaconRenderer.renderBeamSegment(screenX, screenY, screenZ, 0, 1, p_76986_1_.getEntityWorld().getTotalWorldTime(), 0, (int)(255D-p_76986_1_.posY), new float[] {1,1,0}, 0D, 0.5D);
		GlStateManager.enableLighting();
		GlStateManager.enableFog();
		GlStateManager.popMatrix();
	}

	@Override
	protected ResourceLocation getEntityTexture(EntitySolarBeam p_110775_1_) {
		return field_147523_b;
	}

	public static class Factory implements IRenderFactory<EntitySolarBeam> {
		@Override
		public Render<? super EntitySolarBeam> createRenderFor(RenderManager manager) {
			return new RenderSolarBeam(manager);
		}
	}
}
