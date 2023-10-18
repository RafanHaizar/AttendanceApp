package com.itextpdf.kernel.crypto;

import com.itextpdf.p026io.util.SystemUtil;
import java.nio.charset.StandardCharsets;
import org.slf4j.Marker;

public final class IVGenerator {
    private static final ARCFOUREncryption arcfour;

    static {
        ARCFOUREncryption aRCFOUREncryption = new ARCFOUREncryption();
        arcfour = aRCFOUREncryption;
        long time = SystemUtil.getTimeBasedSeed();
        aRCFOUREncryption.prepareARCFOURKey((time + Marker.ANY_NON_NULL_MARKER + SystemUtil.getFreeMemory()).getBytes(StandardCharsets.ISO_8859_1));
    }

    private IVGenerator() {
    }

    public static byte[] getIV() {
        return getIV(16);
    }

    public static byte[] getIV(int len) {
        byte[] b = new byte[len];
        ARCFOUREncryption aRCFOUREncryption = arcfour;
        synchronized (aRCFOUREncryption) {
            aRCFOUREncryption.encryptARCFOUR(b);
        }
        return b;
    }
}
