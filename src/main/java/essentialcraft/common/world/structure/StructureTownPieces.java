package essentialcraft.common.world.structure;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import DummyCore.Utils.Coord2D;
import essentialcraft.common.block.BlocksCore;
import essentialcraft.common.registry.BiomeRegistry;
import essentialcraft.common.world.gen.ECExplosion;
import essentialcraft.common.world.gen.WorldGenDestroyedHouse;
import essentialcraft.common.world.gen.WorldGenMRUSpreader;
import essentialcraft.common.world.gen.WorldGenMRUTower;
import essentialcraft.utils.cfg.Config;
import net.minecraft.block.BlockSandStone;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Biomes;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeProvider;
import net.minecraft.world.gen.structure.MapGenStructureIO;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;
import net.minecraft.world.gen.structure.template.TemplateManager;

public class StructureTownPieces
{
	public static void registerVillagePieces()
	{
		MapGenStructureIO.registerStructureComponent(StructureTownPieces.House1.class, "TViBH");
		MapGenStructureIO.registerStructureComponent(StructureTownPieces.Field1.class, "TViDF");
		MapGenStructureIO.registerStructureComponent(StructureTownPieces.Torch.class, "TViL");
		MapGenStructureIO.registerStructureComponent(StructureTownPieces.Hall.class, "TViPH");
		MapGenStructureIO.registerStructureComponent(StructureTownPieces.House4Garden.class, "TViSH");
		MapGenStructureIO.registerStructureComponent(StructureTownPieces.WoodHut.class, "TViSmH");
		MapGenStructureIO.registerStructureComponent(StructureTownPieces.Church.class, "TViST");
		MapGenStructureIO.registerStructureComponent(StructureTownPieces.House2.class, "TViS");
		MapGenStructureIO.registerStructureComponent(StructureTownPieces.Start.class, "TViStart");
		MapGenStructureIO.registerStructureComponent(StructureTownPieces.Path.class, "TViSR");
		MapGenStructureIO.registerStructureComponent(StructureTownPieces.House3.class, "TViTRH");
		MapGenStructureIO.registerStructureComponent(StructureTownPieces.Well.class, "TViW");
	}

	public static List<StructureTownPieces.PieceWeight> getStructureVillageWeightedPieceList(Random p_75084_0_, int p_75084_1_)
	{
		ArrayList<StructureTownPieces.PieceWeight> arraylist = new ArrayList<StructureTownPieces.PieceWeight>();
		arraylist.add(new StructureTownPieces.PieceWeight(StructureTownPieces.House4Garden.class, 4, 30));
		arraylist.add(new StructureTownPieces.PieceWeight(StructureTownPieces.Church.class, 20, 10));
		arraylist.add(new StructureTownPieces.PieceWeight(StructureTownPieces.House1.class, 20, 30));
		arraylist.add(new StructureTownPieces.PieceWeight(StructureTownPieces.WoodHut.class, 3, 100));
		arraylist.add(new StructureTownPieces.PieceWeight(StructureTownPieces.Hall.class, 15, 60));
		arraylist.add(new StructureTownPieces.PieceWeight(StructureTownPieces.Field1.class, 3, 20));
		arraylist.add(new StructureTownPieces.PieceWeight(StructureTownPieces.House2.class, 15,30));
		arraylist.add(new StructureTownPieces.PieceWeight(StructureTownPieces.House3.class, 8, 30));

		Iterator<StructureTownPieces.PieceWeight> iterator = arraylist.iterator();

		while (iterator.hasNext())
		{
			if (iterator.next().villagePiecesLimit == 0)
			{
				iterator.remove();
			}
		}

		return arraylist;
	}

	private static int updatePieceWeight(List<StructureTownPieces.PieceWeight> weights)
	{
		boolean flag = false;
		int i = 0;
		StructureTownPieces.PieceWeight pieceweight;

		for (Iterator<StructureTownPieces.PieceWeight> iterator = weights.iterator(); iterator.hasNext(); i += pieceweight.villagePieceWeight)
		{
			pieceweight = iterator.next();

			if (pieceweight.villagePiecesLimit > 0 && pieceweight.villagePiecesSpawned < pieceweight.villagePiecesLimit)
			{
				flag = true;
			}
		}

		return flag ? i : -1;
	}

	private static StructureTownPieces.Village findAndCreateComponentFactory(StructureTownPieces.Start start, StructureTownPieces.PieceWeight weight, List<StructureComponent> components, Random rand, int sMinX, int sMinY, int sMinZ, EnumFacing facing, int type)
	{
		Class oclass = weight.villagePieceClass;
		Object object = null;

		if (oclass == StructureTownPieces.House4Garden.class)
		{
			object = StructureTownPieces.House4Garden.createPiece(start, components, rand, sMinX, sMinY, sMinZ, facing, type);
		}
		else if (oclass == StructureTownPieces.Church.class)
		{
			object = StructureTownPieces.Church.createPiece(start, components, rand, sMinX, sMinY, sMinZ, facing, type);
		}
		else if (oclass == StructureTownPieces.House1.class)
		{
			object = StructureTownPieces.House1.createPiece(start, components, rand, sMinX, sMinY, sMinZ, facing, type);
		}
		else if (oclass == StructureTownPieces.WoodHut.class)
		{
			object = StructureTownPieces.WoodHut.findPieceBox(start, components, rand, sMinX, sMinY, sMinZ, facing, type);
		}
		else if (oclass == StructureTownPieces.Hall.class)
		{
			object = StructureTownPieces.Hall.createPiece(start, components, rand, sMinX, sMinY, sMinZ, facing, type);
		}
		else if (oclass == StructureTownPieces.Field1.class)
		{
			object = StructureTownPieces.Field1.createPiece(start, components, rand, sMinX, sMinY, sMinZ, facing, type);
		}
		else if (oclass == StructureTownPieces.Field2.class)
		{
			object = StructureTownPieces.Field2.createPiece(start, components, rand, sMinX, sMinY, sMinZ, facing, type);
		}
		else if (oclass == StructureTownPieces.House2.class)
		{
			object = StructureTownPieces.House2.createPiece(start, components, rand, sMinX, sMinY, sMinZ, facing, type);
		}
		else if (oclass == StructureTownPieces.House3.class)
		{
			object = StructureTownPieces.House3.createPiece(start, components, rand, sMinX, sMinY, sMinZ, facing, type);
		}
		else
		{

		}

		return (StructureTownPieces.Village)object;
	}

	/**
	 * attempts to find a next Village Component to be spawned
	 */
	private static StructureTownPieces.Village generateComponent(StructureTownPieces.Start start, List<StructureComponent> components, Random rand, int sMinX, int sMinY, int sMinZ, EnumFacing facing, int type)
	{
		int j1 = updatePieceWeight(start.structureVillageWeightedPieceList);

		if (j1 <= 0)
		{
			return null;
		}
		else
		{
			int k1 = 0;

			while (k1 < 5)
			{
				++k1;
				int l1 = rand.nextInt(j1);
				for(StructureTownPieces.PieceWeight pieceweight : start.structureVillageWeightedPieceList)
				{
					l1 -= pieceweight.villagePieceWeight;

					if (l1 < 0)
					{
						if (!pieceweight.canSpawnMoreVillagePiecesOfType(type) || pieceweight == start.structVillagePieceWeight && start.structureVillageWeightedPieceList.size() > 1)
						{
							break;
						}

						StructureTownPieces.Village village = findAndCreateComponentFactory(start, pieceweight, components, rand, sMinX, sMinY, sMinZ, facing, type);

						if (village != null)
						{
							++pieceweight.villagePiecesSpawned;
							start.structVillagePieceWeight = pieceweight;

							if (!pieceweight.canSpawnMoreVillagePieces())
							{
								start.structureVillageWeightedPieceList.remove(pieceweight);
							}

							return village;
						}
					}
				}
			}

			StructureBoundingBox structureboundingbox = StructureTownPieces.Torch.findPieceBox(start, components, rand, sMinX, sMinY, sMinZ, facing);

			if (structureboundingbox != null)
			{
				return new StructureTownPieces.Torch(start, type, rand, structureboundingbox, facing);
			}
			else
			{
				return null;
			}
		}
	}

	/**
	 * attempts to find a next Structure Component to be spawned, private Village function
	 */
	private static StructureComponent generateAndAddComponent(StructureTownPieces.Start start, List<StructureComponent> components, Random rand, int sMinX, int sMinY, int sMinZ, EnumFacing facing, int type)
	{
		if (type > 500)
		{
			return null;
		}
		else if (Math.abs(sMinX - start.getBoundingBox().minX) <= 600 && Math.abs(sMinZ - start.getBoundingBox().minZ) <= 600)
		{
			StructureTownPieces.Village village = generateComponent(start, components, rand, sMinX, sMinY, sMinZ, facing, type + 1);

			if (village != null)
			{
				components.add(village);
				start.pendingHouses.add(village);
				return village;
			}

			return null;
		}
		else
		{
			return null;
		}
	}

	private static StructureComponent generateAndAddRoadPiece(StructureTownPieces.Start start, List<StructureComponent> components, Random rand, int sMinX, int sMinY, int sMinZ, EnumFacing facing, int type)
	{
		if (type > 50 + start.terrainType)
		{
			return null;
		}
		else if (Math.abs(sMinX - start.getBoundingBox().minX) <= 600 && Math.abs(sMinZ - start.getBoundingBox().minZ) <= 600)
		{
			StructureBoundingBox structureboundingbox = StructureTownPieces.Path.findPieceBox(start, components, rand, sMinX, sMinY, sMinZ, facing);

			if (structureboundingbox != null && structureboundingbox.minY > 10)
			{
				StructureTownPieces.Path path = new StructureTownPieces.Path(start, type, rand, structureboundingbox, facing);
				components.add(path);
				start.pendingRoads.add(path);
				return path;
			}

			return null;
		}
		else
		{
			return null;
		}
	}

	public static class Church extends StructureTownPieces.Village
	{
		public Church() {}

		public Church(StructureTownPieces.Start start, int type, Random rand, StructureBoundingBox structureBB, EnumFacing facing)
		{
			super(start, type);
			this.setCoordBaseMode(facing);
			this.boundingBox = structureBB;
		}

		public static StructureTownPieces.Church createPiece(StructureTownPieces.Start start, List<StructureComponent> components, Random rand, int sMinX, int sMinY, int sMinZ, EnumFacing facing, int type)
		{
			StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(sMinX, sMinY, sMinZ, 0, 0, 0, 5, 12, 9, facing);
			return canVillageGoDeeper(structureboundingbox) && StructureComponent.findIntersecting(components, structureboundingbox) == null ? new StructureTownPieces.Church(start, type, rand, structureboundingbox, facing) : null;
		}

		/**
		 * second Part of Structure generating, this for example places Spiderwebs, Mob Spawners, it closes
		 * Mineshafts at the end, it adds Fences...
		 */
		@Override
		public boolean addComponentParts(World world, Random rand, StructureBoundingBox structureBB)
		{
			if (this.averageGroundLvl < 0)
			{
				this.averageGroundLvl = this.getAverageGroundLevel(world, structureBB);

				if (this.averageGroundLvl < 0)
				{
					return true;
				}

				this.boundingBox.offset(0, this.averageGroundLvl - this.boundingBox.maxY + 12 - 1, 0);
			}
			if(world.provider.getDimension() == Config.dimensionID)
				new WorldGenMRUTower().generate(world, rand, new BlockPos(structureBB.minX, this.getAverageGroundLevel(world, structureBB), structureBB.minZ));
			return true;
		}
	}

	public static class Field1 extends StructureTownPieces.Village
	{
		public static Coord2D currentStructureCoords, prevStructureCoords;

		public Field1() {}

		public Field1(StructureTownPieces.Start start, int type, Random rand, StructureBoundingBox structureBB, EnumFacing facing)
		{
			super(start, type);
			this.setCoordBaseMode(facing);
			this.boundingBox = structureBB;
		}

		@Override
		protected void writeStructureToNBT(NBTTagCompound nbt)
		{
			super.writeStructureToNBT(nbt);
		}

		@Override
		protected void readStructureFromNBT(NBTTagCompound nbt, TemplateManager tm)
		{
			super.readStructureFromNBT(nbt, tm);
		}

		public static StructureTownPieces.Field1 createPiece(StructureTownPieces.Start p_74900_0_, List<StructureComponent> p_74900_1_, Random p_74900_2_, int p_74900_3_, int p_74900_4_, int p_74900_5_, EnumFacing p_74900_6_, int p_74900_7_)
		{
			StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(p_74900_3_, p_74900_4_, p_74900_5_, 0, 0, 0, 13, 4, 9, p_74900_6_);
			return canVillageGoDeeper(structureboundingbox) && StructureComponent.findIntersecting(p_74900_1_, structureboundingbox) == null ? new StructureTownPieces.Field1(p_74900_0_, p_74900_7_, p_74900_2_, structureboundingbox, p_74900_6_) : null;
		}

		/**
		 * second Part of Structure generating, this for example places Spiderwebs, Mob Spawners, it closes
		 * Mineshafts at the end, it adds Fences...
		 */
		@Override
		public boolean addComponentParts(World world, Random rand, StructureBoundingBox structureBB)
		{
			if (this.averageGroundLvl < 0)
			{
				this.averageGroundLvl = this.getAverageGroundLevel(world, structureBB);

				if (this.averageGroundLvl < 0)
				{
					return true;
				}

				this.boundingBox.offset(0, this.averageGroundLvl - this.boundingBox.maxY + 4 - 1, 0);
			}
			return true;
		}
	}

	public static class Field2 extends StructureTownPieces.Village
	{
		public Field2() {}

		public Field2(StructureTownPieces.Start start, int type, Random rand, StructureBoundingBox structureBB, EnumFacing facing)
		{
			super(start, type);
			this.setCoordBaseMode(facing);
			this.boundingBox = structureBB;
		}

		@Override
		protected void writeStructureToNBT(NBTTagCompound nbt)
		{
			super.writeStructureToNBT(nbt);
		}

		@Override
		protected void readStructureFromNBT(NBTTagCompound nbt, TemplateManager tm)
		{
			super.readStructureFromNBT(nbt, tm);
		}

		public static StructureTownPieces.Field2 createPiece(StructureTownPieces.Start start, List<StructureComponent> components, Random rand, int sMinX, int sMinY, int sMinZ, EnumFacing facing, int type)
		{
			StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(sMinX, sMinY, sMinZ, 0, 0, 0, 7, 4, 9, facing);
			return canVillageGoDeeper(structureboundingbox) && StructureComponent.findIntersecting(components, structureboundingbox) == null ? new StructureTownPieces.Field2(start, type, rand, structureboundingbox, facing) : null;
		}

		/**
		 * second Part of Structure generating, this for example places Spiderwebs, Mob Spawners, it closes
		 * Mineshafts at the end, it adds Fences...
		 */
		@Override
		public boolean addComponentParts(World world, Random rand, StructureBoundingBox structureBB)
		{
			if (this.averageGroundLvl < 0)
			{
				this.averageGroundLvl = this.getAverageGroundLevel(world, structureBB);

				if (this.averageGroundLvl < 0)
				{
					return true;
				}

				this.boundingBox.offset(0, this.averageGroundLvl - this.boundingBox.maxY + 4 - 1, 0);
			}

			return true;
		}
	}

	public static class Hall extends StructureTownPieces.Village
	{
		public Hall() {}

		public Hall(StructureTownPieces.Start start, int type, Random rand, StructureBoundingBox structureBB, EnumFacing facing)
		{
			super(start, type);
			this.setCoordBaseMode(facing);
			this.boundingBox = structureBB;
		}

		public static StructureTownPieces.Hall createPiece(StructureTownPieces.Start start, List<StructureComponent> components, Random rand, int sMinX, int sMinY, int sMinZ, EnumFacing facing, int type)
		{
			StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(sMinX, sMinY, sMinZ, 0, 0, 0, 9, 7, 11, facing);
			return canVillageGoDeeper(structureboundingbox) && StructureComponent.findIntersecting(components, structureboundingbox) == null ? new StructureTownPieces.Hall(start, type, rand, structureboundingbox, facing) : null;
		}

		/**
		 * second Part of Structure generating, this for example places Spiderwebs, Mob Spawners, it closes
		 * Mineshafts at the end, it adds Fences...
		 */
		@Override
		public boolean addComponentParts(World world, Random rand, StructureBoundingBox structureBB)
		{
			if (this.averageGroundLvl < 0)
			{
				this.averageGroundLvl = this.getAverageGroundLevel(world, structureBB);

				if (this.averageGroundLvl < 0)
				{
					return true;
				}

				this.boundingBox.offset(0, this.averageGroundLvl - this.boundingBox.maxY + 7 - 1, 0);
			}
			if(world.provider.getDimension() == Config.dimensionID)
				new WorldGenMRUSpreader().generate(world, rand, new BlockPos(structureBB.minX, this.getAverageGroundLevel(world, structureBB), structureBB.minZ));
			return true;
		}
	}

	public static class House1 extends StructureTownPieces.Village
	{
		public House1() {}

		public House1(StructureTownPieces.Start start, int type, Random rand, StructureBoundingBox structureBB, EnumFacing facing)
		{
			super(start, type);
			this.setCoordBaseMode(facing);
			this.boundingBox = structureBB;
		}

		public static StructureTownPieces.House1 createPiece(StructureTownPieces.Start start, List<StructureComponent> components, Random rand, int sMinX, int sMinY, int sMinZ, EnumFacing facing, int type)
		{
			StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(sMinX, sMinY, sMinZ, 0, 0, 0, 9, 9, 6, facing);
			return canVillageGoDeeper(structureboundingbox) && StructureComponent.findIntersecting(components, structureboundingbox) == null ? new StructureTownPieces.House1(start, type, rand, structureboundingbox, facing) : null;
		}

		/**
		 * second Part of Structure generating, this for example places Spiderwebs, Mob Spawners, it closes
		 * Mineshafts at the end, it adds Fences...
		 */
		@Override
		public boolean addComponentParts(World world, Random rand, StructureBoundingBox structureBB)
		{
			if (this.averageGroundLvl < 0)
			{
				this.averageGroundLvl = this.getAverageGroundLevel(world, structureBB);

				if (this.averageGroundLvl < 0)
				{
					return true;
				}

				this.boundingBox.offset(0, this.averageGroundLvl - this.boundingBox.maxY + 9 - 1, 0);
			}

			if(world.provider.getDimension() == Config.dimensionID)
				new WorldGenDestroyedHouse(rand.nextInt(2)+1,5).generate(world, rand, new BlockPos(structureBB.minX, this.getAverageGroundLevel(world, structureBB), structureBB.minZ));
			return true;
		}
	}

	public static class House2 extends StructureTownPieces.Village
	{
		private boolean hasMadeChest;

		public House2() {}

		public House2(StructureTownPieces.Start start, int type, Random rand, StructureBoundingBox structureBB, EnumFacing facing)
		{
			super(start, type);
			this.setCoordBaseMode(facing);
			this.boundingBox = structureBB;
		}

		public static StructureTownPieces.House2 createPiece(StructureTownPieces.Start start, List<StructureComponent> components, Random rand, int sMinX, int sMinY, int sMinZ, EnumFacing facing, int type)
		{
			StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(sMinX, sMinY, sMinZ, 0, 0, 0, 10, 6, 7, facing);
			return canVillageGoDeeper(structureboundingbox) && StructureComponent.findIntersecting(components, structureboundingbox) == null ? new StructureTownPieces.House2(start, type, rand, structureboundingbox, facing) : null;
		}

		@Override
		protected void writeStructureToNBT(NBTTagCompound nbt)
		{
			super.writeStructureToNBT(nbt);
			nbt.setBoolean("Chest", this.hasMadeChest);
		}

		@Override
		protected void readStructureFromNBT(NBTTagCompound nbt, TemplateManager tm)
		{
			super.readStructureFromNBT(nbt, tm);
			this.hasMadeChest = nbt.getBoolean("Chest");
		}

		/**
		 * second Part of Structure generating, this for example places Spiderwebs, Mob Spawners, it closes
		 * Mineshafts at the end, it adds Fences...
		 */
		@Override
		public boolean addComponentParts(World world, Random rand, StructureBoundingBox structureBB)
		{
			if (this.averageGroundLvl < 0)
			{
				this.averageGroundLvl = this.getAverageGroundLevel(world, structureBB);

				if (this.averageGroundLvl < 0)
				{
					return true;
				}

				this.boundingBox.offset(0, this.averageGroundLvl - this.boundingBox.maxY + 6 - 1, 0);
			}

			if(world.provider.getDimension() == Config.dimensionID)
				new WorldGenDestroyedHouse(rand.nextInt(5)+1,4).generate(world, rand, new BlockPos(structureBB.minX, this.getAverageGroundLevel(world, structureBB), structureBB.minZ));
			return true;
		}
	}

	public static class House3 extends StructureTownPieces.Village
	{

		public House3() {}

		public House3(StructureTownPieces.Start start, int type, Random rand, StructureBoundingBox structureBB, EnumFacing facing)
		{
			super(start, type);
			this.setCoordBaseMode(facing);
			this.boundingBox = structureBB;
		}

		public static StructureTownPieces.House3 createPiece(StructureTownPieces.Start start, List<StructureComponent> components, Random rand, int sMinX, int sMinY, int sMinZ, EnumFacing facing, int type)
		{
			StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(sMinX, sMinY, sMinZ, 0, 0, 0, 9, 7, 12, facing);
			return canVillageGoDeeper(structureboundingbox) && StructureComponent.findIntersecting(components, structureboundingbox) == null ? new StructureTownPieces.House3(start, type, rand, structureboundingbox, facing) : null;
		}

		/**
		 * second Part of Structure generating, this for example places Spiderwebs, Mob Spawners, it closes
		 * Mineshafts at the end, it adds Fences...
		 */
		@Override
		public boolean addComponentParts(World world, Random rand, StructureBoundingBox structureBB)
		{
			if (this.averageGroundLvl < 0)
			{
				this.averageGroundLvl = this.getAverageGroundLevel(world, structureBB);

				if (this.averageGroundLvl < 0)
				{
					return true;
				}

				this.boundingBox.offset(0, this.averageGroundLvl - this.boundingBox.maxY + 7 - 1, 0);
			}
			if(world.provider.getDimension() == Config.dimensionID)
				new WorldGenDestroyedHouse(rand.nextInt(9)+1,3).generate(world, rand, new BlockPos(structureBB.minX, this.getAverageGroundLevel(world, structureBB), structureBB.minZ));
			return true;
		}
	}

	public static class House4Garden extends StructureTownPieces.Village
	{
		private boolean isRoofAccessible;
		public House4Garden() {}

		public House4Garden(StructureTownPieces.Start start, int type, Random rand, StructureBoundingBox structureBB, EnumFacing facing)
		{
			super(start, type);
			this.setCoordBaseMode(facing);
			this.boundingBox = structureBB;
			this.isRoofAccessible = rand.nextBoolean();
		}

		@Override
		protected void writeStructureToNBT(NBTTagCompound nbt)
		{
			super.writeStructureToNBT(nbt);
			nbt.setBoolean("Terrace", this.isRoofAccessible);
		}

		@Override
		protected void readStructureFromNBT(NBTTagCompound nbt, TemplateManager tm)
		{
			super.readStructureFromNBT(nbt, tm);
			this.isRoofAccessible = nbt.getBoolean("Terrace");
		}

		public static StructureTownPieces.House4Garden createPiece(StructureTownPieces.Start start, List<StructureComponent> components, Random rand, int sMinX, int sMinY, int sMinZ, EnumFacing facing, int type)
		{
			StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(sMinX, sMinY, sMinZ, 0, 0, 0, 5, 6, 5, facing);
			return StructureComponent.findIntersecting(components, structureboundingbox) != null ? null : new StructureTownPieces.House4Garden(start, type, rand, structureboundingbox, facing);
		}

		/**
		 * second Part of Structure generating, this for example places Spiderwebs, Mob Spawners, it closes
		 * Mineshafts at the end, it adds Fences...
		 */
		@Override
		public boolean addComponentParts(World world, Random rand, StructureBoundingBox structureBB)
		{
			if (this.averageGroundLvl < 0)
			{
				this.averageGroundLvl = this.getAverageGroundLevel(world, structureBB);

				if (this.averageGroundLvl < 0)
				{
					return true;
				}

				this.boundingBox.offset(0, this.averageGroundLvl - this.boundingBox.maxY + 6 - 1, 0);
			}

			if(world.provider.getDimension() == Config.dimensionID)
				new WorldGenDestroyedHouse(rand.nextInt(3)+1,7).generate(world, rand, new BlockPos(structureBB.minX, this.getAverageGroundLevel(world, structureBB), structureBB.minZ));
			return true;
		}
	}

	public static class Path extends StructureTownPieces.Road
	{
		private int averageGroundLevel;

		public Path() {}

		public Path(StructureTownPieces.Start start, int type, Random rand, StructureBoundingBox structureBB, EnumFacing facing)
		{
			super(start, type);
			this.setCoordBaseMode(facing);
			this.boundingBox = structureBB;
			this.averageGroundLevel = Math.max(structureBB.getXSize(), structureBB.getZSize());
		}

		@Override
		protected void writeStructureToNBT(NBTTagCompound nbt)
		{
			super.writeStructureToNBT(nbt);
			nbt.setInteger("Length", this.averageGroundLevel);
		}

		@Override
		protected void readStructureFromNBT(NBTTagCompound nbt, TemplateManager tm)
		{
			super.readStructureFromNBT(nbt, tm);
			this.averageGroundLevel = nbt.getInteger("Length");
		}

		/**
		 * Initiates construction of the Structure Component picked, at the current Location of StructGen
		 */
		@Override
		public void buildComponent(StructureComponent component, List<StructureComponent> components, Random rand)
		{
			boolean flag = false;
			int i;
			StructureComponent structurecomponent1;

			for (i = rand.nextInt(18); i < this.averageGroundLevel - 8; i += 2 + rand.nextInt(18))
			{
				structurecomponent1 = this.getNextComponentNN((StructureTownPieces.Start)component, components, rand, 0, i);

				if (structurecomponent1 != null)
				{
					i += Math.max(structurecomponent1.getBoundingBox().getXSize(), structurecomponent1.getBoundingBox().getZSize());
					flag = true;
				}
			}

			for (i = rand.nextInt(18); i < this.averageGroundLevel - 8; i += 2 + rand.nextInt(18))
			{
				structurecomponent1 = this.getNextComponentPP((StructureTownPieces.Start)component, components, rand, 0, i);

				if (structurecomponent1 != null)
				{
					i += Math.max(structurecomponent1.getBoundingBox().getXSize(), structurecomponent1.getBoundingBox().getZSize());
					flag = true;
				}
			}

			if (flag && rand.nextInt(18) > 0)
			{
				switch (this.getCoordBaseMode())
				{
				case SOUTH:
					StructureTownPieces.generateAndAddRoadPiece((StructureTownPieces.Start)component, components, rand, this.boundingBox.minX - 1, this.boundingBox.minY, this.boundingBox.maxZ - 2, EnumFacing.WEST, this.getComponentType());
					break;
				case WEST:
					StructureTownPieces.generateAndAddRoadPiece((StructureTownPieces.Start)component, components, rand, this.boundingBox.minX, this.boundingBox.minY, this.boundingBox.minZ - 1, EnumFacing.SOUTH, this.getComponentType());
					break;
				case NORTH:
					StructureTownPieces.generateAndAddRoadPiece((StructureTownPieces.Start)component, components, rand, this.boundingBox.minX - 1, this.boundingBox.minY, this.boundingBox.minZ, EnumFacing.WEST, this.getComponentType());
					break;
				case EAST:
					StructureTownPieces.generateAndAddRoadPiece((StructureTownPieces.Start)component, components, rand, this.boundingBox.maxX - 2, this.boundingBox.minY, this.boundingBox.minZ - 1, EnumFacing.SOUTH, this.getComponentType());
				default:
					break;
				}
			}

			if (flag && rand.nextInt(18) > 0)
			{
				switch (this.getCoordBaseMode())
				{
				case SOUTH:
					StructureTownPieces.generateAndAddRoadPiece((StructureTownPieces.Start)component, components, rand, this.boundingBox.maxX + 1, this.boundingBox.minY, this.boundingBox.maxZ - 2, EnumFacing.EAST, this.getComponentType());
					break;
				case WEST:
					StructureTownPieces.generateAndAddRoadPiece((StructureTownPieces.Start)component, components, rand, this.boundingBox.minX, this.boundingBox.minY, this.boundingBox.maxZ + 1, EnumFacing.NORTH, this.getComponentType());
					break;
				case NORTH:
					StructureTownPieces.generateAndAddRoadPiece((StructureTownPieces.Start)component, components, rand, this.boundingBox.maxX + 1, this.boundingBox.minY, this.boundingBox.minZ, EnumFacing.EAST, this.getComponentType());
					break;
				case EAST:
					StructureTownPieces.generateAndAddRoadPiece((StructureTownPieces.Start)component, components, rand, this.boundingBox.maxX - 2, this.boundingBox.minY, this.boundingBox.maxZ + 1, EnumFacing.NORTH, this.getComponentType());
				default:
					break;
				}
			}
		}

		public static StructureBoundingBox findPieceBox(StructureTownPieces.Start start, List<StructureComponent> components, Random rand, int sMinX, int sMinY, int sMinZ, EnumFacing facing)
		{
			for (int i1 = 7 * MathHelper.getInt(rand, 3, 5); i1 >= 7; i1 -= 7)
			{
				StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(sMinX, sMinY, sMinZ, 0, 0, 0, 5, 5, i1, facing);

				if (StructureComponent.findIntersecting(components, structureboundingbox) == null)
				{
					return structureboundingbox;
				}
			}

			return null;
		}

		/**
		 * second Part of Structure generating, this for example places Spiderwebs, Mob Spawners, it closes
		 * Mineshafts at the end, it adds Fences...
		 */
		@Override
		public boolean addComponentParts(World world, Random rand, StructureBoundingBox structureBB)
		{
			IBlockState block = this.getBiomeSpecificBlockState(BlocksCore.concrete.getDefaultState());

			BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();
			for (int i = this.boundingBox.minX; i <= this.boundingBox.maxX; ++i)
			{
				for (int j = this.boundingBox.minZ; j <= this.boundingBox.maxZ; ++j)
				{
					pos.setPos(i, 64, j);
					if (structureBB.isVecInside(pos))
					{
						int k = world.getTopSolidOrLiquidBlock(pos).getY() - 1;
						world.setBlockState(pos, block, 2);
					}
				}
			}

			return true;
		}
	}

	public static class PieceWeight
	{
		/** The Class object for the represantation of this village piece. */
		public Class<? extends StructureTownPieces.Village> villagePieceClass;
		public final int villagePieceWeight;
		public int villagePiecesSpawned;
		public int villagePiecesLimit;

		public PieceWeight(Class<? extends StructureTownPieces.Village> pieceClass, int weight, int limit)
		{
			this.villagePieceClass = pieceClass;
			this.villagePieceWeight = weight;
			this.villagePiecesLimit = limit;
		}

		public boolean canSpawnMoreVillagePiecesOfType(int type)
		{
			return this.villagePiecesLimit == 0 || this.villagePiecesSpawned < this.villagePiecesLimit;
		}

		public boolean canSpawnMoreVillagePieces()
		{
			return this.villagePiecesLimit == 0 || this.villagePiecesSpawned < this.villagePiecesLimit;
		}
	}

	public abstract static class Road extends StructureTownPieces.Village
	{

		public Road() {}

		protected Road(StructureTownPieces.Start start, int type)
		{
			super(start, type);
		}
	}

	public static class Start extends StructureTownPieces.Well
	{
		public BiomeProvider worldChunkMngr;
		/** Boolean that determines if the village is in a desert or not. */
		public boolean inDesert;
		/** World terrain type, 0 for normal, 1 for flap map */
		public int terrainType;
		public StructureTownPieces.PieceWeight structVillagePieceWeight;
		/**
		 * Contains List of all spawnable Structure Piece Weights. If no more Pieces of a type can be spawned, they
		 * are removed from this list
		 */
		public List<StructureTownPieces.PieceWeight> structureVillageWeightedPieceList;
		public List<StructureComponent> pendingHouses = new ArrayList<StructureComponent>();
		public List<StructureComponent> pendingRoads = new ArrayList<StructureComponent>();
		public Biome biome;

		public Start() {}

		public Start(BiomeProvider biomeProvider, int type, Random rand, int x, int z, List<StructureTownPieces.PieceWeight> weights, int terrainType)
		{
			super(null, 0, rand, x, z);
			this.worldChunkMngr = biomeProvider;
			this.structureVillageWeightedPieceList = weights;
			this.terrainType = terrainType;
			Biome biomegenbase = biomeProvider.getBiome(new BlockPos(x, 0, z));
			this.inDesert = biomegenbase == Biomes.DESERT || biomegenbase == Biomes.DESERT_HILLS || biomegenbase == BiomeRegistry.desert;
			this.biome = biomegenbase;
		}

		public BiomeProvider getBiomeProvider()
		{
			return this.worldChunkMngr;
		}
	}

	public static class Torch extends StructureTownPieces.Village
	{

		public Torch() {}

		public Torch(StructureTownPieces.Start start, int type, Random rand, StructureBoundingBox structureBB, EnumFacing facing)
		{
			super(start, type);
			this.setCoordBaseMode(facing);
			this.boundingBox = structureBB;
		}

		public static StructureBoundingBox findPieceBox(StructureTownPieces.Start start, List<StructureComponent> components, Random rand, int sMinX, int sMinY, int sMinZ, EnumFacing facing)
		{
			StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(sMinX, sMinY, sMinZ, 0, 0, 0, 3, 4, 2, facing);
			return StructureComponent.findIntersecting(components, structureboundingbox) != null ? null : structureboundingbox;
		}

		/**
		 * second Part of Structure generating, this for example places Spiderwebs, Mob Spawners, it closes
		 * Mineshafts at the end, it adds Fences...
		 */
		@Override
		public boolean addComponentParts(World world, Random rand, StructureBoundingBox structureBB)
		{
			if (this.averageGroundLvl < 0)
			{
				this.averageGroundLvl = this.getAverageGroundLevel(world, structureBB);

				if (this.averageGroundLvl < 0)
				{
					return true;
				}

				this.boundingBox.offset(0, this.averageGroundLvl - this.boundingBox.maxY + 4 - 1, 0);
			}

			if(world.provider.getDimension() == Config.dimensionID)
			{
				this.fillWithBlocks(world, structureBB, 0, 0, 0, 2, 3, 1, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
				this.setBlockState(world, BlocksCore.fence[0].getDefaultState(), 1, 0, 0, structureBB);
				this.setBlockState(world, BlocksCore.fence[0].getDefaultState(), 1, 1, 0, structureBB);
				this.setBlockState(world, BlocksCore.fence[0].getDefaultState(), 1, 2, 0, structureBB);
				this.setBlockState(world, BlocksCore.voidStone.getStateFromMeta(15), 1, 3, 0, structureBB);
				this.setBlockState(world, BlocksCore.torch.getStateFromMeta(0), 0, 3, 0, structureBB);
				this.setBlockState(world, BlocksCore.torch.getStateFromMeta(0), 1, 3, 1, structureBB);
				this.setBlockState(world, BlocksCore.torch.getStateFromMeta(0), 2, 3, 0, structureBB);
				this.setBlockState(world, BlocksCore.torch.getStateFromMeta(0), 1, 3, -1, structureBB);
			}
			return true;
		}
	}

	public abstract static class Village extends StructureComponent
	{
		protected int averageGroundLvl = -1;
		private boolean inDesert;
		private StructureTownPieces.Start startPiece;

		public Village() {}

		protected Village(StructureTownPieces.Start start, int type)
		{
			super(type);

			if (start != null)
			{
				this.inDesert = start.inDesert;
				startPiece = start;
			}
		}

		@Override
		protected void writeStructureToNBT(NBTTagCompound nbt)
		{
			nbt.setInteger("HPos", this.averageGroundLvl);
			nbt.setBoolean("Desert", this.inDesert);
		}

		@Override
		protected void readStructureFromNBT(NBTTagCompound nbt, TemplateManager tm)
		{
			this.averageGroundLvl = nbt.getInteger("HPos");
			this.inDesert = nbt.getBoolean("Desert");
		}

		/**
		 * Gets the next village component, with the bounding box shifted -1 in the X and Z direction.
		 */
		protected StructureComponent getNextComponentNN(StructureTownPieces.Start start, List<StructureComponent> components, Random rand, int height, int offset)
		{
			switch (this.getCoordBaseMode())
			{
			case SOUTH:
				return StructureTownPieces.generateAndAddComponent(start, components, rand, this.boundingBox.minX - 1, this.boundingBox.minY + height, this.boundingBox.minZ + offset, EnumFacing.WEST, this.getComponentType());
			case WEST:
				return StructureTownPieces.generateAndAddComponent(start, components, rand, this.boundingBox.minX + offset, this.boundingBox.minY + height, this.boundingBox.minZ - 1, EnumFacing.NORTH, this.getComponentType());
			case NORTH:
				return StructureTownPieces.generateAndAddComponent(start, components, rand, this.boundingBox.minX - 1, this.boundingBox.minY + height, this.boundingBox.minZ + offset, EnumFacing.WEST, this.getComponentType());
			case EAST:
				return StructureTownPieces.generateAndAddComponent(start, components, rand, this.boundingBox.minX + offset, this.boundingBox.minY + height, this.boundingBox.minZ - 1, EnumFacing.NORTH, this.getComponentType());
			default:
				return null;
			}
		}

		/**
		 * Gets the next village component, with the bounding box shifted +1 in the X and Z direction.
		 */
		protected StructureComponent getNextComponentPP(StructureTownPieces.Start start, List<StructureComponent> components, Random rand, int height, int offset)
		{
			switch (this.getCoordBaseMode())
			{
			case SOUTH:
				return StructureTownPieces.generateAndAddComponent(start, components, rand, this.boundingBox.maxX + 1, this.boundingBox.minY + height, this.boundingBox.minZ + offset, EnumFacing.EAST, this.getComponentType());
			case WEST:
				return StructureTownPieces.generateAndAddComponent(start, components, rand, this.boundingBox.minX + offset, this.boundingBox.minY + height, this.boundingBox.maxZ + 1, EnumFacing.SOUTH, this.getComponentType());
			case NORTH:
				return StructureTownPieces.generateAndAddComponent(start, components, rand, this.boundingBox.maxX + 1, this.boundingBox.minY + height, this.boundingBox.minZ + offset, EnumFacing.EAST, this.getComponentType());
			case EAST:
				return StructureTownPieces.generateAndAddComponent(start, components, rand, this.boundingBox.minX + offset, this.boundingBox.minY + height, this.boundingBox.maxZ + 1, EnumFacing.SOUTH, this.getComponentType());
			default:
				return null;
			}
		}

		/**
		 * Discover the y coordinate that will serve as the ground level of the supplied BoundingBox. (A median of
		 * all the levels in the BB's horizontal rectangle).
		 */
		protected int getAverageGroundLevel(World worldIn, StructureBoundingBox structurebb)
		{
			int i = 0;
			int j = 0;
			BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

			for (int k = this.boundingBox.minZ; k <= this.boundingBox.maxZ; ++k)
			{
				for (int l = this.boundingBox.minX; l <= this.boundingBox.maxX; ++l)
				{
					blockpos$mutableblockpos.setPos(l, 64, k);

					if (structurebb.isVecInside(blockpos$mutableblockpos))
					{
						i += Math.max(worldIn.getTopSolidOrLiquidBlock(blockpos$mutableblockpos).getY(), worldIn.provider.getAverageGroundLevel() - 1);
						++j;
					}
				}
			}

			if (j == 0)
			{
				return -1;
			}
			else
			{
				return i / j;
			}
		}

		protected static boolean canVillageGoDeeper(StructureBoundingBox structureBB)
		{
			return structureBB != null && structureBB.minY > 10;
		}

		protected IBlockState getBiomeSpecificBlockState(IBlockState blockstateIn)
		{
			net.minecraftforge.event.terraingen.BiomeEvent.GetVillageBlockID event = new net.minecraftforge.event.terraingen.BiomeEvent.GetVillageBlockID(startPiece == null ? null : startPiece.biome, blockstateIn);
			net.minecraftforge.common.MinecraftForge.TERRAIN_GEN_BUS.post(event);
			if (event.getResult() == net.minecraftforge.fml.common.eventhandler.Event.Result.DENY) return event.getReplacement();
			if (this.inDesert)
			{
				if (blockstateIn.getBlock() == Blocks.LOG || blockstateIn.getBlock() == Blocks.LOG2)
				{
					return Blocks.SANDSTONE.getDefaultState();
				}

				if (blockstateIn.getBlock() == Blocks.COBBLESTONE)
				{
					return Blocks.SANDSTONE.getStateFromMeta(BlockSandStone.EnumType.DEFAULT.getMetadata());
				}

				if (blockstateIn.getBlock() == Blocks.PLANKS)
				{
					return Blocks.SANDSTONE.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.getMetadata());
				}

				if (blockstateIn.getBlock() == Blocks.OAK_STAIRS)
				{
					return Blocks.SANDSTONE_STAIRS.getDefaultState().withProperty(BlockStairs.FACING, blockstateIn.getValue(BlockStairs.FACING));
				}

				if (blockstateIn.getBlock() == Blocks.STONE_STAIRS)
				{
					return Blocks.SANDSTONE_STAIRS.getDefaultState().withProperty(BlockStairs.FACING, blockstateIn.getValue(BlockStairs.FACING));
				}

				if (blockstateIn.getBlock() == Blocks.GRAVEL)
				{
					return Blocks.SANDSTONE.getDefaultState();
				}
			}

			return blockstateIn;
		}

		/**
		 * current Position depends on currently set Coordinates mode, is computed here
		 */
		@Override
		protected void setBlockState(World world, IBlockState block, int x, int y, int z, StructureBoundingBox structureBB)
		{
			IBlockState block1 = this.getBiomeSpecificBlockState(block);
			super.setBlockState(world, block1, x, y, z, structureBB);
		}

		/**
		 * arguments: (World getEntityWorld(), StructureBoundingBox structBB, int minX, int minY, int minZ, int maxX, int
		 * maxY, int maxZ, int placeBlock, int replaceBlock, boolean alwaysreplace)
		 */
		@Override
		protected void fillWithBlocks(World world, StructureBoundingBox structureBB, int xMin, int yMin, int zMin, int xMax, int yMax, int zMax, IBlockState block0, IBlockState block1, boolean existingOnly)
		{
			IBlockState block2 = this.getBiomeSpecificBlockState(block0);
			IBlockState block3 = this.getBiomeSpecificBlockState(block1);
			super.fillWithBlocks(world, structureBB, xMin, yMin, zMin, xMax, yMax, zMax, block2, block3, existingOnly);
		}

		@Override
		protected void replaceAirAndLiquidDownwards(World worldIn, IBlockState blockstateIn, int x, int y, int z, StructureBoundingBox boundingboxIn)
		{
			IBlockState iblockstate = this.getBiomeSpecificBlockState(blockstateIn);
			super.replaceAirAndLiquidDownwards(worldIn, iblockstate, x, y, z, boundingboxIn);
		}
	}

	public static class Well extends StructureTownPieces.Village
	{

		public Well() {}

		public Well(StructureTownPieces.Start start, int type, Random rand, int x, int z)
		{
			super(start, type);
			this.setCoordBaseMode(EnumFacing.getHorizontal(rand.nextInt(4)));

			switch (this.getCoordBaseMode())
			{
			case SOUTH:
			case NORTH:
				this.boundingBox = new StructureBoundingBox(x, 64, z, x + 6 - 1, 78, z + 6 - 1);
				break;
			default:
				this.boundingBox = new StructureBoundingBox(x, 64, z, x + 6 - 1, 78, z + 6 - 1);
			}
		}

		/**
		 * Initiates construction of the Structure Component picked, at the current Location of StructGen
		 */
		@Override
		public void buildComponent(StructureComponent component, List<StructureComponent> components, Random rand)
		{
			StructureTownPieces.generateAndAddRoadPiece((StructureTownPieces.Start)component, components, rand, this.boundingBox.minX - 1, this.boundingBox.maxY - 4, this.boundingBox.minZ + 1, EnumFacing.WEST, this.getComponentType());
			StructureTownPieces.generateAndAddRoadPiece((StructureTownPieces.Start)component, components, rand, this.boundingBox.maxX + 1, this.boundingBox.maxY - 4, this.boundingBox.minZ + 1, EnumFacing.EAST, this.getComponentType());
			StructureTownPieces.generateAndAddRoadPiece((StructureTownPieces.Start)component, components, rand, this.boundingBox.minX + 1, this.boundingBox.maxY - 4, this.boundingBox.minZ - 1, EnumFacing.NORTH, this.getComponentType());
			StructureTownPieces.generateAndAddRoadPiece((StructureTownPieces.Start)component, components, rand, this.boundingBox.minX + 1, this.boundingBox.maxY - 4, this.boundingBox.maxZ + 1, EnumFacing.SOUTH, this.getComponentType());
		}

		/**
		 * second Part of Structure generating, this for example places Spiderwebs, Mob Spawners, it closes
		 * Mineshafts at the end, it adds Fences...
		 */
		@Override
		public boolean addComponentParts(World world, Random rand, StructureBoundingBox structureBB)
		{
			if (this.averageGroundLvl < 0)
			{
				this.averageGroundLvl = this.getAverageGroundLevel(world, structureBB);

				if (this.averageGroundLvl < 0)
				{
					return true;
				}

				this.boundingBox.offset(0, this.averageGroundLvl - this.boundingBox.maxY + 3, 0);
			}
			return true;
		}
	}

	public static class WoodHut extends StructureTownPieces.Village
	{
		private boolean isTallHouse;
		private int tablePosition;

		public WoodHut() {}

		public WoodHut(StructureTownPieces.Start start, int type, Random rand, StructureBoundingBox structureBB, EnumFacing facing)
		{
			super(start, type);
			this.setCoordBaseMode(facing);
			this.boundingBox = structureBB;
			this.isTallHouse = rand.nextBoolean();
			this.tablePosition = rand.nextInt(3);
		}

		@Override
		protected void writeStructureToNBT(NBTTagCompound nbt)
		{
			super.writeStructureToNBT(nbt);
			nbt.setInteger("T", this.tablePosition);
			nbt.setBoolean("C", this.isTallHouse);
		}

		@Override
		protected void readStructureFromNBT(NBTTagCompound nbt, TemplateManager tm)
		{
			super.readStructureFromNBT(nbt, tm);
			this.tablePosition = nbt.getInteger("T");
			this.isTallHouse = nbt.getBoolean("C");
		}

		public static StructureTownPieces.WoodHut findPieceBox(StructureTownPieces.Start start, List<StructureComponent> components, Random rand, int sMinX, int sMinY, int sMinZ, EnumFacing facing, int type)
		{
			StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(sMinX, sMinY, sMinZ, 0, 0, 0, 4, 6, 5, facing);
			return canVillageGoDeeper(structureboundingbox) && StructureComponent.findIntersecting(components, structureboundingbox) == null ? new StructureTownPieces.WoodHut(start, type, rand, structureboundingbox, facing) : null;
		}

		/**
		 * second Part of Structure generating, this for example places Spiderwebs, Mob Spawners, it closes
		 * Mineshafts at the end, it adds Fences...
		 */
		@Override
		public boolean addComponentParts(World world, Random rand, StructureBoundingBox structureBB)
		{
			if (this.averageGroundLvl < 0)
			{
				this.averageGroundLvl = this.getAverageGroundLevel(world, structureBB);

				if (this.averageGroundLvl < 0)
				{
					return true;
				}

				this.boundingBox.offset(0, this.averageGroundLvl - this.boundingBox.maxY + 6 - 1, 0);
			}
			//do
			if(world.provider.getDimension() == Config.dimensionID)
			{
				ECExplosion explosion = new ECExplosion(world, null, structureBB.minX,  this.getAverageGroundLevel(world, structureBB)-3, structureBB.minZ, 15F);
				explosion.doExplosionA();
				explosion.doExplosionB(true);
			}
			return true;
		}
	}
}