package p004pl.droidsonroids.gif;

import java.lang.Thread;

/* renamed from: pl.droidsonroids.gif.SafeRunnable */
abstract class SafeRunnable implements Runnable {
    final GifDrawable mGifDrawable;

    /* access modifiers changed from: package-private */
    public abstract void doWork();

    SafeRunnable(GifDrawable gifDrawable) {
        this.mGifDrawable = gifDrawable;
    }

    public final void run() {
        try {
            if (!this.mGifDrawable.isRecycled()) {
                doWork();
            }
        } catch (Throwable throwable) {
            Thread.UncaughtExceptionHandler uncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
            if (uncaughtExceptionHandler != null) {
                uncaughtExceptionHandler.uncaughtException(Thread.currentThread(), throwable);
            }
            throw throwable;
        }
    }
}
