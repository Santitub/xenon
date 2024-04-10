package dev.portero.xenon.injector;

import dev.portero.xenon.shared.TypedException;

public class DependencyInjectorException extends TypedException {

    public DependencyInjectorException(Class<?> type) {
        super(type);
    }

    public DependencyInjectorException(String message, Class<?> type) {
        super(message, type);
    }

    public DependencyInjectorException(String message, Throwable cause, Class<?> type) {
        super(message, cause, type);
    }

    public DependencyInjectorException(Throwable cause, Class<?> type) {
        super(cause, type);
    }

}
