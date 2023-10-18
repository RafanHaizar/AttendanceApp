package com.itextpdf.p026io.font;

/* renamed from: com.itextpdf.io.font.IExtraEncoding */
public interface IExtraEncoding {
    String byteToChar(byte[] bArr, String str);

    byte[] charToByte(char c, String str);

    byte[] charToByte(String str, String str2);
}
