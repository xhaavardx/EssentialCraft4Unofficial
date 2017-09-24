package essentialcraft.common.world.structure;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import essentialcraft.common.registry.BiomeRegistry;
import essentialcraft.utils.cfg.Config;

import java.util.Random;

import net.minecraft.init.Biomes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.structure.MapGenStructure;
import net.minecraft.world.gen.structure.StructureComponent;
import net.minecraft.world.gen.structure.StructureStart;

public class MapGenTown extends MapGenStructure
{
	/** A list of all the biomes villages can spawn in. */
	public static List<Biome> villageSpawnBiomes = Arrays.asList(new Biome[] {Biomes.PLAINS, BiomeRegistry.chaosCorruption, BiomeRegistry.frozenCorruption});
	private int size;
	private int distance;
	private int minTownSeparation;

	public MapGenTown()
	{
		this.distance = 32;
		this.minTownSeparation = 8;
	}

	public MapGenTown(Map<String, String> map)
	{
		this();
		for (Entry<String, String> entry : map.entrySet())
		{
			if (entry.getKey().equals("size"))
			{
				this.size = MathHelper.getInt(entry.getValue(), this.size, 0);
			}
			else if (entry.getKey().equals("distance"))
			{
				this.distance = MathHelper.getInt(entry.getValue(), this.distance, 9);
			}
		}
	}

	@Override
	public String getStructureName()
	{
		return "Town";
	}

	@Override
	protected boolean canSpawnStructureAtCoords(int p_75047_1_, int p_75047_2_)
	{
		int k = p_75047_1_;
		int l = p_75047_2_;

		if (p_75047_1_ < 0)
		{
			p_75047_1_ -= this.distance - 1;
		}

		if (p_75047_2_ < 0)
		{
			p_75047_2_ -= this.distance - 1;
		}

		int i1 = p_75047_1_ / this.distance;
		int j1 = p_75047_2_ / this.distance;
		Random random = this.world.setRandomSeed(i1, j1, 10387312);
		i1 *= this.distance;
		j1 *= this.distance;
		i1 += random.nextInt(this.distance - 8);
		j1 += random.nextInt(this.distance - 8);
		if (k == i1 && l == j1)
		{
			boolean flag = this.world.provider.getDimension() == Config.dimensionID;
			if (flag)
			{
				return true;
			}
		}

		return false;
	}

	@Override
	public BlockPos getNearestStructurePos(World worldIn, BlockPos pos, boolean findUnexplored)  {
		this.world = worldIn;
		return findNearestStructurePosBySpacing(worldIn, this, pos, this.distance, 8, 10387312, false, 100, findUnexplored);
	}

	@Override
	protected StructureStart getStructureStart(int p_75049_1_, int p_75049_2_)
	{
		return new MapGenTown.Start(this.world, this.rand, p_75049_1_, p_75049_2_, this.size);
	}

	public static class Start extends StructureStart
	{
		/** well ... thats what it does */
		private boolean hasMoreThanTwoComponents;

		public Start() {}

		public Start(World world, Random rand, int chunkX, int chunkZ, int size)
		{
			super(chunkX, chunkZ);
			List<StructureTownPieces.PieceWeight> list = StructureTownPieces.getStructureVillageWeightedPieceList(rand, size);
			StructureTownPieces.Start start = new StructureTownPieces.Start(world.getBiomeProvider(), 0, rand, (chunkX << 4) + 2, (chunkZ << 4) + 2, list, size);
			this.components.add(start);
			start.buildComponent(start, this.components, rand);
			List<StructureComponent> list1 = start.pendingRoads;
			List<StructureComponent> list2 = start.pendingHouses;
			int l;

			while (!list1.isEmpty() || !list2.isEmpty())
			{
				StructureComponent structurecomponent;

				if (list1.isEmpty())
				{
					l = rand.nextInt(list2.size());
					structurecomponent = list2.remove(l);
					structurecomponent.buildComponent(start, this.components, rand);
				}
				else
				{
					l = rand.nextInt(list1.size());
					structurecomponent =list1.remove(l);
					structurecomponent.buildComponent(start, this.components, rand);
				}
			}

			this.updateBoundingBox();
			l = 0;
			Iterator<StructureComponent> iterator = this.components.iterator();

			while (iterator.hasNext())
			{
				StructureComponent structurecomponent1 = iterator.next();

				if (!(structurecomponent1 instanceof StructureTownPieces.Road))
				{
					++l;
				}
			}

			this.hasMoreThanTwoComponents = l > 2;
		}

		/**
		 * currently only defined for Villages, returns true if Village has more than 2 non-road components
		 */
		@Override
		public boolean isSizeableStructure()
		{
			return this.hasMoreThanTwoComponents;
		}

		@Override
		public void writeToNBT(NBTTagCompound nbt)
		{
			super.writeToNBT(nbt);
			nbt.setBoolean("Valid", this.hasMoreThanTwoComponents);
		}

		@Override
		public void readFromNBT(NBTTagCompound nbt)
		{
			super.readFromNBT(nbt);
			this.hasMoreThanTwoComponents = nbt.getBoolean("Valid");
		}
	}
}