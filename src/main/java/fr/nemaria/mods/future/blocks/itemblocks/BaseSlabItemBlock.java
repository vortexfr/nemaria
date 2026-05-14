package fr.nemaria.mods.future.blocks.itemblocks;

import fr.nemaria.mods.future.blocks.BaseSlab;
import fr.nemaria.mods.future.blocks.ISubBlocksBlock;
import net.minecraft.block.Block;
import net.minecraft.item.ItemSlab;
import net.minecraft.item.ItemStack;

public class BaseSlabItemBlock extends ItemSlab {

	public BaseSlabItemBlock(Block block) {
		super(block, ((BaseSlab) block).getSingleSlab(), ((BaseSlab) block).getDoubleSlab(), block == ((BaseSlab) block).getDoubleSlab());
		this.setHasSubtypes(((ISubBlocksBlock) field_150939_a).getTypes().length > 1);
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return addTilePrefix(((ISubBlocksBlock) field_150939_a).getNameFor(stack));
	}

	private String addTilePrefix(String name) {
		return name.startsWith("tile.") ? name : "tile." + ((ISubBlocksBlock) field_150939_a).getNameDomain() + "." + name;
	}

	@Override
	public int getMetadata(int meta) {
		return meta % 8;
	}

}
