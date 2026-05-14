package fr.nemaria.mods.future.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.nemaria.mods.future.client.sound.ModSounds;
import fr.nemaria.mods.future.core.utils.Utils;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.IIcon;

public class BlockLodestone extends Block {

	@SideOnly(Side.CLIENT)
	private IIcon topIcon;

	public BlockLodestone() {
		super(Material.rock);
		setHardness(3.5F);
		setResistance(3.5F);
		setHarvestLevel("pickaxe", 1);
		setBlockName(Utils.getUnlocalisedName("lodestone"));
		setBlockTextureName("lodestone");
		setCreativeTab(CreativeTabs.tabBlock);
		Utils.setBlockSound(this, ModSounds.soundLodestone);
		setTickRandomly(true);
	}

	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int p_149691_1_, int p_149691_2_) {
		return p_149691_1_ > 1 ? blockIcon : topIcon;
	}

	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister p_149651_1_) {
		this.blockIcon = p_149651_1_.registerIcon(this.getTextureName() + "_side");
		this.topIcon = p_149651_1_.registerIcon(this.getTextureName() + "_top");
	}

}


