package essentialcraft.api;

import net.minecraft.nbt.NBTTagCompound;

public interface IESPEHandler {

	public double getMaxESPE();

	public void setMaxESPE(double amount);

	public double getESPE();

	public void setESPE(double amount);

	public double addESPE(double amount, boolean doAdd);

	public double extractESPE(double amount, boolean doExtract);

	public int getTier();

	public void setTier(int tier);

	public NBTTagCompound writeToNBT(NBTTagCompound nbt);

	public void readFromNBT(NBTTagCompound nbt);
}
