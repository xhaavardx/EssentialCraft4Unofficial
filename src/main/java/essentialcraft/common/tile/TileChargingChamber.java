package essentialcraft.common.tile;

import DummyCore.Utils.DataStorage;
import DummyCore.Utils.DummyData;
import essentialcraft.api.ApiCore;
import essentialcraft.api.IMRUHandlerItem;
import essentialcraft.common.capabilities.mru.CapabilityMRUHandler;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.config.Configuration;

public class TileChargingChamber extends TileMRUGeneric {

	public static Capability<IMRUHandlerItem> MRU_HANDLER_ITEM_CAPABILITY = CapabilityMRUHandler.MRU_HANDLER_ITEM_CAPABILITY;
	public static int cfgMaxMRU = ApiCore.DEVICE_MAX_MRU_GENERIC;
	public static float reqMRUModifier = 1.0F;

	public TileChargingChamber() {
		super();
		mruStorage.setMaxMRU(cfgMaxMRU);
		setSlotsNum(2);
	}

	@Override
	public void update() {
		super.update();
		mruStorage.update(getPos(), getWorld(), getStackInSlot(0));
		if(getWorld().isBlockIndirectlyGettingPowered(pos) == 0)
			tryChargeTools();
	}

	public void tryChargeTools(){
		ItemStack stack = getStackInSlot(1);
		if(!stack.isEmpty()) {
			if(stack.hasCapability(MRU_HANDLER_ITEM_CAPABILITY, null)) {
				IMRUHandlerItem mruHandler = stack.getCapability(MRU_HANDLER_ITEM_CAPABILITY, null);
				int mru = mruHandler.getMRU();
				int maxMRU = mruHandler.getMaxMRU();
				int p = (int)((double)maxMRU/20);
				if(mru < maxMRU) {
					if(mru+p < maxMRU) {
						int amount = mruStorage.extractMRU((int)(p*reqMRUModifier), true);
						mruHandler.addMRU(amount, true);
					}
					else {
						int k = maxMRU - mru;
						int amount = mruStorage.extractMRU((int)(k*reqMRUModifier), true);
						mruHandler.addMRU(amount, true);
					}
				}
			}
		}
	}

	public static void setupConfig(Configuration cfg) {
		try {
			cfg.load();
			String[] cfgArrayString = cfg.getStringList("ChargingChamberSettings", "tileentities", new String[] {
					"Max MRU:" + ApiCore.DEVICE_MAX_MRU_GENERIC,
					"Charge cost Modifier:1.0"
			}, "Settings of the given Device.");
			String dataString = "";

			for(int i = 0; i < cfgArrayString.length; ++i)
				dataString += "||" + cfgArrayString[i];

			DummyData[] data = DataStorage.parseData(dataString);
			cfgMaxMRU = (int)Float.parseFloat(data[0].fieldValue);
			reqMRUModifier=Float.parseFloat(data[1].fieldValue);
			cfg.save();
		}
		catch(Exception e) {
			return;
		}
	}

	@Override
	public int[] getOutputSlots() {
		return new int[] {1};
	}
}
