package essentialcraft.common.item;

import DummyCore.Client.IModelRegisterer;
import essentialcraft.utils.common.ECUtils;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.monster.EntityZombieVillager;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;

public class ItemLifeStaff extends ItemStoresMRUInNBT implements IModelRegisterer {

	public ItemLifeStaff() {
		super();
		this.setMaxMRU(5000);
		this.maxStackSize = 1;
		this.bFull3D = true;
	}

	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ)
	{
		ItemStack stack = player.getHeldItem(hand);
		if(ECUtils.tryToDecreaseMRUInStorage(player, -100) || this.increaseMRU(stack, -100))
		{
			if(ItemDye.applyBonemeal(new ItemStack(stack.getItem(),stack.getItemDamage(),stack.getCount()+1), world, pos, player, hand))
			{
				for(int px = -5; px <= 5; ++px)
				{
					for(int pz = -5; pz <= 5; ++pz)
					{
						if(this.getMRU(stack) > 10)

							if(ItemDye.applyBonemeal(new ItemStack(stack.getItem(),stack.getItemDamage(),stack.getCount()+1), world, pos.add(px, 0, pz), player, hand))
							{
								if(!ECUtils.tryToDecreaseMRUInStorage(player, -100))this.increaseMRU(stack, -100);
							}
					}
				}
			}
		}
		return EnumActionResult.PASS;
	}

	@Override
	public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity)
	{
		if(entity instanceof EntityZombieVillager)
		{
			EntityZombieVillager e = (EntityZombieVillager)entity;
			if((ECUtils.tryToDecreaseMRUInStorage(player, -500) || this.increaseMRU(stack, -500)) && !e.getEntityWorld().isRemote)
			{
				EntityVillager entityvillager = new EntityVillager(e.getEntityWorld());
				entityvillager.copyLocationAndAnglesFrom(e);
				entityvillager.onInitialSpawn(entity.getEntityWorld().getDifficultyForLocation(entity.getPosition()), (IEntityLivingData)null);
				if (e.isChild())
				{
					entityvillager.setGrowingAge(-24000);
				}

				e.getEntityWorld().removeEntity(e);
				e.getEntityWorld().spawnEntity(entityvillager);
				entityvillager.addPotionEffect(new PotionEffect(MobEffects.NAUSEA, 200, 0));
				e.getEntityWorld().playEvent((EntityPlayer)null, 1017, e.getPosition(), 0);
			}
			return true;
		}
		if(entity instanceof EntityAgeable)
		{
			EntityAgeable e = (EntityAgeable) entity;
			if(e.isChild() && !e.getEntityWorld().isRemote && (ECUtils.tryToDecreaseMRUInStorage(player, -100) || this.increaseMRU(stack, -100)))
			{
				e.setGrowingAge(0);
			}
		}
		return true;
	}

	@Override
	public void registerModels() {
		ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation("essentialcraft:item/staffoflife", "inventory"));
	}
}
