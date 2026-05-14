package fr.nemaria.mods.init;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;

public class RegisterBlock {
    
    // 1. Déclare ton bloc ici
    // public static Block testBlock;

    public static void init() {
        // 2. Initialise et enregistre d'un seul coup ici
        // register(testBlock = new BlockTest());
    }

    /**
     * Enregistre le bloc automatiquement en récupérant son nom.
     * Le nom (setBlockName) et la texture (setBlockTextureName) doivent être définis dans la classe du Bloc !
     */
    private static <T extends Block> T register(T block) {
        // block.getUnlocalizedName() renvoie "tile.nom_du_bloc"
        // substring(5) enlève "tile." pour récupérer "nom_du_bloc"
        GameRegistry.registerBlock(block, block.getUnlocalizedName().substring(5));
        return block;
    }
}
