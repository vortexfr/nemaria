package fr.nemaria.mods.future.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.nemaria.mods.future.client.sound.ModSounds;
import fr.nemaria.mods.future.configuration.configs.ConfigSounds;
import fr.nemaria.mods.future.core.utils.Utils;
import net.minecraft.block.BlockRotatedPillar;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.IIcon;

public class BlockBone extends BlockRotatedPillar {

	public BlockBone() {
		super(Material.rock);
		setHardness(1.5F);
		setResistance(10.0F);
		setStepSound(ConfigSounds.newBlockSounds ? ModSounds.soundBoneBlock : soundTypeStone);
		setBlockTextureName("bone_block");
		setBlockName(Utils.getUnlocalisedName("bone"));
		setCreativeTab(CreativeTabs.tabBlock);
	}

	@Override
	@SideOnly(Side.CLIENT)
	protected IIcon getSideIcon(int side) {
		return blockIcon;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister reg) {
		blockIcon = reg.registerIcon(this.getTextureName() + "_side");
		field_150164_N = reg.registerIcon(getTextureName() + "_top");
	}
}


