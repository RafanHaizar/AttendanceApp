package com.itextpdf.kernel.events;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventDispatcher implements IEventDispatcher {
    protected Map<String, List<IEventHandler>> eventHandlers = new HashMap();

    public void addEventHandler(String type, IEventHandler handler) {
        removeEventHandler(type, handler);
        List<IEventHandler> handlers = this.eventHandlers.get(type);
        if (handlers == null) {
            handlers = new ArrayList<>();
            this.eventHandlers.put(type, handlers);
        }
        handlers.add(handler);
    }

    public void dispatchEvent(Event event) {
        dispatchEvent(event, false);
    }

    public void dispatchEvent(Event event, boolean delayed) {
        List<IEventHandler> handlers = this.eventHandlers.get(event.getType());
        if (handlers != null) {
            for (IEventHandler handler : handlers) {
                handler.handleEvent(event);
            }
        }
    }

    public boolean hasEventHandler(String type) {
        return this.eventHandlers.containsKey(type);
    }

    public void removeEventHandler(String type, IEventHandler handler) {
        List<IEventHandler> handlers = this.eventHandlers.get(type);
        if (handlers != null) {
            handlers.remove(handler);
            if (handlers.size() == 0) {
                this.eventHandlers.remove(type);
            }
        }
    }

    public void removeAllHandlers() {
        this.eventHandlers.clear();
    }
}
