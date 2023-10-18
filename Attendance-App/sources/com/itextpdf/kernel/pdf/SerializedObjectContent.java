package com.itextpdf.kernel.pdf;

import java.util.Arrays;
import kotlin.UByte;

class SerializedObjectContent {
    private final int hash;
    private final byte[] serializedContent;

    SerializedObjectContent(byte[] serializedContent2) {
        this.serializedContent = serializedContent2;
        this.hash = calculateHash(serializedContent2);
    }

    public boolean equals(Object obj) {
        return (obj instanceof SerializedObjectContent) && hashCode() == obj.hashCode() && Arrays.equals(this.serializedContent, ((SerializedObjectContent) obj).serializedContent);
    }

    public int hashCode() {
        return this.hash;
    }

    private static int calculateHash(byte[] b) {
        int hash2 = 0;
        for (byte b2 : b) {
            hash2 = (hash2 * 31) + (b2 & UByte.MAX_VALUE);
        }
        return hash2;
    }
}
