package androidx.activity;

import android.window.OnBackInvokedCallback;

/* compiled from: D8$$SyntheticClass */
public final /* synthetic */ class OnBackPressedDispatcher$Api33Impl$$ExternalSyntheticLambda0 implements OnBackInvokedCallback {
    public final /* synthetic */ Runnable f$0;

    public /* synthetic */ OnBackPressedDispatcher$Api33Impl$$ExternalSyntheticLambda0(Runnable runnable) {
        this.f$0 = runnable;
    }

    public final void onBackInvoked() {
        this.f$0.run();
    }
}
