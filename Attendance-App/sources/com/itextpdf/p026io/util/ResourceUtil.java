package com.itextpdf.p026io.util;

import java.io.InputStream;

/* renamed from: com.itextpdf.io.util.ResourceUtil */
public final class ResourceUtil {
    private ResourceUtil() {
    }

    public static InputStream getResourceStream(String key) {
        return getResourceStream(key, (ClassLoader) null);
    }

    public static InputStream getResourceStream(String key, ClassLoader loader) {
        if (key.startsWith("/")) {
            key = key.substring(1);
        }
        InputStream stream = null;
        if (loader != null && (stream = loader.getResourceAsStream(key)) != null) {
            return stream;
        }
        try {
            ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
            if (contextClassLoader != null) {
                stream = contextClassLoader.getResourceAsStream(key);
            }
        } catch (SecurityException e) {
        }
        if (stream == null) {
            stream = ResourceUtil.class.getResourceAsStream("/" + key);
        }
        if (stream == null) {
            return ClassLoader.getSystemResourceAsStream(key);
        }
        return stream;
    }
}
