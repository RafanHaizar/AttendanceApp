package com.google.android.material.bottomsheet;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseIntArray;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewParent;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.graphics.Insets;
import androidx.core.math.MathUtils;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.core.view.accessibility.AccessibilityViewCommand;
import androidx.customview.view.AbsSavedState;
import androidx.customview.widget.ViewDragHelper;
import com.google.android.material.C1087R;
import com.google.android.material.internal.ViewUtils;
import com.google.android.material.resources.MaterialResources;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.shape.ShapeAppearanceModel;
import com.itextpdf.p026io.font.constants.FontWeights;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BottomSheetBehavior<V extends View> extends CoordinatorLayout.Behavior<V> {
    private static final int CORNER_ANIMATION_DURATION = 500;
    static final int DEFAULT_SIGNIFICANT_VEL_THRESHOLD = 500;
    private static final int DEF_STYLE_RES = C1087R.C1093style.Widget_Design_BottomSheet_Modal;
    private static final float HIDE_FRICTION = 0.1f;
    private static final float HIDE_THRESHOLD = 0.5f;
    private static final int NO_MAX_SIZE = -1;
    public static final int PEEK_HEIGHT_AUTO = -1;
    public static final int SAVE_ALL = -1;
    public static final int SAVE_FIT_TO_CONTENTS = 2;
    public static final int SAVE_HIDEABLE = 4;
    public static final int SAVE_NONE = 0;
    public static final int SAVE_PEEK_HEIGHT = 1;
    public static final int SAVE_SKIP_COLLAPSED = 8;
    public static final int STATE_COLLAPSED = 4;
    public static final int STATE_DRAGGING = 1;
    public static final int STATE_EXPANDED = 3;
    public static final int STATE_HALF_EXPANDED = 6;
    public static final int STATE_HIDDEN = 5;
    public static final int STATE_SETTLING = 2;
    private static final String TAG = "BottomSheetBehavior";
    static final int VIEW_INDEX_ACCESSIBILITY_DELEGATE_VIEW = 1;
    private static final int VIEW_INDEX_BOTTOM_SHEET = 0;
    WeakReference<View> accessibilityDelegateViewRef;
    int activePointerId;
    private ColorStateList backgroundTint;
    private final ArrayList<BottomSheetCallback> callbacks = new ArrayList<>();
    private int childHeight;
    int collapsedOffset;
    private final ViewDragHelper.Callback dragCallback = new ViewDragHelper.Callback() {
        private long viewCapturedMillis;

        public boolean tryCaptureView(View child, int pointerId) {
            if (BottomSheetBehavior.this.state == 1 || BottomSheetBehavior.this.touchingScrollingChild) {
                return false;
            }
            if (BottomSheetBehavior.this.state == 3 && BottomSheetBehavior.this.activePointerId == pointerId) {
                View scroll = BottomSheetBehavior.this.nestedScrollingChildRef != null ? (View) BottomSheetBehavior.this.nestedScrollingChildRef.get() : null;
                if (scroll != null && scroll.canScrollVertically(-1)) {
                    return false;
                }
            }
            this.viewCapturedMillis = System.currentTimeMillis();
            if (BottomSheetBehavior.this.viewRef == null || BottomSheetBehavior.this.viewRef.get() != child) {
                return false;
            }
            return true;
        }

        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
            BottomSheetBehavior.this.dispatchOnSlide(top);
        }

        public void onViewDragStateChanged(int state) {
            if (state == 1 && BottomSheetBehavior.this.draggable) {
                BottomSheetBehavior.this.setStateInternal(1);
            }
        }

        private boolean releasedLow(View child) {
            return child.getTop() > (BottomSheetBehavior.this.parentHeight + BottomSheetBehavior.this.getExpandedOffset()) / 2;
        }

        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            int targetState;
            int targetState2;
            if (yvel < 0.0f) {
                if (BottomSheetBehavior.this.fitToContents) {
                    targetState = 3;
                } else {
                    int targetState3 = releasedChild.getTop();
                    long dragDurationMillis = System.currentTimeMillis() - this.viewCapturedMillis;
                    if (BottomSheetBehavior.this.shouldSkipHalfExpandedStateWhenDragging()) {
                        if (BottomSheetBehavior.this.shouldExpandOnUpwardDrag(dragDurationMillis, (((float) targetState3) * 100.0f) / ((float) BottomSheetBehavior.this.parentHeight))) {
                            targetState2 = 3;
                        } else {
                            targetState2 = 4;
                        }
                        targetState = targetState2;
                    } else if (targetState3 > BottomSheetBehavior.this.halfExpandedOffset) {
                        targetState = 6;
                    } else {
                        targetState = 3;
                    }
                }
            } else if (!BottomSheetBehavior.this.hideable || !BottomSheetBehavior.this.shouldHide(releasedChild, yvel)) {
                if (yvel == 0.0f || Math.abs(xvel) > Math.abs(yvel)) {
                    int currentTop = releasedChild.getTop();
                    if (BottomSheetBehavior.this.fitToContents) {
                        if (Math.abs(currentTop - BottomSheetBehavior.this.fitToContentsOffset) < Math.abs(currentTop - BottomSheetBehavior.this.collapsedOffset)) {
                            targetState = 3;
                        } else {
                            targetState = 4;
                        }
                    } else if (currentTop < BottomSheetBehavior.this.halfExpandedOffset) {
                        if (currentTop < Math.abs(currentTop - BottomSheetBehavior.this.collapsedOffset)) {
                            targetState = 3;
                        } else if (BottomSheetBehavior.this.shouldSkipHalfExpandedStateWhenDragging()) {
                            targetState = 4;
                        } else {
                            targetState = 6;
                        }
                    } else if (Math.abs(currentTop - BottomSheetBehavior.this.halfExpandedOffset) >= Math.abs(currentTop - BottomSheetBehavior.this.collapsedOffset)) {
                        targetState = 4;
                    } else if (BottomSheetBehavior.this.shouldSkipHalfExpandedStateWhenDragging()) {
                        targetState = 4;
                    } else {
                        targetState = 6;
                    }
                } else if (BottomSheetBehavior.this.fitToContents) {
                    targetState = 4;
                } else {
                    int targetState4 = releasedChild.getTop();
                    if (Math.abs(targetState4 - BottomSheetBehavior.this.halfExpandedOffset) >= Math.abs(targetState4 - BottomSheetBehavior.this.collapsedOffset)) {
                        targetState = 4;
                    } else if (BottomSheetBehavior.this.shouldSkipHalfExpandedStateWhenDragging()) {
                        targetState = 4;
                    } else {
                        targetState = 6;
                    }
                }
            } else if ((Math.abs(xvel) < Math.abs(yvel) && yvel > ((float) BottomSheetBehavior.this.significantVelocityThreshold)) || releasedLow(releasedChild)) {
                targetState = 5;
            } else if (BottomSheetBehavior.this.fitToContents) {
                targetState = 3;
            } else if (Math.abs(releasedChild.getTop() - BottomSheetBehavior.this.getExpandedOffset()) < Math.abs(releasedChild.getTop() - BottomSheetBehavior.this.halfExpandedOffset)) {
                targetState = 3;
            } else {
                targetState = 6;
            }
            BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.this;
            bottomSheetBehavior.startSettling(releasedChild, targetState, bottomSheetBehavior.shouldSkipSmoothAnimation());
        }

        public int clampViewPositionVertical(View child, int top, int dy) {
            return MathUtils.clamp(top, BottomSheetBehavior.this.getExpandedOffset(), getViewVerticalDragRange(child));
        }

        public int clampViewPositionHorizontal(View child, int left, int dx) {
            return child.getLeft();
        }

        public int getViewVerticalDragRange(View child) {
            if (BottomSheetBehavior.this.canBeHiddenByDragging()) {
                return BottomSheetBehavior.this.parentHeight;
            }
            return BottomSheetBehavior.this.collapsedOffset;
        }
    };
    /* access modifiers changed from: private */
    public boolean draggable = true;
    float elevation = -1.0f;
    final SparseIntArray expandHalfwayActionIds = new SparseIntArray();
    private boolean expandedCornersRemoved;
    int expandedOffset;
    /* access modifiers changed from: private */
    public boolean fitToContents = true;
    int fitToContentsOffset;
    /* access modifiers changed from: private */
    public int gestureInsetBottom;
    private boolean gestureInsetBottomIgnored;
    int halfExpandedOffset;
    float halfExpandedRatio = 0.5f;
    private float hideFriction = 0.1f;
    boolean hideable;
    private boolean ignoreEvents;
    private Map<View, Integer> importantForAccessibilityMap;
    private int initialY;
    /* access modifiers changed from: private */
    public int insetBottom;
    /* access modifiers changed from: private */
    public int insetTop;
    private ValueAnimator interpolatorAnimator;
    private int lastNestedScrollDy;
    int lastStableState = 4;
    /* access modifiers changed from: private */
    public boolean marginLeftSystemWindowInsets;
    /* access modifiers changed from: private */
    public boolean marginRightSystemWindowInsets;
    /* access modifiers changed from: private */
    public boolean marginTopSystemWindowInsets;
    /* access modifiers changed from: private */
    public MaterialShapeDrawable materialShapeDrawable;
    private int maxHeight = -1;
    private int maxWidth = -1;
    private float maximumVelocity;
    private boolean nestedScrolled;
    WeakReference<View> nestedScrollingChildRef;
    /* access modifiers changed from: private */
    public boolean paddingBottomSystemWindowInsets;
    /* access modifiers changed from: private */
    public boolean paddingLeftSystemWindowInsets;
    /* access modifiers changed from: private */
    public boolean paddingRightSystemWindowInsets;
    private boolean paddingTopSystemWindowInsets;
    int parentHeight;
    int parentWidth;
    /* access modifiers changed from: private */
    public int peekHeight;
    private boolean peekHeightAuto;
    private int peekHeightGestureInsetBuffer;
    private int peekHeightMin;
    private int saveFlags = 0;
    private ShapeAppearanceModel shapeAppearanceModelDefault;
    private boolean shouldRemoveExpandedCorners;
    /* access modifiers changed from: private */
    public int significantVelocityThreshold;
    /* access modifiers changed from: private */
    public boolean skipCollapsed;
    int state = 4;
    private final BottomSheetBehavior<V>.StateSettlingTracker stateSettlingTracker = new StateSettlingTracker();
    boolean touchingScrollingChild;
    private boolean updateImportantForAccessibilityOnSiblings = false;
    private VelocityTracker velocityTracker;
    ViewDragHelper viewDragHelper;
    WeakReference<V> viewRef;

    @Retention(RetentionPolicy.SOURCE)
    public @interface SaveFlags {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface StableState {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface State {
    }

    public static abstract class BottomSheetCallback {
        public abstract void onSlide(View view, float f);

        public abstract void onStateChanged(View view, int i);

        /* access modifiers changed from: package-private */
        public void onLayout(View bottomSheet) {
        }
    }

    public BottomSheetBehavior() {
    }

    public BottomSheetBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.peekHeightGestureInsetBuffer = context.getResources().getDimensionPixelSize(C1087R.dimen.mtrl_min_touch_target_size);
        TypedArray a = context.obtainStyledAttributes(attrs, C1087R.styleable.BottomSheetBehavior_Layout);
        if (a.hasValue(C1087R.styleable.BottomSheetBehavior_Layout_backgroundTint)) {
            this.backgroundTint = MaterialResources.getColorStateList(context, a, C1087R.styleable.BottomSheetBehavior_Layout_backgroundTint);
        }
        if (a.hasValue(C1087R.styleable.BottomSheetBehavior_Layout_shapeAppearance)) {
            this.shapeAppearanceModelDefault = ShapeAppearanceModel.builder(context, attrs, C1087R.attr.bottomSheetStyle, DEF_STYLE_RES).build();
        }
        createMaterialShapeDrawableIfNeeded(context);
        createShapeValueAnimator();
        if (Build.VERSION.SDK_INT >= 21) {
            this.elevation = a.getDimension(C1087R.styleable.BottomSheetBehavior_Layout_android_elevation, -1.0f);
        }
        if (a.hasValue(C1087R.styleable.BottomSheetBehavior_Layout_android_maxWidth)) {
            setMaxWidth(a.getDimensionPixelSize(C1087R.styleable.BottomSheetBehavior_Layout_android_maxWidth, -1));
        }
        if (a.hasValue(C1087R.styleable.BottomSheetBehavior_Layout_android_maxHeight)) {
            setMaxHeight(a.getDimensionPixelSize(C1087R.styleable.BottomSheetBehavior_Layout_android_maxHeight, -1));
        }
        TypedValue value = a.peekValue(C1087R.styleable.BottomSheetBehavior_Layout_behavior_peekHeight);
        if (value == null || value.data != -1) {
            setPeekHeight(a.getDimensionPixelSize(C1087R.styleable.BottomSheetBehavior_Layout_behavior_peekHeight, -1));
        } else {
            setPeekHeight(value.data);
        }
        setHideable(a.getBoolean(C1087R.styleable.BottomSheetBehavior_Layout_behavior_hideable, false));
        setGestureInsetBottomIgnored(a.getBoolean(C1087R.styleable.BottomSheetBehavior_Layout_gestureInsetBottomIgnored, false));
        setFitToContents(a.getBoolean(C1087R.styleable.BottomSheetBehavior_Layout_behavior_fitToContents, true));
        setSkipCollapsed(a.getBoolean(C1087R.styleable.BottomSheetBehavior_Layout_behavior_skipCollapsed, false));
        setDraggable(a.getBoolean(C1087R.styleable.BottomSheetBehavior_Layout_behavior_draggable, true));
        setSaveFlags(a.getInt(C1087R.styleable.BottomSheetBehavior_Layout_behavior_saveFlags, 0));
        setHalfExpandedRatio(a.getFloat(C1087R.styleable.BottomSheetBehavior_Layout_behavior_halfExpandedRatio, 0.5f));
        TypedValue value2 = a.peekValue(C1087R.styleable.BottomSheetBehavior_Layout_behavior_expandedOffset);
        if (value2 == null || value2.type != 16) {
            setExpandedOffset(a.getDimensionPixelOffset(C1087R.styleable.BottomSheetBehavior_Layout_behavior_expandedOffset, 0));
        } else {
            setExpandedOffset(value2.data);
        }
        setSignificantVelocityThreshold(a.getInt(C1087R.styleable.BottomSheetBehavior_Layout_behavior_significantVelocityThreshold, FontWeights.MEDIUM));
        this.paddingBottomSystemWindowInsets = a.getBoolean(C1087R.styleable.BottomSheetBehavior_Layout_paddingBottomSystemWindowInsets, false);
        this.paddingLeftSystemWindowInsets = a.getBoolean(C1087R.styleable.BottomSheetBehavior_Layout_paddingLeftSystemWindowInsets, false);
        this.paddingRightSystemWindowInsets = a.getBoolean(C1087R.styleable.BottomSheetBehavior_Layout_paddingRightSystemWindowInsets, false);
        this.paddingTopSystemWindowInsets = a.getBoolean(C1087R.styleable.BottomSheetBehavior_Layout_paddingTopSystemWindowInsets, true);
        this.marginLeftSystemWindowInsets = a.getBoolean(C1087R.styleable.BottomSheetBehavior_Layout_marginLeftSystemWindowInsets, false);
        this.marginRightSystemWindowInsets = a.getBoolean(C1087R.styleable.BottomSheetBehavior_Layout_marginRightSystemWindowInsets, false);
        this.marginTopSystemWindowInsets = a.getBoolean(C1087R.styleable.BottomSheetBehavior_Layout_marginTopSystemWindowInsets, false);
        this.shouldRemoveExpandedCorners = a.getBoolean(C1087R.styleable.BottomSheetBehavior_Layout_shouldRemoveExpandedCorners, true);
        a.recycle();
        this.maximumVelocity = (float) ViewConfiguration.get(context).getScaledMaximumFlingVelocity();
    }

    public Parcelable onSaveInstanceState(CoordinatorLayout parent, V child) {
        return new SavedState(super.onSaveInstanceState(parent, child), (BottomSheetBehavior<?>) this);
    }

    public void onRestoreInstanceState(CoordinatorLayout parent, V child, Parcelable state2) {
        SavedState ss = (SavedState) state2;
        super.onRestoreInstanceState(parent, child, ss.getSuperState());
        restoreOptionalState(ss);
        if (ss.state == 1 || ss.state == 2) {
            this.state = 4;
            this.lastStableState = 4;
            return;
        }
        int i = ss.state;
        this.state = i;
        this.lastStableState = i;
    }

    public void onAttachedToLayoutParams(CoordinatorLayout.LayoutParams layoutParams) {
        super.onAttachedToLayoutParams(layoutParams);
        this.viewRef = null;
        this.viewDragHelper = null;
    }

    public void onDetachedFromLayoutParams() {
        super.onDetachedFromLayoutParams();
        this.viewRef = null;
        this.viewDragHelper = null;
    }

    public boolean onMeasureChild(CoordinatorLayout parent, V child, int parentWidthMeasureSpec, int widthUsed, int parentHeightMeasureSpec, int heightUsed) {
        ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) child.getLayoutParams();
        child.measure(getChildMeasureSpec(parentWidthMeasureSpec, parent.getPaddingLeft() + parent.getPaddingRight() + lp.leftMargin + lp.rightMargin + widthUsed, this.maxWidth, lp.width), getChildMeasureSpec(parentHeightMeasureSpec, parent.getPaddingTop() + parent.getPaddingBottom() + lp.topMargin + lp.bottomMargin + heightUsed, this.maxHeight, lp.height));
        return true;
    }

    private int getChildMeasureSpec(int parentMeasureSpec, int padding, int maxSize, int childDimension) {
        int i;
        int result = ViewGroup.getChildMeasureSpec(parentMeasureSpec, padding, childDimension);
        if (maxSize == -1) {
            return result;
        }
        int mode = View.MeasureSpec.getMode(result);
        int size = View.MeasureSpec.getSize(result);
        switch (mode) {
            case 1073741824:
                return View.MeasureSpec.makeMeasureSpec(Math.min(size, maxSize), 1073741824);
            default:
                if (size == 0) {
                    i = maxSize;
                } else {
                    i = Math.min(size, maxSize);
                }
                return View.MeasureSpec.makeMeasureSpec(i, Integer.MIN_VALUE);
        }
    }

    public boolean onLayoutChild(CoordinatorLayout parent, V child, int layoutDirection) {
        if (ViewCompat.getFitsSystemWindows(parent) && !ViewCompat.getFitsSystemWindows(child)) {
            child.setFitsSystemWindows(true);
        }
        if (this.viewRef == null) {
            this.peekHeightMin = parent.getResources().getDimensionPixelSize(C1087R.dimen.design_bottom_sheet_peek_height_min);
            setWindowInsetsListener(child);
            this.viewRef = new WeakReference<>(child);
            MaterialShapeDrawable materialShapeDrawable2 = this.materialShapeDrawable;
            if (materialShapeDrawable2 != null) {
                ViewCompat.setBackground(child, materialShapeDrawable2);
                MaterialShapeDrawable materialShapeDrawable3 = this.materialShapeDrawable;
                float f = this.elevation;
                if (f == -1.0f) {
                    f = ViewCompat.getElevation(child);
                }
                materialShapeDrawable3.setElevation(f);
            } else {
                ColorStateList colorStateList = this.backgroundTint;
                if (colorStateList != null) {
                    ViewCompat.setBackgroundTintList(child, colorStateList);
                }
            }
            updateAccessibilityActions();
            if (ViewCompat.getImportantForAccessibility(child) == 0) {
                ViewCompat.setImportantForAccessibility(child, 1);
            }
        }
        if (this.viewDragHelper == null) {
            this.viewDragHelper = ViewDragHelper.create(parent, this.dragCallback);
        }
        int savedTop = child.getTop();
        parent.onLayoutChild(child, layoutDirection);
        this.parentWidth = parent.getWidth();
        this.parentHeight = parent.getHeight();
        int height = child.getHeight();
        this.childHeight = height;
        int i = this.parentHeight;
        int i2 = i - height;
        int i3 = this.insetTop;
        if (i2 < i3) {
            if (this.paddingTopSystemWindowInsets) {
                this.childHeight = i;
            } else {
                this.childHeight = i - i3;
            }
        }
        this.fitToContentsOffset = Math.max(0, i - this.childHeight);
        calculateHalfExpandedOffset();
        calculateCollapsedOffset();
        int i4 = this.state;
        if (i4 == 3) {
            ViewCompat.offsetTopAndBottom(child, getExpandedOffset());
        } else if (i4 == 6) {
            ViewCompat.offsetTopAndBottom(child, this.halfExpandedOffset);
        } else if (this.hideable && i4 == 5) {
            ViewCompat.offsetTopAndBottom(child, this.parentHeight);
        } else if (i4 == 4) {
            ViewCompat.offsetTopAndBottom(child, this.collapsedOffset);
        } else if (i4 == 1 || i4 == 2) {
            ViewCompat.offsetTopAndBottom(child, savedTop - child.getTop());
        }
        updateDrawableForTargetState(this.state, false);
        this.nestedScrollingChildRef = new WeakReference<>(findScrollingChild(child));
        for (int i5 = 0; i5 < this.callbacks.size(); i5++) {
            this.callbacks.get(i5).onLayout(child);
        }
        return true;
    }

    public boolean onInterceptTouchEvent(CoordinatorLayout parent, V child, MotionEvent event) {
        ViewDragHelper viewDragHelper2;
        if (!child.isShown() || !this.draggable) {
            this.ignoreEvents = true;
            return false;
        }
        int action = event.getActionMasked();
        if (action == 0) {
            reset();
        }
        if (this.velocityTracker == null) {
            this.velocityTracker = VelocityTracker.obtain();
        }
        this.velocityTracker.addMovement(event);
        View scroll = null;
        switch (action) {
            case 0:
                int initialX = (int) event.getX();
                this.initialY = (int) event.getY();
                if (this.state != 2) {
                    WeakReference<View> weakReference = this.nestedScrollingChildRef;
                    View scroll2 = weakReference != null ? (View) weakReference.get() : null;
                    if (scroll2 != null && parent.isPointInChildBounds(scroll2, initialX, this.initialY)) {
                        this.activePointerId = event.getPointerId(event.getActionIndex());
                        this.touchingScrollingChild = true;
                    }
                }
                this.ignoreEvents = this.activePointerId == -1 && !parent.isPointInChildBounds(child, initialX, this.initialY);
                break;
            case 1:
            case 3:
                this.touchingScrollingChild = false;
                this.activePointerId = -1;
                if (this.ignoreEvents) {
                    this.ignoreEvents = false;
                    return false;
                }
                break;
        }
        if (!this.ignoreEvents && (viewDragHelper2 = this.viewDragHelper) != null && viewDragHelper2.shouldInterceptTouchEvent(event)) {
            return true;
        }
        WeakReference<View> weakReference2 = this.nestedScrollingChildRef;
        if (weakReference2 != null) {
            scroll = (View) weakReference2.get();
        }
        if (action != 2 || scroll == null || this.ignoreEvents || this.state == 1 || parent.isPointInChildBounds(scroll, (int) event.getX(), (int) event.getY()) || this.viewDragHelper == null || Math.abs(((float) this.initialY) - event.getY()) <= ((float) this.viewDragHelper.getTouchSlop())) {
            return false;
        }
        return true;
    }

    public boolean onTouchEvent(CoordinatorLayout parent, V child, MotionEvent event) {
        if (!child.isShown()) {
            return false;
        }
        int action = event.getActionMasked();
        if (this.state == 1 && action == 0) {
            return true;
        }
        if (shouldHandleDraggingWithHelper()) {
            this.viewDragHelper.processTouchEvent(event);
        }
        if (action == 0) {
            reset();
        }
        if (this.velocityTracker == null) {
            this.velocityTracker = VelocityTracker.obtain();
        }
        this.velocityTracker.addMovement(event);
        if (shouldHandleDraggingWithHelper() && action == 2 && !this.ignoreEvents && Math.abs(((float) this.initialY) - event.getY()) > ((float) this.viewDragHelper.getTouchSlop())) {
            this.viewDragHelper.captureChildView(child, event.getPointerId(event.getActionIndex()));
        }
        return !this.ignoreEvents;
    }

    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, V v, View directTargetChild, View target, int axes, int type) {
        this.lastNestedScrollDy = 0;
        this.nestedScrolled = false;
        if ((axes & 2) != 0) {
            return true;
        }
        return false;
    }

    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, V child, View target, int dx, int dy, int[] consumed, int type) {
        if (type != 1) {
            WeakReference<View> weakReference = this.nestedScrollingChildRef;
            View scrollingChild = weakReference != null ? (View) weakReference.get() : null;
            if (!isNestedScrollingCheckEnabled() || target == scrollingChild) {
                int currentTop = child.getTop();
                int newTop = currentTop - dy;
                if (dy > 0) {
                    if (newTop < getExpandedOffset()) {
                        consumed[1] = currentTop - getExpandedOffset();
                        ViewCompat.offsetTopAndBottom(child, -consumed[1]);
                        setStateInternal(3);
                    } else if (this.draggable) {
                        consumed[1] = dy;
                        ViewCompat.offsetTopAndBottom(child, -dy);
                        setStateInternal(1);
                    } else {
                        return;
                    }
                } else if (dy < 0 && !target.canScrollVertically(-1)) {
                    if (newTop > this.collapsedOffset && !canBeHiddenByDragging()) {
                        consumed[1] = currentTop - this.collapsedOffset;
                        ViewCompat.offsetTopAndBottom(child, -consumed[1]);
                        setStateInternal(4);
                    } else if (this.draggable) {
                        consumed[1] = dy;
                        ViewCompat.offsetTopAndBottom(child, -dy);
                        setStateInternal(1);
                    } else {
                        return;
                    }
                }
                dispatchOnSlide(child.getTop());
                this.lastNestedScrollDy = dy;
                this.nestedScrolled = true;
            }
        }
    }

    public void onStopNestedScroll(CoordinatorLayout coordinatorLayout, V child, View target, int type) {
        int currentTop;
        WeakReference<View> weakReference;
        if (child.getTop() == getExpandedOffset()) {
            setStateInternal(3);
        } else if (!isNestedScrollingCheckEnabled() || ((weakReference = this.nestedScrollingChildRef) != null && target == weakReference.get() && this.nestedScrolled)) {
            if (this.lastNestedScrollDy > 0) {
                if (this.fitToContents) {
                    currentTop = 3;
                } else if (child.getTop() > this.halfExpandedOffset) {
                    currentTop = 6;
                } else {
                    currentTop = 3;
                }
            } else if (this.hideable != 0 && shouldHide(child, getYVelocity())) {
                currentTop = 5;
            } else if (this.lastNestedScrollDy == 0) {
                int currentTop2 = child.getTop();
                if (!this.fitToContents) {
                    int targetState = this.halfExpandedOffset;
                    if (currentTop2 < targetState) {
                        if (currentTop2 < Math.abs(currentTop2 - this.collapsedOffset)) {
                            currentTop = 3;
                        } else if (shouldSkipHalfExpandedStateWhenDragging() != 0) {
                            currentTop = 4;
                        } else {
                            currentTop = 6;
                        }
                    } else if (Math.abs(currentTop2 - targetState) < Math.abs(currentTop2 - this.collapsedOffset)) {
                        currentTop = 6;
                    } else {
                        currentTop = 4;
                    }
                } else if (Math.abs(currentTop2 - this.fitToContentsOffset) < Math.abs(currentTop2 - this.collapsedOffset)) {
                    currentTop = 3;
                } else {
                    currentTop = 4;
                }
            } else if (this.fitToContents != 0) {
                currentTop = 4;
            } else {
                int targetState2 = child.getTop();
                if (Math.abs(targetState2 - this.halfExpandedOffset) < Math.abs(targetState2 - this.collapsedOffset)) {
                    currentTop = 6;
                } else {
                    currentTop = 4;
                }
            }
            startSettling(child, currentTop, false);
            this.nestedScrolled = false;
        }
    }

    public void onNestedScroll(CoordinatorLayout coordinatorLayout, V v, View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int type, int[] consumed) {
    }

    public boolean onNestedPreFling(CoordinatorLayout coordinatorLayout, V child, View target, float velocityX, float velocityY) {
        WeakReference<View> weakReference;
        if (!isNestedScrollingCheckEnabled() || (weakReference = this.nestedScrollingChildRef) == null || target != weakReference.get()) {
            return false;
        }
        if (this.state != 3 || super.onNestedPreFling(coordinatorLayout, child, target, velocityX, velocityY)) {
            return true;
        }
        return false;
    }

    public boolean isFitToContents() {
        return this.fitToContents;
    }

    public void setFitToContents(boolean fitToContents2) {
        if (this.fitToContents != fitToContents2) {
            this.fitToContents = fitToContents2;
            if (this.viewRef != null) {
                calculateCollapsedOffset();
            }
            setStateInternal((!this.fitToContents || this.state != 6) ? this.state : 3);
            updateDrawableForTargetState(this.state, true);
            updateAccessibilityActions();
        }
    }

    public void setMaxWidth(int maxWidth2) {
        this.maxWidth = maxWidth2;
    }

    public int getMaxWidth() {
        return this.maxWidth;
    }

    public void setMaxHeight(int maxHeight2) {
        this.maxHeight = maxHeight2;
    }

    public int getMaxHeight() {
        return this.maxHeight;
    }

    public void setPeekHeight(int peekHeight2) {
        setPeekHeight(peekHeight2, false);
    }

    public final void setPeekHeight(int peekHeight2, boolean animate) {
        boolean layout = false;
        if (peekHeight2 == -1) {
            if (!this.peekHeightAuto) {
                this.peekHeightAuto = true;
                layout = true;
            }
        } else if (this.peekHeightAuto || this.peekHeight != peekHeight2) {
            this.peekHeightAuto = false;
            this.peekHeight = Math.max(0, peekHeight2);
            layout = true;
        }
        if (layout) {
            updatePeekHeight(animate);
        }
    }

    /* access modifiers changed from: private */
    public void updatePeekHeight(boolean animate) {
        V view;
        if (this.viewRef != null) {
            calculateCollapsedOffset();
            if (this.state == 4 && (view = (View) this.viewRef.get()) != null) {
                if (animate) {
                    setState(4);
                } else {
                    view.requestLayout();
                }
            }
        }
    }

    public int getPeekHeight() {
        if (this.peekHeightAuto) {
            return -1;
        }
        return this.peekHeight;
    }

    public void setHalfExpandedRatio(float ratio) {
        if (ratio <= 0.0f || ratio >= 1.0f) {
            throw new IllegalArgumentException("ratio must be a float value between 0 and 1");
        }
        this.halfExpandedRatio = ratio;
        if (this.viewRef != null) {
            calculateHalfExpandedOffset();
        }
    }

    public float getHalfExpandedRatio() {
        return this.halfExpandedRatio;
    }

    public void setExpandedOffset(int offset) {
        if (offset >= 0) {
            this.expandedOffset = offset;
            updateDrawableForTargetState(this.state, true);
            return;
        }
        throw new IllegalArgumentException("offset must be greater than or equal to 0");
    }

    public int getExpandedOffset() {
        if (this.fitToContents) {
            return this.fitToContentsOffset;
        }
        return Math.max(this.expandedOffset, this.paddingTopSystemWindowInsets ? 0 : this.insetTop);
    }

    public float calculateSlideOffset() {
        WeakReference<V> weakReference = this.viewRef;
        if (weakReference == null || weakReference.get() == null) {
            return -1.0f;
        }
        return calculateSlideOffsetWithTop(((View) this.viewRef.get()).getTop());
    }

    public void setHideable(boolean hideable2) {
        if (this.hideable != hideable2) {
            this.hideable = hideable2;
            if (!hideable2 && this.state == 5) {
                setState(4);
            }
            updateAccessibilityActions();
        }
    }

    public boolean isHideable() {
        return this.hideable;
    }

    public void setSkipCollapsed(boolean skipCollapsed2) {
        this.skipCollapsed = skipCollapsed2;
    }

    public boolean getSkipCollapsed() {
        return this.skipCollapsed;
    }

    public void setDraggable(boolean draggable2) {
        this.draggable = draggable2;
    }

    public boolean isDraggable() {
        return this.draggable;
    }

    public void setSignificantVelocityThreshold(int significantVelocityThreshold2) {
        this.significantVelocityThreshold = significantVelocityThreshold2;
    }

    public int getSignificantVelocityThreshold() {
        return this.significantVelocityThreshold;
    }

    public void setSaveFlags(int flags) {
        this.saveFlags = flags;
    }

    public int getSaveFlags() {
        return this.saveFlags;
    }

    public void setHideFriction(float hideFriction2) {
        this.hideFriction = hideFriction2;
    }

    public float getHideFriction() {
        return this.hideFriction;
    }

    @Deprecated
    public void setBottomSheetCallback(BottomSheetCallback callback) {
        Log.w(TAG, "BottomSheetBehavior now supports multiple callbacks. `setBottomSheetCallback()` removes all existing callbacks, including ones set internally by library authors, which may result in unintended behavior. This may change in the future. Please use `addBottomSheetCallback()` and `removeBottomSheetCallback()` instead to set your own callbacks.");
        this.callbacks.clear();
        if (callback != null) {
            this.callbacks.add(callback);
        }
    }

    public void addBottomSheetCallback(BottomSheetCallback callback) {
        if (!this.callbacks.contains(callback)) {
            this.callbacks.add(callback);
        }
    }

    public void removeBottomSheetCallback(BottomSheetCallback callback) {
        this.callbacks.remove(callback);
    }

    public void setState(int state2) {
        final int finalState;
        if (state2 == 1 || state2 == 2) {
            throw new IllegalArgumentException("STATE_" + (state2 == 1 ? "DRAGGING" : "SETTLING") + " should not be set externally.");
        } else if (this.hideable || state2 != 5) {
            if (state2 != 6 || !this.fitToContents || getTopOffsetForState(state2) > this.fitToContentsOffset) {
                finalState = state2;
            } else {
                finalState = 3;
            }
            WeakReference<V> weakReference = this.viewRef;
            if (weakReference == null || weakReference.get() == null) {
                setStateInternal(state2);
                return;
            }
            final V child = (View) this.viewRef.get();
            runAfterLayout(child, new Runnable() {
                public void run() {
                    BottomSheetBehavior.this.startSettling(child, finalState, false);
                }
            });
        } else {
            Log.w(TAG, "Cannot set state: " + state2);
        }
    }

    private void runAfterLayout(V child, Runnable runnable) {
        if (isLayouting(child)) {
            child.post(runnable);
        } else {
            runnable.run();
        }
    }

    private boolean isLayouting(V child) {
        ViewParent parent = child.getParent();
        return parent != null && parent.isLayoutRequested() && ViewCompat.isAttachedToWindow(child);
    }

    public void setGestureInsetBottomIgnored(boolean gestureInsetBottomIgnored2) {
        this.gestureInsetBottomIgnored = gestureInsetBottomIgnored2;
    }

    public boolean isGestureInsetBottomIgnored() {
        return this.gestureInsetBottomIgnored;
    }

    public int getState() {
        return this.state;
    }

    /* access modifiers changed from: package-private */
    public void setStateInternal(int state2) {
        View bottomSheet;
        if (this.state != state2) {
            this.state = state2;
            if (state2 == 4 || state2 == 3 || state2 == 6 || (this.hideable && state2 == 5)) {
                this.lastStableState = state2;
            }
            WeakReference<V> weakReference = this.viewRef;
            if (weakReference != null && (bottomSheet = (View) weakReference.get()) != null) {
                if (state2 == 3) {
                    updateImportantForAccessibility(true);
                } else if (state2 == 6 || state2 == 5 || state2 == 4) {
                    updateImportantForAccessibility(false);
                }
                updateDrawableForTargetState(state2, true);
                for (int i = 0; i < this.callbacks.size(); i++) {
                    this.callbacks.get(i).onStateChanged(bottomSheet, state2);
                }
                updateAccessibilityActions();
            }
        }
    }

    private void updateDrawableForTargetState(int state2, boolean animate) {
        boolean removeCorners;
        ValueAnimator valueAnimator;
        if (state2 != 2 && this.expandedCornersRemoved != (removeCorners = isExpandedAndShouldRemoveCorners()) && this.materialShapeDrawable != null) {
            this.expandedCornersRemoved = removeCorners;
            float to = 0.0f;
            if (!animate || (valueAnimator = this.interpolatorAnimator) == null) {
                ValueAnimator valueAnimator2 = this.interpolatorAnimator;
                if (valueAnimator2 != null && valueAnimator2.isRunning()) {
                    this.interpolatorAnimator.cancel();
                }
                MaterialShapeDrawable materialShapeDrawable2 = this.materialShapeDrawable;
                if (!this.expandedCornersRemoved) {
                    to = 1.0f;
                }
                materialShapeDrawable2.setInterpolation(to);
            } else if (valueAnimator.isRunning()) {
                this.interpolatorAnimator.reverse();
            } else {
                if (!removeCorners) {
                    to = 1.0f;
                }
                this.interpolatorAnimator.setFloatValues(new float[]{1.0f - to, to});
                this.interpolatorAnimator.start();
            }
        }
    }

    private boolean isExpandedAndShouldRemoveCorners() {
        return this.state == 3 && (this.shouldRemoveExpandedCorners || getExpandedOffset() == 0);
    }

    private int calculatePeekHeight() {
        int i;
        if (this.peekHeightAuto) {
            return Math.min(Math.max(this.peekHeightMin, this.parentHeight - ((this.parentWidth * 9) / 16)), this.childHeight) + this.insetBottom;
        }
        if (this.gestureInsetBottomIgnored != 0 || this.paddingBottomSystemWindowInsets || (i = this.gestureInsetBottom) <= 0) {
            return this.peekHeight + this.insetBottom;
        }
        return Math.max(this.peekHeight, i + this.peekHeightGestureInsetBuffer);
    }

    private void calculateCollapsedOffset() {
        int peek = calculatePeekHeight();
        if (this.fitToContents) {
            this.collapsedOffset = Math.max(this.parentHeight - peek, this.fitToContentsOffset);
        } else {
            this.collapsedOffset = this.parentHeight - peek;
        }
    }

    private void calculateHalfExpandedOffset() {
        this.halfExpandedOffset = (int) (((float) this.parentHeight) * (1.0f - this.halfExpandedRatio));
    }

    private float calculateSlideOffsetWithTop(int top) {
        int i = this.collapsedOffset;
        if (top > i || i == getExpandedOffset()) {
            int i2 = this.collapsedOffset;
            return ((float) (i2 - top)) / ((float) (this.parentHeight - i2));
        }
        int i3 = this.collapsedOffset;
        return ((float) (i3 - top)) / ((float) (i3 - getExpandedOffset()));
    }

    private void reset() {
        this.activePointerId = -1;
        VelocityTracker velocityTracker2 = this.velocityTracker;
        if (velocityTracker2 != null) {
            velocityTracker2.recycle();
            this.velocityTracker = null;
        }
    }

    private void restoreOptionalState(SavedState ss) {
        int i = this.saveFlags;
        if (i != 0) {
            if (i == -1 || (i & 1) == 1) {
                this.peekHeight = ss.peekHeight;
            }
            int i2 = this.saveFlags;
            if (i2 == -1 || (i2 & 2) == 2) {
                this.fitToContents = ss.fitToContents;
            }
            int i3 = this.saveFlags;
            if (i3 == -1 || (i3 & 4) == 4) {
                this.hideable = ss.hideable;
            }
            int i4 = this.saveFlags;
            if (i4 == -1 || (i4 & 8) == 8) {
                this.skipCollapsed = ss.skipCollapsed;
            }
        }
    }

    /* access modifiers changed from: package-private */
    public boolean shouldHide(View child, float yvel) {
        if (this.skipCollapsed) {
            return true;
        }
        if (!isHideableWhenDragging() || child.getTop() < this.collapsedOffset) {
            return false;
        }
        if (Math.abs((((float) child.getTop()) + (this.hideFriction * yvel)) - ((float) this.collapsedOffset)) / ((float) calculatePeekHeight()) > 0.5f) {
            return true;
        }
        return false;
    }

    /* access modifiers changed from: package-private */
    public View findScrollingChild(View view) {
        if (view.getVisibility() != 0) {
            return null;
        }
        if (ViewCompat.isNestedScrollingEnabled(view)) {
            return view;
        }
        if (view instanceof ViewGroup) {
            ViewGroup group = (ViewGroup) view;
            int count = group.getChildCount();
            for (int i = 0; i < count; i++) {
                View scrollingChild = findScrollingChild(group.getChildAt(i));
                if (scrollingChild != null) {
                    return scrollingChild;
                }
            }
        }
        return null;
    }

    private boolean shouldHandleDraggingWithHelper() {
        return this.viewDragHelper != null && (this.draggable || this.state == 1);
    }

    private void createMaterialShapeDrawableIfNeeded(Context context) {
        if (this.shapeAppearanceModelDefault != null) {
            MaterialShapeDrawable materialShapeDrawable2 = new MaterialShapeDrawable(this.shapeAppearanceModelDefault);
            this.materialShapeDrawable = materialShapeDrawable2;
            materialShapeDrawable2.initializeElevationOverlay(context);
            ColorStateList colorStateList = this.backgroundTint;
            if (colorStateList != null) {
                this.materialShapeDrawable.setFillColor(colorStateList);
                return;
            }
            TypedValue defaultColor = new TypedValue();
            context.getTheme().resolveAttribute(16842801, defaultColor, true);
            this.materialShapeDrawable.setTint(defaultColor.data);
        }
    }

    /* access modifiers changed from: package-private */
    public MaterialShapeDrawable getMaterialShapeDrawable() {
        return this.materialShapeDrawable;
    }

    private void createShapeValueAnimator() {
        ValueAnimator ofFloat = ValueAnimator.ofFloat(new float[]{0.0f, 1.0f});
        this.interpolatorAnimator = ofFloat;
        ofFloat.setDuration(500);
        this.interpolatorAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = ((Float) animation.getAnimatedValue()).floatValue();
                if (BottomSheetBehavior.this.materialShapeDrawable != null) {
                    BottomSheetBehavior.this.materialShapeDrawable.setInterpolation(value);
                }
            }
        });
    }

    private void setWindowInsetsListener(View child) {
        final boolean shouldHandleGestureInsets = Build.VERSION.SDK_INT >= 29 && !isGestureInsetBottomIgnored() && !this.peekHeightAuto;
        if (this.paddingBottomSystemWindowInsets || this.paddingLeftSystemWindowInsets || this.paddingRightSystemWindowInsets || this.marginLeftSystemWindowInsets || this.marginRightSystemWindowInsets || this.marginTopSystemWindowInsets || shouldHandleGestureInsets) {
            ViewUtils.doOnApplyWindowInsets(child, new ViewUtils.OnApplyWindowInsetsListener() {
                public WindowInsetsCompat onApplyWindowInsets(View view, WindowInsetsCompat insets, ViewUtils.RelativePadding initialPadding) {
                    Insets systemBarInsets = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                    Insets mandatoryGestureInsets = insets.getInsets(WindowInsetsCompat.Type.mandatorySystemGestures());
                    int unused = BottomSheetBehavior.this.insetTop = systemBarInsets.top;
                    boolean isRtl = ViewUtils.isLayoutRtl(view);
                    int bottomPadding = view.getPaddingBottom();
                    int leftPadding = view.getPaddingLeft();
                    int rightPadding = view.getPaddingRight();
                    if (BottomSheetBehavior.this.paddingBottomSystemWindowInsets) {
                        int unused2 = BottomSheetBehavior.this.insetBottom = insets.getSystemWindowInsetBottom();
                        bottomPadding = initialPadding.bottom + BottomSheetBehavior.this.insetBottom;
                    }
                    if (BottomSheetBehavior.this.paddingLeftSystemWindowInsets) {
                        leftPadding = (isRtl ? initialPadding.end : initialPadding.start) + systemBarInsets.left;
                    }
                    if (BottomSheetBehavior.this.paddingRightSystemWindowInsets) {
                        rightPadding = (isRtl ? initialPadding.start : initialPadding.end) + systemBarInsets.right;
                    }
                    ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
                    boolean marginUpdated = false;
                    if (BottomSheetBehavior.this.marginLeftSystemWindowInsets && mlp.leftMargin != systemBarInsets.left) {
                        mlp.leftMargin = systemBarInsets.left;
                        marginUpdated = true;
                    }
                    if (BottomSheetBehavior.this.marginRightSystemWindowInsets && mlp.rightMargin != systemBarInsets.right) {
                        mlp.rightMargin = systemBarInsets.right;
                        marginUpdated = true;
                    }
                    if (BottomSheetBehavior.this.marginTopSystemWindowInsets && mlp.topMargin != systemBarInsets.top) {
                        mlp.topMargin = systemBarInsets.top;
                        marginUpdated = true;
                    }
                    if (marginUpdated) {
                        view.setLayoutParams(mlp);
                    }
                    view.setPadding(leftPadding, view.getPaddingTop(), rightPadding, bottomPadding);
                    if (shouldHandleGestureInsets) {
                        int unused3 = BottomSheetBehavior.this.gestureInsetBottom = mandatoryGestureInsets.bottom;
                    }
                    if (BottomSheetBehavior.this.paddingBottomSystemWindowInsets || shouldHandleGestureInsets) {
                        BottomSheetBehavior.this.updatePeekHeight(false);
                    }
                    return insets;
                }
            });
        }
    }

    private float getYVelocity() {
        VelocityTracker velocityTracker2 = this.velocityTracker;
        if (velocityTracker2 == null) {
            return 0.0f;
        }
        velocityTracker2.computeCurrentVelocity(1000, this.maximumVelocity);
        return this.velocityTracker.getYVelocity(this.activePointerId);
    }

    /* access modifiers changed from: private */
    public void startSettling(View child, int state2, boolean isReleasingView) {
        int top = getTopOffsetForState(state2);
        ViewDragHelper viewDragHelper2 = this.viewDragHelper;
        if (viewDragHelper2 != null && (!isReleasingView ? viewDragHelper2.smoothSlideViewTo(child, child.getLeft(), top) : viewDragHelper2.settleCapturedViewAt(child.getLeft(), top))) {
            setStateInternal(2);
            updateDrawableForTargetState(state2, true);
            this.stateSettlingTracker.continueSettlingToState(state2);
            return;
        }
        setStateInternal(state2);
    }

    private int getTopOffsetForState(int state2) {
        switch (state2) {
            case 3:
                return getExpandedOffset();
            case 4:
                return this.collapsedOffset;
            case 5:
                return this.parentHeight;
            case 6:
                return this.halfExpandedOffset;
            default:
                throw new IllegalArgumentException("Invalid state to get top offset: " + state2);
        }
    }

    /* access modifiers changed from: package-private */
    public void dispatchOnSlide(int top) {
        View bottomSheet = (View) this.viewRef.get();
        if (bottomSheet != null && !this.callbacks.isEmpty()) {
            float slideOffset = calculateSlideOffsetWithTop(top);
            for (int i = 0; i < this.callbacks.size(); i++) {
                this.callbacks.get(i).onSlide(bottomSheet, slideOffset);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public int getPeekHeightMin() {
        return this.peekHeightMin;
    }

    public void disableShapeAnimations() {
        this.interpolatorAnimator = null;
    }

    public boolean isNestedScrollingCheckEnabled() {
        return true;
    }

    public boolean shouldSkipHalfExpandedStateWhenDragging() {
        return false;
    }

    public boolean shouldSkipSmoothAnimation() {
        return true;
    }

    public boolean isHideableWhenDragging() {
        return true;
    }

    /* access modifiers changed from: private */
    public boolean canBeHiddenByDragging() {
        return isHideable() && isHideableWhenDragging();
    }

    public boolean shouldExpandOnUpwardDrag(long dragDurationMillis, float yPositionPercentage) {
        return false;
    }

    public void setHideableInternal(boolean hideable2) {
        this.hideable = hideable2;
    }

    public int getLastStableState() {
        return this.lastStableState;
    }

    private class StateSettlingTracker {
        private final Runnable continueSettlingRunnable;
        /* access modifiers changed from: private */
        public boolean isContinueSettlingRunnablePosted;
        /* access modifiers changed from: private */
        public int targetState;

        private StateSettlingTracker() {
            this.continueSettlingRunnable = new Runnable() {
                public void run() {
                    boolean unused = StateSettlingTracker.this.isContinueSettlingRunnablePosted = false;
                    if (BottomSheetBehavior.this.viewDragHelper != null && BottomSheetBehavior.this.viewDragHelper.continueSettling(true)) {
                        StateSettlingTracker stateSettlingTracker = StateSettlingTracker.this;
                        stateSettlingTracker.continueSettlingToState(stateSettlingTracker.targetState);
                    } else if (BottomSheetBehavior.this.state == 2) {
                        BottomSheetBehavior.this.setStateInternal(StateSettlingTracker.this.targetState);
                    }
                }
            };
        }

        /* access modifiers changed from: package-private */
        public void continueSettlingToState(int targetState2) {
            if (BottomSheetBehavior.this.viewRef != null && BottomSheetBehavior.this.viewRef.get() != null) {
                this.targetState = targetState2;
                if (!this.isContinueSettlingRunnablePosted) {
                    ViewCompat.postOnAnimation((View) BottomSheetBehavior.this.viewRef.get(), this.continueSettlingRunnable);
                    this.isContinueSettlingRunnablePosted = true;
                }
            }
        }
    }

    protected static class SavedState extends AbsSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.ClassLoaderCreator<SavedState>() {
            public SavedState createFromParcel(Parcel in, ClassLoader loader) {
                return new SavedState(in, loader);
            }

            public SavedState createFromParcel(Parcel in) {
                return new SavedState(in, (ClassLoader) null);
            }

            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };
        boolean fitToContents;
        boolean hideable;
        int peekHeight;
        boolean skipCollapsed;
        final int state;

        public SavedState(Parcel source) {
            this(source, (ClassLoader) null);
        }

        public SavedState(Parcel source, ClassLoader loader) {
            super(source, loader);
            this.state = source.readInt();
            this.peekHeight = source.readInt();
            boolean z = false;
            this.fitToContents = source.readInt() == 1;
            this.hideable = source.readInt() == 1;
            this.skipCollapsed = source.readInt() == 1 ? true : z;
        }

        public SavedState(Parcelable superState, BottomSheetBehavior<?> behavior) {
            super(superState);
            this.state = behavior.state;
            this.peekHeight = behavior.peekHeight;
            this.fitToContents = behavior.fitToContents;
            this.hideable = behavior.hideable;
            this.skipCollapsed = behavior.skipCollapsed;
        }

        @Deprecated
        public SavedState(Parcelable superstate, int state2) {
            super(superstate);
            this.state = state2;
        }

        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeInt(this.state);
            out.writeInt(this.peekHeight);
            out.writeInt(this.fitToContents ? 1 : 0);
            out.writeInt(this.hideable ? 1 : 0);
            out.writeInt(this.skipCollapsed ? 1 : 0);
        }
    }

    public static <V extends View> BottomSheetBehavior<V> from(V view) {
        ViewGroup.LayoutParams params = view.getLayoutParams();
        if (params instanceof CoordinatorLayout.LayoutParams) {
            CoordinatorLayout.Behavior<?> behavior = ((CoordinatorLayout.LayoutParams) params).getBehavior();
            if (behavior instanceof BottomSheetBehavior) {
                return (BottomSheetBehavior) behavior;
            }
            throw new IllegalArgumentException("The view is not associated with BottomSheetBehavior");
        }
        throw new IllegalArgumentException("The view is not a child of CoordinatorLayout");
    }

    public void setUpdateImportantForAccessibilityOnSiblings(boolean updateImportantForAccessibilityOnSiblings2) {
        this.updateImportantForAccessibilityOnSiblings = updateImportantForAccessibilityOnSiblings2;
    }

    private void updateImportantForAccessibility(boolean expanded) {
        Map<View, Integer> map;
        WeakReference<V> weakReference = this.viewRef;
        if (weakReference != null) {
            ViewParent viewParent = ((View) weakReference.get()).getParent();
            if (viewParent instanceof CoordinatorLayout) {
                CoordinatorLayout parent = (CoordinatorLayout) viewParent;
                int childCount = parent.getChildCount();
                if (expanded) {
                    if (this.importantForAccessibilityMap == null) {
                        this.importantForAccessibilityMap = new HashMap(childCount);
                    } else {
                        return;
                    }
                }
                for (int i = 0; i < childCount; i++) {
                    View child = parent.getChildAt(i);
                    if (child != this.viewRef.get()) {
                        if (expanded) {
                            this.importantForAccessibilityMap.put(child, Integer.valueOf(child.getImportantForAccessibility()));
                            if (this.updateImportantForAccessibilityOnSiblings) {
                                ViewCompat.setImportantForAccessibility(child, 4);
                            }
                        } else if (this.updateImportantForAccessibilityOnSiblings && (map = this.importantForAccessibilityMap) != null && map.containsKey(child)) {
                            ViewCompat.setImportantForAccessibility(child, this.importantForAccessibilityMap.get(child).intValue());
                        }
                    }
                }
                if (!expanded) {
                    this.importantForAccessibilityMap = null;
                } else if (this.updateImportantForAccessibilityOnSiblings) {
                    ((View) this.viewRef.get()).sendAccessibilityEvent(8);
                }
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void setAccessibilityDelegateView(View accessibilityDelegateView) {
        WeakReference<View> weakReference;
        if (accessibilityDelegateView != null || (weakReference = this.accessibilityDelegateViewRef) == null) {
            this.accessibilityDelegateViewRef = new WeakReference<>(accessibilityDelegateView);
            updateAccessibilityActions(accessibilityDelegateView, 1);
            return;
        }
        clearAccessibilityAction((View) weakReference.get(), 1);
        this.accessibilityDelegateViewRef = null;
    }

    private void updateAccessibilityActions() {
        WeakReference<V> weakReference = this.viewRef;
        if (weakReference != null) {
            updateAccessibilityActions((View) weakReference.get(), 0);
        }
        WeakReference<View> weakReference2 = this.accessibilityDelegateViewRef;
        if (weakReference2 != null) {
            updateAccessibilityActions((View) weakReference2.get(), 1);
        }
    }

    private void updateAccessibilityActions(View view, int viewIndex) {
        if (view != null) {
            clearAccessibilityAction(view, viewIndex);
            int nextState = 6;
            if (!this.fitToContents && this.state != 6) {
                this.expandHalfwayActionIds.put(viewIndex, addAccessibilityActionForState(view, C1087R.string.bottomsheet_action_expand_halfway, 6));
            }
            if (this.hideable && isHideableWhenDragging() && this.state != 5) {
                replaceAccessibilityActionForState(view, AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_DISMISS, 5);
            }
            switch (this.state) {
                case 3:
                    if (this.fitToContents != 0) {
                        nextState = 4;
                    }
                    replaceAccessibilityActionForState(view, AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_COLLAPSE, nextState);
                    return;
                case 4:
                    if (this.fitToContents) {
                        nextState = 3;
                    }
                    replaceAccessibilityActionForState(view, AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_EXPAND, nextState);
                    return;
                case 6:
                    replaceAccessibilityActionForState(view, AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_COLLAPSE, 4);
                    replaceAccessibilityActionForState(view, AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_EXPAND, 3);
                    return;
                default:
                    return;
            }
        }
    }

    private void clearAccessibilityAction(View view, int viewIndex) {
        if (view != null) {
            ViewCompat.removeAccessibilityAction(view, 524288);
            ViewCompat.removeAccessibilityAction(view, 262144);
            ViewCompat.removeAccessibilityAction(view, 1048576);
            int expandHalfwayActionId = this.expandHalfwayActionIds.get(viewIndex, -1);
            if (expandHalfwayActionId != -1) {
                ViewCompat.removeAccessibilityAction(view, expandHalfwayActionId);
                this.expandHalfwayActionIds.delete(viewIndex);
            }
        }
    }

    private void replaceAccessibilityActionForState(View child, AccessibilityNodeInfoCompat.AccessibilityActionCompat action, int state2) {
        ViewCompat.replaceAccessibilityAction(child, action, (CharSequence) null, createAccessibilityViewCommandForState(state2));
    }

    private int addAccessibilityActionForState(View child, int stringResId, int state2) {
        return ViewCompat.addAccessibilityAction(child, child.getResources().getString(stringResId), createAccessibilityViewCommandForState(state2));
    }

    private AccessibilityViewCommand createAccessibilityViewCommandForState(final int state2) {
        return new AccessibilityViewCommand() {
            public boolean perform(View view, AccessibilityViewCommand.CommandArguments arguments) {
                BottomSheetBehavior.this.setState(state2);
                return true;
            }
        };
    }
}
