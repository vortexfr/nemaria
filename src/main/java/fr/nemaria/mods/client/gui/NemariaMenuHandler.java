package fr.nemaria.mods.client.gui;

import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraftforge.client.event.GuiOpenEvent;

@SideOnly(Side.CLIENT)
public class NemariaMenuHandler {

    public static final NemariaMenuHandler INSTANCE = new NemariaMenuHandler();

    private NemariaMenuHandler() {}

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onGuiOpen(GuiOpenEvent event) {
        // Intercepte le menu principal vanilla et le remplace par le nôtre
        if (event.gui instanceof GuiMainMenu) {
            event.gui = new GuiNemariaMainMenu();
        }
    }
}
