package com.itextpdf.kernel.counter;

public interface IEventCounterFactory {
    EventCounter getCounter(Class<?> cls);
}
