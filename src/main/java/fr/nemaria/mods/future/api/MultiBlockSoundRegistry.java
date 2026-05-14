package fr.nemaria.mods.future.api;

import java.util.HashMap;
import java.util.Map;

import fr.nemaria.mods.future.api.mappings.MultiBlockSoundContainer;
import net.minecraft.block.Block;

public class MultiBlockSoundRegistry {

	public static final Map<Block, MultiBlockSoundContainer> multiBlockSounds = new HashMap<>();

	public enum BlockSoundType {
		BREAK,
		PLACE,
		WALK,
		HIT
	}
}
