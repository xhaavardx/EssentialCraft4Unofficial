package essentialcraft.common.world.structure;

import java.util.Map;
import java.util.Map.Entry;

import essentialcraft.utils.cfg.Config;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.MapGenStructure;
import net.minecraft.world.gen.structure.StructureStart;

public class MapGenModernShafts extends MapGenStructure{

	private double chance = 0.004D;

	@Override
	public String getStructureName() {
		return "ModernMineshaft";
	}

	public MapGenModernShafts() {}

	public MapGenModernShafts(Map<String, String> p_i2034_1_)
	{
		for(Entry<String, String> entry : p_i2034_1_.entrySet())
		{
			if(entry.getKey().equals("chance"))
			{
				this.chance = MathHelper.getDouble(entry.getValue(), this.chance);
			}
		}
	}

	@Override
	protected boolean canSpawnStructureAtCoords(int p_75047_1_, int p_75047_2_)
	{
		return this.rand.nextDouble() < this.chance && this.rand.nextInt(80) < Math.max(Math.abs(p_75047_1_), Math.abs(p_75047_2_)) && this.world.provider.getDimension() == Config.dimensionID;
	}

	@Override
	protected StructureStart getStructureStart(int p_75049_1_, int p_75049_2_)
	{
		return new StructureModernShaftStart(this.world, this.rand, p_75049_1_, p_75049_2_);
	}

	@Override
	public BlockPos getNearestStructurePos(World worldIn, BlockPos pos, boolean findUnexplored)
	{
		int i = 1000;
		int j = pos.getX() >> 4;
		int k = pos.getZ() >> 4;

		for (int l = 0; l <= 1000; ++l)
		{
			for (int i1 = -l; i1 <= l; ++i1)
			{
				boolean flag = i1 == -l || i1 == l;

				for (int j1 = -l; j1 <= l; ++j1)
				{
					boolean flag1 = j1 == -l || j1 == l;

					if (flag || flag1)
					{
						int k1 = j + i1;
						int l1 = k + j1;
						this.rand.setSeed(k1 ^ l1 ^ worldIn.getSeed());
						this.rand.nextInt();

						if (this.canSpawnStructureAtCoords(k1, l1) && (!findUnexplored || !worldIn.isChunkGeneratedAt(k1, l1)))
						{
							return new BlockPos((k1 << 4) + 8, 64, (l1 << 4) + 8);
						}
					}
				}
			}
		}

		return null;
	}
}
