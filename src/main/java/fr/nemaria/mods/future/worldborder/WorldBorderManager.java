package fr.nemaria.mods.future.worldborder;

import net.minecraft.world.World;

public class WorldBorderManager {
    public static final WorldBorderManager INSTANCE = new WorldBorderManager();

    /** Récupère (ou crée) les données de la border pour le monde donné */
    public WorldBorderData getData(World world) {
        WorldBorderData data = (WorldBorderData) world.perWorldStorage.loadData(WorldBorderData.class, WorldBorderData.NAME);
        if (data == null) {
            data = new WorldBorderData();
            world.perWorldStorage.setData(WorldBorderData.NAME, data);
        }
        return data;
    }

    public boolean isActive(World world) {
        return getData(world).active;
    }

    public boolean isInside(World world, double x, double z) {
        WorldBorderData data = getData(world);
        return !data.active || data.isInside(x, z);
    }

    public void create(World world, double radius) {
        WorldBorderData data = getData(world);
        data.radius = radius;
        data.active = true;
        data.markDirty();
    }

    public void setCenter(World world, double x, double z) {
        WorldBorderData data = getData(world);
        data.centerX = x;
        data.centerZ = z;
        data.markDirty();
    }

    public void setRadius(World world, double radius) {
        WorldBorderData data = getData(world);
        data.radius = radius;
        data.markDirty();
    }

    public void remove(World world) {
        WorldBorderData data = getData(world);
        data.active = false;
        data.markDirty();
    }
}
