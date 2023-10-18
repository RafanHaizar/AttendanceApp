package androidx.viewpager2.widget;

import androidx.viewpager2.widget.ViewPager2;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;

final class CompositeOnPageChangeCallback extends ViewPager2.OnPageChangeCallback {
    private final List<ViewPager2.OnPageChangeCallback> mCallbacks;

    CompositeOnPageChangeCallback(int initialCapacity) {
        this.mCallbacks = new ArrayList(initialCapacity);
    }

    /* access modifiers changed from: package-private */
    public void addOnPageChangeCallback(ViewPager2.OnPageChangeCallback callback) {
        this.mCallbacks.add(callback);
    }

    /* access modifiers changed from: package-private */
    public void removeOnPageChangeCallback(ViewPager2.OnPageChangeCallback callback) {
        this.mCallbacks.remove(callback);
    }

    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        try {
            for (ViewPager2.OnPageChangeCallback callback : this.mCallbacks) {
                callback.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }
        } catch (ConcurrentModificationException ex) {
            throwCallbackListModifiedWhileInUse(ex);
        }
    }

    public void onPageSelected(int position) {
        try {
            for (ViewPager2.OnPageChangeCallback callback : this.mCallbacks) {
                callback.onPageSelected(position);
            }
        } catch (ConcurrentModificationException ex) {
            throwCallbackListModifiedWhileInUse(ex);
        }
    }

    public void onPageScrollStateChanged(int state) {
        try {
            for (ViewPager2.OnPageChangeCallback callback : this.mCallbacks) {
                callback.onPageScrollStateChanged(state);
            }
        } catch (ConcurrentModificationException ex) {
            throwCallbackListModifiedWhileInUse(ex);
        }
    }

    private void throwCallbackListModifiedWhileInUse(ConcurrentModificationException parent) {
        throw new IllegalStateException("Adding and removing callbacks during dispatch to callbacks is not supported", parent);
    }
}
