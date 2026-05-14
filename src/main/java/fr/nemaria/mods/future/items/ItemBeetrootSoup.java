package fr.nemaria.mods.future.items;

import fr.nemaria.mods.future.core.utils.Utils;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemSoup;

public class ItemBeetrootSoup extends ItemSoup {

	public ItemBeetrootSoup() {
		super(6);
		setTextureName("beetroot_soup");
		setUnlocalizedName(Utils.getUnlocalisedName("beetroot_soup"));
		setCreativeTab(CreativeTabs.tabFood);
	}
}