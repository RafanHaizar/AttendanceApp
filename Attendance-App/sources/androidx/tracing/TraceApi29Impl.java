package androidx.tracing;

import android.os.Trace;

final class TraceApi29Impl {
    private TraceApi29Impl() {
    }

    public static void beginAsyncSection(String methodName, int cookie) {
        Trace.beginAsyncSection(methodName, cookie);
    }

    public static void endAsyncSection(String methodName, int cookie) {
        Trace.endAsyncSection(methodName, cookie);
    }

    public static void setCounter(String counterName, int counterValue) {
        Trace.setCounter(counterName, (long) counterValue);
    }
}
