package dev.portero.xenon.injector;

public interface DependencyProvider {

    <T> T getDependency(Class<T> clazz);

}
