package ec3.common.tile;

import java.util.List;

import net.minecraftforge.fml.common.eventhandler.Event.Result;
import net.minecraft.block.Block;
import net.minecraft.block.BlockMobSpawner;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldEntitySpawner;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.event.ForgeEventFactory;
import DummyCore.Utils.DataStorage;
import DummyCore.Utils.DummyData;
import DummyCore.Utils.MathUtils;
import ec3.api.ApiCore;
import ec3.utils.common.ECUtils;

public class TileDarknessObelisk extends TileMRUGeneric {
	public static float cfgMaxMRU = ApiCore.DEVICE_MAX_MRU_GENERIC;
	public static boolean generatesCorruption = false;
	public static int genCorruption = 30;
	public static int mruUsage = 500;
	public static float worldSpawnChance = 0.1F;
	public static int mobSpanwerDelay = 100;
	public static boolean enableMobSpawners = true;
	public static int mobSpawnerRadius = 3, worldSpawnerRadius = 8;
	
	public TileDarknessObelisk() {
		super();
		maxMRU = (int)cfgMaxMRU;
		setSlotsNum(2);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void update() {
		if(worldObj.isBlockIndirectlyGettingPowered(pos) == 0) {
			if(getStackInSlot(1) == null || !(Block.getBlockFromItem(getStackInSlot(1).getItem()) instanceof BlockMobSpawner)) {
				Biome biome = worldObj.getBiomeGenForCoords(pos);
				List l = biome.getSpawnableList(EnumCreatureType.MONSTER);
				if(l != null && !l.isEmpty() && !worldObj.isRemote && worldObj.rand.nextFloat() < worldSpawnChance && getMRU() > mruUsage) {
					Biome.SpawnListEntry spawnlistentry = null;
					IEntityLivingData ientitylivingdata = null;
					int rndOffsetX = (int)(pos.getX() + MathUtils.randomDouble(worldObj.rand) * worldSpawnerRadius);
					int rndOffsetY = (int)(pos.getY() + MathUtils.randomDouble(worldObj.rand) * worldSpawnerRadius);
					int rndOffsetZ = (int)(pos.getZ() + MathUtils.randomDouble(worldObj.rand) * worldSpawnerRadius);
					BlockPos rndPos = new BlockPos(rndOffsetX, rndOffsetY, rndOffsetZ);
					if(WorldEntitySpawner.canCreatureTypeSpawnAtLocation(EntityLiving.SpawnPlacementType.ON_GROUND, worldObj, rndPos)) {
						WorldServer wrld = (WorldServer) worldObj;
						spawnlistentry = wrld.getSpawnListEntryForTypeAt(EnumCreatureType.MONSTER, rndPos);
						if(spawnlistentry != null) {
							EntityLiving entityliving;
							
							try {
								entityliving = (EntityLiving)spawnlistentry.entityClass.getConstructor(new Class[] {World.class}).newInstance(new Object[] {wrld});
								entityliving.setLocationAndAngles((double)rndOffsetX+0.5F, (double)rndOffsetY, (double)rndOffsetZ+0.5D, wrld.rand.nextFloat()*360.0F, 0.0F);
								Result canSpawn = ForgeEventFactory.canEntitySpawn(entityliving, wrld, (float)rndOffsetX+0.5F, (float)rndOffsetY, (float)rndOffsetZ+0.5F);
								if (canSpawn == Result.ALLOW || (canSpawn == Result.DEFAULT && entityliving.getCanSpawnHere())) {
									wrld.spawnEntityInWorld(entityliving);
									setMRU(getMRU() - mruUsage);
									if(generatesCorruption)
										ECUtils.increaseCorruptionAt(worldObj, pos.getX(), pos.getY(), pos.getZ(), worldObj.rand.nextInt(genCorruption));
									if(!ForgeEventFactory.doSpecialSpawn(entityliving, wrld, (float)rndOffsetX+0.5F, (float)rndOffsetY, (float)rndOffsetZ+0.5F)) {
										ientitylivingdata = entityliving.onInitialSpawn(worldObj.getDifficultyForLocation(rndPos), ientitylivingdata);
									}
								}
							}
							catch (Exception exception) {
								exception.printStackTrace();
								return;
							}
						}
					}
				}
			}
			else if(Block.getBlockFromItem(getStackInSlot(1).getItem()) instanceof BlockMobSpawner && enableMobSpawners) {
				if(innerRotation >= mobSpanwerDelay && getMRU() > mruUsage) {
					innerRotation = 0;
					int metadata = getStackInSlot(1).getItemDamage();
					Entity base = EntityList.createEntityByID(metadata, worldObj);
					if(base instanceof EntityLiving) {
						EntityLiving entityliving = (EntityLiving) base;
						int rndOffsetX = (int)(pos.getX() + MathUtils.randomDouble(worldObj.rand)*mobSpawnerRadius);
						int rndOffsetY = (int)(pos.getY() + MathUtils.randomDouble(worldObj.rand));
						int rndOffsetZ = (int)(pos.getZ() + MathUtils.randomDouble(worldObj.rand)*mobSpawnerRadius);
						entityliving.setLocationAndAngles(rndOffsetX+0.5D, rndOffsetY, rndOffsetZ+0.5D, worldObj.rand.nextFloat()*360.0F, 0.0F);
						if(entityliving.getCanSpawnHere()) {
							if(!worldObj.isRemote)
								worldObj.spawnEntityInWorld(entityliving);
							
							worldObj.playEvent(2004, pos, 0);
							
							if(entityliving != null)
								entityliving.spawnExplosionParticle();
							setMRU(getMRU() - mruUsage);
							if(generatesCorruption)
								ECUtils.increaseCorruptionAt(worldObj, pos.getX(), pos.getY(), pos.getZ(), worldObj.rand.nextInt(genCorruption));
						}
					}		
				}
			}
		}
		ECUtils.manage(this, 0);
		super.update();
	}
	
	public static void setupConfig(Configuration cfg) {
		try {
			cfg.load();
			String[] cfgArrayString = cfg.getStringList("DarknessObeliskSettings", "tileentities", new String[] {
					"Max MRU:" + ApiCore.DEVICE_MAX_MRU_GENERIC,
					"MRU Usage:500",
					"Ticks required to try to create an entity:100",
					"Can this device actually generate corruption:false",
					"The amount of corruption generated each tick(do not set to 0!):30",
					"A chance per tick to try spawning a mob without a spawner:0.1",
					"Enable mob spawning using spawner:true",
					"Radius for a spawner:3",
					"Radius for no spawner:8"
			}, "");
			String dataString = "";
			
			for(int i = 0; i < cfgArrayString.length; ++i)
				dataString += "||" + cfgArrayString[i];
			
			DummyData[] data = DataStorage.parseData(dataString);
			
			mruUsage = Integer.parseInt(data[1].fieldValue);
			mobSpanwerDelay = Integer.parseInt(data[2].fieldValue);
			cfgMaxMRU = Float.parseFloat(data[0].fieldValue);
			generatesCorruption = Boolean.parseBoolean(data[3].fieldValue);
			genCorruption = Integer.parseInt(data[4].fieldValue);
			worldSpawnChance = Float.parseFloat(data[5].fieldValue);
			enableMobSpawners = Boolean.parseBoolean(data[6].fieldValue);
			mobSpawnerRadius = Integer.parseInt(data[7].fieldValue);
			worldSpawnerRadius = Integer.parseInt(data[8].fieldValue);
			
			cfg.save();
		}
		catch(Exception e) {
			return;
		}
	}
	
	@Override
	public int[] getOutputSlots() {
		return new int[0];
	}
}
