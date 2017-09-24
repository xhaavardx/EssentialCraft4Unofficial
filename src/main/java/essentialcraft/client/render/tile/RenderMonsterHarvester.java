package essentialcraft.client.render.tile;

import DummyCore.Utils.DrawUtils;
import essentialcraft.common.tile.TileMonsterHarvester;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderMonsterHarvester extends TileEntitySpecialRenderer<TileMonsterHarvester> {

	public void doRender(TileMonsterHarvester tile, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_)
	{
		RenderHelper.disableStandardItemLighting();

		float rotation = (tile.getWorld().getWorldTime()+p_76986_8_) % 360;

		GlStateManager.pushMatrix();
		DrawUtils.renderItemStack_Full(tile.getStackInSlot(2), p_76986_2_, p_76986_4_, p_76986_6_, rotation,0F, 1, 1, 1, 0.5F, 0.95F,0.5F);
		GlStateManager.popMatrix();

		GlStateManager.pushMatrix();
		DrawUtils.renderItemStack_Full(tile.getStackInSlot(1), p_76986_2_, p_76986_4_, p_76986_6_, rotation,0F, 1, 1, 1, 0.3F, 1.15F,0.3F);
		GlStateManager.popMatrix();

		GlStateManager.pushMatrix();
		DrawUtils.renderItemStack_Full(tile.getStackInSlot(3), p_76986_2_, p_76986_4_, p_76986_6_, rotation,0F, 1, 1, 1, 0.7F, 1.15F,0.3F);
		GlStateManager.popMatrix();

		GlStateManager.pushMatrix();
		DrawUtils.renderItemStack_Full(tile.getStackInSlot(4), p_76986_2_, p_76986_4_, p_76986_6_, rotation,0F, 1, 1, 1, 0.3F, 1.15F,0.7F);
		GlStateManager.popMatrix();

		GlStateManager.pushMatrix();
		DrawUtils.renderItemStack_Full(tile.getStackInSlot(5), p_76986_2_, p_76986_4_, p_76986_6_, rotation,0F, 1, 1, 1, 0.7F, 1.15F,0.7F);
		GlStateManager.popMatrix();
		RenderHelper.enableStandardItemLighting();
	}

	@Override
	public void render(TileMonsterHarvester p_147500_1_, double p_147500_2_, double p_147500_4_, double p_147500_6_, float p_147500_8_, int destroyStage, float alpha) {
		if(p_147500_1_.getBlockMetadata() == 0)
			this.doRender(p_147500_1_, p_147500_2_, p_147500_4_, p_147500_6_, p_147500_8_, 0);
	}
}