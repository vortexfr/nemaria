package fr.nemaria.mods.future.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.nemaria.mods.Nemaria;
import fr.nemaria.mods.future.core.utils.Utils;
import fr.nemaria.mods.future.lib.GUIIDs;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class BlockSmithingTable extends Block {

	@SideOnly(Side.CLIENT)
	private IIcon topIcon;
	@SideOnly(Side.CLIENT)
	private IIcon sideIcon;
	@SideOnly(Side.CLIENT)
	private IIcon bottomIcon;

	public BlockSmithingTable() {
		super(Material.wood);
		this.setStepSound(soundTypeWood);
		this.setHardness(2.5F);
		this.setHarvestLevel("axe", 0);
		this.setResistance(2.5F);
		this.setBlockName(Utils.getUnlocalisedName("smithing_table"));
		this.setBlockTextureName("smithing_table");
		this.setCreativeTab(CreativeTabs.tabBlock);
	}

	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int p_149691_1_, int p_149691_2_) {
		return p_149691_1_ == 1 ? this.topIcon : (p_149691_1_ == 0 ? bottomIcon : (p_149691_1_ != 2 && p_149691_1_ != 3 ? this.blockIcon : this.sideIcon));
	}

	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister p_149651_1_) {
		this.blockIcon = p_149651_1_.registerIcon(this.getTextureName() + "_side");
		this.topIcon = p_149651_1_.registerIcon(this.getTextureName() + "_top");
		this.sideIcon = p_149651_1_.registerIcon(this.getTextureName() + "_front");
		this.bottomIcon = p_149651_1_.registerIcon(this.getTextureName() + "_bottom");
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float subX, float subY, float subZ) {
		if (!world.isRemote)
			player.openGui(Nemaria.instance, GUIIDs.SMITHING_TABLE, world, x, y, z);
		return true;
	}
}


