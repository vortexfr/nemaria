package fr.nemaria.mods.future.world.nether.biome;

import fr.nemaria.mods.future.ModBlocks;
import fr.nemaria.mods.future.world.nether.biome.decorator.BasaltDeltasDecorator;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.EntityMagmaCube;

public class BiomeBasaltDeltas extends NetherBiomeBase {
	public BiomeBasaltDeltas(int id) {
		super(id, new BasaltDeltasDecorator());
		fogSkyColor = 0x534868;
		topBlock = ModBlocks.BLACKSTONE.get();
		fillerBlock = ModBlocks.BASALT.get();

		spawnableMonsterList.clear();
		this.spawnableMonsterList.add(new SpawnListEntry(EntityGhast.class, 40, 1, 1));
		this.spawnableMonsterList.add(new SpawnListEntry(EntityMagmaCube.class, 100, 2, 5));
	}
}
