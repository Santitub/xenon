package dev.portero.xenon.configuration.implementation;

import dev.portero.xenon.bukkit.position.Position;
import dev.portero.xenon.configuration.ReloadableConfig;
import dev.portero.xenon.injector.annotations.component.ConfigurationFile;
import net.dzikoysk.cdn.entity.Exclude;
import net.dzikoysk.cdn.source.Resource;
import net.dzikoysk.cdn.source.Source;

import java.io.File;

@ConfigurationFile
public class LocationsConfiguration implements ReloadableConfig {

    @Exclude
    public static final Position EMPTY_POSITION = new Position(0, 0, 0, 0.0f, 0.0f, Position.NONE_WORLD);

    @Override
    public Resource resource(File folder) {
        return Source.of(folder, "locations.yml");
    }
}
