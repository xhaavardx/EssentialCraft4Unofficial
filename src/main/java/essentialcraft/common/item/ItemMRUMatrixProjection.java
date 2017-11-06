package essentialcraft.common.item;

import java.util.List;

import DummyCore.Client.IModelRegisterer;
import DummyCore.Utils.MiscUtils;
import essentialcraft.utils.common.ECUtils;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemMRUMatrixProjection extends Item implements IModelRegisterer {

	public static String[] names = {"empty","chaos","frozen","magic","shade"};

	public ItemMRUMatrixProjection() {
		super();
		this.setHasSubtypes(true);
		this.setMaxDamage(0);
	}

	@Override
	public String getUnlocalizedName(ItemStack p_77667_1_)
	{
		return super.getUnlocalizedName(p_77667_1_)+"_"+names[Math.min(p_77667_1_.getItemDamage(),names.length-1)];
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World par2World, EntityPlayer par3EntityPlayer, EnumHand hand)
	{
		ItemStack par1ItemStack = par3EntityPlayer.getHeldItem(hand);
		if(par1ItemStack.getTagCompound() != null)
		{
			String username = par1ItemStack.getTagCompound().getString("playerName");
			if(username.equals(MiscUtils.getUUIDFromPlayer(par3EntityPlayer).toString()))
				par3EntityPlayer.setActiveHand(hand);
		}
		if(par1ItemStack.getTagCompound() == null && !par2World.isRemote && !par3EntityPlayer.isSneaking())
		{
			NBTTagCompound playerTag = new NBTTagCompound();
			playerTag.setString("playerName", MiscUtils.getUUIDFromPlayer(par3EntityPlayer).toString());
			par1ItemStack.setTagCompound(playerTag);
		}
		return super.onItemRightClick(par2World, par3EntityPlayer, hand);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack par1ItemStack, World par2EntityPlayer, List<String> par3List, ITooltipFlag par4)
	{
		if(par1ItemStack.getTagCompound() != null)
		{
			String username = MiscUtils.getUsernameFromUUID(par1ItemStack.getTagCompound().getString("playerName"));
			par3List.add(TextFormatting.DARK_GRAY+"Thus is a projection of "+TextFormatting.GOLD+username+TextFormatting.DARK_GRAY+"'s MRU Matrix");
		}
	}

	@Override
	public void getSubItems(CreativeTabs p_150895_2_, NonNullList<ItemStack> p_150895_3_)
	{
		if(this.isInCreativeTab(p_150895_2_))
			for(int i = 0; i < 5; ++i)
			{
				p_150895_3_.add(new ItemStack(this,1,i));
			}
	}

	@Override
	public EnumAction getItemUseAction(ItemStack p_77661_1_)
	{
		return EnumAction.BLOCK;
	}

	@Override
	public int getMaxItemUseDuration(ItemStack p_77626_1_)
	{
		return 200;
	}

	@Override
	public void onUsingTick(ItemStack stack, EntityLivingBase player, int count)
	{
		if(count % 40 == 0)
		{
			player.playSound(SoundEvents.BLOCK_PORTAL_TRAVEL, 0.3F, 2);
			player.playSound(SoundEvents.BLOCK_PORTAL_TRIGGER, 0.3F, 2);
		}
		if(count == 100)
			player.addPotionEffect(new PotionEffect(MobEffects.NAUSEA,200,0,true,true));
		if(count <= 50)
		{
			player.addPotionEffect(new PotionEffect(MobEffects.BLINDNESS,2000,0,true,true));
		}

	}

	@Override
	public ItemStack onItemUseFinish(ItemStack p_77654_1_, World p_77654_2_, EntityLivingBase p_77654_3_)
	{
		if(p_77654_3_ instanceof EntityPlayer) {
			p_77654_3_.playSound(SoundEvents.BLOCK_PORTAL_TRIGGER, 0.3F, 0.01F);
			p_77654_3_.addPotionEffect(new PotionEffect(MobEffects.WITHER,40*19,0,true,true));
			p_77654_3_.addPotionEffect(new PotionEffect(MobEffects.WEAKNESS,40*19,4,true,true));
			p_77654_3_.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS,40*19,4,true,true));
			p_77654_3_.addPotionEffect(new PotionEffect(MobEffects.MINING_FATIGUE,40*19,4,true,true));
			p_77654_3_.addPotionEffect(new PotionEffect(MobEffects.HUNGER,40*19,0,true,true));
			if(!p_77654_3_.getEntityWorld().isRemote)
			{
				p_77654_3_.sendMessage(new TextComponentString(TextFormatting.AQUA+"Your MRU Matrix twists with new colors!"));
				ECUtils.getData((EntityPlayer)p_77654_3_).modifyMatrixType(p_77654_1_.getItemDamage());
			}
			((EntityPlayer)p_77654_3_).inventory.decrStackSize(((EntityPlayer)p_77654_3_).inventory.currentItem, 1);
		}
		return p_77654_1_;
	}

	@Override
	public void registerModels() {
		for(int i = 0; i < names.length; i++) {
			ModelLoader.setCustomModelResourceLocation(this, i, new ModelResourceLocation("essentialcraft:item/mrumatrixprojection", "type=" + names[i]));
		}
	}
}
