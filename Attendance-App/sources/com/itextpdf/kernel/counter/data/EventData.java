package com.itextpdf.kernel.counter.data;

public class EventData<T> {
    private long count;
    private final T signature;

    public EventData(T signature2) {
        this(signature2, 1);
    }

    public EventData(T signature2, long count2) {
        this.signature = signature2;
        this.count = count2;
    }

    public final T getSignature() {
        return this.signature;
    }

    public final long getCount() {
        return this.count;
    }

    /* access modifiers changed from: protected */
    public void mergeWith(EventData<T> data) {
        this.count += data.getCount();
    }
}
