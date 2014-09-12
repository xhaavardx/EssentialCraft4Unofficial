package ec3.common.tile;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import ec3.api.ApiCore;
import ec3.api.IColdBlock;
import ec3.common.entity.EntitySolarBeam;

public class TileColdDistillator extends TileMRUGeneric{
	
	public TileColdDistillator()
	{
		this.maxMRU = (int) ApiCore.GENERATOR_MAX_MRU_GENERIC*10;
	}
	
	public boolean canGenerateMRU()
	{
		return false;
	}
	
	@Override
	public void updateEntity()
	{
		super.updateEntity();
		this.balance = 0.0F;
		if(!this.worldObj.isRemote)
		{
			int mruGenerated = CgetMru();
			this.setMRU((int) (this.getMRU()+mruGenerated));
			if(this.getMRU() > this.getMaxMRU())
				this.setMRU(this.getMaxMRU());
			CdamageAround();
		}
	}
	
    
    public void CdamageAround()
    {
    	List<EntityLivingBase> l = this.worldObj.getEntitiesWithinAABB(EntityLivingBase.class, AxisAlignedBB.getBoundingBox(xCoord, yCoord, zCoord, xCoord+1, yCoord+1, zCoord+1).expand(3D, 3D, 3D));
    	if(!l.isEmpty())
    	{
    		EntityLivingBase e = l.get(this.worldObj.rand.nextInt(l.size()));
    		if(e instanceof EntityPlayer && !((EntityPlayer)e).capabilities.isCreativeMode)
    		{
    			e.addPotionEffect(new PotionEffect(Potion.digSlowdown.id,3000,2));
    			e.addPotionEffect(new PotionEffect(Potion.weakness.id,3000,2));
    			e.addPotionEffect(new PotionEffect(Potion.moveSlowdown.id,3000,2));
    			if(e.worldObj.rand.nextFloat() < 0.2F && !e.worldObj.isRemote)
    			{
    				e.attackEntityFrom(DamageSource.starve, 1);
    			}
    		}else if(!(e instanceof EntityPlayer))
    		{
    			e.addPotionEffect(new PotionEffect(Potion.digSlowdown.id,3000,2));
    			e.addPotionEffect(new PotionEffect(Potion.weakness.id,3000,2));
    			e.addPotionEffect(new PotionEffect(Potion.moveSlowdown.id,3000,2));
    			if(e.worldObj.rand.nextFloat() < 0.2F && !e.worldObj.isRemote)
    			{
    				e.attackEntityFrom(DamageSource.starve, 1);
    			}
    		}
    	}
    }
	
    public int CgetMru()
    {
    	float i = 0;
		for(int x = -3; x <= 3; ++x)
		{
    		for(int z = -3; z <= 3; ++z)
    		{
        		for(int y = -3; y <= 3; ++y)
        		{
        			if(this.worldObj.getBlock(xCoord+x, yCoord+y, zCoord+z) == Blocks.ice)
        			{
        				i += 0.15F;
        			}
        			if(this.worldObj.getBlock(xCoord+x, yCoord+y, zCoord+z) == Blocks.snow)
        			{
        				i += 0.2F;
        			}
           			if(this.worldObj.getBlock(xCoord+x, yCoord+y, zCoord+z) == Blocks.snow_layer)
        			{
        				i += 0.05F;
        			}
         			if(this.worldObj.getBlock(xCoord+x, yCoord+y, zCoord+z) == Blocks.packed_ice)
        			{
        				i += 0.3F;
        			}
        			Block b = this.worldObj.getBlock(xCoord+x, yCoord+y, zCoord+z);
        			if(b != null && b instanceof IColdBlock)
        			{
        				i += ((IColdBlock)b).getColdModifier(this.worldObj.getBlockMetadata(xCoord+x, yCoord+y, zCoord+z));
        			}
        		}
    		}
		}
    	return (int) i;
    }

}
