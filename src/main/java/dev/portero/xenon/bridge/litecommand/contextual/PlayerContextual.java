package dev.portero.xenon.bridge.litecommand.contextual;

import dev.portero.xenon.injector.annotations.Inject;
import dev.portero.xenon.injector.annotations.lite.LiteContextual;
import dev.portero.xenon.multification.notice.Notice;
import dev.portero.xenon.translation.Translation;
import dev.portero.xenon.translation.TranslationManager;
import dev.rollczi.litecommands.context.ContextProvider;
import dev.rollczi.litecommands.context.ContextResult;
import dev.rollczi.litecommands.invocation.Invocation;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@LiteContextual(Player.class)
class PlayerContextual implements ContextProvider<CommandSender, Player> {

    private final TranslationManager translationManager;

    @Inject
    PlayerContextual(TranslationManager translationManager) {
        this.translationManager = translationManager;
    }

    @Override
    public ContextResult<Player> provide(Invocation<CommandSender> invocation) {
        if (invocation.sender() instanceof Player player) {
            return ContextResult.ok(() -> player);
        }

        Translation translation = this.translationManager.getDefaultMessages();
        Notice onlyPlayer = translation.argument().onlyPlayer();

        return ContextResult.error(onlyPlayer);
    }

}
