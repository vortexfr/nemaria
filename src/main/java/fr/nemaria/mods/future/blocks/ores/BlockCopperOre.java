package fr.nemaria.mods.future.blocks.ores;

import fr.nemaria.mods.future.core.utils.Utils;
import net.minecraft.block.BlockOre;
import net.minecraft.creativetab.CreativeTabs;

public class BlockCopperOre extends BlockOre {

	public BlockCopperOre() {
		super();
		setHardness(3);
		setResistance(3);
		setBlockName(Utils.getUnlocalisedName("copper_ore"));
		setBlockTextureName("copper_ore");
		setHarvestLevel("pickaxe", 1);
		setCreativeTab(CreativeTabs.tabBlock);
	}
}
