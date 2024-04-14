package dev.portero.xenon.feature.plugin;

import com.google.common.base.Stopwatch;
import dev.portero.xenon.configuration.ConfigurationManager;
import dev.portero.xenon.injector.annotations.Inject;
import dev.rollczi.litecommands.annotations.async.Async;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.plugin.PluginDescriptionFile;

import java.util.concurrent.TimeUnit;

@Command(name = "xenon")
class XenonCommand {

    private static final String RELOAD_MESSAGE = "<green>The configuration has been reloaded successfully! (<yellow>%d<green>ms)";

    private final ConfigurationManager configurationManager;
    private final PluginDescriptionFile descriptionFile;
    private final MiniMessage miniMessage;

    @Inject
    XenonCommand(ConfigurationManager configurationManager, PluginDescriptionFile descriptionFile, MiniMessage miniMessage) {
        this.configurationManager = configurationManager;
        this.descriptionFile = descriptionFile;
        this.miniMessage = miniMessage;
    }

    @Execute
    @Permission("xenon.command.base")
    void execute(@Context Audience audience) {
        TextComponent message = Component.text("Running ")
            .color(NamedTextColor.GREEN)
            .append(Component.text(this.descriptionFile.getName())
                        .color(NamedTextColor.YELLOW))
            .append(Component.text(" v")
                        .color(NamedTextColor.GREEN))
            .append(Component.text(this.descriptionFile.getVersion())
                        .color(NamedTextColor.YELLOW))
            .append(Component.text(" by ")
                        .color(NamedTextColor.GREEN))
            .append(Component.text(String.join(", ", this.descriptionFile.getAuthors()))
                        .color(NamedTextColor.YELLOW))
            .append(Component.text(".")
                        .color(NamedTextColor.GREEN));

        audience.sendMessage(message);
    }

    @Async
    @Execute(name = "reload")
    @Permission("xenon.command.base.reload")
    void reload(@Context Audience audience) {
        long millis = this.reload();
        Component message = this.miniMessage.deserialize(RELOAD_MESSAGE.formatted(millis));

        audience.sendMessage(message);
    }

    private long reload() {
        Stopwatch stopwatch = Stopwatch.createStarted();
        this.configurationManager.reload();

        return stopwatch.elapsed(TimeUnit.MILLISECONDS);
    }

}
