package com.itextpdf.kernel.pdf.canvas.parser;

import com.itextpdf.kernel.pdf.PdfLiteral;
import com.itextpdf.kernel.pdf.PdfObject;
import java.util.List;

public interface IContentOperator {
    void invoke(PdfCanvasProcessor pdfCanvasProcessor, PdfLiteral pdfLiteral, List<PdfObject> list);
}
