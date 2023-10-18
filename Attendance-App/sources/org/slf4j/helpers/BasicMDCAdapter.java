package org.slf4j.helpers;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.slf4j.spi.MDCAdapter;

public class BasicMDCAdapter implements MDCAdapter {
    private InheritableThreadLocal<Map<String, String>> inheritableThreadLocal = new InheritableThreadLocal<Map<String, String>>() {
        /* access modifiers changed from: protected */
        public Map<String, String> childValue(Map<String, String> parentValue) {
            if (parentValue == null) {
                return null;
            }
            return new HashMap(parentValue);
        }
    };

    public void put(String key, String val) {
        if (key != null) {
            Map<String, String> map = (Map) this.inheritableThreadLocal.get();
            if (map == null) {
                map = new HashMap<>();
                this.inheritableThreadLocal.set(map);
            }
            map.put(key, val);
            return;
        }
        throw new IllegalArgumentException("key cannot be null");
    }

    public String get(String key) {
        Map<String, String> map = (Map) this.inheritableThreadLocal.get();
        if (map == null || key == null) {
            return null;
        }
        return map.get(key);
    }

    public void remove(String key) {
        Map<String, String> map = (Map) this.inheritableThreadLocal.get();
        if (map != null) {
            map.remove(key);
        }
    }

    public void clear() {
        Map<String, String> map = (Map) this.inheritableThreadLocal.get();
        if (map != null) {
            map.clear();
            this.inheritableThreadLocal.remove();
        }
    }

    public Set<String> getKeys() {
        Map<String, String> map = (Map) this.inheritableThreadLocal.get();
        if (map != null) {
            return map.keySet();
        }
        return null;
    }

    public Map<String, String> getCopyOfContextMap() {
        Map<String, String> oldMap = (Map) this.inheritableThreadLocal.get();
        if (oldMap != null) {
            return new HashMap(oldMap);
        }
        return null;
    }

    public void setContextMap(Map<String, String> contextMap) {
        this.inheritableThreadLocal.set(new HashMap(contextMap));
    }
}
