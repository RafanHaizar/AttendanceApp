package com.itextpdf.kernel.pdf.annot;

import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfAnnotationBorder;
import com.itextpdf.kernel.pdf.PdfArray;
import com.itextpdf.kernel.pdf.PdfDictionary;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfIndirectReference;
import com.itextpdf.kernel.pdf.PdfName;
import com.itextpdf.kernel.pdf.PdfNumber;
import com.itextpdf.kernel.pdf.PdfObject;
import com.itextpdf.kernel.pdf.PdfObjectWrapper;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfString;
import com.itextpdf.kernel.pdf.filespec.PdfFileSpec;
import com.itextpdf.kernel.pdf.layer.IPdfOCG;
import com.itextpdf.p026io.LogMessageConstant;
import com.itextpdf.p026io.font.PdfEncodings;
import java.util.Iterator;
import org.slf4j.LoggerFactory;

public abstract class PdfAnnotation extends PdfObjectWrapper<PdfDictionary> {
    public static final PdfString Accepted = new PdfString("Accepted");
    public static final PdfString Canceled = new PdfString("Cancelled");
    public static final PdfString Completed = new PdfString("Completed");
    public static final int HIDDEN = 2;
    public static final PdfName HIGHLIGHT_INVERT = PdfName.f1339I;
    public static final PdfName HIGHLIGHT_NONE = PdfName.f1357N;
    public static final PdfName HIGHLIGHT_OUTLINE = PdfName.f1361O;
    public static final PdfName HIGHLIGHT_PUSH = PdfName.f1367P;
    public static final PdfName HIGHLIGHT_TOGGLE = PdfName.f1391T;
    public static final int INVISIBLE = 1;
    public static final int LOCKED = 128;
    public static final int LOCKED_CONTENTS = 512;
    public static final PdfString Marked = new PdfString("Marked");
    public static final PdfString MarkedModel = new PdfString("Marked");
    public static final int NO_ROTATE = 16;
    public static final int NO_VIEW = 32;
    public static final int NO_ZOOM = 8;
    public static final PdfString None = new PdfString("None");
    public static final int PRINT = 4;
    public static final int READ_ONLY = 64;
    public static final PdfString Rejected = new PdfString("Rejected");
    public static final PdfString ReviewModel = new PdfString("Review");
    public static final PdfName STYLE_BEVELED = PdfName.f1293B;
    public static final PdfName STYLE_DASHED = PdfName.f1312D;
    public static final PdfName STYLE_INSET = PdfName.f1339I;
    public static final PdfName STYLE_SOLID = PdfName.f1385S;
    public static final PdfName STYLE_UNDERLINE = PdfName.f1403U;
    public static final int TOGGLE_NO_VIEW = 256;
    public static final PdfString Unmarked = new PdfString("Unmarked");
    private static final long serialVersionUID = -6555705164241587799L;
    protected PdfPage page;

    public abstract PdfName getSubtype();

    public static PdfAnnotation makeAnnotation(PdfObject pdfObject) {
        if (pdfObject.isIndirectReference()) {
            pdfObject = ((PdfIndirectReference) pdfObject).getRefersTo();
        }
        if (!pdfObject.isDictionary()) {
            return null;
        }
        PdfName subtype = ((PdfDictionary) pdfObject).getAsName(PdfName.Subtype);
        if (PdfName.Link.equals(subtype)) {
            return new PdfLinkAnnotation((PdfDictionary) pdfObject);
        }
        if (PdfName.Popup.equals(subtype)) {
            return new PdfPopupAnnotation((PdfDictionary) pdfObject);
        }
        if (PdfName.Widget.equals(subtype)) {
            return new PdfWidgetAnnotation((PdfDictionary) pdfObject);
        }
        if (PdfName.Screen.equals(subtype)) {
            return new PdfScreenAnnotation((PdfDictionary) pdfObject);
        }
        if (PdfName._3D.equals(subtype)) {
            return new Pdf3DAnnotation((PdfDictionary) pdfObject);
        }
        if (PdfName.Highlight.equals(subtype) || PdfName.Underline.equals(subtype) || PdfName.Squiggly.equals(subtype) || PdfName.StrikeOut.equals(subtype)) {
            return new PdfTextMarkupAnnotation((PdfDictionary) pdfObject);
        }
        if (PdfName.Caret.equals(subtype)) {
            return new PdfCaretAnnotation((PdfDictionary) pdfObject);
        }
        if (PdfName.Text.equals(subtype)) {
            return new PdfTextAnnotation((PdfDictionary) pdfObject);
        }
        if (PdfName.Sound.equals(subtype)) {
            return new PdfSoundAnnotation((PdfDictionary) pdfObject);
        }
        if (PdfName.Stamp.equals(subtype)) {
            return new PdfStampAnnotation((PdfDictionary) pdfObject);
        }
        if (PdfName.FileAttachment.equals(subtype)) {
            return new PdfFileAttachmentAnnotation((PdfDictionary) pdfObject);
        }
        if (PdfName.Ink.equals(subtype)) {
            return new PdfInkAnnotation((PdfDictionary) pdfObject);
        }
        if (PdfName.PrinterMark.equals(subtype)) {
            return new PdfPrinterMarkAnnotation((PdfDictionary) pdfObject);
        }
        if (PdfName.TrapNet.equals(subtype)) {
            return new PdfTrapNetworkAnnotation((PdfDictionary) pdfObject);
        }
        if (PdfName.FreeText.equals(subtype)) {
            return new PdfFreeTextAnnotation((PdfDictionary) pdfObject);
        }
        if (PdfName.Square.equals(subtype)) {
            return new PdfSquareAnnotation((PdfDictionary) pdfObject);
        }
        if (PdfName.Circle.equals(subtype)) {
            return new PdfCircleAnnotation((PdfDictionary) pdfObject);
        }
        if (PdfName.Line.equals(subtype)) {
            return new PdfLineAnnotation((PdfDictionary) pdfObject);
        }
        if (PdfName.Polygon.equals(subtype)) {
            return new PdfPolygonAnnotation((PdfDictionary) pdfObject);
        }
        if (PdfName.PolyLine.equals(subtype)) {
            return new PdfPolylineAnnotation((PdfDictionary) pdfObject);
        }
        if (PdfName.Redact.equals(subtype)) {
            return new PdfRedactAnnotation((PdfDictionary) pdfObject);
        }
        if (PdfName.Watermark.equals(subtype)) {
            return new PdfWatermarkAnnotation((PdfDictionary) pdfObject);
        }
        return new PdfUnknownAnnotation((PdfDictionary) pdfObject);
    }

    protected PdfAnnotation(Rectangle rect) {
        this(new PdfDictionary());
        put(PdfName.Rect, new PdfArray(rect));
        put(PdfName.Subtype, getSubtype());
    }

    protected PdfAnnotation(PdfDictionary pdfObject) {
        super(pdfObject);
        markObjectAsIndirect(getPdfObject());
    }

    public void setLayer(IPdfOCG layer) {
        ((PdfDictionary) getPdfObject()).put(PdfName.f1362OC, layer.getIndirectReference());
    }

    public PdfString getContents() {
        return ((PdfDictionary) getPdfObject()).getAsString(PdfName.Contents);
    }

    public PdfAnnotation setContents(PdfString contents) {
        return put(PdfName.Contents, contents);
    }

    public PdfAnnotation setContents(String contents) {
        return setContents(new PdfString(contents, PdfEncodings.UNICODE_BIG));
    }

    public PdfDictionary getPageObject() {
        return ((PdfDictionary) getPdfObject()).getAsDictionary(PdfName.f1367P);
    }

    public PdfPage getPage() {
        if (this.page == null) {
            PdfIndirectReference indirectReference = ((PdfDictionary) getPdfObject()).getIndirectReference();
            PdfIndirectReference annotationIndirectReference = indirectReference;
            if (indirectReference != null) {
                PdfDocument doc = annotationIndirectReference.getDocument();
                PdfDictionary pageDictionary = getPageObject();
                if (pageDictionary != null) {
                    this.page = doc.getPage(pageDictionary);
                } else {
                    for (int i = 1; i <= doc.getNumberOfPages(); i++) {
                        PdfPage docPage = doc.getPage(i);
                        if (!docPage.isFlushed()) {
                            Iterator<PdfAnnotation> it = docPage.getAnnotations().iterator();
                            while (true) {
                                if (it.hasNext()) {
                                    if (annotationIndirectReference.equals(((PdfDictionary) it.next().getPdfObject()).getIndirectReference())) {
                                        this.page = docPage;
                                        break;
                                    }
                                } else {
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }
        return this.page;
    }

    public PdfAnnotation setPage(PdfPage page2) {
        this.page = page2;
        return put(PdfName.f1367P, ((PdfDictionary) page2.getPdfObject()).getIndirectReference());
    }

    public PdfString getName() {
        return ((PdfDictionary) getPdfObject()).getAsString(PdfName.f1359NM);
    }

    public PdfAnnotation setName(PdfString name) {
        return put(PdfName.f1359NM, name);
    }

    public PdfString getDate() {
        return ((PdfDictionary) getPdfObject()).getAsString(PdfName.f1352M);
    }

    public PdfAnnotation setDate(PdfString date) {
        return put(PdfName.f1352M, date);
    }

    public int getFlags() {
        PdfNumber f = ((PdfDictionary) getPdfObject()).getAsNumber(PdfName.f1324F);
        if (f != null) {
            return f.intValue();
        }
        return 0;
    }

    public PdfAnnotation setFlags(int flags) {
        return put(PdfName.f1324F, new PdfNumber(flags));
    }

    public PdfAnnotation setFlag(int flag) {
        return setFlags(getFlags() | flag);
    }

    public PdfAnnotation resetFlag(int flag) {
        return setFlags(getFlags() & (flag ^ -1));
    }

    public boolean hasFlag(int flag) {
        if (flag == 0) {
            return false;
        }
        if (((flag - 1) & flag) != 0) {
            throw new IllegalArgumentException("Only one flag must be checked at once.");
        } else if ((getFlags() & flag) != 0) {
            return true;
        } else {
            return false;
        }
    }

    public PdfDictionary getAppearanceDictionary() {
        return ((PdfDictionary) getPdfObject()).getAsDictionary(PdfName.f1291AP);
    }

    public PdfDictionary getAppearanceObject(PdfName appearanceType) {
        PdfDictionary ap = getAppearanceDictionary();
        if (ap == null) {
            return null;
        }
        PdfObject apObject = ap.get(appearanceType);
        if (apObject instanceof PdfDictionary) {
            return (PdfDictionary) apObject;
        }
        return null;
    }

    public PdfDictionary getNormalAppearanceObject() {
        return getAppearanceObject(PdfName.f1357N);
    }

    public PdfDictionary getRolloverAppearanceObject() {
        return getAppearanceObject(PdfName.f1376R);
    }

    public PdfDictionary getDownAppearanceObject() {
        return getAppearanceObject(PdfName.f1312D);
    }

    public PdfAnnotation setAppearance(PdfName appearanceType, PdfDictionary appearance) {
        PdfDictionary ap = getAppearanceDictionary();
        if (ap == null) {
            ap = new PdfDictionary();
            ((PdfDictionary) getPdfObject()).put(PdfName.f1291AP, ap);
        }
        ap.put(appearanceType, appearance);
        return this;
    }

    public PdfAnnotation setNormalAppearance(PdfDictionary appearance) {
        return setAppearance(PdfName.f1357N, appearance);
    }

    public PdfAnnotation setRolloverAppearance(PdfDictionary appearance) {
        return setAppearance(PdfName.f1376R, appearance);
    }

    public PdfAnnotation setDownAppearance(PdfDictionary appearance) {
        return setAppearance(PdfName.f1312D, appearance);
    }

    public PdfAnnotation setAppearance(PdfName appearanceType, PdfAnnotationAppearance appearance) {
        return setAppearance(appearanceType, (PdfDictionary) appearance.getPdfObject());
    }

    public PdfAnnotation setNormalAppearance(PdfAnnotationAppearance appearance) {
        return setAppearance(PdfName.f1357N, appearance);
    }

    public PdfAnnotation setRolloverAppearance(PdfAnnotationAppearance appearance) {
        return setAppearance(PdfName.f1376R, appearance);
    }

    public PdfAnnotation setDownAppearance(PdfAnnotationAppearance appearance) {
        return setAppearance(PdfName.f1312D, appearance);
    }

    public PdfName getAppearanceState() {
        return ((PdfDictionary) getPdfObject()).getAsName(PdfName.f1292AS);
    }

    public PdfAnnotation setAppearanceState(PdfName as) {
        return put(PdfName.f1292AS, as);
    }

    public PdfArray getBorder() {
        return ((PdfDictionary) getPdfObject()).getAsArray(PdfName.Border);
    }

    public PdfAnnotation setBorder(PdfAnnotationBorder border) {
        return put(PdfName.Border, border.getPdfObject());
    }

    public PdfAnnotation setBorder(PdfArray border) {
        return put(PdfName.Border, border);
    }

    public PdfArray getColorObject() {
        return ((PdfDictionary) getPdfObject()).getAsArray(PdfName.f1300C);
    }

    public PdfAnnotation setColor(PdfArray color) {
        return put(PdfName.f1300C, color);
    }

    public PdfAnnotation setColor(float[] color) {
        return setColor(new PdfArray(color));
    }

    public PdfAnnotation setColor(Color color) {
        return setColor(new PdfArray(color.getColorValue()));
    }

    public int getStructParentIndex() {
        PdfNumber n = ((PdfDictionary) getPdfObject()).getAsNumber(PdfName.StructParent);
        if (n == null) {
            return -1;
        }
        return n.intValue();
    }

    public PdfAnnotation setStructParentIndex(int structParentIndex) {
        return put(PdfName.StructParent, new PdfNumber(structParentIndex));
    }

    public PdfAnnotation setTitle(PdfString title) {
        return put(PdfName.f1391T, title);
    }

    public PdfString getTitle() {
        return ((PdfDictionary) getPdfObject()).getAsString(PdfName.f1391T);
    }

    public PdfAnnotation setRectangle(PdfArray array) {
        return put(PdfName.Rect, array);
    }

    public PdfArray getRectangle() {
        return ((PdfDictionary) getPdfObject()).getAsArray(PdfName.Rect);
    }

    public String getLang() {
        PdfString lang = ((PdfDictionary) getPdfObject()).getAsString(PdfName.Lang);
        if (lang != null) {
            return lang.toUnicodeString();
        }
        return null;
    }

    public PdfAnnotation setLang(String lang) {
        return put(PdfName.Lang, new PdfString(lang, PdfEncodings.UNICODE_BIG));
    }

    public PdfName getBlendMode() {
        return ((PdfDictionary) getPdfObject()).getAsName(PdfName.f1297BM);
    }

    public PdfAnnotation setBlendMode(PdfName blendMode) {
        return put(PdfName.f1297BM, blendMode);
    }

    public float getNonStrokingOpacity() {
        PdfNumber nonStrokingOpacity = ((PdfDictionary) getPdfObject()).getAsNumber(PdfName.f1417ca);
        if (nonStrokingOpacity != null) {
            return nonStrokingOpacity.floatValue();
        }
        return 1.0f;
    }

    public PdfAnnotation setNonStrokingOpacity(float nonStrokingOpacity) {
        return put(PdfName.f1417ca, new PdfNumber((double) nonStrokingOpacity));
    }

    public float getStrokingOpacity() {
        PdfNumber strokingOpacity = ((PdfDictionary) getPdfObject()).getAsNumber(PdfName.f1303CA);
        if (strokingOpacity != null) {
            return strokingOpacity.floatValue();
        }
        return 1.0f;
    }

    public PdfAnnotation setStrokingOpacity(float strokingOpacity) {
        return put(PdfName.f1303CA, new PdfNumber((double) strokingOpacity));
    }

    public PdfAnnotation put(PdfName key, PdfObject value) {
        ((PdfDictionary) getPdfObject()).put(key, value);
        setModified();
        return this;
    }

    public PdfAnnotation remove(PdfName key) {
        ((PdfDictionary) getPdfObject()).remove(key);
        return this;
    }

    public void addAssociatedFile(PdfFileSpec fs) {
        if (((PdfDictionary) fs.getPdfObject()).get(PdfName.AFRelationship) == null) {
            LoggerFactory.getLogger((Class<?>) PdfAnnotation.class).error(LogMessageConstant.ASSOCIATED_FILE_SPEC_SHALL_INCLUDE_AFRELATIONSHIP);
        }
        PdfArray afArray = ((PdfDictionary) getPdfObject()).getAsArray(PdfName.f1289AF);
        if (afArray == null) {
            afArray = new PdfArray();
            put(PdfName.f1289AF, afArray);
        }
        afArray.add(fs.getPdfObject());
    }

    public PdfArray getAssociatedFiles(boolean create) {
        PdfArray afArray = ((PdfDictionary) getPdfObject()).getAsArray(PdfName.f1289AF);
        if (afArray != null || !create) {
            return afArray;
        }
        PdfArray afArray2 = new PdfArray();
        put(PdfName.f1289AF, afArray2);
        return afArray2;
    }

    public void flush() {
        super.flush();
    }

    /* access modifiers changed from: protected */
    public boolean isWrappedObjectMustBeIndirect() {
        return true;
    }

    static class PdfUnknownAnnotation extends PdfAnnotation {
        protected PdfUnknownAnnotation(PdfDictionary pdfObject) {
            super(pdfObject);
        }

        public PdfName getSubtype() {
            return ((PdfDictionary) getPdfObject()).getAsName(PdfName.Subtype);
        }
    }
}
