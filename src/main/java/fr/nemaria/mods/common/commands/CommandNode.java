package fr.nemaria.mods.common.commands;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import fr.nemaria.mods.common.commands.NemariaCommand.CommandContext;
import fr.nemaria.mods.common.commands.args.StringWalker;

public abstract class CommandNode {
    Map<String, CommandNode> children = new LinkedHashMap<>();
    Map<String, LiteralNode> literals = new LinkedHashMap<>();
    Map<String, ArgumentNode<?>> arguments = new LinkedHashMap<>();
    ICommand command;

    public abstract String getName();
    public abstract List<String> getExamples(CommandContext context, int argumentIndex);

    public Collection<CommandNode> getChildren() { return children.values(); }
    public ICommand getCommand() { return command; }

    public List<String> getSuggestions(int argumentIndex, CommandContext context) {
        List<String> possible = new ArrayList<>();
        for (CommandNode node : children.values()) {
            possible.addAll(node.getExamples(context, argumentIndex));
        }
        return possible;
    }

    public CommandNode withCommand(ICommand command) {
        this.command = command;
        return this;
    }

    public void addChild(CommandNode node) {
        CommandNode child = children.get(node.getName());
        if (child != null) {
            if (node.command != null) child.command = node.command;
            node.getChildren().forEach(child::addChild);
            return;
        }
        children.put(node.getName(), node);
        if (node instanceof LiteralNode) literals.put(node.getName(), (LiteralNode) node);
        if (node instanceof ArgumentNode) arguments.put(node.getName(), (ArgumentNode<?>) node);
    }

    public CommandNode findCurrentNode(StringWalker walker, CommandContext context) {
        context.addNode(this);
        if (!walker.canRead()) return this;

        if (!literals.isEmpty()) {
            LiteralNode node = literals.get(walker.peek());
            if (node != null) {
                walker.skip();
                return node.findCurrentNode(walker, context);
            }
        }

        if (!arguments.isEmpty()) {
            for (ArgumentNode<?> node : arguments.values()) {
                StringWalker subWalker = walker.copy();
                if (node.isValid(subWalker, context)) {
                    walker.apply(subWalker);
                    return node.findCurrentNode(walker, context);
                }
                int diff = subWalker.getIndex() - walker.getIndex();
                if (diff > 0 && diff < node.getArgumentElements()) {
                    walker.advance(diff);
                    context.setArgumentIndex(diff);
                    return this;
                }
            }
        }
        return null;
    }

    @Override
    public String toString() { 
    	return getName(); 
    }
}
