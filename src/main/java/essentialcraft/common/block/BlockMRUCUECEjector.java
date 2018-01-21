package essentialcraft.common.block;

import DummyCore.Client.IModelRegisterer;
import essentialcraft.common.tile.TileMRUCUECEjector;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;

public class BlockMRUCUECEjector extends BlockContainer implements IModelRegisterer {

	protected BlockMRUCUECEjector() {
		super(Material.ROCK, MapColor.PURPLE);
	}

	@Override
	public TileEntity createNewTileEntity(World var1, int var2) {
		return new TileMRUCUECEjector();
	}

	@Override
	public EnumBlockRenderType getRenderType(IBlockState s) {
		return EnumBlockRenderType.MODEL;
	}

	@Override
	public void registerModels() {
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation("essentialcraft:ecejector", "inventory"));
	}
}
