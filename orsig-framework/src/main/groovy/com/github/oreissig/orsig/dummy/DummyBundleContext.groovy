package com.github.oreissig.orsig.dummy

import groovy.transform.CompileStatic

import org.osgi.framework.BundleContext

@CompileStatic
class DummyBundleContext implements BundleContext {
    @Delegate
    private static final BundleContext UNSUPPORTED_OPERATIONS = Dummys.implement(BundleContext)
}
