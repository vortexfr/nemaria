package fr.nemaria.mods.future.items.equipment;

import fr.nemaria.mods.future.ModItems;
import fr.nemaria.mods.future.core.utils.Utils;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;

public class ItemEFRAxe extends ItemAxe {

	public ItemEFRAxe(ToolMaterial material, int durabilityOverride) {
		super(material);
		this.setMaxDamage(durabilityOverride > -1 ? durabilityOverride : material.getMaxUses());
		this.setUnlocalizedName(Utils.getUnlocalisedName("netherite_axe"));
		this.setTextureName("netherite_axe");
		this.setCreativeTab(CreativeTabs.tabCombat);
	}

	@Override
	public boolean getIsRepairable(ItemStack par1ItemStack, ItemStack par2ItemStack) {
		return ModItems.NETHERITE_INGOT.get() == par2ItemStack.getItem() || super.getIsRepairable(par1ItemStack, par2ItemStack);
	}
}
