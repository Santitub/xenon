package dev.portero.xenon.bridge.litecommand.argument;

import dev.portero.xenon.injector.annotations.Inject;
import dev.portero.xenon.injector.annotations.lite.LiteArgument;
import dev.portero.xenon.translation.Translation;
import dev.portero.xenon.translation.TranslationManager;
import dev.portero.xenon.util.time.DurationUtil;
import dev.portero.xenon.viewer.ViewerService;
import dev.rollczi.litecommands.argument.Argument;
import dev.rollczi.litecommands.argument.parser.ParseResult;
import dev.rollczi.litecommands.invocation.Invocation;
import dev.rollczi.litecommands.suggestion.SuggestionContext;
import dev.rollczi.litecommands.suggestion.SuggestionResult;
import org.bukkit.command.CommandSender;

import java.time.Duration;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;

@LiteArgument(type = Duration.class)
class DurationArgument extends AbstractViewerArgument<Duration> {

    private static final List<Duration> SUGGESTED_DURATIONS = Arrays.asList(
        Duration.ofSeconds(1),
        Duration.ofSeconds(5),
        Duration.ofSeconds(10),
        Duration.ofSeconds(30),
        Duration.ofMinutes(1),
        Duration.ofMinutes(1).plus(Duration.ofSeconds(30)),
        Duration.ofMinutes(5),
        Duration.ofMinutes(10)
    );

    @Inject
    DurationArgument(ViewerService viewerService, TranslationManager translationManager) {
        super(viewerService, translationManager);
    }

    @Override
    public ParseResult<Duration> parse(Invocation<CommandSender> invocation, String argument, Translation translation) {
        try {
            return ParseResult.success(Duration.parse("PT" + argument));
        } catch (DateTimeParseException exception) {
            return ParseResult.failure(translation.argument().invalidTimeFormat());
        }
    }

    @Override
    public SuggestionResult suggest(
        Invocation<CommandSender> invocation,
        Argument<Duration> argument,
        SuggestionContext context
    ) {
        return SUGGESTED_DURATIONS.stream()
            .map(DurationUtil::format)
            .collect(SuggestionResult.collector());
    }
}
