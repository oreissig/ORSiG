package com.github.oreissig.orsig

import org.osgi.framework.launch.Framework

import spock.lang.Specification

class AbstractORSiGSpec extends Specification {
    static final List<File> testJars = System.properties['test.jars']
                                             .split(File.pathSeparator)
                                             *.asType(File)
    
    def File testJar(String name) {
        testJars.find { it.name.contains(name) }
    }
    
    final Framework framework = new ORSiGFramework()
}
