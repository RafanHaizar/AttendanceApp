package com.itextpdf.kernel.log;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Deprecated
public class CounterManager {
    private static CounterManager instance = new CounterManager();
    private Set<ICounterFactory> factories = new HashSet();

    private CounterManager() {
    }

    public static CounterManager getInstance() {
        return instance;
    }

    public List<ICounter> getCounters(Class<?> cls) {
        ArrayList<ICounter> result = new ArrayList<>();
        for (ICounterFactory factory : this.factories) {
            ICounter counter = factory.getCounter(cls);
            if (counter != null) {
                result.add(counter);
            }
        }
        return result;
    }

    public void register(ICounterFactory factory) {
        if (factory != null) {
            this.factories.add(factory);
        }
    }

    public boolean unregister(ICounterFactory factory) {
        if (factory != null) {
            return this.factories.remove(factory);
        }
        return false;
    }
}
