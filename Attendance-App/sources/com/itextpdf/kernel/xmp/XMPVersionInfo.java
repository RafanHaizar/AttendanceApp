package com.itextpdf.kernel.xmp;

public interface XMPVersionInfo {
    int getBuild();

    int getMajor();

    String getMessage();

    int getMicro();

    int getMinor();

    boolean isDebug();
}
