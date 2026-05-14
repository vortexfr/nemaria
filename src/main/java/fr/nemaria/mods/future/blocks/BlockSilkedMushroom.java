package fr.nemaria.mods.future.blocks;

import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.nemaria.mods.future.core.utils.Utils;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.util.IIcon;

public class BlockSilkedMushroom extends Block {

	private final Block block;

	public BlockSilkedMushroom(Block block, String str) {
		super(Material.wood);
		this.block = block;
		setHardness(0.2F);
		setStepSound(soundTypeWood);
		setBlockName(Utils.getUnlocalisedName(str + "_mushroom"));
		setCreativeTab(CreativeTabs.tabBlock);
	}

	@Override
	protected boolean canSilkHarvest() {
		return true;
	}

	@Override
	public int quantityDropped(Random rand) {
		return Math.max(0, rand.nextInt(10) - 7);
	}

	@Override
	public Item getItemDropped(int meta, Random rand, int fortune) {
		return block.getItemDropped(meta, rand, fortune);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {
		return block.getIcon(side, 14);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister reg) {
		// Icons taken from original Block
	}
}

