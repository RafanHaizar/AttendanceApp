package com.itextpdf.p026io.font.cmap;

import com.itextpdf.p026io.LogMessageConstant;
import com.itextpdf.p026io.util.IntHashtable;
import com.itextpdf.p026io.util.TextUtil;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import kotlin.UByte;
import org.slf4j.LoggerFactory;

/* renamed from: com.itextpdf.io.font.cmap.CMapToUnicode */
public class CMapToUnicode extends AbstractCMap {
    public static CMapToUnicode EmptyCMapToUnicodeMap = new CMapToUnicode(true);
    private static final long serialVersionUID = 1037675640549795312L;
    private Map<Integer, char[]> byteMappings;

    private CMapToUnicode(boolean emptyCMap) {
        this.byteMappings = Collections.emptyMap();
    }

    public CMapToUnicode() {
        this.byteMappings = new HashMap();
    }

    public static CMapToUnicode getIdentity() {
        CMapToUnicode uni = new CMapToUnicode();
        for (int i = 0; i < 65537; i++) {
            uni.addChar(i, TextUtil.convertFromUtf32(i));
        }
        return uni;
    }

    public boolean hasByteMappings() {
        return this.byteMappings.size() != 0;
    }

    public char[] lookup(byte[] code, int offset, int length) {
        if (length == 1) {
            return this.byteMappings.get(Integer.valueOf(code[offset] & 255));
        }
        if (length == 2) {
            return this.byteMappings.get(Integer.valueOf(((code[offset] & 255) << 8) + (code[offset + 1] & UByte.MAX_VALUE)));
        }
        return null;
    }

    public char[] lookup(byte[] code) {
        return lookup(code, 0, code.length);
    }

    public char[] lookup(int code) {
        return this.byteMappings.get(Integer.valueOf(code));
    }

    public Set<Integer> getCodes() {
        return this.byteMappings.keySet();
    }

    public IntHashtable createDirectMapping() {
        IntHashtable result = new IntHashtable();
        for (Map.Entry<Integer, char[]> entry : this.byteMappings.entrySet()) {
            if (entry.getValue().length == 1) {
                result.put(entry.getKey().intValue(), convertToInt(entry.getValue()));
            }
        }
        return result;
    }

    public Map<Integer, Integer> createReverseMapping() throws IOException {
        Map<Integer, Integer> result = new HashMap<>();
        for (Map.Entry<Integer, char[]> entry : this.byteMappings.entrySet()) {
            if (entry.getValue().length == 1) {
                result.put(Integer.valueOf(convertToInt(entry.getValue())), entry.getKey());
            }
        }
        return result;
    }

    private int convertToInt(char[] s) {
        int value = 0;
        for (int i = 0; i < s.length - 1; i++) {
            value = (value + s[i]) << 8;
        }
        return value + s[s.length - 1];
    }

    /* access modifiers changed from: package-private */
    public void addChar(int cid, char[] uni) {
        this.byteMappings.put(Integer.valueOf(cid), uni);
    }

    /* access modifiers changed from: package-private */
    public void addChar(String mark, CMapObject code) {
        if (mark.length() == 1) {
            this.byteMappings.put(Integer.valueOf(mark.charAt(0)), createCharsFromDoubleBytes((byte[]) code.getValue()));
        } else if (mark.length() == 2) {
            this.byteMappings.put(Integer.valueOf((mark.charAt(0) << 8) + mark.charAt(1)), createCharsFromDoubleBytes((byte[]) code.getValue()));
        } else {
            LoggerFactory.getLogger((Class<?>) CMapToUnicode.class).warn(LogMessageConstant.TOUNICODE_CMAP_MORE_THAN_2_BYTES_NOT_SUPPORTED);
        }
    }

    private char[] createCharsFromSingleBytes(byte[] bytes) {
        if (bytes.length == 1) {
            return new char[]{(char) (bytes[0] & UByte.MAX_VALUE)};
        }
        char[] chars = new char[bytes.length];
        for (int i = 0; i < bytes.length; i++) {
            chars[i] = (char) (bytes[i] & UByte.MAX_VALUE);
        }
        return chars;
    }

    private char[] createCharsFromDoubleBytes(byte[] bytes) {
        char[] chars = new char[(bytes.length / 2)];
        for (int i = 0; i < bytes.length; i += 2) {
            chars[i / 2] = (char) (((bytes[i] & UByte.MAX_VALUE) << 8) + (bytes[i + 1] & UByte.MAX_VALUE));
        }
        return chars;
    }
}
