package kotlinx.coroutines.android;

import kotlinx.coroutines.DisposableHandle;

/* compiled from: D8$$SyntheticClass */
public final /* synthetic */ class HandlerContext$$ExternalSyntheticLambda0 implements DisposableHandle {
    public final /* synthetic */ HandlerContext f$0;
    public final /* synthetic */ Runnable f$1;

    public /* synthetic */ HandlerContext$$ExternalSyntheticLambda0(HandlerContext handlerContext, Runnable runnable) {
        this.f$0 = handlerContext;
        this.f$1 = runnable;
    }

    public final void dispose() {
        HandlerContext.m1878invokeOnTimeout$lambda3(this.f$0, this.f$1);
    }
}
