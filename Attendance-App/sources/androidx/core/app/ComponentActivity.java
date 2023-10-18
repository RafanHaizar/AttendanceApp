package androidx.core.app;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import androidx.collection.SimpleArrayMap;
import androidx.core.view.KeyEventDispatcher;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;
import androidx.lifecycle.ReportFragment;

public class ComponentActivity extends Activity implements LifecycleOwner, KeyEventDispatcher.Component {
    private SimpleArrayMap<Class<? extends ExtraData>, ExtraData> mExtraDataMap = new SimpleArrayMap<>();
    private LifecycleRegistry mLifecycleRegistry = new LifecycleRegistry(this);

    @Deprecated
    public static class ExtraData {
    }

    @Deprecated
    public void putExtraData(ExtraData extraData) {
        this.mExtraDataMap.put(extraData.getClass(), extraData);
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ReportFragment.injectIfNeededIn(this);
    }

    /* access modifiers changed from: protected */
    public void onSaveInstanceState(Bundle outState) {
        this.mLifecycleRegistry.markState(Lifecycle.State.CREATED);
        super.onSaveInstanceState(outState);
    }

    @Deprecated
    public <T extends ExtraData> T getExtraData(Class<T> extraDataClass) {
        return (ExtraData) this.mExtraDataMap.get(extraDataClass);
    }

    public Lifecycle getLifecycle() {
        return this.mLifecycleRegistry;
    }

    public boolean superDispatchKeyEvent(KeyEvent event) {
        return super.dispatchKeyEvent(event);
    }

    public boolean dispatchKeyShortcutEvent(KeyEvent event) {
        View decor = getWindow().getDecorView();
        if (decor == null || !KeyEventDispatcher.dispatchBeforeHierarchy(decor, event)) {
            return super.dispatchKeyShortcutEvent(event);
        }
        return true;
    }

    public boolean dispatchKeyEvent(KeyEvent event) {
        View decor = getWindow().getDecorView();
        if (decor == null || !KeyEventDispatcher.dispatchBeforeHierarchy(decor, event)) {
            return KeyEventDispatcher.dispatchKeyEvent(this, decor, this, event);
        }
        return true;
    }

    /* access modifiers changed from: protected */
    public final boolean shouldDumpInternalState(String[] args) {
        return !shouldSkipDump(args);
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static boolean shouldSkipDump(java.lang.String[] r4) {
        /*
            r0 = 0
            if (r4 == 0) goto L_0x0065
            int r1 = r4.length
            if (r1 <= 0) goto L_0x0065
            r1 = r4[r0]
            int r2 = r1.hashCode()
            r3 = 1
            switch(r2) {
                case -645125871: goto L_0x0039;
                case 100470631: goto L_0x002f;
                case 472614934: goto L_0x0025;
                case 1159329357: goto L_0x001b;
                case 1455016274: goto L_0x0011;
                default: goto L_0x0010;
            }
        L_0x0010:
            goto L_0x0043
        L_0x0011:
            java.lang.String r2 = "--autofill"
            boolean r1 = r1.equals(r2)
            if (r1 == 0) goto L_0x0010
            r1 = 0
            goto L_0x0044
        L_0x001b:
            java.lang.String r2 = "--contentcapture"
            boolean r1 = r1.equals(r2)
            if (r1 == 0) goto L_0x0010
            r1 = 1
            goto L_0x0044
        L_0x0025:
            java.lang.String r2 = "--list-dumpables"
            boolean r1 = r1.equals(r2)
            if (r1 == 0) goto L_0x0010
            r1 = 3
            goto L_0x0044
        L_0x002f:
            java.lang.String r2 = "--dump-dumpable"
            boolean r1 = r1.equals(r2)
            if (r1 == 0) goto L_0x0010
            r1 = 4
            goto L_0x0044
        L_0x0039:
            java.lang.String r2 = "--translation"
            boolean r1 = r1.equals(r2)
            if (r1 == 0) goto L_0x0010
            r1 = 2
            goto L_0x0044
        L_0x0043:
            r1 = -1
        L_0x0044:
            switch(r1) {
                case 0: goto L_0x005d;
                case 1: goto L_0x0055;
                case 2: goto L_0x004d;
                case 3: goto L_0x0048;
                case 4: goto L_0x0048;
                default: goto L_0x0047;
            }
        L_0x0047:
            goto L_0x0065
        L_0x0048:
            boolean r0 = androidx.core.p001os.BuildCompat.isAtLeastT()
            return r0
        L_0x004d:
            int r1 = android.os.Build.VERSION.SDK_INT
            r2 = 31
            if (r1 < r2) goto L_0x0054
            r0 = 1
        L_0x0054:
            return r0
        L_0x0055:
            int r1 = android.os.Build.VERSION.SDK_INT
            r2 = 29
            if (r1 < r2) goto L_0x005c
            r0 = 1
        L_0x005c:
            return r0
        L_0x005d:
            int r1 = android.os.Build.VERSION.SDK_INT
            r2 = 26
            if (r1 < r2) goto L_0x0064
            r0 = 1
        L_0x0064:
            return r0
        L_0x0065:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.core.app.ComponentActivity.shouldSkipDump(java.lang.String[]):boolean");
    }
}
