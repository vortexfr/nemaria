package fr.nemaria.mods.future.items.equipment;

import fr.nemaria.mods.future.ModItems;
import fr.nemaria.mods.future.core.utils.Utils;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;

public class ItemEFRSpade extends ItemSpade {

	public ItemEFRSpade(ToolMaterial material, int durabilityOverride) {
		super(material);
		this.setMaxDamage(durabilityOverride > -1 ? durabilityOverride : material.getMaxUses());
		this.setUnlocalizedName(Utils.getUnlocalisedName("netherite_spade"));
		this.setTextureName("netherite_shovel");
		this.setCreativeTab(CreativeTabs.tabTools);
	}

	@Override
	public boolean getIsRepairable(ItemStack par1ItemStack, ItemStack par2ItemStack) {
		return ModItems.NETHERITE_INGOT.get() == par2ItemStack.getItem() || super.getIsRepairable(par1ItemStack, par2ItemStack);
	}
}
