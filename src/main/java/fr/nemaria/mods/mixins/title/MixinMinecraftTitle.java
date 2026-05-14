package fr.nemaria.mods.mixins.title;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Util;
import org.lwjgl.opengl.Display;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

@SideOnly(Side.CLIENT)
@Mixin(Minecraft.class)
public class MixinMinecraftTitle {

    @Inject(
        method = "startGame",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraftforge/client/ForgeHooksClient;createDisplay()V"
        )
    )
    private void setCustomIconAndTitle(CallbackInfo ci) {
        Minecraft mc = (Minecraft)(Object)this;

        // Titre avec le pseudo du joueur
        if (mc.getSession() != null) {
            Display.setTitle("Nemaria V6 - " + mc.getSession().getUsername());
        } else {
            Display.setTitle("Nemaria V6");
        }

        // Icônes (pas sur macOS)
        if (Util.getOSType() != Util.EnumOS.OSX) {
            try {
                InputStream icon16 = Minecraft.class.getResourceAsStream("/assets/nemaria/icons/icon_16x16.png");
                InputStream icon32 = Minecraft.class.getResourceAsStream("/assets/nemaria/icons/icon_32x32.png");

                if (icon16 != null && icon32 != null) {
                    Display.setIcon(new ByteBuffer[]{
                        inputStreamToByteBuffer(icon16),
                        inputStreamToByteBuffer(icon32)
                    });
                    icon16.close();
                    icon32.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private ByteBuffer inputStreamToByteBuffer(InputStream stream) throws IOException {
        BufferedImage img = ImageIO.read(stream);
        int[] pixels = img.getRGB(0, 0, img.getWidth(), img.getHeight(), null, 0, img.getWidth());
        ByteBuffer buf = ByteBuffer.allocate(4 * pixels.length);
        for (int k : pixels) {
            buf.putInt(k << 8 | k >> 24 & 255);
        }
        buf.flip();
        return buf;
    }
}
