package com.itextpdf.kernel.counter.context;

import com.itextpdf.kernel.counter.event.IEvent;

public interface IContext {
    boolean allow(IEvent iEvent);
}
