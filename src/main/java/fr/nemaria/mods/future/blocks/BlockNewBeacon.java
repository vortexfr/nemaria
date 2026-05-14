package fr.nemaria.mods.future.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.nemaria.mods.future.ModBlocks;
import fr.nemaria.mods.future.configuration.configs.ConfigWorld;
import fr.nemaria.mods.future.tileentities.TileEntityNewBeacon;
import net.minecraft.block.BlockBeacon;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import java.util.Random;

public class BlockNewBeacon extends BlockBeacon {

	public BlockNewBeacon() {
		setLightLevel(1.0F);
		setBlockTextureName("beacon");
		setBlockName("beacon");
		setCreativeTab(CreativeTabs.tabRedstone);
	}

	@Override
	public Item getItemDropped(int meta, Random rand, int fortune) {
		if (ConfigWorld.tileReplacementMode == -1)
			return Item.getItemFromBlock(ModBlocks.BEACON.get());
		return Item.getItemFromBlock(Blocks.beacon);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Item getItem(World world, int x, int y, int z) {
		if (ConfigWorld.tileReplacementMode == -1)
			return Item.getItemFromBlock(ModBlocks.BEACON.get());
		return Item.getItemFromBlock(Blocks.beacon);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityNewBeacon();
	}
}

