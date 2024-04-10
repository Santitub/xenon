package dev.portero.xenon.injector.bean.processor;

import dev.portero.xenon.injector.DependencyProvider;

import java.lang.annotation.Annotation;

interface Processor<T, A extends Annotation> {

    void process(DependencyProvider dependencyProvider, T bean, A annotation);

}
