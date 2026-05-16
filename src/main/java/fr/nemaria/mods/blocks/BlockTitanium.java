package fr.nemaria.mods.blocks;

import fr.nemaria.mods.init.NemariaTab;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockTitanium extends Block {

    public BlockTitanium() {
        super(Material.iron);
        this.setBlockTextureName("nemaria:Titane Block");
        this.setBlockName("titane_block");
        this.setHardness(5.0F);
        this.setResistance(10.0F);
        this.setHarvestLevel("pickaxe", 3);
        this.setCreativeTab(NemariaTab.INSTANCE);
    }
}
