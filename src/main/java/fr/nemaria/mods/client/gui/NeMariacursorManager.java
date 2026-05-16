package fr.nemaria.mods.client.gui;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.*;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.GuiScreenEvent;
import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Cursor;
import org.lwjgl.input.Mouse;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.nio.IntBuffer;
import java.util.List;

@SideOnly(Side.CLIENT)
public class NeMariacursorManager {

    public static final NeMariacursorManager INSTANCE = new NeMariacursorManager();

    @SuppressWarnings("unused")
	private static final String BASE = "nemaria:textures/gui/cursor/";

    private Cursor curDefault = null;
    private Cursor curHand = null;
    private Cursor curHandGrab = null;
    private Cursor curBeam = null;
    private Cursor curClick = null; // animé click_0/1/2
    private Cursor currentCursor = null;
    private Cursor systemDefault = null;
    private boolean loaded = false;
    private boolean  wasPressed = false;
    private long clickTime = 0L;
    private static final long CLICK_DURATION_MS = 150;

    private NeMariacursorManager() {}


    private void load() {
        if (loaded) return;
        loaded = true;
        try {
            systemDefault = Mouse.getNativeCursor();
            curDefault  = loadCursor("customcursor.png",       0);
            curHand = loadCursor("customcursor_hand.png",  0);
            curHandGrab = loadCursor("customcursor_hand_grab.png", 0);
            curBeam = loadCursor("customcursor_beam.png",  0);
            loadCursor("customcursor_cross.png", 0);
            curClick = buildClickAnimation();
        } catch (Exception e) {
            System.err.println("[Nemaria] Erreur chargement curseurs : " + e.getMessage());
        }
    }

    private Cursor loadCursor(String file, int hotX) throws Exception {
        ResourceLocation rl = new ResourceLocation("nemaria", "textures/gui/cursor/" + file);
        InputStream is = Minecraft.getMinecraft().getResourceManager().getResource(rl).getInputStream();
        BufferedImage img = ImageIO.read(is);
        is.close();
        return toNativeCursor(img, hotX, img.getHeight() - 1);
    }

    private Cursor buildClickAnimation() {
        try {
            BufferedImage[] frames = new BufferedImage[3];
            for (int i = 0; i < 3; i++) {
                ResourceLocation rl = new ResourceLocation("nemaria", "textures/gui/cursor/click_" + i + ".png");
                InputStream is = Minecraft.getMinecraft().getResourceManager().getResource(rl).getInputStream();
                frames[i] = ImageIO.read(is);
                is.close();
            }
            int w = frames[0].getWidth();
            int h = frames[0].getHeight();
            int total = w * h * 3;
            IntBuffer buf = BufferUtils.createIntBuffer(total);
            int[] delays = new int[3];

            for (BufferedImage frame : frames) {
                for (int y = h - 1; y >= 0; y--) {
                    for (int x = 0; x < w; x++) {
                        buf.put(frame.getRGB(x, y));
                    }
                }
            }
            buf.flip();
            delays[0] = 50;
            delays[1] = 50;
            delays[2] = 50;

            IntBuffer delayBuf = BufferUtils.createIntBuffer(3);
            for (int d : delays) delayBuf.put(d);
            delayBuf.flip();

            return new Cursor(w, h, 0, h - 1, 3, buf, delayBuf);
        } catch (Exception e) {
            System.err.println("[Nemaria] Curseur click animé non chargé : " + e.getMessage());
            return curDefault;
        }
    }

    private Cursor toNativeCursor(BufferedImage img, int hotX, int hotY) throws Exception, LWJGLException {
        int w = img.getWidth();
        int h = img.getHeight();
        IntBuffer buf = BufferUtils.createIntBuffer(w * h);
        for (int y = h - 1; y >= 0; y--) {
            for (int x = 0; x < w; x++) {
                buf.put(img.getRGB(x, y));
            }
        }
        buf.flip();
        return new Cursor(w, h, hotX, hotY, 1, buf, null);
    }


    @SubscribeEvent
    public void onGuiDraw(GuiScreenEvent.DrawScreenEvent.Post event) {
        load();

        Minecraft mc = Minecraft.getMinecraft();
        int mouseX = event.mouseX;
        int mouseY = event.mouseY;
        boolean lmbDown = Mouse.isButtonDown(0);

        if (lmbDown && !wasPressed) {
            clickTime = System.currentTimeMillis();
        }
        wasPressed = lmbDown;

        boolean showingClick = (System.currentTimeMillis() - clickTime) < CLICK_DURATION_MS;
        Cursor target = curDefault;

        if (showingClick) {
            target = (curClick != null) ? curClick : curDefault;
        } else {
            GuiScreen gui = mc.currentScreen;
            if (gui != null) {
                if (isOverTextField(gui, mouseX, mouseY)) {
                    target = (curBeam != null) ? curBeam : curDefault;
                }
                else if (isOverButton(gui, mouseX, mouseY, lmbDown)) {
                    target = lmbDown
                        ? ((curHandGrab != null) ? curHandGrab : curHand)
                        : ((curHand != null) ? curHand : curDefault);
                }
            }
        }

        setCursor(target);
    }

    @SubscribeEvent
    public void onGuiClose(GuiScreenEvent.InitGuiEvent.Pre event) {
        if (event.gui == null) {
            setCursor(systemDefault);
        }
    }

    private static Field buttonListField = null;

    @SuppressWarnings("unchecked")
    private boolean isOverButton(GuiScreen gui, int mx, int my, boolean pressed) {
        try {
            if (buttonListField == null) {
                buttonListField = GuiScreen.class.getDeclaredField("buttonList");
                buttonListField.setAccessible(true);
            }
            List<GuiButton> buttons = (List<GuiButton>) buttonListField.get(gui);
            if (buttons == null) return false;
            for (GuiButton btn : buttons) {
                if (!btn.visible || !btn.enabled) continue;
                if (mx >= btn.xPosition && mx < btn.xPosition + btn.width
                 && my >= btn.yPosition && my < btn.yPosition + btn.height) {
                    return true;
                }
            }
        } catch (Exception e) {
            // Ignore
        }
        return false;
    }

    private boolean isOverTextField(GuiScreen gui, int mx, int my) {
        return gui instanceof GuiChat
            || gui instanceof GuiCreateWorld
            || gui instanceof GuiRenameWorld;
    }

    private void setCursor(Cursor c) {
        if (c == null || c == currentCursor) return;
        try {
            Mouse.setNativeCursor(c);
            currentCursor = c;
        } catch (LWJGLException ignored) {}
    }
}
