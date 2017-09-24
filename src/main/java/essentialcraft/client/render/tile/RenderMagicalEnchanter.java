package essentialcraft.client.render.tile;

import essentialcraft.common.tile.TileMagicalEnchanter;
import net.minecraft.client.model.ModelBook;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderMagicalEnchanter extends TileEntitySpecialRenderer<TileMagicalEnchanter>
{
	public static final ResourceLocation bookTextures = new ResourceLocation("textures/entity/enchanting_table_book.png");
	public static final ModelBook book = new ModelBook();

	public void doRender(TileMagicalEnchanter p_147500_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_)
	{
		RenderHelper.disableStandardItemLighting();

		GlStateManager.pushMatrix();
		GlStateManager.translate((float)p_76986_2_ + 0.5F, (float)p_76986_4_ + 0.6F, (float)p_76986_6_ + 0.5F);
		float f1 = p_147500_1_.field_145926_a + p_76986_8_;
		GlStateManager.translate(0.0F, 0.1F + MathHelper.sin(f1 * 0.1F) * 0.01F, 0.0F);
		float f2;

		for (f2 = p_147500_1_.field_145928_o - p_147500_1_.field_145925_p; f2 >= (float)Math.PI; f2 -= (float)Math.PI * 2F)
		{

		}

		while (f2 < -(float)Math.PI)
		{
			f2 += (float)Math.PI * 2F;
		}

		float f3 = p_147500_1_.field_145925_p + f2 * p_76986_8_;
		GlStateManager.rotate(-f3 * 180.0F / (float)Math.PI, 0.0F, 1.0F, 0.0F);
		GlStateManager.rotate(80.0F, 0.0F, 0.0F, 1.0F);
		this.bindTexture(bookTextures);
		float f4 = p_147500_1_.field_145931_j + (p_147500_1_.field_145933_i - p_147500_1_.field_145931_j) * p_76986_8_ + 0.25F;
		float f5 = p_147500_1_.field_145931_j + (p_147500_1_.field_145933_i - p_147500_1_.field_145931_j) * p_76986_8_ + 0.75F;
		f4 = (f4 - MathHelper.fastFloor(f4)) * 1.6F - 0.3F;
		f5 = (f5 - MathHelper.fastFloor(f5)) * 1.6F - 0.3F;

		if (f4 < 0.0F)
		{
			f4 = 0.0F;
		}

		if (f5 < 0.0F)
		{
			f5 = 0.0F;
		}

		if (f4 > 1.0F)
		{
			f4 = 1.0F;
		}

		if (f5 > 1.0F)
		{
			f5 = 1.0F;
		}

		float f6 = p_147500_1_.field_145927_n + (p_147500_1_.field_145930_m - p_147500_1_.field_145927_n) * p_76986_8_;
		GlStateManager.enableCull();
		book.render((Entity)null, f1, f4, f5, f6, 0.0F, 0.0625F);
		GlStateManager.popMatrix();
		RenderHelper.enableStandardItemLighting();
	}

	@Override
	public void render(TileMagicalEnchanter p_147500_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, int destroyStage, float alpha) {
		if(p_147500_1_.getBlockMetadata() == 0)
			this.doRender(p_147500_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, 0);
	}
}