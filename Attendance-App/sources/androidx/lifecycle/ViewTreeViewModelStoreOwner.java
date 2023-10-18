package androidx.lifecycle;

import android.view.View;
import androidx.lifecycle.viewmodel.C0905R;

public class ViewTreeViewModelStoreOwner {
    private ViewTreeViewModelStoreOwner() {
    }

    public static void set(View view, ViewModelStoreOwner viewModelStoreOwner) {
        view.setTag(C0905R.C0906id.view_tree_view_model_store_owner, viewModelStoreOwner);
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v1, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v4, resolved type: androidx.lifecycle.ViewModelStoreOwner} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static androidx.lifecycle.ViewModelStoreOwner get(android.view.View r4) {
        /*
            int r0 = androidx.lifecycle.viewmodel.C0905R.C0906id.view_tree_view_model_store_owner
            java.lang.Object r0 = r4.getTag(r0)
            androidx.lifecycle.ViewModelStoreOwner r0 = (androidx.lifecycle.ViewModelStoreOwner) r0
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
            int r3 = androidx.lifecycle.viewmodel.C0905R.C0906id.view_tree_view_model_store_owner
            java.lang.Object r3 = r2.getTag(r3)
            r0 = r3
            androidx.lifecycle.ViewModelStoreOwner r0 = (androidx.lifecycle.ViewModelStoreOwner) r0
            android.view.ViewParent r1 = r2.getParent()
            goto L_0x000f
        L_0x0026:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.lifecycle.ViewTreeViewModelStoreOwner.get(android.view.View):androidx.lifecycle.ViewModelStoreOwner");
    }
}
