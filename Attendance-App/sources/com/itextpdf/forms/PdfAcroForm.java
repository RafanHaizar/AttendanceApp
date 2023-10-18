package com.itextpdf.forms;

import com.itextpdf.forms.fields.PdfFormField;
import com.itextpdf.forms.xfa.XfaForm;
import com.itextpdf.kernel.PdfException;
import com.itextpdf.kernel.geom.AffineTransform;
import com.itextpdf.kernel.geom.Point;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfArray;
import com.itextpdf.kernel.pdf.PdfBoolean;
import com.itextpdf.kernel.pdf.PdfDictionary;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfName;
import com.itextpdf.kernel.pdf.PdfNumber;
import com.itextpdf.kernel.pdf.PdfObject;
import com.itextpdf.kernel.pdf.PdfObjectWrapper;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfStream;
import com.itextpdf.kernel.pdf.PdfString;
import com.itextpdf.kernel.pdf.PdfVersion;
import com.itextpdf.kernel.pdf.VersionConforming;
import com.itextpdf.kernel.pdf.annot.PdfAnnotation;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.kernel.pdf.tagging.StandardRoles;
import com.itextpdf.kernel.pdf.tagutils.TagTreePointer;
import com.itextpdf.kernel.pdf.xobject.PdfFormXObject;
import com.itextpdf.p026io.LogMessageConstant;
import com.itextpdf.p026io.util.MessageFormatUtil;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PdfAcroForm extends PdfObjectWrapper<PdfDictionary> {
    public static final int APPEND_ONLY = 2;
    public static final int SIGNATURE_EXIST = 1;
    private static Logger logger = LoggerFactory.getLogger((Class<?>) PdfAcroForm.class);
    private PdfDictionary defaultResources;
    protected PdfDocument document;
    protected Map<String, PdfFormField> fields;
    private Set<PdfFormField> fieldsForFlattening;
    protected boolean generateAppearance;
    private XfaForm xfaForm;

    private PdfAcroForm(PdfDictionary pdfObject, PdfDocument pdfDocument) {
        super(pdfObject);
        this.generateAppearance = true;
        this.fields = new LinkedHashMap();
        this.fieldsForFlattening = new LinkedHashSet();
        this.document = pdfDocument;
        getFormFields();
        this.xfaForm = new XfaForm(pdfObject);
    }

    private PdfAcroForm(PdfArray fields2) {
        this(createAcroFormDictionaryByFields(fields2), (PdfDocument) null);
        setForbidRelease();
    }

    public static PdfAcroForm getAcroForm(PdfDocument document2, boolean createIfNotExist) {
        PdfDictionary acroFormDictionary = ((PdfDictionary) document2.getCatalog().getPdfObject()).getAsDictionary(PdfName.AcroForm);
        PdfAcroForm acroForm = null;
        if (acroFormDictionary != null) {
            acroForm = new PdfAcroForm(acroFormDictionary, document2);
        } else if (createIfNotExist) {
            acroForm = new PdfAcroForm(new PdfArray());
            acroForm.makeIndirect(document2);
            document2.getCatalog().put(PdfName.AcroForm, acroForm.getPdfObject());
            document2.getCatalog().setModified();
        }
        if (acroForm != null) {
            PdfDictionary defaultResources2 = acroForm.getDefaultResources();
            acroForm.defaultResources = defaultResources2;
            if (defaultResources2 == null) {
                acroForm.defaultResources = new PdfDictionary();
            }
            acroForm.document = document2;
            acroForm.xfaForm = new XfaForm(document2);
        }
        return acroForm;
    }

    public void addField(PdfFormField field) {
        if (this.document.getNumberOfPages() == 0) {
            this.document.addNewPage();
        }
        addField(field, this.document.getLastPage());
    }

    public void addField(PdfFormField field, PdfPage page) {
        PdfArray kids = field.getKids();
        PdfDictionary fieldDic = (PdfDictionary) field.getPdfObject();
        if (kids != null) {
            processKids(kids, fieldDic, page);
        }
        PdfArray fieldsArray = getFields();
        fieldsArray.add(fieldDic);
        fieldsArray.setModified();
        this.fields.put(field.getFieldName().toUnicodeString(), field);
        if (field.getKids() != null) {
            iterateFields(field.getKids(), this.fields);
        }
        if (fieldDic.containsKey(PdfName.Subtype) && page != null) {
            addWidgetAnnotationToPage(page, PdfAnnotation.makeAnnotation(fieldDic));
        }
        setModified();
    }

    /* JADX WARNING: Code restructure failed: missing block: B:4:0x0014, code lost:
        r3 = (com.itextpdf.kernel.pdf.PdfDictionary) r1.get(0);
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void addFieldAppearanceToPage(com.itextpdf.forms.fields.PdfFormField r7, com.itextpdf.kernel.pdf.PdfPage r8) {
        /*
            r6 = this;
            com.itextpdf.kernel.pdf.PdfObject r0 = r7.getPdfObject()
            com.itextpdf.kernel.pdf.PdfDictionary r0 = (com.itextpdf.kernel.pdf.PdfDictionary) r0
            com.itextpdf.kernel.pdf.PdfArray r1 = r7.getKids()
            if (r1 == 0) goto L_0x003a
            int r2 = r1.size()
            r3 = 1
            if (r2 <= r3) goto L_0x0014
            goto L_0x003a
        L_0x0014:
            r2 = 0
            com.itextpdf.kernel.pdf.PdfObject r3 = r1.get(r2)
            com.itextpdf.kernel.pdf.PdfDictionary r3 = (com.itextpdf.kernel.pdf.PdfDictionary) r3
            com.itextpdf.kernel.pdf.PdfName r4 = com.itextpdf.kernel.pdf.PdfName.Subtype
            com.itextpdf.kernel.pdf.PdfName r4 = r3.getAsName(r4)
            if (r4 == 0) goto L_0x0039
            com.itextpdf.kernel.pdf.PdfName r5 = com.itextpdf.kernel.pdf.PdfName.Widget
            boolean r5 = r4.equals(r5)
            if (r5 == 0) goto L_0x0039
            com.itextpdf.kernel.pdf.PdfName r5 = com.itextpdf.kernel.pdf.PdfName.f1327FT
            boolean r5 = r3.containsKey(r5)
            if (r5 != 0) goto L_0x0036
            r6.mergeWidgetWithParentField(r0, r3)
        L_0x0036:
            r6.defineWidgetPageAndAddToIt(r8, r0, r2)
        L_0x0039:
            return
        L_0x003a:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.forms.PdfAcroForm.addFieldAppearanceToPage(com.itextpdf.forms.fields.PdfFormField, com.itextpdf.kernel.pdf.PdfPage):void");
    }

    public Map<String, PdfFormField> getFormFields() {
        if (this.fields.size() == 0) {
            this.fields = iterateFields(getFields());
        }
        return this.fields;
    }

    public Collection<PdfFormField> getFieldsForFlattening() {
        return Collections.unmodifiableCollection(this.fieldsForFlattening);
    }

    public PdfDocument getPdfDocument() {
        return this.document;
    }

    public PdfAcroForm setNeedAppearances(boolean needAppearances) {
        if (VersionConforming.validatePdfVersionForDeprecatedFeatureLogError(this.document, PdfVersion.PDF_2_0, VersionConforming.DEPRECATED_NEED_APPEARANCES_IN_ACROFORM)) {
            ((PdfDictionary) getPdfObject()).remove(PdfName.NeedAppearances);
            setModified();
        } else {
            put(PdfName.NeedAppearances, PdfBoolean.valueOf(needAppearances));
        }
        return this;
    }

    public PdfBoolean getNeedAppearances() {
        return ((PdfDictionary) getPdfObject()).getAsBoolean(PdfName.NeedAppearances);
    }

    public PdfAcroForm setSignatureFlags(int sigFlags) {
        return put(PdfName.SigFlags, new PdfNumber(sigFlags));
    }

    public PdfAcroForm setSignatureFlag(int sigFlag) {
        return setSignatureFlags(getSignatureFlags() | sigFlag);
    }

    public int getSignatureFlags() {
        PdfNumber f = ((PdfDictionary) getPdfObject()).getAsNumber(PdfName.SigFlags);
        if (f != null) {
            return f.intValue();
        }
        return 0;
    }

    public PdfAcroForm setCalculationOrder(PdfArray calculationOrder) {
        return put(PdfName.f1307CO, calculationOrder);
    }

    public PdfArray getCalculationOrder() {
        return ((PdfDictionary) getPdfObject()).getAsArray(PdfName.f1307CO);
    }

    public PdfAcroForm setDefaultResources(PdfDictionary defaultResources2) {
        return put(PdfName.f1315DR, defaultResources2);
    }

    public PdfDictionary getDefaultResources() {
        return ((PdfDictionary) getPdfObject()).getAsDictionary(PdfName.f1315DR);
    }

    public PdfAcroForm setDefaultAppearance(String appearance) {
        return put(PdfName.f1313DA, new PdfString(appearance));
    }

    public PdfString getDefaultAppearance() {
        return ((PdfDictionary) getPdfObject()).getAsString(PdfName.f1313DA);
    }

    public PdfAcroForm setDefaultJustification(int justification) {
        return put(PdfName.f1375Q, new PdfNumber(justification));
    }

    public PdfNumber getDefaultJustification() {
        return ((PdfDictionary) getPdfObject()).getAsNumber(PdfName.f1375Q);
    }

    public PdfAcroForm setXFAResource(PdfStream xfaResource) {
        return put(PdfName.XFA, xfaResource);
    }

    public PdfAcroForm setXFAResource(PdfArray xfaResource) {
        return put(PdfName.XFA, xfaResource);
    }

    public PdfObject getXFAResource() {
        return ((PdfDictionary) getPdfObject()).get(PdfName.XFA);
    }

    public PdfFormField getField(String fieldName) {
        return this.fields.get(fieldName);
    }

    public boolean isGenerateAppearance() {
        return this.generateAppearance;
    }

    public void setGenerateAppearance(boolean generateAppearance2) {
        if (generateAppearance2) {
            ((PdfDictionary) getPdfObject()).remove(PdfName.NeedAppearances);
            setModified();
        }
        this.generateAppearance = generateAppearance2;
    }

    public void flattenFields() {
        Set<PdfFormField> fields2;
        Iterator<PdfFormField> it;
        Set<PdfPage> wrappedPages;
        Set<PdfFormField> fields3;
        if (!this.document.isAppendMode()) {
            if (this.fieldsForFlattening.size() == 0) {
                this.fields.clear();
                fields2 = new LinkedHashSet<>(getFormFields().values());
            } else {
                fields2 = new LinkedHashSet<>();
                for (PdfFormField field : this.fieldsForFlattening) {
                    fields2.addAll(prepareFieldsForFlattening(field));
                }
            }
            Map<Integer, PdfObject> initialPageResourceClones = new LinkedHashMap<>();
            for (int i = 1; i <= this.document.getNumberOfPages(); i++) {
                PdfObject resources = ((PdfDictionary) this.document.getPage(i).getPdfObject()).getAsDictionary(PdfName.Resources);
                initialPageResourceClones.put(Integer.valueOf(i), resources == null ? null : resources.clone());
            }
            Set<PdfPage> linkedHashSet = new LinkedHashSet<>();
            Iterator<PdfFormField> it2 = fields2.iterator();
            while (it2.hasNext()) {
                PdfFormField field2 = it2.next();
                PdfDictionary fieldObject = (PdfDictionary) field2.getPdfObject();
                PdfPage page = getFieldPage(fieldObject);
                if (page != null) {
                    PdfAnnotation annotation = PdfAnnotation.makeAnnotation(fieldObject);
                    TagTreePointer tagPointer = null;
                    if (annotation != null && this.document.isTagged()) {
                        tagPointer = this.document.getTagStructureContext().removeAnnotationTag(annotation);
                    }
                    PdfDictionary appDic = fieldObject.getAsDictionary(PdfName.f1291AP);
                    PdfObject asNormal = null;
                    if (appDic != null && (asNormal = appDic.getAsStream(PdfName.f1357N)) == null) {
                        asNormal = appDic.getAsDictionary(PdfName.f1357N);
                    }
                    if (this.generateAppearance && (appDic == null || asNormal == null)) {
                        field2.regenerateField();
                        appDic = fieldObject.getAsDictionary(PdfName.f1291AP);
                    }
                    PdfObject normal = appDic != null ? appDic.get(PdfName.f1357N) : null;
                    if (normal != null) {
                        PdfFormXObject xObject = null;
                        if (normal.isStream()) {
                            xObject = new PdfFormXObject((PdfStream) normal);
                            fields3 = fields2;
                        } else if (normal.isDictionary()) {
                            PdfName as = fieldObject.getAsName(PdfName.f1292AS);
                            if (((PdfDictionary) normal).getAsStream(as) != null) {
                                fields3 = fields2;
                                xObject = new PdfFormXObject(((PdfDictionary) normal).getAsStream(as));
                                xObject.makeIndirect(this.document);
                            } else {
                                fields3 = fields2;
                            }
                        } else {
                            fields3 = fields2;
                        }
                        if (xObject != null) {
                            xObject.put(PdfName.Subtype, PdfName.Form);
                            Rectangle annotBBox = fieldObject.getAsRectangle(PdfName.Rect);
                            if (!page.isFlushed()) {
                                PdfCanvas canvas = new PdfCanvas(page, !linkedHashSet.contains(page));
                                linkedHashSet.add(page);
                                wrappedPages = linkedHashSet;
                                PdfObject xObjectResources = ((PdfStream) xObject.getPdfObject()).get(PdfName.Resources);
                                PdfObject pageResources = page.getResources().getPdfObject();
                                if (xObjectResources == null || xObjectResources != pageResources) {
                                    it = it2;
                                    PdfFormField pdfFormField = field2;
                                } else {
                                    PdfObject pdfObject = xObjectResources;
                                    it = it2;
                                    PdfFormField pdfFormField2 = field2;
                                    ((PdfStream) xObject.getPdfObject()).put(PdfName.Resources, initialPageResourceClones.get(Integer.valueOf(this.document.getPageNumber(page))));
                                }
                                if (tagPointer != null) {
                                    tagPointer.setPageForTagging(page);
                                    canvas.openTag(tagPointer.getTagReference());
                                }
                                float[] m = new float[6];
                                calcFieldAppTransformToAnnotRect(xObject, annotBBox).getMatrix(m);
                                canvas.addXObject(xObject, m[0], m[1], m[2], m[3], m[4], m[5]);
                                if (tagPointer != null) {
                                    canvas.closeTag();
                                }
                            } else {
                                Set<PdfPage> wrappedPages2 = linkedHashSet;
                                throw new PdfException(PdfException.f1243xa4aabec1);
                            }
                        } else {
                            wrappedPages = linkedHashSet;
                            it = it2;
                            PdfFormField pdfFormField3 = field2;
                        }
                    } else {
                        fields3 = fields2;
                        wrappedPages = linkedHashSet;
                        it = it2;
                        PdfFormField pdfFormField4 = field2;
                        logger.error(LogMessageConstant.N_ENTRY_IS_REQUIRED_FOR_APPEARANCE_DICTIONARY);
                    }
                    PdfArray fFields = getFields();
                    fFields.remove((PdfObject) fieldObject);
                    if (annotation != null) {
                        page.removeAnnotation(annotation);
                    }
                    PdfDictionary parent = fieldObject.getAsDictionary(PdfName.Parent);
                    if (parent != null) {
                        PdfArray kids = parent.getAsArray(PdfName.Kids);
                        if (kids != null) {
                            kids.remove((PdfObject) fieldObject);
                            if (kids.isEmpty()) {
                                fFields.remove((PdfObject) parent);
                            }
                        } else {
                            fFields.remove((PdfObject) parent);
                        }
                    }
                    fields2 = fields3;
                    linkedHashSet = wrappedPages;
                    it2 = it;
                }
            }
            Set<PdfPage> set = linkedHashSet;
            ((PdfDictionary) getPdfObject()).remove(PdfName.NeedAppearances);
            if (this.fieldsForFlattening.size() == 0) {
                getFields().clear();
            }
            if (getFields().isEmpty()) {
                this.document.getCatalog().remove(PdfName.AcroForm);
                return;
            }
            return;
        }
        throw new PdfException(PdfException.FieldFlatteningIsNotSupportedInAppendMode);
    }

    public boolean removeField(String fieldName) {
        PdfFormField field = getField(fieldName);
        if (field == null) {
            return false;
        }
        PdfDictionary fieldObject = (PdfDictionary) field.getPdfObject();
        PdfPage page = getFieldPage(fieldObject);
        PdfAnnotation annotation = PdfAnnotation.makeAnnotation(fieldObject);
        if (!(page == null || annotation == null)) {
            page.removeAnnotation(annotation);
        }
        PdfDictionary parent = field.getParent();
        if (parent != null) {
            PdfArray kids = parent.getAsArray(PdfName.Kids);
            kids.remove((PdfObject) fieldObject);
            this.fields.remove(fieldName);
            kids.setModified();
            parent.setModified();
            return true;
        }
        PdfArray fieldsPdfArray = getFields();
        if (!fieldsPdfArray.contains(fieldObject)) {
            return false;
        }
        fieldsPdfArray.remove((PdfObject) fieldObject);
        this.fields.remove(fieldName);
        fieldsPdfArray.setModified();
        setModified();
        return true;
    }

    public void partialFormFlattening(String fieldName) {
        PdfFormField field = getFormFields().get(fieldName);
        if (field != null) {
            this.fieldsForFlattening.add(field);
        }
    }

    public void renameField(String oldName, String newName) {
        PdfFormField field;
        Map<String, PdfFormField> fields2 = getFormFields();
        if (!fields2.containsKey(newName) && (field = fields2.get(oldName)) != null) {
            field.setFieldName(newName);
            fields2.remove(oldName);
            fields2.put(newName, field);
        }
    }

    public PdfFormField copyField(String name) {
        PdfFormField oldField = getField(name);
        if (oldField != null) {
            return new PdfFormField((PdfDictionary) ((PdfDictionary) oldField.getPdfObject()).clone().makeIndirect(this.document));
        }
        return null;
    }

    public void replaceField(String name, PdfFormField field) {
        removeField(name);
        addField(field);
    }

    /* access modifiers changed from: protected */
    public PdfArray getFields() {
        PdfArray fields2 = ((PdfDictionary) getPdfObject()).getAsArray(PdfName.Fields);
        if (fields2 != null) {
            return fields2;
        }
        logger.warn(LogMessageConstant.NO_FIELDS_IN_ACROFORM);
        PdfArray fields3 = new PdfArray();
        ((PdfDictionary) getPdfObject()).put(PdfName.Fields, fields3);
        return fields3;
    }

    /* access modifiers changed from: protected */
    public boolean isWrappedObjectMustBeIndirect() {
        return false;
    }

    private Map<String, PdfFormField> iterateFields(PdfArray array, Map<String, PdfFormField> fields2) {
        String name;
        int index = 1;
        Iterator<PdfObject> it = array.iterator();
        while (it.hasNext()) {
            PdfObject field = it.next();
            if (field.isFlushed()) {
                logger.info(LogMessageConstant.FORM_FIELD_WAS_FLUSHED);
            } else {
                PdfFormField formField = PdfFormField.makeFormField(field, this.document);
                if (formField == null) {
                    Logger logger2 = logger;
                    Object[] objArr = new Object[1];
                    objArr[0] = field.getIndirectReference() == null ? field : field.getIndirectReference();
                    logger2.warn(MessageFormatUtil.format(LogMessageConstant.CANNOT_CREATE_FORMFIELD, objArr));
                } else {
                    PdfString fieldName = formField.getFieldName();
                    if (fieldName == null) {
                        PdfFormField parentField = PdfFormField.makeFormField(formField.getParent(), this.document);
                        while (fieldName == null) {
                            fieldName = parentField.getFieldName();
                            if (fieldName == null) {
                                parentField = PdfFormField.makeFormField(parentField.getParent(), this.document);
                            }
                        }
                        name = fieldName.toUnicodeString() + "." + index;
                        index++;
                    } else {
                        name = fieldName.toUnicodeString();
                    }
                    fields2.put(name, formField);
                    if (formField.getKids() != null) {
                        iterateFields(formField.getKids(), fields2);
                    }
                }
            }
        }
        return fields2;
    }

    private Map<String, PdfFormField> iterateFields(PdfArray array) {
        return iterateFields(array, new LinkedHashMap());
    }

    private PdfDictionary processKids(PdfArray kids, PdfDictionary parent, PdfPage page) {
        if (kids.size() == 1) {
            PdfDictionary kidDict = (PdfDictionary) kids.get(0);
            PdfName type = kidDict.getAsName(PdfName.Subtype);
            if (type == null || !type.equals(PdfName.Widget)) {
                PdfArray otherKids = kidDict.getAsArray(PdfName.Kids);
                if (otherKids != null) {
                    processKids(otherKids, kidDict, page);
                }
            } else if (!kidDict.containsKey(PdfName.f1327FT)) {
                mergeWidgetWithParentField(parent, kidDict);
                defineWidgetPageAndAddToIt(page, parent, true);
            } else {
                defineWidgetPageAndAddToIt(page, kidDict, true);
            }
        } else {
            for (int i = 0; i < kids.size(); i++) {
                PdfObject kid = kids.get(i);
                PdfArray otherKids2 = ((PdfDictionary) kid).getAsArray(PdfName.Kids);
                if (otherKids2 != null) {
                    processKids(otherKids2, (PdfDictionary) kid, page);
                }
            }
        }
        return parent;
    }

    private void mergeWidgetWithParentField(PdfDictionary parent, PdfDictionary widgetDict) {
        parent.remove(PdfName.Kids);
        widgetDict.remove(PdfName.Parent);
        parent.mergeDifferent(widgetDict);
    }

    private void defineWidgetPageAndAddToIt(PdfPage currentPage, PdfDictionary mergedFieldAndWidget, boolean warnIfPageFlushed) {
        PdfAnnotation annot = PdfAnnotation.makeAnnotation(mergedFieldAndWidget);
        PdfDictionary pageDic = annot.getPageObject();
        if (pageDic == null) {
            addWidgetAnnotationToPage(currentPage, annot);
        } else if (!warnIfPageFlushed || !pageDic.isFlushed()) {
            addWidgetAnnotationToPage(pageDic.getIndirectReference().getDocument().getPage(pageDic), annot);
        } else {
            throw new PdfException(PdfException.f1243xa4aabec1);
        }
    }

    private void addWidgetAnnotationToPage(PdfPage page, PdfAnnotation annot) {
        if (!page.containsAnnotation(annot)) {
            TagTreePointer tagPointer = null;
            boolean tagged = page.getDocument().isTagged();
            if (tagged) {
                tagPointer = page.getDocument().getTagStructureContext().getAutoTaggingPointer();
                tagPointer.addTag(StandardRoles.FORM);
            }
            page.addAnnotation(annot);
            if (tagged) {
                tagPointer.moveToParent();
            }
        }
    }

    public boolean hasXfaForm() {
        XfaForm xfaForm2 = this.xfaForm;
        return xfaForm2 != null && xfaForm2.isXfaPresent();
    }

    public XfaForm getXfaForm() {
        return this.xfaForm;
    }

    public void removeXfaForm() {
        if (hasXfaForm()) {
            ((PdfDictionary) this.document.getCatalog().getPdfObject()).getAsDictionary(PdfName.AcroForm).remove(PdfName.XFA);
            this.xfaForm = null;
        }
    }

    public PdfAcroForm put(PdfName key, PdfObject value) {
        ((PdfDictionary) getPdfObject()).put(key, value);
        setModified();
        return this;
    }

    public void release() {
        unsetForbidRelease();
        ((PdfDictionary) getPdfObject()).release();
        for (PdfFormField field : this.fields.values()) {
            field.release();
        }
        this.fields = null;
    }

    public PdfObjectWrapper<PdfDictionary> setModified() {
        if (((PdfDictionary) getPdfObject()).getIndirectReference() != null) {
            super.setModified();
        } else {
            this.document.getCatalog().setModified();
        }
        return this;
    }

    private static PdfDictionary createAcroFormDictionaryByFields(PdfArray fields2) {
        PdfDictionary dictionary = new PdfDictionary();
        dictionary.put(PdfName.Fields, fields2);
        return dictionary;
    }

    private PdfPage getFieldPage(PdfDictionary annotDic) {
        PdfAnnotation annotation;
        PdfDictionary pageDic = annotDic.getAsDictionary(PdfName.f1367P);
        if (pageDic != null) {
            return this.document.getPage(pageDic);
        }
        for (int i = 1; i <= this.document.getNumberOfPages(); i++) {
            PdfPage page = this.document.getPage(i);
            if (!page.isFlushed() && (annotation = PdfAnnotation.makeAnnotation(annotDic)) != null && page.containsAnnotation(annotation)) {
                return page;
            }
        }
        return null;
    }

    private Set<PdfFormField> prepareFieldsForFlattening(PdfFormField field) {
        Set<PdfFormField> preparedFields = new LinkedHashSet<>();
        preparedFields.add(field);
        PdfArray kids = field.getKids();
        if (kids != null) {
            Iterator<PdfObject> it = kids.iterator();
            while (it.hasNext()) {
                PdfFormField kidField = new PdfFormField((PdfDictionary) it.next());
                preparedFields.add(kidField);
                if (kidField.getKids() != null) {
                    preparedFields.addAll(prepareFieldsForFlattening(kidField));
                }
            }
        }
        return preparedFields;
    }

    private AffineTransform calcFieldAppTransformToAnnotRect(PdfFormXObject xObject, Rectangle annotBBox) {
        Rectangle transformedRect;
        PdfArray bBox = xObject.getBBox();
        if (bBox.size() != 4) {
            bBox = new PdfArray(new Rectangle(0.0f, 0.0f));
            xObject.setBBox(bBox);
        } else {
            PdfFormXObject pdfFormXObject = xObject;
        }
        float[] xObjBBox = bBox.toFloatArray();
        PdfArray xObjMatrix = ((PdfStream) xObject.getPdfObject()).getAsArray(PdfName.Matrix);
        char c = 1;
        char c2 = 0;
        if (xObjMatrix == null || xObjMatrix.size() != 6) {
            transformedRect = new Rectangle(0.0f, 0.0f).setBbox(xObjBBox[0], xObjBBox[1], xObjBBox[2], xObjBBox[3]);
        } else {
            Point[] xObjRectPoints = {new Point((double) xObjBBox[0], (double) xObjBBox[1]), new Point((double) xObjBBox[0], (double) xObjBBox[3]), new Point((double) xObjBBox[2], (double) xObjBBox[1]), new Point((double) xObjBBox[2], (double) xObjBBox[3])};
            Point[] transformedAppBoxPoints = new Point[xObjRectPoints.length];
            new AffineTransform(xObjMatrix.toDoubleArray()).transform(xObjRectPoints, 0, transformedAppBoxPoints, 0, xObjRectPoints.length);
            float[] transformedRectArr = {Float.MAX_VALUE, Float.MAX_VALUE, -3.4028235E38f, -3.4028235E38f};
            int length = transformedAppBoxPoints.length;
            int i = 0;
            while (i < length) {
                Point p = transformedAppBoxPoints[i];
                float[] transformedRectArr2 = transformedRectArr;
                transformedRectArr2[c2] = (float) Math.min((double) transformedRectArr[c2], p.f1280x);
                transformedRectArr2[c] = (float) Math.min((double) transformedRectArr2[c], p.f1281y);
                transformedRectArr2[2] = (float) Math.max((double) transformedRectArr2[2], p.f1280x);
                transformedRectArr2[3] = (float) Math.max((double) transformedRectArr2[3], p.f1281y);
                i++;
                transformedRectArr = transformedRectArr2;
                c = 1;
                c2 = 0;
            }
            float[] transformedRectArr3 = transformedRectArr;
            transformedRect = new Rectangle(transformedRectArr3[0], transformedRectArr3[1], transformedRectArr3[2] - transformedRectArr3[0], transformedRectArr3[3] - transformedRectArr3[1]);
        }
        AffineTransform at = AffineTransform.getTranslateInstance((double) (-transformedRect.getX()), (double) (-transformedRect.getY()));
        float scaleY = 1.0f;
        float scaleX = transformedRect.getWidth() == 0.0f ? 1.0f : annotBBox.getWidth() / transformedRect.getWidth();
        if (transformedRect.getHeight() != 0.0f) {
            scaleY = annotBBox.getHeight() / transformedRect.getHeight();
        }
        at.preConcatenate(AffineTransform.getScaleInstance((double) scaleX, (double) scaleY));
        at.preConcatenate(AffineTransform.getTranslateInstance((double) annotBBox.getX(), (double) annotBBox.getY()));
        return at;
    }
}
