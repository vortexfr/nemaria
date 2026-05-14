package fr.nemaria.mods.mixins.closedrops;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import fr.nemaria.mods.future.core.handlers.ServerEventHandler;
import net.minecraft.entity.player.EntityPlayerMP;

@Mixin(EntityPlayerMP.class)
public class MixinEntityPlayerMP {
	@Inject(method = "closeContainer", at = @At("HEAD"))
	private void detectClosingContainer(CallbackInfo ci) {
		ServerEventHandler.playersClosedContainers.add((EntityPlayerMP) (Object) this);
	}
}
