package essentialcraft.common.world.gen;

import java.util.Random;

import essentialcraft.common.block.BlocksCore;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenDreadCacti extends WorldGenerator {

	@Override
	public boolean generate(World world, Random rand, BlockPos pos) {
		for (int l = 0; l < 10; ++l) {
			int i1 = pos.getX() + rand.nextInt(8) - rand.nextInt(8);
			int j1 = pos.getY()+ rand.nextInt(4) - rand.nextInt(4);
			int k1 = pos.getZ() + rand.nextInt(8) - rand.nextInt(8);

			if(world.isAirBlock(new BlockPos(i1, j1, k1))) {
				int l1 = 1 + rand.nextInt(rand.nextInt(3) + 1);

				for(int i2 = 0; i2 < l1; ++i2) {
					if(BlocksCore.cacti.canBlockStay(world, new BlockPos(i1, j1 + i2, k1))) {
						world.setBlockState(new BlockPos(i1, j1 + i2, k1), BlocksCore.cacti.getDefaultState(), 2);
					}
				}
			}
		}

		return true;
	}
}