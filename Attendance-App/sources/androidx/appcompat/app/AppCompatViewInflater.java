package androidx.appcompat.app;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.InflateException;
import android.view.View;
import androidx.appcompat.C0503R;
import androidx.appcompat.view.ContextThemeWrapper;
import androidx.appcompat.widget.AppCompatAutoCompleteTextView;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.AppCompatCheckedTextView;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatMultiAutoCompleteTextView;
import androidx.appcompat.widget.AppCompatRadioButton;
import androidx.appcompat.widget.AppCompatRatingBar;
import androidx.appcompat.widget.AppCompatSeekBar;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.AppCompatToggleButton;
import androidx.collection.SimpleArrayMap;
import androidx.core.view.ViewCompat;
import com.itextpdf.styledxmlparser.CommonAttributeConstants;
import com.itextpdf.svg.SvgConstants;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class AppCompatViewInflater {
    private static final String LOG_TAG = "AppCompatViewInflater";
    private static final int[] sAccessibilityHeading = {16844160};
    private static final int[] sAccessibilityPaneTitle = {16844156};
    private static final String[] sClassPrefixList = {"android.widget.", "android.view.", "android.webkit."};
    private static final SimpleArrayMap<String, Constructor<? extends View>> sConstructorMap = new SimpleArrayMap<>();
    private static final Class<?>[] sConstructorSignature = {Context.class, AttributeSet.class};
    private static final int[] sOnClickAttrs = {16843375};
    private static final int[] sScreenReaderFocusable = {16844148};
    private final Object[] mConstructorArgs = new Object[2];

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final android.view.View createView(android.view.View r4, java.lang.String r5, android.content.Context r6, android.util.AttributeSet r7, boolean r8, boolean r9, boolean r10, boolean r11) {
        /*
            r3 = this;
            r0 = r6
            if (r8 == 0) goto L_0x0009
            if (r4 == 0) goto L_0x0009
            android.content.Context r6 = r4.getContext()
        L_0x0009:
            if (r9 != 0) goto L_0x000d
            if (r10 == 0) goto L_0x0011
        L_0x000d:
            android.content.Context r6 = themifyContext(r6, r7, r9, r10)
        L_0x0011:
            if (r11 == 0) goto L_0x0017
            android.content.Context r6 = androidx.appcompat.widget.TintContextWrapper.wrap(r6)
        L_0x0017:
            r1 = 0
            int r2 = r5.hashCode()
            switch(r2) {
                case -1946472170: goto L_0x00ac;
                case -1455429095: goto L_0x00a1;
                case -1346021293: goto L_0x0096;
                case -938935918: goto L_0x008c;
                case -937446323: goto L_0x0082;
                case -658531749: goto L_0x0077;
                case -339785223: goto L_0x006d;
                case 776382189: goto L_0x0063;
                case 799298502: goto L_0x0058;
                case 1125864064: goto L_0x004e;
                case 1413872058: goto L_0x0042;
                case 1601505219: goto L_0x0037;
                case 1666676343: goto L_0x002c;
                case 2001146706: goto L_0x0021;
                default: goto L_0x001f;
            }
        L_0x001f:
            goto L_0x00b7
        L_0x0021:
            java.lang.String r2 = "Button"
            boolean r2 = r5.equals(r2)
            if (r2 == 0) goto L_0x001f
            r2 = 2
            goto L_0x00b8
        L_0x002c:
            java.lang.String r2 = "EditText"
            boolean r2 = r5.equals(r2)
            if (r2 == 0) goto L_0x001f
            r2 = 3
            goto L_0x00b8
        L_0x0037:
            java.lang.String r2 = "CheckBox"
            boolean r2 = r5.equals(r2)
            if (r2 == 0) goto L_0x001f
            r2 = 6
            goto L_0x00b8
        L_0x0042:
            java.lang.String r2 = "AutoCompleteTextView"
            boolean r2 = r5.equals(r2)
            if (r2 == 0) goto L_0x001f
            r2 = 9
            goto L_0x00b8
        L_0x004e:
            java.lang.String r2 = "ImageView"
            boolean r2 = r5.equals(r2)
            if (r2 == 0) goto L_0x001f
            r2 = 1
            goto L_0x00b8
        L_0x0058:
            java.lang.String r2 = "ToggleButton"
            boolean r2 = r5.equals(r2)
            if (r2 == 0) goto L_0x001f
            r2 = 13
            goto L_0x00b8
        L_0x0063:
            java.lang.String r2 = "RadioButton"
            boolean r2 = r5.equals(r2)
            if (r2 == 0) goto L_0x001f
            r2 = 7
            goto L_0x00b8
        L_0x006d:
            java.lang.String r2 = "Spinner"
            boolean r2 = r5.equals(r2)
            if (r2 == 0) goto L_0x001f
            r2 = 4
            goto L_0x00b8
        L_0x0077:
            java.lang.String r2 = "SeekBar"
            boolean r2 = r5.equals(r2)
            if (r2 == 0) goto L_0x001f
            r2 = 12
            goto L_0x00b8
        L_0x0082:
            java.lang.String r2 = "ImageButton"
            boolean r2 = r5.equals(r2)
            if (r2 == 0) goto L_0x001f
            r2 = 5
            goto L_0x00b8
        L_0x008c:
            java.lang.String r2 = "TextView"
            boolean r2 = r5.equals(r2)
            if (r2 == 0) goto L_0x001f
            r2 = 0
            goto L_0x00b8
        L_0x0096:
            java.lang.String r2 = "MultiAutoCompleteTextView"
            boolean r2 = r5.equals(r2)
            if (r2 == 0) goto L_0x001f
            r2 = 10
            goto L_0x00b8
        L_0x00a1:
            java.lang.String r2 = "CheckedTextView"
            boolean r2 = r5.equals(r2)
            if (r2 == 0) goto L_0x001f
            r2 = 8
            goto L_0x00b8
        L_0x00ac:
            java.lang.String r2 = "RatingBar"
            boolean r2 = r5.equals(r2)
            if (r2 == 0) goto L_0x001f
            r2 = 11
            goto L_0x00b8
        L_0x00b7:
            r2 = -1
        L_0x00b8:
            switch(r2) {
                case 0: goto L_0x012a;
                case 1: goto L_0x0122;
                case 2: goto L_0x011a;
                case 3: goto L_0x0112;
                case 4: goto L_0x010a;
                case 5: goto L_0x0102;
                case 6: goto L_0x00fa;
                case 7: goto L_0x00f2;
                case 8: goto L_0x00ea;
                case 9: goto L_0x00e2;
                case 10: goto L_0x00da;
                case 11: goto L_0x00d2;
                case 12: goto L_0x00ca;
                case 13: goto L_0x00c1;
                default: goto L_0x00bb;
            }
        L_0x00bb:
            android.view.View r1 = r3.createView(r6, r5, r7)
            goto L_0x0132
        L_0x00c1:
            androidx.appcompat.widget.AppCompatToggleButton r1 = r3.createToggleButton(r6, r7)
            r3.verifyNotNull(r1, r5)
            goto L_0x0132
        L_0x00ca:
            androidx.appcompat.widget.AppCompatSeekBar r1 = r3.createSeekBar(r6, r7)
            r3.verifyNotNull(r1, r5)
            goto L_0x0132
        L_0x00d2:
            androidx.appcompat.widget.AppCompatRatingBar r1 = r3.createRatingBar(r6, r7)
            r3.verifyNotNull(r1, r5)
            goto L_0x0132
        L_0x00da:
            androidx.appcompat.widget.AppCompatMultiAutoCompleteTextView r1 = r3.createMultiAutoCompleteTextView(r6, r7)
            r3.verifyNotNull(r1, r5)
            goto L_0x0132
        L_0x00e2:
            androidx.appcompat.widget.AppCompatAutoCompleteTextView r1 = r3.createAutoCompleteTextView(r6, r7)
            r3.verifyNotNull(r1, r5)
            goto L_0x0132
        L_0x00ea:
            androidx.appcompat.widget.AppCompatCheckedTextView r1 = r3.createCheckedTextView(r6, r7)
            r3.verifyNotNull(r1, r5)
            goto L_0x0132
        L_0x00f2:
            androidx.appcompat.widget.AppCompatRadioButton r1 = r3.createRadioButton(r6, r7)
            r3.verifyNotNull(r1, r5)
            goto L_0x0132
        L_0x00fa:
            androidx.appcompat.widget.AppCompatCheckBox r1 = r3.createCheckBox(r6, r7)
            r3.verifyNotNull(r1, r5)
            goto L_0x0132
        L_0x0102:
            androidx.appcompat.widget.AppCompatImageButton r1 = r3.createImageButton(r6, r7)
            r3.verifyNotNull(r1, r5)
            goto L_0x0132
        L_0x010a:
            androidx.appcompat.widget.AppCompatSpinner r1 = r3.createSpinner(r6, r7)
            r3.verifyNotNull(r1, r5)
            goto L_0x0132
        L_0x0112:
            androidx.appcompat.widget.AppCompatEditText r1 = r3.createEditText(r6, r7)
            r3.verifyNotNull(r1, r5)
            goto L_0x0132
        L_0x011a:
            androidx.appcompat.widget.AppCompatButton r1 = r3.createButton(r6, r7)
            r3.verifyNotNull(r1, r5)
            goto L_0x0132
        L_0x0122:
            androidx.appcompat.widget.AppCompatImageView r1 = r3.createImageView(r6, r7)
            r3.verifyNotNull(r1, r5)
            goto L_0x0132
        L_0x012a:
            androidx.appcompat.widget.AppCompatTextView r1 = r3.createTextView(r6, r7)
            r3.verifyNotNull(r1, r5)
        L_0x0132:
            if (r1 != 0) goto L_0x013a
            if (r0 == r6) goto L_0x013a
            android.view.View r1 = r3.createViewFromTag(r6, r5, r7)
        L_0x013a:
            if (r1 == 0) goto L_0x0142
            r3.checkOnClickListener(r1, r7)
            r3.backportAccessibilityAttributes(r6, r1, r7)
        L_0x0142:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.appcompat.app.AppCompatViewInflater.createView(android.view.View, java.lang.String, android.content.Context, android.util.AttributeSet, boolean, boolean, boolean, boolean):android.view.View");
    }

    /* access modifiers changed from: protected */
    public AppCompatTextView createTextView(Context context, AttributeSet attrs) {
        return new AppCompatTextView(context, attrs);
    }

    /* access modifiers changed from: protected */
    public AppCompatImageView createImageView(Context context, AttributeSet attrs) {
        return new AppCompatImageView(context, attrs);
    }

    /* access modifiers changed from: protected */
    public AppCompatButton createButton(Context context, AttributeSet attrs) {
        return new AppCompatButton(context, attrs);
    }

    /* access modifiers changed from: protected */
    public AppCompatEditText createEditText(Context context, AttributeSet attrs) {
        return new AppCompatEditText(context, attrs);
    }

    /* access modifiers changed from: protected */
    public AppCompatSpinner createSpinner(Context context, AttributeSet attrs) {
        return new AppCompatSpinner(context, attrs);
    }

    /* access modifiers changed from: protected */
    public AppCompatImageButton createImageButton(Context context, AttributeSet attrs) {
        return new AppCompatImageButton(context, attrs);
    }

    /* access modifiers changed from: protected */
    public AppCompatCheckBox createCheckBox(Context context, AttributeSet attrs) {
        return new AppCompatCheckBox(context, attrs);
    }

    /* access modifiers changed from: protected */
    public AppCompatRadioButton createRadioButton(Context context, AttributeSet attrs) {
        return new AppCompatRadioButton(context, attrs);
    }

    /* access modifiers changed from: protected */
    public AppCompatCheckedTextView createCheckedTextView(Context context, AttributeSet attrs) {
        return new AppCompatCheckedTextView(context, attrs);
    }

    /* access modifiers changed from: protected */
    public AppCompatAutoCompleteTextView createAutoCompleteTextView(Context context, AttributeSet attrs) {
        return new AppCompatAutoCompleteTextView(context, attrs);
    }

    /* access modifiers changed from: protected */
    public AppCompatMultiAutoCompleteTextView createMultiAutoCompleteTextView(Context context, AttributeSet attrs) {
        return new AppCompatMultiAutoCompleteTextView(context, attrs);
    }

    /* access modifiers changed from: protected */
    public AppCompatRatingBar createRatingBar(Context context, AttributeSet attrs) {
        return new AppCompatRatingBar(context, attrs);
    }

    /* access modifiers changed from: protected */
    public AppCompatSeekBar createSeekBar(Context context, AttributeSet attrs) {
        return new AppCompatSeekBar(context, attrs);
    }

    /* access modifiers changed from: protected */
    public AppCompatToggleButton createToggleButton(Context context, AttributeSet attrs) {
        return new AppCompatToggleButton(context, attrs);
    }

    private void verifyNotNull(View view, String name) {
        if (view == null) {
            throw new IllegalStateException(getClass().getName() + " asked to inflate view for <" + name + ">, but returned null");
        }
    }

    /* access modifiers changed from: protected */
    public View createView(Context context, String name, AttributeSet attrs) {
        return null;
    }

    private View createViewFromTag(Context context, String name, AttributeSet attrs) {
        if (name.equals(SvgConstants.Tags.VIEW)) {
            name = attrs.getAttributeValue((String) null, CommonAttributeConstants.CLASS);
        }
        try {
            Object[] objArr = this.mConstructorArgs;
            objArr[0] = context;
            objArr[1] = attrs;
            if (-1 == name.indexOf(46)) {
                int i = 0;
                while (true) {
                    String[] strArr = sClassPrefixList;
                    if (i < strArr.length) {
                        View view = createViewByPrefix(context, name, strArr[i]);
                        if (view != null) {
                            return view;
                        }
                        i++;
                    } else {
                        Object[] objArr2 = this.mConstructorArgs;
                        objArr2[0] = null;
                        objArr2[1] = null;
                        return null;
                    }
                }
            } else {
                View createViewByPrefix = createViewByPrefix(context, name, (String) null);
                Object[] objArr3 = this.mConstructorArgs;
                objArr3[0] = null;
                objArr3[1] = null;
                return createViewByPrefix;
            }
        } catch (Exception e) {
            return null;
        } finally {
            Object[] objArr4 = this.mConstructorArgs;
            objArr4[0] = null;
            objArr4[1] = null;
        }
    }

    private void checkOnClickListener(View view, AttributeSet attrs) {
        Context context = view.getContext();
        if ((context instanceof ContextWrapper) && ViewCompat.hasOnClickListeners(view)) {
            TypedArray a = context.obtainStyledAttributes(attrs, sOnClickAttrs);
            String handlerName = a.getString(0);
            if (handlerName != null) {
                view.setOnClickListener(new DeclaredOnClickListener(view, handlerName));
            }
            a.recycle();
        }
    }

    private View createViewByPrefix(Context context, String name, String prefix) throws ClassNotFoundException, InflateException {
        String str;
        SimpleArrayMap<String, Constructor<? extends View>> simpleArrayMap = sConstructorMap;
        Constructor<? extends U> constructor = simpleArrayMap.get(name);
        if (constructor == null) {
            if (prefix != null) {
                try {
                    str = prefix + name;
                } catch (Exception e) {
                    return null;
                }
            } else {
                str = name;
            }
            constructor = Class.forName(str, false, context.getClassLoader()).asSubclass(View.class).getConstructor(sConstructorSignature);
            simpleArrayMap.put(name, constructor);
        }
        constructor.setAccessible(true);
        return (View) constructor.newInstance(this.mConstructorArgs);
    }

    private static Context themifyContext(Context context, AttributeSet attrs, boolean useAndroidTheme, boolean useAppTheme) {
        TypedArray a = context.obtainStyledAttributes(attrs, C0503R.styleable.View, 0, 0);
        int themeId = 0;
        if (useAndroidTheme) {
            themeId = a.getResourceId(C0503R.styleable.View_android_theme, 0);
        }
        if (useAppTheme && themeId == 0 && (themeId = a.getResourceId(C0503R.styleable.View_theme, 0)) != 0) {
            Log.i(LOG_TAG, "app:theme is now deprecated. Please move to using android:theme instead.");
        }
        a.recycle();
        if (themeId == 0) {
            return context;
        }
        if (!(context instanceof ContextThemeWrapper) || ((ContextThemeWrapper) context).getThemeResId() != themeId) {
            return new ContextThemeWrapper(context, themeId);
        }
        return context;
    }

    private void backportAccessibilityAttributes(Context context, View view, AttributeSet attrs) {
        if (Build.VERSION.SDK_INT <= 28) {
            TypedArray a = context.obtainStyledAttributes(attrs, sAccessibilityHeading);
            if (a.hasValue(0)) {
                ViewCompat.setAccessibilityHeading(view, a.getBoolean(0, false));
            }
            a.recycle();
            TypedArray a2 = context.obtainStyledAttributes(attrs, sAccessibilityPaneTitle);
            if (a2.hasValue(0)) {
                ViewCompat.setAccessibilityPaneTitle(view, a2.getString(0));
            }
            a2.recycle();
            TypedArray a3 = context.obtainStyledAttributes(attrs, sScreenReaderFocusable);
            if (a3.hasValue(0)) {
                ViewCompat.setScreenReaderFocusable(view, a3.getBoolean(0, false));
            }
            a3.recycle();
        }
    }

    private static class DeclaredOnClickListener implements View.OnClickListener {
        private final View mHostView;
        private final String mMethodName;
        private Context mResolvedContext;
        private Method mResolvedMethod;

        public DeclaredOnClickListener(View hostView, String methodName) {
            this.mHostView = hostView;
            this.mMethodName = methodName;
        }

        public void onClick(View v) {
            if (this.mResolvedMethod == null) {
                resolveMethod(this.mHostView.getContext());
            }
            try {
                this.mResolvedMethod.invoke(this.mResolvedContext, new Object[]{v});
            } catch (IllegalAccessException e) {
                throw new IllegalStateException("Could not execute non-public method for android:onClick", e);
            } catch (InvocationTargetException e2) {
                throw new IllegalStateException("Could not execute method for android:onClick", e2);
            }
        }

        private void resolveMethod(Context context) {
            String idText;
            Method method;
            while (context != null) {
                try {
                    if (!context.isRestricted() && (method = context.getClass().getMethod(this.mMethodName, new Class[]{View.class})) != null) {
                        this.mResolvedMethod = method;
                        this.mResolvedContext = context;
                        return;
                    }
                } catch (NoSuchMethodException e) {
                }
                if (context instanceof ContextWrapper) {
                    context = ((ContextWrapper) context).getBaseContext();
                } else {
                    context = null;
                }
            }
            int id = this.mHostView.getId();
            if (id == -1) {
                idText = "";
            } else {
                idText = " with id '" + this.mHostView.getContext().getResources().getResourceEntryName(id) + "'";
            }
            throw new IllegalStateException("Could not find method " + this.mMethodName + "(View) in a parent or ancestor Context for android:onClick attribute defined on view " + this.mHostView.getClass() + idText);
        }
    }
}
