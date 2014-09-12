package ec3.common.item;

import java.util.List;

import ec3.utils.common.ECUtils;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class ItemMagicalShield extends ItemStoresMRUInNBT {

	public ItemMagicalShield() {
		super();	
		this.setMaxMRU(5000);
		this.maxStackSize = 1;
		this.bFull3D = true;
	}
	
    public void onUsingTick(ItemStack stack, EntityPlayer player, int count)
    {
    	player.hurtResistantTime = 20;
    }

    /**
     * How long it takes to use or consume an item
     */
    public int getMaxItemUseDuration(ItemStack p_77626_1_)
    {
        return 40;
    }

    /**
     * returns the action that specifies what animation to play when the items is being used
     */
    public EnumAction getItemUseAction(ItemStack p_77661_1_)
    {
        return EnumAction.block;
    }

    /**
     * Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer
     */
    public ItemStack onItemRightClick(ItemStack p_77659_1_, World p_77659_2_, EntityPlayer p_77659_3_)
    {
        if((ECUtils.tryToDecreaseMRUInStorage(p_77659_3_, -50*40) || this.setMRU(p_77659_1_, -50*40)))
        {
        	p_77659_3_.setItemInUse(p_77659_1_, this.getMaxItemUseDuration(p_77659_1_));
        }
        return p_77659_1_;
    }

}
