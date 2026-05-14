package fr.nemaria.mods.future.configuration;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.spongepowered.asm.mixin.MixinEnvironment;

import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import fr.nemaria.mods.NemariaMixinPlugin;
import fr.nemaria.mods.future.configuration.configs.ConfigBlocksItems;
import fr.nemaria.mods.future.configuration.configs.ConfigEnchantsPotions;
import fr.nemaria.mods.future.configuration.configs.ConfigEntities;
import fr.nemaria.mods.future.configuration.configs.ConfigExperiments;
import fr.nemaria.mods.future.configuration.configs.ConfigFunctions;
import fr.nemaria.mods.future.configuration.configs.ConfigMixins;
import fr.nemaria.mods.future.configuration.configs.ConfigModCompat;
import fr.nemaria.mods.future.configuration.configs.ConfigSounds;
import fr.nemaria.mods.future.configuration.configs.ConfigTweaks;
import fr.nemaria.mods.future.configuration.configs.ConfigWorld;
import fr.nemaria.mods.future.lib.Reference;
import net.minecraft.launchwrapper.Launch;
import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

public abstract class ConfigBase extends Configuration {
	protected final List<ConfigCategory> configCats = new ArrayList<>();
	private static final Set<ConfigBase> CONFIGS = new HashSet<>();

	public static final String configDir = "config" + File.separator + Reference.MOD_ID + File.separator;

	public static final ConfigBase EXPERIMENTS = new ConfigExperiments(createConfigFile("experiments"));

	public static final ConfigBase BLOCKS_ITEMS = new ConfigBlocksItems(createConfigFile("blocksitems"));
	public static final ConfigBase ENCHANTS_POTIONS = new ConfigEnchantsPotions(createConfigFile("enchantspotions"));
	public static final ConfigBase FUNCTIONS = new ConfigFunctions(createConfigFile("functions"));
	public static final ConfigBase TWEAKS = new ConfigTweaks(createConfigFile("tweaks"));
	public static final ConfigBase WORLD = new ConfigWorld(createConfigFile("world"));
	public static final ConfigBase ENTITIES = new ConfigEntities(createConfigFile("entities"));
	public static final ConfigBase SOUNDS = new ConfigSounds(createConfigFile("sounds"));
	public static final ConfigBase MOD_COMPAT = new ConfigModCompat(createConfigFile("modcompat"));

	public static final ConfigBase MIXINS = new ConfigMixins(createConfigFile("mixins"));

	public ConfigBase(File file) {
		super(file);
		CONFIGS.add(this);
	}

	private static File createConfigFile(String name) {
		return new File(Launch.minecraftHome, configDir + name + ".cfg");
	}

	public static void initializeConfigs() {
		for (ConfigBase config : CONFIGS) {
			config.syncConfig();
		}
	}

	private void syncConfig() {
		syncConfigOptions();

		for (ConfigCategory cat : configCats) {
			if (NemariaMixinPlugin.side == MixinEnvironment.Side.SERVER) {
				if (cat.getName().toLowerCase().contains("client")) {
					for (Property prop : cat.getOrderedValues()) {
						cat.remove(prop.getName());
					}
				}
			}

			if (cat.isEmpty() && !cat.getName().toLowerCase().contains("experiment")) {
				removeCategory(cat);
			}
		}

		if (hasChanged()) {
			save();
		}
	}

	protected abstract void syncConfigOptions();

	/**
	 * Used in case we need to wait till later to initialize some config values.
	 */
	protected void initValues() {
	}

	public static void postInit() {
		for (ConfigBase config : CONFIGS) {
			config.initValues();
		}
	}

	@SubscribeEvent
	public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent eventArgs) {
		if (Reference.MOD_ID.equals(eventArgs.modID))
			syncConfig();
	}
}
