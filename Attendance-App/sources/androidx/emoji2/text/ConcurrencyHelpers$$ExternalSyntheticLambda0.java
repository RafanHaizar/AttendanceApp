package androidx.emoji2.text;

import java.util.concurrent.ThreadFactory;

/* compiled from: D8$$SyntheticClass */
public final /* synthetic */ class ConcurrencyHelpers$$ExternalSyntheticLambda0 implements ThreadFactory {
    public final /* synthetic */ String f$0;

    public /* synthetic */ ConcurrencyHelpers$$ExternalSyntheticLambda0(String str) {
        this.f$0 = str;
    }

    public final Thread newThread(Runnable runnable) {
        return ConcurrencyHelpers.lambda$createBackgroundPriorityExecutor$0(this.f$0, runnable);
    }
}
