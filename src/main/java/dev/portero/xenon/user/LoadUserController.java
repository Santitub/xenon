package dev.portero.xenon.user;

import dev.portero.xenon.injector.annotations.Inject;
import dev.portero.xenon.injector.annotations.component.Controller;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerLoadEvent;

@Controller
class LoadUserController implements Listener {

    private final UserManager userManager;
    private final Server server;

    @Inject
    LoadUserController(UserManager userManager, Server server) {
        this.userManager = userManager;
        this.server = server;
    }

    @EventHandler
    void onReload(ServerLoadEvent event) {
        if (event.getType() != ServerLoadEvent.LoadType.RELOAD) {
            return;
        }

        for (Player player : this.server.getOnlinePlayers()) {
            this.userManager.create(player.getUniqueId(), player.getName());
        }
    }
}
