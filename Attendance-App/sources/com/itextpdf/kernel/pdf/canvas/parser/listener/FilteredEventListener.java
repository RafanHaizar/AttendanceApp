package com.itextpdf.kernel.pdf.canvas.parser.listener;

import com.itextpdf.kernel.pdf.canvas.parser.EventType;
import com.itextpdf.kernel.pdf.canvas.parser.data.IEventData;
import com.itextpdf.kernel.pdf.canvas.parser.filter.IEventFilter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class FilteredEventListener implements IEventListener {
    protected final List<IEventListener> delegates;
    protected final List<IEventFilter[]> filters;

    public FilteredEventListener() {
        this.delegates = new ArrayList();
        this.filters = new ArrayList();
    }

    public FilteredEventListener(IEventListener delegate, IEventFilter... filterSet) {
        this();
        attachEventListener(delegate, filterSet);
    }

    public <T extends IEventListener> T attachEventListener(T delegate, IEventFilter... filterSet) {
        this.delegates.add(delegate);
        this.filters.add(filterSet);
        return delegate;
    }

    public void eventOccurred(IEventData data, EventType type) {
        for (int i = 0; i < this.delegates.size(); i++) {
            IEventListener delegate = this.delegates.get(i);
            int i2 = 0;
            boolean filtersPassed = delegate.getSupportedEvents() == null || delegate.getSupportedEvents().contains(type);
            IEventFilter[] iEventFilterArr = this.filters.get(i);
            int length = iEventFilterArr.length;
            while (true) {
                if (i2 >= length) {
                    break;
                } else if (!iEventFilterArr[i2].accept(data, type)) {
                    filtersPassed = false;
                    break;
                } else {
                    i2++;
                }
            }
            if (filtersPassed) {
                delegate.eventOccurred(data, type);
            }
        }
    }

    public Set<EventType> getSupportedEvents() {
        return null;
    }
}
