package com.itextpdf.kernel.pdf.canvas.parser.util;

import com.itextpdf.kernel.PdfException;
import com.itextpdf.kernel.pdf.PdfArray;
import com.itextpdf.kernel.pdf.PdfDictionary;
import com.itextpdf.kernel.pdf.PdfLiteral;
import com.itextpdf.kernel.pdf.PdfName;
import com.itextpdf.kernel.pdf.PdfNumber;
import com.itextpdf.kernel.pdf.PdfObject;
import com.itextpdf.kernel.pdf.PdfResources;
import com.itextpdf.kernel.pdf.PdfStream;
import com.itextpdf.kernel.pdf.PdfString;
import com.itextpdf.p026io.source.PdfTokenizer;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PdfCanvasParser {
    private PdfResources currentResources;
    private PdfTokenizer tokeniser;

    public PdfCanvasParser(PdfTokenizer tokeniser2) {
        this.tokeniser = tokeniser2;
    }

    public PdfCanvasParser(PdfTokenizer tokeniser2, PdfResources currentResources2) {
        this.tokeniser = tokeniser2;
        this.currentResources = currentResources2;
    }

    public List<PdfObject> parse(List<PdfObject> ls) throws IOException {
        if (ls == null) {
            ls = new ArrayList<>();
        } else {
            ls.clear();
        }
        while (true) {
            PdfObject readObject = readObject();
            PdfObject ob = readObject;
            if (readObject == null) {
                break;
            }
            ls.add(ob);
            if (this.tokeniser.getTokenType() == PdfTokenizer.TokenType.Other) {
                if ("BI".equals(ob.toString())) {
                    PdfStream inlineImageAsStream = InlineImageParsingUtils.parse(this, this.currentResources.getResource(PdfName.ColorSpace));
                    ls.clear();
                    ls.add(inlineImageAsStream);
                    ls.add(new PdfLiteral("EI"));
                }
            }
        }
        return ls;
    }

    public PdfTokenizer getTokeniser() {
        return this.tokeniser;
    }

    public void setTokeniser(PdfTokenizer tokeniser2) {
        this.tokeniser = tokeniser2;
    }

    public PdfDictionary readDictionary() throws IOException {
        PdfDictionary dic = new PdfDictionary();
        while (nextValidToken()) {
            if (this.tokeniser.getTokenType() == PdfTokenizer.TokenType.EndDic) {
                return dic;
            }
            if (this.tokeniser.getTokenType() != PdfTokenizer.TokenType.Name) {
                PdfTokenizer pdfTokenizer = this.tokeniser;
                pdfTokenizer.throwError(PdfException.DictionaryKey1IsNotAName, pdfTokenizer.getStringValue());
            }
            dic.put(new PdfName(this.tokeniser.getStringValue()), readObject());
        }
        throw new PdfException(PdfException.UnexpectedEndOfFile);
    }

    public PdfArray readArray() throws IOException {
        PdfArray array = new PdfArray();
        while (true) {
            PdfObject obj = readObject();
            if (!obj.isArray() && this.tokeniser.getTokenType() == PdfTokenizer.TokenType.EndArray) {
                return array;
            }
            if (this.tokeniser.getTokenType() == PdfTokenizer.TokenType.EndDic && obj.getType() != 3) {
                this.tokeniser.throwError(PdfException.UnexpectedGtGt, new Object[0]);
            }
            array.add(obj);
        }
    }

    public PdfObject readObject() throws IOException {
        if (!nextValidToken()) {
            return null;
        }
        switch (C14401.$SwitchMap$com$itextpdf$io$source$PdfTokenizer$TokenType[this.tokeniser.getTokenType().ordinal()]) {
            case 1:
                return readDictionary();
            case 2:
                return readArray();
            case 3:
                return new PdfString(this.tokeniser.getDecodedStringContent()).setHexWriting(this.tokeniser.isHexString());
            case 4:
                return new PdfName(this.tokeniser.getByteContent());
            case 5:
                return new PdfNumber(this.tokeniser.getByteContent());
            default:
                return new PdfLiteral(this.tokeniser.getByteContent());
        }
    }

    /* renamed from: com.itextpdf.kernel.pdf.canvas.parser.util.PdfCanvasParser$1 */
    static /* synthetic */ class C14401 {
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
}
