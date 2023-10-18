package com.itextpdf.forms.xfdf;

import com.itextpdf.forms.PdfAcroForm;
import com.itextpdf.kernel.pdf.PdfArray;
import com.itextpdf.kernel.pdf.PdfDictionary;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfIndirectReference;
import com.itextpdf.kernel.pdf.PdfName;
import com.itextpdf.kernel.pdf.PdfNumber;
import com.itextpdf.kernel.pdf.PdfObject;
import com.itextpdf.kernel.pdf.PdfString;
import com.itextpdf.kernel.pdf.annot.PdfAnnotation;
import com.itextpdf.kernel.pdf.annot.PdfCircleAnnotation;
import com.itextpdf.kernel.pdf.annot.PdfFreeTextAnnotation;
import com.itextpdf.kernel.pdf.annot.PdfLineAnnotation;
import com.itextpdf.kernel.pdf.annot.PdfLinkAnnotation;
import com.itextpdf.kernel.pdf.annot.PdfMarkupAnnotation;
import com.itextpdf.kernel.pdf.annot.PdfPolyGeomAnnotation;
import com.itextpdf.kernel.pdf.annot.PdfPopupAnnotation;
import com.itextpdf.kernel.pdf.annot.PdfSquareAnnotation;
import com.itextpdf.kernel.pdf.annot.PdfStampAnnotation;
import com.itextpdf.kernel.pdf.annot.PdfTextAnnotation;
import com.itextpdf.kernel.pdf.annot.PdfTextMarkupAnnotation;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import javax.xml.parsers.ParserConfigurationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XfdfObjectFactory {
    private static Logger logger = LoggerFactory.getLogger((Class<?>) XfdfObjectFactory.class);

    public XfdfObject createXfdfObject(PdfDocument document, String filename) {
        PdfDocument pdfDocument = document;
        PdfAcroForm form = PdfAcroForm.getAcroForm(pdfDocument, false);
        XfdfObject resultXfdf = new XfdfObject();
        FieldsObject xfdfFields = new FieldsObject();
        if (!(form == null || form.getFormFields() == null || form.getFormFields().isEmpty())) {
            for (String fieldName : form.getFormFields().keySet()) {
                StringTokenizer st = new StringTokenizer(fieldName, ".");
                List<String> nameParts = new ArrayList<>();
                while (st.hasMoreTokens()) {
                    nameParts.add(st.nextToken());
                }
                FieldObject childField = new FieldObject(nameParts.get(nameParts.size() - 1), form.getField(fieldName).getValueAsString(), false);
                if (nameParts.size() > 1) {
                    FieldObject parentField = new FieldObject();
                    parentField.setName(nameParts.get(nameParts.size() - 2));
                    childField.setParent(parentField);
                }
                xfdfFields.addField(childField);
            }
        }
        resultXfdf.setFields(xfdfFields);
        String original = XfdfObjectUtils.convertIdToHexString(document.getOriginalDocumentId().getValue());
        resultXfdf.setIds(new IdsObject().setOriginal(original).setModified(XfdfObjectUtils.convertIdToHexString(document.getModifiedDocumentId().getValue())));
        resultXfdf.setF(new FObject(filename));
        addAnnotations(pdfDocument, resultXfdf);
        return resultXfdf;
    }

    public XfdfObject createXfdfObject(InputStream xfdfInputStream) throws ParserConfigurationException, IOException, SAXException {
        XfdfObject xfdfObject = new XfdfObject();
        Element root = XfdfFileUtils.createXfdfDocumentFromStream(xfdfInputStream).getDocumentElement();
        xfdfObject.setAttributes(readXfdfRootAttributes(root));
        visitChildNodes(root.getChildNodes(), xfdfObject);
        return xfdfObject;
    }

    private void visitFNode(Node node, XfdfObject xfdfObject) {
        if (node.getAttributes() != null) {
            Node href = node.getAttributes().getNamedItem("href");
            if (href != null) {
                xfdfObject.setF(new FObject(href.getNodeValue()));
            } else {
                logger.info(XfdfConstants.EMPTY_F_LEMENT);
            }
        }
    }

    private void visitIdsNode(Node node, XfdfObject xfdfObject) {
        IdsObject idsObject = new IdsObject();
        if (node.getAttributes() != null) {
            Node original = node.getAttributes().getNamedItem(XfdfConstants.ORIGINAL);
            if (original != null) {
                idsObject.setOriginal(original.getNodeValue());
            }
            Node modified = node.getAttributes().getNamedItem(XfdfConstants.MODIFIED);
            if (modified != null) {
                idsObject.setModified(modified.getNodeValue());
            }
            xfdfObject.setIds(idsObject);
            return;
        }
        logger.info(XfdfConstants.EMPTY_IDS_ELEMENT);
    }

    private void visitElementNode(Node node, XfdfObject xfdfObject) {
        if (XfdfConstants.FIELDS.equalsIgnoreCase(node.getNodeName())) {
            FieldsObject fieldsObject = new FieldsObject();
            readFieldList(node, fieldsObject);
            xfdfObject.setFields(fieldsObject);
        }
        if (XfdfConstants.f1185F.equalsIgnoreCase(node.getNodeName())) {
            visitFNode(node, xfdfObject);
        }
        if (XfdfConstants.IDS.equalsIgnoreCase(node.getNodeName())) {
            visitIdsNode(node, xfdfObject);
        }
        if (XfdfConstants.ANNOTS.equalsIgnoreCase(node.getNodeName())) {
            AnnotsObject annotsObject = new AnnotsObject();
            readAnnotsList(node, annotsObject);
            xfdfObject.setAnnots(annotsObject);
        }
    }

    private void visitChildNodes(NodeList nList, XfdfObject xfdfObject) {
        for (int temp = 0; temp < nList.getLength(); temp++) {
            Node node = nList.item(temp);
            if (node.getNodeType() == 1) {
                visitElementNode(node, xfdfObject);
            }
        }
    }

    private static boolean isAnnotSupported(String nodeName) {
        return "text".equalsIgnoreCase(nodeName) || XfdfConstants.HIGHLIGHT.equalsIgnoreCase(nodeName) || "underline".equalsIgnoreCase(nodeName) || XfdfConstants.STRIKEOUT.equalsIgnoreCase(nodeName) || XfdfConstants.SQUIGGLY.equalsIgnoreCase(nodeName) || "circle".equalsIgnoreCase(nodeName) || "square".equalsIgnoreCase(nodeName) || "polyline".equalsIgnoreCase(nodeName) || "polygon".equalsIgnoreCase(nodeName) || "line".equalsIgnoreCase(nodeName);
    }

    private void readAnnotsList(Node node, AnnotsObject annotsObject) {
        NodeList annotsNodeList = node.getChildNodes();
        for (int temp = 0; temp < annotsNodeList.getLength(); temp++) {
            Node currentNode = annotsNodeList.item(temp);
            if (currentNode.getNodeType() == 1 && isAnnotationSubtype(currentNode.getNodeName()) && isAnnotSupported(currentNode.getNodeName())) {
                visitAnnotationNode(currentNode, annotsObject);
            }
        }
    }

    private void visitAnnotationNode(Node currentNode, AnnotsObject annotsObject) {
        AnnotObject annotObject = new AnnotObject();
        annotObject.setName(currentNode.getNodeName());
        if (currentNode.getAttributes() != null) {
            NamedNodeMap attributes = currentNode.getAttributes();
            for (int i = 0; i < attributes.getLength(); i++) {
                addAnnotObjectAttribute(annotObject, attributes.item(i));
            }
            visitAnnotationInnerNodes(annotObject, currentNode);
            annotsObject.addAnnot(annotObject);
        }
    }

    private void visitAnnotationInnerNodes(AnnotObject annotObject, Node annotNode) {
        NodeList children = annotNode.getChildNodes();
        for (int temp = 0; temp < children.getLength(); temp++) {
            Node node = children.item(temp);
            if (node.getNodeType() == 1) {
                if (XfdfConstants.CONTENTS.equalsIgnoreCase(node.getNodeName())) {
                    visitContentsSubelement(node, annotObject);
                }
                if (XfdfConstants.CONTENTS_RICHTEXT.equalsIgnoreCase(node.getNodeName())) {
                    visitContentsRichTextSubelement(node, annotObject);
                }
                if (XfdfConstants.POPUP.equalsIgnoreCase(node.getNodeName())) {
                    visitPopupSubelement(node, annotObject);
                }
                if (XfdfConstants.VERTICES.equalsIgnoreCase(node.getNodeName())) {
                    visitVerticesSubelement(node, annotObject);
                }
            }
        }
    }

    private void visitPopupSubelement(Node popupNode, AnnotObject annotObject) {
        AnnotObject popupAnnotObject = new AnnotObject();
        NamedNodeMap attributes = popupNode.getAttributes();
        for (int i = 0; i < attributes.getLength(); i++) {
            addAnnotObjectAttribute(popupAnnotObject, attributes.item(i));
        }
        annotObject.setPopup(popupAnnotObject);
    }

    private void visitContentsSubelement(Node parentNode, AnnotObject annotObject) {
        NodeList children = parentNode.getChildNodes();
        for (int temp = 0; temp < children.getLength(); temp++) {
            Node node = children.item(temp);
            if (node.getNodeType() == 3) {
                annotObject.setContents(new PdfString(node.getNodeValue()));
            }
        }
    }

    private void visitContentsRichTextSubelement(Node parentNode, AnnotObject annotObject) {
        NodeList children = parentNode.getChildNodes();
        for (int temp = 0; temp < children.getLength(); temp++) {
            Node node = children.item(temp);
            if (node.getNodeType() == 3) {
                annotObject.setContentsRichText(new PdfString(node.getNodeValue()));
            }
        }
    }

    private void visitVerticesSubelement(Node parentNode, AnnotObject annotObject) {
        NodeList children = parentNode.getChildNodes();
        for (int temp = 0; temp < children.getLength(); temp++) {
            Node node = children.item(temp);
            if (node.getNodeType() == 3) {
                annotObject.setVertices(node.getNodeValue());
            }
        }
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void addAnnotObjectAttribute(com.itextpdf.forms.xfdf.AnnotObject r4, org.w3c.dom.Node r5) {
        /*
            r3 = this;
            if (r5 == 0) goto L_0x0105
            java.lang.String r0 = r5.getNodeName()
            int r1 = r0.hashCode()
            switch(r1) {
                case -1867885268: goto L_0x00d4;
                case -1354750946: goto L_0x00c9;
                case -1274028512: goto L_0x00be;
                case -1267206133: goto L_0x00b2;
                case -1266275441: goto L_0x00a7;
                case -433680060: goto L_0x009b;
                case -214800072: goto L_0x008f;
                case 3076014: goto L_0x0085;
                case 3226745: goto L_0x007a;
                case 3373707: goto L_0x006e;
                case 3417674: goto L_0x0061;
                case 3433103: goto L_0x0055;
                case 3496420: goto L_0x0049;
                case 94842723: goto L_0x003e;
                case 97513095: goto L_0x0033;
                case 109757585: goto L_0x0026;
                case 110371416: goto L_0x001a;
                case 1586485005: goto L_0x000f;
                default: goto L_0x000d;
            }
        L_0x000d:
            goto L_0x00e0
        L_0x000f:
            java.lang.String r1 = "creationdate"
            boolean r1 = r0.equals(r1)
            if (r1 == 0) goto L_0x000d
            r1 = 7
            goto L_0x00e1
        L_0x001a:
            java.lang.String r1 = "title"
            boolean r1 = r0.equals(r1)
            if (r1 == 0) goto L_0x000d
            r1 = 6
            goto L_0x00e1
        L_0x0026:
            java.lang.String r1 = "state"
            boolean r1 = r0.equals(r1)
            if (r1 == 0) goto L_0x000d
            r1 = 11
            goto L_0x00e1
        L_0x0033:
            java.lang.String r1 = "flags"
            boolean r1 = r0.equals(r1)
            if (r1 == 0) goto L_0x000d
            r1 = 3
            goto L_0x00e1
        L_0x003e:
            java.lang.String r1 = "color"
            boolean r1 = r0.equals(r1)
            if (r1 == 0) goto L_0x000d
            r1 = 1
            goto L_0x00e1
        L_0x0049:
            java.lang.String r1 = "rect"
            boolean r1 = r0.equals(r1)
            if (r1 == 0) goto L_0x000d
            r1 = 5
            goto L_0x00e1
        L_0x0055:
            java.lang.String r1 = "page"
            boolean r1 = r0.equals(r1)
            if (r1 == 0) goto L_0x000d
            r1 = 0
            goto L_0x00e1
        L_0x0061:
            java.lang.String r1 = "open"
            boolean r1 = r0.equals(r1)
            if (r1 == 0) goto L_0x000d
            r1 = 15
            goto L_0x00e1
        L_0x006e:
            java.lang.String r1 = "name"
            boolean r1 = r0.equals(r1)
            if (r1 == 0) goto L_0x000d
            r1 = 4
            goto L_0x00e1
        L_0x007a:
            java.lang.String r1 = "icon"
            boolean r1 = r0.equals(r1)
            if (r1 == 0) goto L_0x000d
            r1 = 10
            goto L_0x00e1
        L_0x0085:
            java.lang.String r1 = "date"
            boolean r1 = r0.equals(r1)
            if (r1 == 0) goto L_0x000d
            r1 = 2
            goto L_0x00e1
        L_0x008f:
            java.lang.String r1 = "statemodel"
            boolean r1 = r0.equals(r1)
            if (r1 == 0) goto L_0x000d
            r1 = 12
            goto L_0x00e1
        L_0x009b:
            java.lang.String r1 = "replyType"
            boolean r1 = r0.equals(r1)
            if (r1 == 0) goto L_0x000d
            r1 = 14
            goto L_0x00e1
        L_0x00a7:
            java.lang.String r1 = "fringe"
            boolean r1 = r0.equals(r1)
            if (r1 == 0) goto L_0x000d
            r1 = 17
            goto L_0x00e1
        L_0x00b2:
            java.lang.String r1 = "opacity"
            boolean r1 = r0.equals(r1)
            if (r1 == 0) goto L_0x000d
            r1 = 8
            goto L_0x00e1
        L_0x00be:
            java.lang.String r1 = "inreplyto"
            boolean r1 = r0.equals(r1)
            if (r1 == 0) goto L_0x000d
            r1 = 13
            goto L_0x00e1
        L_0x00c9:
            java.lang.String r1 = "coords"
            boolean r1 = r0.equals(r1)
            if (r1 == 0) goto L_0x000d
            r1 = 16
            goto L_0x00e1
        L_0x00d4:
            java.lang.String r1 = "subject"
            boolean r1 = r0.equals(r1)
            if (r1 == 0) goto L_0x000d
            r1 = 9
            goto L_0x00e1
        L_0x00e0:
            r1 = -1
        L_0x00e1:
            switch(r1) {
                case 0: goto L_0x00f9;
                case 1: goto L_0x00ec;
                case 2: goto L_0x00ec;
                case 3: goto L_0x00ec;
                case 4: goto L_0x00ec;
                case 5: goto L_0x00ec;
                case 6: goto L_0x00ec;
                case 7: goto L_0x00ec;
                case 8: goto L_0x00ec;
                case 9: goto L_0x00ec;
                case 10: goto L_0x00ec;
                case 11: goto L_0x00ec;
                case 12: goto L_0x00ec;
                case 13: goto L_0x00ec;
                case 14: goto L_0x00ec;
                case 15: goto L_0x00ec;
                case 16: goto L_0x00ec;
                case 17: goto L_0x00ec;
                default: goto L_0x00e4;
            }
        L_0x00e4:
            org.slf4j.Logger r1 = logger
            java.lang.String r2 = "Xfdf unsupported attribute type"
            r1.warn(r2)
            goto L_0x0105
        L_0x00ec:
            com.itextpdf.forms.xfdf.AttributeObject r1 = new com.itextpdf.forms.xfdf.AttributeObject
            java.lang.String r2 = r5.getNodeValue()
            r1.<init>(r0, r2)
            r4.addAttribute(r1)
            goto L_0x0105
        L_0x00f9:
            java.lang.String r1 = r5.getNodeValue()
            int r1 = java.lang.Integer.parseInt(r1)
            r4.addFdfAttributes(r1)
        L_0x0105:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.forms.xfdf.XfdfObjectFactory.addAnnotObjectAttribute(com.itextpdf.forms.xfdf.AnnotObject, org.w3c.dom.Node):void");
    }

    private boolean isAnnotationSubtype(String tag) {
        return "text".equalsIgnoreCase(tag) || XfdfConstants.HIGHLIGHT.equalsIgnoreCase(tag) || "underline".equalsIgnoreCase(tag) || XfdfConstants.STRIKEOUT.equalsIgnoreCase(tag) || XfdfConstants.SQUIGGLY.equalsIgnoreCase(tag) || "line".equalsIgnoreCase(tag) || "circle".equalsIgnoreCase(tag) || "square".equalsIgnoreCase(tag) || XfdfConstants.CARET.equalsIgnoreCase(tag) || "polygon".equalsIgnoreCase(tag) || "polyline".equalsIgnoreCase(tag) || XfdfConstants.STAMP.equalsIgnoreCase(tag) || XfdfConstants.INK.equalsIgnoreCase(tag) || XfdfConstants.FREETEXT.equalsIgnoreCase(tag) || XfdfConstants.FILEATTACHMENT.equalsIgnoreCase(tag) || XfdfConstants.SOUND.equalsIgnoreCase(tag) || "link".equalsIgnoreCase(tag) || XfdfConstants.REDACT.equalsIgnoreCase(tag) || XfdfConstants.PROJECTION.equalsIgnoreCase(tag);
    }

    private void readFieldList(Node node, FieldsObject fieldsObject) {
        NodeList fieldNodeList = node.getChildNodes();
        for (int temp = 0; temp < fieldNodeList.getLength(); temp++) {
            Node currentNode = fieldNodeList.item(temp);
            if (currentNode.getNodeType() == 1 && XfdfConstants.FIELD.equalsIgnoreCase(currentNode.getNodeName())) {
                visitInnerFields(new FieldObject(), currentNode, fieldsObject);
            }
        }
    }

    private void visitFieldElementNode(Node node, FieldObject parentField, FieldsObject fieldsObject) {
        if (XfdfConstants.VALUE.equalsIgnoreCase(node.getNodeName())) {
            Node valueTextNode = node.getFirstChild();
            if (valueTextNode != null) {
                parentField.setValue(valueTextNode.getTextContent());
            } else {
                logger.info(XfdfConstants.EMPTY_FIELD_VALUE_ELEMENT);
            }
        } else if (XfdfConstants.FIELD.equalsIgnoreCase(node.getNodeName())) {
            FieldObject childField = new FieldObject();
            childField.setParent(parentField);
            childField.setName(parentField.getName() + "." + node.getAttributes().item(0).getNodeValue());
            if (node.getChildNodes() != null) {
                visitInnerFields(childField, node, fieldsObject);
            }
            fieldsObject.addField(childField);
        }
    }

    private void visitInnerFields(FieldObject parentField, Node parentNode, FieldsObject fieldsObject) {
        if (parentNode.getAttributes().getLength() == 0) {
            logger.info(XfdfConstants.EMPTY_FIELD_NAME_ELEMENT);
        } else if (parentField.getName() == null) {
            parentField.setName(parentNode.getAttributes().item(0).getNodeValue());
        }
        NodeList children = parentNode.getChildNodes();
        for (int temp = 0; temp < children.getLength(); temp++) {
            Node node = children.item(temp);
            if (node.getNodeType() == 1) {
                visitFieldElementNode(node, parentField, fieldsObject);
            }
        }
        fieldsObject.addField(parentField);
    }

    private List<AttributeObject> readXfdfRootAttributes(Element root) {
        NamedNodeMap attributes = root.getAttributes();
        int length = attributes.getLength();
        List<AttributeObject> attributeObjects = new ArrayList<>();
        for (int i = 0; i < length; i++) {
            Node attributeNode = attributes.item(i);
            attributeObjects.add(new AttributeObject(attributeNode.getNodeName(), attributeNode.getNodeValue()));
        }
        return attributeObjects;
    }

    private static void addPopup(PdfAnnotation pdfAnnot, AnnotsObject annots, int pageNumber) {
        if (((PdfPopupAnnotation) pdfAnnot).getParentObject() != null) {
            PdfIndirectReference parentRef = ((PdfDictionary) ((PdfPopupAnnotation) pdfAnnot).getParent().getPdfObject()).getIndirectReference();
            boolean hasParentAnnot = false;
            for (AnnotObject annot : annots.getAnnotsList()) {
                if (parentRef.equals(annot.getRef())) {
                    hasParentAnnot = true;
                    annot.setHasPopup(true);
                    annot.setPopup(createXfdfAnnotation(pdfAnnot, pageNumber));
                }
            }
            if (!hasParentAnnot) {
                AnnotObject parentAnnot = new AnnotObject();
                parentAnnot.setRef(parentRef);
                parentAnnot.addFdfAttributes(pageNumber);
                parentAnnot.setHasPopup(true);
                parentAnnot.setPopup(createXfdfAnnotation(pdfAnnot, pageNumber));
                annots.addAnnot(parentAnnot);
                return;
            }
            return;
        }
        annots.addAnnot(createXfdfAnnotation(pdfAnnot, pageNumber));
    }

    private static void addAnnotation(PdfAnnotation pdfAnnot, AnnotsObject annots, int pageNumber) {
        boolean hasCorrecpondingAnnotObject = false;
        for (AnnotObject annot : annots.getAnnotsList()) {
            if (((PdfDictionary) pdfAnnot.getPdfObject()).getIndirectReference().equals(annot.getRef())) {
                hasCorrecpondingAnnotObject = true;
                updateXfdfAnnotation(annot, pdfAnnot, pageNumber);
            }
        }
        if (!hasCorrecpondingAnnotObject) {
            annots.addAnnot(createXfdfAnnotation(pdfAnnot, pageNumber));
        }
    }

    private static void addAnnotations(PdfDocument pdfDoc, XfdfObject resultXfdf) {
        AnnotsObject annots = new AnnotsObject();
        int pageNumber = pdfDoc.getNumberOfPages();
        for (int i = 1; i <= pageNumber; i++) {
            for (PdfAnnotation pdfAnnot : pdfDoc.getPage(i).getAnnotations()) {
                if (pdfAnnot.getSubtype() == PdfName.Popup) {
                    addPopup(pdfAnnot, annots, i);
                } else {
                    addAnnotation(pdfAnnot, annots, i);
                }
            }
        }
        resultXfdf.setAnnots(annots);
    }

    private static void updateXfdfAnnotation(AnnotObject annotObject, PdfAnnotation pdfAnnotation, int pageNumber) {
    }

    private static void addCommonAnnotationAttributes(AnnotObject annot, PdfAnnotation pdfAnnotation) {
        annot.setName(pdfAnnotation.getSubtype().getValue().toLowerCase());
        if (pdfAnnotation.getColorObject() != null) {
            annot.addAttribute(new AttributeObject("color", XfdfObjectUtils.convertColorToString(pdfAnnotation.getColorObject().toFloatArray())));
        }
        annot.addAttribute("date", (PdfObject) pdfAnnotation.getDate());
        String flagsString = XfdfObjectUtils.convertFlagsToString(pdfAnnotation);
        if (flagsString != null) {
            annot.addAttribute(new AttributeObject(XfdfConstants.FLAGS, flagsString));
        }
        annot.addAttribute(XfdfConstants.NAME, (PdfObject) pdfAnnotation.getName());
        annot.addAttribute("rect", pdfAnnotation.getRectangle().toRectangle());
        annot.addAttribute("title", (PdfObject) pdfAnnotation.getTitle());
    }

    private static void addMarkupAnnotationAttributes(AnnotObject annot, PdfMarkupAnnotation pdfMarkupAnnotation) {
        annot.addAttribute(XfdfConstants.CREATION_DATE, (PdfObject) pdfMarkupAnnotation.getCreationDate());
        annot.addAttribute("opacity", (PdfObject) pdfMarkupAnnotation.getOpacity());
        annot.addAttribute("subject", (PdfObject) pdfMarkupAnnotation.getSubject());
    }

    private static void addBorderStyleAttributes(AnnotObject annotObject, PdfNumber width, PdfString dashes, PdfString style) {
        annotObject.addAttribute("width", (PdfObject) width);
        annotObject.addAttribute(XfdfConstants.DASHES, (PdfObject) dashes);
        annotObject.addAttribute("style", (PdfObject) style);
    }

    private static void createTextMarkupAnnotation(PdfAnnotation pdfAnnotation, AnnotObject annot, int pageNumber) {
        PdfTextMarkupAnnotation pdfTextMarkupAnnotation = (PdfTextMarkupAnnotation) pdfAnnotation;
        annot.addAttribute(new AttributeObject(XfdfConstants.COORDS, XfdfObjectUtils.convertQuadPointsToCoordsString(pdfTextMarkupAnnotation.getQuadPoints().toFloatArray())));
        if (pdfTextMarkupAnnotation.getContents() != null) {
            annot.setContents(pdfTextMarkupAnnotation.getContents());
        }
        if (pdfTextMarkupAnnotation.getPopup() != null) {
            annot.setPopup(convertPdfPopupToAnnotObject(pdfTextMarkupAnnotation.getPopup(), pageNumber));
        }
    }

    private static void createTextAnnotation(PdfAnnotation pdfAnnotation, AnnotObject annot, int pageNumber) {
        PdfTextAnnotation pdfTextAnnotation = (PdfTextAnnotation) pdfAnnotation;
        annot.addAttribute("icon", (PdfObject) pdfTextAnnotation.getIconName());
        annot.addAttribute(XfdfConstants.STATE, (PdfObject) pdfTextAnnotation.getState());
        annot.addAttribute(XfdfConstants.STATE_MODEL, (PdfObject) pdfTextAnnotation.getStateModel());
        if (pdfTextAnnotation.getReplyType() != null) {
            annot.addAttribute(new AttributeObject(XfdfConstants.IN_REPLY_TO, pdfTextAnnotation.getInReplyTo().getName().getValue()));
            annot.addAttribute(new AttributeObject(XfdfConstants.REPLY_TYPE, pdfTextAnnotation.getReplyType().getValue()));
        }
        if (pdfTextAnnotation.getContents() != null) {
            annot.setContents(pdfTextAnnotation.getContents());
        }
        if (pdfTextAnnotation.getPopup() != null) {
            annot.setPopup(convertPdfPopupToAnnotObject(pdfTextAnnotation.getPopup(), pageNumber));
        }
    }

    private static void createCircleAnnotation(PdfAnnotation pdfAnnotation, AnnotObject annot, int pageNumber) {
        PdfCircleAnnotation pdfCircleAnnotation = (PdfCircleAnnotation) pdfAnnotation;
        PdfDictionary bs = pdfCircleAnnotation.getBorderStyle();
        if (bs != null) {
            addBorderStyleAttributes(annot, bs.getAsNumber(PdfName.f1409W), bs.getAsString(PdfName.Dashed), bs.getAsString(PdfName.Style));
        }
        if (pdfCircleAnnotation.getBorderEffect() != null) {
            annot.addAttribute("style", (PdfObject) pdfCircleAnnotation.getBorderEffect().getAsString(PdfName.Style));
        }
        if (!(pdfCircleAnnotation.getInteriorColor() == null || pdfCircleAnnotation.getInteriorColor().getColorValue() == null)) {
            annot.addAttribute(new AttributeObject(XfdfConstants.INTERIOR_COLOR, XfdfObjectUtils.convertColorToString(pdfCircleAnnotation.getInteriorColor().getColorValue())));
        }
        if (pdfCircleAnnotation.getRectangleDifferences() != null) {
            annot.addAttribute(new AttributeObject(XfdfConstants.FRINGE, XfdfObjectUtils.convertFringeToString(pdfCircleAnnotation.getRectangleDifferences().toFloatArray())));
        }
        annot.setContents(pdfAnnotation.getContents());
        if (pdfCircleAnnotation.getPopup() != null) {
            annot.setPopup(convertPdfPopupToAnnotObject(pdfCircleAnnotation.getPopup(), pageNumber));
        }
    }

    private static void createSquareAnnotation(PdfAnnotation pdfAnnotation, AnnotObject annot, int pageNumber) {
        PdfSquareAnnotation pdfSquareAnnotation = (PdfSquareAnnotation) pdfAnnotation;
        PdfDictionary bs = pdfSquareAnnotation.getBorderStyle();
        if (bs != null) {
            addBorderStyleAttributes(annot, bs.getAsNumber(PdfName.f1409W), bs.getAsString(PdfName.Dashed), bs.getAsString(PdfName.Style));
        }
        if (pdfSquareAnnotation.getBorderEffect() != null) {
            annot.addAttribute("style", (PdfObject) pdfSquareAnnotation.getBorderEffect().getAsString(PdfName.Style));
        }
        if (!(pdfSquareAnnotation.getInteriorColor() == null || pdfSquareAnnotation.getInteriorColor().getColorValue() == null)) {
            annot.addAttribute(new AttributeObject(XfdfConstants.INTERIOR_COLOR, XfdfObjectUtils.convertColorToString(pdfSquareAnnotation.getInteriorColor().getColorValue())));
        }
        if (pdfSquareAnnotation.getRectangleDifferences() != null) {
            annot.addAttribute(new AttributeObject(XfdfConstants.FRINGE, XfdfObjectUtils.convertFringeToString(pdfSquareAnnotation.getRectangleDifferences().toFloatArray())));
        }
        annot.setContents(pdfAnnotation.getContents());
        if (pdfSquareAnnotation.getPopup() != null) {
            annot.setPopup(convertPdfPopupToAnnotObject(pdfSquareAnnotation.getPopup(), pageNumber));
        }
    }

    private static void createStampAnnotation(PdfAnnotation pdfAnnotation, AnnotObject annot, int pageNumber) {
        PdfStampAnnotation pdfStampAnnotation = (PdfStampAnnotation) pdfAnnotation;
        annot.addAttribute("icon", (PdfObject) pdfStampAnnotation.getIconName());
        if (pdfStampAnnotation.getContents() != null) {
            annot.setContents(pdfStampAnnotation.getContents());
        }
        if (pdfStampAnnotation.getPopup() != null) {
            annot.setPopup(convertPdfPopupToAnnotObject(pdfStampAnnotation.getPopup(), pageNumber));
        }
        if (pdfStampAnnotation.getAppearanceDictionary() == null) {
            return;
        }
        if (pdfAnnotation.getAppearanceObject(PdfName.f1357N) != null) {
            annot.setAppearance(pdfStampAnnotation.getAppearanceDictionary().get(PdfName.f1357N).toString());
        } else if (pdfAnnotation.getAppearanceObject(PdfName.f1376R) != null) {
            annot.setAppearance(pdfStampAnnotation.getAppearanceDictionary().get(PdfName.f1376R).toString());
        } else if (pdfAnnotation.getAppearanceObject(PdfName.f1312D) != null) {
            annot.setAppearance(pdfStampAnnotation.getAppearanceDictionary().get(PdfName.f1312D).toString());
        }
    }

    private static void createFreeTextAnnotation(PdfAnnotation pdfAnnotation, AnnotObject annot) {
        PdfFreeTextAnnotation pdfFreeTextAnnotation = (PdfFreeTextAnnotation) pdfAnnotation;
        PdfDictionary bs = pdfFreeTextAnnotation.getBorderStyle();
        if (bs != null) {
            addBorderStyleAttributes(annot, bs.getAsNumber(PdfName.f1409W), bs.getAsString(PdfName.Dashed), bs.getAsString(PdfName.Style));
        }
        annot.addAttribute(new AttributeObject(XfdfConstants.JUSTIFICATION, String.valueOf(pdfFreeTextAnnotation.getJustification())));
        if (pdfFreeTextAnnotation.getIntent() != null) {
            annot.addAttribute(new AttributeObject(XfdfConstants.INTENT, pdfFreeTextAnnotation.getIntent().getValue()));
        }
        if (pdfFreeTextAnnotation.getContents() != null) {
            annot.setContents(pdfFreeTextAnnotation.getContents());
        }
        if (pdfFreeTextAnnotation.getDefaultAppearance() != null) {
            annot.setDefaultAppearance(pdfFreeTextAnnotation.getDefaultAppearance().getValue());
        }
        if (pdfFreeTextAnnotation.getDefaultStyleString() != null) {
            annot.setDefaultStyle(pdfFreeTextAnnotation.getDefaultStyleString().getValue());
        }
    }

    private static void createLineAnnotation(PdfAnnotation pdfAnnotation, AnnotObject annot, int pageNumber) {
        PdfLineAnnotation pdfLineAnnotation = (PdfLineAnnotation) pdfAnnotation;
        PdfArray line = pdfLineAnnotation.getLine();
        if (line != null) {
            annot.addAttribute(new AttributeObject("start", XfdfObjectUtils.convertLineStartToString(line.toFloatArray())));
            annot.addAttribute(new AttributeObject("end", XfdfObjectUtils.convertLineEndToString(line.toFloatArray())));
        }
        if (pdfLineAnnotation.getLineEndingStyles() != null) {
            if (pdfLineAnnotation.getLineEndingStyles().get(0) != null) {
                annot.addAttribute(new AttributeObject(XfdfConstants.HEAD, pdfLineAnnotation.getLineEndingStyles().get(0).toString().substring(1)));
            }
            if (pdfLineAnnotation.getLineEndingStyles().get(1) != null) {
                annot.addAttribute(new AttributeObject(XfdfConstants.TAIL, pdfLineAnnotation.getLineEndingStyles().get(1).toString().substring(1)));
            }
        }
        if (pdfLineAnnotation.getInteriorColor() != null) {
            annot.addAttribute(new AttributeObject(XfdfConstants.INTERIOR_COLOR, XfdfObjectUtils.convertColorToString(pdfLineAnnotation.getInteriorColor())));
        }
        annot.addAttribute(XfdfConstants.LEADER_EXTENDED, pdfLineAnnotation.getLeaderLineExtension());
        annot.addAttribute(XfdfConstants.LEADER_LENGTH, pdfLineAnnotation.getLeaderLineLength());
        annot.addAttribute("caption", pdfLineAnnotation.getContentsAsCaption());
        annot.addAttribute(XfdfConstants.INTENT, (PdfObject) pdfLineAnnotation.getIntent());
        annot.addAttribute(XfdfConstants.LEADER_OFFSET, pdfLineAnnotation.getLeaderLineOffset());
        annot.addAttribute(XfdfConstants.CAPTION_STYLE, (PdfObject) pdfLineAnnotation.getCaptionPosition());
        if (pdfLineAnnotation.getCaptionOffset() != null) {
            annot.addAttribute(XfdfConstants.CAPTION_OFFSET_H, pdfLineAnnotation.getCaptionOffset().get(0));
            annot.addAttribute(XfdfConstants.CAPTION_OFFSET_V, pdfLineAnnotation.getCaptionOffset().get(1));
        } else {
            annot.addAttribute(new AttributeObject(XfdfConstants.CAPTION_OFFSET_H, "0"));
            annot.addAttribute(new AttributeObject(XfdfConstants.CAPTION_OFFSET_V, "0"));
        }
        PdfDictionary bs = pdfLineAnnotation.getBorderStyle();
        if (bs != null) {
            addBorderStyleAttributes(annot, bs.getAsNumber(PdfName.f1409W), bs.getAsString(PdfName.Dashed), bs.getAsString(PdfName.Style));
        }
        annot.setContents(pdfAnnotation.getContents());
        if (pdfLineAnnotation.getPopup() != null) {
            annot.setPopup(convertPdfPopupToAnnotObject(pdfLineAnnotation.getPopup(), pageNumber));
        }
    }

    private static void createLinkAnnotation(PdfAnnotation pdfAnnotation, AnnotObject annot) {
        PdfLinkAnnotation pdfLinkAnnotation = (PdfLinkAnnotation) pdfAnnotation;
        if (pdfLinkAnnotation.getContents() != null) {
            annot.setContents(pdfLinkAnnotation.getContents());
        }
        PdfDictionary action = pdfLinkAnnotation.getAction();
        if (pdfLinkAnnotation.getAction() != null) {
            PdfName type = action.getAsName(PdfName.f1385S);
            ActionObject actionObject = new ActionObject(type);
            if (PdfName.URI.equals(type)) {
                actionObject.setUri(action.getAsString(PdfName.URI));
                if (action.get(PdfName.IsMap) != null) {
                    actionObject.setMap(action.getAsBool(PdfName.IsMap).booleanValue());
                }
            }
            annot.setAction(actionObject);
        }
        PdfArray dest = (PdfArray) pdfLinkAnnotation.getDestinationObject();
        if (dest != null) {
            createDestElement(dest, annot);
        }
        PdfArray border = pdfLinkAnnotation.getBorder();
        if (border != null) {
            annot.setBorderStyleAlt(new BorderStyleAltObject(border.getAsNumber(0).floatValue(), border.getAsNumber(1).floatValue(), border.getAsNumber(2).floatValue()));
        }
    }

    private static void createDestElement(PdfArray dest, AnnotObject annot) {
        DestObject destObject = new DestObject();
        PdfName type = dest.getAsName(1);
        if (PdfName.XYZ.equals(type)) {
            FitObject xyz = new FitObject(dest.get(0));
            xyz.setLeft(dest.getAsNumber(2).floatValue()).setTop(dest.getAsNumber(3).floatValue()).setZoom(dest.getAsNumber(4).floatValue());
            destObject.setXyz(xyz);
        } else if (PdfName.Fit.equals(type)) {
            destObject.setFit(new FitObject(dest.get(0)));
        } else if (PdfName.FitB.equals(type)) {
            destObject.setFitB(new FitObject(dest.get(0)));
        } else if (PdfName.FitR.equals(type)) {
            FitObject fitR = new FitObject(dest.get(0));
            fitR.setLeft(dest.getAsNumber(2).floatValue());
            fitR.setBottom(dest.getAsNumber(3).floatValue());
            fitR.setRight(dest.getAsNumber(4).floatValue());
            fitR.setTop(dest.getAsNumber(5).floatValue());
            destObject.setFitR(fitR);
        } else if (PdfName.FitH.equals(type)) {
            FitObject fitH = new FitObject(dest.get(0));
            fitH.setTop(dest.getAsNumber(2).floatValue());
            destObject.setFitH(fitH);
        } else if (PdfName.FitBH.equals(type)) {
            FitObject fitBH = new FitObject(dest.get(0));
            fitBH.setTop(dest.getAsNumber(2).floatValue());
            destObject.setFitBH(fitBH);
        } else if (PdfName.FitBV.equals(type)) {
            FitObject fitBV = new FitObject(dest.get(0));
            fitBV.setLeft(dest.getAsNumber(2).floatValue());
            destObject.setFitBV(fitBV);
        } else if (PdfName.FitV.equals(type)) {
            FitObject fitV = new FitObject(dest.get(0));
            fitV.setLeft(dest.getAsNumber(2).floatValue());
            destObject.setFitV(fitV);
        }
        annot.setDestination(destObject);
    }

    private static void createPolyGeomAnnotation(PdfAnnotation pdfAnnotation, AnnotObject annot, int pageNumber) {
        PdfPolyGeomAnnotation pdfPolyGeomAnnotation = (PdfPolyGeomAnnotation) pdfAnnotation;
        PdfDictionary bs = pdfPolyGeomAnnotation.getBorderStyle();
        if (bs != null) {
            addBorderStyleAttributes(annot, bs.getAsNumber(PdfName.f1409W), bs.getAsString(PdfName.Dashed), bs.getAsString(PdfName.Style));
        }
        if (pdfPolyGeomAnnotation.getBorderEffect() != null) {
            annot.addAttribute("style", (PdfObject) pdfPolyGeomAnnotation.getBorderEffect().getAsString(PdfName.Style));
        }
        if (pdfPolyGeomAnnotation.getInteriorColor() != null) {
            annot.addAttribute(new AttributeObject(XfdfConstants.INTERIOR_COLOR, XfdfObjectUtils.convertColorToString(pdfPolyGeomAnnotation.getInteriorColor())));
        }
        if (pdfPolyGeomAnnotation.getIntent() != null) {
            annot.addAttribute(new AttributeObject(XfdfConstants.INTENT, pdfPolyGeomAnnotation.getIntent().getValue()));
        }
        if (pdfPolyGeomAnnotation.getLineEndingStyles() != null) {
            if (pdfPolyGeomAnnotation.getLineEndingStyles().get(0) != null) {
                annot.addAttribute(new AttributeObject(XfdfConstants.HEAD, pdfPolyGeomAnnotation.getLineEndingStyles().get(0).toString().substring(1)));
            }
            if (pdfPolyGeomAnnotation.getLineEndingStyles().get(1) != null) {
                annot.addAttribute(new AttributeObject(XfdfConstants.TAIL, pdfPolyGeomAnnotation.getLineEndingStyles().get(1).toString().substring(1)));
            }
        }
        annot.setVertices(XfdfObjectUtils.convertVerticesToString(pdfPolyGeomAnnotation.getVertices().toFloatArray()));
        annot.setContents(pdfAnnotation.getContents());
        if (pdfPolyGeomAnnotation.getPopup() != null) {
            annot.setPopup(convertPdfPopupToAnnotObject(pdfPolyGeomAnnotation.getPopup(), pageNumber));
        }
    }

    private static AnnotObject createXfdfAnnotation(PdfAnnotation pdfAnnotation, int pageNumber) {
        AnnotObject annot = new AnnotObject();
        annot.setRef(((PdfDictionary) pdfAnnotation.getPdfObject()).getIndirectReference());
        annot.addFdfAttributes(pageNumber);
        if (pdfAnnotation instanceof PdfTextMarkupAnnotation) {
            createTextMarkupAnnotation(pdfAnnotation, annot, pageNumber);
        }
        if (pdfAnnotation instanceof PdfTextAnnotation) {
            createTextAnnotation(pdfAnnotation, annot, pageNumber);
        }
        if (pdfAnnotation instanceof PdfPopupAnnotation) {
            annot = convertPdfPopupToAnnotObject((PdfPopupAnnotation) pdfAnnotation, pageNumber);
        }
        if (pdfAnnotation instanceof PdfCircleAnnotation) {
            createCircleAnnotation(pdfAnnotation, annot, pageNumber);
        }
        if (pdfAnnotation instanceof PdfSquareAnnotation) {
            createSquareAnnotation(pdfAnnotation, annot, pageNumber);
        }
        if (pdfAnnotation instanceof PdfStampAnnotation) {
            createStampAnnotation(pdfAnnotation, annot, pageNumber);
        }
        if (pdfAnnotation instanceof PdfFreeTextAnnotation) {
            createFreeTextAnnotation(pdfAnnotation, annot);
        }
        if (pdfAnnotation instanceof PdfLineAnnotation) {
            createLineAnnotation(pdfAnnotation, annot, pageNumber);
        }
        if (pdfAnnotation instanceof PdfPolyGeomAnnotation) {
            createPolyGeomAnnotation(pdfAnnotation, annot, pageNumber);
        }
        if (pdfAnnotation instanceof PdfLinkAnnotation) {
            createLinkAnnotation(pdfAnnotation, annot);
        }
        if (isSupportedAnnotation(pdfAnnotation)) {
            addCommonAnnotationAttributes(annot, pdfAnnotation);
            if (pdfAnnotation instanceof PdfMarkupAnnotation) {
                addMarkupAnnotationAttributes(annot, (PdfMarkupAnnotation) pdfAnnotation);
            }
        }
        return annot;
    }

    private static AnnotObject convertPdfPopupToAnnotObject(PdfPopupAnnotation pdfPopupAnnotation, int pageNumber) {
        AnnotObject annot = new AnnotObject();
        annot.addFdfAttributes(pageNumber);
        annot.setName(XfdfConstants.POPUP);
        annot.setRef(((PdfDictionary) pdfPopupAnnotation.getPdfObject()).getIndirectReference());
        annot.addAttribute(XfdfConstants.OPEN, pdfPopupAnnotation.getOpen());
        return annot;
    }

    private static boolean isSupportedAnnotation(PdfAnnotation pdfAnnotation) {
        return (pdfAnnotation instanceof PdfTextMarkupAnnotation) || (pdfAnnotation instanceof PdfTextAnnotation) || (pdfAnnotation instanceof PdfCircleAnnotation) || (pdfAnnotation instanceof PdfSquareAnnotation) || (pdfAnnotation instanceof PdfStampAnnotation) || (pdfAnnotation instanceof PdfFreeTextAnnotation) || (pdfAnnotation instanceof PdfLineAnnotation) || (pdfAnnotation instanceof PdfPolyGeomAnnotation) || (pdfAnnotation instanceof PdfLinkAnnotation) || (pdfAnnotation instanceof PdfPopupAnnotation);
    }
}
