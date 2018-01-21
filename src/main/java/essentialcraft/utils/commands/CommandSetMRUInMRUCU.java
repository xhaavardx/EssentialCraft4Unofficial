package essentialcraft.utils.commands;

import java.util.Collections;
import java.util.List;

import essentialcraft.api.IMRUHandler;
import essentialcraft.utils.common.ECUtils;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;

public class CommandSetMRUInMRUCU extends CommandBase
{
	@Override
	public String getName()
	{
		return "setmruinmrucu";
	}

	@Override
	public String getUsage(ICommandSender par1ICommandSender)
	{
		return "/setmruinmrucu <x> <y> <z> <mruAmount>";
	}

	/**
	 * Return the required permission level for this command.
	 */
	@Override
	public int getRequiredPermissionLevel()
	{
		return 3;
	}

	@Override
	public void execute(MinecraftServer s, ICommandSender par1ICommandSender, String[] par2ArrayOfStr) throws CommandException {
		int var3 = parseInt(par2ArrayOfStr[3], 0);
		BlockPos p = parseBlockPos(par1ICommandSender, par2ArrayOfStr, 0, true);
		try {
			IMRUHandler mru = ECUtils.getClosestMRUCU(par1ICommandSender.getEntityWorld(), p, 16);
			mru.setMRU(var3);
		}
		catch(Exception e) {
			throw new CommandException("Could not find MRUCU", new Object[0]);
		}
	}

	@Override
	public List<String> getTabCompletions(MinecraftServer server, ICommandSender par1ICommandSender, String[] par2ArrayOfStr, BlockPos pos) {
		return par2ArrayOfStr.length > 0 && par2ArrayOfStr.length <= 3 ? getTabCompletionCoordinate(par2ArrayOfStr, 0, pos) : Collections.<String>emptyList();
	}
}
