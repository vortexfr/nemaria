package fr.nemaria.mods.init;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;

public class RegisterItems {
    
    // 1. Déclare ton item ici
    // public static Item testItem;

    public static void init() {
        // 2. Initialise et enregistre d'un seul coup ici
        // register(testItem = new ItemTest());
    }

    /**
     * Enregistre l'item automatiquement en récupérant son nom.
     * Le nom (setUnlocalizedName) et la texture (setTextureName) doivent être définis dans la classe de l'Item !
     */
    private static <T extends Item> T register(T item) {
        // item.getUnlocalizedName() renvoie "item.nom_de_l_item"
        // substring(5) enlève "item." pour récupérer "nom_de_l_item"
        GameRegistry.registerItem(item, item.getUnlocalizedName().substring(5));
        return item;
    }
}
