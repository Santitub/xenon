package dev.portero.xenon.bridge.adventure;

import java.util.function.UnaryOperator;

public class AdventureLegacyColorPreProcessor implements UnaryOperator<String> {

    @Override
    public String apply(String component) {
        return component.replace("§", "&");
    }

}
