package androidx.emoji2.text.flatbuffer;

import java.io.IOException;
import java.io.InputStream;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;
import kotlin.UByte;

public class FlatBufferBuilder {
    static final /* synthetic */ boolean $assertionsDisabled = false;

    /* renamed from: bb */
    ByteBuffer f1049bb;
    ByteBufferFactory bb_factory;
    boolean finished;
    boolean force_defaults;
    int minalign;
    boolean nested;
    int num_vtables;
    int object_start;
    int space;
    final Utf8 utf8;
    int vector_num_elems;
    int[] vtable;
    int vtable_in_use;
    int[] vtables;

    public FlatBufferBuilder(int initial_size, ByteBufferFactory bb_factory2) {
        this(initial_size, bb_factory2, (ByteBuffer) null, Utf8.getDefault());
    }

    public FlatBufferBuilder(int initial_size, ByteBufferFactory bb_factory2, ByteBuffer existing_bb, Utf8 utf82) {
        this.minalign = 1;
        this.vtable = null;
        this.vtable_in_use = 0;
        this.nested = false;
        this.finished = false;
        this.vtables = new int[16];
        this.num_vtables = 0;
        this.vector_num_elems = 0;
        this.force_defaults = false;
        initial_size = initial_size <= 0 ? 1 : initial_size;
        this.bb_factory = bb_factory2;
        if (existing_bb != null) {
            this.f1049bb = existing_bb;
            existing_bb.clear();
            this.f1049bb.order(ByteOrder.LITTLE_ENDIAN);
        } else {
            this.f1049bb = bb_factory2.newByteBuffer(initial_size);
        }
        this.utf8 = utf82;
        this.space = this.f1049bb.capacity();
    }

    public FlatBufferBuilder(int initial_size) {
        this(initial_size, HeapByteBufferFactory.INSTANCE, (ByteBuffer) null, Utf8.getDefault());
    }

    public FlatBufferBuilder() {
        this(1024);
    }

    public FlatBufferBuilder(ByteBuffer existing_bb, ByteBufferFactory bb_factory2) {
        this(existing_bb.capacity(), bb_factory2, existing_bb, Utf8.getDefault());
    }

    public FlatBufferBuilder(ByteBuffer existing_bb) {
        this(existing_bb, (ByteBufferFactory) new HeapByteBufferFactory());
    }

    public FlatBufferBuilder init(ByteBuffer existing_bb, ByteBufferFactory bb_factory2) {
        this.bb_factory = bb_factory2;
        this.f1049bb = existing_bb;
        existing_bb.clear();
        this.f1049bb.order(ByteOrder.LITTLE_ENDIAN);
        this.minalign = 1;
        this.space = this.f1049bb.capacity();
        this.vtable_in_use = 0;
        this.nested = false;
        this.finished = false;
        this.object_start = 0;
        this.num_vtables = 0;
        this.vector_num_elems = 0;
        return this;
    }

    public static abstract class ByteBufferFactory {
        public abstract ByteBuffer newByteBuffer(int i);

        public void releaseByteBuffer(ByteBuffer bb) {
        }
    }

    public static final class HeapByteBufferFactory extends ByteBufferFactory {
        public static final HeapByteBufferFactory INSTANCE = new HeapByteBufferFactory();

        public ByteBuffer newByteBuffer(int capacity) {
            return ByteBuffer.allocate(capacity).order(ByteOrder.LITTLE_ENDIAN);
        }
    }

    public static boolean isFieldPresent(Table table, int offset) {
        return table.__offset(offset) != 0;
    }

    public void clear() {
        this.space = this.f1049bb.capacity();
        this.f1049bb.clear();
        this.minalign = 1;
        while (true) {
            int i = this.vtable_in_use;
            if (i > 0) {
                int[] iArr = this.vtable;
                int i2 = i - 1;
                this.vtable_in_use = i2;
                iArr[i2] = 0;
            } else {
                this.vtable_in_use = 0;
                this.nested = false;
                this.finished = false;
                this.object_start = 0;
                this.num_vtables = 0;
                this.vector_num_elems = 0;
                return;
            }
        }
    }

    static ByteBuffer growByteBuffer(ByteBuffer bb, ByteBufferFactory bb_factory2) {
        int old_buf_size = bb.capacity();
        if ((-1073741824 & old_buf_size) == 0) {
            int new_buf_size = old_buf_size == 0 ? 1 : old_buf_size << 1;
            bb.position(0);
            ByteBuffer nbb = bb_factory2.newByteBuffer(new_buf_size);
            nbb.position(nbb.clear().capacity() - old_buf_size);
            nbb.put(bb);
            return nbb;
        }
        throw new AssertionError("FlatBuffers: cannot grow buffer beyond 2 gigabytes.");
    }

    public int offset() {
        return this.f1049bb.capacity() - this.space;
    }

    public void pad(int byte_size) {
        for (int i = 0; i < byte_size; i++) {
            ByteBuffer byteBuffer = this.f1049bb;
            int i2 = this.space - 1;
            this.space = i2;
            byteBuffer.put(i2, (byte) 0);
        }
    }

    public void prep(int size, int additional_bytes) {
        if (size > this.minalign) {
            this.minalign = size;
        }
        int align_size = ((((this.f1049bb.capacity() - this.space) + additional_bytes) ^ -1) + 1) & (size - 1);
        while (this.space < align_size + size + additional_bytes) {
            int old_buf_size = this.f1049bb.capacity();
            ByteBuffer old = this.f1049bb;
            ByteBuffer growByteBuffer = growByteBuffer(old, this.bb_factory);
            this.f1049bb = growByteBuffer;
            if (old != growByteBuffer) {
                this.bb_factory.releaseByteBuffer(old);
            }
            this.space += this.f1049bb.capacity() - old_buf_size;
        }
        pad(align_size);
    }

    public void putBoolean(boolean x) {
        ByteBuffer byteBuffer = this.f1049bb;
        int i = this.space - 1;
        this.space = i;
        byteBuffer.put(i, x ? (byte) 1 : 0);
    }

    public void putByte(byte x) {
        ByteBuffer byteBuffer = this.f1049bb;
        int i = this.space - 1;
        this.space = i;
        byteBuffer.put(i, x);
    }

    public void putShort(short x) {
        ByteBuffer byteBuffer = this.f1049bb;
        int i = this.space - 2;
        this.space = i;
        byteBuffer.putShort(i, x);
    }

    public void putInt(int x) {
        ByteBuffer byteBuffer = this.f1049bb;
        int i = this.space - 4;
        this.space = i;
        byteBuffer.putInt(i, x);
    }

    public void putLong(long x) {
        ByteBuffer byteBuffer = this.f1049bb;
        int i = this.space - 8;
        this.space = i;
        byteBuffer.putLong(i, x);
    }

    public void putFloat(float x) {
        ByteBuffer byteBuffer = this.f1049bb;
        int i = this.space - 4;
        this.space = i;
        byteBuffer.putFloat(i, x);
    }

    public void putDouble(double x) {
        ByteBuffer byteBuffer = this.f1049bb;
        int i = this.space - 8;
        this.space = i;
        byteBuffer.putDouble(i, x);
    }

    public void addBoolean(boolean x) {
        prep(1, 0);
        putBoolean(x);
    }

    public void addByte(byte x) {
        prep(1, 0);
        putByte(x);
    }

    public void addShort(short x) {
        prep(2, 0);
        putShort(x);
    }

    public void addInt(int x) {
        prep(4, 0);
        putInt(x);
    }

    public void addLong(long x) {
        prep(8, 0);
        putLong(x);
    }

    public void addFloat(float x) {
        prep(4, 0);
        putFloat(x);
    }

    public void addDouble(double x) {
        prep(8, 0);
        putDouble(x);
    }

    public void addOffset(int off) {
        prep(4, 0);
        if (off <= offset()) {
            putInt((offset() - off) + 4);
            return;
        }
        throw new AssertionError();
    }

    public void startVector(int elem_size, int num_elems, int alignment) {
        notNested();
        this.vector_num_elems = num_elems;
        prep(4, elem_size * num_elems);
        prep(alignment, elem_size * num_elems);
        this.nested = true;
    }

    public int endVector() {
        if (this.nested) {
            this.nested = false;
            putInt(this.vector_num_elems);
            return offset();
        }
        throw new AssertionError("FlatBuffers: endVector called without startVector");
    }

    public ByteBuffer createUnintializedVector(int elem_size, int num_elems, int alignment) {
        int length = elem_size * num_elems;
        startVector(elem_size, num_elems, alignment);
        ByteBuffer byteBuffer = this.f1049bb;
        int i = this.space - length;
        this.space = i;
        byteBuffer.position(i);
        ByteBuffer copy = this.f1049bb.slice().order(ByteOrder.LITTLE_ENDIAN);
        copy.limit(length);
        return copy;
    }

    public int createVectorOfTables(int[] offsets) {
        notNested();
        startVector(4, offsets.length, 4);
        for (int i = offsets.length - 1; i >= 0; i--) {
            addOffset(offsets[i]);
        }
        return endVector();
    }

    public <T extends Table> int createSortedVectorOfTables(T obj, int[] offsets) {
        obj.sortTables(offsets, this.f1049bb);
        return createVectorOfTables(offsets);
    }

    public int createString(CharSequence s) {
        int length = this.utf8.encodedLength(s);
        addByte((byte) 0);
        startVector(1, length, 1);
        ByteBuffer byteBuffer = this.f1049bb;
        int i = this.space - length;
        this.space = i;
        byteBuffer.position(i);
        this.utf8.encodeUtf8(s, this.f1049bb);
        return endVector();
    }

    public int createString(ByteBuffer s) {
        int length = s.remaining();
        addByte((byte) 0);
        startVector(1, length, 1);
        ByteBuffer byteBuffer = this.f1049bb;
        int i = this.space - length;
        this.space = i;
        byteBuffer.position(i);
        this.f1049bb.put(s);
        return endVector();
    }

    public int createByteVector(byte[] arr) {
        int length = arr.length;
        startVector(1, length, 1);
        ByteBuffer byteBuffer = this.f1049bb;
        int i = this.space - length;
        this.space = i;
        byteBuffer.position(i);
        this.f1049bb.put(arr);
        return endVector();
    }

    public int createByteVector(byte[] arr, int offset, int length) {
        startVector(1, length, 1);
        ByteBuffer byteBuffer = this.f1049bb;
        int i = this.space - length;
        this.space = i;
        byteBuffer.position(i);
        this.f1049bb.put(arr, offset, length);
        return endVector();
    }

    public int createByteVector(ByteBuffer byteBuffer) {
        int length = byteBuffer.remaining();
        startVector(1, length, 1);
        ByteBuffer byteBuffer2 = this.f1049bb;
        int i = this.space - length;
        this.space = i;
        byteBuffer2.position(i);
        this.f1049bb.put(byteBuffer);
        return endVector();
    }

    public void finished() {
        if (!this.finished) {
            throw new AssertionError("FlatBuffers: you can only access the serialized buffer after it has been finished by FlatBufferBuilder.finish().");
        }
    }

    public void notNested() {
        if (this.nested) {
            throw new AssertionError("FlatBuffers: object serialization must not be nested.");
        }
    }

    public void Nested(int obj) {
        if (obj != offset()) {
            throw new AssertionError("FlatBuffers: struct must be serialized inline.");
        }
    }

    public void startTable(int numfields) {
        notNested();
        int[] iArr = this.vtable;
        if (iArr == null || iArr.length < numfields) {
            this.vtable = new int[numfields];
        }
        this.vtable_in_use = numfields;
        Arrays.fill(this.vtable, 0, numfields, 0);
        this.nested = true;
        this.object_start = offset();
    }

    public void addBoolean(int o, boolean x, boolean d) {
        if (this.force_defaults || x != d) {
            addBoolean(x);
            slot(o);
        }
    }

    public void addByte(int o, byte x, int d) {
        if (this.force_defaults || x != d) {
            addByte(x);
            slot(o);
        }
    }

    public void addShort(int o, short x, int d) {
        if (this.force_defaults || x != d) {
            addShort(x);
            slot(o);
        }
    }

    public void addInt(int o, int x, int d) {
        if (this.force_defaults || x != d) {
            addInt(x);
            slot(o);
        }
    }

    public void addLong(int o, long x, long d) {
        if (this.force_defaults || x != d) {
            addLong(x);
            slot(o);
        }
    }

    public void addFloat(int o, float x, double d) {
        if (this.force_defaults || ((double) x) != d) {
            addFloat(x);
            slot(o);
        }
    }

    public void addDouble(int o, double x, double d) {
        if (this.force_defaults || x != d) {
            addDouble(x);
            slot(o);
        }
    }

    public void addOffset(int o, int x, int d) {
        if (this.force_defaults || x != d) {
            addOffset(x);
            slot(o);
        }
    }

    public void addStruct(int voffset, int x, int d) {
        if (x != d) {
            Nested(x);
            slot(voffset);
        }
    }

    public void slot(int voffset) {
        this.vtable[voffset] = offset();
    }

    public int endTable() {
        if (this.vtable == null || !this.nested) {
            throw new AssertionError("FlatBuffers: endTable called without startTable");
        }
        addInt(0);
        int vtableloc = offset();
        int i = this.vtable_in_use - 1;
        while (i >= 0 && this.vtable[i] == 0) {
            i--;
        }
        int trimmed_size = i + 1;
        while (i >= 0) {
            int i2 = this.vtable[i];
            addShort((short) (i2 != 0 ? vtableloc - i2 : 0));
            i--;
        }
        addShort((short) (vtableloc - this.object_start));
        addShort((short) ((trimmed_size + 2) * 2));
        int existing_vtable = 0;
        int i3 = 0;
        loop2:
        while (true) {
            if (i3 >= this.num_vtables) {
                break;
            }
            int vt1 = this.f1049bb.capacity() - this.vtables[i3];
            int vt2 = this.space;
            short len = this.f1049bb.getShort(vt1);
            if (len == this.f1049bb.getShort(vt2)) {
                int j = 2;
                while (j < len) {
                    if (this.f1049bb.getShort(vt1 + j) == this.f1049bb.getShort(vt2 + j)) {
                        j += 2;
                    }
                }
                existing_vtable = this.vtables[i3];
                break loop2;
            }
            i3++;
        }
        if (existing_vtable != 0) {
            int capacity = this.f1049bb.capacity() - vtableloc;
            this.space = capacity;
            this.f1049bb.putInt(capacity, existing_vtable - vtableloc);
        } else {
            int i4 = this.num_vtables;
            int[] iArr = this.vtables;
            if (i4 == iArr.length) {
                this.vtables = Arrays.copyOf(iArr, i4 * 2);
            }
            int[] iArr2 = this.vtables;
            int i5 = this.num_vtables;
            this.num_vtables = i5 + 1;
            iArr2[i5] = offset();
            ByteBuffer byteBuffer = this.f1049bb;
            byteBuffer.putInt(byteBuffer.capacity() - vtableloc, offset() - vtableloc);
        }
        this.nested = false;
        return vtableloc;
    }

    public void required(int table, int field) {
        int table_start = this.f1049bb.capacity() - table;
        if (!(this.f1049bb.getShort((table_start - this.f1049bb.getInt(table_start)) + field) != 0)) {
            throw new AssertionError("FlatBuffers: field " + field + " must be set");
        }
    }

    /* access modifiers changed from: protected */
    public void finish(int root_table, boolean size_prefix) {
        prep(this.minalign, (size_prefix ? 4 : 0) + 4);
        addOffset(root_table);
        if (size_prefix) {
            addInt(this.f1049bb.capacity() - this.space);
        }
        this.f1049bb.position(this.space);
        this.finished = true;
    }

    public void finish(int root_table) {
        finish(root_table, false);
    }

    public void finishSizePrefixed(int root_table) {
        finish(root_table, true);
    }

    /* access modifiers changed from: protected */
    public void finish(int root_table, String file_identifier, boolean size_prefix) {
        prep(this.minalign, (size_prefix ? 4 : 0) + 8);
        if (file_identifier.length() == 4) {
            for (int i = 3; i >= 0; i--) {
                addByte((byte) file_identifier.charAt(i));
            }
            finish(root_table, size_prefix);
            return;
        }
        throw new AssertionError("FlatBuffers: file identifier must be length 4");
    }

    public void finish(int root_table, String file_identifier) {
        finish(root_table, file_identifier, false);
    }

    public void finishSizePrefixed(int root_table, String file_identifier) {
        finish(root_table, file_identifier, true);
    }

    public FlatBufferBuilder forceDefaults(boolean forceDefaults) {
        this.force_defaults = forceDefaults;
        return this;
    }

    public ByteBuffer dataBuffer() {
        finished();
        return this.f1049bb;
    }

    @Deprecated
    private int dataStart() {
        finished();
        return this.space;
    }

    public byte[] sizedByteArray(int start, int length) {
        finished();
        byte[] array = new byte[length];
        this.f1049bb.position(start);
        this.f1049bb.get(array);
        return array;
    }

    public byte[] sizedByteArray() {
        return sizedByteArray(this.space, this.f1049bb.capacity() - this.space);
    }

    public InputStream sizedInputStream() {
        finished();
        ByteBuffer duplicate = this.f1049bb.duplicate();
        duplicate.position(this.space);
        duplicate.limit(this.f1049bb.capacity());
        return new ByteBufferBackedInputStream(duplicate);
    }

    static class ByteBufferBackedInputStream extends InputStream {
        ByteBuffer buf;

        public ByteBufferBackedInputStream(ByteBuffer buf2) {
            this.buf = buf2;
        }

        public int read() throws IOException {
            try {
                return this.buf.get() & UByte.MAX_VALUE;
            } catch (BufferUnderflowException e) {
                return -1;
            }
        }
    }
}
