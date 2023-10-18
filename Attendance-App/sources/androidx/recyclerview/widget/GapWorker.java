package androidx.recyclerview.widget;

import androidx.core.p001os.TraceCompat;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.concurrent.TimeUnit;

final class GapWorker implements Runnable {
    static final ThreadLocal<GapWorker> sGapWorker = new ThreadLocal<>();
    static Comparator<Task> sTaskComparator = new Comparator<Task>() {
        public int compare(Task lhs, Task rhs) {
            if ((lhs.view == null) != (rhs.view == null)) {
                if (lhs.view == null) {
                    return 1;
                }
                return -1;
            } else if (lhs.immediate == rhs.immediate) {
                int deltaViewVelocity = rhs.viewVelocity - lhs.viewVelocity;
                if (deltaViewVelocity != 0) {
                    return deltaViewVelocity;
                }
                int deltaDistanceToItem = lhs.distanceToItem - rhs.distanceToItem;
                if (deltaDistanceToItem != 0) {
                    return deltaDistanceToItem;
                }
                return 0;
            } else if (lhs.immediate) {
                return -1;
            } else {
                return 1;
            }
        }
    };
    long mFrameIntervalNs;
    long mPostTimeNs;
    ArrayList<RecyclerView> mRecyclerViews = new ArrayList<>();
    private ArrayList<Task> mTasks = new ArrayList<>();

    GapWorker() {
    }

    static class Task {
        public int distanceToItem;
        public boolean immediate;
        public int position;
        public RecyclerView view;
        public int viewVelocity;

        Task() {
        }

        public void clear() {
            this.immediate = false;
            this.viewVelocity = 0;
            this.distanceToItem = 0;
            this.view = null;
            this.position = 0;
        }
    }

    static class LayoutPrefetchRegistryImpl implements RecyclerView.LayoutManager.LayoutPrefetchRegistry {
        int mCount;
        int[] mPrefetchArray;
        int mPrefetchDx;
        int mPrefetchDy;

        LayoutPrefetchRegistryImpl() {
        }

        /* access modifiers changed from: package-private */
        public void setPrefetchVector(int dx, int dy) {
            this.mPrefetchDx = dx;
            this.mPrefetchDy = dy;
        }

        /* access modifiers changed from: package-private */
        public void collectPrefetchPositionsFromView(RecyclerView view, boolean nested) {
            this.mCount = 0;
            int[] iArr = this.mPrefetchArray;
            if (iArr != null) {
                Arrays.fill(iArr, -1);
            }
            RecyclerView.LayoutManager layout = view.mLayout;
            if (view.mAdapter != null && layout != null && layout.isItemPrefetchEnabled()) {
                if (nested) {
                    if (!view.mAdapterHelper.hasPendingUpdates()) {
                        layout.collectInitialPrefetchPositions(view.mAdapter.getItemCount(), this);
                    }
                } else if (!view.hasPendingAdapterUpdates()) {
                    layout.collectAdjacentPrefetchPositions(this.mPrefetchDx, this.mPrefetchDy, view.mState, this);
                }
                if (this.mCount > layout.mPrefetchMaxCountObserved) {
                    layout.mPrefetchMaxCountObserved = this.mCount;
                    layout.mPrefetchMaxObservedInInitialPrefetch = nested;
                    view.mRecycler.updateViewCacheSize();
                }
            }
        }

        public void addPosition(int layoutPosition, int pixelDistance) {
            if (layoutPosition < 0) {
                throw new IllegalArgumentException("Layout positions must be non-negative");
            } else if (pixelDistance >= 0) {
                int storagePosition = this.mCount * 2;
                int[] iArr = this.mPrefetchArray;
                if (iArr == null) {
                    int[] iArr2 = new int[4];
                    this.mPrefetchArray = iArr2;
                    Arrays.fill(iArr2, -1);
                } else if (storagePosition >= iArr.length) {
                    int[] oldArray = this.mPrefetchArray;
                    int[] iArr3 = new int[(storagePosition * 2)];
                    this.mPrefetchArray = iArr3;
                    System.arraycopy(oldArray, 0, iArr3, 0, oldArray.length);
                }
                int[] oldArray2 = this.mPrefetchArray;
                oldArray2[storagePosition] = layoutPosition;
                oldArray2[storagePosition + 1] = pixelDistance;
                this.mCount++;
            } else {
                throw new IllegalArgumentException("Pixel distance must be non-negative");
            }
        }

        /* access modifiers changed from: package-private */
        public boolean lastPrefetchIncludedPosition(int position) {
            if (this.mPrefetchArray == null) {
                return false;
            }
            int count = this.mCount * 2;
            for (int i = 0; i < count; i += 2) {
                if (this.mPrefetchArray[i] == position) {
                    return true;
                }
            }
            return false;
        }

        /* access modifiers changed from: package-private */
        public void clearPrefetchPositions() {
            int[] iArr = this.mPrefetchArray;
            if (iArr != null) {
                Arrays.fill(iArr, -1);
            }
            this.mCount = 0;
        }
    }

    public void add(RecyclerView recyclerView) {
        this.mRecyclerViews.add(recyclerView);
    }

    public void remove(RecyclerView recyclerView) {
        boolean remove = this.mRecyclerViews.remove(recyclerView);
    }

    /* access modifiers changed from: package-private */
    public void postFromTraversal(RecyclerView recyclerView, int prefetchDx, int prefetchDy) {
        if (recyclerView.isAttachedToWindow() && this.mPostTimeNs == 0) {
            this.mPostTimeNs = recyclerView.getNanoTime();
            recyclerView.post(this);
        }
        recyclerView.mPrefetchRegistry.setPrefetchVector(prefetchDx, prefetchDy);
    }

    private void buildTaskList() {
        Task task;
        int viewCount = this.mRecyclerViews.size();
        int totalTaskCount = 0;
        for (int i = 0; i < viewCount; i++) {
            RecyclerView view = this.mRecyclerViews.get(i);
            if (view.getWindowVisibility() == 0) {
                view.mPrefetchRegistry.collectPrefetchPositionsFromView(view, false);
                totalTaskCount += view.mPrefetchRegistry.mCount;
            }
        }
        this.mTasks.ensureCapacity(totalTaskCount);
        int totalTaskIndex = 0;
        for (int i2 = 0; i2 < viewCount; i2++) {
            RecyclerView view2 = this.mRecyclerViews.get(i2);
            if (view2.getWindowVisibility() == 0) {
                LayoutPrefetchRegistryImpl prefetchRegistry = view2.mPrefetchRegistry;
                int viewVelocity = Math.abs(prefetchRegistry.mPrefetchDx) + Math.abs(prefetchRegistry.mPrefetchDy);
                for (int j = 0; j < prefetchRegistry.mCount * 2; j += 2) {
                    if (totalTaskIndex >= this.mTasks.size()) {
                        task = new Task();
                        this.mTasks.add(task);
                    } else {
                        task = this.mTasks.get(totalTaskIndex);
                    }
                    int distanceToItem = prefetchRegistry.mPrefetchArray[j + 1];
                    task.immediate = distanceToItem <= viewVelocity;
                    task.viewVelocity = viewVelocity;
                    task.distanceToItem = distanceToItem;
                    task.view = view2;
                    task.position = prefetchRegistry.mPrefetchArray[j];
                    totalTaskIndex++;
                }
            }
        }
        Collections.sort(this.mTasks, sTaskComparator);
    }

    static boolean isPrefetchPositionAttached(RecyclerView view, int position) {
        int childCount = view.mChildHelper.getUnfilteredChildCount();
        for (int i = 0; i < childCount; i++) {
            RecyclerView.ViewHolder holder = RecyclerView.getChildViewHolderInt(view.mChildHelper.getUnfilteredChildAt(i));
            if (holder.mPosition == position && !holder.isInvalid()) {
                return true;
            }
        }
        return false;
    }

    private RecyclerView.ViewHolder prefetchPositionWithDeadline(RecyclerView view, int position, long deadlineNs) {
        if (isPrefetchPositionAttached(view, position)) {
            return null;
        }
        RecyclerView.Recycler recycler = view.mRecycler;
        try {
            view.onEnterLayoutOrScroll();
            RecyclerView.ViewHolder holder = recycler.tryGetViewHolderForPositionByDeadline(position, false, deadlineNs);
            if (holder != null) {
                if (!holder.isBound() || holder.isInvalid()) {
                    recycler.addViewHolderToRecycledViewPool(holder, false);
                } else {
                    recycler.recycleView(holder.itemView);
                }
            }
            return holder;
        } finally {
            view.onExitLayoutOrScroll(false);
        }
    }

    private void prefetchInnerRecyclerViewWithDeadline(RecyclerView innerView, long deadlineNs) {
        if (innerView != null) {
            if (innerView.mDataSetHasChangedAfterLayout && innerView.mChildHelper.getUnfilteredChildCount() != 0) {
                innerView.removeAndRecycleViews();
            }
            LayoutPrefetchRegistryImpl innerPrefetchRegistry = innerView.mPrefetchRegistry;
            innerPrefetchRegistry.collectPrefetchPositionsFromView(innerView, true);
            if (innerPrefetchRegistry.mCount != 0) {
                try {
                    TraceCompat.beginSection("RV Nested Prefetch");
                    innerView.mState.prepareForNestedPrefetch(innerView.mAdapter);
                    for (int i = 0; i < innerPrefetchRegistry.mCount * 2; i += 2) {
                        prefetchPositionWithDeadline(innerView, innerPrefetchRegistry.mPrefetchArray[i], deadlineNs);
                    }
                } finally {
                    TraceCompat.endSection();
                }
            }
        }
    }

    private void flushTaskWithDeadline(Task task, long deadlineNs) {
        RecyclerView.ViewHolder holder = prefetchPositionWithDeadline(task.view, task.position, task.immediate ? Long.MAX_VALUE : deadlineNs);
        if (holder != null && holder.mNestedRecyclerView != null && holder.isBound() && !holder.isInvalid()) {
            prefetchInnerRecyclerViewWithDeadline((RecyclerView) holder.mNestedRecyclerView.get(), deadlineNs);
        }
    }

    private void flushTasksWithDeadline(long deadlineNs) {
        int i = 0;
        while (i < this.mTasks.size()) {
            Task task = this.mTasks.get(i);
            if (task.view != null) {
                flushTaskWithDeadline(task, deadlineNs);
                task.clear();
                i++;
            } else {
                return;
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void prefetch(long deadlineNs) {
        buildTaskList();
        flushTasksWithDeadline(deadlineNs);
    }

    public void run() {
        try {
            TraceCompat.beginSection("RV Prefetch");
            if (!this.mRecyclerViews.isEmpty()) {
                int size = this.mRecyclerViews.size();
                long latestFrameVsyncMs = 0;
                for (int i = 0; i < size; i++) {
                    RecyclerView view = this.mRecyclerViews.get(i);
                    if (view.getWindowVisibility() == 0) {
                        latestFrameVsyncMs = Math.max(view.getDrawingTime(), latestFrameVsyncMs);
                    }
                }
                if (latestFrameVsyncMs == 0) {
                    this.mPostTimeNs = 0;
                    TraceCompat.endSection();
                    return;
                }
                prefetch(TimeUnit.MILLISECONDS.toNanos(latestFrameVsyncMs) + this.mFrameIntervalNs);
                this.mPostTimeNs = 0;
                TraceCompat.endSection();
            }
        } finally {
            this.mPostTimeNs = 0;
            TraceCompat.endSection();
        }
    }
}
