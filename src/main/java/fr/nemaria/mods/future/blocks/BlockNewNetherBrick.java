package fr.nemaria.mods.future.blocks;

import fr.nemaria.mods.future.client.sound.ModSounds;
import fr.nemaria.mods.future.configuration.configs.ConfigSounds;
import fr.nemaria.mods.future.core.utils.Utils;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

public class BlockNewNetherBrick extends BaseSubtypesBlock {

	public BlockNewNetherBrick() {
		super(Material.rock, "red_nether_bricks", "cracked_nether_bricks", "chiseled_nether_bricks");
		this.setResistance(6);
		this.setHardness(2);
		setStepSound(ConfigSounds.newBlockSounds ? ModSounds.soundNetherBricks : soundTypePiston);
		this.setHarvestLevel("pickaxe", 0);
		this.setBlockTextureName("nether_bricks");
		setBlockName(Utils.getUnlocalisedName("red_netherbrick"));
		setCreativeTab(CreativeTabs.tabBlock);
	}
}


