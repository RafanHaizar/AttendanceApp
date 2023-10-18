package com.itextpdf.kernel.counter;

public class SimpleEventCounterFactory implements IEventCounterFactory {
    private EventCounter counter;

    public SimpleEventCounterFactory(EventCounter counter2) {
        this.counter = counter2;
    }

    public EventCounter getCounter(Class<?> cls) {
        return this.counter;
    }
}
