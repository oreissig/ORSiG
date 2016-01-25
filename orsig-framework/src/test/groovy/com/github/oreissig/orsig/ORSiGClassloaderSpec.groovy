package com.github.oreissig.orsig


class ORSiGClassloaderSpec extends AbstractORSiGSpec {
    
    def 'private classes can be loaded'() {
        given:
        def privateJar = jarUrl('test-api')
        def className = API_CLASS
        
        when:
        def loader = new ORSiGClassloader(privateJar)
        def c = loader.loadClass(className)
        
        then:
        noExceptionThrown()
        c.name == className
    }
    
    def 'imported classes can be loaded'() {
        given:
        def importLoader = loaderFor('test-api')
        def className = API_CLASS
        
        when:
        def loader = new ORSiGClassloader('':importLoader)
        def c = loader.loadClass(className)
        
        then:
        noExceptionThrown()
        c.name == className
    }
    
    def 'classes are loaded from the correct origin'() {
        given:
        def privateJar = jarUrl('test-api')
        def imports = [
            (IMPL1_PKG): loaderFor('test-impl1'),
            (IMPL2_PKG): loaderFor('test-impl2'),
        ]
        def loader = new ORSiGClassloader(imports, privateJar)
        
        expect:
        loader.loadClass(IMPL1_POJO)
              .classLoader == imports[IMPL1_PKG]
        
        and:
        loader.loadClass(IMPL2_POJO)
              .classLoader == imports[IMPL2_PKG]
        
        and:
        loader.loadClass(API_CLASS)
              .classLoader == loader.privateJars
    }
    
    def 'class hierarchies are loaded correctly'() {
        given:
        def privateJar = jarUrl('test-impl1')
        def imports = [(API_PKG): loaderFor('test-api')]
        def loader = new ORSiGClassloader(imports, privateJar)
        
        when:
        def c = loader.loadClass(IMPL1_IMPL)
        
        then:
        noExceptionThrown()
        c.name == IMPL1_IMPL
        c.interfaces*.name == [API_CLASS]
        c.classLoader != c.superclass.classLoader
    }
    
    def 'bundle cannot load classes from its users'() {
        given:
        def dependencyJar = jarUrl('test-api')
        def dependencyLoader = new ORSiGClassloader(dependencyJar)
        
        def userJar = jarUrl('test-impl1')
        def userLoader = new ORSiGClassloader([(API_PKG): dependencyLoader], userJar)
        
        expect:
        dependencyLoader.loadClass(API_CLASS)
        userLoader.loadClass(API_CLASS)
        userLoader.loadClass(IMPL1_IMPL)
        
        when:
        dependencyLoader.loadClass(IMPL1_IMPL)
        then:
        thrown(ClassNotFoundException)
    }
    
    def 'transitive dependencies cannot be loaded'() {
        given:
        def transitiveDepJar = jarUrl('test-api')
        def transitiveDepLoader = new ORSiGClassloader(transitiveDepJar)
        
        def directDepJar = jarUrl('test-impl1')
        def directDepLoader = new ORSiGClassloader([(API_PKG): transitiveDepLoader], directDepJar)
        
        def userJar = jarUrl('test-impl2')
        def userLoader = new ORSiGClassloader([(IMPL1_PKG): directDepLoader], userJar)
        
        when:
        userLoader.loadClass(API_CLASS)
        then:
        thrown(ClassNotFoundException)
    }
    
    private URL jarUrl(String name) {
        testJar(name).toURI().toURL()
    }
    
    private ClassLoader loaderFor(String name) {
        new URLClassLoader(jarUrl(name))
    }
}
