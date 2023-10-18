package androidx.lifecycle;

import androidx.arch.core.executor.ArchTaskExecutor;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicBoolean;

public abstract class ComputableLiveData<T> {
    final AtomicBoolean mComputing;
    final Executor mExecutor;
    final AtomicBoolean mInvalid;
    final Runnable mInvalidationRunnable;
    final LiveData<T> mLiveData;
    final Runnable mRefreshRunnable;

    /* access modifiers changed from: protected */
    public abstract T compute();

    public ComputableLiveData() {
        this(ArchTaskExecutor.getIOThreadExecutor());
    }

    public ComputableLiveData(Executor executor) {
        this.mInvalid = new AtomicBoolean(true);
        this.mComputing = new AtomicBoolean(false);
        this.mRefreshRunnable = new Runnable() {
            /* JADX WARNING: Removed duplicated region for block: B:3:0x000e  */
            /* Code decompiled incorrectly, please refer to instructions dump. */
            public void run() {
                /*
                    r5 = this;
                L_0x0001:
                    r0 = 0
                    androidx.lifecycle.ComputableLiveData r1 = androidx.lifecycle.ComputableLiveData.this
                    java.util.concurrent.atomic.AtomicBoolean r1 = r1.mComputing
                    r2 = 0
                    r3 = 1
                    boolean r1 = r1.compareAndSet(r2, r3)
                    if (r1 == 0) goto L_0x003c
                    r1 = 0
                L_0x000f:
                    androidx.lifecycle.ComputableLiveData r4 = androidx.lifecycle.ComputableLiveData.this     // Catch:{ all -> 0x0033 }
                    java.util.concurrent.atomic.AtomicBoolean r4 = r4.mInvalid     // Catch:{ all -> 0x0033 }
                    boolean r4 = r4.compareAndSet(r3, r2)     // Catch:{ all -> 0x0033 }
                    if (r4 == 0) goto L_0x0022
                    r0 = 1
                    androidx.lifecycle.ComputableLiveData r4 = androidx.lifecycle.ComputableLiveData.this     // Catch:{ all -> 0x0033 }
                    java.lang.Object r4 = r4.compute()     // Catch:{ all -> 0x0033 }
                    r1 = r4
                    goto L_0x000f
                L_0x0022:
                    if (r0 == 0) goto L_0x002b
                    androidx.lifecycle.ComputableLiveData r3 = androidx.lifecycle.ComputableLiveData.this     // Catch:{ all -> 0x0033 }
                    androidx.lifecycle.LiveData<T> r3 = r3.mLiveData     // Catch:{ all -> 0x0033 }
                    r3.postValue(r1)     // Catch:{ all -> 0x0033 }
                L_0x002b:
                    androidx.lifecycle.ComputableLiveData r1 = androidx.lifecycle.ComputableLiveData.this
                    java.util.concurrent.atomic.AtomicBoolean r1 = r1.mComputing
                    r1.set(r2)
                    goto L_0x003c
                L_0x0033:
                    r1 = move-exception
                    androidx.lifecycle.ComputableLiveData r3 = androidx.lifecycle.ComputableLiveData.this
                    java.util.concurrent.atomic.AtomicBoolean r3 = r3.mComputing
                    r3.set(r2)
                    throw r1
                L_0x003c:
                    if (r0 == 0) goto L_0x0048
                    androidx.lifecycle.ComputableLiveData r1 = androidx.lifecycle.ComputableLiveData.this
                    java.util.concurrent.atomic.AtomicBoolean r1 = r1.mInvalid
                    boolean r1 = r1.get()
                    if (r1 != 0) goto L_0x0001
                L_0x0048:
                    return
                */
                throw new UnsupportedOperationException("Method not decompiled: androidx.lifecycle.ComputableLiveData.C08852.run():void");
            }
        };
        this.mInvalidationRunnable = new Runnable() {
            public void run() {
                boolean isActive = ComputableLiveData.this.mLiveData.hasActiveObservers();
                if (ComputableLiveData.this.mInvalid.compareAndSet(false, true) && isActive) {
                    ComputableLiveData.this.mExecutor.execute(ComputableLiveData.this.mRefreshRunnable);
                }
            }
        };
        this.mExecutor = executor;
        this.mLiveData = new LiveData<T>() {
            /* access modifiers changed from: protected */
            public void onActive() {
                ComputableLiveData.this.mExecutor.execute(ComputableLiveData.this.mRefreshRunnable);
            }
        };
    }

    public LiveData<T> getLiveData() {
        return this.mLiveData;
    }

    public void invalidate() {
        ArchTaskExecutor.getInstance().executeOnMainThread(this.mInvalidationRunnable);
    }
}
