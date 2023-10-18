package com.itextpdf.p026io.font.cmap;

import com.itextpdf.kernel.PdfException;
import com.itextpdf.p026io.font.PdfEncodings;
import com.itextpdf.p026io.source.ByteBuffer;
import com.itextpdf.p026io.source.PdfTokenizer;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* renamed from: com.itextpdf.io.font.cmap.CMapContentParser */
public class CMapContentParser {
    public static final int COMMAND_TYPE = 200;
    private PdfTokenizer tokeniser;

    public CMapContentParser(PdfTokenizer tokeniser2) {
        this.tokeniser = tokeniser2;
    }

    public void parse(List<CMapObject> ls) throws IOException {
        CMapObject ob;
        ls.clear();
        do {
            CMapObject readObject = readObject();
            ob = readObject;
            if (readObject != null) {
                ls.add(ob);
            } else {
                return;
            }
        } while (!ob.isLiteral());
    }

    public CMapObject readDictionary() throws IOException {
        Map<String, CMapObject> dic = new HashMap<>();
        while (nextValidToken()) {
            if (this.tokeniser.getTokenType() == PdfTokenizer.TokenType.EndDic) {
                return new CMapObject(7, dic);
            }
            if (this.tokeniser.getTokenType() != PdfTokenizer.TokenType.Other || !"def".equals(this.tokeniser.getStringValue())) {
                if (this.tokeniser.getTokenType() == PdfTokenizer.TokenType.Name) {
                    String name = this.tokeniser.getStringValue();
                    CMapObject obj = readObject();
                    if (obj.isToken()) {
                        if (obj.toString().equals(">>")) {
                            this.tokeniser.throwError(com.itextpdf.p026io.IOException.UnexpectedGtGt, new Object[0]);
                        }
                        if (obj.toString().equals("]")) {
                            this.tokeniser.throwError("Unexpected close bracket.", new Object[0]);
                        }
                    }
                    dic.put(name, obj);
                } else {
                    throw new com.itextpdf.p026io.IOException(PdfException.DictionaryKey1IsNotAName).setMessageParams(this.tokeniser.getStringValue());
                }
            }
        }
        throw new com.itextpdf.p026io.IOException(PdfException.UnexpectedEndOfFile);
    }

    public CMapObject readArray() throws IOException {
        List<CMapObject> array = new ArrayList<>();
        while (true) {
            CMapObject obj = readObject();
            if (obj.isToken()) {
                if (obj.toString().equals("]")) {
                    return new CMapObject(6, array);
                }
                if (obj.toString().equals(">>")) {
                    this.tokeniser.throwError(com.itextpdf.p026io.IOException.UnexpectedGtGt, new Object[0]);
                }
            }
            array.add(obj);
        }
    }

    public CMapObject readObject() throws IOException {
        if (!nextValidToken()) {
            return null;
        }
        switch (C14121.$SwitchMap$com$itextpdf$io$source$PdfTokenizer$TokenType[this.tokeniser.getTokenType().ordinal()]) {
            case 1:
                return readDictionary();
            case 2:
                return readArray();
            case 3:
                if (this.tokeniser.isHexString()) {
                    return new CMapObject(2, PdfTokenizer.decodeStringContent(this.tokeniser.getByteContent(), true));
                }
                return new CMapObject(1, PdfTokenizer.decodeStringContent(this.tokeniser.getByteContent(), false));
            case 4:
                return new CMapObject(3, decodeName(this.tokeniser.getByteContent()));
            case 5:
                CMapObject numObject = new CMapObject(4, (Object) null);
                try {
                    numObject.setValue(Integer.valueOf((int) Double.parseDouble(this.tokeniser.getStringValue())));
                } catch (NumberFormatException e) {
                    numObject.setValue(Integer.MIN_VALUE);
                }
                return numObject;
            case 6:
                return new CMapObject(5, this.tokeniser.getStringValue());
            case 7:
                return new CMapObject(8, "]");
            case 8:
                return new CMapObject(8, ">>");
            default:
                return new CMapObject(0, "");
        }
    }

    /* renamed from: com.itextpdf.io.font.cmap.CMapContentParser$1 */
    static /* synthetic */ class C14121 {
        static final /* synthetic */ int[] $SwitchMap$com$itextpdf$io$source$PdfTokenizer$TokenType;

        static {
            int[] iArr = new int[PdfTokenizer.TokenType.values().length];
            $SwitchMap$com$itextpdf$io$source$PdfTokenizer$TokenType = iArr;
            try {
                iArr[PdfTokenizer.TokenType.StartDic.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$itextpdf$io$source$PdfTokenizer$TokenType[PdfTokenizer.TokenType.StartArray.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$itextpdf$io$source$PdfTokenizer$TokenType[PdfTokenizer.TokenType.String.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$com$itextpdf$io$source$PdfTokenizer$TokenType[PdfTokenizer.TokenType.Name.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$com$itextpdf$io$source$PdfTokenizer$TokenType[PdfTokenizer.TokenType.Number.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                $SwitchMap$com$itextpdf$io$source$PdfTokenizer$TokenType[PdfTokenizer.TokenType.Other.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
            try {
                $SwitchMap$com$itextpdf$io$source$PdfTokenizer$TokenType[PdfTokenizer.TokenType.EndArray.ordinal()] = 7;
            } catch (NoSuchFieldError e7) {
            }
            try {
                $SwitchMap$com$itextpdf$io$source$PdfTokenizer$TokenType[PdfTokenizer.TokenType.EndDic.ordinal()] = 8;
            } catch (NoSuchFieldError e8) {
            }
        }
    }

    public boolean nextValidToken() throws IOException {
        while (this.tokeniser.nextToken()) {
            if (this.tokeniser.getTokenType() != PdfTokenizer.TokenType.Comment) {
                return true;
            }
        }
        return false;
    }

    protected static String decodeName(byte[] content) {
        StringBuilder buf = new StringBuilder();
        int k = 0;
        while (k < content.length) {
            try {
                char c = (char) content[k];
                if (c == '#') {
                    c = (char) ((ByteBuffer.getHex(content[k + 1]) << 4) + ByteBuffer.getHex(content[k + 2]));
                    k += 2;
                }
                buf.append(c);
                k++;
            } catch (IndexOutOfBoundsException e) {
            }
        }
        return buf.toString();
    }

    private static String toHex4(int n) {
        String s = "0000" + Integer.toHexString(n);
        return s.substring(s.length() - 4);
    }

    public static String toHex(int n) {
        if (n < 65536) {
            return "<" + toHex4(n) + ">";
        }
        int n2 = n - 65536;
        return "[<" + toHex4((n2 / 1024) + 55296) + toHex4((n2 % 1024) + 56320) + ">]";
    }

    public static String decodeCMapObject(CMapObject cMapObject) {
        if (cMapObject.isHexString()) {
            return PdfEncodings.convertToString(((String) cMapObject.getValue()).getBytes(), PdfEncodings.UNICODE_BIG_UNMARKED);
        }
        return (String) cMapObject.getValue();
    }
}
