package androidx.appcompat.graphics.drawable;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.util.StateSet;
import androidx.appcompat.graphics.drawable.DrawableContainerCompat;
import androidx.appcompat.resources.C0540R;
import androidx.appcompat.resources.Compatibility;
import androidx.appcompat.widget.ResourceManagerInternal;
import androidx.core.content.res.TypedArrayUtils;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public class StateListDrawableCompat extends DrawableContainerCompat {
    private static final boolean DEBUG = false;
    private static final String TAG = "StateListDrawableCompat";
    private boolean mMutated;
    private StateListState mStateListState;

    public StateListDrawableCompat() {
        this((StateListState) null, (Resources) null);
    }

    public void addState(int[] stateSet, Drawable drawable) {
        if (drawable != null) {
            this.mStateListState.addStateSet(stateSet, drawable);
            onStateChange(getState());
        }
    }

    public boolean isStateful() {
        return true;
    }

    /* access modifiers changed from: protected */
    public boolean onStateChange(int[] stateSet) {
        boolean changed = super.onStateChange(stateSet);
        int idx = this.mStateListState.indexOfStateSet(stateSet);
        if (idx < 0) {
            idx = this.mStateListState.indexOfStateSet(StateSet.WILD_CARD);
        }
        return selectDrawable(idx) || changed;
    }

    public void inflate(Context context, Resources r, XmlPullParser parser, AttributeSet attrs, Resources.Theme theme) throws XmlPullParserException, IOException {
        TypedArray a = TypedArrayUtils.obtainAttributes(r, theme, attrs, C0540R.styleable.StateListDrawable);
        setVisible(a.getBoolean(C0540R.styleable.StateListDrawable_android_visible, true), true);
        updateStateFromTypedArray(a);
        updateDensity(r);
        a.recycle();
        inflateChildElements(context, r, parser, attrs, theme);
        onStateChange(getState());
    }

    private void updateStateFromTypedArray(TypedArray a) {
        StateListState state = this.mStateListState;
        if (Build.VERSION.SDK_INT >= 21) {
            state.mChangingConfigurations |= Compatibility.Api21Impl.getChangingConfigurations(a);
        }
        state.mVariablePadding = a.getBoolean(C0540R.styleable.StateListDrawable_android_variablePadding, state.mVariablePadding);
        state.mConstantSize = a.getBoolean(C0540R.styleable.StateListDrawable_android_constantSize, state.mConstantSize);
        state.mEnterFadeDuration = a.getInt(C0540R.styleable.StateListDrawable_android_enterFadeDuration, state.mEnterFadeDuration);
        state.mExitFadeDuration = a.getInt(C0540R.styleable.StateListDrawable_android_exitFadeDuration, state.mExitFadeDuration);
        state.mDither = a.getBoolean(C0540R.styleable.StateListDrawable_android_dither, state.mDither);
    }

    private void inflateChildElements(Context context, Resources r, XmlPullParser parser, AttributeSet attrs, Resources.Theme theme) throws XmlPullParserException, IOException {
        int type;
        AttributeSet attributeSet = attrs;
        StateListState state = this.mStateListState;
        int i = 1;
        int innerDepth = parser.getDepth() + 1;
        while (true) {
            int next = parser.next();
            int type2 = next;
            if (next != i) {
                int depth = parser.getDepth();
                int depth2 = depth;
                if (depth < innerDepth && type2 == 3) {
                    Context context2 = context;
                    Resources resources = r;
                    Resources.Theme theme2 = theme;
                    return;
                } else if (type2 == 2) {
                    if (depth2 > innerDepth) {
                        Context context3 = context;
                        Resources resources2 = r;
                        Resources.Theme theme3 = theme;
                        i = 1;
                    } else if (!parser.getName().equals("item")) {
                        continue;
                    } else {
                        TypedArray a = TypedArrayUtils.obtainAttributes(r, theme, attributeSet, C0540R.styleable.StateListDrawableItem);
                        Drawable dr = null;
                        int drawableId = a.getResourceId(C0540R.styleable.StateListDrawableItem_android_drawable, -1);
                        if (drawableId > 0) {
                            dr = ResourceManagerInternal.get().getDrawable(context, drawableId);
                        } else {
                            Context context4 = context;
                        }
                        a.recycle();
                        int[] states = extractStateSet(attributeSet);
                        if (dr == null) {
                            while (true) {
                                int next2 = parser.next();
                                type = next2;
                                if (next2 != 4) {
                                    break;
                                }
                            }
                            if (type != 2) {
                                throw new XmlPullParserException(parser.getPositionDescription() + ": <item> tag requires a 'drawable' attribute or child tag defining a drawable");
                            } else if (Build.VERSION.SDK_INT >= 21) {
                                dr = Compatibility.Api21Impl.createFromXmlInner(r, parser, attrs, theme);
                            } else {
                                dr = Drawable.createFromXmlInner(r, parser, attrs);
                            }
                        }
                        state.addStateSet(states, dr);
                        i = 1;
                    }
                }
            } else {
                Context context5 = context;
                Resources resources3 = r;
                Resources.Theme theme4 = theme;
                return;
            }
        }
    }

    /* access modifiers changed from: package-private */
    public int[] extractStateSet(AttributeSet attrs) {
        int j = 0;
        int numAttrs = attrs.getAttributeCount();
        int[] states = new int[numAttrs];
        for (int i = 0; i < numAttrs; i++) {
            int stateResId = attrs.getAttributeNameResource(i);
            switch (stateResId) {
                case 0:
                case 16842960:
                case 16843161:
                    break;
                default:
                    int j2 = j + 1;
                    states[j] = attrs.getAttributeBooleanValue(i, false) ? stateResId : -stateResId;
                    j = j2;
                    break;
            }
        }
        return StateSet.trimStateSet(states, j);
    }

    /* access modifiers changed from: package-private */
    public StateListState getStateListState() {
        return this.mStateListState;
    }

    /* access modifiers changed from: package-private */
    public int getStateCount() {
        return this.mStateListState.getChildCount();
    }

    /* access modifiers changed from: package-private */
    public int[] getStateSet(int index) {
        return this.mStateListState.mStateSets[index];
    }

    /* access modifiers changed from: package-private */
    public Drawable getStateDrawable(int index) {
        return this.mStateListState.getChild(index);
    }

    /* access modifiers changed from: package-private */
    public int getStateDrawableIndex(int[] stateSet) {
        return this.mStateListState.indexOfStateSet(stateSet);
    }

    public Drawable mutate() {
        if (!this.mMutated && super.mutate() == this) {
            this.mStateListState.mutate();
            this.mMutated = true;
        }
        return this;
    }

    /* access modifiers changed from: package-private */
    public StateListState cloneConstantState() {
        return new StateListState(this.mStateListState, this, (Resources) null);
    }

    /* access modifiers changed from: package-private */
    public void clearMutated() {
        super.clearMutated();
        this.mMutated = false;
    }

    static class StateListState extends DrawableContainerCompat.DrawableContainerState {
        int[][] mStateSets;

        StateListState(StateListState orig, StateListDrawableCompat owner, Resources res) {
            super(orig, owner, res);
            if (orig != null) {
                this.mStateSets = orig.mStateSets;
            } else {
                this.mStateSets = new int[getCapacity()][];
            }
        }

        /* access modifiers changed from: package-private */
        public void mutate() {
            int[][] iArr = this.mStateSets;
            int[][] stateSets = new int[iArr.length][];
            for (int i = iArr.length - 1; i >= 0; i--) {
                int[] iArr2 = this.mStateSets[i];
                stateSets[i] = iArr2 != null ? (int[]) iArr2.clone() : null;
            }
            this.mStateSets = stateSets;
        }

        /* access modifiers changed from: package-private */
        public int addStateSet(int[] stateSet, Drawable drawable) {
            int pos = addChild(drawable);
            this.mStateSets[pos] = stateSet;
            return pos;
        }

        /* access modifiers changed from: package-private */
        public int indexOfStateSet(int[] stateSet) {
            int[][] stateSets = this.mStateSets;
            int count = getChildCount();
            for (int i = 0; i < count; i++) {
                if (StateSet.stateSetMatches(stateSets[i], stateSet)) {
                    return i;
                }
            }
            return -1;
        }

        public Drawable newDrawable() {
            return new StateListDrawableCompat(this, (Resources) null);
        }

        public Drawable newDrawable(Resources res) {
            return new StateListDrawableCompat(this, res);
        }

        public void growArray(int oldSize, int newSize) {
            super.growArray(oldSize, newSize);
            int[][] newStateSets = new int[newSize][];
            System.arraycopy(this.mStateSets, 0, newStateSets, 0, oldSize);
            this.mStateSets = newStateSets;
        }
    }

    public void applyTheme(Resources.Theme theme) {
        super.applyTheme(theme);
        onStateChange(getState());
    }

    /* access modifiers changed from: package-private */
    public void setConstantState(DrawableContainerCompat.DrawableContainerState state) {
        super.setConstantState(state);
        if (state instanceof StateListState) {
            this.mStateListState = (StateListState) state;
        }
    }

    StateListDrawableCompat(StateListState state, Resources res) {
        setConstantState(new StateListState(state, this, res));
        onStateChange(getState());
    }

    StateListDrawableCompat(StateListState state) {
        if (state != null) {
            setConstantState(state);
        }
    }
}
