package com.github.oreissig.orsig

import groovy.transform.NotYetImplemented

import org.osgi.framework.Bundle
import org.osgi.framework.FrameworkEvent
import org.osgi.framework.launch.FrameworkFactory

import spock.lang.Specification
import spock.lang.Stepwise

@Stepwise
class FrameworkSpec extends Specification {
    static final List<File> testJars = System.properties['test.jars']
                                             .split(File.pathSeparator)
                                             *.asType(File)
    
    def 'FrameworkFactory is registered as service'() {
        expect:
        def loader = ServiceLoader.load(FrameworkFactory.class)
        loader.iterator().hasNext()
        loader.first() instanceof ORSiGFrameworkFactory
    }
    
    @NotYetImplemented
    def 'Framework can be set up'() {
        given:
        FrameworkFactory factory = ServiceLoader.load(FrameworkFactory.class).first()
        
        when:
        def framework = factory.newFramework([:])
        framework.start()
        
        then:
        framework.state == Bundle.ACTIVE
        
        and: 'Framework exits cleanly'
        def result = framework.waitForStop(0)
        
        then:
        result != null
        result.throwable == null
        result.type == FrameworkEvent.STOPPED
    }
    
    @NotYetImplemented
    def 'Bundles can be set up'() {
        given:
        FrameworkFactory factory = ServiceLoader.load(FrameworkFactory.class).first()
        def framework = factory.newFramework([:])
        framework.start()
        
        when:
        testJars.collect { jar ->
            framework.bundleContext.installBundle(jar)
        }.each {
            it.start()
        }
        
        then:
        def result = framework.waitForStop(0)
        result.throwable == null
        result.type == FrameworkEvent.STOPPED
    }
}
