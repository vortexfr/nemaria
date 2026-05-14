package fr.nemaria.mods.future.client.renderer.entity;

import org.lwjgl.opengl.GL11;

import fr.nemaria.mods.future.entities.EntityNewBoat;
import fr.nemaria.mods.future.entities.EntityNewBoatWithChest;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.tileentity.TileEntityChest;

public class ChestBoatRenderer extends NewBoatRenderer {
	private final TileEntityChest chest = new TileEntityChest();

	@Override
	protected void renderExtraBoatContents(EntityNewBoat boat, float partialTicks) {
		if (boat instanceof EntityNewBoatWithChest) {
			GL11.glRotatef(180f, 0f, 1f, 0f);
			GL11.glScalef(0.8f, 0.8f, 0.8f);
			GL11.glTranslatef(
					((EntityNewBoatWithChest) boat).getChestXOffset(),
					((EntityNewBoatWithChest) boat).getChestHeight(),
					((EntityNewBoatWithChest) boat).getChestZOffset());
			TileEntityRendererDispatcher.instance.renderTileEntityAt(chest, 0, 0, 0, partialTicks);
		}
	}
}
