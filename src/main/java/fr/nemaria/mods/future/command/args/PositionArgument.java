package fr.nemaria.mods.future.command.args;

import java.util.Arrays;
import java.util.List;

import fr.nemaria.mods.common.commands.NemariaCommand.CommandContext;
import fr.nemaria.mods.common.commands.args.IArgument;
import fr.nemaria.mods.common.commands.args.StringWalker;
import net.minecraft.command.CommandException;
import net.minecraft.util.ChunkCoordinates;

/**
 * Argument position 2D (X, Z) — prend 2 tokens dans args[].
 * Supporte les préfixes ~ (relatif joueur), ^ (relatif spawn), b (bloc → chunk).
 */
public class PositionArgument implements IArgument<ChunkPos> {
    private static final List<String> FIRST  = Arrays.asList("0", "~", "^", "^1", "~1", "b2");
    private static final List<String> SECOND = Arrays.asList("0", "~", "^", "^-5", "~-5", "b-5");

    boolean chunkPos;

    protected PositionArgument(boolean chunkPos) { this.chunkPos = chunkPos; }

    /** Position en coordonnées de blocs */
    public static PositionArgument blockPos() { return new PositionArgument(false); }
    /** Position en coordonnées de chunks */
    public static PositionArgument chunkPos() { return new PositionArgument(true); }

    @Override
    public ChunkPos parse(StringWalker args, CommandContext context) throws CommandException {
        ChunkCoordinates senderCoords = context.getSender().getPlayerCoordinates();
        ChunkCoordinates spawnCoords  = context.getSenderDimension().getSpawnPoint();

        ChunkPos player = new ChunkPos(senderCoords.posX, senderCoords.posZ);
        ChunkPos spawn  = new ChunkPos(spawnCoords.posX,  spawnCoords.posZ);

        if (chunkPos) {
            player = player.toChunkPos();
            spawn  = spawn.toChunkPos();
        }
        int x = args.parsePosition(player.x, spawn.x, chunkPos);
        int z = args.parsePosition(player.z, spawn.z, chunkPos);
        return new ChunkPos(x, z);
    }

    @Override public int getArgumentElements() { return 2; }
    @Override public List<String> getExamples(CommandContext context, int argumentIndex) {
        return argumentIndex == 0 ? FIRST : SECOND;
    }
}
