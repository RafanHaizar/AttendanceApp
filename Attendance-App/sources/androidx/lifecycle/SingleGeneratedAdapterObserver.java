package androidx.lifecycle;

import androidx.lifecycle.Lifecycle;

class SingleGeneratedAdapterObserver implements LifecycleEventObserver {
    private final GeneratedAdapter mGeneratedAdapter;

    SingleGeneratedAdapterObserver(GeneratedAdapter generatedAdapter) {
        this.mGeneratedAdapter = generatedAdapter;
    }

    public void onStateChanged(LifecycleOwner source, Lifecycle.Event event) {
        this.mGeneratedAdapter.callMethods(source, event, false, (MethodCallsLogger) null);
        this.mGeneratedAdapter.callMethods(source, event, true, (MethodCallsLogger) null);
    }
}
