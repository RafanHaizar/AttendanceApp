package org.slf4j.helpers;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import org.slf4j.IMarkerFactory;
import org.slf4j.Marker;

public class BasicMarkerFactory implements IMarkerFactory {
    private final ConcurrentMap<String, Marker> markerMap = new ConcurrentHashMap();

    public Marker getMarker(String name) {
        if (name != null) {
            Marker marker = (Marker) this.markerMap.get(name);
            if (marker != null) {
                return marker;
            }
            Marker marker2 = new BasicMarker(name);
            Marker oldMarker = this.markerMap.putIfAbsent(name, marker2);
            if (oldMarker != null) {
                return oldMarker;
            }
            return marker2;
        }
        throw new IllegalArgumentException("Marker name cannot be null");
    }

    public boolean exists(String name) {
        if (name == null) {
            return false;
        }
        return this.markerMap.containsKey(name);
    }

    public boolean detachMarker(String name) {
        if (name == null || this.markerMap.remove(name) == null) {
            return false;
        }
        return true;
    }

    public Marker getDetachedMarker(String name) {
        return new BasicMarker(name);
    }
}
