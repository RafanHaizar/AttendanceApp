package androidx.emoji2.text.flatbuffer;

import java.nio.ByteBuffer;

public final class LongVector extends BaseVector {
    public LongVector __assign(int _vector, ByteBuffer _bb) {
        __reset(_vector, 8, _bb);
        return this;
    }

    public long get(int j) {
        return this.f1048bb.getLong(__element(j));
    }
}
