package essentialcraft.common.block;

import DummyCore.Client.IModelRegisterer;
import DummyCore.Utils.DataStorage;
import DummyCore.Utils.DummyData;
import DummyCore.Utils.MiscUtils;
import essentialcraft.common.item.ItemPlayerList;
import essentialcraft.common.mod.EssentialCraftCore;
import essentialcraft.common.tile.TileMRUCoil;
import essentialcraft.utils.cfg.Config;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;

public class BlockMRUCoil extends BlockContainer implements IModelRegisterer {

	public BlockMRUCoil() {
		super(Material.ROCK, MapColor.PURPLE);
	}

	@Override
	public void breakBlock(World par1World, BlockPos par2Pos, IBlockState par3State) {
		IInventory inv = (IInventory)par1World.getTileEntity(par2Pos);
		InventoryHelper.dropInventoryItems(par1World, par2Pos, inv);
		super.breakBlock(par1World, par2Pos, par3State);
	}

	@Override
	public boolean isOpaqueCube(IBlockState s)
	{
		return false;
	}

	@Override
	public boolean isFullCube(IBlockState s)
	{
		return false;
	}

	@Override
	public EnumBlockRenderType getRenderType(IBlockState s)
	{
		return EnumBlockRenderType.MODEL;
	}

	@Override
	public TileEntity createNewTileEntity(World var1, int var2) {
		return new TileMRUCoil();
	}

	@Override
	public boolean onBlockActivated(World par1World, BlockPos par2, IBlockState par3, EntityPlayer par4EntityPlayer, EnumHand par5, EnumFacing par7, float par8, float par9, float par10) {
		if(par4EntityPlayer.isSneaking()) {
			return false;
		}
		boolean flag = par4EntityPlayer.capabilities.isCreativeMode;
		if(!flag) {
			TileMRUCoil tile = (TileMRUCoil)par1World.getTileEntity(par2);
			ItemStack is = tile.getStackInSlot(1);
			if(is.getItem() instanceof ItemPlayerList) {
				NBTTagCompound itemTag = MiscUtils.getStackTag(is);
				if(!itemTag.hasKey("usernames"))
					itemTag.setString("usernames", "||username:null");
				String str = itemTag.getString("usernames");
				DummyData[] dt = DataStorage.parseData(str);
				for(int i = 0; i < dt.length; ++i) {
					String username = dt[i].fieldValue;
					String playerName = MiscUtils.getUUIDFromPlayer(par4EntityPlayer).toString();
					if(username.equals(playerName)) {
						flag = true;
					}
				}
				par4EntityPlayer.sendMessage(new TextComponentString(TextFormatting.RED+I18n.translateToLocal("essentialcraft.txt.noPermission")));
			}
			else {
				flag = true;
			}
		}
		if(flag && !par1World.isRemote) {
			par4EntityPlayer.openGui(EssentialCraftCore.core, Config.guiID[0], par1World, par2.getX(), par2.getY(), par2.getZ());
			return true;
		}
		return flag;
	}

	@Override
	public void registerModels() {
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation("essentialcraft:mrucoil", "inventory"));
	}
}
