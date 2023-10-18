package com.itextpdf.kernel.counter.data;

import com.itextpdf.kernel.counter.data.EventData;
import com.itextpdf.kernel.counter.event.IEvent;
import com.itextpdf.kernel.counter.event.IMetaInfo;

public interface IEventDataFactory<T, V extends EventData<T>> {
    V create(IEvent iEvent, IMetaInfo iMetaInfo);
}
