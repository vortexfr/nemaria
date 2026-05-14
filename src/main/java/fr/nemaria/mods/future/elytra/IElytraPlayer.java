package fr.nemaria.mods.future.elytra;

public interface IElytraPlayer extends fr.nemaria.mods.future.api.elytra.IElytraPlayer {
	void etfu$setElytraFlying(boolean flag);

	float etfu$getTicksElytraFlying();

	boolean etfu$lastElytraFlying();

	void etfu$setLastElytraFlying(boolean flag);

	void tickElytra();
}
