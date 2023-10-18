package com.itextpdf.kernel.log;

@Deprecated
public interface ICounterFactory {
    ICounter getCounter(Class<?> cls);
}
