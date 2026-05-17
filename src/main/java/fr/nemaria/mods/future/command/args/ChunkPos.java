package fr.nemaria.mods.future.command.args;

public class ChunkPos {
    public final int x;
    public final int z;

    public ChunkPos(long longIn) {
        this.x = (int) longIn;
        this.z = (int) (longIn >> 32);
    }

    public ChunkPos(int x, int z) {
        this.x = x;
        this.z = z;
    }

    public ChunkPos offset(int xOff, int zOff) { return new ChunkPos(x + xOff, z + zOff); }

    public static long asLong(int x, int z) { return x & 4294967295L | (z & 4294967295L) << 32; }
    public static int  getX(long pos)       { return (int) pos; }
    public static int  getZ(long pos)       { return (int) (pos >> 32); }
    public static long add(long pos, int x, int z) { return asLong(x + getX(pos), z + getZ(pos)); }

    public long     asLong()                   { return asLong(x, z); }
    public ChunkPos toChunkPos()               { return new ChunkPos(x >> 4, z >> 4); }
    public ChunkPos toChunkFile()              { return new ChunkPos(x >> 5, z >> 5); }

    public int    getDistance(int xPos, int zPos)    { int dx = x - xPos, dz = z - zPos; return dx*dx + dz*dz; }
    public double getSqDistance(int xPos, int zPos)  { return Math.sqrt(getDistance(xPos, zPos)); }
    public int    getSquDistane(ChunkPos pos)         { return (int) getSqDistance(pos.x, pos.z); }

    @Override
    public int hashCode() {
        int i = 1664525 * x + 1013904223;
        int j = 1664525 * (z ^ -559038737) + 1013904223;
        return i ^ j;
    }

    @Override
    public boolean equals(Object arg) {
        if (arg instanceof ChunkPos) { ChunkPos p = (ChunkPos) arg; return p.x == x && p.z == z; }
        return false;
    }

    @Override public String toString() { return "X: " + x + " Z: " + z; }
}
