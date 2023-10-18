package androidx.core.view;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;

/* compiled from: D8$$SyntheticClass */
public final /* synthetic */ class MenuHostHelper$$ExternalSyntheticLambda0 implements LifecycleEventObserver {
    public final /* synthetic */ MenuHostHelper f$0;
    public final /* synthetic */ MenuProvider f$1;

    public /* synthetic */ MenuHostHelper$$ExternalSyntheticLambda0(MenuHostHelper menuHostHelper, MenuProvider menuProvider) {
        this.f$0 = menuHostHelper;
        this.f$1 = menuProvider;
    }

    public final void onStateChanged(LifecycleOwner lifecycleOwner, Lifecycle.Event event) {
        this.f$0.m1312lambda$addMenuProvider$0$androidxcoreviewMenuHostHelper(this.f$1, lifecycleOwner, event);
    }
}
