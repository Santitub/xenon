package dev.portero.xenon.multification.translation;

import org.jetbrains.annotations.NotNull;

import java.util.Locale;

@FunctionalInterface
public interface TranslationProvider<Translation> {

    @NotNull
    Translation provide(Locale locale);
}
