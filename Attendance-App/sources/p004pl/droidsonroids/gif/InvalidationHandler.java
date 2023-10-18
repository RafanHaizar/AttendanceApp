package p004pl.droidsonroids.gif;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import java.lang.ref.WeakReference;
import java.util.Iterator;

/* renamed from: pl.droidsonroids.gif.InvalidationHandler */
class InvalidationHandler extends Handler {
    static final int MSG_TYPE_INVALIDATION = -1;
    private final WeakReference<GifDrawable> mDrawableRef;

    InvalidationHandler(GifDrawable gifDrawable) {
        super(Looper.getMainLooper());
        this.mDrawableRef = new WeakReference<>(gifDrawable);
    }

    public void handleMessage(Message msg) {
        GifDrawable gifDrawable = (GifDrawable) this.mDrawableRef.get();
        if (gifDrawable != null) {
            if (msg.what == -1) {
                gifDrawable.invalidateSelf();
                return;
            }
            Iterator<AnimationListener> it = gifDrawable.mListeners.iterator();
            while (it.hasNext()) {
                it.next().onAnimationCompleted(msg.what);
            }
        }
    }
}
