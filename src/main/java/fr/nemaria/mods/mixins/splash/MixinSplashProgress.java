package fr.nemaria.mods.mixins.splash;

import org.lwjgl.LWJGLException;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import fr.nemaria.mods.future.client.gui.NemariaSplash;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.ResourceLocation;

@Mixin({Minecraft.class})
public class MixinSplashProgress {
	
	@Redirect(
		      method = {"startGame"},
		      at = @At(
		   value = "INVOKE",
		   remap = false,
		   target = "Lcpw/mods/fml/client/SplashProgress;drawVanillaScreen()V"
		)
	)
      private void rdDrawVanillaScreen() throws LWJGLException {
		NemariaSplash.drawVanillaScreen();
	}
	
	@Redirect(
		      method = {"startGame"},
		      at = @At(
		   value = "INVOKE",
		   remap = false,
		   target = "Lcpw/mods/fml/client/SplashProgress;clearVanillaResources(Lnet/minecraft/client/renderer/texture/TextureManager;Lnet/minecraft/util/ResourceLocation;)V"
		)
		   )
    private void rdClear(TextureManager renderEngine, ResourceLocation mojangLogo) {
		NemariaSplash.clearVanillaResources(renderEngine, mojangLogo);
	}

}
