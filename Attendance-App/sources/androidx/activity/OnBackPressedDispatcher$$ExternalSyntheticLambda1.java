package androidx.activity;

/* compiled from: D8$$SyntheticClass */
public final /* synthetic */ class OnBackPressedDispatcher$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ OnBackPressedDispatcher f$0;

    public /* synthetic */ OnBackPressedDispatcher$$ExternalSyntheticLambda1(OnBackPressedDispatcher onBackPressedDispatcher) {
        this.f$0 = onBackPressedDispatcher;
    }

    public final void run() {
        this.f$0.onBackPressed();
    }
}
