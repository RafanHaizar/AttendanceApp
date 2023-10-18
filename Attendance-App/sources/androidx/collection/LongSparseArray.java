package androidx.collection;

public class LongSparseArray<E> implements Cloneable {
    private static final Object DELETED = new Object();
    private boolean mGarbage;
    private long[] mKeys;
    private int mSize;
    private Object[] mValues;

    public LongSparseArray() {
        this(10);
    }

    public LongSparseArray(int initialCapacity) {
        this.mGarbage = false;
        if (initialCapacity == 0) {
            this.mKeys = ContainerHelpers.EMPTY_LONGS;
            this.mValues = ContainerHelpers.EMPTY_OBJECTS;
            return;
        }
        int initialCapacity2 = ContainerHelpers.idealLongArraySize(initialCapacity);
        this.mKeys = new long[initialCapacity2];
        this.mValues = new Object[initialCapacity2];
    }

    public LongSparseArray<E> clone() {
        try {
            LongSparseArray<E> clone = (LongSparseArray) super.clone();
            clone.mKeys = (long[]) this.mKeys.clone();
            clone.mValues = (Object[]) this.mValues.clone();
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }

    public E get(long key) {
        return get(key, (Object) null);
    }

    public E get(long key, E valueIfKeyNotFound) {
        E e;
        int i = ContainerHelpers.binarySearch(this.mKeys, this.mSize, key);
        if (i < 0 || (e = this.mValues[i]) == DELETED) {
            return valueIfKeyNotFound;
        }
        return e;
    }

    @Deprecated
    public void delete(long key) {
        remove(key);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:2:0x000a, code lost:
        r1 = r4.mValues;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void remove(long r5) {
        /*
            r4 = this;
            long[] r0 = r4.mKeys
            int r1 = r4.mSize
            int r0 = androidx.collection.ContainerHelpers.binarySearch((long[]) r0, (int) r1, (long) r5)
            if (r0 < 0) goto L_0x0017
            java.lang.Object[] r1 = r4.mValues
            r2 = r1[r0]
            java.lang.Object r3 = DELETED
            if (r2 == r3) goto L_0x0017
            r1[r0] = r3
            r1 = 1
            r4.mGarbage = r1
        L_0x0017:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.collection.LongSparseArray.remove(long):void");
    }

    public boolean remove(long key, Object value) {
        int index = indexOfKey(key);
        if (index < 0) {
            return false;
        }
        E mapValue = valueAt(index);
        if (value != mapValue && (value == null || !value.equals(mapValue))) {
            return false;
        }
        removeAt(index);
        return true;
    }

    public void removeAt(int index) {
        Object[] objArr = this.mValues;
        Object obj = objArr[index];
        Object obj2 = DELETED;
        if (obj != obj2) {
            objArr[index] = obj2;
            this.mGarbage = true;
        }
    }

    public E replace(long key, E value) {
        int index = indexOfKey(key);
        if (index < 0) {
            return null;
        }
        E[] eArr = this.mValues;
        E oldValue = eArr[index];
        eArr[index] = value;
        return oldValue;
    }

    public boolean replace(long key, E oldValue, E newValue) {
        int index = indexOfKey(key);
        if (index < 0) {
            return false;
        }
        Object mapValue = this.mValues[index];
        if (mapValue != oldValue && (oldValue == null || !oldValue.equals(mapValue))) {
            return false;
        }
        this.mValues[index] = newValue;
        return true;
    }

    /* renamed from: gc */
    private void m149gc() {
        int n = this.mSize;
        int o = 0;
        long[] keys = this.mKeys;
        Object[] values = this.mValues;
        for (int i = 0; i < n; i++) {
            Object val = values[i];
            if (val != DELETED) {
                if (i != o) {
                    keys[o] = keys[i];
                    values[o] = val;
                    values[i] = null;
                }
                o++;
            }
        }
        this.mGarbage = false;
        this.mSize = o;
    }

    public void put(long key, E value) {
        int i = ContainerHelpers.binarySearch(this.mKeys, this.mSize, key);
        if (i >= 0) {
            this.mValues[i] = value;
            return;
        }
        int i2 = i ^ -1;
        int i3 = this.mSize;
        if (i2 < i3) {
            Object[] objArr = this.mValues;
            if (objArr[i2] == DELETED) {
                this.mKeys[i2] = key;
                objArr[i2] = value;
                return;
            }
        }
        if (this.mGarbage && i3 >= this.mKeys.length) {
            m149gc();
            i2 = ContainerHelpers.binarySearch(this.mKeys, this.mSize, key) ^ -1;
        }
        int i4 = this.mSize;
        if (i4 >= this.mKeys.length) {
            int n = ContainerHelpers.idealLongArraySize(i4 + 1);
            long[] nkeys = new long[n];
            Object[] nvalues = new Object[n];
            long[] jArr = this.mKeys;
            System.arraycopy(jArr, 0, nkeys, 0, jArr.length);
            Object[] objArr2 = this.mValues;
            System.arraycopy(objArr2, 0, nvalues, 0, objArr2.length);
            this.mKeys = nkeys;
            this.mValues = nvalues;
        }
        int n2 = this.mSize;
        if (n2 - i2 != 0) {
            long[] jArr2 = this.mKeys;
            System.arraycopy(jArr2, i2, jArr2, i2 + 1, n2 - i2);
            Object[] objArr3 = this.mValues;
            System.arraycopy(objArr3, i2, objArr3, i2 + 1, this.mSize - i2);
        }
        this.mKeys[i2] = key;
        this.mValues[i2] = value;
        this.mSize++;
    }

    public void putAll(LongSparseArray<? extends E> other) {
        int size = other.size();
        for (int i = 0; i < size; i++) {
            put(other.keyAt(i), other.valueAt(i));
        }
    }

    public E putIfAbsent(long key, E value) {
        E mapValue = get(key);
        if (mapValue == null) {
            put(key, value);
        }
        return mapValue;
    }

    public int size() {
        if (this.mGarbage) {
            m149gc();
        }
        return this.mSize;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public long keyAt(int index) {
        if (this.mGarbage) {
            m149gc();
        }
        return this.mKeys[index];
    }

    public E valueAt(int index) {
        if (this.mGarbage) {
            m149gc();
        }
        return this.mValues[index];
    }

    public void setValueAt(int index, E value) {
        if (this.mGarbage) {
            m149gc();
        }
        this.mValues[index] = value;
    }

    public int indexOfKey(long key) {
        if (this.mGarbage) {
            m149gc();
        }
        return ContainerHelpers.binarySearch(this.mKeys, this.mSize, key);
    }

    public int indexOfValue(E value) {
        if (this.mGarbage) {
            m149gc();
        }
        for (int i = 0; i < this.mSize; i++) {
            if (this.mValues[i] == value) {
                return i;
            }
        }
        return -1;
    }

    public boolean containsKey(long key) {
        return indexOfKey(key) >= 0;
    }

    public boolean containsValue(E value) {
        return indexOfValue(value) >= 0;
    }

    public void clear() {
        int n = this.mSize;
        Object[] values = this.mValues;
        for (int i = 0; i < n; i++) {
            values[i] = null;
        }
        this.mSize = 0;
        this.mGarbage = false;
    }

    public void append(long key, E value) {
        int i = this.mSize;
        if (i == 0 || key > this.mKeys[i - 1]) {
            if (this.mGarbage && i >= this.mKeys.length) {
                m149gc();
            }
            int pos = this.mSize;
            if (pos >= this.mKeys.length) {
                int n = ContainerHelpers.idealLongArraySize(pos + 1);
                long[] nkeys = new long[n];
                Object[] nvalues = new Object[n];
                long[] jArr = this.mKeys;
                System.arraycopy(jArr, 0, nkeys, 0, jArr.length);
                Object[] objArr = this.mValues;
                System.arraycopy(objArr, 0, nvalues, 0, objArr.length);
                this.mKeys = nkeys;
                this.mValues = nvalues;
            }
            this.mKeys[pos] = key;
            this.mValues[pos] = value;
            this.mSize = pos + 1;
            return;
        }
        put(key, value);
    }

    public String toString() {
        if (size() <= 0) {
            return "{}";
        }
        StringBuilder buffer = new StringBuilder(this.mSize * 28);
        buffer.append('{');
        for (int i = 0; i < this.mSize; i++) {
            if (i > 0) {
                buffer.append(", ");
            }
            buffer.append(keyAt(i));
            buffer.append('=');
            Object value = valueAt(i);
            if (value != this) {
                buffer.append(value);
            } else {
                buffer.append("(this Map)");
            }
        }
        buffer.append('}');
        return buffer.toString();
    }
}
