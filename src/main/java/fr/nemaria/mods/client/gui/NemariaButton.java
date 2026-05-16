package fr.nemaria.mods.client.gui;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class NemariaButton extends GuiButton {

    private static final ResourceLocation BUTTON_NORMAL = new ResourceLocation("nemaria", "textures/gui/button/button.png");
    private static final ResourceLocation BUTTON_HOVER  = new ResourceLocation("nemaria", "textures/gui/button/button_hover.png");

    private static final int HEIGHT = 20;

    private int textColor;

    /** Largeur automatique selon le texte */
    public NemariaButton(int id, int x, int y, String text) {
        this(id, x, y, -1, text, 0xFFFFFF);
    }

    /** Largeur fixe imposée */
    public NemariaButton(int id, int x, int y, int fixedWidth, String text) {
        this(id, x, y, fixedWidth, text, 0xFFFFFF);
    }

    /** Largeur fixe + couleur de texte custom */
    public NemariaButton(int id, int x, int y, int fixedWidth, String text, int textColor) {
        super(id, x, y, fixedWidth > 0 ? fixedWidth : autoWidth(Minecraft.getMinecraft(), text), HEIGHT, text);
        this.textColor = textColor;
    }

    private static int autoWidth(Minecraft mc, String text) {
        return mc.fontRenderer.getStringWidth(text) + 32;
    }

    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY) {
        if (!this.visible) return;

        // Ne recalcule que si largeur auto (width très petite = probablement pas fixée)

        boolean hovered = mouseX >= this.xPosition
                       && mouseY >= this.yPosition
                       && mouseX <  this.xPosition + this.width
                       && mouseY <  this.yPosition + this.height;

        this.field_146123_n = hovered;

        GL11.glColor4f(1f, 1f, 1f, 1f);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

        // Texture pleine étirée sur toute la surface du bouton
        mc.getTextureManager().bindTexture(hovered ? BUTTON_HOVER : BUTTON_NORMAL);
        drawTexturedRect(this.xPosition, this.yPosition, this.width, this.height);

        GL11.glDisable(GL11.GL_BLEND);

        // Texte centré avec couleur custom
        int color = this.enabled ? textColor : 0xA0A0A0;
        this.drawCenteredString(
            mc.fontRenderer,
            this.displayString,
            this.xPosition + this.width / 2,
            this.yPosition + (this.height - 8) / 2,
            color
        );
    }

    /** Dessine un quad texturé de 0,0 à 1,1 en UV */
    private static void drawTexturedRect(int x, int y, int w, int h) {
        Tessellator t = Tessellator.instance;
        t.startDrawingQuads();
        t.addVertexWithUV(x,     y + h, 0,  0, 1);
        t.addVertexWithUV(x + w, y + h, 0,  1, 1);
        t.addVertexWithUV(x + w, y,     0,  1, 0);
        t.addVertexWithUV(x,     y,     0,  0, 0);
        t.draw();
    }
}
