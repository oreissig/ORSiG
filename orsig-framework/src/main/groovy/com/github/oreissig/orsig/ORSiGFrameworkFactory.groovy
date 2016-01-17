package com.github.oreissig.orsig

import groovy.transform.CompileStatic

import org.osgi.framework.launch.Framework
import org.osgi.framework.launch.FrameworkFactory

import com.google.inject.Guice

@CompileStatic
class ORSiGFrameworkFactory implements FrameworkFactory {

    @Delegate
    private final FrameworkFactory actualFactory

    ORSiGFrameworkFactory() {
        // create Framework instances via Dependency Injection
        actualFactory = Guice
            .createInjector(new ORSiGModule())
            .getInstance(FrameworkFactory)
    }
}
