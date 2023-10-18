package com.itextpdf.kernel.counter;

import com.itextpdf.kernel.counter.event.IEvent;
import com.itextpdf.kernel.counter.event.IMetaInfo;
import com.itextpdf.p026io.util.MessageFormatUtil;

public class SystemOutEventCounter extends EventCounter {
    protected String name;

    public SystemOutEventCounter(String name2) {
        this.name = name2;
    }

    public SystemOutEventCounter() {
        this("iText");
    }

    public SystemOutEventCounter(Class<?> cls) {
        this(cls.getName());
    }

    /* access modifiers changed from: protected */
    public void onEvent(IEvent event, IMetaInfo metaInfo) {
        System.out.println(MessageFormatUtil.format("[{0}] {1} event", this.name, event.getEventType()));
    }
}
