package com.itextpdf.forms.xfdf;

import com.itextpdf.forms.PdfAcroForm;
import com.itextpdf.forms.fields.PdfFormField;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfString;
import com.itextpdf.kernel.pdf.annot.PdfAnnotation;
import com.itextpdf.kernel.pdf.annot.PdfMarkupAnnotation;
import com.itextpdf.p026io.LogMessageConstant;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class XfdfReader {
    private static Logger logger = LoggerFactory.getLogger((Class<?>) XfdfReader.class);

    XfdfReader() {
    }

    /* access modifiers changed from: package-private */
    public void mergeXfdfIntoPdf(XfdfObject xfdfObject, PdfDocument pdfDocument, String pdfDocumentName) {
        if (xfdfObject.getF() == null || xfdfObject.getF().getHref() == null) {
            logger.warn(LogMessageConstant.XFDF_NO_F_OBJECT_TO_COMPARE);
        } else if (pdfDocumentName.equalsIgnoreCase(xfdfObject.getF().getHref())) {
            logger.info("Xfdf href and pdf name are equal. Continue merge");
        } else {
            logger.warn(LogMessageConstant.XFDF_HREF_ATTRIBUTE_AND_PDF_DOCUMENT_NAME_ARE_DIFFERENT);
        }
        PdfAcroForm form = PdfAcroForm.getAcroForm(pdfDocument, false);
        if (form != null) {
            mergeFields(xfdfObject.getFields(), form);
            mergeAnnotations(xfdfObject.getAnnots(), pdfDocument);
        }
    }

    private void mergeFields(FieldsObject fieldsObject, PdfAcroForm form) {
        if (fieldsObject != null && fieldsObject.getFieldList() != null && !fieldsObject.getFieldList().isEmpty()) {
            Map<String, PdfFormField> formFields = form.getFormFields();
            for (FieldObject xfdfField : fieldsObject.getFieldList()) {
                String name = xfdfField.getName();
                if (formFields.get(name) == null || xfdfField.getValue() == null) {
                    logger.error(LogMessageConstant.XFDF_NO_SUCH_FIELD_IN_PDF_DOCUMENT);
                } else {
                    formFields.get(name).setValue(xfdfField.getValue());
                }
            }
        }
    }

    private void mergeAnnotations(AnnotsObject annotsObject, PdfDocument pdfDocument) {
        List<AnnotObject> annotList = null;
        if (annotsObject != null) {
            annotList = annotsObject.getAnnotsList();
        }
        if (annotList != null && !annotList.isEmpty()) {
            for (AnnotObject annot : annotList) {
                addAnnotationToPdf(annot, pdfDocument);
            }
        }
    }

    private void addCommonAnnotationAttributes(PdfAnnotation annotation, AnnotObject annotObject) {
        annotation.setFlags(XfdfObjectUtils.convertFlagsFromString(annotObject.getAttributeValue(XfdfConstants.FLAGS)));
        annotation.setColor(XfdfObjectUtils.convertColorFloatsFromString(annotObject.getAttributeValue("color")));
        annotation.setDate(new PdfString(annotObject.getAttributeValue("date")));
        annotation.setName(new PdfString(annotObject.getAttributeValue(XfdfConstants.NAME)));
        annotation.setTitle(new PdfString(annotObject.getAttributeValue("title")));
    }

    private void addMarkupAnnotationAttributes(PdfMarkupAnnotation annotation, AnnotObject annotObject) {
        annotation.setCreationDate(new PdfString(annotObject.getAttributeValue(XfdfConstants.CREATION_DATE)));
        annotation.setSubject(new PdfString(annotObject.getAttributeValue("subject")));
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void addAnnotationToPdf(com.itextpdf.forms.xfdf.AnnotObject r9, com.itextpdf.kernel.pdf.PdfDocument r10) {
        /*
            r8 = this;
            java.lang.String r0 = r9.getName()
            if (r0 == 0) goto L_0x02e4
            int r1 = r0.hashCode()
            r2 = 0
            r3 = 1
            switch(r1) {
                case -1537391207: goto L_0x0080;
                case -1360216880: goto L_0x0076;
                case -1026963764: goto L_0x006b;
                case -894674659: goto L_0x0060;
                case -781822241: goto L_0x0055;
                case -681210700: goto L_0x004b;
                case -397519558: goto L_0x0040;
                case -192095652: goto L_0x0035;
                case 3556653: goto L_0x002a;
                case 109757379: goto L_0x001e;
                case 561938880: goto L_0x0011;
                default: goto L_0x000f;
            }
        L_0x000f:
            goto L_0x008b
        L_0x0011:
            java.lang.String r1 = "polyline"
            boolean r1 = r0.equals(r1)
            if (r1 == 0) goto L_0x000f
            r1 = 8
            goto L_0x008c
        L_0x001e:
            java.lang.String r1 = "stamp"
            boolean r1 = r0.equals(r1)
            if (r1 == 0) goto L_0x000f
            r1 = 9
            goto L_0x008c
        L_0x002a:
            java.lang.String r1 = "text"
            boolean r1 = r0.equals(r1)
            if (r1 == 0) goto L_0x000f
            r1 = 0
            goto L_0x008c
        L_0x0035:
            java.lang.String r1 = "strikeout"
            boolean r1 = r0.equals(r1)
            if (r1 == 0) goto L_0x000f
            r1 = 3
            goto L_0x008c
        L_0x0040:
            java.lang.String r1 = "polygon"
            boolean r1 = r0.equals(r1)
            if (r1 == 0) goto L_0x000f
            r1 = 7
            goto L_0x008c
        L_0x004b:
            java.lang.String r1 = "highlight"
            boolean r1 = r0.equals(r1)
            if (r1 == 0) goto L_0x000f
            r1 = 1
            goto L_0x008c
        L_0x0055:
            java.lang.String r1 = "squiggly"
            boolean r1 = r0.equals(r1)
            if (r1 == 0) goto L_0x000f
            r1 = 4
            goto L_0x008c
        L_0x0060:
            java.lang.String r1 = "square"
            boolean r1 = r0.equals(r1)
            if (r1 == 0) goto L_0x000f
            r1 = 6
            goto L_0x008c
        L_0x006b:
            java.lang.String r1 = "underline"
            boolean r1 = r0.equals(r1)
            if (r1 == 0) goto L_0x000f
            r1 = 2
            goto L_0x008c
        L_0x0076:
            java.lang.String r1 = "circle"
            boolean r1 = r0.equals(r1)
            if (r1 == 0) goto L_0x000f
            r1 = 5
            goto L_0x008c
        L_0x0080:
            java.lang.String r1 = "freetext"
            boolean r1 = r0.equals(r1)
            if (r1 == 0) goto L_0x000f
            r1 = 10
            goto L_0x008c
        L_0x008b:
            r1 = -1
        L_0x008c:
            java.lang.String r4 = "fringe"
            java.lang.String r5 = "coords"
            java.lang.String r6 = "page"
            java.lang.String r7 = "rect"
            switch(r1) {
                case 0: goto L_0x0289;
                case 1: goto L_0x0258;
                case 2: goto L_0x0226;
                case 3: goto L_0x01f4;
                case 4: goto L_0x01c2;
                case 5: goto L_0x0189;
                case 6: goto L_0x0150;
                case 7: goto L_0x0121;
                case 8: goto L_0x00f2;
                case 9: goto L_0x00d0;
                case 10: goto L_0x00aa;
                default: goto L_0x0099;
            }
        L_0x0099:
            org.slf4j.Logger r1 = logger
            java.lang.Object[] r3 = new java.lang.Object[r3]
            r3[r2] = r0
            java.lang.String r2 = "Xfdf annotation \"{0}\" is not supported"
            java.lang.String r2 = com.itextpdf.p026io.util.MessageFormatUtil.format(r2, r3)
            r1.warn(r2)
            goto L_0x02e4
        L_0x00aa:
            com.itextpdf.forms.xfdf.AttributeObject r1 = r9.getAttribute(r6)
            java.lang.String r1 = r1.getValue()
            int r1 = java.lang.Integer.parseInt(r1)
            com.itextpdf.kernel.pdf.PdfPage r1 = r10.getPage((int) r1)
            com.itextpdf.kernel.pdf.annot.PdfFreeTextAnnotation r2 = new com.itextpdf.kernel.pdf.annot.PdfFreeTextAnnotation
            java.lang.String r3 = r9.getAttributeValue(r7)
            com.itextpdf.kernel.geom.Rectangle r3 = com.itextpdf.forms.xfdf.XfdfObjectUtils.convertRectFromString(r3)
            com.itextpdf.kernel.pdf.PdfString r4 = r9.getContents()
            r2.<init>(r3, r4)
            r1.addAnnotation(r2)
            goto L_0x02e4
        L_0x00d0:
            com.itextpdf.forms.xfdf.AttributeObject r1 = r9.getAttribute(r6)
            java.lang.String r1 = r1.getValue()
            int r1 = java.lang.Integer.parseInt(r1)
            com.itextpdf.kernel.pdf.PdfPage r1 = r10.getPage((int) r1)
            com.itextpdf.kernel.pdf.annot.PdfStampAnnotation r2 = new com.itextpdf.kernel.pdf.annot.PdfStampAnnotation
            java.lang.String r3 = r9.getAttributeValue(r7)
            com.itextpdf.kernel.geom.Rectangle r3 = com.itextpdf.forms.xfdf.XfdfObjectUtils.convertRectFromString(r3)
            r2.<init>((com.itextpdf.kernel.geom.Rectangle) r3)
            r1.addAnnotation(r2)
            goto L_0x02e4
        L_0x00f2:
            java.lang.String r1 = r9.getAttributeValue(r7)
            com.itextpdf.kernel.geom.Rectangle r1 = com.itextpdf.forms.xfdf.XfdfObjectUtils.convertRectFromString(r1)
            java.lang.String r2 = r9.getVertices()
            float[] r2 = com.itextpdf.forms.xfdf.XfdfObjectUtils.convertVerticesFromString(r2)
            com.itextpdf.kernel.pdf.annot.PdfPolyGeomAnnotation r3 = com.itextpdf.kernel.pdf.annot.PdfPolyGeomAnnotation.createPolyLine(r1, r2)
            r8.addCommonAnnotationAttributes(r3, r9)
            r8.addMarkupAnnotationAttributes(r3, r9)
            com.itextpdf.forms.xfdf.AttributeObject r4 = r9.getAttribute(r6)
            java.lang.String r4 = r4.getValue()
            int r4 = java.lang.Integer.parseInt(r4)
            com.itextpdf.kernel.pdf.PdfPage r4 = r10.getPage((int) r4)
            r4.addAnnotation(r3)
            goto L_0x02e4
        L_0x0121:
            java.lang.String r1 = r9.getAttributeValue(r7)
            com.itextpdf.kernel.geom.Rectangle r1 = com.itextpdf.forms.xfdf.XfdfObjectUtils.convertRectFromString(r1)
            java.lang.String r2 = r9.getVertices()
            float[] r2 = com.itextpdf.forms.xfdf.XfdfObjectUtils.convertVerticesFromString(r2)
            com.itextpdf.kernel.pdf.annot.PdfPolyGeomAnnotation r3 = com.itextpdf.kernel.pdf.annot.PdfPolyGeomAnnotation.createPolygon(r1, r2)
            r8.addCommonAnnotationAttributes(r3, r9)
            r8.addMarkupAnnotationAttributes(r3, r9)
            com.itextpdf.forms.xfdf.AttributeObject r4 = r9.getAttribute(r6)
            java.lang.String r4 = r4.getValue()
            int r4 = java.lang.Integer.parseInt(r4)
            com.itextpdf.kernel.pdf.PdfPage r4 = r10.getPage((int) r4)
            r4.addAnnotation(r3)
            goto L_0x02e4
        L_0x0150:
            com.itextpdf.kernel.pdf.annot.PdfSquareAnnotation r1 = new com.itextpdf.kernel.pdf.annot.PdfSquareAnnotation
            java.lang.String r2 = r9.getAttributeValue(r7)
            com.itextpdf.kernel.geom.Rectangle r2 = com.itextpdf.forms.xfdf.XfdfObjectUtils.convertRectFromString(r2)
            r1.<init>((com.itextpdf.kernel.geom.Rectangle) r2)
            r8.addCommonAnnotationAttributes(r1, r9)
            r8.addMarkupAnnotationAttributes(r1, r9)
            java.lang.String r2 = r9.getAttributeValue(r4)
            if (r2 == 0) goto L_0x0174
            java.lang.String r2 = r9.getAttributeValue(r4)
            com.itextpdf.kernel.pdf.PdfArray r2 = com.itextpdf.forms.xfdf.XfdfObjectUtils.convertFringeFromString(r2)
            r1.setRectangleDifferences(r2)
        L_0x0174:
            com.itextpdf.forms.xfdf.AttributeObject r2 = r9.getAttribute(r6)
            java.lang.String r2 = r2.getValue()
            int r2 = java.lang.Integer.parseInt(r2)
            com.itextpdf.kernel.pdf.PdfPage r2 = r10.getPage((int) r2)
            r2.addAnnotation(r1)
            goto L_0x02e4
        L_0x0189:
            com.itextpdf.kernel.pdf.annot.PdfCircleAnnotation r1 = new com.itextpdf.kernel.pdf.annot.PdfCircleAnnotation
            java.lang.String r2 = r9.getAttributeValue(r7)
            com.itextpdf.kernel.geom.Rectangle r2 = com.itextpdf.forms.xfdf.XfdfObjectUtils.convertRectFromString(r2)
            r1.<init>((com.itextpdf.kernel.geom.Rectangle) r2)
            r8.addCommonAnnotationAttributes(r1, r9)
            r8.addMarkupAnnotationAttributes(r1, r9)
            java.lang.String r2 = r9.getAttributeValue(r4)
            if (r2 == 0) goto L_0x01ad
            java.lang.String r2 = r9.getAttributeValue(r4)
            com.itextpdf.kernel.pdf.PdfArray r2 = com.itextpdf.forms.xfdf.XfdfObjectUtils.convertFringeFromString(r2)
            r1.setRectangleDifferences(r2)
        L_0x01ad:
            com.itextpdf.forms.xfdf.AttributeObject r2 = r9.getAttribute(r6)
            java.lang.String r2 = r2.getValue()
            int r2 = java.lang.Integer.parseInt(r2)
            com.itextpdf.kernel.pdf.PdfPage r2 = r10.getPage((int) r2)
            r2.addAnnotation(r1)
            goto L_0x02e4
        L_0x01c2:
            com.itextpdf.kernel.pdf.annot.PdfTextMarkupAnnotation r1 = new com.itextpdf.kernel.pdf.annot.PdfTextMarkupAnnotation
            java.lang.String r2 = r9.getAttributeValue(r7)
            com.itextpdf.kernel.geom.Rectangle r2 = com.itextpdf.forms.xfdf.XfdfObjectUtils.convertRectFromString(r2)
            com.itextpdf.kernel.pdf.PdfName r3 = com.itextpdf.kernel.pdf.PdfName.Squiggly
            java.lang.String r4 = r9.getAttributeValue(r5)
            float[] r4 = com.itextpdf.forms.xfdf.XfdfObjectUtils.convertQuadPointsFromCoordsString(r4)
            r1.<init>(r2, r3, r4)
            r8.addCommonAnnotationAttributes(r1, r9)
            r8.addMarkupAnnotationAttributes(r1, r9)
            com.itextpdf.forms.xfdf.AttributeObject r2 = r9.getAttribute(r6)
            java.lang.String r2 = r2.getValue()
            int r2 = java.lang.Integer.parseInt(r2)
            com.itextpdf.kernel.pdf.PdfPage r2 = r10.getPage((int) r2)
            r2.addAnnotation(r1)
            goto L_0x02e4
        L_0x01f4:
            com.itextpdf.kernel.pdf.annot.PdfTextMarkupAnnotation r1 = new com.itextpdf.kernel.pdf.annot.PdfTextMarkupAnnotation
            java.lang.String r2 = r9.getAttributeValue(r7)
            com.itextpdf.kernel.geom.Rectangle r2 = com.itextpdf.forms.xfdf.XfdfObjectUtils.convertRectFromString(r2)
            com.itextpdf.kernel.pdf.PdfName r3 = com.itextpdf.kernel.pdf.PdfName.StrikeOut
            java.lang.String r4 = r9.getAttributeValue(r5)
            float[] r4 = com.itextpdf.forms.xfdf.XfdfObjectUtils.convertQuadPointsFromCoordsString(r4)
            r1.<init>(r2, r3, r4)
            r8.addCommonAnnotationAttributes(r1, r9)
            r8.addMarkupAnnotationAttributes(r1, r9)
            com.itextpdf.forms.xfdf.AttributeObject r2 = r9.getAttribute(r6)
            java.lang.String r2 = r2.getValue()
            int r2 = java.lang.Integer.parseInt(r2)
            com.itextpdf.kernel.pdf.PdfPage r2 = r10.getPage((int) r2)
            r2.addAnnotation(r1)
            goto L_0x02e4
        L_0x0226:
            com.itextpdf.kernel.pdf.annot.PdfTextMarkupAnnotation r1 = new com.itextpdf.kernel.pdf.annot.PdfTextMarkupAnnotation
            java.lang.String r2 = r9.getAttributeValue(r7)
            com.itextpdf.kernel.geom.Rectangle r2 = com.itextpdf.forms.xfdf.XfdfObjectUtils.convertRectFromString(r2)
            com.itextpdf.kernel.pdf.PdfName r3 = com.itextpdf.kernel.pdf.PdfName.Underline
            java.lang.String r4 = r9.getAttributeValue(r5)
            float[] r4 = com.itextpdf.forms.xfdf.XfdfObjectUtils.convertQuadPointsFromCoordsString(r4)
            r1.<init>(r2, r3, r4)
            r8.addCommonAnnotationAttributes(r1, r9)
            r8.addMarkupAnnotationAttributes(r1, r9)
            com.itextpdf.forms.xfdf.AttributeObject r2 = r9.getAttribute(r6)
            java.lang.String r2 = r2.getValue()
            int r2 = java.lang.Integer.parseInt(r2)
            com.itextpdf.kernel.pdf.PdfPage r2 = r10.getPage((int) r2)
            r2.addAnnotation(r1)
            goto L_0x02e4
        L_0x0258:
            com.itextpdf.kernel.pdf.annot.PdfTextMarkupAnnotation r1 = new com.itextpdf.kernel.pdf.annot.PdfTextMarkupAnnotation
            java.lang.String r2 = r9.getAttributeValue(r7)
            com.itextpdf.kernel.geom.Rectangle r2 = com.itextpdf.forms.xfdf.XfdfObjectUtils.convertRectFromString(r2)
            com.itextpdf.kernel.pdf.PdfName r3 = com.itextpdf.kernel.pdf.PdfName.Highlight
            java.lang.String r4 = r9.getAttributeValue(r5)
            float[] r4 = com.itextpdf.forms.xfdf.XfdfObjectUtils.convertQuadPointsFromCoordsString(r4)
            r1.<init>(r2, r3, r4)
            r8.addCommonAnnotationAttributes(r1, r9)
            r8.addMarkupAnnotationAttributes(r1, r9)
            com.itextpdf.forms.xfdf.AttributeObject r2 = r9.getAttribute(r6)
            java.lang.String r2 = r2.getValue()
            int r2 = java.lang.Integer.parseInt(r2)
            com.itextpdf.kernel.pdf.PdfPage r2 = r10.getPage((int) r2)
            r2.addAnnotation(r1)
            goto L_0x02e4
        L_0x0289:
            com.itextpdf.kernel.pdf.annot.PdfTextAnnotation r1 = new com.itextpdf.kernel.pdf.annot.PdfTextAnnotation
            java.lang.String r2 = r9.getAttributeValue(r7)
            com.itextpdf.kernel.geom.Rectangle r2 = com.itextpdf.forms.xfdf.XfdfObjectUtils.convertRectFromString(r2)
            r1.<init>((com.itextpdf.kernel.geom.Rectangle) r2)
            r8.addCommonAnnotationAttributes(r1, r9)
            r8.addMarkupAnnotationAttributes(r1, r9)
            com.itextpdf.kernel.pdf.PdfName r2 = new com.itextpdf.kernel.pdf.PdfName
            java.lang.String r3 = "icon"
            java.lang.String r3 = r9.getAttributeValue(r3)
            r2.<init>((java.lang.String) r3)
            r1.setIconName(r2)
            java.lang.String r2 = "state"
            java.lang.String r3 = r9.getAttributeValue(r2)
            if (r3 == 0) goto L_0x02bf
            com.itextpdf.kernel.pdf.PdfString r3 = new com.itextpdf.kernel.pdf.PdfString
            java.lang.String r2 = r9.getAttributeValue(r2)
            r3.<init>((java.lang.String) r2)
            r1.setState(r3)
        L_0x02bf:
            java.lang.String r2 = "statemodel"
            java.lang.String r3 = r9.getAttributeValue(r2)
            if (r3 == 0) goto L_0x02d4
            com.itextpdf.kernel.pdf.PdfString r3 = new com.itextpdf.kernel.pdf.PdfString
            java.lang.String r2 = r9.getAttributeValue(r2)
            r3.<init>((java.lang.String) r2)
            r1.setStateModel(r3)
        L_0x02d4:
            java.lang.String r2 = r9.getAttributeValue(r6)
            int r2 = java.lang.Integer.parseInt(r2)
            com.itextpdf.kernel.pdf.PdfPage r2 = r10.getPage((int) r2)
            r2.addAnnotation(r1)
        L_0x02e4:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.forms.xfdf.XfdfReader.addAnnotationToPdf(com.itextpdf.forms.xfdf.AnnotObject, com.itextpdf.kernel.pdf.PdfDocument):void");
    }
}
