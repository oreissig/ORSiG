package com.github.oreissig.orsig

import groovy.transform.CompileStatic

@CompileStatic
class ORSiGBundle extends AbstractBundle {
    final String location
    
    ORSiGBundle(String location) {
        this.location = location
    }
}
