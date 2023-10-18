package androidx.emoji2.text.flatbuffer;

import java.nio.ByteBuffer;

public final class StringVector extends BaseVector {
    private Utf8 utf8 = Utf8.getDefault();

    public StringVector __assign(int _vector, int _element_size, ByteBuffer _bb) {
        __reset(_vector, _element_size, _bb);
        return this;
    }

    public String get(int j) {
        return Table.__string(__element(j), this.f1048bb, this.utf8);
    }
}
