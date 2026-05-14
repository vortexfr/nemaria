package fr.nemaria.mods.future.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.nemaria.mods.future.ModBlocks;
import fr.nemaria.mods.future.core.utils.Utils;
import net.minecraft.block.BlockButtonStone;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.IIcon;

public class BlockPolishedBlackstoneButton extends BlockButtonStone {

	public BlockPolishedBlackstoneButton() {
		super();
		setHardness(0.5F);
		setResistance(0.5F);
		setBlockName(Utils.getUnlocalisedName("polished_blackstone_button"));
		setCreativeTab(CreativeTabs.tabBlock);
	}

	/**
	 * Gets the block's texture. Args: side, meta
	 */
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int p_149691_1_, int p_149691_2_) {
		return ModBlocks.BLACKSTONE.get().getIcon(0, 1);
	}
}


