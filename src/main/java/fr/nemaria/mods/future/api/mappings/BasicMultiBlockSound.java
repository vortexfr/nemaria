package fr.nemaria.mods.future.api.mappings;

import fr.nemaria.mods.future.api.MultiBlockSoundRegistry;
import net.minecraft.block.Block;
import net.minecraft.world.World;

public class BasicMultiBlockSound extends MultiBlockSoundContainer {

	private final Block.SoundType[] types = new Block.SoundType[16];

	public void setTypes(int meta, Block.SoundType type) {
		types[meta % 16] = type;
	}

	@Override
	public String getSound(World world, int x, int y, int z, String oldSoundIn, MultiBlockSoundRegistry.BlockSoundType type) {
		Block.SoundType soundType = types[world.getBlockMetadata(x, y, z)];
		if (soundType == null) return null;
		switch (type) {
			case WALK:
			case HIT:
				return soundType.getStepResourcePath();
			case PLACE:
				return soundType.func_150496_b();
			default:
			case BREAK:
				return soundType.getBreakSound();
		}
	}
}
