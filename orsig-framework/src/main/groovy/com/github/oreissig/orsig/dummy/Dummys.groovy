package com.github.oreissig.orsig.dummy

import groovy.transform.CompileStatic

/**
 * This class creates dummy implementations for a given type,
 * throwing UnsupportedOperationException for any method invocation.
 * 
 * It is used to implement all of the OSGi framework incrementally
 * without adding tons of dummy methods.
 */
@CompileStatic
class Dummys {
    private static final Map UNSUPPORTED = [:].withDefault {
        return { throw new UnsupportedOperationException('not yet implemented in ORSiG') }
    }
    
    // forbid instantiation
    private Dummys() {}
    
    static <T> T implement(Class<T> type) {
        return UNSUPPORTED.asType(type)
    }
}
