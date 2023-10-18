package com.itextpdf.forms.fields;

import com.itextpdf.kernel.pdf.PdfArray;
import com.itextpdf.kernel.pdf.PdfDictionary;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfName;
import com.itextpdf.kernel.pdf.PdfNumber;
import com.itextpdf.kernel.pdf.PdfObject;
import com.itextpdf.kernel.pdf.PdfString;
import com.itextpdf.kernel.pdf.annot.PdfWidgetAnnotation;
import com.itextpdf.p026io.LogMessageConstant;
import com.itextpdf.p026io.font.PdfEncodings;
import com.itextpdf.p026io.util.MessageFormatUtil;
import java.util.List;
import org.slf4j.LoggerFactory;

public class PdfChoiceFormField extends PdfFormField {
    public static final int FF_COMBO = makeFieldFlag(18);
    public static final int FF_COMMIT_ON_SEL_CHANGE = makeFieldFlag(27);
    public static final int FF_DO_NOT_SPELL_CHECK = makeFieldFlag(23);
    public static final int FF_EDIT = makeFieldFlag(19);
    public static final int FF_MULTI_SELECT = makeFieldFlag(22);
    public static final int FF_SORT = makeFieldFlag(20);

    protected PdfChoiceFormField(PdfDocument pdfDocument) {
        super(pdfDocument);
    }

    protected PdfChoiceFormField(PdfWidgetAnnotation widget, PdfDocument pdfDocument) {
        super(widget, pdfDocument);
    }

    protected PdfChoiceFormField(PdfDictionary pdfObject) {
        super(pdfObject);
    }

    public PdfName getFormType() {
        return PdfName.f1311Ch;
    }

    public PdfChoiceFormField setTopIndex(int index) {
        put(PdfName.f1396TI, new PdfNumber(index));
        regenerateField();
        return this;
    }

    public PdfNumber getTopIndex() {
        return ((PdfDictionary) getPdfObject()).getAsNumber(PdfName.f1396TI);
    }

    public PdfChoiceFormField setIndices(PdfArray indices) {
        return (PdfChoiceFormField) put(PdfName.f1339I, indices);
    }

    public PdfChoiceFormField setListSelected(String[] optionValues) {
        return setListSelected(optionValues, true);
    }

    public PdfChoiceFormField setListSelected(String[] optionValues, boolean generateAppearance) {
        if (optionValues.length > 1 && !isMultiSelect()) {
            LoggerFactory.getLogger(getClass()).warn(LogMessageConstant.MULTIPLE_VALUES_ON_A_NON_MULTISELECT_FIELD);
        }
        PdfArray options = getOptions();
        PdfArray indices = new PdfArray();
        PdfArray values = new PdfArray();
        List<String> optionsToUnicodeNames = optionsToUnicodeNames();
        for (String element : optionValues) {
            if (element != null) {
                if (optionsToUnicodeNames.contains(element)) {
                    int index = optionsToUnicodeNames.indexOf(element);
                    indices.add(new PdfNumber(index));
                    PdfObject optByIndex = options.get(index);
                    values.add(optByIndex.isString() ? (PdfString) optByIndex : (PdfString) ((PdfArray) optByIndex).get(1));
                } else {
                    if (!isCombo() || !isEdit()) {
                        LoggerFactory.getLogger(getClass()).warn(MessageFormatUtil.format(LogMessageConstant.FIELD_VALUE_IS_NOT_CONTAINED_IN_OPT_ARRAY, element, getFieldName()));
                    }
                    values.add(new PdfString(element, PdfEncodings.UNICODE_BIG));
                }
            }
        }
        if (indices.size() > 0) {
            setIndices(indices);
        } else {
            remove(PdfName.f1339I);
        }
        if (values.size() == 1) {
            put(PdfName.f1406V, values.get(0));
        } else {
            put(PdfName.f1406V, values);
        }
        if (generateAppearance) {
            regenerateField();
        }
        return this;
    }

    public PdfChoiceFormField setListSelected(int[] optionNumbers) {
        if (optionNumbers.length > 1 && !isMultiSelect()) {
            LoggerFactory.getLogger(getClass()).warn(LogMessageConstant.MULTIPLE_VALUES_ON_A_NON_MULTISELECT_FIELD);
        }
        PdfArray indices = new PdfArray();
        PdfArray values = new PdfArray();
        PdfArray options = getOptions();
        for (int number : optionNumbers) {
            if (number >= 0 && number < options.size()) {
                indices.add(new PdfNumber(number));
                PdfObject option = options.get(number);
                if (option.isString()) {
                    values.add(option);
                } else if (option.isArray()) {
                    values.add(((PdfArray) option).get(0));
                }
            }
        }
        if (indices.size() > 0) {
            setIndices(indices);
            if (values.size() == 1) {
                put(PdfName.f1406V, values.get(0));
            } else {
                put(PdfName.f1406V, values);
            }
        } else {
            remove(PdfName.f1339I);
            remove(PdfName.f1406V);
        }
        regenerateField();
        return this;
    }

    public PdfArray getIndices() {
        return ((PdfDictionary) getPdfObject()).getAsArray(PdfName.f1339I);
    }

    public PdfChoiceFormField setCombo(boolean combo) {
        return (PdfChoiceFormField) setFieldFlag(FF_COMBO, combo);
    }

    public boolean isCombo() {
        return getFieldFlag(FF_COMBO);
    }

    public PdfChoiceFormField setEdit(boolean edit) {
        return (PdfChoiceFormField) setFieldFlag(FF_EDIT, edit);
    }

    public boolean isEdit() {
        return getFieldFlag(FF_EDIT);
    }

    public PdfChoiceFormField setSort(boolean sort) {
        return (PdfChoiceFormField) setFieldFlag(FF_SORT, sort);
    }

    public boolean isSort() {
        return getFieldFlag(FF_SORT);
    }

    public PdfChoiceFormField setMultiSelect(boolean multiSelect) {
        return (PdfChoiceFormField) setFieldFlag(FF_MULTI_SELECT, multiSelect);
    }

    public boolean isMultiSelect() {
        return getFieldFlag(FF_MULTI_SELECT);
    }

    public PdfChoiceFormField setSpellCheck(boolean spellCheck) {
        return (PdfChoiceFormField) setFieldFlag(FF_DO_NOT_SPELL_CHECK, !spellCheck);
    }

    public boolean isSpellCheck() {
        return !getFieldFlag(FF_DO_NOT_SPELL_CHECK);
    }

    public PdfChoiceFormField setCommitOnSelChange(boolean commitOnSelChange) {
        return (PdfChoiceFormField) setFieldFlag(FF_COMMIT_ON_SEL_CHANGE, commitOnSelChange);
    }

    public boolean isCommitOnSelChange() {
        return getFieldFlag(FF_COMMIT_ON_SEL_CHANGE);
    }

    /* JADX WARNING: type inference failed for: r5v7, types: [com.itextpdf.kernel.pdf.PdfObject] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private java.util.List<java.lang.String> optionsToUnicodeNames() {
        /*
            r7 = this;
            com.itextpdf.kernel.pdf.PdfArray r0 = r7.getOptions()
            java.util.ArrayList r1 = new java.util.ArrayList
            int r2 = r0.size()
            r1.<init>(r2)
            r2 = 0
        L_0x000e:
            int r3 = r0.size()
            if (r2 >= r3) goto L_0x0042
            com.itextpdf.kernel.pdf.PdfObject r3 = r0.get(r2)
            r4 = 0
            boolean r5 = r3.isString()
            if (r5 == 0) goto L_0x0023
            r4 = r3
            com.itextpdf.kernel.pdf.PdfString r4 = (com.itextpdf.kernel.pdf.PdfString) r4
            goto L_0x0034
        L_0x0023:
            boolean r5 = r3.isArray()
            if (r5 == 0) goto L_0x0034
            r5 = r3
            com.itextpdf.kernel.pdf.PdfArray r5 = (com.itextpdf.kernel.pdf.PdfArray) r5
            r6 = 1
            com.itextpdf.kernel.pdf.PdfObject r5 = r5.get(r6)
            r4 = r5
            com.itextpdf.kernel.pdf.PdfString r4 = (com.itextpdf.kernel.pdf.PdfString) r4
        L_0x0034:
            if (r4 == 0) goto L_0x003b
            java.lang.String r5 = r4.toUnicodeString()
            goto L_0x003c
        L_0x003b:
            r5 = 0
        L_0x003c:
            r1.add(r5)
            int r2 = r2 + 1
            goto L_0x000e
        L_0x0042:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.forms.fields.PdfChoiceFormField.optionsToUnicodeNames():java.util.List");
    }
}
