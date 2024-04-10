package dev.portero.xenon.bridge.litecommand.configurator.config;

import dev.portero.xenon.configuration.ReloadableConfig;
import dev.portero.xenon.injector.annotations.component.ConfigurationFile;
import net.dzikoysk.cdn.source.Resource;
import net.dzikoysk.cdn.source.Source;

import java.io.File;
import java.util.List;
import java.util.Map;

@ConfigurationFile
public class CommandConfiguration implements ReloadableConfig {

    public Map<String, Command> commands = Map.of(
            "xenon", new Command(
                    "xenon",
                    List.of("xenon"),
                    List.of("xenon.xenon"),
                    Map.of("reload", new SubCommand("reload", true, List.of("rl"), List.of("xenon.reload"))),
                    true)
    );

    @Override
    public Resource resource(File folder) {
        return Source.of(folder, "commands.yml");
    }

}
