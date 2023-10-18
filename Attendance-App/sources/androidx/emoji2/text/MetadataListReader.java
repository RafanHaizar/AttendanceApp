package androidx.emoji2.text;

import android.content.res.AssetManager;
import androidx.emoji2.text.flatbuffer.MetadataList;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import org.bouncycastle.asn1.cmc.BodyPartID;

class MetadataListReader {
    private static final int EMJI_TAG = 1164798569;
    private static final int EMJI_TAG_DEPRECATED = 1701669481;
    private static final int META_TABLE_NAME = 1835365473;

    private interface OpenTypeReader {
        public static final int UINT16_BYTE_COUNT = 2;
        public static final int UINT32_BYTE_COUNT = 4;

        long getPosition();

        int readTag() throws IOException;

        long readUnsignedInt() throws IOException;

        int readUnsignedShort() throws IOException;

        void skip(int i) throws IOException;
    }

    static MetadataList read(InputStream inputStream) throws IOException {
        OpenTypeReader openTypeReader = new InputStreamOpenTypeReader(inputStream);
        OffsetInfo offsetInfo = findOffsetInfo(openTypeReader);
        openTypeReader.skip((int) (offsetInfo.getStartOffset() - openTypeReader.getPosition()));
        ByteBuffer buffer = ByteBuffer.allocate((int) offsetInfo.getLength());
        int numRead = inputStream.read(buffer.array());
        if (((long) numRead) == offsetInfo.getLength()) {
            return MetadataList.getRootAsMetadataList(buffer);
        }
        throw new IOException("Needed " + offsetInfo.getLength() + " bytes, got " + numRead);
    }

    static MetadataList read(ByteBuffer byteBuffer) throws IOException {
        ByteBuffer newBuffer = byteBuffer.duplicate();
        newBuffer.position((int) findOffsetInfo(new ByteBufferReader(newBuffer)).getStartOffset());
        return MetadataList.getRootAsMetadataList(newBuffer);
    }

    static MetadataList read(AssetManager assetManager, String assetPath) throws IOException {
        InputStream inputStream = assetManager.open(assetPath);
        try {
            MetadataList read = read(inputStream);
            if (inputStream != null) {
                inputStream.close();
            }
            return read;
        } catch (Throwable th) {
            th.addSuppressed(th);
        }
        throw th;
    }

    private static OffsetInfo findOffsetInfo(OpenTypeReader reader) throws IOException {
        reader.skip(4);
        int tableCount = reader.readUnsignedShort();
        if (tableCount <= 100) {
            reader.skip(6);
            long metaOffset = -1;
            int i = 0;
            while (true) {
                if (i >= tableCount) {
                    break;
                }
                int tag = reader.readTag();
                reader.skip(4);
                long offset = reader.readUnsignedInt();
                reader.skip(4);
                if (META_TABLE_NAME == tag) {
                    metaOffset = offset;
                    break;
                }
                i++;
            }
            if (metaOffset != -1) {
                reader.skip((int) (metaOffset - reader.getPosition()));
                reader.skip(12);
                long mapsCount = reader.readUnsignedInt();
                for (int i2 = 0; ((long) i2) < mapsCount; i2++) {
                    int tag2 = reader.readTag();
                    long dataOffset = reader.readUnsignedInt();
                    long dataLength = reader.readUnsignedInt();
                    if (EMJI_TAG == tag2 || EMJI_TAG_DEPRECATED == tag2) {
                        return new OffsetInfo(dataOffset + metaOffset, dataLength);
                    }
                }
            }
            throw new IOException("Cannot read metadata.");
        }
        throw new IOException("Cannot read metadata.");
    }

    private static class OffsetInfo {
        private final long mLength;
        private final long mStartOffset;

        OffsetInfo(long startOffset, long length) {
            this.mStartOffset = startOffset;
            this.mLength = length;
        }

        /* access modifiers changed from: package-private */
        public long getStartOffset() {
            return this.mStartOffset;
        }

        /* access modifiers changed from: package-private */
        public long getLength() {
            return this.mLength;
        }
    }

    static int toUnsignedShort(short value) {
        return 65535 & value;
    }

    static long toUnsignedInt(int value) {
        return ((long) value) & BodyPartID.bodyIdMax;
    }

    private static class InputStreamOpenTypeReader implements OpenTypeReader {
        private final byte[] mByteArray;
        private final ByteBuffer mByteBuffer;
        private final InputStream mInputStream;
        private long mPosition = 0;

        InputStreamOpenTypeReader(InputStream inputStream) {
            this.mInputStream = inputStream;
            byte[] bArr = new byte[4];
            this.mByteArray = bArr;
            ByteBuffer wrap = ByteBuffer.wrap(bArr);
            this.mByteBuffer = wrap;
            wrap.order(ByteOrder.BIG_ENDIAN);
        }

        public int readUnsignedShort() throws IOException {
            this.mByteBuffer.position(0);
            read(2);
            return MetadataListReader.toUnsignedShort(this.mByteBuffer.getShort());
        }

        public long readUnsignedInt() throws IOException {
            this.mByteBuffer.position(0);
            read(4);
            return MetadataListReader.toUnsignedInt(this.mByteBuffer.getInt());
        }

        public int readTag() throws IOException {
            this.mByteBuffer.position(0);
            read(4);
            return this.mByteBuffer.getInt();
        }

        public void skip(int numOfBytes) throws IOException {
            while (numOfBytes > 0) {
                int skipped = (int) this.mInputStream.skip((long) numOfBytes);
                if (skipped >= 1) {
                    numOfBytes -= skipped;
                    this.mPosition += (long) skipped;
                } else {
                    throw new IOException("Skip didn't move at least 1 byte forward");
                }
            }
        }

        public long getPosition() {
            return this.mPosition;
        }

        private void read(int numOfBytes) throws IOException {
            if (this.mInputStream.read(this.mByteArray, 0, numOfBytes) == numOfBytes) {
                this.mPosition += (long) numOfBytes;
                return;
            }
            throw new IOException("read failed");
        }
    }

    private static class ByteBufferReader implements OpenTypeReader {
        private final ByteBuffer mByteBuffer;

        ByteBufferReader(ByteBuffer byteBuffer) {
            this.mByteBuffer = byteBuffer;
            byteBuffer.order(ByteOrder.BIG_ENDIAN);
        }

        public int readUnsignedShort() throws IOException {
            return MetadataListReader.toUnsignedShort(this.mByteBuffer.getShort());
        }

        public long readUnsignedInt() throws IOException {
            return MetadataListReader.toUnsignedInt(this.mByteBuffer.getInt());
        }

        public int readTag() throws IOException {
            return this.mByteBuffer.getInt();
        }

        public void skip(int numOfBytes) throws IOException {
            ByteBuffer byteBuffer = this.mByteBuffer;
            byteBuffer.position(byteBuffer.position() + numOfBytes);
        }

        public long getPosition() {
            return (long) this.mByteBuffer.position();
        }
    }

    private MetadataListReader() {
    }
}
