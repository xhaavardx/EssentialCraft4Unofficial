package essentialcraft.common.world.gen;

import java.util.Random;

import DummyCore.Utils.WeightedRandomChestContent;
import essentialcraft.common.block.BlocksCore;
import essentialcraft.common.item.ItemBaublesResistance;
import essentialcraft.common.item.ItemsCore;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenMRUTower extends WorldGenerator {


	public WorldGenMRUTower()
	{
	}
	public static final WeightedRandomChestContent[] generatedItems = {
			new WeightedRandomChestContent(ItemsCore.titanite, 0, 8, 64, 20),
			new WeightedRandomChestContent(ItemsCore.twinkling_titanite, 0, 2, 16, 10),
			new WeightedRandomChestContent(ItemsCore.genericItem, 5, 1, 64, 15),
			new WeightedRandomChestContent(ItemsCore.genericItem, 6, 1, 64, 15),
			new WeightedRandomChestContent(ItemsCore.genericItem, 7, 1, 64, 15),
			new WeightedRandomChestContent(ItemsCore.genericItem, 8, 1, 64, 15),
			new WeightedRandomChestContent(ItemsCore.genericItem, 9, 1, 64, 15),
			new WeightedRandomChestContent(ItemsCore.genericItem, 10, 1, 64, 15),
			new WeightedRandomChestContent(ItemsCore.genericItem, 11, 1, 64, 15),
			new WeightedRandomChestContent(ItemsCore.genericItem, 20, 1, 12, 10),
			new WeightedRandomChestContent(ItemsCore.genericItem, 3, 1, 64, 15),
			new WeightedRandomChestContent(ItemsCore.genericItem, 35, 1, 1, 6),
			new WeightedRandomChestContent(ItemsCore.genericItem, 36, 1, 1, 6),
			new WeightedRandomChestContent(ItemsCore.genericItem, 37, 1, 1, 6)
	};

	public int getGroundToGenerate(World w, int x, int y, int z)
	{
		while(y > 5)
		{
			BlockPos p = new BlockPos(x,y,z);
			if(!w.isAirBlock(p) && w.getBlockState(p).getBlock() != Blocks.WATER)
			{
				if(w.getBlockState(p).getBlock() != BlocksCore.concrete && w.getBlockState(p).getBlock() != BlocksCore.fortifiedStone)
				{
					break;
				}else
				{
					return -1;
				}
			}
			--y;
		}
		if(y == 5)
			return -1;
		return y;
	}

	@Override
	public boolean generate(World w, Random r, BlockPos p)
	{
		int genY = getGroundToGenerate(w,p.getX(),p.getY(),p.getZ());
		if(genY != -1)
		{
			int y = genY;
			for(int i = 0; i < 4; ++i)
			{
				generateFloor(w,p.getX(),y+16*i,p.getZ(),4-i);
			}
			return true;
		}
		return false;
	}

	public void generateFloor(World w, int x, int y, int z, int rad)
	{
		for(int dx = 0; dx <= rad*2; ++dx)
		{
			for(int dz = 0; dz <= rad*2; ++dz)
			{
				for(int dy = 0; dy < 16; ++dy)
				{
					BlockPos p = new BlockPos(x+dx, y+dy, z+dz);
					if(dx == rad+1 && dz == rad+1 || dx == rad-1 && dz == rad-1 || dx == rad+1 && dz == rad-1 || dx == rad-1 && dz == rad+1)
					{
						if(w.getBlockState(p).getBlock() != Blocks.CHEST)
							w.setBlockState(p, BlocksCore.fence[1].getDefaultState());
					}
					if((dx == rad*2 || dx == 0 || dz == 0 || dz == rad*2) && dy == 8)
					{
						if(w.getBlockState(p).getBlock() != Blocks.CHEST)
							w.setBlockState(p, BlocksCore.fortifiedStone.getDefaultState());
						if(rad == 1 && (dx == rad || dz == rad))
						{
							if(w.getBlockState(p.up()).getBlock() != Blocks.CHEST)
							{
								w.setBlockState(p.up(), Blocks.CHEST.getDefaultState());
								TileEntityChest chest = (TileEntityChest)w.getTileEntity(p.up());
								if (chest != null)
								{
									WeightedRandomChestContent.generateChestContents(w.rand, WorldGenOldCatacombs.generatedItems, chest, w.rand.nextInt(12)+6);
									IInventory inv = chest;
									for(int i = 0; i < inv.getSizeInventory(); ++i)
									{
										ItemStack stk = inv.getStackInSlot(i);
										if(stk.getItem() instanceof ItemBaublesResistance)
										{
											ItemBaublesResistance.initRandomTag(stk, w.rand);
										}
									}
								}
							}
						}
					}
					if((dx == 0 || dx == rad*2) && (dz == 0 || dz == rad*2))
					{
						if(w.getBlockState(p).getBlock() != Blocks.CHEST)
							w.setBlockState(p, BlocksCore.fence[2].getDefaultState());
					}
					if(rad > 1 && dx == 0 && dz == 0)
					{
						w.setBlockState(p.east(), BlocksCore.fortifiedStone.getDefaultState());
						w.setBlockState(p.south(), BlocksCore.fortifiedStone.getDefaultState());
						if(dy == 15)
							w.setBlockState(p.add(1, 0, 1), BlocksCore.fortifiedStone.getDefaultState());
					}
					if(rad > 1 && dx == rad*2 && dz == 0)
					{
						w.setBlockState(p.west(), BlocksCore.fortifiedStone.getDefaultState());
						w.setBlockState(p.south(), BlocksCore.fortifiedStone.getDefaultState());
						if(dy == 15)
							w.setBlockState(p.add(-1, 0, 1), BlocksCore.fortifiedStone.getDefaultState());
					}
					if(rad > 1 && dx == 0 && dz == rad*2)
					{
						w.setBlockState(p.east(), BlocksCore.fortifiedStone.getDefaultState());
						w.setBlockState(p.north(), BlocksCore.fortifiedStone.getDefaultState());
						if(dy == 15)
							w.setBlockState(p.add(1, 0, -1), BlocksCore.fortifiedStone.getDefaultState());
					}
					if(rad > 1 && dx == rad*2 && dz == rad*2)
					{
						w.setBlockState(p.west(), BlocksCore.fortifiedStone.getDefaultState());
						w.setBlockState(p.north(), BlocksCore.fortifiedStone.getDefaultState());
						if(dy == 15)
							w.setBlockState(p.add(-1, -1, -1), BlocksCore.fortifiedStone.getDefaultState());
					}
				}
			}
		}
	}
}
