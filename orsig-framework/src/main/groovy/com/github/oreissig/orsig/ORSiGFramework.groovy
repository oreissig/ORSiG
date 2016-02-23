package com.github.oreissig.orsig

import groovy.transform.CompileStatic

import org.osgi.framework.Bundle
import org.osgi.framework.BundleException
import org.osgi.framework.FrameworkEvent
import org.osgi.framework.FrameworkListener
import org.osgi.framework.launch.Framework

import com.google.common.eventbus.EventBus

@CompileStatic
class ORSiGFramework extends AbstractBundle implements Framework {
    private EventBus bus = null
    
    @Override
    void start(int options = 0) {
        // 1. If this Framework is not in the STARTING state, initialize this Framework.
        if (state != Bundle.STARTING)
            init()
        // TODO 2. All installed bundles must be started in accordance with each bundle's persistent autostart setting. This means some bundles will not be started, some will be started with eager activation and some will be started with their declared activation policy. The start level of this Framework is moved to the start level specified by the beginning start level framework property, as described in the Start Level Specification. If this framework property is not specified, then the start level of this Framework is moved to start level one (1). Any exceptions that occur during bundle starting must be wrapped in a BundleException and then published as a framework event of type FrameworkEvent.ERROR
        // 3. This Framework's state is set to ACTIVE.
        state = Bundle.ACTIVE
        // 4. A framework event of type FrameworkEvent.STARTED is fired
        bus.post new FrameworkEvent(FrameworkEvent.STARTED, this, null)
    }
    
    @Override
    void stop(int options = 0) throws BundleException {
        // 1.This Framework's state is set to STOPPING.
        state = Bundle.STOPPING
        // TODO 2. All installed bundles must be stopped without changing each bundle's persistent autostart setting. The start level of this Framework is moved to start level zero (0), as described in the Start Level Specification. Any exceptions that occur during bundle stopping must be wrapped in a BundleException and then published as a framework event of type FrameworkEvent.ERROR
        // TODO 3. Unregister all services registered by this Framework.
        // 4. Event handling is disabled.
        bus = null
        // 5. This Framework's state is set to RESOLVED.
        state = Bundle.RESOLVED
        // TODO 6. All resources held by this Framework are released. This includes threads, bundle class loaders, open files, etc.
        // TODO 7. Notify all threads that are waiting at waitForStop that the stop operation has completed.
    }
    
    @Override
    void init(FrameworkListener... listeners = []) throws BundleException {
        // TODO • Have generated a new framework UUID. 
        // • Be in the STARTING state.
        state = Bundle.STARTING
        // TODO • Have a valid Bundle Context.
        // TODO • Be at start level 0.
        // • Have event handling enabled.
        bus = new EventBus(getClass().name)
        // TODO • Have reified Bundle objects for all installed bundles.
        // TODO • Have registered any framework services. For example, ConditionalPermissionAdmin.
        // TODO • Be adaptable to the OSGi defined types to which a system bundle can be adapted.
        // TODO • Have called the start method of the extension bundle activator for all resolved extension bundles.
    }
    
    @Override
    FrameworkEvent waitForStop(long timeout) throws InterruptedException {
        return new FrameworkEvent(FrameworkEvent.STOPPED, this, null)
    }
}
