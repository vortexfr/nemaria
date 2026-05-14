package fr.nemaria.mods.future.items;

import net.minecraft.item.ItemStack;

public class ItemNetheriteIngot extends BaseItem {

	public ItemNetheriteIngot() {
		super("netherite_ingot");
	}

	@Override
	public boolean isBeaconPayment(ItemStack stack) {
		return true;
	}
}
