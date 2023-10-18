package androidx.constraintlayout.motion.widget;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import com.inan.cmhs.attendance.BuildConfig;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.nio.CharBuffer;

public class Debug {
    public static void logStack(String tag, String msg, int n) {
        StackTraceElement[] st = new Throwable().getStackTrace();
        String s = " ";
        int n2 = Math.min(n, st.length - 1);
        for (int i = 1; i <= n2; i++) {
            StackTraceElement stackTraceElement = st[i];
            s = s + " ";
            Log.v(tag, msg + s + (".(" + st[i].getFileName() + ":" + st[i].getLineNumber() + ") " + st[i].getMethodName()) + s);
        }
    }

    public static void printStack(String msg, int n) {
        StackTraceElement[] st = new Throwable().getStackTrace();
        String s = " ";
        int n2 = Math.min(n, st.length - 1);
        for (int i = 1; i <= n2; i++) {
            StackTraceElement stackTraceElement = st[i];
            s = s + " ";
            System.out.println(msg + s + (".(" + st[i].getFileName() + ":" + st[i].getLineNumber() + ") ") + s);
        }
    }

    public static String getName(View view) {
        try {
            return view.getContext().getResources().getResourceEntryName(view.getId());
        } catch (Exception e) {
            return "UNKNOWN";
        }
    }

    public static void dumpPoc(Object obj) {
        StackTraceElement s = new Throwable().getStackTrace()[1];
        String loc = ".(" + s.getFileName() + ":" + s.getLineNumber() + ")";
        Class c = obj.getClass();
        System.out.println(loc + "------------- " + c.getName() + " --------------------");
        Field[] declaredFields = c.getFields();
        for (Field declaredField : declaredFields) {
            try {
                Object value = declaredField.get(obj);
                if (declaredField.getName().startsWith("layout_constraint")) {
                    if (!(value instanceof Integer) || !value.toString().equals("-1")) {
                        if (!(value instanceof Integer) || !value.toString().equals("0")) {
                            if (!(value instanceof Float) || !value.toString().equals(BuildConfig.VERSION_NAME)) {
                                if (!(value instanceof Float) || !value.toString().equals("0.5")) {
                                    System.out.println(loc + "    " + declaredField.getName() + " " + value);
                                }
                            }
                        }
                    }
                }
            } catch (IllegalAccessException e) {
            }
        }
        System.out.println(loc + "------------- " + c.getSimpleName() + " --------------------");
    }

    public static String getName(Context context, int id) {
        if (id == -1) {
            return "UNKNOWN";
        }
        try {
            return context.getResources().getResourceEntryName(id);
        } catch (Exception e) {
            return "?" + id;
        }
    }

    public static String getName(Context context, int[] id) {
        String tmp;
        try {
            String str = id.length + "[";
            int i = 0;
            while (i < id.length) {
                String str2 = str + (i == 0 ? "" : " ");
                try {
                    tmp = context.getResources().getResourceEntryName(id[i]);
                } catch (Resources.NotFoundException e) {
                    tmp = "? " + id[i] + " ";
                }
                str = str2 + tmp;
                i++;
            }
            return str + "]";
        } catch (Exception ex) {
            Log.v("DEBUG", ex.toString());
            return "UNKNOWN";
        }
    }

    public static String getState(MotionLayout layout, int stateId) {
        return getState(layout, stateId, -1);
    }

    public static String getState(MotionLayout layout, int stateId, int len) {
        int n;
        if (stateId == -1) {
            return "UNDEFINED";
        }
        String str = layout.getContext().getResources().getResourceEntryName(stateId);
        if (len == -1) {
            return str;
        }
        if (str.length() > len) {
            str = str.replaceAll("([^_])[aeiou]+", "$1");
        }
        if (str.length() <= len || (n = str.replaceAll("[^_]", "").length()) <= 0) {
            return str;
        }
        return str.replaceAll(CharBuffer.allocate((str.length() - len) / n).toString().replace(0, '.') + "_", "_");
    }

    public static String getActionType(MotionEvent event) {
        int type = event.getAction();
        Field[] fields = MotionEvent.class.getFields();
        for (Field field : fields) {
            try {
                if (Modifier.isStatic(field.getModifiers()) && field.getType().equals(Integer.TYPE) && field.getInt((Object) null) == type) {
                    return field.getName();
                }
            } catch (IllegalAccessException e) {
            }
        }
        return "---";
    }

    public static String getLocation() {
        StackTraceElement s = new Throwable().getStackTrace()[1];
        return ".(" + s.getFileName() + ":" + s.getLineNumber() + ")";
    }

    public static String getLoc() {
        StackTraceElement s = new Throwable().getStackTrace()[1];
        return ".(" + s.getFileName() + ":" + s.getLineNumber() + ") " + s.getMethodName() + "()";
    }

    public static String getLocation2() {
        StackTraceElement s = new Throwable().getStackTrace()[2];
        return ".(" + s.getFileName() + ":" + s.getLineNumber() + ")";
    }

    public static String getCallFrom(int n) {
        StackTraceElement s = new Throwable().getStackTrace()[n + 2];
        return ".(" + s.getFileName() + ":" + s.getLineNumber() + ")";
    }

    public static void dumpLayoutParams(ViewGroup layout, String str) {
        StackTraceElement s = new Throwable().getStackTrace()[1];
        String loc = ".(" + s.getFileName() + ":" + s.getLineNumber() + ") " + str + "  ";
        int n = layout.getChildCount();
        System.out.println(str + " children " + n);
        for (int i = 0; i < n; i++) {
            View v = layout.getChildAt(i);
            System.out.println(loc + "     " + getName(v));
            ViewGroup.LayoutParams param = v.getLayoutParams();
            Field[] declaredFields = param.getClass().getFields();
            for (Field declaredField : declaredFields) {
                try {
                    Object value = declaredField.get(param);
                    if (declaredField.getName().contains("To")) {
                        if (!value.toString().equals("-1")) {
                            System.out.println(loc + "       " + declaredField.getName() + " " + value);
                        }
                    }
                } catch (IllegalAccessException e) {
                }
            }
        }
    }

    public static void dumpLayoutParams(ViewGroup.LayoutParams param, String str) {
        StackTraceElement s = new Throwable().getStackTrace()[1];
        String loc = ".(" + s.getFileName() + ":" + s.getLineNumber() + ") " + str + "  ";
        System.out.println(" >>>>>>>>>>>>>>>>>>. dump " + loc + "  " + param.getClass().getName());
        Field[] declaredFields = param.getClass().getFields();
        for (Field declaredField : declaredFields) {
            try {
                Object value = declaredField.get(param);
                String name = declaredField.getName();
                if (name.contains("To")) {
                    if (!value.toString().equals("-1")) {
                        System.out.println(loc + "       " + name + " " + value);
                    }
                }
            } catch (IllegalAccessException e) {
            }
        }
        System.out.println(" <<<<<<<<<<<<<<<<< dump " + loc);
    }
}
