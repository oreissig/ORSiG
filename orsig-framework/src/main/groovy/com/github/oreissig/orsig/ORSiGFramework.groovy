package com.github.oreissig.orsig

import groovy.transform.CompileStatic

import org.osgi.framework.Bundle
import org.osgi.framework.BundleException
import org.osgi.framework.FrameworkEvent

import com.github.oreissig.orsig.dummy.DummyFramework

@CompileStatic
class ORSiGFramework extends DummyFramework {
    int state
    
    @Override
    void start(int options = 0) {
        // TODO 1.If this Framework is not in the STARTING state, initialize this Framework.
        // TODO 2.All installed bundles must be started in accordance with each bundle's persistent autostart setting. This means some bundles will not be started, some will be started with eager activation and some will be started with their declared activation policy. The start level of this Framework is moved to the start level specified by the beginning start level framework property, as described in the Start Level Specification. If this framework property is not specified, then the start level of this Framework is moved to start level one (1). Any exceptions that occur during bundle starting must be wrapped in a BundleException and then published as a framework event of type FrameworkEvent.ERROR
        // 3.This Framework's state is set to ACTIVE.
        state = Bundle.ACTIVE
        // TODO 4.A framework event of type FrameworkEvent.STARTED is fired
    }
    
    @Override
    public void stop(int options = 0) throws BundleException {
        // TODO 1.This Framework's state is set to STOPPING.
        // TODO 2.All installed bundles must be stopped without changing each bundle's persistent autostart setting. The start level of this Framework is moved to start level zero (0), as described in the Start Level Specification. Any exceptions that occur during bundle stopping must be wrapped in a BundleException and then published as a framework event of type FrameworkEvent.ERROR
        // TODO 3.Unregister all services registered by this Framework.
        // TODO 4.Event handling is disabled.
        // 5.This Framework's state is set to RESOLVED.
        state = Bundle.RESOLVED
        // TODO 6.All resources held by this Framework are released. This includes threads, bundle class loaders, open files, etc.
        // TODO 7.Notify all threads that are waiting at waitForStop that the stop operation has completed.
    }
    
    @Override
    FrameworkEvent waitForStop(long timeout) throws InterruptedException {
        return new FrameworkEvent(FrameworkEvent.STOPPED, this)
    }
}
