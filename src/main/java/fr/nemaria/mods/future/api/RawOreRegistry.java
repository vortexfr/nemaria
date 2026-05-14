package fr.nemaria.mods.future.api;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;

import fr.nemaria.mods.future.ModItems;
import fr.nemaria.mods.future.api.mappings.RawOreDropMapping;
import fr.nemaria.mods.future.configuration.configs.ConfigBlocksItems;
import fr.nemaria.mods.future.configuration.configs.ConfigFunctions;
import fr.nemaria.mods.future.core.utils.Utils;
import fr.nemaria.mods.future.recipes.ModRecipes;
import net.minecraft.item.Item;
import net.minecraftforge.oredict.OreDictionary;


/**
 * The registry Et Futurum's block breaking handler uses to find out if we should drop
 * a raw ore item. When broken, that blocks OreDictionary tags are checked and if it
 * matches a tag that is mapped here, it will drop the respective item you mapped.
 *
 * @author roadhog360
 * @apiNote Usage example: {addOre("oreIron", ModItems.raw_ore, 1);} would cause any ore with the tag
 * "oreIron" to drop Et Futurum's raw ore item, with a meta value of 1, which is the raw iron item.
 */
public class RawOreRegistry {

	public static final Map<String, RawOreDropMapping> rawOreRegistry = new HashMap<>();

	/**
	 * What item should blocks that have this OreDict tag drop?
	 * Assumes a meta value of 0 for the specified drop.
	 *
	 * @param oreDict
	 * @param ore
	 */
	public static void addOre(String oreDict, Item ore) {
		addOre(oreDict, ore, 0);
	}

	public static void addOre(String oreDict, Item ore, int meta) {
		if (ModRecipes.validateItems(ore)) {
			RawOreDropMapping mapping = new RawOreDropMapping(ore, meta);
			if (ArrayUtils.contains(ConfigFunctions.extraDropRawOres, oreDict)) {
				mapping.setDropsExtra(true);
			}
			addOre(oreDict, mapping);
		}
	}

	private static void addOre(String oreDict, RawOreDropMapping map) {
		rawOreRegistry.put(oreDict, map);
	}

	public static boolean hasOre(String oreDict) {
		return rawOreRegistry.containsKey(oreDict);
	}

	public static RawOreDropMapping getOre(String oreDict) {
		return rawOreRegistry.get(oreDict);
	}

	public static Map<String, RawOreDropMapping> getOreMap() {
		return rawOreRegistry;
	}

	public static void init() {
		if (ModItems.RAW_ORE.isEnabled()) {
			if (ConfigBlocksItems.enableCopper || OreDictionary.doesOreNameExist("ingotCopper")) {
				addOre("oreCopper", ModItems.RAW_ORE.get());
			}
			addOre("oreIron", ModItems.RAW_ORE.get(), 1);
			addOre("oreGold", ModItems.RAW_ORE.get(), 2);
		}
	}
}
