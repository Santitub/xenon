package dev.portero.xenon.translation;

import dev.portero.xenon.configuration.ConfigurationManager;
import dev.portero.xenon.injector.annotations.Bean;
import dev.portero.xenon.injector.annotations.component.BeanSetup;
import dev.portero.xenon.feature.language.config.LanguageConfiguration;
import dev.portero.xenon.translation.implementation.TranslationFactory;
import java.util.List;
import panda.std.stream.PandaStream;

@BeanSetup
class TranslationManagerSetup {

    @Bean
    TranslationManager translationManager(ConfigurationManager configurationManager, LanguageConfiguration languageConfiguration) {
        List<AbstractTranslation> usedMessagesList = PandaStream.of(languageConfiguration.languages)
            .map(TranslationFactory::create)
            .toList();

        Translation defaultTranslation = PandaStream.of(usedMessagesList)
            .find(usedMessages -> usedMessages.getLanguage().equals(languageConfiguration.defaultLanguage))
            .orThrow(() -> new RuntimeException("Default language not found!"));

        TranslationManager translationManager = new TranslationManager(defaultTranslation);

        for (ReloadableTranslation message : usedMessagesList) {
            configurationManager.load(message);
            translationManager.loadLanguage(message.getLanguage(), message);
        }

        return translationManager;
    }

}
