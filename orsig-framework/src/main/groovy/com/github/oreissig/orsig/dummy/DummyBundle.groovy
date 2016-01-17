package com.github.oreissig.orsig.dummy

import groovy.transform.CompileStatic

import org.osgi.framework.Bundle

@CompileStatic
class DummyBundle implements Bundle {
    @Delegate
    private static final Bundle UNSUPPORTED_OPERATIONS = Dummys.implement(Bundle)
}
