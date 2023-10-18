package com.itextpdf.kernel.counter;

import com.itextpdf.kernel.counter.context.IContext;
import com.itextpdf.kernel.counter.event.IEvent;
import com.itextpdf.kernel.counter.event.IMetaInfo;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class EventCounterHandler {
    private static final EventCounterHandler instance = new EventCounterHandler();
    private Map<IEventCounterFactory, Boolean> factories = new ConcurrentHashMap();

    private EventCounterHandler() {
        register(new SimpleEventCounterFactory(new DefaultEventCounter()));
    }

    public static EventCounterHandler getInstance() {
        return instance;
    }

    public void onEvent(IEvent event, IMetaInfo metaInfo, Class<?> caller) {
        IContext context = null;
        boolean contextInitialized = false;
        for (IEventCounterFactory factory : this.factories.keySet()) {
            EventCounter counter = factory.getCounter(caller);
            if (counter != null) {
                if (!contextInitialized) {
                    if (metaInfo != null) {
                        context = ContextManager.getInstance().getContext(metaInfo.getClass());
                    }
                    if (context == null) {
                        context = ContextManager.getInstance().getContext(caller);
                    }
                    if (context == null) {
                        context = ContextManager.getInstance().getContext(event.getClass());
                    }
                    contextInitialized = true;
                }
                if ((context != null && context.allow(event)) || (context == null && counter.fallback.allow(event))) {
                    counter.onEvent(event, metaInfo);
                }
            }
        }
    }

    public void register(IEventCounterFactory factory) {
        if (factory != null) {
            this.factories.put(factory, true);
        }
    }

    public boolean isRegistered(IEventCounterFactory factory) {
        if (factory != null) {
            return this.factories.containsKey(factory);
        }
        return false;
    }

    public boolean unregister(IEventCounterFactory factory) {
        if (factory == null || this.factories.remove(factory) == null) {
            return false;
        }
        return true;
    }
}
