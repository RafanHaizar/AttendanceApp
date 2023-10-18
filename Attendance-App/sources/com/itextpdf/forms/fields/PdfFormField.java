package com.itextpdf.forms.fields;

import com.itextpdf.forms.util.DrawingUtil;
import com.itextpdf.kernel.PdfException;
import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.colors.DeviceCmyk;
import com.itextpdf.kernel.colors.DeviceGray;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.Matrix;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfAConformanceLevel;
import com.itextpdf.kernel.pdf.PdfArray;
import com.itextpdf.kernel.pdf.PdfDictionary;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfName;
import com.itextpdf.kernel.pdf.PdfNumber;
import com.itextpdf.kernel.pdf.PdfObject;
import com.itextpdf.kernel.pdf.PdfObjectWrapper;
import com.itextpdf.kernel.pdf.PdfOutputStream;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfResources;
import com.itextpdf.kernel.pdf.PdfStream;
import com.itextpdf.kernel.pdf.PdfString;
import com.itextpdf.kernel.pdf.action.PdfAction;
import com.itextpdf.kernel.pdf.annot.PdfAnnotation;
import com.itextpdf.kernel.pdf.annot.PdfWidgetAnnotation;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.kernel.pdf.xobject.PdfFormXObject;
import com.itextpdf.kernel.pdf.xobject.PdfImageXObject;
import com.itextpdf.layout.Canvas;
import com.itextpdf.layout.Style;
import com.itextpdf.layout.element.Div;
import com.itextpdf.layout.element.IBlockElement;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.layout.LayoutArea;
import com.itextpdf.layout.layout.LayoutContext;
import com.itextpdf.layout.property.BoxSizingPropertyValue;
import com.itextpdf.layout.property.Leading;
import com.itextpdf.layout.property.OverflowPropertyValue;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.TransparentColor;
import com.itextpdf.layout.property.VerticalAlignment;
import com.itextpdf.layout.renderer.IRenderer;
import com.itextpdf.p026io.LogMessageConstant;
import com.itextpdf.p026io.codec.Base64;
import com.itextpdf.p026io.codec.TIFFConstants;
import com.itextpdf.p026io.font.PdfEncodings;
import com.itextpdf.p026io.image.ImageData;
import com.itextpdf.p026io.image.ImageDataFactory;
import com.itextpdf.p026io.source.OutputStream;
import com.itextpdf.p026io.util.MessageFormatUtil;
import com.itextpdf.svg.SvgConstants;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.jvm.internal.CharCompanionObject;
import org.slf4j.LoggerFactory;

public class PdfFormField extends PdfObjectWrapper<PdfDictionary> {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    public static final int ALIGN_CENTER = 1;
    public static final int ALIGN_LEFT = 0;
    public static final int ALIGN_RIGHT = 2;
    @Deprecated
    public static final int DA_COLOR = 2;
    @Deprecated
    public static final int DA_FONT = 0;
    @Deprecated
    public static final int DA_SIZE = 1;
    @Deprecated
    public static final int DEFAULT_FONT_SIZE = 12;
    public static final int FF_MULTILINE = makeFieldFlag(13);
    public static final int FF_NO_EXPORT = makeFieldFlag(3);
    public static final int FF_PASSWORD = makeFieldFlag(14);
    public static final int FF_READ_ONLY = makeFieldFlag(1);
    public static final int FF_REQUIRED = makeFieldFlag(2);
    public static final int HIDDEN = 1;
    public static final int HIDDEN_BUT_PRINTABLE = 3;
    @Deprecated
    public static final int MIN_FONT_SIZE = 4;
    public static final int TYPE_CHECK = 1;
    public static final int TYPE_CIRCLE = 2;
    public static final int TYPE_CROSS = 3;
    public static final int TYPE_DIAMOND = 4;
    public static final int TYPE_SQUARE = 5;
    public static final int TYPE_STAR = 6;
    public static final int VISIBLE = 4;
    public static final int VISIBLE_BUT_DOES_NOT_PRINT = 2;
    @Deprecated
    public static final float X_OFFSET = 2.0f;
    protected static String[] typeChars = {"4", SvgConstants.Attributes.PATH_DATA_REL_LINE_TO, "8", "u", "n", "H"};
    protected Color backgroundColor;
    protected Color borderColor;
    protected float borderWidth;
    protected int checkType;
    protected Color color;
    protected PdfFont font;
    protected float fontSize;
    protected PdfFormXObject form;
    protected ImageData img;
    protected PdfAConformanceLevel pdfAConformanceLevel;
    protected int rotation;
    protected String text;

    public PdfFormField(PdfDictionary pdfObject) {
        super(pdfObject);
        this.fontSize = -1.0f;
        this.borderWidth = 1.0f;
        this.rotation = 0;
        ensureObjectIsAddedToDocument(pdfObject);
        setForbidRelease();
        retrieveStyles();
    }

    protected PdfFormField(PdfDocument pdfDocument) {
        this((PdfDictionary) new PdfDictionary().makeIndirect(pdfDocument));
        PdfName formType = getFormType();
        if (formType != null) {
            put(PdfName.f1327FT, formType);
        }
    }

    protected PdfFormField(PdfWidgetAnnotation widget, PdfDocument pdfDocument) {
        this((PdfDictionary) new PdfDictionary().makeIndirect(pdfDocument));
        widget.makeIndirect(pdfDocument);
        addKid(widget);
        put(PdfName.f1327FT, getFormType());
    }

    public static int makeFieldFlag(int bitPosition) {
        return 1 << (bitPosition - 1);
    }

    public static PdfFormField createEmptyField(PdfDocument doc) {
        return createEmptyField(doc, (PdfAConformanceLevel) null);
    }

    public static PdfFormField createEmptyField(PdfDocument doc, PdfAConformanceLevel pdfAConformanceLevel2) {
        PdfFormField field = new PdfFormField(doc);
        field.pdfAConformanceLevel = pdfAConformanceLevel2;
        return field;
    }

    public static PdfButtonFormField createButton(PdfDocument doc, Rectangle rect, int flags) {
        return createButton(doc, rect, flags, (PdfAConformanceLevel) null);
    }

    public static PdfButtonFormField createButton(PdfDocument doc, Rectangle rect, int flags, PdfAConformanceLevel pdfAConformanceLevel2) {
        PdfWidgetAnnotation annot = new PdfWidgetAnnotation(rect);
        PdfButtonFormField field = new PdfButtonFormField(annot, doc);
        field.pdfAConformanceLevel = pdfAConformanceLevel2;
        if (pdfAConformanceLevel2 != null) {
            annot.setFlag(4);
        }
        field.setFieldFlags(flags);
        return field;
    }

    public static PdfButtonFormField createButton(PdfDocument doc, int flags) {
        return createButton(doc, flags, (PdfAConformanceLevel) null);
    }

    public static PdfButtonFormField createButton(PdfDocument doc, int flags, PdfAConformanceLevel pdfAConformanceLevel2) {
        PdfButtonFormField field = new PdfButtonFormField(doc);
        field.pdfAConformanceLevel = pdfAConformanceLevel2;
        field.setFieldFlags(flags);
        return field;
    }

    public static PdfTextFormField createText(PdfDocument doc) {
        PdfAConformanceLevel pdfAConformanceLevel2 = null;
        return createText(doc, (PdfAConformanceLevel) null);
    }

    public static PdfTextFormField createText(PdfDocument doc, PdfAConformanceLevel pdfAConformanceLevel2) {
        PdfTextFormField textFormField = new PdfTextFormField(doc);
        textFormField.pdfAConformanceLevel = pdfAConformanceLevel2;
        return textFormField;
    }

    public static PdfTextFormField createText(PdfDocument doc, Rectangle rect) {
        return new PdfTextFormField(new PdfWidgetAnnotation(rect), doc);
    }

    public static PdfTextFormField createText(PdfDocument doc, Rectangle rect, String name) {
        return createText(doc, rect, name, "");
    }

    public static PdfTextFormField createText(PdfDocument doc, Rectangle rect, String name, String value) {
        return createText(doc, rect, name, value, (PdfFont) null, -1.0f);
    }

    public static PdfTextFormField createText(PdfDocument doc, Rectangle rect, String name, String value, PdfFont font2, float fontSize2) {
        return createText(doc, rect, name, value, font2, fontSize2, false);
    }

    public static PdfTextFormField createText(PdfDocument doc, Rectangle rect, String name, String value, PdfFont font2, float fontSize2, boolean multiline) {
        return createText(doc, rect, name, value, font2, fontSize2, multiline, (PdfAConformanceLevel) null);
    }

    public static PdfTextFormField createText(PdfDocument doc, Rectangle rect, String name, String value, PdfFont font2, float fontSize2, boolean multiline, PdfAConformanceLevel pdfAConformanceLevel2) {
        PdfWidgetAnnotation annot = new PdfWidgetAnnotation(rect);
        PdfTextFormField field = new PdfTextFormField(annot, doc);
        field.pdfAConformanceLevel = pdfAConformanceLevel2;
        if (pdfAConformanceLevel2 != null) {
            annot.setFlag(4);
        }
        field.updateFontAndFontSize(font2, fontSize2);
        field.setMultiline(multiline);
        field.setFieldName(name);
        field.setValue(value);
        return field;
    }

    public static PdfTextFormField createMultilineText(PdfDocument doc, Rectangle rect, String name, String value, PdfFont font2, float fontSize2) {
        return createText(doc, rect, name, value, font2, fontSize2, true);
    }

    public static PdfTextFormField createMultilineText(PdfDocument doc, Rectangle rect, String name, String value) {
        return createText(doc, rect, name, value, (PdfFont) null, -1.0f, true);
    }

    public static PdfChoiceFormField createChoice(PdfDocument doc, int flags) {
        return createChoice(doc, flags, (PdfAConformanceLevel) null);
    }

    public static PdfChoiceFormField createChoice(PdfDocument doc, int flags, PdfAConformanceLevel pdfAConformanceLevel2) {
        PdfChoiceFormField field = new PdfChoiceFormField(doc);
        field.pdfAConformanceLevel = pdfAConformanceLevel2;
        field.setFieldFlags(flags);
        return field;
    }

    public static PdfChoiceFormField createChoice(PdfDocument doc, Rectangle rect, int flags) {
        PdfChoiceFormField field = new PdfChoiceFormField(new PdfWidgetAnnotation(rect), doc);
        field.setFieldFlags(flags);
        return field;
    }

    public static PdfChoiceFormField createChoice(PdfDocument doc, Rectangle rect, String name, String value, PdfArray options, int flags) {
        return createChoice(doc, rect, name, value, (PdfFont) null, -1.0f, options, flags);
    }

    public static PdfChoiceFormField createChoice(PdfDocument doc, Rectangle rect, String name, String value, PdfArray options, int flags, PdfFont font2, PdfAConformanceLevel pdfAConformanceLevel2) {
        return createChoice(doc, rect, name, value, font2, 12.0f, options, flags, pdfAConformanceLevel2);
    }

    public static PdfChoiceFormField createChoice(PdfDocument doc, Rectangle rect, String name, String value, PdfFont font2, float fontSize2, PdfArray options, int flags) {
        return createChoice(doc, rect, name, value, font2, fontSize2, options, flags, (PdfAConformanceLevel) null);
    }

    public static PdfChoiceFormField createChoice(PdfDocument doc, Rectangle rect, String name, String value, PdfFont font2, float fontSize2, PdfArray options, int flags, PdfAConformanceLevel pdfAConformanceLevel2) {
        String value2;
        int i = flags;
        PdfAConformanceLevel pdfAConformanceLevel3 = pdfAConformanceLevel2;
        PdfWidgetAnnotation annot = new PdfWidgetAnnotation(rect);
        PdfFormField field = new PdfChoiceFormField(annot, doc);
        field.pdfAConformanceLevel = pdfAConformanceLevel3;
        if (pdfAConformanceLevel3 != null) {
            annot.setFlag(4);
        }
        field.updateFontAndFontSize(font2, fontSize2);
        field.put(PdfName.Opt, options);
        field.setFieldFlags(i);
        field.setFieldName(name);
        ((PdfChoiceFormField) field).setListSelected(new String[]{value}, false);
        if ((PdfChoiceFormField.FF_COMBO & i) == 0) {
            value2 = optionsArrayToString(options);
        } else {
            value2 = value;
        }
        PdfFormXObject xObject = new PdfFormXObject(new Rectangle(0.0f, 0.0f, rect.getWidth(), rect.getHeight()));
        field.drawChoiceAppearance(rect, field.fontSize, value2, xObject, 0);
        annot.setNormalAppearance((PdfDictionary) xObject.getPdfObject());
        return (PdfChoiceFormField) field;
    }

    public static PdfSignatureFormField createSignature(PdfDocument doc) {
        PdfAConformanceLevel pdfAConformanceLevel2 = null;
        return createSignature(doc, (PdfAConformanceLevel) null);
    }

    public static PdfSignatureFormField createSignature(PdfDocument doc, PdfAConformanceLevel pdfAConformanceLevel2) {
        PdfSignatureFormField signatureFormField = new PdfSignatureFormField(doc);
        signatureFormField.pdfAConformanceLevel = pdfAConformanceLevel2;
        return signatureFormField;
    }

    public static PdfSignatureFormField createSignature(PdfDocument doc, Rectangle rect) {
        return createSignature(doc, rect, (PdfAConformanceLevel) null);
    }

    public static PdfSignatureFormField createSignature(PdfDocument doc, Rectangle rect, PdfAConformanceLevel pdfAConformanceLevel2) {
        PdfWidgetAnnotation annot = new PdfWidgetAnnotation(rect);
        PdfSignatureFormField signatureFormField = new PdfSignatureFormField(annot, doc);
        signatureFormField.pdfAConformanceLevel = pdfAConformanceLevel2;
        if (pdfAConformanceLevel2 != null) {
            annot.setFlag(4);
        }
        return signatureFormField;
    }

    public static PdfButtonFormField createRadioGroup(PdfDocument doc, String name, String value) {
        return createRadioGroup(doc, name, value, (PdfAConformanceLevel) null);
    }

    public static PdfButtonFormField createRadioGroup(PdfDocument doc, String name, String value, PdfAConformanceLevel pdfAConformanceLevel2) {
        PdfButtonFormField radio = createButton(doc, PdfButtonFormField.FF_RADIO);
        radio.setFieldName(name);
        radio.put(PdfName.f1406V, new PdfName(value));
        radio.pdfAConformanceLevel = pdfAConformanceLevel2;
        return radio;
    }

    public static PdfFormField createRadioButton(PdfDocument doc, Rectangle rect, PdfButtonFormField radioGroup, String value) {
        PdfWidgetAnnotation annot = new PdfWidgetAnnotation(rect);
        PdfFormField radio = new PdfButtonFormField(annot, doc);
        if (radioGroup.getValue().toString().substring(1).equals(value)) {
            annot.setAppearanceState(new PdfName(value));
        } else {
            annot.setAppearanceState(new PdfName("Off"));
        }
        radio.drawRadioAppearance(rect.getWidth(), rect.getHeight(), value);
        radioGroup.addKid(radio);
        return radio;
    }

    public static PdfFormField createRadioButton(PdfDocument doc, Rectangle rect, PdfButtonFormField radioGroup, String value, PdfAConformanceLevel pdfAConformanceLevel2) {
        PdfWidgetAnnotation annot = new PdfWidgetAnnotation(rect);
        PdfFormField radio = new PdfButtonFormField(annot, doc);
        radio.pdfAConformanceLevel = pdfAConformanceLevel2;
        if (pdfAConformanceLevel2 != null) {
            annot.setFlag(4);
        }
        if (radioGroup.getValue().toString().substring(1).equals(value)) {
            annot.setAppearanceState(new PdfName(value));
        } else {
            annot.setAppearanceState(new PdfName("Off"));
        }
        radio.drawRadioAppearance(rect.getWidth(), rect.getHeight(), value);
        radioGroup.addKid(radio);
        return radio;
    }

    public static PdfButtonFormField createPushButton(PdfDocument doc, Rectangle rect, String name, String caption) {
        try {
            return createPushButton(doc, rect, name, caption, PdfFontFactory.createFont(), 12.0f);
        } catch (IOException e) {
            throw new PdfException((Throwable) e);
        }
    }

    public static PdfButtonFormField createPushButton(PdfDocument doc, Rectangle rect, String name, String caption, PdfFont font2, float fontSize2) {
        return createPushButton(doc, rect, name, caption, font2, fontSize2, (PdfAConformanceLevel) null);
    }

    public static PdfButtonFormField createPushButton(PdfDocument doc, Rectangle rect, String name, String caption, PdfFont font2, float fontSize2, PdfAConformanceLevel pdfAConformanceLevel2) {
        PdfWidgetAnnotation annot = new PdfWidgetAnnotation(rect);
        PdfButtonFormField field = new PdfButtonFormField(annot, doc);
        field.pdfAConformanceLevel = pdfAConformanceLevel2;
        if (pdfAConformanceLevel2 != null) {
            annot.setFlag(4);
        }
        field.setPushButton(true);
        field.setFieldName(name);
        field.text = caption;
        field.updateFontAndFontSize(font2, fontSize2);
        field.backgroundColor = ColorConstants.LIGHT_GRAY;
        annot.setNormalAppearance((PdfDictionary) field.drawPushButtonAppearance(rect.getWidth(), rect.getHeight(), caption, font2, fontSize2).getPdfObject());
        PdfDictionary mk = new PdfDictionary();
        mk.put(PdfName.f1303CA, new PdfString(caption));
        mk.put(PdfName.f1296BG, new PdfArray(field.backgroundColor.getColorValue()));
        annot.setAppearanceCharacteristics(mk);
        if (pdfAConformanceLevel2 != null) {
            createPushButtonAppearanceState((PdfDictionary) annot.getPdfObject());
        }
        return field;
    }

    public static PdfButtonFormField createCheckBox(PdfDocument doc, Rectangle rect, String name, String value) {
        return createCheckBox(doc, rect, name, value, 3);
    }

    public static PdfButtonFormField createCheckBox(PdfDocument doc, Rectangle rect, String name, String value, int checkType2) {
        return createCheckBox(doc, rect, name, value, checkType2, (PdfAConformanceLevel) null);
    }

    public static PdfButtonFormField createCheckBox(PdfDocument doc, Rectangle rect, String name, String value, int checkType2, PdfAConformanceLevel pdfAConformanceLevel2) {
        PdfWidgetAnnotation annot = new PdfWidgetAnnotation(rect);
        PdfButtonFormField check = new PdfButtonFormField(annot, doc);
        check.pdfAConformanceLevel = pdfAConformanceLevel2;
        check.setFontSize(0);
        check.setCheckType(checkType2);
        check.setFieldName(name);
        check.put(PdfName.f1406V, new PdfName(value));
        annot.setAppearanceState(new PdfName(value));
        String str = "Yes";
        if (pdfAConformanceLevel2 != null) {
            float width = rect.getWidth();
            float height = rect.getHeight();
            if (!"Off".equals(value)) {
                str = value;
            }
            check.drawPdfA2CheckAppearance(width, height, str, checkType2);
            annot.setFlag(4);
        } else {
            float width2 = rect.getWidth();
            float height2 = rect.getHeight();
            if (!"Off".equals(value)) {
                str = value;
            }
            check.drawCheckAppearance(width2, height2, str);
        }
        return check;
    }

    public static PdfChoiceFormField createComboBox(PdfDocument doc, Rectangle rect, String name, String value, String[][] options) {
        try {
            return createComboBox(doc, rect, name, value, options, PdfFontFactory.createFont(), (PdfAConformanceLevel) null);
        } catch (IOException e) {
            throw new PdfException((Throwable) e);
        }
    }

    public static PdfChoiceFormField createComboBox(PdfDocument doc, Rectangle rect, String name, String value, String[][] options, PdfFont font2, PdfAConformanceLevel pdfAConformanceLevel2) {
        return createChoice(doc, rect, name, value, processOptions(options), PdfChoiceFormField.FF_COMBO, font2, pdfAConformanceLevel2);
    }

    public static PdfChoiceFormField createComboBox(PdfDocument doc, Rectangle rect, String name, String value, String[] options) {
        return createComboBox(doc, rect, name, value, options, (PdfFont) null, (PdfAConformanceLevel) null);
    }

    public static PdfChoiceFormField createComboBox(PdfDocument doc, Rectangle rect, String name, String value, String[] options, PdfFont font2, PdfAConformanceLevel pdfAConformanceLevel2) {
        return createChoice(doc, rect, name, value, processOptions(options), PdfChoiceFormField.FF_COMBO, font2, pdfAConformanceLevel2);
    }

    public static PdfChoiceFormField createList(PdfDocument doc, Rectangle rect, String name, String value, String[][] options) {
        return createList(doc, rect, name, value, options, (PdfFont) null, (PdfAConformanceLevel) null);
    }

    public static PdfChoiceFormField createList(PdfDocument doc, Rectangle rect, String name, String value, String[][] options, PdfFont font2, PdfAConformanceLevel pdfAConformanceLevel2) {
        return createChoice(doc, rect, name, value, processOptions(options), 0, font2, pdfAConformanceLevel2);
    }

    public static PdfChoiceFormField createList(PdfDocument doc, Rectangle rect, String name, String value, String[] options) {
        return createList(doc, rect, name, value, options, (PdfFont) null, (PdfAConformanceLevel) null);
    }

    public static PdfChoiceFormField createList(PdfDocument doc, Rectangle rect, String name, String value, String[] options, PdfFont font2, PdfAConformanceLevel pdfAConformanceLevel2) {
        return createChoice(doc, rect, name, value, processOptions(options), 0, font2, pdfAConformanceLevel2);
    }

    public static PdfFormField makeFormField(PdfObject pdfObject, PdfDocument document) {
        PdfFormField field;
        if (!pdfObject.isDictionary()) {
            return null;
        }
        PdfDictionary dictionary = (PdfDictionary) pdfObject;
        PdfName formType = dictionary.getAsName(PdfName.f1327FT);
        if (PdfName.f1402Tx.equals(formType)) {
            field = new PdfTextFormField(dictionary);
        } else if (PdfName.Btn.equals(formType)) {
            field = new PdfButtonFormField(dictionary);
        } else if (PdfName.f1311Ch.equals(formType)) {
            field = new PdfChoiceFormField(dictionary);
        } else if (PdfName.Sig.equals(formType)) {
            field = new PdfSignatureFormField(dictionary);
        } else {
            field = new PdfFormField(dictionary);
        }
        field.makeIndirect(document);
        if (!(document == null || document.getReader() == null || document.getReader().getPdfAConformanceLevel() == null)) {
            field.pdfAConformanceLevel = document.getReader().getPdfAConformanceLevel();
        }
        return field;
    }

    public PdfName getFormType() {
        PdfName formType = ((PdfDictionary) getPdfObject()).getAsName(PdfName.f1327FT);
        if (formType == null) {
            return getTypeFromParent((PdfDictionary) getPdfObject());
        }
        return formType;
    }

    public PdfFormField setValue(String value) {
        return setValue(value, !PdfName.Btn.equals(getFormType()) || !getFieldFlag(PdfButtonFormField.FF_RADIO));
    }

    public PdfFormField setValue(String value, boolean generateAppearance) {
        PdfName formType = getFormType();
        if (formType == null || !PdfName.Btn.equals(formType)) {
            PdfArray kids = getKids();
            if (kids != null) {
                Iterator<PdfObject> it = kids.iterator();
                while (it.hasNext()) {
                    PdfObject kid = it.next();
                    if (kid.isDictionary() && ((PdfDictionary) kid).getAsString(PdfName.f1391T) != null) {
                        PdfFormField field = new PdfFormField((PdfDictionary) kid);
                        field.setValue(value);
                        if (field.getDefaultAppearance() == null) {
                            field.font = this.font;
                            field.fontSize = this.fontSize;
                            field.color = this.color;
                        }
                    }
                }
            }
            if (!PdfName.f1311Ch.equals(formType)) {
                put(PdfName.f1406V, new PdfString(value, PdfEncodings.UNICODE_BIG));
            } else if (this instanceof PdfChoiceFormField) {
                ((PdfChoiceFormField) this).setListSelected(new String[]{value}, false);
            } else {
                new PdfChoiceFormField((PdfDictionary) getPdfObject()).setListSelected(new String[]{value}, false);
            }
        } else if (PdfName.Btn.equals(formType)) {
            if (getFieldFlag(PdfButtonFormField.FF_PUSH_BUTTON)) {
                try {
                    this.img = ImageDataFactory.create(Base64.decode(value));
                } catch (Exception e) {
                    this.text = value;
                }
            } else {
                put(PdfName.f1406V, new PdfName(value));
                for (PdfWidgetAnnotation widget : getWidgets()) {
                    if (Arrays.asList(new PdfFormField((PdfDictionary) widget.getPdfObject()).getAppearanceStates()).contains(value)) {
                        widget.setAppearanceState(new PdfName(value));
                    } else {
                        widget.setAppearanceState(new PdfName("Off"));
                    }
                }
            }
        }
        if (generateAppearance) {
            regenerateField();
        }
        setModified();
        return this;
    }

    public PdfFormField setValue(String value, PdfFont font2, float fontSize2) {
        updateFontAndFontSize(font2, fontSize2);
        return setValue(value);
    }

    private void updateFontAndFontSize(PdfFont font2, float fontSize2) {
        if (font2 == null) {
            font2 = getDocument().getDefaultFont();
        }
        this.font = font2;
        if (fontSize2 < 0.0f) {
            fontSize2 = 12.0f;
        }
        this.fontSize = fontSize2;
    }

    public PdfFormField setValue(String value, String display) {
        if (display == null) {
            return setValue(value);
        }
        setValue(display, true);
        if (!PdfName.Btn.equals(getFormType())) {
            put(PdfName.f1406V, new PdfString(value, PdfEncodings.UNICODE_BIG));
        } else if ((getFieldFlags() & PdfButtonFormField.FF_PUSH_BUTTON) != 0) {
            this.text = value;
        } else {
            put(PdfName.f1406V, new PdfName(value));
        }
        return this;
    }

    public PdfFormField setParent(PdfFormField parent) {
        return put(PdfName.Parent, parent.getPdfObject());
    }

    public PdfDictionary getParent() {
        return ((PdfDictionary) getPdfObject()).getAsDictionary(PdfName.Parent);
    }

    public PdfArray getKids() {
        return ((PdfDictionary) getPdfObject()).getAsArray(PdfName.Kids);
    }

    public PdfFormField addKid(PdfFormField kid) {
        kid.setParent(this);
        PdfArray kids = getKids();
        if (kids == null) {
            kids = new PdfArray();
        }
        kids.add(kid.getPdfObject());
        return put(PdfName.Kids, kids);
    }

    public PdfFormField addKid(PdfWidgetAnnotation kid) {
        kid.setParent(getPdfObject());
        PdfArray kids = getKids();
        if (kids == null) {
            kids = new PdfArray();
        }
        kids.add(kid.getPdfObject());
        return put(PdfName.Kids, kids);
    }

    public PdfFormField setFieldName(String name) {
        return put(PdfName.f1391T, new PdfString(name));
    }

    public PdfString getFieldName() {
        PdfString pName;
        String parentName = "";
        if (!(getParent() == null || (pName = makeFormField(getParent(), getDocument()).getFieldName()) == null)) {
            parentName = pName.toUnicodeString() + ".";
        }
        PdfString name = ((PdfDictionary) getPdfObject()).getAsString(PdfName.f1391T);
        if (name != null) {
            return new PdfString(parentName + name.toUnicodeString(), PdfEncodings.UNICODE_BIG);
        }
        return name;
    }

    public PdfFormField setAlternativeName(String name) {
        return put(PdfName.f1401TU, new PdfString(name));
    }

    public PdfString getAlternativeName() {
        return ((PdfDictionary) getPdfObject()).getAsString(PdfName.f1401TU);
    }

    public PdfFormField setMappingName(String name) {
        return put(PdfName.f1398TM, new PdfString(name));
    }

    public PdfString getMappingName() {
        return ((PdfDictionary) getPdfObject()).getAsString(PdfName.f1398TM);
    }

    public boolean getFieldFlag(int flag) {
        return (getFieldFlags() & flag) != 0;
    }

    public PdfFormField setFieldFlag(int flag) {
        return setFieldFlag(flag, true);
    }

    public PdfFormField setFieldFlag(int flag, boolean value) {
        int flags;
        int flags2 = getFieldFlags();
        if (value) {
            flags = flags2 | flag;
        } else {
            flags = flags2 & (flag ^ -1);
        }
        return setFieldFlags(flags);
    }

    public boolean isMultiline() {
        return getFieldFlag(FF_MULTILINE);
    }

    public boolean isPassword() {
        return getFieldFlag(FF_PASSWORD);
    }

    public PdfFormField setFieldFlags(int flags) {
        int oldFlags = getFieldFlags();
        put(PdfName.f1328Ff, new PdfNumber(flags));
        if (!(((oldFlags ^ flags) & PdfTextFormField.FF_COMB) == 0 || !PdfName.f1402Tx.equals(getFormType()) || new PdfTextFormField((PdfDictionary) getPdfObject()).getMaxLen() == 0)) {
            regenerateField();
        }
        return this;
    }

    public int getFieldFlags() {
        PdfNumber f = ((PdfDictionary) getPdfObject()).getAsNumber(PdfName.f1328Ff);
        if (f != null) {
            return f.intValue();
        }
        PdfDictionary parent = getParent();
        if (parent != null) {
            return new PdfFormField(parent).getFieldFlags();
        }
        return 0;
    }

    public PdfObject getValue() {
        if (((PdfDictionary) getPdfObject()).get(PdfName.f1391T) != null || getParent() == null) {
            return ((PdfDictionary) getPdfObject()).get(PdfName.f1406V);
        }
        return getParent().get(PdfName.f1406V);
    }

    public String getValueAsString() {
        PdfObject value = getValue();
        if (value == null) {
            return "";
        }
        if (value instanceof PdfStream) {
            return new String(((PdfStream) value).getBytes(), StandardCharsets.UTF_8);
        }
        if (value instanceof PdfName) {
            return ((PdfName) value).getValue();
        }
        if (value instanceof PdfString) {
            return ((PdfString) value).toUnicodeString();
        }
        return "";
    }

    public PdfFormField setDefaultValue(PdfObject value) {
        return put(PdfName.f1317DV, value);
    }

    public PdfObject getDefaultValue() {
        return ((PdfDictionary) getPdfObject()).get(PdfName.f1317DV);
    }

    public PdfFormField setAdditionalAction(PdfName key, PdfAction action) {
        PdfAction.setAdditionalAction(this, key, action);
        return this;
    }

    public PdfDictionary getAdditionalAction() {
        return ((PdfDictionary) getPdfObject()).getAsDictionary(PdfName.f1288AA);
    }

    public PdfFormField setOptions(PdfArray options) {
        return put(PdfName.Opt, options);
    }

    public PdfArray getOptions() {
        return ((PdfDictionary) getPdfObject()).getAsArray(PdfName.Opt);
    }

    public List<PdfWidgetAnnotation> getWidgets() {
        List<PdfWidgetAnnotation> widgets = new ArrayList<>();
        PdfName subType = ((PdfDictionary) getPdfObject()).getAsName(PdfName.Subtype);
        if (subType != null && subType.equals(PdfName.Widget)) {
            widgets.add((PdfWidgetAnnotation) PdfAnnotation.makeAnnotation(getPdfObject()));
        }
        PdfArray kids = getKids();
        if (kids != null) {
            for (int i = 0; i < kids.size(); i++) {
                PdfObject kid = kids.get(i);
                PdfName subType2 = ((PdfDictionary) kid).getAsName(PdfName.Subtype);
                if (subType2 != null && subType2.equals(PdfName.Widget)) {
                    widgets.add((PdfWidgetAnnotation) PdfAnnotation.makeAnnotation(kid));
                }
            }
        }
        return widgets;
    }

    public PdfString getDefaultAppearance() {
        PdfDictionary parent;
        PdfString defaultAppearance = ((PdfDictionary) getPdfObject()).getAsString(PdfName.f1313DA);
        if (defaultAppearance == null && (parent = getParent()) != null && parent.containsKey(PdfName.f1327FT)) {
            defaultAppearance = parent.getAsString(PdfName.f1313DA);
        }
        if (defaultAppearance == null) {
            return (PdfString) getAcroFormKey(PdfName.f1313DA, 10);
        }
        return defaultAppearance;
    }

    @Deprecated
    public PdfFormField setDefaultAppearance(String defaultAppearance) {
        byte[] b = defaultAppearance.getBytes(StandardCharsets.UTF_8);
        for (int k = 0; k < b.length; k++) {
            if (b[k] == 10) {
                b[k] = 32;
            }
        }
        put(PdfName.f1313DA, new PdfString(new String(b, StandardCharsets.UTF_8)));
        return this;
    }

    public void updateDefaultAppearance() {
        if (!hasDefaultAppearance()) {
            ((PdfDictionary) getPdfObject()).remove(PdfName.f1313DA);
            setModified();
        } else if (this.font != null) {
            PdfDictionary defaultResources = (PdfDictionary) getAcroFormObject(PdfName.f1315DR, 3);
            if (defaultResources == null) {
                addAcroFormToCatalog();
                defaultResources = new PdfDictionary();
                putAcroFormObject(PdfName.f1315DR, defaultResources);
            }
            PdfDictionary fontResources = defaultResources.getAsDictionary(PdfName.Font);
            if (fontResources == null) {
                fontResources = new PdfDictionary();
                defaultResources.put(PdfName.Font, fontResources);
            }
            PdfName fontName = getFontNameFromDR(fontResources, this.font.getPdfObject());
            if (fontName == null) {
                fontName = getUniqueFontNameForDR(fontResources);
                fontResources.put(fontName, this.font.getPdfObject());
                fontResources.setModified();
            }
            put(PdfName.f1313DA, generateDefaultAppearance(fontName, this.fontSize, this.color));
            getDocument().addFont(this.font);
        } else {
            throw new AssertionError();
        }
    }

    public Integer getJustification() {
        Integer justification = ((PdfDictionary) getPdfObject()).getAsInt(PdfName.f1375Q);
        if (justification != null || getParent() == null) {
            return justification;
        }
        return getParent().getAsInt(PdfName.f1375Q);
    }

    public PdfFormField setJustification(int justification) {
        put(PdfName.f1375Q, new PdfNumber(justification));
        regenerateField();
        return this;
    }

    public PdfString getDefaultStyle() {
        return ((PdfDictionary) getPdfObject()).getAsString(PdfName.f1316DS);
    }

    public PdfFormField setDefaultStyle(PdfString defaultStyleString) {
        put(PdfName.f1316DS, defaultStyleString);
        return this;
    }

    public PdfObject getRichText() {
        return ((PdfDictionary) getPdfObject()).get(PdfName.f1384RV);
    }

    public PdfFormField setRichText(PdfObject richText) {
        put(PdfName.f1384RV, richText);
        return this;
    }

    public float getFontSize() {
        return this.fontSize;
    }

    public PdfFont getFont() {
        return this.font;
    }

    public Color getColor() {
        return this.color;
    }

    public PdfFormField setFont(PdfFont font2) {
        updateFontAndFontSize(font2, this.fontSize);
        regenerateField();
        return this;
    }

    public PdfFormField setFontSize(float fontSize2) {
        updateFontAndFontSize(this.font, fontSize2);
        regenerateField();
        return this;
    }

    public PdfFormField setFontSize(int fontSize2) {
        setFontSize((float) fontSize2);
        return this;
    }

    @Deprecated
    public PdfFormField setFontAndSize(PdfFont font2, int fontSize2) {
        updateFontAndFontSize(font2, (float) fontSize2);
        regenerateField();
        return this;
    }

    public PdfFormField setFontAndSize(PdfFont font2, float fontSize2) {
        updateFontAndFontSize(font2, fontSize2);
        regenerateField();
        return this;
    }

    public PdfFormField setBackgroundColor(Color backgroundColor2) {
        this.backgroundColor = backgroundColor2;
        for (PdfWidgetAnnotation kid : getWidgets()) {
            PdfDictionary mk = kid.getAppearanceCharacteristics();
            if (mk == null) {
                mk = new PdfDictionary();
            }
            if (backgroundColor2 == null) {
                mk.remove(PdfName.f1296BG);
            } else {
                mk.put(PdfName.f1296BG, new PdfArray(backgroundColor2.getColorValue()));
            }
            kid.setAppearanceCharacteristics(mk);
        }
        regenerateField();
        return this;
    }

    public PdfFormField setRotation(int degRotation) {
        if (degRotation % 90 == 0) {
            int degRotation2 = degRotation % 360;
            if (degRotation2 < 0) {
                degRotation2 += 360;
            }
            this.rotation = degRotation2;
            PdfDictionary mk = getWidgets().get(0).getAppearanceCharacteristics();
            if (mk == null) {
                mk = new PdfDictionary();
                put(PdfName.f1353MK, mk);
            }
            mk.put(PdfName.f1376R, new PdfNumber(degRotation2));
            this.rotation = degRotation2;
            regenerateField();
            return this;
        }
        throw new IllegalArgumentException("degRotation.must.be.a.multiple.of.90");
    }

    public PdfFormField setAction(PdfAction action) {
        List<PdfWidgetAnnotation> widgets = getWidgets();
        if (widgets != null) {
            for (PdfWidgetAnnotation widget : widgets) {
                widget.setAction(action);
            }
        }
        return this;
    }

    public PdfFormField setCheckType(int checkType2) {
        if (checkType2 < 1 || checkType2 > 6) {
            checkType2 = 3;
        }
        this.checkType = checkType2;
        this.text = typeChars[checkType2 - 1];
        if (this.pdfAConformanceLevel != null) {
            return this;
        }
        try {
            this.font = PdfFontFactory.createFont("ZapfDingbats");
            return this;
        } catch (IOException e) {
            throw new PdfException((Throwable) e);
        }
    }

    public PdfFormField setVisibility(int visibility) {
        switch (visibility) {
            case 1:
                put(PdfName.f1324F, new PdfNumber(6));
                break;
            case 2:
                break;
            case 3:
                put(PdfName.f1324F, new PdfNumber(36));
                break;
            default:
                put(PdfName.f1324F, new PdfNumber(4));
                break;
        }
        return this;
    }

    public boolean regenerateField() {
        boolean result = true;
        updateDefaultAppearance();
        for (PdfWidgetAnnotation widget : getWidgets()) {
            PdfFormField field = new PdfFormField((PdfDictionary) widget.getPdfObject());
            copyParamsToKids(field);
            result &= field.regenerateWidget(getValueAsString());
        }
        return result;
    }

    public float getBorderWidth() {
        PdfNumber w;
        PdfDictionary bs = getWidgets().get(0).getBorderStyle();
        if (!(bs == null || (w = bs.getAsNumber(PdfName.f1409W)) == null)) {
            this.borderWidth = w.floatValue();
        }
        return this.borderWidth;
    }

    public PdfFormField setBorderWidth(float borderWidth2) {
        PdfDictionary bs = getWidgets().get(0).getBorderStyle();
        if (bs == null) {
            bs = new PdfDictionary();
            put(PdfName.f1298BS, bs);
        }
        bs.put(PdfName.f1409W, new PdfNumber((double) borderWidth2));
        this.borderWidth = borderWidth2;
        regenerateField();
        return this;
    }

    public PdfFormField setBorderStyle(PdfDictionary style) {
        getWidgets().get(0).setBorderStyle(style);
        regenerateField();
        return this;
    }

    public PdfFormField setBorderColor(Color color2) {
        this.borderColor = color2;
        for (PdfWidgetAnnotation kid : getWidgets()) {
            PdfDictionary mk = kid.getAppearanceCharacteristics();
            if (mk == null) {
                mk = new PdfDictionary();
            }
            if (this.borderColor == null) {
                mk.remove(PdfName.f1294BC);
            } else {
                mk.put(PdfName.f1294BC, new PdfArray(this.borderColor.getColorValue()));
            }
            kid.setAppearanceCharacteristics(mk);
        }
        regenerateField();
        return this;
    }

    public PdfFormField setColor(Color color2) {
        this.color = color2;
        regenerateField();
        return this;
    }

    public PdfFormField setReadOnly(boolean readOnly) {
        return setFieldFlag(FF_READ_ONLY, readOnly);
    }

    public boolean isReadOnly() {
        return getFieldFlag(FF_READ_ONLY);
    }

    public PdfFormField setRequired(boolean required) {
        return setFieldFlag(FF_REQUIRED, required);
    }

    public boolean isRequired() {
        return getFieldFlag(FF_REQUIRED);
    }

    public PdfFormField setNoExport(boolean noExport) {
        return setFieldFlag(FF_NO_EXPORT, noExport);
    }

    public boolean isNoExport() {
        return getFieldFlag(FF_NO_EXPORT);
    }

    public PdfFormField setPage(int pageNum) {
        PdfAnnotation annot;
        List<PdfWidgetAnnotation> widgets = getWidgets();
        if (widgets.size() > 0 && (annot = widgets.get(0)) != null) {
            annot.setPage(getDocument().getPage(pageNum));
        }
        return this;
    }

    public String[] getAppearanceStates() {
        PdfDictionary dic;
        Set<String> names = new LinkedHashSet<>();
        PdfString stringOpt = ((PdfDictionary) getPdfObject()).getAsString(PdfName.Opt);
        if (stringOpt != null) {
            names.add(stringOpt.toUnicodeString());
        } else {
            PdfArray arrayOpt = ((PdfDictionary) getPdfObject()).getAsArray(PdfName.Opt);
            if (arrayOpt != null) {
                Iterator<PdfObject> it = arrayOpt.iterator();
                while (it.hasNext()) {
                    PdfObject pdfObject = it.next();
                    PdfString valStr = null;
                    if (pdfObject.isArray()) {
                        valStr = ((PdfArray) pdfObject).getAsString(1);
                    } else if (pdfObject.isString()) {
                        valStr = (PdfString) pdfObject;
                    }
                    if (valStr != null) {
                        names.add(valStr.toUnicodeString());
                    }
                }
            }
        }
        PdfDictionary dic2 = ((PdfDictionary) getPdfObject()).getAsDictionary(PdfName.f1291AP);
        if (!(dic2 == null || (dic = dic2.getAsDictionary(PdfName.f1357N)) == null)) {
            for (PdfName state : dic.keySet()) {
                names.add(state.getValue());
            }
        }
        PdfArray kids = getKids();
        if (kids != null) {
            Iterator<PdfObject> it2 = kids.iterator();
            while (it2.hasNext()) {
                Collections.addAll(names, new PdfFormField((PdfDictionary) it2.next()).getAppearanceStates());
            }
        }
        return (String[]) names.toArray(new String[names.size()]);
    }

    public PdfFormField setAppearance(PdfName appearanceType, String appearanceState, PdfStream appearanceStream) {
        PdfDictionary dic;
        PdfWidgetAnnotation widget = getWidgets().get(0);
        if (widget != null) {
            dic = (PdfDictionary) widget.getPdfObject();
        } else {
            dic = (PdfDictionary) getPdfObject();
        }
        PdfDictionary ap = dic.getAsDictionary(PdfName.f1291AP);
        if (ap != null) {
            PdfDictionary appearanceDictionary = ap.getAsDictionary(appearanceType);
            if (appearanceDictionary == null) {
                ap.put(appearanceType, appearanceStream);
            } else {
                appearanceDictionary.put(new PdfName(appearanceState), appearanceStream);
            }
        }
        return this;
    }

    public PdfFormField setFontSizeAutoScale() {
        this.fontSize = 0.0f;
        regenerateField();
        return this;
    }

    public PdfFormField put(PdfName key, PdfObject value) {
        ((PdfDictionary) getPdfObject()).put(key, value);
        setModified();
        return this;
    }

    public PdfFormField remove(PdfName key) {
        ((PdfDictionary) getPdfObject()).remove(key);
        setModified();
        return this;
    }

    public void release() {
        unsetForbidRelease();
        ((PdfDictionary) getPdfObject()).release();
    }

    /* access modifiers changed from: protected */
    public boolean isWrappedObjectMustBeIndirect() {
        return true;
    }

    /* access modifiers changed from: protected */
    public PdfDocument getDocument() {
        return ((PdfDictionary) getPdfObject()).getIndirectReference().getDocument();
    }

    /* access modifiers changed from: protected */
    public Rectangle getRect(PdfDictionary field) {
        PdfArray rect = field.getAsArray(PdfName.Rect);
        if (rect == null) {
            PdfArray kids = field.getAsArray(PdfName.Kids);
            if (kids != null) {
                rect = ((PdfDictionary) kids.get(0)).getAsArray(PdfName.Rect);
            } else {
                throw new PdfException(PdfException.WrongFormFieldAddAnnotationToTheField);
            }
        }
        if (rect != null) {
            return rect.toRectangle();
        }
        return null;
    }

    protected static PdfArray processOptions(String[][] options) {
        PdfArray array = new PdfArray();
        for (String[] option : options) {
            PdfArray subArray = new PdfArray((PdfObject) new PdfString(option[0], PdfEncodings.UNICODE_BIG));
            subArray.add(new PdfString(option[1], PdfEncodings.UNICODE_BIG));
            array.add(subArray);
        }
        return array;
    }

    protected static PdfArray processOptions(String[] options) {
        PdfArray array = new PdfArray();
        for (String option : options) {
            array.add(new PdfString(option, PdfEncodings.UNICODE_BIG));
        }
        return array;
    }

    /* access modifiers changed from: protected */
    @Deprecated
    public String generateDefaultAppearanceString(PdfFont font2, float fontSize2, Color color2, PdfResources res) {
        PdfStream stream = new PdfStream();
        PdfCanvas canvas = new PdfCanvas(stream, res, getDocument());
        canvas.setFontAndSize(font2, fontSize2);
        if (color2 != null) {
            canvas.setColor(color2, true);
        }
        return new String(stream.getBytes(), StandardCharsets.UTF_8);
    }

    /* access modifiers changed from: protected */
    @Deprecated
    public Object[] getFontAndSize(PdfDictionary asNormal) throws IOException {
        return new Object[]{getFont(), Float.valueOf(getFontSize())};
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    protected static java.lang.Object[] splitDAelements(java.lang.String r11) {
        /*
            com.itextpdf.io.source.PdfTokenizer r0 = new com.itextpdf.io.source.PdfTokenizer
            com.itextpdf.io.source.RandomAccessFileOrArray r1 = new com.itextpdf.io.source.RandomAccessFileOrArray
            com.itextpdf.io.source.RandomAccessSourceFactory r2 = new com.itextpdf.io.source.RandomAccessSourceFactory
            r2.<init>()
            r3 = 0
            byte[] r3 = com.itextpdf.p026io.font.PdfEncodings.convertToBytes((java.lang.String) r11, (java.lang.String) r3)
            com.itextpdf.io.source.IRandomAccessSource r2 = r2.createSource((byte[]) r3)
            r1.<init>(r2)
            r0.<init>(r1)
            java.util.ArrayList r1 = new java.util.ArrayList
            r1.<init>()
            r2 = 3
            java.lang.Object[] r3 = new java.lang.Object[r2]
        L_0x0020:
            boolean r4 = r0.nextToken()     // Catch:{ Exception -> 0x0178 }
            if (r4 == 0) goto L_0x0177
            com.itextpdf.io.source.PdfTokenizer$TokenType r4 = r0.getTokenType()     // Catch:{ Exception -> 0x0178 }
            com.itextpdf.io.source.PdfTokenizer$TokenType r5 = com.itextpdf.p026io.source.PdfTokenizer.TokenType.Comment     // Catch:{ Exception -> 0x0178 }
            if (r4 != r5) goto L_0x002f
            goto L_0x0020
        L_0x002f:
            com.itextpdf.io.source.PdfTokenizer$TokenType r4 = r0.getTokenType()     // Catch:{ Exception -> 0x0178 }
            com.itextpdf.io.source.PdfTokenizer$TokenType r5 = com.itextpdf.p026io.source.PdfTokenizer.TokenType.Other     // Catch:{ Exception -> 0x0178 }
            if (r4 != r5) goto L_0x016e
            java.lang.String r4 = r0.getStringValue()     // Catch:{ Exception -> 0x0178 }
            int r5 = r4.hashCode()     // Catch:{ Exception -> 0x0178 }
            r6 = 0
            r7 = 1
            r8 = 2
            switch(r5) {
                case 103: goto L_0x0065;
                case 107: goto L_0x005b;
                case 2706: goto L_0x0051;
                case 3637: goto L_0x0046;
                default: goto L_0x0045;
            }     // Catch:{ Exception -> 0x0178 }
        L_0x0045:
            goto L_0x006f
        L_0x0046:
            java.lang.String r5 = "rg"
            boolean r4 = r4.equals(r5)     // Catch:{ Exception -> 0x0178 }
            if (r4 == 0) goto L_0x0045
            r4 = 2
            goto L_0x0070
        L_0x0051:
            java.lang.String r5 = "Tf"
            boolean r4 = r4.equals(r5)     // Catch:{ Exception -> 0x0178 }
            if (r4 == 0) goto L_0x0045
            r4 = 0
            goto L_0x0070
        L_0x005b:
            java.lang.String r5 = "k"
            boolean r4 = r4.equals(r5)     // Catch:{ Exception -> 0x0178 }
            if (r4 == 0) goto L_0x0045
            r4 = 3
            goto L_0x0070
        L_0x0065:
            java.lang.String r5 = "g"
            boolean r4 = r4.equals(r5)     // Catch:{ Exception -> 0x0178 }
            if (r4 == 0) goto L_0x0045
            r4 = 1
            goto L_0x0070
        L_0x006f:
            r4 = -1
        L_0x0070:
            switch(r4) {
                case 0: goto L_0x0149;
                case 1: goto L_0x0122;
                case 2: goto L_0x00d8;
                case 3: goto L_0x0078;
                default: goto L_0x0073;
            }     // Catch:{ Exception -> 0x0178 }
        L_0x0073:
            r1.clear()     // Catch:{ Exception -> 0x0178 }
            goto L_0x016c
        L_0x0078:
            int r4 = r1.size()     // Catch:{ Exception -> 0x0178 }
            r5 = 4
            if (r4 < r5) goto L_0x016c
            java.lang.Float r4 = new java.lang.Float     // Catch:{ Exception -> 0x0178 }
            int r6 = r1.size()     // Catch:{ Exception -> 0x0178 }
            int r6 = r6 - r5
            java.lang.Object r5 = r1.get(r6)     // Catch:{ Exception -> 0x0178 }
            java.lang.String r5 = (java.lang.String) r5     // Catch:{ Exception -> 0x0178 }
            r4.<init>(r5)     // Catch:{ Exception -> 0x0178 }
            float r4 = r4.floatValue()     // Catch:{ Exception -> 0x0178 }
            java.lang.Float r5 = new java.lang.Float     // Catch:{ Exception -> 0x0178 }
            int r6 = r1.size()     // Catch:{ Exception -> 0x0178 }
            int r6 = r6 - r2
            java.lang.Object r6 = r1.get(r6)     // Catch:{ Exception -> 0x0178 }
            java.lang.String r6 = (java.lang.String) r6     // Catch:{ Exception -> 0x0178 }
            r5.<init>(r6)     // Catch:{ Exception -> 0x0178 }
            float r5 = r5.floatValue()     // Catch:{ Exception -> 0x0178 }
            java.lang.Float r6 = new java.lang.Float     // Catch:{ Exception -> 0x0178 }
            int r9 = r1.size()     // Catch:{ Exception -> 0x0178 }
            int r9 = r9 - r8
            java.lang.Object r9 = r1.get(r9)     // Catch:{ Exception -> 0x0178 }
            java.lang.String r9 = (java.lang.String) r9     // Catch:{ Exception -> 0x0178 }
            r6.<init>(r9)     // Catch:{ Exception -> 0x0178 }
            float r6 = r6.floatValue()     // Catch:{ Exception -> 0x0178 }
            java.lang.Float r9 = new java.lang.Float     // Catch:{ Exception -> 0x0178 }
            int r10 = r1.size()     // Catch:{ Exception -> 0x0178 }
            int r10 = r10 - r7
            java.lang.Object r7 = r1.get(r10)     // Catch:{ Exception -> 0x0178 }
            java.lang.String r7 = (java.lang.String) r7     // Catch:{ Exception -> 0x0178 }
            r9.<init>(r7)     // Catch:{ Exception -> 0x0178 }
            float r7 = r9.floatValue()     // Catch:{ Exception -> 0x0178 }
            com.itextpdf.kernel.colors.DeviceCmyk r9 = new com.itextpdf.kernel.colors.DeviceCmyk     // Catch:{ Exception -> 0x0178 }
            r9.<init>((float) r4, (float) r5, (float) r6, (float) r7)     // Catch:{ Exception -> 0x0178 }
            r3[r8] = r9     // Catch:{ Exception -> 0x0178 }
            goto L_0x016c
        L_0x00d8:
            int r4 = r1.size()     // Catch:{ Exception -> 0x0178 }
            if (r4 < r2) goto L_0x016c
            java.lang.Float r4 = new java.lang.Float     // Catch:{ Exception -> 0x0178 }
            int r5 = r1.size()     // Catch:{ Exception -> 0x0178 }
            int r5 = r5 - r2
            java.lang.Object r5 = r1.get(r5)     // Catch:{ Exception -> 0x0178 }
            java.lang.String r5 = (java.lang.String) r5     // Catch:{ Exception -> 0x0178 }
            r4.<init>(r5)     // Catch:{ Exception -> 0x0178 }
            float r4 = r4.floatValue()     // Catch:{ Exception -> 0x0178 }
            java.lang.Float r5 = new java.lang.Float     // Catch:{ Exception -> 0x0178 }
            int r6 = r1.size()     // Catch:{ Exception -> 0x0178 }
            int r6 = r6 - r8
            java.lang.Object r6 = r1.get(r6)     // Catch:{ Exception -> 0x0178 }
            java.lang.String r6 = (java.lang.String) r6     // Catch:{ Exception -> 0x0178 }
            r5.<init>(r6)     // Catch:{ Exception -> 0x0178 }
            float r5 = r5.floatValue()     // Catch:{ Exception -> 0x0178 }
            java.lang.Float r6 = new java.lang.Float     // Catch:{ Exception -> 0x0178 }
            int r9 = r1.size()     // Catch:{ Exception -> 0x0178 }
            int r9 = r9 - r7
            java.lang.Object r7 = r1.get(r9)     // Catch:{ Exception -> 0x0178 }
            java.lang.String r7 = (java.lang.String) r7     // Catch:{ Exception -> 0x0178 }
            r6.<init>(r7)     // Catch:{ Exception -> 0x0178 }
            float r6 = r6.floatValue()     // Catch:{ Exception -> 0x0178 }
            com.itextpdf.kernel.colors.DeviceRgb r7 = new com.itextpdf.kernel.colors.DeviceRgb     // Catch:{ Exception -> 0x0178 }
            r7.<init>((float) r4, (float) r5, (float) r6)     // Catch:{ Exception -> 0x0178 }
            r3[r8] = r7     // Catch:{ Exception -> 0x0178 }
            goto L_0x016c
        L_0x0122:
            int r4 = r1.size()     // Catch:{ Exception -> 0x0178 }
            if (r4 < r7) goto L_0x016c
            java.lang.Float r4 = new java.lang.Float     // Catch:{ Exception -> 0x0178 }
            int r5 = r1.size()     // Catch:{ Exception -> 0x0178 }
            int r5 = r5 - r7
            java.lang.Object r5 = r1.get(r5)     // Catch:{ Exception -> 0x0178 }
            java.lang.String r5 = (java.lang.String) r5     // Catch:{ Exception -> 0x0178 }
            r4.<init>(r5)     // Catch:{ Exception -> 0x0178 }
            float r4 = r4.floatValue()     // Catch:{ Exception -> 0x0178 }
            r5 = 0
            int r5 = (r4 > r5 ? 1 : (r4 == r5 ? 0 : -1))
            if (r5 == 0) goto L_0x0148
            com.itextpdf.kernel.colors.DeviceGray r5 = new com.itextpdf.kernel.colors.DeviceGray     // Catch:{ Exception -> 0x0178 }
            r5.<init>(r4)     // Catch:{ Exception -> 0x0178 }
            r3[r8] = r5     // Catch:{ Exception -> 0x0178 }
        L_0x0148:
            goto L_0x016c
        L_0x0149:
            int r4 = r1.size()     // Catch:{ Exception -> 0x0178 }
            if (r4 < r8) goto L_0x016c
            int r4 = r1.size()     // Catch:{ Exception -> 0x0178 }
            int r4 = r4 - r8
            java.lang.Object r4 = r1.get(r4)     // Catch:{ Exception -> 0x0178 }
            r3[r6] = r4     // Catch:{ Exception -> 0x0178 }
            java.lang.Float r4 = new java.lang.Float     // Catch:{ Exception -> 0x0178 }
            int r5 = r1.size()     // Catch:{ Exception -> 0x0178 }
            int r5 = r5 - r7
            java.lang.Object r5 = r1.get(r5)     // Catch:{ Exception -> 0x0178 }
            java.lang.String r5 = (java.lang.String) r5     // Catch:{ Exception -> 0x0178 }
            r4.<init>(r5)     // Catch:{ Exception -> 0x0178 }
            r3[r7] = r4     // Catch:{ Exception -> 0x0178 }
        L_0x016c:
            goto L_0x0020
        L_0x016e:
            java.lang.String r4 = r0.getStringValue()     // Catch:{ Exception -> 0x0178 }
            r1.add(r4)     // Catch:{ Exception -> 0x0178 }
            goto L_0x0020
        L_0x0177:
            goto L_0x0179
        L_0x0178:
            r2 = move-exception
        L_0x0179:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.forms.fields.PdfFormField.splitDAelements(java.lang.String):java.lang.Object[]");
    }

    /* access modifiers changed from: protected */
    public void drawTextAppearance(Rectangle rect, PdfFont font2, float fontSize2, String value, PdfFormXObject appearance) {
        String value2;
        float x;
        int start;
        PdfStream stream = (PdfStream) new PdfStream().makeIndirect(getDocument());
        PdfResources resources = appearance.getResources();
        PdfCanvas canvas = new PdfCanvas(stream, resources, getDocument());
        float height = rect.getHeight();
        float width = rect.getWidth();
        PdfFormXObject xObject = new PdfFormXObject(new Rectangle(0.0f, 0.0f, width, height));
        drawBorder(canvas, xObject, width, height);
        if (isPassword()) {
            value2 = obfuscatePassword(value);
        } else {
            value2 = value;
        }
        canvas.beginVariableText().saveState().endPath();
        TextAlignment textAlignment = convertJustificationToTextAlignment();
        if (textAlignment == TextAlignment.RIGHT) {
            x = rect.getWidth();
        } else if (textAlignment == TextAlignment.CENTER) {
            x = rect.getWidth() / 2.0f;
        } else {
            x = 0.0f;
        }
        Canvas modelCanvas = new Canvas(canvas, new Rectangle(0.0f, -height, 0.0f, height * 2.0f));
        modelCanvas.setProperty(82, true);
        Style paragraphStyle = (Style) ((Style) new Style().setFont(font2)).setFontSize(fontSize2);
        paragraphStyle.setProperty(33, new Leading(2, 1.0f));
        if (this.color != null) {
            paragraphStyle.setProperty(21, new TransparentColor(this.color));
        }
        int maxLen = new PdfTextFormField((PdfDictionary) getPdfObject()).getMaxLen();
        if (!getFieldFlag(PdfTextFormField.FF_COMB) || maxLen == 0) {
            float f = height;
            float f2 = width;
            PdfFormXObject pdfFormXObject = xObject;
            if (getFieldFlag(PdfTextFormField.FF_COMB)) {
                LoggerFactory.getLogger((Class<?>) PdfFormField.class).error(MessageFormatUtil.format(LogMessageConstant.COMB_FLAG_MAY_BE_SET_ONLY_IF_MAXLEN_IS_PRESENT, new Object[0]));
            }
            Style style = paragraphStyle;
            Canvas canvas2 = modelCanvas;
            modelCanvas.showTextAligned((Paragraph) ((Paragraph) createParagraphForTextFieldValue(value2).addStyle(paragraphStyle)).setPaddings(0.0f, 2.0f, 0.0f, 2.0f), x, rect.getHeight() / 2.0f, textAlignment, VerticalAlignment.MIDDLE);
        } else {
            float widthPerCharacter = width / ((float) maxLen);
            int numberOfCharacters = Math.min(maxLen, value2.length());
            switch (C14071.$SwitchMap$com$itextpdf$layout$property$TextAlignment[textAlignment.ordinal()]) {
                case 1:
                    PdfResources pdfResources = resources;
                    start = maxLen - numberOfCharacters;
                    break;
                case 2:
                    PdfResources pdfResources2 = resources;
                    start = (maxLen - numberOfCharacters) / 2;
                    break;
                default:
                    PdfResources pdfResources3 = resources;
                    start = 0;
                    break;
            }
            float f3 = height;
            float startOffset = (((float) start) + 0.5f) * widthPerCharacter;
            int i = start;
            int i2 = 0;
            while (i2 < numberOfCharacters) {
                modelCanvas.showTextAligned((Paragraph) new Paragraph(value2.substring(i2, i2 + 1)).addStyle(paragraphStyle), startOffset + (((float) i2) * widthPerCharacter), rect.getHeight() / 2.0f, TextAlignment.CENTER, VerticalAlignment.MIDDLE);
                i2++;
                width = width;
                xObject = xObject;
            }
            PdfFormXObject pdfFormXObject2 = xObject;
            Style style2 = paragraphStyle;
            Canvas canvas3 = modelCanvas;
        }
        canvas.restoreState().endVariableText();
        ((PdfStream) appearance.getPdfObject()).setData(stream.getBytes());
    }

    /* renamed from: com.itextpdf.forms.fields.PdfFormField$1 */
    static /* synthetic */ class C14071 {
        static final /* synthetic */ int[] $SwitchMap$com$itextpdf$layout$property$TextAlignment;

        static {
            int[] iArr = new int[TextAlignment.values().length];
            $SwitchMap$com$itextpdf$layout$property$TextAlignment = iArr;
            try {
                iArr[TextAlignment.RIGHT.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$itextpdf$layout$property$TextAlignment[TextAlignment.CENTER.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
        }
    }

    /* access modifiers changed from: protected */
    @Deprecated
    public void drawMultiLineTextAppearance(Rectangle rect, PdfFont font2, float fontSize2, String value, PdfFormXObject appearance) {
        drawMultiLineTextAppearance(rect, font2, value, appearance);
    }

    /* access modifiers changed from: protected */
    public void drawMultiLineTextAppearance(Rectangle rect, PdfFont font2, String value, PdfFormXObject appearance) {
        PdfStream stream = (PdfStream) new PdfStream().makeIndirect(getDocument());
        PdfCanvas canvas = new PdfCanvas(stream, appearance.getResources(), getDocument());
        float width = rect.getWidth();
        float height = rect.getHeight();
        drawBorder(canvas, appearance, width, height);
        canvas.beginVariableText();
        Rectangle areaRect = new Rectangle(0.0f, 0.0f, width, height);
        Canvas modelCanvas = new Canvas(canvas, areaRect);
        modelCanvas.setProperty(82, true);
        Paragraph paragraph = ((Paragraph) ((Paragraph) ((Paragraph) createParagraphForTextFieldValue(value).setFont(font2)).setMargin(0.0f)).setPadding(3.0f)).setMultipliedLeading(1.0f);
        float f = this.fontSize;
        if (f == 0.0f) {
            paragraph.setFontSize(approximateFontSizeToFitMultiLine(paragraph, areaRect, modelCanvas.getRenderer()));
        } else {
            paragraph.setFontSize(f);
        }
        paragraph.setProperty(26, true);
        paragraph.setTextAlignment(convertJustificationToTextAlignment());
        Color color2 = this.color;
        if (color2 != null) {
            paragraph.setFontColor(color2);
        }
        paragraph.setHeight(height - 1.0E-5f);
        paragraph.setProperty(105, BoxSizingPropertyValue.BORDER_BOX);
        paragraph.setProperty(103, OverflowPropertyValue.FIT);
        paragraph.setProperty(104, OverflowPropertyValue.HIDDEN);
        modelCanvas.add((IBlockElement) paragraph);
        canvas.endVariableText();
        ((PdfStream) appearance.getPdfObject()).setData(stream.getBytes());
    }

    private void drawChoiceAppearance(Rectangle rect, float fontSize2, String value, PdfFormXObject appearance, int topIndex) {
        PdfArray indices;
        float widthBorder;
        Boolean bool;
        PdfFormField pdfFormField = this;
        float f = fontSize2;
        PdfStream stream = (PdfStream) new PdfStream().makeIndirect(getDocument());
        PdfResources resources = appearance.getResources();
        PdfCanvas canvas = new PdfCanvas(stream, resources, getDocument());
        float width = rect.getWidth();
        float height = rect.getHeight();
        float widthBorder2 = CharCompanionObject.MIN_VALUE;
        List<String> strings = pdfFormField.font.splitString(value, f, width - 6.0f);
        pdfFormField.drawBorder(canvas, appearance, width, height);
        canvas.beginVariableText().saveState().rectangle(3.0d, 3.0d, (double) (width - 6.0f), (double) (height - 2.0f)).clip().endPath();
        PdfResources pdfResources = resources;
        float f2 = width;
        Canvas modelCanvas = new Canvas(canvas, new Rectangle(3.0f, 0.0f, Math.max(0.0f, width - 6.0f), Math.max(0.0f, height - 2.0f)));
        boolean z = true;
        modelCanvas.setProperty(82, true);
        Div div = new Div();
        if (pdfFormField.getFieldFlag(PdfChoiceFormField.FF_COMBO)) {
            div.setVerticalAlignment(VerticalAlignment.MIDDLE);
        }
        div.setHeight(Math.max(0.0f, height - 2.0f));
        int index = 0;
        while (true) {
            if (index >= strings.size()) {
                float f3 = widthBorder2;
                break;
            }
            if (Boolean.TRUE.equals(modelCanvas.getRenderer().getPropertyAsBoolean(25))) {
                float f4 = height;
                float f5 = widthBorder2;
                break;
            }
            float height2 = height;
            Paragraph paragraph = ((Paragraph) ((Paragraph) ((Paragraph) new Paragraph(strings.get(index)).setFont(pdfFormField.font)).setFontSize(f)).setMargins(0.0f, 0.0f, 0.0f, 0.0f)).setMultipliedLeading(1.0f);
            paragraph.setProperty(26, z);
            paragraph.setTextAlignment(convertJustificationToTextAlignment());
            Color color2 = pdfFormField.color;
            if (color2 != null) {
                paragraph.setFontColor(color2);
            }
            if (!pdfFormField.getFieldFlag(PdfChoiceFormField.FF_COMBO)) {
                PdfArray indices2 = ((PdfDictionary) getPdfObject()).getAsArray(PdfName.f1339I);
                if (indices2 == null && getKids() == null && getParent() != null) {
                    PdfArray pdfArray = indices2;
                    indices = getParent().getAsArray(PdfName.f1339I);
                } else {
                    indices = indices2;
                }
                if (indices == null || indices.size() <= 0) {
                } else {
                    Iterator<PdfObject> it = indices.iterator();
                    while (it.hasNext()) {
                        PdfObject ind = it.next();
                        if (ind.isNumber()) {
                            PdfArray indices3 = indices;
                            if (((PdfNumber) ind).getValue() == ((double) (index + topIndex))) {
                                bool = z;
                                widthBorder = widthBorder2;
                                paragraph.setBackgroundColor(new DeviceRgb(10, 36, 106));
                                paragraph.setFontColor(ColorConstants.LIGHT_GRAY);
                            } else {
                                bool = z;
                                widthBorder = widthBorder2;
                            }
                            float f6 = fontSize2;
                            z = bool;
                            widthBorder2 = widthBorder;
                            indices = indices3;
                        }
                    }
                }
            }
            div.add((IBlockElement) paragraph);
            index++;
            pdfFormField = this;
            f = fontSize2;
            height = height2;
            z = z;
            widthBorder2 = widthBorder2;
        }
        modelCanvas.add((IBlockElement) div);
        canvas.restoreState().endVariableText();
        ((PdfStream) appearance.getPdfObject()).setData(stream.getBytes());
    }

    /* access modifiers changed from: protected */
    public void drawBorder(PdfCanvas canvas, PdfFormXObject xObject, float width, float height) {
        PdfName borderType;
        PdfCanvas pdfCanvas = canvas;
        float f = width;
        float f2 = height;
        canvas.saveState();
        float borderWidth2 = getBorderWidth();
        PdfDictionary bs = getWidgets().get(0).getBorderStyle();
        if (borderWidth2 < 0.0f) {
            borderWidth2 = 0.0f;
        }
        Color color2 = this.backgroundColor;
        if (color2 != null) {
            pdfCanvas.setFillColor(color2).rectangle(0.0d, 0.0d, (double) f, (double) f2).fill();
        }
        if (borderWidth2 > 0.0f && this.borderColor != null) {
            float borderWidth3 = Math.max(1.0f, borderWidth2);
            pdfCanvas.setStrokeColor(this.borderColor).setLineWidth(borderWidth3);
            if (!(bs == null || (borderType = bs.getAsName(PdfName.f1385S)) == null || !borderType.equals(PdfName.f1312D))) {
                PdfArray dashArray = bs.getAsArray(PdfName.f1312D);
                int i = 3;
                if (!(dashArray == null || dashArray.size() <= 0 || dashArray.getAsNumber(0) == null)) {
                    i = dashArray.getAsNumber(0).intValue();
                }
                int unitsOn = i;
                pdfCanvas.setLineDash((float) unitsOn, (float) ((dashArray == null || dashArray.size() <= 1 || dashArray.getAsNumber(1) == null) ? unitsOn : dashArray.getAsNumber(1).intValue()), 0.0f);
            }
            canvas.rectangle(0.0d, 0.0d, (double) f, (double) f2).stroke();
            float f3 = borderWidth3;
        }
        applyRotation(xObject, f2, f);
        canvas.restoreState();
    }

    /* access modifiers changed from: protected */
    public void drawRadioBorder(PdfCanvas canvas, PdfFormXObject xObject, float width, float height) {
        float r;
        PdfCanvas pdfCanvas = canvas;
        float f = width;
        float f2 = height;
        canvas.saveState();
        float borderWidth2 = getBorderWidth();
        float cx = f / 2.0f;
        float cy = f2 / 2.0f;
        if (borderWidth2 < 0.0f) {
            borderWidth2 = 0.0f;
        }
        float r2 = (Math.min(width, height) - borderWidth2) / 2.0f;
        Color color2 = this.backgroundColor;
        if (color2 != null) {
            r = r2;
            pdfCanvas.setFillColor(color2).circle((double) cx, (double) cy, (double) ((borderWidth2 / 2.0f) + r2)).fill();
        } else {
            r = r2;
        }
        if (borderWidth2 <= 0.0f || this.borderColor == null) {
        } else {
            pdfCanvas.setStrokeColor(this.borderColor).setLineWidth(Math.max(1.0f, borderWidth2)).circle((double) cx, (double) cy, (double) r).stroke();
        }
        applyRotation(xObject, f2, f);
        canvas.restoreState();
    }

    /* access modifiers changed from: protected */
    public void drawRadioAppearance(float width, float height, String value) {
        Rectangle rect = new Rectangle(0.0f, 0.0f, width, height);
        PdfWidgetAnnotation widget = getWidgets().get(0);
        widget.setNormalAppearance(new PdfDictionary());
        PdfFormXObject xObjectOn = new PdfFormXObject(rect);
        if (value != null) {
            PdfStream streamOn = (PdfStream) new PdfStream().makeIndirect(getDocument());
            PdfCanvas canvasOn = new PdfCanvas(streamOn, new PdfResources(), getDocument());
            drawRadioBorder(canvasOn, xObjectOn, width, height);
            drawRadioField(canvasOn, width, height, true);
            ((PdfStream) xObjectOn.getPdfObject()).getOutputStream().writeBytes(streamOn.getBytes());
            widget.getNormalAppearanceObject().put(new PdfName(value), xObjectOn.getPdfObject());
        }
        PdfStream streamOff = (PdfStream) new PdfStream().makeIndirect(getDocument());
        PdfCanvas canvasOff = new PdfCanvas(streamOff, new PdfResources(), getDocument());
        PdfFormXObject xObjectOff = new PdfFormXObject(rect);
        drawRadioBorder(canvasOff, xObjectOff, width, height);
        ((PdfStream) xObjectOff.getPdfObject()).getOutputStream().writeBytes(streamOff.getBytes());
        widget.getNormalAppearanceObject().put(new PdfName("Off"), xObjectOff.getPdfObject());
        PdfAConformanceLevel pdfAConformanceLevel2 = this.pdfAConformanceLevel;
        if (pdfAConformanceLevel2 == null) {
            return;
        }
        if ("2".equals(pdfAConformanceLevel2.getPart()) || "3".equals(this.pdfAConformanceLevel.getPart())) {
            xObjectOn.getResources();
            xObjectOff.getResources();
        }
    }

    /* access modifiers changed from: protected */
    @Deprecated
    public void drawPdfA1RadioAppearance(float width, float height, String value) {
        PdfStream stream = (PdfStream) new PdfStream().makeIndirect(getDocument());
        PdfCanvas canvas = new PdfCanvas(stream, new PdfResources(), getDocument());
        Rectangle rect = new Rectangle(0.0f, 0.0f, width, height);
        PdfFormXObject xObject = new PdfFormXObject(rect);
        drawBorder(canvas, xObject, width, height);
        drawRadioField(canvas, rect.getWidth(), rect.getHeight(), !"Off".equals(value));
        PdfDictionary normalAppearance = new PdfDictionary();
        normalAppearance.put(new PdfName(value), xObject.getPdfObject());
        ((PdfStream) xObject.getPdfObject()).getOutputStream().writeBytes(stream.getBytes());
        getWidgets().get(0).setNormalAppearance(normalAppearance);
    }

    /* access modifiers changed from: protected */
    public void drawRadioField(PdfCanvas canvas, float width, float height, boolean on) {
        canvas.saveState();
        if (on) {
            canvas.resetFillColorRgb();
            DrawingUtil.drawCircle(canvas, width / 2.0f, height / 2.0f, Math.min(width, height) / 4.0f);
        }
        canvas.restoreState();
    }

    /* access modifiers changed from: protected */
    public void drawCheckAppearance(float width, float height, String onStateName) {
        float f = width;
        float f2 = height;
        Rectangle rect = new Rectangle(0.0f, 0.0f, f, f2);
        PdfStream streamOn = (PdfStream) new PdfStream().makeIndirect(getDocument());
        PdfCanvas canvasOn = new PdfCanvas(streamOn, new PdfResources(), getDocument());
        PdfFormXObject xObjectOn = new PdfFormXObject(rect);
        drawBorder(canvasOn, xObjectOn, f, f2);
        float f3 = height;
        drawCheckBox(canvasOn, width, f3, this.fontSize, true);
        ((PdfStream) xObjectOn.getPdfObject()).getOutputStream().writeBytes(streamOn.getBytes());
        xObjectOn.getResources().addFont(getDocument(), getFont());
        PdfStream streamOff = (PdfStream) new PdfStream().makeIndirect(getDocument());
        PdfCanvas canvasOff = new PdfCanvas(streamOff, new PdfResources(), getDocument());
        PdfFormXObject xObjectOff = new PdfFormXObject(rect);
        drawBorder(canvasOff, xObjectOff, f, f2);
        drawCheckBox(canvasOff, width, f3, this.fontSize, false);
        ((PdfStream) xObjectOff.getPdfObject()).getOutputStream().writeBytes(streamOff.getBytes());
        xObjectOff.getResources().addFont(getDocument(), getFont());
        PdfDictionary normalAppearance = new PdfDictionary();
        normalAppearance.put(new PdfName(onStateName), xObjectOn.getPdfObject());
        normalAppearance.put(new PdfName("Off"), xObjectOff.getPdfObject());
        PdfDictionary mk = new PdfDictionary();
        mk.put(PdfName.f1303CA, new PdfString(this.text));
        PdfWidgetAnnotation widget = getWidgets().get(0);
        widget.put(PdfName.f1353MK, mk);
        widget.setNormalAppearance(normalAppearance);
    }

    /* access modifiers changed from: protected */
    public void drawPdfA2CheckAppearance(float width, float height, String onStateName, int checkType2) {
        float f = width;
        float f2 = height;
        this.checkType = checkType2;
        Rectangle rect = new Rectangle(0.0f, 0.0f, f, f2);
        PdfStream streamOn = (PdfStream) new PdfStream().makeIndirect(getDocument());
        PdfCanvas canvasOn = new PdfCanvas(streamOn, new PdfResources(), getDocument());
        PdfFormXObject xObjectOn = new PdfFormXObject(rect);
        xObjectOn.getResources();
        drawBorder(canvasOn, xObjectOn, f, f2);
        drawPdfACheckBox(canvasOn, f, f2, true);
        ((PdfStream) xObjectOn.getPdfObject()).getOutputStream().writeBytes(streamOn.getBytes());
        PdfStream streamOff = (PdfStream) new PdfStream().makeIndirect(getDocument());
        PdfCanvas canvasOff = new PdfCanvas(streamOff, new PdfResources(), getDocument());
        PdfFormXObject xObjectOff = new PdfFormXObject(rect);
        xObjectOff.getResources();
        drawBorder(canvasOff, xObjectOff, f, f2);
        ((PdfStream) xObjectOff.getPdfObject()).getOutputStream().writeBytes(streamOff.getBytes());
        PdfDictionary normalAppearance = new PdfDictionary();
        normalAppearance.put(new PdfName(onStateName), xObjectOn.getPdfObject());
        normalAppearance.put(new PdfName("Off"), xObjectOff.getPdfObject());
        PdfDictionary mk = new PdfDictionary();
        mk.put(PdfName.f1303CA, new PdfString(this.text));
        PdfWidgetAnnotation widget = getWidgets().get(0);
        widget.put(PdfName.f1353MK, mk);
        widget.setNormalAppearance(normalAppearance);
    }

    /* access modifiers changed from: protected */
    @Deprecated
    public void drawPdfA1CheckAppearance(float width, float height, String selectedValue, int checkType2) {
        PdfStream stream = (PdfStream) new PdfStream().makeIndirect(getDocument());
        PdfCanvas canvas = new PdfCanvas(stream, new PdfResources(), getDocument());
        PdfFormXObject xObject = new PdfFormXObject(new Rectangle(0.0f, 0.0f, width, height));
        this.checkType = checkType2;
        drawBorder(canvas, xObject, width, height);
        drawPdfACheckBox(canvas, width, height, !"Off".equals(selectedValue));
        ((PdfStream) xObject.getPdfObject()).getOutputStream().writeBytes(stream.getBytes());
        PdfDictionary normalAppearance = new PdfDictionary();
        normalAppearance.put(new PdfName(selectedValue), xObject.getPdfObject());
        PdfDictionary mk = new PdfDictionary();
        mk.put(PdfName.f1303CA, new PdfString(this.text));
        PdfWidgetAnnotation widget = getWidgets().get(0);
        widget.put(PdfName.f1353MK, mk);
        widget.setNormalAppearance(normalAppearance);
    }

    /* access modifiers changed from: protected */
    public PdfFormXObject drawPushButtonAppearance(float width, float height, String text2, PdfFont font2, float fontSize2) {
        PdfFormXObject xObject;
        float f = width;
        float f2 = height;
        PdfStream stream = (PdfStream) new PdfStream().makeIndirect(getDocument());
        PdfCanvas canvas = new PdfCanvas(stream, new PdfResources(), getDocument());
        PdfFormXObject xObject2 = new PdfFormXObject(new Rectangle(0.0f, 0.0f, f, f2));
        drawBorder(canvas, xObject2, f, f2);
        if (this.img != null) {
            PdfImageXObject imgXObj = new PdfImageXObject(this.img);
            float f3 = this.borderWidth;
            canvas.addXObject(imgXObj, f - f3, 0.0f, 0.0f, f2 - f3, f3 / 2.0f, f3 / 2.0f);
            xObject2.getResources().addImage(imgXObj);
            PdfFont pdfFont = font2;
            xObject = xObject2;
            PdfCanvas pdfCanvas = canvas;
        } else {
            PdfFormXObject pdfFormXObject = this.form;
            if (pdfFormXObject != null) {
                float height2 = (f2 - this.borderWidth) / pdfFormXObject.getHeight();
                float height3 = (f2 - this.borderWidth) / this.form.getHeight();
                float f4 = this.borderWidth;
                canvas.addXObject(pdfFormXObject, height2, 0.0f, 0.0f, height3, f4 / 2.0f, f4 / 2.0f);
                xObject2.getResources().addForm(this.form);
                PdfFont pdfFont2 = font2;
                xObject = xObject2;
                PdfCanvas pdfCanvas2 = canvas;
            } else {
                xObject = xObject2;
                PdfCanvas pdfCanvas3 = canvas;
                drawButton(canvas, 0.0f, 0.0f, width, height, text2, font2, fontSize2);
                xObject.getResources().addFont(getDocument(), font2);
            }
        }
        ((PdfStream) xObject.getPdfObject()).getOutputStream().writeBytes(stream.getBytes());
        return xObject;
    }

    /* access modifiers changed from: protected */
    @Deprecated
    public PdfFormXObject drawPushButtonAppearance(float width, float height, String text2, PdfFont font2, PdfName fontName, float fontSize2) {
        return drawPushButtonAppearance(width, height, text2, font2, fontSize2);
    }

    /* access modifiers changed from: protected */
    public void drawButton(PdfCanvas canvas, float x, float y, float width, float height, String text2, PdfFont font2, float fontSize2) {
        String text3;
        float f = width;
        float f2 = height;
        if (this.color == null) {
            this.color = ColorConstants.BLACK;
        }
        if (text2 == null) {
            text3 = "";
        } else {
            text3 = text2;
        }
        Canvas modelCanvas = new Canvas(canvas, new Rectangle(0.0f, -f2, f, f2 * 2.0f));
        modelCanvas.setProperty(82, true);
        modelCanvas.showTextAligned((Paragraph) ((Paragraph) ((Paragraph) ((Paragraph) new Paragraph(text3).setFont(font2)).setFontSize(fontSize2)).setMargin(0.0f)).setMultipliedLeading(1.0f).setVerticalAlignment(VerticalAlignment.MIDDLE), f / 2.0f, f2 / 2.0f, TextAlignment.CENTER, VerticalAlignment.MIDDLE);
    }

    /* access modifiers changed from: protected */
    public void drawCheckBox(PdfCanvas canvas, float width, float height, float fontSize2, boolean on) {
        if (on) {
            if (this.checkType == 3) {
                DrawingUtil.drawCross(canvas, width, height, this.borderWidth);
                return;
            }
            PdfFont ufont = getFont();
            if (fontSize2 <= 0.0f) {
                fontSize2 = approximateFontSizeToFitSingleLine(ufont, new Rectangle(width, height), this.text, 0.1f);
            }
            canvas.beginText().setFontAndSize(ufont, fontSize2).resetFillColorRgb().setTextMatrix((width - ufont.getWidth(this.text, fontSize2)) / 2.0f, (height - ((float) ufont.getAscent(this.text, fontSize2))) / 2.0f).showText(this.text).endText();
        }
    }

    /* access modifiers changed from: protected */
    public void drawPdfACheckBox(PdfCanvas canvas, float width, float height, boolean on) {
        if (on) {
            switch (this.checkType) {
                case 1:
                    DrawingUtil.drawPdfACheck(canvas, width, height);
                    return;
                case 2:
                    DrawingUtil.drawPdfACircle(canvas, width, height);
                    return;
                case 3:
                    DrawingUtil.drawPdfACross(canvas, width, height);
                    return;
                case 4:
                    DrawingUtil.drawPdfADiamond(canvas, width, height);
                    return;
                case 5:
                    DrawingUtil.drawPdfASquare(canvas, width, height);
                    return;
                case 6:
                    DrawingUtil.drawPdfAStar(canvas, width, height);
                    return;
                default:
                    return;
            }
        }
    }

    private String getRadioButtonValue() {
        for (String state : getAppearanceStates()) {
            if (!"Off".equals(state)) {
                return state;
            }
        }
        return null;
    }

    private float getFontSize(PdfArray bBox, String value) {
        if (!isMultiline()) {
            float f = this.fontSize;
            if (f != 0.0f) {
                return f;
            }
            if (bBox == null || value == null || value.isEmpty()) {
                return 12.0f;
            }
            return approximateFontSizeToFitSingleLine(this.font, bBox.toRectangle(), value, 4.0f);
        }
        throw new AssertionError();
    }

    private float approximateFontSizeToFitMultiLine(Paragraph paragraph, Rectangle rect, IRenderer parentRenderer) {
        IRenderer renderer = paragraph.createRendererSubTree().setParent(parentRenderer);
        LayoutContext layoutContext = new LayoutContext(new LayoutArea(1, rect));
        float lFontSize = 4.0f;
        float rFontSize = 12.0f;
        paragraph.setFontSize(12.0f);
        if (renderer.layout(layoutContext).getStatus() == 1) {
            return 12.0f;
        }
        for (int i = 0; i < 6; i++) {
            float mFontSize = (lFontSize + rFontSize) / 2.0f;
            paragraph.setFontSize(mFontSize);
            if (renderer.layout(layoutContext).getStatus() == 1) {
                lFontSize = mFontSize;
            } else {
                rFontSize = mFontSize;
            }
        }
        return lFontSize;
    }

    private float approximateFontSizeToFitSingleLine(PdfFont localFont, Rectangle bBox, String value, float minValue) {
        float availableWidth;
        float height = bBox.getHeight() - (this.borderWidth * 2.0f);
        int[] fontBbox = localFont.getFontProgram().getFontMetrics().getBbox();
        float fs = (height / ((float) (fontBbox[2] - fontBbox[1]))) * 1000.0f;
        float baseWidth = localFont.getWidth(value, 1.0f);
        if (baseWidth != 0.0f) {
            float availableWidth2 = Math.max(bBox.getWidth() - (this.borderWidth * 2.0f), 0.0f);
            if (availableWidth2 * 0.15f < 4.0f) {
                availableWidth = availableWidth2 - ((availableWidth2 * 0.15f) * 2.0f);
            } else {
                availableWidth = availableWidth2 - (2.0f * 4.0f);
            }
            fs = Math.min(fs, availableWidth / baseWidth);
        }
        return Math.max(fs, minValue);
    }

    private float calculateTranslationHeightAfterFieldRot(Rectangle bBox, double pageRotation, double relFieldRotation) {
        if (relFieldRotation == 0.0d) {
            return 0.0f;
        }
        if (pageRotation == 0.0d) {
            if (relFieldRotation == 1.5707963267948966d) {
                return bBox.getHeight();
            }
            if (relFieldRotation == 3.141592653589793d) {
                return bBox.getHeight();
            }
        }
        if (pageRotation == -1.5707963267948966d) {
            if (relFieldRotation == -1.5707963267948966d) {
                return bBox.getWidth() - bBox.getHeight();
            }
            if (relFieldRotation == 1.5707963267948966d) {
                return bBox.getHeight();
            }
            if (relFieldRotation == 3.141592653589793d) {
                return bBox.getWidth();
            }
        }
        if (pageRotation == -3.141592653589793d) {
            if (relFieldRotation == -3.141592653589793d) {
                return bBox.getHeight();
            }
            if (relFieldRotation == -1.5707963267948966d) {
                return bBox.getHeight() - bBox.getWidth();
            }
            if (relFieldRotation == 1.5707963267948966d) {
                return bBox.getWidth();
            }
        }
        if (pageRotation == -4.71238898038469d) {
            if (relFieldRotation == -4.71238898038469d) {
                return bBox.getWidth();
            }
            if (relFieldRotation == -3.141592653589793d) {
                return bBox.getWidth();
            }
        }
        return 0.0f;
    }

    private float calculateTranslationWidthAfterFieldRot(Rectangle bBox, double pageRotation, double relFieldRotation) {
        if (relFieldRotation == 0.0d) {
            return 0.0f;
        }
        if (pageRotation == 0.0d && (relFieldRotation == 3.141592653589793d || relFieldRotation == 4.71238898038469d)) {
            return bBox.getWidth();
        }
        if (pageRotation == -1.5707963267948966d && (relFieldRotation == -1.5707963267948966d || relFieldRotation == 3.141592653589793d)) {
            return bBox.getHeight();
        }
        if (pageRotation == -3.141592653589793d) {
            if (relFieldRotation == -3.141592653589793d) {
                return bBox.getWidth();
            }
            if (relFieldRotation == -1.5707963267948966d) {
                return bBox.getHeight();
            }
            if (relFieldRotation == 1.5707963267948966d) {
                return (bBox.getHeight() - bBox.getWidth()) * -1.0f;
            }
        }
        if (pageRotation == -4.71238898038469d) {
            if (relFieldRotation == -4.71238898038469d) {
                return (bBox.getWidth() - bBox.getHeight()) * -1.0f;
            }
            if (relFieldRotation == -3.141592653589793d) {
                return bBox.getHeight();
            }
            if (relFieldRotation == -1.5707963267948966d) {
                return bBox.getWidth();
            }
        }
        return 0.0f;
    }

    private boolean hasDefaultAppearance() {
        PdfName type = getFormType();
        return type == PdfName.f1402Tx || type == PdfName.f1311Ch || (type == PdfName.Btn && (getFieldFlags() & PdfButtonFormField.FF_PUSH_BUTTON) != 0);
    }

    private PdfName getUniqueFontNameForDR(PdfDictionary fontResources) {
        int indexer = 1;
        Set<PdfName> fontNames = fontResources.keySet();
        while (true) {
            int indexer2 = indexer + 1;
            PdfName uniqueName = new PdfName("F" + indexer);
            if (!fontNames.contains(uniqueName)) {
                return uniqueName;
            }
            indexer = indexer2;
        }
    }

    private PdfName getFontNameFromDR(PdfDictionary fontResources, PdfObject font2) {
        for (Map.Entry<PdfName, PdfObject> drFont : fontResources.entrySet()) {
            if (drFont.getValue() == font2) {
                return drFont.getKey();
            }
        }
        return null;
    }

    private PdfObject getAcroFormObject(PdfName key, int type) {
        PdfObject acroFormObject = null;
        PdfDictionary acroFormDictionary = ((PdfDictionary) getDocument().getCatalog().getPdfObject()).getAsDictionary(PdfName.AcroForm);
        if (acroFormDictionary != null) {
            acroFormObject = acroFormDictionary.get(key);
        }
        if (acroFormObject == null || acroFormObject.getType() != type) {
            return null;
        }
        return acroFormObject;
    }

    private void putAcroFormObject(PdfName acroFormKey, PdfObject acroFormObject) {
        ((PdfDictionary) getDocument().getCatalog().getPdfObject()).getAsDictionary(PdfName.AcroForm).put(acroFormKey, acroFormObject);
    }

    private void addAcroFormToCatalog() {
        if (((PdfDictionary) getDocument().getCatalog().getPdfObject()).getAsDictionary(PdfName.AcroForm) == null) {
            PdfDictionary acroform = new PdfDictionary();
            acroform.makeIndirect(getDocument());
            acroform.put(PdfName.Fields, new PdfArray());
            getDocument().getCatalog().put(PdfName.AcroForm, acroform);
        }
    }

    private PdfObject getAcroFormKey(PdfName key, int type) {
        PdfDictionary acroFormDictionary;
        PdfObject acroFormKey = null;
        PdfDocument document = getDocument();
        if (!(document == null || (acroFormDictionary = ((PdfDictionary) document.getCatalog().getPdfObject()).getAsDictionary(PdfName.AcroForm)) == null)) {
            acroFormKey = acroFormDictionary.get(key);
        }
        if (acroFormKey == null || acroFormKey.getType() != type) {
            return null;
        }
        return acroFormKey;
    }

    private TextAlignment convertJustificationToTextAlignment() {
        Integer justification = getJustification();
        if (justification == null) {
            justification = 0;
        }
        TextAlignment textAlignment = TextAlignment.LEFT;
        if (justification.intValue() == 2) {
            return TextAlignment.RIGHT;
        }
        if (justification.intValue() == 1) {
            return TextAlignment.CENTER;
        }
        return textAlignment;
    }

    private PdfName getTypeFromParent(PdfDictionary field) {
        PdfDictionary parent = field.getAsDictionary(PdfName.Parent);
        PdfName formType = field.getAsName(PdfName.f1327FT);
        if (parent == null) {
            return formType;
        }
        PdfName formType2 = parent.getAsName(PdfName.f1327FT);
        if (formType2 == null) {
            return getTypeFromParent(parent);
        }
        return formType2;
    }

    private String obfuscatePassword(String text2) {
        char[] pchar = new char[text2.length()];
        for (int i = 0; i < text2.length(); i++) {
            pchar[i] = '*';
        }
        return new String(pchar);
    }

    private void applyRotation(PdfFormXObject xObject, float height, float width) {
        switch (this.rotation) {
            case 90:
                xObject.put(PdfName.Matrix, new PdfArray(new float[]{0.0f, 1.0f, -1.0f, 0.0f, height, 0.0f}));
                return;
            case 180:
                xObject.put(PdfName.Matrix, new PdfArray(new float[]{-1.0f, 0.0f, 0.0f, -1.0f, width, height}));
                return;
            case TIFFConstants.TIFFTAG_IMAGEDESCRIPTION:
                xObject.put(PdfName.Matrix, new PdfArray(new float[]{0.0f, -1.0f, 1.0f, 0.0f, 0.0f, width}));
                return;
            default:
                return;
        }
    }

    private PdfObject getValueFromAppearance(PdfObject appearanceDict, PdfName key) {
        if (appearanceDict instanceof PdfDictionary) {
            return ((PdfDictionary) appearanceDict).get(key);
        }
        return null;
    }

    private void retrieveStyles() {
        PdfDictionary appearanceCharacteristics;
        PdfName subType = ((PdfDictionary) getPdfObject()).getAsName(PdfName.Subtype);
        if (!(subType == null || !subType.equals(PdfName.Widget) || (appearanceCharacteristics = ((PdfDictionary) getPdfObject()).getAsDictionary(PdfName.f1353MK)) == null)) {
            this.backgroundColor = appearancePropToColor(appearanceCharacteristics, PdfName.f1296BG);
            Color extractedBorderColor = appearancePropToColor(appearanceCharacteristics, PdfName.f1294BC);
            if (extractedBorderColor != null) {
                this.borderColor = extractedBorderColor;
            }
        }
        PdfString defaultAppearance = getDefaultAppearance();
        if (defaultAppearance != null) {
            Object[] fontData = splitDAelements(defaultAppearance.getValue());
            if (!(fontData[1] == null || fontData[0] == null)) {
                this.color = (Color) fontData[2];
                this.fontSize = ((Float) fontData[1]).floatValue();
                this.font = resolveFontName((String) fontData[0]);
            }
        }
        updateFontAndFontSize(this.font, this.fontSize);
    }

    private PdfFont resolveFontName(String fontName) {
        PdfDictionary daFontDict;
        PdfDictionary defaultResources = (PdfDictionary) getAcroFormObject(PdfName.f1315DR, 3);
        PdfDictionary defaultFontDic = defaultResources != null ? defaultResources.getAsDictionary(PdfName.Font) : null;
        if (fontName == null || defaultFontDic == null || (daFontDict = defaultFontDic.getAsDictionary(new PdfName(fontName))) == null) {
            return null;
        }
        return getDocument().getFont(daFontDict);
    }

    private Color appearancePropToColor(PdfDictionary appearanceCharacteristics, PdfName property) {
        PdfArray colorData = appearanceCharacteristics.getAsArray(property);
        if (colorData != null) {
            float[] backgroundFloat = new float[colorData.size()];
            for (int i = 0; i < colorData.size(); i++) {
                backgroundFloat[i] = colorData.getAsNumber(i).floatValue();
            }
            switch (colorData.size()) {
                case 0:
                    return null;
                case 1:
                    return new DeviceGray(backgroundFloat[0]);
                case 3:
                    return new DeviceRgb(backgroundFloat[0], backgroundFloat[1], backgroundFloat[2]);
                case 4:
                    return new DeviceCmyk(backgroundFloat[0], backgroundFloat[1], backgroundFloat[2], backgroundFloat[3]);
            }
        }
        return null;
    }

    private void regeneratePushButtonField() {
        PdfDictionary widget = (PdfDictionary) getPdfObject();
        Rectangle rect = getRect(widget);
        PdfDictionary apDic = widget.getAsDictionary(PdfName.f1291AP);
        if (apDic == null) {
            PdfName pdfName = PdfName.f1291AP;
            PdfDictionary pdfDictionary = new PdfDictionary();
            apDic = pdfDictionary;
            put(pdfName, pdfDictionary);
        }
        apDic.put(PdfName.f1357N, drawPushButtonAppearance(rect.getWidth(), rect.getHeight(), this.text, this.font, getFontSize(widget.getAsArray(PdfName.Rect), this.text)).getPdfObject());
        if (this.pdfAConformanceLevel != null) {
            createPushButtonAppearanceState(widget);
        }
    }

    private void regenerateRadioButtonField() {
        Rectangle rect = getRect((PdfDictionary) getPdfObject());
        String value = getRadioButtonValue();
        if (rect != null && !"".equals(value)) {
            drawRadioAppearance(rect.getWidth(), rect.getHeight(), value);
        }
    }

    private void regenerateCheckboxField(String value) {
        Rectangle rect = getRect((PdfDictionary) getPdfObject());
        setCheckType(this.checkType);
        PdfWidgetAnnotation widget = (PdfWidgetAnnotation) PdfAnnotation.makeAnnotation(getPdfObject());
        String str = "Yes";
        if (this.pdfAConformanceLevel != null) {
            float width = rect.getWidth();
            float height = rect.getHeight();
            if (!"Off".equals(value)) {
                str = value;
            }
            drawPdfA2CheckAppearance(width, height, str, this.checkType);
            widget.setFlag(4);
        } else {
            float width2 = rect.getWidth();
            float height2 = rect.getHeight();
            if (!"Off".equals(value)) {
                str = value;
            }
            drawCheckAppearance(width2, height2, str);
        }
        if (widget.getNormalAppearanceObject() == null || !widget.getNormalAppearanceObject().containsKey(new PdfName(value))) {
            widget.setAppearanceState(new PdfName("Off"));
        } else {
            widget.setAppearanceState(new PdfName(value));
        }
    }

    private boolean regenerateTextAndChoiceField(String value, PdfName type) {
        int pageRotation;
        PdfArray matrix;
        PdfArray bBox;
        float fieldRotation;
        PdfFormField pdfFormField;
        int topIndex;
        double translationWidth;
        double translationWidth2;
        double translationHeight;
        String str = value;
        PdfPage page = PdfWidgetAnnotation.makeAnnotation(getPdfObject()).getPage();
        PdfArray bBox2 = ((PdfDictionary) getPdfObject()).getAsArray(PdfName.Rect);
        if (page != null) {
            pageRotation = page.getRotation() * -1;
        } else {
            pageRotation = 0;
        }
        if (pageRotation % 90 == 0) {
            double angle = degreeToRadians((double) (pageRotation % 360));
            Rectangle initialBboxRectangle = bBox2.toRectangle();
            Rectangle rect = initialBboxRectangle.clone();
            if (angle < -3.141592653589793d || angle > -1.5707963267948966d) {
                translationWidth = 0.0d;
            } else {
                translationWidth = (double) rect.getWidth();
            }
            if (angle <= -3.141592653589793d) {
                translationWidth2 = translationWidth;
                translationHeight = (double) rect.getHeight();
            } else {
                translationWidth2 = translationWidth;
                translationHeight = 0.0d;
            }
            double translationHeight2 = translationHeight;
            PdfArray matrix2 = new PdfArray(new double[]{Math.cos(angle), -Math.sin(angle), Math.sin(angle), Math.cos(angle), translationWidth2, translationHeight2});
            if (angle % 1.5707963267948966d == 0.0d && angle % 3.141592653589793d != 0.0d) {
                rect.setWidth(initialBboxRectangle.getHeight());
                rect.setHeight(initialBboxRectangle.getWidth());
            }
            rect.setX(rect.getX() + ((float) translationWidth2));
            double d = angle;
            rect.setY(rect.getY() + ((float) translationHeight2));
            bBox = new PdfArray(rect);
            matrix = matrix2;
        } else {
            LoggerFactory.getLogger((Class<?>) PdfFormField.class).error(LogMessageConstant.INCORRECT_PAGEROTATION);
            bBox = bBox2;
            matrix = new PdfArray(new double[]{1.0d, 0.0d, 0.0d, 1.0d, 0.0d, 0.0d});
        }
        if (((PdfDictionary) getPdfObject()).getAsDictionary(PdfName.f1353MK) == null || ((PdfDictionary) getPdfObject()).getAsDictionary(PdfName.f1353MK).get(PdfName.f1376R) == null) {
            fieldRotation = 0.0f;
        } else {
            fieldRotation = ((PdfDictionary) getPdfObject()).getAsDictionary(PdfName.f1353MK).getAsFloat(PdfName.f1376R).floatValue() + ((float) pageRotation);
        }
        if (fieldRotation % 90.0f == 0.0f) {
            Rectangle initialBboxRectangle2 = bBox.toRectangle();
            double angle2 = degreeToRadians((double) (fieldRotation % 360.0f));
            double translationWidth3 = (double) calculateTranslationWidthAfterFieldRot(initialBboxRectangle2, degreeToRadians((double) pageRotation), angle2);
            double translationHeight3 = (double) calculateTranslationHeightAfterFieldRot(initialBboxRectangle2, degreeToRadians((double) pageRotation), angle2);
            double translationWidth4 = translationWidth3;
            Matrix currentMatrix = new Matrix(matrix.getAsNumber(0).floatValue(), matrix.getAsNumber(1).floatValue(), matrix.getAsNumber(2).floatValue(), matrix.getAsNumber(3).floatValue(), matrix.getAsNumber(4).floatValue(), matrix.getAsNumber(5).floatValue()).multiply(new Matrix((float) Math.cos(angle2), (float) (-Math.sin(angle2)), (float) Math.sin(angle2), (float) Math.cos(angle2), (float) translationWidth4, (float) translationHeight3));
            matrix = new PdfArray(new float[]{currentMatrix.get(0), currentMatrix.get(1), currentMatrix.get(3), currentMatrix.get(4), currentMatrix.get(6), currentMatrix.get(7)});
            Rectangle rect2 = initialBboxRectangle2.clone();
            if (angle2 % 1.5707963267948966d == 0.0d && angle2 % 3.141592653589793d != 0.0d) {
                rect2.setWidth(initialBboxRectangle2.getHeight());
                rect2.setHeight(initialBboxRectangle2.getWidth());
            }
            rect2.setX(rect2.getX() + ((float) translationWidth4));
            rect2.setY(rect2.getY() + ((float) translationHeight3));
            bBox = new PdfArray(rect2);
        }
        Rectangle bboxRectangle = bBox.toRectangle();
        PdfFormXObject appearance = new PdfFormXObject(new Rectangle(0.0f, 0.0f, bboxRectangle.getWidth(), bboxRectangle.getHeight()));
        appearance.put(PdfName.Matrix, matrix);
        if (!PdfName.f1402Tx.equals(type)) {
            pdfFormField = this;
            String value2 = value;
            if (!pdfFormField.getFieldFlag(PdfChoiceFormField.FF_COMBO)) {
                PdfNumber topIndexNum = ((PdfDictionary) getPdfObject()).getAsNumber(PdfName.f1396TI);
                if (topIndexNum == null && getParent() != null) {
                    topIndexNum = getParent().getAsNumber(PdfName.f1396TI);
                }
                PdfArray options = getOptions();
                if (options == null && getParent() != null) {
                    options = getParent().getAsArray(PdfName.Opt);
                }
                if (options != null) {
                    int topIndex2 = topIndexNum != null ? topIndexNum.intValue() : 0;
                    topIndex = topIndex2;
                    value2 = optionsArrayToString(topIndex2 > 0 ? new PdfArray((List<? extends PdfObject>) options.subList(topIndex2, options.size())) : (PdfArray) options.clone());
                    drawChoiceAppearance(bboxRectangle, pdfFormField.getFontSize(bBox, value2), value2, appearance, topIndex);
                }
            }
            topIndex = 0;
            drawChoiceAppearance(bboxRectangle, pdfFormField.getFontSize(bBox, value2), value2, appearance, topIndex);
        } else if (isMultiline()) {
            pdfFormField = this;
            pdfFormField.drawMultiLineTextAppearance(bboxRectangle, pdfFormField.font, value, appearance);
        } else {
            pdfFormField = this;
            drawTextAppearance(bboxRectangle, pdfFormField.font, pdfFormField.getFontSize(bBox, value), value, appearance);
        }
        PdfDictionary ap = new PdfDictionary();
        ap.put(PdfName.f1357N, appearance.getPdfObject());
        ap.setModified();
        pdfFormField.put(PdfName.f1291AP, ap);
        return true;
    }

    private void copyParamsToKids(PdfFormField child) {
        int i = child.checkType;
        if (i <= 0 || i > 5) {
            child.checkType = this.checkType;
        }
        if (child.getDefaultAppearance() == null) {
            child.font = this.font;
            child.fontSize = this.fontSize;
        }
        if (child.color == null) {
            child.color = this.color;
        }
        if (child.text == null) {
            child.text = this.text;
        }
        if (child.img == null) {
            child.img = this.img;
        }
        if (child.borderWidth == 1.0f) {
            child.borderWidth = this.borderWidth;
        }
        if (child.backgroundColor == null) {
            child.backgroundColor = this.backgroundColor;
        }
        if (child.borderColor == null) {
            child.borderColor = this.borderColor;
        }
        if (child.rotation == 0) {
            child.rotation = this.rotation;
        }
        if (child.pdfAConformanceLevel == null) {
            child.pdfAConformanceLevel = this.pdfAConformanceLevel;
        }
        if (child.form == null) {
            child.form = this.form;
        }
    }

    private boolean regenerateWidget(String value) {
        PdfName type = getFormType();
        if (PdfName.f1402Tx.equals(type) || PdfName.f1311Ch.equals(type)) {
            return regenerateTextAndChoiceField(value, type);
        }
        if (!PdfName.Btn.equals(type)) {
            return false;
        }
        if (getFieldFlag(PdfButtonFormField.FF_PUSH_BUTTON)) {
            regeneratePushButtonField();
            return true;
        } else if (getFieldFlag(PdfButtonFormField.FF_RADIO)) {
            regenerateRadioButtonField();
            return true;
        } else {
            regenerateCheckboxField(value);
            return true;
        }
    }

    private static String optionsArrayToString(PdfArray options) {
        StringBuilder sb = new StringBuilder();
        Iterator<PdfObject> it = options.iterator();
        while (it.hasNext()) {
            PdfObject obj = it.next();
            if (obj.isString()) {
                sb.append(((PdfString) obj).toUnicodeString()).append(10);
            } else if (obj.isArray()) {
                PdfObject element = ((PdfArray) obj).get(1);
                if (element.isString()) {
                    sb.append(((PdfString) element).toUnicodeString()).append(10);
                }
            } else {
                sb.append(10);
            }
        }
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

    private static double degreeToRadians(double angle) {
        return (3.141592653589793d * angle) / 180.0d;
    }

    private static PdfString generateDefaultAppearance(PdfName font2, float fontSize2, Color textColor) {
        if (font2 != null) {
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            PdfOutputStream pdfStream = new PdfOutputStream(new OutputStream(output));
            byte[] g = {103};
            byte[] rg = {114, 103};
            byte[] k = {107};
            ((PdfOutputStream) ((PdfOutputStream) ((PdfOutputStream) pdfStream.write((PdfObject) font2).writeSpace()).writeFloat(fontSize2)).writeSpace()).writeBytes(new byte[]{84, 102});
            if (textColor != null) {
                if (textColor instanceof DeviceGray) {
                    ((PdfOutputStream) ((PdfOutputStream) ((PdfOutputStream) pdfStream.writeSpace()).writeFloats(textColor.getColorValue())).writeSpace()).writeBytes(g);
                } else if (textColor instanceof DeviceRgb) {
                    ((PdfOutputStream) ((PdfOutputStream) ((PdfOutputStream) pdfStream.writeSpace()).writeFloats(textColor.getColorValue())).writeSpace()).writeBytes(rg);
                } else if (textColor instanceof DeviceCmyk) {
                    ((PdfOutputStream) ((PdfOutputStream) ((PdfOutputStream) pdfStream.writeSpace()).writeFloats(textColor.getColorValue())).writeSpace()).writeBytes(k);
                } else {
                    LoggerFactory.getLogger((Class<?>) PdfFormField.class).error(LogMessageConstant.UNSUPPORTED_COLOR_IN_DA);
                }
            }
            return new PdfString(output.toByteArray());
        }
        throw new AssertionError();
    }

    private static boolean isWidgetAnnotation(PdfDictionary pdfObject) {
        return pdfObject != null && PdfName.Widget.equals(pdfObject.getAsName(PdfName.Subtype));
    }

    private static void createPushButtonAppearanceState(PdfDictionary widget) {
        PdfDictionary appearances = widget.getAsDictionary(PdfName.f1291AP);
        PdfStream normalAppearanceStream = appearances.getAsStream(PdfName.f1357N);
        if (normalAppearanceStream != null) {
            PdfName stateName = widget.getAsName(PdfName.f1292AS);
            if (stateName == null) {
                stateName = new PdfName("push");
            }
            widget.put(PdfName.f1292AS, stateName);
            PdfDictionary normalAppearance = new PdfDictionary();
            normalAppearance.put(stateName, normalAppearanceStream);
            appearances.put(PdfName.f1357N, normalAppearance);
        }
    }

    private static Paragraph createParagraphForTextFieldValue(String value) {
        Text text2 = new Text(value);
        text2.setNextRenderer(new FormFieldValueNonTrimmingTextRenderer(text2));
        return new Paragraph(text2);
    }
}
