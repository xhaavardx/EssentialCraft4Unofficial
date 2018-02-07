package essentialcraft.common.item;

import java.util.List;

import DummyCore.Client.IModelRegisterer;
import essentialcraft.common.mod.EssentialCraftCore;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.TempCategory;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemSecret extends Item implements IModelRegisterer {
	public static String[] dropNames = {"410_ticket", "d6", "ironwood_branch", "mysterious_stick", "smoothandsilkystone", "strange_figure", "strange_symbol", "the_true_unknown"};

	public ItemSecret() {
		setMaxDamage(0);
		setHasSubtypes(true);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack par1ItemStack, World world, List<String> par3List, ITooltipFlag par4) {
		super.addInformation(par1ItemStack, world, par3List, par4);
		int metadata = par1ItemStack.getItemDamage();
		switch(metadata) {
		case 0: {
			for(int i = 0; i < 5; ++i)
				par3List.add(I18n.translateToLocal("essentialcraft.text.desc.secret_"+metadata+"_"+i));
			break;
		}
		case 1: {
			for(int i = 0; i < 4; ++i)
				par3List.add(I18n.translateToLocal("essentialcraft.text.desc.secret_"+metadata+"_"+i));
			break;
		}
		case 2: {
			par3List.add("The branch seems to be made of "+ TextFormatting.WHITE+"iron");
			par3List.add("However it is clearly a branch of a tree");
			par3List.add("You feel better while holding it");
			par3List.add("Maybe it can improve your "+TextFormatting.AQUA+"spells?");
			break;
		}
		case 3: {
			par3List.add("This seems to be a regular stick");
			par3List.add("But it gives you a strange feel of power");
			par3List.add("You only know one thing");
			par3List.add("Whoever controls the stick controls the "+TextFormatting.DARK_AQUA+"universe...");
			break;
		}
		case 4: {
			par3List.add("This stone is too smooth");
			par3List.add("It makes you feel better");
			par3List.add("It is also very silky");
			par3List.add("Maybe some kind of "+TextFormatting.DARK_GREEN+"bird"+TextFormatting.GRAY+" would like it?");
			break;
		}
		case 5: {
			par3List.add("This is a very strange figure");
			par3List.add("It seems to be from the future");
			par3List.add("You can't do anything with it");
			par3List.add("But it seems a bit "+TextFormatting.DARK_GRAY+"damaged...");
			break;
		}
		case 6: {
			par3List.add("This is a very strange symbol");
			par3List.add("It seems to be an image of something");
			par3List.add("When you look at it you want to glory something");
			par3List.add("There are letters on the bottom that say "+TextFormatting.LIGHT_PURPLE+"EZIC");
			break;
		}
		}
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
		ItemStack par1ItemStack = player.getHeldItem(hand);
		int metadata = par1ItemStack.getItemDamage();
		switch(metadata) {
		case 0: {
			World wrld = player.getEntityWorld();
			List<EntityPlayer> playerLst = wrld.getEntitiesWithinAABB(EntityPlayer.class, new AxisAlignedBB(player.posX-10, player.posY-10, player.posZ-10, player.posX+10, player.posY+10, player.posZ+10));
			Biome biome = wrld.getBiome(new BlockPos(MathHelper.floor(player.posX), MathHelper.floor(player.posY), MathHelper.floor(player.posZ)));
			boolean canWork = wrld.getWorldTime() % 24000 >= 14000 && wrld.getWorldTime() % 24000 <= 16000 && player.rotationPitch <= -42 && player.rotationPitch >= -65 && playerLst.size() == 1 && !wrld.isRaining() && (biome.getTempCategory() == TempCategory.WARM || biome.getTempCategory() == TempCategory.MEDIUM);
			if(canWork) {
				player.inventory.setInventorySlotContents(player.inventory.currentItem, new ItemStack(ItemsCore.record_everlastingSummer,1,0));
				if(wrld.isRemote) {
					Style style = new Style().setColor(TextFormatting.WHITE);
					player.sendMessage(new TextComponentString("You gase into the stars holding the ticket.").setStyle(style));
					player.sendMessage(new TextComponentString("Suddenly a gust of wind swoops upon you.").setStyle(style));
					player.sendMessage(new TextComponentString("You are immediately beeing attacked by lots of feels.").setStyle(style));
					player.sendMessage(new TextComponentString("Strange, warm feels fall upon you.").setStyle(style));
					player.sendMessage(new TextComponentString("You feel calm and relaxed.").setStyle(style));
					player.sendMessage(new TextComponentString("A feeling falls upon you. You feel like you've just lived a whole another life.").setStyle(style));
					player.sendMessage(new TextComponentString("You try to remember what happened, but memory lets you down.").setStyle(style));
					player.sendMessage(new TextComponentString("You suddenly realise, that you no longer keep the ticket in your hand.").setStyle(style));
					player.sendMessage(new TextComponentString("Instead a music disk is in your hand.").setStyle(style));
					player.sendMessage(new TextComponentString("When you gaze to the disk, you begin to hear the song, written on it.").setStyle(style));
					player.sendMessage(new TextComponentString("You start feeling really sad, like you've missed something very important to you.").setStyle(style));
					player.sendMessage(new TextComponentString("You feel lonely, like missing another half of you.").setStyle(style));
					player.sendMessage(new TextComponentString("After some time you calm down.").setStyle(style));
				}
				return ActionResult.<ItemStack>newResult(EnumActionResult.SUCCESS, par1ItemStack);
			}
		}
		}
		return super.onItemRightClick(world, player, hand);
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return getUnlocalizedName()+dropNames[Math.min(stack.getItemDamage(), dropNames.length-1)];
	}

	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> list) {
		if(this.isInCreativeTab(tab)) {
			for(int i = 0; i < 7; ++i) {
				list.add(new ItemStack(this,1,i));
			}
		}
	}

	@Override
	public boolean hasEffect(ItemStack par1ItemStack) {
		int metadata = par1ItemStack.getItemDamage();
		switch(metadata) {
		case 0: {
			return EssentialCraftCore.proxy.itemHasEffect(par1ItemStack);
		}
		}
		return super.hasEffect(par1ItemStack);
	}

	@Override
	public EnumRarity getRarity(ItemStack p_77613_1_) {
		return EssentialCraftCore.proxy.itemHasEffect(p_77613_1_) ? EnumRarity.RARE : EnumRarity.COMMON;
	}

	@Override
	public void registerModels() {
		for(int i = 0; i < dropNames.length-1; i++) {
			ModelLoader.setCustomModelResourceLocation(this, i, new ModelResourceLocation("essentialcraft:item/secret", "type=" + dropNames[i]));
		}
	}
}
