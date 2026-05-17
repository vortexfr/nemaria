package fr.nemaria.mods.future.worldborder.client;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import org.lwjgl.opengl.GL11;
import fr.nemaria.mods.future.worldborder.WorldBorderData;
import fr.nemaria.mods.future.worldborder.WorldBorderManager;

@SideOnly(Side.CLIENT)
public class WorldBorderClientHandler {
    public static final WorldBorderClientHandler INSTANCE = new WorldBorderClientHandler();

    private static final ResourceLocation FORCEFIELD_TEX =
        new ResourceLocation("nemaria", "textures/misc/forcefield.png");

    /** La texture se répète tous les N blocs */
    private static final double TEX_TILE   = 8.0;
    /** Distance max de visibilité depuis le bord */
    private static final double VISIBLE_DIST = 96.0;

    @SubscribeEvent
    public void onRenderWorldLast(RenderWorldLastEvent event) {
        Minecraft mc = Minecraft.getMinecraft();
        if (mc.theWorld == null || mc.thePlayer == null) return;

        WorldBorderData data = WorldBorderManager.INSTANCE.getData(mc.theWorld);
        if (!data.active) return;

        double camX = mc.thePlayer.lastTickPosX + (mc.thePlayer.posX - mc.thePlayer.lastTickPosX) * event.partialTicks;
        double camY = mc.thePlayer.lastTickPosY + (mc.thePlayer.posY - mc.thePlayer.lastTickPosY) * event.partialTicks;
        double camZ = mc.thePlayer.lastTickPosZ + (mc.thePlayer.posZ - mc.thePlayer.lastTickPosZ) * event.partialTicks;

        double distX = data.radius - Math.abs(camX - data.centerX);
        double distZ = data.radius - Math.abs(camZ - data.centerZ);
        double dist  = Math.min(distX, distZ);

        if (dist > VISIBLE_DIST) return;

        float alpha = (float) Math.min(0.75, Math.max(0.05,
            (VISIBLE_DIST - Math.max(dist, -16)) / VISIBLE_DIST * 0.75));

        renderBorder(data, camX, camY, camZ, alpha);
    }

    private void renderBorder(WorldBorderData data, double camX, double camY, double camZ, float alpha) {
        // Coordonnées relatives à la caméra
        double minX = data.centerX - data.radius - camX;
        double maxX = data.centerX + data.radius - camX;
        double minZ = data.centerZ - data.radius - camZ;
        double maxZ = data.centerZ + data.radius - camZ;

        // Mur de Y=0 à Y=255
        double yBot = 0   - camY;
        double yTop = 255 - camY;

        // UVs : combien de fois la texture se répète sur chaque mur
        double uWidth  = (data.radius * 2) / TEX_TILE;
        double vHeight = 255.0 / TEX_TILE;

        GL11.glPushMatrix();

        // État GL
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_CULL_FACE);
        GL11.glDepthMask(false);
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_FOG);

        // Lier la texture
        Minecraft.getMinecraft().getTextureManager().bindTexture(FORCEFIELD_TEX);

        // IMPORTANT : forcer GL_REPEAT pour que la texture se répète (pas GL_CLAMP)
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_REPEAT);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_REPEAT);

        // Couleur teinte + alpha
        GL11.glColor4f(1.0f, 0.3f, 0.3f, alpha);

        Tessellator tess = Tessellator.instance;
        tess.startDrawingQuads();

        // ─── Nord : z = centerZ - radius ─────────────────────────────────
        tess.addVertexWithUV(minX, yBot, minZ,    0,       0);
        tess.addVertexWithUV(minX, yTop, minZ,    0,       vHeight);
        tess.addVertexWithUV(maxX, yTop, minZ,    uWidth,  vHeight);
        tess.addVertexWithUV(maxX, yBot, minZ,    uWidth,  0);
        // face intérieure
        tess.addVertexWithUV(maxX, yBot, minZ,    uWidth,  0);
        tess.addVertexWithUV(maxX, yTop, minZ,    uWidth,  vHeight);
        tess.addVertexWithUV(minX, yTop, minZ,    0,       vHeight);
        tess.addVertexWithUV(minX, yBot, minZ,    0,       0);

        // ─── Sud : z = centerZ + radius ──────────────────────────────────
        tess.addVertexWithUV(maxX, yBot, maxZ,    0,       0);
        tess.addVertexWithUV(maxX, yTop, maxZ,    0,       vHeight);
        tess.addVertexWithUV(minX, yTop, maxZ,    uWidth,  vHeight);
        tess.addVertexWithUV(minX, yBot, maxZ,    uWidth,  0);
        tess.addVertexWithUV(minX, yBot, maxZ,    uWidth,  0);
        tess.addVertexWithUV(minX, yTop, maxZ,    uWidth,  vHeight);
        tess.addVertexWithUV(maxX, yTop, maxZ,    0,       vHeight);
        tess.addVertexWithUV(maxX, yBot, maxZ,    0,       0);

        // ─── Ouest : x = centerX - radius ────────────────────────────────
        tess.addVertexWithUV(minX, yBot, maxZ,    0,       0);
        tess.addVertexWithUV(minX, yTop, maxZ,    0,       vHeight);
        tess.addVertexWithUV(minX, yTop, minZ,    uWidth,  vHeight);
        tess.addVertexWithUV(minX, yBot, minZ,    uWidth,  0);
        tess.addVertexWithUV(minX, yBot, minZ,    uWidth,  0);
        tess.addVertexWithUV(minX, yTop, minZ,    uWidth,  vHeight);
        tess.addVertexWithUV(minX, yTop, maxZ,    0,       vHeight);
        tess.addVertexWithUV(minX, yBot, maxZ,    0,       0);

        // ─── Est : x = centerX + radius ──────────────────────────────────
        tess.addVertexWithUV(maxX, yBot, minZ,    0,       0);
        tess.addVertexWithUV(maxX, yTop, minZ,    0,       vHeight);
        tess.addVertexWithUV(maxX, yTop, maxZ,    uWidth,  vHeight);
        tess.addVertexWithUV(maxX, yBot, maxZ,    uWidth,  0);
        tess.addVertexWithUV(maxX, yBot, maxZ,    uWidth,  0);
        tess.addVertexWithUV(maxX, yTop, maxZ,    uWidth,  vHeight);
        tess.addVertexWithUV(maxX, yTop, minZ,    0,       vHeight);
        tess.addVertexWithUV(maxX, yBot, minZ,    0,       0);

        tess.draw();

        // Restaurer l'état GL
        GL11.glDepthMask(true);
        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_FOG);
        GL11.glColor4f(1f, 1f, 1f, 1f);
        GL11.glPopMatrix();
    }
}
