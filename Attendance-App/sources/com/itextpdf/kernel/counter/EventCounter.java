package com.itextpdf.kernel.counter;

import com.itextpdf.kernel.counter.context.IContext;
import com.itextpdf.kernel.counter.context.UnknownContext;
import com.itextpdf.kernel.counter.event.IEvent;
import com.itextpdf.kernel.counter.event.IMetaInfo;

public abstract class EventCounter {
    final IContext fallback;

    /* access modifiers changed from: protected */
    public abstract void onEvent(IEvent iEvent, IMetaInfo iMetaInfo);

    public EventCounter() {
        this(UnknownContext.PERMISSIVE);
    }

    public EventCounter(IContext fallback2) {
        if (fallback2 != null) {
            this.fallback = fallback2;
            return;
        }
        throw new IllegalArgumentException("The fallback context in EventCounter constructor cannot be null");
    }
}
