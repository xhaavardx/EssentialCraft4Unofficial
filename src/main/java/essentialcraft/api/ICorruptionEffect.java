package essentialcraft.api;

import java.util.ArrayList;

import DummyCore.Utils.UnformedItemStack;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;

public interface ICorruptionEffect {

	public void readFromNBTTagCompound(NBTTagCompound tag, int tagID);

	public NBTTagCompound writeToNBTTagCompound(NBTTagCompound tag, int tagID);

	public EnumCorruptionEffect getType();

	public void onPlayerTick(EntityPlayer player);

	public ResourceLocation getEffectIcon();

	public int getStickiness();

	public ICorruptionEffect copy();

	public boolean canMultiply();

	public ArrayList<UnformedItemStack> cureItems();

	public boolean effectEquals(ICorruptionEffect effect);

	public String getLocalizedName();

	public String getLocalizedDesc();

}
