package fr.nemaria.mods.future.command.args;

import java.util.ArrayList;
import java.util.List;

import fr.nemaria.mods.common.commands.NemariaCommand.CommandContext;
import fr.nemaria.mods.common.commands.args.IArgument;
import fr.nemaria.mods.common.commands.args.StringWalker;
import net.minecraft.command.CommandException;
import net.minecraftforge.common.DimensionManager;

public class DimensionArgument implements IArgument<Integer> {
    boolean loaded;

    protected DimensionArgument(boolean loaded) { this.loaded = loaded; }

    /** Seulement les dimensions actuellement chargées */
    public static DimensionArgument loaded() { return new DimensionArgument(true); }
    /** Toutes les dimensions enregistrées */
    public static DimensionArgument any()    { return new DimensionArgument(false); }

    @Override
    public Integer parse(StringWalker args, CommandContext context) throws CommandException {
        int value = args.readInt();
        if (loaded ? DimensionManager.getWorld(value) == null : !DimensionManager.isDimensionRegistered(value)) {
            throw new CommandException("commands.nemaria.error.parse.dimension_missing", value);
        }
        return value;
    }

    @Override
    public List<String> getExamples(CommandContext context, int argumentIndex) {
        List<String> list = new ArrayList<>();
        for (Integer entry : (loaded ? DimensionManager.getIDs() : DimensionManager.getStaticDimensionIDs())) {
            list.add(entry.toString());
        }
        return list;
    }
}
