package dev.portero.xenon.bridge.litecommand;

import dev.portero.xenon.injector.annotations.Bean;
import dev.portero.xenon.injector.annotations.component.BeanSetup;
import dev.portero.xenon.injector.bean.BeanFactory;
import dev.portero.xenon.publish.Subscribe;
import dev.portero.xenon.publish.Subscriber;
import dev.portero.xenon.publish.event.XenonInitializeEvent;
import dev.portero.xenon.publish.event.XenonShutdownEvent;
import dev.rollczi.litecommands.LiteCommands;
import dev.rollczi.litecommands.LiteCommandsBuilder;
import dev.rollczi.litecommands.adventure.bukkit.platform.LiteAdventurePlatformExtension;
import dev.rollczi.litecommands.annotations.LiteCommandsAnnotations;
import dev.rollczi.litecommands.bukkit.LiteBukkitFactory;
import net.kyori.adventure.platform.AudienceProvider;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

@BeanSetup
class LiteCommandsSetup implements Subscriber {

    @Bean
    public LiteCommandsBuilder<CommandSender, ?, ?> liteCommandsBuilder(
        Plugin plugin,
        Server server,
        AudienceProvider audiencesProvider,
        MiniMessage miniMessage,
        LiteCommandsAnnotations<CommandSender> liteCommandsAnnotations
    ) {
        return LiteBukkitFactory.builder("xenon", plugin, server)
            .commands(liteCommandsAnnotations)
            .extension(new LiteAdventurePlatformExtension<CommandSender>(audiencesProvider), extension -> extension
                .serializer(miniMessage)
            );
    }

    @Bean
    public LiteCommandsAnnotations<CommandSender> liteCommandsAnnotations() {
        return LiteCommandsAnnotations.create();
    }

    @Subscribe(XenonInitializeEvent.class)
    public void onEnable(BeanFactory beanFactory, LiteCommandsBuilder<CommandSender, ?, ?> liteCommandsBuilder) {
        LiteCommands<CommandSender> register = liteCommandsBuilder.build();
        beanFactory.addCandidate(LiteCommands.class, () -> register);
    }

    @Subscribe(XenonShutdownEvent.class)
    public void onShutdown(LiteCommands<CommandSender> liteCommands) {
        liteCommands.unregister();
    }

}
