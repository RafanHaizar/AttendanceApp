package com.itextpdf.signatures;

import com.itextpdf.forms.PdfAcroForm;
import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfName;
import com.itextpdf.kernel.pdf.PdfStream;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.kernel.pdf.xobject.PdfFormXObject;
import com.itextpdf.layout.Canvas;
import com.itextpdf.layout.element.IBlockElement;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.layout.LayoutArea;
import com.itextpdf.layout.layout.LayoutContext;
import com.itextpdf.layout.renderer.IRenderer;
import com.itextpdf.p026io.image.ImageData;
import com.itextpdf.signatures.CertificateInfo;
import java.io.IOException;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.Calendar;

public class PdfSignatureAppearance {
    private static final float MARGIN = 2.0f;
    private static final float TOP_SECTION = 0.3f;
    private String contact = "";
    private PdfDocument document;
    private String fieldName;
    private ImageData image;
    private float imageScale;
    private PdfFont layer2Font;
    private Color layer2FontColor;
    private float layer2FontSize = 0.0f;
    private String layer2Text;
    private String location = "";
    private String locationCaption = "Location: ";

    /* renamed from: n0 */
    private PdfFormXObject f1605n0;

    /* renamed from: n2 */
    private PdfFormXObject f1606n2;
    private int page = 1;
    private Rectangle pageRect;
    private String reason = "";
    private String reasonCaption = "Reason: ";
    private Rectangle rect;
    private RenderingMode renderingMode = RenderingMode.DESCRIPTION;
    private boolean reuseAppearance = false;
    private Certificate signCertificate;
    private Calendar signDate;
    private String signatureCreator = "";
    private ImageData signatureGraphic = null;
    private PdfFormXObject topLayer;

    public enum RenderingMode {
        DESCRIPTION,
        NAME_AND_DESCRIPTION,
        GRAPHIC_AND_DESCRIPTION,
        GRAPHIC
    }

    protected PdfSignatureAppearance(PdfDocument document2, Rectangle pageRect2, int pageNumber) {
        this.document = document2;
        this.pageRect = new Rectangle(pageRect2);
        this.rect = new Rectangle(pageRect2.getWidth(), pageRect2.getHeight());
        this.page = pageNumber;
    }

    public int getPageNumber() {
        return this.page;
    }

    public PdfSignatureAppearance setPageNumber(int pageNumber) {
        this.page = pageNumber;
        setPageRect(this.pageRect);
        return this;
    }

    public Rectangle getPageRect() {
        return this.pageRect;
    }

    public PdfSignatureAppearance setPageRect(Rectangle pageRect2) {
        this.pageRect = new Rectangle(pageRect2);
        this.rect = new Rectangle(pageRect2.getWidth(), pageRect2.getHeight());
        return this;
    }

    public PdfFormXObject getLayer0() {
        if (this.f1605n0 == null) {
            PdfFormXObject pdfFormXObject = new PdfFormXObject(this.rect);
            this.f1605n0 = pdfFormXObject;
            pdfFormXObject.makeIndirect(this.document);
        }
        return this.f1605n0;
    }

    public PdfFormXObject getLayer2() {
        if (this.f1606n2 == null) {
            PdfFormXObject pdfFormXObject = new PdfFormXObject(this.rect);
            this.f1606n2 = pdfFormXObject;
            pdfFormXObject.makeIndirect(this.document);
        }
        return this.f1606n2;
    }

    public RenderingMode getRenderingMode() {
        return this.renderingMode;
    }

    public PdfSignatureAppearance setRenderingMode(RenderingMode renderingMode2) {
        this.renderingMode = renderingMode2;
        return this;
    }

    public String getReason() {
        return this.reason;
    }

    public PdfSignatureAppearance setReason(String reason2) {
        this.reason = reason2;
        return this;
    }

    public PdfSignatureAppearance setReasonCaption(String reasonCaption2) {
        this.reasonCaption = reasonCaption2;
        return this;
    }

    public String getLocation() {
        return this.location;
    }

    public PdfSignatureAppearance setLocation(String location2) {
        this.location = location2;
        return this;
    }

    public PdfSignatureAppearance setLocationCaption(String locationCaption2) {
        this.locationCaption = locationCaption2;
        return this;
    }

    public String getSignatureCreator() {
        return this.signatureCreator;
    }

    public PdfSignatureAppearance setSignatureCreator(String signatureCreator2) {
        this.signatureCreator = signatureCreator2;
        return this;
    }

    public String getContact() {
        return this.contact;
    }

    public PdfSignatureAppearance setContact(String contact2) {
        this.contact = contact2;
        return this;
    }

    public PdfSignatureAppearance setCertificate(Certificate signCertificate2) {
        this.signCertificate = signCertificate2;
        return this;
    }

    public Certificate getCertificate() {
        return this.signCertificate;
    }

    public ImageData getSignatureGraphic() {
        return this.signatureGraphic;
    }

    public PdfSignatureAppearance setSignatureGraphic(ImageData signatureGraphic2) {
        this.signatureGraphic = signatureGraphic2;
        return this;
    }

    public PdfSignatureAppearance setReuseAppearance(boolean reuseAppearance2) {
        this.reuseAppearance = reuseAppearance2;
        return this;
    }

    public ImageData getImage() {
        return this.image;
    }

    public PdfSignatureAppearance setImage(ImageData image2) {
        this.image = image2;
        return this;
    }

    public float getImageScale() {
        return this.imageScale;
    }

    public PdfSignatureAppearance setImageScale(float imageScale2) {
        this.imageScale = imageScale2;
        return this;
    }

    public PdfSignatureAppearance setLayer2Text(String text) {
        this.layer2Text = text;
        return this;
    }

    public String getLayer2Text() {
        return this.layer2Text;
    }

    public PdfFont getLayer2Font() {
        return this.layer2Font;
    }

    public PdfSignatureAppearance setLayer2Font(PdfFont layer2Font2) {
        this.layer2Font = layer2Font2;
        return this;
    }

    public PdfSignatureAppearance setLayer2FontSize(float fontSize) {
        this.layer2FontSize = fontSize;
        return this;
    }

    public float getLayer2FontSize() {
        return this.layer2FontSize;
    }

    public PdfSignatureAppearance setLayer2FontColor(Color color) {
        this.layer2FontColor = color;
        return this;
    }

    public Color getLayer2FontColor() {
        return this.layer2FontColor;
    }

    public boolean isInvisible() {
        Rectangle rectangle = this.rect;
        return rectangle == null || rectangle.getWidth() == 0.0f || this.rect.getHeight() == 0.0f;
    }

    /* access modifiers changed from: protected */
    public PdfFormXObject getAppearance() throws IOException {
        String text;
        PdfFont font;
        if (isInvisible()) {
            PdfFormXObject appearance = new PdfFormXObject(new Rectangle(0.0f, 0.0f));
            appearance.makeIndirect(this.document);
            return appearance;
        }
        if (this.f1605n0 == null && !this.reuseAppearance) {
            createBlankN0();
        }
        if (this.f1606n2 == null) {
            PdfFormXObject pdfFormXObject = new PdfFormXObject(this.rect);
            this.f1606n2 = pdfFormXObject;
            pdfFormXObject.makeIndirect(this.document);
            PdfCanvas canvas = new PdfCanvas(this.f1606n2, this.document);
            int rotation = this.document.getPage(this.page).getRotation();
            if (rotation == 90) {
                canvas.concatMatrix(0.0d, 1.0d, -1.0d, 0.0d, (double) this.rect.getWidth(), 0.0d);
            } else if (rotation == 180) {
                canvas.concatMatrix(-1.0d, 0.0d, 0.0d, -1.0d, (double) this.rect.getWidth(), (double) this.rect.getHeight());
            } else if (rotation == 270) {
                canvas.concatMatrix(0.0d, -1.0d, 1.0d, 0.0d, 0.0d, (double) this.rect.getHeight());
            }
            Rectangle rotatedRect = rotateRectangle(this.rect, this.document.getPage(this.page).getRotation());
            if (this.layer2Text == null) {
                StringBuilder buf = new StringBuilder();
                buf.append("Digitally signed by ");
                String name = null;
                CertificateInfo.X500Name x500name = CertificateInfo.getSubjectFields((X509Certificate) this.signCertificate);
                if (x500name != null && (name = x500name.getField("CN")) == null) {
                    name = x500name.getField("E");
                }
                if (name == null) {
                    name = "";
                }
                buf.append(name).append(10);
                buf.append("Date: ").append(SignUtils.dateToString(this.signDate));
                if (this.reason != null) {
                    buf.append(10).append(this.reasonCaption).append(this.reason);
                }
                if (this.location != null) {
                    buf.append(10).append(this.locationCaption).append(this.location);
                }
                text = buf.toString();
            } else {
                text = this.layer2Text;
            }
            if (this.image != null) {
                float f = this.imageScale;
                if (f == 0.0f) {
                    new PdfCanvas(this.f1606n2, this.document).addImage(this.image, rotatedRect.getWidth(), 0.0f, 0.0f, rotatedRect.getHeight(), 0.0f, 0.0f);
                } else {
                    float usableScale = this.imageScale;
                    if (f < 0.0f) {
                        usableScale = Math.min(rotatedRect.getWidth() / this.image.getWidth(), rotatedRect.getHeight() / this.image.getHeight());
                    }
                    float w = this.image.getWidth() * usableScale;
                    float h = this.image.getHeight() * usableScale;
                    float x = (rotatedRect.getWidth() - w) / 2.0f;
                    float y = (rotatedRect.getHeight() - h) / 2.0f;
                    PdfCanvas canvas2 = new PdfCanvas(this.f1606n2, this.document);
                    canvas2.addImage(this.image, w, 0.0f, 0.0f, h, x, y);
                    PdfCanvas pdfCanvas = canvas2;
                }
            } else {
                PdfCanvas pdfCanvas2 = canvas;
            }
            if (this.layer2Font == null) {
                font = PdfFontFactory.createFont();
            } else {
                font = this.layer2Font;
            }
            Rectangle dataRect = null;
            Rectangle signatureRect = null;
            if (this.renderingMode == RenderingMode.NAME_AND_DESCRIPTION || (this.renderingMode == RenderingMode.GRAPHIC_AND_DESCRIPTION && this.signatureGraphic != null)) {
                if (rotatedRect.getHeight() > rotatedRect.getWidth()) {
                    signatureRect = new Rectangle(2.0f, rotatedRect.getHeight() / 2.0f, rotatedRect.getWidth() - 4.0f, rotatedRect.getHeight() / 2.0f);
                    dataRect = new Rectangle(2.0f, 2.0f, rotatedRect.getWidth() - 4.0f, (rotatedRect.getHeight() / 2.0f) - 4.0f);
                } else {
                    signatureRect = new Rectangle(2.0f, 2.0f, (rotatedRect.getWidth() / 2.0f) - 4.0f, rotatedRect.getHeight() - 4.0f);
                    dataRect = new Rectangle((rotatedRect.getWidth() / 2.0f) + 1.0f, 2.0f, (rotatedRect.getWidth() / 2.0f) - 2.0f, rotatedRect.getHeight() - 4.0f);
                }
            } else if (this.renderingMode != RenderingMode.GRAPHIC) {
                dataRect = new Rectangle(2.0f, 2.0f, rotatedRect.getWidth() - 4.0f, (rotatedRect.getHeight() * 0.7f) - 4.0f);
            } else if (this.signatureGraphic != null) {
                signatureRect = new Rectangle(2.0f, 2.0f, rotatedRect.getWidth() - 4.0f, rotatedRect.getHeight() - 4.0f);
            } else {
                throw new IllegalStateException("A signature image must be present when rendering mode is graphic. Use setSignatureGraphic()");
            }
            switch (C14821.f1607xdf7d1079[this.renderingMode.ordinal()]) {
                case 1:
                    String signedBy = CertificateInfo.getSubjectFields((X509Certificate) this.signCertificate).getField("CN");
                    if (signedBy == null) {
                        signedBy = CertificateInfo.getSubjectFields((X509Certificate) this.signCertificate).getField("E");
                    }
                    if (signedBy == null) {
                        signedBy = "";
                    }
                    addTextToCanvas(signedBy, font, signatureRect);
                    break;
                case 2:
                    ImageData imageData = this.signatureGraphic;
                    if (imageData != null) {
                        float imgWidth = imageData.getWidth();
                        if (imgWidth == 0.0f) {
                            imgWidth = signatureRect.getWidth();
                        }
                        float imgHeight = this.signatureGraphic.getHeight();
                        if (imgHeight == 0.0f) {
                            imgHeight = signatureRect.getHeight();
                        }
                        float multiplier = Math.min(signatureRect.getWidth() / this.signatureGraphic.getWidth(), signatureRect.getHeight() / this.signatureGraphic.getHeight());
                        float imgWidth2 = imgWidth * multiplier;
                        float imgHeight2 = imgHeight * multiplier;
                        float y2 = signatureRect.getBottom() + ((signatureRect.getHeight() - imgHeight2) / 2.0f);
                        PdfCanvas canvas3 = new PdfCanvas(this.f1606n2, this.document);
                        canvas3.addImage(this.signatureGraphic, imgWidth2, 0.0f, 0.0f, imgHeight2, signatureRect.getRight() - imgWidth2, y2);
                        PdfCanvas pdfCanvas3 = canvas3;
                        break;
                    } else {
                        throw new IllegalStateException("A signature image must be present when rendering mode is graphic and description. Use setSignatureGraphic()");
                    }
                case 3:
                    float imgWidth3 = this.signatureGraphic.getWidth();
                    if (imgWidth3 == 0.0f) {
                        imgWidth3 = signatureRect.getWidth();
                    }
                    float imgHeight3 = this.signatureGraphic.getHeight();
                    if (imgHeight3 == 0.0f) {
                        imgHeight3 = signatureRect.getHeight();
                    }
                    float multiplier2 = Math.min(signatureRect.getWidth() / this.signatureGraphic.getWidth(), signatureRect.getHeight() / this.signatureGraphic.getHeight());
                    float imgWidth4 = imgWidth3 * multiplier2;
                    float imgHeight4 = imgHeight3 * multiplier2;
                    float x2 = signatureRect.getLeft() + ((signatureRect.getWidth() - imgWidth4) / 2.0f);
                    float y3 = signatureRect.getBottom() + ((signatureRect.getHeight() - imgHeight4) / 2.0f);
                    int i = rotation;
                    PdfCanvas canvas4 = new PdfCanvas(this.f1606n2, this.document);
                    canvas4.addImage(this.signatureGraphic, imgWidth4, 0.0f, 0.0f, imgHeight4, x2, y3);
                    PdfCanvas pdfCanvas4 = canvas4;
                    break;
                default:
                    int i2 = rotation;
                    break;
            }
            if (this.renderingMode != RenderingMode.GRAPHIC) {
                addTextToCanvas(text, font, dataRect);
            }
        }
        Rectangle rotated = new Rectangle(this.rect);
        if (this.topLayer == null) {
            PdfFormXObject pdfFormXObject2 = new PdfFormXObject(rotated);
            this.topLayer = pdfFormXObject2;
            pdfFormXObject2.makeIndirect(this.document);
            if (this.reuseAppearance) {
                PdfStream stream = PdfAcroForm.getAcroForm(this.document, true).getField(this.fieldName).getWidgets().get(0).getAppearanceDictionary().getAsStream(PdfName.f1357N);
                PdfFormXObject xobj = new PdfFormXObject(stream);
                if (stream != null) {
                    this.topLayer.getResources().addForm(xobj, new PdfName("n0"));
                    new PdfCanvas(this.topLayer, this.document).addXObject(xobj, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f);
                } else {
                    this.reuseAppearance = false;
                    if (this.f1605n0 == null) {
                        createBlankN0();
                    }
                }
            }
            if (!this.reuseAppearance) {
                this.topLayer.getResources().addForm(this.f1605n0, new PdfName("n0"));
                new PdfCanvas(this.topLayer, this.document).addXObject(this.f1605n0, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f);
            }
            this.topLayer.getResources().addForm(this.f1606n2, new PdfName("n2"));
            new PdfCanvas(this.topLayer, this.document).addXObject(this.f1606n2, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f);
        }
        PdfFormXObject napp = new PdfFormXObject(rotated);
        napp.makeIndirect(this.document);
        napp.getResources().addForm(this.topLayer, new PdfName("FRM"));
        new PdfCanvas(napp, this.document).addXObject(this.topLayer, 0.0f, 0.0f);
        return napp;
    }

    /* renamed from: com.itextpdf.signatures.PdfSignatureAppearance$1 */
    static /* synthetic */ class C14821 {

        /* renamed from: $SwitchMap$com$itextpdf$signatures$PdfSignatureAppearance$RenderingMode */
        static final /* synthetic */ int[] f1607xdf7d1079;

        static {
            int[] iArr = new int[RenderingMode.values().length];
            f1607xdf7d1079 = iArr;
            try {
                iArr[RenderingMode.NAME_AND_DESCRIPTION.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                f1607xdf7d1079[RenderingMode.GRAPHIC_AND_DESCRIPTION.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                f1607xdf7d1079[RenderingMode.GRAPHIC.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
        }
    }

    /* access modifiers changed from: protected */
    public Calendar getSignDate() {
        return this.signDate;
    }

    /* access modifiers changed from: protected */
    public PdfSignatureAppearance setSignDate(Calendar signDate2) {
        this.signDate = signDate2;
        return this;
    }

    /* access modifiers changed from: protected */
    public PdfSignatureAppearance setFieldName(String fieldName2) {
        this.fieldName = fieldName2;
        return this;
    }

    private static Rectangle rotateRectangle(Rectangle rect2, int angle) {
        if ((angle / 90) % 2 == 0) {
            return new Rectangle(rect2.getWidth(), rect2.getHeight());
        }
        return new Rectangle(rect2.getHeight(), rect2.getWidth());
    }

    private void createBlankN0() {
        PdfFormXObject pdfFormXObject = new PdfFormXObject(new Rectangle(100.0f, 100.0f));
        this.f1605n0 = pdfFormXObject;
        pdfFormXObject.makeIndirect(this.document);
        new PdfCanvas(this.f1605n0, this.document).writeLiteral("% DSBlank\n");
    }

    private void addTextToCanvas(String text, PdfFont font, Rectangle dataRect) {
        PdfCanvas canvas = new PdfCanvas(this.f1606n2, this.document);
        Paragraph paragraph = ((Paragraph) ((Paragraph) new Paragraph(text).setFont(font)).setMargin(0.0f)).setMultipliedLeading(0.9f);
        Canvas layoutCanvas = new Canvas(canvas, dataRect);
        paragraph.setFontColor(this.layer2FontColor);
        float f = this.layer2FontSize;
        if (f == 0.0f) {
            applyCopyFittingFontSize(paragraph, dataRect, layoutCanvas.getRenderer());
        } else {
            paragraph.setFontSize(f);
        }
        layoutCanvas.add((IBlockElement) paragraph);
    }

    private void applyCopyFittingFontSize(Paragraph paragraph, Rectangle rect2, IRenderer parentRenderer) {
        IRenderer renderer = paragraph.createRendererSubTree().setParent(parentRenderer);
        LayoutContext layoutContext = new LayoutContext(new LayoutArea(1, rect2));
        float lFontSize = 0.1f;
        float rFontSize = 100.0f;
        for (int i = 0; i < 15; i++) {
            float mFontSize = (lFontSize + rFontSize) / 2.0f;
            paragraph.setFontSize(mFontSize);
            if (renderer.layout(layoutContext).getStatus() == 1) {
                lFontSize = mFontSize;
            } else {
                rFontSize = mFontSize;
            }
        }
        paragraph.setFontSize(lFontSize);
    }
}
