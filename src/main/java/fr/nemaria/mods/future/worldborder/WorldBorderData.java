package fr.nemaria.mods.future.worldborder;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.WorldSavedData;

/**
 * Données de la WorldBorder sauvegardées dans le monde (NBT).
 * Une instance par dimension.
 */
public class WorldBorderData extends WorldSavedData {
    public static final String NAME = "nemaria_worldborder";

    public boolean active   = false;
    public double  centerX  = 0;
    public double  centerZ  = 0;
    public double  radius   = 1000;

    public WorldBorderData()            { super(NAME); }
    public WorldBorderData(String name) { super(name); }

    @Override
    public void readFromNBT(NBTTagCompound tag) {
        active  = tag.getBoolean("active");
        centerX = tag.getDouble("centerX");
        centerZ = tag.getDouble("centerZ");
        radius  = tag.getDouble("radius");
    }

    @Override
    public void writeToNBT(NBTTagCompound tag) {
        tag.setBoolean("active",  active);
        tag.setDouble("centerX",  centerX);
        tag.setDouble("centerZ",  centerZ);
        tag.setDouble("radius",   radius);
    }

    /** Retourne true si la position (x, z) est à l'intérieur de la bordure */
    public boolean isInside(double x, double z) {
        return Math.abs(x - centerX) <= radius && Math.abs(z - centerZ) <= radius;
    }

    public boolean isActive() { return active; }
}
