package dev.portero.xenon.scheduler;

import dev.portero.xenon.bukkit.scheduler.BukkitSchedulerImpl;
import dev.portero.xenon.injector.annotations.Bean;
import dev.portero.xenon.injector.annotations.component.BeanSetup;
import dev.portero.xenon.publish.Subscriber;
import org.bukkit.plugin.Plugin;

@BeanSetup
public class SchedulerSetup implements Subscriber {

    @Bean
    public Scheduler scheduler(Plugin plugin) {
        return new BukkitSchedulerImpl(plugin);
    }

}
