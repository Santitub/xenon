package dev.portero.xenon.scheduler;

public interface Task {

    void cancel();

    boolean isCanceled();

    boolean isAsync();

}