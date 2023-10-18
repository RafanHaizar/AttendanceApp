package com.itextpdf.layout;

public interface IPropertyContainer {
    void deleteOwnProperty(int i);

    <T1> T1 getDefaultProperty(int i);

    <T1> T1 getOwnProperty(int i);

    <T1> T1 getProperty(int i);

    boolean hasOwnProperty(int i);

    boolean hasProperty(int i);

    void setProperty(int i, Object obj);
}
