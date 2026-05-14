package fr.nemaria.mods.future.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.nemaria.mods.future.api.HoeRegistry;
import fr.nemaria.mods.future.client.sound.ModSounds;
import fr.nemaria.mods.future.configuration.configs.ConfigBlocksItems;
import fr.nemaria.mods.future.configuration.configs.ConfigExperiments;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.IBlockAccess;

import java.util.List;

public class BlockNetherwart extends BaseSubtypesBlock {

	public BlockNetherwart() {
		super(Material.grass, "nether_wart_block", "warped_wart_block");
		setHardness(1F);
		setResistance(5F);
		setBlockSound(ModSounds.soundWartBlock);
		setNames("wart_block");
		setCreativeTab(CreativeTabs.tabBlock);
		HoeRegistry.addToHoeArray(this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item item, CreativeTabs tab, List list) {
		if (ConfigBlocksItems.enableNetherwartBlock) {
			list.add(new ItemStack(item, 1, 0));
		}
		if (ConfigExperiments.enableWarpedBlocks) {
			list.add(new ItemStack(item, 1, 1));
		}
	}

	@Override
	public boolean isLeaves(IBlockAccess world, int x, int y, int z) {
		return true;
	}
}


