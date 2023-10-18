package com.itextpdf.kernel.counter.data;

import com.itextpdf.kernel.counter.data.EventData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class EventDataCacheQueueBased<T, V extends EventData<T>> implements IEventDataCache<T, V> {
    private Map<T, V> map = new HashMap();
    private LinkedList<T> signatureQueue = new LinkedList<>();

    public void put(V data) {
        if (data != null) {
            V old = (EventData) this.map.put(data.getSignature(), data);
            if (old != null) {
                data.mergeWith(old);
            } else {
                this.signatureQueue.addLast(data.getSignature());
            }
        }
    }

    public V retrieveNext() {
        if (!this.signatureQueue.isEmpty()) {
            return (EventData) this.map.remove(this.signatureQueue.pollFirst());
        }
        return null;
    }

    public List<V> clear() {
        ArrayList<V> result = new ArrayList<>(this.map.values());
        this.map.clear();
        this.signatureQueue.clear();
        return result;
    }
}
