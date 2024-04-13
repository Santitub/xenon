package dev.portero.xenon.placeholder;

import dev.portero.xenon.configuration.implementation.PlaceholdersConfiguration;
import dev.portero.xenon.injector.annotations.component.Controller;
import dev.portero.xenon.publish.Subscribe;
import dev.portero.xenon.publish.Subscriber;
import dev.portero.xenon.publish.event.XenonInitializeEvent;
import org.bukkit.Server;

@Controller
class PlaceholdersSetup implements Subscriber {

    @Subscribe(XenonInitializeEvent.class)
    void setUp(PlaceholderRegistry placeholderRegistry, PlaceholdersConfiguration placeholdersConfiguration) {
        placeholdersConfiguration.placeholders.forEach((key, value) -> {
            placeholderRegistry.registerPlaceholder(PlaceholderReplacer.of(key, value));
        });
    }


    @Subscribe(XenonInitializeEvent.class)
    void setUpPlaceholders(PlaceholderRegistry placeholderRegistry, Server server) {
        placeholderRegistry.registerPlaceholder(PlaceholderReplacer.of("online", player -> String.valueOf(server.getOnlinePlayers()
                                                                                                              .size())));
    }

}
