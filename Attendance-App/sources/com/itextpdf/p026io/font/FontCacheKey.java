package com.itextpdf.p026io.font;

import java.util.Arrays;

/* renamed from: com.itextpdf.io.font.FontCacheKey */
public abstract class FontCacheKey {
    public static FontCacheKey create(String fontName) {
        return new FontCacheStringKey(fontName);
    }

    public static FontCacheKey create(String fontName, int ttcIndex) {
        return new FontCacheTtcKey(fontName, ttcIndex);
    }

    public static FontCacheKey create(byte[] fontProgram) {
        return new FontCacheBytesKey(fontProgram);
    }

    public static FontCacheKey create(byte[] fontProgram, int ttcIndex) {
        return new FontCacheTtcKey(fontProgram, ttcIndex);
    }

    /* renamed from: com.itextpdf.io.font.FontCacheKey$FontCacheStringKey */
    private static class FontCacheStringKey extends FontCacheKey {
        private String fontName;

        FontCacheStringKey(String fontName2) {
            this.fontName = fontName2;
        }

        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            FontCacheStringKey that = (FontCacheStringKey) o;
            String str = this.fontName;
            if (str != null) {
                return str.equals(that.fontName);
            }
            if (that.fontName == null) {
                return true;
            }
            return false;
        }

        public int hashCode() {
            String str = this.fontName;
            if (str != null) {
                return str.hashCode();
            }
            return 0;
        }
    }

    /* renamed from: com.itextpdf.io.font.FontCacheKey$FontCacheBytesKey */
    private static class FontCacheBytesKey extends FontCacheKey {
        private byte[] firstFontBytes;
        private int fontLength;
        private int hashcode;

        FontCacheBytesKey(byte[] fontBytes) {
            if (fontBytes != null) {
                this.firstFontBytes = fontBytes.length > 10000 ? Arrays.copyOf(fontBytes, 10000) : fontBytes;
                this.fontLength = fontBytes.length;
            }
            this.hashcode = calcHashCode();
        }

        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            FontCacheBytesKey that = (FontCacheBytesKey) o;
            if (this.fontLength != that.fontLength) {
                return false;
            }
            return Arrays.equals(this.firstFontBytes, that.firstFontBytes);
        }

        public int hashCode() {
            return this.hashcode;
        }

        private int calcHashCode() {
            return (Arrays.hashCode(this.firstFontBytes) * 31) + this.fontLength;
        }
    }

    /* renamed from: com.itextpdf.io.font.FontCacheKey$FontCacheTtcKey */
    private static class FontCacheTtcKey extends FontCacheKey {
        private int ttcIndex;
        private FontCacheKey ttcKey;

        FontCacheTtcKey(String fontName, int ttcIndex2) {
            this.ttcKey = new FontCacheStringKey(fontName);
            this.ttcIndex = ttcIndex2;
        }

        FontCacheTtcKey(byte[] fontBytes, int ttcIndex2) {
            this.ttcKey = new FontCacheBytesKey(fontBytes);
            this.ttcIndex = ttcIndex2;
        }

        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            FontCacheTtcKey that = (FontCacheTtcKey) o;
            if (this.ttcIndex != that.ttcIndex) {
                return false;
            }
            return this.ttcKey.equals(that.ttcKey);
        }

        public int hashCode() {
            return (this.ttcKey.hashCode() * 31) + this.ttcIndex;
        }
    }
}
