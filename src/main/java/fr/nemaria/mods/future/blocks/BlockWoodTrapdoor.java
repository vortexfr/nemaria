package fr.nemaria.mods.future.blocks;

import fr.nemaria.mods.future.client.sound.ModSounds;
import fr.nemaria.mods.future.core.utils.Utils;
import fr.nemaria.mods.future.lib.RenderIDs;
import net.minecraft.block.Block;
import net.minecraft.block.BlockTrapDoor;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

public class BlockWoodTrapdoor extends BlockTrapDoor {

	public BlockWoodTrapdoor(String type) {
		super(Material.wood);
		disableStats();
		setHardness(3.0F);
		setBlockName(Utils.getUnlocalisedName(type + "_trapdoor"));
		setBlockTextureName(type + "_trapdoor");
		setCreativeTab(CreativeTabs.tabBlock);
		if (type.equals("crimson") || type.equals("warped")) {
			Utils.setBlockSound(this, ModSounds.soundNetherWood);
		} else if (type.equals("cherry")) {
			Utils.setBlockSound(this, ModSounds.soundCherryWood);
		} else if (type.equals("bamboo")) {
			Utils.setBlockSound(this, ModSounds.soundBambooWood);
		} else {
			setStepSound(Block.soundTypeWood);
		}

	}

	@Override
	public int getRenderType() {
		return RenderIDs.TRAPDOOR;
	}

}


