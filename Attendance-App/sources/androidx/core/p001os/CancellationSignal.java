package androidx.core.p001os;

/* renamed from: androidx.core.os.CancellationSignal */
public final class CancellationSignal {
    private boolean mCancelInProgress;
    private Object mCancellationSignalObj;
    private boolean mIsCanceled;
    private OnCancelListener mOnCancelListener;

    /* renamed from: androidx.core.os.CancellationSignal$OnCancelListener */
    public interface OnCancelListener {
        void onCancel();
    }

    public boolean isCanceled() {
        boolean z;
        synchronized (this) {
            z = this.mIsCanceled;
        }
        return z;
    }

    public void throwIfCanceled() {
        if (isCanceled()) {
            throw new OperationCanceledException();
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:11:?, code lost:
        r0.onCancel();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0018, code lost:
        r3 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x001a, code lost:
        if (r1 == null) goto L_0x002b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x001c, code lost:
        androidx.core.p001os.CancellationSignal.Api16Impl.cancel(r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x0020, code lost:
        monitor-enter(r4);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:?, code lost:
        r4.mCancelInProgress = false;
        notifyAll();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x0027, code lost:
        throw r3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x002b, code lost:
        monitor-enter(r4);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:?, code lost:
        r4.mCancelInProgress = false;
        notifyAll();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x0031, code lost:
        monitor-exit(r4);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x0033, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x0012, code lost:
        if (r0 == null) goto L_0x001a;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void cancel() {
        /*
            r4 = this;
            monitor-enter(r4)
            boolean r0 = r4.mIsCanceled     // Catch:{ all -> 0x0037 }
            if (r0 == 0) goto L_0x0007
            monitor-exit(r4)     // Catch:{ all -> 0x0037 }
            return
        L_0x0007:
            r0 = 1
            r4.mIsCanceled = r0     // Catch:{ all -> 0x0037 }
            r4.mCancelInProgress = r0     // Catch:{ all -> 0x0037 }
            androidx.core.os.CancellationSignal$OnCancelListener r0 = r4.mOnCancelListener     // Catch:{ all -> 0x0037 }
            java.lang.Object r1 = r4.mCancellationSignalObj     // Catch:{ all -> 0x0037 }
            monitor-exit(r4)     // Catch:{ all -> 0x0037 }
            r2 = 0
            if (r0 == 0) goto L_0x001a
            r0.onCancel()     // Catch:{ all -> 0x0018 }
            goto L_0x001a
        L_0x0018:
            r3 = move-exception
            goto L_0x0020
        L_0x001a:
            if (r1 == 0) goto L_0x002b
            androidx.core.p001os.CancellationSignal.Api16Impl.cancel(r1)     // Catch:{ all -> 0x0018 }
            goto L_0x002b
        L_0x0020:
            monitor-enter(r4)
            r4.mCancelInProgress = r2     // Catch:{ all -> 0x0028 }
            r4.notifyAll()     // Catch:{ all -> 0x0028 }
            monitor-exit(r4)     // Catch:{ all -> 0x0028 }
            throw r3
        L_0x0028:
            r2 = move-exception
            monitor-exit(r4)     // Catch:{ all -> 0x0028 }
            throw r2
        L_0x002b:
            monitor-enter(r4)
            r4.mCancelInProgress = r2     // Catch:{ all -> 0x0034 }
            r4.notifyAll()     // Catch:{ all -> 0x0034 }
            monitor-exit(r4)     // Catch:{ all -> 0x0034 }
            return
        L_0x0034:
            r2 = move-exception
            monitor-exit(r4)     // Catch:{ all -> 0x0034 }
            throw r2
        L_0x0037:
            r0 = move-exception
            monitor-exit(r4)     // Catch:{ all -> 0x0037 }
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.core.p001os.CancellationSignal.cancel():void");
    }

    public void setOnCancelListener(OnCancelListener listener) {
        synchronized (this) {
            waitForCancelFinishedLocked();
            if (this.mOnCancelListener != listener) {
                this.mOnCancelListener = listener;
                if (this.mIsCanceled) {
                    if (listener != null) {
                        listener.onCancel();
                    }
                }
            }
        }
    }

    public Object getCancellationSignalObject() {
        Object obj;
        synchronized (this) {
            if (this.mCancellationSignalObj == null) {
                android.os.CancellationSignal createCancellationSignal = Api16Impl.createCancellationSignal();
                this.mCancellationSignalObj = createCancellationSignal;
                if (this.mIsCanceled) {
                    Api16Impl.cancel(createCancellationSignal);
                }
            }
            obj = this.mCancellationSignalObj;
        }
        return obj;
    }

    private void waitForCancelFinishedLocked() {
        while (this.mCancelInProgress) {
            try {
                wait();
            } catch (InterruptedException e) {
            }
        }
    }

    /* renamed from: androidx.core.os.CancellationSignal$Api16Impl */
    static class Api16Impl {
        private Api16Impl() {
        }

        static void cancel(Object cancellationSignal) {
            ((android.os.CancellationSignal) cancellationSignal).cancel();
        }

        static android.os.CancellationSignal createCancellationSignal() {
            return new android.os.CancellationSignal();
        }
    }
}
