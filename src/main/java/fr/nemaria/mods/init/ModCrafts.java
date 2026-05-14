package fr.nemaria.mods.init;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.ItemStack;

public class ModCrafts {

    public static void init() {
        // Enregistre tes crafts ici
        
        // Exemple Craft Normal (Shaped)
        // GameRegistry.addRecipe(new ItemStack(ModBlocks.testBlock), 
        //     "XXX", 
        //     "X X", 
        //     "XXX", 
        //     'X', ModItems.testItem
        // );

        // Exemple Craft Informel (Shapeless)
        // GameRegistry.addShapelessRecipe(new ItemStack(ModItems.testItem), 
        //     ModBlocks.testBlock, ModBlocks.testBlock
        // );
        
        // Exemple Cuisson (Smelting)
        // GameRegistry.addSmelting(ModBlocks.testBlock, new ItemStack(ModItems.testItem), 0.1F);
    }
}
