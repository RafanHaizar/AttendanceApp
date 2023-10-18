package com.itextpdf.kernel.pdf.layer;

import com.itextpdf.kernel.pdf.PdfArray;
import com.itextpdf.kernel.pdf.PdfDictionary;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfIndirectReference;
import com.itextpdf.kernel.pdf.PdfName;
import com.itextpdf.kernel.pdf.PdfNumber;
import com.itextpdf.kernel.pdf.PdfObject;
import com.itextpdf.kernel.pdf.PdfObjectWrapper;
import com.itextpdf.kernel.pdf.PdfString;
import com.itextpdf.p026io.font.PdfEncodings;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class PdfLayer extends PdfObjectWrapper<PdfDictionary> implements IPdfOCG {
    private static final long serialVersionUID = -5367953708241595665L;
    protected List<PdfLayer> children;
    protected boolean locked;

    /* renamed from: on */
    protected boolean f1500on;
    protected boolean onPanel;
    protected PdfLayer parent;
    protected String title;

    public /* bridge */ /* synthetic */ PdfDictionary getPdfObject() {
        return (PdfDictionary) super.getPdfObject();
    }

    public PdfLayer(PdfDictionary layerDictionary) {
        super(layerDictionary);
        this.f1500on = true;
        this.onPanel = true;
        this.locked = false;
        setForbidRelease();
        ensureObjectIsAddedToDocument(layerDictionary);
    }

    public PdfLayer(String name, PdfDocument document) {
        this(document);
        setName(name);
        document.getCatalog().getOCProperties(true).registerLayer(this);
    }

    private PdfLayer(PdfDocument document) {
        super(new PdfDictionary());
        this.f1500on = true;
        this.onPanel = true;
        this.locked = false;
        makeIndirect(document);
        ((PdfDictionary) getPdfObject()).put(PdfName.Type, PdfName.OCG);
    }

    public static PdfLayer createTitle(String title2, PdfDocument document) {
        PdfLayer layer = createTitleSilent(title2, document);
        document.getCatalog().getOCProperties(true).registerLayer(layer);
        return layer;
    }

    public static void addOCGRadioGroup(PdfDocument document, List<PdfLayer> group) {
        document.getCatalog().getOCProperties(true).addOCGRadioGroup(group);
    }

    public void addChild(PdfLayer childLayer) {
        if (childLayer.parent == null) {
            childLayer.parent = this;
            if (this.children == null) {
                this.children = new ArrayList();
            }
            this.children.add(childLayer);
            return;
        }
        throw new IllegalArgumentException("Illegal argument: childLayer");
    }

    public PdfLayer getParent() {
        return this.parent;
    }

    public void setName(String name) {
        ((PdfDictionary) getPdfObject()).put(PdfName.Name, new PdfString(name, PdfEncodings.UNICODE_BIG));
        ((PdfDictionary) getPdfObject()).setModified();
    }

    public boolean isOn() {
        return this.f1500on;
    }

    public void setOn(boolean on) {
        if (this.f1500on != on) {
            fetchOCProperties().setModified();
        }
        this.f1500on = on;
    }

    public boolean isLocked() {
        return this.locked;
    }

    public void setLocked(boolean locked2) {
        if (isLocked() != locked2) {
            fetchOCProperties().setModified();
        }
        this.locked = locked2;
    }

    public boolean isOnPanel() {
        return this.onPanel;
    }

    public void setOnPanel(boolean onPanel2) {
        if (this.f1500on != onPanel2) {
            fetchOCProperties().setModified();
        }
        this.onPanel = onPanel2;
    }

    public Collection<PdfName> getIntents() {
        PdfObject intent = ((PdfDictionary) getPdfObject()).get(PdfName.Intent);
        if (intent instanceof PdfName) {
            return Collections.singletonList((PdfName) intent);
        }
        if (!(intent instanceof PdfArray)) {
            return Collections.singletonList(PdfName.View);
        }
        PdfArray intentArr = (PdfArray) intent;
        Collection<PdfName> intentsCollection = new ArrayList<>(intentArr.size());
        Iterator<PdfObject> it = intentArr.iterator();
        while (it.hasNext()) {
            PdfObject i = it.next();
            if (i instanceof PdfName) {
                intentsCollection.add((PdfName) i);
            }
        }
        return intentsCollection;
    }

    public void setIntents(List<PdfName> intents) {
        if (intents == null || intents.size() == 0) {
            ((PdfDictionary) getPdfObject()).remove(PdfName.Intent);
        } else if (intents.size() == 1) {
            ((PdfDictionary) getPdfObject()).put(PdfName.Intent, intents.get(0));
        } else {
            PdfArray array = new PdfArray();
            for (PdfName intent : intents) {
                array.add(intent);
            }
            ((PdfDictionary) getPdfObject()).put(PdfName.Intent, array);
        }
        ((PdfDictionary) getPdfObject()).setModified();
    }

    public void setCreatorInfo(String creator, String subtype) {
        PdfDictionary usage = getUsage();
        PdfDictionary dic = new PdfDictionary();
        dic.put(PdfName.Creator, new PdfString(creator, PdfEncodings.UNICODE_BIG));
        dic.put(PdfName.Subtype, new PdfName(subtype));
        usage.put(PdfName.CreatorInfo, dic);
        usage.setModified();
    }

    public void setLanguage(String lang, boolean preferred) {
        PdfDictionary usage = getUsage();
        PdfDictionary dic = new PdfDictionary();
        dic.put(PdfName.Lang, new PdfString(lang, PdfEncodings.UNICODE_BIG));
        if (preferred) {
            dic.put(PdfName.Preferred, PdfName.f1364ON);
        }
        usage.put(PdfName.Language, dic);
        usage.setModified();
    }

    public void setExport(boolean export) {
        PdfDictionary usage = getUsage();
        PdfDictionary dic = new PdfDictionary();
        dic.put(PdfName.ExportState, export ? PdfName.f1364ON : PdfName.OFF);
        usage.put(PdfName.Export, dic);
        usage.setModified();
    }

    public void setZoom(float min, float max) {
        if (min > 0.0f || max >= 0.0f) {
            PdfDictionary usage = getUsage();
            PdfDictionary dic = new PdfDictionary();
            if (min > 0.0f) {
                dic.put(PdfName.min, new PdfNumber((double) min));
            }
            if (max >= 0.0f) {
                dic.put(PdfName.max, new PdfNumber((double) max));
            }
            usage.put(PdfName.Zoom, dic);
            usage.setModified();
        }
    }

    public void setPrint(String subtype, boolean printState) {
        PdfDictionary usage = getUsage();
        PdfDictionary dic = new PdfDictionary();
        dic.put(PdfName.Subtype, new PdfName(subtype));
        dic.put(PdfName.PrintState, printState ? PdfName.f1364ON : PdfName.OFF);
        usage.put(PdfName.Print, dic);
        usage.setModified();
    }

    public void setView(boolean view) {
        PdfDictionary usage = getUsage();
        PdfDictionary dic = new PdfDictionary();
        dic.put(PdfName.ViewState, view ? PdfName.f1364ON : PdfName.OFF);
        usage.put(PdfName.View, dic);
        usage.setModified();
    }

    public void setUser(String type, String... names) {
        if (type == null || (!"Ind".equals(type) && !"Ttl".equals(type) && !"Org".equals(type))) {
            throw new IllegalArgumentException("Illegal type argument");
        } else if (names == null || names.length == 0) {
            throw new IllegalArgumentException("Illegal names argument");
        } else {
            PdfDictionary usage = getUsage();
            PdfDictionary dic = new PdfDictionary();
            dic.put(PdfName.Type, new PdfName(type));
            if (names.length == 1) {
                dic.put(PdfName.Name, new PdfString(names[0], PdfEncodings.UNICODE_BIG));
            } else {
                PdfArray namesArray = new PdfArray();
                for (String name : names) {
                    namesArray.add(new PdfString(name, PdfEncodings.UNICODE_BIG));
                }
                dic.put(PdfName.Name, namesArray);
            }
            usage.put(PdfName.User, dic);
            usage.setModified();
        }
    }

    public void setPageElement(String pe) {
        PdfDictionary usage = getUsage();
        PdfDictionary dic = new PdfDictionary();
        dic.put(PdfName.Subtype, new PdfName(pe));
        usage.put(PdfName.PageElement, dic);
        usage.setModified();
    }

    public PdfIndirectReference getIndirectReference() {
        return ((PdfDictionary) getPdfObject()).getIndirectReference();
    }

    public String getTitle() {
        return this.title;
    }

    public List<PdfLayer> getChildren() {
        if (this.children == null) {
            return null;
        }
        return new ArrayList(this.children);
    }

    /* access modifiers changed from: protected */
    public boolean isWrappedObjectMustBeIndirect() {
        return true;
    }

    /* access modifiers changed from: protected */
    public PdfDocument getDocument() {
        return ((PdfDictionary) getPdfObject()).getIndirectReference().getDocument();
    }

    protected static PdfLayer createTitleSilent(String title2, PdfDocument document) {
        if (title2 != null) {
            PdfLayer layer = new PdfLayer(document);
            layer.title = title2;
            return layer;
        }
        throw new IllegalArgumentException("Invalid title argument");
    }

    /* access modifiers changed from: protected */
    public PdfDictionary getUsage() {
        PdfDictionary usage = ((PdfDictionary) getPdfObject()).getAsDictionary(PdfName.Usage);
        if (usage != null) {
            return usage;
        }
        PdfDictionary usage2 = new PdfDictionary();
        ((PdfDictionary) getPdfObject()).put(PdfName.Usage, usage2);
        return usage2;
    }

    private PdfOCProperties fetchOCProperties() {
        return getDocument().getCatalog().getOCProperties(true);
    }
}
