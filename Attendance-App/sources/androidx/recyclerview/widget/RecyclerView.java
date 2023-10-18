package androidx.recyclerview.widget;

import android.animation.LayoutTransition;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.database.Observable;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.Display;
import android.view.FocusFinder;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;
import android.view.animation.Interpolator;
import android.widget.EdgeEffect;
import android.widget.OverScroller;
import androidx.core.p001os.TraceCompat;
import androidx.core.util.Preconditions;
import androidx.core.view.AccessibilityDelegateCompat;
import androidx.core.view.NestedScrollingChild2;
import androidx.core.view.NestedScrollingChild3;
import androidx.core.view.NestedScrollingChildHelper;
import androidx.core.view.ScrollingView;
import androidx.core.view.ViewCompat;
import androidx.core.view.ViewConfigurationCompat;
import androidx.core.view.accessibility.AccessibilityEventCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.core.widget.EdgeEffectCompat;
import androidx.customview.view.AbsSavedState;
import androidx.recyclerview.C0926R;
import androidx.recyclerview.widget.AdapterHelper;
import androidx.recyclerview.widget.ChildHelper;
import androidx.recyclerview.widget.GapWorker;
import androidx.recyclerview.widget.RecyclerViewAccessibilityDelegate;
import androidx.recyclerview.widget.ViewBoundsCheck;
import androidx.recyclerview.widget.ViewInfoStore;
import com.itextpdf.kernel.pdf.canvas.wmf.MetaDo;
import com.itextpdf.p026io.codec.TIFFConstants;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RecyclerView extends ViewGroup implements ScrollingView, NestedScrollingChild2, NestedScrollingChild3 {
    static final boolean ALLOW_SIZE_IN_UNSPECIFIED_SPEC = (Build.VERSION.SDK_INT >= 23);
    static final boolean ALLOW_THREAD_GAP_WORK = (Build.VERSION.SDK_INT >= 21);
    static final boolean DEBUG = false;
    static final int DEFAULT_ORIENTATION = 1;
    static final boolean DISPATCH_TEMP_DETACH = false;
    private static final boolean FORCE_ABS_FOCUS_SEARCH_DIRECTION = false;
    static final boolean FORCE_INVALIDATE_DISPLAY_LIST = (Build.VERSION.SDK_INT == 19 || Build.VERSION.SDK_INT == 20);
    static final long FOREVER_NS = Long.MAX_VALUE;
    public static final int HORIZONTAL = 0;
    private static final boolean IGNORE_DETACHED_FOCUSED_CHILD = false;
    private static final int INVALID_POINTER = -1;
    public static final int INVALID_TYPE = -1;
    private static final Class<?>[] LAYOUT_MANAGER_CONSTRUCTOR_SIGNATURE = {Context.class, AttributeSet.class, Integer.TYPE, Integer.TYPE};
    static final int MAX_SCROLL_DURATION = 2000;
    private static final int[] NESTED_SCROLLING_ATTRS = {16843830};
    public static final long NO_ID = -1;
    public static final int NO_POSITION = -1;
    static final boolean POST_UPDATES_ON_ANIMATION = true;
    public static final int SCROLL_STATE_DRAGGING = 1;
    public static final int SCROLL_STATE_IDLE = 0;
    public static final int SCROLL_STATE_SETTLING = 2;
    static final String TAG = "RecyclerView";
    public static final int TOUCH_SLOP_DEFAULT = 0;
    public static final int TOUCH_SLOP_PAGING = 1;
    static final String TRACE_BIND_VIEW_TAG = "RV OnBindView";
    static final String TRACE_CREATE_VIEW_TAG = "RV CreateView";
    private static final String TRACE_HANDLE_ADAPTER_UPDATES_TAG = "RV PartialInvalidate";
    static final String TRACE_NESTED_PREFETCH_TAG = "RV Nested Prefetch";
    private static final String TRACE_ON_DATA_SET_CHANGE_LAYOUT_TAG = "RV FullInvalidate";
    private static final String TRACE_ON_LAYOUT_TAG = "RV OnLayout";
    static final String TRACE_PREFETCH_TAG = "RV Prefetch";
    static final String TRACE_SCROLL_TAG = "RV Scroll";
    public static final int UNDEFINED_DURATION = Integer.MIN_VALUE;
    static final boolean VERBOSE_TRACING = false;
    public static final int VERTICAL = 1;
    static final Interpolator sQuinticInterpolator = new Interpolator() {
        public float getInterpolation(float t) {
            float t2 = t - 1.0f;
            return (t2 * t2 * t2 * t2 * t2) + 1.0f;
        }
    };
    RecyclerViewAccessibilityDelegate mAccessibilityDelegate;
    private final AccessibilityManager mAccessibilityManager;
    Adapter mAdapter;
    AdapterHelper mAdapterHelper;
    boolean mAdapterUpdateDuringMeasure;
    private EdgeEffect mBottomGlow;
    private ChildDrawingOrderCallback mChildDrawingOrderCallback;
    ChildHelper mChildHelper;
    boolean mClipToPadding;
    boolean mDataSetHasChangedAfterLayout;
    boolean mDispatchItemsChangedEvent;
    private int mDispatchScrollCounter;
    private int mEatenAccessibilityChangeFlags;
    private EdgeEffectFactory mEdgeEffectFactory;
    boolean mEnableFastScroller;
    boolean mFirstLayoutComplete;
    GapWorker mGapWorker;
    boolean mHasFixedSize;
    private boolean mIgnoreMotionEventTillDown;
    private int mInitialTouchX;
    private int mInitialTouchY;
    private int mInterceptRequestLayoutDepth;
    private OnItemTouchListener mInterceptingOnItemTouchListener;
    boolean mIsAttached;
    ItemAnimator mItemAnimator;
    private ItemAnimator.ItemAnimatorListener mItemAnimatorListener;
    private Runnable mItemAnimatorRunner;
    final ArrayList<ItemDecoration> mItemDecorations;
    boolean mItemsAddedOrRemoved;
    boolean mItemsChanged;
    private int mLastTouchX;
    private int mLastTouchY;
    LayoutManager mLayout;
    private int mLayoutOrScrollCounter;
    boolean mLayoutSuppressed;
    boolean mLayoutWasDefered;
    private EdgeEffect mLeftGlow;
    private final int mMaxFlingVelocity;
    private final int mMinFlingVelocity;
    private final int[] mMinMaxLayoutPositions;
    private final int[] mNestedOffsets;
    private final RecyclerViewDataObserver mObserver;
    private List<OnChildAttachStateChangeListener> mOnChildAttachStateListeners;
    private OnFlingListener mOnFlingListener;
    private final ArrayList<OnItemTouchListener> mOnItemTouchListeners;
    final List<ViewHolder> mPendingAccessibilityImportanceChange;
    private SavedState mPendingSavedState;
    boolean mPostedAnimatorRunner;
    GapWorker.LayoutPrefetchRegistryImpl mPrefetchRegistry;
    private boolean mPreserveFocusAfterLayout;
    final Recycler mRecycler;
    RecyclerListener mRecyclerListener;
    final int[] mReusableIntPair;
    private EdgeEffect mRightGlow;
    private float mScaledHorizontalScrollFactor;
    private float mScaledVerticalScrollFactor;
    private OnScrollListener mScrollListener;
    private List<OnScrollListener> mScrollListeners;
    private final int[] mScrollOffset;
    private int mScrollPointerId;
    private int mScrollState;
    private NestedScrollingChildHelper mScrollingChildHelper;
    final State mState;
    final Rect mTempRect;
    private final Rect mTempRect2;
    final RectF mTempRectF;
    private EdgeEffect mTopGlow;
    private int mTouchSlop;
    final Runnable mUpdateChildViewsRunnable;
    private VelocityTracker mVelocityTracker;
    final ViewFlinger mViewFlinger;
    private final ViewInfoStore.ProcessCallback mViewInfoProcessCallback;
    final ViewInfoStore mViewInfoStore;

    @Retention(RetentionPolicy.SOURCE)
    public @interface Orientation {
    }

    public interface ChildDrawingOrderCallback {
        int onGetChildDrawingOrder(int i, int i2);
    }

    public interface OnChildAttachStateChangeListener {
        void onChildViewAttachedToWindow(View view);

        void onChildViewDetachedFromWindow(View view);
    }

    public static abstract class OnFlingListener {
        public abstract boolean onFling(int i, int i2);
    }

    public interface OnItemTouchListener {
        boolean onInterceptTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent);

        void onRequestDisallowInterceptTouchEvent(boolean z);

        void onTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent);
    }

    public interface RecyclerListener {
        void onViewRecycled(ViewHolder viewHolder);
    }

    public static abstract class ViewCacheExtension {
        public abstract View getViewForPositionAndType(Recycler recycler, int i, int i2);
    }

    public RecyclerView(Context context) {
        this(context, (AttributeSet) null);
    }

    public RecyclerView(Context context, AttributeSet attrs) {
        this(context, attrs, C0926R.attr.recyclerViewStyle);
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public RecyclerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Context context2 = context;
        AttributeSet attributeSet = attrs;
        int i = defStyleAttr;
        this.mObserver = new RecyclerViewDataObserver();
        this.mRecycler = new Recycler();
        this.mViewInfoStore = new ViewInfoStore();
        this.mUpdateChildViewsRunnable = new Runnable() {
            public void run() {
                if (RecyclerView.this.mFirstLayoutComplete && !RecyclerView.this.isLayoutRequested()) {
                    if (!RecyclerView.this.mIsAttached) {
                        RecyclerView.this.requestLayout();
                    } else if (RecyclerView.this.mLayoutSuppressed) {
                        RecyclerView.this.mLayoutWasDefered = true;
                    } else {
                        RecyclerView.this.consumePendingUpdateOperations();
                    }
                }
            }
        };
        this.mTempRect = new Rect();
        this.mTempRect2 = new Rect();
        this.mTempRectF = new RectF();
        this.mItemDecorations = new ArrayList<>();
        this.mOnItemTouchListeners = new ArrayList<>();
        this.mInterceptRequestLayoutDepth = 0;
        this.mDataSetHasChangedAfterLayout = false;
        this.mDispatchItemsChangedEvent = false;
        this.mLayoutOrScrollCounter = 0;
        this.mDispatchScrollCounter = 0;
        this.mEdgeEffectFactory = new EdgeEffectFactory();
        this.mItemAnimator = new DefaultItemAnimator();
        this.mScrollState = 0;
        this.mScrollPointerId = -1;
        this.mScaledHorizontalScrollFactor = Float.MIN_VALUE;
        this.mScaledVerticalScrollFactor = Float.MIN_VALUE;
        this.mPreserveFocusAfterLayout = true;
        this.mViewFlinger = new ViewFlinger();
        this.mPrefetchRegistry = ALLOW_THREAD_GAP_WORK ? new GapWorker.LayoutPrefetchRegistryImpl() : null;
        this.mState = new State();
        this.mItemsAddedOrRemoved = false;
        this.mItemsChanged = false;
        this.mItemAnimatorListener = new ItemAnimatorRestoreListener();
        this.mPostedAnimatorRunner = false;
        this.mMinMaxLayoutPositions = new int[2];
        this.mScrollOffset = new int[2];
        this.mNestedOffsets = new int[2];
        this.mReusableIntPair = new int[2];
        this.mPendingAccessibilityImportanceChange = new ArrayList();
        this.mItemAnimatorRunner = new Runnable() {
            public void run() {
                if (RecyclerView.this.mItemAnimator != null) {
                    RecyclerView.this.mItemAnimator.runPendingAnimations();
                }
                RecyclerView.this.mPostedAnimatorRunner = false;
            }
        };
        this.mViewInfoProcessCallback = new ViewInfoStore.ProcessCallback() {
            public void processDisappeared(ViewHolder viewHolder, ItemAnimator.ItemHolderInfo info, ItemAnimator.ItemHolderInfo postInfo) {
                RecyclerView.this.mRecycler.unscrapView(viewHolder);
                RecyclerView.this.animateDisappearance(viewHolder, info, postInfo);
            }

            public void processAppeared(ViewHolder viewHolder, ItemAnimator.ItemHolderInfo preInfo, ItemAnimator.ItemHolderInfo info) {
                RecyclerView.this.animateAppearance(viewHolder, preInfo, info);
            }

            public void processPersistent(ViewHolder viewHolder, ItemAnimator.ItemHolderInfo preInfo, ItemAnimator.ItemHolderInfo postInfo) {
                viewHolder.setIsRecyclable(false);
                if (RecyclerView.this.mDataSetHasChangedAfterLayout) {
                    if (RecyclerView.this.mItemAnimator.animateChange(viewHolder, viewHolder, preInfo, postInfo)) {
                        RecyclerView.this.postAnimationRunner();
                    }
                } else if (RecyclerView.this.mItemAnimator.animatePersistence(viewHolder, preInfo, postInfo)) {
                    RecyclerView.this.postAnimationRunner();
                }
            }

            public void unused(ViewHolder viewHolder) {
                RecyclerView.this.mLayout.removeAndRecycleView(viewHolder.itemView, RecyclerView.this.mRecycler);
            }
        };
        setScrollContainer(true);
        setFocusableInTouchMode(true);
        ViewConfiguration vc = ViewConfiguration.get(context);
        this.mTouchSlop = vc.getScaledTouchSlop();
        this.mScaledHorizontalScrollFactor = ViewConfigurationCompat.getScaledHorizontalScrollFactor(vc, context2);
        this.mScaledVerticalScrollFactor = ViewConfigurationCompat.getScaledVerticalScrollFactor(vc, context2);
        this.mMinFlingVelocity = vc.getScaledMinimumFlingVelocity();
        this.mMaxFlingVelocity = vc.getScaledMaximumFlingVelocity();
        setWillNotDraw(getOverScrollMode() == 2);
        this.mItemAnimator.setListener(this.mItemAnimatorListener);
        initAdapterManager();
        initChildrenHelper();
        initAutofill();
        if (ViewCompat.getImportantForAccessibility(this) == 0) {
            ViewCompat.setImportantForAccessibility(this, 1);
        }
        this.mAccessibilityManager = (AccessibilityManager) getContext().getSystemService("accessibility");
        setAccessibilityDelegateCompat(new RecyclerViewAccessibilityDelegate(this));
        TypedArray a = context2.obtainStyledAttributes(attributeSet, C0926R.styleable.RecyclerView, i, 0);
        if (Build.VERSION.SDK_INT >= 29) {
            saveAttributeDataForStyleable(context, C0926R.styleable.RecyclerView, attrs, a, defStyleAttr, 0);
        }
        String layoutManagerName = a.getString(C0926R.styleable.RecyclerView_layoutManager);
        int descendantFocusability = a.getInt(C0926R.styleable.RecyclerView_android_descendantFocusability, -1);
        if (descendantFocusability == -1) {
            setDescendantFocusability(262144);
        }
        this.mClipToPadding = a.getBoolean(C0926R.styleable.RecyclerView_android_clipToPadding, true);
        boolean z = a.getBoolean(C0926R.styleable.RecyclerView_fastScrollEnabled, false);
        this.mEnableFastScroller = z;
        if (z) {
            initFastScroller((StateListDrawable) a.getDrawable(C0926R.styleable.RecyclerView_fastScrollVerticalThumbDrawable), a.getDrawable(C0926R.styleable.RecyclerView_fastScrollVerticalTrackDrawable), (StateListDrawable) a.getDrawable(C0926R.styleable.RecyclerView_fastScrollHorizontalThumbDrawable), a.getDrawable(C0926R.styleable.RecyclerView_fastScrollHorizontalTrackDrawable));
        }
        a.recycle();
        createLayoutManager(context, layoutManagerName, attrs, defStyleAttr, 0);
        boolean nestedScrollingEnabled = true;
        if (Build.VERSION.SDK_INT >= 21) {
            int[] iArr = NESTED_SCROLLING_ATTRS;
            TypedArray a2 = context2.obtainStyledAttributes(attributeSet, iArr, i, 0);
            if (Build.VERSION.SDK_INT >= 29) {
                int i2 = descendantFocusability;
                saveAttributeDataForStyleable(context, iArr, attrs, a2, defStyleAttr, 0);
            }
            nestedScrollingEnabled = a2.getBoolean(0, true);
            a2.recycle();
        }
        setNestedScrollingEnabled(nestedScrollingEnabled);
    }

    /* access modifiers changed from: package-private */
    public String exceptionLabel() {
        return " " + super.toString() + ", adapter:" + this.mAdapter + ", layout:" + this.mLayout + ", context:" + getContext();
    }

    private void initAutofill() {
        if (ViewCompat.getImportantForAutofill(this) == 0) {
            ViewCompat.setImportantForAutofill(this, 8);
        }
    }

    public RecyclerViewAccessibilityDelegate getCompatAccessibilityDelegate() {
        return this.mAccessibilityDelegate;
    }

    public void setAccessibilityDelegateCompat(RecyclerViewAccessibilityDelegate accessibilityDelegate) {
        this.mAccessibilityDelegate = accessibilityDelegate;
        ViewCompat.setAccessibilityDelegate(this, accessibilityDelegate);
    }

    public CharSequence getAccessibilityClassName() {
        return "androidx.recyclerview.widget.RecyclerView";
    }

    private void createLayoutManager(Context context, String className, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        ClassLoader classLoader;
        Constructor<? extends U> constructor;
        if (className != null) {
            String className2 = className.trim();
            if (!className2.isEmpty()) {
                String className3 = getFullClassName(context, className2);
                try {
                    if (isInEditMode()) {
                        classLoader = getClass().getClassLoader();
                    } else {
                        classLoader = context.getClassLoader();
                    }
                    Class<? extends U> asSubclass = Class.forName(className3, false, classLoader).asSubclass(LayoutManager.class);
                    Object[] constructorArgs = null;
                    try {
                        constructor = asSubclass.getConstructor(LAYOUT_MANAGER_CONSTRUCTOR_SIGNATURE);
                        constructorArgs = new Object[]{context, attrs, Integer.valueOf(defStyleAttr), Integer.valueOf(defStyleRes)};
                    } catch (NoSuchMethodException e) {
                        constructor = asSubclass.getConstructor(new Class[0]);
                    }
                    constructor.setAccessible(true);
                    setLayoutManager((LayoutManager) constructor.newInstance(constructorArgs));
                } catch (NoSuchMethodException e1) {
                    e1.initCause(e);
                    throw new IllegalStateException(attrs.getPositionDescription() + ": Error creating LayoutManager " + className3, e1);
                } catch (ClassNotFoundException e2) {
                    throw new IllegalStateException(attrs.getPositionDescription() + ": Unable to find LayoutManager " + className3, e2);
                } catch (InvocationTargetException e3) {
                    throw new IllegalStateException(attrs.getPositionDescription() + ": Could not instantiate the LayoutManager: " + className3, e3);
                } catch (InstantiationException e4) {
                    throw new IllegalStateException(attrs.getPositionDescription() + ": Could not instantiate the LayoutManager: " + className3, e4);
                } catch (IllegalAccessException e5) {
                    throw new IllegalStateException(attrs.getPositionDescription() + ": Cannot access non-public constructor " + className3, e5);
                } catch (ClassCastException e6) {
                    throw new IllegalStateException(attrs.getPositionDescription() + ": Class is not a LayoutManager " + className3, e6);
                }
            }
        }
    }

    private String getFullClassName(Context context, String className) {
        if (className.charAt(0) == '.') {
            return context.getPackageName() + className;
        }
        if (className.contains(".")) {
            return className;
        }
        return RecyclerView.class.getPackage().getName() + '.' + className;
    }

    private void initChildrenHelper() {
        this.mChildHelper = new ChildHelper(new ChildHelper.Callback() {
            public int getChildCount() {
                return RecyclerView.this.getChildCount();
            }

            public void addView(View child, int index) {
                RecyclerView.this.addView(child, index);
                RecyclerView.this.dispatchChildAttached(child);
            }

            public int indexOfChild(View view) {
                return RecyclerView.this.indexOfChild(view);
            }

            public void removeViewAt(int index) {
                View child = RecyclerView.this.getChildAt(index);
                if (child != null) {
                    RecyclerView.this.dispatchChildDetached(child);
                    child.clearAnimation();
                }
                RecyclerView.this.removeViewAt(index);
            }

            public View getChildAt(int offset) {
                return RecyclerView.this.getChildAt(offset);
            }

            public void removeAllViews() {
                int count = getChildCount();
                for (int i = 0; i < count; i++) {
                    View child = getChildAt(i);
                    RecyclerView.this.dispatchChildDetached(child);
                    child.clearAnimation();
                }
                RecyclerView.this.removeAllViews();
            }

            public ViewHolder getChildViewHolder(View view) {
                return RecyclerView.getChildViewHolderInt(view);
            }

            public void attachViewToParent(View child, int index, ViewGroup.LayoutParams layoutParams) {
                ViewHolder vh = RecyclerView.getChildViewHolderInt(child);
                if (vh != null) {
                    if (vh.isTmpDetached() || vh.shouldIgnore()) {
                        vh.clearTmpDetachFlag();
                    } else {
                        throw new IllegalArgumentException("Called attach on a child which is not detached: " + vh + RecyclerView.this.exceptionLabel());
                    }
                }
                RecyclerView.this.attachViewToParent(child, index, layoutParams);
            }

            public void detachViewFromParent(int offset) {
                ViewHolder vh;
                View view = getChildAt(offset);
                if (!(view == null || (vh = RecyclerView.getChildViewHolderInt(view)) == null)) {
                    if (!vh.isTmpDetached() || vh.shouldIgnore()) {
                        vh.addFlags(256);
                    } else {
                        throw new IllegalArgumentException("called detach on an already detached child " + vh + RecyclerView.this.exceptionLabel());
                    }
                }
                RecyclerView.this.detachViewFromParent(offset);
            }

            public void onEnteredHiddenState(View child) {
                ViewHolder vh = RecyclerView.getChildViewHolderInt(child);
                if (vh != null) {
                    vh.onEnteredHiddenState(RecyclerView.this);
                }
            }

            public void onLeftHiddenState(View child) {
                ViewHolder vh = RecyclerView.getChildViewHolderInt(child);
                if (vh != null) {
                    vh.onLeftHiddenState(RecyclerView.this);
                }
            }
        });
    }

    /* access modifiers changed from: package-private */
    public void initAdapterManager() {
        this.mAdapterHelper = new AdapterHelper(new AdapterHelper.Callback() {
            public ViewHolder findViewHolder(int position) {
                ViewHolder vh = RecyclerView.this.findViewHolderForPosition(position, true);
                if (vh != null && !RecyclerView.this.mChildHelper.isHidden(vh.itemView)) {
                    return vh;
                }
                return null;
            }

            public void offsetPositionsForRemovingInvisible(int start, int count) {
                RecyclerView.this.offsetPositionRecordsForRemove(start, count, true);
                RecyclerView.this.mItemsAddedOrRemoved = true;
                RecyclerView.this.mState.mDeletedInvisibleItemCountSincePreviousLayout += count;
            }

            public void offsetPositionsForRemovingLaidOutOrNewView(int positionStart, int itemCount) {
                RecyclerView.this.offsetPositionRecordsForRemove(positionStart, itemCount, false);
                RecyclerView.this.mItemsAddedOrRemoved = true;
            }

            public void markViewHoldersUpdated(int positionStart, int itemCount, Object payload) {
                RecyclerView.this.viewRangeUpdate(positionStart, itemCount, payload);
                RecyclerView.this.mItemsChanged = true;
            }

            public void onDispatchFirstPass(AdapterHelper.UpdateOp op) {
                dispatchUpdate(op);
            }

            /* access modifiers changed from: package-private */
            public void dispatchUpdate(AdapterHelper.UpdateOp op) {
                switch (op.cmd) {
                    case 1:
                        RecyclerView.this.mLayout.onItemsAdded(RecyclerView.this, op.positionStart, op.itemCount);
                        return;
                    case 2:
                        RecyclerView.this.mLayout.onItemsRemoved(RecyclerView.this, op.positionStart, op.itemCount);
                        return;
                    case 4:
                        RecyclerView.this.mLayout.onItemsUpdated(RecyclerView.this, op.positionStart, op.itemCount, op.payload);
                        return;
                    case 8:
                        RecyclerView.this.mLayout.onItemsMoved(RecyclerView.this, op.positionStart, op.itemCount, 1);
                        return;
                    default:
                        return;
                }
            }

            public void onDispatchSecondPass(AdapterHelper.UpdateOp op) {
                dispatchUpdate(op);
            }

            public void offsetPositionsForAdd(int positionStart, int itemCount) {
                RecyclerView.this.offsetPositionRecordsForInsert(positionStart, itemCount);
                RecyclerView.this.mItemsAddedOrRemoved = true;
            }

            public void offsetPositionsForMove(int from, int to) {
                RecyclerView.this.offsetPositionRecordsForMove(from, to);
                RecyclerView.this.mItemsAddedOrRemoved = true;
            }
        });
    }

    public void setHasFixedSize(boolean hasFixedSize) {
        this.mHasFixedSize = hasFixedSize;
    }

    public boolean hasFixedSize() {
        return this.mHasFixedSize;
    }

    public void setClipToPadding(boolean clipToPadding) {
        if (clipToPadding != this.mClipToPadding) {
            invalidateGlows();
        }
        this.mClipToPadding = clipToPadding;
        super.setClipToPadding(clipToPadding);
        if (this.mFirstLayoutComplete) {
            requestLayout();
        }
    }

    public boolean getClipToPadding() {
        return this.mClipToPadding;
    }

    public void setScrollingTouchSlop(int slopConstant) {
        ViewConfiguration vc = ViewConfiguration.get(getContext());
        switch (slopConstant) {
            case 0:
                break;
            case 1:
                this.mTouchSlop = vc.getScaledPagingTouchSlop();
                return;
            default:
                Log.w(TAG, "setScrollingTouchSlop(): bad argument constant " + slopConstant + "; using default value");
                break;
        }
        this.mTouchSlop = vc.getScaledTouchSlop();
    }

    public void swapAdapter(Adapter adapter, boolean removeAndRecycleExistingViews) {
        setLayoutFrozen(false);
        setAdapterInternal(adapter, true, removeAndRecycleExistingViews);
        processDataSetCompletelyChanged(true);
        requestLayout();
    }

    public void setAdapter(Adapter adapter) {
        setLayoutFrozen(false);
        setAdapterInternal(adapter, false, true);
        processDataSetCompletelyChanged(false);
        requestLayout();
    }

    /* access modifiers changed from: package-private */
    public void removeAndRecycleViews() {
        ItemAnimator itemAnimator = this.mItemAnimator;
        if (itemAnimator != null) {
            itemAnimator.endAnimations();
        }
        LayoutManager layoutManager = this.mLayout;
        if (layoutManager != null) {
            layoutManager.removeAndRecycleAllViews(this.mRecycler);
            this.mLayout.removeAndRecycleScrapInt(this.mRecycler);
        }
        this.mRecycler.clear();
    }

    private void setAdapterInternal(Adapter adapter, boolean compatibleWithPrevious, boolean removeAndRecycleViews) {
        Adapter adapter2 = this.mAdapter;
        if (adapter2 != null) {
            adapter2.unregisterAdapterDataObserver(this.mObserver);
            this.mAdapter.onDetachedFromRecyclerView(this);
        }
        if (!compatibleWithPrevious || removeAndRecycleViews) {
            removeAndRecycleViews();
        }
        this.mAdapterHelper.reset();
        Adapter oldAdapter = this.mAdapter;
        this.mAdapter = adapter;
        if (adapter != null) {
            adapter.registerAdapterDataObserver(this.mObserver);
            adapter.onAttachedToRecyclerView(this);
        }
        LayoutManager layoutManager = this.mLayout;
        if (layoutManager != null) {
            layoutManager.onAdapterChanged(oldAdapter, this.mAdapter);
        }
        this.mRecycler.onAdapterChanged(oldAdapter, this.mAdapter, compatibleWithPrevious);
        this.mState.mStructureChanged = true;
    }

    public Adapter getAdapter() {
        return this.mAdapter;
    }

    public void setRecyclerListener(RecyclerListener listener) {
        this.mRecyclerListener = listener;
    }

    public int getBaseline() {
        LayoutManager layoutManager = this.mLayout;
        if (layoutManager != null) {
            return layoutManager.getBaseline();
        }
        return super.getBaseline();
    }

    public void addOnChildAttachStateChangeListener(OnChildAttachStateChangeListener listener) {
        if (this.mOnChildAttachStateListeners == null) {
            this.mOnChildAttachStateListeners = new ArrayList();
        }
        this.mOnChildAttachStateListeners.add(listener);
    }

    public void removeOnChildAttachStateChangeListener(OnChildAttachStateChangeListener listener) {
        List<OnChildAttachStateChangeListener> list = this.mOnChildAttachStateListeners;
        if (list != null) {
            list.remove(listener);
        }
    }

    public void clearOnChildAttachStateChangeListeners() {
        List<OnChildAttachStateChangeListener> list = this.mOnChildAttachStateListeners;
        if (list != null) {
            list.clear();
        }
    }

    public void setLayoutManager(LayoutManager layout) {
        if (layout != this.mLayout) {
            stopScroll();
            if (this.mLayout != null) {
                ItemAnimator itemAnimator = this.mItemAnimator;
                if (itemAnimator != null) {
                    itemAnimator.endAnimations();
                }
                this.mLayout.removeAndRecycleAllViews(this.mRecycler);
                this.mLayout.removeAndRecycleScrapInt(this.mRecycler);
                this.mRecycler.clear();
                if (this.mIsAttached) {
                    this.mLayout.dispatchDetachedFromWindow(this, this.mRecycler);
                }
                this.mLayout.setRecyclerView((RecyclerView) null);
                this.mLayout = null;
            } else {
                this.mRecycler.clear();
            }
            this.mChildHelper.removeAllViewsUnfiltered();
            this.mLayout = layout;
            if (layout != null) {
                if (layout.mRecyclerView == null) {
                    this.mLayout.setRecyclerView(this);
                    if (this.mIsAttached) {
                        this.mLayout.dispatchAttachedToWindow(this);
                    }
                } else {
                    throw new IllegalArgumentException("LayoutManager " + layout + " is already attached to a RecyclerView:" + layout.mRecyclerView.exceptionLabel());
                }
            }
            this.mRecycler.updateViewCacheSize();
            requestLayout();
        }
    }

    public void setOnFlingListener(OnFlingListener onFlingListener) {
        this.mOnFlingListener = onFlingListener;
    }

    public OnFlingListener getOnFlingListener() {
        return this.mOnFlingListener;
    }

    /* access modifiers changed from: protected */
    public Parcelable onSaveInstanceState() {
        SavedState state = new SavedState(super.onSaveInstanceState());
        SavedState savedState = this.mPendingSavedState;
        if (savedState != null) {
            state.copyFrom(savedState);
        } else {
            LayoutManager layoutManager = this.mLayout;
            if (layoutManager != null) {
                state.mLayoutState = layoutManager.onSaveInstanceState();
            } else {
                state.mLayoutState = null;
            }
        }
        return state;
    }

    /* access modifiers changed from: protected */
    public void onRestoreInstanceState(Parcelable state) {
        if (!(state instanceof SavedState)) {
            super.onRestoreInstanceState(state);
            return;
        }
        SavedState savedState = (SavedState) state;
        this.mPendingSavedState = savedState;
        super.onRestoreInstanceState(savedState.getSuperState());
        if (this.mLayout != null && this.mPendingSavedState.mLayoutState != null) {
            this.mLayout.onRestoreInstanceState(this.mPendingSavedState.mLayoutState);
        }
    }

    /* access modifiers changed from: protected */
    public void dispatchSaveInstanceState(SparseArray<Parcelable> container) {
        dispatchFreezeSelfOnly(container);
    }

    /* access modifiers changed from: protected */
    public void dispatchRestoreInstanceState(SparseArray<Parcelable> container) {
        dispatchThawSelfOnly(container);
    }

    private void addAnimatingView(ViewHolder viewHolder) {
        View view = viewHolder.itemView;
        boolean alreadyParented = view.getParent() == this;
        this.mRecycler.unscrapView(getChildViewHolder(view));
        if (viewHolder.isTmpDetached()) {
            this.mChildHelper.attachViewToParent(view, -1, view.getLayoutParams(), true);
        } else if (!alreadyParented) {
            this.mChildHelper.addView(view, true);
        } else {
            this.mChildHelper.hide(view);
        }
    }

    /* access modifiers changed from: package-private */
    public boolean removeAnimatingView(View view) {
        startInterceptRequestLayout();
        boolean removed = this.mChildHelper.removeViewIfHidden(view);
        if (removed) {
            ViewHolder viewHolder = getChildViewHolderInt(view);
            this.mRecycler.unscrapView(viewHolder);
            this.mRecycler.recycleViewHolderInternal(viewHolder);
        }
        stopInterceptRequestLayout(!removed);
        return removed;
    }

    public LayoutManager getLayoutManager() {
        return this.mLayout;
    }

    public RecycledViewPool getRecycledViewPool() {
        return this.mRecycler.getRecycledViewPool();
    }

    public void setRecycledViewPool(RecycledViewPool pool) {
        this.mRecycler.setRecycledViewPool(pool);
    }

    public void setViewCacheExtension(ViewCacheExtension extension) {
        this.mRecycler.setViewCacheExtension(extension);
    }

    public void setItemViewCacheSize(int size) {
        this.mRecycler.setViewCacheSize(size);
    }

    public int getScrollState() {
        return this.mScrollState;
    }

    /* access modifiers changed from: package-private */
    public void setScrollState(int state) {
        if (state != this.mScrollState) {
            this.mScrollState = state;
            if (state != 2) {
                stopScrollersInternal();
            }
            dispatchOnScrollStateChanged(state);
        }
    }

    public void addItemDecoration(ItemDecoration decor, int index) {
        LayoutManager layoutManager = this.mLayout;
        if (layoutManager != null) {
            layoutManager.assertNotInLayoutOrScroll("Cannot add item decoration during a scroll  or layout");
        }
        if (this.mItemDecorations.isEmpty()) {
            setWillNotDraw(false);
        }
        if (index < 0) {
            this.mItemDecorations.add(decor);
        } else {
            this.mItemDecorations.add(index, decor);
        }
        markItemDecorInsetsDirty();
        requestLayout();
    }

    public void addItemDecoration(ItemDecoration decor) {
        addItemDecoration(decor, -1);
    }

    public ItemDecoration getItemDecorationAt(int index) {
        int size = getItemDecorationCount();
        if (index >= 0 && index < size) {
            return this.mItemDecorations.get(index);
        }
        throw new IndexOutOfBoundsException(index + " is an invalid index for size " + size);
    }

    public int getItemDecorationCount() {
        return this.mItemDecorations.size();
    }

    public void removeItemDecorationAt(int index) {
        int size = getItemDecorationCount();
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(index + " is an invalid index for size " + size);
        }
        removeItemDecoration(getItemDecorationAt(index));
    }

    public void removeItemDecoration(ItemDecoration decor) {
        LayoutManager layoutManager = this.mLayout;
        if (layoutManager != null) {
            layoutManager.assertNotInLayoutOrScroll("Cannot remove item decoration during a scroll  or layout");
        }
        this.mItemDecorations.remove(decor);
        if (this.mItemDecorations.isEmpty()) {
            setWillNotDraw(getOverScrollMode() == 2);
        }
        markItemDecorInsetsDirty();
        requestLayout();
    }

    public void setChildDrawingOrderCallback(ChildDrawingOrderCallback childDrawingOrderCallback) {
        if (childDrawingOrderCallback != this.mChildDrawingOrderCallback) {
            this.mChildDrawingOrderCallback = childDrawingOrderCallback;
            setChildrenDrawingOrderEnabled(childDrawingOrderCallback != null);
        }
    }

    @Deprecated
    public void setOnScrollListener(OnScrollListener listener) {
        this.mScrollListener = listener;
    }

    public void addOnScrollListener(OnScrollListener listener) {
        if (this.mScrollListeners == null) {
            this.mScrollListeners = new ArrayList();
        }
        this.mScrollListeners.add(listener);
    }

    public void removeOnScrollListener(OnScrollListener listener) {
        List<OnScrollListener> list = this.mScrollListeners;
        if (list != null) {
            list.remove(listener);
        }
    }

    public void clearOnScrollListeners() {
        List<OnScrollListener> list = this.mScrollListeners;
        if (list != null) {
            list.clear();
        }
    }

    public void scrollToPosition(int position) {
        if (!this.mLayoutSuppressed) {
            stopScroll();
            LayoutManager layoutManager = this.mLayout;
            if (layoutManager == null) {
                Log.e(TAG, "Cannot scroll to position a LayoutManager set. Call setLayoutManager with a non-null argument.");
                return;
            }
            layoutManager.scrollToPosition(position);
            awakenScrollBars();
        }
    }

    /* access modifiers changed from: package-private */
    public void jumpToPositionForSmoothScroller(int position) {
        if (this.mLayout != null) {
            setScrollState(2);
            this.mLayout.scrollToPosition(position);
            awakenScrollBars();
        }
    }

    public void smoothScrollToPosition(int position) {
        if (!this.mLayoutSuppressed) {
            LayoutManager layoutManager = this.mLayout;
            if (layoutManager == null) {
                Log.e(TAG, "Cannot smooth scroll without a LayoutManager set. Call setLayoutManager with a non-null argument.");
            } else {
                layoutManager.smoothScrollToPosition(this, this.mState, position);
            }
        }
    }

    public void scrollTo(int x, int y) {
        Log.w(TAG, "RecyclerView does not support scrolling to an absolute position. Use scrollToPosition instead");
    }

    public void scrollBy(int x, int y) {
        LayoutManager layoutManager = this.mLayout;
        if (layoutManager == null) {
            Log.e(TAG, "Cannot scroll without a LayoutManager set. Call setLayoutManager with a non-null argument.");
        } else if (!this.mLayoutSuppressed) {
            boolean canScrollHorizontal = layoutManager.canScrollHorizontally();
            boolean canScrollVertical = this.mLayout.canScrollVertically();
            if (canScrollHorizontal || canScrollVertical) {
                int i = 0;
                int i2 = canScrollHorizontal ? x : 0;
                if (canScrollVertical) {
                    i = y;
                }
                scrollByInternal(i2, i, (MotionEvent) null);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void scrollStep(int dx, int dy, int[] consumed) {
        startInterceptRequestLayout();
        onEnterLayoutOrScroll();
        TraceCompat.beginSection(TRACE_SCROLL_TAG);
        fillRemainingScrollValues(this.mState);
        int consumedX = 0;
        int consumedY = 0;
        if (dx != 0) {
            consumedX = this.mLayout.scrollHorizontallyBy(dx, this.mRecycler, this.mState);
        }
        if (dy != 0) {
            consumedY = this.mLayout.scrollVerticallyBy(dy, this.mRecycler, this.mState);
        }
        TraceCompat.endSection();
        repositionShadowingViews();
        onExitLayoutOrScroll();
        stopInterceptRequestLayout(false);
        if (consumed != null) {
            consumed[0] = consumedX;
            consumed[1] = consumedY;
        }
    }

    /* access modifiers changed from: package-private */
    public void consumePendingUpdateOperations() {
        if (!this.mFirstLayoutComplete || this.mDataSetHasChangedAfterLayout) {
            TraceCompat.beginSection(TRACE_ON_DATA_SET_CHANGE_LAYOUT_TAG);
            dispatchLayout();
            TraceCompat.endSection();
        } else if (this.mAdapterHelper.hasPendingUpdates()) {
            if (this.mAdapterHelper.hasAnyUpdateTypes(4) && !this.mAdapterHelper.hasAnyUpdateTypes(11)) {
                TraceCompat.beginSection(TRACE_HANDLE_ADAPTER_UPDATES_TAG);
                startInterceptRequestLayout();
                onEnterLayoutOrScroll();
                this.mAdapterHelper.preProcess();
                if (!this.mLayoutWasDefered) {
                    if (hasUpdatedView()) {
                        dispatchLayout();
                    } else {
                        this.mAdapterHelper.consumePostponedUpdates();
                    }
                }
                stopInterceptRequestLayout(true);
                onExitLayoutOrScroll();
                TraceCompat.endSection();
            } else if (this.mAdapterHelper.hasPendingUpdates()) {
                TraceCompat.beginSection(TRACE_ON_DATA_SET_CHANGE_LAYOUT_TAG);
                dispatchLayout();
                TraceCompat.endSection();
            }
        }
    }

    private boolean hasUpdatedView() {
        int childCount = this.mChildHelper.getChildCount();
        for (int i = 0; i < childCount; i++) {
            ViewHolder holder = getChildViewHolderInt(this.mChildHelper.getChildAt(i));
            if (holder != null && !holder.shouldIgnore() && holder.isUpdated()) {
                return true;
            }
        }
        return false;
    }

    /* access modifiers changed from: package-private */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x00b0, code lost:
        if (r3 != 0) goto L_0x00b5;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean scrollByInternal(int r22, int r23, android.view.MotionEvent r24) {
        /*
            r21 = this;
            r8 = r21
            r9 = r22
            r10 = r23
            r11 = r24
            r0 = 0
            r1 = 0
            r2 = 0
            r3 = 0
            r21.consumePendingUpdateOperations()
            androidx.recyclerview.widget.RecyclerView$Adapter r4 = r8.mAdapter
            r12 = 1
            r13 = 0
            if (r4 == 0) goto L_0x002d
            int[] r4 = r8.mReusableIntPair
            r4[r13] = r13
            r4[r12] = r13
            r8.scrollStep(r9, r10, r4)
            int[] r4 = r8.mReusableIntPair
            r2 = r4[r13]
            r3 = r4[r12]
            int r0 = r9 - r2
            int r1 = r10 - r3
            r14 = r0
            r15 = r1
            r7 = r2
            r6 = r3
            goto L_0x0031
        L_0x002d:
            r14 = r0
            r15 = r1
            r7 = r2
            r6 = r3
        L_0x0031:
            java.util.ArrayList<androidx.recyclerview.widget.RecyclerView$ItemDecoration> r0 = r8.mItemDecorations
            boolean r0 = r0.isEmpty()
            if (r0 != 0) goto L_0x003c
            r21.invalidate()
        L_0x003c:
            int[] r5 = r8.mReusableIntPair
            r5[r13] = r13
            r5[r12] = r13
            int[] r4 = r8.mScrollOffset
            r16 = 0
            r0 = r21
            r1 = r7
            r2 = r6
            r3 = r14
            r17 = r4
            r4 = r15
            r18 = r5
            r5 = r17
            r19 = r6
            r6 = r16
            r20 = r7
            r7 = r18
            r0.dispatchNestedScroll(r1, r2, r3, r4, r5, r6, r7)
            int[] r0 = r8.mReusableIntPair
            r1 = r0[r13]
            int r14 = r14 - r1
            r0 = r0[r12]
            int r15 = r15 - r0
            if (r1 != 0) goto L_0x006c
            if (r0 == 0) goto L_0x006a
            goto L_0x006c
        L_0x006a:
            r0 = 0
            goto L_0x006d
        L_0x006c:
            r0 = 1
        L_0x006d:
            int r1 = r8.mLastTouchX
            int[] r2 = r8.mScrollOffset
            r3 = r2[r13]
            int r1 = r1 - r3
            r8.mLastTouchX = r1
            int r1 = r8.mLastTouchY
            r2 = r2[r12]
            int r1 = r1 - r2
            r8.mLastTouchY = r1
            int[] r1 = r8.mNestedOffsets
            r4 = r1[r13]
            int r4 = r4 + r3
            r1[r13] = r4
            r3 = r1[r12]
            int r3 = r3 + r2
            r1[r12] = r3
            int r1 = r21.getOverScrollMode()
            r2 = 2
            if (r1 == r2) goto L_0x00aa
            if (r11 == 0) goto L_0x00a7
            r1 = 8194(0x2002, float:1.1482E-41)
            boolean r1 = androidx.core.view.MotionEventCompat.isFromSource(r11, r1)
            if (r1 != 0) goto L_0x00a7
            float r1 = r24.getX()
            float r2 = (float) r14
            float r3 = r24.getY()
            float r4 = (float) r15
            r8.pullGlows(r1, r2, r3, r4)
        L_0x00a7:
            r21.considerReleasingGlowsOnScroll(r22, r23)
        L_0x00aa:
            r2 = r20
            if (r2 != 0) goto L_0x00b3
            r3 = r19
            if (r3 == 0) goto L_0x00b8
            goto L_0x00b5
        L_0x00b3:
            r3 = r19
        L_0x00b5:
            r8.dispatchOnScrolled(r2, r3)
        L_0x00b8:
            boolean r1 = r21.awakenScrollBars()
            if (r1 != 0) goto L_0x00c1
            r21.invalidate()
        L_0x00c1:
            if (r0 != 0) goto L_0x00c9
            if (r2 != 0) goto L_0x00c9
            if (r3 == 0) goto L_0x00c8
            goto L_0x00c9
        L_0x00c8:
            r12 = 0
        L_0x00c9:
            return r12
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.recyclerview.widget.RecyclerView.scrollByInternal(int, int, android.view.MotionEvent):boolean");
    }

    public int computeHorizontalScrollOffset() {
        LayoutManager layoutManager = this.mLayout;
        if (layoutManager != null && layoutManager.canScrollHorizontally()) {
            return this.mLayout.computeHorizontalScrollOffset(this.mState);
        }
        return 0;
    }

    public int computeHorizontalScrollExtent() {
        LayoutManager layoutManager = this.mLayout;
        if (layoutManager != null && layoutManager.canScrollHorizontally()) {
            return this.mLayout.computeHorizontalScrollExtent(this.mState);
        }
        return 0;
    }

    public int computeHorizontalScrollRange() {
        LayoutManager layoutManager = this.mLayout;
        if (layoutManager != null && layoutManager.canScrollHorizontally()) {
            return this.mLayout.computeHorizontalScrollRange(this.mState);
        }
        return 0;
    }

    public int computeVerticalScrollOffset() {
        LayoutManager layoutManager = this.mLayout;
        if (layoutManager != null && layoutManager.canScrollVertically()) {
            return this.mLayout.computeVerticalScrollOffset(this.mState);
        }
        return 0;
    }

    public int computeVerticalScrollExtent() {
        LayoutManager layoutManager = this.mLayout;
        if (layoutManager != null && layoutManager.canScrollVertically()) {
            return this.mLayout.computeVerticalScrollExtent(this.mState);
        }
        return 0;
    }

    public int computeVerticalScrollRange() {
        LayoutManager layoutManager = this.mLayout;
        if (layoutManager != null && layoutManager.canScrollVertically()) {
            return this.mLayout.computeVerticalScrollRange(this.mState);
        }
        return 0;
    }

    /* access modifiers changed from: package-private */
    public void startInterceptRequestLayout() {
        int i = this.mInterceptRequestLayoutDepth + 1;
        this.mInterceptRequestLayoutDepth = i;
        if (i == 1 && !this.mLayoutSuppressed) {
            this.mLayoutWasDefered = false;
        }
    }

    /* access modifiers changed from: package-private */
    public void stopInterceptRequestLayout(boolean performLayoutChildren) {
        if (this.mInterceptRequestLayoutDepth < 1) {
            this.mInterceptRequestLayoutDepth = 1;
        }
        if (!performLayoutChildren && !this.mLayoutSuppressed) {
            this.mLayoutWasDefered = false;
        }
        if (this.mInterceptRequestLayoutDepth == 1) {
            if (performLayoutChildren && this.mLayoutWasDefered && !this.mLayoutSuppressed && this.mLayout != null && this.mAdapter != null) {
                dispatchLayout();
            }
            if (!this.mLayoutSuppressed) {
                this.mLayoutWasDefered = false;
            }
        }
        this.mInterceptRequestLayoutDepth--;
    }

    public final void suppressLayout(boolean suppress) {
        if (suppress != this.mLayoutSuppressed) {
            assertNotInLayoutOrScroll("Do not suppressLayout in layout or scroll");
            if (!suppress) {
                this.mLayoutSuppressed = false;
                if (!(!this.mLayoutWasDefered || this.mLayout == null || this.mAdapter == null)) {
                    requestLayout();
                }
                this.mLayoutWasDefered = false;
                return;
            }
            long now = SystemClock.uptimeMillis();
            onTouchEvent(MotionEvent.obtain(now, now, 3, 0.0f, 0.0f, 0));
            this.mLayoutSuppressed = true;
            this.mIgnoreMotionEventTillDown = true;
            stopScroll();
        }
    }

    public final boolean isLayoutSuppressed() {
        return this.mLayoutSuppressed;
    }

    @Deprecated
    public void setLayoutFrozen(boolean frozen) {
        suppressLayout(frozen);
    }

    @Deprecated
    public boolean isLayoutFrozen() {
        return isLayoutSuppressed();
    }

    @Deprecated
    public void setLayoutTransition(LayoutTransition transition) {
        if (transition == null) {
            super.setLayoutTransition((LayoutTransition) null);
            return;
        }
        throw new IllegalArgumentException("Providing a LayoutTransition into RecyclerView is not supported. Please use setItemAnimator() instead for animating changes to the items in this RecyclerView");
    }

    public void smoothScrollBy(int dx, int dy) {
        smoothScrollBy(dx, dy, (Interpolator) null);
    }

    public void smoothScrollBy(int dx, int dy, Interpolator interpolator) {
        smoothScrollBy(dx, dy, interpolator, Integer.MIN_VALUE);
    }

    public void smoothScrollBy(int dx, int dy, Interpolator interpolator, int duration) {
        smoothScrollBy(dx, dy, interpolator, duration, false);
    }

    /* access modifiers changed from: package-private */
    public void smoothScrollBy(int dx, int dy, Interpolator interpolator, int duration, boolean withNestedScrolling) {
        LayoutManager layoutManager = this.mLayout;
        if (layoutManager == null) {
            Log.e(TAG, "Cannot smooth scroll without a LayoutManager set. Call setLayoutManager with a non-null argument.");
        } else if (!this.mLayoutSuppressed) {
            if (!layoutManager.canScrollHorizontally()) {
                dx = 0;
            }
            if (!this.mLayout.canScrollVertically()) {
                dy = 0;
            }
            if (dx != 0 || dy != 0) {
                if (duration == Integer.MIN_VALUE || duration > 0) {
                    if (withNestedScrolling) {
                        int nestedScrollAxis = 0;
                        if (dx != 0) {
                            nestedScrollAxis = 0 | 1;
                        }
                        if (dy != 0) {
                            nestedScrollAxis |= 2;
                        }
                        startNestedScroll(nestedScrollAxis, 1);
                    }
                    this.mViewFlinger.smoothScrollBy(dx, dy, duration, interpolator);
                    return;
                }
                scrollBy(dx, dy);
            }
        }
    }

    public boolean fling(int velocityX, int velocityY) {
        LayoutManager layoutManager = this.mLayout;
        if (layoutManager == null) {
            Log.e(TAG, "Cannot fling without a LayoutManager set. Call setLayoutManager with a non-null argument.");
            return false;
        } else if (this.mLayoutSuppressed) {
            return false;
        } else {
            boolean canScrollHorizontal = layoutManager.canScrollHorizontally();
            boolean canScrollVertical = this.mLayout.canScrollVertically();
            if (!canScrollHorizontal || Math.abs(velocityX) < this.mMinFlingVelocity) {
                velocityX = 0;
            }
            if (!canScrollVertical || Math.abs(velocityY) < this.mMinFlingVelocity) {
                velocityY = 0;
            }
            if (!(velocityX == 0 && velocityY == 0) && !dispatchNestedPreFling((float) velocityX, (float) velocityY)) {
                boolean canScroll = canScrollHorizontal || canScrollVertical;
                dispatchNestedFling((float) velocityX, (float) velocityY, canScroll);
                OnFlingListener onFlingListener = this.mOnFlingListener;
                if (onFlingListener != null && onFlingListener.onFling(velocityX, velocityY)) {
                    return true;
                }
                if (canScroll) {
                    int nestedScrollAxis = 0;
                    if (canScrollHorizontal) {
                        nestedScrollAxis = 0 | 1;
                    }
                    if (canScrollVertical) {
                        nestedScrollAxis |= 2;
                    }
                    startNestedScroll(nestedScrollAxis, 1);
                    int i = this.mMaxFlingVelocity;
                    int velocityX2 = Math.max(-i, Math.min(velocityX, i));
                    int i2 = this.mMaxFlingVelocity;
                    this.mViewFlinger.fling(velocityX2, Math.max(-i2, Math.min(velocityY, i2)));
                    return true;
                }
            }
            return false;
        }
    }

    public void stopScroll() {
        setScrollState(0);
        stopScrollersInternal();
    }

    private void stopScrollersInternal() {
        this.mViewFlinger.stop();
        LayoutManager layoutManager = this.mLayout;
        if (layoutManager != null) {
            layoutManager.stopSmoothScroller();
        }
    }

    public int getMinFlingVelocity() {
        return this.mMinFlingVelocity;
    }

    public int getMaxFlingVelocity() {
        return this.mMaxFlingVelocity;
    }

    private void pullGlows(float x, float overscrollX, float y, float overscrollY) {
        boolean invalidate = false;
        if (overscrollX < 0.0f) {
            ensureLeftGlow();
            EdgeEffectCompat.onPull(this.mLeftGlow, (-overscrollX) / ((float) getWidth()), 1.0f - (y / ((float) getHeight())));
            invalidate = true;
        } else if (overscrollX > 0.0f) {
            ensureRightGlow();
            EdgeEffectCompat.onPull(this.mRightGlow, overscrollX / ((float) getWidth()), y / ((float) getHeight()));
            invalidate = true;
        }
        if (overscrollY < 0.0f) {
            ensureTopGlow();
            EdgeEffectCompat.onPull(this.mTopGlow, (-overscrollY) / ((float) getHeight()), x / ((float) getWidth()));
            invalidate = true;
        } else if (overscrollY > 0.0f) {
            ensureBottomGlow();
            EdgeEffectCompat.onPull(this.mBottomGlow, overscrollY / ((float) getHeight()), 1.0f - (x / ((float) getWidth())));
            invalidate = true;
        }
        if (invalidate || overscrollX != 0.0f || overscrollY != 0.0f) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    private void releaseGlows() {
        boolean needsInvalidate = false;
        EdgeEffect edgeEffect = this.mLeftGlow;
        if (edgeEffect != null) {
            edgeEffect.onRelease();
            needsInvalidate = this.mLeftGlow.isFinished();
        }
        EdgeEffect edgeEffect2 = this.mTopGlow;
        if (edgeEffect2 != null) {
            edgeEffect2.onRelease();
            needsInvalidate |= this.mTopGlow.isFinished();
        }
        EdgeEffect edgeEffect3 = this.mRightGlow;
        if (edgeEffect3 != null) {
            edgeEffect3.onRelease();
            needsInvalidate |= this.mRightGlow.isFinished();
        }
        EdgeEffect edgeEffect4 = this.mBottomGlow;
        if (edgeEffect4 != null) {
            edgeEffect4.onRelease();
            needsInvalidate |= this.mBottomGlow.isFinished();
        }
        if (needsInvalidate) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    /* access modifiers changed from: package-private */
    public void considerReleasingGlowsOnScroll(int dx, int dy) {
        boolean needsInvalidate = false;
        EdgeEffect edgeEffect = this.mLeftGlow;
        if (edgeEffect != null && !edgeEffect.isFinished() && dx > 0) {
            this.mLeftGlow.onRelease();
            needsInvalidate = this.mLeftGlow.isFinished();
        }
        EdgeEffect edgeEffect2 = this.mRightGlow;
        if (edgeEffect2 != null && !edgeEffect2.isFinished() && dx < 0) {
            this.mRightGlow.onRelease();
            needsInvalidate |= this.mRightGlow.isFinished();
        }
        EdgeEffect edgeEffect3 = this.mTopGlow;
        if (edgeEffect3 != null && !edgeEffect3.isFinished() && dy > 0) {
            this.mTopGlow.onRelease();
            needsInvalidate |= this.mTopGlow.isFinished();
        }
        EdgeEffect edgeEffect4 = this.mBottomGlow;
        if (edgeEffect4 != null && !edgeEffect4.isFinished() && dy < 0) {
            this.mBottomGlow.onRelease();
            needsInvalidate |= this.mBottomGlow.isFinished();
        }
        if (needsInvalidate) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    /* access modifiers changed from: package-private */
    public void absorbGlows(int velocityX, int velocityY) {
        if (velocityX < 0) {
            ensureLeftGlow();
            if (this.mLeftGlow.isFinished()) {
                this.mLeftGlow.onAbsorb(-velocityX);
            }
        } else if (velocityX > 0) {
            ensureRightGlow();
            if (this.mRightGlow.isFinished()) {
                this.mRightGlow.onAbsorb(velocityX);
            }
        }
        if (velocityY < 0) {
            ensureTopGlow();
            if (this.mTopGlow.isFinished()) {
                this.mTopGlow.onAbsorb(-velocityY);
            }
        } else if (velocityY > 0) {
            ensureBottomGlow();
            if (this.mBottomGlow.isFinished()) {
                this.mBottomGlow.onAbsorb(velocityY);
            }
        }
        if (velocityX != 0 || velocityY != 0) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    /* access modifiers changed from: package-private */
    public void ensureLeftGlow() {
        if (this.mLeftGlow == null) {
            EdgeEffect createEdgeEffect = this.mEdgeEffectFactory.createEdgeEffect(this, 0);
            this.mLeftGlow = createEdgeEffect;
            if (this.mClipToPadding) {
                createEdgeEffect.setSize((getMeasuredHeight() - getPaddingTop()) - getPaddingBottom(), (getMeasuredWidth() - getPaddingLeft()) - getPaddingRight());
            } else {
                createEdgeEffect.setSize(getMeasuredHeight(), getMeasuredWidth());
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void ensureRightGlow() {
        if (this.mRightGlow == null) {
            EdgeEffect createEdgeEffect = this.mEdgeEffectFactory.createEdgeEffect(this, 2);
            this.mRightGlow = createEdgeEffect;
            if (this.mClipToPadding) {
                createEdgeEffect.setSize((getMeasuredHeight() - getPaddingTop()) - getPaddingBottom(), (getMeasuredWidth() - getPaddingLeft()) - getPaddingRight());
            } else {
                createEdgeEffect.setSize(getMeasuredHeight(), getMeasuredWidth());
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void ensureTopGlow() {
        if (this.mTopGlow == null) {
            EdgeEffect createEdgeEffect = this.mEdgeEffectFactory.createEdgeEffect(this, 1);
            this.mTopGlow = createEdgeEffect;
            if (this.mClipToPadding) {
                createEdgeEffect.setSize((getMeasuredWidth() - getPaddingLeft()) - getPaddingRight(), (getMeasuredHeight() - getPaddingTop()) - getPaddingBottom());
            } else {
                createEdgeEffect.setSize(getMeasuredWidth(), getMeasuredHeight());
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void ensureBottomGlow() {
        if (this.mBottomGlow == null) {
            EdgeEffect createEdgeEffect = this.mEdgeEffectFactory.createEdgeEffect(this, 3);
            this.mBottomGlow = createEdgeEffect;
            if (this.mClipToPadding) {
                createEdgeEffect.setSize((getMeasuredWidth() - getPaddingLeft()) - getPaddingRight(), (getMeasuredHeight() - getPaddingTop()) - getPaddingBottom());
            } else {
                createEdgeEffect.setSize(getMeasuredWidth(), getMeasuredHeight());
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void invalidateGlows() {
        this.mBottomGlow = null;
        this.mTopGlow = null;
        this.mRightGlow = null;
        this.mLeftGlow = null;
    }

    public void setEdgeEffectFactory(EdgeEffectFactory edgeEffectFactory) {
        Preconditions.checkNotNull(edgeEffectFactory);
        this.mEdgeEffectFactory = edgeEffectFactory;
        invalidateGlows();
    }

    public EdgeEffectFactory getEdgeEffectFactory() {
        return this.mEdgeEffectFactory;
    }

    public View focusSearch(View focused, int direction) {
        View result;
        View result2 = this.mLayout.onInterceptFocusSearch(focused, direction);
        if (result2 != null) {
            return result2;
        }
        boolean z = true;
        boolean canRunFocusFailure = this.mAdapter != null && this.mLayout != null && !isComputingLayout() && !this.mLayoutSuppressed;
        FocusFinder ff = FocusFinder.getInstance();
        if (!canRunFocusFailure || !(direction == 2 || direction == 1)) {
            result = ff.findNextFocus(this, focused, direction);
            if (result == null && canRunFocusFailure) {
                consumePendingUpdateOperations();
                if (findContainingItemView(focused) == null) {
                    return null;
                }
                startInterceptRequestLayout();
                result = this.mLayout.onFocusSearchFailed(focused, direction, this.mRecycler, this.mState);
                stopInterceptRequestLayout(false);
            }
        } else {
            boolean needsFocusFailureLayout = false;
            if (this.mLayout.canScrollVertically()) {
                int absDir = direction == 2 ? 130 : 33;
                needsFocusFailureLayout = ff.findNextFocus(this, focused, absDir) == null;
                if (FORCE_ABS_FOCUS_SEARCH_DIRECTION) {
                    direction = absDir;
                }
            }
            if (!needsFocusFailureLayout && this.mLayout.canScrollHorizontally()) {
                int absDir2 = (direction == 2) ^ (this.mLayout.getLayoutDirection() == 1) ? 66 : 17;
                if (ff.findNextFocus(this, focused, absDir2) != null) {
                    z = false;
                }
                needsFocusFailureLayout = z;
                if (FORCE_ABS_FOCUS_SEARCH_DIRECTION) {
                    direction = absDir2;
                }
            }
            if (needsFocusFailureLayout) {
                consumePendingUpdateOperations();
                if (findContainingItemView(focused) == null) {
                    return null;
                }
                startInterceptRequestLayout();
                this.mLayout.onFocusSearchFailed(focused, direction, this.mRecycler, this.mState);
                stopInterceptRequestLayout(false);
            }
            result = ff.findNextFocus(this, focused, direction);
        }
        if (result == null || result.hasFocusable()) {
            if (isPreferredNextFocus(focused, result, direction)) {
                return result;
            }
            return super.focusSearch(focused, direction);
        } else if (getFocusedChild() == null) {
            return super.focusSearch(focused, direction);
        } else {
            requestChildOnScreen(result, (View) null);
            return focused;
        }
    }

    private boolean isPreferredNextFocus(View focused, View next, int direction) {
        if (next == null || next == this || findContainingItemView(next) == null) {
            return false;
        }
        if (focused == null || findContainingItemView(focused) == null) {
            return true;
        }
        this.mTempRect.set(0, 0, focused.getWidth(), focused.getHeight());
        this.mTempRect2.set(0, 0, next.getWidth(), next.getHeight());
        offsetDescendantRectToMyCoords(focused, this.mTempRect);
        offsetDescendantRectToMyCoords(next, this.mTempRect2);
        int rtl = this.mLayout.getLayoutDirection() == 1 ? -1 : 1;
        int rightness = 0;
        if ((this.mTempRect.left < this.mTempRect2.left || this.mTempRect.right <= this.mTempRect2.left) && this.mTempRect.right < this.mTempRect2.right) {
            rightness = 1;
        } else if ((this.mTempRect.right > this.mTempRect2.right || this.mTempRect.left >= this.mTempRect2.right) && this.mTempRect.left > this.mTempRect2.left) {
            rightness = -1;
        }
        int downness = 0;
        if ((this.mTempRect.top < this.mTempRect2.top || this.mTempRect.bottom <= this.mTempRect2.top) && this.mTempRect.bottom < this.mTempRect2.bottom) {
            downness = 1;
        } else if ((this.mTempRect.bottom > this.mTempRect2.bottom || this.mTempRect.top >= this.mTempRect2.bottom) && this.mTempRect.top > this.mTempRect2.top) {
            downness = -1;
        }
        switch (direction) {
            case 1:
                if (downness < 0 || (downness == 0 && rightness * rtl <= 0)) {
                    return true;
                }
                return false;
            case 2:
                if (downness > 0 || (downness == 0 && rightness * rtl >= 0)) {
                    return true;
                }
                return false;
            case 17:
                if (rightness < 0) {
                    return true;
                }
                return false;
            case 33:
                if (downness < 0) {
                    return true;
                }
                return false;
            case 66:
                if (rightness > 0) {
                    return true;
                }
                return false;
            case 130:
                if (downness > 0) {
                    return true;
                }
                return false;
            default:
                throw new IllegalArgumentException("Invalid direction: " + direction + exceptionLabel());
        }
    }

    public void requestChildFocus(View child, View focused) {
        if (!this.mLayout.onRequestChildFocus(this, this.mState, child, focused) && focused != null) {
            requestChildOnScreen(child, focused);
        }
        super.requestChildFocus(child, focused);
    }

    private void requestChildOnScreen(View child, View focused) {
        View rectView = focused != null ? focused : child;
        this.mTempRect.set(0, 0, rectView.getWidth(), rectView.getHeight());
        ViewGroup.LayoutParams focusedLayoutParams = rectView.getLayoutParams();
        if (focusedLayoutParams instanceof LayoutParams) {
            LayoutParams lp = (LayoutParams) focusedLayoutParams;
            if (!lp.mInsetsDirty) {
                Rect insets = lp.mDecorInsets;
                this.mTempRect.left -= insets.left;
                this.mTempRect.right += insets.right;
                this.mTempRect.top -= insets.top;
                this.mTempRect.bottom += insets.bottom;
            }
        }
        if (focused != null) {
            offsetDescendantRectToMyCoords(focused, this.mTempRect);
            offsetRectIntoDescendantCoords(child, this.mTempRect);
        }
        this.mLayout.requestChildRectangleOnScreen(this, child, this.mTempRect, !this.mFirstLayoutComplete, focused == null);
    }

    public boolean requestChildRectangleOnScreen(View child, Rect rect, boolean immediate) {
        return this.mLayout.requestChildRectangleOnScreen(this, child, rect, immediate);
    }

    public void addFocusables(ArrayList<View> views, int direction, int focusableMode) {
        LayoutManager layoutManager = this.mLayout;
        if (layoutManager == null || !layoutManager.onAddFocusables(this, views, direction, focusableMode)) {
            super.addFocusables(views, direction, focusableMode);
        }
    }

    /* access modifiers changed from: protected */
    public boolean onRequestFocusInDescendants(int direction, Rect previouslyFocusedRect) {
        if (isComputingLayout()) {
            return false;
        }
        return super.onRequestFocusInDescendants(direction, previouslyFocusedRect);
    }

    /* access modifiers changed from: protected */
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.mLayoutOrScrollCounter = 0;
        boolean z = true;
        this.mIsAttached = true;
        if (!this.mFirstLayoutComplete || isLayoutRequested()) {
            z = false;
        }
        this.mFirstLayoutComplete = z;
        LayoutManager layoutManager = this.mLayout;
        if (layoutManager != null) {
            layoutManager.dispatchAttachedToWindow(this);
        }
        this.mPostedAnimatorRunner = false;
        if (ALLOW_THREAD_GAP_WORK) {
            GapWorker gapWorker = GapWorker.sGapWorker.get();
            this.mGapWorker = gapWorker;
            if (gapWorker == null) {
                this.mGapWorker = new GapWorker();
                Display display = ViewCompat.getDisplay(this);
                float refreshRate = 60.0f;
                if (!isInEditMode() && display != null) {
                    float displayRefreshRate = display.getRefreshRate();
                    if (displayRefreshRate >= 30.0f) {
                        refreshRate = displayRefreshRate;
                    }
                }
                this.mGapWorker.mFrameIntervalNs = (long) (1.0E9f / refreshRate);
                GapWorker.sGapWorker.set(this.mGapWorker);
            }
            this.mGapWorker.add(this);
        }
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        GapWorker gapWorker;
        super.onDetachedFromWindow();
        ItemAnimator itemAnimator = this.mItemAnimator;
        if (itemAnimator != null) {
            itemAnimator.endAnimations();
        }
        stopScroll();
        this.mIsAttached = false;
        LayoutManager layoutManager = this.mLayout;
        if (layoutManager != null) {
            layoutManager.dispatchDetachedFromWindow(this, this.mRecycler);
        }
        this.mPendingAccessibilityImportanceChange.clear();
        removeCallbacks(this.mItemAnimatorRunner);
        this.mViewInfoStore.onDetach();
        if (ALLOW_THREAD_GAP_WORK && (gapWorker = this.mGapWorker) != null) {
            gapWorker.remove(this);
            this.mGapWorker = null;
        }
    }

    public boolean isAttachedToWindow() {
        return this.mIsAttached;
    }

    /* access modifiers changed from: package-private */
    public void assertInLayoutOrScroll(String message) {
        if (isComputingLayout()) {
            return;
        }
        if (message == null) {
            throw new IllegalStateException("Cannot call this method unless RecyclerView is computing a layout or scrolling" + exceptionLabel());
        }
        throw new IllegalStateException(message + exceptionLabel());
    }

    /* access modifiers changed from: package-private */
    public void assertNotInLayoutOrScroll(String message) {
        if (isComputingLayout()) {
            if (message == null) {
                throw new IllegalStateException("Cannot call this method while RecyclerView is computing a layout or scrolling" + exceptionLabel());
            }
            throw new IllegalStateException(message);
        } else if (this.mDispatchScrollCounter > 0) {
            Log.w(TAG, "Cannot call this method in a scroll callback. Scroll callbacks mightbe run during a measure & layout pass where you cannot change theRecyclerView data. Any method call that might change the structureof the RecyclerView or the adapter contents should be postponed tothe next frame.", new IllegalStateException("" + exceptionLabel()));
        }
    }

    public void addOnItemTouchListener(OnItemTouchListener listener) {
        this.mOnItemTouchListeners.add(listener);
    }

    public void removeOnItemTouchListener(OnItemTouchListener listener) {
        this.mOnItemTouchListeners.remove(listener);
        if (this.mInterceptingOnItemTouchListener == listener) {
            this.mInterceptingOnItemTouchListener = null;
        }
    }

    private boolean dispatchToOnItemTouchListeners(MotionEvent e) {
        OnItemTouchListener onItemTouchListener = this.mInterceptingOnItemTouchListener;
        if (onItemTouchListener != null) {
            onItemTouchListener.onTouchEvent(this, e);
            int action = e.getAction();
            if (action == 3 || action == 1) {
                this.mInterceptingOnItemTouchListener = null;
            }
            return true;
        } else if (e.getAction() == 0) {
            return false;
        } else {
            return findInterceptingOnItemTouchListener(e);
        }
    }

    private boolean findInterceptingOnItemTouchListener(MotionEvent e) {
        int action = e.getAction();
        int listenerCount = this.mOnItemTouchListeners.size();
        int i = 0;
        while (i < listenerCount) {
            OnItemTouchListener listener = this.mOnItemTouchListeners.get(i);
            if (!listener.onInterceptTouchEvent(this, e) || action == 3) {
                i++;
            } else {
                this.mInterceptingOnItemTouchListener = listener;
                return true;
            }
        }
        return false;
    }

    public boolean onInterceptTouchEvent(MotionEvent e) {
        if (this.mLayoutSuppressed) {
            return false;
        }
        this.mInterceptingOnItemTouchListener = null;
        if (findInterceptingOnItemTouchListener(e)) {
            cancelScroll();
            return true;
        }
        LayoutManager layoutManager = this.mLayout;
        if (layoutManager == null) {
            return false;
        }
        boolean canScrollHorizontally = layoutManager.canScrollHorizontally();
        boolean canScrollVertically = this.mLayout.canScrollVertically();
        if (this.mVelocityTracker == null) {
            this.mVelocityTracker = VelocityTracker.obtain();
        }
        this.mVelocityTracker.addMovement(e);
        int action = e.getActionMasked();
        int actionIndex = e.getActionIndex();
        switch (action) {
            case 0:
                if (this.mIgnoreMotionEventTillDown) {
                    this.mIgnoreMotionEventTillDown = false;
                }
                this.mScrollPointerId = e.getPointerId(0);
                int x = (int) (e.getX() + 0.5f);
                this.mLastTouchX = x;
                this.mInitialTouchX = x;
                int y = (int) (e.getY() + 0.5f);
                this.mLastTouchY = y;
                this.mInitialTouchY = y;
                if (this.mScrollState == 2) {
                    getParent().requestDisallowInterceptTouchEvent(true);
                    setScrollState(1);
                    stopNestedScroll(1);
                }
                int[] iArr = this.mNestedOffsets;
                iArr[1] = 0;
                iArr[0] = 0;
                int nestedScrollAxis = 0;
                if (canScrollHorizontally) {
                    nestedScrollAxis = 0 | 1;
                }
                if (canScrollVertically) {
                    nestedScrollAxis |= 2;
                }
                startNestedScroll(nestedScrollAxis, 0);
                break;
            case 1:
                this.mVelocityTracker.clear();
                stopNestedScroll(0);
                break;
            case 2:
                int index = e.findPointerIndex(this.mScrollPointerId);
                if (index >= 0) {
                    int x2 = (int) (e.getX(index) + 0.5f);
                    int y2 = (int) (e.getY(index) + 0.5f);
                    if (this.mScrollState != 1) {
                        int dx = x2 - this.mInitialTouchX;
                        int dy = y2 - this.mInitialTouchY;
                        boolean startScroll = false;
                        if (canScrollHorizontally && Math.abs(dx) > this.mTouchSlop) {
                            this.mLastTouchX = x2;
                            startScroll = true;
                        }
                        if (canScrollVertically && Math.abs(dy) > this.mTouchSlop) {
                            this.mLastTouchY = y2;
                            startScroll = true;
                        }
                        if (startScroll) {
                            setScrollState(1);
                            break;
                        }
                    }
                } else {
                    Log.e(TAG, "Error processing scroll; pointer index for id " + this.mScrollPointerId + " not found. Did any MotionEvents get skipped?");
                    return false;
                }
                break;
            case 3:
                cancelScroll();
                break;
            case 5:
                this.mScrollPointerId = e.getPointerId(actionIndex);
                int x3 = (int) (e.getX(actionIndex) + 0.5f);
                this.mLastTouchX = x3;
                this.mInitialTouchX = x3;
                int y3 = (int) (e.getY(actionIndex) + 0.5f);
                this.mLastTouchY = y3;
                this.mInitialTouchY = y3;
                break;
            case 6:
                onPointerUp(e);
                break;
        }
        if (this.mScrollState == 1) {
            return true;
        }
        return false;
    }

    public void requestDisallowInterceptTouchEvent(boolean disallowIntercept) {
        int listenerCount = this.mOnItemTouchListeners.size();
        for (int i = 0; i < listenerCount; i++) {
            this.mOnItemTouchListeners.get(i).onRequestDisallowInterceptTouchEvent(disallowIntercept);
        }
        super.requestDisallowInterceptTouchEvent(disallowIntercept);
    }

    public boolean onTouchEvent(MotionEvent e) {
        int dy;
        int dx;
        int dy2;
        char c;
        int dx2;
        MotionEvent motionEvent = e;
        int i = 0;
        if (this.mLayoutSuppressed || this.mIgnoreMotionEventTillDown) {
            return false;
        }
        int i2 = 1;
        if (dispatchToOnItemTouchListeners(e)) {
            cancelScroll();
            return true;
        }
        LayoutManager layoutManager = this.mLayout;
        if (layoutManager == null) {
            return false;
        }
        boolean canScrollHorizontally = layoutManager.canScrollHorizontally();
        boolean canScrollVertically = this.mLayout.canScrollVertically();
        if (this.mVelocityTracker == null) {
            this.mVelocityTracker = VelocityTracker.obtain();
        }
        boolean eventAddedToVelocityTracker = false;
        int action = e.getActionMasked();
        int actionIndex = e.getActionIndex();
        if (action == 0) {
            int[] iArr = this.mNestedOffsets;
            iArr[1] = 0;
            iArr[0] = 0;
        }
        MotionEvent vtev = MotionEvent.obtain(e);
        int[] iArr2 = this.mNestedOffsets;
        vtev.offsetLocation((float) iArr2[0], (float) iArr2[1]);
        switch (action) {
            case 0:
                this.mScrollPointerId = motionEvent.getPointerId(0);
                int x = (int) (e.getX() + 0.5f);
                this.mLastTouchX = x;
                this.mInitialTouchX = x;
                int y = (int) (e.getY() + 0.5f);
                this.mLastTouchY = y;
                this.mInitialTouchY = y;
                int nestedScrollAxis = 0;
                if (canScrollHorizontally) {
                    nestedScrollAxis = 0 | 1;
                }
                if (canScrollVertically) {
                    nestedScrollAxis |= 2;
                }
                startNestedScroll(nestedScrollAxis, 0);
                break;
            case 1:
                this.mVelocityTracker.addMovement(vtev);
                eventAddedToVelocityTracker = true;
                this.mVelocityTracker.computeCurrentVelocity(1000, (float) this.mMaxFlingVelocity);
                float xvel = canScrollHorizontally ? -this.mVelocityTracker.getXVelocity(this.mScrollPointerId) : 0.0f;
                float yvel = canScrollVertically ? -this.mVelocityTracker.getYVelocity(this.mScrollPointerId) : 0.0f;
                if ((xvel == 0.0f && yvel == 0.0f) || !fling((int) xvel, (int) yvel)) {
                    setScrollState(0);
                }
                resetScroll();
                break;
            case 2:
                int index = motionEvent.findPointerIndex(this.mScrollPointerId);
                if (index >= 0) {
                    int x2 = (int) (motionEvent.getX(index) + 0.5f);
                    int y2 = (int) (motionEvent.getY(index) + 0.5f);
                    int dx3 = this.mLastTouchX - x2;
                    int dy3 = this.mLastTouchY - y2;
                    if (this.mScrollState != 1) {
                        boolean startScroll = false;
                        if (canScrollHorizontally) {
                            if (dx3 > 0) {
                                dx3 = Math.max(0, dx3 - this.mTouchSlop);
                            } else {
                                dx3 = Math.min(0, this.mTouchSlop + dx3);
                            }
                            if (dx3 != 0) {
                                startScroll = true;
                            }
                        }
                        if (canScrollVertically) {
                            if (dy3 > 0) {
                                dy3 = Math.max(0, dy3 - this.mTouchSlop);
                            } else {
                                dy3 = Math.min(0, this.mTouchSlop + dy3);
                            }
                            if (dy3 != 0) {
                                startScroll = true;
                            }
                        }
                        if (startScroll) {
                            i2 = 1;
                            setScrollState(1);
                        } else {
                            i2 = 1;
                        }
                        dx = dx3;
                        dy = dy3;
                    } else {
                        dx = dx3;
                        dy = dy3;
                    }
                    if (this.mScrollState != i2) {
                        int i3 = x2;
                        int i4 = index;
                        break;
                    } else {
                        int[] iArr3 = this.mReusableIntPair;
                        iArr3[0] = 0;
                        iArr3[i2] = 0;
                        int y3 = y2;
                        int x3 = x2;
                        int i5 = index;
                        if (dispatchNestedPreScroll(canScrollHorizontally ? dx : 0, canScrollVertically ? dy : 0, iArr3, this.mScrollOffset, 0)) {
                            int[] iArr4 = this.mReusableIntPair;
                            int dx4 = dx - iArr4[0];
                            c = 1;
                            int dy4 = dy - iArr4[1];
                            int[] iArr5 = this.mNestedOffsets;
                            int i6 = iArr5[0];
                            int[] iArr6 = this.mScrollOffset;
                            iArr5[0] = i6 + iArr6[0];
                            iArr5[1] = iArr5[1] + iArr6[1];
                            getParent().requestDisallowInterceptTouchEvent(true);
                            dx2 = dx4;
                            dy2 = dy4;
                        } else {
                            c = 1;
                            dx2 = dx;
                            dy2 = dy;
                        }
                        int[] iArr7 = this.mScrollOffset;
                        this.mLastTouchX = x3 - iArr7[0];
                        this.mLastTouchY = y3 - iArr7[c];
                        int i7 = canScrollHorizontally ? dx2 : 0;
                        if (canScrollVertically) {
                            i = dy2;
                        }
                        if (scrollByInternal(i7, i, motionEvent)) {
                            getParent().requestDisallowInterceptTouchEvent(true);
                        }
                        GapWorker gapWorker = this.mGapWorker;
                        if (!(gapWorker == null || (dx2 == 0 && dy2 == 0))) {
                            gapWorker.postFromTraversal(this, dx2, dy2);
                            break;
                        }
                    }
                } else {
                    Log.e(TAG, "Error processing scroll; pointer index for id " + this.mScrollPointerId + " not found. Did any MotionEvents get skipped?");
                    return false;
                }
            case 3:
                cancelScroll();
                break;
            case 5:
                this.mScrollPointerId = motionEvent.getPointerId(actionIndex);
                int x4 = (int) (motionEvent.getX(actionIndex) + 0.5f);
                this.mLastTouchX = x4;
                this.mInitialTouchX = x4;
                int y4 = (int) (motionEvent.getY(actionIndex) + 0.5f);
                this.mLastTouchY = y4;
                this.mInitialTouchY = y4;
                break;
            case 6:
                onPointerUp(e);
                break;
        }
        if (!eventAddedToVelocityTracker) {
            this.mVelocityTracker.addMovement(vtev);
        }
        vtev.recycle();
        return true;
    }

    private void resetScroll() {
        VelocityTracker velocityTracker = this.mVelocityTracker;
        if (velocityTracker != null) {
            velocityTracker.clear();
        }
        stopNestedScroll(0);
        releaseGlows();
    }

    private void cancelScroll() {
        resetScroll();
        setScrollState(0);
    }

    private void onPointerUp(MotionEvent e) {
        int actionIndex = e.getActionIndex();
        if (e.getPointerId(actionIndex) == this.mScrollPointerId) {
            int newIndex = actionIndex == 0 ? 1 : 0;
            this.mScrollPointerId = e.getPointerId(newIndex);
            int x = (int) (e.getX(newIndex) + 0.5f);
            this.mLastTouchX = x;
            this.mInitialTouchX = x;
            int y = (int) (e.getY(newIndex) + 0.5f);
            this.mLastTouchY = y;
            this.mInitialTouchY = y;
        }
    }

    public boolean onGenericMotionEvent(MotionEvent event) {
        float hScroll;
        float vScroll;
        if (this.mLayout != null && !this.mLayoutSuppressed && event.getAction() == 8) {
            if ((event.getSource() & 2) != 0) {
                if (this.mLayout.canScrollVertically()) {
                    vScroll = -event.getAxisValue(9);
                } else {
                    vScroll = 0.0f;
                }
                if (this.mLayout.canScrollHorizontally()) {
                    hScroll = event.getAxisValue(10);
                } else {
                    hScroll = 0.0f;
                }
            } else if ((event.getSource() & 4194304) != 0) {
                float axisScroll = event.getAxisValue(26);
                if (this.mLayout.canScrollVertically()) {
                    vScroll = -axisScroll;
                    hScroll = 0.0f;
                } else if (this.mLayout.canScrollHorizontally()) {
                    float f = axisScroll;
                    vScroll = 0.0f;
                    hScroll = f;
                } else {
                    vScroll = 0.0f;
                    hScroll = 0.0f;
                }
            } else {
                vScroll = 0.0f;
                hScroll = 0.0f;
            }
            if (!(vScroll == 0.0f && hScroll == 0.0f)) {
                scrollByInternal((int) (this.mScaledHorizontalScrollFactor * hScroll), (int) (this.mScaledVerticalScrollFactor * vScroll), event);
            }
        }
        return false;
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int widthSpec, int heightSpec) {
        LayoutManager layoutManager = this.mLayout;
        if (layoutManager == null) {
            defaultOnMeasure(widthSpec, heightSpec);
            return;
        }
        boolean measureSpecModeIsExactly = false;
        if (layoutManager.isAutoMeasureEnabled()) {
            int widthMode = View.MeasureSpec.getMode(widthSpec);
            int heightMode = View.MeasureSpec.getMode(heightSpec);
            this.mLayout.onMeasure(this.mRecycler, this.mState, widthSpec, heightSpec);
            if (widthMode == 1073741824 && heightMode == 1073741824) {
                measureSpecModeIsExactly = true;
            }
            if (!measureSpecModeIsExactly && this.mAdapter != null) {
                if (this.mState.mLayoutStep == 1) {
                    dispatchLayoutStep1();
                }
                this.mLayout.setMeasureSpecs(widthSpec, heightSpec);
                this.mState.mIsMeasuring = true;
                dispatchLayoutStep2();
                this.mLayout.setMeasuredDimensionFromChildren(widthSpec, heightSpec);
                if (this.mLayout.shouldMeasureTwice()) {
                    this.mLayout.setMeasureSpecs(View.MeasureSpec.makeMeasureSpec(getMeasuredWidth(), 1073741824), View.MeasureSpec.makeMeasureSpec(getMeasuredHeight(), 1073741824));
                    this.mState.mIsMeasuring = true;
                    dispatchLayoutStep2();
                    this.mLayout.setMeasuredDimensionFromChildren(widthSpec, heightSpec);
                }
            }
        } else if (this.mHasFixedSize != 0) {
            this.mLayout.onMeasure(this.mRecycler, this.mState, widthSpec, heightSpec);
        } else {
            if (this.mAdapterUpdateDuringMeasure) {
                startInterceptRequestLayout();
                onEnterLayoutOrScroll();
                processAdapterUpdatesAndSetAnimationFlags();
                onExitLayoutOrScroll();
                if (this.mState.mRunPredictiveAnimations) {
                    this.mState.mInPreLayout = true;
                } else {
                    this.mAdapterHelper.consumeUpdatesInOnePass();
                    this.mState.mInPreLayout = false;
                }
                this.mAdapterUpdateDuringMeasure = false;
                stopInterceptRequestLayout(false);
            } else if (this.mState.mRunPredictiveAnimations) {
                setMeasuredDimension(getMeasuredWidth(), getMeasuredHeight());
                return;
            }
            Adapter adapter = this.mAdapter;
            if (adapter != null) {
                this.mState.mItemCount = adapter.getItemCount();
            } else {
                this.mState.mItemCount = 0;
            }
            startInterceptRequestLayout();
            this.mLayout.onMeasure(this.mRecycler, this.mState, widthSpec, heightSpec);
            stopInterceptRequestLayout(false);
            this.mState.mInPreLayout = false;
        }
    }

    /* access modifiers changed from: package-private */
    public void defaultOnMeasure(int widthSpec, int heightSpec) {
        setMeasuredDimension(LayoutManager.chooseSize(widthSpec, getPaddingLeft() + getPaddingRight(), ViewCompat.getMinimumWidth(this)), LayoutManager.chooseSize(heightSpec, getPaddingTop() + getPaddingBottom(), ViewCompat.getMinimumHeight(this)));
    }

    /* access modifiers changed from: protected */
    public void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (w != oldw || h != oldh) {
            invalidateGlows();
        }
    }

    public void setItemAnimator(ItemAnimator animator) {
        ItemAnimator itemAnimator = this.mItemAnimator;
        if (itemAnimator != null) {
            itemAnimator.endAnimations();
            this.mItemAnimator.setListener((ItemAnimator.ItemAnimatorListener) null);
        }
        this.mItemAnimator = animator;
        if (animator != null) {
            animator.setListener(this.mItemAnimatorListener);
        }
    }

    /* access modifiers changed from: package-private */
    public void onEnterLayoutOrScroll() {
        this.mLayoutOrScrollCounter++;
    }

    /* access modifiers changed from: package-private */
    public void onExitLayoutOrScroll() {
        onExitLayoutOrScroll(true);
    }

    /* access modifiers changed from: package-private */
    public void onExitLayoutOrScroll(boolean enableChangeEvents) {
        int i = this.mLayoutOrScrollCounter - 1;
        this.mLayoutOrScrollCounter = i;
        if (i < 1) {
            this.mLayoutOrScrollCounter = 0;
            if (enableChangeEvents) {
                dispatchContentChangedIfNecessary();
                dispatchPendingImportantForAccessibilityChanges();
            }
        }
    }

    /* access modifiers changed from: package-private */
    public boolean isAccessibilityEnabled() {
        AccessibilityManager accessibilityManager = this.mAccessibilityManager;
        return accessibilityManager != null && accessibilityManager.isEnabled();
    }

    private void dispatchContentChangedIfNecessary() {
        int flags = this.mEatenAccessibilityChangeFlags;
        this.mEatenAccessibilityChangeFlags = 0;
        if (flags != 0 && isAccessibilityEnabled()) {
            AccessibilityEvent event = AccessibilityEvent.obtain();
            event.setEventType(2048);
            AccessibilityEventCompat.setContentChangeTypes(event, flags);
            sendAccessibilityEventUnchecked(event);
        }
    }

    public boolean isComputingLayout() {
        return this.mLayoutOrScrollCounter > 0;
    }

    /* access modifiers changed from: package-private */
    public boolean shouldDeferAccessibilityEvent(AccessibilityEvent event) {
        if (!isComputingLayout()) {
            return false;
        }
        int type = 0;
        if (event != null) {
            type = AccessibilityEventCompat.getContentChangeTypes(event);
        }
        if (type == 0) {
            type = 0;
        }
        this.mEatenAccessibilityChangeFlags |= type;
        return true;
    }

    public void sendAccessibilityEventUnchecked(AccessibilityEvent event) {
        if (!shouldDeferAccessibilityEvent(event)) {
            super.sendAccessibilityEventUnchecked(event);
        }
    }

    public boolean dispatchPopulateAccessibilityEvent(AccessibilityEvent event) {
        onPopulateAccessibilityEvent(event);
        return true;
    }

    public ItemAnimator getItemAnimator() {
        return this.mItemAnimator;
    }

    /* access modifiers changed from: package-private */
    public void postAnimationRunner() {
        if (!this.mPostedAnimatorRunner && this.mIsAttached) {
            ViewCompat.postOnAnimation(this, this.mItemAnimatorRunner);
            this.mPostedAnimatorRunner = true;
        }
    }

    private boolean predictiveItemAnimationsEnabled() {
        return this.mItemAnimator != null && this.mLayout.supportsPredictiveItemAnimations();
    }

    private void processAdapterUpdatesAndSetAnimationFlags() {
        if (this.mDataSetHasChangedAfterLayout) {
            this.mAdapterHelper.reset();
            if (this.mDispatchItemsChangedEvent) {
                this.mLayout.onItemsChanged(this);
            }
        }
        if (predictiveItemAnimationsEnabled()) {
            this.mAdapterHelper.preProcess();
        } else {
            this.mAdapterHelper.consumeUpdatesInOnePass();
        }
        boolean z = false;
        boolean animationTypeSupported = this.mItemsAddedOrRemoved || this.mItemsChanged;
        this.mState.mRunSimpleAnimations = this.mFirstLayoutComplete && this.mItemAnimator != null && (this.mDataSetHasChangedAfterLayout || animationTypeSupported || this.mLayout.mRequestedSimpleAnimations) && (!this.mDataSetHasChangedAfterLayout || this.mAdapter.hasStableIds());
        State state = this.mState;
        if (state.mRunSimpleAnimations && animationTypeSupported && !this.mDataSetHasChangedAfterLayout && predictiveItemAnimationsEnabled()) {
            z = true;
        }
        state.mRunPredictiveAnimations = z;
    }

    /* access modifiers changed from: package-private */
    public void dispatchLayout() {
        if (this.mAdapter == null) {
            Log.e(TAG, "No adapter attached; skipping layout");
        } else if (this.mLayout == null) {
            Log.e(TAG, "No layout manager attached; skipping layout");
        } else {
            this.mState.mIsMeasuring = false;
            if (this.mState.mLayoutStep == 1) {
                dispatchLayoutStep1();
                this.mLayout.setExactMeasureSpecsFrom(this);
                dispatchLayoutStep2();
            } else if (!this.mAdapterHelper.hasUpdates() && this.mLayout.getWidth() == getWidth() && this.mLayout.getHeight() == getHeight()) {
                this.mLayout.setExactMeasureSpecsFrom(this);
            } else {
                this.mLayout.setExactMeasureSpecsFrom(this);
                dispatchLayoutStep2();
            }
            dispatchLayoutStep3();
        }
    }

    private void saveFocusInfo() {
        int i;
        View child = null;
        if (this.mPreserveFocusAfterLayout && hasFocus() && this.mAdapter != null) {
            child = getFocusedChild();
        }
        ViewHolder focusedVh = child == null ? null : findContainingViewHolder(child);
        if (focusedVh == null) {
            resetFocusInfo();
            return;
        }
        this.mState.mFocusedItemId = this.mAdapter.hasStableIds() ? focusedVh.getItemId() : -1;
        State state = this.mState;
        if (this.mDataSetHasChangedAfterLayout) {
            i = -1;
        } else if (focusedVh.isRemoved()) {
            i = focusedVh.mOldPosition;
        } else {
            i = focusedVh.getAdapterPosition();
        }
        state.mFocusedItemPosition = i;
        this.mState.mFocusedSubChildId = getDeepestFocusedViewWithId(focusedVh.itemView);
    }

    private void resetFocusInfo() {
        this.mState.mFocusedItemId = -1;
        this.mState.mFocusedItemPosition = -1;
        this.mState.mFocusedSubChildId = -1;
    }

    private View findNextViewToFocus() {
        ViewHolder nextFocus;
        int startFocusSearchIndex = this.mState.mFocusedItemPosition != -1 ? this.mState.mFocusedItemPosition : 0;
        int itemCount = this.mState.getItemCount();
        int i = startFocusSearchIndex;
        while (i < itemCount) {
            ViewHolder nextFocus2 = findViewHolderForAdapterPosition(i);
            if (nextFocus2 == null) {
                break;
            } else if (nextFocus2.itemView.hasFocusable()) {
                return nextFocus2.itemView;
            } else {
                i++;
            }
        }
        int i2 = Math.min(itemCount, startFocusSearchIndex) - 1;
        while (i2 >= 0 && (nextFocus = findViewHolderForAdapterPosition(i2)) != null) {
            if (nextFocus.itemView.hasFocusable()) {
                return nextFocus.itemView;
            }
            i2--;
        }
        return null;
    }

    private void recoverFocusFromState() {
        View child;
        if (this.mPreserveFocusAfterLayout && this.mAdapter != null && hasFocus() && getDescendantFocusability() != 393216) {
            if (getDescendantFocusability() != 131072 || !isFocused()) {
                if (!isFocused()) {
                    View focusedChild = getFocusedChild();
                    if (!IGNORE_DETACHED_FOCUSED_CHILD || (focusedChild.getParent() != null && focusedChild.hasFocus())) {
                        if (!this.mChildHelper.isHidden(focusedChild)) {
                            return;
                        }
                    } else if (this.mChildHelper.getChildCount() == 0) {
                        requestFocus();
                        return;
                    }
                }
                ViewHolder focusTarget = null;
                if (this.mState.mFocusedItemId != -1 && this.mAdapter.hasStableIds()) {
                    focusTarget = findViewHolderForItemId(this.mState.mFocusedItemId);
                }
                View viewToFocus = null;
                if (focusTarget != null && !this.mChildHelper.isHidden(focusTarget.itemView) && focusTarget.itemView.hasFocusable()) {
                    viewToFocus = focusTarget.itemView;
                } else if (this.mChildHelper.getChildCount() > 0) {
                    viewToFocus = findNextViewToFocus();
                }
                if (viewToFocus != null) {
                    if (!(((long) this.mState.mFocusedSubChildId) == -1 || (child = viewToFocus.findViewById(this.mState.mFocusedSubChildId)) == null || !child.isFocusable())) {
                        viewToFocus = child;
                    }
                    viewToFocus.requestFocus();
                }
            }
        }
    }

    private int getDeepestFocusedViewWithId(View view) {
        int lastKnownId = view.getId();
        while (!view.isFocused() && (view instanceof ViewGroup) && view.hasFocus()) {
            view = ((ViewGroup) view).getFocusedChild();
            if (view.getId() != -1) {
                lastKnownId = view.getId();
            }
        }
        return lastKnownId;
    }

    /* access modifiers changed from: package-private */
    public final void fillRemainingScrollValues(State state) {
        if (getScrollState() == 2) {
            OverScroller scroller = this.mViewFlinger.mOverScroller;
            state.mRemainingScrollHorizontal = scroller.getFinalX() - scroller.getCurrX();
            state.mRemainingScrollVertical = scroller.getFinalY() - scroller.getCurrY();
            return;
        }
        state.mRemainingScrollHorizontal = 0;
        state.mRemainingScrollVertical = 0;
    }

    private void dispatchLayoutStep1() {
        boolean z = true;
        this.mState.assertLayoutStep(1);
        fillRemainingScrollValues(this.mState);
        this.mState.mIsMeasuring = false;
        startInterceptRequestLayout();
        this.mViewInfoStore.clear();
        onEnterLayoutOrScroll();
        processAdapterUpdatesAndSetAnimationFlags();
        saveFocusInfo();
        State state = this.mState;
        if (!state.mRunSimpleAnimations || !this.mItemsChanged) {
            z = false;
        }
        state.mTrackOldChangeHolders = z;
        this.mItemsChanged = false;
        this.mItemsAddedOrRemoved = false;
        State state2 = this.mState;
        state2.mInPreLayout = state2.mRunPredictiveAnimations;
        this.mState.mItemCount = this.mAdapter.getItemCount();
        findMinMaxChildLayoutPositions(this.mMinMaxLayoutPositions);
        if (this.mState.mRunSimpleAnimations) {
            int count = this.mChildHelper.getChildCount();
            for (int i = 0; i < count; i++) {
                ViewHolder holder = getChildViewHolderInt(this.mChildHelper.getChildAt(i));
                if (!holder.shouldIgnore() && (!holder.isInvalid() || this.mAdapter.hasStableIds())) {
                    this.mViewInfoStore.addToPreLayout(holder, this.mItemAnimator.recordPreLayoutInformation(this.mState, holder, ItemAnimator.buildAdapterChangeFlagsForAnimations(holder), holder.getUnmodifiedPayloads()));
                    if (this.mState.mTrackOldChangeHolders && holder.isUpdated() && !holder.isRemoved() && !holder.shouldIgnore() && !holder.isInvalid()) {
                        this.mViewInfoStore.addToOldChangeHolders(getChangedHolderKey(holder), holder);
                    }
                }
            }
        }
        if (this.mState.mRunPredictiveAnimations) {
            saveOldPositions();
            boolean didStructureChange = this.mState.mStructureChanged;
            this.mState.mStructureChanged = false;
            this.mLayout.onLayoutChildren(this.mRecycler, this.mState);
            this.mState.mStructureChanged = didStructureChange;
            for (int i2 = 0; i2 < this.mChildHelper.getChildCount(); i2++) {
                ViewHolder viewHolder = getChildViewHolderInt(this.mChildHelper.getChildAt(i2));
                if (!viewHolder.shouldIgnore() && !this.mViewInfoStore.isInPreLayout(viewHolder)) {
                    int flags = ItemAnimator.buildAdapterChangeFlagsForAnimations(viewHolder);
                    boolean wasHidden = viewHolder.hasAnyOfTheFlags(8192);
                    if (!wasHidden) {
                        flags |= 4096;
                    }
                    ItemAnimator.ItemHolderInfo animationInfo = this.mItemAnimator.recordPreLayoutInformation(this.mState, viewHolder, flags, viewHolder.getUnmodifiedPayloads());
                    if (wasHidden) {
                        recordAnimationInfoIfBouncedHiddenView(viewHolder, animationInfo);
                    } else {
                        this.mViewInfoStore.addToAppearedInPreLayoutHolders(viewHolder, animationInfo);
                    }
                }
            }
            clearOldPositions();
        } else {
            clearOldPositions();
        }
        onExitLayoutOrScroll();
        stopInterceptRequestLayout(false);
        this.mState.mLayoutStep = 2;
    }

    private void dispatchLayoutStep2() {
        startInterceptRequestLayout();
        onEnterLayoutOrScroll();
        this.mState.assertLayoutStep(6);
        this.mAdapterHelper.consumeUpdatesInOnePass();
        this.mState.mItemCount = this.mAdapter.getItemCount();
        this.mState.mDeletedInvisibleItemCountSincePreviousLayout = 0;
        this.mState.mInPreLayout = false;
        this.mLayout.onLayoutChildren(this.mRecycler, this.mState);
        this.mState.mStructureChanged = false;
        this.mPendingSavedState = null;
        State state = this.mState;
        state.mRunSimpleAnimations = state.mRunSimpleAnimations && this.mItemAnimator != null;
        this.mState.mLayoutStep = 4;
        onExitLayoutOrScroll();
        stopInterceptRequestLayout(false);
    }

    private void dispatchLayoutStep3() {
        this.mState.assertLayoutStep(4);
        startInterceptRequestLayout();
        onEnterLayoutOrScroll();
        this.mState.mLayoutStep = 1;
        if (this.mState.mRunSimpleAnimations) {
            for (int i = this.mChildHelper.getChildCount() - 1; i >= 0; i--) {
                ViewHolder holder = getChildViewHolderInt(this.mChildHelper.getChildAt(i));
                if (!holder.shouldIgnore()) {
                    long key = getChangedHolderKey(holder);
                    ItemAnimator.ItemHolderInfo animationInfo = this.mItemAnimator.recordPostLayoutInformation(this.mState, holder);
                    ViewHolder oldChangeViewHolder = this.mViewInfoStore.getFromOldChangeHolders(key);
                    if (oldChangeViewHolder == null || oldChangeViewHolder.shouldIgnore()) {
                        this.mViewInfoStore.addToPostLayout(holder, animationInfo);
                    } else {
                        boolean oldDisappearing = this.mViewInfoStore.isDisappearing(oldChangeViewHolder);
                        boolean newDisappearing = this.mViewInfoStore.isDisappearing(holder);
                        if (!oldDisappearing || oldChangeViewHolder != holder) {
                            ItemAnimator.ItemHolderInfo preInfo = this.mViewInfoStore.popFromPreLayout(oldChangeViewHolder);
                            this.mViewInfoStore.addToPostLayout(holder, animationInfo);
                            ItemAnimator.ItemHolderInfo postInfo = this.mViewInfoStore.popFromPostLayout(holder);
                            if (preInfo == null) {
                                handleMissingPreInfoForChangeError(key, holder, oldChangeViewHolder);
                            } else {
                                animateChange(oldChangeViewHolder, holder, preInfo, postInfo, oldDisappearing, newDisappearing);
                            }
                        } else {
                            this.mViewInfoStore.addToPostLayout(holder, animationInfo);
                        }
                    }
                }
            }
            this.mViewInfoStore.process(this.mViewInfoProcessCallback);
        }
        this.mLayout.removeAndRecycleScrapInt(this.mRecycler);
        State state = this.mState;
        state.mPreviousLayoutItemCount = state.mItemCount;
        this.mDataSetHasChangedAfterLayout = false;
        this.mDispatchItemsChangedEvent = false;
        this.mState.mRunSimpleAnimations = false;
        this.mState.mRunPredictiveAnimations = false;
        this.mLayout.mRequestedSimpleAnimations = false;
        if (this.mRecycler.mChangedScrap != null) {
            this.mRecycler.mChangedScrap.clear();
        }
        if (this.mLayout.mPrefetchMaxObservedInInitialPrefetch) {
            this.mLayout.mPrefetchMaxCountObserved = 0;
            this.mLayout.mPrefetchMaxObservedInInitialPrefetch = false;
            this.mRecycler.updateViewCacheSize();
        }
        this.mLayout.onLayoutCompleted(this.mState);
        onExitLayoutOrScroll();
        stopInterceptRequestLayout(false);
        this.mViewInfoStore.clear();
        int[] iArr = this.mMinMaxLayoutPositions;
        if (didChildRangeChange(iArr[0], iArr[1])) {
            dispatchOnScrolled(0, 0);
        }
        recoverFocusFromState();
        resetFocusInfo();
    }

    private void handleMissingPreInfoForChangeError(long key, ViewHolder holder, ViewHolder oldChangeViewHolder) {
        int childCount = this.mChildHelper.getChildCount();
        for (int i = 0; i < childCount; i++) {
            ViewHolder other = getChildViewHolderInt(this.mChildHelper.getChildAt(i));
            if (other != holder && getChangedHolderKey(other) == key) {
                Adapter adapter = this.mAdapter;
                if (adapter == null || !adapter.hasStableIds()) {
                    throw new IllegalStateException("Two different ViewHolders have the same change ID. This might happen due to inconsistent Adapter update events or if the LayoutManager lays out the same View multiple times.\n ViewHolder 1:" + other + " \n View Holder 2:" + holder + exceptionLabel());
                }
                throw new IllegalStateException("Two different ViewHolders have the same stable ID. Stable IDs in your adapter MUST BE unique and SHOULD NOT change.\n ViewHolder 1:" + other + " \n View Holder 2:" + holder + exceptionLabel());
            }
        }
        Log.e(TAG, "Problem while matching changed view holders with the newones. The pre-layout information for the change holder " + oldChangeViewHolder + " cannot be found but it is necessary for " + holder + exceptionLabel());
    }

    /* access modifiers changed from: package-private */
    public void recordAnimationInfoIfBouncedHiddenView(ViewHolder viewHolder, ItemAnimator.ItemHolderInfo animationInfo) {
        viewHolder.setFlags(0, 8192);
        if (this.mState.mTrackOldChangeHolders && viewHolder.isUpdated() && !viewHolder.isRemoved() && !viewHolder.shouldIgnore()) {
            this.mViewInfoStore.addToOldChangeHolders(getChangedHolderKey(viewHolder), viewHolder);
        }
        this.mViewInfoStore.addToPreLayout(viewHolder, animationInfo);
    }

    private void findMinMaxChildLayoutPositions(int[] into) {
        int count = this.mChildHelper.getChildCount();
        if (count == 0) {
            into[0] = -1;
            into[1] = -1;
            return;
        }
        int minPositionPreLayout = Integer.MAX_VALUE;
        int maxPositionPreLayout = Integer.MIN_VALUE;
        for (int i = 0; i < count; i++) {
            ViewHolder holder = getChildViewHolderInt(this.mChildHelper.getChildAt(i));
            if (!holder.shouldIgnore()) {
                int pos = holder.getLayoutPosition();
                if (pos < minPositionPreLayout) {
                    minPositionPreLayout = pos;
                }
                if (pos > maxPositionPreLayout) {
                    maxPositionPreLayout = pos;
                }
            }
        }
        into[0] = minPositionPreLayout;
        into[1] = maxPositionPreLayout;
    }

    private boolean didChildRangeChange(int minPositionPreLayout, int maxPositionPreLayout) {
        findMinMaxChildLayoutPositions(this.mMinMaxLayoutPositions);
        int[] iArr = this.mMinMaxLayoutPositions;
        return (iArr[0] == minPositionPreLayout && iArr[1] == maxPositionPreLayout) ? false : true;
    }

    /* access modifiers changed from: protected */
    public void removeDetachedView(View child, boolean animate) {
        ViewHolder vh = getChildViewHolderInt(child);
        if (vh != null) {
            if (vh.isTmpDetached()) {
                vh.clearTmpDetachFlag();
            } else if (!vh.shouldIgnore()) {
                throw new IllegalArgumentException("Called removeDetachedView with a view which is not flagged as tmp detached." + vh + exceptionLabel());
            }
        }
        child.clearAnimation();
        dispatchChildDetached(child);
        super.removeDetachedView(child, animate);
    }

    /* access modifiers changed from: package-private */
    public long getChangedHolderKey(ViewHolder holder) {
        return this.mAdapter.hasStableIds() ? holder.getItemId() : (long) holder.mPosition;
    }

    /* access modifiers changed from: package-private */
    public void animateAppearance(ViewHolder itemHolder, ItemAnimator.ItemHolderInfo preLayoutInfo, ItemAnimator.ItemHolderInfo postLayoutInfo) {
        itemHolder.setIsRecyclable(false);
        if (this.mItemAnimator.animateAppearance(itemHolder, preLayoutInfo, postLayoutInfo)) {
            postAnimationRunner();
        }
    }

    /* access modifiers changed from: package-private */
    public void animateDisappearance(ViewHolder holder, ItemAnimator.ItemHolderInfo preLayoutInfo, ItemAnimator.ItemHolderInfo postLayoutInfo) {
        addAnimatingView(holder);
        holder.setIsRecyclable(false);
        if (this.mItemAnimator.animateDisappearance(holder, preLayoutInfo, postLayoutInfo)) {
            postAnimationRunner();
        }
    }

    private void animateChange(ViewHolder oldHolder, ViewHolder newHolder, ItemAnimator.ItemHolderInfo preInfo, ItemAnimator.ItemHolderInfo postInfo, boolean oldHolderDisappearing, boolean newHolderDisappearing) {
        oldHolder.setIsRecyclable(false);
        if (oldHolderDisappearing) {
            addAnimatingView(oldHolder);
        }
        if (oldHolder != newHolder) {
            if (newHolderDisappearing) {
                addAnimatingView(newHolder);
            }
            oldHolder.mShadowedHolder = newHolder;
            addAnimatingView(oldHolder);
            this.mRecycler.unscrapView(oldHolder);
            newHolder.setIsRecyclable(false);
            newHolder.mShadowingHolder = oldHolder;
        }
        if (this.mItemAnimator.animateChange(oldHolder, newHolder, preInfo, postInfo)) {
            postAnimationRunner();
        }
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean changed, int l, int t, int r, int b) {
        TraceCompat.beginSection(TRACE_ON_LAYOUT_TAG);
        dispatchLayout();
        TraceCompat.endSection();
        this.mFirstLayoutComplete = true;
    }

    public void requestLayout() {
        if (this.mInterceptRequestLayoutDepth != 0 || this.mLayoutSuppressed) {
            this.mLayoutWasDefered = true;
        } else {
            super.requestLayout();
        }
    }

    /* access modifiers changed from: package-private */
    public void markItemDecorInsetsDirty() {
        int childCount = this.mChildHelper.getUnfilteredChildCount();
        for (int i = 0; i < childCount; i++) {
            ((LayoutParams) this.mChildHelper.getUnfilteredChildAt(i).getLayoutParams()).mInsetsDirty = true;
        }
        this.mRecycler.markItemDecorInsetsDirty();
    }

    public void draw(Canvas c) {
        super.draw(c);
        int count = this.mItemDecorations.size();
        for (int i = 0; i < count; i++) {
            this.mItemDecorations.get(i).onDrawOver(c, this, this.mState);
        }
        boolean needsInvalidate = false;
        EdgeEffect edgeEffect = this.mLeftGlow;
        boolean z = true;
        if (edgeEffect != null && !edgeEffect.isFinished()) {
            int restore = c.save();
            int padding = this.mClipToPadding ? getPaddingBottom() : 0;
            c.rotate(270.0f);
            c.translate((float) ((-getHeight()) + padding), 0.0f);
            EdgeEffect edgeEffect2 = this.mLeftGlow;
            needsInvalidate = edgeEffect2 != null && edgeEffect2.draw(c);
            c.restoreToCount(restore);
        }
        EdgeEffect edgeEffect3 = this.mTopGlow;
        if (edgeEffect3 != null && !edgeEffect3.isFinished()) {
            int restore2 = c.save();
            if (this.mClipToPadding) {
                c.translate((float) getPaddingLeft(), (float) getPaddingTop());
            }
            EdgeEffect edgeEffect4 = this.mTopGlow;
            needsInvalidate |= edgeEffect4 != null && edgeEffect4.draw(c);
            c.restoreToCount(restore2);
        }
        EdgeEffect edgeEffect5 = this.mRightGlow;
        if (edgeEffect5 != null && !edgeEffect5.isFinished()) {
            int restore3 = c.save();
            int width = getWidth();
            int padding2 = this.mClipToPadding ? getPaddingTop() : 0;
            c.rotate(90.0f);
            c.translate((float) (-padding2), (float) (-width));
            EdgeEffect edgeEffect6 = this.mRightGlow;
            needsInvalidate |= edgeEffect6 != null && edgeEffect6.draw(c);
            c.restoreToCount(restore3);
        }
        EdgeEffect edgeEffect7 = this.mBottomGlow;
        if (edgeEffect7 != null && !edgeEffect7.isFinished()) {
            int restore4 = c.save();
            c.rotate(180.0f);
            if (this.mClipToPadding) {
                c.translate((float) ((-getWidth()) + getPaddingRight()), (float) ((-getHeight()) + getPaddingBottom()));
            } else {
                c.translate((float) (-getWidth()), (float) (-getHeight()));
            }
            EdgeEffect edgeEffect8 = this.mBottomGlow;
            if (edgeEffect8 == null || !edgeEffect8.draw(c)) {
                z = false;
            }
            needsInvalidate |= z;
            c.restoreToCount(restore4);
        }
        if (!needsInvalidate && this.mItemAnimator != null && this.mItemDecorations.size() > 0 && this.mItemAnimator.isRunning()) {
            needsInvalidate = true;
        }
        if (needsInvalidate) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    public void onDraw(Canvas c) {
        super.onDraw(c);
        int count = this.mItemDecorations.size();
        for (int i = 0; i < count; i++) {
            this.mItemDecorations.get(i).onDraw(c, this, this.mState);
        }
    }

    /* access modifiers changed from: protected */
    public boolean checkLayoutParams(ViewGroup.LayoutParams p) {
        return (p instanceof LayoutParams) && this.mLayout.checkLayoutParams((LayoutParams) p);
    }

    /* access modifiers changed from: protected */
    public ViewGroup.LayoutParams generateDefaultLayoutParams() {
        LayoutManager layoutManager = this.mLayout;
        if (layoutManager != null) {
            return layoutManager.generateDefaultLayoutParams();
        }
        throw new IllegalStateException("RecyclerView has no LayoutManager" + exceptionLabel());
    }

    public ViewGroup.LayoutParams generateLayoutParams(AttributeSet attrs) {
        LayoutManager layoutManager = this.mLayout;
        if (layoutManager != null) {
            return layoutManager.generateLayoutParams(getContext(), attrs);
        }
        throw new IllegalStateException("RecyclerView has no LayoutManager" + exceptionLabel());
    }

    /* access modifiers changed from: protected */
    public ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams p) {
        LayoutManager layoutManager = this.mLayout;
        if (layoutManager != null) {
            return layoutManager.generateLayoutParams(p);
        }
        throw new IllegalStateException("RecyclerView has no LayoutManager" + exceptionLabel());
    }

    public boolean isAnimating() {
        ItemAnimator itemAnimator = this.mItemAnimator;
        return itemAnimator != null && itemAnimator.isRunning();
    }

    /* access modifiers changed from: package-private */
    public void saveOldPositions() {
        int childCount = this.mChildHelper.getUnfilteredChildCount();
        for (int i = 0; i < childCount; i++) {
            ViewHolder holder = getChildViewHolderInt(this.mChildHelper.getUnfilteredChildAt(i));
            if (!holder.shouldIgnore()) {
                holder.saveOldPosition();
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void clearOldPositions() {
        int childCount = this.mChildHelper.getUnfilteredChildCount();
        for (int i = 0; i < childCount; i++) {
            ViewHolder holder = getChildViewHolderInt(this.mChildHelper.getUnfilteredChildAt(i));
            if (!holder.shouldIgnore()) {
                holder.clearOldPosition();
            }
        }
        this.mRecycler.clearOldPositions();
    }

    /* access modifiers changed from: package-private */
    public void offsetPositionRecordsForMove(int from, int to) {
        int inBetweenOffset;
        int end;
        int start;
        int childCount = this.mChildHelper.getUnfilteredChildCount();
        if (from < to) {
            start = from;
            end = to;
            inBetweenOffset = -1;
        } else {
            start = to;
            end = from;
            inBetweenOffset = 1;
        }
        for (int i = 0; i < childCount; i++) {
            ViewHolder holder = getChildViewHolderInt(this.mChildHelper.getUnfilteredChildAt(i));
            if (holder != null && holder.mPosition >= start && holder.mPosition <= end) {
                if (holder.mPosition == from) {
                    holder.offsetPosition(to - from, false);
                } else {
                    holder.offsetPosition(inBetweenOffset, false);
                }
                this.mState.mStructureChanged = true;
            }
        }
        this.mRecycler.offsetPositionRecordsForMove(from, to);
        requestLayout();
    }

    /* access modifiers changed from: package-private */
    public void offsetPositionRecordsForInsert(int positionStart, int itemCount) {
        int childCount = this.mChildHelper.getUnfilteredChildCount();
        for (int i = 0; i < childCount; i++) {
            ViewHolder holder = getChildViewHolderInt(this.mChildHelper.getUnfilteredChildAt(i));
            if (holder != null && !holder.shouldIgnore() && holder.mPosition >= positionStart) {
                holder.offsetPosition(itemCount, false);
                this.mState.mStructureChanged = true;
            }
        }
        this.mRecycler.offsetPositionRecordsForInsert(positionStart, itemCount);
        requestLayout();
    }

    /* access modifiers changed from: package-private */
    public void offsetPositionRecordsForRemove(int positionStart, int itemCount, boolean applyToPreLayout) {
        int positionEnd = positionStart + itemCount;
        int childCount = this.mChildHelper.getUnfilteredChildCount();
        for (int i = 0; i < childCount; i++) {
            ViewHolder holder = getChildViewHolderInt(this.mChildHelper.getUnfilteredChildAt(i));
            if (holder != null && !holder.shouldIgnore()) {
                if (holder.mPosition >= positionEnd) {
                    holder.offsetPosition(-itemCount, applyToPreLayout);
                    this.mState.mStructureChanged = true;
                } else if (holder.mPosition >= positionStart) {
                    holder.flagRemovedAndOffsetPosition(positionStart - 1, -itemCount, applyToPreLayout);
                    this.mState.mStructureChanged = true;
                }
            }
        }
        this.mRecycler.offsetPositionRecordsForRemove(positionStart, itemCount, applyToPreLayout);
        requestLayout();
    }

    /* access modifiers changed from: package-private */
    public void viewRangeUpdate(int positionStart, int itemCount, Object payload) {
        int childCount = this.mChildHelper.getUnfilteredChildCount();
        int positionEnd = positionStart + itemCount;
        for (int i = 0; i < childCount; i++) {
            View child = this.mChildHelper.getUnfilteredChildAt(i);
            ViewHolder holder = getChildViewHolderInt(child);
            if (holder != null && !holder.shouldIgnore() && holder.mPosition >= positionStart && holder.mPosition < positionEnd) {
                holder.addFlags(2);
                holder.addChangePayload(payload);
                ((LayoutParams) child.getLayoutParams()).mInsetsDirty = true;
            }
        }
        this.mRecycler.viewRangeUpdate(positionStart, itemCount);
    }

    /* access modifiers changed from: package-private */
    public boolean canReuseUpdatedViewHolder(ViewHolder viewHolder) {
        ItemAnimator itemAnimator = this.mItemAnimator;
        return itemAnimator == null || itemAnimator.canReuseUpdatedViewHolder(viewHolder, viewHolder.getUnmodifiedPayloads());
    }

    /* access modifiers changed from: package-private */
    public void processDataSetCompletelyChanged(boolean dispatchItemsChanged) {
        this.mDispatchItemsChangedEvent |= dispatchItemsChanged;
        this.mDataSetHasChangedAfterLayout = true;
        markKnownViewsInvalid();
    }

    /* access modifiers changed from: package-private */
    public void markKnownViewsInvalid() {
        int childCount = this.mChildHelper.getUnfilteredChildCount();
        for (int i = 0; i < childCount; i++) {
            ViewHolder holder = getChildViewHolderInt(this.mChildHelper.getUnfilteredChildAt(i));
            if (holder != null && !holder.shouldIgnore()) {
                holder.addFlags(6);
            }
        }
        markItemDecorInsetsDirty();
        this.mRecycler.markKnownViewsInvalid();
    }

    public void invalidateItemDecorations() {
        if (this.mItemDecorations.size() != 0) {
            LayoutManager layoutManager = this.mLayout;
            if (layoutManager != null) {
                layoutManager.assertNotInLayoutOrScroll("Cannot invalidate item decorations during a scroll or layout");
            }
            markItemDecorInsetsDirty();
            requestLayout();
        }
    }

    public boolean getPreserveFocusAfterLayout() {
        return this.mPreserveFocusAfterLayout;
    }

    public void setPreserveFocusAfterLayout(boolean preserveFocusAfterLayout) {
        this.mPreserveFocusAfterLayout = preserveFocusAfterLayout;
    }

    public ViewHolder getChildViewHolder(View child) {
        ViewParent parent = child.getParent();
        if (parent == null || parent == this) {
            return getChildViewHolderInt(child);
        }
        throw new IllegalArgumentException("View " + child + " is not a direct child of " + this);
    }

    public View findContainingItemView(View view) {
        ViewParent parent = view.getParent();
        while (parent != null && parent != this && (parent instanceof View)) {
            view = (View) parent;
            parent = view.getParent();
        }
        if (parent == this) {
            return view;
        }
        return null;
    }

    public ViewHolder findContainingViewHolder(View view) {
        View itemView = findContainingItemView(view);
        if (itemView == null) {
            return null;
        }
        return getChildViewHolder(itemView);
    }

    static ViewHolder getChildViewHolderInt(View child) {
        if (child == null) {
            return null;
        }
        return ((LayoutParams) child.getLayoutParams()).mViewHolder;
    }

    @Deprecated
    public int getChildPosition(View child) {
        return getChildAdapterPosition(child);
    }

    public int getChildAdapterPosition(View child) {
        ViewHolder holder = getChildViewHolderInt(child);
        if (holder != null) {
            return holder.getAdapterPosition();
        }
        return -1;
    }

    public int getChildLayoutPosition(View child) {
        ViewHolder holder = getChildViewHolderInt(child);
        if (holder != null) {
            return holder.getLayoutPosition();
        }
        return -1;
    }

    public long getChildItemId(View child) {
        ViewHolder holder;
        Adapter adapter = this.mAdapter;
        if (adapter == null || !adapter.hasStableIds() || (holder = getChildViewHolderInt(child)) == null) {
            return -1;
        }
        return holder.getItemId();
    }

    @Deprecated
    public ViewHolder findViewHolderForPosition(int position) {
        return findViewHolderForPosition(position, false);
    }

    public ViewHolder findViewHolderForLayoutPosition(int position) {
        return findViewHolderForPosition(position, false);
    }

    public ViewHolder findViewHolderForAdapterPosition(int position) {
        if (this.mDataSetHasChangedAfterLayout) {
            return null;
        }
        int childCount = this.mChildHelper.getUnfilteredChildCount();
        ViewHolder hidden = null;
        for (int i = 0; i < childCount; i++) {
            ViewHolder holder = getChildViewHolderInt(this.mChildHelper.getUnfilteredChildAt(i));
            if (holder != null && !holder.isRemoved() && getAdapterPositionFor(holder) == position) {
                if (!this.mChildHelper.isHidden(holder.itemView)) {
                    return holder;
                }
                hidden = holder;
            }
        }
        return hidden;
    }

    /* access modifiers changed from: package-private */
    public ViewHolder findViewHolderForPosition(int position, boolean checkNewPosition) {
        int childCount = this.mChildHelper.getUnfilteredChildCount();
        ViewHolder hidden = null;
        for (int i = 0; i < childCount; i++) {
            ViewHolder holder = getChildViewHolderInt(this.mChildHelper.getUnfilteredChildAt(i));
            if (holder != null && !holder.isRemoved()) {
                if (checkNewPosition) {
                    if (holder.mPosition != position) {
                        continue;
                    }
                } else if (holder.getLayoutPosition() != position) {
                    continue;
                }
                if (!this.mChildHelper.isHidden(holder.itemView)) {
                    return holder;
                }
                hidden = holder;
            }
        }
        return hidden;
    }

    public ViewHolder findViewHolderForItemId(long id) {
        Adapter adapter = this.mAdapter;
        if (adapter == null || !adapter.hasStableIds()) {
            return null;
        }
        int childCount = this.mChildHelper.getUnfilteredChildCount();
        ViewHolder hidden = null;
        for (int i = 0; i < childCount; i++) {
            ViewHolder holder = getChildViewHolderInt(this.mChildHelper.getUnfilteredChildAt(i));
            if (holder != null && !holder.isRemoved() && holder.getItemId() == id) {
                if (!this.mChildHelper.isHidden(holder.itemView)) {
                    return holder;
                }
                hidden = holder;
            }
        }
        return hidden;
    }

    public View findChildViewUnder(float x, float y) {
        for (int i = this.mChildHelper.getChildCount() - 1; i >= 0; i--) {
            View child = this.mChildHelper.getChildAt(i);
            float translationX = child.getTranslationX();
            float translationY = child.getTranslationY();
            if (x >= ((float) child.getLeft()) + translationX && x <= ((float) child.getRight()) + translationX && y >= ((float) child.getTop()) + translationY && y <= ((float) child.getBottom()) + translationY) {
                return child;
            }
        }
        return null;
    }

    public boolean drawChild(Canvas canvas, View child, long drawingTime) {
        return super.drawChild(canvas, child, drawingTime);
    }

    public void offsetChildrenVertical(int dy) {
        int childCount = this.mChildHelper.getChildCount();
        for (int i = 0; i < childCount; i++) {
            this.mChildHelper.getChildAt(i).offsetTopAndBottom(dy);
        }
    }

    public void onChildAttachedToWindow(View child) {
    }

    public void onChildDetachedFromWindow(View child) {
    }

    public void offsetChildrenHorizontal(int dx) {
        int childCount = this.mChildHelper.getChildCount();
        for (int i = 0; i < childCount; i++) {
            this.mChildHelper.getChildAt(i).offsetLeftAndRight(dx);
        }
    }

    public void getDecoratedBoundsWithMargins(View view, Rect outBounds) {
        getDecoratedBoundsWithMarginsInt(view, outBounds);
    }

    static void getDecoratedBoundsWithMarginsInt(View view, Rect outBounds) {
        LayoutParams lp = (LayoutParams) view.getLayoutParams();
        Rect insets = lp.mDecorInsets;
        outBounds.set((view.getLeft() - insets.left) - lp.leftMargin, (view.getTop() - insets.top) - lp.topMargin, view.getRight() + insets.right + lp.rightMargin, view.getBottom() + insets.bottom + lp.bottomMargin);
    }

    /* access modifiers changed from: package-private */
    public Rect getItemDecorInsetsForChild(View child) {
        LayoutParams lp = (LayoutParams) child.getLayoutParams();
        if (!lp.mInsetsDirty) {
            return lp.mDecorInsets;
        }
        if (this.mState.isPreLayout() && (lp.isItemChanged() || lp.isViewInvalid())) {
            return lp.mDecorInsets;
        }
        Rect insets = lp.mDecorInsets;
        insets.set(0, 0, 0, 0);
        int decorCount = this.mItemDecorations.size();
        for (int i = 0; i < decorCount; i++) {
            this.mTempRect.set(0, 0, 0, 0);
            this.mItemDecorations.get(i).getItemOffsets(this.mTempRect, child, this, this.mState);
            insets.left += this.mTempRect.left;
            insets.top += this.mTempRect.top;
            insets.right += this.mTempRect.right;
            insets.bottom += this.mTempRect.bottom;
        }
        lp.mInsetsDirty = false;
        return insets;
    }

    public void onScrolled(int dx, int dy) {
    }

    /* access modifiers changed from: package-private */
    public void dispatchOnScrolled(int hresult, int vresult) {
        this.mDispatchScrollCounter++;
        int scrollX = getScrollX();
        int scrollY = getScrollY();
        onScrollChanged(scrollX, scrollY, scrollX - hresult, scrollY - vresult);
        onScrolled(hresult, vresult);
        OnScrollListener onScrollListener = this.mScrollListener;
        if (onScrollListener != null) {
            onScrollListener.onScrolled(this, hresult, vresult);
        }
        List<OnScrollListener> list = this.mScrollListeners;
        if (list != null) {
            for (int i = list.size() - 1; i >= 0; i--) {
                this.mScrollListeners.get(i).onScrolled(this, hresult, vresult);
            }
        }
        this.mDispatchScrollCounter--;
    }

    public void onScrollStateChanged(int state) {
    }

    /* access modifiers changed from: package-private */
    public void dispatchOnScrollStateChanged(int state) {
        LayoutManager layoutManager = this.mLayout;
        if (layoutManager != null) {
            layoutManager.onScrollStateChanged(state);
        }
        onScrollStateChanged(state);
        OnScrollListener onScrollListener = this.mScrollListener;
        if (onScrollListener != null) {
            onScrollListener.onScrollStateChanged(this, state);
        }
        List<OnScrollListener> list = this.mScrollListeners;
        if (list != null) {
            for (int i = list.size() - 1; i >= 0; i--) {
                this.mScrollListeners.get(i).onScrollStateChanged(this, state);
            }
        }
    }

    public boolean hasPendingAdapterUpdates() {
        return !this.mFirstLayoutComplete || this.mDataSetHasChangedAfterLayout || this.mAdapterHelper.hasPendingUpdates();
    }

    class ViewFlinger implements Runnable {
        private boolean mEatRunOnAnimationRequest = false;
        Interpolator mInterpolator = RecyclerView.sQuinticInterpolator;
        private int mLastFlingX;
        private int mLastFlingY;
        OverScroller mOverScroller;
        private boolean mReSchedulePostAnimationCallback = false;

        ViewFlinger() {
            this.mOverScroller = new OverScroller(RecyclerView.this.getContext(), RecyclerView.sQuinticInterpolator);
        }

        public void run() {
            boolean z;
            if (RecyclerView.this.mLayout == null) {
                stop();
                return;
            }
            this.mReSchedulePostAnimationCallback = false;
            this.mEatRunOnAnimationRequest = true;
            RecyclerView.this.consumePendingUpdateOperations();
            OverScroller scroller = this.mOverScroller;
            if (scroller.computeScrollOffset()) {
                int x = scroller.getCurrX();
                int y = scroller.getCurrY();
                int unconsumedX = x - this.mLastFlingX;
                int unconsumedY = y - this.mLastFlingY;
                this.mLastFlingX = x;
                this.mLastFlingY = y;
                int consumedX = 0;
                int consumedY = 0;
                RecyclerView.this.mReusableIntPair[0] = 0;
                RecyclerView.this.mReusableIntPair[1] = 0;
                RecyclerView recyclerView = RecyclerView.this;
                if (recyclerView.dispatchNestedPreScroll(unconsumedX, unconsumedY, recyclerView.mReusableIntPair, (int[]) null, 1)) {
                    unconsumedX -= RecyclerView.this.mReusableIntPair[0];
                    unconsumedY -= RecyclerView.this.mReusableIntPair[1];
                }
                if (RecyclerView.this.getOverScrollMode() != 2) {
                    RecyclerView.this.considerReleasingGlowsOnScroll(unconsumedX, unconsumedY);
                }
                if (RecyclerView.this.mAdapter != null) {
                    RecyclerView.this.mReusableIntPair[0] = 0;
                    RecyclerView.this.mReusableIntPair[1] = 0;
                    RecyclerView recyclerView2 = RecyclerView.this;
                    recyclerView2.scrollStep(unconsumedX, unconsumedY, recyclerView2.mReusableIntPair);
                    consumedX = RecyclerView.this.mReusableIntPair[0];
                    consumedY = RecyclerView.this.mReusableIntPair[1];
                    unconsumedX -= consumedX;
                    unconsumedY -= consumedY;
                    SmoothScroller smoothScroller = RecyclerView.this.mLayout.mSmoothScroller;
                    if (smoothScroller != null && !smoothScroller.isPendingInitialRun() && smoothScroller.isRunning()) {
                        int adapterSize = RecyclerView.this.mState.getItemCount();
                        if (adapterSize == 0) {
                            smoothScroller.stop();
                        } else if (smoothScroller.getTargetPosition() >= adapterSize) {
                            smoothScroller.setTargetPosition(adapterSize - 1);
                            smoothScroller.onAnimation(consumedX, consumedY);
                        } else {
                            smoothScroller.onAnimation(consumedX, consumedY);
                        }
                    }
                }
                if (!RecyclerView.this.mItemDecorations.isEmpty()) {
                    RecyclerView.this.invalidate();
                }
                RecyclerView.this.mReusableIntPair[0] = 0;
                RecyclerView.this.mReusableIntPair[1] = 0;
                RecyclerView recyclerView3 = RecyclerView.this;
                recyclerView3.dispatchNestedScroll(consumedX, consumedY, unconsumedX, unconsumedY, (int[]) null, 1, recyclerView3.mReusableIntPair);
                int unconsumedX2 = unconsumedX - RecyclerView.this.mReusableIntPair[0];
                int unconsumedY2 = unconsumedY - RecyclerView.this.mReusableIntPair[1];
                if (!(consumedX == 0 && consumedY == 0)) {
                    RecyclerView.this.dispatchOnScrolled(consumedX, consumedY);
                }
                if (!RecyclerView.this.awakenScrollBars()) {
                    RecyclerView.this.invalidate();
                }
                boolean doneScrolling = scroller.isFinished() || (((scroller.getCurrX() == scroller.getFinalX()) || unconsumedX2 != 0) && ((scroller.getCurrY() == scroller.getFinalY()) || unconsumedY2 != 0));
                SmoothScroller smoothScroller2 = RecyclerView.this.mLayout.mSmoothScroller;
                if ((smoothScroller2 != null && smoothScroller2.isPendingInitialRun()) || !doneScrolling) {
                    postOnAnimation();
                    if (RecyclerView.this.mGapWorker != null) {
                        RecyclerView.this.mGapWorker.postFromTraversal(RecyclerView.this, consumedX, consumedY);
                    }
                } else {
                    if (RecyclerView.this.getOverScrollMode() != 2) {
                        int vel = (int) scroller.getCurrVelocity();
                        int i = vel;
                        RecyclerView.this.absorbGlows(unconsumedX2 < 0 ? -vel : unconsumedX2 > 0 ? vel : 0, unconsumedY2 < 0 ? -vel : unconsumedY2 > 0 ? vel : 0);
                    }
                    if (RecyclerView.ALLOW_THREAD_GAP_WORK != 0) {
                        RecyclerView.this.mPrefetchRegistry.clearPrefetchPositions();
                    }
                }
            }
            SmoothScroller smoothScroller3 = RecyclerView.this.mLayout.mSmoothScroller;
            if (smoothScroller3 == null || !smoothScroller3.isPendingInitialRun()) {
                z = false;
            } else {
                z = false;
                smoothScroller3.onAnimation(0, 0);
            }
            this.mEatRunOnAnimationRequest = z;
            if (this.mReSchedulePostAnimationCallback) {
                internalPostOnAnimation();
                return;
            }
            RecyclerView.this.setScrollState(z ? 1 : 0);
            RecyclerView.this.stopNestedScroll(1);
        }

        /* access modifiers changed from: package-private */
        public void postOnAnimation() {
            if (this.mEatRunOnAnimationRequest) {
                this.mReSchedulePostAnimationCallback = true;
            } else {
                internalPostOnAnimation();
            }
        }

        private void internalPostOnAnimation() {
            RecyclerView.this.removeCallbacks(this);
            ViewCompat.postOnAnimation(RecyclerView.this, this);
        }

        public void fling(int velocityX, int velocityY) {
            RecyclerView.this.setScrollState(2);
            this.mLastFlingY = 0;
            this.mLastFlingX = 0;
            if (this.mInterpolator != RecyclerView.sQuinticInterpolator) {
                this.mInterpolator = RecyclerView.sQuinticInterpolator;
                this.mOverScroller = new OverScroller(RecyclerView.this.getContext(), RecyclerView.sQuinticInterpolator);
            }
            this.mOverScroller.fling(0, 0, velocityX, velocityY, Integer.MIN_VALUE, Integer.MAX_VALUE, Integer.MIN_VALUE, Integer.MAX_VALUE);
            postOnAnimation();
        }

        public void smoothScrollBy(int dx, int dy, int duration, Interpolator interpolator) {
            if (duration == Integer.MIN_VALUE) {
                duration = computeScrollDuration(dx, dy, 0, 0);
            }
            if (interpolator == null) {
                interpolator = RecyclerView.sQuinticInterpolator;
            }
            if (this.mInterpolator != interpolator) {
                this.mInterpolator = interpolator;
                this.mOverScroller = new OverScroller(RecyclerView.this.getContext(), interpolator);
            }
            this.mLastFlingY = 0;
            this.mLastFlingX = 0;
            RecyclerView.this.setScrollState(2);
            this.mOverScroller.startScroll(0, 0, dx, dy, duration);
            if (Build.VERSION.SDK_INT < 23) {
                this.mOverScroller.computeScrollOffset();
            }
            postOnAnimation();
        }

        private float distanceInfluenceForSnapDuration(float f) {
            return (float) Math.sin((double) ((f - 0.5f) * 0.47123894f));
        }

        private int computeScrollDuration(int dx, int dy, int vx, int vy) {
            int duration;
            int absDx = Math.abs(dx);
            int absDy = Math.abs(dy);
            boolean horizontal = absDx > absDy;
            int velocity = (int) Math.sqrt((double) ((vx * vx) + (vy * vy)));
            int delta = (int) Math.sqrt((double) ((dx * dx) + (dy * dy)));
            RecyclerView recyclerView = RecyclerView.this;
            int containerSize = horizontal ? recyclerView.getWidth() : recyclerView.getHeight();
            int halfContainerSize = containerSize / 2;
            float distance = ((float) halfContainerSize) + (((float) halfContainerSize) * distanceInfluenceForSnapDuration(Math.min(1.0f, (((float) delta) * 1.0f) / ((float) containerSize))));
            if (velocity > 0) {
                duration = Math.round(Math.abs(distance / ((float) velocity)) * 1000.0f) * 4;
            } else {
                duration = (int) (((((float) (horizontal ? absDx : absDy)) / ((float) containerSize)) + 1.0f) * 300.0f);
            }
            return Math.min(duration, RecyclerView.MAX_SCROLL_DURATION);
        }

        public void stop() {
            RecyclerView.this.removeCallbacks(this);
            this.mOverScroller.abortAnimation();
        }
    }

    /* access modifiers changed from: package-private */
    public void repositionShadowingViews() {
        int count = this.mChildHelper.getChildCount();
        for (int i = 0; i < count; i++) {
            View view = this.mChildHelper.getChildAt(i);
            ViewHolder holder = getChildViewHolder(view);
            if (!(holder == null || holder.mShadowingHolder == null)) {
                View shadowingView = holder.mShadowingHolder.itemView;
                int left = view.getLeft();
                int top = view.getTop();
                if (left != shadowingView.getLeft() || top != shadowingView.getTop()) {
                    shadowingView.layout(left, top, shadowingView.getWidth() + left, shadowingView.getHeight() + top);
                }
            }
        }
    }

    private class RecyclerViewDataObserver extends AdapterDataObserver {
        RecyclerViewDataObserver() {
        }

        public void onChanged() {
            RecyclerView.this.assertNotInLayoutOrScroll((String) null);
            RecyclerView.this.mState.mStructureChanged = true;
            RecyclerView.this.processDataSetCompletelyChanged(true);
            if (!RecyclerView.this.mAdapterHelper.hasPendingUpdates()) {
                RecyclerView.this.requestLayout();
            }
        }

        public void onItemRangeChanged(int positionStart, int itemCount, Object payload) {
            RecyclerView.this.assertNotInLayoutOrScroll((String) null);
            if (RecyclerView.this.mAdapterHelper.onItemRangeChanged(positionStart, itemCount, payload)) {
                triggerUpdateProcessor();
            }
        }

        public void onItemRangeInserted(int positionStart, int itemCount) {
            RecyclerView.this.assertNotInLayoutOrScroll((String) null);
            if (RecyclerView.this.mAdapterHelper.onItemRangeInserted(positionStart, itemCount)) {
                triggerUpdateProcessor();
            }
        }

        public void onItemRangeRemoved(int positionStart, int itemCount) {
            RecyclerView.this.assertNotInLayoutOrScroll((String) null);
            if (RecyclerView.this.mAdapterHelper.onItemRangeRemoved(positionStart, itemCount)) {
                triggerUpdateProcessor();
            }
        }

        public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
            RecyclerView.this.assertNotInLayoutOrScroll((String) null);
            if (RecyclerView.this.mAdapterHelper.onItemRangeMoved(fromPosition, toPosition, itemCount)) {
                triggerUpdateProcessor();
            }
        }

        /* access modifiers changed from: package-private */
        public void triggerUpdateProcessor() {
            if (!RecyclerView.POST_UPDATES_ON_ANIMATION || !RecyclerView.this.mHasFixedSize || !RecyclerView.this.mIsAttached) {
                RecyclerView.this.mAdapterUpdateDuringMeasure = true;
                RecyclerView.this.requestLayout();
                return;
            }
            RecyclerView recyclerView = RecyclerView.this;
            ViewCompat.postOnAnimation(recyclerView, recyclerView.mUpdateChildViewsRunnable);
        }
    }

    public static class EdgeEffectFactory {
        public static final int DIRECTION_BOTTOM = 3;
        public static final int DIRECTION_LEFT = 0;
        public static final int DIRECTION_RIGHT = 2;
        public static final int DIRECTION_TOP = 1;

        @Retention(RetentionPolicy.SOURCE)
        public @interface EdgeDirection {
        }

        /* access modifiers changed from: protected */
        public EdgeEffect createEdgeEffect(RecyclerView view, int direction) {
            return new EdgeEffect(view.getContext());
        }
    }

    public static class RecycledViewPool {
        private static final int DEFAULT_MAX_SCRAP = 5;
        private int mAttachCount = 0;
        SparseArray<ScrapData> mScrap = new SparseArray<>();

        static class ScrapData {
            long mBindRunningAverageNs = 0;
            long mCreateRunningAverageNs = 0;
            int mMaxScrap = 5;
            final ArrayList<ViewHolder> mScrapHeap = new ArrayList<>();

            ScrapData() {
            }
        }

        public void clear() {
            for (int i = 0; i < this.mScrap.size(); i++) {
                this.mScrap.valueAt(i).mScrapHeap.clear();
            }
        }

        public void setMaxRecycledViews(int viewType, int max) {
            ScrapData scrapData = getScrapDataForType(viewType);
            scrapData.mMaxScrap = max;
            ArrayList<ViewHolder> scrapHeap = scrapData.mScrapHeap;
            while (scrapHeap.size() > max) {
                scrapHeap.remove(scrapHeap.size() - 1);
            }
        }

        public int getRecycledViewCount(int viewType) {
            return getScrapDataForType(viewType).mScrapHeap.size();
        }

        public ViewHolder getRecycledView(int viewType) {
            ScrapData scrapData = this.mScrap.get(viewType);
            if (scrapData == null || scrapData.mScrapHeap.isEmpty()) {
                return null;
            }
            ArrayList<ViewHolder> scrapHeap = scrapData.mScrapHeap;
            for (int i = scrapHeap.size() - 1; i >= 0; i--) {
                if (!scrapHeap.get(i).isAttachedToTransitionOverlay()) {
                    return scrapHeap.remove(i);
                }
            }
            return null;
        }

        /* access modifiers changed from: package-private */
        public int size() {
            int count = 0;
            for (int i = 0; i < this.mScrap.size(); i++) {
                ArrayList<ViewHolder> viewHolders = this.mScrap.valueAt(i).mScrapHeap;
                if (viewHolders != null) {
                    count += viewHolders.size();
                }
            }
            return count;
        }

        public void putRecycledView(ViewHolder scrap) {
            int viewType = scrap.getItemViewType();
            ArrayList<ViewHolder> scrapHeap = getScrapDataForType(viewType).mScrapHeap;
            if (this.mScrap.get(viewType).mMaxScrap > scrapHeap.size()) {
                scrap.resetInternal();
                scrapHeap.add(scrap);
            }
        }

        /* access modifiers changed from: package-private */
        public long runningAverage(long oldAverage, long newValue) {
            if (oldAverage == 0) {
                return newValue;
            }
            return ((oldAverage / 4) * 3) + (newValue / 4);
        }

        /* access modifiers changed from: package-private */
        public void factorInCreateTime(int viewType, long createTimeNs) {
            ScrapData scrapData = getScrapDataForType(viewType);
            scrapData.mCreateRunningAverageNs = runningAverage(scrapData.mCreateRunningAverageNs, createTimeNs);
        }

        /* access modifiers changed from: package-private */
        public void factorInBindTime(int viewType, long bindTimeNs) {
            ScrapData scrapData = getScrapDataForType(viewType);
            scrapData.mBindRunningAverageNs = runningAverage(scrapData.mBindRunningAverageNs, bindTimeNs);
        }

        /* access modifiers changed from: package-private */
        public boolean willCreateInTime(int viewType, long approxCurrentNs, long deadlineNs) {
            long expectedDurationNs = getScrapDataForType(viewType).mCreateRunningAverageNs;
            return expectedDurationNs == 0 || approxCurrentNs + expectedDurationNs < deadlineNs;
        }

        /* access modifiers changed from: package-private */
        public boolean willBindInTime(int viewType, long approxCurrentNs, long deadlineNs) {
            long expectedDurationNs = getScrapDataForType(viewType).mBindRunningAverageNs;
            return expectedDurationNs == 0 || approxCurrentNs + expectedDurationNs < deadlineNs;
        }

        /* access modifiers changed from: package-private */
        public void attach() {
            this.mAttachCount++;
        }

        /* access modifiers changed from: package-private */
        public void detach() {
            this.mAttachCount--;
        }

        /* access modifiers changed from: package-private */
        public void onAdapterChanged(Adapter oldAdapter, Adapter newAdapter, boolean compatibleWithPrevious) {
            if (oldAdapter != null) {
                detach();
            }
            if (!compatibleWithPrevious && this.mAttachCount == 0) {
                clear();
            }
            if (newAdapter != null) {
                attach();
            }
        }

        private ScrapData getScrapDataForType(int viewType) {
            ScrapData scrapData = this.mScrap.get(viewType);
            if (scrapData != null) {
                return scrapData;
            }
            ScrapData scrapData2 = new ScrapData();
            this.mScrap.put(viewType, scrapData2);
            return scrapData2;
        }
    }

    static RecyclerView findNestedRecyclerView(View view) {
        if (!(view instanceof ViewGroup)) {
            return null;
        }
        if (view instanceof RecyclerView) {
            return (RecyclerView) view;
        }
        ViewGroup parent = (ViewGroup) view;
        int count = parent.getChildCount();
        for (int i = 0; i < count; i++) {
            RecyclerView descendant = findNestedRecyclerView(parent.getChildAt(i));
            if (descendant != null) {
                return descendant;
            }
        }
        return null;
    }

    /* JADX WARNING: type inference failed for: r1v2, types: [android.view.ViewParent] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static void clearNestedRecyclerViewIfNotNested(androidx.recyclerview.widget.RecyclerView.ViewHolder r3) {
        /*
            java.lang.ref.WeakReference<androidx.recyclerview.widget.RecyclerView> r0 = r3.mNestedRecyclerView
            if (r0 == 0) goto L_0x0024
            java.lang.ref.WeakReference<androidx.recyclerview.widget.RecyclerView> r0 = r3.mNestedRecyclerView
            java.lang.Object r0 = r0.get()
            android.view.View r0 = (android.view.View) r0
        L_0x000c:
            if (r0 == 0) goto L_0x0021
            android.view.View r1 = r3.itemView
            if (r0 != r1) goto L_0x0013
            return
        L_0x0013:
            android.view.ViewParent r1 = r0.getParent()
            boolean r2 = r1 instanceof android.view.View
            if (r2 == 0) goto L_0x001f
            r0 = r1
            android.view.View r0 = (android.view.View) r0
            goto L_0x0020
        L_0x001f:
            r0 = 0
        L_0x0020:
            goto L_0x000c
        L_0x0021:
            r1 = 0
            r3.mNestedRecyclerView = r1
        L_0x0024:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.recyclerview.widget.RecyclerView.clearNestedRecyclerViewIfNotNested(androidx.recyclerview.widget.RecyclerView$ViewHolder):void");
    }

    /* access modifiers changed from: package-private */
    public long getNanoTime() {
        if (ALLOW_THREAD_GAP_WORK) {
            return System.nanoTime();
        }
        return 0;
    }

    public final class Recycler {
        static final int DEFAULT_CACHE_SIZE = 2;
        final ArrayList<ViewHolder> mAttachedScrap;
        final ArrayList<ViewHolder> mCachedViews = new ArrayList<>();
        ArrayList<ViewHolder> mChangedScrap = null;
        RecycledViewPool mRecyclerPool;
        private int mRequestedCacheMax;
        private final List<ViewHolder> mUnmodifiableAttachedScrap;
        private ViewCacheExtension mViewCacheExtension;
        int mViewCacheMax;

        public Recycler() {
            ArrayList<ViewHolder> arrayList = new ArrayList<>();
            this.mAttachedScrap = arrayList;
            this.mUnmodifiableAttachedScrap = Collections.unmodifiableList(arrayList);
            this.mRequestedCacheMax = 2;
            this.mViewCacheMax = 2;
        }

        public void clear() {
            this.mAttachedScrap.clear();
            recycleAndClearCachedViews();
        }

        public void setViewCacheSize(int viewCount) {
            this.mRequestedCacheMax = viewCount;
            updateViewCacheSize();
        }

        /* access modifiers changed from: package-private */
        public void updateViewCacheSize() {
            this.mViewCacheMax = this.mRequestedCacheMax + (RecyclerView.this.mLayout != null ? RecyclerView.this.mLayout.mPrefetchMaxCountObserved : 0);
            for (int i = this.mCachedViews.size() - 1; i >= 0 && this.mCachedViews.size() > this.mViewCacheMax; i--) {
                recycleCachedViewAt(i);
            }
        }

        public List<ViewHolder> getScrapList() {
            return this.mUnmodifiableAttachedScrap;
        }

        /* access modifiers changed from: package-private */
        public boolean validateViewHolderForOffsetPosition(ViewHolder holder) {
            if (holder.isRemoved()) {
                return RecyclerView.this.mState.isPreLayout();
            }
            if (holder.mPosition < 0 || holder.mPosition >= RecyclerView.this.mAdapter.getItemCount()) {
                throw new IndexOutOfBoundsException("Inconsistency detected. Invalid view holder adapter position" + holder + RecyclerView.this.exceptionLabel());
            } else if (!RecyclerView.this.mState.isPreLayout() && RecyclerView.this.mAdapter.getItemViewType(holder.mPosition) != holder.getItemViewType()) {
                return false;
            } else {
                if (!RecyclerView.this.mAdapter.hasStableIds() || holder.getItemId() == RecyclerView.this.mAdapter.getItemId(holder.mPosition)) {
                    return true;
                }
                return false;
            }
        }

        private boolean tryBindViewHolderByDeadline(ViewHolder holder, int offsetPosition, int position, long deadlineNs) {
            holder.mOwnerRecyclerView = RecyclerView.this;
            int viewType = holder.getItemViewType();
            long startBindNs = RecyclerView.this.getNanoTime();
            if (deadlineNs != Long.MAX_VALUE && !this.mRecyclerPool.willBindInTime(viewType, startBindNs, deadlineNs)) {
                return false;
            }
            RecyclerView.this.mAdapter.bindViewHolder(holder, offsetPosition);
            this.mRecyclerPool.factorInBindTime(holder.getItemViewType(), RecyclerView.this.getNanoTime() - startBindNs);
            attachAccessibilityDelegateOnBind(holder);
            if (!RecyclerView.this.mState.isPreLayout()) {
                return true;
            }
            holder.mPreLayoutPosition = position;
            return true;
        }

        public void bindViewToPosition(View view, int position) {
            LayoutParams rvLayoutParams;
            ViewHolder holder = RecyclerView.getChildViewHolderInt(view);
            if (holder != null) {
                int offsetPosition = RecyclerView.this.mAdapterHelper.findPositionOffset(position);
                if (offsetPosition < 0 || offsetPosition >= RecyclerView.this.mAdapter.getItemCount()) {
                    throw new IndexOutOfBoundsException("Inconsistency detected. Invalid item position " + position + "(offset:" + offsetPosition + ").state:" + RecyclerView.this.mState.getItemCount() + RecyclerView.this.exceptionLabel());
                }
                tryBindViewHolderByDeadline(holder, offsetPosition, position, Long.MAX_VALUE);
                ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();
                if (lp == null) {
                    rvLayoutParams = (LayoutParams) RecyclerView.this.generateDefaultLayoutParams();
                    holder.itemView.setLayoutParams(rvLayoutParams);
                } else if (!RecyclerView.this.checkLayoutParams(lp)) {
                    rvLayoutParams = (LayoutParams) RecyclerView.this.generateLayoutParams(lp);
                    holder.itemView.setLayoutParams(rvLayoutParams);
                } else {
                    rvLayoutParams = (LayoutParams) lp;
                }
                boolean z = true;
                rvLayoutParams.mInsetsDirty = true;
                rvLayoutParams.mViewHolder = holder;
                if (holder.itemView.getParent() != null) {
                    z = false;
                }
                rvLayoutParams.mPendingInvalidate = z;
                return;
            }
            throw new IllegalArgumentException("The view does not have a ViewHolder. You cannot pass arbitrary views to this method, they should be created by the Adapter" + RecyclerView.this.exceptionLabel());
        }

        public int convertPreLayoutPositionToPostLayout(int position) {
            if (position < 0 || position >= RecyclerView.this.mState.getItemCount()) {
                throw new IndexOutOfBoundsException("invalid position " + position + ". State item count is " + RecyclerView.this.mState.getItemCount() + RecyclerView.this.exceptionLabel());
            } else if (!RecyclerView.this.mState.isPreLayout()) {
                return position;
            } else {
                return RecyclerView.this.mAdapterHelper.findPositionOffset(position);
            }
        }

        public View getViewForPosition(int position) {
            return getViewForPosition(position, false);
        }

        /* access modifiers changed from: package-private */
        public View getViewForPosition(int position, boolean dryRun) {
            return tryGetViewHolderForPositionByDeadline(position, dryRun, Long.MAX_VALUE).itemView;
        }

        /* access modifiers changed from: package-private */
        public ViewHolder tryGetViewHolderForPositionByDeadline(int position, boolean dryRun, long deadlineNs) {
            ViewHolder holder;
            boolean fromScrapOrHiddenOrCache;
            LayoutParams rvLayoutParams;
            RecyclerView innerView;
            ViewCacheExtension viewCacheExtension;
            View view;
            int i = position;
            boolean z = dryRun;
            if (i < 0 || i >= RecyclerView.this.mState.getItemCount()) {
                throw new IndexOutOfBoundsException("Invalid item position " + i + "(" + i + "). Item count:" + RecyclerView.this.mState.getItemCount() + RecyclerView.this.exceptionLabel());
            }
            boolean fromScrapOrHiddenOrCache2 = false;
            ViewHolder holder2 = null;
            boolean z2 = true;
            if (RecyclerView.this.mState.isPreLayout()) {
                holder2 = getChangedScrapViewForPosition(position);
                fromScrapOrHiddenOrCache2 = holder2 != null;
            }
            if (holder2 == null && (holder2 = getScrapOrHiddenOrCachedHolderForPosition(position, dryRun)) != null) {
                if (!validateViewHolderForOffsetPosition(holder2)) {
                    if (!z) {
                        holder2.addFlags(4);
                        if (holder2.isScrap()) {
                            RecyclerView.this.removeDetachedView(holder2.itemView, false);
                            holder2.unScrap();
                        } else if (holder2.wasReturnedFromScrap()) {
                            holder2.clearReturnedFromScrapFlag();
                        }
                        recycleViewHolderInternal(holder2);
                    }
                    holder2 = null;
                } else {
                    fromScrapOrHiddenOrCache2 = true;
                }
            }
            if (holder2 == null) {
                int offsetPosition = RecyclerView.this.mAdapterHelper.findPositionOffset(i);
                if (offsetPosition < 0 || offsetPosition >= RecyclerView.this.mAdapter.getItemCount()) {
                    throw new IndexOutOfBoundsException("Inconsistency detected. Invalid item position " + i + "(offset:" + offsetPosition + ").state:" + RecyclerView.this.mState.getItemCount() + RecyclerView.this.exceptionLabel());
                }
                int type = RecyclerView.this.mAdapter.getItemViewType(offsetPosition);
                if (RecyclerView.this.mAdapter.hasStableIds() && (holder2 = getScrapOrCachedViewForId(RecyclerView.this.mAdapter.getItemId(offsetPosition), type, z)) != null) {
                    holder2.mPosition = offsetPosition;
                    fromScrapOrHiddenOrCache2 = true;
                }
                if (!(holder2 != null || (viewCacheExtension = this.mViewCacheExtension) == null || (view = viewCacheExtension.getViewForPositionAndType(this, i, type)) == null)) {
                    holder2 = RecyclerView.this.getChildViewHolder(view);
                    if (holder2 == null) {
                        throw new IllegalArgumentException("getViewForPositionAndType returned a view which does not have a ViewHolder" + RecyclerView.this.exceptionLabel());
                    } else if (holder2.shouldIgnore()) {
                        throw new IllegalArgumentException("getViewForPositionAndType returned a view that is ignored. You must call stopIgnoring before returning this view." + RecyclerView.this.exceptionLabel());
                    }
                }
                if (holder2 == null && (holder2 = getRecycledViewPool().getRecycledView(type)) != null) {
                    holder2.resetInternal();
                    if (RecyclerView.FORCE_INVALIDATE_DISPLAY_LIST) {
                        invalidateDisplayListInt(holder2);
                    }
                }
                if (holder2 == null) {
                    long start = RecyclerView.this.getNanoTime();
                    if (deadlineNs != Long.MAX_VALUE && !this.mRecyclerPool.willCreateInTime(type, start, deadlineNs)) {
                        return null;
                    }
                    ViewHolder holder3 = RecyclerView.this.mAdapter.createViewHolder(RecyclerView.this, type);
                    if (RecyclerView.ALLOW_THREAD_GAP_WORK && (innerView = RecyclerView.findNestedRecyclerView(holder3.itemView)) != null) {
                        holder3.mNestedRecyclerView = new WeakReference<>(innerView);
                    }
                    this.mRecyclerPool.factorInCreateTime(type, RecyclerView.this.getNanoTime() - start);
                    fromScrapOrHiddenOrCache = fromScrapOrHiddenOrCache2;
                    holder = holder3;
                } else {
                    fromScrapOrHiddenOrCache = fromScrapOrHiddenOrCache2;
                    holder = holder2;
                }
            } else {
                fromScrapOrHiddenOrCache = fromScrapOrHiddenOrCache2;
                holder = holder2;
            }
            if (fromScrapOrHiddenOrCache && !RecyclerView.this.mState.isPreLayout() && holder.hasAnyOfTheFlags(8192)) {
                holder.setFlags(0, 8192);
                if (RecyclerView.this.mState.mRunSimpleAnimations) {
                    RecyclerView.this.recordAnimationInfoIfBouncedHiddenView(holder, RecyclerView.this.mItemAnimator.recordPreLayoutInformation(RecyclerView.this.mState, holder, ItemAnimator.buildAdapterChangeFlagsForAnimations(holder) | 4096, holder.getUnmodifiedPayloads()));
                }
            }
            boolean bound = false;
            if (RecyclerView.this.mState.isPreLayout() && holder.isBound()) {
                holder.mPreLayoutPosition = i;
            } else if (!holder.isBound() || holder.needsUpdate() || holder.isInvalid()) {
                bound = tryBindViewHolderByDeadline(holder, RecyclerView.this.mAdapterHelper.findPositionOffset(i), position, deadlineNs);
            }
            ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();
            if (lp == null) {
                rvLayoutParams = (LayoutParams) RecyclerView.this.generateDefaultLayoutParams();
                holder.itemView.setLayoutParams(rvLayoutParams);
            } else if (!RecyclerView.this.checkLayoutParams(lp)) {
                rvLayoutParams = (LayoutParams) RecyclerView.this.generateLayoutParams(lp);
                holder.itemView.setLayoutParams(rvLayoutParams);
            } else {
                rvLayoutParams = (LayoutParams) lp;
            }
            rvLayoutParams.mViewHolder = holder;
            if (!fromScrapOrHiddenOrCache || !bound) {
                z2 = false;
            }
            rvLayoutParams.mPendingInvalidate = z2;
            return holder;
        }

        private void attachAccessibilityDelegateOnBind(ViewHolder holder) {
            if (RecyclerView.this.isAccessibilityEnabled()) {
                View itemView = holder.itemView;
                if (ViewCompat.getImportantForAccessibility(itemView) == 0) {
                    ViewCompat.setImportantForAccessibility(itemView, 1);
                }
                if (RecyclerView.this.mAccessibilityDelegate != null) {
                    AccessibilityDelegateCompat itemDelegate = RecyclerView.this.mAccessibilityDelegate.getItemDelegate();
                    if (itemDelegate instanceof RecyclerViewAccessibilityDelegate.ItemDelegate) {
                        ((RecyclerViewAccessibilityDelegate.ItemDelegate) itemDelegate).saveOriginalDelegate(itemView);
                    }
                    ViewCompat.setAccessibilityDelegate(itemView, itemDelegate);
                }
            }
        }

        private void invalidateDisplayListInt(ViewHolder holder) {
            if (holder.itemView instanceof ViewGroup) {
                invalidateDisplayListInt((ViewGroup) holder.itemView, false);
            }
        }

        private void invalidateDisplayListInt(ViewGroup viewGroup, boolean invalidateThis) {
            for (int i = viewGroup.getChildCount() - 1; i >= 0; i--) {
                View view = viewGroup.getChildAt(i);
                if (view instanceof ViewGroup) {
                    invalidateDisplayListInt((ViewGroup) view, true);
                }
            }
            if (invalidateThis) {
                if (viewGroup.getVisibility() == 4) {
                    viewGroup.setVisibility(0);
                    viewGroup.setVisibility(4);
                    return;
                }
                int visibility = viewGroup.getVisibility();
                viewGroup.setVisibility(4);
                viewGroup.setVisibility(visibility);
            }
        }

        public void recycleView(View view) {
            ViewHolder holder = RecyclerView.getChildViewHolderInt(view);
            if (holder.isTmpDetached()) {
                RecyclerView.this.removeDetachedView(view, false);
            }
            if (holder.isScrap()) {
                holder.unScrap();
            } else if (holder.wasReturnedFromScrap()) {
                holder.clearReturnedFromScrapFlag();
            }
            recycleViewHolderInternal(holder);
            if (RecyclerView.this.mItemAnimator != null && !holder.isRecyclable()) {
                RecyclerView.this.mItemAnimator.endAnimation(holder);
            }
        }

        /* access modifiers changed from: package-private */
        public void recycleAndClearCachedViews() {
            for (int i = this.mCachedViews.size() - 1; i >= 0; i--) {
                recycleCachedViewAt(i);
            }
            this.mCachedViews.clear();
            if (RecyclerView.ALLOW_THREAD_GAP_WORK) {
                RecyclerView.this.mPrefetchRegistry.clearPrefetchPositions();
            }
        }

        /* access modifiers changed from: package-private */
        public void recycleCachedViewAt(int cachedViewIndex) {
            addViewHolderToRecycledViewPool(this.mCachedViews.get(cachedViewIndex), true);
            this.mCachedViews.remove(cachedViewIndex);
        }

        /* access modifiers changed from: package-private */
        public void recycleViewHolderInternal(ViewHolder holder) {
            boolean z = false;
            if (holder.isScrap() || holder.itemView.getParent() != null) {
                StringBuilder append = new StringBuilder().append("Scrapped or attached views may not be recycled. isScrap:").append(holder.isScrap()).append(" isAttached:");
                if (holder.itemView.getParent() != null) {
                    z = true;
                }
                throw new IllegalArgumentException(append.append(z).append(RecyclerView.this.exceptionLabel()).toString());
            } else if (holder.isTmpDetached()) {
                throw new IllegalArgumentException("Tmp detached view should be removed from RecyclerView before it can be recycled: " + holder + RecyclerView.this.exceptionLabel());
            } else if (!holder.shouldIgnore()) {
                boolean transientStatePreventsRecycling = holder.doesTransientStatePreventRecycling();
                boolean cached = false;
                boolean recycled = false;
                if ((RecyclerView.this.mAdapter != null && transientStatePreventsRecycling && RecyclerView.this.mAdapter.onFailedToRecycleView(holder)) || holder.isRecyclable()) {
                    if (this.mViewCacheMax > 0 && !holder.hasAnyOfTheFlags(MetaDo.META_SETVIEWPORTEXT)) {
                        int cachedViewSize = this.mCachedViews.size();
                        if (cachedViewSize >= this.mViewCacheMax && cachedViewSize > 0) {
                            recycleCachedViewAt(0);
                            cachedViewSize--;
                        }
                        int targetCacheIndex = cachedViewSize;
                        if (RecyclerView.ALLOW_THREAD_GAP_WORK && cachedViewSize > 0 && !RecyclerView.this.mPrefetchRegistry.lastPrefetchIncludedPosition(holder.mPosition)) {
                            int cacheIndex = cachedViewSize - 1;
                            while (cacheIndex >= 0) {
                                if (!RecyclerView.this.mPrefetchRegistry.lastPrefetchIncludedPosition(this.mCachedViews.get(cacheIndex).mPosition)) {
                                    break;
                                }
                                cacheIndex--;
                            }
                            targetCacheIndex = cacheIndex + 1;
                        }
                        this.mCachedViews.add(targetCacheIndex, holder);
                        cached = true;
                    }
                    if (!cached) {
                        addViewHolderToRecycledViewPool(holder, true);
                        recycled = true;
                    }
                }
                RecyclerView.this.mViewInfoStore.removeViewHolder(holder);
                if (!cached && !recycled && transientStatePreventsRecycling) {
                    holder.mOwnerRecyclerView = null;
                }
            } else {
                throw new IllegalArgumentException("Trying to recycle an ignored view holder. You should first call stopIgnoringView(view) before calling recycle." + RecyclerView.this.exceptionLabel());
            }
        }

        /* access modifiers changed from: package-private */
        public void addViewHolderToRecycledViewPool(ViewHolder holder, boolean dispatchRecycled) {
            RecyclerView.clearNestedRecyclerViewIfNotNested(holder);
            View itemView = holder.itemView;
            if (RecyclerView.this.mAccessibilityDelegate != null) {
                AccessibilityDelegateCompat itemDelegate = RecyclerView.this.mAccessibilityDelegate.getItemDelegate();
                AccessibilityDelegateCompat originalDelegate = null;
                if (itemDelegate instanceof RecyclerViewAccessibilityDelegate.ItemDelegate) {
                    originalDelegate = ((RecyclerViewAccessibilityDelegate.ItemDelegate) itemDelegate).getAndRemoveOriginalDelegateForItem(itemView);
                }
                ViewCompat.setAccessibilityDelegate(itemView, originalDelegate);
            }
            if (dispatchRecycled) {
                dispatchViewRecycled(holder);
            }
            holder.mOwnerRecyclerView = null;
            getRecycledViewPool().putRecycledView(holder);
        }

        /* access modifiers changed from: package-private */
        public void quickRecycleScrapView(View view) {
            ViewHolder holder = RecyclerView.getChildViewHolderInt(view);
            holder.mScrapContainer = null;
            holder.mInChangeScrap = false;
            holder.clearReturnedFromScrapFlag();
            recycleViewHolderInternal(holder);
        }

        /* access modifiers changed from: package-private */
        public void scrapView(View view) {
            ViewHolder holder = RecyclerView.getChildViewHolderInt(view);
            if (!holder.hasAnyOfTheFlags(12) && holder.isUpdated() && !RecyclerView.this.canReuseUpdatedViewHolder(holder)) {
                if (this.mChangedScrap == null) {
                    this.mChangedScrap = new ArrayList<>();
                }
                holder.setScrapContainer(this, true);
                this.mChangedScrap.add(holder);
            } else if (!holder.isInvalid() || holder.isRemoved() || RecyclerView.this.mAdapter.hasStableIds()) {
                holder.setScrapContainer(this, false);
                this.mAttachedScrap.add(holder);
            } else {
                throw new IllegalArgumentException("Called scrap view with an invalid view. Invalid views cannot be reused from scrap, they should rebound from recycler pool." + RecyclerView.this.exceptionLabel());
            }
        }

        /* access modifiers changed from: package-private */
        public void unscrapView(ViewHolder holder) {
            if (holder.mInChangeScrap) {
                this.mChangedScrap.remove(holder);
            } else {
                this.mAttachedScrap.remove(holder);
            }
            holder.mScrapContainer = null;
            holder.mInChangeScrap = false;
            holder.clearReturnedFromScrapFlag();
        }

        /* access modifiers changed from: package-private */
        public int getScrapCount() {
            return this.mAttachedScrap.size();
        }

        /* access modifiers changed from: package-private */
        public View getScrapViewAt(int index) {
            return this.mAttachedScrap.get(index).itemView;
        }

        /* access modifiers changed from: package-private */
        public void clearScrap() {
            this.mAttachedScrap.clear();
            ArrayList<ViewHolder> arrayList = this.mChangedScrap;
            if (arrayList != null) {
                arrayList.clear();
            }
        }

        /* access modifiers changed from: package-private */
        public ViewHolder getChangedScrapViewForPosition(int position) {
            int offsetPosition;
            ArrayList<ViewHolder> arrayList = this.mChangedScrap;
            if (arrayList != null) {
                int size = arrayList.size();
                int changedScrapSize = size;
                if (size != 0) {
                    int i = 0;
                    while (i < changedScrapSize) {
                        ViewHolder holder = this.mChangedScrap.get(i);
                        if (holder.wasReturnedFromScrap() || holder.getLayoutPosition() != position) {
                            i++;
                        } else {
                            holder.addFlags(32);
                            return holder;
                        }
                    }
                    if (RecyclerView.this.mAdapter.hasStableIds() && (offsetPosition = RecyclerView.this.mAdapterHelper.findPositionOffset(position)) > 0 && offsetPosition < RecyclerView.this.mAdapter.getItemCount()) {
                        long id = RecyclerView.this.mAdapter.getItemId(offsetPosition);
                        int i2 = 0;
                        while (i2 < changedScrapSize) {
                            ViewHolder holder2 = this.mChangedScrap.get(i2);
                            if (holder2.wasReturnedFromScrap() || holder2.getItemId() != id) {
                                i2++;
                            } else {
                                holder2.addFlags(32);
                                return holder2;
                            }
                        }
                    }
                    return null;
                }
            }
            return null;
        }

        /* access modifiers changed from: package-private */
        public ViewHolder getScrapOrHiddenOrCachedHolderForPosition(int position, boolean dryRun) {
            View view;
            int scrapCount = this.mAttachedScrap.size();
            int i = 0;
            while (i < scrapCount) {
                ViewHolder holder = this.mAttachedScrap.get(i);
                if (holder.wasReturnedFromScrap() || holder.getLayoutPosition() != position || holder.isInvalid() || (!RecyclerView.this.mState.mInPreLayout && holder.isRemoved())) {
                    i++;
                } else {
                    holder.addFlags(32);
                    return holder;
                }
            }
            if (dryRun || (view = RecyclerView.this.mChildHelper.findHiddenNonRemovedView(position)) == null) {
                int cacheSize = this.mCachedViews.size();
                int i2 = 0;
                while (i2 < cacheSize) {
                    ViewHolder holder2 = this.mCachedViews.get(i2);
                    if (holder2.isInvalid() || holder2.getLayoutPosition() != position || holder2.isAttachedToTransitionOverlay()) {
                        i2++;
                    } else {
                        if (!dryRun) {
                            this.mCachedViews.remove(i2);
                        }
                        return holder2;
                    }
                }
                return null;
            }
            ViewHolder vh = RecyclerView.getChildViewHolderInt(view);
            RecyclerView.this.mChildHelper.unhide(view);
            int layoutIndex = RecyclerView.this.mChildHelper.indexOfChild(view);
            if (layoutIndex != -1) {
                RecyclerView.this.mChildHelper.detachViewFromParent(layoutIndex);
                scrapView(view);
                vh.addFlags(8224);
                return vh;
            }
            throw new IllegalStateException("layout index should not be -1 after unhiding a view:" + vh + RecyclerView.this.exceptionLabel());
        }

        /* access modifiers changed from: package-private */
        public ViewHolder getScrapOrCachedViewForId(long id, int type, boolean dryRun) {
            for (int i = this.mAttachedScrap.size() - 1; i >= 0; i--) {
                ViewHolder holder = this.mAttachedScrap.get(i);
                if (holder.getItemId() == id && !holder.wasReturnedFromScrap()) {
                    if (type == holder.getItemViewType()) {
                        holder.addFlags(32);
                        if (holder.isRemoved() && !RecyclerView.this.mState.isPreLayout()) {
                            holder.setFlags(2, 14);
                        }
                        return holder;
                    } else if (!dryRun) {
                        this.mAttachedScrap.remove(i);
                        RecyclerView.this.removeDetachedView(holder.itemView, false);
                        quickRecycleScrapView(holder.itemView);
                    }
                }
            }
            for (int i2 = this.mCachedViews.size() - 1; i2 >= 0; i2--) {
                ViewHolder holder2 = this.mCachedViews.get(i2);
                if (holder2.getItemId() == id && !holder2.isAttachedToTransitionOverlay()) {
                    if (type == holder2.getItemViewType()) {
                        if (!dryRun) {
                            this.mCachedViews.remove(i2);
                        }
                        return holder2;
                    } else if (!dryRun) {
                        recycleCachedViewAt(i2);
                        return null;
                    }
                }
            }
            return null;
        }

        /* access modifiers changed from: package-private */
        public void dispatchViewRecycled(ViewHolder holder) {
            if (RecyclerView.this.mRecyclerListener != null) {
                RecyclerView.this.mRecyclerListener.onViewRecycled(holder);
            }
            if (RecyclerView.this.mAdapter != null) {
                RecyclerView.this.mAdapter.onViewRecycled(holder);
            }
            if (RecyclerView.this.mState != null) {
                RecyclerView.this.mViewInfoStore.removeViewHolder(holder);
            }
        }

        /* access modifiers changed from: package-private */
        public void onAdapterChanged(Adapter oldAdapter, Adapter newAdapter, boolean compatibleWithPrevious) {
            clear();
            getRecycledViewPool().onAdapterChanged(oldAdapter, newAdapter, compatibleWithPrevious);
        }

        /* access modifiers changed from: package-private */
        public void offsetPositionRecordsForMove(int from, int to) {
            int inBetweenOffset;
            int end;
            int start;
            if (from < to) {
                start = from;
                end = to;
                inBetweenOffset = -1;
            } else {
                start = to;
                end = from;
                inBetweenOffset = 1;
            }
            int cachedCount = this.mCachedViews.size();
            for (int i = 0; i < cachedCount; i++) {
                ViewHolder holder = this.mCachedViews.get(i);
                if (holder != null && holder.mPosition >= start && holder.mPosition <= end) {
                    if (holder.mPosition == from) {
                        holder.offsetPosition(to - from, false);
                    } else {
                        holder.offsetPosition(inBetweenOffset, false);
                    }
                }
            }
        }

        /* access modifiers changed from: package-private */
        public void offsetPositionRecordsForInsert(int insertedAt, int count) {
            int cachedCount = this.mCachedViews.size();
            for (int i = 0; i < cachedCount; i++) {
                ViewHolder holder = this.mCachedViews.get(i);
                if (holder != null && holder.mPosition >= insertedAt) {
                    holder.offsetPosition(count, true);
                }
            }
        }

        /* access modifiers changed from: package-private */
        public void offsetPositionRecordsForRemove(int removedFrom, int count, boolean applyToPreLayout) {
            int removedEnd = removedFrom + count;
            for (int i = this.mCachedViews.size() - 1; i >= 0; i--) {
                ViewHolder holder = this.mCachedViews.get(i);
                if (holder != null) {
                    if (holder.mPosition >= removedEnd) {
                        holder.offsetPosition(-count, applyToPreLayout);
                    } else if (holder.mPosition >= removedFrom) {
                        holder.addFlags(8);
                        recycleCachedViewAt(i);
                    }
                }
            }
        }

        /* access modifiers changed from: package-private */
        public void setViewCacheExtension(ViewCacheExtension extension) {
            this.mViewCacheExtension = extension;
        }

        /* access modifiers changed from: package-private */
        public void setRecycledViewPool(RecycledViewPool pool) {
            RecycledViewPool recycledViewPool = this.mRecyclerPool;
            if (recycledViewPool != null) {
                recycledViewPool.detach();
            }
            this.mRecyclerPool = pool;
            if (pool != null && RecyclerView.this.getAdapter() != null) {
                this.mRecyclerPool.attach();
            }
        }

        /* access modifiers changed from: package-private */
        public RecycledViewPool getRecycledViewPool() {
            if (this.mRecyclerPool == null) {
                this.mRecyclerPool = new RecycledViewPool();
            }
            return this.mRecyclerPool;
        }

        /* access modifiers changed from: package-private */
        public void viewRangeUpdate(int positionStart, int itemCount) {
            int pos;
            int positionEnd = positionStart + itemCount;
            for (int i = this.mCachedViews.size() - 1; i >= 0; i--) {
                ViewHolder holder = this.mCachedViews.get(i);
                if (holder != null && (pos = holder.mPosition) >= positionStart && pos < positionEnd) {
                    holder.addFlags(2);
                    recycleCachedViewAt(i);
                }
            }
        }

        /* access modifiers changed from: package-private */
        public void markKnownViewsInvalid() {
            int cachedCount = this.mCachedViews.size();
            for (int i = 0; i < cachedCount; i++) {
                ViewHolder holder = this.mCachedViews.get(i);
                if (holder != null) {
                    holder.addFlags(6);
                    holder.addChangePayload((Object) null);
                }
            }
            if (RecyclerView.this.mAdapter == null || !RecyclerView.this.mAdapter.hasStableIds()) {
                recycleAndClearCachedViews();
            }
        }

        /* access modifiers changed from: package-private */
        public void clearOldPositions() {
            int cachedCount = this.mCachedViews.size();
            for (int i = 0; i < cachedCount; i++) {
                this.mCachedViews.get(i).clearOldPosition();
            }
            int scrapCount = this.mAttachedScrap.size();
            for (int i2 = 0; i2 < scrapCount; i2++) {
                this.mAttachedScrap.get(i2).clearOldPosition();
            }
            ArrayList<ViewHolder> arrayList = this.mChangedScrap;
            if (arrayList != null) {
                int changedScrapCount = arrayList.size();
                for (int i3 = 0; i3 < changedScrapCount; i3++) {
                    this.mChangedScrap.get(i3).clearOldPosition();
                }
            }
        }

        /* access modifiers changed from: package-private */
        public void markItemDecorInsetsDirty() {
            int cachedCount = this.mCachedViews.size();
            for (int i = 0; i < cachedCount; i++) {
                LayoutParams layoutParams = (LayoutParams) this.mCachedViews.get(i).itemView.getLayoutParams();
                if (layoutParams != null) {
                    layoutParams.mInsetsDirty = true;
                }
            }
        }
    }

    public static abstract class Adapter<VH extends ViewHolder> {
        private boolean mHasStableIds = false;
        private final AdapterDataObservable mObservable = new AdapterDataObservable();

        public abstract int getItemCount();

        public abstract void onBindViewHolder(VH vh, int i);

        public abstract VH onCreateViewHolder(ViewGroup viewGroup, int i);

        public void onBindViewHolder(VH holder, int position, List<Object> list) {
            onBindViewHolder(holder, position);
        }

        public final VH createViewHolder(ViewGroup parent, int viewType) {
            try {
                TraceCompat.beginSection(RecyclerView.TRACE_CREATE_VIEW_TAG);
                VH holder = onCreateViewHolder(parent, viewType);
                if (holder.itemView.getParent() == null) {
                    holder.mItemViewType = viewType;
                    return holder;
                }
                throw new IllegalStateException("ViewHolder views must not be attached when created. Ensure that you are not passing 'true' to the attachToRoot parameter of LayoutInflater.inflate(..., boolean attachToRoot)");
            } finally {
                TraceCompat.endSection();
            }
        }

        public final void bindViewHolder(VH holder, int position) {
            holder.mPosition = position;
            if (hasStableIds()) {
                holder.mItemId = getItemId(position);
            }
            holder.setFlags(1, TIFFConstants.TIFFTAG_JPEGQTABLES);
            TraceCompat.beginSection(RecyclerView.TRACE_BIND_VIEW_TAG);
            onBindViewHolder(holder, position, holder.getUnmodifiedPayloads());
            holder.clearPayload();
            ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
            if (layoutParams instanceof LayoutParams) {
                ((LayoutParams) layoutParams).mInsetsDirty = true;
            }
            TraceCompat.endSection();
        }

        public int getItemViewType(int position) {
            return 0;
        }

        public void setHasStableIds(boolean hasStableIds) {
            if (!hasObservers()) {
                this.mHasStableIds = hasStableIds;
                return;
            }
            throw new IllegalStateException("Cannot change whether this adapter has stable IDs while the adapter has registered observers.");
        }

        public long getItemId(int position) {
            return -1;
        }

        public final boolean hasStableIds() {
            return this.mHasStableIds;
        }

        public void onViewRecycled(VH vh) {
        }

        public boolean onFailedToRecycleView(VH vh) {
            return false;
        }

        public void onViewAttachedToWindow(VH vh) {
        }

        public void onViewDetachedFromWindow(VH vh) {
        }

        public final boolean hasObservers() {
            return this.mObservable.hasObservers();
        }

        public void registerAdapterDataObserver(AdapterDataObserver observer) {
            this.mObservable.registerObserver(observer);
        }

        public void unregisterAdapterDataObserver(AdapterDataObserver observer) {
            this.mObservable.unregisterObserver(observer);
        }

        public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        }

        public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        }

        public final void notifyDataSetChanged() {
            this.mObservable.notifyChanged();
        }

        public final void notifyItemChanged(int position) {
            this.mObservable.notifyItemRangeChanged(position, 1);
        }

        public final void notifyItemChanged(int position, Object payload) {
            this.mObservable.notifyItemRangeChanged(position, 1, payload);
        }

        public final void notifyItemRangeChanged(int positionStart, int itemCount) {
            this.mObservable.notifyItemRangeChanged(positionStart, itemCount);
        }

        public final void notifyItemRangeChanged(int positionStart, int itemCount, Object payload) {
            this.mObservable.notifyItemRangeChanged(positionStart, itemCount, payload);
        }

        public final void notifyItemInserted(int position) {
            this.mObservable.notifyItemRangeInserted(position, 1);
        }

        public final void notifyItemMoved(int fromPosition, int toPosition) {
            this.mObservable.notifyItemMoved(fromPosition, toPosition);
        }

        public final void notifyItemRangeInserted(int positionStart, int itemCount) {
            this.mObservable.notifyItemRangeInserted(positionStart, itemCount);
        }

        public final void notifyItemRemoved(int position) {
            this.mObservable.notifyItemRangeRemoved(position, 1);
        }

        public final void notifyItemRangeRemoved(int positionStart, int itemCount) {
            this.mObservable.notifyItemRangeRemoved(positionStart, itemCount);
        }
    }

    /* access modifiers changed from: package-private */
    public void dispatchChildDetached(View child) {
        ViewHolder viewHolder = getChildViewHolderInt(child);
        onChildDetachedFromWindow(child);
        Adapter adapter = this.mAdapter;
        if (!(adapter == null || viewHolder == null)) {
            adapter.onViewDetachedFromWindow(viewHolder);
        }
        List<OnChildAttachStateChangeListener> list = this.mOnChildAttachStateListeners;
        if (list != null) {
            for (int i = list.size() - 1; i >= 0; i--) {
                this.mOnChildAttachStateListeners.get(i).onChildViewDetachedFromWindow(child);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void dispatchChildAttached(View child) {
        ViewHolder viewHolder = getChildViewHolderInt(child);
        onChildAttachedToWindow(child);
        Adapter adapter = this.mAdapter;
        if (!(adapter == null || viewHolder == null)) {
            adapter.onViewAttachedToWindow(viewHolder);
        }
        List<OnChildAttachStateChangeListener> list = this.mOnChildAttachStateListeners;
        if (list != null) {
            for (int i = list.size() - 1; i >= 0; i--) {
                this.mOnChildAttachStateListeners.get(i).onChildViewAttachedToWindow(child);
            }
        }
    }

    public static abstract class LayoutManager {
        boolean mAutoMeasure = false;
        ChildHelper mChildHelper;
        private int mHeight;
        private int mHeightMode;
        ViewBoundsCheck mHorizontalBoundCheck;
        private final ViewBoundsCheck.Callback mHorizontalBoundCheckCallback;
        boolean mIsAttachedToWindow = false;
        private boolean mItemPrefetchEnabled = true;
        private boolean mMeasurementCacheEnabled = true;
        int mPrefetchMaxCountObserved;
        boolean mPrefetchMaxObservedInInitialPrefetch;
        RecyclerView mRecyclerView;
        boolean mRequestedSimpleAnimations = false;
        SmoothScroller mSmoothScroller;
        ViewBoundsCheck mVerticalBoundCheck;
        private final ViewBoundsCheck.Callback mVerticalBoundCheckCallback;
        private int mWidth;
        private int mWidthMode;

        public interface LayoutPrefetchRegistry {
            void addPosition(int i, int i2);
        }

        public static class Properties {
            public int orientation;
            public boolean reverseLayout;
            public int spanCount;
            public boolean stackFromEnd;
        }

        public abstract LayoutParams generateDefaultLayoutParams();

        public LayoutManager() {
            C09721 r0 = new ViewBoundsCheck.Callback() {
                public View getChildAt(int index) {
                    return LayoutManager.this.getChildAt(index);
                }

                public int getParentStart() {
                    return LayoutManager.this.getPaddingLeft();
                }

                public int getParentEnd() {
                    return LayoutManager.this.getWidth() - LayoutManager.this.getPaddingRight();
                }

                public int getChildStart(View view) {
                    return LayoutManager.this.getDecoratedLeft(view) - ((LayoutParams) view.getLayoutParams()).leftMargin;
                }

                public int getChildEnd(View view) {
                    return LayoutManager.this.getDecoratedRight(view) + ((LayoutParams) view.getLayoutParams()).rightMargin;
                }
            };
            this.mHorizontalBoundCheckCallback = r0;
            C09732 r1 = new ViewBoundsCheck.Callback() {
                public View getChildAt(int index) {
                    return LayoutManager.this.getChildAt(index);
                }

                public int getParentStart() {
                    return LayoutManager.this.getPaddingTop();
                }

                public int getParentEnd() {
                    return LayoutManager.this.getHeight() - LayoutManager.this.getPaddingBottom();
                }

                public int getChildStart(View view) {
                    return LayoutManager.this.getDecoratedTop(view) - ((LayoutParams) view.getLayoutParams()).topMargin;
                }

                public int getChildEnd(View view) {
                    return LayoutManager.this.getDecoratedBottom(view) + ((LayoutParams) view.getLayoutParams()).bottomMargin;
                }
            };
            this.mVerticalBoundCheckCallback = r1;
            this.mHorizontalBoundCheck = new ViewBoundsCheck(r0);
            this.mVerticalBoundCheck = new ViewBoundsCheck(r1);
        }

        /* access modifiers changed from: package-private */
        public void setRecyclerView(RecyclerView recyclerView) {
            if (recyclerView == null) {
                this.mRecyclerView = null;
                this.mChildHelper = null;
                this.mWidth = 0;
                this.mHeight = 0;
            } else {
                this.mRecyclerView = recyclerView;
                this.mChildHelper = recyclerView.mChildHelper;
                this.mWidth = recyclerView.getWidth();
                this.mHeight = recyclerView.getHeight();
            }
            this.mWidthMode = 1073741824;
            this.mHeightMode = 1073741824;
        }

        /* access modifiers changed from: package-private */
        public void setMeasureSpecs(int wSpec, int hSpec) {
            this.mWidth = View.MeasureSpec.getSize(wSpec);
            int mode = View.MeasureSpec.getMode(wSpec);
            this.mWidthMode = mode;
            if (mode == 0 && !RecyclerView.ALLOW_SIZE_IN_UNSPECIFIED_SPEC) {
                this.mWidth = 0;
            }
            this.mHeight = View.MeasureSpec.getSize(hSpec);
            int mode2 = View.MeasureSpec.getMode(hSpec);
            this.mHeightMode = mode2;
            if (mode2 == 0 && !RecyclerView.ALLOW_SIZE_IN_UNSPECIFIED_SPEC) {
                this.mHeight = 0;
            }
        }

        /* access modifiers changed from: package-private */
        public void setMeasuredDimensionFromChildren(int widthSpec, int heightSpec) {
            int count = getChildCount();
            if (count == 0) {
                this.mRecyclerView.defaultOnMeasure(widthSpec, heightSpec);
                return;
            }
            int minX = Integer.MAX_VALUE;
            int minY = Integer.MAX_VALUE;
            int maxX = Integer.MIN_VALUE;
            int maxY = Integer.MIN_VALUE;
            for (int i = 0; i < count; i++) {
                View child = getChildAt(i);
                Rect bounds = this.mRecyclerView.mTempRect;
                getDecoratedBoundsWithMargins(child, bounds);
                if (bounds.left < minX) {
                    minX = bounds.left;
                }
                if (bounds.right > maxX) {
                    maxX = bounds.right;
                }
                if (bounds.top < minY) {
                    minY = bounds.top;
                }
                if (bounds.bottom > maxY) {
                    maxY = bounds.bottom;
                }
            }
            this.mRecyclerView.mTempRect.set(minX, minY, maxX, maxY);
            setMeasuredDimension(this.mRecyclerView.mTempRect, widthSpec, heightSpec);
        }

        public void setMeasuredDimension(Rect childrenBounds, int wSpec, int hSpec) {
            setMeasuredDimension(chooseSize(wSpec, childrenBounds.width() + getPaddingLeft() + getPaddingRight(), getMinimumWidth()), chooseSize(hSpec, childrenBounds.height() + getPaddingTop() + getPaddingBottom(), getMinimumHeight()));
        }

        public void requestLayout() {
            RecyclerView recyclerView = this.mRecyclerView;
            if (recyclerView != null) {
                recyclerView.requestLayout();
            }
        }

        public void assertInLayoutOrScroll(String message) {
            RecyclerView recyclerView = this.mRecyclerView;
            if (recyclerView != null) {
                recyclerView.assertInLayoutOrScroll(message);
            }
        }

        public static int chooseSize(int spec, int desired, int min) {
            int mode = View.MeasureSpec.getMode(spec);
            int size = View.MeasureSpec.getSize(spec);
            switch (mode) {
                case Integer.MIN_VALUE:
                    return Math.min(size, Math.max(desired, min));
                case 1073741824:
                    return size;
                default:
                    return Math.max(desired, min);
            }
        }

        public void assertNotInLayoutOrScroll(String message) {
            RecyclerView recyclerView = this.mRecyclerView;
            if (recyclerView != null) {
                recyclerView.assertNotInLayoutOrScroll(message);
            }
        }

        @Deprecated
        public void setAutoMeasureEnabled(boolean enabled) {
            this.mAutoMeasure = enabled;
        }

        public boolean isAutoMeasureEnabled() {
            return this.mAutoMeasure;
        }

        public boolean supportsPredictiveItemAnimations() {
            return false;
        }

        public final void setItemPrefetchEnabled(boolean enabled) {
            if (enabled != this.mItemPrefetchEnabled) {
                this.mItemPrefetchEnabled = enabled;
                this.mPrefetchMaxCountObserved = 0;
                RecyclerView recyclerView = this.mRecyclerView;
                if (recyclerView != null) {
                    recyclerView.mRecycler.updateViewCacheSize();
                }
            }
        }

        public final boolean isItemPrefetchEnabled() {
            return this.mItemPrefetchEnabled;
        }

        public void collectAdjacentPrefetchPositions(int dx, int dy, State state, LayoutPrefetchRegistry layoutPrefetchRegistry) {
        }

        public void collectInitialPrefetchPositions(int adapterItemCount, LayoutPrefetchRegistry layoutPrefetchRegistry) {
        }

        /* access modifiers changed from: package-private */
        public void dispatchAttachedToWindow(RecyclerView view) {
            this.mIsAttachedToWindow = true;
            onAttachedToWindow(view);
        }

        /* access modifiers changed from: package-private */
        public void dispatchDetachedFromWindow(RecyclerView view, Recycler recycler) {
            this.mIsAttachedToWindow = false;
            onDetachedFromWindow(view, recycler);
        }

        public boolean isAttachedToWindow() {
            return this.mIsAttachedToWindow;
        }

        public void postOnAnimation(Runnable action) {
            RecyclerView recyclerView = this.mRecyclerView;
            if (recyclerView != null) {
                ViewCompat.postOnAnimation(recyclerView, action);
            }
        }

        public boolean removeCallbacks(Runnable action) {
            RecyclerView recyclerView = this.mRecyclerView;
            if (recyclerView != null) {
                return recyclerView.removeCallbacks(action);
            }
            return false;
        }

        public void onAttachedToWindow(RecyclerView view) {
        }

        @Deprecated
        public void onDetachedFromWindow(RecyclerView view) {
        }

        public void onDetachedFromWindow(RecyclerView view, Recycler recycler) {
            onDetachedFromWindow(view);
        }

        public boolean getClipToPadding() {
            RecyclerView recyclerView = this.mRecyclerView;
            return recyclerView != null && recyclerView.mClipToPadding;
        }

        public void onLayoutChildren(Recycler recycler, State state) {
            Log.e(RecyclerView.TAG, "You must override onLayoutChildren(Recycler recycler, State state) ");
        }

        public void onLayoutCompleted(State state) {
        }

        public boolean checkLayoutParams(LayoutParams lp) {
            return lp != null;
        }

        public LayoutParams generateLayoutParams(ViewGroup.LayoutParams lp) {
            if (lp instanceof LayoutParams) {
                return new LayoutParams((LayoutParams) lp);
            }
            if (lp instanceof ViewGroup.MarginLayoutParams) {
                return new LayoutParams((ViewGroup.MarginLayoutParams) lp);
            }
            return new LayoutParams(lp);
        }

        public LayoutParams generateLayoutParams(Context c, AttributeSet attrs) {
            return new LayoutParams(c, attrs);
        }

        public int scrollHorizontallyBy(int dx, Recycler recycler, State state) {
            return 0;
        }

        public int scrollVerticallyBy(int dy, Recycler recycler, State state) {
            return 0;
        }

        public boolean canScrollHorizontally() {
            return false;
        }

        public boolean canScrollVertically() {
            return false;
        }

        public void scrollToPosition(int position) {
        }

        public void smoothScrollToPosition(RecyclerView recyclerView, State state, int position) {
            Log.e(RecyclerView.TAG, "You must override smoothScrollToPosition to support smooth scrolling");
        }

        public void startSmoothScroll(SmoothScroller smoothScroller) {
            SmoothScroller smoothScroller2 = this.mSmoothScroller;
            if (!(smoothScroller2 == null || smoothScroller == smoothScroller2 || !smoothScroller2.isRunning())) {
                this.mSmoothScroller.stop();
            }
            this.mSmoothScroller = smoothScroller;
            smoothScroller.start(this.mRecyclerView, this);
        }

        public boolean isSmoothScrolling() {
            SmoothScroller smoothScroller = this.mSmoothScroller;
            return smoothScroller != null && smoothScroller.isRunning();
        }

        public int getLayoutDirection() {
            return ViewCompat.getLayoutDirection(this.mRecyclerView);
        }

        public void endAnimation(View view) {
            if (this.mRecyclerView.mItemAnimator != null) {
                this.mRecyclerView.mItemAnimator.endAnimation(RecyclerView.getChildViewHolderInt(view));
            }
        }

        public void addDisappearingView(View child) {
            addDisappearingView(child, -1);
        }

        public void addDisappearingView(View child, int index) {
            addViewInt(child, index, true);
        }

        public void addView(View child) {
            addView(child, -1);
        }

        public void addView(View child, int index) {
            addViewInt(child, index, false);
        }

        private void addViewInt(View child, int index, boolean disappearing) {
            ViewHolder holder = RecyclerView.getChildViewHolderInt(child);
            if (disappearing || holder.isRemoved()) {
                this.mRecyclerView.mViewInfoStore.addToDisappearedInLayout(holder);
            } else {
                this.mRecyclerView.mViewInfoStore.removeFromDisappearedInLayout(holder);
            }
            LayoutParams lp = (LayoutParams) child.getLayoutParams();
            if (holder.wasReturnedFromScrap() || holder.isScrap()) {
                if (holder.isScrap()) {
                    holder.unScrap();
                } else {
                    holder.clearReturnedFromScrapFlag();
                }
                this.mChildHelper.attachViewToParent(child, index, child.getLayoutParams(), false);
            } else if (child.getParent() == this.mRecyclerView) {
                int currentIndex = this.mChildHelper.indexOfChild(child);
                if (index == -1) {
                    index = this.mChildHelper.getChildCount();
                }
                if (currentIndex == -1) {
                    throw new IllegalStateException("Added View has RecyclerView as parent but view is not a real child. Unfiltered index:" + this.mRecyclerView.indexOfChild(child) + this.mRecyclerView.exceptionLabel());
                } else if (currentIndex != index) {
                    this.mRecyclerView.mLayout.moveView(currentIndex, index);
                }
            } else {
                this.mChildHelper.addView(child, index, false);
                lp.mInsetsDirty = true;
                SmoothScroller smoothScroller = this.mSmoothScroller;
                if (smoothScroller != null && smoothScroller.isRunning()) {
                    this.mSmoothScroller.onChildAttachedToWindow(child);
                }
            }
            if (lp.mPendingInvalidate) {
                holder.itemView.invalidate();
                lp.mPendingInvalidate = false;
            }
        }

        public void removeView(View child) {
            this.mChildHelper.removeView(child);
        }

        public void removeViewAt(int index) {
            if (getChildAt(index) != null) {
                this.mChildHelper.removeViewAt(index);
            }
        }

        public void removeAllViews() {
            for (int i = getChildCount() - 1; i >= 0; i--) {
                this.mChildHelper.removeViewAt(i);
            }
        }

        public int getBaseline() {
            return -1;
        }

        public int getPosition(View view) {
            return ((LayoutParams) view.getLayoutParams()).getViewLayoutPosition();
        }

        public int getItemViewType(View view) {
            return RecyclerView.getChildViewHolderInt(view).getItemViewType();
        }

        public View findContainingItemView(View view) {
            View found;
            RecyclerView recyclerView = this.mRecyclerView;
            if (recyclerView == null || (found = recyclerView.findContainingItemView(view)) == null || this.mChildHelper.isHidden(found)) {
                return null;
            }
            return found;
        }

        public View findViewByPosition(int position) {
            int childCount = getChildCount();
            for (int i = 0; i < childCount; i++) {
                View child = getChildAt(i);
                ViewHolder vh = RecyclerView.getChildViewHolderInt(child);
                if (vh != null && vh.getLayoutPosition() == position && !vh.shouldIgnore() && (this.mRecyclerView.mState.isPreLayout() || !vh.isRemoved())) {
                    return child;
                }
            }
            return null;
        }

        public void detachView(View child) {
            int ind = this.mChildHelper.indexOfChild(child);
            if (ind >= 0) {
                detachViewInternal(ind, child);
            }
        }

        public void detachViewAt(int index) {
            detachViewInternal(index, getChildAt(index));
        }

        private void detachViewInternal(int index, View view) {
            this.mChildHelper.detachViewFromParent(index);
        }

        public void attachView(View child, int index, LayoutParams lp) {
            ViewHolder vh = RecyclerView.getChildViewHolderInt(child);
            if (vh.isRemoved()) {
                this.mRecyclerView.mViewInfoStore.addToDisappearedInLayout(vh);
            } else {
                this.mRecyclerView.mViewInfoStore.removeFromDisappearedInLayout(vh);
            }
            this.mChildHelper.attachViewToParent(child, index, lp, vh.isRemoved());
        }

        public void attachView(View child, int index) {
            attachView(child, index, (LayoutParams) child.getLayoutParams());
        }

        public void attachView(View child) {
            attachView(child, -1);
        }

        public void removeDetachedView(View child) {
            this.mRecyclerView.removeDetachedView(child, false);
        }

        public void moveView(int fromIndex, int toIndex) {
            View view = getChildAt(fromIndex);
            if (view != null) {
                detachViewAt(fromIndex);
                attachView(view, toIndex);
                return;
            }
            throw new IllegalArgumentException("Cannot move a child from non-existing index:" + fromIndex + this.mRecyclerView.toString());
        }

        public void detachAndScrapView(View child, Recycler recycler) {
            scrapOrRecycleView(recycler, this.mChildHelper.indexOfChild(child), child);
        }

        public void detachAndScrapViewAt(int index, Recycler recycler) {
            scrapOrRecycleView(recycler, index, getChildAt(index));
        }

        public void removeAndRecycleView(View child, Recycler recycler) {
            removeView(child);
            recycler.recycleView(child);
        }

        public void removeAndRecycleViewAt(int index, Recycler recycler) {
            View view = getChildAt(index);
            removeViewAt(index);
            recycler.recycleView(view);
        }

        public int getChildCount() {
            ChildHelper childHelper = this.mChildHelper;
            if (childHelper != null) {
                return childHelper.getChildCount();
            }
            return 0;
        }

        public View getChildAt(int index) {
            ChildHelper childHelper = this.mChildHelper;
            if (childHelper != null) {
                return childHelper.getChildAt(index);
            }
            return null;
        }

        public int getWidthMode() {
            return this.mWidthMode;
        }

        public int getHeightMode() {
            return this.mHeightMode;
        }

        public int getWidth() {
            return this.mWidth;
        }

        public int getHeight() {
            return this.mHeight;
        }

        public int getPaddingLeft() {
            RecyclerView recyclerView = this.mRecyclerView;
            if (recyclerView != null) {
                return recyclerView.getPaddingLeft();
            }
            return 0;
        }

        public int getPaddingTop() {
            RecyclerView recyclerView = this.mRecyclerView;
            if (recyclerView != null) {
                return recyclerView.getPaddingTop();
            }
            return 0;
        }

        public int getPaddingRight() {
            RecyclerView recyclerView = this.mRecyclerView;
            if (recyclerView != null) {
                return recyclerView.getPaddingRight();
            }
            return 0;
        }

        public int getPaddingBottom() {
            RecyclerView recyclerView = this.mRecyclerView;
            if (recyclerView != null) {
                return recyclerView.getPaddingBottom();
            }
            return 0;
        }

        public int getPaddingStart() {
            RecyclerView recyclerView = this.mRecyclerView;
            if (recyclerView != null) {
                return ViewCompat.getPaddingStart(recyclerView);
            }
            return 0;
        }

        public int getPaddingEnd() {
            RecyclerView recyclerView = this.mRecyclerView;
            if (recyclerView != null) {
                return ViewCompat.getPaddingEnd(recyclerView);
            }
            return 0;
        }

        public boolean isFocused() {
            RecyclerView recyclerView = this.mRecyclerView;
            return recyclerView != null && recyclerView.isFocused();
        }

        public boolean hasFocus() {
            RecyclerView recyclerView = this.mRecyclerView;
            return recyclerView != null && recyclerView.hasFocus();
        }

        public View getFocusedChild() {
            View focused;
            RecyclerView recyclerView = this.mRecyclerView;
            if (recyclerView == null || (focused = recyclerView.getFocusedChild()) == null || this.mChildHelper.isHidden(focused)) {
                return null;
            }
            return focused;
        }

        public int getItemCount() {
            RecyclerView recyclerView = this.mRecyclerView;
            Adapter a = recyclerView != null ? recyclerView.getAdapter() : null;
            if (a != null) {
                return a.getItemCount();
            }
            return 0;
        }

        public void offsetChildrenHorizontal(int dx) {
            RecyclerView recyclerView = this.mRecyclerView;
            if (recyclerView != null) {
                recyclerView.offsetChildrenHorizontal(dx);
            }
        }

        public void offsetChildrenVertical(int dy) {
            RecyclerView recyclerView = this.mRecyclerView;
            if (recyclerView != null) {
                recyclerView.offsetChildrenVertical(dy);
            }
        }

        public void ignoreView(View view) {
            ViewParent parent = view.getParent();
            RecyclerView recyclerView = this.mRecyclerView;
            if (parent != recyclerView || recyclerView.indexOfChild(view) == -1) {
                throw new IllegalArgumentException("View should be fully attached to be ignored" + this.mRecyclerView.exceptionLabel());
            }
            ViewHolder vh = RecyclerView.getChildViewHolderInt(view);
            vh.addFlags(128);
            this.mRecyclerView.mViewInfoStore.removeViewHolder(vh);
        }

        public void stopIgnoringView(View view) {
            ViewHolder vh = RecyclerView.getChildViewHolderInt(view);
            vh.stopIgnoring();
            vh.resetInternal();
            vh.addFlags(4);
        }

        public void detachAndScrapAttachedViews(Recycler recycler) {
            for (int i = getChildCount() - 1; i >= 0; i--) {
                scrapOrRecycleView(recycler, i, getChildAt(i));
            }
        }

        private void scrapOrRecycleView(Recycler recycler, int index, View view) {
            ViewHolder viewHolder = RecyclerView.getChildViewHolderInt(view);
            if (!viewHolder.shouldIgnore()) {
                if (!viewHolder.isInvalid() || viewHolder.isRemoved() || this.mRecyclerView.mAdapter.hasStableIds()) {
                    detachViewAt(index);
                    recycler.scrapView(view);
                    this.mRecyclerView.mViewInfoStore.onViewDetached(viewHolder);
                    return;
                }
                removeViewAt(index);
                recycler.recycleViewHolderInternal(viewHolder);
            }
        }

        /* access modifiers changed from: package-private */
        public void removeAndRecycleScrapInt(Recycler recycler) {
            int scrapCount = recycler.getScrapCount();
            for (int i = scrapCount - 1; i >= 0; i--) {
                View scrap = recycler.getScrapViewAt(i);
                ViewHolder vh = RecyclerView.getChildViewHolderInt(scrap);
                if (!vh.shouldIgnore()) {
                    vh.setIsRecyclable(false);
                    if (vh.isTmpDetached()) {
                        this.mRecyclerView.removeDetachedView(scrap, false);
                    }
                    if (this.mRecyclerView.mItemAnimator != null) {
                        this.mRecyclerView.mItemAnimator.endAnimation(vh);
                    }
                    vh.setIsRecyclable(true);
                    recycler.quickRecycleScrapView(scrap);
                }
            }
            recycler.clearScrap();
            if (scrapCount > 0) {
                this.mRecyclerView.invalidate();
            }
        }

        public void measureChild(View child, int widthUsed, int heightUsed) {
            LayoutParams lp = (LayoutParams) child.getLayoutParams();
            Rect insets = this.mRecyclerView.getItemDecorInsetsForChild(child);
            int widthUsed2 = widthUsed + insets.left + insets.right;
            int heightUsed2 = heightUsed + insets.top + insets.bottom;
            int widthSpec = getChildMeasureSpec(getWidth(), getWidthMode(), getPaddingLeft() + getPaddingRight() + widthUsed2, lp.width, canScrollHorizontally());
            int heightSpec = getChildMeasureSpec(getHeight(), getHeightMode(), getPaddingTop() + getPaddingBottom() + heightUsed2, lp.height, canScrollVertically());
            if (shouldMeasureChild(child, widthSpec, heightSpec, lp)) {
                child.measure(widthSpec, heightSpec);
            }
        }

        /* access modifiers changed from: package-private */
        public boolean shouldReMeasureChild(View child, int widthSpec, int heightSpec, LayoutParams lp) {
            return !this.mMeasurementCacheEnabled || !isMeasurementUpToDate(child.getMeasuredWidth(), widthSpec, lp.width) || !isMeasurementUpToDate(child.getMeasuredHeight(), heightSpec, lp.height);
        }

        /* access modifiers changed from: package-private */
        public boolean shouldMeasureChild(View child, int widthSpec, int heightSpec, LayoutParams lp) {
            return child.isLayoutRequested() || !this.mMeasurementCacheEnabled || !isMeasurementUpToDate(child.getWidth(), widthSpec, lp.width) || !isMeasurementUpToDate(child.getHeight(), heightSpec, lp.height);
        }

        public boolean isMeasurementCacheEnabled() {
            return this.mMeasurementCacheEnabled;
        }

        public void setMeasurementCacheEnabled(boolean measurementCacheEnabled) {
            this.mMeasurementCacheEnabled = measurementCacheEnabled;
        }

        private static boolean isMeasurementUpToDate(int childSize, int spec, int dimension) {
            int specMode = View.MeasureSpec.getMode(spec);
            int specSize = View.MeasureSpec.getSize(spec);
            if (dimension > 0 && childSize != dimension) {
                return false;
            }
            switch (specMode) {
                case Integer.MIN_VALUE:
                    if (specSize >= childSize) {
                        return true;
                    }
                    return false;
                case 0:
                    return true;
                case 1073741824:
                    if (specSize == childSize) {
                        return true;
                    }
                    return false;
                default:
                    return false;
            }
        }

        public void measureChildWithMargins(View child, int widthUsed, int heightUsed) {
            LayoutParams lp = (LayoutParams) child.getLayoutParams();
            Rect insets = this.mRecyclerView.getItemDecorInsetsForChild(child);
            int widthUsed2 = widthUsed + insets.left + insets.right;
            int heightUsed2 = heightUsed + insets.top + insets.bottom;
            int widthSpec = getChildMeasureSpec(getWidth(), getWidthMode(), getPaddingLeft() + getPaddingRight() + lp.leftMargin + lp.rightMargin + widthUsed2, lp.width, canScrollHorizontally());
            int heightSpec = getChildMeasureSpec(getHeight(), getHeightMode(), getPaddingTop() + getPaddingBottom() + lp.topMargin + lp.bottomMargin + heightUsed2, lp.height, canScrollVertically());
            if (shouldMeasureChild(child, widthSpec, heightSpec, lp)) {
                child.measure(widthSpec, heightSpec);
            }
        }

        @Deprecated
        public static int getChildMeasureSpec(int parentSize, int padding, int childDimension, boolean canScroll) {
            int size = Math.max(0, parentSize - padding);
            int resultSize = 0;
            int resultMode = 0;
            if (canScroll) {
                if (childDimension >= 0) {
                    resultSize = childDimension;
                    resultMode = 1073741824;
                } else {
                    resultSize = 0;
                    resultMode = 0;
                }
            } else if (childDimension >= 0) {
                resultSize = childDimension;
                resultMode = 1073741824;
            } else if (childDimension == -1) {
                resultSize = size;
                resultMode = 1073741824;
            } else if (childDimension == -2) {
                resultSize = size;
                resultMode = Integer.MIN_VALUE;
            }
            return View.MeasureSpec.makeMeasureSpec(resultSize, resultMode);
        }

        public static int getChildMeasureSpec(int parentSize, int parentMode, int padding, int childDimension, boolean canScroll) {
            int size = Math.max(0, parentSize - padding);
            int resultSize = 0;
            int resultMode = 0;
            if (canScroll) {
                if (childDimension >= 0) {
                    resultSize = childDimension;
                    resultMode = 1073741824;
                } else if (childDimension == -1) {
                    switch (parentMode) {
                        case Integer.MIN_VALUE:
                        case 1073741824:
                            resultSize = size;
                            resultMode = parentMode;
                            break;
                        case 0:
                            resultSize = 0;
                            resultMode = 0;
                            break;
                    }
                } else if (childDimension == -2) {
                    resultSize = 0;
                    resultMode = 0;
                }
            } else if (childDimension >= 0) {
                resultSize = childDimension;
                resultMode = 1073741824;
            } else if (childDimension == -1) {
                resultSize = size;
                resultMode = parentMode;
            } else if (childDimension == -2) {
                resultSize = size;
                resultMode = (parentMode == Integer.MIN_VALUE || parentMode == 1073741824) ? Integer.MIN_VALUE : 0;
            }
            return View.MeasureSpec.makeMeasureSpec(resultSize, resultMode);
        }

        public int getDecoratedMeasuredWidth(View child) {
            Rect insets = ((LayoutParams) child.getLayoutParams()).mDecorInsets;
            return child.getMeasuredWidth() + insets.left + insets.right;
        }

        public int getDecoratedMeasuredHeight(View child) {
            Rect insets = ((LayoutParams) child.getLayoutParams()).mDecorInsets;
            return child.getMeasuredHeight() + insets.top + insets.bottom;
        }

        public void layoutDecorated(View child, int left, int top, int right, int bottom) {
            Rect insets = ((LayoutParams) child.getLayoutParams()).mDecorInsets;
            child.layout(insets.left + left, insets.top + top, right - insets.right, bottom - insets.bottom);
        }

        public void layoutDecoratedWithMargins(View child, int left, int top, int right, int bottom) {
            LayoutParams lp = (LayoutParams) child.getLayoutParams();
            Rect insets = lp.mDecorInsets;
            child.layout(insets.left + left + lp.leftMargin, insets.top + top + lp.topMargin, (right - insets.right) - lp.rightMargin, (bottom - insets.bottom) - lp.bottomMargin);
        }

        public void getTransformedBoundingBox(View child, boolean includeDecorInsets, Rect out) {
            Matrix childMatrix;
            if (includeDecorInsets) {
                Rect insets = ((LayoutParams) child.getLayoutParams()).mDecorInsets;
                out.set(-insets.left, -insets.top, child.getWidth() + insets.right, child.getHeight() + insets.bottom);
            } else {
                out.set(0, 0, child.getWidth(), child.getHeight());
            }
            if (!(this.mRecyclerView == null || (childMatrix = child.getMatrix()) == null || childMatrix.isIdentity())) {
                RectF tempRectF = this.mRecyclerView.mTempRectF;
                tempRectF.set(out);
                childMatrix.mapRect(tempRectF);
                out.set((int) Math.floor((double) tempRectF.left), (int) Math.floor((double) tempRectF.top), (int) Math.ceil((double) tempRectF.right), (int) Math.ceil((double) tempRectF.bottom));
            }
            out.offset(child.getLeft(), child.getTop());
        }

        public void getDecoratedBoundsWithMargins(View view, Rect outBounds) {
            RecyclerView.getDecoratedBoundsWithMarginsInt(view, outBounds);
        }

        public int getDecoratedLeft(View child) {
            return child.getLeft() - getLeftDecorationWidth(child);
        }

        public int getDecoratedTop(View child) {
            return child.getTop() - getTopDecorationHeight(child);
        }

        public int getDecoratedRight(View child) {
            return child.getRight() + getRightDecorationWidth(child);
        }

        public int getDecoratedBottom(View child) {
            return child.getBottom() + getBottomDecorationHeight(child);
        }

        public void calculateItemDecorationsForChild(View child, Rect outRect) {
            RecyclerView recyclerView = this.mRecyclerView;
            if (recyclerView == null) {
                outRect.set(0, 0, 0, 0);
            } else {
                outRect.set(recyclerView.getItemDecorInsetsForChild(child));
            }
        }

        public int getTopDecorationHeight(View child) {
            return ((LayoutParams) child.getLayoutParams()).mDecorInsets.top;
        }

        public int getBottomDecorationHeight(View child) {
            return ((LayoutParams) child.getLayoutParams()).mDecorInsets.bottom;
        }

        public int getLeftDecorationWidth(View child) {
            return ((LayoutParams) child.getLayoutParams()).mDecorInsets.left;
        }

        public int getRightDecorationWidth(View child) {
            return ((LayoutParams) child.getLayoutParams()).mDecorInsets.right;
        }

        public View onFocusSearchFailed(View focused, int direction, Recycler recycler, State state) {
            return null;
        }

        public View onInterceptFocusSearch(View focused, int direction) {
            return null;
        }

        private int[] getChildRectangleOnScreenScrollAmount(View child, Rect rect) {
            int dx;
            int dy;
            Rect rect2 = rect;
            int[] out = new int[2];
            int parentLeft = getPaddingLeft();
            int parentTop = getPaddingTop();
            int parentRight = getWidth() - getPaddingRight();
            int parentBottom = getHeight() - getPaddingBottom();
            int childLeft = (child.getLeft() + rect2.left) - child.getScrollX();
            int childTop = (child.getTop() + rect2.top) - child.getScrollY();
            int childRight = rect.width() + childLeft;
            int offScreenLeft = Math.min(0, childLeft - parentLeft);
            int offScreenTop = Math.min(0, childTop - parentTop);
            int offScreenRight = Math.max(0, childRight - parentRight);
            int offScreenBottom = Math.max(0, (rect.height() + childTop) - parentBottom);
            if (getLayoutDirection() == 1) {
                if (offScreenRight != 0) {
                    dx = offScreenRight;
                } else {
                    dx = Math.max(offScreenLeft, childRight - parentRight);
                }
            } else if (offScreenLeft != 0) {
                dx = offScreenLeft;
            } else {
                dx = Math.min(childLeft - parentLeft, offScreenRight);
            }
            if (offScreenTop != 0) {
                dy = offScreenTop;
            } else {
                dy = Math.min(childTop - parentTop, offScreenBottom);
            }
            out[0] = dx;
            out[1] = dy;
            return out;
        }

        public boolean requestChildRectangleOnScreen(RecyclerView parent, View child, Rect rect, boolean immediate) {
            return requestChildRectangleOnScreen(parent, child, rect, immediate, false);
        }

        public boolean requestChildRectangleOnScreen(RecyclerView parent, View child, Rect rect, boolean immediate, boolean focusedChildVisible) {
            int[] scrollAmount = getChildRectangleOnScreenScrollAmount(child, rect);
            int dx = scrollAmount[0];
            int dy = scrollAmount[1];
            if ((focusedChildVisible && !isFocusedChildVisibleAfterScrolling(parent, dx, dy)) || (dx == 0 && dy == 0)) {
                return false;
            }
            if (immediate) {
                parent.scrollBy(dx, dy);
            } else {
                parent.smoothScrollBy(dx, dy);
            }
            return true;
        }

        public boolean isViewPartiallyVisible(View child, boolean completelyVisible, boolean acceptEndPointInclusion) {
            boolean isViewFullyVisible = this.mHorizontalBoundCheck.isViewWithinBoundFlags(child, 24579) && this.mVerticalBoundCheck.isViewWithinBoundFlags(child, 24579);
            if (completelyVisible) {
                return isViewFullyVisible;
            }
            if (!isViewFullyVisible) {
                return true;
            }
            return false;
        }

        private boolean isFocusedChildVisibleAfterScrolling(RecyclerView parent, int dx, int dy) {
            View focusedChild = parent.getFocusedChild();
            if (focusedChild == null) {
                return false;
            }
            int parentLeft = getPaddingLeft();
            int parentTop = getPaddingTop();
            int parentRight = getWidth() - getPaddingRight();
            int parentBottom = getHeight() - getPaddingBottom();
            Rect bounds = this.mRecyclerView.mTempRect;
            getDecoratedBoundsWithMargins(focusedChild, bounds);
            if (bounds.left - dx >= parentRight || bounds.right - dx <= parentLeft || bounds.top - dy >= parentBottom || bounds.bottom - dy <= parentTop) {
                return false;
            }
            return true;
        }

        @Deprecated
        public boolean onRequestChildFocus(RecyclerView parent, View child, View focused) {
            return isSmoothScrolling() || parent.isComputingLayout();
        }

        public boolean onRequestChildFocus(RecyclerView parent, State state, View child, View focused) {
            return onRequestChildFocus(parent, child, focused);
        }

        public void onAdapterChanged(Adapter oldAdapter, Adapter newAdapter) {
        }

        public boolean onAddFocusables(RecyclerView recyclerView, ArrayList<View> arrayList, int direction, int focusableMode) {
            return false;
        }

        public void onItemsChanged(RecyclerView recyclerView) {
        }

        public void onItemsAdded(RecyclerView recyclerView, int positionStart, int itemCount) {
        }

        public void onItemsRemoved(RecyclerView recyclerView, int positionStart, int itemCount) {
        }

        public void onItemsUpdated(RecyclerView recyclerView, int positionStart, int itemCount) {
        }

        public void onItemsUpdated(RecyclerView recyclerView, int positionStart, int itemCount, Object payload) {
            onItemsUpdated(recyclerView, positionStart, itemCount);
        }

        public void onItemsMoved(RecyclerView recyclerView, int from, int to, int itemCount) {
        }

        public int computeHorizontalScrollExtent(State state) {
            return 0;
        }

        public int computeHorizontalScrollOffset(State state) {
            return 0;
        }

        public int computeHorizontalScrollRange(State state) {
            return 0;
        }

        public int computeVerticalScrollExtent(State state) {
            return 0;
        }

        public int computeVerticalScrollOffset(State state) {
            return 0;
        }

        public int computeVerticalScrollRange(State state) {
            return 0;
        }

        public void onMeasure(Recycler recycler, State state, int widthSpec, int heightSpec) {
            this.mRecyclerView.defaultOnMeasure(widthSpec, heightSpec);
        }

        public void setMeasuredDimension(int widthSize, int heightSize) {
            this.mRecyclerView.setMeasuredDimension(widthSize, heightSize);
        }

        public int getMinimumWidth() {
            return ViewCompat.getMinimumWidth(this.mRecyclerView);
        }

        public int getMinimumHeight() {
            return ViewCompat.getMinimumHeight(this.mRecyclerView);
        }

        public Parcelable onSaveInstanceState() {
            return null;
        }

        public void onRestoreInstanceState(Parcelable state) {
        }

        /* access modifiers changed from: package-private */
        public void stopSmoothScroller() {
            SmoothScroller smoothScroller = this.mSmoothScroller;
            if (smoothScroller != null) {
                smoothScroller.stop();
            }
        }

        /* access modifiers changed from: package-private */
        public void onSmoothScrollerStopped(SmoothScroller smoothScroller) {
            if (this.mSmoothScroller == smoothScroller) {
                this.mSmoothScroller = null;
            }
        }

        public void onScrollStateChanged(int state) {
        }

        public void removeAndRecycleAllViews(Recycler recycler) {
            for (int i = getChildCount() - 1; i >= 0; i--) {
                if (!RecyclerView.getChildViewHolderInt(getChildAt(i)).shouldIgnore()) {
                    removeAndRecycleViewAt(i, recycler);
                }
            }
        }

        /* access modifiers changed from: package-private */
        public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfoCompat info) {
            onInitializeAccessibilityNodeInfo(this.mRecyclerView.mRecycler, this.mRecyclerView.mState, info);
        }

        public void onInitializeAccessibilityNodeInfo(Recycler recycler, State state, AccessibilityNodeInfoCompat info) {
            if (this.mRecyclerView.canScrollVertically(-1) || this.mRecyclerView.canScrollHorizontally(-1)) {
                info.addAction(8192);
                info.setScrollable(true);
            }
            if (this.mRecyclerView.canScrollVertically(1) || this.mRecyclerView.canScrollHorizontally(1)) {
                info.addAction(4096);
                info.setScrollable(true);
            }
            info.setCollectionInfo(AccessibilityNodeInfoCompat.CollectionInfoCompat.obtain(getRowCountForAccessibility(recycler, state), getColumnCountForAccessibility(recycler, state), isLayoutHierarchical(recycler, state), getSelectionModeForAccessibility(recycler, state)));
        }

        public void onInitializeAccessibilityEvent(AccessibilityEvent event) {
            onInitializeAccessibilityEvent(this.mRecyclerView.mRecycler, this.mRecyclerView.mState, event);
        }

        public void onInitializeAccessibilityEvent(Recycler recycler, State state, AccessibilityEvent event) {
            RecyclerView recyclerView = this.mRecyclerView;
            if (recyclerView != null && event != null) {
                boolean z = true;
                if (!recyclerView.canScrollVertically(1) && !this.mRecyclerView.canScrollVertically(-1) && !this.mRecyclerView.canScrollHorizontally(-1) && !this.mRecyclerView.canScrollHorizontally(1)) {
                    z = false;
                }
                event.setScrollable(z);
                if (this.mRecyclerView.mAdapter != null) {
                    event.setItemCount(this.mRecyclerView.mAdapter.getItemCount());
                }
            }
        }

        /* access modifiers changed from: package-private */
        public void onInitializeAccessibilityNodeInfoForItem(View host, AccessibilityNodeInfoCompat info) {
            ViewHolder vh = RecyclerView.getChildViewHolderInt(host);
            if (vh != null && !vh.isRemoved() && !this.mChildHelper.isHidden(vh.itemView)) {
                onInitializeAccessibilityNodeInfoForItem(this.mRecyclerView.mRecycler, this.mRecyclerView.mState, host, info);
            }
        }

        public void onInitializeAccessibilityNodeInfoForItem(Recycler recycler, State state, View host, AccessibilityNodeInfoCompat info) {
            info.setCollectionItemInfo(AccessibilityNodeInfoCompat.CollectionItemInfoCompat.obtain(canScrollVertically() ? getPosition(host) : 0, 1, canScrollHorizontally() ? getPosition(host) : 0, 1, false, false));
        }

        public void requestSimpleAnimationsInNextLayout() {
            this.mRequestedSimpleAnimations = true;
        }

        public int getSelectionModeForAccessibility(Recycler recycler, State state) {
            return 0;
        }

        public int getRowCountForAccessibility(Recycler recycler, State state) {
            RecyclerView recyclerView = this.mRecyclerView;
            if (recyclerView == null || recyclerView.mAdapter == null || !canScrollVertically()) {
                return 1;
            }
            return this.mRecyclerView.mAdapter.getItemCount();
        }

        public int getColumnCountForAccessibility(Recycler recycler, State state) {
            RecyclerView recyclerView = this.mRecyclerView;
            if (recyclerView == null || recyclerView.mAdapter == null || !canScrollHorizontally()) {
                return 1;
            }
            return this.mRecyclerView.mAdapter.getItemCount();
        }

        public boolean isLayoutHierarchical(Recycler recycler, State state) {
            return false;
        }

        /* access modifiers changed from: package-private */
        public boolean performAccessibilityAction(int action, Bundle args) {
            return performAccessibilityAction(this.mRecyclerView.mRecycler, this.mRecyclerView.mState, action, args);
        }

        public boolean performAccessibilityAction(Recycler recycler, State state, int action, Bundle args) {
            RecyclerView recyclerView = this.mRecyclerView;
            if (recyclerView == null) {
                return false;
            }
            int vScroll = 0;
            int hScroll = 0;
            switch (action) {
                case 4096:
                    if (recyclerView.canScrollVertically(1)) {
                        vScroll = (getHeight() - getPaddingTop()) - getPaddingBottom();
                    }
                    if (this.mRecyclerView.canScrollHorizontally(1)) {
                        hScroll = (getWidth() - getPaddingLeft()) - getPaddingRight();
                        break;
                    }
                    break;
                case 8192:
                    if (recyclerView.canScrollVertically(-1)) {
                        vScroll = -((getHeight() - getPaddingTop()) - getPaddingBottom());
                    }
                    if (this.mRecyclerView.canScrollHorizontally(-1)) {
                        hScroll = -((getWidth() - getPaddingLeft()) - getPaddingRight());
                        break;
                    }
                    break;
            }
            if (vScroll == 0 && hScroll == 0) {
                return false;
            }
            this.mRecyclerView.smoothScrollBy(hScroll, vScroll, (Interpolator) null, Integer.MIN_VALUE, true);
            return true;
        }

        /* access modifiers changed from: package-private */
        public boolean performAccessibilityActionForItem(View view, int action, Bundle args) {
            return performAccessibilityActionForItem(this.mRecyclerView.mRecycler, this.mRecyclerView.mState, view, action, args);
        }

        public boolean performAccessibilityActionForItem(Recycler recycler, State state, View view, int action, Bundle args) {
            return false;
        }

        public static Properties getProperties(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
            Properties properties = new Properties();
            TypedArray a = context.obtainStyledAttributes(attrs, C0926R.styleable.RecyclerView, defStyleAttr, defStyleRes);
            properties.orientation = a.getInt(C0926R.styleable.RecyclerView_android_orientation, 1);
            properties.spanCount = a.getInt(C0926R.styleable.RecyclerView_spanCount, 1);
            properties.reverseLayout = a.getBoolean(C0926R.styleable.RecyclerView_reverseLayout, false);
            properties.stackFromEnd = a.getBoolean(C0926R.styleable.RecyclerView_stackFromEnd, false);
            a.recycle();
            return properties;
        }

        /* access modifiers changed from: package-private */
        public void setExactMeasureSpecsFrom(RecyclerView recyclerView) {
            setMeasureSpecs(View.MeasureSpec.makeMeasureSpec(recyclerView.getWidth(), 1073741824), View.MeasureSpec.makeMeasureSpec(recyclerView.getHeight(), 1073741824));
        }

        /* access modifiers changed from: package-private */
        public boolean shouldMeasureTwice() {
            return false;
        }

        /* access modifiers changed from: package-private */
        public boolean hasFlexibleChildInBothOrientations() {
            int childCount = getChildCount();
            for (int i = 0; i < childCount; i++) {
                ViewGroup.LayoutParams lp = getChildAt(i).getLayoutParams();
                if (lp.width < 0 && lp.height < 0) {
                    return true;
                }
            }
            return false;
        }
    }

    public static abstract class ItemDecoration {
        public void onDraw(Canvas c, RecyclerView parent, State state) {
            onDraw(c, parent);
        }

        @Deprecated
        public void onDraw(Canvas c, RecyclerView parent) {
        }

        public void onDrawOver(Canvas c, RecyclerView parent, State state) {
            onDrawOver(c, parent);
        }

        @Deprecated
        public void onDrawOver(Canvas c, RecyclerView parent) {
        }

        @Deprecated
        public void getItemOffsets(Rect outRect, int itemPosition, RecyclerView parent) {
            outRect.set(0, 0, 0, 0);
        }

        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, State state) {
            getItemOffsets(outRect, ((LayoutParams) view.getLayoutParams()).getViewLayoutPosition(), parent);
        }
    }

    public static class SimpleOnItemTouchListener implements OnItemTouchListener {
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            return false;
        }

        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        }

        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
        }
    }

    public static abstract class OnScrollListener {
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        }

        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        }
    }

    public static abstract class ViewHolder {
        static final int FLAG_ADAPTER_FULLUPDATE = 1024;
        static final int FLAG_ADAPTER_POSITION_UNKNOWN = 512;
        static final int FLAG_APPEARED_IN_PRE_LAYOUT = 4096;
        static final int FLAG_BOUNCED_FROM_HIDDEN_LIST = 8192;
        static final int FLAG_BOUND = 1;
        static final int FLAG_IGNORE = 128;
        static final int FLAG_INVALID = 4;
        static final int FLAG_MOVED = 2048;
        static final int FLAG_NOT_RECYCLABLE = 16;
        static final int FLAG_REMOVED = 8;
        static final int FLAG_RETURNED_FROM_SCRAP = 32;
        static final int FLAG_TMP_DETACHED = 256;
        static final int FLAG_UPDATE = 2;
        private static final List<Object> FULLUPDATE_PAYLOADS = Collections.emptyList();
        static final int PENDING_ACCESSIBILITY_STATE_NOT_SET = -1;
        public final View itemView;
        int mFlags;
        boolean mInChangeScrap = false;
        private int mIsRecyclableCount = 0;
        long mItemId = -1;
        int mItemViewType = -1;
        WeakReference<RecyclerView> mNestedRecyclerView;
        int mOldPosition = -1;
        RecyclerView mOwnerRecyclerView;
        List<Object> mPayloads = null;
        int mPendingAccessibilityState = -1;
        int mPosition = -1;
        int mPreLayoutPosition = -1;
        Recycler mScrapContainer = null;
        ViewHolder mShadowedHolder = null;
        ViewHolder mShadowingHolder = null;
        List<Object> mUnmodifiedPayloads = null;
        private int mWasImportantForAccessibilityBeforeHidden = 0;

        public ViewHolder(View itemView2) {
            if (itemView2 != null) {
                this.itemView = itemView2;
                return;
            }
            throw new IllegalArgumentException("itemView may not be null");
        }

        /* access modifiers changed from: package-private */
        public void flagRemovedAndOffsetPosition(int mNewPosition, int offset, boolean applyToPreLayout) {
            addFlags(8);
            offsetPosition(offset, applyToPreLayout);
            this.mPosition = mNewPosition;
        }

        /* access modifiers changed from: package-private */
        public void offsetPosition(int offset, boolean applyToPreLayout) {
            if (this.mOldPosition == -1) {
                this.mOldPosition = this.mPosition;
            }
            if (this.mPreLayoutPosition == -1) {
                this.mPreLayoutPosition = this.mPosition;
            }
            if (applyToPreLayout) {
                this.mPreLayoutPosition += offset;
            }
            this.mPosition += offset;
            if (this.itemView.getLayoutParams() != null) {
                ((LayoutParams) this.itemView.getLayoutParams()).mInsetsDirty = true;
            }
        }

        /* access modifiers changed from: package-private */
        public void clearOldPosition() {
            this.mOldPosition = -1;
            this.mPreLayoutPosition = -1;
        }

        /* access modifiers changed from: package-private */
        public void saveOldPosition() {
            if (this.mOldPosition == -1) {
                this.mOldPosition = this.mPosition;
            }
        }

        /* access modifiers changed from: package-private */
        public boolean shouldIgnore() {
            return (this.mFlags & 128) != 0;
        }

        @Deprecated
        public final int getPosition() {
            int i = this.mPreLayoutPosition;
            return i == -1 ? this.mPosition : i;
        }

        public final int getLayoutPosition() {
            int i = this.mPreLayoutPosition;
            return i == -1 ? this.mPosition : i;
        }

        public final int getAdapterPosition() {
            RecyclerView recyclerView = this.mOwnerRecyclerView;
            if (recyclerView == null) {
                return -1;
            }
            return recyclerView.getAdapterPositionFor(this);
        }

        public final int getOldPosition() {
            return this.mOldPosition;
        }

        public final long getItemId() {
            return this.mItemId;
        }

        public final int getItemViewType() {
            return this.mItemViewType;
        }

        /* access modifiers changed from: package-private */
        public boolean isScrap() {
            return this.mScrapContainer != null;
        }

        /* access modifiers changed from: package-private */
        public void unScrap() {
            this.mScrapContainer.unscrapView(this);
        }

        /* access modifiers changed from: package-private */
        public boolean wasReturnedFromScrap() {
            return (this.mFlags & 32) != 0;
        }

        /* access modifiers changed from: package-private */
        public void clearReturnedFromScrapFlag() {
            this.mFlags &= -33;
        }

        /* access modifiers changed from: package-private */
        public void clearTmpDetachFlag() {
            this.mFlags &= -257;
        }

        /* access modifiers changed from: package-private */
        public void stopIgnoring() {
            this.mFlags &= -129;
        }

        /* access modifiers changed from: package-private */
        public void setScrapContainer(Recycler recycler, boolean isChangeScrap) {
            this.mScrapContainer = recycler;
            this.mInChangeScrap = isChangeScrap;
        }

        /* access modifiers changed from: package-private */
        public boolean isInvalid() {
            return (this.mFlags & 4) != 0;
        }

        /* access modifiers changed from: package-private */
        public boolean needsUpdate() {
            return (this.mFlags & 2) != 0;
        }

        /* access modifiers changed from: package-private */
        public boolean isBound() {
            return (this.mFlags & 1) != 0;
        }

        /* access modifiers changed from: package-private */
        public boolean isRemoved() {
            return (this.mFlags & 8) != 0;
        }

        /* access modifiers changed from: package-private */
        public boolean hasAnyOfTheFlags(int flags) {
            return (this.mFlags & flags) != 0;
        }

        /* access modifiers changed from: package-private */
        public boolean isTmpDetached() {
            return (this.mFlags & 256) != 0;
        }

        /* access modifiers changed from: package-private */
        public boolean isAttachedToTransitionOverlay() {
            return (this.itemView.getParent() == null || this.itemView.getParent() == this.mOwnerRecyclerView) ? false : true;
        }

        /* access modifiers changed from: package-private */
        public boolean isAdapterPositionUnknown() {
            return (this.mFlags & 512) != 0 || isInvalid();
        }

        /* access modifiers changed from: package-private */
        public void setFlags(int flags, int mask) {
            this.mFlags = (this.mFlags & (mask ^ -1)) | (flags & mask);
        }

        /* access modifiers changed from: package-private */
        public void addFlags(int flags) {
            this.mFlags |= flags;
        }

        /* access modifiers changed from: package-private */
        public void addChangePayload(Object payload) {
            if (payload == null) {
                addFlags(1024);
            } else if ((1024 & this.mFlags) == 0) {
                createPayloadsIfNeeded();
                this.mPayloads.add(payload);
            }
        }

        private void createPayloadsIfNeeded() {
            if (this.mPayloads == null) {
                ArrayList arrayList = new ArrayList();
                this.mPayloads = arrayList;
                this.mUnmodifiedPayloads = Collections.unmodifiableList(arrayList);
            }
        }

        /* access modifiers changed from: package-private */
        public void clearPayload() {
            List<Object> list = this.mPayloads;
            if (list != null) {
                list.clear();
            }
            this.mFlags &= -1025;
        }

        /* access modifiers changed from: package-private */
        public List<Object> getUnmodifiedPayloads() {
            if ((this.mFlags & 1024) != 0) {
                return FULLUPDATE_PAYLOADS;
            }
            List<Object> list = this.mPayloads;
            if (list == null || list.size() == 0) {
                return FULLUPDATE_PAYLOADS;
            }
            return this.mUnmodifiedPayloads;
        }

        /* access modifiers changed from: package-private */
        public void resetInternal() {
            this.mFlags = 0;
            this.mPosition = -1;
            this.mOldPosition = -1;
            this.mItemId = -1;
            this.mPreLayoutPosition = -1;
            this.mIsRecyclableCount = 0;
            this.mShadowedHolder = null;
            this.mShadowingHolder = null;
            clearPayload();
            this.mWasImportantForAccessibilityBeforeHidden = 0;
            this.mPendingAccessibilityState = -1;
            RecyclerView.clearNestedRecyclerViewIfNotNested(this);
        }

        /* access modifiers changed from: package-private */
        public void onEnteredHiddenState(RecyclerView parent) {
            int i = this.mPendingAccessibilityState;
            if (i != -1) {
                this.mWasImportantForAccessibilityBeforeHidden = i;
            } else {
                this.mWasImportantForAccessibilityBeforeHidden = ViewCompat.getImportantForAccessibility(this.itemView);
            }
            parent.setChildImportantForAccessibilityInternal(this, 4);
        }

        /* access modifiers changed from: package-private */
        public void onLeftHiddenState(RecyclerView parent) {
            parent.setChildImportantForAccessibilityInternal(this, this.mWasImportantForAccessibilityBeforeHidden);
            this.mWasImportantForAccessibilityBeforeHidden = 0;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder((getClass().isAnonymousClass() ? "ViewHolder" : getClass().getSimpleName()) + "{" + Integer.toHexString(hashCode()) + " position=" + this.mPosition + " id=" + this.mItemId + ", oldPos=" + this.mOldPosition + ", pLpos:" + this.mPreLayoutPosition);
            if (isScrap()) {
                sb.append(" scrap ").append(this.mInChangeScrap ? "[changeScrap]" : "[attachedScrap]");
            }
            if (isInvalid()) {
                sb.append(" invalid");
            }
            if (!isBound()) {
                sb.append(" unbound");
            }
            if (needsUpdate()) {
                sb.append(" update");
            }
            if (isRemoved()) {
                sb.append(" removed");
            }
            if (shouldIgnore()) {
                sb.append(" ignored");
            }
            if (isTmpDetached()) {
                sb.append(" tmpDetached");
            }
            if (!isRecyclable()) {
                sb.append(" not recyclable(" + this.mIsRecyclableCount + ")");
            }
            if (isAdapterPositionUnknown()) {
                sb.append(" undefined adapter position");
            }
            if (this.itemView.getParent() == null) {
                sb.append(" no parent");
            }
            sb.append("}");
            return sb.toString();
        }

        public final void setIsRecyclable(boolean recyclable) {
            int i = this.mIsRecyclableCount;
            int i2 = recyclable ? i - 1 : i + 1;
            this.mIsRecyclableCount = i2;
            if (i2 < 0) {
                this.mIsRecyclableCount = 0;
                Log.e("View", "isRecyclable decremented below 0: unmatched pair of setIsRecyable() calls for " + this);
            } else if (!recyclable && i2 == 1) {
                this.mFlags |= 16;
            } else if (recyclable && i2 == 0) {
                this.mFlags &= -17;
            }
        }

        public final boolean isRecyclable() {
            return (this.mFlags & 16) == 0 && !ViewCompat.hasTransientState(this.itemView);
        }

        /* access modifiers changed from: package-private */
        public boolean shouldBeKeptAsChild() {
            return (this.mFlags & 16) != 0;
        }

        /* access modifiers changed from: package-private */
        public boolean doesTransientStatePreventRecycling() {
            return (this.mFlags & 16) == 0 && ViewCompat.hasTransientState(this.itemView);
        }

        /* access modifiers changed from: package-private */
        public boolean isUpdated() {
            return (this.mFlags & 2) != 0;
        }
    }

    /* access modifiers changed from: package-private */
    public boolean setChildImportantForAccessibilityInternal(ViewHolder viewHolder, int importantForAccessibility) {
        if (isComputingLayout()) {
            viewHolder.mPendingAccessibilityState = importantForAccessibility;
            this.mPendingAccessibilityImportanceChange.add(viewHolder);
            return false;
        }
        ViewCompat.setImportantForAccessibility(viewHolder.itemView, importantForAccessibility);
        return true;
    }

    /* access modifiers changed from: package-private */
    public void dispatchPendingImportantForAccessibilityChanges() {
        int state;
        for (int i = this.mPendingAccessibilityImportanceChange.size() - 1; i >= 0; i--) {
            ViewHolder viewHolder = this.mPendingAccessibilityImportanceChange.get(i);
            if (viewHolder.itemView.getParent() == this && !viewHolder.shouldIgnore() && (state = viewHolder.mPendingAccessibilityState) != -1) {
                ViewCompat.setImportantForAccessibility(viewHolder.itemView, state);
                viewHolder.mPendingAccessibilityState = -1;
            }
        }
        this.mPendingAccessibilityImportanceChange.clear();
    }

    /* access modifiers changed from: package-private */
    public int getAdapterPositionFor(ViewHolder viewHolder) {
        if (viewHolder.hasAnyOfTheFlags(MetaDo.META_SETWINDOWEXT) || !viewHolder.isBound()) {
            return -1;
        }
        return this.mAdapterHelper.applyPendingUpdatesToPosition(viewHolder.mPosition);
    }

    /* access modifiers changed from: package-private */
    public void initFastScroller(StateListDrawable verticalThumbDrawable, Drawable verticalTrackDrawable, StateListDrawable horizontalThumbDrawable, Drawable horizontalTrackDrawable) {
        if (verticalThumbDrawable == null || verticalTrackDrawable == null || horizontalThumbDrawable == null || horizontalTrackDrawable == null) {
            throw new IllegalArgumentException("Trying to set fast scroller without both required drawables." + exceptionLabel());
        }
        Resources resources = getContext().getResources();
        new FastScroller(this, verticalThumbDrawable, verticalTrackDrawable, horizontalThumbDrawable, horizontalTrackDrawable, resources.getDimensionPixelSize(C0926R.dimen.fastscroll_default_thickness), resources.getDimensionPixelSize(C0926R.dimen.fastscroll_minimum_range), resources.getDimensionPixelOffset(C0926R.dimen.fastscroll_margin));
    }

    public void setNestedScrollingEnabled(boolean enabled) {
        getScrollingChildHelper().setNestedScrollingEnabled(enabled);
    }

    public boolean isNestedScrollingEnabled() {
        return getScrollingChildHelper().isNestedScrollingEnabled();
    }

    public boolean startNestedScroll(int axes) {
        return getScrollingChildHelper().startNestedScroll(axes);
    }

    public boolean startNestedScroll(int axes, int type) {
        return getScrollingChildHelper().startNestedScroll(axes, type);
    }

    public void stopNestedScroll() {
        getScrollingChildHelper().stopNestedScroll();
    }

    public void stopNestedScroll(int type) {
        getScrollingChildHelper().stopNestedScroll(type);
    }

    public boolean hasNestedScrollingParent() {
        return getScrollingChildHelper().hasNestedScrollingParent();
    }

    public boolean hasNestedScrollingParent(int type) {
        return getScrollingChildHelper().hasNestedScrollingParent(type);
    }

    public boolean dispatchNestedScroll(int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int[] offsetInWindow) {
        return getScrollingChildHelper().dispatchNestedScroll(dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, offsetInWindow);
    }

    public boolean dispatchNestedScroll(int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int[] offsetInWindow, int type) {
        return getScrollingChildHelper().dispatchNestedScroll(dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, offsetInWindow, type);
    }

    public final void dispatchNestedScroll(int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int[] offsetInWindow, int type, int[] consumed) {
        getScrollingChildHelper().dispatchNestedScroll(dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, offsetInWindow, type, consumed);
    }

    public boolean dispatchNestedPreScroll(int dx, int dy, int[] consumed, int[] offsetInWindow) {
        return getScrollingChildHelper().dispatchNestedPreScroll(dx, dy, consumed, offsetInWindow);
    }

    public boolean dispatchNestedPreScroll(int dx, int dy, int[] consumed, int[] offsetInWindow, int type) {
        return getScrollingChildHelper().dispatchNestedPreScroll(dx, dy, consumed, offsetInWindow, type);
    }

    public boolean dispatchNestedFling(float velocityX, float velocityY, boolean consumed) {
        return getScrollingChildHelper().dispatchNestedFling(velocityX, velocityY, consumed);
    }

    public boolean dispatchNestedPreFling(float velocityX, float velocityY) {
        return getScrollingChildHelper().dispatchNestedPreFling(velocityX, velocityY);
    }

    public static class LayoutParams extends ViewGroup.MarginLayoutParams {
        final Rect mDecorInsets = new Rect();
        boolean mInsetsDirty = true;
        boolean mPendingInvalidate = false;
        ViewHolder mViewHolder;

        public LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
        }

        public LayoutParams(int width, int height) {
            super(width, height);
        }

        public LayoutParams(ViewGroup.MarginLayoutParams source) {
            super(source);
        }

        public LayoutParams(ViewGroup.LayoutParams source) {
            super(source);
        }

        public LayoutParams(LayoutParams source) {
            super(source);
        }

        public boolean viewNeedsUpdate() {
            return this.mViewHolder.needsUpdate();
        }

        public boolean isViewInvalid() {
            return this.mViewHolder.isInvalid();
        }

        public boolean isItemRemoved() {
            return this.mViewHolder.isRemoved();
        }

        public boolean isItemChanged() {
            return this.mViewHolder.isUpdated();
        }

        @Deprecated
        public int getViewPosition() {
            return this.mViewHolder.getPosition();
        }

        public int getViewLayoutPosition() {
            return this.mViewHolder.getLayoutPosition();
        }

        public int getViewAdapterPosition() {
            return this.mViewHolder.getAdapterPosition();
        }
    }

    public static abstract class AdapterDataObserver {
        public void onChanged() {
        }

        public void onItemRangeChanged(int positionStart, int itemCount) {
        }

        public void onItemRangeChanged(int positionStart, int itemCount, Object payload) {
            onItemRangeChanged(positionStart, itemCount);
        }

        public void onItemRangeInserted(int positionStart, int itemCount) {
        }

        public void onItemRangeRemoved(int positionStart, int itemCount) {
        }

        public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
        }
    }

    public static abstract class SmoothScroller {
        private LayoutManager mLayoutManager;
        private boolean mPendingInitialRun;
        private RecyclerView mRecyclerView;
        private final Action mRecyclingAction = new Action(0, 0);
        private boolean mRunning;
        private boolean mStarted;
        private int mTargetPosition = -1;
        private View mTargetView;

        public interface ScrollVectorProvider {
            PointF computeScrollVectorForPosition(int i);
        }

        /* access modifiers changed from: protected */
        public abstract void onSeekTargetStep(int i, int i2, State state, Action action);

        /* access modifiers changed from: protected */
        public abstract void onStart();

        /* access modifiers changed from: protected */
        public abstract void onStop();

        /* access modifiers changed from: protected */
        public abstract void onTargetFound(View view, State state, Action action);

        /* access modifiers changed from: package-private */
        public void start(RecyclerView recyclerView, LayoutManager layoutManager) {
            recyclerView.mViewFlinger.stop();
            if (this.mStarted) {
                Log.w(RecyclerView.TAG, "An instance of " + getClass().getSimpleName() + " was started more than once. Each instance of" + getClass().getSimpleName() + " is intended to only be used once. You should create a new instance for each use.");
            }
            this.mRecyclerView = recyclerView;
            this.mLayoutManager = layoutManager;
            if (this.mTargetPosition != -1) {
                recyclerView.mState.mTargetPosition = this.mTargetPosition;
                this.mRunning = true;
                this.mPendingInitialRun = true;
                this.mTargetView = findViewByPosition(getTargetPosition());
                onStart();
                this.mRecyclerView.mViewFlinger.postOnAnimation();
                this.mStarted = true;
                return;
            }
            throw new IllegalArgumentException("Invalid target position");
        }

        public void setTargetPosition(int targetPosition) {
            this.mTargetPosition = targetPosition;
        }

        public PointF computeScrollVectorForPosition(int targetPosition) {
            LayoutManager layoutManager = getLayoutManager();
            if (layoutManager instanceof ScrollVectorProvider) {
                return ((ScrollVectorProvider) layoutManager).computeScrollVectorForPosition(targetPosition);
            }
            Log.w(RecyclerView.TAG, "You should override computeScrollVectorForPosition when the LayoutManager does not implement " + ScrollVectorProvider.class.getCanonicalName());
            return null;
        }

        public LayoutManager getLayoutManager() {
            return this.mLayoutManager;
        }

        /* access modifiers changed from: protected */
        public final void stop() {
            if (this.mRunning) {
                this.mRunning = false;
                onStop();
                this.mRecyclerView.mState.mTargetPosition = -1;
                this.mTargetView = null;
                this.mTargetPosition = -1;
                this.mPendingInitialRun = false;
                this.mLayoutManager.onSmoothScrollerStopped(this);
                this.mLayoutManager = null;
                this.mRecyclerView = null;
            }
        }

        public boolean isPendingInitialRun() {
            return this.mPendingInitialRun;
        }

        public boolean isRunning() {
            return this.mRunning;
        }

        public int getTargetPosition() {
            return this.mTargetPosition;
        }

        /* access modifiers changed from: package-private */
        public void onAnimation(int dx, int dy) {
            PointF pointF;
            RecyclerView recyclerView = this.mRecyclerView;
            if (this.mTargetPosition == -1 || recyclerView == null) {
                stop();
            }
            if (!(!this.mPendingInitialRun || this.mTargetView != null || this.mLayoutManager == null || (pointF = computeScrollVectorForPosition(this.mTargetPosition)) == null || (pointF.x == 0.0f && pointF.y == 0.0f))) {
                recyclerView.scrollStep((int) Math.signum(pointF.x), (int) Math.signum(pointF.y), (int[]) null);
            }
            this.mPendingInitialRun = false;
            View view = this.mTargetView;
            if (view != null) {
                if (getChildPosition(view) == this.mTargetPosition) {
                    onTargetFound(this.mTargetView, recyclerView.mState, this.mRecyclingAction);
                    this.mRecyclingAction.runIfNecessary(recyclerView);
                    stop();
                } else {
                    Log.e(RecyclerView.TAG, "Passed over target position while smooth scrolling.");
                    this.mTargetView = null;
                }
            }
            if (this.mRunning) {
                onSeekTargetStep(dx, dy, recyclerView.mState, this.mRecyclingAction);
                boolean hadJumpTarget = this.mRecyclingAction.hasJumpTarget();
                this.mRecyclingAction.runIfNecessary(recyclerView);
                if (hadJumpTarget && this.mRunning) {
                    this.mPendingInitialRun = true;
                    recyclerView.mViewFlinger.postOnAnimation();
                }
            }
        }

        public int getChildPosition(View view) {
            return this.mRecyclerView.getChildLayoutPosition(view);
        }

        public int getChildCount() {
            return this.mRecyclerView.mLayout.getChildCount();
        }

        public View findViewByPosition(int position) {
            return this.mRecyclerView.mLayout.findViewByPosition(position);
        }

        @Deprecated
        public void instantScrollToPosition(int position) {
            this.mRecyclerView.scrollToPosition(position);
        }

        /* access modifiers changed from: protected */
        public void onChildAttachedToWindow(View child) {
            if (getChildPosition(child) == getTargetPosition()) {
                this.mTargetView = child;
            }
        }

        /* access modifiers changed from: protected */
        public void normalize(PointF scrollVector) {
            float magnitude = (float) Math.sqrt((double) ((scrollVector.x * scrollVector.x) + (scrollVector.y * scrollVector.y)));
            scrollVector.x /= magnitude;
            scrollVector.y /= magnitude;
        }

        public static class Action {
            public static final int UNDEFINED_DURATION = Integer.MIN_VALUE;
            private boolean mChanged;
            private int mConsecutiveUpdates;
            private int mDuration;
            private int mDx;
            private int mDy;
            private Interpolator mInterpolator;
            private int mJumpToPosition;

            public Action(int dx, int dy) {
                this(dx, dy, Integer.MIN_VALUE, (Interpolator) null);
            }

            public Action(int dx, int dy, int duration) {
                this(dx, dy, duration, (Interpolator) null);
            }

            public Action(int dx, int dy, int duration, Interpolator interpolator) {
                this.mJumpToPosition = -1;
                this.mChanged = false;
                this.mConsecutiveUpdates = 0;
                this.mDx = dx;
                this.mDy = dy;
                this.mDuration = duration;
                this.mInterpolator = interpolator;
            }

            public void jumpTo(int targetPosition) {
                this.mJumpToPosition = targetPosition;
            }

            /* access modifiers changed from: package-private */
            public boolean hasJumpTarget() {
                return this.mJumpToPosition >= 0;
            }

            /* access modifiers changed from: package-private */
            public void runIfNecessary(RecyclerView recyclerView) {
                if (this.mJumpToPosition >= 0) {
                    int position = this.mJumpToPosition;
                    this.mJumpToPosition = -1;
                    recyclerView.jumpToPositionForSmoothScroller(position);
                    this.mChanged = false;
                } else if (this.mChanged != 0) {
                    validate();
                    recyclerView.mViewFlinger.smoothScrollBy(this.mDx, this.mDy, this.mDuration, this.mInterpolator);
                    int i = this.mConsecutiveUpdates + 1;
                    this.mConsecutiveUpdates = i;
                    if (i > 10) {
                        Log.e(RecyclerView.TAG, "Smooth Scroll action is being updated too frequently. Make sure you are not changing it unless necessary");
                    }
                    this.mChanged = false;
                } else {
                    this.mConsecutiveUpdates = 0;
                }
            }

            private void validate() {
                if (this.mInterpolator != null && this.mDuration < 1) {
                    throw new IllegalStateException("If you provide an interpolator, you must set a positive duration");
                } else if (this.mDuration < 1) {
                    throw new IllegalStateException("Scroll duration must be a positive number");
                }
            }

            public int getDx() {
                return this.mDx;
            }

            public void setDx(int dx) {
                this.mChanged = true;
                this.mDx = dx;
            }

            public int getDy() {
                return this.mDy;
            }

            public void setDy(int dy) {
                this.mChanged = true;
                this.mDy = dy;
            }

            public int getDuration() {
                return this.mDuration;
            }

            public void setDuration(int duration) {
                this.mChanged = true;
                this.mDuration = duration;
            }

            public Interpolator getInterpolator() {
                return this.mInterpolator;
            }

            public void setInterpolator(Interpolator interpolator) {
                this.mChanged = true;
                this.mInterpolator = interpolator;
            }

            public void update(int dx, int dy, int duration, Interpolator interpolator) {
                this.mDx = dx;
                this.mDy = dy;
                this.mDuration = duration;
                this.mInterpolator = interpolator;
                this.mChanged = true;
            }
        }
    }

    static class AdapterDataObservable extends Observable<AdapterDataObserver> {
        AdapterDataObservable() {
        }

        public boolean hasObservers() {
            return !this.mObservers.isEmpty();
        }

        public void notifyChanged() {
            for (int i = this.mObservers.size() - 1; i >= 0; i--) {
                ((AdapterDataObserver) this.mObservers.get(i)).onChanged();
            }
        }

        public void notifyItemRangeChanged(int positionStart, int itemCount) {
            notifyItemRangeChanged(positionStart, itemCount, (Object) null);
        }

        public void notifyItemRangeChanged(int positionStart, int itemCount, Object payload) {
            for (int i = this.mObservers.size() - 1; i >= 0; i--) {
                ((AdapterDataObserver) this.mObservers.get(i)).onItemRangeChanged(positionStart, itemCount, payload);
            }
        }

        public void notifyItemRangeInserted(int positionStart, int itemCount) {
            for (int i = this.mObservers.size() - 1; i >= 0; i--) {
                ((AdapterDataObserver) this.mObservers.get(i)).onItemRangeInserted(positionStart, itemCount);
            }
        }

        public void notifyItemRangeRemoved(int positionStart, int itemCount) {
            for (int i = this.mObservers.size() - 1; i >= 0; i--) {
                ((AdapterDataObserver) this.mObservers.get(i)).onItemRangeRemoved(positionStart, itemCount);
            }
        }

        public void notifyItemMoved(int fromPosition, int toPosition) {
            for (int i = this.mObservers.size() - 1; i >= 0; i--) {
                ((AdapterDataObserver) this.mObservers.get(i)).onItemRangeMoved(fromPosition, toPosition, 1);
            }
        }
    }

    public static class SavedState extends AbsSavedState {
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
        Parcelable mLayoutState;

        SavedState(Parcel in, ClassLoader loader) {
            super(in, loader);
            ClassLoader classLoader;
            if (loader != null) {
                classLoader = loader;
            } else {
                classLoader = LayoutManager.class.getClassLoader();
            }
            this.mLayoutState = in.readParcelable(classLoader);
        }

        SavedState(Parcelable superState) {
            super(superState);
        }

        public void writeToParcel(Parcel dest, int flags) {
            super.writeToParcel(dest, flags);
            dest.writeParcelable(this.mLayoutState, 0);
        }

        /* access modifiers changed from: package-private */
        public void copyFrom(SavedState other) {
            this.mLayoutState = other.mLayoutState;
        }
    }

    public static class State {
        static final int STEP_ANIMATIONS = 4;
        static final int STEP_LAYOUT = 2;
        static final int STEP_START = 1;
        private SparseArray<Object> mData;
        int mDeletedInvisibleItemCountSincePreviousLayout = 0;
        long mFocusedItemId;
        int mFocusedItemPosition;
        int mFocusedSubChildId;
        boolean mInPreLayout = false;
        boolean mIsMeasuring = false;
        int mItemCount = 0;
        int mLayoutStep = 1;
        int mPreviousLayoutItemCount = 0;
        int mRemainingScrollHorizontal;
        int mRemainingScrollVertical;
        boolean mRunPredictiveAnimations = false;
        boolean mRunSimpleAnimations = false;
        boolean mStructureChanged = false;
        int mTargetPosition = -1;
        boolean mTrackOldChangeHolders = false;

        /* access modifiers changed from: package-private */
        public void assertLayoutStep(int accepted) {
            if ((this.mLayoutStep & accepted) == 0) {
                throw new IllegalStateException("Layout state should be one of " + Integer.toBinaryString(accepted) + " but it is " + Integer.toBinaryString(this.mLayoutStep));
            }
        }

        /* access modifiers changed from: package-private */
        public void prepareForNestedPrefetch(Adapter adapter) {
            this.mLayoutStep = 1;
            this.mItemCount = adapter.getItemCount();
            this.mInPreLayout = false;
            this.mTrackOldChangeHolders = false;
            this.mIsMeasuring = false;
        }

        public boolean isMeasuring() {
            return this.mIsMeasuring;
        }

        public boolean isPreLayout() {
            return this.mInPreLayout;
        }

        public boolean willRunPredictiveAnimations() {
            return this.mRunPredictiveAnimations;
        }

        public boolean willRunSimpleAnimations() {
            return this.mRunSimpleAnimations;
        }

        public void remove(int resourceId) {
            SparseArray<Object> sparseArray = this.mData;
            if (sparseArray != null) {
                sparseArray.remove(resourceId);
            }
        }

        public <T> T get(int resourceId) {
            SparseArray<Object> sparseArray = this.mData;
            if (sparseArray == null) {
                return null;
            }
            return sparseArray.get(resourceId);
        }

        public void put(int resourceId, Object data) {
            if (this.mData == null) {
                this.mData = new SparseArray<>();
            }
            this.mData.put(resourceId, data);
        }

        public int getTargetScrollPosition() {
            return this.mTargetPosition;
        }

        public boolean hasTargetScrollPosition() {
            return this.mTargetPosition != -1;
        }

        public boolean didStructureChange() {
            return this.mStructureChanged;
        }

        public int getItemCount() {
            return this.mInPreLayout ? this.mPreviousLayoutItemCount - this.mDeletedInvisibleItemCountSincePreviousLayout : this.mItemCount;
        }

        public int getRemainingScrollHorizontal() {
            return this.mRemainingScrollHorizontal;
        }

        public int getRemainingScrollVertical() {
            return this.mRemainingScrollVertical;
        }

        public String toString() {
            return "State{mTargetPosition=" + this.mTargetPosition + ", mData=" + this.mData + ", mItemCount=" + this.mItemCount + ", mIsMeasuring=" + this.mIsMeasuring + ", mPreviousLayoutItemCount=" + this.mPreviousLayoutItemCount + ", mDeletedInvisibleItemCountSincePreviousLayout=" + this.mDeletedInvisibleItemCountSincePreviousLayout + ", mStructureChanged=" + this.mStructureChanged + ", mInPreLayout=" + this.mInPreLayout + ", mRunSimpleAnimations=" + this.mRunSimpleAnimations + ", mRunPredictiveAnimations=" + this.mRunPredictiveAnimations + '}';
        }
    }

    private class ItemAnimatorRestoreListener implements ItemAnimator.ItemAnimatorListener {
        ItemAnimatorRestoreListener() {
        }

        public void onAnimationFinished(ViewHolder item) {
            item.setIsRecyclable(true);
            if (item.mShadowedHolder != null && item.mShadowingHolder == null) {
                item.mShadowedHolder = null;
            }
            item.mShadowingHolder = null;
            if (!item.shouldBeKeptAsChild() && !RecyclerView.this.removeAnimatingView(item.itemView) && item.isTmpDetached()) {
                RecyclerView.this.removeDetachedView(item.itemView, false);
            }
        }
    }

    public static abstract class ItemAnimator {
        public static final int FLAG_APPEARED_IN_PRE_LAYOUT = 4096;
        public static final int FLAG_CHANGED = 2;
        public static final int FLAG_INVALIDATED = 4;
        public static final int FLAG_MOVED = 2048;
        public static final int FLAG_REMOVED = 8;
        private long mAddDuration = 120;
        private long mChangeDuration = 250;
        private ArrayList<ItemAnimatorFinishedListener> mFinishedListeners = new ArrayList<>();
        private ItemAnimatorListener mListener = null;
        private long mMoveDuration = 250;
        private long mRemoveDuration = 120;

        @Retention(RetentionPolicy.SOURCE)
        public @interface AdapterChanges {
        }

        public interface ItemAnimatorFinishedListener {
            void onAnimationsFinished();
        }

        interface ItemAnimatorListener {
            void onAnimationFinished(ViewHolder viewHolder);
        }

        public abstract boolean animateAppearance(ViewHolder viewHolder, ItemHolderInfo itemHolderInfo, ItemHolderInfo itemHolderInfo2);

        public abstract boolean animateChange(ViewHolder viewHolder, ViewHolder viewHolder2, ItemHolderInfo itemHolderInfo, ItemHolderInfo itemHolderInfo2);

        public abstract boolean animateDisappearance(ViewHolder viewHolder, ItemHolderInfo itemHolderInfo, ItemHolderInfo itemHolderInfo2);

        public abstract boolean animatePersistence(ViewHolder viewHolder, ItemHolderInfo itemHolderInfo, ItemHolderInfo itemHolderInfo2);

        public abstract void endAnimation(ViewHolder viewHolder);

        public abstract void endAnimations();

        public abstract boolean isRunning();

        public abstract void runPendingAnimations();

        public long getMoveDuration() {
            return this.mMoveDuration;
        }

        public void setMoveDuration(long moveDuration) {
            this.mMoveDuration = moveDuration;
        }

        public long getAddDuration() {
            return this.mAddDuration;
        }

        public void setAddDuration(long addDuration) {
            this.mAddDuration = addDuration;
        }

        public long getRemoveDuration() {
            return this.mRemoveDuration;
        }

        public void setRemoveDuration(long removeDuration) {
            this.mRemoveDuration = removeDuration;
        }

        public long getChangeDuration() {
            return this.mChangeDuration;
        }

        public void setChangeDuration(long changeDuration) {
            this.mChangeDuration = changeDuration;
        }

        /* access modifiers changed from: package-private */
        public void setListener(ItemAnimatorListener listener) {
            this.mListener = listener;
        }

        public ItemHolderInfo recordPreLayoutInformation(State state, ViewHolder viewHolder, int changeFlags, List<Object> list) {
            return obtainHolderInfo().setFrom(viewHolder);
        }

        public ItemHolderInfo recordPostLayoutInformation(State state, ViewHolder viewHolder) {
            return obtainHolderInfo().setFrom(viewHolder);
        }

        static int buildAdapterChangeFlagsForAnimations(ViewHolder viewHolder) {
            int flags = viewHolder.mFlags & 14;
            if (viewHolder.isInvalid()) {
                return 4;
            }
            if ((flags & 4) != 0) {
                return flags;
            }
            int oldPos = viewHolder.getOldPosition();
            int pos = viewHolder.getAdapterPosition();
            if (oldPos == -1 || pos == -1 || oldPos == pos) {
                return flags;
            }
            return flags | 2048;
        }

        public final void dispatchAnimationFinished(ViewHolder viewHolder) {
            onAnimationFinished(viewHolder);
            ItemAnimatorListener itemAnimatorListener = this.mListener;
            if (itemAnimatorListener != null) {
                itemAnimatorListener.onAnimationFinished(viewHolder);
            }
        }

        public void onAnimationFinished(ViewHolder viewHolder) {
        }

        public final void dispatchAnimationStarted(ViewHolder viewHolder) {
            onAnimationStarted(viewHolder);
        }

        public void onAnimationStarted(ViewHolder viewHolder) {
        }

        public final boolean isRunning(ItemAnimatorFinishedListener listener) {
            boolean running = isRunning();
            if (listener != null) {
                if (!running) {
                    listener.onAnimationsFinished();
                } else {
                    this.mFinishedListeners.add(listener);
                }
            }
            return running;
        }

        public boolean canReuseUpdatedViewHolder(ViewHolder viewHolder) {
            return true;
        }

        public boolean canReuseUpdatedViewHolder(ViewHolder viewHolder, List<Object> list) {
            return canReuseUpdatedViewHolder(viewHolder);
        }

        public final void dispatchAnimationsFinished() {
            int count = this.mFinishedListeners.size();
            for (int i = 0; i < count; i++) {
                this.mFinishedListeners.get(i).onAnimationsFinished();
            }
            this.mFinishedListeners.clear();
        }

        public ItemHolderInfo obtainHolderInfo() {
            return new ItemHolderInfo();
        }

        public static class ItemHolderInfo {
            public int bottom;
            public int changeFlags;
            public int left;
            public int right;
            public int top;

            public ItemHolderInfo setFrom(ViewHolder holder) {
                return setFrom(holder, 0);
            }

            public ItemHolderInfo setFrom(ViewHolder holder, int flags) {
                View view = holder.itemView;
                this.left = view.getLeft();
                this.top = view.getTop();
                this.right = view.getRight();
                this.bottom = view.getBottom();
                return this;
            }
        }
    }

    /* access modifiers changed from: protected */
    public int getChildDrawingOrder(int childCount, int i) {
        ChildDrawingOrderCallback childDrawingOrderCallback = this.mChildDrawingOrderCallback;
        if (childDrawingOrderCallback == null) {
            return super.getChildDrawingOrder(childCount, i);
        }
        return childDrawingOrderCallback.onGetChildDrawingOrder(childCount, i);
    }

    private NestedScrollingChildHelper getScrollingChildHelper() {
        if (this.mScrollingChildHelper == null) {
            this.mScrollingChildHelper = new NestedScrollingChildHelper(this);
        }
        return this.mScrollingChildHelper;
    }
}
