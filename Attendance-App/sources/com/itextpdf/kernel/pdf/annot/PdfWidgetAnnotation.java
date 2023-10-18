package com.itextpdf.kernel.pdf.annot;

import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfArray;
import com.itextpdf.kernel.pdf.PdfDictionary;
import com.itextpdf.kernel.pdf.PdfName;
import com.itextpdf.kernel.pdf.PdfNumber;
import com.itextpdf.kernel.pdf.PdfObject;
import com.itextpdf.kernel.pdf.action.PdfAction;
import java.util.HashSet;
import java.util.Iterator;

public class PdfWidgetAnnotation extends PdfAnnotation {
    public static final int HIDDEN = 1;
    public static final int HIDDEN_BUT_PRINTABLE = 3;
    public static final int VISIBLE = 4;
    public static final int VISIBLE_BUT_DOES_NOT_PRINT = 2;
    private static final long serialVersionUID = 9013938639824707088L;
    private HashSet<PdfName> widgetEntries;

    public PdfWidgetAnnotation(Rectangle rect) {
        super(rect);
        HashSet<PdfName> hashSet = new HashSet<>();
        this.widgetEntries = hashSet;
        hashSet.add(PdfName.Subtype);
        this.widgetEntries.add(PdfName.Type);
        this.widgetEntries.add(PdfName.Rect);
        this.widgetEntries.add(PdfName.Contents);
        this.widgetEntries.add(PdfName.f1367P);
        this.widgetEntries.add(PdfName.f1359NM);
        this.widgetEntries.add(PdfName.f1352M);
        this.widgetEntries.add(PdfName.f1324F);
        this.widgetEntries.add(PdfName.f1291AP);
        this.widgetEntries.add(PdfName.f1292AS);
        this.widgetEntries.add(PdfName.Border);
        this.widgetEntries.add(PdfName.f1300C);
        this.widgetEntries.add(PdfName.StructParent);
        this.widgetEntries.add(PdfName.f1362OC);
        this.widgetEntries.add(PdfName.f1331H);
        this.widgetEntries.add(PdfName.f1353MK);
        this.widgetEntries.add(PdfName.f1287A);
        this.widgetEntries.add(PdfName.f1288AA);
        this.widgetEntries.add(PdfName.f1298BS);
    }

    protected PdfWidgetAnnotation(PdfDictionary pdfObject) {
        super(pdfObject);
        HashSet<PdfName> hashSet = new HashSet<>();
        this.widgetEntries = hashSet;
        hashSet.add(PdfName.Subtype);
        this.widgetEntries.add(PdfName.Type);
        this.widgetEntries.add(PdfName.Rect);
        this.widgetEntries.add(PdfName.Contents);
        this.widgetEntries.add(PdfName.f1367P);
        this.widgetEntries.add(PdfName.f1359NM);
        this.widgetEntries.add(PdfName.f1352M);
        this.widgetEntries.add(PdfName.f1324F);
        this.widgetEntries.add(PdfName.f1291AP);
        this.widgetEntries.add(PdfName.f1292AS);
        this.widgetEntries.add(PdfName.Border);
        this.widgetEntries.add(PdfName.f1300C);
        this.widgetEntries.add(PdfName.StructParent);
        this.widgetEntries.add(PdfName.f1362OC);
        this.widgetEntries.add(PdfName.f1331H);
        this.widgetEntries.add(PdfName.f1353MK);
        this.widgetEntries.add(PdfName.f1287A);
        this.widgetEntries.add(PdfName.f1288AA);
        this.widgetEntries.add(PdfName.f1298BS);
    }

    public PdfName getSubtype() {
        return PdfName.Widget;
    }

    public PdfWidgetAnnotation setParent(PdfObject parent) {
        return (PdfWidgetAnnotation) put(PdfName.Parent, parent);
    }

    public PdfWidgetAnnotation setHighlightMode(PdfName mode) {
        return (PdfWidgetAnnotation) put(PdfName.f1331H, mode);
    }

    public PdfName getHighlightMode() {
        return ((PdfDictionary) getPdfObject()).getAsName(PdfName.f1331H);
    }

    public void releaseFormFieldFromWidgetAnnotation() {
        PdfDictionary annotDict = (PdfDictionary) getPdfObject();
        Iterator<PdfName> it = this.widgetEntries.iterator();
        while (it.hasNext()) {
            annotDict.remove(it.next());
        }
        PdfDictionary parent = annotDict.getAsDictionary(PdfName.Parent);
        if (parent != null && annotDict.size() == 1) {
            PdfArray kids = parent.getAsArray(PdfName.Kids);
            kids.remove((PdfObject) annotDict);
            if (kids.size() == 0) {
                parent.remove(PdfName.Kids);
            }
        }
    }

    public PdfWidgetAnnotation setVisibility(int visibility) {
        switch (visibility) {
            case 1:
                ((PdfDictionary) getPdfObject()).put(PdfName.f1324F, new PdfNumber(6));
                break;
            case 2:
                break;
            case 3:
                ((PdfDictionary) getPdfObject()).put(PdfName.f1324F, new PdfNumber(36));
                break;
            default:
                ((PdfDictionary) getPdfObject()).put(PdfName.f1324F, new PdfNumber(4));
                break;
        }
        return this;
    }

    public PdfDictionary getAction() {
        return ((PdfDictionary) getPdfObject()).getAsDictionary(PdfName.f1287A);
    }

    public PdfWidgetAnnotation setAction(PdfAction action) {
        return (PdfWidgetAnnotation) put(PdfName.f1287A, action.getPdfObject());
    }

    public PdfDictionary getAdditionalAction() {
        return ((PdfDictionary) getPdfObject()).getAsDictionary(PdfName.f1288AA);
    }

    public PdfWidgetAnnotation setAdditionalAction(PdfName key, PdfAction action) {
        PdfAction.setAdditionalAction(this, key, action);
        return this;
    }

    public PdfDictionary getAppearanceCharacteristics() {
        return ((PdfDictionary) getPdfObject()).getAsDictionary(PdfName.f1353MK);
    }

    public PdfWidgetAnnotation setAppearanceCharacteristics(PdfDictionary characteristics) {
        return (PdfWidgetAnnotation) put(PdfName.f1353MK, characteristics);
    }

    public PdfDictionary getBorderStyle() {
        return ((PdfDictionary) getPdfObject()).getAsDictionary(PdfName.f1298BS);
    }

    public PdfWidgetAnnotation setBorderStyle(PdfDictionary borderStyle) {
        return (PdfWidgetAnnotation) put(PdfName.f1298BS, borderStyle);
    }

    public PdfWidgetAnnotation setBorderStyle(PdfName style) {
        return setBorderStyle(BorderStyleUtil.setStyle(getBorderStyle(), style));
    }

    public PdfWidgetAnnotation setDashPattern(PdfArray dashPattern) {
        return setBorderStyle(BorderStyleUtil.setDashPattern(getBorderStyle(), dashPattern));
    }
}
