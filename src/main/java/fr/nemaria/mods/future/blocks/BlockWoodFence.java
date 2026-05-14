package fr.nemaria.mods.future.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.nemaria.mods.future.configuration.configs.ConfigFunctions;
import fr.nemaria.mods.future.core.utils.Utils;
import fr.nemaria.mods.future.recipes.ModRecipes;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFence;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockWoodFence extends BlockFence {

	private final int meta;

	public BlockWoodFence(int meta) {
		super(null, Material.wood);
		this.meta = meta;
		setHardness(2.0F);
		setResistance(5.0F);
		setStepSound(soundTypeWood);
		setBlockName(Utils.getUnlocalisedName(ModRecipes.woodTypes[meta] + "_fence"));
		setCreativeTab(CreativeTabs.tabBlock);
	}

	@Override
	public boolean canPlaceTorchOnTop(World world, int x, int y, int z) {
		return true;
	}

	@Override
	public boolean canConnectFenceTo(IBlockAccess world, int x, int y, int z) {
		Block block = world.getBlock(x, y, z);
		return block instanceof BlockWoodFence || block instanceof BlockModernWoodFence || block instanceof BlockWoodFenceGate || super.canConnectFenceTo(world, x, y, z);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister ignored) {
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int _meta) {
		return Blocks.planks.getIcon(side, this.meta);
	}

	@Override
	public boolean isFlammable(IBlockAccess aWorld, int aX, int aY, int aZ, ForgeDirection aSide) {
		return ConfigFunctions.enableExtraBurnableBlocks;
	}

	@Override
	public int getFlammability(IBlockAccess aWorld, int aX, int aY, int aZ, ForgeDirection aSide) {
		return ConfigFunctions.enableExtraBurnableBlocks ? 20 : 0;
	}

	@Override
	public int getFireSpreadSpeed(IBlockAccess aWorld, int aX, int aY, int aZ, ForgeDirection aSide) {
		return ConfigFunctions.enableExtraBurnableBlocks ? 5 : 0;
	}
}

