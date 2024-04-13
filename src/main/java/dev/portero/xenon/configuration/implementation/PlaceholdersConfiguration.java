package dev.portero.xenon.configuration.implementation;

import dev.portero.xenon.configuration.ReloadableConfig;
import dev.portero.xenon.injector.annotations.component.ConfigurationFile;
import net.dzikoysk.cdn.entity.Description;
import net.dzikoysk.cdn.source.Resource;
import net.dzikoysk.cdn.source.Source;

import java.io.File;
import java.util.Map;

@ConfigurationFile
public class PlaceholdersConfiguration implements ReloadableConfig {

    @Description({
        "# Enables the creation of global placeholders",
        "# Remember that you can use the placeholders in Xenon configuration files",
        "# Example: &7Hello, {prefix}&7World!"
    })

    public Map<String, String> placeholders = Map.of(
    );

    @Override
    public Resource resource(File folder) {
        return Source.of(folder, "placeholders.yml");
    }
}
