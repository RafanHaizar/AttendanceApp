package com.itextpdf.forms;

import com.itextpdf.forms.fields.PdfFormField;
import com.itextpdf.kernel.pdf.IPdfPageExtraCopier;
import com.itextpdf.kernel.pdf.PdfArray;
import com.itextpdf.kernel.pdf.PdfDictionary;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfName;
import com.itextpdf.kernel.pdf.PdfObject;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfString;
import com.itextpdf.kernel.pdf.annot.PdfAnnotation;
import com.itextpdf.p026io.LogMessageConstant;
import com.itextpdf.p026io.util.MessageFormatUtil;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PdfPageFormCopier implements IPdfPageExtraCopier {
    private static Logger logger = LoggerFactory.getLogger((Class<?>) PdfPageFormCopier.class);
    private PdfDocument documentFrom;
    private PdfDocument documentTo;
    private PdfAcroForm formFrom;
    private PdfAcroForm formTo;

    public void copy(PdfPage fromPage, PdfPage toPage) {
        if (this.documentFrom != fromPage.getDocument()) {
            PdfDocument document = fromPage.getDocument();
            this.documentFrom = document;
            this.formFrom = PdfAcroForm.getAcroForm(document, false);
        }
        if (this.documentTo != toPage.getDocument()) {
            PdfDocument document2 = toPage.getDocument();
            this.documentTo = document2;
            this.formTo = PdfAcroForm.getAcroForm(document2, true);
        }
        if (this.formFrom != null) {
            List<PdfName> excludedKeys = new ArrayList<>();
            excludedKeys.add(PdfName.Fields);
            excludedKeys.add(PdfName.f1315DR);
            ((PdfDictionary) this.formTo.getPdfObject()).mergeDifferent(((PdfDictionary) this.formFrom.getPdfObject()).copyTo(this.documentTo, excludedKeys, false));
            Map<String, PdfFormField> fieldsFrom = this.formFrom.getFormFields();
            if (fieldsFrom.size() > 0) {
                Map<String, PdfFormField> fieldsTo = this.formTo.getFormFields();
                for (PdfAnnotation annot : toPage.getAnnotations()) {
                    if (annot.getSubtype().equals(PdfName.Widget)) {
                        copyField(toPage, fieldsFrom, fieldsTo, annot);
                    }
                }
            }
        }
    }

    private PdfFormField makeFormField(PdfObject fieldDict) {
        PdfFormField field = PdfFormField.makeFormField(fieldDict, this.documentTo);
        if (field == null) {
            logger.warn(MessageFormatUtil.format(LogMessageConstant.CANNOT_CREATE_FORMFIELD, fieldDict.getIndirectReference()));
        }
        return field;
    }

    private void copyField(PdfPage toPage, Map<String, PdfFormField> fieldsFrom, Map<String, PdfFormField> fieldsTo, PdfAnnotation currentAnnot) {
        PdfFormField field;
        PdfDictionary parent = ((PdfDictionary) currentAnnot.getPdfObject()).getAsDictionary(PdfName.Parent);
        if (parent != null) {
            PdfFormField parentField = getParentField(parent, this.documentTo);
            if (parentField != null && parentField.getFieldName() != null) {
                copyParentFormField(toPage, fieldsTo, currentAnnot, parentField);
                return;
            }
            return;
        }
        PdfString annotName = ((PdfDictionary) currentAnnot.getPdfObject()).getAsString(PdfName.f1391T);
        String annotNameString = null;
        if (annotName != null) {
            annotNameString = annotName.toUnicodeString();
        }
        if (annotNameString != null && fieldsFrom.containsKey(annotNameString) && (field = makeFormField(currentAnnot.getPdfObject())) != null) {
            if (fieldsTo.get(annotNameString) != null) {
                field = mergeFieldsWithTheSameName(field);
            }
            this.formTo.addField(field, toPage);
            field.updateDefaultAppearance();
        }
    }

    private void copyParentFormField(PdfPage toPage, Map<String, PdfFormField> fieldsTo, PdfAnnotation annot, PdfFormField parentField) {
        if (!fieldsTo.containsKey(parentField.getFieldName().toUnicodeString())) {
            PdfFormField field = createParentFieldCopy((PdfDictionary) annot.getPdfObject(), this.documentTo);
            PdfArray kids = field.getKids();
            ((PdfDictionary) field.getPdfObject()).remove(PdfName.Kids);
            this.formTo.addField(field, toPage);
            ((PdfDictionary) field.getPdfObject()).put(PdfName.Kids, kids);
            return;
        }
        PdfFormField field2 = makeFormField(annot.getPdfObject());
        if (field2 != null) {
            PdfString fieldName = field2.getFieldName();
            if (fieldName != null) {
                if (fieldsTo.get(fieldName.toUnicodeString()) != null) {
                    PdfFormField mergedField = mergeFieldsWithTheSameName(field2);
                    this.formTo.getFormFields().put(mergedField.getFieldName().toUnicodeString(), mergedField);
                    return;
                }
                HashSet<String> existingFields = new HashSet<>();
                getAllFieldNames(this.formTo.getFields(), existingFields);
                addChildToExistingParent((PdfDictionary) annot.getPdfObject(), existingFields, fieldsTo);
            } else if (!parentField.getKids().contains(field2.getPdfObject())) {
                HashSet<String> existingFields2 = new HashSet<>();
                getAllFieldNames(this.formTo.getFields(), existingFields2);
                addChildToExistingParent((PdfDictionary) annot.getPdfObject(), existingFields2);
            }
        }
    }

    private PdfFormField mergeFieldsWithTheSameName(PdfFormField newField) {
        String fullFieldName = newField.getFieldName().toUnicodeString();
        PdfString fieldName = ((PdfDictionary) newField.getPdfObject()).getAsString(PdfName.f1391T);
        logger.warn(MessageFormatUtil.format(LogMessageConstant.DOCUMENT_ALREADY_HAS_FIELD, fullFieldName));
        PdfFormField existingField = this.formTo.getField(fullFieldName);
        if (existingField.isFlushed()) {
            int index = 0;
            do {
                index++;
                newField.setFieldName(fieldName.toUnicodeString() + "_#" + index);
            } while (this.formTo.getField(newField.getFieldName().toUnicodeString()) != null);
            return newField;
        }
        ((PdfDictionary) newField.getPdfObject()).remove(PdfName.f1391T);
        ((PdfDictionary) newField.getPdfObject()).remove(PdfName.f1367P);
        this.formTo.getFields().remove(existingField.getPdfObject());
        PdfArray kids = existingField.getKids();
        if (kids == null || kids.isEmpty()) {
            ((PdfDictionary) existingField.getPdfObject()).remove(PdfName.f1391T);
            ((PdfDictionary) existingField.getPdfObject()).remove(PdfName.f1367P);
            PdfFormField mergedField = PdfFormField.createEmptyField(this.documentTo);
            mergedField.put(PdfName.f1327FT, existingField.getFormType()).put(PdfName.f1391T, fieldName);
            PdfDictionary parent = existingField.getParent();
            if (parent != null) {
                mergedField.put(PdfName.Parent, parent);
                PdfArray parentKids = parent.getAsArray(PdfName.Kids);
                int i = 0;
                while (true) {
                    if (i >= parentKids.size()) {
                        break;
                    } else if (parentKids.get(i) == existingField.getPdfObject()) {
                        parentKids.set(i, mergedField.getPdfObject());
                        break;
                    } else {
                        i++;
                    }
                }
            }
            PdfArray kids2 = existingField.getKids();
            if (kids2 != null) {
                mergedField.put(PdfName.Kids, kids2);
            }
            mergedField.addKid(existingField).addKid(newField);
            if (((PdfDictionary) existingField.getPdfObject()).get(PdfName.f1406V) != null) {
                mergedField.put(PdfName.f1406V, ((PdfDictionary) existingField.getPdfObject()).get(PdfName.f1406V));
            }
            return mergedField;
        }
        existingField.addKid(newField);
        return existingField;
    }

    private static PdfFormField getParentField(PdfDictionary parent, PdfDocument pdfDoc) {
        PdfDictionary parentOfParent = parent.getAsDictionary(PdfName.Parent);
        if (parentOfParent != null) {
            return getParentField(parentOfParent, pdfDoc);
        }
        return PdfFormField.makeFormField(parent, pdfDoc);
    }

    private PdfFormField createParentFieldCopy(PdfDictionary fieldDic, PdfDocument pdfDoc) {
        PdfDictionary parent = fieldDic.getAsDictionary(PdfName.Parent);
        PdfFormField field = PdfFormField.makeFormField(fieldDic, pdfDoc);
        if (parent != null) {
            field = createParentFieldCopy(parent, pdfDoc);
            PdfArray kids = (PdfArray) parent.get(PdfName.Kids);
            if (kids == null) {
                parent.put(PdfName.Kids, new PdfArray((PdfObject) fieldDic));
            } else {
                kids.add(fieldDic);
            }
        }
        return field;
    }

    private void addChildToExistingParent(PdfDictionary fieldDic, Set<String> existingFields) {
        PdfString parentName;
        PdfDictionary parent = fieldDic.getAsDictionary(PdfName.Parent);
        if (parent != null && (parentName = parent.getAsString(PdfName.f1391T)) != null) {
            if (existingFields.contains(parentName.toUnicodeString())) {
                parent.getAsArray(PdfName.Kids).add(fieldDic);
                return;
            }
            parent.put(PdfName.Kids, new PdfArray((PdfObject) fieldDic));
            addChildToExistingParent(parent, existingFields);
        }
    }

    private void addChildToExistingParent(PdfDictionary fieldDic, Set<String> existingFields, Map<String, PdfFormField> fieldsTo) {
        PdfString parentName;
        PdfDictionary parent = fieldDic.getAsDictionary(PdfName.Parent);
        if (parent != null && (parentName = parent.getAsString(PdfName.f1391T)) != null) {
            if (existingFields.contains(parentName.toUnicodeString())) {
                PdfArray kids = parent.getAsArray(PdfName.Kids);
                Iterator<PdfObject> it = kids.iterator();
                while (it.hasNext()) {
                    PdfObject kid = it.next();
                    if (((PdfDictionary) kid).get(PdfName.f1391T).equals(fieldDic.get(PdfName.f1391T))) {
                        PdfFormField kidField = makeFormField(kid);
                        PdfFormField field = makeFormField(fieldDic);
                        if (!(kidField == null || field == null)) {
                            fieldsTo.put(kidField.getFieldName().toUnicodeString(), kidField);
                            PdfFormField mergedField = mergeFieldsWithTheSameName(field);
                            this.formTo.getFormFields().put(mergedField.getFieldName().toUnicodeString(), mergedField);
                            return;
                        }
                    }
                }
                kids.add(fieldDic);
                return;
            }
            parent.put(PdfName.Kids, new PdfArray((PdfObject) fieldDic));
            addChildToExistingParent(parent, existingFields);
        }
    }

    private void getAllFieldNames(PdfArray fields, Set<String> existingFields) {
        Iterator<PdfObject> it = fields.iterator();
        while (it.hasNext()) {
            PdfObject field = it.next();
            if (!field.isFlushed()) {
                PdfDictionary dic = (PdfDictionary) field;
                PdfString name = dic.getAsString(PdfName.f1391T);
                if (name != null) {
                    existingFields.add(name.toUnicodeString());
                }
                PdfArray kids = dic.getAsArray(PdfName.Kids);
                if (kids != null) {
                    getAllFieldNames(kids, existingFields);
                }
            }
        }
    }
}
