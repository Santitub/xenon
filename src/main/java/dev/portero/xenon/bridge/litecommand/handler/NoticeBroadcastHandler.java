package dev.portero.xenon.bridge.litecommand.handler;

import dev.portero.xenon.injector.annotations.lite.LiteHandler;
import dev.portero.xenon.multification.notice.NoticeBroadcast;
import dev.rollczi.litecommands.handler.result.ResultHandler;
import dev.rollczi.litecommands.handler.result.ResultHandlerChain;
import dev.rollczi.litecommands.invocation.Invocation;
import org.bukkit.command.CommandSender;

@LiteHandler(NoticeBroadcast.class)
public class NoticeBroadcastHandler implements ResultHandler<CommandSender, NoticeBroadcast> {

    @Override
    public void handle(Invocation<CommandSender> invocation, NoticeBroadcast result, ResultHandlerChain<CommandSender> chain) {
        result.send();
    }

}
