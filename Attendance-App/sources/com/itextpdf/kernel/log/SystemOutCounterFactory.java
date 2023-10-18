package com.itextpdf.kernel.log;

@Deprecated
public class SystemOutCounterFactory implements ICounterFactory {
    public ICounter getCounter(Class<?> cls) {
        SystemOutCounter systemOutCounter;
        if (cls == null) {
            systemOutCounter = new SystemOutCounter();
        }
        return systemOutCounter;
    }
}
