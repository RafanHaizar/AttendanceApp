package com.itextpdf.kernel.pdf.filters;

import com.itextpdf.kernel.pdf.PdfDictionary;
import com.itextpdf.kernel.pdf.PdfName;
import com.itextpdf.kernel.pdf.PdfObject;

public interface IFilterHandler {
    byte[] decode(byte[] bArr, PdfName pdfName, PdfObject pdfObject, PdfDictionary pdfDictionary);
}
