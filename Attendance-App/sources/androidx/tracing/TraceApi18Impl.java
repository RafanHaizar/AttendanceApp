package androidx.tracing;

import android.os.Trace;

final class TraceApi18Impl {
    private TraceApi18Impl() {
    }

    public static void beginSection(String label) {
        Trace.beginSection(label);
    }

    public static void endSection() {
        Trace.endSection();
    }
}
