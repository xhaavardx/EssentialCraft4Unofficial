package essentialcraft.client.render.item;

import java.util.List;

import com.google.common.collect.ImmutableList;

import codechicken.lib.render.item.IItemRenderer;
import codechicken.lib.util.TransformUtils;
import essentialcraft.client.render.tile.RenderDarknessObelisk;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.block.model.ItemOverrideList;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.model.IModelState;

public class RenderDarknessObeliskAsItem implements IItemRenderer {

	@Override
	public void renderItem(ItemStack paramItemStack, TransformType type) {
		GlStateManager.pushMatrix();
		Minecraft.getMinecraft().renderEngine.bindTexture(RenderDarknessObelisk.obelisk);
		RenderDarknessObelisk.modelObelisk.renderAll();
		GlStateManager.popMatrix();
	}

	@Override
	public List<BakedQuad> getQuads(IBlockState state, EnumFacing side, long rand) {
		return ImmutableList.<BakedQuad>of();
	}

	@Override
	public boolean isAmbientOcclusion() {
		return false;
	}

	@Override
	public boolean isGui3d() {
		return false;
	}

	@Override
	public boolean isBuiltInRenderer() {
		return true;
	}

	@Override
	public TextureAtlasSprite getParticleTexture() {
		return Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite("essentialcraft:blocks/voidstone");
	}

	@Override
	public ItemCameraTransforms getItemCameraTransforms() {
		return ItemCameraTransforms.DEFAULT;
	}

	@Override
	public ItemOverrideList getOverrides() {
		return ItemOverrideList.NONE;
	}

	@Override
	public IModelState getTransforms() {
		return TransformUtils.DEFAULT_BLOCK;
	}
}
