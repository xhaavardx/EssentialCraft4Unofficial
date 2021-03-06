package essentialcraft.common.tile;

import DummyCore.Utils.DataStorage;
import DummyCore.Utils.DummyData;
import essentialcraft.api.ApiCore;
import essentialcraft.api.RadiatingChamberRecipe;
import essentialcraft.api.RadiatingChamberRecipes;
import essentialcraft.utils.common.ECUtils;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.config.Configuration;

public class TileRadiatingChamber extends TileMRUGeneric {

	public int progressLevel;
	public RadiatingChamberRecipe currentRecipe;
	public static int cfgMaxMRU = ApiCore.DEVICE_MAX_MRU_GENERIC;
	public static boolean generatesCorruption = true;
	public static int genCorruption = 1;
	public static float mruUsage = 1F;

	public TileRadiatingChamber() {
		super();
		mruStorage.setMaxMRU(cfgMaxMRU);
		setSlotsNum(4);
	}

	@Override
	public void update() {
		super.update();
		mruStorage.update(getPos(), getWorld(), getStackInSlot(0));
		if(getWorld().isBlockIndirectlyGettingPowered(pos) == 0) {
			ItemStack[] craftMatrix = new ItemStack[2];
			craftMatrix[0] = getStackInSlot(1);
			craftMatrix[1] = getStackInSlot(2);
			RadiatingChamberRecipe rec = RadiatingChamberRecipes.getRecipeByCPAndBalance(craftMatrix, mruStorage.getBalance());
			if(currentRecipe == null && rec != null && progressLevel != 0) {
				if(canFunction(rec))
					currentRecipe = rec;
			}
			if(currentRecipe == null && rec != null && progressLevel == 0) {
				if(canFunction(rec))
					currentRecipe = rec;
			}
			if(currentRecipe != null && rec == null) {
				progressLevel = 0;
				currentRecipe = null;
				return;
			}
			if(currentRecipe != null && rec != null) {
				if(!canFunction(rec)) {
					progressLevel = 0;
					currentRecipe = null;
					return;
				}
				int mruReq = (int)(mruUsage * currentRecipe.costModifier);
				if(mruStorage.getMRU() >= mruReq && progressLevel < currentRecipe.mruRequired) {
					progressLevel += 1;
					if(generatesCorruption)
						ECUtils.increaseCorruptionAt(getWorld(), pos, getWorld().rand.nextInt(genCorruption));
					mruStorage.extractMRU(mruReq, true);
					if(progressLevel >= currentRecipe.mruRequired) {
						progressLevel = 0;
						craft();
						currentRecipe = null;
					}
				}
			}
		}
	}

	public boolean canFunction(RadiatingChamberRecipe rec) {
		ItemStack result = rec.result;
		if(!result.isEmpty()) {
			if(getStackInSlot(3).isEmpty())
				return true;
			else if(getStackInSlot(3).isItemEqual(result)) {
				if(getStackInSlot(3).getCount()+result.getCount() <= getInventoryStackLimit() && getStackInSlot(3).getCount()+result.getCount() <= getStackInSlot(3).getMaxStackSize())
					return true;
			}
		}
		return false;
	}

	public void craft()
	{
		if(canFunction(currentRecipe)) {
			ItemStack stk = currentRecipe.result.copy();

			if(getStackInSlot(3).isEmpty())
				setInventorySlotContents(3, stk.copy());
			else if (getStackInSlot(3).getItem() == stk.getItem())
				setInventorySlotContents(3, new ItemStack(stk.getItem(),stk.getCount()+getStackInSlot(3).getCount(),stk.getItemDamage()));
			for(int i = 1; i < 3; ++i) {
				decrStackSize(i, 1);
			}
		}
	}

	public static void setupConfig(Configuration cfg) {
		try {
			cfg.load();
			String[] cfgArrayString = cfg.getStringList("RadiatingChamberSettings", "tileentities", new String[] {
					"Max MRU:" + ApiCore.DEVICE_MAX_MRU_GENERIC,
					"MRU Usage Modifier:1.0",
					"Can this device actually generate corruption:true",
					"The amount of corruption generated each tick(do not set to 0!):1"
			}, "");
			String dataString = "";

			for(int i = 0; i < cfgArrayString.length; ++i)
				dataString += "||" + cfgArrayString[i];

			DummyData[] data = DataStorage.parseData(dataString);

			mruUsage = Float.parseFloat(data[1].fieldValue);
			cfgMaxMRU = Integer.parseInt(data[0].fieldValue);
			generatesCorruption = Boolean.parseBoolean(data[2].fieldValue);
			genCorruption = Integer.parseInt(data[3].fieldValue);

			cfg.save();
		}
		catch(Exception e) {
			return;
		}
	}

	@Override
	public int[] getOutputSlots() {
		return new int[] {3};
	}
}
