package com.github.oreissig.orsig

import groovy.transform.NotYetImplemented
import spock.lang.Stepwise
import spock.lang.Unroll

@Stepwise
@Unroll
class ClassloadingSpec extends AbstractORSiGSpec {
    
    def 'Classes without dependencies can be loaded from #name'(name,className) {
        when:
        def bundle = install(name)
        def clazz = bundle.loadClass(className)
        
        then:
        noExceptionThrown()
        clazz != null
        clazz.name == className
        
        where:
        name         | className
        'test-api'   | 'com.github.oreissig.orsig.test.api.TestInterface'
        'test-impl1' | 'com.github.oreissig.orsig.test.impl1.Pojo1'
        'test-impl2' | 'com.github.oreissig.orsig.test.impl2.Pojo2'
    }
    
    @NotYetImplemented
    def 'Dependency classes can be loaded (#name)'(name) {
        given:
        def bundle = install(name)
        
        when:
        def clazz = bundle.loadClass('com.github.oreissig.orsig.test.api.TestInterface')
        
        then:
        noExceptionThrown()
        clazz != null
        
        where:
        name << ['test-impl1', 'test-impl2']
    }
    
    @NotYetImplemented
    def 'Classes with dependencies can be loaded and invoked (#name)'(name,className) {
        given:
        def bundle = install(name)
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
    
    // TODO this works by accident @NotYetImplemented
    def 'Other impl classes cannot be loaded (#className in #name)'(name,className) {
        given:
        def bundle = install(name)
        
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
