package androidx.collection;

public final class CircularIntArray {
    private int mCapacityBitmask;
    private int[] mElements;
    private int mHead;
    private int mTail;

    private void doubleCapacity() {
        int[] iArr = this.mElements;
        int n = iArr.length;
        int i = this.mHead;
        int r = n - i;
        int newCapacity = n << 1;
        if (newCapacity >= 0) {
            int[] a = new int[newCapacity];
            System.arraycopy(iArr, i, a, 0, r);
            System.arraycopy(this.mElements, 0, a, r, this.mHead);
            this.mElements = a;
            this.mHead = 0;
            this.mTail = n;
            this.mCapacityBitmask = newCapacity - 1;
            return;
        }
        throw new RuntimeException("Max array capacity exceeded");
    }

    public CircularIntArray() {
        this(8);
    }

    public CircularIntArray(int minCapacity) {
        int arrayCapacity;
        if (minCapacity < 1) {
            throw new IllegalArgumentException("capacity must be >= 1");
        } else if (minCapacity <= 1073741824) {
            if (Integer.bitCount(minCapacity) != 1) {
                arrayCapacity = Integer.highestOneBit(minCapacity - 1) << 1;
            } else {
                arrayCapacity = minCapacity;
            }
            this.mCapacityBitmask = arrayCapacity - 1;
            this.mElements = new int[arrayCapacity];
        } else {
            throw new IllegalArgumentException("capacity must be <= 2^30");
        }
    }

    public void addFirst(int e) {
        int i = (this.mHead - 1) & this.mCapacityBitmask;
        this.mHead = i;
        this.mElements[i] = e;
        if (i == this.mTail) {
            doubleCapacity();
        }
    }

    public void addLast(int e) {
        int[] iArr = this.mElements;
        int i = this.mTail;
        iArr[i] = e;
        int i2 = this.mCapacityBitmask & (i + 1);
        this.mTail = i2;
        if (i2 == this.mHead) {
            doubleCapacity();
        }
    }

    public int popFirst() {
        int i = this.mHead;
        if (i != this.mTail) {
            int result = this.mElements[i];
            this.mHead = (i + 1) & this.mCapacityBitmask;
            return result;
        }
        throw new ArrayIndexOutOfBoundsException();
    }

    public int popLast() {
        int i = this.mHead;
        int i2 = this.mTail;
        if (i != i2) {
            int t = this.mCapacityBitmask & (i2 - 1);
            int result = this.mElements[t];
            this.mTail = t;
            return result;
        }
        throw new ArrayIndexOutOfBoundsException();
    }

    public void clear() {
        this.mTail = this.mHead;
    }

    public void removeFromStart(int numOfElements) {
        if (numOfElements > 0) {
            if (numOfElements <= size()) {
                this.mHead = (this.mHead + numOfElements) & this.mCapacityBitmask;
                return;
            }
            throw new ArrayIndexOutOfBoundsException();
        }
    }

    public void removeFromEnd(int numOfElements) {
        if (numOfElements > 0) {
            if (numOfElements <= size()) {
                this.mTail = (this.mTail - numOfElements) & this.mCapacityBitmask;
                return;
            }
            throw new ArrayIndexOutOfBoundsException();
        }
    }

    public int getFirst() {
        int i = this.mHead;
        if (i != this.mTail) {
            return this.mElements[i];
        }
        throw new ArrayIndexOutOfBoundsException();
    }

    public int getLast() {
        int i = this.mHead;
        int i2 = this.mTail;
        if (i != i2) {
            return this.mElements[(i2 - 1) & this.mCapacityBitmask];
        }
        throw new ArrayIndexOutOfBoundsException();
    }

    public int get(int n) {
        if (n >= 0 && n < size()) {
            return this.mElements[(this.mHead + n) & this.mCapacityBitmask];
        }
        throw new ArrayIndexOutOfBoundsException();
    }

    public int size() {
        return (this.mTail - this.mHead) & this.mCapacityBitmask;
    }

    public boolean isEmpty() {
        return this.mHead == this.mTail;
    }
}
