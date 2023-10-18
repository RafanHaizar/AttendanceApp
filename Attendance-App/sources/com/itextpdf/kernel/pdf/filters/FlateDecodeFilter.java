package com.itextpdf.kernel.pdf.filters;

import com.itextpdf.kernel.PdfException;
import com.itextpdf.kernel.pdf.MemoryLimitsAwareException;
import com.itextpdf.kernel.pdf.MemoryLimitsAwareFilter;
import com.itextpdf.kernel.pdf.PdfDictionary;
import com.itextpdf.kernel.pdf.PdfName;
import com.itextpdf.kernel.pdf.PdfNumber;
import com.itextpdf.kernel.pdf.PdfObject;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.zip.InflaterInputStream;
import kotlin.UByte;

public class FlateDecodeFilter extends MemoryLimitsAwareFilter {
    @Deprecated
    private boolean strictDecoding;

    public FlateDecodeFilter() {
        this(false);
    }

    @Deprecated
    public FlateDecodeFilter(boolean strictDecoding2) {
        this.strictDecoding = false;
        this.strictDecoding = strictDecoding2;
    }

    @Deprecated
    public boolean isStrictDecoding() {
        return this.strictDecoding;
    }

    public static byte[] flateDecode(byte[] in, boolean strict) {
        return flateDecodeInternal(in, strict, new ByteArrayOutputStream());
    }

    public static byte[] decodePredictor(byte[] in, PdfObject decodeParams) {
        int colors;
        int predictor;
        PdfObject obj;
        byte[] bArr = in;
        if (decodeParams == null || decodeParams.getType() != 3) {
            return bArr;
        }
        PdfDictionary dic = (PdfDictionary) decodeParams;
        PdfObject obj2 = dic.get(PdfName.Predictor);
        if (obj2 == null) {
            PdfObject pdfObject = obj2;
        } else if (obj2.getType() != 8) {
            PdfDictionary pdfDictionary = dic;
            PdfObject pdfObject2 = obj2;
        } else {
            int predictor2 = ((PdfNumber) obj2).intValue();
            if (predictor2 < 10 && predictor2 != 2) {
                return bArr;
            }
            int width = getNumberOrDefault(dic, PdfName.Columns, 1);
            int colors2 = getNumberOrDefault(dic, PdfName.Colors, 1);
            int bpc = getNumberOrDefault(dic, PdfName.BitsPerComponent, 8);
            DataInputStream dataStream = new DataInputStream(new ByteArrayInputStream(bArr));
            ByteArrayOutputStream fout = new ByteArrayOutputStream(bArr.length);
            int bytesPerPixel = (colors2 * bpc) / 8;
            int bytesPerRow = (((colors2 * width) * bpc) + 7) / 8;
            byte[] curr = new byte[bytesPerRow];
            byte[] prior = new byte[bytesPerRow];
            if (predictor2 == 2) {
                if (bpc == 8) {
                    int numRows = bArr.length / bytesPerRow;
                    int row = 0;
                    while (row < numRows) {
                        int rowStart = row * bytesPerRow;
                        byte[] curr2 = curr;
                        int col = bytesPerPixel;
                        while (col < bytesPerRow) {
                            bArr[rowStart + col] = (byte) (bArr[rowStart + col] + bArr[(rowStart + col) - bytesPerPixel]);
                            col++;
                            dic = dic;
                        }
                        row++;
                        curr = curr2;
                    }
                    PdfDictionary pdfDictionary2 = dic;
                } else {
                    PdfDictionary pdfDictionary3 = dic;
                }
                return bArr;
            }
            PdfDictionary pdfDictionary4 = dic;
            byte[] curr3 = curr;
            while (true) {
                try {
                    int filter = dataStream.read();
                    if (filter < 0) {
                        try {
                            return fout.toByteArray();
                        } catch (Exception e) {
                            PdfObject pdfObject3 = obj2;
                            int i = predictor2;
                            int i2 = colors2;
                            return fout.toByteArray();
                        }
                    } else {
                        dataStream.readFully(curr3, 0, bytesPerRow);
                        switch (filter) {
                            case 0:
                                obj = obj2;
                                int i3 = filter;
                                predictor = predictor2;
                                colors = colors2;
                                break;
                            case 1:
                                obj = obj2;
                                int i4 = filter;
                                predictor = predictor2;
                                colors = colors2;
                                for (int i5 = bytesPerPixel; i5 < bytesPerRow; i5++) {
                                    curr3[i5] = (byte) (curr3[i5] + curr3[i5 - bytesPerPixel]);
                                }
                                break;
                            case 2:
                                obj = obj2;
                                int i6 = filter;
                                predictor = predictor2;
                                colors = colors2;
                                for (int i7 = 0; i7 < bytesPerRow; i7++) {
                                    curr3[i7] = (byte) (curr3[i7] + prior[i7]);
                                }
                                break;
                            case 3:
                                obj = obj2;
                                int i8 = filter;
                                predictor = predictor2;
                                colors = colors2;
                                for (int i9 = 0; i9 < bytesPerPixel; i9++) {
                                    curr3[i9] = (byte) (curr3[i9] + ((byte) (prior[i9] / 2)));
                                }
                                for (int i10 = bytesPerPixel; i10 < bytesPerRow; i10++) {
                                    curr3[i10] = (byte) (curr3[i10] + ((byte) (((curr3[i10 - bytesPerPixel] & UByte.MAX_VALUE) + (prior[i10] & UByte.MAX_VALUE)) / 2)));
                                }
                                break;
                            case 4:
                                for (int i11 = 0; i11 < bytesPerPixel; i11++) {
                                    curr3[i11] = (byte) (curr3[i11] + prior[i11]);
                                }
                                int i12 = bytesPerPixel;
                                while (i12 < bytesPerRow) {
                                    int i13 = curr3[i12 - bytesPerPixel] & 255;
                                    int i14 = prior[i12] & 255;
                                    PdfObject obj3 = obj2;
                                    int c = prior[i12 - bytesPerPixel] & 255;
                                    int p = (i13 + i14) - c;
                                    int filter2 = filter;
                                    int pa = Math.abs(p - i13);
                                    int predictor3 = predictor2;
                                    int pb = Math.abs(p - i14);
                                    int colors3 = colors2;
                                    int colors4 = Math.abs(p - c);
                                    if (pa <= pb && pa <= colors4) {
                                        byte b = c;
                                        c = i13;
                                    } else if (pb <= colors4) {
                                        byte b2 = c;
                                        c = i14;
                                    } else {
                                        int ret = c;
                                        byte b3 = c;
                                    }
                                    int i15 = pa;
                                    curr3[i12] = (byte) (curr3[i12] + ((byte) c));
                                    i12++;
                                    obj2 = obj3;
                                    filter = filter2;
                                    predictor2 = predictor3;
                                    colors2 = colors3;
                                }
                                obj = obj2;
                                int i16 = filter;
                                predictor = predictor2;
                                colors = colors2;
                                break;
                            default:
                                PdfObject pdfObject4 = obj2;
                                throw new PdfException(PdfException.PngFilterUnknown);
                        }
                        try {
                            fout.write(curr3);
                        } catch (IOException e2) {
                        }
                        byte[] tmp = prior;
                        prior = curr3;
                        curr3 = tmp;
                        obj2 = obj;
                        predictor2 = predictor;
                        colors2 = colors;
                    }
                } catch (Exception e3) {
                    PdfObject pdfObject5 = obj2;
                    int i17 = predictor2;
                    int i18 = colors2;
                }
            }
        }
        return bArr;
    }

    public byte[] decode(byte[] b, PdfName filterName, PdfObject decodeParams, PdfDictionary streamDictionary) {
        ByteArrayOutputStream outputStream = enableMemoryLimitsAwareHandler(streamDictionary);
        byte[] res = flateDecodeInternal(b, true, outputStream);
        if (res == null && !this.strictDecoding) {
            outputStream.reset();
            res = flateDecodeInternal(b, false, outputStream);
        }
        return decodePredictor(res, decodeParams);
    }

    @Deprecated
    public FlateDecodeFilter setStrictDecoding(boolean strict) {
        this.strictDecoding = strict;
        return this;
    }

    protected static byte[] flateDecodeInternal(byte[] in, boolean strict, ByteArrayOutputStream out) {
        InflaterInputStream zip = new InflaterInputStream(new ByteArrayInputStream(in));
        byte[] b = new byte[(strict ? 4092 : 1)];
        while (true) {
            try {
                int read = zip.read(b);
                int n = read;
                if (read >= 0) {
                    out.write(b, 0, n);
                } else {
                    zip.close();
                    out.close();
                    return out.toByteArray();
                }
            } catch (MemoryLimitsAwareException e) {
                throw e;
            } catch (Exception e2) {
                if (strict) {
                    return null;
                }
                return out.toByteArray();
            }
        }
    }

    private static int getNumberOrDefault(PdfDictionary dict, PdfName key, int defaultInt) {
        int result = defaultInt;
        PdfObject obj = dict.get(key);
        if (obj == null || obj.getType() != 8) {
            return result;
        }
        return ((PdfNumber) obj).intValue();
    }
}
