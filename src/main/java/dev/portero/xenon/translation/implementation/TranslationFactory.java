package dev.portero.xenon.translation.implementation;

import dev.portero.xenon.feature.language.Language;
import dev.portero.xenon.translation.AbstractTranslation;

import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

public final class TranslationFactory {

    private static final Function<Language, AbstractTranslation> OTHER_LANG_TRANSLATION = ENTranslation::new;

    private static final Map<Language, Supplier<AbstractTranslation>> DEFAULT_TRANSLATIONS = Map.of(
        Language.EN, ENTranslation::new,
        Language.ES, ESTranslation::new
    );

    private TranslationFactory() {
    }

    public static AbstractTranslation create(Language language) {
        Supplier<AbstractTranslation> translationSupplier = DEFAULT_TRANSLATIONS.get(language);

        if (translationSupplier != null) {
            return translationSupplier.get();
        }

        return OTHER_LANG_TRANSLATION.apply(language);
    }
}
