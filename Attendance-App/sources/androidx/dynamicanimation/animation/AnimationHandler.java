package androidx.dynamicanimation.animation;

import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.view.Choreographer;
import androidx.collection.SimpleArrayMap;
import java.util.ArrayList;

class AnimationHandler {
    private static final long FRAME_DELAY_MS = 10;
    public static final ThreadLocal<AnimationHandler> sAnimatorHandler = new ThreadLocal<>();
    final ArrayList<AnimationFrameCallback> mAnimationCallbacks = new ArrayList<>();
    private final AnimationCallbackDispatcher mCallbackDispatcher = new AnimationCallbackDispatcher();
    long mCurrentFrameTime = 0;
    private final SimpleArrayMap<AnimationFrameCallback, Long> mDelayedCallbackStartTime = new SimpleArrayMap<>();
    private boolean mListDirty = false;
    private AnimationFrameCallbackProvider mProvider;

    interface AnimationFrameCallback {
        boolean doAnimationFrame(long j);
    }

    AnimationHandler() {
    }

    class AnimationCallbackDispatcher {
        AnimationCallbackDispatcher() {
        }

        /* access modifiers changed from: package-private */
        public void dispatchAnimationFrame() {
            AnimationHandler.this.mCurrentFrameTime = SystemClock.uptimeMillis();
            AnimationHandler animationHandler = AnimationHandler.this;
            animationHandler.doAnimationFrame(animationHandler.mCurrentFrameTime);
            if (AnimationHandler.this.mAnimationCallbacks.size() > 0) {
                AnimationHandler.this.getProvider().postFrameCallback();
            }
        }
    }

    public static AnimationHandler getInstance() {
        ThreadLocal<AnimationHandler> threadLocal = sAnimatorHandler;
        if (threadLocal.get() == null) {
            threadLocal.set(new AnimationHandler());
        }
        return threadLocal.get();
    }

    public static long getFrameTime() {
        ThreadLocal<AnimationHandler> threadLocal = sAnimatorHandler;
        if (threadLocal.get() == null) {
            return 0;
        }
        return threadLocal.get().mCurrentFrameTime;
    }

    public void setProvider(AnimationFrameCallbackProvider provider) {
        this.mProvider = provider;
    }

    /* access modifiers changed from: package-private */
    public AnimationFrameCallbackProvider getProvider() {
        if (this.mProvider == null) {
            this.mProvider = new FrameCallbackProvider16(this.mCallbackDispatcher);
        }
        return this.mProvider;
    }

    public void addAnimationFrameCallback(AnimationFrameCallback callback, long delay) {
        if (this.mAnimationCallbacks.size() == 0) {
            getProvider().postFrameCallback();
        }
        if (!this.mAnimationCallbacks.contains(callback)) {
            this.mAnimationCallbacks.add(callback);
        }
        if (delay > 0) {
            this.mDelayedCallbackStartTime.put(callback, Long.valueOf(SystemClock.uptimeMillis() + delay));
        }
    }

    public void removeCallback(AnimationFrameCallback callback) {
        this.mDelayedCallbackStartTime.remove(callback);
        int id = this.mAnimationCallbacks.indexOf(callback);
        if (id >= 0) {
            this.mAnimationCallbacks.set(id, (Object) null);
            this.mListDirty = true;
        }
    }

    /* access modifiers changed from: package-private */
    public void doAnimationFrame(long frameTime) {
        long currentTime = SystemClock.uptimeMillis();
        for (int i = 0; i < this.mAnimationCallbacks.size(); i++) {
            AnimationFrameCallback callback = this.mAnimationCallbacks.get(i);
            if (callback != null && isCallbackDue(callback, currentTime)) {
                callback.doAnimationFrame(frameTime);
            }
        }
        cleanUpList();
    }

    private boolean isCallbackDue(AnimationFrameCallback callback, long currentTime) {
        Long startTime = this.mDelayedCallbackStartTime.get(callback);
        if (startTime == null) {
            return true;
        }
        if (startTime.longValue() >= currentTime) {
            return false;
        }
        this.mDelayedCallbackStartTime.remove(callback);
        return true;
    }

    private void cleanUpList() {
        if (this.mListDirty) {
            for (int i = this.mAnimationCallbacks.size() - 1; i >= 0; i--) {
                if (this.mAnimationCallbacks.get(i) == null) {
                    this.mAnimationCallbacks.remove(i);
                }
            }
            this.mListDirty = false;
        }
    }

    private static class FrameCallbackProvider16 extends AnimationFrameCallbackProvider {
        private final Choreographer mChoreographer = Choreographer.getInstance();
        private final Choreographer.FrameCallback mChoreographerCallback = new Choreographer.FrameCallback() {
            public void doFrame(long frameTimeNanos) {
                FrameCallbackProvider16.this.mDispatcher.dispatchAnimationFrame();
            }
        };

        FrameCallbackProvider16(AnimationCallbackDispatcher dispatcher) {
            super(dispatcher);
        }

        /* access modifiers changed from: package-private */
        public void postFrameCallback() {
            this.mChoreographer.postFrameCallback(this.mChoreographerCallback);
        }
    }

    private static class FrameCallbackProvider14 extends AnimationFrameCallbackProvider {
        private final Handler mHandler = new Handler(Looper.myLooper());
        long mLastFrameTime = -1;
        private final Runnable mRunnable = new Runnable() {
            public void run() {
                FrameCallbackProvider14.this.mLastFrameTime = SystemClock.uptimeMillis();
                FrameCallbackProvider14.this.mDispatcher.dispatchAnimationFrame();
            }
        };

        FrameCallbackProvider14(AnimationCallbackDispatcher dispatcher) {
            super(dispatcher);
        }

        /* access modifiers changed from: package-private */
        public void postFrameCallback() {
            this.mHandler.postDelayed(this.mRunnable, Math.max(AnimationHandler.FRAME_DELAY_MS - (SystemClock.uptimeMillis() - this.mLastFrameTime), 0));
        }
    }

    static abstract class AnimationFrameCallbackProvider {
        final AnimationCallbackDispatcher mDispatcher;

        /* access modifiers changed from: package-private */
        public abstract void postFrameCallback();

        AnimationFrameCallbackProvider(AnimationCallbackDispatcher dispatcher) {
            this.mDispatcher = dispatcher;
        }
    }
}
