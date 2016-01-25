package com.github.oreissig.orsig

import groovy.transform.CompileStatic

@CompileStatic
class ORSiGClassloader extends URLClassLoader {
    
    ORSiGClassloader(Map<String,ClassLoader> imports, URL... privateJars) {
        super(privateJars, new ImportsClassLoader(imports))
    }
    
    static class ImportsClassLoader extends ClassLoader {
        final Map<String,ClassLoader> imports
        
        ImportsClassLoader(Map<String,ClassLoader> imports) {
            super(null)
            this.imports = imports
        }
        
        @Override
        protected Class<?> loadClass(String name, boolean resolve)
                throws ClassNotFoundException {
            def loader = imports.find { name.startsWith(it.key) }?.value
            if (!loader)
                throw new ClassNotFoundException(name)
            return loader.loadClass(name)
        }
    }
}
