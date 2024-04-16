package dev.portero.xenon.feature.command.heal;

import dev.portero.xenon.injector.annotations.Inject;
import dev.portero.xenon.notice.NoticeService;
import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import org.bukkit.entity.Player;

@Command(name = "heal")
public class HealCommand {

    private final NoticeService noticeService;

    @Inject
    public HealCommand(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @Execute
    public void execute(@Context Player sender) {
        sender.setHealth(20);
        sender.setFoodLevel(20);
        sender.setSaturation(20);
    }

    @Execute
    public void execute(@Context Player sender, @Arg Player target) {
        target.setHealth(20);
        target.setFoodLevel(20);
        target.setSaturation(20);
    }
}
