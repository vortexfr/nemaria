package fr.nemaria.mods.future.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.nemaria.mods.future.ModBlocks;
import fr.nemaria.mods.future.core.utils.Utils;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;

public class BlockSmoothQuartzSlab extends BaseSlab {

	public BlockSmoothQuartzSlab(boolean p_i45410_1_) {
		super(p_i45410_1_, Material.rock, "smooth_quartz");
		setResistance(6);
		setHardness(2.0F);
		setBlockName(Utils.getUnlocalisedName("smooth_quartz_slab"));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister reg) {
		IIcon[] icon = new IIcon[getTypes().length];
		blockIcon = icon[0] = ModBlocks.SMOOTH_QUARTZ.get().getIcon(2, 0);
		setIcons(icon);
	}
}

