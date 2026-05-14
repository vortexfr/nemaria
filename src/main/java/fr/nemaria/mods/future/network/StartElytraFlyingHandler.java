package fr.nemaria.mods.future.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import fr.nemaria.mods.future.ModItems;
import fr.nemaria.mods.future.configuration.configs.ConfigMixins;
import fr.nemaria.mods.future.elytra.IElytraPlayer;
import fr.nemaria.mods.future.items.equipment.ItemArmorElytra;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.network.NetHandlerPlayServer;

public class StartElytraFlyingHandler implements IMessageHandler<StartElytraFlyingMessage, IMessage> {
	@Override
	public IMessage onMessage(StartElytraFlyingMessage message, MessageContext ctx) {
		EntityPlayer player = ((NetHandlerPlayServer) ctx.netHandler).playerEntity;
		if (!player.onGround && (ConfigMixins.enableNewElytraTakeoffLogic || player.motionY < 0.0D) && !((IElytraPlayer) player).etfu$isElytraFlying() && !player.isInWater()) {
			ItemStack itemstack = ItemArmorElytra.getElytra(player);

			if (itemstack != null && itemstack.getItem() == ModItems.ELYTRA.get() && !ItemArmorElytra.isBroken(itemstack)) {
				((IElytraPlayer) player).etfu$setElytraFlying(true);
			}
		} else {
			((IElytraPlayer) player).etfu$setElytraFlying(true);
			((IElytraPlayer) player).etfu$setElytraFlying(false);
		}
		return null;
	}
}
