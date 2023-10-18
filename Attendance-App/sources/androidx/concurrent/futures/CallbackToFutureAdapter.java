package androidx.concurrent.futures;

import com.google.common.util.concurrent.ListenableFuture;
import java.lang.ref.WeakReference;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public final class CallbackToFutureAdapter {

    public interface Resolver<T> {
        Object attachCompleter(Completer<T> completer) throws Exception;
    }

    private CallbackToFutureAdapter() {
    }

    public static <T> ListenableFuture<T> getFuture(Resolver<T> callback) {
        Completer<T> completer = new Completer<>();
        SafeFuture<T> safeFuture = new SafeFuture<>(completer);
        completer.future = safeFuture;
        completer.tag = callback.getClass();
        try {
            Object tag = callback.attachCompleter(completer);
            if (tag != null) {
                completer.tag = tag;
            }
        } catch (Exception e) {
            safeFuture.setException(e);
        }
        return safeFuture;
    }

    private static final class SafeFuture<T> implements ListenableFuture<T> {
        final WeakReference<Completer<T>> completerWeakReference;
        private final AbstractResolvableFuture<T> delegate = new AbstractResolvableFuture<T>() {
            /* access modifiers changed from: protected */
            public String pendingToString() {
                Completer<T> completer = (Completer) SafeFuture.this.completerWeakReference.get();
                if (completer == null) {
                    return "Completer object has been garbage collected, future will fail soon";
                }
                return "tag=[" + completer.tag + "]";
            }
        };

        SafeFuture(Completer<T> completer) {
            this.completerWeakReference = new WeakReference<>(completer);
        }

        public boolean cancel(boolean mayInterruptIfRunning) {
            Completer<T> completer = (Completer) this.completerWeakReference.get();
            boolean cancelled = this.delegate.cancel(mayInterruptIfRunning);
            if (cancelled && completer != null) {
                completer.fireCancellationListeners();
            }
            return cancelled;
        }

        /* access modifiers changed from: package-private */
        public boolean cancelWithoutNotifyingCompleter(boolean shouldInterrupt) {
            return this.delegate.cancel(shouldInterrupt);
        }

        /* access modifiers changed from: package-private */
        public boolean set(T value) {
            return this.delegate.set(value);
        }

        /* access modifiers changed from: package-private */
        public boolean setException(Throwable t) {
            return this.delegate.setException(t);
        }

        public boolean isCancelled() {
            return this.delegate.isCancelled();
        }

        public boolean isDone() {
            return this.delegate.isDone();
        }

        public T get() throws InterruptedException, ExecutionException {
            return this.delegate.get();
        }

        public T get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
            return this.delegate.get(timeout, unit);
        }

        public void addListener(Runnable listener, Executor executor) {
            this.delegate.addListener(listener, executor);
        }

        public String toString() {
            return this.delegate.toString();
        }
    }

    public static final class Completer<T> {
        private boolean attemptedSetting;
        private ResolvableFuture<Void> cancellationFuture = ResolvableFuture.create();
        SafeFuture<T> future;
        Object tag;

        Completer() {
        }

        public boolean set(T value) {
            boolean wasSet = true;
            this.attemptedSetting = true;
            SafeFuture<T> localFuture = this.future;
            if (localFuture == null || !localFuture.set(value)) {
                wasSet = false;
            }
            if (wasSet) {
                setCompletedNormally();
            }
            return wasSet;
        }

        public boolean setException(Throwable t) {
            boolean wasSet = true;
            this.attemptedSetting = true;
            SafeFuture<T> localFuture = this.future;
            if (localFuture == null || !localFuture.setException(t)) {
                wasSet = false;
            }
            if (wasSet) {
                setCompletedNormally();
            }
            return wasSet;
        }

        public boolean setCancelled() {
            boolean wasSet = true;
            this.attemptedSetting = true;
            SafeFuture<T> localFuture = this.future;
            if (localFuture == null || !localFuture.cancelWithoutNotifyingCompleter(true)) {
                wasSet = false;
            }
            if (wasSet) {
                setCompletedNormally();
            }
            return wasSet;
        }

        public void addCancellationListener(Runnable runnable, Executor executor) {
            ListenableFuture<?> localCancellationFuture = this.cancellationFuture;
            if (localCancellationFuture != null) {
                localCancellationFuture.addListener(runnable, executor);
            }
        }

        /* access modifiers changed from: package-private */
        public void fireCancellationListeners() {
            this.tag = null;
            this.future = null;
            this.cancellationFuture.set(null);
        }

        private void setCompletedNormally() {
            this.tag = null;
            this.future = null;
            this.cancellationFuture = null;
        }

        /* access modifiers changed from: protected */
        public void finalize() {
            ResolvableFuture<Void> localCancellationFuture;
            SafeFuture<T> localFuture = this.future;
            if (localFuture != null && !localFuture.isDone()) {
                localFuture.setException(new FutureGarbageCollectedException("The completer object was garbage collected - this future would otherwise never complete. The tag was: " + this.tag));
            }
            if (!this.attemptedSetting && (localCancellationFuture = this.cancellationFuture) != null) {
                localCancellationFuture.set(null);
            }
        }
    }

    static final class FutureGarbageCollectedException extends Throwable {
        FutureGarbageCollectedException(String message) {
            super(message);
        }

        public synchronized Throwable fillInStackTrace() {
            return this;
        }
    }
}
