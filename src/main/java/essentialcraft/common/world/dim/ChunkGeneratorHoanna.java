package essentialcraft.common.world.dim;

import java.util.List;
import java.util.Random;

import com.google.common.collect.Lists;

import essentialcraft.common.world.gen.WorldGenOldCatacombs;
import essentialcraft.common.world.structure.MapGenModernShafts;
import essentialcraft.common.world.structure.MapGenTown;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome.SpawnListEntry;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.ChunkGeneratorOverworld;

public class ChunkGeneratorHoanna extends ChunkGeneratorOverworld {

	public Random rand;
	public World world;
	public MapGenTown town = new MapGenTown();
	public MapGenModernShafts shafts = new MapGenModernShafts();
	public static ChunkGeneratorHoanna instance;

	public static MapGenTown getGen() {
		return instance.town;
	}

	public ChunkGeneratorHoanna(World world, long seed, boolean mapFeaturesEnabledIn, String generatorOptions) {
		super(world, seed, false, generatorOptions);
		instance = this;
		try {
			rand = new Random(seed);
		}
		catch(Exception e) {
			e.printStackTrace();
			return;
		}
		this.world = world;
	}

	@Override
	public void populate(int x, int z) {
		instance = this;
		super.populate(x, z);
		int k = x * 16;
		int l = z * 16;

		town.generateStructure(world, rand, new ChunkPos(x, z));
		shafts.generateStructure(world, rand, new ChunkPos(x, z));

		int y = rand.nextInt(256);
		if(y > 6 && y < 32 && rand.nextFloat() < 0.1F) {
			int l1 = k + rand.nextInt(16) + 8;
			int j2 = l + rand.nextInt(16) + 8;
			new WorldGenOldCatacombs().generate(world, rand, new BlockPos(l1, y, j2));
		}
	}

	@Override
	public boolean isInsideStructure(World worldIn, String structureName, BlockPos pos) {
		if(structureName.equals("Town")) {
			return this.town.isInsideStructure(pos);
		}
		if(structureName.equals("ModernShafts")) {
			return this.shafts.isInsideStructure(pos);
		}
		if(structureName.equals("OldCatacombs")) {

		}
		return super.isInsideStructure(worldIn, structureName, pos);
	}

	@Override
	public Chunk generateChunk(int x, int z) {
		instance = this;
		ChunkPrimer chunkprimer = new ChunkPrimer();
		town.generate(world, x, z, chunkprimer);
		shafts.generate(world, x, z, chunkprimer);
		return super.generateChunk(x, z);
	}

	@Override
	public void recreateStructures(Chunk chunk, int x, int z) {
		ChunkPrimer chunkprimer = new ChunkPrimer();
		town.generate(world, x, z, chunkprimer);
		shafts.generate(world, x, z, chunkprimer);
		super.recreateStructures(chunk, x, z);
	}

	@Override
	public List<SpawnListEntry> getPossibleCreatures(EnumCreatureType creatureType, BlockPos pos) {
		if(creatureType.getPeacefulCreature()) {
			return Lists.newArrayList();
		}
		return super.getPossibleCreatures(creatureType, pos);
	}
}
