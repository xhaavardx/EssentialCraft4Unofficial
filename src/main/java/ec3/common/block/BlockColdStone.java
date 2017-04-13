package ec3.common.block;

import DummyCore.Client.IModelRegisterer;
import ec3.api.IColdBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.client.model.ModelLoader;

public class BlockColdStone extends Block implements IColdBlock, IModelRegisterer {

	public BlockColdStone(Material p_i45394_1_) {
		super(p_i45394_1_);
	}

	public BlockColdStone() {
		super(Material.ICE);
	}

	@Override
	public float getColdModifier(IBlockAccess w, BlockPos pos) 
	{
		return 0.5F;
	}

	@Override
	public void registerModels() {
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation("essentialcraft:coldStone", "inventory"));
	}
}
