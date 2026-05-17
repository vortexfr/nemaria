package fr.nemaria.mods.common.commands;

import fr.nemaria.mods.common.commands.NemariaCommand.CommandContext;

public interface ICommand {
    void execute(CommandContext context);
}
