package com.github.oreissig.orsig

import groovy.transform.CompileStatic

/**
 * The bundle classloader of ORSiG.
 * 
 * As a workaround for parent-first delegation, I add it as parent
 * to its delegate URLClassLoader, so that it calls back when locating
 * imported classes. To avoid stack overflow I introduced the
 * {@code resolving} flag that discriminates whether this classloader
 * is being called directly or indirectly from its delegating
 * ClassLoader.
 */
@CompileStatic
class ORSiGClassloader extends ClassLoader {
    
    final Map<String,ClassLoader> imports
    final ClassLoader privateJars
    private boolean resolving = false
    
    ORSiGClassloader(Map<String,ClassLoader> imports = [:], URL... privateJars) {
        this.imports = imports
        this.privateJars = new URLClassLoader(privateJars, this)
    }
    
    @Override
    protected synchronized Class<?> loadClass(String name, boolean resolve)
            throws ClassNotFoundException {
        // First, check if the class has already been loaded
        Class<?> c = findLoadedClass(name)
        if (!c) {
            def loader = imports.find { name.startsWith(it.key) }?.value
            // only check delegate classloader when not being called as its parent
            if (!loader && !resolving)
                loader = privateJars
            if (!loader)
                // TODO replace parent delegation with implicit "java" runtime bundle
                // throw new ClassNotFoundException(name)
                loader = parent
            try {
                resolving = true
                c = loader.loadClass(name)
            } finally {
                resolving = false
            }
        }
        return c;
    }
}
