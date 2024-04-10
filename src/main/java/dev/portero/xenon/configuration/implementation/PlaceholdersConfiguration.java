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
        "# An example is {prefix}, whenever you use {prefix} the specified value will be displayed",
        "# Remember that it only works in Xenon!"
    })
    public Map<String, String> placeholders = Map.of(
        "prefix", "&7"
    );

    @Override
    public Resource resource(File folder) {
        return Source.of(folder, "placeholders.yml");
    }
}
