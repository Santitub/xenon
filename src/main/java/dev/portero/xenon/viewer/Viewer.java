package dev.portero.xenon.viewer;

import dev.portero.xenon.feature.language.Language;

import java.util.UUID;

public interface Viewer {

    UUID getUniqueId();

    Language getLanguage();

    boolean isConsole();

    String getName();

}
