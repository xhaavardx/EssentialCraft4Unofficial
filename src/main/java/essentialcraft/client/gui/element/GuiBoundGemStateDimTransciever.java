package essentialcraft.client.gui.element;

import DummyCore.Utils.MiscUtils;
import essentialcraft.api.IMRUHandler;
import essentialcraft.common.item.ItemBoundGem;
import net.minecraft.client.Minecraft;
import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;

public class GuiBoundGemStateDimTransciever extends GuiTextElement {

	public TileEntity tile;
	public int slotNum;

	public GuiBoundGemStateDimTransciever(int i, int j, TileEntity t, int slot)
	{
		super(i,j);
		tile = t;
		slotNum = slot;
	}

	@Override
	public ResourceLocation getElementTexture() {
		return super.getElementTexture();
	}

	@Override
	public void draw(int posX, int posY, int mouseX, int mouseY) {
		super.draw(posX, posY, mouseX, mouseY);
	}

	@Override
	public int getX() {
		return super.getX();
	}

	@Override
	public int getY() {
		return super.getY();
	}

	@Override
	public void drawText(int posX, int posY) {
		IInventory inventory = (IInventory) tile;
		if(!(inventory.getStackInSlot(slotNum).getItem() instanceof ItemBoundGem)) {
			Minecraft.getMinecraft().fontRenderer.drawString("No Bound Gem!", posX+6, posY+5, 0xff0000, true);
		}
		else if(inventory.getStackInSlot(slotNum).getTagCompound() == null) {
			Minecraft.getMinecraft().fontRenderer.drawString("Gem Not Bound!", posX+4, posY+5, 0xff0000, true);
		}
		else {
			int o[] = ItemBoundGem.getCoords(inventory.getStackInSlot(slotNum));
			int d = MiscUtils.getStackTag(inventory.getStackInSlot(slotNum)).getInteger("dim");
			World w = DimensionManager.getWorld(d);
			if(w.getTileEntity(new BlockPos(o[0], o[1], o[2])) == null) {
				Minecraft.getMinecraft().fontRenderer.drawString("No Tile At Pos!", posX+5, posY+5, 0xff0000, true);
			}
			else if(!(w.getTileEntity(new BlockPos(o[0], o[1], o[2])) instanceof IMRUHandler)) {
				Minecraft.getMinecraft().fontRenderer.drawString("Not Magical!", posX+12, posY+5, 0xff0000, true);
			}
			else if(((IMRUHandler)w.getTileEntity(new BlockPos(o[0], o[1], o[2]))).getMRU() <= 0) {
				Minecraft.getMinecraft().fontRenderer.drawString("No MRU In Tile!", posX+6, posY+5, 0xff0000, true);
			}
			else {
				Minecraft.getMinecraft().fontRenderer.drawString("Working", posX+22, posY+5, 0x00ff00, true);
			}
		}
	}
}
