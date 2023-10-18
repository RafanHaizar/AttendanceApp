package com.itextpdf.kernel.pdf.tagutils;

import com.itextpdf.kernel.pdf.PdfArray;
import com.itextpdf.kernel.pdf.PdfDictionary;
import com.itextpdf.kernel.pdf.PdfName;
import com.itextpdf.kernel.pdf.PdfNumber;
import com.itextpdf.kernel.pdf.PdfObject;
import com.itextpdf.kernel.pdf.PdfString;
import com.itextpdf.kernel.pdf.tagging.PdfStructElem;
import com.itextpdf.kernel.pdf.tagging.PdfStructureAttributes;
import com.itextpdf.p026io.font.PdfEncodings;
import java.util.List;

final class AccessibilityPropertiesToStructElem {
    AccessibilityPropertiesToStructElem() {
    }

    static void apply(AccessibilityProperties properties, PdfStructElem elem) {
        if (properties.getActualText() != null) {
            elem.setActualText(new PdfString(properties.getActualText(), PdfEncodings.UNICODE_BIG));
        }
        if (properties.getAlternateDescription() != null) {
            elem.setAlt(new PdfString(properties.getAlternateDescription(), PdfEncodings.UNICODE_BIG));
        }
        if (properties.getExpansion() != null) {
            elem.setE(new PdfString(properties.getExpansion(), PdfEncodings.UNICODE_BIG));
        }
        if (properties.getLanguage() != null) {
            elem.setLang(new PdfString(properties.getLanguage(), PdfEncodings.UNICODE_BIG));
        }
        List<PdfStructureAttributes> newAttributesList = properties.getAttributesList();
        if (newAttributesList.size() > 0) {
            elem.setAttributes(combineAttributesList(elem.getAttributes(false), -1, newAttributesList, ((PdfDictionary) elem.getPdfObject()).getAsNumber(PdfName.f1376R)));
        }
        if (properties.getPhoneme() != null) {
            elem.setPhoneme(new PdfString(properties.getPhoneme(), PdfEncodings.UNICODE_BIG));
        }
        if (properties.getPhoneticAlphabet() != null) {
            elem.setPhoneticAlphabet(new PdfName(properties.getPhoneticAlphabet()));
        }
        if (properties.getNamespace() != null) {
            elem.setNamespace(properties.getNamespace());
        }
        for (TagTreePointer ref : properties.getRefsList()) {
            elem.addRef(ref.getCurrentStructElem());
        }
    }

    static PdfObject combineAttributesList(PdfObject attributesObject, int insertIndex, List<PdfStructureAttributes> newAttributesList, PdfNumber revision) {
        if (attributesObject instanceof PdfDictionary) {
            PdfArray combinedAttributesArray = new PdfArray();
            combinedAttributesArray.add(attributesObject);
            addNewAttributesToAttributesArray(insertIndex, newAttributesList, revision, combinedAttributesArray);
            return combinedAttributesArray;
        } else if (attributesObject instanceof PdfArray) {
            PdfArray combinedAttributesArray2 = (PdfArray) attributesObject;
            addNewAttributesToAttributesArray(insertIndex, newAttributesList, revision, combinedAttributesArray2);
            return combinedAttributesArray2;
        } else if (newAttributesList.size() != 1) {
            PdfArray pdfArray = new PdfArray();
            addNewAttributesToAttributesArray(insertIndex, newAttributesList, revision, pdfArray);
            return pdfArray;
        } else if (insertIndex <= 0) {
            return newAttributesList.get(0).getPdfObject();
        } else {
            throw new IndexOutOfBoundsException();
        }
    }

    private static void addNewAttributesToAttributesArray(int insertIndex, List<PdfStructureAttributes> newAttributesList, PdfNumber revision, PdfArray attributesArray) {
        if (insertIndex < 0) {
            insertIndex = attributesArray.size();
        }
        if (revision != null) {
            for (PdfStructureAttributes attributes : newAttributesList) {
                int insertIndex2 = insertIndex + 1;
                attributesArray.add(insertIndex, attributes.getPdfObject());
                insertIndex = insertIndex2 + 1;
                attributesArray.add(insertIndex2, revision);
            }
            return;
        }
        for (PdfStructureAttributes newAttribute : newAttributesList) {
            attributesArray.add(insertIndex, newAttribute.getPdfObject());
            insertIndex++;
        }
    }
}
