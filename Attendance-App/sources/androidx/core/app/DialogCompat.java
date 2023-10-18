package androidx.core.app;

import android.app.Dialog;
import android.os.Build;
import android.view.View;

public class DialogCompat {
    private DialogCompat() {
    }

    public static View requireViewById(Dialog dialog, int id) {
        if (Build.VERSION.SDK_INT >= 28) {
            return (View) Api28Impl.requireViewById(dialog, id);
        }
        View view = dialog.findViewById(id);
        if (view != null) {
            return view;
        }
        throw new IllegalArgumentException("ID does not reference a View inside this Dialog");
    }

    static class Api28Impl {
        private Api28Impl() {
        }

        static <T> T requireViewById(Dialog dialog, int id) {
            return dialog.requireViewById(id);
        }
    }
}
