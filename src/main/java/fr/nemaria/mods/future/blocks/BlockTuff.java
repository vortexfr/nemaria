package fr.nemaria.mods.future.blocks;

import fr.nemaria.mods.future.ModBlocks;
import fr.nemaria.mods.future.client.sound.ModSounds;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class BlockTuff extends BaseBlock {

	public BlockTuff() {
		super(Material.rock);
		setHardness(1.5F);
		setResistance(6.0F);
		setNames("tuff");
		setBlockSound(ModSounds.soundTuff);
		setCreativeTab(CreativeTabs.tabBlock);
	}

	@Override
	public boolean isReplaceableOreGen(World world, int x, int y, int z, Block target) {
		boolean flag = target == Blocks.stone || target == ModBlocks.DEEPSLATE.get() || this == target;
		if (flag) {
			BlockDeepslate.doDeepslateRedoCheck(world, x, y, z);
		}
		return flag;
	}

}


