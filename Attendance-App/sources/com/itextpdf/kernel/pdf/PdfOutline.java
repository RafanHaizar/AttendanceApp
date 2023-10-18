package com.itextpdf.kernel.pdf;

import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.pdf.action.PdfAction;
import com.itextpdf.kernel.pdf.navigation.PdfDestination;
import com.itextpdf.p026io.font.PdfEncodings;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PdfOutline implements Serializable {
    public static int FLAG_BOLD = 2;
    public static int FLAG_ITALIC = 1;
    private static final long serialVersionUID = 5730874960685950376L;
    private List<PdfOutline> children = new ArrayList();
    private PdfDictionary content;
    private PdfDestination destination;
    private PdfOutline parent;
    private PdfDocument pdfDoc;
    private String title;

    PdfOutline(String title2, PdfDictionary content2, PdfDocument pdfDocument) {
        this.title = title2;
        this.content = content2;
        this.pdfDoc = pdfDocument;
    }

    PdfOutline(String title2, PdfDictionary content2, PdfOutline parent2) {
        this.title = title2;
        this.content = content2;
        this.parent = parent2;
        this.pdfDoc = parent2.pdfDoc;
        content2.makeIndirect(parent2.pdfDoc);
    }

    PdfOutline(PdfDocument doc) {
        PdfDictionary pdfDictionary = new PdfDictionary();
        this.content = pdfDictionary;
        pdfDictionary.put(PdfName.Type, PdfName.Outlines);
        this.pdfDoc = doc;
        this.content.makeIndirect(doc);
        doc.getCatalog().addRootOutline(this);
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title2) {
        this.title = title2;
        this.content.put(PdfName.Title, new PdfString(title2, PdfEncodings.UNICODE_BIG));
    }

    public void setColor(Color color) {
        this.content.put(PdfName.f1300C, new PdfArray(color.getColorValue()));
    }

    public void setStyle(int style) {
        if (style == FLAG_BOLD || style == FLAG_ITALIC) {
            this.content.put(PdfName.f1324F, new PdfNumber(style));
        }
    }

    public PdfDictionary getContent() {
        return this.content;
    }

    public List<PdfOutline> getAllChildren() {
        return this.children;
    }

    public PdfOutline getParent() {
        return this.parent;
    }

    public PdfDestination getDestination() {
        return this.destination;
    }

    public void addDestination(PdfDestination destination2) {
        setDestination(destination2);
        this.content.put(PdfName.Dest, destination2.getPdfObject());
    }

    public void addAction(PdfAction action) {
        this.content.put(PdfName.f1287A, action.getPdfObject());
    }

    public void setOpen(boolean open) {
        if (!open) {
            this.content.put(PdfName.Count, new PdfNumber(-1));
        } else if (this.children.size() > 0) {
            this.content.put(PdfName.Count, new PdfNumber(this.children.size()));
        } else {
            this.content.remove(PdfName.Count);
        }
    }

    public PdfOutline addOutline(String title2, int position) {
        if (position == -1) {
            position = this.children.size();
        }
        PdfDictionary dictionary = new PdfDictionary();
        PdfOutline outline = new PdfOutline(title2, dictionary, this);
        dictionary.put(PdfName.Title, new PdfString(title2, PdfEncodings.UNICODE_BIG));
        dictionary.put(PdfName.Parent, this.content);
        if (this.children.size() > 0) {
            if (position != 0) {
                PdfDictionary prevContent = this.children.get(position - 1).getContent();
                dictionary.put(PdfName.Prev, prevContent);
                prevContent.put(PdfName.Next, dictionary);
            }
            if (position != this.children.size()) {
                PdfDictionary nextContent = this.children.get(position).getContent();
                dictionary.put(PdfName.Next, nextContent);
                nextContent.put(PdfName.Prev, dictionary);
            }
        }
        if (position == 0) {
            this.content.put(PdfName.First, dictionary);
        }
        if (position == this.children.size()) {
            this.content.put(PdfName.Last, dictionary);
        }
        PdfNumber count = this.content.getAsNumber(PdfName.Count);
        if (count == null || count.getValue() != -1.0d) {
            this.content.put(PdfName.Count, new PdfNumber(this.children.size() + 1));
        }
        this.children.add(position, outline);
        return outline;
    }

    public PdfOutline addOutline(String title2) {
        return addOutline(title2, -1);
    }

    public PdfOutline addOutline(PdfOutline outline) {
        PdfOutline newOutline = addOutline(outline.getTitle());
        newOutline.addDestination(outline.getDestination());
        for (PdfOutline child : outline.getAllChildren()) {
            newOutline.addOutline(child);
        }
        return newOutline;
    }

    public void removeOutline() {
        if (!this.pdfDoc.hasOutlines() || isOutlineRoot()) {
            this.pdfDoc.getCatalog().remove(PdfName.Outlines);
            return;
        }
        PdfOutline parent2 = this.parent;
        List<PdfOutline> children2 = parent2.children;
        children2.remove(this);
        PdfDictionary parentContent = parent2.content;
        if (children2.size() > 0) {
            parentContent.put(PdfName.First, children2.get(0).content);
            parentContent.put(PdfName.Last, children2.get(children2.size() - 1).content);
            PdfDictionary next = this.content.getAsDictionary(PdfName.Next);
            PdfDictionary prev = this.content.getAsDictionary(PdfName.Prev);
            if (prev != null) {
                if (next != null) {
                    prev.put(PdfName.Next, next);
                    next.put(PdfName.Prev, prev);
                    return;
                }
                prev.remove(PdfName.Next);
            } else if (next != null) {
                next.remove(PdfName.Prev);
            }
        } else {
            parent2.removeOutline();
        }
    }

    /* access modifiers changed from: package-private */
    public void clear() {
        this.children.clear();
    }

    /* access modifiers changed from: package-private */
    public void setDestination(PdfDestination destination2) {
        this.destination = destination2;
    }

    private PdfDictionary getOutlineRoot() {
        if (!this.pdfDoc.hasOutlines()) {
            return null;
        }
        return ((PdfDictionary) this.pdfDoc.getCatalog().getPdfObject()).getAsDictionary(PdfName.Outlines);
    }

    private boolean isOutlineRoot() {
        return getOutlineRoot() == this.content;
    }
}
