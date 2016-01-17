package com.github.oreissig.orsig

import groovy.transform.CompileStatic

import org.osgi.framework.Bundle
import org.osgi.framework.BundleException
import org.osgi.framework.FrameworkEvent
import org.osgi.framework.FrameworkListener
import org.osgi.framework.launch.Framework

@CompileStatic
class ORSiGFramework extends AbstractBundle implements Framework {
    @Override
    void start(int options = 0) {
        // 1.If this Framework is not in the STARTING state, initialize this Framework.
        if (state != Bundle.STARTING)
            init()
        // TODO 2.All installed bundles must be started in accordance with each bundle's persistent autostart setting. This means some bundles will not be started, some will be started with eager activation and some will be started with their declared activation policy. The start level of this Framework is moved to the start level specified by the beginning start level framework property, as described in the Start Level Specification. If this framework property is not specified, then the start level of this Framework is moved to start level one (1). Any exceptions that occur during bundle starting must be wrapped in a BundleException and then published as a framework event of type FrameworkEvent.ERROR
        // 3.This Framework's state is set to ACTIVE.
        state = Bundle.ACTIVE
        // TODO 4.A framework event of type FrameworkEvent.STARTED is fired
    }
    
    @Override
    void stop(int options = 0) throws BundleException {
        // TODO this is just a bare minimum
        state = Bundle.RESOLVED
    }
    
    void init(FrameworkListener... listeners = []) throws BundleException {
        // TODO do nothing for now
    }
    
    @Override
    FrameworkEvent waitForStop(long timeout) throws InterruptedException {
        return new FrameworkEvent(FrameworkEvent.STOPPED, this)
    }
}
