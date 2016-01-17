package com.github.oreissig.orsig

import groovy.transform.CompileStatic

import org.osgi.framework.launch.Framework
import org.osgi.framework.launch.FrameworkFactory

import com.google.inject.AbstractModule
import com.google.inject.assistedinject.FactoryModuleBuilder

@CompileStatic
class ORSiGModule extends AbstractModule {

    @Override
    protected void configure() {
        install(new FactoryModuleBuilder()
             .implement(Framework, ORSiGFramework)
             .build(FrameworkFactory))
    }
}
