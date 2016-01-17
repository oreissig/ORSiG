package com.github.oreissig.orsig;

import groovy.transform.CompileStatic

import org.osgi.framework.launch.Framework

@CompileStatic
class ORSiGFramework implements Framework {

    // implement all other methods by throwing UnsupportedOperationException
    @Delegate
    private static final Framework UNSUPPORTED_OPERATIONS = [:].withDefault {
        return { throw new UnsupportedOperationException('not yet implemented in ORSiG') }
    } as Framework
}
