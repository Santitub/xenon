package dev.portero.xenon.bridge.skullapi;

import dev.portero.xenon.publish.Subscriber;
import dev.portero.xenon.publish.event.XenonShutdownEvent;
import dev.portero.xenon.injector.annotations.Bean;
import dev.portero.xenon.injector.annotations.component.BeanSetup;
import dev.portero.xenon.publish.Subscribe;
import dev.rollczi.liteskullapi.LiteSkullFactory;
import dev.rollczi.liteskullapi.SkullAPI;
import org.bukkit.plugin.Plugin;

@BeanSetup
class SkullAPISetup implements Subscriber {

    @Bean
    public SkullAPI skullAPI(Plugin plugin) {
        return LiteSkullFactory.builder()
            .bukkitScheduler(plugin)
            .build();
    }

    @Subscribe(XenonShutdownEvent.class)
    public void onShutdown(SkullAPI skullAPI) {
        skullAPI.shutdown();
    }

}
