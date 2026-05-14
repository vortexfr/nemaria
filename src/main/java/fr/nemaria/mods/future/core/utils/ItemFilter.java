package fr.nemaria.mods.future.core.utils;

import net.minecraft.item.ItemStack;

public interface ItemFilter {
	boolean matches(ItemStack var1);

	interface ItemFilterProvider {
		codechicken.nei.api.ItemFilter getFilter();
	}
}
