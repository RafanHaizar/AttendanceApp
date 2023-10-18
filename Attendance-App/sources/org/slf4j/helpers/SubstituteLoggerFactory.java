package org.slf4j.helpers;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;

public class SubstituteLoggerFactory implements ILoggerFactory {
    final ConcurrentMap<String, SubstituteLogger> loggers = new ConcurrentHashMap();

    public Logger getLogger(String name) {
        SubstituteLogger logger = (SubstituteLogger) this.loggers.get(name);
        if (logger != null) {
            return logger;
        }
        SubstituteLogger logger2 = new SubstituteLogger(name);
        SubstituteLogger oldLogger = this.loggers.putIfAbsent(name, logger2);
        if (oldLogger != null) {
            return oldLogger;
        }
        return logger2;
    }

    public List<String> getLoggerNames() {
        return new ArrayList(this.loggers.keySet());
    }

    public List<SubstituteLogger> getLoggers() {
        return new ArrayList(this.loggers.values());
    }

    public void clear() {
        this.loggers.clear();
    }
}
