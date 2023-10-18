package com.itextpdf.kernel.pdf.layer;

import com.itextpdf.kernel.pdf.PdfArray;
import com.itextpdf.kernel.pdf.PdfDictionary;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfIndirectReference;
import com.itextpdf.kernel.pdf.PdfName;
import com.itextpdf.kernel.pdf.PdfObject;
import com.itextpdf.kernel.pdf.PdfObjectWrapper;
import com.itextpdf.kernel.pdf.PdfString;
import com.itextpdf.p026io.font.PdfEncodings;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class PdfOCProperties extends PdfObjectWrapper<PdfDictionary> {
    static final String OC_CONFIG_NAME_PATTERN = "OCConfigName";
    private static final long serialVersionUID = 1137977454824741350L;
    private List<PdfLayer> layers;

    public PdfOCProperties(PdfDocument document) {
        this((PdfDictionary) new PdfDictionary().makeIndirect(document));
    }

    public PdfOCProperties(PdfDictionary ocPropertiesDict) {
        super(ocPropertiesDict);
        this.layers = new ArrayList();
        ensureObjectIsAddedToDocument(ocPropertiesDict);
        readLayersFromDictionary();
    }

    public void addOCGRadioGroup(List<PdfLayer> group) {
        PdfArray ar = new PdfArray();
        for (PdfLayer layer : group) {
            if (layer.getTitle() == null) {
                ar.add(((PdfDictionary) layer.getPdfObject()).getIndirectReference());
            }
        }
        if (ar.size() != 0) {
            PdfDictionary d = ((PdfDictionary) getPdfObject()).getAsDictionary(PdfName.f1312D);
            if (d == null) {
                d = new PdfDictionary();
                ((PdfDictionary) getPdfObject()).put(PdfName.f1312D, d);
            }
            PdfArray radioButtonGroups = d.getAsArray(PdfName.RBGroups);
            if (radioButtonGroups == null) {
                radioButtonGroups = new PdfArray();
                d.put(PdfName.RBGroups, radioButtonGroups);
                d.setModified();
            } else {
                radioButtonGroups.setModified();
            }
            radioButtonGroups.add(ar);
        }
    }

    public PdfObject fillDictionary() {
        return fillDictionary(true);
    }

    public PdfObject fillDictionary(boolean removeNonDocumentOcgs) {
        PdfArray gr = new PdfArray();
        for (PdfLayer layer : this.layers) {
            if (layer.getTitle() == null) {
                gr.add(layer.getIndirectReference());
            }
        }
        ((PdfDictionary) getPdfObject()).put(PdfName.OCGs, gr);
        PdfArray rbGroups = null;
        PdfDictionary d = ((PdfDictionary) getPdfObject()).getAsDictionary(PdfName.f1312D);
        if (d != null) {
            rbGroups = d.getAsArray(PdfName.RBGroups);
        }
        PdfDictionary d2 = new PdfDictionary();
        if (rbGroups != null) {
            d2.put(PdfName.RBGroups, rbGroups);
        }
        d2.put(PdfName.Name, new PdfString(createUniqueName(), PdfEncodings.UNICODE_BIG));
        ((PdfDictionary) getPdfObject()).put(PdfName.f1312D, d2);
        List<PdfLayer> docOrder = new ArrayList<>(this.layers);
        int i = 0;
        while (i < docOrder.size()) {
            PdfLayer layer2 = docOrder.get(i);
            if (layer2.getParent() != null) {
                docOrder.remove(layer2);
                i--;
            }
            i++;
        }
        PdfArray order = new PdfArray();
        for (PdfLayer layer3 : docOrder) {
            getOCGOrder(order, layer3);
        }
        d2.put(PdfName.Order, order);
        PdfArray off = new PdfArray();
        for (PdfLayer layer4 : this.layers) {
            if (layer4.getTitle() == null && !layer4.isOn()) {
                off.add(layer4.getIndirectReference());
            }
        }
        if (off.size() > 0) {
            d2.put(PdfName.OFF, off);
        } else {
            d2.remove(PdfName.OFF);
        }
        PdfArray locked = new PdfArray();
        for (PdfLayer layer5 : this.layers) {
            if (layer5.getTitle() == null && layer5.isLocked()) {
                locked.add(layer5.getIndirectReference());
            }
        }
        if (locked.size() > 0) {
            d2.put(PdfName.Locked, locked);
        } else {
            d2.remove(PdfName.Locked);
        }
        d2.remove(PdfName.f1292AS);
        addASEvent(PdfName.View, PdfName.Zoom);
        addASEvent(PdfName.View, PdfName.View);
        addASEvent(PdfName.Print, PdfName.Print);
        addASEvent(PdfName.Export, PdfName.Export);
        if (removeNonDocumentOcgs) {
            removeNotRegisteredOcgs();
        }
        return getPdfObject();
    }

    public void flush() {
        fillDictionary();
        super.flush();
    }

    public List<PdfLayer> getLayers() {
        return new ArrayList(this.layers);
    }

    /* access modifiers changed from: protected */
    public boolean isWrappedObjectMustBeIndirect() {
        return true;
    }

    /* access modifiers changed from: protected */
    public void registerLayer(PdfLayer layer) {
        if (layer != null) {
            this.layers.add(layer);
            return;
        }
        throw new IllegalArgumentException("layer argument is null");
    }

    /* access modifiers changed from: protected */
    public PdfDocument getDocument() {
        return ((PdfDictionary) getPdfObject()).getIndirectReference().getDocument();
    }

    private static void getOCGOrder(PdfArray order, PdfLayer layer) {
        if (layer.isOnPanel()) {
            if (layer.getTitle() == null) {
                order.add(((PdfDictionary) layer.getPdfObject()).getIndirectReference());
            }
            List<PdfLayer> children = layer.getChildren();
            if (children != null) {
                PdfArray kids = new PdfArray();
                if (layer.getTitle() != null) {
                    kids.add(new PdfString(layer.getTitle(), PdfEncodings.UNICODE_BIG));
                }
                for (PdfLayer child : children) {
                    getOCGOrder(kids, child);
                }
                if (kids.size() > 0) {
                    order.add(kids);
                }
            }
        }
    }

    private void removeNotRegisteredOcgs() {
        PdfDictionary dDict = ((PdfDictionary) getPdfObject()).getAsDictionary(PdfName.f1312D);
        PdfDictionary ocProperties = ((PdfDictionary) getDocument().getCatalog().getPdfObject()).getAsDictionary(PdfName.OCProperties);
        Set<PdfIndirectReference> ocgsFromDocument = new HashSet<>();
        if (ocProperties.getAsArray(PdfName.OCGs) != null) {
            Iterator<PdfObject> it = ocProperties.getAsArray(PdfName.OCGs).iterator();
            while (it.hasNext()) {
                PdfObject ocgObj = it.next();
                if (ocgObj.isDictionary()) {
                    ocgsFromDocument.add(ocgObj.getIndirectReference());
                }
            }
        }
        PdfArray rbGroups = dDict.getAsArray(PdfName.RBGroups);
        if (rbGroups != null) {
            Iterator<PdfObject> it2 = rbGroups.iterator();
            while (it2.hasNext()) {
                PdfArray rbGroup = (PdfArray) it2.next();
                int i = rbGroup.size();
                while (true) {
                    i--;
                    if (i > -1) {
                        if (!ocgsFromDocument.contains(rbGroup.get(i).getIndirectReference())) {
                            rbGroup.remove(i);
                        }
                    }
                }
            }
        }
    }

    private void addASEvent(PdfName event, PdfName category) {
        PdfDictionary usage;
        PdfArray arr = new PdfArray();
        for (PdfLayer layer : this.layers) {
            if (layer.getTitle() == null && !((PdfDictionary) layer.getPdfObject()).isFlushed() && (usage = ((PdfDictionary) layer.getPdfObject()).getAsDictionary(PdfName.Usage)) != null && usage.get(category) != null) {
                arr.add(((PdfDictionary) layer.getPdfObject()).getIndirectReference());
            }
        }
        if (arr.size() != 0) {
            PdfDictionary d = ((PdfDictionary) getPdfObject()).getAsDictionary(PdfName.f1312D);
            PdfArray arras = d.getAsArray(PdfName.f1292AS);
            if (arras == null) {
                arras = new PdfArray();
                d.put(PdfName.f1292AS, arras);
            }
            PdfDictionary as = new PdfDictionary();
            as.put(PdfName.Event, event);
            PdfArray categoryArray = new PdfArray();
            categoryArray.add(category);
            as.put(PdfName.Category, categoryArray);
            as.put(PdfName.OCGs, arr);
            arras.add(as);
        }
    }

    private void readLayersFromDictionary() {
        PdfArray ocgs = ((PdfDictionary) getPdfObject()).getAsArray(PdfName.OCGs);
        if (ocgs != null && !ocgs.isEmpty()) {
            Map<PdfIndirectReference, PdfLayer> layerMap = new TreeMap<>();
            for (int ind = 0; ind < ocgs.size(); ind++) {
                PdfLayer currentLayer = new PdfLayer((PdfDictionary) ocgs.getAsDictionary(ind).makeIndirect(getDocument()));
                currentLayer.onPanel = false;
                layerMap.put(currentLayer.getIndirectReference(), currentLayer);
            }
            PdfDictionary d = ((PdfDictionary) getPdfObject()).getAsDictionary(PdfName.f1312D);
            if (d != null && !d.isEmpty()) {
                PdfArray off = d.getAsArray(PdfName.OFF);
                if (off != null) {
                    for (int i = 0; i < off.size(); i++) {
                        PdfObject offLayer = off.get(i, false);
                        if (offLayer.isIndirectReference()) {
                            layerMap.get((PdfIndirectReference) offLayer).f1500on = false;
                        } else {
                            layerMap.get(offLayer.getIndirectReference()).f1500on = false;
                        }
                    }
                }
                PdfArray locked = d.getAsArray(PdfName.Locked);
                if (locked != null) {
                    for (int i2 = 0; i2 < locked.size(); i2++) {
                        PdfObject lockedLayer = locked.get(i2, false);
                        if (lockedLayer.isIndirectReference()) {
                            layerMap.get((PdfIndirectReference) lockedLayer).locked = true;
                        } else {
                            layerMap.get(lockedLayer.getIndirectReference()).locked = true;
                        }
                    }
                }
                PdfArray orderArray = d.getAsArray(PdfName.Order);
                if (orderArray != null && !orderArray.isEmpty()) {
                    readOrderFromDictionary((PdfLayer) null, orderArray, layerMap);
                }
            }
            for (PdfLayer layer : layerMap.values()) {
                if (!layer.isOnPanel()) {
                    this.layers.add(layer);
                }
            }
        }
    }

    private void readOrderFromDictionary(PdfLayer parent, PdfArray orderArray, Map<PdfIndirectReference, PdfLayer> layerMap) {
        int i = 0;
        while (i < orderArray.size()) {
            PdfObject item = orderArray.get(i);
            if (item.getType() == 3) {
                PdfLayer layer = layerMap.get(item.getIndirectReference());
                if (layer != null) {
                    this.layers.add(layer);
                    layer.onPanel = true;
                    if (parent != null) {
                        parent.addChild(layer);
                    }
                    if (i + 1 < orderArray.size() && orderArray.get(i + 1).getType() == 1) {
                        PdfArray nextArray = orderArray.getAsArray(i + 1);
                        if (nextArray.size() > 0 && nextArray.get(0).getType() != 10) {
                            readOrderFromDictionary(layer, orderArray.getAsArray(i + 1), layerMap);
                            i++;
                        }
                    }
                }
            } else if (item.getType() == 1) {
                PdfArray subArray = (PdfArray) item;
                if (!subArray.isEmpty()) {
                    PdfObject firstObj = subArray.get(0);
                    if (firstObj.getType() == 10) {
                        PdfLayer titleLayer = PdfLayer.createTitleSilent(((PdfString) firstObj).toUnicodeString(), getDocument());
                        titleLayer.onPanel = true;
                        this.layers.add(titleLayer);
                        if (parent != null) {
                            parent.addChild(titleLayer);
                        }
                        readOrderFromDictionary(titleLayer, new PdfArray((List<? extends PdfObject>) subArray.subList(1, subArray.size())), layerMap);
                    } else {
                        readOrderFromDictionary(parent, subArray, layerMap);
                    }
                }
            }
            i++;
        }
    }

    private String createUniqueName() {
        int uniqueID = 0;
        Set<String> usedNames = new HashSet<>();
        PdfArray configs = ((PdfDictionary) getPdfObject()).getAsArray(PdfName.Configs);
        if (configs != null) {
            for (int i = 0; i < configs.size(); i++) {
                PdfDictionary alternateDictionary = configs.getAsDictionary(i);
                if (alternateDictionary != null && alternateDictionary.containsKey(PdfName.Name)) {
                    usedNames.add(alternateDictionary.getAsString(PdfName.Name).toUnicodeString());
                }
            }
        }
        while (usedNames.contains(OC_CONFIG_NAME_PATTERN + uniqueID)) {
            uniqueID++;
        }
        return OC_CONFIG_NAME_PATTERN + uniqueID;
    }
}
