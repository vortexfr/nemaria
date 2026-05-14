package fr.nemaria.mods.future.items;

import fr.nemaria.mods.future.core.utils.Utils;
import fr.nemaria.mods.future.entities.EntityPlacedEndCrystal;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemSimpleFoiled;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemEndCrystal extends ItemSimpleFoiled {

	public ItemEndCrystal() {
		setTextureName("end_crystal");
		setUnlocalizedName(Utils.getUnlocalisedName("end_crystal"));
		setCreativeTab(CreativeTabs.tabDecorations);
	}

	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
		if (side != 1)
			return false;

		Block block = world.getBlock(x, y, z);
		if (block == Blocks.obsidian || block == Blocks.bedrock)
			if (world.isAirBlock(x, y + 1, z)) {
				if (!world.isRemote) {
					EntityPlacedEndCrystal endCrystal = new EntityPlacedEndCrystal(world);
					endCrystal.setPosition(x + 0.5, y, z + 0.5);
					endCrystal.setBlockPos(x, y, z);

					world.spawnEntityInWorld(endCrystal);
					if (!player.capabilities.isCreativeMode)
						stack.stackSize--;
				}
				return true;
			}

		return false;
	}
}