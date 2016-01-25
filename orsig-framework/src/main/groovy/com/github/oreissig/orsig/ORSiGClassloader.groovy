package com.github.oreissig.orsig

import groovy.transform.CompileStatic

@CompileStatic
class ORSiGClassloader extends URLClassLoader {
    
    ORSiGClassloader(Map<String,ClassLoader> imports = [:], URL... privateJars) {
        super(privateJars, new ImportsClassLoader(imports))
    }
    
    static class ImportsClassLoader extends ClassLoader {
        final Map<String,ClassLoader> imports
        
        ImportsClassLoader(Map<String,ClassLoader> imports) {
            // TODO replace bootstrap delegation with implicit "java" runtime bundle
            // super(null)
            super()
            this.imports = imports
        }
        
        @Override
        protected Class<?> loadClass(String name, boolean resolve)
                throws ClassNotFoundException {
            def loader = imports.find { name.startsWith(it.key) }?.value
            if (!loader)
                // TODO replace parent delegation with implicit "java" runtime bundle
                // throw new ClassNotFoundException(name)
                loader = parent
            return loader.loadClass(name)
        }
    }
}
