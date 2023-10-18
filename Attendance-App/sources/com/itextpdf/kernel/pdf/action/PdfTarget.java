package com.itextpdf.kernel.pdf.action;

import com.itextpdf.kernel.PdfException;
import com.itextpdf.kernel.pdf.PdfDictionary;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfName;
import com.itextpdf.kernel.pdf.PdfNumber;
import com.itextpdf.kernel.pdf.PdfObject;
import com.itextpdf.kernel.pdf.PdfObjectWrapper;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfString;
import com.itextpdf.kernel.pdf.annot.PdfAnnotation;
import com.itextpdf.kernel.pdf.annot.PdfFileAttachmentAnnotation;
import java.util.List;

public class PdfTarget extends PdfObjectWrapper<PdfDictionary> {
    private static final long serialVersionUID = -5814265943827690509L;

    private PdfTarget(PdfDictionary pdfObject) {
        super(pdfObject);
    }

    public static PdfTarget create(PdfDictionary pdfObject) {
        return new PdfTarget(pdfObject);
    }

    private static PdfTarget create(PdfName r) {
        PdfTarget pdfTarget = new PdfTarget(new PdfDictionary());
        pdfTarget.put(PdfName.f1376R, r);
        return pdfTarget;
    }

    public static PdfTarget createParentTarget() {
        return create(PdfName.f1367P);
    }

    public static PdfTarget createChildTarget() {
        return create(PdfName.f1300C);
    }

    public static PdfTarget createChildTarget(String embeddedFileName) {
        return create(PdfName.f1300C).put(PdfName.f1357N, new PdfString(embeddedFileName));
    }

    public static PdfTarget createChildTarget(String namedDestination, String annotationIdentifier) {
        return create(PdfName.f1300C).put(PdfName.f1367P, new PdfString(namedDestination)).put(PdfName.f1287A, new PdfString(annotationIdentifier));
    }

    public static PdfTarget createChildTarget(int pageNumber, int annotationIndex) {
        return create(PdfName.f1300C).put(PdfName.f1367P, new PdfNumber(pageNumber - 1)).put(PdfName.f1287A, new PdfNumber(annotationIndex));
    }

    public PdfTarget setName(String name) {
        return put(PdfName.f1357N, new PdfString(name));
    }

    public String getName() {
        return ((PdfDictionary) getPdfObject()).getAsString(PdfName.f1357N).toString();
    }

    public PdfTarget setAnnotation(PdfFileAttachmentAnnotation pdfAnnotation, PdfDocument pdfDocument) {
        PdfPage page = pdfAnnotation.getPage();
        if (page != null) {
            put(PdfName.f1367P, new PdfNumber(pdfDocument.getPageNumber(page) - 1));
            int indexOfAnnotation = -1;
            List<PdfAnnotation> annots = page.getAnnotations();
            int i = 0;
            while (true) {
                if (i < annots.size()) {
                    if (annots.get(i) != null && ((PdfDictionary) pdfAnnotation.getPdfObject()).equals(annots.get(i).getPdfObject())) {
                        indexOfAnnotation = i;
                        break;
                    }
                    i++;
                } else {
                    break;
                }
            }
            put(PdfName.f1287A, new PdfNumber(indexOfAnnotation));
            return this;
        }
        throw new PdfException(PdfException.AnnotationShallHaveReferenceToPage);
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v3, resolved type: com.itextpdf.kernel.pdf.annot.PdfAnnotation} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v3, resolved type: com.itextpdf.kernel.pdf.annot.PdfFileAttachmentAnnotation} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.itextpdf.kernel.pdf.annot.PdfFileAttachmentAnnotation getAnnotation(com.itextpdf.kernel.pdf.PdfDocument r9) {
        /*
            r8 = this;
            com.itextpdf.kernel.pdf.PdfObject r0 = r8.getPdfObject()
            com.itextpdf.kernel.pdf.PdfDictionary r0 = (com.itextpdf.kernel.pdf.PdfDictionary) r0
            com.itextpdf.kernel.pdf.PdfName r1 = com.itextpdf.kernel.pdf.PdfName.f1367P
            com.itextpdf.kernel.pdf.PdfObject r0 = r0.get(r1)
            r1 = 0
            boolean r2 = r0 instanceof com.itextpdf.kernel.pdf.PdfNumber
            if (r2 == 0) goto L_0x001f
            r2 = r0
            com.itextpdf.kernel.pdf.PdfNumber r2 = (com.itextpdf.kernel.pdf.PdfNumber) r2
            int r2 = r2.intValue()
            int r2 = r2 + 1
            com.itextpdf.kernel.pdf.PdfPage r1 = r9.getPage((int) r2)
            goto L_0x0062
        L_0x001f:
            boolean r2 = r0 instanceof com.itextpdf.kernel.pdf.PdfString
            if (r2 == 0) goto L_0x0062
            com.itextpdf.kernel.pdf.PdfCatalog r2 = r9.getCatalog()
            com.itextpdf.kernel.pdf.PdfName r3 = com.itextpdf.kernel.pdf.PdfName.Dests
            com.itextpdf.kernel.pdf.PdfNameTree r2 = r2.getNameTree(r3)
            java.util.Map r3 = r2.getNames()
            r4 = r0
            com.itextpdf.kernel.pdf.PdfString r4 = (com.itextpdf.kernel.pdf.PdfString) r4
            java.lang.String r4 = r4.getValue()
            java.lang.Object r4 = r3.get(r4)
            com.itextpdf.kernel.pdf.PdfArray r4 = (com.itextpdf.kernel.pdf.PdfArray) r4
            if (r4 == 0) goto L_0x0062
            r5 = 0
            com.itextpdf.kernel.pdf.PdfObject r6 = r4.get(r5)
            boolean r6 = r6 instanceof com.itextpdf.kernel.pdf.PdfNumber
            if (r6 == 0) goto L_0x0058
            com.itextpdf.kernel.pdf.PdfObject r5 = r4.get(r5)
            com.itextpdf.kernel.pdf.PdfNumber r5 = (com.itextpdf.kernel.pdf.PdfNumber) r5
            int r5 = r5.intValue()
            com.itextpdf.kernel.pdf.PdfPage r1 = r9.getPage((int) r5)
            goto L_0x0062
        L_0x0058:
            com.itextpdf.kernel.pdf.PdfObject r5 = r4.get(r5)
            com.itextpdf.kernel.pdf.PdfDictionary r5 = (com.itextpdf.kernel.pdf.PdfDictionary) r5
            com.itextpdf.kernel.pdf.PdfPage r1 = r9.getPage((com.itextpdf.kernel.pdf.PdfDictionary) r5)
        L_0x0062:
            r2 = 0
            if (r1 == 0) goto L_0x0069
            java.util.List r2 = r1.getAnnotations()
        L_0x0069:
            com.itextpdf.kernel.pdf.PdfObject r3 = r8.getPdfObject()
            com.itextpdf.kernel.pdf.PdfDictionary r3 = (com.itextpdf.kernel.pdf.PdfDictionary) r3
            com.itextpdf.kernel.pdf.PdfName r4 = com.itextpdf.kernel.pdf.PdfName.f1287A
            com.itextpdf.kernel.pdf.PdfObject r3 = r3.get(r4)
            r4 = 0
            if (r2 == 0) goto L_0x00ae
            boolean r5 = r3 instanceof com.itextpdf.kernel.pdf.PdfNumber
            if (r5 == 0) goto L_0x008b
            r5 = r3
            com.itextpdf.kernel.pdf.PdfNumber r5 = (com.itextpdf.kernel.pdf.PdfNumber) r5
            int r5 = r5.intValue()
            java.lang.Object r5 = r2.get(r5)
            r4 = r5
            com.itextpdf.kernel.pdf.annot.PdfFileAttachmentAnnotation r4 = (com.itextpdf.kernel.pdf.annot.PdfFileAttachmentAnnotation) r4
            goto L_0x00ae
        L_0x008b:
            boolean r5 = r3 instanceof com.itextpdf.kernel.pdf.PdfString
            if (r5 == 0) goto L_0x00ae
            java.util.Iterator r5 = r2.iterator()
        L_0x0093:
            boolean r6 = r5.hasNext()
            if (r6 == 0) goto L_0x00ae
            java.lang.Object r6 = r5.next()
            com.itextpdf.kernel.pdf.annot.PdfAnnotation r6 = (com.itextpdf.kernel.pdf.annot.PdfAnnotation) r6
            com.itextpdf.kernel.pdf.PdfString r7 = r6.getName()
            boolean r7 = r3.equals(r7)
            if (r7 == 0) goto L_0x00ad
            r4 = r6
            com.itextpdf.kernel.pdf.annot.PdfFileAttachmentAnnotation r4 = (com.itextpdf.kernel.pdf.annot.PdfFileAttachmentAnnotation) r4
            goto L_0x00ae
        L_0x00ad:
            goto L_0x0093
        L_0x00ae:
            if (r4 != 0) goto L_0x00bb
            java.lang.Class<com.itextpdf.kernel.pdf.action.PdfTarget> r5 = com.itextpdf.kernel.pdf.action.PdfTarget.class
            org.slf4j.Logger r5 = org.slf4j.LoggerFactory.getLogger((java.lang.Class<?>) r5)
            java.lang.String r6 = "Some fields in target dictionary are not set or incorrect. Null will be returned."
            r5.error(r6)
        L_0x00bb:
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.kernel.pdf.action.PdfTarget.getAnnotation(com.itextpdf.kernel.pdf.PdfDocument):com.itextpdf.kernel.pdf.annot.PdfFileAttachmentAnnotation");
    }

    public PdfTarget setTarget(PdfTarget target) {
        return put(PdfName.f1391T, target.getPdfObject());
    }

    public PdfTarget getTarget() {
        PdfDictionary targetDictObject = ((PdfDictionary) getPdfObject()).getAsDictionary(PdfName.f1391T);
        if (targetDictObject != null) {
            return new PdfTarget(targetDictObject);
        }
        return null;
    }

    public PdfTarget put(PdfName key, PdfObject value) {
        ((PdfDictionary) getPdfObject()).put(key, value);
        return this;
    }

    /* access modifiers changed from: protected */
    public boolean isWrappedObjectMustBeIndirect() {
        return false;
    }
}
