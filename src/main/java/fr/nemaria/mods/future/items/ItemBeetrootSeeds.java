package fr.nemaria.mods.future.items;

import fr.nemaria.mods.future.ModBlocks;
import fr.nemaria.mods.future.core.utils.Utils;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemSeeds;

public class ItemBeetrootSeeds extends ItemSeeds {

	public ItemBeetrootSeeds() {
		super(ModBlocks.BEETROOTS.get(), Blocks.farmland);
		setTextureName("beetroot_seeds");
		setUnlocalizedName(Utils.getUnlocalisedName("beetroot_seeds"));
		setCreativeTab(CreativeTabs.tabMisc);
	}
}