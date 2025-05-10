package com.mycompany.junitperf;

public @interface JUnitPerfTest {

    int threads();

    int durationMs();

    int maxExecutionsPerSecond();

}
