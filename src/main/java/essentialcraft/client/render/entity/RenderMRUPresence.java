package essentialcraft.client.render.entity;

import java.util.Random;

import org.lwjgl.opengl.GL11;

import essentialcraft.client.render.RenderHandlerEC;
import essentialcraft.common.entity.EntityMRUPresence;
import essentialcraft.utils.common.ECUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class RenderMRUPresence extends Render<EntityMRUPresence> {

	static final float HALF_SQRT_3 = 0.8660254F;

	public RenderMRUPresence(RenderManager renderManager) {
		super(renderManager);
	}

	public void doActualRender(EntityMRUPresence par1Entity, double par2, double par4, double par6, float par8, float par9) {
		float var4 = par1Entity.renderIndex;
		float stability = par1Entity.mruStorage.getBalance();
		float colorRRender = 0.0F;
		float colorGRender = 1.0F;
		float colorBRender = 1.0F;

		float colorRNormal = 0.0F;
		float colorGNormal = 1.0F;
		float colorBNormal = 1.0F;

		float colorRChaos = 1.0F;
		float colorGChaos = 0.0F;
		float colorBChaos = 0.0F;

		float colorRFrozen = 0.0F;
		float colorGFrozen = 0.0F;
		float colorBFrozen = 1.0F;

		int mru = par1Entity.mruStorage.getMRU();
		if(stability!=1.0F)
		{
			if(stability<1.0F)
			{
				float diff = stability;
				if(diff < 0.01F)
					diff = 0.0F;
				colorRRender = colorRNormal*diff + colorRFrozen*(1.0F-diff);
				colorGRender = colorGNormal*diff + colorGFrozen*(1.0F-diff);
				colorBRender = colorBNormal*diff + colorBFrozen*(1.0F-diff);
			}
			if(stability>1.0F)
			{
				float diff = 2.0F-stability;
				if(diff < 0.01F)
					diff = 0.0F;
				colorRRender = colorRNormal*diff + colorRChaos*(1.0F-diff);
				colorGRender = colorGNormal*diff + colorGChaos*(1.0F-diff);
				colorBRender = colorBNormal*diff + colorBChaos*(1.0F-diff);
			}
		}
		Random var6 = new Random(432L);


		GlStateManager.pushMatrix();

		GlStateManager.depthFunc(GL11.GL_ALWAYS);
		GlStateManager.enableBlend();
		GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
		GlStateManager.disableAlpha();

		GlStateManager.translate(par2, par4, par6);

		Minecraft.getMinecraft().renderEngine.bindTexture(RenderHandlerEC.whitebox);
		GlStateManager.scale(0.0000075F*mru, 0.0000075F*mru, 0.0000075F*mru);
		for(int var7 = 0; (float)var7 < par1Entity.mruStorage.getMRU()/50; ++var7) {
			//GlStateManager.rotate(var6.nextFloat() * 360.0F, 1.0F, 0.0F, 0.0F);
			//GlStateManager.rotate(var6.nextFloat() * 360.0F, 0.0F, 1.0F, 0.0F);
			//GlStateManager.rotate(var6.nextFloat() * 360.0F, 0.0F, 0.0F, 1.0F);
			GlStateManager.rotate(var6.nextFloat() * 360.0F, 1.0F, 0.0F, 0.0F);
			GlStateManager.rotate(var6.nextFloat() * 360.0F, 0.0F, 1.0F, 0.0F);
			GlStateManager.rotate(var6.nextFloat() * 360.0F + var4 * 90.0F, 0.0F, 0.0F, 1.0F);
			GlStateManager.color(colorRRender, colorGRender, colorBRender, 1F);
			float var8 = var6.nextFloat() * 20F + 5F;
			float var9 = var6.nextFloat() * 2F + 1F;
			GlStateManager.glBegin(GL11.GL_TRIANGLE_FAN);
			GlStateManager.glVertex3f(0, 0, 0);
			GlStateManager.glVertex3f(-HALF_SQRT_3*var9, var8, -var9/2F);
			GlStateManager.glVertex3f(HALF_SQRT_3*var9, var8, -var9/2F);
			GlStateManager.glVertex3f(0, var8, var9);
			GlStateManager.glVertex3f(-HALF_SQRT_3*var9, var8, -var9/2F);
			GlStateManager.glEnd();
		}

		GlStateManager.disableBlend();
		GlStateManager.enableAlpha();
		GlStateManager.depthFunc(GL11.GL_LEQUAL);
		GlStateManager.color(1, 1, 1, 1);
		GlStateManager.popMatrix();
	}

	@Override
	public void doRender(EntityMRUPresence par1Entity, double par2, double par4, double par6, float par8, float par9) {
		if(ECUtils.canPlayerSeeMRU(Minecraft.getMinecraft().player)) {
			doActualRender(par1Entity, par2, par4, par6, par8, par9);
		}
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityMRUPresence entity) {
		return RenderHandlerEC.whitebox;
	}

	public static class Factory implements IRenderFactory<EntityMRUPresence> {
		@Override
		public Render<? super EntityMRUPresence> createRenderFor(RenderManager manager) {
			return new RenderMRUPresence(manager);
		}
	}
}
