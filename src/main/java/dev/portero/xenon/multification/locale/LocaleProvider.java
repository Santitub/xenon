package dev.portero.xenon.multification.locale;

import org.jetbrains.annotations.NotNull;

import java.util.Locale;

@FunctionalInterface
public interface LocaleProvider<Viewer> {

    @NotNull
    Locale provide(Viewer viewer);

}
