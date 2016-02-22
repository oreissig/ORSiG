package com.github.oreissig.orsig;

import org.osgi.framework.launch.Framework;

import dagger.Component;

@Component(modules = { ORSiGModule.class })
public interface ORSiGComponent {
    Framework getFramework();
}
