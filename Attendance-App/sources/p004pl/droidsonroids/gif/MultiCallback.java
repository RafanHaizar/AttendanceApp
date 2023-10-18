package p004pl.droidsonroids.gif;

import android.graphics.drawable.Drawable;
import android.view.View;
import java.lang.ref.WeakReference;
import java.util.concurrent.CopyOnWriteArrayList;

/* renamed from: pl.droidsonroids.gif.MultiCallback */
public class MultiCallback implements Drawable.Callback {
    private final CopyOnWriteArrayList<CallbackWeakReference> mCallbacks;
    private final boolean mUseViewInvalidate;

    public MultiCallback() {
        this(false);
    }

    public MultiCallback(boolean useViewInvalidate) {
        this.mCallbacks = new CopyOnWriteArrayList<>();
        this.mUseViewInvalidate = useViewInvalidate;
    }

    public void invalidateDrawable(Drawable who) {
        for (int i = 0; i < this.mCallbacks.size(); i++) {
            CallbackWeakReference reference = this.mCallbacks.get(i);
            Drawable.Callback callback = (Drawable.Callback) reference.get();
            if (callback == null) {
                this.mCallbacks.remove(reference);
            } else if (!this.mUseViewInvalidate || !(callback instanceof View)) {
                callback.invalidateDrawable(who);
            } else {
                ((View) callback).invalidate();
            }
        }
    }

    public void scheduleDrawable(Drawable who, Runnable what, long when) {
        for (int i = 0; i < this.mCallbacks.size(); i++) {
            CallbackWeakReference reference = this.mCallbacks.get(i);
            Drawable.Callback callback = (Drawable.Callback) reference.get();
            if (callback != null) {
                callback.scheduleDrawable(who, what, when);
            } else {
                this.mCallbacks.remove(reference);
            }
        }
    }

    public void unscheduleDrawable(Drawable who, Runnable what) {
        for (int i = 0; i < this.mCallbacks.size(); i++) {
            CallbackWeakReference reference = this.mCallbacks.get(i);
            Drawable.Callback callback = (Drawable.Callback) reference.get();
            if (callback != null) {
                callback.unscheduleDrawable(who, what);
            } else {
                this.mCallbacks.remove(reference);
            }
        }
    }

    public void addView(Drawable.Callback callback) {
        for (int i = 0; i < this.mCallbacks.size(); i++) {
            CallbackWeakReference reference = this.mCallbacks.get(i);
            if (((Drawable.Callback) reference.get()) == null) {
                this.mCallbacks.remove(reference);
            }
        }
        this.mCallbacks.addIfAbsent(new CallbackWeakReference(callback));
    }

    public void removeView(Drawable.Callback callback) {
        for (int i = 0; i < this.mCallbacks.size(); i++) {
            CallbackWeakReference reference = this.mCallbacks.get(i);
            Drawable.Callback item = (Drawable.Callback) reference.get();
            if (item == null || item == callback) {
                this.mCallbacks.remove(reference);
            }
        }
    }

    /* renamed from: pl.droidsonroids.gif.MultiCallback$CallbackWeakReference */
    static final class CallbackWeakReference extends WeakReference<Drawable.Callback> {
        CallbackWeakReference(Drawable.Callback r) {
            super(r);
        }

        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o != null && getClass() == o.getClass() && get() == ((CallbackWeakReference) o).get()) {
                return true;
            }
            return false;
        }

        public int hashCode() {
            Drawable.Callback callback = (Drawable.Callback) get();
            if (callback != null) {
                return callback.hashCode();
            }
            return 0;
        }
    }
}
