package fr.nemaria.mods.future.command.args;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.nemaria.mods.common.commands.NemariaCommand.CommandContext;
import fr.nemaria.mods.common.commands.args.IArgument;
import fr.nemaria.mods.common.commands.args.StringWalker;
import net.minecraft.command.CommandException;
import net.minecraft.command.NumberInvalidException;

/**
 * Argument temps en ticks.
 * Formats acceptés : {@code 1d} (jour=24000t), {@code 1s} (seconde=20t), {@code 1t} (tick), {@code 100} (ticks bruts).
 */
public class TimeArgument implements IArgument<Long> {
    private static final List<String> EXAMPLES = Arrays.asList("1d", "1s", "1t", "1", "0");
    private static final Map<Character, Long> MULTIPLIERS = createMultipliers();

    public static TimeArgument time() { return new TimeArgument(); }

    @Override
    public Long parse(StringWalker args, CommandContext context) throws CommandException {
        if (!args.canRead()) throw new CommandException("commands.nemaria.error.parse.missing.args");
        try {
            String s = args.peek();
            char suffix = s.charAt(s.length() - 1);
            if (!isNumber(suffix)) {
                long result = (long) (Double.parseDouble(s.substring(0, s.length() - 1)) * MULTIPLIERS.getOrDefault(suffix, 1L));
                args.skip();
                return result;
            }
            return (long) args.readDouble();
        } catch (Exception e) {
            throw new NumberInvalidException("commands.nemaria.error.parse.double", args.poll());
        }
    }

    @Override
    public List<String> getExamples(CommandContext context, int argumentIndex) { return EXAMPLES; }

    private static boolean isNumber(char c) { return (c >= '0' && c <= '9') || c == '.' || c == '-'; }

    private static Map<Character, Long> createMultipliers() {
        Map<Character, Long> m = new HashMap<>();
        m.put('d', 24000L); // jour
        m.put('s', 20L);    // seconde
        m.put('t', 1L);     // tick
        return m;
    }
}
