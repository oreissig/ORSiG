package com.github.oreissig.orsig

import groovy.transform.CompileStatic

import org.osgi.framework.Bundle
import org.osgi.framework.BundleException

import com.github.oreissig.orsig.dummy.DummyBundleContext

@CompileStatic
class ORSiGBundleContext extends DummyBundleContext {
    @Override
    Bundle installBundle(String location, InputStream input = null) throws BundleException {
        // TODO 1. If a bundle containing the same location identifier is already installed, the Bundle object for that bundle is returned.
        // 2. The bundle's content is read from the input stream. If this fails, a BundleException is thrown.
        if (input)
            throw new BundleException('installBundle with explicit input not supported yet',
                                      BundleException.READ_ERROR)
        def bundle = new ORSiGBundle(location)
        // TODO 3. The bundle's associated resources are allocated. The associated resources minimally consist of a unique identifier and a persistent storage area if the platform has file system support. If this step fails, a BundleException is thrown.
        // 4. The bundle's state is set to INSTALLED.
        // somehow "bundle.state =" doesn't work
        bundle.setState Bundle.INSTALLED
        // TODO 5. A bundle event of type BundleEvent.INSTALLED is fired.
        // 6. The Bundle object for the newly or previously installed bundle is returned.
        return bundle
    }
}
