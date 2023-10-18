package com.itextpdf.kernel.counter.data;

import com.itextpdf.kernel.counter.data.EventData;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class EventDataCacheComparatorBased<T, V extends EventData<T>> implements IEventDataCache<T, V> {
    private Map<T, V> map = new HashMap();
    private Set<V> orderedCache;

    public EventDataCacheComparatorBased(Comparator<V> comparator) {
        this.orderedCache = new TreeSet(comparator);
    }

    public void put(V data) {
        if (data != null) {
            V old = (EventData) this.map.put(data.getSignature(), data);
            if (old != null) {
                this.orderedCache.remove(old);
                data.mergeWith(old);
                this.orderedCache.add(data);
                return;
            }
            this.orderedCache.add(data);
        }
    }

    public V retrieveNext() {
        for (V data : this.orderedCache) {
            if (data != null) {
                this.map.remove(data.getSignature());
                this.orderedCache.remove(data);
                return data;
            }
        }
        return null;
    }

    public List<V> clear() {
        ArrayList<V> result = new ArrayList<>(this.map.values());
        this.map.clear();
        this.orderedCache.clear();
        return result;
    }
}
