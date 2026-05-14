package fr.nemaria.mods.future.blocks;

import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.nemaria.mods.future.client.particle.CustomParticles;
import fr.nemaria.mods.future.core.utils.Utils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockTorch;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.world.World;

public class BlockSoulTorch extends BlockTorch {

	public BlockSoulTorch() {
		super();
		setStepSound(Block.soundTypeWood);
		setLightLevel(0.666F);
		setBlockName(Utils.getUnlocalisedName("soul_torch"));
		setBlockTextureName("soul_torch");
		setCreativeTab(CreativeTabs.tabBlock);
	}

	/**
	 * A randomly called display update to be able to add particles or other items for display
	 */
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(World world, int x, int y, int z, Random rand) {
		int l = world.getBlockMetadata(x, y, z);
		double d0 = (float) x + 0.5F;
		double d1 = (float) y + 0.7F;
		double d2 = (float) z + 0.5F;
		double d3 = 0.2199999988079071D;
		double d4 = 0.27000001072883606D;

		switch (l) {
			case 1:
				world.spawnParticle("smoke", d0 - d4, d1 + d3, d2, 0.0D, 0.0D, 0.0D);
				CustomParticles.spawnSoulFlame(world, d0 - d4, d1 + d3, d2);
				break;
			case 2:
				world.spawnParticle("smoke", d0 + d4, d1 + d3, d2, 0.0D, 0.0D, 0.0D);
				CustomParticles.spawnSoulFlame(world, d0 + d4, d1 + d3, d2);
				break;
			case 3:
				world.spawnParticle("smoke", d0, d1 + d3, d2 - d4, 0.0D, 0.0D, 0.0D);
				CustomParticles.spawnSoulFlame(world, d0, d1 + d3, d2 - d4);
				break;
			case 4:
				world.spawnParticle("smoke", d0, d1 + d3, d2 + d4, 0.0D, 0.0D, 0.0D);
				CustomParticles.spawnSoulFlame(world, d0, d1 + d3, d2 + d4);
				break;
			default:
				world.spawnParticle("smoke", d0, d1, d2, 0.0D, 0.0D, 0.0D);
				CustomParticles.spawnSoulFlame(world, d0, d1, d2);
				break;
		}
	}
}


