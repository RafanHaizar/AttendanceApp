package androidx.core.util;

import java.util.Objects;

public class ObjectsCompat {
    private ObjectsCompat() {
    }

    public static boolean equals(Object a, Object b) {
        return Api19Impl.equals(a, b);
    }

    public static int hashCode(Object o) {
        if (o != null) {
            return o.hashCode();
        }
        return 0;
    }

    public static int hash(Object... values) {
        return Api19Impl.hash(values);
    }

    public static String toString(Object o, String nullDefault) {
        return o != null ? o.toString() : nullDefault;
    }

    public static <T> T requireNonNull(T obj) {
        if (obj != null) {
            return obj;
        }
        throw new NullPointerException();
    }

    public static <T> T requireNonNull(T obj, String message) {
        if (obj != null) {
            return obj;
        }
        throw new NullPointerException(message);
    }

    static class Api19Impl {
        private Api19Impl() {
        }

        static boolean equals(Object a, Object b) {
            return Objects.equals(a, b);
        }

        static int hash(Object... values) {
            return Objects.hash(values);
        }
    }
}
