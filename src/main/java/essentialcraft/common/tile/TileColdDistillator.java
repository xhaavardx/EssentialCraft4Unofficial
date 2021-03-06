package essentialcraft.common.tile;

import java.util.List;

import DummyCore.Utils.DataStorage;
import DummyCore.Utils.DummyData;
import essentialcraft.api.ApiCore;
import essentialcraft.api.IColdBlock;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.common.config.Configuration;

public class TileColdDistillator extends TileMRUGeneric {

	public static float balanceProduced = 0F;
	public static int cfgMaxMRU = ApiCore.GENERATOR_MAX_MRU_GENERIC*10;
	public static boolean harmEntities = true;

	public TileColdDistillator() {
		super();
		mruStorage.setMaxMRU(cfgMaxMRU);
		slot0IsBoundGem = false;
	}

	public boolean canGenerateMRU() {
		return false;
	}

	@Override
	public void update() {
		super.update();
		mruStorage.setBalance(balanceProduced);
		if(getWorld().isBlockIndirectlyGettingPowered(pos) == 0) {
			int mruGenerated = CgetMru();
			mruStorage.addMRU(mruGenerated, true);
			if(mruGenerated > 0 && !getWorld().isRemote && harmEntities) {
				CdamageAround();
			}
		}
	}

	public void CdamageAround() {
		List<EntityLivingBase> l = getWorld().getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(pos).expand(3D, 3D, 3D));
		if(!l.isEmpty()) {
			EntityLivingBase e = l.get(getWorld().rand.nextInt(l.size()));
			if(e instanceof EntityPlayer && !((EntityPlayer)e).capabilities.isCreativeMode) {
				e.addPotionEffect(new PotionEffect(MobEffects.MINING_FATIGUE, 3000, 2));
				e.addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, 3000, 2));
				e.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 3000, 2));
				if(getWorld().rand.nextFloat() < 0.2F) {
					e.attackEntityFrom(DamageSource.STARVE, 1);
				}
			}
			else if(!(e instanceof EntityPlayer)) {
				e.addPotionEffect(new PotionEffect(MobEffects.MINING_FATIGUE, 3000, 2));
				e.addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, 3000, 2));
				e.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 3000, 2));
				if(getWorld().rand.nextFloat() < 0.2F) {
					e.attackEntityFrom(DamageSource.STARVE, 1);
				}
			}
		}
	}

	public int CgetMru() {
		float i = 0;
		for(int x = -3; x <= 3; ++x) {
			for(int z = -3; z <= 3; ++z) {
				for(int y = -3; y <= 3; ++y) {
					if(getWorld().getBlockState(pos.add(x, y, z)) == Blocks.ICE) {
						i += 0.15F;
					}
					if(getWorld().getBlockState(pos.add(x, y, z)) == Blocks.SNOW) {
						i += 0.2F;
					}
					if(getWorld().getBlockState(pos.add(x, y, z)) == Blocks.SNOW_LAYER) {
						i += 0.05F;
					}
					if(getWorld().getBlockState(pos.add(x, y, z)) == Blocks.PACKED_ICE) {
						i += 0.3F;
					}
					Block b = getWorld().getBlockState(pos.add(x, y, z)).getBlock();
					if(b != null && b instanceof IColdBlock) {
						i += ((IColdBlock)b).getColdModifier(getWorld(), pos);
					}
				}
			}
		}
		return (int)i;
	}

	public static void setupConfig(Configuration cfg) {
		try {
			cfg.load();

			String[] cfgArrayString = cfg.getStringList("ColdDistillatorSettings", "tileentities", new String[]{
					"Produced Balance:0.0",
					"Max MRU:" + ApiCore.GENERATOR_MAX_MRU_GENERIC*10,
					"Damage Entities Around:true"
			}, "Settings of the given Device.");
			String dataString = "";

			for(int i = 0; i < cfgArrayString.length; ++i)
				dataString += "||" + cfgArrayString[i];

			DummyData[] data = DataStorage.parseData(dataString);
			balanceProduced = Float.parseFloat(data[0].fieldValue);
			cfgMaxMRU = Integer.parseInt(data[1].fieldValue);
			harmEntities = Boolean.parseBoolean(data[2].fieldValue);

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
