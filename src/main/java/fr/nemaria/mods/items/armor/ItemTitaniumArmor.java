package fr.nemaria.mods.items.armor;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.nemaria.mods.init.NemariaTab;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraftforge.common.ISpecialArmor;

public class ItemTitaniumArmor extends ItemArmor implements ISpecialArmor {

    public ItemTitaniumArmor(ArmorMaterial material, int type) {
        super(material, 0, type);
        String slot = "helmet";
        switch (type) {
            case 1: slot = "chestplate"; break;
            case 2: slot = "leggings"; break;
            case 3: slot = "boots"; break;
        }
        this.setUnlocalizedName("titane_" + slot);
        this.setTextureName("nemaria:titane_" + slot);
        this.setCreativeTab(NemariaTab.INSTANCE);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type) {
        // Titane_Armor_1.png pour casque/plastron/bottes, _2 pour jambières
        return "nemaria:textures/models/armor/Titane_Armor_" + (this.armorType == 2 ? "2" : "1") + ".png";
    }

    @Override
    public ArmorProperties getProperties(EntityLivingBase player, ItemStack armor, DamageSource source, double damage, int slot) {
        ItemTitaniumArmor armorItem = (ItemTitaniumArmor) armor.getItem();
        return new ArmorProperties(0, source.isUnblockable() ? 0 : armorItem.damageReduceAmount / 25D, armorItem.getMaxDamage() + 1 - armor.getItemDamage());
    }

    @Override
    public int getArmorDisplay(EntityPlayer player, ItemStack armor, int slot) {
        return this.damageReduceAmount;
    }

    @Override
    public void damageArmor(EntityLivingBase entity, ItemStack stack, DamageSource source, int damage, int slot) {
        if (!source.isUnblockable()) {
            stack.damageItem(damage, entity);
        }
    }
}
