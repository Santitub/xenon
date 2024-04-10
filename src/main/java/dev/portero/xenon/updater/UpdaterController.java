package dev.portero.xenon.updater;

import dev.portero.xenon.configuration.implementation.PluginConfiguration;
import dev.portero.xenon.injector.annotations.Inject;
import dev.portero.xenon.injector.annotations.component.Controller;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.platform.AudienceProvider;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.concurrent.CompletableFuture;

@Controller
class UpdaterController implements Listener {

    private static final String NEW_VERSION_AVAILABLE = "<b><gradient:#8a1212:#fc6b03>Xenon:</gradient></b> <color:#fce303>New version of Xenon is available, please update!";

    private final PluginConfiguration pluginConfiguration;
    private final UpdaterService updaterService;
    private final AudienceProvider audienceProvider;
    private final MiniMessage miniMessage;

    @Inject
    UpdaterController(PluginConfiguration pluginConfiguration, UpdaterService updaterService, AudienceProvider audienceProvider, MiniMessage miniMessage) {
        this.pluginConfiguration = pluginConfiguration;
        this.updaterService = updaterService;
        this.audienceProvider = audienceProvider;
        this.miniMessage = miniMessage;
    }

    @EventHandler
    void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        if (!player.hasPermission("xenon.receiveupdates")) {
            return;
        }

        if (!this.pluginConfiguration.shouldReceivePluginUpdates) {
            return;
        }

        Audience playerAudience = this.audienceProvider.player(player.getUniqueId());

        CompletableFuture<Boolean> isUpToDate = this.getUpToDate();

        isUpToDate.whenComplete((isUpToUpdate, throwable) -> {
            if (throwable != null) {
                throwable.printStackTrace();
                return;
            }

            if (!isUpToUpdate) {
                playerAudience.sendMessage(this.miniMessage.deserialize(NEW_VERSION_AVAILABLE));
            }
        });
    }

    private CompletableFuture<Boolean> getUpToDate() {
        // return this.updaterService.isUpToDate();
        return CompletableFuture.completedFuture(true);
    }

}
