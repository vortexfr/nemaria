package fr.nemaria.mods.future.blocks;

import fr.nemaria.mods.future.client.sound.ModSounds;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockBambooMosaic extends BaseBlock {
	public BlockBambooMosaic() {
		super(Material.wood);
		setHardness(2.0F);
		setResistance(5.0F);
		setNames("bamboo_mosaic");
		setCreativeTab(CreativeTabs.tabBlock);
		setBlockSound(ModSounds.soundBambooWood);
	}

	@Override
	public boolean isFlammable(IBlockAccess aWorld, int aX, int aY, int aZ, ForgeDirection aSide) {
		return true;
	}

	@Override
	public int getFlammability(IBlockAccess aWorld, int aX, int aY, int aZ, ForgeDirection aSide) {
		return 20;
	}

	@Override
	public int getFireSpreadSpeed(IBlockAccess aWorld, int aX, int aY, int aZ, ForgeDirection aSide) {
		return 5;
	}
}


