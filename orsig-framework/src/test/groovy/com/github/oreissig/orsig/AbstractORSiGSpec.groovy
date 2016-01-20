package com.github.oreissig.orsig

import groovy.transform.CompileStatic

import org.osgi.framework.Bundle
import org.osgi.framework.launch.Framework

import spock.lang.Specification

@CompileStatic
class AbstractORSiGSpec extends Specification {
    static final List<File> testJars = System.properties['test.jars']
                                             .toString()
                                             .split(File.pathSeparator)
                                             *.asType(File)
    
    File testJar(String name) {
        testJars.find { it.name.contains(name) }
    }
    
    Bundle install(String name) {
        def location = testJar(name).path
        framework.bundleContext.installBundle(location)
    }
    
    final Framework framework = new ORSiGFramework()
}
