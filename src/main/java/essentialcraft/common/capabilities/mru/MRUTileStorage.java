package essentialcraft.common.capabilities.mru;

import DummyCore.Utils.MathUtils;
import essentialcraft.api.IMRUHandler;
import essentialcraft.api.IWorldUpdatable;
import essentialcraft.common.item.ItemBoundGem;
import essentialcraft.common.mod.EssentialCraftCore;
import essentialcraft.common.tile.TileMRUReactor;
import essentialcraft.common.tile.TileRayTower;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class MRUTileStorage extends MRUStorage implements IWorldUpdatable<ItemStack> {

	public MRUTileStorage() {
		super();
	}

	public MRUTileStorage(int maxMRU) {
		super(maxMRU);
	}

	@Override
	public void update(BlockPos pos, World world, ItemStack boundGem) {
		this.spawnMRUParticles(pos, world, boundGem);
		this.mruIn(pos, world, boundGem);
	}

	public void mruIn(BlockPos pos, World world, ItemStack boundGem) {
		if(boundGem.getItem() instanceof ItemBoundGem && boundGem.getTagCompound() != null) {
			int[] o = ItemBoundGem.getCoords(boundGem);
			if(MathUtils.getDifference(pos.getX(), o[0]) <= 16 && MathUtils.getDifference(pos.getY(), o[1]) <= 16 && MathUtils.getDifference(pos.getZ(), o[2]) <= 16) {
				BlockPos o1 = new BlockPos(o[0],o[1],o[2]);
				if(!pos.equals(o1) && world.getTileEntity(o1) != null && world.getTileEntity(o1).hasCapability(CapabilityMRUHandler.MRU_HANDLER_CAPABILITY, null)) {
					IMRUHandler other = world.getTileEntity(o1).getCapability(CapabilityMRUHandler.MRU_HANDLER_CAPABILITY, null);
					if(this.getMRU() < this.getMaxMRU()) {
						int req = this.getMaxMRU() - this.getMRU();
						int extracted = other.extractMRU(req, true);
						if(extracted+this.getMRU() > 0)
							this.setBalance((other.getBalance()*extracted+this.getBalance()*this.getMRU())/(extracted+this.getMRU()));
						this.addMRU(extracted, true);
					}
				}
			}
		}
	}

	public void spawnMRUParticles(BlockPos pos, World world, ItemStack boundGem) {
		if(world.isRemote) {
			if(boundGem.getItem() instanceof ItemBoundGem && boundGem.getTagCompound() != null) {
				int[] o = ItemBoundGem.getCoords(boundGem);
				if(MathUtils.getDifference(pos.getX(), o[0]) <= 16 && MathUtils.getDifference(pos.getY(), o[1]) <= 16 && MathUtils.getDifference(pos.getZ(), o[2]) <= 16) {
					BlockPos o1 = new BlockPos(o[0],o[1],o[2]);
					if(world.getTileEntity(o1) != null && world.getTileEntity(o1).hasCapability(CapabilityMRUHandler.MRU_HANDLER_CAPABILITY, null)) {
						IMRUHandler other = world.getTileEntity(o1).getCapability(CapabilityMRUHandler.MRU_HANDLER_CAPABILITY, null);
						float balance = other.getBalance();
						float colorRRender = 0.0F;
						float colorGRender = 1.0F;
						float colorBRender = 1.0F;

						float colorRNormal = 0.0F;
						float colorGNormal = 1.0F;
						float colorBNormal = 1.0F;

						float colorRChaos = 1.0F;
						float colorGChaos = 0.0F;
						float colorBChaos = 0.0F;

						float colorRFrozen = 0.0F;
						float colorGFrozen = 0.0F;
						float colorBFrozen = 1.0F;
						if(balance!=1.0F) {
							if(balance<1.0F) {
								float diff = balance;
								if(diff < 0.01F)
									diff = 0.0F;
								colorRRender = colorRNormal*diff + colorRFrozen*(1.0F-diff);
								colorGRender = colorGNormal*diff + colorGFrozen*(1.0F-diff);
								colorBRender = colorBNormal*diff + colorBFrozen*(1.0F-diff);
							}
							if(balance>1.0F) {
								float diff = 2.0F-balance;
								if(diff < 0.01F)
									diff = 0.0F;
								colorRRender = colorRNormal*diff + colorRChaos*(1.0F-diff);
								colorGRender = colorGNormal*diff + colorGChaos*(1.0F-diff);
								colorBRender = colorBNormal*diff + colorBChaos*(1.0F-diff);
							}
						}
						//will change
						if(world.getTileEntity(pos) instanceof TileRayTower) {
							if(world.getTileEntity(o1) instanceof TileRayTower)
								EssentialCraftCore.proxy.MRUFX(o[0]+0.5D, o[1]+1.85D, o[2]+0.5D, pos.getX()-o[0], pos.getY()-o[1]+0.25D, pos.getZ()-o[2],colorRRender,colorGRender,colorBRender);
							else if(world.getTileEntity(o1) instanceof TileMRUReactor)
								EssentialCraftCore.proxy.MRUFX(o[0]+0.5D, o[1]+1.1D, o[2]+0.5D, pos.getX()-o[0], pos.getY()-o[1]+0.8D, pos.getZ()-o[2],colorRRender,colorGRender,colorBRender);
							else
								EssentialCraftCore.proxy.MRUFX(o[0]+0.5D, o[1]+0.5D, o[2]+0.5D, pos.getX()-o[0], pos.getY()-o[1]+1.5D, pos.getZ()-o[2],colorRRender,colorGRender,colorBRender);
						}
						else {
							if(world.getTileEntity(o1) instanceof TileRayTower)
								EssentialCraftCore.proxy.MRUFX(o[0]+0.5D, o[1]+1.85D, o[2]+0.5D, pos.getX()-o[0], pos.getY()-o[1]-1.5D, pos.getZ()-o[2],colorRRender,colorGRender,colorBRender);
							else if(world.getTileEntity(o1) instanceof TileMRUReactor)
								EssentialCraftCore.proxy.MRUFX(o[0]+0.5D, o[1]+1.1D, o[2]+0.5D, pos.getX()-o[0], pos.getY()-o[1]-0.6D, pos.getZ()-o[2],colorRRender,colorGRender,colorBRender);
							else
								EssentialCraftCore.proxy.MRUFX(o[0]+0.5D, o[1]+0.5D, o[2]+0.5D, pos.getX()-o[0], pos.getY()-o[1], pos.getZ()-o[2],colorRRender,colorGRender,colorBRender);
						}
					}
				}
			}
		}
	}
}
