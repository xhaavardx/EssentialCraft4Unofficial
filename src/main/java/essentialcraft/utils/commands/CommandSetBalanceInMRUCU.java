package essentialcraft.utils.commands;

import java.util.Collections;
import java.util.List;

import DummyCore.Utils.Coord3D;
import essentialcraft.common.entity.EntityMRUPresence;
import essentialcraft.utils.common.ECUtils;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;

public class CommandSetBalanceInMRUCU extends CommandBase {
	@Override
	public String getName() {
		return "setbalanceinmrucu";
	}

	@Override
	public String getUsage(ICommandSender par1ICommandSender) {
		return "/setbalanceinmrucu <x> <y> <z> <balance>";
	}

	/**
	 * Return the required permission level for this command.
	 */
	@Override
	public int getRequiredPermissionLevel() {
		return 3;
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender par1ICommandSender, String[] par2ArrayOfStr) throws CommandException {
		double var3 = parseDouble(par2ArrayOfStr[3], 0, 2);
		BlockPos p = parseBlockPos(par1ICommandSender, par2ArrayOfStr, 0, true);
		int x = p.getX();
		int y = p.getY();
		int z = p.getZ();
		try {
			EntityMRUPresence mru = (EntityMRUPresence)ECUtils.getClosestMRUCU(par1ICommandSender.getEntityWorld(), new Coord3D(x,y,z), 16);
			mru.setBalance((float)var3);
		}
		catch(Exception e) {
			throw new CommandException("Could not find MRUCU", new Object[0]);
		}
	}

	public List<String> getTabCompletionOptions(MinecraftServer server, ICommandSender par1ICommandSender, String[] par2ArrayOfStr, BlockPos pos) {
		return par2ArrayOfStr.length > 0 && par2ArrayOfStr.length <= 3 ? getTabCompletionCoordinate(par2ArrayOfStr, 0, pos) : Collections.<String>emptyList();
	}
}
