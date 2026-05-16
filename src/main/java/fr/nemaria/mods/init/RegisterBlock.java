package fr.nemaria.mods.init;

import cpw.mods.fml.common.registry.GameRegistry;
import fr.nemaria.mods.blocks.BlockTitanium;
import fr.nemaria.mods.blocks.BlockTitaniumOre;
import net.minecraft.block.Block;

public class RegisterBlock {

    public static Block titaniumBlock;
    public static Block titaniumOre;

    public static void init() {
        register(titaniumBlock = new BlockTitanium());
        register(titaniumOre  = new BlockTitaniumOre());
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
