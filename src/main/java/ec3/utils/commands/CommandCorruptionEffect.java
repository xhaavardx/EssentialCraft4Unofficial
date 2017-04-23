package ec3.utils.commands;

import java.util.List;

import ec3.api.CorruptionEffectLibrary;
import ec3.utils.common.ECUtils;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;

public class CommandCorruptionEffect extends CommandBase {

	@Override
	public String getName() {
		return "corruptionEffect";
	}

	@Override
	public String getUsage(ICommandSender sender) {
		return "/corruptionEffect <player> <id|clear>";
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		try {
			if(args.length > 1 && args[1].equalsIgnoreCase("clear")) {
	    		EntityPlayerMP player = getPlayer(server, sender, args[0]);
	    		ECUtils.getData(player).getEffects().clear();
	    		return;
			}
    		int var3 = parseInt(args.length == 1 ? args[0] : args[1], 0);
    		EntityPlayerMP player = args.length == 1 ? getCommandSenderAsPlayer(sender) : getPlayer(server, sender, args[0]);
    		ECUtils.getData(player).getEffects().add(CorruptionEffectLibrary.allPossibleEffects.get(var3));
    	}
    	catch(Throwable e) {
			throw new CommandException("Error adding corruption effect", new Object[0]);
    	}
	}

	public List<String> getTabCompletions(MinecraftServer server, ICommandSender par1ICommandSender, String[] par2ArrayOfStr, BlockPos pos) {
		return par2ArrayOfStr.length == 1 ? getListOfStringsMatchingLastWord(par2ArrayOfStr, server.getOnlinePlayerNames()) : null;
	}

	public boolean isUsernameIndex(String[] s, int par1) {
		return par1 == 0;
	}
}
