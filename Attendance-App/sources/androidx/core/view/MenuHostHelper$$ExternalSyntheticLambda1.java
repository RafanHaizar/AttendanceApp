package androidx.core.view;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;

/* compiled from: D8$$SyntheticClass */
public final /* synthetic */ class MenuHostHelper$$ExternalSyntheticLambda1 implements LifecycleEventObserver {
    public final /* synthetic */ MenuHostHelper f$0;
    public final /* synthetic */ Lifecycle.State f$1;
    public final /* synthetic */ MenuProvider f$2;

    public /* synthetic */ MenuHostHelper$$ExternalSyntheticLambda1(MenuHostHelper menuHostHelper, Lifecycle.State state, MenuProvider menuProvider) {
        this.f$0 = menuHostHelper;
        this.f$1 = state;
        this.f$2 = menuProvider;
    }

    public final void onStateChanged(LifecycleOwner lifecycleOwner, Lifecycle.Event event) {
        this.f$0.m1313lambda$addMenuProvider$1$androidxcoreviewMenuHostHelper(this.f$1, this.f$2, lifecycleOwner, event);
    }
}
