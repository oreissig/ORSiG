package com.github.oreissig.orsig

import groovy.transform.CompileStatic

import org.osgi.framework.Bundle
import org.osgi.framework.BundleException

import com.github.oreissig.orsig.dummy.DummyBundleContext

@CompileStatic
class ORSiGBundleContext extends DummyBundleContext {
    @Override
    Bundle installBundle(String location, InputStream input = null) throws BundleException {
        if (input)
            throw new BundleException('installBundle with explicit input not supported yet',
                                      BundleException.READ_ERROR)
        
        return new ORSiGBundle(location)
    }
}
