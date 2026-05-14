package fr.nemaria.mods.future.blocks;

import fr.nemaria.mods.future.core.utils.Utils;
import fr.nemaria.mods.future.lib.Reference;

public class BlockOldRose extends BaseFlower {

	public BlockOldRose() {
		setBlockName(Utils.getUnlocalisedName("rose"));
		setBlockTextureName(Reference.MOD_ID + ":flower_rose");
	}
}
