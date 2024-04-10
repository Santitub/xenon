package dev.portero.xenon.placeholder;

import dev.portero.xenon.viewer.Viewer;

import java.util.Optional;

public interface PlaceholderRegistry {

    void registerPlaceholder(PlaceholderReplacer stack);

    String format(String text, Viewer target);

    Optional<PlaceholderRaw> getRawPlaceholder(String target);

}
