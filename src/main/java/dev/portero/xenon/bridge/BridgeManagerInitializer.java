package dev.portero.xenon.bridge;

import dev.portero.xenon.injector.annotations.Bean;
import dev.portero.xenon.injector.annotations.component.BeanSetup;
import dev.portero.xenon.placeholder.PlaceholderRegistry;
import java.util.logging.Logger;
import org.bukkit.Server;
import org.bukkit.plugin.PluginDescriptionFile;

@BeanSetup
class BridgeManagerInitializer {

    @Bean
    BridgeManager bridgeManager(PlaceholderRegistry registry, Server server, Logger logger, PluginDescriptionFile description) {
        BridgeManager bridgeManager = new BridgeManager(registry, description, server, logger);
        bridgeManager.init();

        return bridgeManager;
    }

}
