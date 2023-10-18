package androidx.core.graphics;

import android.content.Context;
import android.graphics.Typeface;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class TypefaceCompatApi28Impl extends TypefaceCompatApi26Impl {
    private static final String CREATE_FROM_FAMILIES_WITH_DEFAULT_METHOD = "createFromFamiliesWithDefault";
    private static final String DEFAULT_FAMILY = "sans-serif";
    private static final int RESOLVE_BY_FONT_TABLE = -1;

    /* access modifiers changed from: protected */
    public Typeface createFromFamiliesWithDefault(Object family) {
        try {
            Object familyArray = Array.newInstance(this.mFontFamily, 1);
            Array.set(familyArray, 0, family);
            return (Typeface) this.mCreateFromFamiliesWithDefault.invoke((Object) null, new Object[]{familyArray, DEFAULT_FAMILY, -1, -1});
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    /* access modifiers changed from: protected */
    public Method obtainCreateFromFamiliesWithDefaultMethod(Class<?> fontFamily) throws NoSuchMethodException {
        Method m = Typeface.class.getDeclaredMethod(CREATE_FROM_FAMILIES_WITH_DEFAULT_METHOD, new Class[]{Array.newInstance(fontFamily, 1).getClass(), String.class, Integer.TYPE, Integer.TYPE});
        m.setAccessible(true);
        return m;
    }

    /* access modifiers changed from: package-private */
    public Typeface createWeightStyle(Context context, Typeface base, int weight, boolean italic) {
        return Typeface.create(base, weight, italic);
    }
}
