package androidx.activity;

import android.window.OnBackInvokedCallback;
import android.window.OnBackInvokedDispatcher;
import androidx.core.p001os.BuildCompat;
import androidx.core.util.Consumer;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;
import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.Objects;

public final class OnBackPressedDispatcher {
    private boolean mBackInvokedCallbackRegistered;
    private Consumer<Boolean> mEnabledConsumer;
    private final Runnable mFallbackOnBackPressed;
    private OnBackInvokedDispatcher mInvokedDispatcher;
    private OnBackInvokedCallback mOnBackInvokedCallback;
    final ArrayDeque<OnBackPressedCallback> mOnBackPressedCallbacks;

    public void setOnBackInvokedDispatcher(OnBackInvokedDispatcher invoker) {
        this.mInvokedDispatcher = invoker;
        updateBackInvokedCallbackState();
    }

    /* access modifiers changed from: package-private */
    public void updateBackInvokedCallbackState() {
        boolean shouldBeRegistered = hasEnabledCallbacks();
        OnBackInvokedDispatcher onBackInvokedDispatcher = this.mInvokedDispatcher;
        if (onBackInvokedDispatcher == null) {
            return;
        }
        if (shouldBeRegistered && !this.mBackInvokedCallbackRegistered) {
            Api33Impl.registerOnBackInvokedCallback(onBackInvokedDispatcher, 0, this.mOnBackInvokedCallback);
            this.mBackInvokedCallbackRegistered = true;
        } else if (!shouldBeRegistered && this.mBackInvokedCallbackRegistered) {
            Api33Impl.unregisterOnBackInvokedCallback(onBackInvokedDispatcher, this.mOnBackInvokedCallback);
            this.mBackInvokedCallbackRegistered = false;
        }
    }

    public OnBackPressedDispatcher() {
        this((Runnable) null);
    }

    public OnBackPressedDispatcher(Runnable fallbackOnBackPressed) {
        this.mOnBackPressedCallbacks = new ArrayDeque<>();
        this.mBackInvokedCallbackRegistered = false;
        this.mFallbackOnBackPressed = fallbackOnBackPressed;
        if (BuildCompat.isAtLeastT()) {
            this.mEnabledConsumer = new OnBackPressedDispatcher$$ExternalSyntheticLambda0(this);
            this.mOnBackInvokedCallback = Api33Impl.createOnBackInvokedCallback(new OnBackPressedDispatcher$$ExternalSyntheticLambda1(this));
        }
    }

    /* access modifiers changed from: package-private */
    /* renamed from: lambda$new$0$androidx-activity-OnBackPressedDispatcher  reason: not valid java name */
    public /* synthetic */ void m1310lambda$new$0$androidxactivityOnBackPressedDispatcher(Boolean aBoolean) {
        if (BuildCompat.isAtLeastT()) {
            updateBackInvokedCallbackState();
        }
    }

    public void addCallback(OnBackPressedCallback onBackPressedCallback) {
        addCancellableCallback(onBackPressedCallback);
    }

    /* access modifiers changed from: package-private */
    public Cancellable addCancellableCallback(OnBackPressedCallback onBackPressedCallback) {
        this.mOnBackPressedCallbacks.add(onBackPressedCallback);
        OnBackPressedCancellable cancellable = new OnBackPressedCancellable(onBackPressedCallback);
        onBackPressedCallback.addCancellable(cancellable);
        if (BuildCompat.isAtLeastT()) {
            updateBackInvokedCallbackState();
            onBackPressedCallback.setIsEnabledConsumer(this.mEnabledConsumer);
        }
        return cancellable;
    }

    public void addCallback(LifecycleOwner owner, OnBackPressedCallback onBackPressedCallback) {
        Lifecycle lifecycle = owner.getLifecycle();
        if (lifecycle.getCurrentState() != Lifecycle.State.DESTROYED) {
            onBackPressedCallback.addCancellable(new LifecycleOnBackPressedCancellable(lifecycle, onBackPressedCallback));
            if (BuildCompat.isAtLeastT()) {
                updateBackInvokedCallbackState();
                onBackPressedCallback.setIsEnabledConsumer(this.mEnabledConsumer);
            }
        }
    }

    public boolean hasEnabledCallbacks() {
        Iterator<OnBackPressedCallback> iterator = this.mOnBackPressedCallbacks.descendingIterator();
        while (iterator.hasNext()) {
            if (iterator.next().isEnabled()) {
                return true;
            }
        }
        return false;
    }

    public void onBackPressed() {
        Iterator<OnBackPressedCallback> iterator = this.mOnBackPressedCallbacks.descendingIterator();
        while (iterator.hasNext()) {
            OnBackPressedCallback callback = iterator.next();
            if (callback.isEnabled()) {
                callback.handleOnBackPressed();
                return;
            }
        }
        Runnable runnable = this.mFallbackOnBackPressed;
        if (runnable != null) {
            runnable.run();
        }
    }

    private class OnBackPressedCancellable implements Cancellable {
        private final OnBackPressedCallback mOnBackPressedCallback;

        OnBackPressedCancellable(OnBackPressedCallback onBackPressedCallback) {
            this.mOnBackPressedCallback = onBackPressedCallback;
        }

        public void cancel() {
            OnBackPressedDispatcher.this.mOnBackPressedCallbacks.remove(this.mOnBackPressedCallback);
            this.mOnBackPressedCallback.removeCancellable(this);
            if (BuildCompat.isAtLeastT()) {
                this.mOnBackPressedCallback.setIsEnabledConsumer((Consumer<Boolean>) null);
                OnBackPressedDispatcher.this.updateBackInvokedCallbackState();
            }
        }
    }

    private class LifecycleOnBackPressedCancellable implements LifecycleEventObserver, Cancellable {
        private Cancellable mCurrentCancellable;
        private final Lifecycle mLifecycle;
        private final OnBackPressedCallback mOnBackPressedCallback;

        LifecycleOnBackPressedCancellable(Lifecycle lifecycle, OnBackPressedCallback onBackPressedCallback) {
            this.mLifecycle = lifecycle;
            this.mOnBackPressedCallback = onBackPressedCallback;
            lifecycle.addObserver(this);
        }

        public void onStateChanged(LifecycleOwner source, Lifecycle.Event event) {
            if (event == Lifecycle.Event.ON_START) {
                this.mCurrentCancellable = OnBackPressedDispatcher.this.addCancellableCallback(this.mOnBackPressedCallback);
            } else if (event == Lifecycle.Event.ON_STOP) {
                Cancellable cancellable = this.mCurrentCancellable;
                if (cancellable != null) {
                    cancellable.cancel();
                }
            } else if (event == Lifecycle.Event.ON_DESTROY) {
                cancel();
            }
        }

        public void cancel() {
            this.mLifecycle.removeObserver(this);
            this.mOnBackPressedCallback.removeCancellable(this);
            Cancellable cancellable = this.mCurrentCancellable;
            if (cancellable != null) {
                cancellable.cancel();
                this.mCurrentCancellable = null;
            }
        }
    }

    static class Api33Impl {
        private Api33Impl() {
        }

        static void registerOnBackInvokedCallback(Object dispatcher, int priority, Object callback) {
            ((OnBackInvokedDispatcher) dispatcher).registerOnBackInvokedCallback(priority, (OnBackInvokedCallback) callback);
        }

        static void unregisterOnBackInvokedCallback(Object dispatcher, Object callback) {
            ((OnBackInvokedDispatcher) dispatcher).unregisterOnBackInvokedCallback((OnBackInvokedCallback) callback);
        }

        static OnBackInvokedCallback createOnBackInvokedCallback(Runnable runnable) {
            Objects.requireNonNull(runnable);
            return new OnBackPressedDispatcher$Api33Impl$$ExternalSyntheticLambda0(runnable);
        }
    }
}
