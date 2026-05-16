package fr.nemaria.mods.client;

import java.lang.management.ManagementFactory;
import java.util.List;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.client.event.RenderGameOverlayEvent;

@SideOnly(Side.CLIENT)
public class NemariaDebugOverlay {

    public static final NemariaDebugOverlay INSTANCE = new NemariaDebugOverlay();

    private NemariaDebugOverlay() {}

    @SubscribeEvent
    public void onDebugText(RenderGameOverlayEvent.Text event) {
        Minecraft mc = Minecraft.getMinecraft();

        // Seulement si le F3 est ouvert
        if (!mc.gameSettings.showDebugInfo) return;

        List<String> left  = event.left;
        List<String> right = event.right;

        // ── Vider et reconstruire la liste gauche ─────────────────────────────
        left.clear();

        EntityPlayer player = mc.thePlayer;
        World world = mc.theWorld;

        if (player == null || world == null) return;

        int x = MathHelper.floor_double(player.posX);
        int y = MathHelper.floor_double(player.posY);
        int z = MathHelper.floor_double(player.posZ);

        BiomeGenBase biome = world.getBiomeGenForCoords(x, z);
        String biomeName   = biome != null ? biome.biomeName : "Inconnu";

        int chunkX = x >> 4;
        int chunkZ = z >> 4;

        String dim;
        switch (world.provider.dimensionId) {
            case -1: dim = "Nether";    break;
            case  1: dim = "End";       break;
            default: dim = "Overworld"; break;
        }

        left.add(EnumChatFormatting.BOLD + "§5Nemaria (Release)");
        left.add("");
        left.add("§eCoordonnées : §f" + x + " / " + y + " / " + z);
        left.add("§eChunk : §f" + chunkX + " " + chunkZ
                + " §7(local " + (x & 15) + " " + (y & 15) + " " + (z & 15) + ")");
        left.add("");
        left.add("§eBiome : §f" + biomeName);
        left.add("");
        // mc.debug contient "120 fps, 12 chunk updates"
        String fps = mc.debug.contains(" ") ? mc.debug.split(" ")[0] : mc.debug;
        left.add("§eFPS : §f" + fps);

        // ── Vider et reconstruire la liste droite ─────────────────────────────
        right.clear();

        Runtime runtime = Runtime.getRuntime();
        long maxMem  = runtime.maxMemory()  / 1024 / 1024;
        long totalMem = runtime.totalMemory() / 1024 / 1024;
        long freeMem = runtime.freeMemory()  / 1024 / 1024;
        long usedMem = totalMem - freeMem;

        String javaVersion = System.getProperty("java.version");
        int cpuCount = runtime.availableProcessors();

        // CPU name via ManagementFactory (marche sur HotSpot)
        String osName = System.getProperty("os.name");
        String osArch = System.getProperty("os.arch");

        right.add("§5§lSystème");
        right.add("");
        right.add("§eJava : §f" + javaVersion);
        right.add("§eCPU : §f" + cpuCount + " cœurs");
        right.add("§eOS : §f" + osName + " (" + osArch + ")");
        right.add("");
        right.add("§5§lMémoire");
        right.add("");
        right.add("§eUtilisée : §f" + usedMem + " MB / " + maxMem + " MB");
        right.add("§eAllouée : §f" + totalMem + " MB");
        right.add("§eLibre : §f" + freeMem + " MB");
    }
}
