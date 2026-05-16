package fr.nemaria.mods.init;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.ItemStack;

public class ModCrafts {

    public static void init() {
        // Casque
        GameRegistry.addRecipe(new ItemStack(RegisterItems.titaniumHelmet), new Object[]{
            "TTT", "T T",
            'T', new ItemStack(RegisterItems.titaniumIngot)});

        // Plastron
        GameRegistry.addRecipe(new ItemStack(RegisterItems.titaniumChestplate), new Object[]{
            "T T", "TTT", "TTT",
            'T', new ItemStack(RegisterItems.titaniumIngot)});

        // Jambières
        GameRegistry.addRecipe(new ItemStack(RegisterItems.titaniumLeggings), new Object[]{
            "TTT", "T T", "T T",
            'T', new ItemStack(RegisterItems.titaniumIngot)});

        // Bottes
        GameRegistry.addRecipe(new ItemStack(RegisterItems.titaniumBoots), new Object[]{
            "T T", "T T",
            'T', new ItemStack(RegisterItems.titaniumIngot)});

        // Bloc de Titane (9 lingots → 1 bloc)
        GameRegistry.addRecipe(new ItemStack(RegisterBlock.titaniumBlock), new Object[]{
            "TTT", "TTT", "TTT",
            'T', new ItemStack(RegisterItems.titaniumIngot)});

        // Décompression (1 bloc → 9 lingots)
        GameRegistry.addRecipe(new ItemStack(RegisterItems.titaniumIngot, 9), new Object[]{
            "X",
            'X', new ItemStack(RegisterBlock.titaniumBlock)});
    }
}
