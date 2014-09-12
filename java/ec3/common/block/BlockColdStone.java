package ec3.common.block;

import ec3.api.IColdBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockColdStone extends Block implements IColdBlock{

	public BlockColdStone(Material p_i45394_1_) {
		super(p_i45394_1_);
	}
	
	public BlockColdStone() {
		super(Material.ice);
	}

	@Override
	public float getColdModifier(int meta) {
		// TODO Auto-generated method stub
		return 0.5F;
	}

}
