package essentialcraft.common.tile;

import java.util.Set;

import com.google.common.collect.Sets;

import DummyCore.Utils.DummyChunkLoader;
import DummyCore.Utils.DummyChunkLoader.IChunkLoader;
import essentialcraft.api.ApiCore;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.ChunkPos;

public class TileMRUChunkLoader extends TileMRUGeneric implements IChunkLoader {

	public boolean getChunks = true;
	public DummyChunkLoader loader = new DummyChunkLoader(this);

	public TileMRUChunkLoader() {
		super();
		mruStorage.setMaxMRU(ApiCore.DEVICE_MAX_MRU_GENERIC);
		setSlotsNum(1);
	}

	@Override
	public void update() {
		super.update();
		mruStorage.update(getPos(), getWorld(), getStackInSlot(0));

		if(!getWorld().isRemote) {
			loader.tick();
			if(canOperate()) {
				mruStorage.extractMRU(5, true);
			}
		}
	}

	@Override
	public boolean isItemValidForSlot(int p_94041_1_, ItemStack p_94041_2_) {
		return isBoundGem(p_94041_2_);
	}

	@Override
	public int[] getOutputSlots() {
		return new int[0];
	}

	@Override
	public boolean canOperate() {
		return mruStorage.getMRU() >= 5;
	}

	@Override
	public void invalidate() {
		super.invalidate();
		if(!this.getWorld().isRemote)
			loader.invalidate();
	}

	@Override
	public DummyChunkLoader getChunkLoader() {
		return loader;
	}

	@Override
	public Set<ChunkPos> getChunks() {
		return Sets.<ChunkPos>newHashSet(new ChunkPos(getPos()));
	}

	@Override
	public void readFromNBT(NBTTagCompound i) {
		loader.read(i);
		super.readFromNBT(i);
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound i) {
		loader.write(i);
		return super.writeToNBT(i);
	}
}
