package dev.portero.xenon.multification.shared;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Supplier;

public class Formatter {

    private final Map<String, Supplier<?>> placeholders = new LinkedHashMap<>();

    public String format(String message) {
        for (Map.Entry<String, Supplier<?>> placeholderEntry : this.placeholders.entrySet()) {
            String key = placeholderEntry.getKey();

            if (!message.contains(key)) {
                continue;
            }

            Object value = placeholderEntry.getValue().get();

            if (value == null) {
                continue;
            }

            message = message.replace(key, value.toString());
        }

        return message;
    }

    public Formatter register(String placeholder, Object value) {
        return this.register(placeholder, value::toString);
    }

    public Formatter register(String placeholder, Supplier<?> value) {
        this.placeholders.put(placeholder, value);
        return this;
    }

}
