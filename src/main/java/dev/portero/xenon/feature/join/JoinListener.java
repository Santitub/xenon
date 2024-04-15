package dev.portero.xenon.feature.join;

import dev.portero.xenon.injector.annotations.Inject;
import dev.portero.xenon.injector.annotations.component.Controller;
import dev.portero.xenon.notice.NoticeService;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

@Controller
public class JoinListener implements Listener {

    private final NoticeService noticeService;
    private final Server server;

    @Inject
    public JoinListener(NoticeService noticeService, Server server) {
        this.noticeService = noticeService;
        this.server = server;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        event.joinMessage(null);

        this.noticeService.create()
                .notice(translation -> translation.player().joinMessage())
                .placeholder("{PLAYER}", player.getName())
                .all()
                .send();
    }
}
