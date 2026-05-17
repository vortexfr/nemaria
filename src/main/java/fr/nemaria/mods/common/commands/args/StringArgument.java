package fr.nemaria.mods.common.commands.args;

import java.util.Arrays;
import java.util.List;

import fr.nemaria.mods.common.commands.NemariaCommand.CommandContext;

public class StringArgument implements IArgument<String> {

    public static StringArgument text() {
    	return new StringArgument(); 
    }

    @Override
    public String parse(StringWalker args, CommandContext context) {
    	return args.poll(); 
    }

    @Override
    public List<String> getExamples(CommandContext context, int argumentIndex) {
        return Arrays.asList("word", "words_with_underline");
    }
}
