package androidx.appcompat.app;

import android.content.Context;

/* compiled from: D8$$SyntheticClass */
public final /* synthetic */ class AppCompatDelegate$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ Context f$0;

    public /* synthetic */ AppCompatDelegate$$ExternalSyntheticLambda0(Context context) {
        this.f$0 = context;
    }

    public final void run() {
        AppCompatDelegate.syncRequestedAndStoredLocales(this.f$0);
    }
}
