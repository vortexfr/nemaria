package fr.nemaria.mods.common.commands;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import cpw.mods.fml.common.FMLCommonHandler;
import fr.nemaria.mods.common.commands.args.StringWalker;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;


public abstract class NemariaCommand extends CommandBase {

    String name;
    CommandNode root;

    public NemariaCommand(String baseName) {
        this.name = baseName;
        root = new LiteralNode(baseName);
    }

    protected void addChild(CommandNode child) {
        root.addChild(child);
    }

    protected void addChildren(CommandNode node) {
        node.getChildren().forEach(this::addChild);
    }

    @Override
    public String getCommandName() { 
    	return name; 
    }

    @Override
    public abstract String getCommandUsage(ICommandSender sender);

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
        CommandContext context = new CommandContext(sender);
        CommandNode node = root.findCurrentNode(new StringWalker(args), context);

        if (context.getException() != null) {
            context.sendFailure(ChatUtils.literal(context.getException()));
            return;
        }
        if (node == null) {
            context.sendFailure(ChatUtils.literal("Commande introuvable: /" + name + " " + String.join(" ", args)));
            return;
        }
        if (node.getCommand() == null) {
            context.sendFailure(ChatUtils.literal("Arguments manquants pour: /" + name));
            return;
        }
        node.getCommand().execute(context);
    }

    @Override
    public List<String> addTabCompletionOptions(ICommandSender sender, String[] args) {
        CommandContext context = new CommandContext(sender);
        StringWalker walker = new StringWalker(args);
        walker.capTop();
        CommandNode node = root.findCurrentNode(walker, context);
        walker.resetCap();
        return getBestMatch(
            walker.canRead() ? walker.peek() : null,
            (node == null ? root : node).getSuggestions(context.getArgumentIndex(), context)
        );
    }

    public static List<String> getBestMatch(String argument, Collection<String> args) {
        List<String> results = new ArrayList<>();
        for (String arg : args) {
            if (argument == null || arg.regionMatches(true, 0, argument, 0, argument.length())) {
                results.add(arg);
            }
        }
        return results;
    }

    public static class CommandContext {
        ICommandSender sender;
        List<CommandNode> nodes = new ArrayList<>();
        Map<String, Object> args  = new HashMap<>();
        String argumentId;
        String exception;
        int argumentIndex;

        public CommandContext(ICommandSender sender) {
        	this.sender = sender; 
        }

        public void setException(String argumentId, String exception) {
            this.argumentId = argumentId;
            this.exception  = exception;
        }

        public String getException() {
        	return exception; 
        }

        public void setArgumentIndex(int index) {
        	this.argumentIndex = index; 
        }
        
        public int  getArgumentIndex() { 
        	return argumentIndex; 
        }

        public void addNode(CommandNode node) {
            nodes.add(node);
            exception = null;
        }

        public boolean addArgument(String name, Object arg) {
            args.put(name, arg);
            return true;
        }

        public <T> T getArgument(String name, Class<T> clz) {
            Object arg = args.get(name);
            if (arg == null) throw new IllegalStateException("Argument [" + name + "] manquant");
            if (clz.isInstance(arg)) return clz.cast(arg);
            throw new IllegalStateException("Argument [" + name + "] est de type [" + arg.getClass().getSimpleName() + "], attendu [" + clz.getSimpleName() + "]");
        }

        public <T> T getArgumentOrDefault(String name, Class<T> clz, T defaultValue) {
            Object arg = args.get(name);
            return arg != null && clz.isInstance(arg) ? clz.cast(arg) : defaultValue;
        }

        public <T> boolean hasArgument(String name, Class<T> clz) {
            Object arg = args.get(name);
            return arg != null && clz.isInstance(arg);
        }

        public ICommandSender getSender() { 
        	return sender; 
        }
        
        public IChatComponent getSenderName(){
        	return sender.func_145748_c_(); 
        }
        
        public UUID getSenderId() {
        	return sender instanceof Entity ? ((Entity) sender).getUniqueID() : null; 
        }
        
        public World getSenderDimension() {
        	return sender.getEntityWorld(); 
        }
        
        public int getSenderDimensionId() {
        	return sender.getEntityWorld().provider.dimensionId; 
        }

        public WorldServer getWorld(int dimension) {
            return FMLCommonHandler.instance().getMinecraftServerInstance().worldServerForDimension(dimension);
        }

        public boolean isOp() {
            return sender instanceof EntityPlayerMP
                ? CommandBase.getCommandSenderAsPlayer(sender).capabilities.isCreativeMode
                : true;
        }

        public void sendSuccess(IChatComponent text) {
            sender.addChatMessage(text);
        }

        public void sendFailure(IChatComponent text) {
            sender.addChatMessage(ChatUtils.applyColor(text.createCopy(), EnumChatFormatting.RED));
        }
    }

    public static class ChatUtils {
        public static IChatComponent literal(String text) {
        	return new ChatComponentText(text); 
        }
        
        public static IChatComponent translate(String key, Object... args)  {
        	return new ChatComponentTranslation(key, args); 
        }

        public static IChatComponent applyColor(IChatComponent text, EnumChatFormatting color) {
            text.getChatStyle().setColor(color);
            return text;
        }
    }
}
