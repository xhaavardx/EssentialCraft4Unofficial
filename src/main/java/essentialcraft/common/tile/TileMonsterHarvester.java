package essentialcraft.common.tile;

import java.util.List;

import DummyCore.Utils.DataStorage;
import DummyCore.Utils.DummyData;
import DummyCore.Utils.MiscUtils;
import essentialcraft.api.ApiCore;
import essentialcraft.utils.common.ECUtils;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.util.FakePlayer;

public class TileMonsterHarvester extends TileMRUGeneric {
	public static float rad = 12F;
	public float rotation = 0F;
	public int destrTick;
	public static int cfgMaxMRU = ApiCore.DEVICE_MAX_MRU_GENERIC;
	public static boolean generatesCorruption = false;
	public static int genCorruption = 10;
	public static int mruUsage = 100;
	public static int mobDestructionTimer = 1440;
	public static boolean allowBossDuplication = false;
	public static boolean clearCopyInventory = true;

	public TileMonsterHarvester() {
		super();
		mruStorage.setMaxMRU(cfgMaxMRU);
		setSlotsNum(6);
	}

	@Override
	public void update() {
		super.update();
		mruStorage.update(getPos(), getWorld(), getStackInSlot(0));
		if(getWorld().isBlockIndirectlyGettingPowered(pos) == 0) {
			++destrTick;
			if(destrTick >= mobDestructionTimer) {
				destrTick = 0;
				List<EntityLivingBase> lst = getWorld().getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(pos.getX()-16, pos.getY()-16, pos.getZ()-16, pos.getX()+17, pos.getY()+17, pos.getZ()+17));
				if(!lst.isEmpty() && !getWorld().isRemote) {
					for(int i = 0; i < lst.size(); ++i) {
						EntityLivingBase e = lst.get(i);
						if(!(e instanceof EntityPlayer)) {
							if(mruStorage.getMRU() >= mruUsage) {
								if(!e.isNonBoss() && !allowBossDuplication)
									return;
								mruStorage.extractMRU(mruUsage, true);

								if(!world.isRemote) {
									EntityLivingBase copy = (EntityLivingBase)MiscUtils.cloneEntity(e);
									getWorld().spawnEntity(copy);
									if(clearCopyInventory) {
										copy.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, ItemStack.EMPTY);
										copy.setItemStackToSlot(EntityEquipmentSlot.OFFHAND, ItemStack.EMPTY);
										copy.setItemStackToSlot(EntityEquipmentSlot.HEAD, ItemStack.EMPTY);
										copy.setItemStackToSlot(EntityEquipmentSlot.CHEST, ItemStack.EMPTY);
										copy.setItemStackToSlot(EntityEquipmentSlot.LEGS, ItemStack.EMPTY);
										copy.setItemStackToSlot(EntityEquipmentSlot.FEET, ItemStack.EMPTY);
									}
									FakePlayer player = new FakePlayer((WorldServer)e.world, ECUtils.EC3FakePlayerProfile);
									ItemStack stk = getStackInSlot(2);
									if(!stk.isEmpty())
										player.inventory.setInventorySlotContents(player.inventory.currentItem, stk.copy());
									copy.setHealth(0.01F);
									player.attackTargetEntityWithCurrentItem(copy);
									player.setDead();
									if(copy.getHealth() > 0)
										copy.setDead();
									if(generatesCorruption)
										ECUtils.increaseCorruptionAt(getWorld(), pos, getWorld().rand.nextInt(genCorruption));
								}
							}
						}
					}
				}
			}
		}
	}

	public static void setupConfig(Configuration cfg) {
		try {
			cfg.load();
			String[] cfgArrayString = cfg.getStringList("MonsterDuplicatorSettings", "tileentities", new String[] {
					"Max MRU:" + ApiCore.DEVICE_MAX_MRU_GENERIC,
					"MRU Usage Per Mob:100",
					"Can this device actually generate corruption:false",
					"The amount of corruption generated each tick(do not set to 0!):10",
					"Radius to duplicate mobs within:12.0",
					"Ticks required to duplicate mobs:1440",
					"Allow duplication of bosses:false",
					"Remove inventory of a duplicate before killing it:true"
			}, "");
			String dataString = "";

			for(int i = 0; i < cfgArrayString.length; ++i)
				dataString += "||" + cfgArrayString[i];

			DummyData[] data = DataStorage.parseData(dataString);

			mruUsage = Integer.parseInt(data[1].fieldValue);
			cfgMaxMRU = Integer.parseInt(data[0].fieldValue);
			generatesCorruption = Boolean.parseBoolean(data[2].fieldValue);
			genCorruption = Integer.parseInt(data[3].fieldValue);
			rad = Float.parseFloat(data[4].fieldValue);
			mobDestructionTimer = Integer.parseInt(data[5].fieldValue);
			allowBossDuplication = Boolean.parseBoolean(data[6].fieldValue);
			clearCopyInventory = Boolean.parseBoolean(data[7].fieldValue);

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
