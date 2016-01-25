package com.github.oreissig.orsig

import groovy.transform.CompileStatic

import java.util.jar.Attributes
import java.util.jar.JarFile

@CompileStatic
class ORSiGBundle extends AbstractBundle {
    final String location
    final Map<String,String> manifest
    final ClassLoader loader

    ORSiGBundle(String location) {
        this.location = location
        // read manifest attributes
        def manifest = new TreeMap<String,String>()
        def file = new File(location)
        new JarFile(file).withCloseable {
            it.manifest.mainAttributes.collect { k,v ->
                manifest[k.toString()] = v.toString()
            }
        }
        this.manifest = manifest.asImmutable()
        // TODO parse imports
        loader = new ORSiGClassloader([:], file.toURI().toURL())
    }
    
    @Override
    Class<?> loadClass(String name) throws ClassNotFoundException {
        loader.loadClass(name)
    }
}
