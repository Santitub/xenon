package dev.portero.xenon.multification.executor;

@FunctionalInterface
public interface AsyncExecutor {

    void execute(Runnable runnable);
}
