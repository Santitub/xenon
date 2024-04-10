package dev.portero.xenon.user;

import dev.portero.xenon.feature.language.Language;
import dev.portero.xenon.feature.language.LanguageSettings;

public interface UserSettings extends LanguageSettings {

    @Override
    Language getLanguage();

    @Override
    void setLanguage(Language language);

}
