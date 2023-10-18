package androidx.emoji2.text;

import android.os.Handler;
import java.util.concurrent.Executor;

/* compiled from: D8$$SyntheticClass */
public final /* synthetic */ class ConcurrencyHelpers$$ExternalSyntheticLambda1 implements Executor {
    public final /* synthetic */ Handler f$0;

    public /* synthetic */ ConcurrencyHelpers$$ExternalSyntheticLambda1(Handler handler) {
        this.f$0 = handler;
    }

    public final void execute(Runnable runnable) {
        this.f$0.post(runnable);
    }
}
