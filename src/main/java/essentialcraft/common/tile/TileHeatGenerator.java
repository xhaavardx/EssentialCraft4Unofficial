package essentialcraft.common.tile;

import DummyCore.Utils.DataStorage;
import DummyCore.Utils.DummyData;
import DummyCore.Utils.MathUtils;
import essentialcraft.api.ApiCore;
import essentialcraft.api.IHotBlock;
import essentialcraft.common.item.ItemsCore;
import essentialcraft.common.mod.EssentialCraftCore;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.config.Configuration;

public class TileHeatGenerator extends TileMRUGeneric {

	public int currentBurnTime, currentMaxBurnTime;
	public static int cfgMaxMRU = ApiCore.GENERATOR_MAX_MRU_GENERIC;
	public static float cfgBalance = -1F;
	public static int mruGenerated = 20;

	private boolean firstTick = true;

	public TileHeatGenerator() {
		super();
		mruStorage.setMaxMRU(cfgMaxMRU);
		slot0IsBoundGem = false;
		setSlotsNum(2);
	}

	@Override
	public void update() {
		float mruGen = mruGenerated;
		if(!getWorld().isRemote && cfgBalance == -1F && firstTick) {
			mruStorage.setBalance(getWorld().rand.nextFloat()*2);
		}
		super.update();
		firstTick = false;
		if(getWorld().isBlockIndirectlyGettingPowered(pos) == 0) {
			if(currentBurnTime > 0) {
				--currentBurnTime;
				float mruFactor = 1.0F;
				Block[] b = new Block[4];
				b[0] = getWorld().getBlockState(pos.east(2)).getBlock();
				b[1] = getWorld().getBlockState(pos.west(2)).getBlock();
				b[2] = getWorld().getBlockState(pos.south(2)).getBlock();
				b[3] = getWorld().getBlockState(pos.north(2)).getBlock();
				int[] ox = {2, -2, 0, 0};
				int[] oz = {0, 0, 2, -2};
				for(int i = 0; i < 4; ++i) {
					if(b[i] == Blocks.AIR)
						mruFactor *= 0;
					else if(b[i] == Blocks.NETHERRACK)
						mruFactor *= 0.75F;
					else if(b[i] == Blocks.LAVA)
						mruFactor *= 0.95F;
					else if(b[i] == Blocks.FIRE)
						mruFactor *= 0.7F;
					else if(b[i] instanceof IHotBlock)
						mruFactor *= ((IHotBlock)b[i]).getHeatModifier(getWorld(), pos.add(ox[i], 0, oz[i]));
					else
						mruFactor *= 0.5F;
				}

				mruGen *= mruFactor;
				if(mruGen >= 1) {
					mruStorage.addMRU((int)mruGen, true);
				}
			}

			if(!getStackInSlot(0).isEmpty()) {
				if(currentBurnTime == 0 && mruStorage.getMRU() < mruStorage.getMaxMRU()) {
					currentMaxBurnTime = currentBurnTime = TileEntityFurnace.getItemBurnTime(getStackInSlot(0));

					if(currentBurnTime > 0) {
						if(!getStackInSlot(0).isEmpty()) {
							if(getStackInSlot(1).isEmpty() || getStackInSlot(1).getCount() < getInventoryStackLimit()) {
								if(getStackInSlot(1).getItem() == ItemsCore.magicalSlag) {
									ItemStack stk = getStackInSlot(1);
									stk.grow(1);
									setInventorySlotContents(1, stk);
								}
								if(getStackInSlot(1).isEmpty()) {
									ItemStack stk = new ItemStack(ItemsCore.magicalSlag,1,0);
									setInventorySlotContents(1, stk);
								}
							}
							if(getStackInSlot(0).getCount() == 0) {
								setInventorySlotContents(0, getStackInSlot(0).getItem().getContainerItem(getStackInSlot(0)));
							}
							decrStackSize(0, 1);
						}
					}
				}
			}
		}
		if(getWorld().isRemote) {
			for(int i = 2; i < 6; ++i) {
				EnumFacing rotation = EnumFacing.getFront(i);
				float rotXAdv = rotation.getFrontOffsetX()-0.5F;
				float rotZAdv = rotation.getFrontOffsetZ()-0.5F;
				EssentialCraftCore.proxy.FlameFX(pos.getX()+0.725F + rotXAdv/2.2F, pos.getY()+0.4F, pos.getZ()+0.725F + rotZAdv/2.2F, 0, 0F, 0, 0.8D, 0.5D, 0.5F, 0.5F);
				EssentialCraftCore.proxy.FlameFX(pos.getX()+0.5F + MathUtils.randomFloat(getWorld().rand)*0.2F, pos.getY()+0.65F, pos.getZ()+0.5F + MathUtils.randomFloat(getWorld().rand)*0.2F, 0, 0.01F, 0, 0.8D, 0.5D, 0.5F, 1F);
			}
			EssentialCraftCore.proxy.SmokeFX(pos.getX()+0.5F + MathUtils.randomFloat(getWorld().rand)*0.05F, pos.getY()+0.8F, pos.getZ()+0.5F + MathUtils.randomFloat(getWorld().rand)*0.05F, 0, 0, 0, 1);
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound i) {
		currentBurnTime = i.getInteger("burn");
		currentMaxBurnTime = i.getInteger("burnMax");
		super.readFromNBT(i);
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound i) {
		i.setInteger("burn", currentBurnTime);
		i.setInteger("burnMax", currentMaxBurnTime);
		return super.writeToNBT(i);
	}

	public static void setupConfig(Configuration cfg) {
		try {
			cfg.load();
			String[] cfgArrayString = cfg.getStringList("HeatGeneratorSettings", "tileentities", new String[] {
					"Max MRU:"+ApiCore.GENERATOR_MAX_MRU_GENERIC,
					"Default balance(-1 is random):-1.0",
					"Max MRU generated per tick:20"
			}, "");
			String dataString = "";

			for(int i = 0; i < cfgArrayString.length; ++i)
				dataString += "||" + cfgArrayString[i];

			DummyData[] data = DataStorage.parseData(dataString);

			cfgMaxMRU = Integer.parseInt(data[0].fieldValue);
			cfgBalance = Float.parseFloat(data[1].fieldValue);
			mruGenerated = Integer.parseInt(data[2].fieldValue);

			cfg.save();
		}
		catch(Exception e) {
			return;
		}
	}

	@Override
	public int[] getOutputSlots() {
		return new int[] {1};
	}

	@Override
	public boolean isItemValidForSlot(int p_94041_1_, ItemStack p_94041_2_) {
		return p_94041_1_ == 0;
	}
}
