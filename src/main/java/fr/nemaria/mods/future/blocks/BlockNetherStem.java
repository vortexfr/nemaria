package fr.nemaria.mods.future.blocks;

import fr.nemaria.mods.future.client.sound.ModSounds;
import fr.nemaria.mods.future.core.utils.Utils;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockNetherStem extends BaseLog {

	public BlockNetherStem(String type) {
		super(type);
		for (int i = 0; i < getTypes().length; i++) {
			getTypes()[i] = getTypes()[i].replace("log", "stem").replace("wood", "hyphae");
		}
		setBlockName(Utils.getUnlocalisedName(type + "_stem"));
		setBlockSound(ModSounds.soundStem);
	}

	@Override
	public boolean canSustainLeaves(IBlockAccess world, int x, int y, int z) {
		return false;
	}

	@Override
	public boolean isFlammable(IBlockAccess aWorld, int aX, int aY, int aZ, ForgeDirection aSide) {
		return false;
	}

	@Override
	public int getFlammability(IBlockAccess aWorld, int aX, int aY, int aZ, ForgeDirection aSide) {
		return 0;
	}

	@Override
	public int getFireSpreadSpeed(IBlockAccess aWorld, int aX, int aY, int aZ, ForgeDirection aSide) {
		return 0;
	}
}

