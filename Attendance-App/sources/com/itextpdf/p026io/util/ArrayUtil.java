package com.itextpdf.p026io.util;

import java.util.Collection;

/* renamed from: com.itextpdf.io.util.ArrayUtil */
public final class ArrayUtil {
    private ArrayUtil() {
    }

    public static byte[] shortenArray(byte[] src, int length) {
        if (length >= src.length) {
            return src;
        }
        byte[] shortened = new byte[length];
        System.arraycopy(src, 0, shortened, 0, length);
        return shortened;
    }

    public static int[] toIntArray(Collection<Integer> collection) {
        int[] array = new int[collection.size()];
        int k = 0;
        for (Integer key : collection) {
            array[k] = key.intValue();
            k++;
        }
        return array;
    }

    public static int hashCode(byte[] a) {
        if (a == null) {
            return 0;
        }
        int result = 1;
        for (byte element : a) {
            result = (result * 31) + element;
        }
        return result;
    }

    public static int[] fillWithValue(int[] a, int value) {
        for (int i = 0; i < a.length; i++) {
            a[i] = value;
        }
        return a;
    }

    public static float[] fillWithValue(float[] a, float value) {
        for (int i = 0; i < a.length; i++) {
            a[i] = value;
        }
        return a;
    }

    public static <T> void fillWithValue(T[] a, T value) {
        for (int i = 0; i < a.length; i++) {
            a[i] = value;
        }
    }

    public static int[] cloneArray(int[] src) {
        return (int[]) src.clone();
    }
}
