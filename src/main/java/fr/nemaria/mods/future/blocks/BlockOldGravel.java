package fr.nemaria.mods.future.blocks;

import java.util.Random;

import fr.nemaria.mods.future.core.utils.Utils;
import net.minecraft.block.BlockGravel;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class BlockOldGravel extends BlockGravel {

	public BlockOldGravel() {
		setHardness(0.6F);
		setStepSound(soundTypeGravel);
		setBlockTextureName("old_gravel");
		setBlockName(Utils.getUnlocalisedName("old_gravel"));
		setCreativeTab(CreativeTabs.tabBlock);
	}

	@Override
	public Item getItemDropped(int meta, Random rand, int fortune) {
		return Item.getItemFromBlock(this);
	}
}

