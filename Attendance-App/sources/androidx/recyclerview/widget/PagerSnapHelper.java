package androidx.recyclerview.widget;

import android.graphics.PointF;
import android.util.DisplayMetrics;
import android.view.View;
import androidx.recyclerview.widget.RecyclerView;

public class PagerSnapHelper extends SnapHelper {
    private static final int MAX_SCROLL_ON_FLING_DURATION = 100;
    private OrientationHelper mHorizontalHelper;
    private OrientationHelper mVerticalHelper;

    public int[] calculateDistanceToFinalSnap(RecyclerView.LayoutManager layoutManager, View targetView) {
        int[] out = new int[2];
        if (layoutManager.canScrollHorizontally()) {
            out[0] = distanceToCenter(layoutManager, targetView, getHorizontalHelper(layoutManager));
        } else {
            out[0] = 0;
        }
        if (layoutManager.canScrollVertically()) {
            out[1] = distanceToCenter(layoutManager, targetView, getVerticalHelper(layoutManager));
        } else {
            out[1] = 0;
        }
        return out;
    }

    public View findSnapView(RecyclerView.LayoutManager layoutManager) {
        if (layoutManager.canScrollVertically()) {
            return findCenterView(layoutManager, getVerticalHelper(layoutManager));
        }
        if (layoutManager.canScrollHorizontally()) {
            return findCenterView(layoutManager, getHorizontalHelper(layoutManager));
        }
        return null;
    }

    public int findTargetSnapPosition(RecyclerView.LayoutManager layoutManager, int velocityX, int velocityY) {
        OrientationHelper orientationHelper;
        int itemCount = layoutManager.getItemCount();
        if (itemCount == 0 || (orientationHelper = getOrientationHelper(layoutManager)) == null) {
            return -1;
        }
        View closestChildBeforeCenter = null;
        int distanceBefore = Integer.MIN_VALUE;
        View closestChildAfterCenter = null;
        int distanceAfter = Integer.MAX_VALUE;
        int childCount = layoutManager.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = layoutManager.getChildAt(i);
            if (child != null) {
                int distance = distanceToCenter(layoutManager, child, orientationHelper);
                if (distance <= 0 && distance > distanceBefore) {
                    distanceBefore = distance;
                    closestChildBeforeCenter = child;
                }
                if (distance >= 0 && distance < distanceAfter) {
                    distanceAfter = distance;
                    closestChildAfterCenter = child;
                }
            }
        }
        int i2 = isForwardFling(layoutManager, velocityX, velocityY);
        if (i2 != 0 && closestChildAfterCenter != null) {
            return layoutManager.getPosition(closestChildAfterCenter);
        }
        if (i2 == 0 && closestChildBeforeCenter != null) {
            return layoutManager.getPosition(closestChildBeforeCenter);
        }
        View visibleView = i2 != 0 ? closestChildBeforeCenter : closestChildAfterCenter;
        if (visibleView == null) {
            return -1;
        }
        int snapToPosition = (isReverseLayout(layoutManager) == i2 ? -1 : 1) + layoutManager.getPosition(visibleView);
        if (snapToPosition < 0 || snapToPosition >= itemCount) {
            return -1;
        }
        return snapToPosition;
    }

    private boolean isForwardFling(RecyclerView.LayoutManager layoutManager, int velocityX, int velocityY) {
        if (layoutManager.canScrollHorizontally()) {
            if (velocityX > 0) {
                return true;
            }
            return false;
        } else if (velocityY > 0) {
            return true;
        } else {
            return false;
        }
    }

    private boolean isReverseLayout(RecyclerView.LayoutManager layoutManager) {
        PointF vectorForEnd;
        int itemCount = layoutManager.getItemCount();
        if (!(layoutManager instanceof RecyclerView.SmoothScroller.ScrollVectorProvider) || (vectorForEnd = ((RecyclerView.SmoothScroller.ScrollVectorProvider) layoutManager).computeScrollVectorForPosition(itemCount - 1)) == null) {
            return false;
        }
        if (vectorForEnd.x < 0.0f || vectorForEnd.y < 0.0f) {
            return true;
        }
        return false;
    }

    /* access modifiers changed from: protected */
    public LinearSmoothScroller createSnapScroller(RecyclerView.LayoutManager layoutManager) {
        if (!(layoutManager instanceof RecyclerView.SmoothScroller.ScrollVectorProvider)) {
            return null;
        }
        return new LinearSmoothScroller(this.mRecyclerView.getContext()) {
            /* access modifiers changed from: protected */
            public void onTargetFound(View targetView, RecyclerView.State state, RecyclerView.SmoothScroller.Action action) {
                PagerSnapHelper pagerSnapHelper = PagerSnapHelper.this;
                int[] snapDistances = pagerSnapHelper.calculateDistanceToFinalSnap(pagerSnapHelper.mRecyclerView.getLayoutManager(), targetView);
                int dx = snapDistances[0];
                int dy = snapDistances[1];
                int time = calculateTimeForDeceleration(Math.max(Math.abs(dx), Math.abs(dy)));
                if (time > 0) {
                    action.update(dx, dy, time, this.mDecelerateInterpolator);
                }
            }

            /* access modifiers changed from: protected */
            public float calculateSpeedPerPixel(DisplayMetrics displayMetrics) {
                return 100.0f / ((float) displayMetrics.densityDpi);
            }

            /* access modifiers changed from: protected */
            public int calculateTimeForScrolling(int dx) {
                return Math.min(100, super.calculateTimeForScrolling(dx));
            }
        };
    }

    private int distanceToCenter(RecyclerView.LayoutManager layoutManager, View targetView, OrientationHelper helper) {
        return (helper.getDecoratedStart(targetView) + (helper.getDecoratedMeasurement(targetView) / 2)) - (helper.getStartAfterPadding() + (helper.getTotalSpace() / 2));
    }

    private View findCenterView(RecyclerView.LayoutManager layoutManager, OrientationHelper helper) {
        int childCount = layoutManager.getChildCount();
        if (childCount == 0) {
            return null;
        }
        View closestChild = null;
        int center = helper.getStartAfterPadding() + (helper.getTotalSpace() / 2);
        int absClosest = Integer.MAX_VALUE;
        for (int i = 0; i < childCount; i++) {
            View child = layoutManager.getChildAt(i);
            int absDistance = Math.abs((helper.getDecoratedStart(child) + (helper.getDecoratedMeasurement(child) / 2)) - center);
            if (absDistance < absClosest) {
                absClosest = absDistance;
                closestChild = child;
            }
        }
        return closestChild;
    }

    private OrientationHelper getOrientationHelper(RecyclerView.LayoutManager layoutManager) {
        if (layoutManager.canScrollVertically()) {
            return getVerticalHelper(layoutManager);
        }
        if (layoutManager.canScrollHorizontally()) {
            return getHorizontalHelper(layoutManager);
        }
        return null;
    }

    private OrientationHelper getVerticalHelper(RecyclerView.LayoutManager layoutManager) {
        OrientationHelper orientationHelper = this.mVerticalHelper;
        if (orientationHelper == null || orientationHelper.mLayoutManager != layoutManager) {
            this.mVerticalHelper = OrientationHelper.createVerticalHelper(layoutManager);
        }
        return this.mVerticalHelper;
    }

    private OrientationHelper getHorizontalHelper(RecyclerView.LayoutManager layoutManager) {
        OrientationHelper orientationHelper = this.mHorizontalHelper;
        if (orientationHelper == null || orientationHelper.mLayoutManager != layoutManager) {
            this.mHorizontalHelper = OrientationHelper.createHorizontalHelper(layoutManager);
        }
        return this.mHorizontalHelper;
    }
}
