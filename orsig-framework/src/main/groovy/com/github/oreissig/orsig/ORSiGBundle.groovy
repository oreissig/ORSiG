package com.github.oreissig.orsig

import groovy.transform.CompileStatic

import java.util.jar.Attributes
import java.util.jar.JarFile

@CompileStatic
class ORSiGBundle extends AbstractBundle {
    final String location
    final Map<String,String> manifest

    ORSiGBundle(String location) {
        this.location = location
        // read manifest attributes
        def manifest = new TreeMap<String,String>()
        new JarFile(location).withCloseable {
            it.manifest.mainAttributes.collect { k,v ->
                manifest[k.toString()] = v.toString()
            }
        }
        this.manifest = manifest.asImmutable()
    }
}
