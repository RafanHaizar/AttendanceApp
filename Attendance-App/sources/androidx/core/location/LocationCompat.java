package androidx.core.location;

import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import androidx.core.util.Preconditions;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

public final class LocationCompat {
    public static final String EXTRA_BEARING_ACCURACY = "bearingAccuracy";
    public static final String EXTRA_IS_MOCK = "mockLocation";
    public static final String EXTRA_MSL_ALTITUDE = "androidx.core.location.extra.MSL_ALTITUDE";
    public static final String EXTRA_MSL_ALTITUDE_ACCURACY = "androidx.core.location.extra.MSL_ALTITUDE_ACCURACY";
    public static final String EXTRA_SPEED_ACCURACY = "speedAccuracy";
    public static final String EXTRA_VERTICAL_ACCURACY = "verticalAccuracy";
    private static Method sSetIsFromMockProviderMethod;

    private LocationCompat() {
    }

    public static long getElapsedRealtimeNanos(Location location) {
        return Api17Impl.getElapsedRealtimeNanos(location);
    }

    public static long getElapsedRealtimeMillis(Location location) {
        return TimeUnit.NANOSECONDS.toMillis(Api17Impl.getElapsedRealtimeNanos(location));
    }

    public static boolean hasVerticalAccuracy(Location location) {
        if (Build.VERSION.SDK_INT >= 26) {
            return Api26Impl.hasVerticalAccuracy(location);
        }
        return containsExtra(location, EXTRA_VERTICAL_ACCURACY);
    }

    public static float getVerticalAccuracyMeters(Location location) {
        if (Build.VERSION.SDK_INT >= 26) {
            return Api26Impl.getVerticalAccuracyMeters(location);
        }
        Bundle extras = location.getExtras();
        if (extras == null) {
            return 0.0f;
        }
        return extras.getFloat(EXTRA_VERTICAL_ACCURACY, 0.0f);
    }

    public static void setVerticalAccuracyMeters(Location location, float verticalAccuracyM) {
        if (Build.VERSION.SDK_INT >= 26) {
            Api26Impl.setVerticalAccuracyMeters(location, verticalAccuracyM);
        } else {
            getOrCreateExtras(location).putFloat(EXTRA_VERTICAL_ACCURACY, verticalAccuracyM);
        }
    }

    public static boolean hasSpeedAccuracy(Location location) {
        if (Build.VERSION.SDK_INT >= 26) {
            return Api26Impl.hasSpeedAccuracy(location);
        }
        return containsExtra(location, EXTRA_SPEED_ACCURACY);
    }

    public static float getSpeedAccuracyMetersPerSecond(Location location) {
        if (Build.VERSION.SDK_INT >= 26) {
            return Api26Impl.getSpeedAccuracyMetersPerSecond(location);
        }
        Bundle extras = location.getExtras();
        if (extras == null) {
            return 0.0f;
        }
        return extras.getFloat(EXTRA_SPEED_ACCURACY, 0.0f);
    }

    public static void setSpeedAccuracyMetersPerSecond(Location location, float speedAccuracyMps) {
        if (Build.VERSION.SDK_INT >= 26) {
            Api26Impl.setSpeedAccuracyMetersPerSecond(location, speedAccuracyMps);
        } else {
            getOrCreateExtras(location).putFloat(EXTRA_SPEED_ACCURACY, speedAccuracyMps);
        }
    }

    public static boolean hasBearingAccuracy(Location location) {
        if (Build.VERSION.SDK_INT >= 26) {
            return Api26Impl.hasBearingAccuracy(location);
        }
        return containsExtra(location, EXTRA_BEARING_ACCURACY);
    }

    public static float getBearingAccuracyDegrees(Location location) {
        if (Build.VERSION.SDK_INT >= 26) {
            return Api26Impl.getBearingAccuracyDegrees(location);
        }
        Bundle extras = location.getExtras();
        if (extras == null) {
            return 0.0f;
        }
        return extras.getFloat(EXTRA_BEARING_ACCURACY, 0.0f);
    }

    public static void setBearingAccuracyDegrees(Location location, float bearingAccuracyD) {
        if (Build.VERSION.SDK_INT >= 26) {
            Api26Impl.setBearingAccuracyDegrees(location, bearingAccuracyD);
        } else {
            getOrCreateExtras(location).putFloat(EXTRA_BEARING_ACCURACY, bearingAccuracyD);
        }
    }

    public static double getMslAltitudeMeters(Location location) {
        Preconditions.checkState(hasMslAltitude(location), "The Mean Sea Level altitude of the location is not set.");
        return getOrCreateExtras(location).getDouble(EXTRA_MSL_ALTITUDE);
    }

    public static void setMslAltitudeMeters(Location location, double mslAltitudeMeters) {
        getOrCreateExtras(location).putDouble(EXTRA_MSL_ALTITUDE, mslAltitudeMeters);
    }

    public static boolean hasMslAltitude(Location location) {
        return containsExtra(location, EXTRA_MSL_ALTITUDE);
    }

    public static void removeMslAltitude(Location location) {
        removeExtra(location, EXTRA_MSL_ALTITUDE);
    }

    public static float getMslAltitudeAccuracyMeters(Location location) {
        Preconditions.checkState(hasMslAltitudeAccuracy(location), "The Mean Sea Level altitude accuracy of the location is not set.");
        return getOrCreateExtras(location).getFloat(EXTRA_MSL_ALTITUDE_ACCURACY);
    }

    public static void setMslAltitudeAccuracyMeters(Location location, float mslAltitudeAccuracyMeters) {
        getOrCreateExtras(location).putFloat(EXTRA_MSL_ALTITUDE_ACCURACY, mslAltitudeAccuracyMeters);
    }

    public static boolean hasMslAltitudeAccuracy(Location location) {
        return containsExtra(location, EXTRA_MSL_ALTITUDE_ACCURACY);
    }

    public static void removeMslAltitudeAccuracy(Location location) {
        removeExtra(location, EXTRA_MSL_ALTITUDE_ACCURACY);
    }

    public static boolean isMock(Location location) {
        return Api18Impl.isMock(location);
    }

    public static void setMock(Location location, boolean mock) {
        try {
            getSetIsFromMockProviderMethod().invoke(location, new Object[]{Boolean.valueOf(mock)});
        } catch (NoSuchMethodException e) {
            Error error = new NoSuchMethodError();
            error.initCause(e);
            throw error;
        } catch (IllegalAccessException e2) {
            Error error2 = new IllegalAccessError();
            error2.initCause(e2);
            throw error2;
        } catch (InvocationTargetException e3) {
            throw new RuntimeException(e3);
        }
    }

    private static class Api26Impl {
        private Api26Impl() {
        }

        static boolean hasVerticalAccuracy(Location location) {
            return location.hasVerticalAccuracy();
        }

        static float getVerticalAccuracyMeters(Location location) {
            return location.getVerticalAccuracyMeters();
        }

        static void setVerticalAccuracyMeters(Location location, float verticalAccuracyM) {
            location.setVerticalAccuracyMeters(verticalAccuracyM);
        }

        static boolean hasSpeedAccuracy(Location location) {
            return location.hasSpeedAccuracy();
        }

        static float getSpeedAccuracyMetersPerSecond(Location location) {
            return location.getSpeedAccuracyMetersPerSecond();
        }

        static void setSpeedAccuracyMetersPerSecond(Location location, float speedAccuracyMps) {
            location.setSpeedAccuracyMetersPerSecond(speedAccuracyMps);
        }

        static boolean hasBearingAccuracy(Location location) {
            return location.hasBearingAccuracy();
        }

        static float getBearingAccuracyDegrees(Location location) {
            return location.getBearingAccuracyDegrees();
        }

        static void setBearingAccuracyDegrees(Location location, float bearingAccuracyD) {
            location.setBearingAccuracyDegrees(bearingAccuracyD);
        }
    }

    private static class Api18Impl {
        private Api18Impl() {
        }

        static boolean isMock(Location location) {
            return location.isFromMockProvider();
        }
    }

    private static class Api17Impl {
        private Api17Impl() {
        }

        static long getElapsedRealtimeNanos(Location location) {
            return location.getElapsedRealtimeNanos();
        }
    }

    private static Method getSetIsFromMockProviderMethod() throws NoSuchMethodException {
        if (sSetIsFromMockProviderMethod == null) {
            Method declaredMethod = Location.class.getDeclaredMethod("setIsFromMockProvider", new Class[]{Boolean.TYPE});
            sSetIsFromMockProviderMethod = declaredMethod;
            declaredMethod.setAccessible(true);
        }
        return sSetIsFromMockProviderMethod;
    }

    private static Bundle getOrCreateExtras(Location location) {
        Bundle extras = location.getExtras();
        if (extras != null) {
            return extras;
        }
        location.setExtras(new Bundle());
        return location.getExtras();
    }

    private static boolean containsExtra(Location location, String key) {
        Bundle extras = location.getExtras();
        return extras != null && extras.containsKey(key);
    }

    private static void removeExtra(Location location, String key) {
        Bundle extras = location.getExtras();
        if (extras != null) {
            extras.remove(key);
            if (extras.isEmpty()) {
                location.setExtras((Bundle) null);
            }
        }
    }
}
