package com.github.oreissig.orsig

import groovy.transform.CompileStatic

import org.osgi.framework.launch.Framework
import org.osgi.framework.launch.FrameworkFactory

@CompileStatic
class ORSiGFrameworkFactory implements FrameworkFactory {

    @Override
    Framework newFramework(Map<String, String> configuration) {
        return new ORSiGFramework()
    }
}
