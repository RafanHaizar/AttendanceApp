package androidx.emoji2.text.flatbuffer;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import kotlin.UByte;
import kotlin.text.Typography;
import org.bouncycastle.asn1.cmc.BodyPartID;

public class FlexBuffers {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    /* access modifiers changed from: private */
    public static final ReadBuf EMPTY_BB = new ArrayReadWriteBuf(new byte[]{0}, 1);
    public static final int FBT_BLOB = 25;
    public static final int FBT_BOOL = 26;
    public static final int FBT_FLOAT = 3;
    public static final int FBT_INDIRECT_FLOAT = 8;
    public static final int FBT_INDIRECT_INT = 6;
    public static final int FBT_INDIRECT_UINT = 7;
    public static final int FBT_INT = 1;
    public static final int FBT_KEY = 4;
    public static final int FBT_MAP = 9;
    public static final int FBT_NULL = 0;
    public static final int FBT_STRING = 5;
    public static final int FBT_UINT = 2;
    public static final int FBT_VECTOR = 10;
    public static final int FBT_VECTOR_BOOL = 36;
    public static final int FBT_VECTOR_FLOAT = 13;
    public static final int FBT_VECTOR_FLOAT2 = 18;
    public static final int FBT_VECTOR_FLOAT3 = 21;
    public static final int FBT_VECTOR_FLOAT4 = 24;
    public static final int FBT_VECTOR_INT = 11;
    public static final int FBT_VECTOR_INT2 = 16;
    public static final int FBT_VECTOR_INT3 = 19;
    public static final int FBT_VECTOR_INT4 = 22;
    public static final int FBT_VECTOR_KEY = 14;
    public static final int FBT_VECTOR_STRING_DEPRECATED = 15;
    public static final int FBT_VECTOR_UINT = 12;
    public static final int FBT_VECTOR_UINT2 = 17;
    public static final int FBT_VECTOR_UINT3 = 20;
    public static final int FBT_VECTOR_UINT4 = 23;

    static boolean isTypedVector(int type) {
        return (type >= 11 && type <= 15) || type == 36;
    }

    static boolean isTypeInline(int type) {
        return type <= 3 || type == 26;
    }

    static int toTypedVectorElementType(int original_type) {
        return (original_type - 11) + 1;
    }

    static int toTypedVector(int type, int fixedLength) {
        if (isTypedVectorElementType(type)) {
            switch (fixedLength) {
                case 0:
                    return (type - 1) + 11;
                case 2:
                    return (type - 1) + 16;
                case 3:
                    return (type - 1) + 19;
                case 4:
                    return (type - 1) + 22;
                default:
                    throw new AssertionError();
            }
        } else {
            throw new AssertionError();
        }
    }

    static boolean isTypedVectorElementType(int type) {
        return (type >= 1 && type <= 4) || type == 26;
    }

    /* access modifiers changed from: private */
    public static int indirect(ReadBuf bb, int offset, int byteWidth) {
        return (int) (((long) offset) - readUInt(bb, offset, byteWidth));
    }

    /* access modifiers changed from: private */
    public static long readUInt(ReadBuf buff, int end, int byteWidth) {
        switch (byteWidth) {
            case 1:
                return (long) Unsigned.byteToUnsignedInt(buff.get(end));
            case 2:
                return (long) Unsigned.shortToUnsignedInt(buff.getShort(end));
            case 4:
                return Unsigned.intToUnsignedLong(buff.getInt(end));
            case 8:
                return buff.getLong(end);
            default:
                return -1;
        }
    }

    /* access modifiers changed from: private */
    public static int readInt(ReadBuf buff, int end, int byteWidth) {
        return (int) readLong(buff, end, byteWidth);
    }

    /* access modifiers changed from: private */
    public static long readLong(ReadBuf buff, int end, int byteWidth) {
        switch (byteWidth) {
            case 1:
                return (long) buff.get(end);
            case 2:
                return (long) buff.getShort(end);
            case 4:
                return (long) buff.getInt(end);
            case 8:
                return buff.getLong(end);
            default:
                return -1;
        }
    }

    /* access modifiers changed from: private */
    public static double readDouble(ReadBuf buff, int end, int byteWidth) {
        switch (byteWidth) {
            case 4:
                return (double) buff.getFloat(end);
            case 8:
                return buff.getDouble(end);
            default:
                return -1.0d;
        }
    }

    @Deprecated
    public static Reference getRoot(ByteBuffer buffer) {
        return getRoot(buffer.hasArray() ? new ArrayReadWriteBuf(buffer.array(), buffer.limit()) : new ByteBufferReadWriteBuf(buffer));
    }

    public static Reference getRoot(ReadBuf buffer) {
        int end = buffer.limit() - 1;
        int byteWidth = buffer.get(end);
        int end2 = end - 1;
        return new Reference(buffer, end2 - byteWidth, byteWidth, Unsigned.byteToUnsignedInt(buffer.get(end2)));
    }

    public static class Reference {
        /* access modifiers changed from: private */
        public static final Reference NULL_REFERENCE = new Reference(FlexBuffers.EMPTY_BB, 0, 1, 0);

        /* renamed from: bb */
        private ReadBuf f1051bb;
        private int byteWidth;
        private int end;
        private int parentWidth;
        private int type;

        Reference(ReadBuf bb, int end2, int parentWidth2, int packedType) {
            this(bb, end2, parentWidth2, 1 << (packedType & 3), packedType >> 2);
        }

        Reference(ReadBuf bb, int end2, int parentWidth2, int byteWidth2, int type2) {
            this.f1051bb = bb;
            this.end = end2;
            this.parentWidth = parentWidth2;
            this.byteWidth = byteWidth2;
            this.type = type2;
        }

        public int getType() {
            return this.type;
        }

        public boolean isNull() {
            return this.type == 0;
        }

        public boolean isBoolean() {
            return this.type == 26;
        }

        public boolean isNumeric() {
            return isIntOrUInt() || isFloat();
        }

        public boolean isIntOrUInt() {
            return isInt() || isUInt();
        }

        public boolean isFloat() {
            int i = this.type;
            return i == 3 || i == 8;
        }

        public boolean isInt() {
            int i = this.type;
            return i == 1 || i == 6;
        }

        public boolean isUInt() {
            int i = this.type;
            return i == 2 || i == 7;
        }

        public boolean isString() {
            return this.type == 5;
        }

        public boolean isKey() {
            return this.type == 4;
        }

        public boolean isVector() {
            int i = this.type;
            return i == 10 || i == 9;
        }

        public boolean isTypedVector() {
            return FlexBuffers.isTypedVector(this.type);
        }

        public boolean isMap() {
            return this.type == 9;
        }

        public boolean isBlob() {
            return this.type == 25;
        }

        public int asInt() {
            int i = this.type;
            if (i == 1) {
                return FlexBuffers.readInt(this.f1051bb, this.end, this.parentWidth);
            }
            switch (i) {
                case 0:
                    return 0;
                case 2:
                    return (int) FlexBuffers.readUInt(this.f1051bb, this.end, this.parentWidth);
                case 3:
                    return (int) FlexBuffers.readDouble(this.f1051bb, this.end, this.parentWidth);
                case 5:
                    return Integer.parseInt(asString());
                case 6:
                    ReadBuf readBuf = this.f1051bb;
                    return FlexBuffers.readInt(readBuf, FlexBuffers.indirect(readBuf, this.end, this.parentWidth), this.byteWidth);
                case 7:
                    ReadBuf readBuf2 = this.f1051bb;
                    return (int) FlexBuffers.readUInt(readBuf2, FlexBuffers.indirect(readBuf2, this.end, this.parentWidth), this.parentWidth);
                case 8:
                    ReadBuf readBuf3 = this.f1051bb;
                    return (int) FlexBuffers.readDouble(readBuf3, FlexBuffers.indirect(readBuf3, this.end, this.parentWidth), this.byteWidth);
                case 10:
                    return asVector().size();
                case 26:
                    return FlexBuffers.readInt(this.f1051bb, this.end, this.parentWidth);
                default:
                    return 0;
            }
        }

        public long asUInt() {
            int i = this.type;
            if (i == 2) {
                return FlexBuffers.readUInt(this.f1051bb, this.end, this.parentWidth);
            }
            switch (i) {
                case 0:
                    return 0;
                case 1:
                    return FlexBuffers.readLong(this.f1051bb, this.end, this.parentWidth);
                case 3:
                    return (long) FlexBuffers.readDouble(this.f1051bb, this.end, this.parentWidth);
                case 5:
                    return Long.parseLong(asString());
                case 6:
                    ReadBuf readBuf = this.f1051bb;
                    return FlexBuffers.readLong(readBuf, FlexBuffers.indirect(readBuf, this.end, this.parentWidth), this.byteWidth);
                case 7:
                    ReadBuf readBuf2 = this.f1051bb;
                    return FlexBuffers.readUInt(readBuf2, FlexBuffers.indirect(readBuf2, this.end, this.parentWidth), this.byteWidth);
                case 8:
                    ReadBuf readBuf3 = this.f1051bb;
                    return (long) FlexBuffers.readDouble(readBuf3, FlexBuffers.indirect(readBuf3, this.end, this.parentWidth), this.parentWidth);
                case 10:
                    return (long) asVector().size();
                case 26:
                    return (long) FlexBuffers.readInt(this.f1051bb, this.end, this.parentWidth);
                default:
                    return 0;
            }
        }

        public long asLong() {
            int i = this.type;
            if (i == 1) {
                return FlexBuffers.readLong(this.f1051bb, this.end, this.parentWidth);
            }
            switch (i) {
                case 0:
                    return 0;
                case 2:
                    return FlexBuffers.readUInt(this.f1051bb, this.end, this.parentWidth);
                case 3:
                    return (long) FlexBuffers.readDouble(this.f1051bb, this.end, this.parentWidth);
                case 5:
                    try {
                        return Long.parseLong(asString());
                    } catch (NumberFormatException e) {
                        return 0;
                    }
                case 6:
                    ReadBuf readBuf = this.f1051bb;
                    return FlexBuffers.readLong(readBuf, FlexBuffers.indirect(readBuf, this.end, this.parentWidth), this.byteWidth);
                case 7:
                    ReadBuf readBuf2 = this.f1051bb;
                    return FlexBuffers.readUInt(readBuf2, FlexBuffers.indirect(readBuf2, this.end, this.parentWidth), this.parentWidth);
                case 8:
                    ReadBuf readBuf3 = this.f1051bb;
                    return (long) FlexBuffers.readDouble(readBuf3, FlexBuffers.indirect(readBuf3, this.end, this.parentWidth), this.byteWidth);
                case 10:
                    return (long) asVector().size();
                case 26:
                    return (long) FlexBuffers.readInt(this.f1051bb, this.end, this.parentWidth);
                default:
                    return 0;
            }
        }

        public double asFloat() {
            int i = this.type;
            if (i == 3) {
                return FlexBuffers.readDouble(this.f1051bb, this.end, this.parentWidth);
            }
            switch (i) {
                case 0:
                    return 0.0d;
                case 1:
                    return (double) FlexBuffers.readInt(this.f1051bb, this.end, this.parentWidth);
                case 2:
                case 26:
                    return (double) FlexBuffers.readUInt(this.f1051bb, this.end, this.parentWidth);
                case 5:
                    return Double.parseDouble(asString());
                case 6:
                    ReadBuf readBuf = this.f1051bb;
                    return (double) FlexBuffers.readInt(readBuf, FlexBuffers.indirect(readBuf, this.end, this.parentWidth), this.byteWidth);
                case 7:
                    ReadBuf readBuf2 = this.f1051bb;
                    return (double) FlexBuffers.readUInt(readBuf2, FlexBuffers.indirect(readBuf2, this.end, this.parentWidth), this.byteWidth);
                case 8:
                    ReadBuf readBuf3 = this.f1051bb;
                    return FlexBuffers.readDouble(readBuf3, FlexBuffers.indirect(readBuf3, this.end, this.parentWidth), this.byteWidth);
                case 10:
                    return (double) asVector().size();
                default:
                    return 0.0d;
            }
        }

        public Key asKey() {
            if (!isKey()) {
                return Key.empty();
            }
            ReadBuf readBuf = this.f1051bb;
            return new Key(readBuf, FlexBuffers.indirect(readBuf, this.end, this.parentWidth), this.byteWidth);
        }

        public String asString() {
            if (isString()) {
                int start = FlexBuffers.indirect(this.f1051bb, this.end, this.parentWidth);
                ReadBuf readBuf = this.f1051bb;
                int i = this.byteWidth;
                return this.f1051bb.getString(start, (int) FlexBuffers.readUInt(readBuf, start - i, i));
            } else if (isKey() == 0) {
                return "";
            } else {
                int start2 = FlexBuffers.indirect(this.f1051bb, this.end, this.byteWidth);
                int i2 = start2;
                while (this.f1051bb.get(i2) != 0) {
                    i2++;
                }
                return this.f1051bb.getString(start2, i2 - start2);
            }
        }

        public Map asMap() {
            if (!isMap()) {
                return Map.empty();
            }
            ReadBuf readBuf = this.f1051bb;
            return new Map(readBuf, FlexBuffers.indirect(readBuf, this.end, this.parentWidth), this.byteWidth);
        }

        public Vector asVector() {
            if (isVector()) {
                ReadBuf readBuf = this.f1051bb;
                return new Vector(readBuf, FlexBuffers.indirect(readBuf, this.end, this.parentWidth), this.byteWidth);
            }
            int i = this.type;
            if (i == 15) {
                ReadBuf readBuf2 = this.f1051bb;
                return new TypedVector(readBuf2, FlexBuffers.indirect(readBuf2, this.end, this.parentWidth), this.byteWidth, 4);
            } else if (!FlexBuffers.isTypedVector(i)) {
                return Vector.empty();
            } else {
                ReadBuf readBuf3 = this.f1051bb;
                return new TypedVector(readBuf3, FlexBuffers.indirect(readBuf3, this.end, this.parentWidth), this.byteWidth, FlexBuffers.toTypedVectorElementType(this.type));
            }
        }

        public Blob asBlob() {
            if (!isBlob() && !isString()) {
                return Blob.empty();
            }
            ReadBuf readBuf = this.f1051bb;
            return new Blob(readBuf, FlexBuffers.indirect(readBuf, this.end, this.parentWidth), this.byteWidth);
        }

        public boolean asBoolean() {
            if (isBoolean()) {
                if (this.f1051bb.get(this.end) != 0) {
                    return true;
                }
                return false;
            } else if (asUInt() != 0) {
                return true;
            } else {
                return false;
            }
        }

        public String toString() {
            return toString(new StringBuilder(128)).toString();
        }

        /* access modifiers changed from: package-private */
        public StringBuilder toString(StringBuilder sb) {
            switch (this.type) {
                case 0:
                    return sb.append("null");
                case 1:
                case 6:
                    return sb.append(asLong());
                case 2:
                case 7:
                    return sb.append(asUInt());
                case 3:
                case 8:
                    return sb.append(asFloat());
                case 4:
                    return asKey().toString(sb.append(Typography.quote)).append(Typography.quote);
                case 5:
                    return sb.append(Typography.quote).append(asString()).append(Typography.quote);
                case 9:
                    return asMap().toString(sb);
                case 10:
                    return asVector().toString(sb);
                case 11:
                case 12:
                case 13:
                case 14:
                case 15:
                case 36:
                    return sb.append(asVector());
                case 16:
                case 17:
                case 18:
                case 19:
                case 20:
                case 21:
                case 22:
                case 23:
                case 24:
                    throw new FlexBufferException("not_implemented:" + this.type);
                case 25:
                    return asBlob().toString(sb);
                case 26:
                    return sb.append(asBoolean());
                default:
                    return sb;
            }
        }
    }

    private static abstract class Object {

        /* renamed from: bb */
        ReadBuf f1050bb;
        int byteWidth;
        int end;

        public abstract StringBuilder toString(StringBuilder sb);

        Object(ReadBuf buff, int end2, int byteWidth2) {
            this.f1050bb = buff;
            this.end = end2;
            this.byteWidth = byteWidth2;
        }

        public String toString() {
            return toString(new StringBuilder(128)).toString();
        }
    }

    private static abstract class Sized extends Object {
        protected final int size;

        Sized(ReadBuf buff, int end, int byteWidth) {
            super(buff, end, byteWidth);
            this.size = FlexBuffers.readInt(this.f1050bb, end - byteWidth, byteWidth);
        }

        public int size() {
            return this.size;
        }
    }

    public static class Blob extends Sized {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        static final Blob EMPTY = new Blob(FlexBuffers.EMPTY_BB, 1, 1);

        static {
            Class<FlexBuffers> cls = FlexBuffers.class;
        }

        public /* bridge */ /* synthetic */ int size() {
            return super.size();
        }

        Blob(ReadBuf buff, int end, int byteWidth) {
            super(buff, end, byteWidth);
        }

        public static Blob empty() {
            return EMPTY;
        }

        public ByteBuffer data() {
            ByteBuffer dup = ByteBuffer.wrap(this.f1050bb.data());
            dup.position(this.end);
            dup.limit(this.end + size());
            return dup.asReadOnlyBuffer().slice();
        }

        public byte[] getBytes() {
            int size = size();
            byte[] result = new byte[size];
            for (int i = 0; i < size; i++) {
                result[i] = this.f1050bb.get(this.end + i);
            }
            return result;
        }

        public byte get(int pos) {
            if (pos >= 0 && pos <= size()) {
                return this.f1050bb.get(this.end + pos);
            }
            throw new AssertionError();
        }

        public String toString() {
            return this.f1050bb.getString(this.end, size());
        }

        public StringBuilder toString(StringBuilder sb) {
            sb.append(Typography.quote);
            sb.append(this.f1050bb.getString(this.end, size()));
            return sb.append(Typography.quote);
        }
    }

    public static class Key extends Object {
        /* access modifiers changed from: private */
        public static final Key EMPTY = new Key(FlexBuffers.EMPTY_BB, 0, 0);

        Key(ReadBuf buff, int end, int byteWidth) {
            super(buff, end, byteWidth);
        }

        public static Key empty() {
            return EMPTY;
        }

        public StringBuilder toString(StringBuilder sb) {
            return sb.append(toString());
        }

        public String toString() {
            int i = this.end;
            while (this.f1050bb.get(i) != 0) {
                i++;
            }
            return this.f1050bb.getString(this.end, i - this.end);
        }

        /* access modifiers changed from: package-private */
        public int compareTo(byte[] other) {
            byte c1;
            byte c2;
            int ia = this.end;
            int io = 0;
            do {
                c1 = this.f1050bb.get(ia);
                c2 = other[io];
                if (c1 == 0) {
                    return c1 - c2;
                }
                ia++;
                io++;
                if (io == other.length) {
                    return c1 - c2;
                }
            } while (c1 == c2);
            return c1 - c2;
        }

        public boolean equals(Object obj) {
            if ((obj instanceof Key) && ((Key) obj).end == this.end && ((Key) obj).byteWidth == this.byteWidth) {
                return true;
            }
            return false;
        }

        public int hashCode() {
            return this.end ^ this.byteWidth;
        }
    }

    public static class Map extends Vector {
        private static final Map EMPTY_MAP = new Map(FlexBuffers.EMPTY_BB, 1, 1);

        Map(ReadBuf bb, int end, int byteWidth) {
            super(bb, end, byteWidth);
        }

        public static Map empty() {
            return EMPTY_MAP;
        }

        public Reference get(String key) {
            return get(key.getBytes(StandardCharsets.UTF_8));
        }

        public Reference get(byte[] key) {
            KeyVector keys = keys();
            int size = keys.size();
            int index = binarySearch(keys, key);
            if (index < 0 || index >= size) {
                return Reference.NULL_REFERENCE;
            }
            return get(index);
        }

        public KeyVector keys() {
            int keysOffset = this.end - (this.byteWidth * 3);
            return new KeyVector(new TypedVector(this.f1050bb, FlexBuffers.indirect(this.f1050bb, keysOffset, this.byteWidth), FlexBuffers.readInt(this.f1050bb, this.byteWidth + keysOffset, this.byteWidth), 4));
        }

        public Vector values() {
            return new Vector(this.f1050bb, this.end, this.byteWidth);
        }

        public StringBuilder toString(StringBuilder builder) {
            builder.append("{ ");
            KeyVector keys = keys();
            int size = size();
            Vector vals = values();
            for (int i = 0; i < size; i++) {
                builder.append(Typography.quote).append(keys.get(i).toString()).append("\" : ");
                builder.append(vals.get(i).toString());
                if (i != size - 1) {
                    builder.append(", ");
                }
            }
            builder.append(" }");
            return builder;
        }

        private int binarySearch(KeyVector keys, byte[] searchedKey) {
            int low = 0;
            int high = keys.size() - 1;
            while (low <= high) {
                int mid = (low + high) >>> 1;
                int cmp = keys.get(mid).compareTo(searchedKey);
                if (cmp < 0) {
                    low = mid + 1;
                } else if (cmp <= 0) {
                    return mid;
                } else {
                    high = mid - 1;
                }
            }
            return -(low + 1);
        }
    }

    public static class Vector extends Sized {
        private static final Vector EMPTY_VECTOR = new Vector(FlexBuffers.EMPTY_BB, 1, 1);

        public /* bridge */ /* synthetic */ int size() {
            return super.size();
        }

        public /* bridge */ /* synthetic */ String toString() {
            return super.toString();
        }

        Vector(ReadBuf bb, int end, int byteWidth) {
            super(bb, end, byteWidth);
        }

        public static Vector empty() {
            return EMPTY_VECTOR;
        }

        public boolean isEmpty() {
            return this == EMPTY_VECTOR;
        }

        public StringBuilder toString(StringBuilder sb) {
            sb.append("[ ");
            int size = size();
            for (int i = 0; i < size; i++) {
                get(i).toString(sb);
                if (i != size - 1) {
                    sb.append(", ");
                }
            }
            sb.append(" ]");
            return sb;
        }

        public Reference get(int index) {
            long len = (long) size();
            if (((long) index) >= len) {
                return Reference.NULL_REFERENCE;
            }
            int packedType = Unsigned.byteToUnsignedInt(this.f1050bb.get((int) (((long) this.end) + (((long) this.byteWidth) * len) + ((long) index))));
            return new Reference(this.f1050bb, this.end + (this.byteWidth * index), this.byteWidth, packedType);
        }
    }

    public static class TypedVector extends Vector {
        private static final TypedVector EMPTY_VECTOR = new TypedVector(FlexBuffers.EMPTY_BB, 1, 1, 1);
        private final int elemType;

        TypedVector(ReadBuf bb, int end, int byteWidth, int elemType2) {
            super(bb, end, byteWidth);
            this.elemType = elemType2;
        }

        public static TypedVector empty() {
            return EMPTY_VECTOR;
        }

        public boolean isEmptyVector() {
            return this == EMPTY_VECTOR;
        }

        public int getElemType() {
            return this.elemType;
        }

        public Reference get(int pos) {
            if (pos >= size()) {
                return Reference.NULL_REFERENCE;
            }
            return new Reference(this.f1050bb, this.end + (this.byteWidth * pos), this.byteWidth, 1, this.elemType);
        }
    }

    public static class KeyVector {
        private final TypedVector vec;

        KeyVector(TypedVector vec2) {
            this.vec = vec2;
        }

        public Key get(int pos) {
            if (pos >= size()) {
                return Key.EMPTY;
            }
            return new Key(this.vec.f1050bb, FlexBuffers.indirect(this.vec.f1050bb, this.vec.end + (this.vec.byteWidth * pos), this.vec.byteWidth), 1);
        }

        public int size() {
            return this.vec.size();
        }

        public String toString() {
            StringBuilder b = new StringBuilder();
            b.append('[');
            for (int i = 0; i < this.vec.size(); i++) {
                this.vec.get(i).toString(b);
                if (i != this.vec.size() - 1) {
                    b.append(", ");
                }
            }
            return b.append("]").toString();
        }
    }

    public static class FlexBufferException extends RuntimeException {
        FlexBufferException(String msg) {
            super(msg);
        }
    }

    static class Unsigned {
        Unsigned() {
        }

        static int byteToUnsignedInt(byte x) {
            return x & UByte.MAX_VALUE;
        }

        static int shortToUnsignedInt(short x) {
            return 65535 & x;
        }

        static long intToUnsignedLong(int x) {
            return ((long) x) & BodyPartID.bodyIdMax;
        }
    }
}
