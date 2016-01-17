package com.github.oreissig.orsig

import groovy.transform.CompileStatic

import org.osgi.framework.Bundle
import org.osgi.framework.BundleContext
import org.osgi.framework.BundleException

import com.github.oreissig.orsig.dummy.DummyBundle

@CompileStatic
abstract class AbstractBundle extends DummyBundle {
    int state
    BundleContext bundleContext = new ORSiGBundleContext()
    
    @Override
    void start(int options = 0) {
        state = Bundle.ACTIVE
    }
    
    @Override
    public void stop(int options = 0) throws BundleException {
        state = Bundle.RESOLVED
    }
}
