package com.itextpdf.kernel.pdf.tagutils;

import com.itextpdf.kernel.pdf.PdfArray;
import com.itextpdf.kernel.pdf.PdfDictionary;
import com.itextpdf.kernel.pdf.PdfName;
import com.itextpdf.kernel.pdf.PdfObject;
import com.itextpdf.kernel.pdf.PdfString;
import com.itextpdf.kernel.pdf.tagging.PdfNamespace;
import com.itextpdf.kernel.pdf.tagging.PdfStructElem;
import com.itextpdf.kernel.pdf.tagging.PdfStructTreeRoot;
import com.itextpdf.kernel.pdf.tagging.PdfStructureAttributes;
import com.itextpdf.p026io.font.PdfEncodings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

class BackedAccessibilityProperties extends AccessibilityProperties {
    private static final long serialVersionUID = 4080083623525383278L;
    private TagTreePointer pointerToBackingElem;

    BackedAccessibilityProperties(TagTreePointer pointerToBackingElem2) {
        this.pointerToBackingElem = new TagTreePointer(pointerToBackingElem2);
    }

    public String getRole() {
        return getBackingElem().getRole().getValue();
    }

    public AccessibilityProperties setRole(String role) {
        getBackingElem().setRole(PdfStructTreeRoot.convertRoleToPdfName(role));
        return this;
    }

    public String getLanguage() {
        return toUnicodeString(getBackingElem().getLang());
    }

    public AccessibilityProperties setLanguage(String language) {
        getBackingElem().setLang(new PdfString(language, PdfEncodings.UNICODE_BIG));
        return this;
    }

    public String getActualText() {
        return toUnicodeString(getBackingElem().getActualText());
    }

    public AccessibilityProperties setActualText(String actualText) {
        getBackingElem().setActualText(new PdfString(actualText, PdfEncodings.UNICODE_BIG));
        return this;
    }

    public String getAlternateDescription() {
        return toUnicodeString(getBackingElem().getAlt());
    }

    public AccessibilityProperties setAlternateDescription(String alternateDescription) {
        getBackingElem().setAlt(new PdfString(alternateDescription, PdfEncodings.UNICODE_BIG));
        return this;
    }

    public String getExpansion() {
        return toUnicodeString(getBackingElem().getE());
    }

    public AccessibilityProperties setExpansion(String expansion) {
        getBackingElem().setE(new PdfString(expansion, PdfEncodings.UNICODE_BIG));
        return this;
    }

    public AccessibilityProperties addAttributes(PdfStructureAttributes attributes) {
        return addAttributes(-1, attributes);
    }

    public AccessibilityProperties addAttributes(int index, PdfStructureAttributes attributes) {
        if (attributes == null) {
            return this;
        }
        getBackingElem().setAttributes(AccessibilityPropertiesToStructElem.combineAttributesList(getBackingElem().getAttributes(false), index, Collections.singletonList(attributes), ((PdfDictionary) getBackingElem().getPdfObject()).getAsNumber(PdfName.f1376R)));
        return this;
    }

    public AccessibilityProperties clearAttributes() {
        ((PdfDictionary) getBackingElem().getPdfObject()).remove(PdfName.f1287A);
        return this;
    }

    public List<PdfStructureAttributes> getAttributesList() {
        ArrayList<PdfStructureAttributes> attributesList = new ArrayList<>();
        PdfObject elemAttributesObj = getBackingElem().getAttributes(false);
        if (elemAttributesObj != null) {
            if (elemAttributesObj.isDictionary()) {
                attributesList.add(new PdfStructureAttributes((PdfDictionary) elemAttributesObj));
            } else if (elemAttributesObj.isArray()) {
                Iterator<PdfObject> it = ((PdfArray) elemAttributesObj).iterator();
                while (it.hasNext()) {
                    PdfObject attributeObj = it.next();
                    if (attributeObj.isDictionary()) {
                        attributesList.add(new PdfStructureAttributes((PdfDictionary) attributeObj));
                    }
                }
            }
        }
        return attributesList;
    }

    public AccessibilityProperties setPhoneme(String phoneme) {
        getBackingElem().setPhoneme(new PdfString(phoneme));
        return this;
    }

    public String getPhoneme() {
        return toUnicodeString(getBackingElem().getPhoneme());
    }

    public AccessibilityProperties setPhoneticAlphabet(String phoneticAlphabet) {
        getBackingElem().setPhoneticAlphabet(PdfStructTreeRoot.convertRoleToPdfName(phoneticAlphabet));
        return this;
    }

    public String getPhoneticAlphabet() {
        return getBackingElem().getPhoneticAlphabet().getValue();
    }

    public AccessibilityProperties setNamespace(PdfNamespace namespace) {
        getBackingElem().setNamespace(namespace);
        this.pointerToBackingElem.getContext().ensureNamespaceRegistered(namespace);
        return this;
    }

    public PdfNamespace getNamespace() {
        return getBackingElem().getNamespace();
    }

    public AccessibilityProperties addRef(TagTreePointer treePointer) {
        getBackingElem().addRef(treePointer.getCurrentStructElem());
        return this;
    }

    public List<TagTreePointer> getRefsList() {
        List<TagTreePointer> refsList = new ArrayList<>();
        for (PdfStructElem ref : getBackingElem().getRefsList()) {
            refsList.add(new TagTreePointer(ref, this.pointerToBackingElem.getDocument()));
        }
        return Collections.unmodifiableList(refsList);
    }

    public AccessibilityProperties clearRefs() {
        ((PdfDictionary) getBackingElem().getPdfObject()).remove(PdfName.Ref);
        return this;
    }

    private PdfStructElem getBackingElem() {
        return this.pointerToBackingElem.getCurrentStructElem();
    }

    private String toUnicodeString(PdfString pdfString) {
        if (pdfString != null) {
            return pdfString.toUnicodeString();
        }
        return null;
    }
}
