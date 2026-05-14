package fr.nemaria.mods.future.command;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

public class CommandLocate extends CommandBase {

	@Override
	public String getCommandName() {
		return "locate";
	}

	@Override
	public int getRequiredPermissionLevel() {
		return 2;
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "/locate <structure> OU /locate biome <biome>";
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args) {
		if (args.length < 1) {
			throw new WrongUsageException(getCommandUsage(sender));
		}

		World world = sender.getEntityWorld();
		int x = MathHelper.floor_double(sender.getPlayerCoordinates().posX);
		int z = MathHelper.floor_double(sender.getPlayerCoordinates().posZ);

		if (args[0].equalsIgnoreCase("biome")) {
			if (args.length < 2) {
				throw new WrongUsageException("/locate biome <nom du biome>");
			}
			
			// Reconstituer le nom du biome (s'il contient des espaces)
			StringBuilder biomeNameBuilder = new StringBuilder();
			for (int i = 1; i < args.length; i++) {
				if (i > 1) biomeNameBuilder.append(" ");
				biomeNameBuilder.append(args[i]);
			}
			String targetBiomeName = biomeNameBuilder.toString().toLowerCase().replace(" ", "");

			BiomeGenBase targetBiome = null;
			for (BiomeGenBase b : BiomeGenBase.getBiomeGenArray()) {
				if (b != null && b.biomeName != null && b.biomeName.toLowerCase().replace(" ", "").equals(targetBiomeName)) {
					targetBiome = b;
					break;
				}
			}

			if (targetBiome == null) {
				throw new CommandException("Biome introuvable : " + biomeNameBuilder.toString());
			}

			sender.addChatMessage(new ChatComponentText(EnumChatFormatting.YELLOW + "Recherche du biome " + targetBiome.biomeName + "..."));

			// Recherche en spirale rapide (ne génère pas de chunks, utilise juste les maths du biome)
			int radius = 10000; // Rayon de recherche max
			int step = 32; // On vérifie tous les 32 blocs pour être sûr de ne pas le rater

			for (int r = 0; r <= radius; r += step) {
				for (int dx = -r; dx <= r; dx += step) {
					for (int dz = -r; dz <= r; dz += step) {
						// Seulement le périmètre de la recherche actuelle
						if (Math.abs(dx) == r || Math.abs(dz) == r) {
							int checkX = x + dx;
							int checkZ = z + dz;
							if (world.getBiomeGenForCoords(checkX, checkZ) == targetBiome) {
								int distance = MathHelper.floor_double(Math.sqrt(dx * dx + dz * dz));
								ChatComponentText msg = new ChatComponentText(EnumChatFormatting.GREEN + "Biome " + targetBiome.biomeName + " trouvé en " + checkX + " ~ " + checkZ + " (" + distance + " blocs)");
								msg.setChatStyle(new net.minecraft.util.ChatStyle()
									.setChatClickEvent(new net.minecraft.event.ClickEvent(net.minecraft.event.ClickEvent.Action.SUGGEST_COMMAND, "/tp @p " + checkX + " ~ " + checkZ))
									.setChatHoverEvent(new net.minecraft.event.HoverEvent(net.minecraft.event.HoverEvent.Action.SHOW_TEXT, new ChatComponentText(EnumChatFormatting.AQUA + "Cliquez pour préparer le /tp"))));
								sender.addChatMessage(msg);
								return;
							}
						}
					}
				}
			}
			throw new CommandException("Le biome n'a pas pu être trouvé dans un rayon de " + radius + " blocs.");

		} else {
			// Recherche de structure
			String structureName = args[0];
			
			// Cas spécial pour le Monument Océanique (structure custom EtFuturum)
			if (structureName.equalsIgnoreCase("Monument") || structureName.equalsIgnoreCase("OceanMonument")) {
				int chunkX = x >> 4;
				int chunkZ = z >> 4;
				int spacing = 32;
				int separation = 5;
				
				// On ajuste la grille pour les coordonnées négatives (comme dans OceanMonument.canSpawnAt)
				int gridX = chunkX < 0 ? (chunkX - spacing + 1) / spacing : chunkX / spacing;
				int gridZ = chunkZ < 0 ? (chunkZ - spacing + 1) / spacing : chunkZ / spacing;

				int maxRadius = 100; // Rayon de recherche en "cases" de grille (100 * 32 chunks = énorme)
				for (int r = 0; r <= maxRadius; r++) {
					for (int dx = -r; dx <= r; dx++) {
						for (int dz = -r; dz <= r; dz++) {
							if (Math.abs(dx) == r || Math.abs(dz) == r) {
								int cgX = gridX + dx;
								int cgZ = gridZ + dz;
								
								int i1 = cgX * spacing;
								int j1 = cgZ * spacing;
								java.util.Random random = world.setRandomSeed(cgX, cgZ, 10387313);
								int spawnChunkX = i1 + (random.nextInt(spacing - separation) + random.nextInt(spacing - separation)) / 2;
								int spawnChunkZ = j1 + (random.nextInt(spacing - separation) + random.nextInt(spacing - separation)) / 2;
								
								if (fr.nemaria.mods.future.world.structure.OceanMonument.canSpawnAt(world, spawnChunkX, spawnChunkZ)) {
									int monumentX = spawnChunkX * 16 + 8;
									int monumentZ = spawnChunkZ * 16 + 8;
									int dist = MathHelper.floor_double(Math.sqrt(Math.pow(monumentX - x, 2) + Math.pow(monumentZ - z, 2)));
									
									ChatComponentText msg = new ChatComponentText(EnumChatFormatting.GREEN + "Monument océanique trouvé en " + monumentX + " ~ " + monumentZ + " (" + dist + " blocs)");
									msg.setChatStyle(new net.minecraft.util.ChatStyle()
										.setChatClickEvent(new net.minecraft.event.ClickEvent(net.minecraft.event.ClickEvent.Action.SUGGEST_COMMAND, "/tp @p " + monumentX + " ~ " + monumentZ))
										.setChatHoverEvent(new net.minecraft.event.HoverEvent(net.minecraft.event.HoverEvent.Action.SHOW_TEXT, new ChatComponentText(EnumChatFormatting.AQUA + "Cliquez pour préparer le /tp"))));
									sender.addChatMessage(msg);
									return;
								}
							}
						}
					}
				}
				throw new CommandException("Aucun Monument océanique trouvé dans un très large rayon.");
			}
			
			// Trouver la structure via le provider du monde pour les structures Vanilla
			ChunkPosition pos = world.getChunkProvider().func_147416_a(world, structureName, x, 0, z);

			if (pos != null) {
				int dx = pos.chunkPosX - x;
				int dz = pos.chunkPosZ - z;
				int distance = MathHelper.floor_double(Math.sqrt(dx * dx + dz * dz));
				
				ChatComponentText msg = new ChatComponentText(EnumChatFormatting.GREEN + "Structure " + structureName + " trouvée en " + pos.chunkPosX + " ~ " + pos.chunkPosZ + " (" + distance + " blocs)");
				msg.setChatStyle(new net.minecraft.util.ChatStyle()
					.setChatClickEvent(new net.minecraft.event.ClickEvent(net.minecraft.event.ClickEvent.Action.SUGGEST_COMMAND, "/tp @p " + pos.chunkPosX + " ~ " + pos.chunkPosZ))
					.setChatHoverEvent(new net.minecraft.event.HoverEvent(net.minecraft.event.HoverEvent.Action.SHOW_TEXT, new ChatComponentText(EnumChatFormatting.AQUA + "Cliquez pour préparer le /tp"))));
				sender.addChatMessage(msg);
			} else {
				throw new CommandException("Structure introuvable : " + structureName + " (Es-tu dans la bonne dimension ?)");
			}
		}
	}
	
	@Override
	public List addTabCompletionOptions(ICommandSender sender, String[] args) {
		if (args.length == 1) {
			return getListOfStringsMatchingLastWord(args, "biome", "Stronghold", "Mineshaft", "Village", "Temple", "Monument", "Fortress");
		} else if (args.length >= 2 && args[0].equalsIgnoreCase("biome")) {
			List<String> biomes = new ArrayList<String>();
			for (BiomeGenBase b : BiomeGenBase.getBiomeGenArray()) {
				if (b != null && b.biomeName != null) {
					biomes.add(b.biomeName.replace(" ", ""));
				}
			}
			return getListOfStringsMatchingLastWord(args, biomes.toArray(new String[0]));
		}
		return null;
	}
}
