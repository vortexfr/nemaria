package fr.nemaria.mods.future.blocks;

import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.nemaria.mods.Nemaria;
import fr.nemaria.mods.future.ModBlocks;
import fr.nemaria.mods.future.configuration.configs.ConfigWorld;
import fr.nemaria.mods.future.lib.GUIIDs;
import net.minecraft.block.BlockEnchantmentTable;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.world.World;

public class BlockNewEnchantmentTable extends BlockEnchantmentTable {

	public BlockNewEnchantmentTable() {
		setHardness(5.0F);
		setResistance(2000.0F);
		setBlockTextureName("enchanting_table");
		setBlockName("enchantmentTable");
		setCreativeTab(CreativeTabs.tabBlock);
	}

	@Override
	public Item getItemDropped(int meta, Random rand, int fortune) {
		if (ConfigWorld.tileReplacementMode == -1)
			return Item.getItemFromBlock(ModBlocks.ENCHANTMENT_TABLE.get());
		return Item.getItemFromBlock(Blocks.enchanting_table);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Item getItem(World world, int x, int y, int z) {
		if (ConfigWorld.tileReplacementMode == -1)
			return Item.getItemFromBlock(ModBlocks.ENCHANTMENT_TABLE.get());
		return Item.getItemFromBlock(Blocks.enchanting_table);
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if (world.isRemote)
			return true;
		player.openGui(Nemaria.instance, GUIIDs.ENCHANTING_TABLE, world, x, y, z);
		return true;
	}
}

