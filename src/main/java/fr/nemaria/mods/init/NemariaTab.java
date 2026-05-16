package fr.nemaria.mods.init;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

public class NemariaTab extends CreativeTabs {

    public static final CreativeTabs INSTANCE = new NemariaTab("nemaria") {
        @Override
        @SideOnly(Side.CLIENT)
        public int func_151243_f() {
            return 0;
        }
    };

    public NemariaTab(String label) {
        super(label);
    }

    @Override
    public Item getTabIconItem() {
        Item icon = RegisterItems.titaniumIngot;
        return icon != null ? icon : Items.diamond;
    }
}
