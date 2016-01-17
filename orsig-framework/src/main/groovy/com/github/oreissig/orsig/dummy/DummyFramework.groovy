package com.github.oreissig.orsig.dummy

import groovy.transform.CompileStatic

import org.osgi.framework.launch.Framework

@CompileStatic
class DummyFramework implements Framework {
    @Delegate
    private static final Framework UNSUPPORTED_OPERATIONS = Dummys.implement(Framework)
}
