package com.itextpdf.kernel.pdf.tagutils;

import com.itextpdf.kernel.pdf.tagging.PdfNamespace;
import com.itextpdf.kernel.pdf.tagging.PdfStructureAttributes;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DefaultAccessibilityProperties extends AccessibilityProperties {
    private static final long serialVersionUID = 3139055327755008473L;
    protected String actualText;
    protected String alternateDescription;
    protected List<PdfStructureAttributes> attributesList = new ArrayList();
    protected String expansion;
    protected String language;
    protected PdfNamespace namespace;
    protected String phoneme;
    protected String phoneticAlphabet;
    protected List<TagTreePointer> refs = new ArrayList();
    protected String role;

    public DefaultAccessibilityProperties(String role2) {
        this.role = role2;
    }

    public String getRole() {
        return this.role;
    }

    public AccessibilityProperties setRole(String role2) {
        this.role = role2;
        return this;
    }

    public String getLanguage() {
        return this.language;
    }

    public AccessibilityProperties setLanguage(String language2) {
        this.language = language2;
        return this;
    }

    public String getActualText() {
        return this.actualText;
    }

    public AccessibilityProperties setActualText(String actualText2) {
        this.actualText = actualText2;
        return this;
    }

    public String getAlternateDescription() {
        return this.alternateDescription;
    }

    public AccessibilityProperties setAlternateDescription(String alternateDescription2) {
        this.alternateDescription = alternateDescription2;
        return this;
    }

    public String getExpansion() {
        return this.expansion;
    }

    public AccessibilityProperties setExpansion(String expansion2) {
        this.expansion = expansion2;
        return this;
    }

    public AccessibilityProperties addAttributes(PdfStructureAttributes attributes) {
        return addAttributes(-1, attributes);
    }

    public AccessibilityProperties addAttributes(int index, PdfStructureAttributes attributes) {
        if (attributes != null) {
            if (index > 0) {
                this.attributesList.add(index, attributes);
            } else {
                this.attributesList.add(attributes);
            }
        }
        return this;
    }

    public AccessibilityProperties clearAttributes() {
        this.attributesList.clear();
        return this;
    }

    public List<PdfStructureAttributes> getAttributesList() {
        return this.attributesList;
    }

    public String getPhoneme() {
        return this.phoneme;
    }

    public AccessibilityProperties setPhoneme(String phoneme2) {
        this.phoneme = phoneme2;
        return this;
    }

    public String getPhoneticAlphabet() {
        return this.phoneticAlphabet;
    }

    public AccessibilityProperties setPhoneticAlphabet(String phoneticAlphabet2) {
        this.phoneticAlphabet = phoneticAlphabet2;
        return this;
    }

    public PdfNamespace getNamespace() {
        return this.namespace;
    }

    public AccessibilityProperties setNamespace(PdfNamespace namespace2) {
        this.namespace = namespace2;
        return this;
    }

    public AccessibilityProperties addRef(TagTreePointer treePointer) {
        this.refs.add(new TagTreePointer(treePointer));
        return this;
    }

    public List<TagTreePointer> getRefsList() {
        return Collections.unmodifiableList(this.refs);
    }

    public AccessibilityProperties clearRefs() {
        this.refs.clear();
        return this;
    }
}
