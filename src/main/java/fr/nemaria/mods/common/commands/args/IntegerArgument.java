package fr.nemaria.mods.common.commands.args;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import fr.nemaria.mods.common.commands.NemariaCommand.CommandContext;
import net.minecraft.command.CommandException;

public class IntegerArgument implements IArgument<Integer> {
    public static final DecimalFormat NUMBERS = new DecimalFormat("###,###", DecimalFormatSymbols.getInstance(Locale.ENGLISH));
    private static final List<String> EXAMPLES = Arrays.asList("0", "123", "-123");

    int min, max;

    protected IntegerArgument(int min, int max) { 
    	this.min = min; this.max = max; 
    }

    public static IntegerArgument value() { 
    	return new IntegerArgument(Integer.MIN_VALUE, Integer.MAX_VALUE); 
    }
    
    public static IntegerArgument min(int min) {
    	return new IntegerArgument(min, Integer.MAX_VALUE); 
    }
    
    public static IntegerArgument max(int max)  { 
    	return new IntegerArgument(Integer.MIN_VALUE, max); 
    }
    public static IntegerArgument range(int min, int max) {
    	return new IntegerArgument(min, max); 
    }

    @Override
    public Integer parse(StringWalker args, CommandContext context) throws CommandException {
        int value = args.readInt();
        if (value < min) throw new CommandException("commands.nemaria.error.parse.to_small", NUMBERS.format(value), NUMBERS.format(min));
        if (value > max) throw new CommandException("commands.nemaria.error.parse.to_big", NUMBERS.format(value), NUMBERS.format(max));
        return value;
    }

    @Override
    public List<String> getExamples(CommandContext context, int argumentIndex) { 
    	return EXAMPLES; 
    }
}
