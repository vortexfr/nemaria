package fr.nemaria.mods.future.blocks;

import fr.nemaria.mods.future.client.sound.ModSounds;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.world.World;

public class BlockPackedMud extends BaseSubtypesBlock  {

	public BlockPackedMud() {
		super(Material.rock, "packed_mud", "mud_bricks");
		setHardness(1);
		setResistance(3);
		setHarvestLevel("pickaxe", 0);
		setCreativeTab(CreativeTabs.tabBlock);
		setBlockSound(ModSounds.soundPackedMud);
	}

	@Override
	public float getBlockHardness(World world, int x, int y, int z) {
		if (world.getBlockMetadata(x, y, z) == 1) {
			return 1.5F;
		}
		return blockHardness;
	}
}


