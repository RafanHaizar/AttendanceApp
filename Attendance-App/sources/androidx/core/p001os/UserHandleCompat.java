package androidx.core.p001os;

import android.os.Build;
import android.os.UserHandle;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/* renamed from: androidx.core.os.UserHandleCompat */
public class UserHandleCompat {
    private static Method sGetUserIdMethod;
    private static Constructor<UserHandle> sUserHandleConstructor;

    private UserHandleCompat() {
    }

    public static UserHandle getUserHandleForUid(int uid) {
        if (Build.VERSION.SDK_INT >= 24) {
            return Api24Impl.getUserHandleForUid(uid);
        }
        try {
            Method getUserIdMethod = getGetUserIdMethod();
            Object[] objArr = {Integer.valueOf(uid)};
            return getUserHandleConstructor().newInstance(new Object[]{(Integer) getUserIdMethod.invoke((Object) null, objArr)});
        } catch (NoSuchMethodException e) {
            Error error = new NoSuchMethodError();
            error.initCause(e);
            throw error;
        } catch (IllegalAccessException e2) {
            Error error2 = new IllegalAccessError();
            error2.initCause(e2);
            throw error2;
        } catch (InstantiationException e3) {
            Error error3 = new InstantiationError();
            error3.initCause(e3);
            throw error3;
        } catch (InvocationTargetException e4) {
            throw new RuntimeException(e4);
        }
    }

    /* renamed from: androidx.core.os.UserHandleCompat$Api24Impl */
    private static class Api24Impl {
        private Api24Impl() {
        }

        static UserHandle getUserHandleForUid(int uid) {
            return UserHandle.getUserHandleForUid(uid);
        }
    }

    private static Method getGetUserIdMethod() throws NoSuchMethodException {
        if (sGetUserIdMethod == null) {
            Method declaredMethod = UserHandle.class.getDeclaredMethod("getUserId", new Class[]{Integer.TYPE});
            sGetUserIdMethod = declaredMethod;
            declaredMethod.setAccessible(true);
        }
        return sGetUserIdMethod;
    }

    private static Constructor<UserHandle> getUserHandleConstructor() throws NoSuchMethodException {
        if (sUserHandleConstructor == null) {
            Constructor<UserHandle> declaredConstructor = UserHandle.class.getDeclaredConstructor(new Class[]{Integer.TYPE});
            sUserHandleConstructor = declaredConstructor;
            declaredConstructor.setAccessible(true);
        }
        return sUserHandleConstructor;
    }
}
