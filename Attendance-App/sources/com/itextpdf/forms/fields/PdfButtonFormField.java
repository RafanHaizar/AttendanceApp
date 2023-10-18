package com.itextpdf.forms.fields;

import com.itextpdf.kernel.pdf.PdfDictionary;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfName;
import com.itextpdf.kernel.pdf.annot.PdfWidgetAnnotation;
import com.itextpdf.kernel.pdf.xobject.PdfFormXObject;
import com.itextpdf.p026io.codec.Base64;
import com.itextpdf.p026io.util.StreamUtil;
import java.io.FileInputStream;
import java.io.IOException;

public class PdfButtonFormField extends PdfFormField {
    public static final int FF_NO_TOGGLE_TO_OFF = makeFieldFlag(15);
    public static final int FF_PUSH_BUTTON = makeFieldFlag(17);
    public static final int FF_RADIO = makeFieldFlag(16);
    public static final int FF_RADIOS_IN_UNISON = makeFieldFlag(26);

    protected PdfButtonFormField(PdfDocument pdfDocument) {
        super(pdfDocument);
    }

    protected PdfButtonFormField(PdfWidgetAnnotation widget, PdfDocument pdfDocument) {
        super(widget, pdfDocument);
    }

    protected PdfButtonFormField(PdfDictionary pdfObject) {
        super(pdfObject);
    }

    public PdfName getFormType() {
        return PdfName.Btn;
    }

    public boolean isRadio() {
        return getFieldFlag(FF_RADIO);
    }

    public PdfButtonFormField setRadio(boolean radio) {
        return (PdfButtonFormField) setFieldFlag(FF_RADIO, radio);
    }

    public boolean isToggleOff() {
        return !getFieldFlag(FF_NO_TOGGLE_TO_OFF);
    }

    public PdfButtonFormField setToggleOff(boolean toggleOff) {
        return (PdfButtonFormField) setFieldFlag(FF_NO_TOGGLE_TO_OFF, !toggleOff);
    }

    public boolean isPushButton() {
        return getFieldFlag(FF_PUSH_BUTTON);
    }

    public PdfButtonFormField setPushButton(boolean pushButton) {
        return (PdfButtonFormField) setFieldFlag(FF_PUSH_BUTTON, pushButton);
    }

    public boolean isRadiosInUnison() {
        return getFieldFlag(FF_RADIOS_IN_UNISON);
    }

    public PdfButtonFormField setRadiosInUnison(boolean radiosInUnison) {
        return (PdfButtonFormField) setFieldFlag(FF_RADIOS_IN_UNISON, radiosInUnison);
    }

    public PdfButtonFormField setImage(String image) throws IOException {
        return (PdfButtonFormField) setValue(Base64.encodeBytes(StreamUtil.inputStreamToArray(new FileInputStream(image))));
    }

    public PdfButtonFormField setImageAsForm(PdfFormXObject form) {
        this.form = form;
        regenerateField();
        return this;
    }
}
