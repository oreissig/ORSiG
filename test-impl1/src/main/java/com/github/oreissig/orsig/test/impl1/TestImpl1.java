package com.github.oreissig.orsig.test.impl1;

import com.github.oreissig.orsig.test.api.TestInterface;

public class TestImpl1 implements TestInterface {

    @Override
    public int increment(int value) {
        return ++value;
    }
}
