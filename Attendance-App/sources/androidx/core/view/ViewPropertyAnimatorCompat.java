package androidx.core.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.graphics.Paint;
import android.os.Build;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.view.animation.Interpolator;
import java.lang.ref.WeakReference;

public final class ViewPropertyAnimatorCompat {
    static final int LISTENER_TAG_ID = 2113929216;
    Runnable mEndAction = null;
    int mOldLayerType = -1;
    Runnable mStartAction = null;
    private final WeakReference<View> mView;

    ViewPropertyAnimatorCompat(View view) {
        this.mView = new WeakReference<>(view);
    }

    static class ViewPropertyAnimatorListenerApi14 implements ViewPropertyAnimatorListener {
        boolean mAnimEndCalled;
        ViewPropertyAnimatorCompat mVpa;

        ViewPropertyAnimatorListenerApi14(ViewPropertyAnimatorCompat vpa) {
            this.mVpa = vpa;
        }

        public void onAnimationStart(View view) {
            this.mAnimEndCalled = false;
            if (this.mVpa.mOldLayerType > -1) {
                view.setLayerType(2, (Paint) null);
            }
            if (this.mVpa.mStartAction != null) {
                Runnable startAction = this.mVpa.mStartAction;
                this.mVpa.mStartAction = null;
                startAction.run();
            }
            Object listenerTag = view.getTag(ViewPropertyAnimatorCompat.LISTENER_TAG_ID);
            ViewPropertyAnimatorListener listener = null;
            if (listenerTag instanceof ViewPropertyAnimatorListener) {
                listener = (ViewPropertyAnimatorListener) listenerTag;
            }
            if (listener != null) {
                listener.onAnimationStart(view);
            }
        }

        public void onAnimationEnd(View view) {
            if (this.mVpa.mOldLayerType > -1) {
                view.setLayerType(this.mVpa.mOldLayerType, (Paint) null);
                this.mVpa.mOldLayerType = -1;
            }
            if (this.mVpa.mEndAction != null) {
                Runnable endAction = this.mVpa.mEndAction;
                this.mVpa.mEndAction = null;
                endAction.run();
            }
            Object listenerTag = view.getTag(ViewPropertyAnimatorCompat.LISTENER_TAG_ID);
            ViewPropertyAnimatorListener listener = null;
            if (listenerTag instanceof ViewPropertyAnimatorListener) {
                listener = (ViewPropertyAnimatorListener) listenerTag;
            }
            if (listener != null) {
                listener.onAnimationEnd(view);
            }
            this.mAnimEndCalled = true;
        }

        public void onAnimationCancel(View view) {
            Object listenerTag = view.getTag(ViewPropertyAnimatorCompat.LISTENER_TAG_ID);
            ViewPropertyAnimatorListener listener = null;
            if (listenerTag instanceof ViewPropertyAnimatorListener) {
                listener = (ViewPropertyAnimatorListener) listenerTag;
            }
            if (listener != null) {
                listener.onAnimationCancel(view);
            }
        }
    }

    public ViewPropertyAnimatorCompat setDuration(long value) {
        View view = (View) this.mView.get();
        View view2 = view;
        if (view != null) {
            view2.animate().setDuration(value);
        }
        return this;
    }

    public ViewPropertyAnimatorCompat alpha(float value) {
        View view = (View) this.mView.get();
        View view2 = view;
        if (view != null) {
            view2.animate().alpha(value);
        }
        return this;
    }

    public ViewPropertyAnimatorCompat alphaBy(float value) {
        View view = (View) this.mView.get();
        View view2 = view;
        if (view != null) {
            view2.animate().alphaBy(value);
        }
        return this;
    }

    public ViewPropertyAnimatorCompat translationX(float value) {
        View view = (View) this.mView.get();
        View view2 = view;
        if (view != null) {
            view2.animate().translationX(value);
        }
        return this;
    }

    public ViewPropertyAnimatorCompat translationY(float value) {
        View view = (View) this.mView.get();
        View view2 = view;
        if (view != null) {
            view2.animate().translationY(value);
        }
        return this;
    }

    public ViewPropertyAnimatorCompat withEndAction(Runnable runnable) {
        View view = (View) this.mView.get();
        View view2 = view;
        if (view != null) {
            Api16Impl.withEndAction(view2.animate(), runnable);
        }
        return this;
    }

    public long getDuration() {
        View view = (View) this.mView.get();
        View view2 = view;
        if (view != null) {
            return view2.animate().getDuration();
        }
        return 0;
    }

    public ViewPropertyAnimatorCompat setInterpolator(Interpolator value) {
        View view = (View) this.mView.get();
        View view2 = view;
        if (view != null) {
            view2.animate().setInterpolator(value);
        }
        return this;
    }

    public Interpolator getInterpolator() {
        View view = (View) this.mView.get();
        View view2 = view;
        if (view != null) {
            return Api18Impl.getInterpolator(view2.animate());
        }
        return null;
    }

    public ViewPropertyAnimatorCompat setStartDelay(long value) {
        View view = (View) this.mView.get();
        View view2 = view;
        if (view != null) {
            view2.animate().setStartDelay(value);
        }
        return this;
    }

    public long getStartDelay() {
        View view = (View) this.mView.get();
        View view2 = view;
        if (view != null) {
            return view2.animate().getStartDelay();
        }
        return 0;
    }

    public ViewPropertyAnimatorCompat rotation(float value) {
        View view = (View) this.mView.get();
        View view2 = view;
        if (view != null) {
            view2.animate().rotation(value);
        }
        return this;
    }

    public ViewPropertyAnimatorCompat rotationBy(float value) {
        View view = (View) this.mView.get();
        View view2 = view;
        if (view != null) {
            view2.animate().rotationBy(value);
        }
        return this;
    }

    public ViewPropertyAnimatorCompat rotationX(float value) {
        View view = (View) this.mView.get();
        View view2 = view;
        if (view != null) {
            view2.animate().rotationX(value);
        }
        return this;
    }

    public ViewPropertyAnimatorCompat rotationXBy(float value) {
        View view = (View) this.mView.get();
        View view2 = view;
        if (view != null) {
            view2.animate().rotationXBy(value);
        }
        return this;
    }

    public ViewPropertyAnimatorCompat rotationY(float value) {
        View view = (View) this.mView.get();
        View view2 = view;
        if (view != null) {
            view2.animate().rotationY(value);
        }
        return this;
    }

    public ViewPropertyAnimatorCompat rotationYBy(float value) {
        View view = (View) this.mView.get();
        View view2 = view;
        if (view != null) {
            view2.animate().rotationYBy(value);
        }
        return this;
    }

    public ViewPropertyAnimatorCompat scaleX(float value) {
        View view = (View) this.mView.get();
        View view2 = view;
        if (view != null) {
            view2.animate().scaleX(value);
        }
        return this;
    }

    public ViewPropertyAnimatorCompat scaleXBy(float value) {
        View view = (View) this.mView.get();
        View view2 = view;
        if (view != null) {
            view2.animate().scaleXBy(value);
        }
        return this;
    }

    public ViewPropertyAnimatorCompat scaleY(float value) {
        View view = (View) this.mView.get();
        View view2 = view;
        if (view != null) {
            view2.animate().scaleY(value);
        }
        return this;
    }

    public ViewPropertyAnimatorCompat scaleYBy(float value) {
        View view = (View) this.mView.get();
        View view2 = view;
        if (view != null) {
            view2.animate().scaleYBy(value);
        }
        return this;
    }

    public void cancel() {
        View view = (View) this.mView.get();
        View view2 = view;
        if (view != null) {
            view2.animate().cancel();
        }
    }

    /* renamed from: x */
    public ViewPropertyAnimatorCompat mo16357x(float value) {
        View view = (View) this.mView.get();
        View view2 = view;
        if (view != null) {
            view2.animate().x(value);
        }
        return this;
    }

    public ViewPropertyAnimatorCompat xBy(float value) {
        View view = (View) this.mView.get();
        View view2 = view;
        if (view != null) {
            view2.animate().xBy(value);
        }
        return this;
    }

    /* renamed from: y */
    public ViewPropertyAnimatorCompat mo16359y(float value) {
        View view = (View) this.mView.get();
        View view2 = view;
        if (view != null) {
            view2.animate().y(value);
        }
        return this;
    }

    public ViewPropertyAnimatorCompat yBy(float value) {
        View view = (View) this.mView.get();
        View view2 = view;
        if (view != null) {
            view2.animate().yBy(value);
        }
        return this;
    }

    public ViewPropertyAnimatorCompat translationXBy(float value) {
        View view = (View) this.mView.get();
        View view2 = view;
        if (view != null) {
            view2.animate().translationXBy(value);
        }
        return this;
    }

    public ViewPropertyAnimatorCompat translationYBy(float value) {
        View view = (View) this.mView.get();
        View view2 = view;
        if (view != null) {
            view2.animate().translationYBy(value);
        }
        return this;
    }

    public ViewPropertyAnimatorCompat translationZBy(float value) {
        View view = (View) this.mView.get();
        View view2 = view;
        if (view != null && Build.VERSION.SDK_INT >= 21) {
            Api21Impl.translationZBy(view2.animate(), value);
        }
        return this;
    }

    public ViewPropertyAnimatorCompat translationZ(float value) {
        View view = (View) this.mView.get();
        View view2 = view;
        if (view != null && Build.VERSION.SDK_INT >= 21) {
            Api21Impl.translationZ(view2.animate(), value);
        }
        return this;
    }

    /* renamed from: z */
    public ViewPropertyAnimatorCompat mo16361z(float value) {
        View view = (View) this.mView.get();
        View view2 = view;
        if (view != null && Build.VERSION.SDK_INT >= 21) {
            Api21Impl.m212z(view2.animate(), value);
        }
        return this;
    }

    public ViewPropertyAnimatorCompat zBy(float value) {
        View view = (View) this.mView.get();
        View view2 = view;
        if (view != null && Build.VERSION.SDK_INT >= 21) {
            Api21Impl.zBy(view2.animate(), value);
        }
        return this;
    }

    public void start() {
        View view = (View) this.mView.get();
        View view2 = view;
        if (view != null) {
            view2.animate().start();
        }
    }

    public ViewPropertyAnimatorCompat withLayer() {
        View view = (View) this.mView.get();
        View view2 = view;
        if (view != null) {
            Api16Impl.withLayer(view2.animate());
        }
        return this;
    }

    public ViewPropertyAnimatorCompat withStartAction(Runnable runnable) {
        View view = (View) this.mView.get();
        View view2 = view;
        if (view != null) {
            Api16Impl.withStartAction(view2.animate(), runnable);
        }
        return this;
    }

    public ViewPropertyAnimatorCompat setListener(ViewPropertyAnimatorListener listener) {
        View view = (View) this.mView.get();
        View view2 = view;
        if (view != null) {
            setListenerInternal(view2, listener);
        }
        return this;
    }

    private void setListenerInternal(final View view, final ViewPropertyAnimatorListener listener) {
        if (listener != null) {
            view.animate().setListener(new AnimatorListenerAdapter() {
                public void onAnimationCancel(Animator animation) {
                    listener.onAnimationCancel(view);
                }

                public void onAnimationEnd(Animator animation) {
                    listener.onAnimationEnd(view);
                }

                public void onAnimationStart(Animator animation) {
                    listener.onAnimationStart(view);
                }
            });
        } else {
            view.animate().setListener((Animator.AnimatorListener) null);
        }
    }

    public ViewPropertyAnimatorCompat setUpdateListener(ViewPropertyAnimatorUpdateListener listener) {
        View view = (View) this.mView.get();
        View view2 = view;
        if (view != null) {
            ValueAnimator.AnimatorUpdateListener wrapped = null;
            if (listener != null) {
                wrapped = new ViewPropertyAnimatorCompat$$ExternalSyntheticLambda0(listener, view2);
            }
            Api19Impl.setUpdateListener(view2.animate(), wrapped);
        }
        return this;
    }

    static class Api16Impl {
        private Api16Impl() {
        }

        static ViewPropertyAnimator withEndAction(ViewPropertyAnimator viewPropertyAnimator, Runnable runnable) {
            return viewPropertyAnimator.withEndAction(runnable);
        }

        static ViewPropertyAnimator withLayer(ViewPropertyAnimator viewPropertyAnimator) {
            return viewPropertyAnimator.withLayer();
        }

        static ViewPropertyAnimator withStartAction(ViewPropertyAnimator viewPropertyAnimator, Runnable runnable) {
            return viewPropertyAnimator.withStartAction(runnable);
        }
    }

    static class Api18Impl {
        private Api18Impl() {
        }

        static Interpolator getInterpolator(ViewPropertyAnimator viewPropertyAnimator) {
            return (Interpolator) viewPropertyAnimator.getInterpolator();
        }
    }

    static class Api21Impl {
        private Api21Impl() {
        }

        static ViewPropertyAnimator translationZBy(ViewPropertyAnimator viewPropertyAnimator, float value) {
            return viewPropertyAnimator.translationZBy(value);
        }

        static ViewPropertyAnimator translationZ(ViewPropertyAnimator viewPropertyAnimator, float value) {
            return viewPropertyAnimator.translationZ(value);
        }

        /* renamed from: z */
        static ViewPropertyAnimator m212z(ViewPropertyAnimator viewPropertyAnimator, float value) {
            return viewPropertyAnimator.z(value);
        }

        static ViewPropertyAnimator zBy(ViewPropertyAnimator viewPropertyAnimator, float value) {
            return viewPropertyAnimator.zBy(value);
        }
    }

    static class Api19Impl {
        private Api19Impl() {
        }

        static ViewPropertyAnimator setUpdateListener(ViewPropertyAnimator viewPropertyAnimator, ValueAnimator.AnimatorUpdateListener listener) {
            return viewPropertyAnimator.setUpdateListener(listener);
        }
    }
}
