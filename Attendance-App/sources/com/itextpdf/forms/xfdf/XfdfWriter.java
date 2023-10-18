package com.itextpdf.forms.xfdf;

import com.itextpdf.kernel.pdf.PdfName;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

class XfdfWriter {
    private static Logger logger = LoggerFactory.getLogger((Class<?>) XfdfWriter.class);
    private OutputStream outputStream;

    XfdfWriter(OutputStream outputStream2) {
        this.outputStream = outputStream2;
    }

    /* access modifiers changed from: package-private */
    public void write(XfdfObject xfdfObject) throws TransformerException, ParserConfigurationException {
        writeDom(xfdfObject);
    }

    static void addField(FieldObject fieldObject, Element parentElement, Document document, List<FieldObject> fieldList) {
        List<FieldObject> childrenFields = findChildrenFields(fieldObject, fieldList);
        Element field = document.createElement(XfdfConstants.FIELD);
        field.setAttribute(XfdfConstants.NAME, fieldObject.getName());
        if (!childrenFields.isEmpty()) {
            for (FieldObject childField : childrenFields) {
                addField(childField, field, document, fieldList);
            }
        } else if (fieldObject.getValue() == null || fieldObject.getValue().isEmpty()) {
            logger.info(XfdfConstants.EMPTY_FIELD_VALUE_ELEMENT);
        } else {
            Element value = document.createElement(XfdfConstants.VALUE);
            value.setTextContent(fieldObject.getValue());
            field.appendChild(value);
        }
        parentElement.appendChild(field);
    }

    private void writeDom(XfdfObject xfdfObject) throws ParserConfigurationException, TransformerException {
        Document document = XfdfFileUtils.createNewXfdfDocument();
        Element root = document.createElement("xfdf");
        document.appendChild(root);
        if (!(xfdfObject.getFields() == null || xfdfObject.getFields().getFieldList() == null || xfdfObject.getFields().getFieldList().isEmpty())) {
            Element fields = document.createElement(XfdfConstants.FIELDS);
            root.appendChild(fields);
            List<FieldObject> fieldList = xfdfObject.getFields().getFieldList();
            for (FieldObject fieldObject : fieldList) {
                if (fieldObject.getParent() == null) {
                    addField(fieldObject, fields, document, fieldList);
                }
            }
        }
        if (!(xfdfObject.getAnnots() == null || xfdfObject.getAnnots().getAnnotsList() == null || xfdfObject.getAnnots().getAnnotsList().isEmpty())) {
            Element annots = document.createElement(XfdfConstants.ANNOTS);
            root.appendChild(annots);
            for (AnnotObject annotObject : xfdfObject.getAnnots().getAnnotsList()) {
                addAnnot(annotObject, annots, document);
            }
        }
        if (xfdfObject.getF() != null) {
            Element f = document.createElement(XfdfConstants.f1185F);
            addFAttributes(xfdfObject.getF(), f);
            root.appendChild(f);
        }
        if (xfdfObject.getIds() != null) {
            Element ids = document.createElement(XfdfConstants.IDS);
            addIdsAttributes(xfdfObject.getIds(), ids);
            root.appendChild(ids);
        }
        XfdfFileUtils.saveXfdfDocumentToFile(document, this.outputStream);
    }

    private static void addIdsAttributes(IdsObject idsObject, Element ids) {
        if (idsObject.getOriginal() != null) {
            ids.setAttribute(XfdfConstants.ORIGINAL, idsObject.getOriginal());
        }
        if (idsObject.getModified() != null) {
            ids.setAttribute(XfdfConstants.MODIFIED, idsObject.getModified());
        }
    }

    private static void addFAttributes(FObject fObject, Element f) {
        if (fObject.getHref() != null) {
            f.setAttribute("href", fObject.getHref());
        }
    }

    private static List<FieldObject> findChildrenFields(FieldObject field, List<FieldObject> fieldList) {
        List<FieldObject> childrenFields = new ArrayList<>();
        for (FieldObject currentField : fieldList) {
            if (currentField.getParent() != null && currentField.getParent().getName().equalsIgnoreCase(field.getName())) {
                childrenFields.add(currentField);
            }
        }
        return childrenFields;
    }

    private static void addAnnot(AnnotObject annotObject, Element annots, Document document) {
        if (annotObject.getName() != null) {
            Element annot = document.createElement(annotObject.getName());
            for (AttributeObject attr : annotObject.getAttributes()) {
                annot.setAttribute(attr.getName(), attr.getValue());
            }
            if (annotObject.getPopup() != null) {
                addPopup(annotObject.getPopup(), document.createElement(XfdfConstants.POPUP), annot);
            }
            if (annotObject.getContents() != null) {
                Element contents = document.createElement(XfdfConstants.CONTENTS);
                contents.setTextContent(annotObject.getContents().toString().replace(13, 10));
                annot.appendChild(contents);
            }
            if (annotObject.getAppearance() != null) {
                Element appearance = document.createElement(XfdfConstants.APPEARANCE);
                appearance.setTextContent(annotObject.getAppearance());
                annot.appendChild(appearance);
            }
            if ("link".equalsIgnoreCase(annotObject.getName())) {
                if (annotObject.getDestination() != null) {
                    addDest(annotObject.getDestination(), annot, document);
                } else if (annotObject.getAction() != null) {
                    Element onActivation = document.createElement(XfdfConstants.ON_ACTIVATION);
                    addActionObject(annotObject.getAction(), onActivation, document);
                    annot.appendChild(onActivation);
                } else {
                    logger.error("Dest and OnActivation elements are both missing");
                }
                if (annotObject.getBorderStyleAlt() != null) {
                    addBorderStyleAlt(annotObject.getBorderStyleAlt(), annot, document);
                }
            }
            if (XfdfConstants.FREETEXT.equalsIgnoreCase(annotObject.getName())) {
                String defaultAppearanceString = annotObject.getDefaultAppearance();
                if (defaultAppearanceString != null) {
                    Element defaultAppearance = document.createElement(XfdfConstants.DEFAULT_APPEARANCE);
                    defaultAppearance.setTextContent(defaultAppearanceString);
                    annot.appendChild(defaultAppearance);
                }
                String defaultStyleString = annotObject.getDefaultStyle();
                if (defaultStyleString != null) {
                    Element defaultStyle = document.createElement(XfdfConstants.DEFAULT_STYLE);
                    defaultStyle.setTextContent(defaultStyleString);
                    annot.appendChild(defaultStyle);
                }
            }
            annots.appendChild(annot);
        }
    }

    private static void addBorderStyleAlt(BorderStyleAltObject borderStyleAltObject, Element annot, Document document) {
        Element borderStyleAlt = document.createElement(XfdfConstants.BORDER_STYLE_ALT);
        borderStyleAlt.setAttribute(XfdfConstants.H_CORNER_RADIUS, XfdfObjectUtils.convertFloatToString(borderStyleAltObject.getHCornerRadius()));
        borderStyleAlt.setAttribute(XfdfConstants.V_CORNER_RADIUS, XfdfObjectUtils.convertFloatToString(borderStyleAltObject.getVCornerRadius()));
        borderStyleAlt.setAttribute(XfdfConstants.WIDTH_CAPITAL, XfdfObjectUtils.convertFloatToString(borderStyleAltObject.getWidth()));
        if (borderStyleAltObject.getDashPattern() != null) {
            borderStyleAlt.setAttribute(XfdfConstants.DASH_PATTERN, Arrays.toString(borderStyleAltObject.getDashPattern()));
        }
        if (borderStyleAltObject.getContent() != null) {
            borderStyleAlt.setTextContent(borderStyleAltObject.getContent());
        }
        annot.appendChild(borderStyleAlt);
    }

    private static void addXYZ(FitObject xyzObject, Element dest, Document document) {
        Element xyz = document.createElement(XfdfConstants.XYZ_CAPITAL);
        xyz.setAttribute(XfdfConstants.PAGE_CAPITAL, String.valueOf(xyzObject.getPage()));
        xyz.setAttribute(XfdfConstants.LEFT, XfdfObjectUtils.convertFloatToString(xyzObject.getLeft()));
        xyz.setAttribute(XfdfConstants.BOTTOM, XfdfObjectUtils.convertFloatToString(xyzObject.getBottom()));
        xyz.setAttribute(XfdfConstants.RIGHT, XfdfObjectUtils.convertFloatToString(xyzObject.getRight()));
        xyz.setAttribute(XfdfConstants.TOP, XfdfObjectUtils.convertFloatToString(xyzObject.getTop()));
        dest.appendChild(xyz);
    }

    private static void addFit(FitObject fitObject, Element dest, Document document) {
        Element fit = document.createElement(XfdfConstants.FIT);
        fit.setAttribute(XfdfConstants.PAGE_CAPITAL, String.valueOf(fitObject.getPage()));
        dest.appendChild(fit);
    }

    private static void addFitB(FitObject fitBObject, Element dest, Document document) {
        Element fitB = document.createElement(XfdfConstants.FIT_B);
        fitB.setAttribute(XfdfConstants.PAGE_CAPITAL, String.valueOf(fitBObject.getPage()));
        dest.appendChild(fitB);
    }

    private static void addFitBH(FitObject fitBHObject, Element dest, Document document) {
        Element fitBH = document.createElement(XfdfConstants.FIT_BH);
        fitBH.setAttribute(XfdfConstants.PAGE_CAPITAL, String.valueOf(fitBHObject.getPage()));
        fitBH.setAttribute(XfdfConstants.TOP, XfdfObjectUtils.convertFloatToString(fitBHObject.getTop()));
        dest.appendChild(fitBH);
    }

    private static void addFitBV(FitObject fitBVObject, Element dest, Document document) {
        Element fitBV = document.createElement(XfdfConstants.FIT_BV);
        fitBV.setAttribute(XfdfConstants.PAGE_CAPITAL, String.valueOf(fitBVObject.getPage()));
        fitBV.setAttribute(XfdfConstants.LEFT, XfdfObjectUtils.convertFloatToString(fitBVObject.getLeft()));
        dest.appendChild(fitBV);
    }

    private static void addFitH(FitObject fitHObject, Element dest, Document document) {
        Element fitH = document.createElement(XfdfConstants.FIT_H);
        fitH.setAttribute(XfdfConstants.PAGE_CAPITAL, String.valueOf(fitHObject.getPage()));
        fitH.setAttribute(XfdfConstants.TOP, XfdfObjectUtils.convertFloatToString(fitHObject.getTop()));
        dest.appendChild(fitH);
    }

    private static void addFitR(FitObject fitRObject, Element dest, Document document) {
        Element fitR = document.createElement(XfdfConstants.FIT_R);
        fitR.setAttribute(XfdfConstants.PAGE_CAPITAL, String.valueOf(fitRObject.getPage()));
        fitR.setAttribute(XfdfConstants.LEFT, XfdfObjectUtils.convertFloatToString(fitRObject.getLeft()));
        fitR.setAttribute(XfdfConstants.BOTTOM, XfdfObjectUtils.convertFloatToString(fitRObject.getBottom()));
        fitR.setAttribute(XfdfConstants.RIGHT, XfdfObjectUtils.convertFloatToString(fitRObject.getRight()));
        fitR.setAttribute(XfdfConstants.TOP, XfdfObjectUtils.convertFloatToString(fitRObject.getTop()));
        dest.appendChild(fitR);
    }

    private static void addFitV(FitObject fitVObject, Element dest, Document document) {
        Element fitV = document.createElement(XfdfConstants.FIT_V);
        fitV.setAttribute(XfdfConstants.PAGE_CAPITAL, String.valueOf(fitVObject.getPage()));
        fitV.setAttribute(XfdfConstants.LEFT, XfdfObjectUtils.convertFloatToString(fitVObject.getLeft()));
        dest.appendChild(fitV);
    }

    private static void addDest(DestObject destObject, Element annot, Document document) {
        Element dest = document.createElement(XfdfConstants.DEST);
        if (destObject.getName() != null) {
            Element named = document.createElement(XfdfConstants.NAMED);
            named.setAttribute(XfdfConstants.NAME, destObject.getName());
            dest.appendChild(named);
        } else if (destObject.getXyz() != null) {
            addXYZ(destObject.getXyz(), dest, document);
        } else if (destObject.getFit() != null) {
            addFit(destObject.getFit(), dest, document);
        } else if (destObject.getFitB() != null) {
            addFitB(destObject.getFitB(), dest, document);
        } else if (destObject.getFitBH() != null) {
            addFitBH(destObject.getFitBH(), dest, document);
        } else if (destObject.getFitBV() != null) {
            addFitBV(destObject.getFitBV(), dest, document);
        } else if (destObject.getFitH() != null) {
            addFitH(destObject.getFitH(), dest, document);
        } else if (destObject.getFitR() != null) {
            addFitR(destObject.getFitR(), dest, document);
        } else if (destObject.getFitV() != null) {
            addFitV(destObject.getFitV(), dest, document);
        }
        annot.appendChild(dest);
    }

    private static void addActionObject(ActionObject actionObject, Element onActivation, Document document) {
        Element action = document.createElement(XfdfConstants.ACTION);
        if (actionObject.getUri() != null) {
            Element uri = document.createElement(XfdfConstants.URI);
            uri.setAttribute(XfdfConstants.NAME_CAPITAL, actionObject.getUri().getValue());
            if (actionObject.isMap()) {
                uri.setAttribute(XfdfConstants.IS_MAP, "true");
            } else {
                uri.setAttribute(XfdfConstants.IS_MAP, "false");
            }
            action.appendChild(uri);
        } else if (PdfName.GoTo.equals(actionObject.getType())) {
            Element goTo = document.createElement(XfdfConstants.GO_TO);
            addDest(actionObject.getDestination(), goTo, document);
            action.appendChild(goTo);
        } else if (PdfName.GoToR.equals(actionObject.getType())) {
            Element goToR = document.createElement(XfdfConstants.GO_TO_R);
            if (actionObject.getDestination() != null) {
                addDest(actionObject.getDestination(), goToR, document);
            } else if (actionObject.getFileOriginalName() != null) {
                Element file = document.createElement(XfdfConstants.FILE);
                file.setAttribute(XfdfConstants.ORIGINAL_NAME, actionObject.getFileOriginalName());
                goToR.appendChild(file);
            } else {
                logger.error("Dest or File elements are missing.");
            }
            action.appendChild(goToR);
        } else if (PdfName.Named.equals(actionObject.getType())) {
            Element named = document.createElement(XfdfConstants.NAMED);
            named.setAttribute(XfdfConstants.NAME_CAPITAL, actionObject.getNameAction().getValue());
            action.appendChild(named);
        } else if (PdfName.Launch.equals(actionObject.getType())) {
            Element launch = document.createElement(XfdfConstants.LAUNCH);
            if (actionObject.getFileOriginalName() != null) {
                Element file2 = document.createElement(XfdfConstants.FILE);
                file2.setAttribute(XfdfConstants.ORIGINAL_NAME, actionObject.getFileOriginalName());
                launch.appendChild(file2);
            } else {
                logger.error("File element is missing");
            }
            if (actionObject.isNewWindow()) {
                launch.setAttribute(XfdfConstants.NEW_WINDOW, "true");
            }
            action.appendChild(launch);
        }
        onActivation.appendChild(action);
    }

    private static void addPopup(AnnotObject popupAnnotObject, Element popup, Element annot) {
        for (AttributeObject attr : popupAnnotObject.getAttributes()) {
            popup.setAttribute(attr.getName(), attr.getValue());
        }
        annot.appendChild(popup);
    }
}
