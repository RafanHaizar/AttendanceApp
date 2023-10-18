package androidx.emoji2.text.flatbuffer;

import java.nio.ByteBuffer;

public class BaseVector {

    /* renamed from: bb */
    protected ByteBuffer f1048bb;
    private int element_size;
    private int length;
    private int vector;

    /* access modifiers changed from: protected */
    public int __vector() {
        return this.vector;
    }

    /* access modifiers changed from: protected */
    public int __element(int j) {
        return this.vector + (this.element_size * j);
    }

    /* access modifiers changed from: protected */
    public void __reset(int _vector, int _element_size, ByteBuffer _bb) {
        this.f1048bb = _bb;
        if (_bb != null) {
            this.vector = _vector;
            this.length = _bb.getInt(_vector - 4);
            this.element_size = _element_size;
            return;
        }
        this.vector = 0;
        this.length = 0;
        this.element_size = 0;
    }

    public void reset() {
        __reset(0, 0, (ByteBuffer) null);
    }

    public int length() {
        return this.length;
    }
}
