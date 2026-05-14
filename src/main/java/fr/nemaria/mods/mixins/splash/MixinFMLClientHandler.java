package fr.nemaria.mods.mixins.splash;

import java.util.concurrent.Semaphore;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import cpw.mods.fml.client.FMLClientHandler;
import fr.nemaria.mods.future.client.gui.NemariaSplash;

@Mixin(
   value = {FMLClientHandler.class},
   remap = false
)
public class MixinFMLClientHandler {
   @Redirect(
      method = {"beginMinecraftLoading"},
      at = @At(
         value = "INVOKE",
         target = "Lcpw/mods/fml/client/SplashProgress;start()V"
      )
   )
   private void rdStart() {
	   NemariaSplash.start();
   }

   @Redirect(
      method = {"haltGame"},
      at = @At(
         value = "INVOKE",
         target = "Lcpw/mods/fml/client/SplashProgress;finish()V"
      )
   )
   private void rdFinish() {
	   NemariaSplash.finish();
   }

   @Redirect(
      method = {"finishMinecraftLoading"},
      at = @At(
         value = "INVOKE",
         target = "Lcpw/mods/fml/client/SplashProgress;finish()V"
      )
   )
   private void rdFinish2() {
	   NemariaSplash.finish();
   }

   @Redirect(
      method = {"onInitializationComplete"},
      at = @At(
         value = "INVOKE",
         target = "Lcpw/mods/fml/client/SplashProgress;finish()V"
      )
   )
   private void rdFinish3() {
	   NemariaSplash.finish();
   }

   @Redirect(
      method = {"processWindowMessages"},
      at = @At(
         value = "INVOKE",
         target = "Ljava/util/concurrent/Semaphore;tryAcquire()Z"
      )
   )
   private boolean rdTryAcquire(Semaphore instance) {
      return NemariaSplash.mutex.tryAcquire();
   }

   @Redirect(
      method = {"processWindowMessages"},
      at = @At(
         value = "INVOKE",
         target = "Ljava/util/concurrent/Semaphore;release()V"
      )
   )
   private void rdRelease(Semaphore instance) {
	   NemariaSplash.mutex.release();
   }
}
