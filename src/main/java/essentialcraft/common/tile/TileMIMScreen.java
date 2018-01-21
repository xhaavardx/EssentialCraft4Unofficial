package essentialcraft.common.tile;

import DummyCore.Utils.DataStorage;
import DummyCore.Utils.DummyData;
import essentialcraft.api.ApiCore;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.config.Configuration;

public class TileMIMScreen extends TileMRUGeneric {

	public TileMIM parent;
	int tickTime;

	public static int cfgMaxMRU = ApiCore.DEVICE_MAX_MRU_GENERIC;
	public static int mruForIns = 500;
	public static int mruForOut = 10;

	public TileMIMScreen() {
		mruStorage.setMaxMRU(cfgMaxMRU);
		setSlotsNum(2);
	}

	@Override
	public int[] getOutputSlots() {
		return new int[] {1};
	}

	@Override
	public void update() {
		super.update();
		mruStorage.update(getPos(), getWorld(), getStackInSlot(0));
		if(tickTime == 0) {
			tickTime = 20;
			if(parent != null)
				if(!parent.isParent(this))
					parent = null;
		}
		else
			--tickTime;

		if(parent != null) {
			if(!getStackInSlot(1).isEmpty()) {
				if(mruStorage.getMRU() >= mruForIns) {
					mruStorage.extractMRU(mruForIns, true);
					if(parent.addItemStackToSystem(getStackInSlot(1)))
						setInventorySlotContents(1, ItemStack.EMPTY);

					syncTick = 0;
				}
			}
		}
	}

	public static void setupConfig(Configuration cfg) {
		try {
			cfg.load();
			String[] cfgArrayString = cfg.getStringList("NewMagicalInventoryManagerScreenSettings", "tileentities", new String[] {
					"Max MRU:" + ApiCore.DEVICE_MAX_MRU_GENERIC,
					"MRU Per Inserted Item:500",
					"MRU Per Requested Item(for 1):10"
			}, "");
			String dataString = "";

			for(int i = 0; i < cfgArrayString.length; ++i)
				dataString += "||" + cfgArrayString[i];

			DummyData[] data = DataStorage.parseData(dataString);

			mruForIns = Integer.parseInt(data[1].fieldValue);
			mruForOut = Integer.parseInt(data[2].fieldValue);
			cfgMaxMRU = Integer.parseInt(data[0].fieldValue);

			cfg.save();
		}catch(Exception e) {
			return;
		}
	}
}
