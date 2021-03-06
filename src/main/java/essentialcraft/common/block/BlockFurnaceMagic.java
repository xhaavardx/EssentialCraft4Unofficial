package essentialcraft.common.block;

import DummyCore.Client.IModelRegisterer;
import essentialcraft.common.mod.EssentialCraftCore;
import essentialcraft.common.tile.TileFurnaceMagic;
import essentialcraft.utils.cfg.Config;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.Mirror;
import net.minecraft.util.NonNullList;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;

public class BlockFurnaceMagic extends BlockContainer implements IModelRegisterer {

	public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);
	public static final PropertyEnum<FurnaceType> TYPE = PropertyEnum.<FurnaceType>create("type", FurnaceType.class);

	public BlockFurnaceMagic()
	{
		super(Material.ROCK);
		setDefaultState(blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(TYPE, FurnaceType.FORTIFIED));
	}

	@Override
	public EnumBlockRenderType getRenderType(IBlockState s)
	{
		return EnumBlockRenderType.MODEL;
	}

	@Override
	public void breakBlock(World par1World, BlockPos par2Pos, IBlockState par3State) {
		IInventory inv = (IInventory)par1World.getTileEntity(par2Pos);
		InventoryHelper.dropInventoryItems(par1World, par2Pos, inv);
		super.breakBlock(par1World, par2Pos, par3State);
	}

	@Override
	public MapColor getMapColor(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
		return state.getValue(TYPE).getMapColor();
	}

	@Override
	public int damageDropped(IBlockState s)
	{
		return s.getValue(TYPE).getIndex()*4;
	}

	@Override
	public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
		return new ItemStack(this,1,state.getValue(TYPE).getIndex()*4);
	}

	@Override
	public void getSubBlocks(CreativeTabs p_149666_2_, NonNullList<ItemStack> p_149666_3_)
	{
		p_149666_3_.add(new ItemStack(this, 1, 0));
		p_149666_3_.add(new ItemStack(this, 1, 4));
		p_149666_3_.add(new ItemStack(this, 1, 8));
		p_149666_3_.add(new ItemStack(this, 1, 12));
	}

	@Override
	public void onBlockAdded(World w, BlockPos p, IBlockState s)
	{
		super.onBlockAdded(w, p, s);
		this.setBlockRotation(w, p, s);
	}

	private void setBlockRotation(World w, BlockPos p, IBlockState s)
	{
		if (!w.isRemote)
		{
			IBlockState block = w.getBlockState(p.north());
			IBlockState block1 = w.getBlockState(p.south());
			IBlockState block2 = w.getBlockState(p.west());
			IBlockState block3 = w.getBlockState(p.east());
			int b0 = s.getValue(TYPE).getIndex();

			if (block.isOpaqueCube() && !block1.isOpaqueCube())
			{
				b0 += 1;
				w.setBlockState(p, getStateFromMeta(b0), 3);
				return;
			}

			if (block1.isOpaqueCube() && !block.isOpaqueCube())
			{
				b0 += 0;
				w.setBlockState(p, getStateFromMeta(b0), 3);
				return;
			}

			if (block2.isOpaqueCube() && !block3.isOpaqueCube())
			{
				b0 += 3;
				w.setBlockState(p, getStateFromMeta(b0), 3);
				return;
			}

			if (block3.isOpaqueCube() && !block2.isOpaqueCube())
			{
				b0 += 2;
				w.setBlockState(p, getStateFromMeta(b0), 3);
				return;
			}
		}
	}


	@Override
	public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
	{
		return this.getDefaultState().withProperty(TYPE, FurnaceType.fromIndex(meta/4)).withProperty(FACING, placer.getHorizontalFacing().getOpposite());
	}

	@Override
	public void onBlockPlacedBy(World w, BlockPos p, IBlockState s, EntityLivingBase pl, ItemStack is)
	{
		w.setBlockState(p, getDefaultState().withProperty(TYPE, FurnaceType.fromIndex(is.getItemDamage()/4)).withProperty(FACING, pl.getHorizontalFacing().getOpposite()), 2);
	}

	@Override
	public TileEntity createNewTileEntity(World w, int meta)
	{
		return new TileFurnaceMagic();
	}

	@Override
	public boolean onBlockActivated(World par1World, BlockPos par2, IBlockState par3, EntityPlayer par4EntityPlayer, EnumHand par5, EnumFacing par7, float par8, float par9, float par10) {
		if(par4EntityPlayer.isSneaking()) {
			return false;
		}
		if(!par1World.isRemote) {
			par4EntityPlayer.openGui(EssentialCraftCore.core, Config.guiID[0], par1World, par2.getX(), par2.getY(), par2.getZ());
			return true;
		}
		return true;
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return getDefaultState().withProperty(TYPE, FurnaceType.fromIndex(meta/4)).withProperty(FACING, EnumFacing.getHorizontal(meta%4));
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(TYPE).getIndex()*4 + state.getValue(FACING).getHorizontalIndex();
	}

	@Override
	public IBlockState withRotation(IBlockState state, Rotation rot) {
		return state.withProperty(FACING, rot.rotate(state.getValue(FACING)));
	}

	@Override
	public IBlockState withMirror(IBlockState state, Mirror mirrorIn) {
		return state.withRotation(mirrorIn.toRotation(state.getValue(FACING)));
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, FACING, TYPE);
	}

	@Override
	public void registerModels() {
		for(int i = 0; i < FurnaceType.values().length; i++) {
			ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), i*4, new ModelResourceLocation("essentialcraft:furnacemagic", "facing=north,type=" + FurnaceType.fromIndex(i).getName()));
		}
	}

	public static enum FurnaceType implements IStringSerializable {
		FORTIFIED(0, "fortified", MapColor.STONE),
		MAGIC(1, "magic", MapColor.PURPLE),
		PALE(2, "pale", MapColor.LAPIS),
		VOID(3, "void", MapColor.BLACK);

		private final int index;
		private final String name;
		private final MapColor mapColor;

		private FurnaceType(int index, String name, MapColor mapColor) {
			this.index = index;
			this.name = name;
			this.mapColor = mapColor;
		}

		@Override
		public String getName() {
			return name;
		}

		@Override
		public String toString() {
			return name;
		}

		public int getIndex() {
			return index;
		}

		public MapColor getMapColor() {
			return mapColor;
		}

		public static FurnaceType fromIndex(int i) {
			return values()[i%4];
		}
	}
}
