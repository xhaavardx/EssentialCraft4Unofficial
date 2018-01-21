package essentialcraft.common.tile;

public class TileCreativeMRUSource extends TileMRUGeneric {

	public TileCreativeMRUSource() {
		setSlotsNum(0);
		mruStorage.setMaxMRU(100000);
	}

	@Override
	public int[] getOutputSlots() {
		return new int[0];
	}

	@Override
	public void update()  {
		mruStorage.setMRU(mruStorage.getMaxMRU());
		super.update();
	}
}
