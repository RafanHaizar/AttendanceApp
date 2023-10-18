package com.itextpdf.kernel.counter.data;

import com.itextpdf.kernel.counter.data.EventData;
import java.util.List;

public interface IEventDataCache<T, V extends EventData<T>> {
    List<V> clear();

    void put(V v);

    V retrieveNext();
}
