package fr.nemaria.mods.common.commands.args;

import java.util.List;

import fr.nemaria.mods.common.commands.NemariaCommand.CommandContext;
import net.minecraft.command.CommandException;

public interface IArgument<T> {
    public T parse(StringWalker args, CommandContext context) 
    		throws CommandException;
    public List<String> getExamples(CommandContext context, int argumentIndex);
    public default int getArgumentElements() {
    	return 1; 
    }
}
