package dev.portero.xenon.feature.language.config;

import dev.portero.xenon.configuration.ReloadableConfig;
import dev.portero.xenon.feature.language.Language;
import dev.portero.xenon.injector.annotations.component.ConfigurationFile;
import net.dzikoysk.cdn.entity.Description;
import net.dzikoysk.cdn.source.Resource;
import net.dzikoysk.cdn.source.Source;

import java.io.File;
import java.util.Arrays;
import java.util.List;

@ConfigurationFile
public class LanguageConfiguration implements ReloadableConfig {

    @Description(" ")
    public Language defaultLanguage = Language.ES;
    public List<Language> languages = Arrays.asList(Language.EN, Language.ES);

    @Override
    public Resource resource(File folder) {
        return Source.of(folder, "language.yml");
    }
}
