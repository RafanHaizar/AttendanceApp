package androidx.emoji2.text.flatbuffer;

import java.nio.ByteBuffer;
import org.bouncycastle.asn1.cmc.BodyPartID;

public final class IntVector extends BaseVector {
    public IntVector __assign(int _vector, ByteBuffer _bb) {
        __reset(_vector, 4, _bb);
        return this;
    }

    public int get(int j) {
        return this.f1048bb.getInt(__element(j));
    }

    public long getAsUnsigned(int j) {
        return ((long) get(j)) & BodyPartID.bodyIdMax;
    }
}
