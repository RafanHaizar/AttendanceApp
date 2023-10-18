package androidx.constraintlayout.helper.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import androidx.constraintlayout.motion.widget.MotionHelper;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.constraintlayout.motion.widget.MotionScene;
import androidx.constraintlayout.widget.C0657R;
import androidx.constraintlayout.widget.ConstraintSet;
import java.util.ArrayList;
import java.util.Iterator;

public class Carousel extends MotionHelper {
    private static final boolean DEBUG = false;
    private static final String TAG = "Carousel";
    public static final int TOUCH_UP_CARRY_ON = 2;
    public static final int TOUCH_UP_IMMEDIATE_STOP = 1;
    private int backwardTransition = -1;
    /* access modifiers changed from: private */
    public float dampening = 0.9f;
    private int emptyViewBehavior = 4;
    private int firstViewReference = -1;
    private int forwardTransition = -1;
    private boolean infiniteCarousel = false;
    /* access modifiers changed from: private */
    public Adapter mAdapter = null;
    private int mAnimateTargetDelay = 200;
    /* access modifiers changed from: private */
    public int mIndex = 0;
    int mLastStartId = -1;
    private final ArrayList<View> mList = new ArrayList<>();
    /* access modifiers changed from: private */
    public MotionLayout mMotionLayout;
    /* access modifiers changed from: private */
    public int mPreviousIndex = 0;
    private int mTargetIndex = -1;
    Runnable mUpdateRunnable = new Runnable() {
        public void run() {
            Carousel.this.mMotionLayout.setProgress(0.0f);
            Carousel.this.updateItems();
            Carousel.this.mAdapter.onNewItem(Carousel.this.mIndex);
            float velocity = Carousel.this.mMotionLayout.getVelocity();
            if (Carousel.this.touchUpMode == 2 && velocity > Carousel.this.velocityThreshold && Carousel.this.mIndex < Carousel.this.mAdapter.count() - 1) {
                final float v = Carousel.this.dampening * velocity;
                if (Carousel.this.mIndex == 0 && Carousel.this.mPreviousIndex > Carousel.this.mIndex) {
                    return;
                }
                if (Carousel.this.mIndex != Carousel.this.mAdapter.count() - 1 || Carousel.this.mPreviousIndex >= Carousel.this.mIndex) {
                    Carousel.this.mMotionLayout.post(new Runnable() {
                        public void run() {
                            Carousel.this.mMotionLayout.touchAnimateTo(5, 1.0f, v);
                        }
                    });
                }
            }
        }
    };
    private int nextState = -1;
    private int previousState = -1;
    private int startIndex = 0;
    /* access modifiers changed from: private */
    public int touchUpMode = 1;
    /* access modifiers changed from: private */
    public float velocityThreshold = 2.0f;

    public interface Adapter {
        int count();

        void onNewItem(int i);

        void populate(View view, int i);
    }

    public Carousel(Context context) {
        super(context);
    }

    public Carousel(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public Carousel(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, C0657R.styleable.Carousel);
            int N = a.getIndexCount();
            for (int i = 0; i < N; i++) {
                int attr = a.getIndex(i);
                if (attr == C0657R.styleable.Carousel_carousel_firstView) {
                    this.firstViewReference = a.getResourceId(attr, this.firstViewReference);
                } else if (attr == C0657R.styleable.Carousel_carousel_backwardTransition) {
                    this.backwardTransition = a.getResourceId(attr, this.backwardTransition);
                } else if (attr == C0657R.styleable.Carousel_carousel_forwardTransition) {
                    this.forwardTransition = a.getResourceId(attr, this.forwardTransition);
                } else if (attr == C0657R.styleable.Carousel_carousel_emptyViewsBehavior) {
                    this.emptyViewBehavior = a.getInt(attr, this.emptyViewBehavior);
                } else if (attr == C0657R.styleable.Carousel_carousel_previousState) {
                    this.previousState = a.getResourceId(attr, this.previousState);
                } else if (attr == C0657R.styleable.Carousel_carousel_nextState) {
                    this.nextState = a.getResourceId(attr, this.nextState);
                } else if (attr == C0657R.styleable.Carousel_carousel_touchUp_dampeningFactor) {
                    this.dampening = a.getFloat(attr, this.dampening);
                } else if (attr == C0657R.styleable.Carousel_carousel_touchUpMode) {
                    this.touchUpMode = a.getInt(attr, this.touchUpMode);
                } else if (attr == C0657R.styleable.Carousel_carousel_touchUp_velocityThreshold) {
                    this.velocityThreshold = a.getFloat(attr, this.velocityThreshold);
                } else if (attr == C0657R.styleable.Carousel_carousel_infinite) {
                    this.infiniteCarousel = a.getBoolean(attr, this.infiniteCarousel);
                }
            }
            a.recycle();
        }
    }

    public void setAdapter(Adapter adapter) {
        this.mAdapter = adapter;
    }

    public int getCount() {
        Adapter adapter = this.mAdapter;
        if (adapter != null) {
            return adapter.count();
        }
        return 0;
    }

    public int getCurrentIndex() {
        return this.mIndex;
    }

    public void transitionToIndex(int index, int delay) {
        this.mTargetIndex = Math.max(0, Math.min(getCount() - 1, index));
        int max = Math.max(0, delay);
        this.mAnimateTargetDelay = max;
        this.mMotionLayout.setTransitionDuration(max);
        if (index < this.mIndex) {
            this.mMotionLayout.transitionToState(this.previousState, this.mAnimateTargetDelay);
        } else {
            this.mMotionLayout.transitionToState(this.nextState, this.mAnimateTargetDelay);
        }
    }

    public void jumpToIndex(int index) {
        this.mIndex = Math.max(0, Math.min(getCount() - 1, index));
        refresh();
    }

    public void refresh() {
        int count = this.mList.size();
        for (int i = 0; i < count; i++) {
            View view = this.mList.get(i);
            if (this.mAdapter.count() == 0) {
                updateViewVisibility(view, this.emptyViewBehavior);
            } else {
                updateViewVisibility(view, 0);
            }
        }
        this.mMotionLayout.rebuildScene();
        updateItems();
    }

    public void onTransitionChange(MotionLayout motionLayout, int startId, int endId, float progress) {
        this.mLastStartId = startId;
    }

    public void onTransitionCompleted(MotionLayout motionLayout, int currentId) {
        int i = this.mIndex;
        this.mPreviousIndex = i;
        if (currentId == this.nextState) {
            this.mIndex = i + 1;
        } else if (currentId == this.previousState) {
            this.mIndex = i - 1;
        }
        if (this.infiniteCarousel) {
            if (this.mIndex >= this.mAdapter.count()) {
                this.mIndex = 0;
            }
            if (this.mIndex < 0) {
                this.mIndex = this.mAdapter.count() - 1;
            }
        } else {
            if (this.mIndex >= this.mAdapter.count()) {
                this.mIndex = this.mAdapter.count() - 1;
            }
            if (this.mIndex < 0) {
                this.mIndex = 0;
            }
        }
        if (this.mPreviousIndex != this.mIndex) {
            this.mMotionLayout.post(this.mUpdateRunnable);
        }
    }

    private void enableAllTransitions(boolean enable) {
        Iterator<MotionScene.Transition> it = this.mMotionLayout.getDefinedTransitions().iterator();
        while (it.hasNext()) {
            it.next().setEnabled(enable);
        }
    }

    private boolean enableTransition(int transitionID, boolean enable) {
        MotionLayout motionLayout;
        MotionScene.Transition transition;
        if (transitionID == -1 || (motionLayout = this.mMotionLayout) == null || (transition = motionLayout.getTransition(transitionID)) == null || enable == transition.isEnabled()) {
            return false;
        }
        transition.setEnabled(enable);
        return true;
    }

    /* access modifiers changed from: protected */
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (getParent() instanceof MotionLayout) {
            MotionLayout container = (MotionLayout) getParent();
            for (int i = 0; i < this.mCount; i++) {
                int id = this.mIds[i];
                View view = container.getViewById(id);
                if (this.firstViewReference == id) {
                    this.startIndex = i;
                }
                this.mList.add(view);
            }
            this.mMotionLayout = container;
            if (this.touchUpMode == 2) {
                MotionScene.Transition forward = container.getTransition(this.forwardTransition);
                if (forward != null) {
                    forward.setOnTouchUp(5);
                }
                MotionScene.Transition backward = this.mMotionLayout.getTransition(this.backwardTransition);
                if (backward != null) {
                    backward.setOnTouchUp(5);
                }
            }
            updateItems();
        }
    }

    private boolean updateViewVisibility(View view, int visibility) {
        MotionLayout motionLayout = this.mMotionLayout;
        if (motionLayout == null) {
            return false;
        }
        boolean needsMotionSceneRebuild = false;
        int[] constraintSets = motionLayout.getConstraintSetIds();
        for (int updateViewVisibility : constraintSets) {
            needsMotionSceneRebuild |= updateViewVisibility(updateViewVisibility, view, visibility);
        }
        return needsMotionSceneRebuild;
    }

    private boolean updateViewVisibility(int constraintSetId, View view, int visibility) {
        ConstraintSet.Constraint constraint;
        ConstraintSet constraintSet = this.mMotionLayout.getConstraintSet(constraintSetId);
        if (constraintSet == null || (constraint = constraintSet.getConstraint(view.getId())) == null) {
            return false;
        }
        constraint.propertySet.mVisibilityMode = 1;
        view.setVisibility(visibility);
        return true;
    }

    /* access modifiers changed from: private */
    public void updateItems() {
        Adapter adapter = this.mAdapter;
        if (adapter != null && this.mMotionLayout != null && adapter.count() != 0) {
            int viewCount = this.mList.size();
            for (int i = 0; i < viewCount; i++) {
                View view = this.mList.get(i);
                int index = (this.mIndex + i) - this.startIndex;
                if (this.infiniteCarousel) {
                    if (index < 0) {
                        int i2 = this.emptyViewBehavior;
                        if (i2 != 4) {
                            updateViewVisibility(view, i2);
                        } else {
                            updateViewVisibility(view, 0);
                        }
                        if (index % this.mAdapter.count() == 0) {
                            this.mAdapter.populate(view, 0);
                        } else {
                            Adapter adapter2 = this.mAdapter;
                            adapter2.populate(view, adapter2.count() + (index % this.mAdapter.count()));
                        }
                    } else if (index >= this.mAdapter.count()) {
                        if (index == this.mAdapter.count()) {
                            index = 0;
                        } else if (index > this.mAdapter.count()) {
                            index %= this.mAdapter.count();
                        }
                        int i3 = this.emptyViewBehavior;
                        if (i3 != 4) {
                            updateViewVisibility(view, i3);
                        } else {
                            updateViewVisibility(view, 0);
                        }
                        this.mAdapter.populate(view, index);
                    } else {
                        updateViewVisibility(view, 0);
                        this.mAdapter.populate(view, index);
                    }
                } else if (index < 0) {
                    updateViewVisibility(view, this.emptyViewBehavior);
                } else if (index >= this.mAdapter.count()) {
                    updateViewVisibility(view, this.emptyViewBehavior);
                } else {
                    updateViewVisibility(view, 0);
                    this.mAdapter.populate(view, index);
                }
            }
            int i4 = this.mTargetIndex;
            if (i4 != -1 && i4 != this.mIndex) {
                this.mMotionLayout.post(new Carousel$$ExternalSyntheticLambda0(this));
            } else if (i4 == this.mIndex) {
                this.mTargetIndex = -1;
            }
            if (this.backwardTransition == -1 || this.forwardTransition == -1) {
                Log.w(TAG, "No backward or forward transitions defined for Carousel!");
            } else if (!this.infiniteCarousel) {
                int count = this.mAdapter.count();
                if (this.mIndex == 0) {
                    enableTransition(this.backwardTransition, false);
                } else {
                    enableTransition(this.backwardTransition, true);
                    this.mMotionLayout.setTransition(this.backwardTransition);
                }
                if (this.mIndex == count - 1) {
                    enableTransition(this.forwardTransition, false);
                    return;
                }
                enableTransition(this.forwardTransition, true);
                this.mMotionLayout.setTransition(this.forwardTransition);
            }
        }
    }

    /* access modifiers changed from: package-private */
    /* renamed from: lambda$updateItems$0$androidx-constraintlayout-helper-widget-Carousel */
    public /* synthetic */ void mo13732xc943cdea() {
        this.mMotionLayout.setTransitionDuration(this.mAnimateTargetDelay);
        if (this.mTargetIndex < this.mIndex) {
            this.mMotionLayout.transitionToState(this.previousState, this.mAnimateTargetDelay);
        } else {
            this.mMotionLayout.transitionToState(this.nextState, this.mAnimateTargetDelay);
        }
    }
}
