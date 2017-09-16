package ec3.client.render.entity;

import ec3.common.entity.EntityPoisonFume;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderPoisonFume extends RenderLiving<EntityPoisonFume> {
	private static final ResourceLocation villagerTextures = new ResourceLocation("essentialcraft","textures/entities/windMage_apprentice.png");

	public RenderPoisonFume() {
		super(Minecraft.getMinecraft().getRenderManager(), new ModelBiped(0.0F), 0.5F);
	}

	public RenderPoisonFume(RenderManager rm) {
		super(rm, new ModelBiped(0.0F), 0.5F);
	}

	@Override
	public void doRender(EntityPoisonFume p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_) {}

	@Override
	protected ResourceLocation getEntityTexture(EntityPoisonFume p_110775_1_) {
		return villagerTextures;
	}

	@Override
	protected void preRenderCallback(EntityPoisonFume p_77041_1_, float p_77041_2_) {
		float f1 = 0.9375F;

		GlStateManager.scale(f1, f1, f1);
	}

	public static class Factory implements IRenderFactory<EntityPoisonFume> {
		@Override
		public Render<? super EntityPoisonFume> createRenderFor(RenderManager manager) {
			return new RenderPoisonFume(manager);
		}
	}
}