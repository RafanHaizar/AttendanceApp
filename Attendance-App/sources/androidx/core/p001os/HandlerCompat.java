package androidx.core.p001os;

import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import java.lang.reflect.InvocationTargetException;

/* renamed from: androidx.core.os.HandlerCompat */
public final class HandlerCompat {
    private static final String TAG = "HandlerCompat";

    public static Handler createAsync(Looper looper) {
        if (Build.VERSION.SDK_INT >= 28) {
            return Api28Impl.createAsync(looper);
        }
        Class<Handler> cls = Handler.class;
        try {
            return cls.getDeclaredConstructor(new Class[]{Looper.class, Handler.Callback.class, Boolean.TYPE}).newInstance(new Object[]{looper, null, true});
        } catch (IllegalAccessException | InstantiationException | NoSuchMethodException wrappedException) {
            Log.w(TAG, "Unable to invoke Handler(Looper, Callback, boolean) constructor", wrappedException);
            return new Handler(looper);
        } catch (InvocationTargetException e) {
            Throwable cause = e.getCause();
            if (cause instanceof RuntimeException) {
                throw ((RuntimeException) cause);
            } else if (cause instanceof Error) {
                throw ((Error) cause);
            } else {
                throw new RuntimeException(cause);
            }
        }
    }

    public static Handler createAsync(Looper looper, Handler.Callback callback) {
        if (Build.VERSION.SDK_INT >= 28) {
            return Api28Impl.createAsync(looper, callback);
        }
        Class<Handler> cls = Handler.class;
        try {
            return cls.getDeclaredConstructor(new Class[]{Looper.class, Handler.Callback.class, Boolean.TYPE}).newInstance(new Object[]{looper, callback, true});
        } catch (IllegalAccessException | InstantiationException | NoSuchMethodException wrappedException) {
            Log.w(TAG, "Unable to invoke Handler(Looper, Callback, boolean) constructor", wrappedException);
            return new Handler(looper, callback);
        } catch (InvocationTargetException e) {
            Throwable cause = e.getCause();
            if (cause instanceof RuntimeException) {
                throw ((RuntimeException) cause);
            } else if (cause instanceof Error) {
                throw ((Error) cause);
            } else {
                throw new RuntimeException(cause);
            }
        }
    }

    public static boolean postDelayed(Handler handler, Runnable r, Object token, long delayMillis) {
        if (Build.VERSION.SDK_INT >= 28) {
            return Api28Impl.postDelayed(handler, r, token, delayMillis);
        }
        Message message = Message.obtain(handler, r);
        message.obj = token;
        return handler.sendMessageDelayed(message, delayMillis);
    }

    public static boolean hasCallbacks(Handler handler, Runnable r) {
        Exception wrappedException;
        if (Build.VERSION.SDK_INT >= 29) {
            return Api29Impl.hasCallbacks(handler, r);
        }
        try {
            return ((Boolean) Handler.class.getMethod("hasCallbacks", new Class[]{Runnable.class}).invoke(handler, new Object[]{r})).booleanValue();
        } catch (InvocationTargetException e) {
            Throwable cause = e.getCause();
            if (cause instanceof RuntimeException) {
                throw ((RuntimeException) cause);
            } else if (cause instanceof Error) {
                throw ((Error) cause);
            } else {
                throw new RuntimeException(cause);
            }
        } catch (IllegalAccessException e2) {
            wrappedException = e2;
            throw new UnsupportedOperationException("Failed to call Handler.hasCallbacks(), but there is no safe failure mode for this method. Raising exception.", wrappedException);
        } catch (NoSuchMethodException e3) {
            wrappedException = e3;
            throw new UnsupportedOperationException("Failed to call Handler.hasCallbacks(), but there is no safe failure mode for this method. Raising exception.", wrappedException);
        } catch (NullPointerException e4) {
            wrappedException = e4;
            throw new UnsupportedOperationException("Failed to call Handler.hasCallbacks(), but there is no safe failure mode for this method. Raising exception.", wrappedException);
        }
    }

    private HandlerCompat() {
    }

    /* renamed from: androidx.core.os.HandlerCompat$Api29Impl */
    private static class Api29Impl {
        private Api29Impl() {
        }

        public static boolean hasCallbacks(Handler handler, Runnable r) {
            return handler.hasCallbacks(r);
        }
    }

    /* renamed from: androidx.core.os.HandlerCompat$Api28Impl */
    private static class Api28Impl {
        private Api28Impl() {
        }

        public static Handler createAsync(Looper looper) {
            return Handler.createAsync(looper);
        }

        public static Handler createAsync(Looper looper, Handler.Callback callback) {
            return Handler.createAsync(looper, callback);
        }

        public static boolean postDelayed(Handler handler, Runnable r, Object token, long delayMillis) {
            return handler.postDelayed(r, token, delayMillis);
        }
    }
}
