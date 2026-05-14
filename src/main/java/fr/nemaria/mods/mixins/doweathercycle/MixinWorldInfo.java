package fr.nemaria.mods.mixins.doweathercycle;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import fr.nemaria.mods.future.gamerule.DoWeatherCycle;
import net.minecraft.world.storage.WorldInfo;

@Mixin(WorldInfo.class)
public class MixinWorldInfo {

	@Inject(method = {"setRainTime", "setRaining", "setThunderTime", "setThundering"}, at = @At("HEAD"), cancellable = true)
	private void cancelWeatherChange(CallbackInfo ci) {
		if (DoWeatherCycle.INSTANCE.canCancelWeatherChange(((WorldInfo) (Object) this).getGameRulesInstance())) {
			ci.cancel();
		}
	}

}
