package com.itextpdf.kernel.counter.data;

import com.itextpdf.p026io.LogMessageConstant;
import java.util.Comparator;
import org.slf4j.LoggerFactory;

public final class EventDataHandlerUtil {
    private EventDataHandlerUtil() {
    }

    public static <T, V extends EventData<T>> void registerProcessAllShutdownHook(final EventDataHandler<T, V> dataHandler) {
        try {
            Runtime.getRuntime().addShutdownHook(new Thread() {
                public void run() {
                    dataHandler.tryProcessRest();
                }
            });
        } catch (SecurityException e) {
            LoggerFactory.getLogger((Class<?>) EventDataHandlerUtil.class).error(LogMessageConstant.UNABLE_TO_REGISTER_EVENT_DATA_HANDLER_SHUTDOWN_HOOK);
        } catch (Exception e2) {
        }
    }

    public static <T, V extends EventData<T>> void registerTimedProcessing(final EventDataHandler<T, V> dataHandler) {
        Thread thread = new Thread() {
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(dataHandler.getWaitTime().getTime());
                        dataHandler.tryProcessNextAsync(false);
                    } catch (InterruptedException e) {
                        return;
                    } catch (Exception any) {
                        LoggerFactory.getLogger((Class<?>) EventDataHandlerUtil.class).error(LogMessageConstant.UNEXPECTED_EVENT_HANDLER_SERVICE_THREAD_EXCEPTION, (Throwable) any);
                        return;
                    }
                }
            }
        };
        thread.setDaemon(true);
        thread.start();
    }

    public static class BiggerCountComparator<T, V extends EventData<T>> implements Comparator<V> {
        public int compare(V o1, V o2) {
            return Long.compare(o2.getCount(), o1.getCount());
        }
    }
}
