package fr.nemaria.mods.future.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.IIcon;

public interface IFloatingParticleBlock {
	@SideOnly(Side.CLIENT)
	IIcon getParticleName(int meta);
}

