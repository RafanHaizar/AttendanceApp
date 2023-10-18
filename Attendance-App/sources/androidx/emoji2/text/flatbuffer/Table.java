package androidx.emoji2.text.flatbuffer;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;
import java.util.Comparator;

public class Table {

    /* renamed from: bb */
    protected ByteBuffer f1054bb;
    protected int bb_pos;
    Utf8 utf8 = Utf8.getDefault();
    private int vtable_size;
    private int vtable_start;

    public ByteBuffer getByteBuffer() {
        return this.f1054bb;
    }

    /* access modifiers changed from: protected */
    public int __offset(int vtable_offset) {
        if (vtable_offset < this.vtable_size) {
            return this.f1054bb.getShort(this.vtable_start + vtable_offset);
        }
        return 0;
    }

    protected static int __offset(int vtable_offset, int offset, ByteBuffer bb) {
        int vtable = bb.capacity() - offset;
        return bb.getShort((vtable + vtable_offset) - bb.getInt(vtable)) + vtable;
    }

    /* access modifiers changed from: protected */
    public int __indirect(int offset) {
        return this.f1054bb.getInt(offset) + offset;
    }

    /* access modifiers changed from: protected */
    public static int __indirect(int offset, ByteBuffer bb) {
        return bb.getInt(offset) + offset;
    }

    /* access modifiers changed from: protected */
    public String __string(int offset) {
        return __string(offset, this.f1054bb, this.utf8);
    }

    protected static String __string(int offset, ByteBuffer bb, Utf8 utf82) {
        int offset2 = offset + bb.getInt(offset);
        return utf82.decodeUtf8(bb, offset2 + 4, bb.getInt(offset2));
    }

    /* access modifiers changed from: protected */
    public int __vector_len(int offset) {
        int offset2 = offset + this.bb_pos;
        return this.f1054bb.getInt(offset2 + this.f1054bb.getInt(offset2));
    }

    /* access modifiers changed from: protected */
    public int __vector(int offset) {
        int offset2 = offset + this.bb_pos;
        return this.f1054bb.getInt(offset2) + offset2 + 4;
    }

    /* access modifiers changed from: protected */
    public ByteBuffer __vector_as_bytebuffer(int vector_offset, int elem_size) {
        int o = __offset(vector_offset);
        if (o == 0) {
            return null;
        }
        ByteBuffer bb = this.f1054bb.duplicate().order(ByteOrder.LITTLE_ENDIAN);
        int vectorstart = __vector(o);
        bb.position(vectorstart);
        bb.limit((__vector_len(o) * elem_size) + vectorstart);
        return bb;
    }

    /* access modifiers changed from: protected */
    public ByteBuffer __vector_in_bytebuffer(ByteBuffer bb, int vector_offset, int elem_size) {
        int o = __offset(vector_offset);
        if (o == 0) {
            return null;
        }
        int vectorstart = __vector(o);
        bb.rewind();
        bb.limit((__vector_len(o) * elem_size) + vectorstart);
        bb.position(vectorstart);
        return bb;
    }

    /* access modifiers changed from: protected */
    public Table __union(Table t, int offset) {
        return __union(t, offset, this.f1054bb);
    }

    protected static Table __union(Table t, int offset, ByteBuffer bb) {
        t.__reset(__indirect(offset, bb), bb);
        return t;
    }

    protected static boolean __has_identifier(ByteBuffer bb, String ident) {
        if (ident.length() == 4) {
            for (int i = 0; i < 4; i++) {
                if (ident.charAt(i) != ((char) bb.get(bb.position() + 4 + i))) {
                    return false;
                }
            }
            return true;
        }
        throw new AssertionError("FlatBuffers: file identifier must be length 4");
    }

    /* access modifiers changed from: protected */
    public void sortTables(int[] offsets, final ByteBuffer bb) {
        Integer[] off = new Integer[offsets.length];
        for (int i = 0; i < offsets.length; i++) {
            off[i] = Integer.valueOf(offsets[i]);
        }
        Arrays.sort(off, new Comparator<Integer>() {
            public int compare(Integer o1, Integer o2) {
                return Table.this.keysCompare(o1, o2, bb);
            }
        });
        for (int i2 = 0; i2 < offsets.length; i2++) {
            offsets[i2] = off[i2].intValue();
        }
    }

    /* access modifiers changed from: protected */
    public int keysCompare(Integer o1, Integer o2, ByteBuffer bb) {
        return 0;
    }

    protected static int compareStrings(int offset_1, int offset_2, ByteBuffer bb) {
        int offset_12 = offset_1 + bb.getInt(offset_1);
        int offset_22 = offset_2 + bb.getInt(offset_2);
        int len_1 = bb.getInt(offset_12);
        int len_2 = bb.getInt(offset_22);
        int startPos_1 = offset_12 + 4;
        int startPos_2 = offset_22 + 4;
        int len = Math.min(len_1, len_2);
        for (int i = 0; i < len; i++) {
            if (bb.get(i + startPos_1) != bb.get(i + startPos_2)) {
                return bb.get(i + startPos_1) - bb.get(i + startPos_2);
            }
        }
        return len_1 - len_2;
    }

    protected static int compareStrings(int offset_1, byte[] key, ByteBuffer bb) {
        int offset_12 = offset_1 + bb.getInt(offset_1);
        int len_1 = bb.getInt(offset_12);
        int len_2 = key.length;
        int startPos_1 = offset_12 + 4;
        int len = Math.min(len_1, len_2);
        for (int i = 0; i < len; i++) {
            if (bb.get(i + startPos_1) != key[i]) {
                return bb.get(i + startPos_1) - key[i];
            }
        }
        return len_1 - len_2;
    }

    /* access modifiers changed from: protected */
    public void __reset(int _i, ByteBuffer _bb) {
        this.f1054bb = _bb;
        if (_bb != null) {
            this.bb_pos = _i;
            int i = _i - _bb.getInt(_i);
            this.vtable_start = i;
            this.vtable_size = this.f1054bb.getShort(i);
            return;
        }
        this.bb_pos = 0;
        this.vtable_start = 0;
        this.vtable_size = 0;
    }

    public void __reset() {
        __reset(0, (ByteBuffer) null);
    }
}
