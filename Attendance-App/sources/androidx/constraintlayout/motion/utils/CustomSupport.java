package androidx.constraintlayout.motion.utils;

import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import androidx.constraintlayout.motion.widget.Debug;
import androidx.constraintlayout.widget.ConstraintAttribute;
import com.itextpdf.svg.SvgConstants;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class CustomSupport {
    private static final String TAG = "CustomSupport";

    public static void setInterpolatedValue(ConstraintAttribute att, View view, float[] value) {
        View view2 = view;
        Class<?> cls = view.getClass();
        String methodName = SvgConstants.Tags.SET + att.getName();
        try {
            boolean z = true;
            switch (C06351.f1015x66adad53[att.getType().ordinal()]) {
                case 1:
                    cls.getMethod(methodName, new Class[]{Integer.TYPE}).invoke(view2, new Object[]{Integer.valueOf((int) value[0])});
                    return;
                case 2:
                    cls.getMethod(methodName, new Class[]{Float.TYPE}).invoke(view2, new Object[]{Float.valueOf(value[0])});
                    return;
                case 3:
                    Method method = cls.getMethod(methodName, new Class[]{Drawable.class});
                    int r = clamp((int) (((float) Math.pow((double) value[0], 0.45454545454545453d)) * 255.0f));
                    int g = clamp((int) (((float) Math.pow((double) value[1], 0.45454545454545453d)) * 255.0f));
                    int b = clamp((int) (((float) Math.pow((double) value[2], 0.45454545454545453d)) * 255.0f));
                    ColorDrawable drawable = new ColorDrawable();
                    drawable.setColor((clamp((int) (value[3] * 255.0f)) << 24) | (r << 16) | (g << 8) | b);
                    method.invoke(view2, new Object[]{drawable});
                    return;
                case 4:
                    cls.getMethod(methodName, new Class[]{Integer.TYPE}).invoke(view2, new Object[]{Integer.valueOf((clamp((int) (value[3] * 255.0f)) << 24) | (clamp((int) (((float) Math.pow((double) value[0], 0.45454545454545453d)) * 255.0f)) << 16) | (clamp((int) (((float) Math.pow((double) value[1], 0.45454545454545453d)) * 255.0f)) << 8) | clamp((int) (((float) Math.pow((double) value[2], 0.45454545454545453d)) * 255.0f)))});
                    return;
                case 5:
                    throw new RuntimeException("unable to interpolate strings " + att.getName());
                case 6:
                    Method method2 = cls.getMethod(methodName, new Class[]{Boolean.TYPE});
                    Object[] objArr = new Object[1];
                    if (value[0] <= 0.5f) {
                        z = false;
                    }
                    objArr[0] = Boolean.valueOf(z);
                    method2.invoke(view2, objArr);
                    return;
                case 7:
                    cls.getMethod(methodName, new Class[]{Float.TYPE}).invoke(view2, new Object[]{Float.valueOf(value[0])});
                    return;
                default:
                    return;
            }
        } catch (NoSuchMethodException e) {
            Log.e(TAG, "no method " + methodName + " on View \"" + Debug.getName(view) + "\"");
            e.printStackTrace();
        } catch (IllegalAccessException e2) {
            Log.e(TAG, "cannot access method " + methodName + " on View \"" + Debug.getName(view) + "\"");
            e2.printStackTrace();
        } catch (InvocationTargetException e3) {
            e3.printStackTrace();
        }
    }

    /* renamed from: androidx.constraintlayout.motion.utils.CustomSupport$1 */
    static /* synthetic */ class C06351 {

        /* renamed from: $SwitchMap$androidx$constraintlayout$widget$ConstraintAttribute$AttributeType */
        static final /* synthetic */ int[] f1015x66adad53;

        static {
            int[] iArr = new int[ConstraintAttribute.AttributeType.values().length];
            f1015x66adad53 = iArr;
            try {
                iArr[ConstraintAttribute.AttributeType.INT_TYPE.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                f1015x66adad53[ConstraintAttribute.AttributeType.FLOAT_TYPE.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                f1015x66adad53[ConstraintAttribute.AttributeType.COLOR_DRAWABLE_TYPE.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                f1015x66adad53[ConstraintAttribute.AttributeType.COLOR_TYPE.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                f1015x66adad53[ConstraintAttribute.AttributeType.STRING_TYPE.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                f1015x66adad53[ConstraintAttribute.AttributeType.BOOLEAN_TYPE.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
            try {
                f1015x66adad53[ConstraintAttribute.AttributeType.DIMENSION_TYPE.ordinal()] = 7;
            } catch (NoSuchFieldError e7) {
            }
        }
    }

    private static int clamp(int c) {
        int c2 = (c & ((c >> 31) ^ -1)) - 255;
        return (c2 & (c2 >> 31)) + 255;
    }
}
