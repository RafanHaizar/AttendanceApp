package com.itextpdf.p026io.colors;

import com.itextpdf.p026io.IOException;
import com.itextpdf.p026io.source.RandomAccessFileOrArray;
import com.itextpdf.p026io.source.RandomAccessSourceFactory;
import com.itextpdf.pdfa.checker.PdfAChecker;
import java.io.InputStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import kotlin.UByte;

/* renamed from: com.itextpdf.io.colors.IccProfile */
public class IccProfile implements Serializable {
    private static Map<String, Integer> cstags = null;
    private static final long serialVersionUID = -7466035855770591929L;
    protected byte[] data;
    protected int numComponents;

    static {
        HashMap hashMap = new HashMap();
        cstags = hashMap;
        hashMap.put("XYZ ", 3);
        cstags.put("Lab ", 3);
        cstags.put("Luv ", 3);
        cstags.put("YCbr", 3);
        cstags.put("Yxy ", 3);
        cstags.put(PdfAChecker.ICC_COLOR_SPACE_RGB, 3);
        cstags.put(PdfAChecker.ICC_COLOR_SPACE_GRAY, 1);
        cstags.put("HSV ", 3);
        cstags.put("HLS ", 3);
        cstags.put(PdfAChecker.ICC_COLOR_SPACE_CMYK, 4);
        cstags.put("CMY ", 3);
        cstags.put("2CLR", 2);
        cstags.put("3CLR", 3);
        cstags.put("4CLR", 4);
        cstags.put("5CLR", 5);
        cstags.put("6CLR", 6);
        cstags.put("7CLR", 7);
        cstags.put("8CLR", 8);
        cstags.put("9CLR", 9);
        cstags.put("ACLR", 10);
        cstags.put("BCLR", 11);
        cstags.put("CCLR", 12);
        cstags.put("DCLR", 13);
        cstags.put("ECLR", 14);
        cstags.put("FCLR", 15);
    }

    protected IccProfile() {
    }

    public static IccProfile getInstance(byte[] data2, int numComponents2) {
        if (data2.length >= 128 && data2[36] == 97 && data2[37] == 99 && data2[38] == 115 && data2[39] == 112) {
            IccProfile icc = new IccProfile();
            icc.data = data2;
            Integer cs = getIccNumberOfComponents(data2);
            int nc = cs == null ? 0 : cs.intValue();
            icc.numComponents = nc;
            if (nc == numComponents2) {
                return icc;
            }
            throw new IOException(IOException.IccProfileContains0ComponentsWhileImageDataContains1Components).setMessageParams(Integer.valueOf(nc), Integer.valueOf(numComponents2));
        }
        throw new IOException(IOException.InvalidIccProfile);
    }

    public static IccProfile getInstance(byte[] data2) {
        Integer cs = getIccNumberOfComponents(data2);
        return getInstance(data2, cs == null ? 0 : cs.intValue());
    }

    public static IccProfile getInstance(RandomAccessFileOrArray file) {
        try {
            byte[] head = new byte[128];
            int remain = head.length;
            int ptr = 0;
            while (remain > 0) {
                int n = file.read(head, ptr, remain);
                if (n >= 0) {
                    remain -= n;
                    ptr += n;
                } else {
                    throw new IOException(IOException.InvalidIccProfile);
                }
            }
            if (head[36] == 97 && head[37] == 99 && head[38] == 115 && head[39] == 112) {
                int remain2 = ((head[0] & UByte.MAX_VALUE) << 24) | ((head[1] & UByte.MAX_VALUE) << Tnaf.POW_2_WIDTH) | ((head[2] & UByte.MAX_VALUE) << 8) | (head[3] & UByte.MAX_VALUE);
                byte[] icc = new byte[remain2];
                System.arraycopy(head, 0, icc, 0, head.length);
                int remain3 = remain2 - head.length;
                int ptr2 = head.length;
                while (remain3 > 0) {
                    int n2 = file.read(icc, ptr2, remain3);
                    if (n2 >= 0) {
                        remain3 -= n2;
                        ptr2 += n2;
                    } else {
                        throw new IOException(IOException.InvalidIccProfile);
                    }
                }
                return getInstance(icc);
            }
            throw new IOException(IOException.InvalidIccProfile);
        } catch (Exception ex) {
            throw new IOException(IOException.InvalidIccProfile, (Throwable) ex);
        }
    }

    public static IccProfile getInstance(InputStream stream) {
        try {
            return getInstance(new RandomAccessFileOrArray(new RandomAccessSourceFactory().createSource(stream)));
        } catch (java.io.IOException e) {
            throw new IOException(IOException.InvalidIccProfile, (Throwable) e);
        }
    }

    public static IccProfile getInstance(String filename) {
        try {
            return getInstance(new RandomAccessFileOrArray(new RandomAccessSourceFactory().createBestSource(filename)));
        } catch (java.io.IOException e) {
            throw new IOException(IOException.InvalidIccProfile, (Throwable) e);
        }
    }

    public static String getIccColorSpaceName(byte[] data2) {
        try {
            return new String(data2, 16, 4, "US-ASCII");
        } catch (UnsupportedEncodingException e) {
            throw new IOException(IOException.InvalidIccProfile, (Throwable) e);
        }
    }

    public static String getIccDeviceClass(byte[] data2) {
        try {
            return new String(data2, 12, 4, "US-ASCII");
        } catch (UnsupportedEncodingException e) {
            throw new IOException(IOException.InvalidIccProfile, (Throwable) e);
        }
    }

    public static Integer getIccNumberOfComponents(byte[] data2) {
        return cstags.get(getIccColorSpaceName(data2));
    }

    public byte[] getData() {
        return this.data;
    }

    public int getNumComponents() {
        return this.numComponents;
    }
}
