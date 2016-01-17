package com.github.oreissig.orsig.test.impl;

import com.github.oreissig.orsig.test.api.TestInterface;

public class TestIncrementer implements TestInterface {

    @Override
    public int increment(int value) {
        return value++;
    }
}
