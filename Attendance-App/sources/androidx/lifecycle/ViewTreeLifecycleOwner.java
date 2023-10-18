package androidx.lifecycle;

import android.view.View;
import androidx.lifecycle.runtime.C0903R;

public class ViewTreeLifecycleOwner {
    private ViewTreeLifecycleOwner() {
    }

    public static void set(View view, LifecycleOwner lifecycleOwner) {
        view.setTag(C0903R.C0904id.view_tree_lifecycle_owner, lifecycleOwner);
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v1, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v4, resolved type: androidx.lifecycle.LifecycleOwner} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static androidx.lifecycle.LifecycleOwner get(android.view.View r4) {
        /*
            int r0 = androidx.lifecycle.runtime.C0903R.C0904id.view_tree_lifecycle_owner
            java.lang.Object r0 = r4.getTag(r0)
            androidx.lifecycle.LifecycleOwner r0 = (androidx.lifecycle.LifecycleOwner) r0
            if (r0 == 0) goto L_0x000b
            return r0
        L_0x000b:
            android.view.ViewParent r1 = r4.getParent()
        L_0x000f:
            if (r0 != 0) goto L_0x0026
            boolean r2 = r1 instanceof android.view.View
            if (r2 == 0) goto L_0x0026
            r2 = r1
            android.view.View r2 = (android.view.View) r2
            int r3 = androidx.lifecycle.runtime.C0903R.C0904id.view_tree_lifecycle_owner
            java.lang.Object r3 = r2.getTag(r3)
            r0 = r3
            androidx.lifecycle.LifecycleOwner r0 = (androidx.lifecycle.LifecycleOwner) r0
            android.view.ViewParent r1 = r2.getParent()
            goto L_0x000f
        L_0x0026:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.lifecycle.ViewTreeLifecycleOwner.get(android.view.View):androidx.lifecycle.LifecycleOwner");
    }
}
