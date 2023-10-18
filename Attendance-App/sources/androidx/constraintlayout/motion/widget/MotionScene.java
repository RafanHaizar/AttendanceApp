package androidx.constraintlayout.motion.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.util.TypedValue;
import android.util.Xml;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnimationUtils;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.OvershootInterpolator;
import androidx.constraintlayout.core.motion.utils.Easing;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.constraintlayout.widget.C0657R;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.constraintlayout.widget.StateSet;
import com.itextpdf.p026io.font.constants.FontWeights;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public class MotionScene {
    static final int ANTICIPATE = 6;
    static final int BOUNCE = 4;
    private static final String CONSTRAINTSET_TAG = "ConstraintSet";
    private static final boolean DEBUG = false;
    static final int EASE_IN = 1;
    static final int EASE_IN_OUT = 0;
    static final int EASE_OUT = 2;
    private static final String INCLUDE_TAG = "include";
    private static final String INCLUDE_TAG_UC = "Include";
    private static final int INTERPOLATOR_REFERENCE_ID = -2;
    private static final String KEYFRAMESET_TAG = "KeyFrameSet";
    public static final int LAYOUT_CALL_MEASURE = 2;
    public static final int LAYOUT_HONOR_REQUEST = 1;
    public static final int LAYOUT_IGNORE_REQUEST = 0;
    static final int LINEAR = 3;
    private static final int MIN_DURATION = 8;
    private static final String MOTIONSCENE_TAG = "MotionScene";
    private static final String ONCLICK_TAG = "OnClick";
    private static final String ONSWIPE_TAG = "OnSwipe";
    static final int OVERSHOOT = 5;
    private static final int SPLINE_STRING = -1;
    private static final String STATESET_TAG = "StateSet";
    private static final String TAG = "MotionScene";
    static final int TRANSITION_BACKWARD = 0;
    static final int TRANSITION_FORWARD = 1;
    private static final String TRANSITION_TAG = "Transition";
    public static final int UNSET = -1;
    private static final String VIEW_TRANSITION = "ViewTransition";
    private boolean DEBUG_DESKTOP = false;
    private ArrayList<Transition> mAbstractTransitionList = new ArrayList<>();
    private HashMap<String, Integer> mConstraintSetIdMap = new HashMap<>();
    /* access modifiers changed from: private */
    public SparseArray<ConstraintSet> mConstraintSetMap = new SparseArray<>();
    Transition mCurrentTransition = null;
    /* access modifiers changed from: private */
    public int mDefaultDuration = FontWeights.NORMAL;
    private Transition mDefaultTransition = null;
    private SparseIntArray mDeriveMap = new SparseIntArray();
    private boolean mDisableAutoTransition = false;
    private boolean mIgnoreTouch = false;
    private MotionEvent mLastTouchDown;
    float mLastTouchX;
    float mLastTouchY;
    /* access modifiers changed from: private */
    public int mLayoutDuringTransition = 0;
    /* access modifiers changed from: private */
    public final MotionLayout mMotionLayout;
    private boolean mMotionOutsideRegion = false;
    private boolean mRtl;
    StateSet mStateSet = null;
    private ArrayList<Transition> mTransitionList = new ArrayList<>();
    private MotionLayout.MotionTracker mVelocityTracker;
    final ViewTransitionController mViewTransitionController;

    /* access modifiers changed from: package-private */
    public void setTransition(int beginId, int endId) {
        int start = beginId;
        int end = endId;
        StateSet stateSet = this.mStateSet;
        if (stateSet != null) {
            int tmp = stateSet.stateGetConstraintID(beginId, -1, -1);
            if (tmp != -1) {
                start = tmp;
            }
            int tmp2 = this.mStateSet.stateGetConstraintID(endId, -1, -1);
            if (tmp2 != -1) {
                end = tmp2;
            }
        }
        Transition transition = this.mCurrentTransition;
        if (transition == null || transition.mConstraintSetEnd != endId || this.mCurrentTransition.mConstraintSetStart != beginId) {
            Iterator<Transition> it = this.mTransitionList.iterator();
            while (it.hasNext()) {
                Transition transition2 = it.next();
                if ((transition2.mConstraintSetEnd == end && transition2.mConstraintSetStart == start) || (transition2.mConstraintSetEnd == endId && transition2.mConstraintSetStart == beginId)) {
                    this.mCurrentTransition = transition2;
                    if (transition2 != null && transition2.mTouchResponse != null) {
                        this.mCurrentTransition.mTouchResponse.setRTL(this.mRtl);
                        return;
                    }
                    return;
                }
            }
            Transition matchTransition = this.mDefaultTransition;
            Iterator<Transition> it2 = this.mAbstractTransitionList.iterator();
            while (it2.hasNext()) {
                Transition transition3 = it2.next();
                if (transition3.mConstraintSetEnd == endId) {
                    matchTransition = transition3;
                }
            }
            Transition t = new Transition(this, matchTransition);
            int unused = t.mConstraintSetStart = start;
            int unused2 = t.mConstraintSetEnd = end;
            if (start != -1) {
                this.mTransitionList.add(t);
            }
            this.mCurrentTransition = t;
        }
    }

    public void addTransition(Transition transition) {
        int index = getIndex(transition);
        if (index == -1) {
            this.mTransitionList.add(transition);
        } else {
            this.mTransitionList.set(index, transition);
        }
    }

    public void removeTransition(Transition transition) {
        int index = getIndex(transition);
        if (index != -1) {
            this.mTransitionList.remove(index);
        }
    }

    private int getIndex(Transition transition) {
        int id = transition.mId;
        if (id != -1) {
            for (int index = 0; index < this.mTransitionList.size(); index++) {
                if (this.mTransitionList.get(index).mId == id) {
                    return index;
                }
            }
            return -1;
        }
        throw new IllegalArgumentException("The transition must have an id");
    }

    public boolean validateLayout(MotionLayout layout) {
        return layout == this.mMotionLayout && layout.mScene == this;
    }

    public void setTransition(Transition transition) {
        this.mCurrentTransition = transition;
        if (transition != null && transition.mTouchResponse != null) {
            this.mCurrentTransition.mTouchResponse.setRTL(this.mRtl);
        }
    }

    private int getRealID(int stateId) {
        int tmp;
        StateSet stateSet = this.mStateSet;
        if (stateSet == null || (tmp = stateSet.stateGetConstraintID(stateId, -1, -1)) == -1) {
            return stateId;
        }
        return tmp;
    }

    public List<Transition> getTransitionsWithState(int stateId) {
        int stateId2 = getRealID(stateId);
        ArrayList<Transition> ret = new ArrayList<>();
        Iterator<Transition> it = this.mTransitionList.iterator();
        while (it.hasNext()) {
            Transition transition = it.next();
            if (transition.mConstraintSetStart == stateId2 || transition.mConstraintSetEnd == stateId2) {
                ret.add(transition);
            }
        }
        return ret;
    }

    public void addOnClickListeners(MotionLayout motionLayout, int currentState) {
        Iterator<Transition> it = this.mTransitionList.iterator();
        while (it.hasNext()) {
            Transition transition = it.next();
            if (transition.mOnClicks.size() > 0) {
                Iterator it2 = transition.mOnClicks.iterator();
                while (it2.hasNext()) {
                    ((Transition.TransitionOnClick) it2.next()).removeOnClickListeners(motionLayout);
                }
            }
        }
        Iterator<Transition> it3 = this.mAbstractTransitionList.iterator();
        while (it3.hasNext()) {
            Transition transition2 = it3.next();
            if (transition2.mOnClicks.size() > 0) {
                Iterator it4 = transition2.mOnClicks.iterator();
                while (it4.hasNext()) {
                    ((Transition.TransitionOnClick) it4.next()).removeOnClickListeners(motionLayout);
                }
            }
        }
        Iterator<Transition> it5 = this.mTransitionList.iterator();
        while (it5.hasNext()) {
            Transition transition3 = it5.next();
            if (transition3.mOnClicks.size() > 0) {
                Iterator it6 = transition3.mOnClicks.iterator();
                while (it6.hasNext()) {
                    ((Transition.TransitionOnClick) it6.next()).addOnClickListeners(motionLayout, currentState, transition3);
                }
            }
        }
        Iterator<Transition> it7 = this.mAbstractTransitionList.iterator();
        while (it7.hasNext()) {
            Transition transition4 = it7.next();
            if (transition4.mOnClicks.size() > 0) {
                Iterator it8 = transition4.mOnClicks.iterator();
                while (it8.hasNext()) {
                    ((Transition.TransitionOnClick) it8.next()).addOnClickListeners(motionLayout, currentState, transition4);
                }
            }
        }
    }

    public Transition bestTransitionFor(int currentState, float dx, float dy, MotionEvent lastTouchDown) {
        Iterator<Transition> it;
        RectF cache;
        RectF region;
        float val;
        float val2;
        int i = currentState;
        float f = dx;
        float f2 = dy;
        if (i == -1) {
            return this.mCurrentTransition;
        }
        List<Transition> candidates = getTransitionsWithState(currentState);
        float max = 0.0f;
        Transition best = null;
        RectF cache2 = new RectF();
        Iterator<Transition> it2 = candidates.iterator();
        while (it2.hasNext()) {
            Transition transition = it2.next();
            if (!transition.mDisable) {
                if (transition.mTouchResponse != null) {
                    transition.mTouchResponse.setRTL(this.mRtl);
                    RectF region2 = transition.mTouchResponse.getTouchRegion(this.mMotionLayout, cache2);
                    if ((region2 == null || lastTouchDown == null || region2.contains(lastTouchDown.getX(), lastTouchDown.getY())) && ((region = transition.mTouchResponse.getLimitBoundsTo(this.mMotionLayout, cache2)) == null || lastTouchDown == null || region.contains(lastTouchDown.getX(), lastTouchDown.getY()))) {
                        float val3 = transition.mTouchResponse.dot(f, f2);
                        if (!transition.mTouchResponse.mIsRotateMode || lastTouchDown == null) {
                            cache = cache2;
                            it = it2;
                            RectF rectF = region;
                            val = val3;
                        } else {
                            float startX = lastTouchDown.getX() - transition.mTouchResponse.mRotateCenterX;
                            float startY = lastTouchDown.getY() - transition.mTouchResponse.mRotateCenterY;
                            cache = cache2;
                            it = it2;
                            RectF rectF2 = region;
                            float f3 = val3;
                            val = 10.0f * ((float) (Math.atan2((double) (f2 + startY), (double) (f + startX)) - Math.atan2((double) startX, (double) startY)));
                        }
                        if (transition.mConstraintSetEnd == i) {
                            val2 = val * -1.0f;
                        } else {
                            val2 = val * 1.1f;
                        }
                        if (val2 > max) {
                            max = val2;
                            best = transition;
                        }
                    }
                } else {
                    cache = cache2;
                    it = it2;
                }
                f = dx;
                f2 = dy;
                cache2 = cache;
                it2 = it;
            }
        }
        return best;
    }

    public ArrayList<Transition> getDefinedTransitions() {
        return this.mTransitionList;
    }

    public Transition getTransitionById(int id) {
        Iterator<Transition> it = this.mTransitionList.iterator();
        while (it.hasNext()) {
            Transition transition = it.next();
            if (transition.mId == id) {
                return transition;
            }
        }
        return null;
    }

    public int[] getConstraintSetIds() {
        int[] ids = new int[this.mConstraintSetMap.size()];
        for (int i = 0; i < ids.length; i++) {
            ids[i] = this.mConstraintSetMap.keyAt(i);
        }
        return ids;
    }

    /* access modifiers changed from: package-private */
    public boolean autoTransition(MotionLayout motionLayout, int currentState) {
        Transition transition;
        if (isProcessingTouch() || this.mDisableAutoTransition) {
            return false;
        }
        Iterator<Transition> it = this.mTransitionList.iterator();
        while (it.hasNext()) {
            Transition transition2 = it.next();
            if (transition2.mAutoTransition != 0 && ((transition = this.mCurrentTransition) != transition2 || !transition.isTransitionFlag(2))) {
                if (currentState == transition2.mConstraintSetStart && (transition2.mAutoTransition == 4 || transition2.mAutoTransition == 2)) {
                    motionLayout.setState(MotionLayout.TransitionState.FINISHED);
                    motionLayout.setTransition(transition2);
                    if (transition2.mAutoTransition == 4) {
                        motionLayout.transitionToEnd();
                        motionLayout.setState(MotionLayout.TransitionState.SETUP);
                        motionLayout.setState(MotionLayout.TransitionState.MOVING);
                    } else {
                        motionLayout.setProgress(1.0f);
                        motionLayout.evaluate(true);
                        motionLayout.setState(MotionLayout.TransitionState.SETUP);
                        motionLayout.setState(MotionLayout.TransitionState.MOVING);
                        motionLayout.setState(MotionLayout.TransitionState.FINISHED);
                        motionLayout.onNewStateAttachHandlers();
                    }
                    return true;
                } else if (currentState == transition2.mConstraintSetEnd && (transition2.mAutoTransition == 3 || transition2.mAutoTransition == 1)) {
                    motionLayout.setState(MotionLayout.TransitionState.FINISHED);
                    motionLayout.setTransition(transition2);
                    if (transition2.mAutoTransition == 3) {
                        motionLayout.transitionToStart();
                        motionLayout.setState(MotionLayout.TransitionState.SETUP);
                        motionLayout.setState(MotionLayout.TransitionState.MOVING);
                    } else {
                        motionLayout.setProgress(0.0f);
                        motionLayout.evaluate(true);
                        motionLayout.setState(MotionLayout.TransitionState.SETUP);
                        motionLayout.setState(MotionLayout.TransitionState.MOVING);
                        motionLayout.setState(MotionLayout.TransitionState.FINISHED);
                        motionLayout.onNewStateAttachHandlers();
                    }
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isProcessingTouch() {
        return this.mVelocityTracker != null;
    }

    public void setRtl(boolean rtl) {
        this.mRtl = rtl;
        Transition transition = this.mCurrentTransition;
        if (transition != null && transition.mTouchResponse != null) {
            this.mCurrentTransition.mTouchResponse.setRTL(this.mRtl);
        }
    }

    public void viewTransition(int id, View... view) {
        this.mViewTransitionController.viewTransition(id, view);
    }

    public void enableViewTransition(int id, boolean enable) {
        this.mViewTransitionController.enableViewTransition(id, enable);
    }

    public boolean isViewTransitionEnabled(int id) {
        return this.mViewTransitionController.isViewTransitionEnabled(id);
    }

    public boolean applyViewTransition(int viewTransitionId, MotionController motionController) {
        return this.mViewTransitionController.applyViewTransition(viewTransitionId, motionController);
    }

    public static class Transition {
        public static final int AUTO_ANIMATE_TO_END = 4;
        public static final int AUTO_ANIMATE_TO_START = 3;
        public static final int AUTO_JUMP_TO_END = 2;
        public static final int AUTO_JUMP_TO_START = 1;
        public static final int AUTO_NONE = 0;
        public static final int INTERPOLATE_ANTICIPATE = 6;
        public static final int INTERPOLATE_BOUNCE = 4;
        public static final int INTERPOLATE_EASE_IN = 1;
        public static final int INTERPOLATE_EASE_IN_OUT = 0;
        public static final int INTERPOLATE_EASE_OUT = 2;
        public static final int INTERPOLATE_LINEAR = 3;
        public static final int INTERPOLATE_OVERSHOOT = 5;
        public static final int INTERPOLATE_REFERENCE_ID = -2;
        public static final int INTERPOLATE_SPLINE_STRING = -1;
        static final int TRANSITION_FLAG_FIRST_DRAW = 1;
        static final int TRANSITION_FLAG_INTERCEPT_TOUCH = 4;
        static final int TRANSITION_FLAG_INTRA_AUTO = 2;
        /* access modifiers changed from: private */
        public int mAutoTransition = 0;
        /* access modifiers changed from: private */
        public int mConstraintSetEnd = -1;
        /* access modifiers changed from: private */
        public int mConstraintSetStart = -1;
        /* access modifiers changed from: private */
        public int mDefaultInterpolator = 0;
        /* access modifiers changed from: private */
        public int mDefaultInterpolatorID = -1;
        /* access modifiers changed from: private */
        public String mDefaultInterpolatorString = null;
        /* access modifiers changed from: private */
        public boolean mDisable = false;
        /* access modifiers changed from: private */
        public int mDuration = FontWeights.NORMAL;
        /* access modifiers changed from: private */
        public int mId = -1;
        /* access modifiers changed from: private */
        public boolean mIsAbstract = false;
        /* access modifiers changed from: private */
        public ArrayList<KeyFrames> mKeyFramesList = new ArrayList<>();
        private int mLayoutDuringTransition = 0;
        /* access modifiers changed from: private */
        public final MotionScene mMotionScene;
        /* access modifiers changed from: private */
        public ArrayList<TransitionOnClick> mOnClicks = new ArrayList<>();
        /* access modifiers changed from: private */
        public int mPathMotionArc = -1;
        /* access modifiers changed from: private */
        public float mStagger = 0.0f;
        /* access modifiers changed from: private */
        public TouchResponse mTouchResponse = null;
        private int mTransitionFlags = 0;

        public void setOnSwipe(OnSwipe onSwipe) {
            this.mTouchResponse = onSwipe == null ? null : new TouchResponse(this.mMotionScene.mMotionLayout, onSwipe);
        }

        public void addOnClick(int id, int action) {
            Iterator<TransitionOnClick> it = this.mOnClicks.iterator();
            while (it.hasNext()) {
                TransitionOnClick onClick = it.next();
                if (onClick.mTargetId == id) {
                    onClick.mMode = action;
                    return;
                }
            }
            this.mOnClicks.add(new TransitionOnClick(this, id, action));
        }

        public void removeOnClick(int id) {
            TransitionOnClick toRemove = null;
            Iterator<TransitionOnClick> it = this.mOnClicks.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                TransitionOnClick onClick = it.next();
                if (onClick.mTargetId == id) {
                    toRemove = onClick;
                    break;
                }
            }
            if (toRemove != null) {
                this.mOnClicks.remove(toRemove);
            }
        }

        public int getLayoutDuringTransition() {
            return this.mLayoutDuringTransition;
        }

        public void setLayoutDuringTransition(int mode) {
            this.mLayoutDuringTransition = mode;
        }

        public void addOnClick(Context context, XmlPullParser parser) {
            this.mOnClicks.add(new TransitionOnClick(context, this, parser));
        }

        public void setAutoTransition(int type) {
            this.mAutoTransition = type;
        }

        public int getAutoTransition() {
            return this.mAutoTransition;
        }

        public int getId() {
            return this.mId;
        }

        public int getEndConstraintSetId() {
            return this.mConstraintSetEnd;
        }

        public int getStartConstraintSetId() {
            return this.mConstraintSetStart;
        }

        public void setDuration(int duration) {
            this.mDuration = Math.max(duration, 8);
        }

        public int getDuration() {
            return this.mDuration;
        }

        public float getStagger() {
            return this.mStagger;
        }

        public List<KeyFrames> getKeyFrameList() {
            return this.mKeyFramesList;
        }

        public void addKeyFrame(KeyFrames keyFrames) {
            this.mKeyFramesList.add(keyFrames);
        }

        public List<TransitionOnClick> getOnClickList() {
            return this.mOnClicks;
        }

        public TouchResponse getTouchResponse() {
            return this.mTouchResponse;
        }

        public void setStagger(float stagger) {
            this.mStagger = stagger;
        }

        public void setPathMotionArc(int arcMode) {
            this.mPathMotionArc = arcMode;
        }

        public int getPathMotionArc() {
            return this.mPathMotionArc;
        }

        public boolean isEnabled() {
            return !this.mDisable;
        }

        public void setEnable(boolean enable) {
            setEnabled(enable);
        }

        public void setEnabled(boolean enable) {
            this.mDisable = !enable;
        }

        public String debugString(Context context) {
            String ret;
            if (this.mConstraintSetStart == -1) {
                ret = "null";
            } else {
                ret = context.getResources().getResourceEntryName(this.mConstraintSetStart);
            }
            if (this.mConstraintSetEnd == -1) {
                return ret + " -> null";
            }
            return ret + " -> " + context.getResources().getResourceEntryName(this.mConstraintSetEnd);
        }

        public boolean isTransitionFlag(int flag) {
            return (this.mTransitionFlags & flag) != 0;
        }

        public void setTransitionFlag(int flag) {
            this.mTransitionFlags = flag;
        }

        public void setOnTouchUp(int touchUpMode) {
            TouchResponse touchResponse = getTouchResponse();
            if (touchResponse != null) {
                touchResponse.setTouchUpMode(touchUpMode);
            }
        }

        public static class TransitionOnClick implements View.OnClickListener {
            public static final int ANIM_TOGGLE = 17;
            public static final int ANIM_TO_END = 1;
            public static final int ANIM_TO_START = 16;
            public static final int JUMP_TO_END = 256;
            public static final int JUMP_TO_START = 4096;
            int mMode = 17;
            int mTargetId = -1;
            private final Transition mTransition;

            public TransitionOnClick(Context context, Transition transition, XmlPullParser parser) {
                this.mTransition = transition;
                TypedArray a = context.obtainStyledAttributes(Xml.asAttributeSet(parser), C0657R.styleable.OnClick);
                int N = a.getIndexCount();
                for (int i = 0; i < N; i++) {
                    int attr = a.getIndex(i);
                    if (attr == C0657R.styleable.OnClick_targetId) {
                        this.mTargetId = a.getResourceId(attr, this.mTargetId);
                    } else if (attr == C0657R.styleable.OnClick_clickAction) {
                        this.mMode = a.getInt(attr, this.mMode);
                    }
                }
                a.recycle();
            }

            public TransitionOnClick(Transition transition, int id, int action) {
                this.mTransition = transition;
                this.mTargetId = id;
                this.mMode = action;
            }

            public void addOnClickListeners(MotionLayout motionLayout, int currentState, Transition transition) {
                int i = this.mTargetId;
                View v = i == -1 ? motionLayout : motionLayout.findViewById(i);
                if (v == null) {
                    Log.e(TypedValues.MotionScene.NAME, "OnClick could not find id " + this.mTargetId);
                    return;
                }
                int start = transition.mConstraintSetStart;
                int end = transition.mConstraintSetEnd;
                if (start == -1) {
                    v.setOnClickListener(this);
                    return;
                }
                int i2 = this.mMode;
                boolean z = false;
                boolean listen = ((i2 & 1) != 0 && currentState == start) | ((i2 & 256) != 0 && currentState == start) | ((i2 & 1) != 0 && currentState == start) | ((i2 & 16) != 0 && currentState == end);
                if ((i2 & 4096) != 0 && currentState == end) {
                    z = true;
                }
                if (listen || z) {
                    v.setOnClickListener(this);
                }
            }

            public void removeOnClickListeners(MotionLayout motionLayout) {
                int i = this.mTargetId;
                if (i != -1) {
                    View v = motionLayout.findViewById(i);
                    if (v == null) {
                        Log.e(TypedValues.MotionScene.NAME, " (*)  could not find id " + this.mTargetId);
                    } else {
                        v.setOnClickListener((View.OnClickListener) null);
                    }
                }
            }

            /* access modifiers changed from: package-private */
            public boolean isTransitionViable(Transition current, MotionLayout tl) {
                Transition transition = this.mTransition;
                if (transition == current) {
                    return true;
                }
                int dest = transition.mConstraintSetEnd;
                int from = this.mTransition.mConstraintSetStart;
                if (from == -1) {
                    if (tl.mCurrentState != dest) {
                        return true;
                    }
                    return false;
                } else if (tl.mCurrentState == from || tl.mCurrentState == dest) {
                    return true;
                } else {
                    return false;
                }
            }

            public void onClick(View view) {
                MotionLayout tl = this.mTransition.mMotionScene.mMotionLayout;
                if (tl.isInteractionEnabled()) {
                    if (this.mTransition.mConstraintSetStart == -1) {
                        int currentState = tl.getCurrentState();
                        if (currentState == -1) {
                            tl.transitionToState(this.mTransition.mConstraintSetEnd);
                            return;
                        }
                        Transition t = new Transition(this.mTransition.mMotionScene, this.mTransition);
                        int unused = t.mConstraintSetStart = currentState;
                        int unused2 = t.mConstraintSetEnd = this.mTransition.mConstraintSetEnd;
                        tl.setTransition(t);
                        tl.transitionToEnd();
                        return;
                    }
                    Transition current = this.mTransition.mMotionScene.mCurrentTransition;
                    int i = this.mMode;
                    boolean bidirectional = false;
                    boolean forward = ((i & 1) == 0 && (i & 256) == 0) ? false : true;
                    boolean backward = ((i & 16) == 0 && (i & 4096) == 0) ? false : true;
                    if (forward && backward) {
                        bidirectional = true;
                    }
                    if (bidirectional) {
                        Transition transition = this.mTransition.mMotionScene.mCurrentTransition;
                        Transition transition2 = this.mTransition;
                        if (transition != transition2) {
                            tl.setTransition(transition2);
                        }
                        if (tl.getCurrentState() == tl.getEndState() || tl.getProgress() > 0.5f) {
                            forward = false;
                        } else {
                            backward = false;
                        }
                    }
                    if (!isTransitionViable(current, tl)) {
                        return;
                    }
                    if (forward && (1 & this.mMode) != 0) {
                        tl.setTransition(this.mTransition);
                        tl.transitionToEnd();
                    } else if (backward && (this.mMode & 16) != 0) {
                        tl.setTransition(this.mTransition);
                        tl.transitionToStart();
                    } else if (forward && (this.mMode & 256) != 0) {
                        tl.setTransition(this.mTransition);
                        tl.setProgress(1.0f);
                    } else if (backward && (this.mMode & 4096) != 0) {
                        tl.setTransition(this.mTransition);
                        tl.setProgress(0.0f);
                    }
                }
            }
        }

        Transition(MotionScene motionScene, Transition global) {
            this.mMotionScene = motionScene;
            this.mDuration = motionScene.mDefaultDuration;
            if (global != null) {
                this.mPathMotionArc = global.mPathMotionArc;
                this.mDefaultInterpolator = global.mDefaultInterpolator;
                this.mDefaultInterpolatorString = global.mDefaultInterpolatorString;
                this.mDefaultInterpolatorID = global.mDefaultInterpolatorID;
                this.mDuration = global.mDuration;
                this.mKeyFramesList = global.mKeyFramesList;
                this.mStagger = global.mStagger;
                this.mLayoutDuringTransition = global.mLayoutDuringTransition;
            }
        }

        public Transition(int id, MotionScene motionScene, int constraintSetStartId, int constraintSetEndId) {
            this.mId = id;
            this.mMotionScene = motionScene;
            this.mConstraintSetStart = constraintSetStartId;
            this.mConstraintSetEnd = constraintSetEndId;
            this.mDuration = motionScene.mDefaultDuration;
            this.mLayoutDuringTransition = motionScene.mLayoutDuringTransition;
        }

        Transition(MotionScene motionScene, Context context, XmlPullParser parser) {
            this.mDuration = motionScene.mDefaultDuration;
            this.mLayoutDuringTransition = motionScene.mLayoutDuringTransition;
            this.mMotionScene = motionScene;
            fillFromAttributeList(motionScene, context, Xml.asAttributeSet(parser));
        }

        public void setInterpolatorInfo(int interpolator, String interpolatorString, int interpolatorID) {
            this.mDefaultInterpolator = interpolator;
            this.mDefaultInterpolatorString = interpolatorString;
            this.mDefaultInterpolatorID = interpolatorID;
        }

        private void fillFromAttributeList(MotionScene motionScene, Context context, AttributeSet attrs) {
            TypedArray a = context.obtainStyledAttributes(attrs, C0657R.styleable.Transition);
            fill(motionScene, context, a);
            a.recycle();
        }

        private void fill(MotionScene motionScene, Context context, TypedArray a) {
            int N = a.getIndexCount();
            for (int i = 0; i < N; i++) {
                int attr = a.getIndex(i);
                if (attr == C0657R.styleable.Transition_constraintSetEnd) {
                    this.mConstraintSetEnd = a.getResourceId(attr, -1);
                    String type = context.getResources().getResourceTypeName(this.mConstraintSetEnd);
                    if ("layout".equals(type)) {
                        ConstraintSet cSet = new ConstraintSet();
                        cSet.load(context, this.mConstraintSetEnd);
                        motionScene.mConstraintSetMap.append(this.mConstraintSetEnd, cSet);
                    } else if ("xml".equals(type)) {
                        this.mConstraintSetEnd = motionScene.parseInclude(context, this.mConstraintSetEnd);
                    }
                } else if (attr == C0657R.styleable.Transition_constraintSetStart) {
                    this.mConstraintSetStart = a.getResourceId(attr, this.mConstraintSetStart);
                    String type2 = context.getResources().getResourceTypeName(this.mConstraintSetStart);
                    if ("layout".equals(type2)) {
                        ConstraintSet cSet2 = new ConstraintSet();
                        cSet2.load(context, this.mConstraintSetStart);
                        motionScene.mConstraintSetMap.append(this.mConstraintSetStart, cSet2);
                    } else if ("xml".equals(type2)) {
                        this.mConstraintSetStart = motionScene.parseInclude(context, this.mConstraintSetStart);
                    }
                } else if (attr == C0657R.styleable.Transition_motionInterpolator) {
                    TypedValue type3 = a.peekValue(attr);
                    if (type3.type == 1) {
                        int resourceId = a.getResourceId(attr, -1);
                        this.mDefaultInterpolatorID = resourceId;
                        if (resourceId != -1) {
                            this.mDefaultInterpolator = -2;
                        }
                    } else if (type3.type == 3) {
                        String string = a.getString(attr);
                        this.mDefaultInterpolatorString = string;
                        if (string != null) {
                            if (string.indexOf("/") > 0) {
                                this.mDefaultInterpolatorID = a.getResourceId(attr, -1);
                                this.mDefaultInterpolator = -2;
                            } else {
                                this.mDefaultInterpolator = -1;
                            }
                        }
                    } else {
                        this.mDefaultInterpolator = a.getInteger(attr, this.mDefaultInterpolator);
                    }
                } else if (attr == C0657R.styleable.Transition_duration) {
                    int i2 = a.getInt(attr, this.mDuration);
                    this.mDuration = i2;
                    if (i2 < 8) {
                        this.mDuration = 8;
                    }
                } else if (attr == C0657R.styleable.Transition_staggered) {
                    this.mStagger = a.getFloat(attr, this.mStagger);
                } else if (attr == C0657R.styleable.Transition_autoTransition) {
                    this.mAutoTransition = a.getInteger(attr, this.mAutoTransition);
                } else if (attr == C0657R.styleable.Transition_android_id) {
                    this.mId = a.getResourceId(attr, this.mId);
                } else if (attr == C0657R.styleable.Transition_transitionDisable) {
                    this.mDisable = a.getBoolean(attr, this.mDisable);
                } else if (attr == C0657R.styleable.Transition_pathMotionArc) {
                    this.mPathMotionArc = a.getInteger(attr, -1);
                } else if (attr == C0657R.styleable.Transition_layoutDuringTransition) {
                    this.mLayoutDuringTransition = a.getInteger(attr, 0);
                } else if (attr == C0657R.styleable.Transition_transitionFlags) {
                    this.mTransitionFlags = a.getInteger(attr, 0);
                }
            }
            if (this.mConstraintSetStart == -1) {
                this.mIsAbstract = true;
            }
        }
    }

    public MotionScene(MotionLayout layout) {
        this.mMotionLayout = layout;
        this.mViewTransitionController = new ViewTransitionController(layout);
    }

    MotionScene(Context context, MotionLayout layout, int resourceID) {
        this.mMotionLayout = layout;
        this.mViewTransitionController = new ViewTransitionController(layout);
        load(context, resourceID);
        this.mConstraintSetMap.put(C0657R.C0660id.motion_base, new ConstraintSet());
        this.mConstraintSetIdMap.put("motion_base", Integer.valueOf(C0657R.C0660id.motion_base));
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Code restructure failed: missing block: B:47:0x00ac, code lost:
        switch(r6) {
            case 0: goto L_0x016a;
            case 1: goto L_0x0128;
            case 2: goto L_0x00e7;
            case 3: goto L_0x00e0;
            case 4: goto L_0x00d7;
            case 5: goto L_0x00d2;
            case 6: goto L_0x00cd;
            case 7: goto L_0x00cd;
            case 8: goto L_0x00bd;
            case 9: goto L_0x00b1;
            default: goto L_0x00af;
        };
     */
    /* JADX WARNING: Code restructure failed: missing block: B:49:0x00b1, code lost:
        r11.mViewTransitionController.add(new androidx.constraintlayout.motion.widget.ViewTransition(r12, r1));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:50:0x00bd, code lost:
        r6 = new androidx.constraintlayout.motion.widget.KeyFrames(r12, r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:51:0x00c2, code lost:
        if (r4 == null) goto L_0x016e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:52:0x00c4, code lost:
        androidx.constraintlayout.motion.widget.MotionScene.Transition.access$1400(r4).add(r6);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:53:0x00cd, code lost:
        parseInclude(r12, r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:54:0x00d2, code lost:
        parseConstraintSet(r12, r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:55:0x00d7, code lost:
        r11.mStateSet = new androidx.constraintlayout.widget.StateSet(r12, r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:56:0x00e0, code lost:
        if (r4 == null) goto L_0x016e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:57:0x00e2, code lost:
        r4.addOnClick(r12, r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:58:0x00e7, code lost:
        if (r4 != null) goto L_0x011b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:59:0x00e9, code lost:
        r6 = r12.getResources().getResourceEntryName(r13);
        android.util.Log.v(androidx.constraintlayout.core.motion.utils.TypedValues.MotionScene.NAME, " OnSwipe (" + r6 + ".xml:" + r1.getLineNumber() + ")");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:60:0x011b, code lost:
        if (r4 == null) goto L_0x016e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:61:0x011d, code lost:
        androidx.constraintlayout.motion.widget.MotionScene.Transition.access$202(r4, new androidx.constraintlayout.motion.widget.TouchResponse(r12, r11.mMotionLayout, r1));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:62:0x0128, code lost:
        r6 = r11.mTransitionList;
        r7 = new androidx.constraintlayout.motion.widget.MotionScene.Transition(r11, r12, r1);
        r4 = r7;
        r6.add(r7);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:63:0x0135, code lost:
        if (r11.mCurrentTransition != null) goto L_0x0150;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:65:0x013b, code lost:
        if (androidx.constraintlayout.motion.widget.MotionScene.Transition.access$1300(r4) != false) goto L_0x0150;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:66:0x013d, code lost:
        r11.mCurrentTransition = r4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:67:0x0143, code lost:
        if (androidx.constraintlayout.motion.widget.MotionScene.Transition.access$200(r4) == null) goto L_0x0150;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:68:0x0145, code lost:
        androidx.constraintlayout.motion.widget.MotionScene.Transition.access$200(r11.mCurrentTransition).setRTL(r11.mRtl);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:70:0x0154, code lost:
        if (androidx.constraintlayout.motion.widget.MotionScene.Transition.access$1300(r4) == false) goto L_0x016e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:72:0x015a, code lost:
        if (androidx.constraintlayout.motion.widget.MotionScene.Transition.access$000(r4) != -1) goto L_0x015f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:73:0x015c, code lost:
        r11.mDefaultTransition = r4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:74:0x015f, code lost:
        r11.mAbstractTransitionList.add(r4);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:75:0x0164, code lost:
        r11.mTransitionList.remove(r4);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:76:0x016a, code lost:
        parseMotionSceneTags(r12, r1);
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void load(android.content.Context r12, int r13) {
        /*
            r11 = this;
            android.content.res.Resources r0 = r12.getResources()
            android.content.res.XmlResourceParser r1 = r0.getXml(r13)
            r2 = 0
            r3 = 0
            r4 = 0
            int r5 = r1.getEventType()     // Catch:{ XmlPullParserException -> 0x0182, IOException -> 0x017d }
        L_0x000f:
            r6 = 1
            if (r5 == r6) goto L_0x017c
            switch(r5) {
                case 0: goto L_0x016f;
                case 1: goto L_0x0015;
                case 2: goto L_0x001a;
                case 3: goto L_0x0017;
                default: goto L_0x0015;
            }     // Catch:{ XmlPullParserException -> 0x0182, IOException -> 0x017d }
        L_0x0015:
            goto L_0x0175
        L_0x0017:
            r3 = 0
            goto L_0x0175
        L_0x001a:
            java.lang.String r7 = r1.getName()     // Catch:{ XmlPullParserException -> 0x0182, IOException -> 0x017d }
            r3 = r7
            boolean r7 = r11.DEBUG_DESKTOP     // Catch:{ XmlPullParserException -> 0x0182, IOException -> 0x017d }
            if (r7 == 0) goto L_0x003c
            java.io.PrintStream r7 = java.lang.System.out     // Catch:{ XmlPullParserException -> 0x0182, IOException -> 0x017d }
            java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch:{ XmlPullParserException -> 0x0182, IOException -> 0x017d }
            r8.<init>()     // Catch:{ XmlPullParserException -> 0x0182, IOException -> 0x017d }
            java.lang.String r9 = "parsing = "
            java.lang.StringBuilder r8 = r8.append(r9)     // Catch:{ XmlPullParserException -> 0x0182, IOException -> 0x017d }
            java.lang.StringBuilder r8 = r8.append(r3)     // Catch:{ XmlPullParserException -> 0x0182, IOException -> 0x017d }
            java.lang.String r8 = r8.toString()     // Catch:{ XmlPullParserException -> 0x0182, IOException -> 0x017d }
            r7.println(r8)     // Catch:{ XmlPullParserException -> 0x0182, IOException -> 0x017d }
        L_0x003c:
            int r7 = r3.hashCode()     // Catch:{ XmlPullParserException -> 0x0182, IOException -> 0x017d }
            java.lang.String r8 = "MotionScene"
            r9 = -1
            switch(r7) {
                case -1349929691: goto L_0x00a1;
                case -1239391468: goto L_0x0096;
                case -687739768: goto L_0x008c;
                case 61998586: goto L_0x0081;
                case 269306229: goto L_0x0078;
                case 312750793: goto L_0x006e;
                case 327855227: goto L_0x0064;
                case 793277014: goto L_0x005c;
                case 1382829617: goto L_0x0052;
                case 1942574248: goto L_0x0048;
                default: goto L_0x0046;
            }
        L_0x0046:
            goto L_0x00ab
        L_0x0048:
            java.lang.String r6 = "include"
            boolean r6 = r3.equals(r6)     // Catch:{ XmlPullParserException -> 0x0182, IOException -> 0x017d }
            if (r6 == 0) goto L_0x0046
            r6 = 6
            goto L_0x00ac
        L_0x0052:
            java.lang.String r6 = "StateSet"
            boolean r6 = r3.equals(r6)     // Catch:{ XmlPullParserException -> 0x0182, IOException -> 0x017d }
            if (r6 == 0) goto L_0x0046
            r6 = 4
            goto L_0x00ac
        L_0x005c:
            boolean r6 = r3.equals(r8)     // Catch:{ XmlPullParserException -> 0x0182, IOException -> 0x017d }
            if (r6 == 0) goto L_0x0046
            r6 = 0
            goto L_0x00ac
        L_0x0064:
            java.lang.String r6 = "OnSwipe"
            boolean r6 = r3.equals(r6)     // Catch:{ XmlPullParserException -> 0x0182, IOException -> 0x017d }
            if (r6 == 0) goto L_0x0046
            r6 = 2
            goto L_0x00ac
        L_0x006e:
            java.lang.String r6 = "OnClick"
            boolean r6 = r3.equals(r6)     // Catch:{ XmlPullParserException -> 0x0182, IOException -> 0x017d }
            if (r6 == 0) goto L_0x0046
            r6 = 3
            goto L_0x00ac
        L_0x0078:
            java.lang.String r7 = "Transition"
            boolean r7 = r3.equals(r7)     // Catch:{ XmlPullParserException -> 0x0182, IOException -> 0x017d }
            if (r7 == 0) goto L_0x0046
            goto L_0x00ac
        L_0x0081:
            java.lang.String r6 = "ViewTransition"
            boolean r6 = r3.equals(r6)     // Catch:{ XmlPullParserException -> 0x0182, IOException -> 0x017d }
            if (r6 == 0) goto L_0x0046
            r6 = 9
            goto L_0x00ac
        L_0x008c:
            java.lang.String r6 = "Include"
            boolean r6 = r3.equals(r6)     // Catch:{ XmlPullParserException -> 0x0182, IOException -> 0x017d }
            if (r6 == 0) goto L_0x0046
            r6 = 7
            goto L_0x00ac
        L_0x0096:
            java.lang.String r6 = "KeyFrameSet"
            boolean r6 = r3.equals(r6)     // Catch:{ XmlPullParserException -> 0x0182, IOException -> 0x017d }
            if (r6 == 0) goto L_0x0046
            r6 = 8
            goto L_0x00ac
        L_0x00a1:
            java.lang.String r6 = "ConstraintSet"
            boolean r6 = r3.equals(r6)     // Catch:{ XmlPullParserException -> 0x0182, IOException -> 0x017d }
            if (r6 == 0) goto L_0x0046
            r6 = 5
            goto L_0x00ac
        L_0x00ab:
            r6 = -1
        L_0x00ac:
            switch(r6) {
                case 0: goto L_0x016a;
                case 1: goto L_0x0128;
                case 2: goto L_0x00e7;
                case 3: goto L_0x00e0;
                case 4: goto L_0x00d7;
                case 5: goto L_0x00d2;
                case 6: goto L_0x00cd;
                case 7: goto L_0x00cd;
                case 8: goto L_0x00bd;
                case 9: goto L_0x00b1;
                default: goto L_0x00af;
            }     // Catch:{ XmlPullParserException -> 0x0182, IOException -> 0x017d }
        L_0x00af:
            goto L_0x016e
        L_0x00b1:
            androidx.constraintlayout.motion.widget.ViewTransition r6 = new androidx.constraintlayout.motion.widget.ViewTransition     // Catch:{ XmlPullParserException -> 0x0182, IOException -> 0x017d }
            r6.<init>(r12, r1)     // Catch:{ XmlPullParserException -> 0x0182, IOException -> 0x017d }
            androidx.constraintlayout.motion.widget.ViewTransitionController r7 = r11.mViewTransitionController     // Catch:{ XmlPullParserException -> 0x0182, IOException -> 0x017d }
            r7.add(r6)     // Catch:{ XmlPullParserException -> 0x0182, IOException -> 0x017d }
            goto L_0x016e
        L_0x00bd:
            androidx.constraintlayout.motion.widget.KeyFrames r6 = new androidx.constraintlayout.motion.widget.KeyFrames     // Catch:{ XmlPullParserException -> 0x0182, IOException -> 0x017d }
            r6.<init>(r12, r1)     // Catch:{ XmlPullParserException -> 0x0182, IOException -> 0x017d }
            if (r4 == 0) goto L_0x016e
            java.util.ArrayList r7 = r4.mKeyFramesList     // Catch:{ XmlPullParserException -> 0x0182, IOException -> 0x017d }
            r7.add(r6)     // Catch:{ XmlPullParserException -> 0x0182, IOException -> 0x017d }
            goto L_0x016e
        L_0x00cd:
            r11.parseInclude((android.content.Context) r12, (org.xmlpull.v1.XmlPullParser) r1)     // Catch:{ XmlPullParserException -> 0x0182, IOException -> 0x017d }
            goto L_0x016e
        L_0x00d2:
            r11.parseConstraintSet(r12, r1)     // Catch:{ XmlPullParserException -> 0x0182, IOException -> 0x017d }
            goto L_0x016e
        L_0x00d7:
            androidx.constraintlayout.widget.StateSet r6 = new androidx.constraintlayout.widget.StateSet     // Catch:{ XmlPullParserException -> 0x0182, IOException -> 0x017d }
            r6.<init>(r12, r1)     // Catch:{ XmlPullParserException -> 0x0182, IOException -> 0x017d }
            r11.mStateSet = r6     // Catch:{ XmlPullParserException -> 0x0182, IOException -> 0x017d }
            goto L_0x016e
        L_0x00e0:
            if (r4 == 0) goto L_0x016e
            r4.addOnClick((android.content.Context) r12, (org.xmlpull.v1.XmlPullParser) r1)     // Catch:{ XmlPullParserException -> 0x0182, IOException -> 0x017d }
            goto L_0x016e
        L_0x00e7:
            if (r4 != 0) goto L_0x011b
            android.content.res.Resources r6 = r12.getResources()     // Catch:{ XmlPullParserException -> 0x0182, IOException -> 0x017d }
            java.lang.String r6 = r6.getResourceEntryName(r13)     // Catch:{ XmlPullParserException -> 0x0182, IOException -> 0x017d }
            int r7 = r1.getLineNumber()     // Catch:{ XmlPullParserException -> 0x0182, IOException -> 0x017d }
            java.lang.StringBuilder r9 = new java.lang.StringBuilder     // Catch:{ XmlPullParserException -> 0x0182, IOException -> 0x017d }
            r9.<init>()     // Catch:{ XmlPullParserException -> 0x0182, IOException -> 0x017d }
            java.lang.String r10 = " OnSwipe ("
            java.lang.StringBuilder r9 = r9.append(r10)     // Catch:{ XmlPullParserException -> 0x0182, IOException -> 0x017d }
            java.lang.StringBuilder r9 = r9.append(r6)     // Catch:{ XmlPullParserException -> 0x0182, IOException -> 0x017d }
            java.lang.String r10 = ".xml:"
            java.lang.StringBuilder r9 = r9.append(r10)     // Catch:{ XmlPullParserException -> 0x0182, IOException -> 0x017d }
            java.lang.StringBuilder r9 = r9.append(r7)     // Catch:{ XmlPullParserException -> 0x0182, IOException -> 0x017d }
            java.lang.String r10 = ")"
            java.lang.StringBuilder r9 = r9.append(r10)     // Catch:{ XmlPullParserException -> 0x0182, IOException -> 0x017d }
            java.lang.String r9 = r9.toString()     // Catch:{ XmlPullParserException -> 0x0182, IOException -> 0x017d }
            android.util.Log.v(r8, r9)     // Catch:{ XmlPullParserException -> 0x0182, IOException -> 0x017d }
        L_0x011b:
            if (r4 == 0) goto L_0x016e
            androidx.constraintlayout.motion.widget.TouchResponse r6 = new androidx.constraintlayout.motion.widget.TouchResponse     // Catch:{ XmlPullParserException -> 0x0182, IOException -> 0x017d }
            androidx.constraintlayout.motion.widget.MotionLayout r7 = r11.mMotionLayout     // Catch:{ XmlPullParserException -> 0x0182, IOException -> 0x017d }
            r6.<init>(r12, r7, r1)     // Catch:{ XmlPullParserException -> 0x0182, IOException -> 0x017d }
            androidx.constraintlayout.motion.widget.TouchResponse unused = r4.mTouchResponse = r6     // Catch:{ XmlPullParserException -> 0x0182, IOException -> 0x017d }
            goto L_0x016e
        L_0x0128:
            java.util.ArrayList<androidx.constraintlayout.motion.widget.MotionScene$Transition> r6 = r11.mTransitionList     // Catch:{ XmlPullParserException -> 0x0182, IOException -> 0x017d }
            androidx.constraintlayout.motion.widget.MotionScene$Transition r7 = new androidx.constraintlayout.motion.widget.MotionScene$Transition     // Catch:{ XmlPullParserException -> 0x0182, IOException -> 0x017d }
            r7.<init>(r11, r12, r1)     // Catch:{ XmlPullParserException -> 0x0182, IOException -> 0x017d }
            r4 = r7
            r6.add(r7)     // Catch:{ XmlPullParserException -> 0x0182, IOException -> 0x017d }
            androidx.constraintlayout.motion.widget.MotionScene$Transition r6 = r11.mCurrentTransition     // Catch:{ XmlPullParserException -> 0x0182, IOException -> 0x017d }
            if (r6 != 0) goto L_0x0150
            boolean r6 = r4.mIsAbstract     // Catch:{ XmlPullParserException -> 0x0182, IOException -> 0x017d }
            if (r6 != 0) goto L_0x0150
            r11.mCurrentTransition = r4     // Catch:{ XmlPullParserException -> 0x0182, IOException -> 0x017d }
            androidx.constraintlayout.motion.widget.TouchResponse r6 = r4.mTouchResponse     // Catch:{ XmlPullParserException -> 0x0182, IOException -> 0x017d }
            if (r6 == 0) goto L_0x0150
            androidx.constraintlayout.motion.widget.MotionScene$Transition r6 = r11.mCurrentTransition     // Catch:{ XmlPullParserException -> 0x0182, IOException -> 0x017d }
            androidx.constraintlayout.motion.widget.TouchResponse r6 = r6.mTouchResponse     // Catch:{ XmlPullParserException -> 0x0182, IOException -> 0x017d }
            boolean r7 = r11.mRtl     // Catch:{ XmlPullParserException -> 0x0182, IOException -> 0x017d }
            r6.setRTL(r7)     // Catch:{ XmlPullParserException -> 0x0182, IOException -> 0x017d }
        L_0x0150:
            boolean r6 = r4.mIsAbstract     // Catch:{ XmlPullParserException -> 0x0182, IOException -> 0x017d }
            if (r6 == 0) goto L_0x016e
            int r6 = r4.mConstraintSetEnd     // Catch:{ XmlPullParserException -> 0x0182, IOException -> 0x017d }
            if (r6 != r9) goto L_0x015f
            r11.mDefaultTransition = r4     // Catch:{ XmlPullParserException -> 0x0182, IOException -> 0x017d }
            goto L_0x0164
        L_0x015f:
            java.util.ArrayList<androidx.constraintlayout.motion.widget.MotionScene$Transition> r6 = r11.mAbstractTransitionList     // Catch:{ XmlPullParserException -> 0x0182, IOException -> 0x017d }
            r6.add(r4)     // Catch:{ XmlPullParserException -> 0x0182, IOException -> 0x017d }
        L_0x0164:
            java.util.ArrayList<androidx.constraintlayout.motion.widget.MotionScene$Transition> r6 = r11.mTransitionList     // Catch:{ XmlPullParserException -> 0x0182, IOException -> 0x017d }
            r6.remove(r4)     // Catch:{ XmlPullParserException -> 0x0182, IOException -> 0x017d }
            goto L_0x016e
        L_0x016a:
            r11.parseMotionSceneTags(r12, r1)     // Catch:{ XmlPullParserException -> 0x0182, IOException -> 0x017d }
        L_0x016e:
            goto L_0x0175
        L_0x016f:
            java.lang.String r6 = r1.getName()     // Catch:{ XmlPullParserException -> 0x0182, IOException -> 0x017d }
            r2 = r6
        L_0x0175:
            int r6 = r1.next()     // Catch:{ XmlPullParserException -> 0x0182, IOException -> 0x017d }
            r5 = r6
            goto L_0x000f
        L_0x017c:
            goto L_0x0186
        L_0x017d:
            r4 = move-exception
            r4.printStackTrace()
            goto L_0x0187
        L_0x0182:
            r4 = move-exception
            r4.printStackTrace()
        L_0x0186:
        L_0x0187:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.constraintlayout.motion.widget.MotionScene.load(android.content.Context, int):void");
    }

    private void parseMotionSceneTags(Context context, XmlPullParser parser) {
        TypedArray a = context.obtainStyledAttributes(Xml.asAttributeSet(parser), C0657R.styleable.MotionScene);
        int count = a.getIndexCount();
        for (int i = 0; i < count; i++) {
            int attr = a.getIndex(i);
            if (attr == C0657R.styleable.MotionScene_defaultDuration) {
                int i2 = a.getInt(attr, this.mDefaultDuration);
                this.mDefaultDuration = i2;
                if (i2 < 8) {
                    this.mDefaultDuration = 8;
                }
            } else if (attr == C0657R.styleable.MotionScene_layoutDuringTransition) {
                this.mLayoutDuringTransition = a.getInteger(attr, 0);
            }
        }
        a.recycle();
    }

    private int getId(Context context, String idString) {
        int id = -1;
        if (idString.contains("/")) {
            id = context.getResources().getIdentifier(idString.substring(idString.indexOf(47) + 1), "id", context.getPackageName());
            if (this.DEBUG_DESKTOP) {
                System.out.println("id getMap res = " + id);
            }
        }
        if (id != -1) {
            return id;
        }
        if (idString != null && idString.length() > 1) {
            return Integer.parseInt(idString.substring(1));
        }
        Log.e(TypedValues.MotionScene.NAME, "error in parsing id");
        return id;
    }

    private void parseInclude(Context context, XmlPullParser mainParser) {
        TypedArray a = context.obtainStyledAttributes(Xml.asAttributeSet(mainParser), C0657R.styleable.include);
        int N = a.getIndexCount();
        for (int i = 0; i < N; i++) {
            int attr = a.getIndex(i);
            if (attr == C0657R.styleable.include_constraintSet) {
                parseInclude(context, a.getResourceId(attr, -1));
            }
        }
        a.recycle();
    }

    /* access modifiers changed from: private */
    public int parseInclude(Context context, int resourceId) {
        XmlPullParser includeParser = context.getResources().getXml(resourceId);
        try {
            for (int eventType = includeParser.getEventType(); eventType != 1; eventType = includeParser.next()) {
                String tagName = includeParser.getName();
                if (2 == eventType && CONSTRAINTSET_TAG.equals(tagName)) {
                    return parseConstraintSet(context, includeParser);
                }
            }
            return -1;
        } catch (XmlPullParserException e) {
            e.printStackTrace();
            return -1;
        } catch (IOException e2) {
            e2.printStackTrace();
            return -1;
        }
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private int parseConstraintSet(android.content.Context r18, org.xmlpull.v1.XmlPullParser r19) {
        /*
            r17 = this;
            r1 = r17
            r2 = r18
            r3 = r19
            androidx.constraintlayout.widget.ConstraintSet r0 = new androidx.constraintlayout.widget.ConstraintSet
            r0.<init>()
            r4 = r0
            r5 = 0
            r4.setForceId(r5)
            int r6 = r19.getAttributeCount()
            r0 = -1
            r7 = -1
            r8 = 0
            r9 = r8
            r8 = r7
            r7 = r0
        L_0x001a:
            r11 = 1
            if (r9 >= r6) goto L_0x00f9
            java.lang.String r12 = r3.getAttributeName(r9)
            java.lang.String r13 = r3.getAttributeValue(r9)
            boolean r0 = r1.DEBUG_DESKTOP
            if (r0 == 0) goto L_0x0041
            java.io.PrintStream r0 = java.lang.System.out
            java.lang.StringBuilder r14 = new java.lang.StringBuilder
            r14.<init>()
            java.lang.String r15 = "id string = "
            java.lang.StringBuilder r14 = r14.append(r15)
            java.lang.StringBuilder r14 = r14.append(r13)
            java.lang.String r14 = r14.toString()
            r0.println(r14)
        L_0x0041:
            int r0 = r12.hashCode()
            r14 = 2
            switch(r0) {
                case -1496482599: goto L_0x005e;
                case -1153153640: goto L_0x0054;
                case 3355: goto L_0x004a;
                default: goto L_0x0049;
            }
        L_0x0049:
            goto L_0x0068
        L_0x004a:
            java.lang.String r0 = "id"
            boolean r0 = r12.equals(r0)
            if (r0 == 0) goto L_0x0049
            r0 = 0
            goto L_0x0069
        L_0x0054:
            java.lang.String r0 = "constraintRotate"
            boolean r0 = r12.equals(r0)
            if (r0 == 0) goto L_0x0049
            r0 = 2
            goto L_0x0069
        L_0x005e:
            java.lang.String r0 = "deriveConstraintsFrom"
            boolean r0 = r12.equals(r0)
            if (r0 == 0) goto L_0x0049
            r0 = 1
            goto L_0x0069
        L_0x0068:
            r0 = -1
        L_0x0069:
            switch(r0) {
                case 0: goto L_0x00dd;
                case 1: goto L_0x00d7;
                case 2: goto L_0x006e;
                default: goto L_0x006c;
            }
        L_0x006c:
            goto L_0x00f5
        L_0x006e:
            int r0 = java.lang.Integer.parseInt(r13)     // Catch:{ NumberFormatException -> 0x0076 }
            r4.mRotate = r0     // Catch:{ NumberFormatException -> 0x0076 }
            goto L_0x00f5
        L_0x0076:
            r0 = move-exception
            int r15 = r13.hashCode()
            r10 = 4
            r5 = 3
            switch(r15) {
                case -768416914: goto L_0x00b0;
                case 3317767: goto L_0x00a5;
                case 3387192: goto L_0x0099;
                case 108511772: goto L_0x008d;
                case 1954540437: goto L_0x0081;
                default: goto L_0x0080;
            }
        L_0x0080:
            goto L_0x00bc
        L_0x0081:
            java.lang.String r15 = "x_right"
            boolean r15 = r13.equals(r15)
            if (r15 == 0) goto L_0x0080
            r16 = 3
            goto L_0x00be
        L_0x008d:
            java.lang.String r15 = "right"
            boolean r15 = r13.equals(r15)
            if (r15 == 0) goto L_0x0080
            r16 = 1
            goto L_0x00be
        L_0x0099:
            java.lang.String r15 = "none"
            boolean r15 = r13.equals(r15)
            if (r15 == 0) goto L_0x0080
            r16 = 0
            goto L_0x00be
        L_0x00a5:
            java.lang.String r15 = "left"
            boolean r15 = r13.equals(r15)
            if (r15 == 0) goto L_0x0080
            r16 = 2
            goto L_0x00be
        L_0x00b0:
            java.lang.String r15 = "x_left"
            boolean r15 = r13.equals(r15)
            if (r15 == 0) goto L_0x0080
            r16 = 4
            goto L_0x00be
        L_0x00bc:
            r16 = -1
        L_0x00be:
            switch(r16) {
                case 0: goto L_0x00d3;
                case 1: goto L_0x00cf;
                case 2: goto L_0x00cb;
                case 3: goto L_0x00c7;
                case 4: goto L_0x00c3;
                default: goto L_0x00c1;
            }
        L_0x00c1:
            r5 = 0
            goto L_0x00f5
        L_0x00c3:
            r4.mRotate = r10
            r5 = 0
            goto L_0x00f5
        L_0x00c7:
            r4.mRotate = r5
            r5 = 0
            goto L_0x00f5
        L_0x00cb:
            r4.mRotate = r14
            r5 = 0
            goto L_0x00f5
        L_0x00cf:
            r4.mRotate = r11
            r5 = 0
            goto L_0x00f5
        L_0x00d3:
            r5 = 0
            r4.mRotate = r5
            goto L_0x00f5
        L_0x00d7:
            int r0 = r1.getId(r2, r13)
            r8 = r0
            goto L_0x00f5
        L_0x00dd:
            int r0 = r1.getId(r2, r13)
            java.util.HashMap<java.lang.String, java.lang.Integer> r7 = r1.mConstraintSetIdMap
            java.lang.String r10 = stripID(r13)
            java.lang.Integer r11 = java.lang.Integer.valueOf(r0)
            r7.put(r10, r11)
            java.lang.String r7 = androidx.constraintlayout.motion.widget.Debug.getName((android.content.Context) r2, (int) r0)
            r4.mIdString = r7
            r7 = r0
        L_0x00f5:
            int r9 = r9 + 1
            goto L_0x001a
        L_0x00f9:
            r5 = -1
            if (r7 == r5) goto L_0x0115
            androidx.constraintlayout.motion.widget.MotionLayout r0 = r1.mMotionLayout
            int r0 = r0.mDebugPath
            if (r0 == 0) goto L_0x0105
            r4.setValidateOnParse(r11)
        L_0x0105:
            r4.load((android.content.Context) r2, (org.xmlpull.v1.XmlPullParser) r3)
            r5 = -1
            if (r8 == r5) goto L_0x0110
            android.util.SparseIntArray r0 = r1.mDeriveMap
            r0.put(r7, r8)
        L_0x0110:
            android.util.SparseArray<androidx.constraintlayout.widget.ConstraintSet> r0 = r1.mConstraintSetMap
            r0.put(r7, r4)
        L_0x0115:
            return r7
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.constraintlayout.motion.widget.MotionScene.parseConstraintSet(android.content.Context, org.xmlpull.v1.XmlPullParser):int");
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean changed, int left, int top, int right, int bottom) {
    }

    public ConstraintSet getConstraintSet(Context context, String id) {
        if (this.DEBUG_DESKTOP) {
            System.out.println("id " + id);
            System.out.println("size " + this.mConstraintSetMap.size());
        }
        for (int i = 0; i < this.mConstraintSetMap.size(); i++) {
            int key = this.mConstraintSetMap.keyAt(i);
            String IdAsString = context.getResources().getResourceName(key);
            if (this.DEBUG_DESKTOP) {
                System.out.println("Id for <" + i + "> is <" + IdAsString + "> looking for <" + id + ">");
            }
            if (id.equals(IdAsString)) {
                return this.mConstraintSetMap.get(key);
            }
        }
        return null;
    }

    /* access modifiers changed from: package-private */
    public ConstraintSet getConstraintSet(int id) {
        return getConstraintSet(id, -1, -1);
    }

    /* access modifiers changed from: package-private */
    public ConstraintSet getConstraintSet(int id, int width, int height) {
        int cid;
        if (this.DEBUG_DESKTOP) {
            System.out.println("id " + id);
            System.out.println("size " + this.mConstraintSetMap.size());
        }
        StateSet stateSet = this.mStateSet;
        if (!(stateSet == null || (cid = stateSet.stateGetConstraintID(id, width, height)) == -1)) {
            id = cid;
        }
        if (this.mConstraintSetMap.get(id) != null) {
            return this.mConstraintSetMap.get(id);
        }
        Log.e(TypedValues.MotionScene.NAME, "Warning could not find ConstraintSet id/" + Debug.getName(this.mMotionLayout.getContext(), id) + " In MotionScene");
        SparseArray<ConstraintSet> sparseArray = this.mConstraintSetMap;
        return sparseArray.get(sparseArray.keyAt(0));
    }

    public void setConstraintSet(int id, ConstraintSet set) {
        this.mConstraintSetMap.put(id, set);
    }

    public void getKeyFrames(MotionController motionController) {
        Transition transition = this.mCurrentTransition;
        if (transition == null) {
            Transition transition2 = this.mDefaultTransition;
            if (transition2 != null) {
                Iterator it = transition2.mKeyFramesList.iterator();
                while (it.hasNext()) {
                    ((KeyFrames) it.next()).addFrames(motionController);
                }
                return;
            }
            return;
        }
        Iterator it2 = transition.mKeyFramesList.iterator();
        while (it2.hasNext()) {
            ((KeyFrames) it2.next()).addFrames(motionController);
        }
    }

    /* access modifiers changed from: package-private */
    public Key getKeyFrame(Context context, int type, int target, int position) {
        Transition transition = this.mCurrentTransition;
        if (transition == null) {
            return null;
        }
        Iterator it = transition.mKeyFramesList.iterator();
        while (it.hasNext()) {
            KeyFrames keyFrames = (KeyFrames) it.next();
            Iterator<Integer> it2 = keyFrames.getKeys().iterator();
            while (true) {
                if (it2.hasNext()) {
                    Integer integer = it2.next();
                    if (target == integer.intValue()) {
                        Iterator<Key> it3 = keyFrames.getKeyFramesForView(integer.intValue()).iterator();
                        while (it3.hasNext()) {
                            Key key = it3.next();
                            if (key.mFramePosition == position && key.mType == type) {
                                return key;
                            }
                        }
                        continue;
                    }
                }
            }
        }
        return null;
    }

    /* access modifiers changed from: package-private */
    public int getTransitionDirection(int stateId) {
        Iterator<Transition> it = this.mTransitionList.iterator();
        while (it.hasNext()) {
            if (it.next().mConstraintSetStart == stateId) {
                return 0;
            }
        }
        return 1;
    }

    /* access modifiers changed from: package-private */
    public boolean hasKeyFramePosition(View view, int position) {
        Transition transition = this.mCurrentTransition;
        if (transition == null) {
            return false;
        }
        Iterator it = transition.mKeyFramesList.iterator();
        while (it.hasNext()) {
            Iterator<Key> it2 = ((KeyFrames) it.next()).getKeyFramesForView(view.getId()).iterator();
            while (true) {
                if (it2.hasNext()) {
                    if (it2.next().mFramePosition == position) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public void setKeyframe(View view, int position, String name, Object value) {
        Transition transition = this.mCurrentTransition;
        if (transition != null) {
            Iterator it = transition.mKeyFramesList.iterator();
            while (it.hasNext()) {
                Iterator<Key> it2 = ((KeyFrames) it.next()).getKeyFramesForView(view.getId()).iterator();
                while (it2.hasNext()) {
                    if (it2.next().mFramePosition == position) {
                        float v = 0.0f;
                        if (value != null) {
                            v = ((Float) value).floatValue();
                        }
                        if (v == 0.0f) {
                        }
                        name.equalsIgnoreCase("app:PerpendicularPath_percent");
                    }
                }
            }
        }
    }

    public float getPathPercent(View view, int position) {
        return 0.0f;
    }

    /* access modifiers changed from: package-private */
    public boolean supportTouch() {
        Iterator<Transition> it = this.mTransitionList.iterator();
        while (it.hasNext()) {
            if (it.next().mTouchResponse != null) {
                return true;
            }
        }
        Transition transition = this.mCurrentTransition;
        if (transition == null || transition.mTouchResponse == null) {
            return false;
        }
        return true;
    }

    /* access modifiers changed from: package-private */
    public void processTouchEvent(MotionEvent event, int currentState, MotionLayout motionLayout) {
        MotionLayout.MotionTracker motionTracker;
        MotionEvent motionEvent;
        RectF cache = new RectF();
        if (this.mVelocityTracker == null) {
            this.mVelocityTracker = this.mMotionLayout.obtainVelocityTracker();
        }
        this.mVelocityTracker.addMovement(event);
        if (currentState != -1) {
            boolean z = false;
            switch (event.getAction()) {
                case 0:
                    this.mLastTouchX = event.getRawX();
                    this.mLastTouchY = event.getRawY();
                    this.mLastTouchDown = event;
                    this.mIgnoreTouch = false;
                    if (this.mCurrentTransition.mTouchResponse != null) {
                        RectF region = this.mCurrentTransition.mTouchResponse.getLimitBoundsTo(this.mMotionLayout, cache);
                        if (region == null || region.contains(this.mLastTouchDown.getX(), this.mLastTouchDown.getY())) {
                            RectF region2 = this.mCurrentTransition.mTouchResponse.getTouchRegion(this.mMotionLayout, cache);
                            if (region2 == null || region2.contains(this.mLastTouchDown.getX(), this.mLastTouchDown.getY())) {
                                this.mMotionOutsideRegion = false;
                            } else {
                                this.mMotionOutsideRegion = true;
                            }
                            this.mCurrentTransition.mTouchResponse.setDown(this.mLastTouchX, this.mLastTouchY);
                            return;
                        }
                        this.mLastTouchDown = null;
                        this.mIgnoreTouch = true;
                        return;
                    }
                    return;
                case 2:
                    if (!this.mIgnoreTouch) {
                        float dy = event.getRawY() - this.mLastTouchY;
                        float dx = event.getRawX() - this.mLastTouchX;
                        if ((((double) dx) != 0.0d || ((double) dy) != 0.0d) && (motionEvent = this.mLastTouchDown) != null) {
                            Transition transition = bestTransitionFor(currentState, dx, dy, motionEvent);
                            if (transition != null) {
                                motionLayout.setTransition(transition);
                                RectF region3 = this.mCurrentTransition.mTouchResponse.getTouchRegion(this.mMotionLayout, cache);
                                if (region3 != null && !region3.contains(this.mLastTouchDown.getX(), this.mLastTouchDown.getY())) {
                                    z = true;
                                }
                                this.mMotionOutsideRegion = z;
                                this.mCurrentTransition.mTouchResponse.setUpTouchEvent(this.mLastTouchX, this.mLastTouchY);
                                break;
                            }
                        } else {
                            return;
                        }
                    }
                    break;
            }
        }
        if (!this.mIgnoreTouch) {
            Transition transition2 = this.mCurrentTransition;
            if (!(transition2 == null || transition2.mTouchResponse == null || this.mMotionOutsideRegion)) {
                this.mCurrentTransition.mTouchResponse.processTouchEvent(event, this.mVelocityTracker, currentState, this);
            }
            this.mLastTouchX = event.getRawX();
            this.mLastTouchY = event.getRawY();
            if (event.getAction() == 1 && (motionTracker = this.mVelocityTracker) != null) {
                motionTracker.recycle();
                this.mVelocityTracker = null;
                if (motionLayout.mCurrentState != -1) {
                    autoTransition(motionLayout, motionLayout.mCurrentState);
                }
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void processScrollMove(float dx, float dy) {
        Transition transition = this.mCurrentTransition;
        if (transition != null && transition.mTouchResponse != null) {
            this.mCurrentTransition.mTouchResponse.scrollMove(dx, dy);
        }
    }

    /* access modifiers changed from: package-private */
    public void processScrollUp(float dx, float dy) {
        Transition transition = this.mCurrentTransition;
        if (transition != null && transition.mTouchResponse != null) {
            this.mCurrentTransition.mTouchResponse.scrollUp(dx, dy);
        }
    }

    /* access modifiers changed from: package-private */
    public float getProgressDirection(float dx, float dy) {
        Transition transition = this.mCurrentTransition;
        if (transition == null || transition.mTouchResponse == null) {
            return 0.0f;
        }
        return this.mCurrentTransition.mTouchResponse.getProgressDirection(dx, dy);
    }

    /* access modifiers changed from: package-private */
    public int getStartId() {
        Transition transition = this.mCurrentTransition;
        if (transition == null) {
            return -1;
        }
        return transition.mConstraintSetStart;
    }

    /* access modifiers changed from: package-private */
    public int getEndId() {
        Transition transition = this.mCurrentTransition;
        if (transition == null) {
            return -1;
        }
        return transition.mConstraintSetEnd;
    }

    public Interpolator getInterpolator() {
        switch (this.mCurrentTransition.mDefaultInterpolator) {
            case -2:
                return AnimationUtils.loadInterpolator(this.mMotionLayout.getContext(), this.mCurrentTransition.mDefaultInterpolatorID);
            case -1:
                final Easing easing = Easing.getInterpolator(this.mCurrentTransition.mDefaultInterpolatorString);
                return new Interpolator(this) {
                    public float getInterpolation(float v) {
                        return (float) easing.get((double) v);
                    }
                };
            case 0:
                return new AccelerateDecelerateInterpolator();
            case 1:
                return new AccelerateInterpolator();
            case 2:
                return new DecelerateInterpolator();
            case 3:
                return null;
            case 4:
                return new BounceInterpolator();
            case 5:
                return new OvershootInterpolator();
            case 6:
                return new AnticipateInterpolator();
            default:
                return null;
        }
    }

    public int getDuration() {
        Transition transition = this.mCurrentTransition;
        if (transition != null) {
            return transition.mDuration;
        }
        return this.mDefaultDuration;
    }

    public void setDuration(int duration) {
        Transition transition = this.mCurrentTransition;
        if (transition != null) {
            transition.setDuration(duration);
        } else {
            this.mDefaultDuration = duration;
        }
    }

    public int gatPathMotionArc() {
        Transition transition = this.mCurrentTransition;
        if (transition != null) {
            return transition.mPathMotionArc;
        }
        return -1;
    }

    public float getStaggered() {
        Transition transition = this.mCurrentTransition;
        if (transition != null) {
            return transition.mStagger;
        }
        return 0.0f;
    }

    /* access modifiers changed from: package-private */
    public float getMaxAcceleration() {
        Transition transition = this.mCurrentTransition;
        if (transition == null || transition.mTouchResponse == null) {
            return 0.0f;
        }
        return this.mCurrentTransition.mTouchResponse.getMaxAcceleration();
    }

    /* access modifiers changed from: package-private */
    public float getMaxVelocity() {
        Transition transition = this.mCurrentTransition;
        if (transition == null || transition.mTouchResponse == null) {
            return 0.0f;
        }
        return this.mCurrentTransition.mTouchResponse.getMaxVelocity();
    }

    /* access modifiers changed from: package-private */
    public float getSpringStiffiness() {
        Transition transition = this.mCurrentTransition;
        if (transition == null || transition.mTouchResponse == null) {
            return 0.0f;
        }
        return this.mCurrentTransition.mTouchResponse.getSpringStiffness();
    }

    /* access modifiers changed from: package-private */
    public float getSpringMass() {
        Transition transition = this.mCurrentTransition;
        if (transition == null || transition.mTouchResponse == null) {
            return 0.0f;
        }
        return this.mCurrentTransition.mTouchResponse.getSpringMass();
    }

    /* access modifiers changed from: package-private */
    public float getSpringDamping() {
        Transition transition = this.mCurrentTransition;
        if (transition == null || transition.mTouchResponse == null) {
            return 0.0f;
        }
        return this.mCurrentTransition.mTouchResponse.getSpringDamping();
    }

    /* access modifiers changed from: package-private */
    public float getSpringStopThreshold() {
        Transition transition = this.mCurrentTransition;
        if (transition == null || transition.mTouchResponse == null) {
            return 0.0f;
        }
        return this.mCurrentTransition.mTouchResponse.getSpringStopThreshold();
    }

    /* access modifiers changed from: package-private */
    public int getSpringBoundary() {
        Transition transition = this.mCurrentTransition;
        if (transition == null || transition.mTouchResponse == null) {
            return 0;
        }
        return this.mCurrentTransition.mTouchResponse.getSpringBoundary();
    }

    /* access modifiers changed from: package-private */
    public int getAutoCompleteMode() {
        Transition transition = this.mCurrentTransition;
        if (transition == null || transition.mTouchResponse == null) {
            return 0;
        }
        return this.mCurrentTransition.mTouchResponse.getAutoCompleteMode();
    }

    /* access modifiers changed from: package-private */
    public void setupTouch() {
        Transition transition = this.mCurrentTransition;
        if (transition != null && transition.mTouchResponse != null) {
            this.mCurrentTransition.mTouchResponse.setupTouch();
        }
    }

    /* access modifiers changed from: package-private */
    public boolean getMoveWhenScrollAtTop() {
        Transition transition = this.mCurrentTransition;
        if (transition == null || transition.mTouchResponse == null) {
            return false;
        }
        return this.mCurrentTransition.mTouchResponse.getMoveWhenScrollAtTop();
    }

    /* access modifiers changed from: package-private */
    public void readFallback(MotionLayout motionLayout) {
        int i = 0;
        while (i < this.mConstraintSetMap.size()) {
            int key = this.mConstraintSetMap.keyAt(i);
            if (hasCycleDependency(key)) {
                Log.e(TypedValues.MotionScene.NAME, "Cannot be derived from yourself");
                return;
            } else {
                readConstraintChain(key, motionLayout);
                i++;
            }
        }
    }

    private boolean hasCycleDependency(int key) {
        int derived = this.mDeriveMap.get(key);
        int len = this.mDeriveMap.size();
        while (derived > 0) {
            if (derived == key) {
                return true;
            }
            int len2 = len - 1;
            if (len < 0) {
                return true;
            }
            derived = this.mDeriveMap.get(derived);
            len = len2;
        }
        return false;
    }

    private void readConstraintChain(int key, MotionLayout motionLayout) {
        ConstraintSet cs = this.mConstraintSetMap.get(key);
        cs.derivedState = cs.mIdString;
        int derivedFromId = this.mDeriveMap.get(key);
        if (derivedFromId > 0) {
            readConstraintChain(derivedFromId, motionLayout);
            ConstraintSet derivedFrom = this.mConstraintSetMap.get(derivedFromId);
            if (derivedFrom == null) {
                Log.e(TypedValues.MotionScene.NAME, "ERROR! invalid deriveConstraintsFrom: @id/" + Debug.getName(this.mMotionLayout.getContext(), derivedFromId));
                return;
            } else {
                cs.derivedState += "/" + derivedFrom.derivedState;
                cs.readFallback(derivedFrom);
            }
        } else {
            cs.derivedState += "  layout";
            cs.readFallback((ConstraintLayout) motionLayout);
        }
        cs.applyDeltaFrom(cs);
    }

    public static String stripID(String id) {
        if (id == null) {
            return "";
        }
        int index = id.indexOf(47);
        if (index < 0) {
            return id;
        }
        return id.substring(index + 1);
    }

    public int lookUpConstraintId(String id) {
        Integer boxed = this.mConstraintSetIdMap.get(id);
        if (boxed == null) {
            return 0;
        }
        return boxed.intValue();
    }

    public String lookUpConstraintName(int id) {
        for (Map.Entry<String, Integer> entry : this.mConstraintSetIdMap.entrySet()) {
            Integer boxed = entry.getValue();
            if (boxed != null && boxed.intValue() == id) {
                return entry.getKey();
            }
        }
        return null;
    }

    public void disableAutoTransition(boolean disable) {
        this.mDisableAutoTransition = disable;
    }

    static String getLine(Context context, int resourceId, XmlPullParser pullParser) {
        return ".(" + Debug.getName(context, resourceId) + ".xml:" + pullParser.getLineNumber() + ") \"" + pullParser.getName() + "\"";
    }
}
