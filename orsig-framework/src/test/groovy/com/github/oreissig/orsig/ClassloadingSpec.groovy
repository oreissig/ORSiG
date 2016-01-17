package com.github.oreissig.orsig

import groovy.transform.NotYetImplemented

import org.osgi.framework.launch.Framework

import spock.lang.Stepwise
import spock.lang.Unroll

@Stepwise
@Unroll
class ClassloadingSpec extends AbstractORSiGSpec {
    
    @NotYetImplemented
    def 'Test classes can be loaded from #name'(name,className) {
        given:
        def location = testJar(name).path
        
        when:
        def bundle = framework.bundleContext.installBundle(location)
        def clazz = bundle.loadClass(className)
        
        then:
        noExceptionThrown()
        clazz != null
        clazz.name == className
        
        where:
        name         | className
        'test-api'   | 'com.github.oreissig.orsig.test.api.TestInterface'
        'test-impl1' | 'com.github.oreissig.orsig.test.impl1.TestIncrementer1'
        'test-impl2' | 'com.github.oreissig.orsig.test.impl2.TestIncrementer2'
    }
    
    @NotYetImplemented
    def 'Test classes can be invoked (#name)'(name,className) {
        given:
        def location = testJar(name).path
        def bundle = framework.bundleContext.installBundle(location)
        def clazz = bundle.loadClass(className)
        
        when:
        def instance = clazz.newInstance()
        
        then:
        instance != null
        
        expect:
        instance.increment(3) == 4
        
        where:
        name         | className
        'test-impl1' | 'com.github.oreissig.orsig.test.impl1.TestIncrementer1'
        'test-impl2' | 'com.github.oreissig.orsig.test.impl2.TestIncrementer2'
    }
    
    @NotYetImplemented
    def 'Common class can be loaded for all bundles (#name)'(name) {
        given:
        def location = testJar(name).path
        def bundle = framework.bundleContext.installBundle(location)
        
        when:
        def clazz = bundle.loadClass('com.github.oreissig.orsig.test.api.TestInterface')
        
        then:
        noExceptionThrown()
        clazz != null
        
        where:
        name << ['test-api', 'test-impl1', 'test-impl2']
    }
    
    @NotYetImplemented
    def 'Other impl classes cannot be loaded (#className in #name)'(name,className) {
        given:
        def location = testJar(name).path
        def bundle = framework.bundleContext.installBundle(location)
        
        when:
        bundle.loadClass(className)
        
        then:
        thrown(ClassNotFoundException)
        
        where:
        name         | className
        'test-api'   | 'com.github.oreissig.orsig.test.impl1.TestIncrementer1'
        'test-api'   | 'com.github.oreissig.orsig.test.impl1.TestIncrementer2'
        'test-impl1' | 'com.github.oreissig.orsig.test.impl1.TestIncrementer2'
        'test-impl2' | 'com.github.oreissig.orsig.test.impl2.TestIncrementer1'
    }
}
