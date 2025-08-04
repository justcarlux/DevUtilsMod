package me.carlux.devutils.client.command;

import me.carlux.devutils.client.manager.HighlightedAreaManager;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.MovingObjectPosition;

import java.util.Collections;
import java.util.List;

public class HighlightCommand extends CommandBase {

    @Override
    public String getCommandName() {
        return "highlight";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "commands.highlight.usage";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
        if (args.length <= 7) {
            throw new WrongUsageException("commands.highlight.usage");
        }

        final String name = args[0];
        final BlockPos pos1 = parseBlockPos(sender, args, 1, false);
        final BlockPos pos2 = parseBlockPos(sender, args, 4, false);
        final double minX = Math.min(pos1.getX(), pos2.getX());
        final double minY = Math.min(pos1.getY(), pos2.getY());
        final double minZ = Math.min(pos1.getZ(), pos2.getZ());
        final double maxX = Math.max(pos1.getX(), pos2.getX());
        final double maxY = Math.max(pos1.getY(), pos2.getY());
        final double maxZ = Math.max(pos1.getZ(), pos2.getZ());

        HighlightedAreaManager.Color color;
        try {
            color = HighlightedAreaManager.Color.valueOf(args[7].toUpperCase());
        } catch (Exception e) {
            throw new CommandException("commands.highlight.invalidColor");
        }

        HighlightedAreaManager.Type type = HighlightedAreaManager.Type.OUTLINE;
        if (args.length >= 9) {
            try {
                type = HighlightedAreaManager.Type.valueOf(args[8].toUpperCase());
            } catch (Exception e) {
                throw new CommandException("commands.highlight.invalidType");
            }
        }

        HighlightedAreaManager.INSTANCE.set(
            name,
            new AxisAlignedBB(minX, minY, minZ, maxX + 1.0f, maxY + 1.0f, maxZ + 1.0f),
            color,
            type
        );
        sender.addChatMessage(new ChatComponentTranslation("commands.highlight.success"));
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender sender) {
        return true;
    }

    public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
        final MovingObjectPosition objectMouseOver = Minecraft.getMinecraft().objectMouseOver;
        final boolean isHoveringBlock = objectMouseOver != null && objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK;
        if (args.length > 1 && args.length <= 4) {
            return func_175771_a(args, 1, isHoveringBlock ? objectMouseOver.getBlockPos() : null);
        } else if (args.length > 4 && args.length <= 7) {
            return func_175771_a(args, 4, isHoveringBlock ? objectMouseOver.getBlockPos() : null);
        } else if (args.length == 8) {
            return getListOfStringsMatchingLastWord(args, HighlightedAreaManager.INSTANCE.getAvailableColors());
        } else if (args.length == 9) {
            return getListOfStringsMatchingLastWord(args, HighlightedAreaManager.INSTANCE.getAvailableTypes());
        } else {
            return Collections.emptyList();
        }
    }
}
