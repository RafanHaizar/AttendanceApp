package com.itextpdf.kernel.counter;

import com.itextpdf.kernel.counter.context.IContext;
import com.itextpdf.kernel.counter.context.UnknownContext;
import com.itextpdf.kernel.counter.data.EventData;
import com.itextpdf.kernel.counter.data.EventDataHandler;
import com.itextpdf.kernel.counter.data.EventDataHandlerUtil;
import com.itextpdf.kernel.counter.event.IEvent;
import com.itextpdf.kernel.counter.event.IMetaInfo;

public class DataHandlerCounter<T, V extends EventData<T>> extends EventCounter {
    private final EventDataHandler<T, V> dataHandler;

    public DataHandlerCounter(EventDataHandler<T, V> dataHandler2) {
        this(dataHandler2, UnknownContext.PERMISSIVE);
    }

    public DataHandlerCounter(EventDataHandler<T, V> dataHandler2, IContext fallback) {
        super(fallback);
        this.dataHandler = dataHandler2;
        EventDataHandlerUtil.registerProcessAllShutdownHook(dataHandler2);
        EventDataHandlerUtil.registerTimedProcessing(dataHandler2);
    }

    /* access modifiers changed from: protected */
    public void onEvent(IEvent event, IMetaInfo metaInfo) {
        this.dataHandler.register(event, metaInfo);
    }
}
