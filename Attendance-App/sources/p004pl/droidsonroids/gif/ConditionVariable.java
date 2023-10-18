package p004pl.droidsonroids.gif;

/* renamed from: pl.droidsonroids.gif.ConditionVariable */
class ConditionVariable {
    private volatile boolean mCondition;

    ConditionVariable() {
    }

    /* access modifiers changed from: package-private */
    public synchronized void set(boolean state) {
        if (state) {
            open();
        } else {
            close();
        }
    }

    /* access modifiers changed from: package-private */
    public synchronized void open() {
        boolean old = this.mCondition;
        this.mCondition = true;
        if (!old) {
            notify();
        }
    }

    /* access modifiers changed from: package-private */
    public synchronized void close() {
        this.mCondition = false;
    }

    /* access modifiers changed from: package-private */
    public synchronized void block() throws InterruptedException {
        while (!this.mCondition) {
            wait();
        }
    }
}
