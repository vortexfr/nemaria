package fr.nemaria.mods.client.gui.chat;

import java.util.function.Consumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiNewChat;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;

public class MultilineChatScreen extends GuiNewChat {

    public MultilineChatScreen() {
        super(Minecraft.getMinecraft());
    }

    @Override
    public void printChatMessageWithOptionalDeletion(IChatComponent chatComponent, int chatLineId) {
        if (chatLineId != 0) {
            super.printChatMessageWithOptionalDeletion(chatComponent, chatLineId);
            return;
        }
        split(chatComponent, T -> super.printChatMessageWithOptionalDeletion(T, chatLineId));
    }

    @SuppressWarnings("unchecked")
    public static void split(IChatComponent input, Consumer<IChatComponent> output) {
        IChatComponent current = new ChatComponentText("");
        for (IChatComponent component : (Iterable<IChatComponent>) input) {
            String text = component.getUnformattedTextForChat();
            if ("\\n".equals(text)) {
                output.accept(current);
                current = new ChatComponentText("");
            } else {
                boolean hasSplit = false;
                for (String line : text.split("\\n")) {
                    if (hasSplit) { output.accept(current); current = new ChatComponentText(""); hasSplit = false; }
                    current.appendSibling(new ChatComponentText(line).setChatStyle(component.getChatStyle()));
                    hasSplit = true;
                }
            }
        }
        if (!current.getSiblings().isEmpty()) output.accept(current);
    }
}
