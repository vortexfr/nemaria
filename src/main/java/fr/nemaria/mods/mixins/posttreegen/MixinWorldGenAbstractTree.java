package fr.nemaria.mods.mixins.posttreegen;

import java.util.Random;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import fr.nemaria.mods.future.api.event.PostTreeGenerateEvent;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.common.MinecraftForge;

@Mixin(WorldGenAbstractTree.class)
public abstract class MixinWorldGenAbstractTree extends WorldGenerator {

	@Inject(method = "func_150524_b", at = @At(value = "TAIL"))
	private void fireTreeGenEvent(World world, Random rand, int x, int y, int z, CallbackInfo ci) {
		MinecraftForge.TERRAIN_GEN_BUS.post(new PostTreeGenerateEvent(world, rand, x, y, z));
	}
}
