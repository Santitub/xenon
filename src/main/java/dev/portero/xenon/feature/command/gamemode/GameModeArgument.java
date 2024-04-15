package dev.portero.xenon.feature.command.gamemode;

import dev.portero.xenon.bridge.litecommand.argument.AbstractViewerArgument;
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
import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import panda.std.Option;

import java.util.Optional;

@LiteArgument(type = GameMode.class)
class GameModeArgument extends AbstractViewerArgument<GameMode> {

    private final GameModeCommand gameModeCommand;

    @Inject
    GameModeArgument(ViewerService viewerService, TranslationManager translationManager, GameModeCommand gameModeCommand) {
        super(viewerService, translationManager);
        this.gameModeCommand = gameModeCommand;
    }

    @Override
    public ParseResult<GameMode> parse(Invocation<CommandSender> invocation, String argument, Translation translation) {
        Option<GameMode> gameMode = Option.supplyThrowing(IllegalArgumentException.class, () -> GameMode.valueOf(argument.toUpperCase()));

        if (gameMode.isPresent()) {
            return ParseResult.success(gameMode.get());
        }

        Optional<GameMode> alias = this.gameModeCommand.getByAlias(argument);

        return alias
                .map(ParseResult::success)
                .orElseGet(() -> ParseResult.failure(translation.player().gameModeNotCorrect()));
    }

    @Override
    public SuggestionResult suggest(Invocation<CommandSender> invocation, Argument<GameMode> argument, SuggestionContext context) {
        return this.gameModeCommand.getAvailableAliases()
                .stream()
                .collect(SuggestionResult.collector());
    }
}
