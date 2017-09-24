package essentialcraft.common.tile;

import essentialcraft.api.EnumStructureType;
import essentialcraft.api.IMRUHandlerRequires;
import essentialcraft.api.IStructurePiece;
import net.minecraft.tileentity.TileEntity;

public class TileMRUCUECStateChecker extends TileEntity implements IStructurePiece, IMRUHandlerRequires {
	public TileMRUCUECController controller;

	@Override
	public int getMRU() {
		return controller != null ? controller.getMRU() : 0;
	}

	@Override
	public int getMaxMRU() {
		return controller != null ? controller.getMaxMRU() : 0;
	}

	@Override
	public boolean setMRU(int i) {
		return false;
	}

	@Override
	public float getBalance() {
		return controller != null ? controller.getBalance() : 1;
	}

	@Override
	public boolean setBalance(float f) {
		return false;
	}

	@Override
	public EnumStructureType getStructure() {
		return EnumStructureType.MRUCUEC;
	}

	@Override
	public TileEntity structureController() {
		return controller;
	}

	@Override
	public void setStructureController(TileEntity tile, EnumStructureType structure) {
		if(tile instanceof TileMRUCUECController && structure == getStructure())
			controller = (TileMRUCUECController)tile;
	}

	@Override
	public boolean setMaxMRU(float f) {
		return false;
	}
}
