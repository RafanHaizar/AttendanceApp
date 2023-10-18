package androidx.recyclerview.widget;

import android.graphics.PointF;
import android.view.View;
import androidx.recyclerview.widget.RecyclerView;

public class LinearSnapHelper extends SnapHelper {
    private static final float INVALID_DISTANCE = 1.0f;
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

    public int findTargetSnapPosition(RecyclerView.LayoutManager layoutManager, int velocityX, int velocityY) {
        int itemCount;
        View currentView;
        int currentPosition;
        PointF vectorForEnd;
        int hDeltaJump;
        int vDeltaJump;
        if (!(layoutManager instanceof RecyclerView.SmoothScroller.ScrollVectorProvider) || (itemCount = layoutManager.getItemCount()) == 0 || (currentView = findSnapView(layoutManager)) == null || (currentPosition = layoutManager.getPosition(currentView)) == -1 || (vectorForEnd = ((RecyclerView.SmoothScroller.ScrollVectorProvider) layoutManager).computeScrollVectorForPosition(itemCount - 1)) == null) {
            return -1;
        }
        if (layoutManager.canScrollHorizontally()) {
            hDeltaJump = estimateNextPositionDiffForFling(layoutManager, getHorizontalHelper(layoutManager), velocityX, 0);
            if (vectorForEnd.x < 0.0f) {
                hDeltaJump = -hDeltaJump;
            }
        } else {
            hDeltaJump = 0;
        }
        if (layoutManager.canScrollVertically()) {
            vDeltaJump = estimateNextPositionDiffForFling(layoutManager, getVerticalHelper(layoutManager), 0, velocityY);
            if (vectorForEnd.y < 0.0f) {
                vDeltaJump = -vDeltaJump;
            }
        } else {
            vDeltaJump = 0;
        }
        int deltaJump = layoutManager.canScrollVertically() ? vDeltaJump : hDeltaJump;
        if (deltaJump == 0) {
            return -1;
        }
        int targetPos = currentPosition + deltaJump;
        if (targetPos < 0) {
            targetPos = 0;
        }
        if (targetPos >= itemCount) {
            return itemCount - 1;
        }
        return targetPos;
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

    private int distanceToCenter(RecyclerView.LayoutManager layoutManager, View targetView, OrientationHelper helper) {
        return (helper.getDecoratedStart(targetView) + (helper.getDecoratedMeasurement(targetView) / 2)) - (helper.getStartAfterPadding() + (helper.getTotalSpace() / 2));
    }

    private int estimateNextPositionDiffForFling(RecyclerView.LayoutManager layoutManager, OrientationHelper helper, int velocityX, int velocityY) {
        int[] distances = calculateScrollDistance(velocityX, velocityY);
        float distancePerChild = computeDistancePerChild(layoutManager, helper);
        if (distancePerChild <= 0.0f) {
            return 0;
        }
        return Math.round(((float) (Math.abs(distances[0]) > Math.abs(distances[1]) ? distances[0] : distances[1])) / distancePerChild);
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

    private float computeDistancePerChild(RecyclerView.LayoutManager layoutManager, OrientationHelper helper) {
        int distance;
        View minPosView = null;
        View maxPosView = null;
        int minPos = Integer.MAX_VALUE;
        int maxPos = Integer.MIN_VALUE;
        int childCount = layoutManager.getChildCount();
        if (childCount == 0) {
            return 1.0f;
        }
        for (int i = 0; i < childCount; i++) {
            View child = layoutManager.getChildAt(i);
            int pos = layoutManager.getPosition(child);
            if (pos != -1) {
                if (pos < minPos) {
                    minPos = pos;
                    minPosView = child;
                }
                if (pos > maxPos) {
                    maxPos = pos;
                    maxPosView = child;
                }
            }
        }
        if (minPosView == null || maxPosView == null || (distance = Math.max(helper.getDecoratedEnd(minPosView), helper.getDecoratedEnd(maxPosView)) - Math.min(helper.getDecoratedStart(minPosView), helper.getDecoratedStart(maxPosView))) == 0) {
            return 1.0f;
        }
        return (((float) distance) * 1.0f) / ((float) ((maxPos - minPos) + 1));
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
