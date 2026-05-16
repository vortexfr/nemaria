package fr.nemaria.mods.blocks;

import fr.nemaria.mods.init.NemariaTab;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockTitaniumOre extends Block {

    public BlockTitaniumOre() {
        super(Material.rock);
        this.setBlockTextureName("nemaria:titane_ore");
        this.setBlockName("titane_ore");
        this.setHardness(3.0F);
        this.setResistance(5.0F);
        this.setHarvestLevel("pickaxe", 3);
        this.setCreativeTab(NemariaTab.INSTANCE);
    }
}
