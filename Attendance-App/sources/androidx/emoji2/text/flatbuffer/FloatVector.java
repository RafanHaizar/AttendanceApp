package androidx.emoji2.text.flatbuffer;

import java.nio.ByteBuffer;

public final class FloatVector extends BaseVector {
    public FloatVector __assign(int _vector, ByteBuffer _bb) {
        __reset(_vector, 4, _bb);
        return this;
    }

    public float get(int j) {
        return this.f1048bb.getFloat(__element(j));
    }
}
