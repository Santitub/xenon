package dev.portero.xenon.bridge.litecommand.contextual;

import dev.portero.xenon.injector.annotations.Inject;
import dev.portero.xenon.injector.annotations.lite.LiteContextual;
import dev.portero.xenon.multification.notice.Notice;
import dev.portero.xenon.translation.Translation;
import dev.portero.xenon.translation.TranslationManager;
import dev.portero.xenon.user.User;
import dev.portero.xenon.user.UserManager;
import dev.rollczi.litecommands.context.ContextProvider;
import dev.rollczi.litecommands.context.ContextResult;
import dev.rollczi.litecommands.invocation.Invocation;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@LiteContextual(User.class)
class UserContextual implements ContextProvider<CommandSender, User> {

    private final TranslationManager translationManager;
    private final UserManager userManager;

    @Inject
    UserContextual(TranslationManager translationManager, UserManager userManager) {
        this.translationManager = translationManager;
        this.userManager = userManager;
    }

    @Override
    public ContextResult<User> provide(Invocation<CommandSender> invocation) {
        if (invocation.sender() instanceof Player player) {
            return ContextResult.ok(() -> this.userManager.getUser(player.getUniqueId())
                .orElseThrow(() -> new IllegalStateException("Player " + player.getName() + " is not registered!")));
        }

        Translation translation = this.translationManager.getDefaultMessages();
        Notice onlyPlayer = translation.argument().onlyPlayer();

        return ContextResult.error(onlyPlayer);
    }

}
