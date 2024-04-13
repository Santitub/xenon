package dev.portero.xenon.bridge.litecommand.handler;

import dev.portero.xenon.injector.annotations.lite.LiteHandler;
import dev.portero.xenon.multification.notice.NoticeBroadcast;
import dev.portero.xenon.translation.Translation;
import dev.portero.xenon.viewer.Viewer;
import dev.rollczi.litecommands.handler.result.ResultHandler;
import dev.rollczi.litecommands.handler.result.ResultHandlerChain;
import dev.rollczi.litecommands.invocation.Invocation;
import org.bukkit.command.CommandSender;

@LiteHandler(NoticeBroadcast.class)
public class NoticeBroadcastHandler<T extends NoticeBroadcast<Viewer, Translation, T>> implements ResultHandler<CommandSender, NoticeBroadcast<Viewer, Translation, T>> {


    @Override
    public void handle(Invocation<CommandSender> invocation, NoticeBroadcast<Viewer, Translation, T> result, ResultHandlerChain<CommandSender> chain) {
        result.send();
    }
}
