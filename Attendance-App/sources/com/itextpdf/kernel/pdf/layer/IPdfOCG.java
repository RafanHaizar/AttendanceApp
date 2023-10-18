package com.itextpdf.kernel.pdf.layer;

import com.itextpdf.kernel.pdf.PdfDictionary;
import com.itextpdf.kernel.pdf.PdfIndirectReference;

public interface IPdfOCG {
    PdfIndirectReference getIndirectReference();

    PdfDictionary getPdfObject();
}
