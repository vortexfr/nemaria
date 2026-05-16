package fr.nemaria.mods.proxy;

import java.io.File;

import com.mojang.authlib.minecraft.MinecraftSessionService;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import fr.nemaria.mods.future.ModBlocks;
import fr.nemaria.mods.future.client.model.ModelShulker;
import fr.nemaria.mods.future.client.renderer.block.BlockAmethystClusterRenderer;
import fr.nemaria.mods.future.client.renderer.block.BlockAzaleaRenderer;
import fr.nemaria.mods.future.client.renderer.block.BlockBambooRenderer;
import fr.nemaria.mods.future.client.renderer.block.BlockBarrelRenderer;
import fr.nemaria.mods.future.client.renderer.block.BlockChainRenderer;
import fr.nemaria.mods.future.client.renderer.block.BlockChestRenderer;
import fr.nemaria.mods.future.client.renderer.block.BlockChorusFlowerRenderer;
import fr.nemaria.mods.future.client.renderer.block.BlockChorusPlantRenderer;
import fr.nemaria.mods.future.client.renderer.block.BlockColoredWaterCauldronRenderer;
import fr.nemaria.mods.future.client.renderer.block.BlockComposterRenderer;
import fr.nemaria.mods.future.client.renderer.block.BlockDoorRenderer;
import fr.nemaria.mods.future.client.renderer.block.BlockDoubleCubeRenderer;
import fr.nemaria.mods.future.client.renderer.block.BlockEmissiveLayerRenderer;
import fr.nemaria.mods.future.client.renderer.block.BlockEndRodRenderer;
import fr.nemaria.mods.future.client.renderer.block.BlockExtendedCrossedSquaresRenderer;
import fr.nemaria.mods.future.client.renderer.block.BlockGlazedTerracottaRenderer;
import fr.nemaria.mods.future.client.renderer.block.BlockLanternRenderer;
import fr.nemaria.mods.future.client.renderer.block.BlockLavaCauldronRenderer;
import fr.nemaria.mods.future.client.renderer.block.BlockLoomRenderer;
import fr.nemaria.mods.future.client.renderer.block.BlockMangroveRootsRenderer;
import fr.nemaria.mods.future.client.renderer.block.BlockNewFenceRenderer;
import fr.nemaria.mods.future.client.renderer.block.BlockObserverRenderer;
import fr.nemaria.mods.future.client.renderer.block.BlockPinkPetalsRenderer;
import fr.nemaria.mods.future.client.renderer.block.BlockPointedDripstoneRenderer;
import fr.nemaria.mods.future.client.renderer.block.BlockStonecutterRenderer;
import fr.nemaria.mods.future.client.renderer.block.BlockTrapDoorRenderer;
import fr.nemaria.mods.future.client.renderer.entity.ArmourStandRenderer;
import fr.nemaria.mods.future.client.renderer.entity.BeeRenderer;
import fr.nemaria.mods.future.client.renderer.entity.BrownMooshroomRenderer;
import fr.nemaria.mods.future.client.renderer.entity.ChestBoatRenderer;
import fr.nemaria.mods.future.client.renderer.entity.EndermiteRenderer;
import fr.nemaria.mods.future.client.renderer.entity.HuskRenderer;
import fr.nemaria.mods.future.client.renderer.entity.LingeringEffectRenderer;
import fr.nemaria.mods.future.client.renderer.entity.LingeringPotionRenderer;
import fr.nemaria.mods.future.client.renderer.entity.NewBoatRenderer;
import fr.nemaria.mods.future.client.renderer.entity.NewSnowGolemRenderer;
import fr.nemaria.mods.future.client.renderer.entity.PlacedEndCrystalRenderer;
import fr.nemaria.mods.future.client.renderer.entity.RabbitRenderer;
import fr.nemaria.mods.future.client.renderer.entity.ShulkerBulletRenderer;
import fr.nemaria.mods.future.client.renderer.entity.ShulkerRenderer;
import fr.nemaria.mods.future.client.renderer.entity.StrayRenderer;
import fr.nemaria.mods.future.client.renderer.entity.TechnobladeCrownRenderer;
import fr.nemaria.mods.future.client.renderer.entity.VillagerZombieRenderer;
import fr.nemaria.mods.future.client.renderer.item.Item3DBedRenderer;
import fr.nemaria.mods.future.client.renderer.item.ItemBannerRenderer;
import fr.nemaria.mods.future.client.renderer.item.ItemBowRenderer;
import fr.nemaria.mods.future.client.renderer.item.ItemShulkerBoxRenderer;
import fr.nemaria.mods.future.client.renderer.item.ItemSkullRenderer;
import fr.nemaria.mods.future.client.renderer.tileentity.TileEntityBannerRenderer;
import fr.nemaria.mods.future.client.renderer.tileentity.TileEntityFancySkullRenderer;
import fr.nemaria.mods.future.client.renderer.tileentity.TileEntityGatewayRenderer;
import fr.nemaria.mods.future.client.renderer.tileentity.TileEntityNewBeaconRenderer;
import fr.nemaria.mods.future.client.renderer.tileentity.TileEntityShulkerBoxRenderer;
import fr.nemaria.mods.future.client.renderer.tileentity.TileEntityWoodSignRenderer;
import fr.nemaria.mods.future.client.skins.NewRenderPlayer;
import fr.nemaria.mods.future.client.skins.NewSkinManager;
import fr.nemaria.mods.future.client.subtitle.GuiSubtitles;
import fr.nemaria.mods.future.configuration.configs.ConfigFunctions;
import fr.nemaria.mods.future.configuration.configs.ConfigMixins;
import fr.nemaria.mods.future.core.handlers.ClientEventHandler;
import fr.nemaria.mods.future.core.utils.VersionChecker;
import fr.nemaria.mods.future.entities.EntityArmourStand;
import fr.nemaria.mods.future.entities.EntityBee;
import fr.nemaria.mods.future.entities.EntityBrownMooshroom;
import fr.nemaria.mods.future.entities.EntityEndermite;
import fr.nemaria.mods.future.entities.EntityHusk;
import fr.nemaria.mods.future.entities.EntityLingeringEffect;
import fr.nemaria.mods.future.entities.EntityLingeringPotion;
import fr.nemaria.mods.future.entities.EntityNewBoat;
import fr.nemaria.mods.future.entities.EntityNewBoatWithChest;
import fr.nemaria.mods.future.entities.EntityNewSnowGolem;
import fr.nemaria.mods.future.entities.EntityPlacedEndCrystal;
import fr.nemaria.mods.future.entities.EntityRabbit;
import fr.nemaria.mods.future.entities.EntityShulker;
import fr.nemaria.mods.future.entities.EntityShulkerBullet;
import fr.nemaria.mods.future.entities.EntityStray;
import fr.nemaria.mods.future.entities.EntityZombieVillager;
import fr.nemaria.mods.future.lib.Reference;
import fr.nemaria.mods.future.lib.RenderIDs;
import fr.nemaria.mods.future.spectator.SpectatorModeClient;
import fr.nemaria.mods.future.tileentities.TileEntityBanner;
import fr.nemaria.mods.future.tileentities.TileEntityGateway;
import fr.nemaria.mods.future.tileentities.TileEntityNewBeacon;
import fr.nemaria.mods.future.tileentities.TileEntityShulkerBox;
import fr.nemaria.mods.future.tileentities.TileEntityWoodSign;
import fr.nemaria.mods.future.world.nether.biome.utils.BiomeFogEventHandler;
import net.minecraft.block.BlockBed;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntitySkull;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.MinecraftForge;

public class ClientProxy extends CommonProxy {

	public static boolean isRenderingInventoryPlayer = false;

	@Override
	public void registerEvents() {
		super.registerEvents();
		FMLCommonHandler.instance().bus().register(ClientEventHandler.INSTANCE);
		MinecraftForge.EVENT_BUS.register(ClientEventHandler.INSTANCE);
		if (ConfigMixins.enableSpectatorMode) {
			FMLCommonHandler.instance().bus().register(SpectatorModeClient.INSTANCE);
			MinecraftForge.EVENT_BUS.register(SpectatorModeClient.INSTANCE);
		}

		if (ConfigFunctions.enableSubtitles) {
			GuiSubtitles.INSTANCE = new GuiSubtitles(FMLClientHandler.instance().getClient());
			MinecraftForge.EVENT_BUS.register(GuiSubtitles.INSTANCE);
		}

		if (ConfigFunctions.enableUpdateChecker && !Reference.SNAPSHOT_BUILD && !Reference.DEV_ENVIRONMENT) {
			FMLCommonHandler.instance().bus().register(VersionChecker.instance);
		}

		MinecraftForge.EVENT_BUS.register(BiomeFogEventHandler.INSTANCE);
		MinecraftForge.EVENT_BUS.register(fr.nemaria.mods.client.NemariaDebugOverlay.INSTANCE);
		MinecraftForge.EVENT_BUS.register(fr.nemaria.mods.client.gui.NemariaMenuHandler.INSTANCE);
		MinecraftForge.EVENT_BUS.register(fr.nemaria.mods.client.gui.NeMariacursorManager.INSTANCE);
	}

	@Override
	public void registerRenderers() {
		registerItemRenderers();
		registerBlockRenderers();
		registerEntityRenderers();
	}

	private void registerItemRenderers() {
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ModBlocks.BANNER.get()), new ItemBannerRenderer());
		MinecraftForgeClient.registerItemRenderer(Items.skull, new ItemSkullRenderer());
		MinecraftForgeClient.registerItemRenderer(Items.bow, new ItemBowRenderer());
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ModBlocks.SHULKER_BOX.get()), new ItemShulkerBoxRenderer());
		MinecraftForgeClient.registerItemRenderer(Items.bed, new Item3DBedRenderer((BlockBed) Blocks.bed));
		for (ModBlocks bed : ModBlocks.BEDS) {
			if (bed.isEnabled()) {
				MinecraftForgeClient.registerItemRenderer(bed.getItem(), new Item3DBedRenderer((BlockBed) bed.get()));
			}
		}
	}

	private void registerBlockRenderers() {
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityWoodSign.class, new TileEntityWoodSignRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityBanner.class, new TileEntityBannerRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntitySkull.class, new TileEntityFancySkullRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityNewBeacon.class, new TileEntityNewBeaconRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityShulkerBox.class, new TileEntityShulkerBoxRenderer(new ModelShulker()));
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityGateway.class, new TileEntityGatewayRenderer());

		RenderingRegistry.registerBlockHandler(new BlockExtendedCrossedSquaresRenderer());
		RenderingRegistry.registerBlockHandler(new BlockNewFenceRenderer());
		RenderingRegistry.registerBlockHandler(new BlockTrapDoorRenderer(RenderIDs.TRAPDOOR));
		RenderingRegistry.registerBlockHandler(new BlockDoorRenderer(RenderIDs.DOOR));
		RenderingRegistry.registerBlockHandler(new BlockDoubleCubeRenderer(13, RenderIDs.SLIME_BLOCK));
		RenderingRegistry.registerBlockHandler(new BlockEndRodRenderer(RenderIDs.END_ROD));
		RenderingRegistry.registerBlockHandler(new BlockChorusFlowerRenderer(RenderIDs.CHORUS_FLOWER));
		RenderingRegistry.registerBlockHandler(new BlockChorusPlantRenderer(RenderIDs.CHORUS_PLANT));
		RenderingRegistry.registerBlockHandler(new BlockLanternRenderer(RenderIDs.LANTERN));
		RenderingRegistry.registerBlockHandler(new BlockBarrelRenderer(RenderIDs.BARREL));
		RenderingRegistry.registerBlockHandler(new BlockGlazedTerracottaRenderer(RenderIDs.GLAZED_TERRACOTTA));
		RenderingRegistry.registerBlockHandler(new BlockLavaCauldronRenderer(RenderIDs.LAVA_CAULDRON));
		RenderingRegistry.registerBlockHandler(new BlockStonecutterRenderer(RenderIDs.STONECUTTER));
		RenderingRegistry.registerBlockHandler(new BlockComposterRenderer(RenderIDs.COMPOSTER));
		RenderingRegistry.registerBlockHandler(new BlockLoomRenderer(RenderIDs.LOOM));
		RenderingRegistry.registerBlockHandler(new BlockAmethystClusterRenderer(RenderIDs.AMETHYST_CLUSTER));
		RenderingRegistry.registerBlockHandler(new BlockPointedDripstoneRenderer(RenderIDs.POINTED_DRIPSTONE));
		RenderingRegistry.registerBlockHandler(new BlockColoredWaterCauldronRenderer(RenderIDs.COLORED_CAULDRON));
		RenderingRegistry.registerBlockHandler(new BlockChestRenderer());
		RenderingRegistry.registerBlockHandler(new BlockObserverRenderer(RenderIDs.OBSERVER));
		RenderingRegistry.registerBlockHandler(new BlockDoubleCubeRenderer(15, RenderIDs.HONEY_BLOCK));
		RenderingRegistry.registerBlockHandler(new BlockChainRenderer(RenderIDs.CHAIN));
		RenderingRegistry.registerBlockHandler(new BlockAzaleaRenderer(RenderIDs.AZALEA));
		RenderingRegistry.registerBlockHandler(new BlockMangroveRootsRenderer(RenderIDs.MANGROVE_ROOTS));
		RenderingRegistry.registerBlockHandler(new BlockPinkPetalsRenderer(RenderIDs.PINK_PETALS));
		RenderingRegistry.registerBlockHandler(new BlockBambooRenderer(RenderIDs.BAMBOO));

		RenderingRegistry.registerBlockHandler(new BlockEmissiveLayerRenderer(RenderIDs.EMISSIVE_DOUBLE_LAYER));
	}

	private void registerEntityRenderers() {
		RenderingRegistry.registerEntityRenderingHandler(EntityArmourStand.class, new ArmourStandRenderer());
		RenderingRegistry.registerEntityRenderingHandler(EntityEndermite.class, new EndermiteRenderer());
		RenderingRegistry.registerEntityRenderingHandler(EntityRabbit.class, new RabbitRenderer());
		RenderingRegistry.registerEntityRenderingHandler(EntityHusk.class, new HuskRenderer());
		RenderingRegistry.registerEntityRenderingHandler(EntityStray.class, new StrayRenderer());
		RenderingRegistry.registerEntityRenderingHandler(EntityLingeringPotion.class, new LingeringPotionRenderer());
		RenderingRegistry.registerEntityRenderingHandler(EntityLingeringEffect.class, new LingeringEffectRenderer());
		RenderingRegistry.registerEntityRenderingHandler(EntityZombieVillager.class, new VillagerZombieRenderer());
		RenderingRegistry.registerEntityRenderingHandler(EntityPlacedEndCrystal.class, new PlacedEndCrystalRenderer());
		RenderingRegistry.registerEntityRenderingHandler(EntityBrownMooshroom.class, new BrownMooshroomRenderer());
		RenderingRegistry.registerEntityRenderingHandler(EntityNewBoat.class, new NewBoatRenderer());
		RenderingRegistry.registerEntityRenderingHandler(EntityNewBoatWithChest.class, new ChestBoatRenderer());
		RenderingRegistry.registerEntityRenderingHandler(EntityShulker.class, new ShulkerRenderer());
		RenderingRegistry.registerEntityRenderingHandler(EntityShulkerBullet.class, new ShulkerBulletRenderer());
		RenderingRegistry.registerEntityRenderingHandler(EntityBee.class, new BeeRenderer());
		RenderingRegistry.registerEntityRenderingHandler(EntityNewSnowGolem.class, new NewSnowGolemRenderer());

		RenderingRegistry.registerEntityRenderingHandler(EntityPig.class, new TechnobladeCrownRenderer());

//      {
//          RenderingRegistry.registerEntityRenderingHandler(EntityFallingDripstone.class, new FallingDripstoneRenderer());
//      }

		if (ConfigFunctions.enablePlayerSkinOverlay) {
			TextureManager texManager = Minecraft.getMinecraft().renderEngine;
			File skinFolder = new File(Minecraft.getMinecraft().fileAssets, "skins");
			MinecraftSessionService sessionService = Minecraft.getMinecraft().func_152347_ac();
			Minecraft.getMinecraft().field_152350_aA = new NewSkinManager(Minecraft.getMinecraft().func_152342_ad(), texManager, skinFolder, sessionService);

			RenderManager.instance.entityRenderMap.put(EntityPlayer.class, new NewRenderPlayer());
		}
	}
}