package com.itextpdf.forms.fields;

import com.itextpdf.kernel.pdf.PdfDictionary;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfName;
import com.itextpdf.kernel.pdf.PdfNumber;
import com.itextpdf.kernel.pdf.annot.PdfWidgetAnnotation;

public class PdfTextFormField extends PdfFormField {
    public static final int FF_COMB = makeFieldFlag(25);
    public static final int FF_DO_NOT_SCROLL = makeFieldFlag(24);
    public static final int FF_DO_NOT_SPELL_CHECK = makeFieldFlag(23);
    public static final int FF_FILE_SELECT = makeFieldFlag(21);
    public static final int FF_RICH_TEXT = makeFieldFlag(26);

    protected PdfTextFormField(PdfDocument pdfDocument) {
        super(pdfDocument);
    }

    protected PdfTextFormField(PdfWidgetAnnotation widget, PdfDocument pdfDocument) {
        super(widget, pdfDocument);
    }

    protected PdfTextFormField(PdfDictionary pdfObject) {
        super(pdfObject);
    }

    public PdfName getFormType() {
        return PdfName.f1402Tx;
    }

    public PdfTextFormField setMultiline(boolean multiline) {
        return (PdfTextFormField) setFieldFlag(FF_MULTILINE, multiline);
    }

    public PdfTextFormField setPassword(boolean password) {
        return (PdfTextFormField) setFieldFlag(FF_PASSWORD, password);
    }

    public boolean isFileSelect() {
        return getFieldFlag(FF_FILE_SELECT);
    }

    public PdfTextFormField setFileSelect(boolean fileSelect) {
        return (PdfTextFormField) setFieldFlag(FF_FILE_SELECT, fileSelect);
    }

    public boolean isSpellCheck() {
        return !getFieldFlag(FF_DO_NOT_SPELL_CHECK);
    }

    public PdfTextFormField setSpellCheck(boolean spellCheck) {
        return (PdfTextFormField) setFieldFlag(FF_DO_NOT_SPELL_CHECK, !spellCheck);
    }

    public boolean isScroll() {
        return !getFieldFlag(FF_DO_NOT_SCROLL);
    }

    public PdfTextFormField setScroll(boolean scroll) {
        return (PdfTextFormField) setFieldFlag(FF_DO_NOT_SCROLL, !scroll);
    }

    public boolean isComb() {
        return getFieldFlag(FF_COMB);
    }

    public PdfTextFormField setComb(boolean comb) {
        return (PdfTextFormField) setFieldFlag(FF_COMB, comb);
    }

    public boolean isRichText() {
        return getFieldFlag(FF_RICH_TEXT);
    }

    public PdfTextFormField setRichText(boolean richText) {
        return (PdfTextFormField) setFieldFlag(FF_RICH_TEXT, richText);
    }

    public int getMaxLen() {
        PdfNumber maxLenEntry = ((PdfDictionary) getPdfObject()).getAsNumber(PdfName.MaxLen);
        if (maxLenEntry != null) {
            return maxLenEntry.intValue();
        }
        PdfDictionary parent = getParent();
        if (parent != null) {
            return new PdfTextFormField(parent).getMaxLen();
        }
        return 0;
    }

    public PdfTextFormField setMaxLen(int maxLen) {
        put(PdfName.MaxLen, new PdfNumber(maxLen));
        if (getFieldFlag(FF_COMB)) {
            regenerateField();
        }
        return this;
    }
}
