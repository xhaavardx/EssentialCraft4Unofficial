package ec3.common.tile;

import java.util.UUID;

import ec3.api.EnumStructureType;
import ec3.api.IStructurePiece;
import net.minecraft.tileentity.TileEntity;

public class TileecHoldingChamber extends TileEntity implements IStructurePiece {
	public TileecController controller;
	public UUID uuid = UUID.randomUUID();

	@Override
	public EnumStructureType getStructure() {
		return EnumStructureType.MRUCUEnrichmentChamber;
	}

	@Override
	public TileEntity structureController() {
		return controller;
	}

	@Override
	public void setStructureController(TileEntity tile, EnumStructureType structure) {
		if(tile instanceof TileecController && structure == getStructure())
			controller = (TileecController) tile;
	}
}
