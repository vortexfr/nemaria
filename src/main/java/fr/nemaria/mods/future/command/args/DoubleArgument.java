package fr.nemaria.mods.future.command.args;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import fr.nemaria.mods.common.commands.NemariaCommand.CommandContext;
import fr.nemaria.mods.common.commands.args.IArgument;
import fr.nemaria.mods.common.commands.args.StringWalker;
import net.minecraft.command.CommandException;

public class DoubleArgument implements IArgument<Double> {
    public static final DecimalFormat FLOATING = new DecimalFormat("###,###.##", DecimalFormatSymbols.getInstance(Locale.ENGLISH));
    private static final List<String> EXAMPLES = Arrays.asList("0", "1.2", ".5", "-1", "-.5", "-1234.56");

    double min, max;

    protected DoubleArgument(double min, double max) { this.min = min; this.max = max; }

    public static DoubleArgument value()               { return new DoubleArgument(-Double.MAX_VALUE, Double.MAX_VALUE); }
    public static DoubleArgument min(double min)       { return new DoubleArgument(min, Double.MAX_VALUE); }
    public static DoubleArgument max(double max)       { return new DoubleArgument(-Double.MAX_VALUE, max); }
    public static DoubleArgument range(double min, double max) { return new DoubleArgument(min, max); }

    @Override
    public Double parse(StringWalker args, CommandContext context) throws CommandException {
        double value = args.readDouble();
        if (value < min) throw new CommandException("commands.nemaria.error.parse.to_small", FLOATING.format(value), FLOATING.format(min));
        if (value > max) throw new CommandException("commands.nemaria.error.parse.to_big",   FLOATING.format(value), FLOATING.format(max));
        return value;
    }

    @Override
    public List<String> getExamples(CommandContext context, int argumentIndex) { return EXAMPLES; }
}
