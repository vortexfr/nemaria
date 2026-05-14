package fr.nemaria.mods.future.lib;

import net.minecraft.launchwrapper.Launch;

public class Reference {

	public static final String MOD_ID = "nemaria";
	public static final String MOD_NAME = "Nemaria";
	public static final String MOD_URL = "@URL@";
	public static final String DEPENDENCIES = "required-after:Forge@[10.13.4.1558,);";
	public static final String VERSION_NUMBER = "@VERSION@";
	public static final boolean TESTING = Boolean.parseBoolean(System.getProperty("nemaria.testing"));
	public static final boolean DEV_ENVIRONMENT = (Boolean) Launch.blackboard.get("fml.deobfuscatedEnvironment");
	public static String BUILD_VERSION;
	public static final String CREDITS = "@CREDITS@";
	public static final String[] AUTHOR_LIST = {"@AUTHORLIST@"};
	public static final String DESCRIPTION = "@DESCRIPTION@";
	public static final String LOGO_FILE = "@LOGOFILE@";
	public static final String VERSION_URL = System.getProperty("nemaria.versionUrl", "nemaria.fr");
	public static final String GITHUB_URL = "";

	public static final String MCAssetVer = "minecraft_1.20.2";

	public static final String ITEM_BLOCK_TEXTURE_PATH = MOD_ID + ":";
	public static final String ARMOUR_TEXTURE_PATH = ITEM_BLOCK_TEXTURE_PATH + "textures/models/armor/";
	public static final String ENTITY_TEXTURE_PATH = ITEM_BLOCK_TEXTURE_PATH + "textures/entities/";

	public static boolean launchConfigWarning;
	public static boolean SNAPSHOT_BUILD = false;
}
