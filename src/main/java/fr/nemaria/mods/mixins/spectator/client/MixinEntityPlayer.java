package fr.nemaria.mods.mixins.spectator.client;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import fr.nemaria.mods.future.spectator.SpectatorMode;
import net.minecraft.entity.player.EntityPlayer;

@Mixin(EntityPlayer.class)
public class MixinEntityPlayer {
	@Inject(method = "isInvisibleToPlayer", at = @At("HEAD"), cancellable = true)
	public void isInvisibleToPlayer(EntityPlayer p_98034_1_, CallbackInfoReturnable<Boolean> cir) {
		if (SpectatorMode.isSpectator(p_98034_1_)) {
			cir.setReturnValue(false);
		}
	}
}
