package essentialcraft.client.render.tile;

import essentialcraft.common.tile.TileMagicalChest;
import net.minecraft.client.model.ModelChest;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderMagicalChest extends TileEntitySpecialRenderer<TileMagicalChest>
{
	public static final ResourceLocation magicalTextures = new ResourceLocation("essentialcraft:textures/blocks/chests/magical.png");
	public static final ResourceLocation voidTextures = new ResourceLocation("essentialcraft:textures/blocks/chests/void.png");
	public static final ModelChest chest = new ModelChest();
	public static final ModelChest inventoryChest = new ModelChest();

	public void doRender(TileMagicalChest p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_)
	{
		RenderHelper.disableStandardItemLighting();
		GlStateManager.pushMatrix();

		GlStateManager.enableRescaleNormal();
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		GlStateManager.translate((float)p_76986_2_, (float)p_76986_4_ + 1.0F, (float)p_76986_6_ + 1.0F);
		GlStateManager.scale(1.0F, -1.0F, -1.0F);
		GlStateManager.translate(0.5F, 0.5F, 0.5F);
		GlStateManager.rotate((float)p_76986_1_.rotation*90+180, 0.0F, 1.0F, 0.0F);
		GlStateManager.translate(-0.5F, -0.5F, -0.5F);

		float f1 = p_76986_1_.prevLidAngle + (p_76986_1_.lidAngle - p_76986_1_.prevLidAngle) * p_76986_8_;
		chest.chestLid.rotateAngleX = -(f1 * (float)Math.PI / 2.0F);
		this.bindTexture(p_76986_1_.getBlockMetadata() == 0 ? magicalTextures : voidTextures);
		chest.renderAll();
		GlStateManager.disableRescaleNormal();
		GlStateManager.popMatrix();
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
	}

	@Override
	public void render(TileMagicalChest p_147500_1_, double p_147500_2_,double p_147500_4_, double p_147500_6_, float p_147500_8_, int destroyStage, float alpha) {
		this.doRender(p_147500_1_, p_147500_2_, p_147500_4_, p_147500_6_, p_147500_8_, 0);
	}
}