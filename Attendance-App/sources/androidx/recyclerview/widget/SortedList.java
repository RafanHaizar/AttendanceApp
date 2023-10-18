package androidx.recyclerview.widget;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;

public class SortedList<T> {
    private static final int CAPACITY_GROWTH = 10;
    private static final int DELETION = 2;
    private static final int INSERTION = 1;
    public static final int INVALID_POSITION = -1;
    private static final int LOOKUP = 4;
    private static final int MIN_CAPACITY = 10;
    private BatchedCallback mBatchedCallback;
    private Callback mCallback;
    T[] mData;
    private int mNewDataStart;
    private T[] mOldData;
    private int mOldDataSize;
    private int mOldDataStart;
    private int mSize;
    private final Class<T> mTClass;

    public SortedList(Class<T> klass, Callback<T> callback) {
        this(klass, callback, 10);
    }

    public SortedList(Class<T> klass, Callback<T> callback, int initialCapacity) {
        this.mTClass = klass;
        this.mData = (Object[]) Array.newInstance(klass, initialCapacity);
        this.mCallback = callback;
        this.mSize = 0;
    }

    public int size() {
        return this.mSize;
    }

    public int add(T item) {
        throwIfInMutationOperation();
        return add(item, true);
    }

    public void addAll(T[] items, boolean mayModifyInput) {
        throwIfInMutationOperation();
        if (items.length != 0) {
            if (mayModifyInput) {
                addAllInternal(items);
            } else {
                addAllInternal(copyArray(items));
            }
        }
    }

    public void addAll(T... items) {
        addAll(items, false);
    }

    public void addAll(Collection<T> items) {
        addAll(items.toArray((Object[]) Array.newInstance(this.mTClass, items.size())), true);
    }

    public void replaceAll(T[] items, boolean mayModifyInput) {
        throwIfInMutationOperation();
        if (mayModifyInput) {
            replaceAllInternal(items);
        } else {
            replaceAllInternal(copyArray(items));
        }
    }

    public void replaceAll(T... items) {
        replaceAll(items, false);
    }

    public void replaceAll(Collection<T> items) {
        replaceAll(items.toArray((Object[]) Array.newInstance(this.mTClass, items.size())), true);
    }

    private void addAllInternal(T[] newItems) {
        if (newItems.length >= 1) {
            int newSize = sortAndDedup(newItems);
            if (this.mSize == 0) {
                this.mData = newItems;
                this.mSize = newSize;
                this.mCallback.onInserted(0, newSize);
                return;
            }
            merge(newItems, newSize);
        }
    }

    private void replaceAllInternal(T[] newData) {
        boolean forceBatchedUpdates = !(this.mCallback instanceof BatchedCallback);
        if (forceBatchedUpdates) {
            beginBatchedUpdates();
        }
        this.mOldDataStart = 0;
        this.mOldDataSize = this.mSize;
        this.mOldData = this.mData;
        this.mNewDataStart = 0;
        int newSize = sortAndDedup(newData);
        this.mData = (Object[]) Array.newInstance(this.mTClass, newSize);
        while (true) {
            int itemCount = this.mNewDataStart;
            if (itemCount >= newSize && this.mOldDataStart >= this.mOldDataSize) {
                break;
            }
            int i = this.mOldDataStart;
            int i2 = this.mOldDataSize;
            if (i >= i2) {
                int insertIndex = this.mNewDataStart;
                int itemCount2 = newSize - itemCount;
                System.arraycopy(newData, insertIndex, this.mData, insertIndex, itemCount2);
                this.mNewDataStart += itemCount2;
                this.mSize += itemCount2;
                this.mCallback.onInserted(insertIndex, itemCount2);
                break;
            } else if (itemCount >= newSize) {
                int itemCount3 = i2 - i;
                this.mSize -= itemCount3;
                this.mCallback.onRemoved(itemCount, itemCount3);
                break;
            } else {
                T oldItem = this.mOldData[i];
                T newItem = newData[itemCount];
                int result = this.mCallback.compare(oldItem, newItem);
                if (result < 0) {
                    replaceAllRemove();
                } else if (result > 0) {
                    replaceAllInsert(newItem);
                } else if (!this.mCallback.areItemsTheSame(oldItem, newItem)) {
                    replaceAllRemove();
                    replaceAllInsert(newItem);
                } else {
                    T[] tArr = this.mData;
                    int i3 = this.mNewDataStart;
                    tArr[i3] = newItem;
                    this.mOldDataStart++;
                    this.mNewDataStart = i3 + 1;
                    if (!this.mCallback.areContentsTheSame(oldItem, newItem)) {
                        Callback callback = this.mCallback;
                        callback.onChanged(this.mNewDataStart - 1, 1, callback.getChangePayload(oldItem, newItem));
                    }
                }
            }
        }
        this.mOldData = null;
        if (forceBatchedUpdates) {
            endBatchedUpdates();
        }
    }

    private void replaceAllInsert(T newItem) {
        T[] tArr = this.mData;
        int i = this.mNewDataStart;
        tArr[i] = newItem;
        int i2 = i + 1;
        this.mNewDataStart = i2;
        this.mSize++;
        this.mCallback.onInserted(i2 - 1, 1);
    }

    private void replaceAllRemove() {
        this.mSize--;
        this.mOldDataStart++;
        this.mCallback.onRemoved(this.mNewDataStart, 1);
    }

    private int sortAndDedup(T[] items) {
        if (items.length == 0) {
            return 0;
        }
        Arrays.sort(items, this.mCallback);
        int rangeStart = 0;
        int rangeEnd = 1;
        for (int i = 1; i < items.length; i++) {
            T currentItem = items[i];
            if (this.mCallback.compare(items[rangeStart], currentItem) == 0) {
                int sameItemPos = findSameItem(currentItem, items, rangeStart, rangeEnd);
                if (sameItemPos != -1) {
                    items[sameItemPos] = currentItem;
                } else {
                    if (rangeEnd != i) {
                        items[rangeEnd] = currentItem;
                    }
                    rangeEnd++;
                }
            } else {
                if (rangeEnd != i) {
                    items[rangeEnd] = currentItem;
                }
                rangeStart = rangeEnd;
                rangeEnd++;
            }
        }
        return rangeEnd;
    }

    private int findSameItem(T item, T[] items, int from, int to) {
        for (int pos = from; pos < to; pos++) {
            if (this.mCallback.areItemsTheSame(items[pos], item)) {
                return pos;
            }
        }
        return -1;
    }

    private void merge(T[] newData, int newDataSize) {
        boolean forceBatchedUpdates = !(this.mCallback instanceof BatchedCallback);
        if (forceBatchedUpdates) {
            beginBatchedUpdates();
        }
        this.mOldData = this.mData;
        this.mOldDataStart = 0;
        int i = this.mSize;
        this.mOldDataSize = i;
        this.mData = (Object[]) Array.newInstance(this.mTClass, i + newDataSize + 10);
        this.mNewDataStart = 0;
        int newDataStart = 0;
        while (true) {
            int i2 = this.mOldDataStart;
            int i3 = this.mOldDataSize;
            if (i2 >= i3 && newDataStart >= newDataSize) {
                break;
            } else if (i2 == i3) {
                int itemCount = newDataSize - newDataStart;
                System.arraycopy(newData, newDataStart, this.mData, this.mNewDataStart, itemCount);
                int i4 = this.mNewDataStart + itemCount;
                this.mNewDataStart = i4;
                this.mSize += itemCount;
                this.mCallback.onInserted(i4 - itemCount, itemCount);
                break;
            } else if (newDataStart == newDataSize) {
                int itemCount2 = i3 - i2;
                System.arraycopy(this.mOldData, i2, this.mData, this.mNewDataStart, itemCount2);
                this.mNewDataStart += itemCount2;
                break;
            } else {
                T oldItem = this.mOldData[i2];
                T newItem = newData[newDataStart];
                int compare = this.mCallback.compare(oldItem, newItem);
                if (compare > 0) {
                    T[] tArr = this.mData;
                    int i5 = this.mNewDataStart;
                    int i6 = i5 + 1;
                    this.mNewDataStart = i6;
                    tArr[i5] = newItem;
                    this.mSize++;
                    newDataStart++;
                    this.mCallback.onInserted(i6 - 1, 1);
                } else if (compare != 0 || !this.mCallback.areItemsTheSame(oldItem, newItem)) {
                    T[] tArr2 = this.mData;
                    int i7 = this.mNewDataStart;
                    this.mNewDataStart = i7 + 1;
                    tArr2[i7] = oldItem;
                    this.mOldDataStart++;
                } else {
                    T[] tArr3 = this.mData;
                    int i8 = this.mNewDataStart;
                    this.mNewDataStart = i8 + 1;
                    tArr3[i8] = newItem;
                    newDataStart++;
                    this.mOldDataStart++;
                    if (!this.mCallback.areContentsTheSame(oldItem, newItem)) {
                        Callback callback = this.mCallback;
                        callback.onChanged(this.mNewDataStart - 1, 1, callback.getChangePayload(oldItem, newItem));
                    }
                }
            }
        }
        this.mOldData = null;
        if (forceBatchedUpdates) {
            endBatchedUpdates();
        }
    }

    private void throwIfInMutationOperation() {
        if (this.mOldData != null) {
            throw new IllegalStateException("Data cannot be mutated in the middle of a batch update operation such as addAll or replaceAll.");
        }
    }

    public void beginBatchedUpdates() {
        throwIfInMutationOperation();
        Callback callback = this.mCallback;
        if (!(callback instanceof BatchedCallback)) {
            if (this.mBatchedCallback == null) {
                this.mBatchedCallback = new BatchedCallback(callback);
            }
            this.mCallback = this.mBatchedCallback;
        }
    }

    public void endBatchedUpdates() {
        throwIfInMutationOperation();
        Callback callback = this.mCallback;
        if (callback instanceof BatchedCallback) {
            ((BatchedCallback) callback).dispatchLastEvent();
        }
        Callback callback2 = this.mCallback;
        BatchedCallback batchedCallback = this.mBatchedCallback;
        if (callback2 == batchedCallback) {
            this.mCallback = batchedCallback.mWrappedCallback;
        }
    }

    private int add(T item, boolean notify) {
        int index = findIndexOf(item, this.mData, 0, this.mSize, 1);
        if (index == -1) {
            index = 0;
        } else if (index < this.mSize) {
            T existing = this.mData[index];
            if (this.mCallback.areItemsTheSame(existing, item)) {
                if (this.mCallback.areContentsTheSame(existing, item)) {
                    this.mData[index] = item;
                    return index;
                }
                this.mData[index] = item;
                Callback callback = this.mCallback;
                callback.onChanged(index, 1, callback.getChangePayload(existing, item));
                return index;
            }
        }
        addToData(index, item);
        if (notify) {
            this.mCallback.onInserted(index, 1);
        }
        return index;
    }

    public boolean remove(T item) {
        throwIfInMutationOperation();
        return remove(item, true);
    }

    public T removeItemAt(int index) {
        throwIfInMutationOperation();
        T item = get(index);
        removeItemAtIndex(index, true);
        return item;
    }

    private boolean remove(T item, boolean notify) {
        int index = findIndexOf(item, this.mData, 0, this.mSize, 2);
        if (index == -1) {
            return false;
        }
        removeItemAtIndex(index, notify);
        return true;
    }

    private void removeItemAtIndex(int index, boolean notify) {
        T[] tArr = this.mData;
        System.arraycopy(tArr, index + 1, tArr, index, (this.mSize - index) - 1);
        int i = this.mSize - 1;
        this.mSize = i;
        this.mData[i] = null;
        if (notify) {
            this.mCallback.onRemoved(index, 1);
        }
    }

    public void updateItemAt(int index, T item) {
        throwIfInMutationOperation();
        T existing = get(index);
        boolean contentsChanged = existing == item || !this.mCallback.areContentsTheSame(existing, item);
        if (existing == item || this.mCallback.compare(existing, item) != 0) {
            if (contentsChanged) {
                Callback callback = this.mCallback;
                callback.onChanged(index, 1, callback.getChangePayload(existing, item));
            }
            removeItemAtIndex(index, false);
            int newIndex = add(item, false);
            if (index != newIndex) {
                this.mCallback.onMoved(index, newIndex);
                return;
            }
            return;
        }
        this.mData[index] = item;
        if (contentsChanged) {
            Callback callback2 = this.mCallback;
            callback2.onChanged(index, 1, callback2.getChangePayload(existing, item));
        }
    }

    public void recalculatePositionOfItemAt(int index) {
        throwIfInMutationOperation();
        T item = get(index);
        removeItemAtIndex(index, false);
        int newIndex = add(item, false);
        if (index != newIndex) {
            this.mCallback.onMoved(index, newIndex);
        }
    }

    public T get(int index) throws IndexOutOfBoundsException {
        int i;
        if (index >= this.mSize || index < 0) {
            throw new IndexOutOfBoundsException("Asked to get item at " + index + " but size is " + this.mSize);
        }
        T[] tArr = this.mOldData;
        if (tArr == null || index < (i = this.mNewDataStart)) {
            return this.mData[index];
        }
        return tArr[(index - i) + this.mOldDataStart];
    }

    public int indexOf(T item) {
        if (this.mOldData != null) {
            int index = findIndexOf(item, this.mData, 0, this.mNewDataStart, 4);
            if (index != -1) {
                return index;
            }
            int index2 = findIndexOf(item, this.mOldData, this.mOldDataStart, this.mOldDataSize, 4);
            if (index2 != -1) {
                return (index2 - this.mOldDataStart) + this.mNewDataStart;
            }
            return -1;
        }
        return findIndexOf(item, this.mData, 0, this.mSize, 4);
    }

    private int findIndexOf(T item, T[] mData2, int left, int right, int reason) {
        while (left < right) {
            int middle = (left + right) / 2;
            T myItem = mData2[middle];
            int cmp = this.mCallback.compare(myItem, item);
            if (cmp < 0) {
                left = middle + 1;
            } else if (cmp != 0) {
                right = middle;
            } else if (this.mCallback.areItemsTheSame(myItem, item)) {
                return middle;
            } else {
                int exact = linearEqualitySearch(item, middle, left, right);
                if (reason == 1) {
                    return exact == -1 ? middle : exact;
                }
                return exact;
            }
        }
        if (reason == 1) {
            return left;
        }
        return -1;
    }

    private int linearEqualitySearch(T item, int middle, int left, int right) {
        int next = middle - 1;
        while (next >= left) {
            T nextItem = this.mData[next];
            if (this.mCallback.compare(nextItem, item) != 0) {
                break;
            } else if (this.mCallback.areItemsTheSame(nextItem, item)) {
                return next;
            } else {
                next--;
            }
        }
        for (int next2 = middle + 1; next2 < right; next2++) {
            T nextItem2 = this.mData[next2];
            if (this.mCallback.compare(nextItem2, item) != 0) {
                return -1;
            }
            if (this.mCallback.areItemsTheSame(nextItem2, item)) {
                return next2;
            }
        }
        return -1;
    }

    private void addToData(int index, T item) {
        int i = this.mSize;
        if (index <= i) {
            T[] tArr = this.mData;
            if (i == tArr.length) {
                T[] newData = (Object[]) Array.newInstance(this.mTClass, tArr.length + 10);
                System.arraycopy(this.mData, 0, newData, 0, index);
                newData[index] = item;
                System.arraycopy(this.mData, index, newData, index + 1, this.mSize - index);
                this.mData = newData;
            } else {
                System.arraycopy(tArr, index, tArr, index + 1, i - index);
                this.mData[index] = item;
            }
            this.mSize++;
            return;
        }
        throw new IndexOutOfBoundsException("cannot add item to " + index + " because size is " + this.mSize);
    }

    private T[] copyArray(T[] items) {
        T[] copy = (Object[]) Array.newInstance(this.mTClass, items.length);
        System.arraycopy(items, 0, copy, 0, items.length);
        return copy;
    }

    public void clear() {
        throwIfInMutationOperation();
        if (this.mSize != 0) {
            int prevSize = this.mSize;
            Arrays.fill(this.mData, 0, prevSize, (Object) null);
            this.mSize = 0;
            this.mCallback.onRemoved(0, prevSize);
        }
    }

    public static abstract class Callback<T2> implements Comparator<T2>, ListUpdateCallback {
        public abstract boolean areContentsTheSame(T2 t2, T2 t22);

        public abstract boolean areItemsTheSame(T2 t2, T2 t22);

        public abstract int compare(T2 t2, T2 t22);

        public abstract void onChanged(int i, int i2);

        public void onChanged(int position, int count, Object payload) {
            onChanged(position, count);
        }

        public Object getChangePayload(T2 t2, T2 t22) {
            return null;
        }
    }

    public static class BatchedCallback<T2> extends Callback<T2> {
        private final BatchingListUpdateCallback mBatchingListUpdateCallback;
        final Callback<T2> mWrappedCallback;

        public BatchedCallback(Callback<T2> wrappedCallback) {
            this.mWrappedCallback = wrappedCallback;
            this.mBatchingListUpdateCallback = new BatchingListUpdateCallback(wrappedCallback);
        }

        public int compare(T2 o1, T2 o2) {
            return this.mWrappedCallback.compare(o1, o2);
        }

        public void onInserted(int position, int count) {
            this.mBatchingListUpdateCallback.onInserted(position, count);
        }

        public void onRemoved(int position, int count) {
            this.mBatchingListUpdateCallback.onRemoved(position, count);
        }

        public void onMoved(int fromPosition, int toPosition) {
            this.mBatchingListUpdateCallback.onMoved(fromPosition, toPosition);
        }

        public void onChanged(int position, int count) {
            this.mBatchingListUpdateCallback.onChanged(position, count, (Object) null);
        }

        public void onChanged(int position, int count, Object payload) {
            this.mBatchingListUpdateCallback.onChanged(position, count, payload);
        }

        public boolean areContentsTheSame(T2 oldItem, T2 newItem) {
            return this.mWrappedCallback.areContentsTheSame(oldItem, newItem);
        }

        public boolean areItemsTheSame(T2 item1, T2 item2) {
            return this.mWrappedCallback.areItemsTheSame(item1, item2);
        }

        public Object getChangePayload(T2 item1, T2 item2) {
            return this.mWrappedCallback.getChangePayload(item1, item2);
        }

        public void dispatchLastEvent() {
            this.mBatchingListUpdateCallback.dispatchLastEvent();
        }
    }
}
