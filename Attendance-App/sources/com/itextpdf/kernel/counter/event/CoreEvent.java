package com.itextpdf.kernel.counter.event;

import com.itextpdf.kernel.counter.NamespaceConstant;

public class CoreEvent implements IGenericEvent {
    public static final CoreEvent PROCESS = new CoreEvent("process");
    private final String subtype;

    private CoreEvent(String subtype2) {
        this.subtype = subtype2;
    }

    public String getEventType() {
        return "core-" + this.subtype;
    }

    public String getOriginId() {
        return NamespaceConstant.ITEXT;
    }
}
