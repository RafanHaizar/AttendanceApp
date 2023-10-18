package com.itextpdf.kernel.counter.data;

import com.itextpdf.kernel.counter.data.EventData;
import com.itextpdf.kernel.counter.event.IEvent;
import com.itextpdf.kernel.counter.event.IMetaInfo;
import com.itextpdf.p026io.util.SystemUtil;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public abstract class EventDataHandler<T, V extends EventData<T>> {
    private final IEventDataCache<T, V> cache;
    private final IEventDataFactory<T, V> factory;
    private final AtomicLong lastProcessedTime = new AtomicLong();
    private final Object processLock = new Object();
    private volatile WaitTime waitTime;

    /* access modifiers changed from: protected */
    public abstract boolean process(V v);

    public EventDataHandler(IEventDataCache<T, V> cache2, IEventDataFactory<T, V> factory2, long initialWaitTimeMillis, long maxWaitTimeMillis) {
        this.cache = cache2;
        this.factory = factory2;
        this.waitTime = new WaitTime(initialWaitTimeMillis, maxWaitTimeMillis);
    }

    public List<V> clear() {
        List<V> all;
        synchronized (this.cache) {
            all = this.cache.clear();
        }
        this.lastProcessedTime.set(0);
        resetWaitTime();
        return all != null ? all : Collections.emptyList();
    }

    public void register(IEvent event, IMetaInfo metaInfo) {
        V data;
        synchronized (this.factory) {
            data = this.factory.create(event, metaInfo);
        }
        if (data != null) {
            synchronized (this.cache) {
                this.cache.put(data);
            }
            tryProcessNextAsync();
        }
    }

    public void tryProcessNext() {
        V data;
        boolean successful;
        if (SystemUtil.getRelativeTimeMillis() - this.lastProcessedTime.get() > this.waitTime.getTime()) {
            this.lastProcessedTime.set(SystemUtil.getRelativeTimeMillis());
            synchronized (this.cache) {
                data = this.cache.retrieveNext();
            }
            if (data != null) {
                synchronized (this.processLock) {
                    successful = tryProcess(data);
                }
                if (successful) {
                    onSuccess(data);
                    return;
                }
                synchronized (this.cache) {
                    this.cache.put(data);
                }
                onFailure(data);
            }
        }
    }

    public void tryProcessNextAsync() {
        tryProcessNextAsync((Boolean) null);
    }

    public void tryProcessNextAsync(Boolean daemon) {
        if (SystemUtil.getRelativeTimeMillis() - this.lastProcessedTime.get() > this.waitTime.getTime()) {
            Thread thread = new Thread() {
                public void run() {
                    EventDataHandler.this.tryProcessNext();
                }
            };
            if (daemon != null) {
                thread.setDaemon(daemon.booleanValue());
            }
            thread.start();
        }
    }

    public void tryProcessRest() {
        List<V> unprocessedEvents = clear();
        if (!unprocessedEvents.isEmpty()) {
            try {
                synchronized (this.processLock) {
                    for (V data : unprocessedEvents) {
                        process(data);
                    }
                }
            } catch (Exception e) {
            }
        }
    }

    public void resetWaitTime() {
        WaitTime local = this.waitTime;
        this.waitTime = new WaitTime(local.getInitial(), local.getMaximum());
    }

    public void increaseWaitTime() {
        WaitTime local = this.waitTime;
        this.waitTime = new WaitTime(local.getInitial(), local.getMaximum(), Math.min(local.getTime() * 2, local.getMaximum()));
    }

    public void setNoWaitTime() {
        WaitTime local = this.waitTime;
        this.waitTime = new WaitTime(local.getInitial(), local.getMaximum(), 0);
    }

    public WaitTime getWaitTime() {
        return this.waitTime;
    }

    /* access modifiers changed from: protected */
    public void onSuccess(V v) {
        resetWaitTime();
    }

    /* access modifiers changed from: protected */
    public void onFailure(V v) {
        increaseWaitTime();
    }

    /* access modifiers changed from: protected */
    public boolean onProcessException(Exception exception) {
        return false;
    }

    private boolean tryProcess(V data) {
        try {
            return process(data);
        } catch (Exception any) {
            return onProcessException(any);
        }
    }
}
