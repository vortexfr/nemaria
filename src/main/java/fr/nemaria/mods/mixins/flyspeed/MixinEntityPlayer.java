package fr.nemaria.mods.mixins.flyspeed;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import fr.nemaria.mods.future.configuration.configs.ConfigMixins;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.world.World;

@Mixin(EntityPlayer.class)
public abstract class MixinEntityPlayer extends EntityLivingBase {
	@Shadow
	public PlayerCapabilities capabilities;

	public MixinEntityPlayer(World p_i1594_1_) {
		super(p_i1594_1_);
	}

	@Inject(method = "moveEntityWithHeading", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/EntityLivingBase;moveEntityWithHeading(FF)V", ordinal = 0))
	private void setMovementFactor(float p_70612_1_, float p_70612_2_, CallbackInfo ci) {
		this.jumpMovementFactor = this.capabilities.getFlySpeed() * (this.isSprinting() ? ConfigMixins.creativeFlightSpeedModifier : 1);
	}
}