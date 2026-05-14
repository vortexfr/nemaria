package fr.nemaria.mods.future.compat.nei;

import cpw.mods.fml.common.event.FMLInterModComms;
import fr.nemaria.mods.future.ModBlocks;
import fr.nemaria.mods.future.lib.Reference;
import net.minecraft.nbt.NBTTagCompound;

public class IMCSenderGTNH {

	public static void IMCSender() {

		if (ModBlocks.SMOKER.isEnabled()) {
			sendHandler("fr.nemaria.mods.future.compat.nei.SmokerRecipeHandler", "nemaria:smoker");
			sendCatalyst("nemaria.smoker", "nemaria:smoker");

			sendCatalyst("fuel", "nemaria:smoker");
		}

		if (ModBlocks.BLAST_FURNACE.isEnabled()) {
			sendHandler("fr.nemaria.mods.future.compat.nei.BlastFurnaceRecipeHandler", "nemaria:blast_furnace");
			sendCatalyst("nemaria.blastfurnace", "nemaria:blast_furnace");

			sendCatalyst("fuel", "nemaria:blast_furnace");
		}

		if (ModBlocks.BANNER.isEnabled()) {
			sendHandler("fr.nemaria.mods.future.compat.nei.BannerPatternHandler", "nemaria:banner");
		}

		if (ModBlocks.COMPOSTER.isEnabled()) {
			sendHandler("fr.nemaria.mods.future.compat.nei.ComposterHandler", "nemaria:composter", 1);
			sendCatalyst("nemaria.composter", "nemaria:composter");
		}
	}

	/*
	 * These were copied from GTNewHorizons/GoodGenerator (Fork of GlodBlock/GoodGenerator)
	 * Author: GlodBlock
	 */

	private static void sendHandler(String aName, String aBlock) {
		sendHandler(aName, aBlock, 5);
	}

	private static void sendHandler(String aName, String aBlock, int maxRecipesPerPage) {
		NBTTagCompound aNBT = new NBTTagCompound();
		aNBT.setString("handler", aName);
		aNBT.setString("modName", Reference.MOD_NAME);
		aNBT.setString("modId", Reference.MOD_ID);
		aNBT.setBoolean("modRequired", true);
		aNBT.setString("itemName", aBlock);
		aNBT.setInteger("handlerHeight", 65);
		aNBT.setInteger("handlerWidth", 166);
		aNBT.setInteger("maxRecipesPerPage", maxRecipesPerPage);
		aNBT.setInteger("yShift", 6);
		FMLInterModComms.sendMessage("NotEnoughItems", "registerHandlerInfo", aNBT);
	}

	private static void sendCatalyst(String aName, String aStack, int aPriority) {
		NBTTagCompound aNBT = new NBTTagCompound();
		aNBT.setString("handlerID", aName);
		aNBT.setString("itemName", aStack);
		aNBT.setInteger("priority", aPriority);
		FMLInterModComms.sendMessage("NotEnoughItems", "registerCatalystInfo", aNBT);
	}

	private static void sendCatalyst(String aName, String aStack) {
		sendCatalyst(aName, aStack, 0);
	}
}
