package com.itextpdf.p026io.codec;

import java.io.Serializable;
import kotlin.UByte;
import kotlin.jvm.internal.CharCompanionObject;

/* renamed from: com.itextpdf.io.codec.TIFFField */
public class TIFFField implements Comparable<TIFFField>, Serializable {
    public static final int TIFF_ASCII = 2;
    public static final int TIFF_BYTE = 1;
    public static final int TIFF_DOUBLE = 12;
    public static final int TIFF_FLOAT = 11;
    public static final int TIFF_LONG = 4;
    public static final int TIFF_RATIONAL = 5;
    public static final int TIFF_SBYTE = 6;
    public static final int TIFF_SHORT = 3;
    public static final int TIFF_SLONG = 9;
    public static final int TIFF_SRATIONAL = 10;
    public static final int TIFF_SSHORT = 8;
    public static final int TIFF_UNDEFINED = 7;
    private static final long serialVersionUID = 9088332901412823834L;
    int count;
    Object data;
    int tag;
    int type;

    TIFFField() {
    }

    public TIFFField(int tag2, int type2, int count2, Object data2) {
        this.tag = tag2;
        this.type = type2;
        this.count = count2;
        this.data = data2;
    }

    public int getTag() {
        return this.tag;
    }

    public int getType() {
        return this.type;
    }

    public int getCount() {
        return this.count;
    }

    public byte[] getAsBytes() {
        return (byte[]) this.data;
    }

    public char[] getAsChars() {
        return (char[]) this.data;
    }

    public short[] getAsShorts() {
        return (short[]) this.data;
    }

    public int[] getAsInts() {
        return (int[]) this.data;
    }

    public long[] getAsLongs() {
        return (long[]) this.data;
    }

    public float[] getAsFloats() {
        return (float[]) this.data;
    }

    public double[] getAsDoubles() {
        return (double[]) this.data;
    }

    public String[] getAsStrings() {
        return (String[]) this.data;
    }

    public int[][] getAsSRationals() {
        return (int[][]) this.data;
    }

    public long[][] getAsRationals() {
        return (long[][]) this.data;
    }

    public int getAsInt(int index) {
        switch (this.type) {
            case 1:
            case 7:
                return ((byte[]) this.data)[index] & UByte.MAX_VALUE;
            case 3:
                return ((char[]) this.data)[index] & CharCompanionObject.MAX_VALUE;
            case 6:
                return ((byte[]) this.data)[index];
            case 8:
                return ((short[]) this.data)[index];
            case 9:
                return ((int[]) this.data)[index];
            default:
                throw new ClassCastException();
        }
    }

    public long getAsLong(int index) {
        switch (this.type) {
            case 1:
            case 7:
                return (long) (((byte[]) this.data)[index] & UByte.MAX_VALUE);
            case 3:
                return (long) (((char[]) this.data)[index] & CharCompanionObject.MAX_VALUE);
            case 4:
                return ((long[]) this.data)[index];
            case 6:
                return (long) ((byte[]) this.data)[index];
            case 8:
                return (long) ((short[]) this.data)[index];
            case 9:
                return (long) ((int[]) this.data)[index];
            default:
                throw new ClassCastException();
        }
    }

    public float getAsFloat(int index) {
        switch (this.type) {
            case 1:
                return (float) (((byte[]) this.data)[index] & UByte.MAX_VALUE);
            case 3:
                return (float) (((char[]) this.data)[index] & CharCompanionObject.MAX_VALUE);
            case 4:
                return (float) ((long[]) this.data)[index];
            case 5:
                long[] lvalue = getAsRational(index);
                double d = (double) lvalue[0];
                double d2 = (double) lvalue[1];
                Double.isNaN(d);
                Double.isNaN(d2);
                return (float) (d / d2);
            case 6:
                return (float) ((byte[]) this.data)[index];
            case 8:
                return (float) ((short[]) this.data)[index];
            case 9:
                return (float) ((int[]) this.data)[index];
            case 10:
                int[] ivalue = getAsSRational(index);
                double d3 = (double) ivalue[0];
                double d4 = (double) ivalue[1];
                Double.isNaN(d3);
                Double.isNaN(d4);
                return (float) (d3 / d4);
            case 11:
                return ((float[]) this.data)[index];
            case 12:
                return (float) ((double[]) this.data)[index];
            default:
                throw new ClassCastException();
        }
    }

    public double getAsDouble(int index) {
        switch (this.type) {
            case 1:
                return (double) (((byte[]) this.data)[index] & UByte.MAX_VALUE);
            case 3:
                return (double) (((char[]) this.data)[index] & CharCompanionObject.MAX_VALUE);
            case 4:
                return (double) ((long[]) this.data)[index];
            case 5:
                long[] lvalue = getAsRational(index);
                double d = (double) lvalue[0];
                double d2 = (double) lvalue[1];
                Double.isNaN(d);
                Double.isNaN(d2);
                return d / d2;
            case 6:
                return (double) ((byte[]) this.data)[index];
            case 8:
                return (double) ((short[]) this.data)[index];
            case 9:
                return (double) ((int[]) this.data)[index];
            case 10:
                int[] ivalue = getAsSRational(index);
                double d3 = (double) ivalue[0];
                double d4 = (double) ivalue[1];
                Double.isNaN(d3);
                Double.isNaN(d4);
                return d3 / d4;
            case 11:
                return (double) ((float[]) this.data)[index];
            case 12:
                return ((double[]) this.data)[index];
            default:
                throw new ClassCastException();
        }
    }

    public String getAsString(int index) {
        return ((String[]) this.data)[index];
    }

    public int[] getAsSRational(int index) {
        return ((int[][]) this.data)[index];
    }

    public long[] getAsRational(int index) {
        if (this.type == 4) {
            return getAsLongs();
        }
        return ((long[][]) this.data)[index];
    }

    public int compareTo(TIFFField o) {
        if (o != null) {
            int oTag = o.getTag();
            int i = this.tag;
            if (i < oTag) {
                return -1;
            }
            if (i > oTag) {
                return 1;
            }
            return 0;
        }
        throw new IllegalArgumentException();
    }
}
