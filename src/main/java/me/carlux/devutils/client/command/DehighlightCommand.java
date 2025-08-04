package me.carlux.devutils.client.command;

import me.carlux.devutils.client.manager.HighlightedAreaManager;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.util.ChatComponentTranslation;

public class DehighlightCommand extends CommandBase {

    @Override
    public String getCommandName() {
        return "dehighlight";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "commands.dehighlight.usage";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
        if (args.length < 1) {
            throw new WrongUsageException("commands.dehighlight.usage");
        }

        final String name = args[0];
        if (HighlightedAreaManager.INSTANCE.remove(name)) {
            sender.addChatMessage(new ChatComponentTranslation("commands.dehighlight.success"));
        } else {
            sender.addChatMessage(new ChatComponentTranslation("commands.dehighlight.notFound"));
        }
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender sender) {
        return true;
    }

}
