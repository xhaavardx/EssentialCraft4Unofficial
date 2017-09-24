package essentialcraft.api;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public interface IWorldEvent {

	public void onEventBeginning(World w);

	public void worldTick(World w, int leftoverTime);

	public void playerTick(EntityPlayer p, int leftoverTime);

	public void onEventEnd(World w);

	public int getEventDuration(World w);

	public boolean possibleToApply(World w);

	public float getEventProbability(World w);

	public String getEventID();

}
