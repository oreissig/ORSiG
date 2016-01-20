package com.github.oreissig.orsig

import groovy.transform.CompileStatic

@CompileStatic
class ORSiGClassloader extends ClassLoader {
    
    final Map<String,ClassLoader> imports
    final ClassLoader privateJars
    
    ORSiGClassloader(Map<String,ClassLoader> imports = [:], URL... privateJars) {
        this.imports = imports
        this.privateJars = new URLClassLoader(privateJars)
    }
    
    @Override
    Class<?> loadClass(String name) throws ClassNotFoundException {
        // First, check if the class has already been loaded
        Class<?> c = findLoadedClass(name)
        if (!c) {
            c = privateJars.loadClass(name)
        }
        return c;
    }
}
