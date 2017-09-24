package essentialcraft.common.world.gen.layer;

import net.minecraft.world.WorldType;
import net.minecraft.world.gen.ChunkGeneratorSettings;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.GenLayerAddIsland;
import net.minecraft.world.gen.layer.GenLayerAddMushroomIsland;
import net.minecraft.world.gen.layer.GenLayerAddSnow;
import net.minecraft.world.gen.layer.GenLayerBiomeEdge;
import net.minecraft.world.gen.layer.GenLayerDeepOcean;
import net.minecraft.world.gen.layer.GenLayerEdge;
import net.minecraft.world.gen.layer.GenLayerFuzzyZoom;
import net.minecraft.world.gen.layer.GenLayerHills;
import net.minecraft.world.gen.layer.GenLayerIsland;
import net.minecraft.world.gen.layer.GenLayerRareBiome;
import net.minecraft.world.gen.layer.GenLayerRemoveTooMuchOcean;
import net.minecraft.world.gen.layer.GenLayerRiver;
import net.minecraft.world.gen.layer.GenLayerRiverInit;
import net.minecraft.world.gen.layer.GenLayerRiverMix;
import net.minecraft.world.gen.layer.GenLayerShore;
import net.minecraft.world.gen.layer.GenLayerSmooth;
import net.minecraft.world.gen.layer.GenLayerVoronoiZoom;
import net.minecraft.world.gen.layer.GenLayerZoom;

public abstract class GenLayerHoanna extends GenLayer {

	public GenLayerHoanna(long seed) {
		super(seed);
	}

	public static GenLayer[] initializeAllBiomeGenerators(long seed, WorldType type, ChunkGeneratorSettings settings) {
		GenLayer genlayer = new GenLayerIsland(1L);
		genlayer = new GenLayerFuzzyZoom(2000L, genlayer);
		genlayer = new GenLayerAddIsland(1L, genlayer);
		genlayer = new GenLayerZoom(2001L, genlayer);
		genlayer = new GenLayerAddIsland(2L, genlayer);
		genlayer = new GenLayerAddIsland(50L, genlayer);
		genlayer = new GenLayerAddIsland(70L, genlayer);
		genlayer = new GenLayerRemoveTooMuchOcean(2L, genlayer);
		genlayer = new GenLayerAddSnow(2L, genlayer);
		genlayer= new GenLayerAddIsland(3L, genlayer);
		genlayer = new GenLayerEdge(2L, genlayer, GenLayerEdge.Mode.COOL_WARM);
		genlayer = new GenLayerEdge(2L, genlayer, GenLayerEdge.Mode.HEAT_ICE);
		genlayer = new GenLayerEdge(3L, genlayer, GenLayerEdge.Mode.SPECIAL);
		genlayer = new GenLayerZoom(2002L, genlayer);
		genlayer = new GenLayerZoom(2003L, genlayer);
		genlayer = new GenLayerAddIsland(4L, genlayer);
		genlayer = new GenLayerAddMushroomIsland(5L, genlayer);
		genlayer = new GenLayerDeepOcean(4L, genlayer);
		genlayer = GenLayerZoom.magnify(1000L, genlayer, 0);
		int i = 4;
		int j = i;

		if(settings != null) {
			i = settings.biomeSize;
			j = settings.riverSize;
		}

		if(type == WorldType.LARGE_BIOMES) {
			i = 6;
		}

		i = getModdedBiomeSize(type, i);

		GenLayer lvt_7_1_ = GenLayerZoom.magnify(1000L, genlayer, 0);
		GenLayer genlayerriverinit = new GenLayerRiverInit(100L, lvt_7_1_);
		GenLayer genlayerbiomeedge = new GenLayerHoannaBiomes(200L, genlayer, type, settings);
		genlayerbiomeedge = GenLayerZoom.magnify(1000L, genlayerbiomeedge, 2);
		genlayerbiomeedge = new GenLayerBiomeEdge(1000L, genlayerbiomeedge);
		GenLayer lvt_9_1_ = GenLayerZoom.magnify(1000L, genlayerriverinit, 2);
		GenLayer genlayerhills = new GenLayerHills(1000L, genlayerbiomeedge, lvt_9_1_);
		GenLayer genlayer5 = GenLayerZoom.magnify(1000L, genlayerriverinit, 2);
		genlayer5 = GenLayerZoom.magnify(1000L, genlayer5, j);
		GenLayer genlayerriver = new GenLayerRiver(1L, genlayer5);
		GenLayer genlayersmooth = new GenLayerSmooth(1000L, genlayerriver);
		genlayerhills = new GenLayerRareBiome(1001L, genlayerhills);

		for(int k = 0; k < i; ++k) {
			genlayerhills = new GenLayerZoom(1000 + k, genlayerhills);

			if(k == 0) {
				genlayerhills = new GenLayerAddIsland(3L, genlayerhills);
			}

			if(k == 1 || i == 1) {
				genlayerhills = new GenLayerShore(1000L, genlayerhills);
			}
		}

		GenLayer genlayersmooth1 = new GenLayerSmooth(1000L, genlayerhills);
		GenLayer genlayerrivermix = new GenLayerRiverMix(100L, genlayersmooth1, genlayersmooth);
		GenLayer genlayer3 = new GenLayerVoronoiZoom(10L, genlayerrivermix);
		genlayerrivermix.initWorldGenSeed(seed);
		genlayer3.initWorldGenSeed(seed);
		return new GenLayer[] {genlayerrivermix, genlayer3};
	}
}
