package dev.portero.xenon.bridge.litecommand.contextual;

import dev.portero.xenon.injector.annotations.Inject;
import dev.portero.xenon.injector.annotations.lite.LiteContextual;
import dev.portero.xenon.viewer.Viewer;
import dev.portero.xenon.viewer.ViewerService;
import dev.rollczi.litecommands.context.ContextProvider;
import dev.rollczi.litecommands.context.ContextResult;
import dev.rollczi.litecommands.invocation.Invocation;
import org.bukkit.command.CommandSender;

@LiteContextual(Viewer.class)
class ViewerContextual implements ContextProvider<CommandSender, Viewer> {

    private final ViewerService bukkitViewerService;

    @Inject
    ViewerContextual(ViewerService bukkitViewerService) {
        this.bukkitViewerService = bukkitViewerService;
    }

    @Override
    public ContextResult<Viewer> provide(Invocation<CommandSender> invocation) {
        return ContextResult.ok(() -> this.bukkitViewerService.any(invocation.sender()));
    }

}
