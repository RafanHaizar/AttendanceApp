package androidx.customview.widget;

import android.content.Context;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.widget.OverScroller;
import androidx.core.view.ViewCompat;
import java.util.Arrays;

public class ViewDragHelper {
    private static final int BASE_SETTLE_DURATION = 256;
    public static final int DIRECTION_ALL = 3;
    public static final int DIRECTION_HORIZONTAL = 1;
    public static final int DIRECTION_VERTICAL = 2;
    public static final int EDGE_ALL = 15;
    public static final int EDGE_BOTTOM = 8;
    public static final int EDGE_LEFT = 1;
    public static final int EDGE_RIGHT = 2;
    private static final int EDGE_SIZE = 20;
    public static final int EDGE_TOP = 4;
    public static final int INVALID_POINTER = -1;
    private static final int MAX_SETTLE_DURATION = 600;
    public static final int STATE_DRAGGING = 1;
    public static final int STATE_IDLE = 0;
    public static final int STATE_SETTLING = 2;
    private static final String TAG = "ViewDragHelper";
    private static final Interpolator sInterpolator = new Interpolator() {
        public float getInterpolation(float t) {
            float t2 = t - 1.0f;
            return (t2 * t2 * t2 * t2 * t2) + 1.0f;
        }
    };
    private int mActivePointerId = -1;
    private final Callback mCallback;
    private View mCapturedView;
    private final int mDefaultEdgeSize;
    private int mDragState;
    private int[] mEdgeDragsInProgress;
    private int[] mEdgeDragsLocked;
    private int mEdgeSize;
    private int[] mInitialEdgesTouched;
    private float[] mInitialMotionX;
    private float[] mInitialMotionY;
    private float[] mLastMotionX;
    private float[] mLastMotionY;
    private float mMaxVelocity;
    private float mMinVelocity;
    private final ViewGroup mParentView;
    private int mPointersDown;
    private boolean mReleaseInProgress;
    private OverScroller mScroller;
    private final Runnable mSetIdleRunnable = new Runnable() {
        public void run() {
            ViewDragHelper.this.setDragState(0);
        }
    };
    private int mTouchSlop;
    private int mTrackingEdges;
    private VelocityTracker mVelocityTracker;

    public static abstract class Callback {
        public abstract boolean tryCaptureView(View view, int i);

        public void onViewDragStateChanged(int state) {
        }

        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
        }

        public void onViewCaptured(View capturedChild, int activePointerId) {
        }

        public void onViewReleased(View releasedChild, float xvel, float yvel) {
        }

        public void onEdgeTouched(int edgeFlags, int pointerId) {
        }

        public boolean onEdgeLock(int edgeFlags) {
            return false;
        }

        public void onEdgeDragStarted(int edgeFlags, int pointerId) {
        }

        public int getOrderedChildIndex(int index) {
            return index;
        }

        public int getViewHorizontalDragRange(View child) {
            return 0;
        }

        public int getViewVerticalDragRange(View child) {
            return 0;
        }

        public int clampViewPositionHorizontal(View child, int left, int dx) {
            return 0;
        }

        public int clampViewPositionVertical(View child, int top, int dy) {
            return 0;
        }
    }

    public static ViewDragHelper create(ViewGroup forParent, Callback cb) {
        return new ViewDragHelper(forParent.getContext(), forParent, cb);
    }

    public static ViewDragHelper create(ViewGroup forParent, float sensitivity, Callback cb) {
        ViewDragHelper helper = create(forParent, cb);
        helper.mTouchSlop = (int) (((float) helper.mTouchSlop) * (1.0f / sensitivity));
        return helper;
    }

    private ViewDragHelper(Context context, ViewGroup forParent, Callback cb) {
        if (forParent == null) {
            throw new IllegalArgumentException("Parent view may not be null");
        } else if (cb != null) {
            this.mParentView = forParent;
            this.mCallback = cb;
            ViewConfiguration vc = ViewConfiguration.get(context);
            int i = (int) ((20.0f * context.getResources().getDisplayMetrics().density) + 0.5f);
            this.mDefaultEdgeSize = i;
            this.mEdgeSize = i;
            this.mTouchSlop = vc.getScaledTouchSlop();
            this.mMaxVelocity = (float) vc.getScaledMaximumFlingVelocity();
            this.mMinVelocity = (float) vc.getScaledMinimumFlingVelocity();
            this.mScroller = new OverScroller(context, sInterpolator);
        } else {
            throw new IllegalArgumentException("Callback may not be null");
        }
    }

    public void setMinVelocity(float minVel) {
        this.mMinVelocity = minVel;
    }

    public float getMinVelocity() {
        return this.mMinVelocity;
    }

    public int getViewDragState() {
        return this.mDragState;
    }

    public void setEdgeTrackingEnabled(int edgeFlags) {
        this.mTrackingEdges = edgeFlags;
    }

    public int getEdgeSize() {
        return this.mEdgeSize;
    }

    public void setEdgeSize(int size) {
        this.mEdgeSize = size;
    }

    public int getDefaultEdgeSize() {
        return this.mDefaultEdgeSize;
    }

    public void captureChildView(View childView, int activePointerId) {
        if (childView.getParent() == this.mParentView) {
            this.mCapturedView = childView;
            this.mActivePointerId = activePointerId;
            this.mCallback.onViewCaptured(childView, activePointerId);
            setDragState(1);
            return;
        }
        throw new IllegalArgumentException("captureChildView: parameter must be a descendant of the ViewDragHelper's tracked parent view (" + this.mParentView + ")");
    }

    public View getCapturedView() {
        return this.mCapturedView;
    }

    public int getActivePointerId() {
        return this.mActivePointerId;
    }

    public int getTouchSlop() {
        return this.mTouchSlop;
    }

    public void cancel() {
        this.mActivePointerId = -1;
        clearMotionHistory();
        VelocityTracker velocityTracker = this.mVelocityTracker;
        if (velocityTracker != null) {
            velocityTracker.recycle();
            this.mVelocityTracker = null;
        }
    }

    public void abort() {
        cancel();
        if (this.mDragState == 2) {
            int oldX = this.mScroller.getCurrX();
            int oldY = this.mScroller.getCurrY();
            this.mScroller.abortAnimation();
            int newX = this.mScroller.getCurrX();
            int newY = this.mScroller.getCurrY();
            this.mCallback.onViewPositionChanged(this.mCapturedView, newX, newY, newX - oldX, newY - oldY);
        }
        setDragState(0);
    }

    public boolean smoothSlideViewTo(View child, int finalLeft, int finalTop) {
        this.mCapturedView = child;
        this.mActivePointerId = -1;
        boolean continueSliding = forceSettleCapturedViewAt(finalLeft, finalTop, 0, 0);
        if (!continueSliding && this.mDragState == 0 && this.mCapturedView != null) {
            this.mCapturedView = null;
        }
        return continueSliding;
    }

    public boolean settleCapturedViewAt(int finalLeft, int finalTop) {
        if (this.mReleaseInProgress) {
            return forceSettleCapturedViewAt(finalLeft, finalTop, (int) this.mVelocityTracker.getXVelocity(this.mActivePointerId), (int) this.mVelocityTracker.getYVelocity(this.mActivePointerId));
        }
        throw new IllegalStateException("Cannot settleCapturedViewAt outside of a call to Callback#onViewReleased");
    }

    private boolean forceSettleCapturedViewAt(int finalLeft, int finalTop, int xvel, int yvel) {
        int startLeft = this.mCapturedView.getLeft();
        int startTop = this.mCapturedView.getTop();
        int dx = finalLeft - startLeft;
        int dy = finalTop - startTop;
        if (dx == 0 && dy == 0) {
            this.mScroller.abortAnimation();
            setDragState(0);
            return false;
        }
        this.mScroller.startScroll(startLeft, startTop, dx, dy, computeSettleDuration(this.mCapturedView, dx, dy, xvel, yvel));
        setDragState(2);
        return true;
    }

    private int computeSettleDuration(View child, int dx, int dy, int xvel, int yvel) {
        float xweight;
        float yweight;
        View view = child;
        int xvel2 = clampMag(xvel, (int) this.mMinVelocity, (int) this.mMaxVelocity);
        int yvel2 = clampMag(yvel, (int) this.mMinVelocity, (int) this.mMaxVelocity);
        int absDx = Math.abs(dx);
        int absDy = Math.abs(dy);
        int absXVel = Math.abs(xvel2);
        int absYVel = Math.abs(yvel2);
        int addedVel = absXVel + absYVel;
        int addedDistance = absDx + absDy;
        if (xvel2 != 0) {
            xweight = ((float) absXVel) / ((float) addedVel);
        } else {
            xweight = ((float) absDx) / ((float) addedDistance);
        }
        if (yvel2 != 0) {
            yweight = ((float) absYVel) / ((float) addedVel);
        } else {
            yweight = ((float) absDy) / ((float) addedDistance);
        }
        return (int) ((((float) computeAxisDuration(dx, xvel2, this.mCallback.getViewHorizontalDragRange(view))) * xweight) + (((float) computeAxisDuration(dy, yvel2, this.mCallback.getViewVerticalDragRange(view))) * yweight));
    }

    private int computeAxisDuration(int delta, int velocity, int motionRange) {
        int duration;
        if (delta == 0) {
            return 0;
        }
        int width = this.mParentView.getWidth();
        int halfWidth = width / 2;
        float distance = ((float) halfWidth) + (((float) halfWidth) * distanceInfluenceForSnapDuration(Math.min(1.0f, ((float) Math.abs(delta)) / ((float) width))));
        int velocity2 = Math.abs(velocity);
        if (velocity2 > 0) {
            duration = Math.round(Math.abs(distance / ((float) velocity2)) * 1000.0f) * 4;
        } else {
            duration = (int) ((1.0f + (((float) Math.abs(delta)) / ((float) motionRange))) * 256.0f);
        }
        return Math.min(duration, 600);
    }

    private int clampMag(int value, int absMin, int absMax) {
        int absValue = Math.abs(value);
        if (absValue < absMin) {
            return 0;
        }
        if (absValue > absMax) {
            return value > 0 ? absMax : -absMax;
        }
        return value;
    }

    private float clampMag(float value, float absMin, float absMax) {
        float absValue = Math.abs(value);
        if (absValue < absMin) {
            return 0.0f;
        }
        if (absValue > absMax) {
            return value > 0.0f ? absMax : -absMax;
        }
        return value;
    }

    private float distanceInfluenceForSnapDuration(float f) {
        return (float) Math.sin((double) ((f - 0.5f) * 0.47123894f));
    }

    public void flingCapturedView(int minLeft, int minTop, int maxLeft, int maxTop) {
        if (this.mReleaseInProgress) {
            this.mScroller.fling(this.mCapturedView.getLeft(), this.mCapturedView.getTop(), (int) this.mVelocityTracker.getXVelocity(this.mActivePointerId), (int) this.mVelocityTracker.getYVelocity(this.mActivePointerId), minLeft, maxLeft, minTop, maxTop);
            setDragState(2);
            return;
        }
        throw new IllegalStateException("Cannot flingCapturedView outside of a call to Callback#onViewReleased");
    }

    public boolean continueSettling(boolean deferCallbacks) {
        if (this.mDragState == 2) {
            boolean keepGoing = this.mScroller.computeScrollOffset();
            int x = this.mScroller.getCurrX();
            int y = this.mScroller.getCurrY();
            int dx = x - this.mCapturedView.getLeft();
            int dy = y - this.mCapturedView.getTop();
            if (dx != 0) {
                ViewCompat.offsetLeftAndRight(this.mCapturedView, dx);
            }
            if (dy != 0) {
                ViewCompat.offsetTopAndBottom(this.mCapturedView, dy);
            }
            if (!(dx == 0 && dy == 0)) {
                this.mCallback.onViewPositionChanged(this.mCapturedView, x, y, dx, dy);
            }
            if (keepGoing && x == this.mScroller.getFinalX() && y == this.mScroller.getFinalY()) {
                this.mScroller.abortAnimation();
                keepGoing = false;
            }
            if (!keepGoing) {
                if (deferCallbacks) {
                    this.mParentView.post(this.mSetIdleRunnable);
                } else {
                    setDragState(0);
                }
            }
        }
        if (this.mDragState == 2) {
            return true;
        }
        return false;
    }

    private void dispatchViewReleased(float xvel, float yvel) {
        this.mReleaseInProgress = true;
        this.mCallback.onViewReleased(this.mCapturedView, xvel, yvel);
        this.mReleaseInProgress = false;
        if (this.mDragState == 1) {
            setDragState(0);
        }
    }

    private void clearMotionHistory() {
        float[] fArr = this.mInitialMotionX;
        if (fArr != null) {
            Arrays.fill(fArr, 0.0f);
            Arrays.fill(this.mInitialMotionY, 0.0f);
            Arrays.fill(this.mLastMotionX, 0.0f);
            Arrays.fill(this.mLastMotionY, 0.0f);
            Arrays.fill(this.mInitialEdgesTouched, 0);
            Arrays.fill(this.mEdgeDragsInProgress, 0);
            Arrays.fill(this.mEdgeDragsLocked, 0);
            this.mPointersDown = 0;
        }
    }

    private void clearMotionHistory(int pointerId) {
        if (this.mInitialMotionX != null && isPointerDown(pointerId)) {
            this.mInitialMotionX[pointerId] = 0.0f;
            this.mInitialMotionY[pointerId] = 0.0f;
            this.mLastMotionX[pointerId] = 0.0f;
            this.mLastMotionY[pointerId] = 0.0f;
            this.mInitialEdgesTouched[pointerId] = 0;
            this.mEdgeDragsInProgress[pointerId] = 0;
            this.mEdgeDragsLocked[pointerId] = 0;
            this.mPointersDown &= (1 << pointerId) ^ -1;
        }
    }

    private void ensureMotionHistorySizeForId(int pointerId) {
        float[] fArr = this.mInitialMotionX;
        if (fArr == null || fArr.length <= pointerId) {
            float[] imx = new float[(pointerId + 1)];
            float[] imy = new float[(pointerId + 1)];
            float[] lmx = new float[(pointerId + 1)];
            float[] lmy = new float[(pointerId + 1)];
            int[] iit = new int[(pointerId + 1)];
            int[] edip = new int[(pointerId + 1)];
            int[] edl = new int[(pointerId + 1)];
            if (fArr != null) {
                System.arraycopy(fArr, 0, imx, 0, fArr.length);
                float[] fArr2 = this.mInitialMotionY;
                System.arraycopy(fArr2, 0, imy, 0, fArr2.length);
                float[] fArr3 = this.mLastMotionX;
                System.arraycopy(fArr3, 0, lmx, 0, fArr3.length);
                float[] fArr4 = this.mLastMotionY;
                System.arraycopy(fArr4, 0, lmy, 0, fArr4.length);
                int[] iArr = this.mInitialEdgesTouched;
                System.arraycopy(iArr, 0, iit, 0, iArr.length);
                int[] iArr2 = this.mEdgeDragsInProgress;
                System.arraycopy(iArr2, 0, edip, 0, iArr2.length);
                int[] iArr3 = this.mEdgeDragsLocked;
                System.arraycopy(iArr3, 0, edl, 0, iArr3.length);
            }
            this.mInitialMotionX = imx;
            this.mInitialMotionY = imy;
            this.mLastMotionX = lmx;
            this.mLastMotionY = lmy;
            this.mInitialEdgesTouched = iit;
            this.mEdgeDragsInProgress = edip;
            this.mEdgeDragsLocked = edl;
        }
    }

    private void saveInitialMotion(float x, float y, int pointerId) {
        ensureMotionHistorySizeForId(pointerId);
        float[] fArr = this.mInitialMotionX;
        this.mLastMotionX[pointerId] = x;
        fArr[pointerId] = x;
        float[] fArr2 = this.mInitialMotionY;
        this.mLastMotionY[pointerId] = y;
        fArr2[pointerId] = y;
        this.mInitialEdgesTouched[pointerId] = getEdgesTouched((int) x, (int) y);
        this.mPointersDown |= 1 << pointerId;
    }

    private void saveLastMotion(MotionEvent ev) {
        int pointerCount = ev.getPointerCount();
        for (int i = 0; i < pointerCount; i++) {
            int pointerId = ev.getPointerId(i);
            if (isValidPointerForActionMove(pointerId)) {
                float x = ev.getX(i);
                float y = ev.getY(i);
                this.mLastMotionX[pointerId] = x;
                this.mLastMotionY[pointerId] = y;
            }
        }
    }

    public boolean isPointerDown(int pointerId) {
        return (this.mPointersDown & (1 << pointerId)) != 0;
    }

    /* access modifiers changed from: package-private */
    public void setDragState(int state) {
        this.mParentView.removeCallbacks(this.mSetIdleRunnable);
        if (this.mDragState != state) {
            this.mDragState = state;
            this.mCallback.onViewDragStateChanged(state);
            if (this.mDragState == 0) {
                this.mCapturedView = null;
            }
        }
    }

    /* access modifiers changed from: package-private */
    public boolean tryCaptureViewForDrag(View toCapture, int pointerId) {
        if (toCapture == this.mCapturedView && this.mActivePointerId == pointerId) {
            return true;
        }
        if (toCapture == null || !this.mCallback.tryCaptureView(toCapture, pointerId)) {
            return false;
        }
        this.mActivePointerId = pointerId;
        captureChildView(toCapture, pointerId);
        return true;
    }

    /* access modifiers changed from: protected */
    public boolean canScroll(View v, boolean checkV, int dx, int dy, int x, int y) {
        View view = v;
        if (view instanceof ViewGroup) {
            ViewGroup group = (ViewGroup) view;
            int scrollX = v.getScrollX();
            int scrollY = v.getScrollY();
            for (int i = group.getChildCount() - 1; i >= 0; i--) {
                View child = group.getChildAt(i);
                if (x + scrollX >= child.getLeft() && x + scrollX < child.getRight() && y + scrollY >= child.getTop() && y + scrollY < child.getBottom()) {
                    if (canScroll(child, true, dx, dy, (x + scrollX) - child.getLeft(), (y + scrollY) - child.getTop())) {
                        return true;
                    }
                }
            }
        }
        if (!checkV) {
            int i2 = dx;
            int i3 = dy;
        } else if (view.canScrollHorizontally(-dx)) {
            int i4 = dy;
            return true;
        } else if (view.canScrollVertically(-dy)) {
            return true;
        }
        return false;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:41:0x00fd, code lost:
        if (r2 != r15) goto L_0x010c;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean shouldInterceptTouchEvent(android.view.MotionEvent r22) {
        /*
            r21 = this;
            r0 = r21
            r1 = r22
            int r2 = r22.getActionMasked()
            int r3 = r22.getActionIndex()
            if (r2 != 0) goto L_0x0011
            r21.cancel()
        L_0x0011:
            android.view.VelocityTracker r4 = r0.mVelocityTracker
            if (r4 != 0) goto L_0x001b
            android.view.VelocityTracker r4 = android.view.VelocityTracker.obtain()
            r0.mVelocityTracker = r4
        L_0x001b:
            android.view.VelocityTracker r4 = r0.mVelocityTracker
            r4.addMovement(r1)
            r4 = 2
            switch(r2) {
                case 0: goto L_0x0142;
                case 1: goto L_0x0139;
                case 2: goto L_0x007a;
                case 3: goto L_0x0139;
                case 4: goto L_0x0024;
                case 5: goto L_0x0039;
                case 6: goto L_0x002b;
                default: goto L_0x0024;
            }
        L_0x0024:
            r16 = r2
            r17 = r3
            r5 = 0
            goto L_0x0177
        L_0x002b:
            int r4 = r1.getPointerId(r3)
            r0.clearMotionHistory(r4)
            r16 = r2
            r17 = r3
            r5 = 0
            goto L_0x0177
        L_0x0039:
            int r7 = r1.getPointerId(r3)
            float r8 = r1.getX(r3)
            float r9 = r1.getY(r3)
            r0.saveInitialMotion(r8, r9, r7)
            int r10 = r0.mDragState
            if (r10 != 0) goto L_0x005d
            int[] r4 = r0.mInitialEdgesTouched
            r4 = r4[r7]
            int r10 = r0.mTrackingEdges
            r11 = r4 & r10
            if (r11 == 0) goto L_0x0073
            androidx.customview.widget.ViewDragHelper$Callback r11 = r0.mCallback
            r10 = r10 & r4
            r11.onEdgeTouched(r10, r7)
            goto L_0x0073
        L_0x005d:
            if (r10 != r4) goto L_0x0073
            int r4 = (int) r8
            int r10 = (int) r9
            android.view.View r4 = r0.findTopChildUnder(r4, r10)
            android.view.View r10 = r0.mCapturedView
            if (r4 != r10) goto L_0x006c
            r0.tryCaptureViewForDrag(r4, r7)
        L_0x006c:
            r16 = r2
            r17 = r3
            r5 = 0
            goto L_0x0177
        L_0x0073:
            r16 = r2
            r17 = r3
            r5 = 0
            goto L_0x0177
        L_0x007a:
            float[] r4 = r0.mInitialMotionX
            if (r4 == 0) goto L_0x0133
            float[] r4 = r0.mInitialMotionY
            if (r4 != 0) goto L_0x0089
            r16 = r2
            r17 = r3
            r5 = 0
            goto L_0x0177
        L_0x0089:
            int r4 = r22.getPointerCount()
            r7 = 0
        L_0x008e:
            if (r7 >= r4) goto L_0x0128
            int r8 = r1.getPointerId(r7)
            boolean r9 = r0.isValidPointerForActionMove(r8)
            if (r9 != 0) goto L_0x00a2
            r16 = r2
            r17 = r3
            r18 = r4
            goto L_0x011e
        L_0x00a2:
            float r9 = r1.getX(r7)
            float r10 = r1.getY(r7)
            float[] r11 = r0.mInitialMotionX
            r11 = r11[r8]
            float r11 = r9 - r11
            float[] r12 = r0.mInitialMotionY
            r12 = r12[r8]
            float r12 = r10 - r12
            int r13 = (int) r9
            int r14 = (int) r10
            android.view.View r13 = r0.findTopChildUnder(r13, r14)
            if (r13 == 0) goto L_0x00c6
            boolean r14 = r0.checkTouchSlop(r13, r11, r12)
            if (r14 == 0) goto L_0x00c6
            r14 = 1
            goto L_0x00c7
        L_0x00c6:
            r14 = 0
        L_0x00c7:
            if (r14 == 0) goto L_0x0106
            int r15 = r13.getLeft()
            int r5 = (int) r11
            int r5 = r5 + r15
            androidx.customview.widget.ViewDragHelper$Callback r6 = r0.mCallback
            r16 = r2
            int r2 = (int) r11
            int r2 = r6.clampViewPositionHorizontal(r13, r5, r2)
            int r6 = r13.getTop()
            r17 = r3
            int r3 = (int) r12
            int r3 = r3 + r6
            r18 = r4
            androidx.customview.widget.ViewDragHelper$Callback r4 = r0.mCallback
            r19 = r5
            int r5 = (int) r12
            int r4 = r4.clampViewPositionVertical(r13, r3, r5)
            androidx.customview.widget.ViewDragHelper$Callback r5 = r0.mCallback
            int r5 = r5.getViewHorizontalDragRange(r13)
            r20 = r3
            androidx.customview.widget.ViewDragHelper$Callback r3 = r0.mCallback
            int r3 = r3.getViewVerticalDragRange(r13)
            if (r5 == 0) goto L_0x00ff
            if (r5 <= 0) goto L_0x010c
            if (r2 != r15) goto L_0x010c
        L_0x00ff:
            if (r3 == 0) goto L_0x012e
            if (r3 <= 0) goto L_0x010c
            if (r4 != r6) goto L_0x010c
            goto L_0x012e
        L_0x0106:
            r16 = r2
            r17 = r3
            r18 = r4
        L_0x010c:
            r0.reportNewEdgeDrags(r11, r12, r8)
            int r2 = r0.mDragState
            r3 = 1
            if (r2 != r3) goto L_0x0115
            goto L_0x012e
        L_0x0115:
            if (r14 == 0) goto L_0x011e
            boolean r2 = r0.tryCaptureViewForDrag(r13, r8)
            if (r2 == 0) goto L_0x011e
            goto L_0x012e
        L_0x011e:
            int r7 = r7 + 1
            r2 = r16
            r3 = r17
            r4 = r18
            goto L_0x008e
        L_0x0128:
            r16 = r2
            r17 = r3
            r18 = r4
        L_0x012e:
            r21.saveLastMotion(r22)
            r5 = 0
            goto L_0x0177
        L_0x0133:
            r16 = r2
            r17 = r3
            r5 = 0
            goto L_0x0177
        L_0x0139:
            r16 = r2
            r17 = r3
            r21.cancel()
            r5 = 0
            goto L_0x0177
        L_0x0142:
            r16 = r2
            r17 = r3
            float r2 = r22.getX()
            float r3 = r22.getY()
            r5 = 0
            int r6 = r1.getPointerId(r5)
            r0.saveInitialMotion(r2, r3, r6)
            int r7 = (int) r2
            int r8 = (int) r3
            android.view.View r7 = r0.findTopChildUnder(r7, r8)
            android.view.View r8 = r0.mCapturedView
            if (r7 != r8) goto L_0x0167
            int r8 = r0.mDragState
            if (r8 != r4) goto L_0x0167
            r0.tryCaptureViewForDrag(r7, r6)
        L_0x0167:
            int[] r4 = r0.mInitialEdgesTouched
            r4 = r4[r6]
            int r8 = r0.mTrackingEdges
            r9 = r4 & r8
            if (r9 == 0) goto L_0x0177
            androidx.customview.widget.ViewDragHelper$Callback r9 = r0.mCallback
            r8 = r8 & r4
            r9.onEdgeTouched(r8, r6)
        L_0x0177:
            int r2 = r0.mDragState
            r3 = 1
            if (r2 != r3) goto L_0x017d
            r5 = 1
        L_0x017d:
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.customview.widget.ViewDragHelper.shouldInterceptTouchEvent(android.view.MotionEvent):boolean");
    }

    public void processTouchEvent(MotionEvent ev) {
        int action = ev.getActionMasked();
        int actionIndex = ev.getActionIndex();
        if (action == 0) {
            cancel();
        }
        if (this.mVelocityTracker == null) {
            this.mVelocityTracker = VelocityTracker.obtain();
        }
        this.mVelocityTracker.addMovement(ev);
        switch (action) {
            case 0:
                float x = ev.getX();
                float y = ev.getY();
                int pointerId = ev.getPointerId(0);
                View toCapture = findTopChildUnder((int) x, (int) y);
                saveInitialMotion(x, y, pointerId);
                tryCaptureViewForDrag(toCapture, pointerId);
                int edgesTouched = this.mInitialEdgesTouched[pointerId];
                int i = this.mTrackingEdges;
                if ((edgesTouched & i) != 0) {
                    this.mCallback.onEdgeTouched(i & edgesTouched, pointerId);
                    return;
                }
                return;
            case 1:
                if (this.mDragState == 1) {
                    releaseViewForPointerUp();
                }
                cancel();
                return;
            case 2:
                if (this.mDragState != 1) {
                    int pointerCount = ev.getPointerCount();
                    for (int i2 = 0; i2 < pointerCount; i2++) {
                        int pointerId2 = ev.getPointerId(i2);
                        if (isValidPointerForActionMove(pointerId2)) {
                            float x2 = ev.getX(i2);
                            float y2 = ev.getY(i2);
                            float dx = x2 - this.mInitialMotionX[pointerId2];
                            float dy = y2 - this.mInitialMotionY[pointerId2];
                            reportNewEdgeDrags(dx, dy, pointerId2);
                            if (this.mDragState != 1) {
                                View toCapture2 = findTopChildUnder((int) x2, (int) y2);
                                if (checkTouchSlop(toCapture2, dx, dy) && tryCaptureViewForDrag(toCapture2, pointerId2)) {
                                }
                            }
                            saveLastMotion(ev);
                            return;
                        }
                    }
                    saveLastMotion(ev);
                    return;
                } else if (isValidPointerForActionMove(this.mActivePointerId)) {
                    int index = ev.findPointerIndex(this.mActivePointerId);
                    float x3 = ev.getX(index);
                    float y3 = ev.getY(index);
                    float[] fArr = this.mLastMotionX;
                    int i3 = this.mActivePointerId;
                    int idx = (int) (x3 - fArr[i3]);
                    int idy = (int) (y3 - this.mLastMotionY[i3]);
                    dragTo(this.mCapturedView.getLeft() + idx, this.mCapturedView.getTop() + idy, idx, idy);
                    saveLastMotion(ev);
                    return;
                } else {
                    return;
                }
            case 3:
                if (this.mDragState == 1) {
                    dispatchViewReleased(0.0f, 0.0f);
                }
                cancel();
                return;
            case 5:
                int pointerId3 = ev.getPointerId(actionIndex);
                float x4 = ev.getX(actionIndex);
                float y4 = ev.getY(actionIndex);
                saveInitialMotion(x4, y4, pointerId3);
                if (this.mDragState == 0) {
                    tryCaptureViewForDrag(findTopChildUnder((int) x4, (int) y4), pointerId3);
                    int edgesTouched2 = this.mInitialEdgesTouched[pointerId3];
                    int i4 = this.mTrackingEdges;
                    if ((edgesTouched2 & i4) != 0) {
                        this.mCallback.onEdgeTouched(i4 & edgesTouched2, pointerId3);
                        return;
                    }
                    return;
                } else if (isCapturedViewUnder((int) x4, (int) y4)) {
                    tryCaptureViewForDrag(this.mCapturedView, pointerId3);
                    return;
                } else {
                    return;
                }
            case 6:
                int pointerId4 = ev.getPointerId(actionIndex);
                if (this.mDragState == 1 && pointerId4 == this.mActivePointerId) {
                    int newActivePointer = -1;
                    int pointerCount2 = ev.getPointerCount();
                    int i5 = 0;
                    while (true) {
                        if (i5 < pointerCount2) {
                            int id = ev.getPointerId(i5);
                            if (id != this.mActivePointerId) {
                                View findTopChildUnder = findTopChildUnder((int) ev.getX(i5), (int) ev.getY(i5));
                                View view = this.mCapturedView;
                                if (findTopChildUnder == view && tryCaptureViewForDrag(view, id)) {
                                    newActivePointer = this.mActivePointerId;
                                }
                            }
                            i5++;
                        }
                    }
                    if (newActivePointer == -1) {
                        releaseViewForPointerUp();
                    }
                }
                clearMotionHistory(pointerId4);
                return;
            default:
                return;
        }
    }

    private void reportNewEdgeDrags(float dx, float dy, int pointerId) {
        int dragsStarted = 0;
        if (checkNewEdgeDrag(dx, dy, pointerId, 1)) {
            dragsStarted = 0 | 1;
        }
        if (checkNewEdgeDrag(dy, dx, pointerId, 4)) {
            dragsStarted |= 4;
        }
        if (checkNewEdgeDrag(dx, dy, pointerId, 2)) {
            dragsStarted |= 2;
        }
        if (checkNewEdgeDrag(dy, dx, pointerId, 8)) {
            dragsStarted |= 8;
        }
        if (dragsStarted != 0) {
            int[] iArr = this.mEdgeDragsInProgress;
            iArr[pointerId] = iArr[pointerId] | dragsStarted;
            this.mCallback.onEdgeDragStarted(dragsStarted, pointerId);
        }
    }

    private boolean checkNewEdgeDrag(float delta, float odelta, int pointerId, int edge) {
        float absDelta = Math.abs(delta);
        float absODelta = Math.abs(odelta);
        if (!((this.mInitialEdgesTouched[pointerId] & edge) != edge || (this.mTrackingEdges & edge) == 0 || (this.mEdgeDragsLocked[pointerId] & edge) == edge || (this.mEdgeDragsInProgress[pointerId] & edge) == edge)) {
            int i = this.mTouchSlop;
            if (absDelta > ((float) i) || absODelta > ((float) i)) {
                if (absDelta < 0.5f * absODelta && this.mCallback.onEdgeLock(edge)) {
                    int[] iArr = this.mEdgeDragsLocked;
                    iArr[pointerId] = iArr[pointerId] | edge;
                    return false;
                } else if ((this.mEdgeDragsInProgress[pointerId] & edge) != 0 || absDelta <= ((float) this.mTouchSlop)) {
                    return false;
                } else {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean checkTouchSlop(View child, float dx, float dy) {
        if (child == null) {
            return false;
        }
        boolean checkHorizontal = this.mCallback.getViewHorizontalDragRange(child) > 0;
        boolean checkVertical = this.mCallback.getViewVerticalDragRange(child) > 0;
        if (checkHorizontal && checkVertical) {
            float f = (dx * dx) + (dy * dy);
            int i = this.mTouchSlop;
            if (f > ((float) (i * i))) {
                return true;
            }
            return false;
        } else if (checkHorizontal) {
            if (Math.abs(dx) > ((float) this.mTouchSlop)) {
                return true;
            }
            return false;
        } else if (!checkVertical || Math.abs(dy) <= ((float) this.mTouchSlop)) {
            return false;
        } else {
            return true;
        }
    }

    public boolean checkTouchSlop(int directions) {
        int count = this.mInitialMotionX.length;
        for (int i = 0; i < count; i++) {
            if (checkTouchSlop(directions, i)) {
                return true;
            }
        }
        return false;
    }

    public boolean checkTouchSlop(int directions, int pointerId) {
        if (!isPointerDown(pointerId)) {
            return false;
        }
        boolean checkHorizontal = (directions & 1) == 1;
        boolean checkVertical = (directions & 2) == 2;
        float dx = this.mLastMotionX[pointerId] - this.mInitialMotionX[pointerId];
        float dy = this.mLastMotionY[pointerId] - this.mInitialMotionY[pointerId];
        if (checkHorizontal && checkVertical) {
            float f = (dx * dx) + (dy * dy);
            int i = this.mTouchSlop;
            if (f > ((float) (i * i))) {
                return true;
            }
            return false;
        } else if (checkHorizontal) {
            if (Math.abs(dx) > ((float) this.mTouchSlop)) {
                return true;
            }
            return false;
        } else if (!checkVertical || Math.abs(dy) <= ((float) this.mTouchSlop)) {
            return false;
        } else {
            return true;
        }
    }

    public boolean isEdgeTouched(int edges) {
        int count = this.mInitialEdgesTouched.length;
        for (int i = 0; i < count; i++) {
            if (isEdgeTouched(edges, i)) {
                return true;
            }
        }
        return false;
    }

    public boolean isEdgeTouched(int edges, int pointerId) {
        return isPointerDown(pointerId) && (this.mInitialEdgesTouched[pointerId] & edges) != 0;
    }

    private void releaseViewForPointerUp() {
        this.mVelocityTracker.computeCurrentVelocity(1000, this.mMaxVelocity);
        dispatchViewReleased(clampMag(this.mVelocityTracker.getXVelocity(this.mActivePointerId), this.mMinVelocity, this.mMaxVelocity), clampMag(this.mVelocityTracker.getYVelocity(this.mActivePointerId), this.mMinVelocity, this.mMaxVelocity));
    }

    private void dragTo(int left, int top, int dx, int dy) {
        int i = dx;
        int i2 = dy;
        int clampedX = left;
        int clampedY = top;
        int oldLeft = this.mCapturedView.getLeft();
        int oldTop = this.mCapturedView.getTop();
        if (i != 0) {
            clampedX = this.mCallback.clampViewPositionHorizontal(this.mCapturedView, left, i);
            ViewCompat.offsetLeftAndRight(this.mCapturedView, clampedX - oldLeft);
        } else {
            int i3 = left;
        }
        if (i2 != 0) {
            clampedY = this.mCallback.clampViewPositionVertical(this.mCapturedView, top, i2);
            ViewCompat.offsetTopAndBottom(this.mCapturedView, clampedY - oldTop);
        } else {
            int i4 = top;
        }
        if (i != 0 || i2 != 0) {
            this.mCallback.onViewPositionChanged(this.mCapturedView, clampedX, clampedY, clampedX - oldLeft, clampedY - oldTop);
        }
    }

    public boolean isCapturedViewUnder(int x, int y) {
        return isViewUnder(this.mCapturedView, x, y);
    }

    public boolean isViewUnder(View view, int x, int y) {
        if (view != null && x >= view.getLeft() && x < view.getRight() && y >= view.getTop() && y < view.getBottom()) {
            return true;
        }
        return false;
    }

    public View findTopChildUnder(int x, int y) {
        for (int i = this.mParentView.getChildCount() - 1; i >= 0; i--) {
            View child = this.mParentView.getChildAt(this.mCallback.getOrderedChildIndex(i));
            if (x >= child.getLeft() && x < child.getRight() && y >= child.getTop() && y < child.getBottom()) {
                return child;
            }
        }
        return null;
    }

    private int getEdgesTouched(int x, int y) {
        int result = 0;
        if (x < this.mParentView.getLeft() + this.mEdgeSize) {
            result = 0 | 1;
        }
        if (y < this.mParentView.getTop() + this.mEdgeSize) {
            result |= 4;
        }
        if (x > this.mParentView.getRight() - this.mEdgeSize) {
            result |= 2;
        }
        if (y > this.mParentView.getBottom() - this.mEdgeSize) {
            return result | 8;
        }
        return result;
    }

    private boolean isValidPointerForActionMove(int pointerId) {
        if (isPointerDown(pointerId)) {
            return true;
        }
        Log.e(TAG, "Ignoring pointerId=" + pointerId + " because ACTION_DOWN was not received for this pointer before ACTION_MOVE. It likely happened because  ViewDragHelper did not receive all the events in the event stream.");
        return false;
    }
}
