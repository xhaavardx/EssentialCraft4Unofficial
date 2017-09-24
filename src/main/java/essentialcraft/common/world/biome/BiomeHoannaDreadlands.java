package essentialcraft.common.world.biome;

import essentialcraft.common.block.BlocksCore;
import essentialcraft.common.world.gen.WorldGenDreadCacti;
import net.minecraft.world.biome.Biome;

public class BiomeHoannaDreadlands extends Biome
{
	public int grassColor = 16777215;
	public int waterColor = 16777215;
	public int leavesColor = 16777215;

	public BiomeHoannaDreadlands setGrassColor(int i)
	{
		grassColor = i;
		return this;
	}

	public BiomeHoannaDreadlands setWaterColor(int i)
	{
		waterColor = i;
		return this;
	}

	public BiomeHoannaDreadlands setLeavesColor(int i)
	{
		leavesColor = i;
		return this;
	}

	public BiomeHoannaDreadlands(BiomeProperties par1)
	{
		super(par1);
		this.topBlock = BlocksCore.dreadDirt.getDefaultState();
		this.fillerBlock = BlocksCore.dreadDirt.getDefaultState();
		this.decorator.treesPerChunk = -999;
		this.decorator.deadBushPerChunk = 2;
		this.decorator.reedsPerChunk = -999;
		this.decorator.cactiPerChunk = -999;
		this.decorator.cactusGen = new WorldGenDreadCacti();

		this.spawnableCreatureList.clear();
	}

	public int getBiomeGrassColor()
	{
		return grassColor;
	}

	public int getBiomeFoliageColor()
	{
		return leavesColor;
	}

	@Override
	public int getWaterColorMultiplier()
	{
		return waterColor;
	}

	@Override
	public int getModdedBiomeGrassColor(int original)
	{
		return grassColor;
	}

	@Override
	public int getModdedBiomeFoliageColor(int original)
	{
		return leavesColor;
	}
}
