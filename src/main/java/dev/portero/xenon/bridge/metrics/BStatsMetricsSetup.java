package dev.portero.xenon.bridge.metrics;

import dev.portero.xenon.injector.annotations.component.Controller;
import dev.portero.xenon.publish.Subscriber;
import dev.portero.xenon.publish.event.XenonInitializeEvent;
import dev.portero.xenon.publish.Subscribe;

import org.bukkit.plugin.java.JavaPlugin;

@Controller
class BStatsMetricsSetup implements Subscriber {

    @Subscribe(XenonInitializeEvent.class)
    public void onInitialize(JavaPlugin javaPlugin) {
        // Disabled bStats metrics
        // Metrics metrics = new Metrics(javaPlugin, 13964);
    }

}
