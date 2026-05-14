package fr.nemaria.mods.future.blocks;

import fr.nemaria.mods.future.core.utils.Utils;
import net.minecraft.block.BlockPressurePlate;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

public class BlockPolishedBlackstonePressurePlate extends BlockPressurePlate {
	public BlockPolishedBlackstonePressurePlate() {
		super("polished_blackstone", Material.rock, Sensitivity.everything);
		setHardness(0.5F);
		setResistance(0.5F);
		setBlockName(Utils.getUnlocalisedName("polished_blackstone_pressure_plate"));
		setCreativeTab(CreativeTabs.tabBlock);
	}
}


