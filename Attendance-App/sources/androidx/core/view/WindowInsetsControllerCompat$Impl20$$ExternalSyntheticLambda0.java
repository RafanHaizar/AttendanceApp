package androidx.core.view;

import android.view.View;
import android.view.inputmethod.InputMethodManager;

/* compiled from: D8$$SyntheticClass */
public final /* synthetic */ class WindowInsetsControllerCompat$Impl20$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ View f$0;

    public /* synthetic */ WindowInsetsControllerCompat$Impl20$$ExternalSyntheticLambda0(View view) {
        this.f$0 = view;
    }

    public final void run() {
        ((InputMethodManager) this.f$0.getContext().getSystemService("input_method")).showSoftInput(this.f$0, 0);
    }
}
