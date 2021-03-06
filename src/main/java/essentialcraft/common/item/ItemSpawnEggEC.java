package essentialcraft.common.item;

import DummyCore.Client.IItemColor;
import DummyCore.Client.IModelRegisterer;
import DummyCore.Client.ModelUtils;
import essentialcraft.common.capabilities.mru.CapabilityMRUHandler;
import essentialcraft.common.entity.EntitiesCore;
import essentialcraft.common.entity.EntityMRUPresence;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemMonsterPlacer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;

public class ItemSpawnEggEC extends ItemMonsterPlacer implements IItemColor, IModelRegisterer {

	public ItemSpawnEggEC() {
		setHasSubtypes(true);
	}

	@Override
	public int getColorFromItemstack(ItemStack p_82790_1_, int p_82790_2_) {
		return 0xffffff;
	}

	@Override
	public EnumActionResult onItemUseFirst(EntityPlayer p_77648_2_, World p_77648_3_, BlockPos p_77648_4_, EnumFacing p_77648_7_, float p_77648_8_, float p_77648_9_, float p_77648_10_, EnumHand hand) {
		ItemStack p_77648_1_ = p_77648_2_.getHeldItem(hand);

		if(!p_77648_2_.capabilities.isCreativeMode) {
			p_77648_1_.shrink(1);
		}

		if(p_77648_3_.isRemote) {
			return EnumActionResult.SUCCESS;
		}
		else {
			Block block = p_77648_3_.getBlockState(p_77648_4_).getBlock();
			p_77648_4_ = p_77648_4_.offset(p_77648_7_);
			double d0 = 0.0D;

			if(p_77648_7_ == EnumFacing.UP) {
				d0 = block.getCollisionBoundingBox(p_77648_3_.getBlockState(p_77648_4_.down()), p_77648_3_, p_77648_4_.down()).maxY - 1;
			}

			Entity entity = spawnCreature(p_77648_3_, p_77648_1_.getItemDamage(), p_77648_4_.getX() + 0.5D, p_77648_4_.getY()+ d0, p_77648_4_.getZ() + 0.5D);

			if(entity != null) {
				if(entity instanceof EntityLivingBase && p_77648_1_.hasDisplayName()) {
					((EntityLiving)entity).setCustomNameTag(p_77648_1_.getDisplayName());
				}
			}

			return EnumActionResult.SUCCESS;
		}
	}

	@Override
	public String getItemStackDisplayName(ItemStack p_77653_1_)
	{
		String s = ("" + I18n.translateToLocal(this.getUnlocalizedName() + ".name")).trim();
		String s1 = EntitiesCore.registeredEntities.get(p_77653_1_.getItemDamage()).getName();

		if (s1 != null)
		{
			s = s + " " + I18n.translateToLocal("entity." + s1 + ".name");
		}

		return s;
	}

	@Override
	public void getSubItems(CreativeTabs t, NonNullList<ItemStack> l)
	{
		if(this.isInCreativeTab(t))
			for(int j = 0; j < EntitiesCore.registeredEntities.size(); ++j)
			{
				l.add(new ItemStack(this,1,j));
			}
	}

	public static Entity spawnCreature(World p_77840_0_, int p_77840_1_, double p_77840_2_, double p_77840_4_, double p_77840_6_) {
		try {
			Entity entity = null;

			for(int j = 0; j < 1; ++j) {
				entity = EntitiesCore.registeredEntities.get(p_77840_1_).newInstance(p_77840_0_);

				if(entity != null && entity instanceof EntityLivingBase) {
					EntityLivingBase entityliving = (EntityLivingBase)entity;
					entity.setLocationAndAngles(p_77840_2_, p_77840_4_, p_77840_6_, MathHelper.wrapDegrees(p_77840_0_.rand.nextFloat() * 360.0F), 0.0F);
					entityliving.rotationYawHead = entityliving.rotationYaw;
					entityliving.renderYawOffset = entityliving.rotationYaw;
					if(entity instanceof EntityLiving)
						((EntityLiving)entityliving).onInitialSpawn(p_77840_0_.getDifficultyForLocation(new BlockPos(p_77840_2_, p_77840_4_, p_77840_6_)), (IEntityLivingData)null);
					p_77840_0_.spawnEntity(entity);
					if(entity instanceof EntityLiving)
						((EntityLiving)entityliving).playLivingSound();
					if(entity instanceof EntityMRUPresence)
						entity.getCapability(CapabilityMRUHandler.MRU_HANDLER_CAPABILITY, null).setMRU(500);
				}
			}

			return entity;
		}
		catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public void registerModels() {
		ModelUtils.setItemModelSingleIcon(this, "essentialcraft:item/fruit_item");
	}
}
