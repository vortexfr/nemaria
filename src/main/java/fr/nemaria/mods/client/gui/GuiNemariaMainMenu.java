package fr.nemaria.mods.client.gui;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.nemaria.mods.utils.References;
import net.minecraft.client.gui.*;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class GuiNemariaMainMenu extends GuiScreen {

    private static final ResourceLocation BACKGROUND = new ResourceLocation("nemaria", "textures/gui/menu/background.png");
    private static final ResourceLocation LOGO = new ResourceLocation("nemaria", "textures/gui/menu/nemaria.png");
    private static final int LOGO_W = 200;
    private static final int LOGO_H = 54;
    private static final int BTN_PLAY = 0;
    private static final int BTN_SOLO = 1;
    private static final int BTN_OPTIONS = 2;
    private static final int BTN_QUIT = 3;

    @SuppressWarnings("unchecked")
	@Override
    public void initGui() {
        this.buttonList.clear();
        int leftX = 30;
        int btnWidth = 120;
        int startY = this.height / 2 - 40;
        int spacing  = 28;
        this.buttonList.add(new NemariaButton(BTN_PLAY, leftX, startY, btnWidth, "Jouer", 0xFF4444));
        this.buttonList.add(new NemariaButton(BTN_SOLO, leftX, startY + spacing, btnWidth, "Solo"));
        this.buttonList.add(new NemariaButton(BTN_OPTIONS, leftX, startY + spacing * 2, btnWidth, "Options"));
        this.buttonList.add(new NemariaButton(BTN_QUIT, leftX, startY + spacing * 3, btnWidth, "Quitter"));
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        switch (button.id) {
            case BTN_PLAY:
                this.mc.displayGuiScreen(new GuiMultiplayer(this));
                break;
            case BTN_SOLO:
                this.mc.displayGuiScreen(new GuiSelectWorld(this));
                break;
            case BTN_OPTIONS:
                this.mc.displayGuiScreen(new GuiOptions(this, this.mc.gameSettings));
                break;
            case BTN_QUIT:
                this.mc.shutdown();
                break;
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glColor4f(1f, 1f, 1f, 1f);
        this.mc.getTextureManager().bindTexture(BACKGROUND);
        Tessellator t = Tessellator.instance;
        t.startDrawingQuads();
        t.addVertexWithUV(0, this.height, 0,  0, 1);
        t.addVertexWithUV(this.width, this.height, 0,  1, 1);
        t.addVertexWithUV(this.width, 0, 0,  1, 0);
        t.addVertexWithUV(0, 0,  0,  0, 0);
        t.draw();
        GL11.glDisable(GL11.GL_BLEND);
        int logoX = (this.width - LOGO_W) / 2;
        int logoY = 4;
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glColor4f(1f, 1f, 1f, 1f);
        this.mc.getTextureManager().bindTexture(LOGO);
        Tessellator tl = Tessellator.instance;
        tl.startDrawingQuads();
        tl.addVertexWithUV(logoX, logoY + LOGO_H, 0,  0, 1);
        tl.addVertexWithUV(logoX + LOGO_W, logoY + LOGO_H, 0,  1, 1);
        tl.addVertexWithUV(logoX + LOGO_W, logoY, 0,  1, 0);
        tl.addVertexWithUV(logoX, logoY, 0,  0, 0);
        tl.draw();
        GL11.glDisable(GL11.GL_BLEND);
        String forge = "Version " + References.VERSION;
        String mojang = "Minecraft By Mojang Studios";

        int padRight = 6;
        int padBot = 6;

        this.fontRendererObj.drawStringWithShadow(
            mojang,
            this.width - this.fontRendererObj.getStringWidth(mojang) - padRight,
            this.height - this.fontRendererObj.FONT_HEIGHT * 2 - padBot,
            0xAAAAAA
        );
        this.fontRendererObj.drawStringWithShadow(
            forge,
            this.width - this.fontRendererObj.getStringWidth(forge) - padRight,
            this.height - this.fontRendererObj.FONT_HEIGHT - padBot,
            0x888888
        );

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }
}
