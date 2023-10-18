package com.itextpdf.kernel.colors;

import org.bouncycastle.crypto.tls.CipherSuite;

public class ColorConstants {
    public static final Color BLACK = DeviceRgb.BLACK;
    public static final Color BLUE = DeviceRgb.BLUE;
    public static final Color CYAN = new DeviceRgb(0, 255, 255);
    public static final Color DARK_GRAY = new DeviceRgb(64, 64, 64);
    public static final Color GRAY = new DeviceRgb(128, 128, 128);
    public static final Color GREEN = DeviceRgb.GREEN;
    public static final Color LIGHT_GRAY = new DeviceRgb(192, 192, 192);
    public static final Color MAGENTA = new DeviceRgb(255, 0, 255);
    public static final Color ORANGE = new DeviceRgb(255, 200, 0);
    public static final Color PINK = new DeviceRgb(255, (int) CipherSuite.TLS_PSK_WITH_AES_256_CBC_SHA384, (int) CipherSuite.TLS_PSK_WITH_AES_256_CBC_SHA384);
    public static final Color RED = DeviceRgb.RED;
    public static final Color WHITE = DeviceRgb.WHITE;
    public static final Color YELLOW = new DeviceRgb(255, 255, 0);
}
