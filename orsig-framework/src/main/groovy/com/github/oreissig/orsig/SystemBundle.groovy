package com.github.oreissig.orsig

import groovy.transform.CompileStatic

@CompileStatic
class SystemBundle extends AbstractBundle {
    final String location
    final ClassLoader loader
    
    SystemBundle() {
        this.location = System.getProperty('java.home')
        loader = getClass().classLoader
    }
    
    @Override
    Class<?> loadClass(String name) throws ClassNotFoundException {
        loader.loadClass(name)
    }
}
