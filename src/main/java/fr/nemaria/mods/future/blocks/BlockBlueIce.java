package fr.nemaria.mods.future.blocks;

import fr.nemaria.mods.future.core.utils.Utils;
import net.minecraft.block.BlockPackedIce;
import net.minecraft.creativetab.CreativeTabs;

public class BlockBlueIce extends BlockPackedIce {

	public BlockBlueIce() {
		super();
		this.slipperiness = 0.989F;
		this.setHarvestLevel("pickaxe", 0);
		this.setStepSound(soundTypeGlass);
		this.setHardness(2.8F);
		this.setResistance(2.8F);
		this.setBlockName(Utils.getUnlocalisedName("blue_ice"));
		this.setBlockTextureName("blue_ice");
		this.setCreativeTab(CreativeTabs.tabBlock);
	}
}


