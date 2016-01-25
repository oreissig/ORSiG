package com.github.oreissig.orsig

import groovy.transform.CompileStatic

import org.junit.Rule
import org.junit.rules.TestName
import org.osgi.framework.Bundle
import org.osgi.framework.launch.Framework

import spock.lang.Specification

@CompileStatic
class AbstractORSiGSpec extends Specification {
    static final List<File> testJars = System.properties['test.jars']
                                             .toString()
                                             .split(File.pathSeparator)
                                             *.asType(File)
    
    static final String API_PKG    = 'com.github.oreissig.orsig.test.api'
    static final String API_CLASS  = 'com.github.oreissig.orsig.test.api.TestInterface'
    static final String IMPL1_PKG  = 'com.github.oreissig.orsig.test.impl1'
    static final String IMPL1_IMPL = 'com.github.oreissig.orsig.test.impl1.TestImpl1'
    static final String IMPL1_POJO = 'com.github.oreissig.orsig.test.impl1.Pojo1'
    static final String IMPL2_PKG  = 'com.github.oreissig.orsig.test.impl2'
    static final String IMPL2_IMPL = 'com.github.oreissig.orsig.test.impl2.TestImpl2'
    static final String IMPL2_POJO = 'com.github.oreissig.orsig.test.impl2.Pojo2'
    
    File testJar(String name) {
        testJars.find { it.name.contains(name) }
    }
    
    Bundle install(String name) {
        def location = testJar(name).path
        framework.bundleContext.installBundle(location)
    }
    
    final Framework framework = new ORSiGFramework()
    
    @Rule
    final TestName testName = new TestName()
    
    def setup() {
        println "\n-> Test $testName.methodName:"
    }
}
