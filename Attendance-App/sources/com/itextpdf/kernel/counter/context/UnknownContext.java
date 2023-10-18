package com.itextpdf.kernel.counter.context;

import com.itextpdf.kernel.counter.event.IEvent;

public class UnknownContext implements IContext {
    public static final IContext PERMISSIVE = new UnknownContext(true);
    public static final IContext RESTRICTIVE = new UnknownContext(false);
    private final boolean allowEvents;

    public UnknownContext(boolean allowEvents2) {
        this.allowEvents = allowEvents2;
    }

    public boolean allow(IEvent event) {
        return this.allowEvents;
    }
}
