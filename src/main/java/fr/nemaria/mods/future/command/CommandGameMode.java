package fr.nemaria.mods.future.command;

import fr.nemaria.mods.future.spectator.SpectatorMode;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.WorldSettings;

import java.util.Arrays;
import java.util.List;

public class CommandGameMode extends CommandBase {

    @Override
    public String getCommandName() {
        return "gamemode";
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/gamemode <survival|creative|adventure|spectateur> [joueur]";
    }

    @Override
    public List addTabCompletionOptions(ICommandSender sender, String[] args) {
        if (args.length == 1) {
            return getListOfStringsMatchingLastWord(args,
                    "survival", "creative", "adventure", "spectateur",
                    "0", "1", "2", "3");
        }
        if (args.length == 2) {
            return getListOfStringsMatchingLastWord(args,
                    MinecraftServer.getServer().getAllUsernames());
        }
        return null;
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        if (args.length == 0) {
            throw new WrongUsageException(getCommandUsage(sender));
        }

        // Résoudre le joueur cible
        EntityPlayerMP target;
        if (args.length >= 2) {
            target = getPlayer(sender, args[1]);
        } else {
            target = getCommandSenderAsPlayer(sender);
        }

        WorldSettings.GameType gameType = resolveGameType(args[0]);
        if (gameType == null) {
            throw new CommandException("commands.gamemode.invalid", args[0]);
        }

        target.setGameType(gameType);
        target.addChatMessage(new ChatComponentText(
                "§aMode de jeu changé en : §b" + getGameTypeName(gameType)));

        if (sender != target) {
            sender.addChatMessage(new ChatComponentText(
                    "§aMode de jeu de §b" + target.getCommandSenderName()
                    + "§a changé en : §b" + getGameTypeName(gameType)));
        }
    }

    private WorldSettings.GameType resolveGameType(String arg) {
        // Noms français
        switch (arg.toLowerCase()) {
            case "survie":
            case "survival":
            case "0": return WorldSettings.GameType.SURVIVAL;
            case "creatif":
            case "créatif":
            case "creative":
            case "1": return WorldSettings.GameType.CREATIVE;
            case "aventure":
            case "adventure":
            case "2": return WorldSettings.GameType.ADVENTURE;
            case "spectateur":
            case "spectator":
            case "3": return SpectatorMode.SPECTATOR_GAMETYPE;
        }
        return null;
    }

    private String getGameTypeName(WorldSettings.GameType type) {
        if (type == SpectatorMode.SPECTATOR_GAMETYPE) return "Spectateur";
        switch (type) {
            case SURVIVAL:  return "Survie";
            case CREATIVE:  return "Créatif";
            case ADVENTURE: return "Aventure";
            default:        return type.getName();
        }
    }
}
