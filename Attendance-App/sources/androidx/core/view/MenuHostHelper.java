package androidx.core.view;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

public class MenuHostHelper {
    private final CopyOnWriteArrayList<MenuProvider> mMenuProviders = new CopyOnWriteArrayList<>();
    private final Runnable mOnInvalidateMenuCallback;
    private final Map<MenuProvider, LifecycleContainer> mProviderToLifecycleContainers = new HashMap();

    public MenuHostHelper(Runnable onInvalidateMenuCallback) {
        this.mOnInvalidateMenuCallback = onInvalidateMenuCallback;
    }

    public void onPrepareMenu(Menu menu) {
        Iterator<MenuProvider> it = this.mMenuProviders.iterator();
        while (it.hasNext()) {
            it.next().onPrepareMenu(menu);
        }
    }

    public void onCreateMenu(Menu menu, MenuInflater menuInflater) {
        Iterator<MenuProvider> it = this.mMenuProviders.iterator();
        while (it.hasNext()) {
            it.next().onCreateMenu(menu, menuInflater);
        }
    }

    public boolean onMenuItemSelected(MenuItem item) {
        Iterator<MenuProvider> it = this.mMenuProviders.iterator();
        while (it.hasNext()) {
            if (it.next().onMenuItemSelected(item)) {
                return true;
            }
        }
        return false;
    }

    public void onMenuClosed(Menu menu) {
        Iterator<MenuProvider> it = this.mMenuProviders.iterator();
        while (it.hasNext()) {
            it.next().onMenuClosed(menu);
        }
    }

    public void addMenuProvider(MenuProvider provider) {
        this.mMenuProviders.add(provider);
        this.mOnInvalidateMenuCallback.run();
    }

    public void addMenuProvider(MenuProvider provider, LifecycleOwner owner) {
        addMenuProvider(provider);
        Lifecycle lifecycle = owner.getLifecycle();
        LifecycleContainer lifecycleContainer = this.mProviderToLifecycleContainers.remove(provider);
        if (lifecycleContainer != null) {
            lifecycleContainer.clearObservers();
        }
        this.mProviderToLifecycleContainers.put(provider, new LifecycleContainer(lifecycle, new MenuHostHelper$$ExternalSyntheticLambda0(this, provider)));
    }

    /* access modifiers changed from: package-private */
    /* renamed from: lambda$addMenuProvider$0$androidx-core-view-MenuHostHelper  reason: not valid java name */
    public /* synthetic */ void m1312lambda$addMenuProvider$0$androidxcoreviewMenuHostHelper(MenuProvider provider, LifecycleOwner source, Lifecycle.Event event) {
        if (event == Lifecycle.Event.ON_DESTROY) {
            removeMenuProvider(provider);
        }
    }

    public void addMenuProvider(MenuProvider provider, LifecycleOwner owner, Lifecycle.State state) {
        Lifecycle lifecycle = owner.getLifecycle();
        LifecycleContainer lifecycleContainer = this.mProviderToLifecycleContainers.remove(provider);
        if (lifecycleContainer != null) {
            lifecycleContainer.clearObservers();
        }
        this.mProviderToLifecycleContainers.put(provider, new LifecycleContainer(lifecycle, new MenuHostHelper$$ExternalSyntheticLambda1(this, state, provider)));
    }

    /* access modifiers changed from: package-private */
    /* renamed from: lambda$addMenuProvider$1$androidx-core-view-MenuHostHelper  reason: not valid java name */
    public /* synthetic */ void m1313lambda$addMenuProvider$1$androidxcoreviewMenuHostHelper(Lifecycle.State state, MenuProvider provider, LifecycleOwner source, Lifecycle.Event event) {
        if (event == Lifecycle.Event.upTo(state)) {
            addMenuProvider(provider);
        } else if (event == Lifecycle.Event.ON_DESTROY) {
            removeMenuProvider(provider);
        } else if (event == Lifecycle.Event.downFrom(state)) {
            this.mMenuProviders.remove(provider);
            this.mOnInvalidateMenuCallback.run();
        }
    }

    public void removeMenuProvider(MenuProvider provider) {
        this.mMenuProviders.remove(provider);
        LifecycleContainer lifecycleContainer = this.mProviderToLifecycleContainers.remove(provider);
        if (lifecycleContainer != null) {
            lifecycleContainer.clearObservers();
        }
        this.mOnInvalidateMenuCallback.run();
    }

    private static class LifecycleContainer {
        final Lifecycle mLifecycle;
        private LifecycleEventObserver mObserver;

        LifecycleContainer(Lifecycle lifecycle, LifecycleEventObserver observer) {
            this.mLifecycle = lifecycle;
            this.mObserver = observer;
            lifecycle.addObserver(observer);
        }

        /* access modifiers changed from: package-private */
        public void clearObservers() {
            this.mLifecycle.removeObserver(this.mObserver);
            this.mObserver = null;
        }
    }
}
