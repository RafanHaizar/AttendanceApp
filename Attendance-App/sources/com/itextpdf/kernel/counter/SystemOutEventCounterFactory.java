package com.itextpdf.kernel.counter;

public class SystemOutEventCounterFactory implements IEventCounterFactory {
    public EventCounter getCounter(Class<?> cls) {
        SystemOutEventCounter systemOutEventCounter;
        if (cls == null) {
            systemOutEventCounter = new SystemOutEventCounter();
        }
        return systemOutEventCounter;
    }
}
