package dev.portero.xenon.feature.fly;

import dev.portero.xenon.injector.annotations.Inject;
import dev.portero.xenon.notice.NoticeService;
import dev.portero.xenon.viewer.Viewer;
import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import org.bukkit.entity.Player;

@Command(name = "fly")
class FlyCommand {

    private final NoticeService noticeService;

    @Inject
    FlyCommand(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @Execute
    @Permission("xenon.fly")
    void execute(@Context Player player) {
        this.toggleFlight(player);
    }

    @Execute
    @Permission("xenon.fly.other")
    void execute(@Context Viewer viewer, @Arg Player target) {
        this.toggleFlight(target);

        this.noticeService.create()
            .notice(translation -> target.getAllowFlight() ? translation.player().flySetEnable() : translation.player()
                .flySetDisable())
            .placeholder("{PLAYER}", target.getName())
            .placeholder("{STATE}", translation -> target.getAllowFlight() ? translation.format()
                .enable() : translation.format().disable())
            .viewer(viewer)
            .send();
    }

    private void toggleFlight(@Context Player player) {
        player.setAllowFlight(!player.getAllowFlight());

        this.noticeService.create()
            .notice(translation -> player.getAllowFlight() ? translation.player().flyEnable() : translation.player()
                .flyDisable())
            .placeholder("{STATE}", translation -> player.getAllowFlight() ? translation.format()
                .enable() : translation.format().disable())
            .player(player.getUniqueId())
            .send();
    }
}
