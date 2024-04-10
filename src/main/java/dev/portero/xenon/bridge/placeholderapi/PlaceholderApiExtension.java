package dev.portero.xenon.bridge.placeholderapi;

import dev.portero.xenon.bridge.BridgeInitializer;
import dev.portero.xenon.placeholder.PlaceholderRaw;
import dev.portero.xenon.placeholder.PlaceholderRegistry;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class PlaceholderApiExtension extends PlaceholderExpansion implements BridgeInitializer {

    private final PlaceholderRegistry placeholderRegistry;
    private final PluginDescriptionFile pluginDescriptionFile;

    public PlaceholderApiExtension(PlaceholderRegistry placeholderRegistry, PluginDescriptionFile pluginDescriptionFile) {
        this.placeholderRegistry = placeholderRegistry;
        this.pluginDescriptionFile = pluginDescriptionFile;
    }

    @Override
    public @Nullable String onPlaceholderRequest(Player player, @NotNull String params) {
        Optional<PlaceholderRaw> optional = this.placeholderRegistry.getRawPlaceholder(params);

        if (optional.isPresent()) {
            return optional.get().rawApply(player);
        }

        return "Unknown placeholder!";
    }

    @Override
    public @NotNull String getIdentifier() {
        return "xenon";
    }

    @Override
    public @NotNull String getAuthor() {
        return "Portero";
    }

    @Override
    public @NotNull String getVersion() {
        return this.pluginDescriptionFile.getVersion();
    }

    @Override
    public void initialize() {
        this.register();
    }

}
