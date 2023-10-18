package com.itextpdf.p026io.codec;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.TreeMap;

/* renamed from: com.itextpdf.io.codec.TiffWriter */
public class TiffWriter {
    private TreeMap<Integer, FieldBase> ifd = new TreeMap<>();

    public void addField(FieldBase field) {
        this.ifd.put(Integer.valueOf(field.getTag()), field);
    }

    public int getIfdSize() {
        return (this.ifd.size() * 12) + 6;
    }

    public void writeFile(OutputStream stream) throws IOException {
        stream.write(77);
        stream.write(77);
        stream.write(0);
        stream.write(42);
        writeLong(8, stream);
        writeShort(this.ifd.size(), stream);
        int offset = getIfdSize() + 8;
        for (FieldBase field : this.ifd.values()) {
            int size = field.getValueSize();
            if (size > 4) {
                field.setOffset(offset);
                offset += size;
            }
            field.writeField(stream);
        }
        writeLong(0, stream);
        for (FieldBase field2 : this.ifd.values()) {
            field2.writeValue(stream);
        }
    }

    /* renamed from: com.itextpdf.io.codec.TiffWriter$FieldBase */
    public static abstract class FieldBase {
        private int count;
        protected byte[] data;
        private int fieldType;
        private int offset;
        private int tag;

        protected FieldBase(int tag2, int fieldType2, int count2) {
            this.tag = tag2;
            this.fieldType = fieldType2;
            this.count = count2;
        }

        public int getValueSize() {
            return (this.data.length + 1) & -2;
        }

        public int getTag() {
            return this.tag;
        }

        public void setOffset(int offset2) {
            this.offset = offset2;
        }

        public void writeField(OutputStream stream) throws IOException {
            TiffWriter.writeShort(this.tag, stream);
            TiffWriter.writeShort(this.fieldType, stream);
            TiffWriter.writeLong(this.count, stream);
            byte[] bArr = this.data;
            if (bArr.length <= 4) {
                stream.write(bArr);
                for (int k = this.data.length; k < 4; k++) {
                    stream.write(0);
                }
                return;
            }
            TiffWriter.writeLong(this.offset, stream);
        }

        public void writeValue(OutputStream stream) throws IOException {
            byte[] bArr = this.data;
            if (bArr.length > 4) {
                stream.write(bArr);
                if ((this.data.length & 1) == 1) {
                    stream.write(0);
                }
            }
        }
    }

    /* renamed from: com.itextpdf.io.codec.TiffWriter$FieldShort */
    public static class FieldShort extends FieldBase {
        public FieldShort(int tag, int value) {
            super(tag, 3, 1);
            this.data = new byte[2];
            this.data[0] = (byte) (value >> 8);
            this.data[1] = (byte) value;
        }

        public FieldShort(int tag, int[] values) {
            super(tag, 3, values.length);
            this.data = new byte[(values.length * 2)];
            int ptr = 0;
            int length = values.length;
            int i = 0;
            while (i < length) {
                int value = values[i];
                int ptr2 = ptr + 1;
                this.data[ptr] = (byte) (value >> 8);
                this.data[ptr2] = (byte) value;
                i++;
                ptr = ptr2 + 1;
            }
        }
    }

    /* renamed from: com.itextpdf.io.codec.TiffWriter$FieldLong */
    public static class FieldLong extends FieldBase {
        public FieldLong(int tag, int value) {
            super(tag, 4, 1);
            this.data = new byte[4];
            this.data[0] = (byte) (value >> 24);
            this.data[1] = (byte) (value >> 16);
            this.data[2] = (byte) (value >> 8);
            this.data[3] = (byte) value;
        }

        public FieldLong(int tag, int[] values) {
            super(tag, 4, values.length);
            this.data = new byte[(values.length * 4)];
            int ptr = 0;
            int length = values.length;
            int i = 0;
            while (i < length) {
                int value = values[i];
                int ptr2 = ptr + 1;
                this.data[ptr] = (byte) (value >> 24);
                int ptr3 = ptr2 + 1;
                this.data[ptr2] = (byte) (value >> 16);
                int ptr4 = ptr3 + 1;
                this.data[ptr3] = (byte) (value >> 8);
                this.data[ptr4] = (byte) value;
                i++;
                ptr = ptr4 + 1;
            }
        }
    }

    /* renamed from: com.itextpdf.io.codec.TiffWriter$FieldRational */
    public static class FieldRational extends FieldBase {
        public FieldRational(int tag, int[] value) {
            this(tag, new int[][]{value});
        }

        public FieldRational(int tag, int[][] values) {
            super(tag, 5, values.length);
            this.data = new byte[(values.length * 8)];
            int ptr = 0;
            int length = values.length;
            int i = 0;
            while (i < length) {
                int[] value = values[i];
                int ptr2 = ptr + 1;
                this.data[ptr] = (byte) (value[0] >> 24);
                int ptr3 = ptr2 + 1;
                this.data[ptr2] = (byte) (value[0] >> 16);
                int ptr4 = ptr3 + 1;
                this.data[ptr3] = (byte) (value[0] >> 8);
                int ptr5 = ptr4 + 1;
                this.data[ptr4] = (byte) value[0];
                int ptr6 = ptr5 + 1;
                this.data[ptr5] = (byte) (value[1] >> 24);
                int ptr7 = ptr6 + 1;
                this.data[ptr6] = (byte) (value[1] >> 16);
                int ptr8 = ptr7 + 1;
                this.data[ptr7] = (byte) (value[1] >> 8);
                this.data[ptr8] = (byte) value[1];
                i++;
                ptr = ptr8 + 1;
            }
        }
    }

    /* renamed from: com.itextpdf.io.codec.TiffWriter$FieldByte */
    public static class FieldByte extends FieldBase {
        public FieldByte(int tag, byte[] values) {
            super(tag, 1, values.length);
            this.data = values;
        }
    }

    /* renamed from: com.itextpdf.io.codec.TiffWriter$FieldUndefined */
    public static class FieldUndefined extends FieldBase {
        public FieldUndefined(int tag, byte[] values) {
            super(tag, 7, values.length);
            this.data = values;
        }
    }

    /* renamed from: com.itextpdf.io.codec.TiffWriter$FieldImage */
    public static class FieldImage extends FieldBase {
        public FieldImage(byte[] values) {
            super(TIFFConstants.TIFFTAG_STRIPOFFSETS, 4, 1);
            this.data = values;
        }
    }

    /* renamed from: com.itextpdf.io.codec.TiffWriter$FieldAscii */
    public static class FieldAscii extends FieldBase {
        public FieldAscii(int tag, String values) {
            super(tag, 2, values.getBytes(StandardCharsets.US_ASCII).length + 1);
            byte[] b = values.getBytes(StandardCharsets.US_ASCII);
            this.data = new byte[(b.length + 1)];
            System.arraycopy(b, 0, this.data, 0, b.length);
        }
    }

    public static void writeShort(int v, OutputStream stream) throws IOException {
        stream.write((v >> 8) & 255);
        stream.write(v & 255);
    }

    public static void writeLong(int v, OutputStream stream) throws IOException {
        stream.write((v >> 24) & 255);
        stream.write((v >> 16) & 255);
        stream.write((v >> 8) & 255);
        stream.write(v & 255);
    }

    public static void compressLZW(OutputStream stream, int predictor, byte[] b, int height, int samplesPerPixel, int stride) throws IOException {
        boolean usePredictor = true;
        LZWCompressor lzwCompressor = new LZWCompressor(stream, 8, true);
        if (predictor != 2) {
            usePredictor = false;
        }
        if (!usePredictor) {
            lzwCompressor.compress(b, 0, b.length);
        } else {
            int off = 0;
            byte[] rowBuf = new byte[stride];
            for (int i = 0; i < height; i++) {
                System.arraycopy(b, off, rowBuf, 0, stride);
                for (int j = stride - 1; j >= samplesPerPixel; j--) {
                    rowBuf[j] = (byte) (rowBuf[j] - rowBuf[j - samplesPerPixel]);
                }
                lzwCompressor.compress(rowBuf, 0, stride);
                off += stride;
            }
        }
        lzwCompressor.flush();
    }
}
