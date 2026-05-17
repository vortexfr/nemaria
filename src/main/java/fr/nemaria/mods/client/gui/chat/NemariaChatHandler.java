package fr.nemaria.mods.client.gui.chat;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.ReflectionHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiSleepMP;
import net.minecraftforge.client.event.GuiOpenEvent;


@SideOnly(Side.CLIENT)
public class NemariaChatHandler {
    public static final NemariaChatHandler INSTANCE = new NemariaChatHandler();

    private boolean replacedChat = false;

    @SubscribeEvent
    public void onGuiOpen(GuiOpenEvent event) {
        GuiScreen screen = event.gui;
        Minecraft mc = Minecraft.getMinecraft();

        if (screen instanceof GuiMainMenu) {
            if (!replacedChat) {
                ReflectionHelper.setPrivateValue(
                    GuiIngame.class, mc.ingameGUI,
                    new MultilineChatScreen(),
                    "persistantChatGUI", "field_73840_e"
                );
                replacedChat = true;
            }
        } else if (screen instanceof GuiChat && !(screen instanceof INemariaChat)) {
            event.setCanceled(true);
            if (screen instanceof GuiSleepMP) {
                mc.displayGuiScreen(new MPChatScreen());
            } else {
                mc.displayGuiScreen(new ChatScreen((GuiChat) screen));
            }
        }
    }
}
