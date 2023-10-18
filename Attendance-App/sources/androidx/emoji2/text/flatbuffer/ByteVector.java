package androidx.emoji2.text.flatbuffer;

import java.nio.ByteBuffer;
import kotlin.UByte;

public final class ByteVector extends BaseVector {
    public ByteVector __assign(int vector, ByteBuffer bb) {
        __reset(vector, 1, bb);
        return this;
    }

    public byte get(int j) {
        return this.f1048bb.get(__element(j));
    }

    public int getAsUnsigned(int j) {
        return get(j) & UByte.MAX_VALUE;
    }
}
