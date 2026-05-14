package fr.nemaria.mods.mixins.backlytra;

import org.spongepowered.asm.mixin.Mixin;

import fr.nemaria.mods.future.elytra.IElytraEntityTrackerEntry;
import net.minecraft.entity.EntityTrackerEntry;

@Mixin(EntityTrackerEntry.class)
public class MixinEntityTrackerEntry implements IElytraEntityTrackerEntry {
	private boolean etfu$wasSendingVelUpdates;

	@Override
	public boolean etfu$getWasSendingVelUpdates() {
		return etfu$wasSendingVelUpdates;
	}

	@Override
	public void etfu$setWasSendingVelUpdates(boolean b) {
		etfu$wasSendingVelUpdates = b;
	}
}
