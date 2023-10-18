package androidx.collection;

public final class CircularArray<E> {
    private int mCapacityBitmask;
    private E[] mElements;
    private int mHead;
    private int mTail;

    private void doubleCapacity() {
        E[] eArr = this.mElements;
        int n = eArr.length;
        int i = this.mHead;
        int r = n - i;
        int newCapacity = n << 1;
        if (newCapacity >= 0) {
            E[] eArr2 = new Object[newCapacity];
            System.arraycopy(eArr, i, eArr2, 0, r);
            System.arraycopy(this.mElements, 0, eArr2, r, this.mHead);
            this.mElements = (Object[]) eArr2;
            this.mHead = 0;
            this.mTail = n;
            this.mCapacityBitmask = newCapacity - 1;
            return;
        }
        throw new RuntimeException("Max array capacity exceeded");
    }

    public CircularArray() {
        this(8);
    }

    public CircularArray(int minCapacity) {
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
            this.mElements = (Object[]) new Object[arrayCapacity];
        } else {
            throw new IllegalArgumentException("capacity must be <= 2^30");
        }
    }

    public void addFirst(E e) {
        int i = (this.mHead - 1) & this.mCapacityBitmask;
        this.mHead = i;
        this.mElements[i] = e;
        if (i == this.mTail) {
            doubleCapacity();
        }
    }

    public void addLast(E e) {
        E[] eArr = this.mElements;
        int i = this.mTail;
        eArr[i] = e;
        int i2 = this.mCapacityBitmask & (i + 1);
        this.mTail = i2;
        if (i2 == this.mHead) {
            doubleCapacity();
        }
    }

    public E popFirst() {
        int i = this.mHead;
        if (i != this.mTail) {
            E[] eArr = this.mElements;
            E result = eArr[i];
            eArr[i] = null;
            this.mHead = (i + 1) & this.mCapacityBitmask;
            return result;
        }
        throw new ArrayIndexOutOfBoundsException();
    }

    public E popLast() {
        int i = this.mHead;
        int i2 = this.mTail;
        if (i != i2) {
            int t = this.mCapacityBitmask & (i2 - 1);
            E[] eArr = this.mElements;
            E result = eArr[t];
            eArr[t] = null;
            this.mTail = t;
            return result;
        }
        throw new ArrayIndexOutOfBoundsException();
    }

    public void clear() {
        removeFromStart(size());
    }

    public void removeFromStart(int numOfElements) {
        if (numOfElements > 0) {
            if (numOfElements <= size()) {
                int end = this.mElements.length;
                int i = this.mHead;
                if (numOfElements < end - i) {
                    end = i + numOfElements;
                }
                for (int i2 = this.mHead; i2 < end; i2++) {
                    this.mElements[i2] = null;
                }
                int i3 = this.mHead;
                int removed = end - i3;
                int numOfElements2 = numOfElements - removed;
                this.mHead = (i3 + removed) & this.mCapacityBitmask;
                if (numOfElements2 > 0) {
                    for (int i4 = 0; i4 < numOfElements2; i4++) {
                        this.mElements[i4] = null;
                    }
                    this.mHead = numOfElements2;
                    return;
                }
                return;
            }
            throw new ArrayIndexOutOfBoundsException();
        }
    }

    public void removeFromEnd(int numOfElements) {
        int i;
        if (numOfElements > 0) {
            if (numOfElements <= size()) {
                int start = 0;
                int i2 = this.mTail;
                if (numOfElements < i2) {
                    start = i2 - numOfElements;
                }
                int i3 = start;
                while (true) {
                    i = this.mTail;
                    if (i3 >= i) {
                        break;
                    }
                    this.mElements[i3] = null;
                    i3++;
                }
                int i4 = i - start;
                int numOfElements2 = numOfElements - i4;
                this.mTail = i - i4;
                if (numOfElements2 > 0) {
                    int length = this.mElements.length;
                    this.mTail = length;
                    int newTail = length - numOfElements2;
                    for (int i5 = newTail; i5 < this.mTail; i5++) {
                        this.mElements[i5] = null;
                    }
                    this.mTail = newTail;
                    return;
                }
                return;
            }
            throw new ArrayIndexOutOfBoundsException();
        }
    }

    public E getFirst() {
        int i = this.mHead;
        if (i != this.mTail) {
            return this.mElements[i];
        }
        throw new ArrayIndexOutOfBoundsException();
    }

    public E getLast() {
        int i = this.mHead;
        int i2 = this.mTail;
        if (i != i2) {
            return this.mElements[(i2 - 1) & this.mCapacityBitmask];
        }
        throw new ArrayIndexOutOfBoundsException();
    }

    public E get(int n) {
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
