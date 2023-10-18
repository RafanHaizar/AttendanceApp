package androidx.core.view;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import com.android.tools.r8.annotations.SynthesizedClassV2;

public interface MenuProvider {
    void onCreateMenu(Menu menu, MenuInflater menuInflater);

    void onMenuClosed(Menu menu);

    boolean onMenuItemSelected(MenuItem menuItem);

    void onPrepareMenu(Menu menu);

    @SynthesizedClassV2(kind = 8, versionHash = "ea87655719898b9807d7a88878e9de051d12af172d2fab563c9881b5e404e7d4")
    /* renamed from: androidx.core.view.MenuProvider$-CC  reason: invalid class name */
    public final /* synthetic */ class CC {
        public static void $default$onPrepareMenu(MenuProvider _this, Menu menu) {
        }

        public static void $default$onMenuClosed(MenuProvider _this, Menu menu) {
        }
    }
}
