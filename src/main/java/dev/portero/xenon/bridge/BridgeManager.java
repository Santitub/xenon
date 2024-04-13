package dev.portero.xenon.bridge;

import dev.portero.xenon.bridge.placeholderapi.PlaceholderApiExtension;
import dev.portero.xenon.bridge.placeholderapi.PlaceholderApiReplacer;
import dev.portero.xenon.placeholder.PlaceholderRegistry;
import org.bukkit.Server;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;

import java.util.logging.Logger;

class BridgeManager {

    private final PlaceholderRegistry placeholderRegistry;
    private final PluginDescriptionFile pluginDescriptionFile;
    private final Server server;
    private final Logger logger;

    BridgeManager(PlaceholderRegistry placeholderRegistry, PluginDescriptionFile pluginDescriptionFile, Server server, Logger logger) {
        this.placeholderRegistry = placeholderRegistry;
        this.pluginDescriptionFile = pluginDescriptionFile;
        this.server = server;
        this.logger = logger;
    }

    void init() {
        this.setupBridge("PlaceholderAPI", () -> {
            this.placeholderRegistry.registerPlaceholder(new PlaceholderApiReplacer());
            new PlaceholderApiExtension(this.placeholderRegistry, this.pluginDescriptionFile).initialize();
        });
    }

    private void setupBridge(String pluginName, BridgeInitializer bridge) {
        PluginManager pluginManager = this.server.getPluginManager();

        if (pluginManager.isPluginEnabled(pluginName)) {
            bridge.initialize();

            this.logger.info("Successfully hooked into " + pluginName + " bridge!");
        }
    }
}
