package com.github.oreissig.orsig

import org.osgi.framework.Bundle
import org.osgi.framework.FrameworkEvent
import org.osgi.framework.launch.FrameworkFactory

import spock.lang.Specification
import spock.lang.Stepwise

@Stepwise
class LifecycleSpec extends Specification {
    static final List<File> testJars = System.properties['test.jars']
                                             .split(File.pathSeparator)
                                             *.asType(File)
    
    def 'FrameworkFactory is registered as service'() {
        expect:
        def loader = ServiceLoader.load(FrameworkFactory.class)
        loader.iterator().hasNext()
        loader.first() instanceof ORSiGFrameworkFactory
    }
    
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
    
    def 'Bundles can be set up'() {
        given:
        FrameworkFactory factory = ServiceLoader.load(FrameworkFactory.class).first()
        def framework = factory.newFramework([:])
        framework.start()
        
        when:
        def bundles = testJars.collect { jar ->
            framework.bundleContext.installBundle(jar.path)
        }
        bundles.each {
            it.start()
        }
        
        then:
        bundles*.state.every { it == Bundle.ACTIVE }
        
        and:
        def result = framework.waitForStop(0)
        result.throwable == null
        result.type == FrameworkEvent.STOPPED
    }
}
