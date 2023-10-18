package org.slf4j;

import java.io.Closeable;
import java.util.Map;
import org.slf4j.helpers.NOPMDCAdapter;
import org.slf4j.helpers.Util;
import org.slf4j.impl.StaticMDCBinder;
import org.slf4j.spi.MDCAdapter;

public class MDC {
    static final String NO_STATIC_MDC_BINDER_URL = "http://www.slf4j.org/codes.html#no_static_mdc_binder";
    static final String NULL_MDCA_URL = "http://www.slf4j.org/codes.html#null_MDCA";
    static MDCAdapter mdcAdapter;

    public static class MDCCloseable implements Closeable {
        private final String key;

        private MDCCloseable(String key2) {
            this.key = key2;
        }

        public void close() {
            MDC.remove(this.key);
        }
    }

    private MDC() {
    }

    static {
        try {
            mdcAdapter = StaticMDCBinder.SINGLETON.getMDCA();
        } catch (NoClassDefFoundError ncde) {
            mdcAdapter = new NOPMDCAdapter();
            String msg = ncde.getMessage();
            if (msg == null || !msg.contains("StaticMDCBinder")) {
                throw ncde;
            }
            Util.report("Failed to load class \"org.slf4j.impl.StaticMDCBinder\".");
            Util.report("Defaulting to no-operation MDCAdapter implementation.");
            Util.report("See http://www.slf4j.org/codes.html#no_static_mdc_binder for further details.");
        } catch (Exception e) {
            Util.report("MDC binding unsuccessful.", e);
        }
    }

    public static void put(String key, String val) throws IllegalArgumentException {
        if (key != null) {
            MDCAdapter mDCAdapter = mdcAdapter;
            if (mDCAdapter != null) {
                mDCAdapter.put(key, val);
                return;
            }
            throw new IllegalStateException("MDCAdapter cannot be null. See also http://www.slf4j.org/codes.html#null_MDCA");
        }
        throw new IllegalArgumentException("key parameter cannot be null");
    }

    public static MDCCloseable putCloseable(String key, String val) throws IllegalArgumentException {
        put(key, val);
        return new MDCCloseable(key);
    }

    public static String get(String key) throws IllegalArgumentException {
        if (key != null) {
            MDCAdapter mDCAdapter = mdcAdapter;
            if (mDCAdapter != null) {
                return mDCAdapter.get(key);
            }
            throw new IllegalStateException("MDCAdapter cannot be null. See also http://www.slf4j.org/codes.html#null_MDCA");
        }
        throw new IllegalArgumentException("key parameter cannot be null");
    }

    public static void remove(String key) throws IllegalArgumentException {
        if (key != null) {
            MDCAdapter mDCAdapter = mdcAdapter;
            if (mDCAdapter != null) {
                mDCAdapter.remove(key);
                return;
            }
            throw new IllegalStateException("MDCAdapter cannot be null. See also http://www.slf4j.org/codes.html#null_MDCA");
        }
        throw new IllegalArgumentException("key parameter cannot be null");
    }

    public static void clear() {
        MDCAdapter mDCAdapter = mdcAdapter;
        if (mDCAdapter != null) {
            mDCAdapter.clear();
            return;
        }
        throw new IllegalStateException("MDCAdapter cannot be null. See also http://www.slf4j.org/codes.html#null_MDCA");
    }

    public static Map<String, String> getCopyOfContextMap() {
        MDCAdapter mDCAdapter = mdcAdapter;
        if (mDCAdapter != null) {
            return mDCAdapter.getCopyOfContextMap();
        }
        throw new IllegalStateException("MDCAdapter cannot be null. See also http://www.slf4j.org/codes.html#null_MDCA");
    }

    public static void setContextMap(Map<String, String> contextMap) {
        MDCAdapter mDCAdapter = mdcAdapter;
        if (mDCAdapter != null) {
            mDCAdapter.setContextMap(contextMap);
            return;
        }
        throw new IllegalStateException("MDCAdapter cannot be null. See also http://www.slf4j.org/codes.html#null_MDCA");
    }

    public static MDCAdapter getMDCAdapter() {
        return mdcAdapter;
    }
}
