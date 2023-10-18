package com.itextpdf.kernel.counter.data;

public final class WaitTime {
    private final long initial;
    private final long maximum;
    private final long time;

    public WaitTime(long initial2, long maximum2) {
        this(initial2, maximum2, initial2);
    }

    public WaitTime(long initial2, long maximum2, long time2) {
        this.initial = initial2;
        this.maximum = maximum2;
        this.time = time2;
    }

    public long getInitial() {
        return this.initial;
    }

    public long getMaximum() {
        return this.maximum;
    }

    public long getTime() {
        return this.time;
    }
}
