package fr.nemaria.mods.future.worldborder;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.EnderTeleportEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;

public class WorldBorderEventHandler {
    public static final WorldBorderEventHandler INSTANCE = new WorldBorderEventHandler();

    /** Tick entre chaque message d'avertissement (en ticks serveur) */
    private static final int WARN_INTERVAL = 60;

    // ── Bloque la pose de blocs en dehors ─────────────────────────────────

    @SubscribeEvent
    public void onBlockPlace(BlockEvent.PlaceEvent event) {
        if (!WorldBorderManager.INSTANCE.isActive(event.world)) return;
        if (!WorldBorderManager.INSTANCE.isInside(event.world, event.x, event.z)) {
            event.setCanceled(true);
            if (event.player != null)
                notify(event.player, "§cImpossible de poser des blocs en dehors de la bordure !");
        }
    }

    // ── Bloque l'utilisation des enderpearls ───────────────────────────────

    @SubscribeEvent
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.world.isRemote) return;
        if (!WorldBorderManager.INSTANCE.isActive(event.world)) return;
        if (event.action != PlayerInteractEvent.Action.RIGHT_CLICK_AIR
            && event.action != PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK) return;

        EntityPlayer player = event.entityPlayer;
        if (player.getHeldItem() == null) return;
        if (player.getHeldItem().getItem() instanceof net.minecraft.item.ItemEnderPearl) {
            if (!WorldBorderManager.INSTANCE.isInside(event.world, player.posX, player.posZ)) {
                event.setCanceled(true);
                notify(player, "§cImpossible de lancer une enderpearl depuis l'extérieur de la bordure !");
            }
        }
    }

    // ── Bloque la téléportation (enderpearl/tp) vers l'extérieur ──────────

    @SubscribeEvent
    public void onEnderTeleport(EnderTeleportEvent event) {
        if (!WorldBorderManager.INSTANCE.isActive(event.entityLiving.worldObj)) return;
        if (!WorldBorderManager.INSTANCE.isInside(event.entityLiving.worldObj, event.targetX, event.targetZ)) {
            event.setCanceled(true);
            if (event.entityLiving instanceof EntityPlayer)
                notify((EntityPlayer) event.entityLiving, "§cImpossible de se téléporter en dehors de la bordure !");
        }
    }

    // ── Bloque et repousse les joueurs (vol + marche) ─────────────────────

    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;
        EntityPlayer player = event.player;
        if (player.worldObj.isRemote) return;

        World world = player.worldObj;
        if (!WorldBorderManager.INSTANCE.isActive(world)) return;

        WorldBorderData data = WorldBorderManager.INSTANCE.getData(world);

        double px = player.posX;
        double pz = player.posZ;

        // Clamp la position à l'intérieur de la bordure
        double clampedX = clamp(px, data.centerX - data.radius + 1, data.centerX + data.radius - 1);
        double clampedZ = clamp(pz, data.centerZ - data.radius + 1, data.centerZ + data.radius - 1);

        boolean outsideX = clampedX != px;
        boolean outsideZ = clampedZ != pz;

        if (outsideX || outsideZ) {
            // Téléporte le joueur à l'intérieur (fonctionne en vol aussi)
            if (player instanceof EntityPlayerMP) {
                EntityPlayerMP mp = (EntityPlayerMP) player;
                mp.setPositionAndUpdate(clampedX, player.posY, clampedZ);
                if (outsideX) player.motionX = 0;
                if (outsideZ) player.motionZ = 0;

                if (player.ticksExisted % WARN_INTERVAL == 0) {
                    notify(player, "§cVous avez atteint la limite de la bordure du monde !");
                }
            }
        } else {
            // Avertissement à 2 blocs de la bordure (seulement toutes les 2s)
            double distX = data.radius - Math.abs(player.posX - data.centerX);
            double distZ = data.radius - Math.abs(player.posZ - data.centerZ);
            double closestDist = Math.min(distX, distZ);

            if (closestDist <= 2.0 && player.ticksExisted % WARN_INTERVAL == 0) {
                notify(player, "§e⚠ §6Attention ! §eVous approchez de la bordure du monde !");
            }
        }
    }

    private double clamp(double value, double min, double max) {
        return Math.max(min, Math.min(max, value));
    }

    private void notify(EntityPlayer player, String msg) {
        player.addChatMessage(new ChatComponentText(msg));
    }
}
