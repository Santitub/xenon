package dev.portero.xenon.bridge.litecommand.configurator.config;

import lombok.Getter;
import net.dzikoysk.cdn.entity.Contextual;

import java.util.ArrayList;
import java.util.List;

@Contextual
public class SubCommand {

    public String name;

    @Getter
    public boolean enabled;

    public List<String> aliases = new ArrayList<>();
    public List<String> permissions = new ArrayList<>();

    public SubCommand() {
    }

    public SubCommand(String name, boolean enabled, List<String> aliases, List<String> permissions) {
        this.name = name;
        this.enabled = enabled;
        this.aliases = aliases;
        this.permissions = permissions;
    }

    public String name() {
        return this.name;
    }

    public List<String> aliases() {
        return this.aliases;
    }

    public List<String> permissions() {
        return this.permissions;
    }
}
