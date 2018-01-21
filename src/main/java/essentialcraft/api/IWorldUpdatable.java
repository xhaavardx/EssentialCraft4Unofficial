package essentialcraft.api;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface IWorldUpdatable<T> {

	public void update(BlockPos pos, World world, T param);
}
