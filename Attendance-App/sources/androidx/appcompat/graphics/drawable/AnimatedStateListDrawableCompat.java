package androidx.appcompat.graphics.drawable;

import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.util.StateSet;
import androidx.appcompat.graphics.drawable.DrawableContainerCompat;
import androidx.appcompat.graphics.drawable.StateListDrawableCompat;
import androidx.appcompat.resources.C0540R;
import androidx.appcompat.resources.Compatibility;
import androidx.appcompat.widget.ResourceManagerInternal;
import androidx.collection.LongSparseArray;
import androidx.collection.SparseArrayCompat;
import androidx.core.content.res.TypedArrayUtils;
import androidx.core.graphics.drawable.TintAwareDrawable;
import androidx.core.util.ObjectsCompat;
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat;
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public class AnimatedStateListDrawableCompat extends StateListDrawableCompat implements TintAwareDrawable {
    private static final String ELEMENT_ITEM = "item";
    private static final String ELEMENT_TRANSITION = "transition";
    private static final String ITEM_MISSING_DRAWABLE_ERROR = ": <item> tag requires a 'drawable' attribute or child tag defining a drawable";
    private static final String LOGTAG = AnimatedStateListDrawableCompat.class.getSimpleName();
    private static final String TRANSITION_MISSING_DRAWABLE_ERROR = ": <transition> tag requires a 'drawable' attribute or child tag defining a drawable";
    private static final String TRANSITION_MISSING_FROM_TO_ID = ": <transition> tag requires 'fromId' & 'toId' attributes";
    private boolean mMutated;
    private AnimatedStateListState mState;
    private Transition mTransition;
    private int mTransitionFromIndex;
    private int mTransitionToIndex;

    public AnimatedStateListDrawableCompat() {
        this((AnimatedStateListState) null, (Resources) null);
    }

    AnimatedStateListDrawableCompat(AnimatedStateListState state, Resources res) {
        super((StateListDrawableCompat.StateListState) null);
        this.mTransitionToIndex = -1;
        this.mTransitionFromIndex = -1;
        setConstantState(new AnimatedStateListState(state, this, res));
        onStateChange(getState());
        jumpToCurrentState();
    }

    /* JADX WARNING: Removed duplicated region for block: B:11:0x0022 A[Catch:{ XmlPullParserException -> 0x0031, IOException -> 0x002a }] */
    /* JADX WARNING: Removed duplicated region for block: B:9:0x001d A[Catch:{ XmlPullParserException -> 0x0031, IOException -> 0x002a }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static androidx.appcompat.graphics.drawable.AnimatedStateListDrawableCompat create(android.content.Context r7, int r8, android.content.res.Resources.Theme r9) {
        /*
            java.lang.String r0 = "parser error"
            android.content.res.Resources r1 = r7.getResources()     // Catch:{ XmlPullParserException -> 0x0031, IOException -> 0x002a }
            android.content.res.XmlResourceParser r2 = r1.getXml(r8)     // Catch:{ XmlPullParserException -> 0x0031, IOException -> 0x002a }
            android.util.AttributeSet r3 = android.util.Xml.asAttributeSet(r2)     // Catch:{ XmlPullParserException -> 0x0031, IOException -> 0x002a }
        L_0x000f:
            int r4 = r2.next()     // Catch:{ XmlPullParserException -> 0x0031, IOException -> 0x002a }
            r5 = r4
            r6 = 2
            if (r4 == r6) goto L_0x001b
            r4 = 1
            if (r5 == r4) goto L_0x001b
            goto L_0x000f
        L_0x001b:
            if (r5 != r6) goto L_0x0022
            androidx.appcompat.graphics.drawable.AnimatedStateListDrawableCompat r0 = createFromXmlInner(r7, r1, r2, r3, r9)     // Catch:{ XmlPullParserException -> 0x0031, IOException -> 0x002a }
            return r0
        L_0x0022:
            org.xmlpull.v1.XmlPullParserException r4 = new org.xmlpull.v1.XmlPullParserException     // Catch:{ XmlPullParserException -> 0x0031, IOException -> 0x002a }
            java.lang.String r6 = "No start tag found"
            r4.<init>(r6)     // Catch:{ XmlPullParserException -> 0x0031, IOException -> 0x002a }
            throw r4     // Catch:{ XmlPullParserException -> 0x0031, IOException -> 0x002a }
        L_0x002a:
            r1 = move-exception
            java.lang.String r2 = LOGTAG
            android.util.Log.e(r2, r0, r1)
            goto L_0x0038
        L_0x0031:
            r1 = move-exception
            java.lang.String r2 = LOGTAG
            android.util.Log.e(r2, r0, r1)
        L_0x0038:
            r0 = 0
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.appcompat.graphics.drawable.AnimatedStateListDrawableCompat.create(android.content.Context, int, android.content.res.Resources$Theme):androidx.appcompat.graphics.drawable.AnimatedStateListDrawableCompat");
    }

    public static AnimatedStateListDrawableCompat createFromXmlInner(Context context, Resources resources, XmlPullParser parser, AttributeSet attrs, Resources.Theme theme) throws IOException, XmlPullParserException {
        String name = parser.getName();
        if (name.equals("animated-selector")) {
            AnimatedStateListDrawableCompat asl = new AnimatedStateListDrawableCompat();
            asl.inflate(context, resources, parser, attrs, theme);
            return asl;
        }
        throw new XmlPullParserException(parser.getPositionDescription() + ": invalid animated-selector tag " + name);
    }

    public void inflate(Context context, Resources resources, XmlPullParser parser, AttributeSet attrs, Resources.Theme theme) throws XmlPullParserException, IOException {
        TypedArray a = TypedArrayUtils.obtainAttributes(resources, theme, attrs, C0540R.styleable.AnimatedStateListDrawableCompat);
        setVisible(a.getBoolean(C0540R.styleable.AnimatedStateListDrawableCompat_android_visible, true), true);
        updateStateFromTypedArray(a);
        updateDensity(resources);
        a.recycle();
        inflateChildElements(context, resources, parser, attrs, theme);
        init();
    }

    public boolean setVisible(boolean visible, boolean restart) {
        boolean changed = super.setVisible(visible, restart);
        Transition transition = this.mTransition;
        if (transition != null && (changed || restart)) {
            if (visible) {
                transition.start();
            } else {
                jumpToCurrentState();
            }
        }
        return changed;
    }

    public void addState(int[] stateSet, Drawable drawable, int id) {
        ObjectsCompat.requireNonNull(drawable);
        this.mState.addStateSet(stateSet, drawable, id);
        onStateChange(getState());
    }

    public <T extends Drawable & Animatable> void addTransition(int fromId, int toId, T transition, boolean reversible) {
        ObjectsCompat.requireNonNull(transition);
        this.mState.addTransition(fromId, toId, transition, reversible);
    }

    public boolean isStateful() {
        return true;
    }

    public void jumpToCurrentState() {
        super.jumpToCurrentState();
        Transition transition = this.mTransition;
        if (transition != null) {
            transition.stop();
            this.mTransition = null;
            selectDrawable(this.mTransitionToIndex);
            this.mTransitionToIndex = -1;
            this.mTransitionFromIndex = -1;
        }
    }

    /* access modifiers changed from: protected */
    public boolean onStateChange(int[] stateSet) {
        int targetIndex = this.mState.indexOfKeyframe(stateSet);
        boolean changed = targetIndex != getCurrentIndex() && (selectTransition(targetIndex) || selectDrawable(targetIndex));
        Drawable current = getCurrent();
        if (current != null) {
            return changed | current.setState(stateSet);
        }
        return changed;
    }

    private boolean selectTransition(int toIndex) {
        int fromIndex;
        int transitionIndex;
        Transition transition;
        Transition currentTransition = this.mTransition;
        if (currentTransition == null) {
            fromIndex = getCurrentIndex();
        } else if (toIndex == this.mTransitionToIndex) {
            return true;
        } else {
            if (toIndex != this.mTransitionFromIndex || !currentTransition.canReverse()) {
                fromIndex = this.mTransitionToIndex;
                currentTransition.stop();
            } else {
                currentTransition.reverse();
                this.mTransitionToIndex = this.mTransitionFromIndex;
                this.mTransitionFromIndex = toIndex;
                return true;
            }
        }
        this.mTransition = null;
        this.mTransitionFromIndex = -1;
        this.mTransitionToIndex = -1;
        AnimatedStateListState state = this.mState;
        int fromId = state.getKeyframeIdAt(fromIndex);
        int toId = state.getKeyframeIdAt(toIndex);
        if (toId == 0 || fromId == 0 || (transitionIndex = state.indexOfTransition(fromId, toId)) < 0) {
            return false;
        }
        boolean hasReversibleFlag = state.transitionHasReversibleFlag(fromId, toId);
        selectDrawable(transitionIndex);
        Drawable d = getCurrent();
        if (d instanceof AnimationDrawable) {
            transition = new AnimationDrawableTransition((AnimationDrawable) d, state.isTransitionReversed(fromId, toId), hasReversibleFlag);
        } else if (d instanceof AnimatedVectorDrawableCompat) {
            transition = new AnimatedVectorDrawableTransition((AnimatedVectorDrawableCompat) d);
        } else if (!(d instanceof Animatable)) {
            return false;
        } else {
            transition = new AnimatableTransition((Animatable) d);
        }
        transition.start();
        this.mTransition = transition;
        this.mTransitionFromIndex = fromIndex;
        this.mTransitionToIndex = toIndex;
        return true;
    }

    private static abstract class Transition {
        public abstract void start();

        public abstract void stop();

        private Transition() {
        }

        public void reverse() {
        }

        public boolean canReverse() {
            return false;
        }
    }

    private static class AnimatableTransition extends Transition {

        /* renamed from: mA */
        private final Animatable f978mA;

        AnimatableTransition(Animatable a) {
            super();
            this.f978mA = a;
        }

        public void start() {
            this.f978mA.start();
        }

        public void stop() {
            this.f978mA.stop();
        }
    }

    private static class AnimationDrawableTransition extends Transition {
        private final ObjectAnimator mAnim;
        private final boolean mHasReversibleFlag;

        AnimationDrawableTransition(AnimationDrawable ad, boolean reversed, boolean hasReversibleFlag) {
            super();
            int frameCount = ad.getNumberOfFrames();
            int fromFrame = reversed ? frameCount - 1 : 0;
            int toFrame = reversed ? 0 : frameCount - 1;
            FrameInterpolator interp = new FrameInterpolator(ad, reversed);
            ObjectAnimator anim = ObjectAnimator.ofInt(ad, "currentIndex", new int[]{fromFrame, toFrame});
            Compatibility.Api18Impl.setAutoCancel(anim, true);
            anim.setDuration((long) interp.getTotalDuration());
            anim.setInterpolator(interp);
            this.mHasReversibleFlag = hasReversibleFlag;
            this.mAnim = anim;
        }

        public boolean canReverse() {
            return this.mHasReversibleFlag;
        }

        public void start() {
            this.mAnim.start();
        }

        public void reverse() {
            this.mAnim.reverse();
        }

        public void stop() {
            this.mAnim.cancel();
        }
    }

    private static class AnimatedVectorDrawableTransition extends Transition {
        private final AnimatedVectorDrawableCompat mAvd;

        AnimatedVectorDrawableTransition(AnimatedVectorDrawableCompat avd) {
            super();
            this.mAvd = avd;
        }

        public void start() {
            this.mAvd.start();
        }

        public void stop() {
            this.mAvd.stop();
        }
    }

    private void updateStateFromTypedArray(TypedArray a) {
        AnimatedStateListState state = this.mState;
        if (Build.VERSION.SDK_INT >= 21) {
            state.mChangingConfigurations |= Compatibility.Api21Impl.getChangingConfigurations(a);
        }
        state.setVariablePadding(a.getBoolean(C0540R.styleable.AnimatedStateListDrawableCompat_android_variablePadding, state.mVariablePadding));
        state.setConstantSize(a.getBoolean(C0540R.styleable.AnimatedStateListDrawableCompat_android_constantSize, state.mConstantSize));
        state.setEnterFadeDuration(a.getInt(C0540R.styleable.AnimatedStateListDrawableCompat_android_enterFadeDuration, state.mEnterFadeDuration));
        state.setExitFadeDuration(a.getInt(C0540R.styleable.AnimatedStateListDrawableCompat_android_exitFadeDuration, state.mExitFadeDuration));
        setDither(a.getBoolean(C0540R.styleable.AnimatedStateListDrawableCompat_android_dither, state.mDither));
    }

    private void init() {
        onStateChange(getState());
    }

    private void inflateChildElements(Context context, Resources resources, XmlPullParser parser, AttributeSet attrs, Resources.Theme theme) throws XmlPullParserException, IOException {
        int innerDepth = parser.getDepth() + 1;
        while (true) {
            int next = parser.next();
            int type = next;
            if (next != 1) {
                int depth = parser.getDepth();
                int depth2 = depth;
                if (depth < innerDepth && type == 3) {
                    return;
                }
                if (type == 2 && depth2 <= innerDepth) {
                    if (parser.getName().equals(ELEMENT_ITEM)) {
                        parseItem(context, resources, parser, attrs, theme);
                    } else if (parser.getName().equals(ELEMENT_TRANSITION)) {
                        parseTransition(context, resources, parser, attrs, theme);
                    }
                }
            } else {
                return;
            }
        }
    }

    private int parseTransition(Context context, Resources resources, XmlPullParser parser, AttributeSet attrs, Resources.Theme theme) throws XmlPullParserException, IOException {
        int next;
        int type;
        Resources.Theme theme2 = theme;
        TypedArray a = TypedArrayUtils.obtainAttributes(resources, theme2, attrs, C0540R.styleable.AnimatedStateListDrawableTransition);
        int fromId = a.getResourceId(C0540R.styleable.AnimatedStateListDrawableTransition_android_fromId, -1);
        int toId = a.getResourceId(C0540R.styleable.AnimatedStateListDrawableTransition_android_toId, -1);
        Drawable dr = null;
        int drawableId = a.getResourceId(C0540R.styleable.AnimatedStateListDrawableTransition_android_drawable, -1);
        if (drawableId > 0) {
            dr = ResourceManagerInternal.get().getDrawable(context, drawableId);
        } else {
            Context context2 = context;
        }
        boolean reversible = a.getBoolean(C0540R.styleable.AnimatedStateListDrawableTransition_android_reversible, false);
        a.recycle();
        if (dr == null) {
            do {
                next = parser.next();
                type = next;
            } while (next == 4);
            if (type != 2) {
                throw new XmlPullParserException(parser.getPositionDescription() + TRANSITION_MISSING_DRAWABLE_ERROR);
            } else if (parser.getName().equals("animated-vector")) {
                dr = AnimatedVectorDrawableCompat.createFromXmlInner(context, resources, parser, attrs, theme);
            } else if (Build.VERSION.SDK_INT >= 21) {
                dr = Compatibility.Api21Impl.createFromXmlInner(resources, parser, attrs, theme);
            } else {
                dr = Drawable.createFromXmlInner(resources, parser, attrs);
            }
        }
        if (dr == null) {
            throw new XmlPullParserException(parser.getPositionDescription() + TRANSITION_MISSING_DRAWABLE_ERROR);
        } else if (fromId != -1 && toId != -1) {
            return this.mState.addTransition(fromId, toId, dr, reversible);
        } else {
            throw new XmlPullParserException(parser.getPositionDescription() + TRANSITION_MISSING_FROM_TO_ID);
        }
    }

    private int parseItem(Context context, Resources resources, XmlPullParser parser, AttributeSet attrs, Resources.Theme theme) throws XmlPullParserException, IOException {
        int next;
        int type;
        TypedArray a = TypedArrayUtils.obtainAttributes(resources, theme, attrs, C0540R.styleable.AnimatedStateListDrawableItem);
        int keyframeId = a.getResourceId(C0540R.styleable.AnimatedStateListDrawableItem_android_id, 0);
        Drawable dr = null;
        int drawableId = a.getResourceId(C0540R.styleable.AnimatedStateListDrawableItem_android_drawable, -1);
        if (drawableId > 0) {
            dr = ResourceManagerInternal.get().getDrawable(context, drawableId);
        }
        a.recycle();
        int[] states = extractStateSet(attrs);
        if (dr == null) {
            do {
                next = parser.next();
                type = next;
            } while (next == 4);
            if (type != 2) {
                throw new XmlPullParserException(parser.getPositionDescription() + ITEM_MISSING_DRAWABLE_ERROR);
            } else if (parser.getName().equals("vector")) {
                dr = VectorDrawableCompat.createFromXmlInner(resources, parser, attrs, theme);
            } else if (Build.VERSION.SDK_INT >= 21) {
                dr = Compatibility.Api21Impl.createFromXmlInner(resources, parser, attrs, theme);
            } else {
                dr = Drawable.createFromXmlInner(resources, parser, attrs);
            }
        }
        if (dr != null) {
            return this.mState.addStateSet(states, dr, keyframeId);
        }
        throw new XmlPullParserException(parser.getPositionDescription() + ITEM_MISSING_DRAWABLE_ERROR);
    }

    public Drawable mutate() {
        if (!this.mMutated && super.mutate() == this) {
            this.mState.mutate();
            this.mMutated = true;
        }
        return this;
    }

    /* access modifiers changed from: package-private */
    public AnimatedStateListState cloneConstantState() {
        return new AnimatedStateListState(this.mState, this, (Resources) null);
    }

    /* access modifiers changed from: package-private */
    public void clearMutated() {
        super.clearMutated();
        this.mMutated = false;
    }

    static class AnimatedStateListState extends StateListDrawableCompat.StateListState {
        private static final long REVERSED_BIT = 4294967296L;
        private static final long REVERSIBLE_FLAG_BIT = 8589934592L;
        SparseArrayCompat<Integer> mStateIds;
        LongSparseArray<Long> mTransitions;

        AnimatedStateListState(AnimatedStateListState orig, AnimatedStateListDrawableCompat owner, Resources res) {
            super(orig, owner, res);
            if (orig != null) {
                this.mTransitions = orig.mTransitions;
                this.mStateIds = orig.mStateIds;
                return;
            }
            this.mTransitions = new LongSparseArray<>();
            this.mStateIds = new SparseArrayCompat<>();
        }

        /* access modifiers changed from: package-private */
        public void mutate() {
            this.mTransitions = this.mTransitions.clone();
            this.mStateIds = this.mStateIds.clone();
        }

        /* access modifiers changed from: package-private */
        public int addTransition(int fromId, int toId, Drawable anim, boolean reversible) {
            int pos = super.addChild(anim);
            long keyFromTo = generateTransitionKey(fromId, toId);
            long reversibleBit = 0;
            if (reversible) {
                reversibleBit = REVERSIBLE_FLAG_BIT;
            }
            this.mTransitions.append(keyFromTo, Long.valueOf(((long) pos) | reversibleBit));
            if (reversible) {
                this.mTransitions.append(generateTransitionKey(toId, fromId), Long.valueOf(((long) pos) | REVERSED_BIT | reversibleBit));
            } else {
                int i = fromId;
                int i2 = toId;
            }
            return pos;
        }

        /* access modifiers changed from: package-private */
        public int addStateSet(int[] stateSet, Drawable drawable, int id) {
            int index = super.addStateSet(stateSet, drawable);
            this.mStateIds.put(index, Integer.valueOf(id));
            return index;
        }

        /* access modifiers changed from: package-private */
        public int indexOfKeyframe(int[] stateSet) {
            int index = super.indexOfStateSet(stateSet);
            if (index >= 0) {
                return index;
            }
            return super.indexOfStateSet(StateSet.WILD_CARD);
        }

        /* access modifiers changed from: package-private */
        public int getKeyframeIdAt(int index) {
            if (index < 0) {
                return 0;
            }
            return this.mStateIds.get(index, 0).intValue();
        }

        /* access modifiers changed from: package-private */
        public int indexOfTransition(int fromId, int toId) {
            return (int) this.mTransitions.get(generateTransitionKey(fromId, toId), -1L).longValue();
        }

        /* access modifiers changed from: package-private */
        public boolean isTransitionReversed(int fromId, int toId) {
            return (this.mTransitions.get(generateTransitionKey(fromId, toId), -1L).longValue() & REVERSED_BIT) != 0;
        }

        /* access modifiers changed from: package-private */
        public boolean transitionHasReversibleFlag(int fromId, int toId) {
            return (this.mTransitions.get(generateTransitionKey(fromId, toId), -1L).longValue() & REVERSIBLE_FLAG_BIT) != 0;
        }

        public Drawable newDrawable() {
            return new AnimatedStateListDrawableCompat(this, (Resources) null);
        }

        public Drawable newDrawable(Resources res) {
            return new AnimatedStateListDrawableCompat(this, res);
        }

        private static long generateTransitionKey(int fromId, int toId) {
            return (((long) fromId) << 32) | ((long) toId);
        }
    }

    /* access modifiers changed from: package-private */
    public void setConstantState(DrawableContainerCompat.DrawableContainerState state) {
        super.setConstantState(state);
        if (state instanceof AnimatedStateListState) {
            this.mState = (AnimatedStateListState) state;
        }
    }

    private static class FrameInterpolator implements TimeInterpolator {
        private int[] mFrameTimes;
        private int mFrames;
        private int mTotalDuration;

        FrameInterpolator(AnimationDrawable d, boolean reversed) {
            updateFrames(d, reversed);
        }

        /* access modifiers changed from: package-private */
        public int updateFrames(AnimationDrawable d, boolean reversed) {
            int frameCount = d.getNumberOfFrames();
            this.mFrames = frameCount;
            int[] iArr = this.mFrameTimes;
            if (iArr == null || iArr.length < frameCount) {
                this.mFrameTimes = new int[frameCount];
            }
            int[] frameTimes = this.mFrameTimes;
            int totalDuration = 0;
            for (int i = 0; i < frameCount; i++) {
                int duration = d.getDuration(reversed ? (frameCount - i) - 1 : i);
                frameTimes[i] = duration;
                totalDuration += duration;
            }
            this.mTotalDuration = totalDuration;
            return totalDuration;
        }

        /* access modifiers changed from: package-private */
        public int getTotalDuration() {
            return this.mTotalDuration;
        }

        public float getInterpolation(float input) {
            float frameElapsed;
            int frameCount = this.mFrames;
            int[] frameTimes = this.mFrameTimes;
            int remaining = (int) ((((float) this.mTotalDuration) * input) + 0.5f);
            int i = 0;
            while (i < frameCount && remaining >= frameTimes[i]) {
                remaining -= frameTimes[i];
                i++;
            }
            if (i < frameCount) {
                frameElapsed = ((float) remaining) / ((float) this.mTotalDuration);
            } else {
                frameElapsed = 0.0f;
            }
            return (((float) i) / ((float) frameCount)) + frameElapsed;
        }
    }
}
