package androidx.arch.core.executor;

import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class DefaultTaskExecutor extends TaskExecutor {
    private final ExecutorService mDiskIO = Executors.newFixedThreadPool(4, new ThreadFactory() {
        private static final String THREAD_NAME_STEM = "arch_disk_io_%d";
        private final AtomicInteger mThreadId = new AtomicInteger(0);

        public Thread newThread(Runnable r) {
            Thread t = new Thread(r);
            t.setName(String.format(THREAD_NAME_STEM, new Object[]{Integer.valueOf(this.mThreadId.getAndIncrement())}));
            return t;
        }
    });
    private final Object mLock = new Object();
    private volatile Handler mMainHandler;

    public void executeOnDiskIO(Runnable runnable) {
        this.mDiskIO.execute(runnable);
    }

    public void postToMainThread(Runnable runnable) {
        if (this.mMainHandler == null) {
            synchronized (this.mLock) {
                if (this.mMainHandler == null) {
                    this.mMainHandler = createAsync(Looper.getMainLooper());
                }
            }
        }
        this.mMainHandler.post(runnable);
    }

    public boolean isMainThread() {
        return Looper.getMainLooper().getThread() == Thread.currentThread();
    }

    private static Handler createAsync(Looper looper) {
        if (Build.VERSION.SDK_INT >= 28) {
            return Handler.createAsync(looper);
        }
        Class<Handler> cls = Handler.class;
        try {
            return cls.getDeclaredConstructor(new Class[]{Looper.class, Handler.Callback.class, Boolean.TYPE}).newInstance(new Object[]{looper, null, true});
        } catch (IllegalAccessException | InstantiationException | NoSuchMethodException e) {
            return new Handler(looper);
        } catch (InvocationTargetException e2) {
            return new Handler(looper);
        }
    }
}
