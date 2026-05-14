package fr.nemaria.mods.mixins.observer;

import java.util.Set;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import fr.nemaria.mods.future.ducks.IObserverWorldExtension;
import net.minecraft.block.Block;
import net.minecraft.world.NextTickListEntry;
import net.minecraft.world.WorldServer;

@Mixin(WorldServer.class)
public class MixinWorldServer implements IObserverWorldExtension {

	@Shadow
	private Set pendingTickListEntriesHashSet;

	@Override
	public boolean etfu$hasScheduledUpdate(int x, int y, int z, Block block) {
		return this.pendingTickListEntriesHashSet.contains(new NextTickListEntry(x, y, z, block));
	}
}
