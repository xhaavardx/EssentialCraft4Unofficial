package essentialcraft.common.tile;

import essentialcraft.api.EnumStructureType;
import essentialcraft.api.IMRUHandlerStores;
import essentialcraft.api.IStructurePiece;
import net.minecraft.tileentity.TileEntity;

public class TileMRUCUECEjector extends TileEntity implements IMRUHandlerStores, IStructurePiece {
	public TileMRUCUECController controller;

	@Override
	public int getMRU() {
		if(controller!=null)
			return controller.getMRU();
		return 0;
	}

	@Override
	public int getMaxMRU() {
		return 60000;
	}

	@Override
	public boolean setMRU(int i) {
		return controller != null && i < getMRU() ? controller.setMRU(i) : false;
	}

	@Override
	public float getBalance() {
		return controller != null ? controller.getBalance() : 1;
	}

	@Override
	public boolean setBalance(float f) {
		return controller != null ? controller.setBalance(f) : false;
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
			controller = (TileMRUCUECController) tile;
	}

	@Override
	public boolean setMaxMRU(float f) {
		return controller != null ? controller.setMaxMRU(f) : false;
	}
}
