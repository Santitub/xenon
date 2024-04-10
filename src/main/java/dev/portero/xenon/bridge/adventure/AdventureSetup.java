package dev.portero.xenon.bridge.adventure;

import dev.portero.xenon.injector.annotations.Bean;
import dev.portero.xenon.injector.annotations.component.BeanSetup;
import net.kyori.adventure.platform.AudienceProvider;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.plugin.Plugin;

@BeanSetup
class AdventureSetup {

    @Bean
    AudienceProvider skullAPI(Plugin plugin) {
        return BukkitAudiences.create(plugin);
    }

    @Bean
    MiniMessage miniMessage() {
        return MiniMessage.builder()
            .postProcessor(new AdventureLegacyColorPostProcessor())
            .preProcessor(new AdventureLegacyColorPreProcessor())
            .build();
    }

}
