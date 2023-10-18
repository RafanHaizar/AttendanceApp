package androidx.core.p001os;

import android.os.Build;
import android.os.Message;

/* renamed from: androidx.core.os.MessageCompat */
public final class MessageCompat {
    private static boolean sTryIsAsynchronous = true;
    private static boolean sTrySetAsynchronous = true;

    public static void setAsynchronous(Message message, boolean async) {
        if (Build.VERSION.SDK_INT >= 22) {
            Api22Impl.setAsynchronous(message, async);
        } else if (sTrySetAsynchronous) {
            try {
                Api22Impl.setAsynchronous(message, async);
            } catch (NoSuchMethodError e) {
                sTrySetAsynchronous = false;
            }
        }
    }

    public static boolean isAsynchronous(Message message) {
        if (Build.VERSION.SDK_INT >= 22) {
            return Api22Impl.isAsynchronous(message);
        }
        if (sTryIsAsynchronous) {
            try {
                return Api22Impl.isAsynchronous(message);
            } catch (NoSuchMethodError e) {
                sTryIsAsynchronous = false;
            }
        }
        return false;
    }

    private MessageCompat() {
    }

    /* renamed from: androidx.core.os.MessageCompat$Api22Impl */
    static class Api22Impl {
        private Api22Impl() {
        }

        static boolean isAsynchronous(Message message) {
            return message.isAsynchronous();
        }

        static void setAsynchronous(Message message, boolean async) {
            message.setAsynchronous(async);
        }
    }
}
