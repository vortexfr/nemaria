package fr.nemaria.mods.future.entities;

public interface INoGravityEntity {
	void setMoveVertical(float moveVertical);

	void setNoGravity(boolean gravity);

	boolean hasNoGravity();
}
