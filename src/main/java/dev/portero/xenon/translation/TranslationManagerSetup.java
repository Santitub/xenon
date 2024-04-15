package dev.portero.xenon.translation;

import dev.portero.xenon.configuration.ConfigurationManager;
import dev.portero.xenon.feature.language.Language;
import dev.portero.xenon.feature.language.config.LanguageConfiguration;
import dev.portero.xenon.injector.annotations.Bean;
import dev.portero.xenon.injector.annotations.component.BeanSetup;
import dev.portero.xenon.translation.implementation.TranslationFactory;
import panda.std.stream.PandaStream;

import java.util.List;

@BeanSetup
class TranslationManagerSetup {

    @Bean
    TranslationManager translationManager(ConfigurationManager configurationManager, LanguageConfiguration languageConfiguration) {
        List<AbstractTranslation> usedMessagesList = this.createTranslations(languageConfiguration);
        Translation defaultTranslation = this.findDefaultTranslation(usedMessagesList, languageConfiguration.defaultLanguage);

        TranslationManager translationManager = new TranslationManager(defaultTranslation);
        usedMessagesList.forEach(message -> this.configureTranslation(configurationManager, translationManager, message));

        return translationManager;
    }

    private List<AbstractTranslation> createTranslations(LanguageConfiguration languageConfiguration) {
        try (PandaStream<Language> languageStream = PandaStream.of(languageConfiguration.languages)) {
            return languageStream.map(TranslationFactory::create).toList();
        } catch (Exception e) {
            throw new RuntimeException("Failed to process languages", e);
        }
    }

    private Translation findDefaultTranslation(List<AbstractTranslation> translations, Language defaultLanguage) {
        try (PandaStream<AbstractTranslation> translationStream = PandaStream.of(translations)) {
            return translationStream
                    .find(translation -> translation.getLanguage().equals(defaultLanguage))
                    .orThrow(() -> new RuntimeException("Default language not found!"));
        } catch (Exception e) {
            throw new RuntimeException("Failed to find default translation", e);
        }
    }

    private void configureTranslation(ConfigurationManager configurationManager, TranslationManager translationManager, AbstractTranslation message) {
        configurationManager.load(message);
        translationManager.loadLanguage(message.getLanguage(), message);
    }
}
