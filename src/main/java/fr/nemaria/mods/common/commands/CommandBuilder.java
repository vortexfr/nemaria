package fr.nemaria.mods.common.commands;

import java.util.Stack;

import fr.nemaria.mods.common.commands.args.IArgument;

public class CommandBuilder {
    Stack<CommandNode> queue = new Stack<>();
    CommandNode current;

    public CommandBuilder(String literal) {
        current = new LiteralNode(literal);
    }

    public CommandBuilder(String name, IArgument<?> argument) {
        current = new ArgumentNode<>(name, argument);
    }

    public CommandBuilder literal(String literal) {
        return push(new LiteralNode(literal));
    }

    public CommandBuilder literal(String literal, ICommand command) {
        return push(new LiteralNode(literal).withCommand(command));
    }

    public CommandBuilder arg(String name, IArgument<?> type) {
        return push(new ArgumentNode<>(name, type));
    }

    public CommandBuilder arg(String name, IArgument<?> type, ICommand command) {
        return push(new ArgumentNode<>(name, type).withCommand(command));
    }

    public CommandBuilder arg(String name, IArgument<?> type, ArgumentNode.SuggestionProvider suggestion) {
        return push(new ArgumentNode<>(name, type).withSuggestion(suggestion));
    }

    public CommandBuilder arg(String name, IArgument<?> type, ArgumentNode.SuggestionProvider suggestion, ICommand command) {
        return push(new ArgumentNode<>(name, type).withSuggestion(suggestion).withCommand(command));
    }

    private CommandBuilder push(CommandNode command) {
        current.addChild(command);
        queue.push(current);
        current = command;
        return this;
    }

    /** Attache une commande au nœud courant (racine ou nœud en cours) */
    public CommandBuilder withCommand(ICommand command) {
        current.command = command;
        return this;
    }

    public CommandBuilder pop() {
        current = queue.pop();
        return this;
    }

    public CommandBuilder pop(int layers) {
        while (layers-- > 0) current = queue.pop();
        return this;
    }

    public CommandBuilder popTop() {
        while (!queue.isEmpty()) current = queue.pop();
        return this;
    }

    public CommandNode build() {
        while (!queue.isEmpty()) current = queue.pop();
        return current;
    }
}
