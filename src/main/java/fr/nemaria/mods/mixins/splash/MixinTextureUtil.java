package fr.nemaria.mods.mixins.splash;

import java.nio.IntBuffer;

import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import fr.nemaria.mods.future.client.gui.NemariaSplash;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.texture.TextureUtil;

@Mixin({TextureUtil.class})
public class MixinTextureUtil {
   @Shadow
   static void bindTexture(int p_94277_0_) {
   }

   @Shadow
   public static void deleteTexture(int textureId) {
   }

   @Inject(
      method = {"allocateTextureImpl"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private static void injectTextureImpl(int p_147946_0_, int p_147946_1_, int p_147946_2_, int p_147946_3_, float p_147946_4_, CallbackInfo ci) {
      synchronized(NemariaSplash.class) {
         deleteTexture(p_147946_0_);
         bindTexture(p_147946_0_);
      }

      if (OpenGlHelper.anisotropicFilteringSupported) {
         GL11.glTexParameterf(3553, 34046, p_147946_4_);
      }

      if (p_147946_1_ > 0) {
         GL11.glTexParameteri(3553, 33085, p_147946_1_);
         GL11.glTexParameterf(3553, 33082, 0.0F);
         GL11.glTexParameterf(3553, 33083, (float)p_147946_1_);
         GL11.glTexParameterf(3553, 34049, 0.0F);
      }

      for(int i1 = 0; i1 <= p_147946_1_; ++i1) {
         GL11.glTexImage2D(3553, i1, 6408, p_147946_2_ >> i1, p_147946_3_ >> i1, 0, 32993, 33639, (IntBuffer)null);
      }

      ci.cancel();
   }
}
