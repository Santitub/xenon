package dev.portero.xenon.bridge.litecommand.configurator;

import dev.portero.xenon.bridge.litecommand.configurator.config.Command;
import dev.portero.xenon.bridge.litecommand.configurator.config.CommandConfiguration;
import dev.portero.xenon.bridge.litecommand.configurator.config.SubCommand;
import dev.portero.xenon.injector.annotations.Inject;
import dev.portero.xenon.injector.annotations.lite.LiteCommandEditor;
import dev.rollczi.litecommands.command.builder.CommandBuilder;
import dev.rollczi.litecommands.editor.Editor;
import dev.rollczi.litecommands.meta.Meta;
import org.bukkit.command.CommandSender;

@LiteCommandEditor
class CommandConfigurator implements Editor<CommandSender> {

    private final CommandConfiguration commandConfiguration;

    @Inject
    CommandConfigurator(CommandConfiguration commandConfiguration) {
        this.commandConfiguration = commandConfiguration;
    }

    @Override
    public CommandBuilder<CommandSender> edit(CommandBuilder<CommandSender> context) {
        Command command = this.commandConfiguration.commands.get(context.name());

        if (command == null) {
            return context;
        }

        for (String child : command.subCommands().keySet()) {
            SubCommand subCommand = command.subCommands().get(child);

            context = context.editChild(child, editor -> editor.name(subCommand.name())
                .aliases(subCommand.aliases())
                .applyMeta(meta -> meta.list(Meta.PERMISSIONS, permissions -> permissions.addAll(command.permissions())))
                .enabled(subCommand.isEnabled())
            );
        }

        return context
            .name(command.name())
            .aliases(command.aliases())
            .applyMeta(meta -> meta.list(Meta.PERMISSIONS, permissions -> permissions.addAll(command.permissions())))
            .enabled(command.isEnabled());
    }

}
