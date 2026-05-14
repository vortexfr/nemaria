package fr.nemaria.mods.future.blocks;

import fr.nemaria.mods.future.core.utils.Utils;
import net.minecraft.block.BlockTrapDoor;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class BlockIronTrapdoor extends BlockTrapDoor {

	public BlockIronTrapdoor() {
		super(Material.iron);
		setHardness(5.0F);
		setStepSound(soundTypeMetal);
		setBlockTextureName("iron_trapdoor");
		setBlockName(Utils.getUnlocalisedName("iron_trapdoor"));
		setCreativeTab(CreativeTabs.tabRedstone);
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		return false;
	}
}

