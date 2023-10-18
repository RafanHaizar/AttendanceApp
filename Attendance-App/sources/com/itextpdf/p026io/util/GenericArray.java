package com.itextpdf.p026io.util;

import java.util.ArrayList;
import java.util.List;

/* renamed from: com.itextpdf.io.util.GenericArray */
public class GenericArray<T> {
    private List<T> array;

    public GenericArray(int size) {
        this.array = new ArrayList(size);
        for (int i = 0; i < size; i++) {
            this.array.add((Object) null);
        }
    }

    public T get(int index) {
        return this.array.get(index);
    }

    public T set(int index, T element) {
        return this.array.set(index, element);
    }
}
