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

public class BlockAncientDebris extends Block {

	@SideOnly(Side.CLIENT)
	public IIcon iconTop;

	public BlockAncientDebris() {
		super(Material.rock);
		setHarvestLevel("pickaxe", 3);
		setHardness(30F);
		setResistance(1200F);
		Utils.setBlockSound(this, ModSounds.soundAncientDebris);
		setBlockTextureName("ancient_debris");
		setBlockName(Utils.getUnlocalisedName("ancient_debris"));
		setCreativeTab(CreativeTabs.tabBlock);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int p_149691_2_) {
		return side > 1 ? blockIcon : iconTop;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister register) {
		blockIcon = register.registerIcon(this.getTextureName() + "_side");
		iconTop = register.registerIcon(this.getTextureName() + "_top");
	}
}


