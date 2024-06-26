package dev.portero.xenon.bridge.litecommand.argument;

import dev.portero.xenon.injector.annotations.Inject;
import dev.portero.xenon.injector.annotations.lite.LiteArgument;
import dev.portero.xenon.translation.Translation;
import dev.portero.xenon.translation.TranslationManager;
import dev.portero.xenon.viewer.ViewerService;
import dev.rollczi.litecommands.argument.Argument;
import dev.rollczi.litecommands.argument.parser.ParseResult;
import dev.rollczi.litecommands.invocation.Invocation;
import dev.rollczi.litecommands.suggestion.SuggestionContext;
import dev.rollczi.litecommands.suggestion.SuggestionResult;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;

@LiteArgument(type = Player.class)
public class PlayerArgument extends AbstractViewerArgument<Player> {

    private final Server server;

    @Inject
    public PlayerArgument(ViewerService viewerService, TranslationManager translationManager, Server server) {
        super(viewerService, translationManager);
        this.server = server;
    }

    @Override
    public ParseResult<Player> parse(Invocation<CommandSender> invocation, String argument, Translation translation) {
        Player target = this.server.getPlayer(argument);

        if (target == null) {
            return ParseResult.failure(translation.argument().offlinePlayer());
        }

        return ParseResult.success(target);
    }

    @Override
    public SuggestionResult suggest(Invocation<CommandSender> invocation, Argument<Player> argument, SuggestionContext context) {
        return this.server.getOnlinePlayers().stream()
            .map(HumanEntity::getName)
            .collect(SuggestionResult.collector());
    }
}
