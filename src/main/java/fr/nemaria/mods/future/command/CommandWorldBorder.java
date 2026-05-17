package fr.nemaria.mods.future.command;

import fr.nemaria.mods.common.commands.CommandBuilder;
import fr.nemaria.mods.common.commands.NemariaCommand;
import fr.nemaria.mods.common.commands.NemariaCommand.ChatUtils;
import fr.nemaria.mods.future.command.args.DoubleArgument;
import fr.nemaria.mods.future.worldborder.WorldBorderData;
import fr.nemaria.mods.future.worldborder.WorldBorderManager;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

/**
 * Commandes WorldBorder :
 *   /worldborder create <rayon>         — crée la bordure
 *   /worldborder set <rayon>            — change le rayon
 *   /worldborder center <x> <z>         — déplace le centre
 *   /worldborder remove                 — supprime la bordure
 *   /worldborder info                   — affiche les infos
 */
public class CommandWorldBorder extends NemariaCommand {

    public CommandWorldBorder() {
        super("worldborder");

        // /worldborder create <radius>
        addChild(new CommandBuilder("create")
            .arg("radius", DoubleArgument.min(1), ctx -> {
                double radius = ctx.getArgument("radius", Double.class);
                World world = ctx.getSenderDimension();
                WorldBorderManager.INSTANCE.create(world, radius);
                ctx.sendSuccess(ChatUtils.applyColor(
                    ChatUtils.literal("Bordure créée : rayon de " + (int) radius + " blocs."),
                    EnumChatFormatting.GREEN));
            }).build());

        // /worldborder set <radius>
        addChild(new CommandBuilder("set")
            .arg("radius", DoubleArgument.min(1), ctx -> {
                double radius = ctx.getArgument("radius", Double.class);
                World world = ctx.getSenderDimension();
                if (!WorldBorderManager.INSTANCE.isActive(world)) {
                    ctx.sendFailure(ChatUtils.literal("Aucune bordure active. Utilisez /worldborder create <rayon>"));
                    return;
                }
                WorldBorderManager.INSTANCE.setRadius(world, radius);
                ctx.sendSuccess(ChatUtils.applyColor(
                    ChatUtils.literal("Rayon de la bordure mis à jour : " + (int) radius + " blocs."),
                    EnumChatFormatting.GREEN));
            }).build());

        // /worldborder center <x> <z>
        addChild(new CommandBuilder("center")
            .arg("x", DoubleArgument.value())
            .arg("z", DoubleArgument.value(), ctx -> {
                double x = ctx.getArgument("x", Double.class);
                double z = ctx.getArgument("z", Double.class);
                World world = ctx.getSenderDimension();
                WorldBorderManager.INSTANCE.setCenter(world, x, z);
                ctx.sendSuccess(ChatUtils.applyColor(
                    ChatUtils.literal("Centre déplacé en X=" + (int) x + " Z=" + (int) z + "."),
                    EnumChatFormatting.GREEN));
            }).build());

        // /worldborder remove
        addChild(new CommandBuilder("remove")
            .withCommand(ctx -> {
                World world = ctx.getSenderDimension();
                WorldBorderManager.INSTANCE.remove(world);
                ctx.sendSuccess(ChatUtils.applyColor(
                    ChatUtils.literal("Bordure supprimée."),
                    EnumChatFormatting.YELLOW));
            }).build());

        // /worldborder info
        addChild(new CommandBuilder("info")
            .withCommand(ctx -> {
                World world = ctx.getSenderDimension();
                WorldBorderData data = WorldBorderManager.INSTANCE.getData(world);
                if (!data.active) {
                    ctx.sendSuccess(ChatUtils.literal(EnumChatFormatting.RED + "Aucune bordure active."));
                    return;
                }
                ctx.sendSuccess(ChatUtils.literal(
                    EnumChatFormatting.GOLD + "=== WorldBorder ===" +
                    "\n§eCentre : §f" + (int) data.centerX + ", " + (int) data.centerZ +
                    "\n§eRayon  : §f" + (int) data.radius + " blocs" +
                    "\n§eZone   : §f" + (int)(data.centerX - data.radius) + " à " + (int)(data.centerX + data.radius) + " (X)" +
                    " / " + (int)(data.centerZ - data.radius) + " à " + (int)(data.centerZ + data.radius) + " (Z)"
                ));
            }).build());
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/worldborder <create|set|center|remove|info>";
    }

    @Override
    public int getRequiredPermissionLevel() { return 2; }
}
