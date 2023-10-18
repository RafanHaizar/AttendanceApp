package androidx.core.location;

import android.content.Context;
import android.location.GnssMeasurementsEvent;
import android.location.GnssStatus;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationRequest;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.provider.Settings;
import android.text.TextUtils;
import androidx.collection.SimpleArrayMap;
import androidx.core.location.GnssStatusCompat;
import androidx.core.p001os.CancellationSignal;
import androidx.core.p001os.ExecutorCompat;
import androidx.core.util.Consumer;
import androidx.core.util.ObjectsCompat;
import androidx.core.util.Preconditions;
import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.WeakHashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.RejectedExecutionException;

public final class LocationManagerCompat {
    private static final long GET_CURRENT_LOCATION_TIMEOUT_MS = 30000;
    private static final long MAX_CURRENT_LOCATION_AGE_MS = 10000;
    private static final long PRE_N_LOOPER_TIMEOUT_S = 5;
    private static Field sContextField;
    private static Method sGnssRequestBuilderBuildMethod;
    private static Class<?> sGnssRequestBuilderClass;
    static final WeakHashMap<LocationListenerKey, WeakReference<LocationListenerTransport>> sLocationListeners = new WeakHashMap<>();
    private static Method sRegisterGnssMeasurementsCallbackMethod;

    public static boolean isLocationEnabled(LocationManager locationManager) {
        if (Build.VERSION.SDK_INT >= 28) {
            return Api28Impl.isLocationEnabled(locationManager);
        }
        if (Build.VERSION.SDK_INT <= 19) {
            try {
                if (sContextField == null) {
                    Field declaredField = LocationManager.class.getDeclaredField("mContext");
                    sContextField = declaredField;
                    declaredField.setAccessible(true);
                }
                Context context = (Context) sContextField.get(locationManager);
                if (context != null) {
                    if (Build.VERSION.SDK_INT != 19) {
                        return !TextUtils.isEmpty(Settings.Secure.getString(context.getContentResolver(), "location_providers_allowed"));
                    }
                    if (Settings.Secure.getInt(context.getContentResolver(), "location_mode", 0) != 0) {
                        return true;
                    }
                    return false;
                }
            } catch (ClassCastException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
            }
        }
        if (locationManager.isProviderEnabled("network") || locationManager.isProviderEnabled("gps")) {
            return true;
        }
        return false;
    }

    public static boolean hasProvider(LocationManager locationManager, String provider) {
        if (Build.VERSION.SDK_INT >= 31) {
            return Api31Impl.hasProvider(locationManager, provider);
        }
        if (locationManager.getAllProviders().contains(provider)) {
            return true;
        }
        try {
            if (locationManager.getProvider(provider) != null) {
                return true;
            }
            return false;
        } catch (SecurityException e) {
            return false;
        }
    }

    public static void getCurrentLocation(LocationManager locationManager, String provider, CancellationSignal cancellationSignal, Executor executor, Consumer<Location> consumer) {
        if (Build.VERSION.SDK_INT >= 30) {
            Api30Impl.getCurrentLocation(locationManager, provider, cancellationSignal, executor, consumer);
            return;
        }
        if (cancellationSignal != null) {
            cancellationSignal.throwIfCanceled();
        }
        Location location = locationManager.getLastKnownLocation(provider);
        if (location == null || SystemClock.elapsedRealtime() - LocationCompat.getElapsedRealtimeMillis(location) >= MAX_CURRENT_LOCATION_AGE_MS) {
            CancellableLocationListener listener = new CancellableLocationListener(locationManager, executor, consumer);
            locationManager.requestLocationUpdates(provider, 0, 0.0f, listener, Looper.getMainLooper());
            if (cancellationSignal != null) {
                Objects.requireNonNull(listener);
                cancellationSignal.setOnCancelListener(new LocationManagerCompat$$ExternalSyntheticLambda2(listener));
            }
            listener.startTimeout(GET_CURRENT_LOCATION_TIMEOUT_MS);
            return;
        }
        executor.execute(new LocationManagerCompat$$ExternalSyntheticLambda1(consumer, location));
    }

    public static void requestLocationUpdates(LocationManager locationManager, String provider, LocationRequestCompat locationRequest, Executor executor, LocationListenerCompat listener) {
        if (Build.VERSION.SDK_INT >= 31) {
            Api31Impl.requestLocationUpdates(locationManager, provider, locationRequest.toLocationRequest(), executor, listener);
        } else if (Build.VERSION.SDK_INT < 30 || !Api30Impl.tryRequestLocationUpdates(locationManager, provider, locationRequest, executor, listener)) {
            LocationListenerTransport transport = new LocationListenerTransport(new LocationListenerKey(provider, listener), executor);
            if (!Api19Impl.tryRequestLocationUpdates(locationManager, provider, locationRequest, transport)) {
                synchronized (sLocationListeners) {
                    locationManager.requestLocationUpdates(provider, locationRequest.getIntervalMillis(), locationRequest.getMinUpdateDistanceMeters(), transport, Looper.getMainLooper());
                    registerLocationListenerTransport(locationManager, transport);
                }
            }
        }
    }

    static void registerLocationListenerTransport(LocationManager locationManager, LocationListenerTransport transport) {
        WeakReference<LocationListenerTransport> oldRef = sLocationListeners.put(transport.getKey(), new WeakReference(transport));
        LocationListenerTransport oldTransport = oldRef != null ? (LocationListenerTransport) oldRef.get() : null;
        if (oldTransport != null) {
            oldTransport.unregister();
            locationManager.removeUpdates(oldTransport);
        }
    }

    public static void requestLocationUpdates(LocationManager locationManager, String provider, LocationRequestCompat locationRequest, LocationListenerCompat listener, Looper looper) {
        if (Build.VERSION.SDK_INT >= 31) {
            Api31Impl.requestLocationUpdates(locationManager, provider, locationRequest.toLocationRequest(), ExecutorCompat.create(new Handler(looper)), listener);
        } else if (!Api19Impl.tryRequestLocationUpdates(locationManager, provider, locationRequest, listener, looper)) {
            locationManager.requestLocationUpdates(provider, locationRequest.getIntervalMillis(), locationRequest.getMinUpdateDistanceMeters(), listener, looper);
        }
    }

    public static void removeUpdates(LocationManager locationManager, LocationListenerCompat listener) {
        WeakHashMap<LocationListenerKey, WeakReference<LocationListenerTransport>> weakHashMap = sLocationListeners;
        synchronized (weakHashMap) {
            ArrayList<LocationListenerKey> cleanup = null;
            for (WeakReference<LocationListenerTransport> transportRef : weakHashMap.values()) {
                LocationListenerTransport transport = (LocationListenerTransport) transportRef.get();
                if (transport != null) {
                    LocationListenerKey key = transport.getKey();
                    if (key.mListener == listener) {
                        if (cleanup == null) {
                            cleanup = new ArrayList<>();
                        }
                        cleanup.add(key);
                        transport.unregister();
                        locationManager.removeUpdates(transport);
                    }
                }
            }
            if (cleanup != null) {
                Iterator<LocationListenerKey> it = cleanup.iterator();
                while (it.hasNext()) {
                    sLocationListeners.remove(it.next());
                }
            }
        }
        locationManager.removeUpdates(listener);
    }

    public static String getGnssHardwareModelName(LocationManager locationManager) {
        if (Build.VERSION.SDK_INT >= 28) {
            return Api28Impl.getGnssHardwareModelName(locationManager);
        }
        return null;
    }

    public static int getGnssYearOfHardware(LocationManager locationManager) {
        if (Build.VERSION.SDK_INT >= 28) {
            return Api28Impl.getGnssYearOfHardware(locationManager);
        }
        return 0;
    }

    private static class GnssListenersHolder {
        static final SimpleArrayMap<Object, Object> sGnssStatusListeners = new SimpleArrayMap<>();

        private GnssListenersHolder() {
        }
    }

    public static boolean registerGnssMeasurementsCallback(LocationManager locationManager, GnssMeasurementsEvent.Callback callback, Handler handler) {
        if (Build.VERSION.SDK_INT != 30) {
            return Api24Impl.registerGnssMeasurementsCallback(locationManager, callback, handler);
        }
        return registerGnssMeasurementsCallbackOnR(locationManager, ExecutorCompat.create(handler), callback);
    }

    public static boolean registerGnssMeasurementsCallback(LocationManager locationManager, Executor executor, GnssMeasurementsEvent.Callback callback) {
        if (Build.VERSION.SDK_INT > 30) {
            return Api31Impl.registerGnssMeasurementsCallback(locationManager, executor, callback);
        }
        return registerGnssMeasurementsCallbackOnR(locationManager, executor, callback);
    }

    public static void unregisterGnssMeasurementsCallback(LocationManager locationManager, GnssMeasurementsEvent.Callback callback) {
        Api24Impl.unregisterGnssMeasurementsCallback(locationManager, callback);
    }

    private static boolean registerGnssMeasurementsCallbackOnR(LocationManager locationManager, Executor executor, GnssMeasurementsEvent.Callback callback) {
        if (Build.VERSION.SDK_INT == 30) {
            try {
                if (sGnssRequestBuilderClass == null) {
                    sGnssRequestBuilderClass = Class.forName("android.location.GnssRequest$Builder");
                }
                if (sGnssRequestBuilderBuildMethod == null) {
                    Method declaredMethod = sGnssRequestBuilderClass.getDeclaredMethod("build", new Class[0]);
                    sGnssRequestBuilderBuildMethod = declaredMethod;
                    declaredMethod.setAccessible(true);
                }
                if (sRegisterGnssMeasurementsCallbackMethod == null) {
                    Method declaredMethod2 = LocationManager.class.getDeclaredMethod("registerGnssMeasurementsCallback", new Class[]{Class.forName("android.location.GnssRequest"), Executor.class, GnssMeasurementsEvent.Callback.class});
                    sRegisterGnssMeasurementsCallbackMethod = declaredMethod2;
                    declaredMethod2.setAccessible(true);
                }
                Object success = sRegisterGnssMeasurementsCallbackMethod.invoke(locationManager, new Object[]{sGnssRequestBuilderBuildMethod.invoke(sGnssRequestBuilderClass.getDeclaredConstructor(new Class[0]).newInstance(new Object[0]), new Object[0]), executor, callback});
                if (success == null || !((Boolean) success).booleanValue()) {
                    return false;
                }
                return true;
            } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException e) {
                return false;
            }
        } else {
            throw new IllegalStateException();
        }
    }

    public static boolean registerGnssStatusCallback(LocationManager locationManager, GnssStatusCompat.Callback callback, Handler handler) {
        if (Build.VERSION.SDK_INT >= 30) {
            return registerGnssStatusCallback(locationManager, ExecutorCompat.create(handler), callback);
        }
        return registerGnssStatusCallback(locationManager, (Executor) new InlineHandlerExecutor(handler), callback);
    }

    public static boolean registerGnssStatusCallback(LocationManager locationManager, Executor executor, GnssStatusCompat.Callback callback) {
        if (Build.VERSION.SDK_INT >= 30) {
            return registerGnssStatusCallback(locationManager, (Handler) null, executor, callback);
        }
        Looper looper = Looper.myLooper();
        if (looper == null) {
            looper = Looper.getMainLooper();
        }
        return registerGnssStatusCallback(locationManager, new Handler(looper), executor, callback);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:39:0x008f, code lost:
        return true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:43:0x009b, code lost:
        return false;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static boolean registerGnssStatusCallback(android.location.LocationManager r18, android.os.Handler r19, java.util.concurrent.Executor r20, androidx.core.location.GnssStatusCompat.Callback r21) {
        /*
            r1 = r18
            r2 = r19
            r3 = r21
            int r0 = android.os.Build.VERSION.SDK_INT
            r4 = 30
            if (r0 < r4) goto L_0x0011
            boolean r0 = androidx.core.location.LocationManagerCompat.Api30Impl.registerGnssStatusCallback(r18, r19, r20, r21)
            return r0
        L_0x0011:
            int r0 = android.os.Build.VERSION.SDK_INT
            r4 = 24
            if (r0 < r4) goto L_0x001c
            boolean r0 = androidx.core.location.LocationManagerCompat.Api24Impl.registerGnssStatusCallback(r18, r19, r20, r21)
            return r0
        L_0x001c:
            r4 = 1
            r5 = 0
            if (r2 == 0) goto L_0x0022
            r0 = 1
            goto L_0x0023
        L_0x0022:
            r0 = 0
        L_0x0023:
            androidx.core.util.Preconditions.checkArgument(r0)
            androidx.collection.SimpleArrayMap<java.lang.Object, java.lang.Object> r6 = androidx.core.location.LocationManagerCompat.GnssListenersHolder.sGnssStatusListeners
            monitor-enter(r6)
            androidx.collection.SimpleArrayMap<java.lang.Object, java.lang.Object> r0 = androidx.core.location.LocationManagerCompat.GnssListenersHolder.sGnssStatusListeners     // Catch:{ all -> 0x010a }
            java.lang.Object r0 = r0.get(r3)     // Catch:{ all -> 0x010a }
            androidx.core.location.LocationManagerCompat$GpsStatusTransport r0 = (androidx.core.location.LocationManagerCompat.GpsStatusTransport) r0     // Catch:{ all -> 0x010a }
            if (r0 != 0) goto L_0x003a
            androidx.core.location.LocationManagerCompat$GpsStatusTransport r7 = new androidx.core.location.LocationManagerCompat$GpsStatusTransport     // Catch:{ all -> 0x010a }
            r7.<init>(r1, r3)     // Catch:{ all -> 0x010a }
            r0 = r7
            goto L_0x003e
        L_0x003a:
            r0.unregister()     // Catch:{ all -> 0x010a }
            r7 = r0
        L_0x003e:
            r8 = r20
            r7.register(r8)     // Catch:{ all -> 0x010f }
            r9 = r7
            java.util.concurrent.FutureTask r0 = new java.util.concurrent.FutureTask     // Catch:{ all -> 0x010f }
            androidx.core.location.LocationManagerCompat$$ExternalSyntheticLambda0 r10 = new androidx.core.location.LocationManagerCompat$$ExternalSyntheticLambda0     // Catch:{ all -> 0x010f }
            r10.<init>(r1, r9)     // Catch:{ all -> 0x010f }
            r0.<init>(r10)     // Catch:{ all -> 0x010f }
            r10 = r0
            android.os.Looper r0 = android.os.Looper.myLooper()     // Catch:{ all -> 0x010f }
            android.os.Looper r11 = r19.getLooper()     // Catch:{ all -> 0x010f }
            if (r0 != r11) goto L_0x005d
            r10.run()     // Catch:{ all -> 0x010f }
            goto L_0x0063
        L_0x005d:
            boolean r0 = r2.post(r10)     // Catch:{ all -> 0x010f }
            if (r0 == 0) goto L_0x00f1
        L_0x0063:
            r11 = 0
            java.util.concurrent.TimeUnit r0 = java.util.concurrent.TimeUnit.SECONDS     // Catch:{ ExecutionException -> 0x00c1, TimeoutException -> 0x00a7 }
            r12 = 5
            long r12 = r0.toNanos(r12)     // Catch:{ ExecutionException -> 0x00c1, TimeoutException -> 0x00a7 }
            long r14 = java.lang.System.nanoTime()     // Catch:{ ExecutionException -> 0x00c1, TimeoutException -> 0x00a7 }
            long r14 = r14 + r12
        L_0x0071:
            java.util.concurrent.TimeUnit r0 = java.util.concurrent.TimeUnit.NANOSECONDS     // Catch:{ InterruptedException -> 0x009c }
            java.lang.Object r0 = r10.get(r12, r0)     // Catch:{ InterruptedException -> 0x009c }
            java.lang.Boolean r0 = (java.lang.Boolean) r0     // Catch:{ InterruptedException -> 0x009c }
            boolean r0 = r0.booleanValue()     // Catch:{ InterruptedException -> 0x009c }
            if (r0 == 0) goto L_0x0090
            androidx.collection.SimpleArrayMap<java.lang.Object, java.lang.Object> r0 = androidx.core.location.LocationManagerCompat.GnssListenersHolder.sGnssStatusListeners     // Catch:{ InterruptedException -> 0x009c }
            r0.put(r3, r9)     // Catch:{ InterruptedException -> 0x009c }
            if (r11 == 0) goto L_0x008e
            java.lang.Thread r0 = java.lang.Thread.currentThread()     // Catch:{ all -> 0x010f }
            r0.interrupt()     // Catch:{ all -> 0x010f }
        L_0x008e:
            monitor-exit(r6)     // Catch:{ all -> 0x010f }
            return r4
        L_0x0090:
            if (r11 == 0) goto L_0x009a
            java.lang.Thread r0 = java.lang.Thread.currentThread()     // Catch:{ all -> 0x010f }
            r0.interrupt()     // Catch:{ all -> 0x010f }
        L_0x009a:
            monitor-exit(r6)     // Catch:{ all -> 0x010f }
            return r5
        L_0x009c:
            r0 = move-exception
            r11 = 1
            long r16 = java.lang.System.nanoTime()     // Catch:{ ExecutionException -> 0x00c1, TimeoutException -> 0x00a7 }
            long r12 = r14 - r16
            goto L_0x0071
        L_0x00a5:
            r0 = move-exception
            goto L_0x00e6
        L_0x00a7:
            r0 = move-exception
            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException     // Catch:{ all -> 0x00a5 }
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ all -> 0x00a5 }
            r5.<init>()     // Catch:{ all -> 0x00a5 }
            java.lang.StringBuilder r5 = r5.append(r2)     // Catch:{ all -> 0x00a5 }
            java.lang.String r12 = " appears to be blocked, please run registerGnssStatusCallback() directly on a Looper thread or ensure the main Looper is not blocked by this thread"
            java.lang.StringBuilder r5 = r5.append(r12)     // Catch:{ all -> 0x00a5 }
            java.lang.String r5 = r5.toString()     // Catch:{ all -> 0x00a5 }
            r4.<init>(r5, r0)     // Catch:{ all -> 0x00a5 }
            throw r4     // Catch:{ all -> 0x00a5 }
        L_0x00c1:
            r0 = move-exception
            java.lang.Throwable r4 = r0.getCause()     // Catch:{ all -> 0x00a5 }
            boolean r4 = r4 instanceof java.lang.RuntimeException     // Catch:{ all -> 0x00a5 }
            if (r4 != 0) goto L_0x00df
            java.lang.Throwable r4 = r0.getCause()     // Catch:{ all -> 0x00a5 }
            boolean r4 = r4 instanceof java.lang.Error     // Catch:{ all -> 0x00a5 }
            if (r4 == 0) goto L_0x00d9
            java.lang.Throwable r4 = r0.getCause()     // Catch:{ all -> 0x00a5 }
            java.lang.Error r4 = (java.lang.Error) r4     // Catch:{ all -> 0x00a5 }
            throw r4     // Catch:{ all -> 0x00a5 }
        L_0x00d9:
            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException     // Catch:{ all -> 0x00a5 }
            r4.<init>(r0)     // Catch:{ all -> 0x00a5 }
            throw r4     // Catch:{ all -> 0x00a5 }
        L_0x00df:
            java.lang.Throwable r4 = r0.getCause()     // Catch:{ all -> 0x00a5 }
            java.lang.RuntimeException r4 = (java.lang.RuntimeException) r4     // Catch:{ all -> 0x00a5 }
            throw r4     // Catch:{ all -> 0x00a5 }
        L_0x00e6:
            if (r11 == 0) goto L_0x00ef
            java.lang.Thread r4 = java.lang.Thread.currentThread()     // Catch:{ all -> 0x010f }
            r4.interrupt()     // Catch:{ all -> 0x010f }
        L_0x00ef:
            throw r0     // Catch:{ all -> 0x010f }
        L_0x00f1:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException     // Catch:{ all -> 0x010f }
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ all -> 0x010f }
            r4.<init>()     // Catch:{ all -> 0x010f }
            java.lang.StringBuilder r4 = r4.append(r2)     // Catch:{ all -> 0x010f }
            java.lang.String r5 = " is shutting down"
            java.lang.StringBuilder r4 = r4.append(r5)     // Catch:{ all -> 0x010f }
            java.lang.String r4 = r4.toString()     // Catch:{ all -> 0x010f }
            r0.<init>(r4)     // Catch:{ all -> 0x010f }
            throw r0     // Catch:{ all -> 0x010f }
        L_0x010a:
            r0 = move-exception
            r8 = r20
        L_0x010d:
            monitor-exit(r6)     // Catch:{ all -> 0x010f }
            throw r0
        L_0x010f:
            r0 = move-exception
            goto L_0x010d
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.core.location.LocationManagerCompat.registerGnssStatusCallback(android.location.LocationManager, android.os.Handler, java.util.concurrent.Executor, androidx.core.location.GnssStatusCompat$Callback):boolean");
    }

    public static void unregisterGnssStatusCallback(LocationManager locationManager, GnssStatusCompat.Callback callback) {
        if (Build.VERSION.SDK_INT >= 24) {
            synchronized (GnssListenersHolder.sGnssStatusListeners) {
                Object transport = GnssListenersHolder.sGnssStatusListeners.remove(callback);
                if (transport != null) {
                    Api24Impl.unregisterGnssStatusCallback(locationManager, transport);
                }
            }
            return;
        }
        synchronized (GnssListenersHolder.sGnssStatusListeners) {
            GpsStatusTransport transport2 = (GpsStatusTransport) GnssListenersHolder.sGnssStatusListeners.remove(callback);
            if (transport2 != null) {
                transport2.unregister();
                locationManager.removeGpsStatusListener(transport2);
            }
        }
    }

    private LocationManagerCompat() {
    }

    private static class LocationListenerKey {
        final LocationListenerCompat mListener;
        final String mProvider;

        LocationListenerKey(String provider, LocationListenerCompat listener) {
            this.mProvider = (String) ObjectsCompat.requireNonNull(provider, "invalid null provider");
            this.mListener = (LocationListenerCompat) ObjectsCompat.requireNonNull(listener, "invalid null listener");
        }

        public boolean equals(Object o) {
            if (!(o instanceof LocationListenerKey)) {
                return false;
            }
            LocationListenerKey that = (LocationListenerKey) o;
            if (!this.mProvider.equals(that.mProvider) || !this.mListener.equals(that.mListener)) {
                return false;
            }
            return true;
        }

        public int hashCode() {
            return ObjectsCompat.hash(this.mProvider, this.mListener);
        }
    }

    private static class LocationListenerTransport implements LocationListener {
        final Executor mExecutor;
        volatile LocationListenerKey mKey;

        LocationListenerTransport(LocationListenerKey key, Executor executor) {
            this.mKey = key;
            this.mExecutor = executor;
        }

        public LocationListenerKey getKey() {
            return (LocationListenerKey) ObjectsCompat.requireNonNull(this.mKey);
        }

        public void unregister() {
            this.mKey = null;
        }

        public void onLocationChanged(Location location) {
            if (this.mKey != null) {
                this.mExecutor.execute(new C0705xa0af9a69(this, location));
            }
        }

        /* access modifiers changed from: package-private */
        /* renamed from: lambda$onLocationChanged$0$androidx-core-location-LocationManagerCompat$LocationListenerTransport */
        public /* synthetic */ void mo15802xa8d50b3d(Location location) {
            LocationListenerKey key = this.mKey;
            if (key != null) {
                key.mListener.onLocationChanged(location);
            }
        }

        public void onLocationChanged(List<Location> locations) {
            if (this.mKey != null) {
                this.mExecutor.execute(new C0703xa0af9a67(this, locations));
            }
        }

        /* access modifiers changed from: package-private */
        /* renamed from: lambda$onLocationChanged$1$androidx-core-location-LocationManagerCompat$LocationListenerTransport */
        public /* synthetic */ void mo15803x2b1fc01c(List locations) {
            LocationListenerKey key = this.mKey;
            if (key != null) {
                key.mListener.onLocationChanged(locations);
            }
        }

        public void onFlushComplete(int requestCode) {
            if (this.mKey != null) {
                this.mExecutor.execute(new C0702xa0af9a66(this, requestCode));
            }
        }

        /* access modifiers changed from: package-private */
        /* renamed from: lambda$onFlushComplete$2$androidx-core-location-LocationManagerCompat$LocationListenerTransport */
        public /* synthetic */ void mo15801xf04cfe9d(int requestCode) {
            LocationListenerKey key = this.mKey;
            if (key != null) {
                key.mListener.onFlushComplete(requestCode);
            }
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
            if (this.mKey != null) {
                this.mExecutor.execute(new C0706xa0af9a6a(this, provider, status, extras));
            }
        }

        /* access modifiers changed from: package-private */
        /* renamed from: lambda$onStatusChanged$3$androidx-core-location-LocationManagerCompat$LocationListenerTransport */
        public /* synthetic */ void mo15806xdbe6a717(String provider, int status, Bundle extras) {
            LocationListenerKey key = this.mKey;
            if (key != null) {
                key.mListener.onStatusChanged(provider, status, extras);
            }
        }

        public void onProviderEnabled(String provider) {
            if (this.mKey != null) {
                this.mExecutor.execute(new C0701xa0af9a65(this, provider));
            }
        }

        /* access modifiers changed from: package-private */
        /* renamed from: lambda$onProviderEnabled$4$androidx-core-location-LocationManagerCompat$LocationListenerTransport */
        public /* synthetic */ void mo15805x5a2a7b08(String provider) {
            LocationListenerKey key = this.mKey;
            if (key != null) {
                key.mListener.onProviderEnabled(provider);
            }
        }

        public void onProviderDisabled(String provider) {
            if (this.mKey != null) {
                this.mExecutor.execute(new C0704xa0af9a68(this, provider));
            }
        }

        /* access modifiers changed from: package-private */
        /* renamed from: lambda$onProviderDisabled$5$androidx-core-location-LocationManagerCompat$LocationListenerTransport */
        public /* synthetic */ void mo15804x442abc92(String provider) {
            LocationListenerKey key = this.mKey;
            if (key != null) {
                key.mListener.onProviderDisabled(provider);
            }
        }
    }

    private static class GnssStatusTransport extends GnssStatus.Callback {
        final GnssStatusCompat.Callback mCallback;

        GnssStatusTransport(GnssStatusCompat.Callback callback) {
            Preconditions.checkArgument(callback != null, "invalid null callback");
            this.mCallback = callback;
        }

        public void onStarted() {
            this.mCallback.onStarted();
        }

        public void onStopped() {
            this.mCallback.onStopped();
        }

        public void onFirstFix(int ttffMillis) {
            this.mCallback.onFirstFix(ttffMillis);
        }

        public void onSatelliteStatusChanged(GnssStatus status) {
            this.mCallback.onSatelliteStatusChanged(GnssStatusCompat.wrap(status));
        }
    }

    private static class PreRGnssStatusTransport extends GnssStatus.Callback {
        final GnssStatusCompat.Callback mCallback;
        volatile Executor mExecutor;

        PreRGnssStatusTransport(GnssStatusCompat.Callback callback) {
            Preconditions.checkArgument(callback != null, "invalid null callback");
            this.mCallback = callback;
        }

        public void register(Executor executor) {
            boolean z = true;
            Preconditions.checkArgument(executor != null, "invalid null executor");
            if (this.mExecutor != null) {
                z = false;
            }
            Preconditions.checkState(z);
            this.mExecutor = executor;
        }

        public void unregister() {
            this.mExecutor = null;
        }

        public void onStarted() {
            Executor executor = this.mExecutor;
            if (executor != null) {
                executor.execute(new C0707xcc169346(this, executor));
            }
        }

        /* access modifiers changed from: package-private */
        /* renamed from: lambda$onStarted$0$androidx-core-location-LocationManagerCompat$PreRGnssStatusTransport */
        public /* synthetic */ void mo15816x7ba12b9c(Executor executor) {
            if (this.mExecutor == executor) {
                this.mCallback.onStarted();
            }
        }

        public void onStopped() {
            Executor executor = this.mExecutor;
            if (executor != null) {
                executor.execute(new C0710xcc169349(this, executor));
            }
        }

        /* access modifiers changed from: package-private */
        /* renamed from: lambda$onStopped$1$androidx-core-location-LocationManagerCompat$PreRGnssStatusTransport */
        public /* synthetic */ void mo15817x80a5cd6f(Executor executor) {
            if (this.mExecutor == executor) {
                this.mCallback.onStopped();
            }
        }

        public void onFirstFix(int ttffMillis) {
            Executor executor = this.mExecutor;
            if (executor != null) {
                executor.execute(new C0709xcc169348(this, executor, ttffMillis));
            }
        }

        /* access modifiers changed from: package-private */
        /* renamed from: lambda$onFirstFix$2$androidx-core-location-LocationManagerCompat$PreRGnssStatusTransport */
        public /* synthetic */ void mo15814x4191f1e(Executor executor, int ttffMillis) {
            if (this.mExecutor == executor) {
                this.mCallback.onFirstFix(ttffMillis);
            }
        }

        public void onSatelliteStatusChanged(GnssStatus status) {
            Executor executor = this.mExecutor;
            if (executor != null) {
                executor.execute(new C0708xcc169347(this, executor, status));
            }
        }

        /* access modifiers changed from: package-private */
        /* renamed from: lambda$onSatelliteStatusChanged$3$androidx-core-location-LocationManagerCompat$PreRGnssStatusTransport */
        public /* synthetic */ void mo15815xdecf6cdb(Executor executor, GnssStatus status) {
            if (this.mExecutor == executor) {
                this.mCallback.onSatelliteStatusChanged(GnssStatusCompat.wrap(status));
            }
        }
    }

    private static class GpsStatusTransport implements GpsStatus.Listener {
        final GnssStatusCompat.Callback mCallback;
        volatile Executor mExecutor;
        private final LocationManager mLocationManager;

        GpsStatusTransport(LocationManager locationManager, GnssStatusCompat.Callback callback) {
            Preconditions.checkArgument(callback != null, "invalid null callback");
            this.mLocationManager = locationManager;
            this.mCallback = callback;
        }

        public void register(Executor executor) {
            Preconditions.checkState(this.mExecutor == null);
            this.mExecutor = executor;
        }

        public void unregister() {
            this.mExecutor = null;
        }

        public void onGpsStatusChanged(int event) {
            Executor executor = this.mExecutor;
            if (executor != null) {
                switch (event) {
                    case 1:
                        executor.execute(new C0697x7b1274a6(this, executor));
                        return;
                    case 2:
                        executor.execute(new C0698x7b1274a7(this, executor));
                        return;
                    case 3:
                        GpsStatus gpsStatus = this.mLocationManager.getGpsStatus((GpsStatus) null);
                        if (gpsStatus != null) {
                            executor.execute(new C0699x7b1274a8(this, executor, gpsStatus.getTimeToFirstFix()));
                            return;
                        }
                        return;
                    case 4:
                        GpsStatus gpsStatus2 = this.mLocationManager.getGpsStatus((GpsStatus) null);
                        if (gpsStatus2 != null) {
                            executor.execute(new C0700x7b1274a9(this, executor, GnssStatusCompat.wrap(gpsStatus2)));
                            return;
                        }
                        return;
                    default:
                        return;
                }
            }
        }

        /* access modifiers changed from: package-private */
        /* renamed from: lambda$onGpsStatusChanged$0$androidx-core-location-LocationManagerCompat$GpsStatusTransport */
        public /* synthetic */ void mo15790x75e92221(Executor executor) {
            if (this.mExecutor == executor) {
                this.mCallback.onStarted();
            }
        }

        /* access modifiers changed from: package-private */
        /* renamed from: lambda$onGpsStatusChanged$1$androidx-core-location-LocationManagerCompat$GpsStatusTransport */
        public /* synthetic */ void mo15791xc3a89a22(Executor executor) {
            if (this.mExecutor == executor) {
                this.mCallback.onStopped();
            }
        }

        /* access modifiers changed from: package-private */
        /* renamed from: lambda$onGpsStatusChanged$2$androidx-core-location-LocationManagerCompat$GpsStatusTransport */
        public /* synthetic */ void mo15792x11681223(Executor executor, int ttff) {
            if (this.mExecutor == executor) {
                this.mCallback.onFirstFix(ttff);
            }
        }

        /* access modifiers changed from: package-private */
        /* renamed from: lambda$onGpsStatusChanged$3$androidx-core-location-LocationManagerCompat$GpsStatusTransport */
        public /* synthetic */ void mo15793x5f278a24(Executor executor, GnssStatusCompat gnssStatus) {
            if (this.mExecutor == executor) {
                this.mCallback.onSatelliteStatusChanged(gnssStatus);
            }
        }
    }

    private static final class CancellableLocationListener implements LocationListener {
        private Consumer<Location> mConsumer;
        private final Executor mExecutor;
        private final LocationManager mLocationManager;
        private final Handler mTimeoutHandler = new Handler(Looper.getMainLooper());
        Runnable mTimeoutRunnable;
        private boolean mTriggered;

        CancellableLocationListener(LocationManager locationManager, Executor executor, Consumer<Location> consumer) {
            this.mLocationManager = locationManager;
            this.mExecutor = executor;
            this.mConsumer = consumer;
        }

        public void cancel() {
            synchronized (this) {
                if (!this.mTriggered) {
                    this.mTriggered = true;
                    cleanup();
                }
            }
        }

        public void startTimeout(long timeoutMs) {
            synchronized (this) {
                if (!this.mTriggered) {
                    C0695x27d5f43a locationManagerCompat$CancellableLocationListener$$ExternalSyntheticLambda0 = new C0695x27d5f43a(this);
                    this.mTimeoutRunnable = locationManagerCompat$CancellableLocationListener$$ExternalSyntheticLambda0;
                    this.mTimeoutHandler.postDelayed(locationManagerCompat$CancellableLocationListener$$ExternalSyntheticLambda0, timeoutMs);
                }
            }
        }

        /* access modifiers changed from: package-private */
        /* renamed from: lambda$startTimeout$0$androidx-core-location-LocationManagerCompat$CancellableLocationListener */
        public /* synthetic */ void mo15780x40ccd759() {
            this.mTimeoutRunnable = null;
            Location location = null;
            onLocationChanged((Location) null);
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
        }

        public void onProviderEnabled(String provider) {
        }

        public void onProviderDisabled(String p) {
            Location location = null;
            onLocationChanged((Location) null);
        }

        public void onLocationChanged(Location location) {
            synchronized (this) {
                if (!this.mTriggered) {
                    this.mTriggered = true;
                    this.mExecutor.execute(new C0696x27d5f43b(this.mConsumer, location));
                    cleanup();
                }
            }
        }

        private void cleanup() {
            this.mConsumer = null;
            this.mLocationManager.removeUpdates(this);
            Runnable runnable = this.mTimeoutRunnable;
            if (runnable != null) {
                this.mTimeoutHandler.removeCallbacks(runnable);
                this.mTimeoutRunnable = null;
            }
        }
    }

    private static final class InlineHandlerExecutor implements Executor {
        private final Handler mHandler;

        InlineHandlerExecutor(Handler handler) {
            this.mHandler = (Handler) Preconditions.checkNotNull(handler);
        }

        public void execute(Runnable command) {
            if (Looper.myLooper() == this.mHandler.getLooper()) {
                command.run();
            } else if (!this.mHandler.post((Runnable) Preconditions.checkNotNull(command))) {
                throw new RejectedExecutionException(this.mHandler + " is shutting down");
            }
        }
    }

    private static class Api31Impl {
        private Api31Impl() {
        }

        static boolean hasProvider(LocationManager locationManager, String provider) {
            return locationManager.hasProvider(provider);
        }

        static void requestLocationUpdates(LocationManager locationManager, String provider, LocationRequest locationRequest, Executor executor, LocationListener listener) {
            locationManager.requestLocationUpdates(provider, locationRequest, executor, listener);
        }

        static boolean registerGnssMeasurementsCallback(LocationManager locationManager, Executor executor, GnssMeasurementsEvent.Callback callback) {
            return locationManager.registerGnssMeasurementsCallback(executor, callback);
        }
    }

    private static class Api30Impl {
        private static Class<?> sLocationRequestClass;
        private static Method sRequestLocationUpdatesExecutorMethod;

        private Api30Impl() {
        }

        static void getCurrentLocation(LocationManager locationManager, String provider, CancellationSignal cancellationSignal, Executor executor, Consumer<Location> consumer) {
            android.os.CancellationSignal cancellationSignal2;
            if (cancellationSignal != null) {
                cancellationSignal2 = (android.os.CancellationSignal) cancellationSignal.getCancellationSignalObject();
            } else {
                cancellationSignal2 = null;
            }
            Objects.requireNonNull(consumer);
            locationManager.getCurrentLocation(provider, cancellationSignal2, executor, new LocationManagerCompat$Api30Impl$$ExternalSyntheticLambda0(consumer));
        }

        public static boolean tryRequestLocationUpdates(LocationManager locationManager, String provider, LocationRequestCompat locationRequest, Executor executor, LocationListenerCompat listener) {
            if (Build.VERSION.SDK_INT >= 30) {
                try {
                    if (sLocationRequestClass == null) {
                        sLocationRequestClass = Class.forName("android.location.LocationRequest");
                    }
                    if (sRequestLocationUpdatesExecutorMethod == null) {
                        Method declaredMethod = LocationManager.class.getDeclaredMethod("requestLocationUpdates", new Class[]{sLocationRequestClass, Executor.class, LocationListener.class});
                        sRequestLocationUpdatesExecutorMethod = declaredMethod;
                        declaredMethod.setAccessible(true);
                    }
                    Object request = locationRequest.toLocationRequest(provider);
                    if (request != null) {
                        sRequestLocationUpdatesExecutorMethod.invoke(locationManager, new Object[]{request, executor, listener});
                        return true;
                    }
                } catch (ClassNotFoundException | IllegalAccessException | NoSuchMethodException | UnsupportedOperationException | InvocationTargetException e) {
                }
            }
            return false;
        }

        public static boolean registerGnssStatusCallback(LocationManager locationManager, Handler baseHandler, Executor executor, GnssStatusCompat.Callback callback) {
            synchronized (GnssListenersHolder.sGnssStatusListeners) {
                GnssStatusTransport transport = (GnssStatusTransport) GnssListenersHolder.sGnssStatusListeners.get(callback);
                if (transport == null) {
                    transport = new GnssStatusTransport(callback);
                }
                if (!locationManager.registerGnssStatusCallback(executor, transport)) {
                    return false;
                }
                GnssListenersHolder.sGnssStatusListeners.put(callback, transport);
                return true;
            }
        }
    }

    private static class Api28Impl {
        private Api28Impl() {
        }

        static boolean isLocationEnabled(LocationManager locationManager) {
            return locationManager.isLocationEnabled();
        }

        static String getGnssHardwareModelName(LocationManager locationManager) {
            return locationManager.getGnssHardwareModelName();
        }

        static int getGnssYearOfHardware(LocationManager locationManager) {
            return locationManager.getGnssYearOfHardware();
        }
    }

    static class Api19Impl {
        private static Class<?> sLocationRequestClass;
        private static Method sRequestLocationUpdatesLooperMethod;

        private Api19Impl() {
        }

        static boolean tryRequestLocationUpdates(LocationManager locationManager, String provider, LocationRequestCompat locationRequest, LocationListenerTransport transport) {
            try {
                if (sLocationRequestClass == null) {
                    sLocationRequestClass = Class.forName("android.location.LocationRequest");
                }
                if (sRequestLocationUpdatesLooperMethod == null) {
                    Method declaredMethod = LocationManager.class.getDeclaredMethod("requestLocationUpdates", new Class[]{sLocationRequestClass, LocationListener.class, Looper.class});
                    sRequestLocationUpdatesLooperMethod = declaredMethod;
                    declaredMethod.setAccessible(true);
                }
                LocationRequest request = locationRequest.toLocationRequest(provider);
                if (request != null) {
                    synchronized (LocationManagerCompat.sLocationListeners) {
                        sRequestLocationUpdatesLooperMethod.invoke(locationManager, new Object[]{request, transport, Looper.getMainLooper()});
                        LocationManagerCompat.registerLocationListenerTransport(locationManager, transport);
                    }
                    return true;
                }
            } catch (ClassNotFoundException | IllegalAccessException | NoSuchMethodException | UnsupportedOperationException | InvocationTargetException e) {
            }
            return false;
        }

        static boolean tryRequestLocationUpdates(LocationManager locationManager, String provider, LocationRequestCompat locationRequest, LocationListenerCompat listener, Looper looper) {
            try {
                if (sLocationRequestClass == null) {
                    sLocationRequestClass = Class.forName("android.location.LocationRequest");
                }
                if (sRequestLocationUpdatesLooperMethod == null) {
                    Method declaredMethod = LocationManager.class.getDeclaredMethod("requestLocationUpdates", new Class[]{sLocationRequestClass, LocationListener.class, Looper.class});
                    sRequestLocationUpdatesLooperMethod = declaredMethod;
                    declaredMethod.setAccessible(true);
                }
                LocationRequest request = locationRequest.toLocationRequest(provider);
                if (request != null) {
                    sRequestLocationUpdatesLooperMethod.invoke(locationManager, new Object[]{request, listener, looper});
                    return true;
                }
            } catch (ClassNotFoundException | IllegalAccessException | NoSuchMethodException | UnsupportedOperationException | InvocationTargetException e) {
            }
            return false;
        }
    }

    static class Api24Impl {
        private Api24Impl() {
        }

        static boolean registerGnssMeasurementsCallback(LocationManager locationManager, GnssMeasurementsEvent.Callback callback, Handler handler) {
            return locationManager.registerGnssMeasurementsCallback(callback, handler);
        }

        static void unregisterGnssMeasurementsCallback(LocationManager locationManager, GnssMeasurementsEvent.Callback callback) {
            locationManager.unregisterGnssMeasurementsCallback(callback);
        }

        static boolean registerGnssStatusCallback(LocationManager locationManager, Handler baseHandler, Executor executor, GnssStatusCompat.Callback callback) {
            Preconditions.checkArgument(baseHandler != null);
            synchronized (GnssListenersHolder.sGnssStatusListeners) {
                PreRGnssStatusTransport transport = (PreRGnssStatusTransport) GnssListenersHolder.sGnssStatusListeners.get(callback);
                if (transport == null) {
                    transport = new PreRGnssStatusTransport(callback);
                } else {
                    transport.unregister();
                }
                transport.register(executor);
                if (!locationManager.registerGnssStatusCallback(transport, baseHandler)) {
                    return false;
                }
                GnssListenersHolder.sGnssStatusListeners.put(callback, transport);
                return true;
            }
        }

        static void unregisterGnssStatusCallback(LocationManager locationManager, Object callback) {
            if (callback instanceof PreRGnssStatusTransport) {
                ((PreRGnssStatusTransport) callback).unregister();
            }
            locationManager.unregisterGnssStatusCallback((GnssStatus.Callback) callback);
        }
    }
}
