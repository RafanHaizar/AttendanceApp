package androidx.core.p001os;

import android.os.Build;
import android.os.Trace;
import android.util.Log;
import java.lang.reflect.Method;

@Deprecated
/* renamed from: androidx.core.os.TraceCompat */
public final class TraceCompat {
    private static final String TAG = "TraceCompat";
    private static Method sAsyncTraceBeginMethod;
    private static Method sAsyncTraceEndMethod;
    private static Method sIsTagEnabledMethod;
    private static Method sTraceCounterMethod;
    private static long sTraceTagApp;

    static {
        if (Build.VERSION.SDK_INT < 29) {
            try {
                sTraceTagApp = Trace.class.getField("TRACE_TAG_APP").getLong((Object) null);
                sIsTagEnabledMethod = Trace.class.getMethod("isTagEnabled", new Class[]{Long.TYPE});
                sAsyncTraceBeginMethod = Trace.class.getMethod("asyncTraceBegin", new Class[]{Long.TYPE, String.class, Integer.TYPE});
                sAsyncTraceEndMethod = Trace.class.getMethod("asyncTraceEnd", new Class[]{Long.TYPE, String.class, Integer.TYPE});
                sTraceCounterMethod = Trace.class.getMethod("traceCounter", new Class[]{Long.TYPE, String.class, Integer.TYPE});
            } catch (Exception e) {
                Log.i(TAG, "Unable to initialize via reflection.", e);
            }
        }
    }

    public static boolean isEnabled() {
        if (Build.VERSION.SDK_INT >= 29) {
            return Api29Impl.isEnabled();
        }
        try {
            return ((Boolean) sIsTagEnabledMethod.invoke((Object) null, new Object[]{Long.valueOf(sTraceTagApp)})).booleanValue();
        } catch (Exception e) {
            Log.v(TAG, "Unable to invoke isTagEnabled() via reflection.");
            return false;
        }
    }

    public static void beginSection(String sectionName) {
        Api18Impl.beginSection(sectionName);
    }

    public static void endSection() {
        Api18Impl.endSection();
    }

    public static void beginAsyncSection(String methodName, int cookie) {
        if (Build.VERSION.SDK_INT >= 29) {
            Api29Impl.beginAsyncSection(methodName, cookie);
            return;
        }
        try {
            sAsyncTraceBeginMethod.invoke((Object) null, new Object[]{Long.valueOf(sTraceTagApp), methodName, Integer.valueOf(cookie)});
        } catch (Exception e) {
            Log.v(TAG, "Unable to invoke asyncTraceBegin() via reflection.");
        }
    }

    public static void endAsyncSection(String methodName, int cookie) {
        if (Build.VERSION.SDK_INT >= 29) {
            Api29Impl.endAsyncSection(methodName, cookie);
            return;
        }
        try {
            sAsyncTraceEndMethod.invoke((Object) null, new Object[]{Long.valueOf(sTraceTagApp), methodName, Integer.valueOf(cookie)});
        } catch (Exception e) {
            Log.v(TAG, "Unable to invoke endAsyncSection() via reflection.");
        }
    }

    public static void setCounter(String counterName, int counterValue) {
        if (Build.VERSION.SDK_INT >= 29) {
            Api29Impl.setCounter(counterName, (long) counterValue);
            return;
        }
        try {
            sTraceCounterMethod.invoke((Object) null, new Object[]{Long.valueOf(sTraceTagApp), counterName, Integer.valueOf(counterValue)});
        } catch (Exception e) {
            Log.v(TAG, "Unable to invoke traceCounter() via reflection.");
        }
    }

    private TraceCompat() {
    }

    /* renamed from: androidx.core.os.TraceCompat$Api29Impl */
    static class Api29Impl {
        private Api29Impl() {
        }

        static boolean isEnabled() {
            return Trace.isEnabled();
        }

        static void endAsyncSection(String methodName, int cookie) {
            Trace.endAsyncSection(methodName, cookie);
        }

        static void beginAsyncSection(String methodName, int cookie) {
            Trace.beginAsyncSection(methodName, cookie);
        }

        static void setCounter(String counterName, long counterValue) {
            Trace.setCounter(counterName, counterValue);
        }
    }

    /* renamed from: androidx.core.os.TraceCompat$Api18Impl */
    static class Api18Impl {
        private Api18Impl() {
        }

        static void beginSection(String sectionName) {
            Trace.beginSection(sectionName);
        }

        static void endSection() {
            Trace.endSection();
        }
    }
}
