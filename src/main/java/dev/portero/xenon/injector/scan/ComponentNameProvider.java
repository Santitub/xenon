package dev.portero.xenon.injector.scan;

import java.lang.annotation.Annotation;

@FunctionalInterface
interface ComponentNameProvider<COMPONENT extends Annotation> {

    String getName(COMPONENT component);

}
