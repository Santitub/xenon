package dev.portero.xenon.feature.essentials.gamemode;

import dev.portero.xenon.injector.annotations.Inject;
import dev.portero.xenon.notice.NoticeService;
import dev.portero.xenon.viewer.Viewer;
import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import dev.rollczi.litecommands.invalidusage.InvalidUsage;
import dev.rollczi.litecommands.invalidusage.InvalidUsageException;
import dev.rollczi.litecommands.invocation.Invocation;
import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Map;

@Command(name = "gamemode", aliases = {"gm"})
@Permission("xenon.command.gamemode")
public class GameModeCommand extends GameModeArgumentResolver {

    private final NoticeService noticeService;

    @Inject
    GameModeCommand(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @Execute
    void execute(@Context Invocation<CommandSender> invocation, @Context Player player) {
        GameMode gameMode = this.gameModeShortCuts.entrySet().stream()
                .filter(entry -> entry.getValue().contains(invocation.label()))
                .map(Map.Entry::getKey)
                .findFirst()
                .orElse(null);

        if (gameMode == null) {
            throw new InvalidUsageException(InvalidUsage.Cause.INVALID_ARGUMENT);
        }

        this.execute(player, gameMode);
    }

    @Execute
    void execute(@Context Player sender, @Arg GameMode gameMode) {
        sender.setGameMode(gameMode);

        this.noticeService.create()
                .notice(translation -> translation.player().gameModeMessage())
                .placeholder("{GAMEMODE}", gameMode.name())
                .player(sender.getUniqueId())
                .send();
    }

    @Execute
    void execute(@Context Viewer viewer, @Arg GameMode gameMode, @Arg Player player) {
        player.setGameMode(gameMode);

        this.noticeService.create()
                .notice(translation -> translation.player().gameModeMessage())
                .placeholder("{GAMEMODE}", gameMode.name())
                .player(player.getUniqueId())
                .send();

        this.noticeService.create()
                .notice(translation -> translation.player().gameModeSetMessage())
                .placeholder("{GAMEMODE}", gameMode.name())
                .placeholder("{PLAYER}", player.getName())
                .viewer(viewer)
                .send();
    }
}
