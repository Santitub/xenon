package dev.portero.xenon.bridge.litecommand.handler;

import dev.portero.xenon.injector.annotations.Inject;
import dev.portero.xenon.injector.annotations.lite.LiteHandler;
import dev.portero.xenon.notice.NoticeService;
import dev.portero.xenon.viewer.Viewer;
import dev.portero.xenon.viewer.ViewerService;
import dev.rollczi.litecommands.handler.result.ResultHandlerChain;
import dev.rollczi.litecommands.invocation.Invocation;
import dev.rollczi.litecommands.permission.MissingPermissions;
import dev.rollczi.litecommands.permission.MissingPermissionsHandler;
import org.bukkit.command.CommandSender;

@LiteHandler(MissingPermissions.class)
public class PermissionMessage implements MissingPermissionsHandler<CommandSender> {

    private final ViewerService viewerService;
    private final NoticeService noticeService;

    @Inject
    public PermissionMessage(ViewerService viewerService, NoticeService noticeService) {
        this.viewerService = viewerService;
        this.noticeService = noticeService;
    }

    @Override
    public void handle(Invocation<CommandSender> invocation, MissingPermissions missingPermissions, ResultHandlerChain<CommandSender> chain) {
        Viewer viewer = this.viewerService.any(invocation.sender());
        String permissions = missingPermissions.asJoinedText();

        this.noticeService.create()
            .notice(translation -> translation.argument().permissionMessage())
            .placeholder("{PERMISSIONS}", permissions)
            .viewer(viewer)
            .send();
    }
}
