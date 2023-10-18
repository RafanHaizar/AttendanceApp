package androidx.concurrent.futures;

import com.google.common.util.concurrent.ListenableFuture;

public final class ResolvableFuture<V> extends AbstractResolvableFuture<V> {
    public static <V> ResolvableFuture<V> create() {
        return new ResolvableFuture<>();
    }

    public boolean set(V value) {
        return super.set(value);
    }

    public boolean setException(Throwable throwable) {
        return super.setException(throwable);
    }

    public boolean setFuture(ListenableFuture<? extends V> future) {
        return super.setFuture(future);
    }

    private ResolvableFuture() {
    }
}
