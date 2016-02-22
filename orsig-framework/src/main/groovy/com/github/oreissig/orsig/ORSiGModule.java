package com.github.oreissig.orsig;

import org.osgi.framework.launch.Framework;

import dagger.Module;
import dagger.Provides;

@Module
public class ORSiGModule {
    @Provides
    public static Framework provideFramework(ORSiGFramework impl) {
        return impl;
    }
}
