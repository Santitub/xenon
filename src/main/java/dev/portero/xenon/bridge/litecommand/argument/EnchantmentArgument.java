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
import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;

import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.StreamSupport;

@LiteArgument(type = Enchantment.class)
class EnchantmentArgument extends AbstractViewerArgument<Enchantment> {

    @Inject
    EnchantmentArgument(ViewerService viewerService, TranslationManager translationManager) {
        super(viewerService, translationManager);
    }

    @Override
    public ParseResult<Enchantment> parse(Invocation<CommandSender> invocation, String argument, Translation translation) {
        Enchantment enchantment = Registry.ENCHANTMENT.get(NamespacedKey.minecraft(argument));

        if (enchantment == null) {
            return ParseResult.failure(translation.argument().noEnchantment());
        }

        return ParseResult.success(enchantment);
    }

    @Override
    public SuggestionResult suggest(Invocation<CommandSender> invocation, Argument<Enchantment> argument, SuggestionContext context) {
        return StreamSupport.stream(Spliterators.spliteratorUnknownSize(Registry.ENCHANTMENT.iterator(), Spliterator.ORDERED),
                false).map(enchantment -> enchantment.getKey().getKey()).collect(SuggestionResult.collector());
    }


}
