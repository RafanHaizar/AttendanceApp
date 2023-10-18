package androidx.emoji2.text.flatbuffer;

import java.nio.ByteBuffer;
import kotlin.UShort;

public final class ShortVector extends BaseVector {
    public ShortVector __assign(int _vector, ByteBuffer _bb) {
        __reset(_vector, 2, _bb);
        return this;
    }

    public short get(int j) {
        return this.f1048bb.getShort(__element(j));
    }

    public int getAsUnsigned(int j) {
        return get(j) & UShort.MAX_VALUE;
    }
}
