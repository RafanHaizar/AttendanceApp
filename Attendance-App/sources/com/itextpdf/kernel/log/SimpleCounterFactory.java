package com.itextpdf.kernel.log;

@Deprecated
public class SimpleCounterFactory implements ICounterFactory {
    private ICounter counter;

    public SimpleCounterFactory(ICounter counter2) {
        this.counter = counter2;
    }

    public ICounter getCounter(Class<?> cls) {
        return this.counter;
    }
}
