package ec3.common.world.biome;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;

public class BiomeGenCorruption_Shadow extends Biome
{
	public BiomeGenCorruption_Shadow(BiomeProperties par1)
	{
		super(par1);
	}

	@Override
	public int getGrassColorAtPos(BlockPos pos)
	{
		return 0x222222;
	}

	@Override
	public int getFoliageColorAtPos(BlockPos pos)
	{
		return 0x222222;
	}

	@Override
	public int getWaterColorMultiplier()
	{
		return 0x222222;
	}

	@Override
	public int getModdedBiomeGrassColor(int original)
	{
		return 0x222222;
	}

	@Override
	public int getModdedBiomeFoliageColor(int original)
	{
		return 0x222222;
	}
}
