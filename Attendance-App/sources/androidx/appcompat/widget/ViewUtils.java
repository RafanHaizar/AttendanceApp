package androidx.appcompat.widget;

import android.graphics.Rect;
import android.os.Build;
import android.util.Log;
import android.view.View;
import androidx.core.view.ViewCompat;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ViewUtils {
    static final boolean SDK_LEVEL_SUPPORTS_AUTOSIZE = (Build.VERSION.SDK_INT >= 27);
    private static final String TAG = "ViewUtils";
    private static Method sComputeFitSystemWindowsMethod;

    static {
        try {
            Method declaredMethod = View.class.getDeclaredMethod("computeFitSystemWindows", new Class[]{Rect.class, Rect.class});
            sComputeFitSystemWindowsMethod = declaredMethod;
            if (!declaredMethod.isAccessible()) {
                sComputeFitSystemWindowsMethod.setAccessible(true);
            }
        } catch (NoSuchMethodException e) {
            Log.d(TAG, "Could not find method computeFitSystemWindows. Oh well.");
        }
    }

    private ViewUtils() {
    }

    public static boolean isLayoutRtl(View view) {
        return ViewCompat.getLayoutDirection(view) == 1;
    }

    public static void computeFitSystemWindows(View view, Rect inoutInsets, Rect outLocalInsets) {
        Method method = sComputeFitSystemWindowsMethod;
        if (method != null) {
            try {
                method.invoke(view, new Object[]{inoutInsets, outLocalInsets});
            } catch (Exception e) {
                Log.d(TAG, "Could not invoke computeFitSystemWindows", e);
            }
        }
    }

    public static void makeOptionalFitsSystemWindows(View view) {
        try {
            Method method = view.getClass().getMethod("makeOptionalFitsSystemWindows", new Class[0]);
            if (!method.isAccessible()) {
                method.setAccessible(true);
            }
            method.invoke(view, new Object[0]);
        } catch (NoSuchMethodException e) {
            Log.d(TAG, "Could not find method makeOptionalFitsSystemWindows. Oh well...");
        } catch (InvocationTargetException e2) {
            Log.d(TAG, "Could not invoke makeOptionalFitsSystemWindows", e2);
        } catch (IllegalAccessException e3) {
            Log.d(TAG, "Could not invoke makeOptionalFitsSystemWindows", e3);
        }
    }
}
