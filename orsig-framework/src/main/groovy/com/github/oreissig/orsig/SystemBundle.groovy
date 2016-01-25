package com.github.oreissig.orsig

import groovy.transform.CompileStatic

@CompileStatic
class SystemBundle extends AbstractBundle {
    final String location
    final ClassLoader loader
    
    SystemBundle() {
        location = System.getProperty('java.home')
        loader = ClassLoader.systemClassLoader
        start()
    }
    
    @Override
    Class<?> loadClass(String name) throws ClassNotFoundException {
        loader.loadClass(name)
    }
}
