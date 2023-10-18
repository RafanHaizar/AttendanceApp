package com.itextpdf.kernel.counter.context;

import com.itextpdf.kernel.counter.event.IEvent;
import com.itextpdf.kernel.counter.event.IGenericEvent;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class GenericContext implements IContext {
    private final Set<String> supported;

    public GenericContext(Collection<String> supported2) {
        HashSet hashSet = new HashSet();
        this.supported = hashSet;
        hashSet.addAll(supported2);
    }

    public boolean allow(IEvent event) {
        if (event instanceof IGenericEvent) {
            return this.supported.contains(((IGenericEvent) event).getOriginId());
        }
        return false;
    }
}
