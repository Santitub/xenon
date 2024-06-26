package dev.portero.xenon.injector.scan;

import dev.portero.xenon.injector.DependencyInjector;
import dev.portero.xenon.injector.annotations.Bean;
import dev.portero.xenon.injector.bean.BeanCandidate;
import dev.portero.xenon.injector.bean.BeanHolder;

import java.lang.reflect.Method;

class MethodBeanCandidate implements BeanCandidate {

    private final DependencyInjector dependencyInjector;
    private final Class<?> componentClass;
    private final Method method;
    private final Bean bean;

    public MethodBeanCandidate(DependencyInjector dependencyInjector, Class<?> componentClass, Method method, Bean bean) {
        this.dependencyInjector = dependencyInjector;
        this.componentClass = componentClass;
        this.method = method;
        this.bean = bean;
    }

    @Override
    public boolean isCandidate(Class<?> clazz) {
        Class<?> returnType = this.method.getReturnType();

        return clazz.isAssignableFrom(returnType);
    }

    @Override
    public <T> BeanHolder<T> createBean(Class<T> clazz) {
        if (!this.isCandidate(clazz)) {
            throw new IllegalArgumentException("Class " + clazz.getName() + " is not a candidate for " + this.method.getReturnType()
                .getName());
        }

        Object instanceOfComponent = this.dependencyInjector.dependencyProvider().getDependency(this.componentClass);
        Object instanceOfBean = this.dependencyInjector.invokeMethod(instanceOfComponent, this.method);

        return BeanHolder.of(this.bean.value(), clazz.cast(instanceOfBean));
    }

    @Override
    public String toString() {
        return "MethodBeanCandidate{" +
            "componentClass=" + this.componentClass +
            ", method=" + this.method +
            '}';
    }
}
