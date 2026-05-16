package fr.nemaria.mods.items;

import fr.nemaria.mods.init.NemariaTab;
import net.minecraft.item.Item;

public class ItemTitaniumIngot extends Item {

    public ItemTitaniumIngot() {
        this.setUnlocalizedName("titane_ingot");
        this.setTextureName("nemaria:titane_ingot");
        this.setCreativeTab(NemariaTab.INSTANCE);
    }
}
