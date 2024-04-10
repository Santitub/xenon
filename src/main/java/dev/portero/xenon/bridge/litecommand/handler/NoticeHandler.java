package dev.portero.xenon.bridge.litecommand.handler;

import dev.portero.xenon.injector.annotations.Inject;
import dev.portero.xenon.injector.annotations.lite.LiteHandler;
import dev.portero.xenon.notice.NoticeService;
import dev.portero.xenon.multification.notice.Notice;
import dev.portero.xenon.viewer.ViewerService;
import dev.portero.xenon.viewer.Viewer;
import dev.rollczi.litecommands.handler.result.ResultHandler;
import dev.rollczi.litecommands.handler.result.ResultHandlerChain;
import dev.rollczi.litecommands.invocation.Invocation;
import org.bukkit.command.CommandSender;

@LiteHandler(Notice.class)
public class NoticeHandler implements ResultHandler<CommandSender, Notice> {

    private final ViewerService viewerService;
    private final NoticeService noticeService;

    @Inject
    public NoticeHandler(ViewerService viewerService, NoticeService noticeService) {
        this.viewerService = viewerService;
        this.noticeService = noticeService;
    }

    @Override
    public void handle(Invocation<CommandSender> invocation, Notice result, ResultHandlerChain<CommandSender> chain) {
        Viewer viewer = this.viewerService.any(invocation.sender());

        this.noticeService.create()
            .viewer(viewer)
            .notice(result)
            .send();
    }

}
