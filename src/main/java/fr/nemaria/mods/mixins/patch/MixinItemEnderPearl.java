package fr.nemaria.mods.mixins.patch;

import java.util.Random;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.entity.item.EntityEnderPearl;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemEnderPearl;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

@Mixin(ItemEnderPearl.class)
public class MixinItemEnderPearl {
    
    private static final Random random = new Random();

    @Inject(
        method = {
            "onItemRightClick",  
            "func_77659_a"       
        },
        at = @At("HEAD"),
        cancellable = true
    )
    public void fixCreativeThrow(ItemStack stack, World world, EntityPlayer player, CallbackInfoReturnable<ItemStack> cir) {
        // En 1.7.10 Vanilla, on ne peut pas lancer d'ender pearl en créatif.
        // Ce mixin corrige cela en permettant le lancer même en mode créatif.
        
        if (player.capabilities.isCreativeMode) {
            world.playSoundAtEntity(player, "random.bow", 0.5F, 0.4F / (random.nextFloat() * 0.4F + 0.8F));
            
            if (!world.isRemote) {
                world.spawnEntityInWorld(new EntityEnderPearl(world, player));
            }
            
            cir.setReturnValue(stack);
        }
    }
}
