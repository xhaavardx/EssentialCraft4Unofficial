package ec3.common.world;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

public class ECExplosion extends Explosion
{
    private int field_77289_h = 16;
    private World worldObj;
    private float explosionSize;
    private double explosionX;
    private double explosionY;
    private double explosionZ;
    private Entity exploder;
    private Map<Object, Object> field_77288_k = new HashMap<Object, Object>();
    public ECExplosion(World p_i1948_1_, Entity p_i1948_2_, double p_i1948_3_, double p_i1948_5_, double p_i1948_7_, float p_i1948_9_)
    {
    	super(p_i1948_1_, p_i1948_2_, p_i1948_3_, p_i1948_5_, p_i1948_7_, p_i1948_9_, false, true);
    	this.worldObj = p_i1948_1_;
    	this.exploder = p_i1948_2_;
    	this.explosionX = p_i1948_3_;
    	this.explosionY = p_i1948_5_;
    	this.explosionZ = p_i1948_7_;
    	this.explosionSize = p_i1948_9_;
    }

    /**
     * Does the first part of the explosion (destroy blocks)
     */
    @SuppressWarnings("unchecked")
	public void doExplosionA()
    {
        float f = this.explosionSize;
        HashSet<BlockPos> hashset = new HashSet<BlockPos>();
        int i;
        int j;
        int k;
        double d5;
        double d6;
        double d7;

        for (i = 0; i < this.field_77289_h; ++i)
        {
            for (j = 0; j < this.field_77289_h; ++j)
            {
                for (k = 0; k < this.field_77289_h; ++k)
                {
                    if (i == 0 || i == this.field_77289_h - 1 || j == 0 || j == this.field_77289_h - 1 || k == 0 || k == this.field_77289_h - 1)
                    {
                        double d0 = (double)((float)i / ((float)this.field_77289_h - 1.0F) * 2.0F - 1.0F);
                        double d1 = (double)((float)j / ((float)this.field_77289_h - 1.0F) * 2.0F - 1.0F);
                        double d2 = (double)((float)k / ((float)this.field_77289_h - 1.0F) * 2.0F - 1.0F);
                        double d3 = Math.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
                        d0 /= d3;
                        d1 /= d3;
                        d2 /= d3;
                        float f1 = this.explosionSize * (0.7F + this.worldObj.rand.nextFloat() * 0.6F);
                        d5 = this.explosionX;
                        d6 = this.explosionY;
                        d7 = this.explosionZ;

                        for (float f2 = 0.3F; f1 > 0.0F; f1 -= f2 * 0.75F)
                        {
                            int j1 = MathHelper.floor_double(d5);
                            int k1 = MathHelper.floor_double(d6);
                            int l1 = MathHelper.floor_double(d7);
                            BlockPos pos = new BlockPos(j1,k1,l1);
                            IBlockState block = this.worldObj.getBlockState(pos);

                            if (block.getMaterial() != Material.AIR)
                            {
                                float f3 = this.exploder != null ? this.exploder.getExplosionResistance(this, this.worldObj, pos, block) : block.getBlock().getExplosionResistance(worldObj, pos, this.exploder, this);
                                f1 -= (f3 + 0.3F) * f2;
                            }

                            if (f1 > 0.0F && (this.exploder == null || this.exploder.verifyExplosion(this, this.worldObj, pos, block, f1)))
                            {
                                hashset.add(new BlockPos(j1, k1, l1));
                            }

                            d5 += d0 * (double)f2;
                            d6 += d1 * (double)f2;
                            d7 += d2 * (double)f2;
                        }
                    }
                }
            }
        }

        this.getAffectedBlockPositions().addAll(hashset);
        this.explosionSize *= 2.0F;
        i = MathHelper.floor_double(this.explosionX - (double)this.explosionSize - 1.0D);
        j = MathHelper.floor_double(this.explosionX + (double)this.explosionSize + 1.0D);
        k = MathHelper.floor_double(this.explosionY - (double)this.explosionSize - 1.0D);
        this.explosionSize = f;
    }

    /**
     * Does the second part of the explosion (sound, particles, drop spawn)
     */
    @SuppressWarnings("unchecked")
	public void doExplosionB(boolean p_77279_1_)
    {
        int i;
        int j;
        int k;
        IBlockState block;

        if(true)
        {
            for(BlockPos chunkposition : this.getAffectedBlockPositions())
            {
                i = chunkposition.getX();
                j = chunkposition.getY();
                k = chunkposition.getZ();
                block = this.worldObj.getBlockState(chunkposition);

                if (block.getMaterial() != Material.AIR)
                {
                    if (block.getBlock().canDropFromExplosion(this))
                    {
                        //block.dropBlockAsItemWithChance(this.worldObj, i, j, k, this.worldObj.getBlockMetadata(i, j, k), 1.0F / this.explosionSize, 0);
                    }

                    block.getBlock().onBlockExploded(this.worldObj, chunkposition, this);
                }
            }
        }
    }

    public Map<Object, Object> func_77277_b()
    {
        return this.field_77288_k;
    }

    /**
     * Returns either the entity that placed the explosive block, the entity that caused the explosion or null.
     */
    public EntityLivingBase getExplosivePlacedBy()
    {
        return this.exploder == null ? null : (this.exploder instanceof EntityTNTPrimed ? ((EntityTNTPrimed)this.exploder).getTntPlacedBy() : (this.exploder instanceof EntityLivingBase ? (EntityLivingBase)this.exploder : null));
    }
}