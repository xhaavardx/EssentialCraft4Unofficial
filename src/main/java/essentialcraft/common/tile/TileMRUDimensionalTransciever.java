package essentialcraft.common.tile;

import essentialcraft.api.ApiCore;
import essentialcraft.common.capabilities.mru.MRUTileCrossDimStorage;
import net.minecraft.item.ItemStack;

public class TileMRUDimensionalTransciever extends TileMRUGeneric {

	public TileMRUDimensionalTransciever() {
		super();
		mruStorage = new MRUTileCrossDimStorage();
		mruStorage.setMaxMRU(ApiCore.DEVICE_MAX_MRU_GENERIC);
		setSlotsNum(1);
	}

	@Override
	public void update() {
		super.update();
		mruStorage.update(getPos(), getWorld(), getStackInSlot(0));
	}

	@Override
	public boolean isItemValidForSlot(int p_94041_1_, ItemStack p_94041_2_) {
		return isBoundGem(p_94041_2_);
	}

	@Override
	public int[] getOutputSlots() {
		return new int[0];
	}
}
