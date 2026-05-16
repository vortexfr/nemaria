package fr.nemaria.mods.init;

import cpw.mods.fml.common.registry.GameRegistry;
import fr.nemaria.mods.items.ItemTitaniumIngot;
import fr.nemaria.mods.items.armor.ItemTitaniumArmor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraftforge.common.util.EnumHelper;

public class RegisterItems {

    // ── Matériau Titane (plus fort que la Netherite) ──────────────────────────
    // Netherite : durabilityFactor=37, protection=[3,8,6,3], enchantability=15
    public static final ArmorMaterial TITANIUM_ARMOR_MATERIAL = EnumHelper.addArmorMaterial(
        "TITANIUM",
        45,               // durabilityFactor (netherite=37)
        new int[]{4, 10, 8, 4}, // protection helmet/chest/legs/boots  (netherite=3,8,6,3) → total 26 vs 20
        20                // enchantabilité (netherite=15)
    );

    // ── Items ─────────────────────────────────────────────────────────────────
    public static Item titaniumIngot;
    public static Item titaniumHelmet;
    public static Item titaniumChestplate;
    public static Item titaniumLeggings;
    public static Item titaniumBoots;

    public static void init() {
        // Ingot
        register(titaniumIngot     = new ItemTitaniumIngot());

        // Armure  (0=casque, 1=plastron, 2=jambières, 3=bottes)
        register(titaniumHelmet    = new ItemTitaniumArmor(TITANIUM_ARMOR_MATERIAL, 0));
        register(titaniumChestplate = new ItemTitaniumArmor(TITANIUM_ARMOR_MATERIAL, 1));
        register(titaniumLeggings  = new ItemTitaniumArmor(TITANIUM_ARMOR_MATERIAL, 2));
        register(titaniumBoots     = new ItemTitaniumArmor(TITANIUM_ARMOR_MATERIAL, 3));
    }

    private static <T extends Item> T register(T item) {
        GameRegistry.registerItem(item, item.getUnlocalizedName().substring(5));
        return item;
    }
}

