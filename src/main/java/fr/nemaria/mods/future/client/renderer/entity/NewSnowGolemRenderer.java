package fr.nemaria.mods.future.client.renderer.entity;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.nemaria.mods.future.entities.EntityNewSnowGolem;
import net.minecraft.client.renderer.entity.RenderSnowMan;
import net.minecraft.entity.monster.EntitySnowman;

@SideOnly(Side.CLIENT)
public class NewSnowGolemRenderer extends RenderSnowMan {

	@Override
	protected void renderEquippedItems(EntitySnowman entity, float partialTickTime) {
		if (((EntityNewSnowGolem) entity).hasPumpkin())
			super.renderEquippedItems(entity, partialTickTime);
	}
}