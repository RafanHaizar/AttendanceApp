package androidx.emoji2.text.flatbuffer;

import androidx.emoji2.text.flatbuffer.FlexBuffers;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

public class FlexBuffersBuilder {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    public static final int BUILDER_FLAG_NONE = 0;
    public static final int BUILDER_FLAG_SHARE_ALL = 7;
    public static final int BUILDER_FLAG_SHARE_KEYS = 1;
    public static final int BUILDER_FLAG_SHARE_KEYS_AND_STRINGS = 3;
    public static final int BUILDER_FLAG_SHARE_KEY_VECTORS = 4;
    public static final int BUILDER_FLAG_SHARE_STRINGS = 2;
    private static final int WIDTH_16 = 1;
    private static final int WIDTH_32 = 2;
    private static final int WIDTH_64 = 3;
    private static final int WIDTH_8 = 0;
    /* access modifiers changed from: private */

    /* renamed from: bb */
    public final ReadWriteBuf f1052bb;
    private boolean finished;
    private final int flags;
    private Comparator<Value> keyComparator;
    private final HashMap<String, Integer> keyPool;
    private final ArrayList<Value> stack;
    private final HashMap<String, Integer> stringPool;

    public FlexBuffersBuilder(int bufSize) {
        this((ReadWriteBuf) new ArrayReadWriteBuf(bufSize), 1);
    }

    public FlexBuffersBuilder() {
        this(256);
    }

    @Deprecated
    public FlexBuffersBuilder(ByteBuffer bb, int flags2) {
        this((ReadWriteBuf) new ArrayReadWriteBuf(bb.array()), flags2);
    }

    public FlexBuffersBuilder(ReadWriteBuf bb, int flags2) {
        this.stack = new ArrayList<>();
        this.keyPool = new HashMap<>();
        this.stringPool = new HashMap<>();
        this.finished = false;
        this.keyComparator = new Comparator<Value>() {
            public int compare(Value o1, Value o2) {
                byte c1;
                byte c2;
                int ia = o1.key;
                int io = o2.key;
                do {
                    c1 = FlexBuffersBuilder.this.f1052bb.get(ia);
                    c2 = FlexBuffersBuilder.this.f1052bb.get(io);
                    if (c1 == 0) {
                        return c1 - c2;
                    }
                    ia++;
                    io++;
                } while (c1 == c2);
                return c1 - c2;
            }
        };
        this.f1052bb = bb;
        this.flags = flags2;
    }

    public FlexBuffersBuilder(ByteBuffer bb) {
        this(bb, 1);
    }

    public ReadWriteBuf getBuffer() {
        if (this.finished) {
            return this.f1052bb;
        }
        throw new AssertionError();
    }

    public void putBoolean(boolean val) {
        putBoolean((String) null, val);
    }

    public void putBoolean(String key, boolean val) {
        this.stack.add(Value.bool(putKey(key), val));
    }

    private int putKey(String key) {
        if (key == null) {
            return -1;
        }
        int pos = this.f1052bb.writePosition();
        if ((this.flags & 1) != 0) {
            Integer keyFromPool = this.keyPool.get(key);
            if (keyFromPool != null) {
                return keyFromPool.intValue();
            }
            byte[] keyBytes = key.getBytes(StandardCharsets.UTF_8);
            this.f1052bb.put(keyBytes, 0, keyBytes.length);
            this.f1052bb.put((byte) 0);
            this.keyPool.put(key, Integer.valueOf(pos));
            return pos;
        }
        byte[] keyBytes2 = key.getBytes(StandardCharsets.UTF_8);
        this.f1052bb.put(keyBytes2, 0, keyBytes2.length);
        this.f1052bb.put((byte) 0);
        this.keyPool.put(key, Integer.valueOf(pos));
        return pos;
    }

    public void putInt(int val) {
        putInt((String) null, val);
    }

    public void putInt(String key, int val) {
        putInt(key, (long) val);
    }

    public void putInt(String key, long val) {
        int iKey = putKey(key);
        if (-128 <= val && val <= 127) {
            this.stack.add(Value.int8(iKey, (int) val));
        } else if (-32768 <= val && val <= 32767) {
            this.stack.add(Value.int16(iKey, (int) val));
        } else if (-2147483648L > val || val > 2147483647L) {
            this.stack.add(Value.int64(iKey, val));
        } else {
            this.stack.add(Value.int32(iKey, (int) val));
        }
    }

    public void putInt(long value) {
        putInt((String) null, value);
    }

    public void putUInt(int value) {
        putUInt((String) null, (long) value);
    }

    public void putUInt(long value) {
        putUInt((String) null, value);
    }

    public void putUInt64(BigInteger value) {
        putUInt64((String) null, value.longValue());
    }

    private void putUInt64(String key, long value) {
        this.stack.add(Value.uInt64(putKey(key), value));
    }

    private void putUInt(String key, long value) {
        Value vVal;
        int iKey = putKey(key);
        int width = widthUInBits(value);
        if (width == 0) {
            vVal = Value.uInt8(iKey, (int) value);
        } else if (width == 1) {
            vVal = Value.uInt16(iKey, (int) value);
        } else if (width == 2) {
            vVal = Value.uInt32(iKey, (int) value);
        } else {
            vVal = Value.uInt64(iKey, value);
        }
        this.stack.add(vVal);
    }

    public void putFloat(float value) {
        putFloat((String) null, value);
    }

    public void putFloat(String key, float val) {
        this.stack.add(Value.float32(putKey(key), val));
    }

    public void putFloat(double value) {
        putFloat((String) null, value);
    }

    public void putFloat(String key, double val) {
        this.stack.add(Value.float64(putKey(key), val));
    }

    public int putString(String value) {
        return putString((String) null, value);
    }

    public int putString(String key, String val) {
        int iKey = putKey(key);
        if ((this.flags & 2) != 0) {
            Integer i = this.stringPool.get(val);
            if (i == null) {
                Value value = writeString(iKey, val);
                this.stringPool.put(val, Integer.valueOf((int) value.iValue));
                this.stack.add(value);
                return (int) value.iValue;
            }
            this.stack.add(Value.blob(iKey, i.intValue(), 5, widthUInBits((long) val.length())));
            return i.intValue();
        }
        Value value2 = writeString(iKey, val);
        this.stack.add(value2);
        return (int) value2.iValue;
    }

    private Value writeString(int key, String s) {
        return writeBlob(key, s.getBytes(StandardCharsets.UTF_8), 5, true);
    }

    static int widthUInBits(long len) {
        if (len <= ((long) FlexBuffers.Unsigned.byteToUnsignedInt((byte) -1))) {
            return 0;
        }
        if (len <= ((long) FlexBuffers.Unsigned.shortToUnsignedInt(-1))) {
            return 1;
        }
        if (len <= FlexBuffers.Unsigned.intToUnsignedLong(-1)) {
            return 2;
        }
        return 3;
    }

    private Value writeBlob(int key, byte[] blob, int type, boolean trailing) {
        int bitWidth = widthUInBits((long) blob.length);
        writeInt((long) blob.length, align(bitWidth));
        int sloc = this.f1052bb.writePosition();
        this.f1052bb.put(blob, 0, blob.length);
        if (trailing) {
            this.f1052bb.put((byte) 0);
        }
        return Value.blob(key, sloc, type, bitWidth);
    }

    private int align(int alignment) {
        int byteWidth = 1 << alignment;
        int padBytes = Value.paddingBytes(this.f1052bb.writePosition(), byteWidth);
        while (true) {
            int padBytes2 = padBytes - 1;
            if (padBytes == 0) {
                return byteWidth;
            }
            this.f1052bb.put((byte) 0);
            padBytes = padBytes2;
        }
    }

    private void writeInt(long value, int byteWidth) {
        switch (byteWidth) {
            case 1:
                this.f1052bb.put((byte) ((int) value));
                return;
            case 2:
                this.f1052bb.putShort((short) ((int) value));
                return;
            case 4:
                this.f1052bb.putInt((int) value);
                return;
            case 8:
                this.f1052bb.putLong(value);
                return;
            default:
                return;
        }
    }

    public int putBlob(byte[] value) {
        return putBlob((String) null, value);
    }

    public int putBlob(String key, byte[] val) {
        Value value = writeBlob(putKey(key), val, 25, false);
        this.stack.add(value);
        return (int) value.iValue;
    }

    public int startVector() {
        return this.stack.size();
    }

    public int endVector(String key, int start, boolean typed, boolean fixed) {
        Value vec = createVector(putKey(key), start, this.stack.size() - start, typed, fixed, (Value) null);
        while (this.stack.size() > start) {
            ArrayList<Value> arrayList = this.stack;
            arrayList.remove(arrayList.size() - 1);
        }
        this.stack.add(vec);
        return (int) vec.iValue;
    }

    public ByteBuffer finish() {
        if (this.stack.size() == 1) {
            int byteWidth = align(this.stack.get(0).elemWidth(this.f1052bb.writePosition(), 0));
            writeAny(this.stack.get(0), byteWidth);
            this.f1052bb.put(this.stack.get(0).storedPackedType());
            this.f1052bb.put((byte) byteWidth);
            this.finished = true;
            return ByteBuffer.wrap(this.f1052bb.data(), 0, this.f1052bb.writePosition());
        }
        throw new AssertionError();
    }

    private Value createVector(int key, int start, int length, boolean typed, boolean fixed, Value keys) {
        int i;
        int i2 = length;
        Value value = keys;
        if (!fixed || typed) {
            int i3 = 0;
            int bitWidth = Math.max(0, widthUInBits((long) i2));
            int prefixElems = 1;
            if (value != null) {
                bitWidth = Math.max(bitWidth, value.elemWidth(this.f1052bb.writePosition(), 0));
                prefixElems = 1 + 2;
            }
            int vectorType = 4;
            for (int i4 = start; i4 < this.stack.size(); i4++) {
                bitWidth = Math.max(bitWidth, this.stack.get(i4).elemWidth(this.f1052bb.writePosition(), i4 + prefixElems));
                if (!typed) {
                    int i5 = start;
                } else if (i4 == start) {
                    vectorType = this.stack.get(i4).type;
                    if (!FlexBuffers.isTypedVectorElementType(vectorType)) {
                        throw new FlexBuffers.FlexBufferException("TypedVector does not support this element type");
                    }
                } else if (vectorType != this.stack.get(i4).type) {
                    throw new AssertionError();
                }
            }
            int i6 = start;
            if (!fixed || FlexBuffers.isTypedVectorElementType(vectorType)) {
                int byteWidth = align(bitWidth);
                if (value != null) {
                    writeOffset(value.iValue, byteWidth);
                    writeInt(1 << value.minBitWidth, byteWidth);
                }
                if (!fixed) {
                    writeInt((long) i2, byteWidth);
                }
                int vloc = this.f1052bb.writePosition();
                for (int i7 = start; i7 < this.stack.size(); i7++) {
                    writeAny(this.stack.get(i7), byteWidth);
                }
                if (!typed) {
                    for (int i8 = start; i8 < this.stack.size(); i8++) {
                        this.f1052bb.put(this.stack.get(i8).storedPackedType(bitWidth));
                    }
                }
                if (value != null) {
                    i = 9;
                } else if (typed) {
                    if (fixed) {
                        i3 = i2;
                    }
                    i = FlexBuffers.toTypedVector(vectorType, i3);
                } else {
                    i = 10;
                }
                return new Value(key, i, bitWidth, (long) vloc);
            }
            throw new AssertionError();
        }
        throw new AssertionError();
    }

    private void writeOffset(long val, int byteWidth) {
        int reloff = (int) (((long) this.f1052bb.writePosition()) - val);
        if (byteWidth == 8 || ((long) reloff) < (1 << (byteWidth * 8))) {
            writeInt((long) reloff, byteWidth);
            return;
        }
        throw new AssertionError();
    }

    private void writeAny(Value val, int byteWidth) {
        switch (val.type) {
            case 0:
            case 1:
            case 2:
            case 26:
                writeInt(val.iValue, byteWidth);
                return;
            case 3:
                writeDouble(val.dValue, byteWidth);
                return;
            default:
                writeOffset(val.iValue, byteWidth);
                return;
        }
    }

    private void writeDouble(double val, int byteWidth) {
        if (byteWidth == 4) {
            this.f1052bb.putFloat((float) val);
        } else if (byteWidth == 8) {
            this.f1052bb.putDouble(val);
        }
    }

    public int startMap() {
        return this.stack.size();
    }

    public int endMap(String key, int start) {
        int iKey = putKey(key);
        ArrayList<Value> arrayList = this.stack;
        Collections.sort(arrayList.subList(start, arrayList.size()), this.keyComparator);
        Value keys = createKeyVector(start, this.stack.size() - start);
        Value vec = createVector(iKey, start, this.stack.size() - start, false, false, keys);
        while (this.stack.size() > start) {
            ArrayList<Value> arrayList2 = this.stack;
            arrayList2.remove(arrayList2.size() - 1);
        }
        this.stack.add(vec);
        return (int) vec.iValue;
    }

    private Value createKeyVector(int start, int length) {
        int bitWidth = Math.max(0, widthUInBits((long) length));
        for (int i = start; i < this.stack.size(); i++) {
            bitWidth = Math.max(bitWidth, Value.elemWidth(4, 0, (long) this.stack.get(i).key, this.f1052bb.writePosition(), i + 1));
        }
        int i2 = align(bitWidth);
        writeInt((long) length, i2);
        int vloc = this.f1052bb.writePosition();
        int i3 = start;
        while (i3 < this.stack.size()) {
            if (this.stack.get(i3).key != -1) {
                writeOffset((long) this.stack.get(i3).key, i2);
                i3++;
            } else {
                throw new AssertionError();
            }
        }
        return new Value(-1, FlexBuffers.toTypedVector(4, 0), bitWidth, (long) vloc);
    }

    private static class Value {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        final double dValue;
        long iValue;
        int key;
        final int minBitWidth;
        final int type;

        static {
            Class<FlexBuffersBuilder> cls = FlexBuffersBuilder.class;
        }

        Value(int key2, int type2, int bitWidth, long iValue2) {
            this.key = key2;
            this.type = type2;
            this.minBitWidth = bitWidth;
            this.iValue = iValue2;
            this.dValue = Double.MIN_VALUE;
        }

        Value(int key2, int type2, int bitWidth, double dValue2) {
            this.key = key2;
            this.type = type2;
            this.minBitWidth = bitWidth;
            this.dValue = dValue2;
            this.iValue = Long.MIN_VALUE;
        }

        static Value bool(int key2, boolean b) {
            return new Value(key2, 26, 0, b ? 1 : 0);
        }

        static Value blob(int key2, int position, int type2, int bitWidth) {
            return new Value(key2, type2, bitWidth, (long) position);
        }

        static Value int8(int key2, int value) {
            return new Value(key2, 1, 0, (long) value);
        }

        static Value int16(int key2, int value) {
            return new Value(key2, 1, 1, (long) value);
        }

        static Value int32(int key2, int value) {
            return new Value(key2, 1, 2, (long) value);
        }

        static Value int64(int key2, long value) {
            return new Value(key2, 1, 3, value);
        }

        static Value uInt8(int key2, int value) {
            return new Value(key2, 2, 0, (long) value);
        }

        static Value uInt16(int key2, int value) {
            return new Value(key2, 2, 1, (long) value);
        }

        static Value uInt32(int key2, int value) {
            return new Value(key2, 2, 2, (long) value);
        }

        static Value uInt64(int key2, long value) {
            return new Value(key2, 2, 3, value);
        }

        static Value float32(int key2, float value) {
            return new Value(key2, 3, 2, (double) value);
        }

        static Value float64(int key2, double value) {
            return new Value(key2, 3, 3, value);
        }

        /* access modifiers changed from: private */
        public byte storedPackedType() {
            return storedPackedType(0);
        }

        /* access modifiers changed from: private */
        public byte storedPackedType(int parentBitWidth) {
            return packedType(storedWidth(parentBitWidth), this.type);
        }

        private static byte packedType(int bitWidth, int type2) {
            return (byte) ((type2 << 2) | bitWidth);
        }

        private int storedWidth(int parentBitWidth) {
            if (FlexBuffers.isTypeInline(this.type)) {
                return Math.max(this.minBitWidth, parentBitWidth);
            }
            return this.minBitWidth;
        }

        /* access modifiers changed from: private */
        public int elemWidth(int bufSize, int elemIndex) {
            return elemWidth(this.type, this.minBitWidth, this.iValue, bufSize, elemIndex);
        }

        /* access modifiers changed from: private */
        public static int elemWidth(int type2, int minBitWidth2, long iValue2, int bufSize, int elemIndex) {
            if (FlexBuffers.isTypeInline(type2)) {
                return minBitWidth2;
            }
            for (int byteWidth = 1; byteWidth <= 32; byteWidth *= 2) {
                int bitWidth = FlexBuffersBuilder.widthUInBits((long) ((int) (((long) ((paddingBytes(bufSize, byteWidth) + bufSize) + (elemIndex * byteWidth))) - iValue2)));
                if ((1 << bitWidth) == ((long) byteWidth)) {
                    return bitWidth;
                }
            }
            throw new AssertionError();
        }

        /* access modifiers changed from: private */
        public static int paddingBytes(int bufSize, int scalarSize) {
            return ((bufSize ^ -1) + 1) & (scalarSize - 1);
        }
    }
}
