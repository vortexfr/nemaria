package fr.nemaria.mods.world;

import java.util.Random;

import cpw.mods.fml.common.IWorldGenerator;
import fr.nemaria.mods.init.RegisterBlock;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;

public class NemariaWorldGenerator implements IWorldGenerator {

    public static final NemariaWorldGenerator INSTANCE = new NemariaWorldGenerator();

    // Minerai de Titane : rare, profond, 4 blocs par veine
    private final WorldGenMinable titaniumOreGen = new WorldGenMinable(
        RegisterBlock.titaniumOre,  // le bloc
        4,                          // taille de la veine
        Blocks.stone                // remplace la pierre
    );

    @Override
    public void generate(Random rand, int chunkX, int chunkZ, World world,
                         IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {

        // Overworld uniquement (dimension 0)
        if (world.provider.dimensionId != 0) return;

        // Génération du minerai de Titane
        // Rare (3 fois par chunk), entre Y=5 et Y=30 (profond comme le diamant)
        for (int i = 0; i < 3; i++) {
            int x = (chunkX << 4) + rand.nextInt(16);
            int y = 5 + rand.nextInt(25); // entre Y=5 et Y=30
            int z = (chunkZ << 4) + rand.nextInt(16);
            titaniumOreGen.generate(world, rand, x, y, z);
        }
    }
}
