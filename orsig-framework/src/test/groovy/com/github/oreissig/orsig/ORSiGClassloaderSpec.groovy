package com.github.oreissig.orsig

import groovy.transform.NotYetImplemented

class ORSiGClassloaderSpec extends AbstractORSiGSpec {

    def 'private classes can be loaded'() {
        given:
        def privateJar = jarUrl('test-api')
        def className = 'com.github.oreissig.orsig.test.api.TestInterface'
        
        when:
        def loader = new ORSiGClassloader(privateJar)
        def c = loader.loadClass(className)
        
        then:
        noExceptionThrown()
        c.name == className
    }
    
    def 'imported classes can be loaded'() {
        given:
        def importLoader = loaderFor('test-api')
        def className = 'com.github.oreissig.orsig.test.api.TestInterface'
        
        when:
        def loader = new ORSiGClassloader('':importLoader)
        def c = loader.loadClass(className)
        
        then:
        noExceptionThrown()
        c.name == className
    }
    
    def 'classes are loaded from the correct origin'() {
        given:
        def privateJar = jarUrl('test-api')
        def imports = [
            'com.github.oreissig.orsig.test.impl1': loaderFor('test-impl1'),
            'com.github.oreissig.orsig.test.impl2': loaderFor('test-impl2'),
        ]
        def loader = new ORSiGClassloader(imports, privateJar)
        
        expect:
        loader.loadClass('com.github.oreissig.orsig.test.impl1.Pojo1')
              .classLoader == imports['com.github.oreissig.orsig.test.impl1']
        
        and:
        loader.loadClass('com.github.oreissig.orsig.test.impl2.Pojo2')
              .classLoader == imports['com.github.oreissig.orsig.test.impl2']
        
        and:
        loader.loadClass('com.github.oreissig.orsig.test.api.TestInterface')
              .classLoader == loader.privateJars
    }
    
    @NotYetImplemented
    def 'class hierarchies are loaded correctly'() {
        given:
        def privateJar = jarUrl('test-impl1')
        def imports = ['com.github.oreissig.orsig.test.api': loaderFor('test-api')]
        def loader = new ORSiGClassloader(imports, privateJar)
        
        when:
        def c = loader.loadClass('com.github.oreissig.orsig.test.impl1.TestImpl1')
        
        then:
        noExceptionThrown()
        c.simpleName == 'TestImpl1'
        c.superclass.simpleName == 'TestInterface'
        c.classLoader != c.superclass.classLoader
    }
    
    private URL jarUrl(String name) {
        testJar(name).toURI().toURL()
    }
    
    private ClassLoader loaderFor(String name) {
        new URLClassLoader(jarUrl(name))
    }
}
