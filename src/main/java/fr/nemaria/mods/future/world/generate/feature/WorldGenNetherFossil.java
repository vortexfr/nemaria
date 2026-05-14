package fr.nemaria.mods.future.world.generate.feature;

import java.util.List;
import java.util.Random;

import com.google.common.collect.Lists;

import fr.nemaria.mods.future.core.utils.helpers.BlockPos;
import fr.nemaria.mods.future.core.utils.structurenbt.EFRBlockStateConverter;
import fr.nemaria.mods.future.core.utils.structurenbt.NBTStructure;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class WorldGenNetherFossil extends WorldGenFossil {

	private final List<NBTStructure> fossils;

	public WorldGenNetherFossil() {
		fossils = Lists.newArrayList();
		for (int i = 1; i <= 14; i++) {
			fossils.add(new NBTStructure("/data/structure/nether_fossils/fossil_" + i + ".nbt", EFRBlockStateConverter.INSTANCE));
		}
	}

	@Override
	public boolean generate(World world, Random rand, int x, int y, int z) {
		NBTStructure fossil = fossils.get(rand.nextInt(fossils.size()));
		ForgeDirection dir = ForgeDirection.getOrientation(rand.nextInt(4) + 2);
		if (!canFossilGenerateHere(world, x, y, z, fossil.getSize(dir))) return false;
		fossil.placeStructure(world, rand, x, y, z, dir);
		return true;
	}

	protected boolean canFossilGenerateHere(World world, int x, int y, int z, BlockPos corners) {
		int air = 0;
		if (!validCorner(world, x, y, z)) {
			air++;
		}
		if (!validCorner(world, x + corners.getX(), y, z)) {
			air++;
		}
		if (!validCorner(world, x, y, z + corners.getZ())) {
			air++;
		}
		if (!validCorner(world, x + corners.getX(), y, z + corners.getZ())) {
			if (air++ >= 3) return false;
		}
		if (!validCorner(world, x + (corners.getX() >> 1), y, z + (corners.getZ() >> 1))) {
			if (air++ >= 3) return false;
		}
		return air <= 3;
	}

	@Override
	protected boolean validCorner(World world, int x, int y, int z) {
		return world.getBlock(x, y - 1, z).isOpaqueCube() && world.isAirBlock(x, y, z);
	}
}
