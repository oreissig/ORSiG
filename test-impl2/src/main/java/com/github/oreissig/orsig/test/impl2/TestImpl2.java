package com.github.oreissig.orsig.test.impl2;

import com.github.oreissig.orsig.test.api.TestInterface;

public class TestImpl2 implements TestInterface {

    @Override
    public int increment(int value) {
        return value + 1;
    }
}
