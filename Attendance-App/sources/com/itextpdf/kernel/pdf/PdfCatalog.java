package com.itextpdf.kernel.pdf;

import com.itextpdf.kernel.PdfException;
import com.itextpdf.kernel.pdf.action.PdfAction;
import com.itextpdf.kernel.pdf.collection.PdfCollection;
import com.itextpdf.kernel.pdf.layer.PdfOCProperties;
import com.itextpdf.kernel.pdf.navigation.PdfDestination;
import com.itextpdf.kernel.pdf.navigation.PdfExplicitDestination;
import com.itextpdf.kernel.pdf.navigation.PdfStringDestination;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.slf4j.LoggerFactory;

public class PdfCatalog extends PdfObjectWrapper<PdfDictionary> {
    private static final String OutlineRoot = "Outlines";
    private static final Set<PdfName> PAGE_LAYOUTS = new HashSet(Arrays.asList(new PdfName[]{PdfName.SinglePage, PdfName.OneColumn, PdfName.TwoColumnLeft, PdfName.TwoColumnRight, PdfName.TwoPageLeft, PdfName.TwoPageRight}));
    private static final Set<PdfName> PAGE_MODES = new HashSet(Arrays.asList(new PdfName[]{PdfName.UseNone, PdfName.UseOutlines, PdfName.UseThumbs, PdfName.FullScreen, PdfName.UseOC, PdfName.UseAttachments}));
    private static final long serialVersionUID = -1354567597112193418L;
    protected Map<PdfName, PdfNameTree> nameTrees;
    protected PdfOCProperties ocProperties;
    private boolean outlineMode;
    private PdfOutline outlines;
    protected PdfNumTree pageLabels;
    private final PdfPagesTree pageTree;
    private Map<PdfObject, List<PdfOutline>> pagesWithOutlines;

    protected PdfCatalog(PdfDictionary pdfObject) {
        super(pdfObject);
        this.nameTrees = new LinkedHashMap();
        this.pagesWithOutlines = new HashMap();
        if (pdfObject != null) {
            ensureObjectIsAddedToDocument(pdfObject);
            ((PdfDictionary) getPdfObject()).put(PdfName.Type, PdfName.Catalog);
            setForbidRelease();
            this.pageTree = new PdfPagesTree(this);
            return;
        }
        throw new PdfException(PdfException.DocumentHasNoPdfCatalogObject);
    }

    protected PdfCatalog(PdfDocument pdfDocument) {
        this((PdfDictionary) new PdfDictionary().makeIndirect(pdfDocument));
    }

    public PdfOCProperties getOCProperties(boolean createIfNotExists) {
        PdfOCProperties pdfOCProperties = this.ocProperties;
        if (pdfOCProperties != null) {
            return pdfOCProperties;
        }
        PdfDictionary ocPropertiesDict = ((PdfDictionary) getPdfObject()).getAsDictionary(PdfName.OCProperties);
        if (ocPropertiesDict != null) {
            if (getDocument().getWriter() != null) {
                ocPropertiesDict.makeIndirect(getDocument());
            }
            this.ocProperties = new PdfOCProperties(ocPropertiesDict);
        } else if (createIfNotExists) {
            this.ocProperties = new PdfOCProperties(getDocument());
        }
        return this.ocProperties;
    }

    public PdfDocument getDocument() {
        return ((PdfDictionary) getPdfObject()).getIndirectReference().getDocument();
    }

    public void flush() {
        LoggerFactory.getLogger((Class<?>) PdfDocument.class).warn("PdfCatalog cannot be flushed manually");
    }

    public PdfCatalog setOpenAction(PdfDestination destination) {
        return put(PdfName.OpenAction, destination.getPdfObject());
    }

    public PdfCatalog setOpenAction(PdfAction action) {
        return put(PdfName.OpenAction, action.getPdfObject());
    }

    public PdfCatalog setAdditionalAction(PdfName key, PdfAction action) {
        PdfAction.setAdditionalAction(this, key, action);
        return this;
    }

    public PdfCatalog setPageMode(PdfName pageMode) {
        if (PAGE_MODES.contains(pageMode)) {
            return put(PdfName.PageMode, pageMode);
        }
        return this;
    }

    public PdfName getPageMode() {
        return ((PdfDictionary) getPdfObject()).getAsName(PdfName.PageMode);
    }

    public PdfCatalog setPageLayout(PdfName pageLayout) {
        if (PAGE_LAYOUTS.contains(pageLayout)) {
            return put(PdfName.PageLayout, pageLayout);
        }
        return this;
    }

    public PdfName getPageLayout() {
        return ((PdfDictionary) getPdfObject()).getAsName(PdfName.PageLayout);
    }

    public PdfCatalog setViewerPreferences(PdfViewerPreferences preferences) {
        return put(PdfName.ViewerPreferences, preferences.getPdfObject());
    }

    public PdfViewerPreferences getViewerPreferences() {
        PdfDictionary viewerPreferences = ((PdfDictionary) getPdfObject()).getAsDictionary(PdfName.ViewerPreferences);
        if (viewerPreferences != null) {
            return new PdfViewerPreferences(viewerPreferences);
        }
        return null;
    }

    public PdfNameTree getNameTree(PdfName treeType) {
        PdfNameTree tree = this.nameTrees.get(treeType);
        if (tree != null) {
            return tree;
        }
        PdfNameTree tree2 = new PdfNameTree(this, treeType);
        this.nameTrees.put(treeType, tree2);
        return tree2;
    }

    public PdfNumTree getPageLabelsTree(boolean createIfNotExists) {
        if (this.pageLabels == null && (((PdfDictionary) getPdfObject()).containsKey(PdfName.PageLabels) || createIfNotExists)) {
            this.pageLabels = new PdfNumTree(this, PdfName.PageLabels);
        }
        return this.pageLabels;
    }

    public void setLang(PdfString lang) {
        put(PdfName.Lang, lang);
    }

    public PdfString getLang() {
        return ((PdfDictionary) getPdfObject()).getAsString(PdfName.Lang);
    }

    public void addDeveloperExtension(PdfDeveloperExtension extension) {
        PdfDictionary extensions = ((PdfDictionary) getPdfObject()).getAsDictionary(PdfName.Extensions);
        if (extensions == null) {
            extensions = new PdfDictionary();
            put(PdfName.Extensions, extensions);
        } else {
            PdfDictionary existingExtensionDict = extensions.getAsDictionary(extension.getPrefix());
            if (existingExtensionDict != null && (extension.getBaseVersion().compareTo(existingExtensionDict.getAsName(PdfName.BaseVersion)) < 0 || extension.getExtensionLevel() - existingExtensionDict.getAsNumber(PdfName.ExtensionLevel).intValue() <= 0)) {
                return;
            }
        }
        extensions.put(extension.getPrefix(), extension.getDeveloperExtensions());
    }

    public PdfCollection getCollection() {
        PdfDictionary collectionDictionary = ((PdfDictionary) getPdfObject()).getAsDictionary(PdfName.Collection);
        if (collectionDictionary != null) {
            return new PdfCollection(collectionDictionary);
        }
        return null;
    }

    public PdfCatalog setCollection(PdfCollection collection) {
        put(PdfName.Collection, collection.getPdfObject());
        return this;
    }

    public PdfCatalog put(PdfName key, PdfObject value) {
        ((PdfDictionary) getPdfObject()).put(key, value);
        setModified();
        return this;
    }

    public PdfCatalog remove(PdfName key) {
        ((PdfDictionary) getPdfObject()).remove(key);
        setModified();
        return this;
    }

    /* access modifiers changed from: protected */
    public boolean isWrappedObjectMustBeIndirect() {
        return true;
    }

    /* access modifiers changed from: protected */
    public boolean isOCPropertiesMayHaveChanged() {
        return this.ocProperties != null;
    }

    /* access modifiers changed from: package-private */
    public PdfPagesTree getPageTree() {
        return this.pageTree;
    }

    /* access modifiers changed from: package-private */
    public Map<PdfObject, List<PdfOutline>> getPagesWithOutlines() {
        return this.pagesWithOutlines;
    }

    /* access modifiers changed from: package-private */
    public void addNamedDestination(String key, PdfObject value) {
        addNameToNameTree(key, value, PdfName.Dests);
    }

    /* access modifiers changed from: package-private */
    public void addNameToNameTree(String key, PdfObject value, PdfName treeType) {
        getNameTree(treeType).addEntry(key, value);
    }

    /* access modifiers changed from: package-private */
    public PdfOutline getOutlines(boolean updateOutlines) {
        PdfOutline pdfOutline = this.outlines;
        if (pdfOutline != null && !updateOutlines) {
            return pdfOutline;
        }
        if (pdfOutline != null) {
            pdfOutline.clear();
            this.pagesWithOutlines.clear();
        }
        this.outlineMode = true;
        PdfNameTree destsTree = getNameTree(PdfName.Dests);
        PdfDictionary outlineRoot = ((PdfDictionary) getPdfObject()).getAsDictionary(PdfName.Outlines);
        if (outlineRoot != null) {
            constructOutlines(outlineRoot, destsTree.getNames());
        } else if (getDocument().getWriter() == null) {
            return null;
        } else {
            this.outlines = new PdfOutline(getDocument());
        }
        return this.outlines;
    }

    /* access modifiers changed from: package-private */
    public boolean hasOutlines() {
        return ((PdfDictionary) getPdfObject()).containsKey(PdfName.Outlines);
    }

    /* access modifiers changed from: package-private */
    public boolean isOutlineMode() {
        return this.outlineMode;
    }

    /* access modifiers changed from: package-private */
    public void removeOutlines(PdfPage page) {
        if (getDocument().getWriter() != null && hasOutlines()) {
            getOutlines(false);
            if (this.pagesWithOutlines.size() > 0 && this.pagesWithOutlines.get(page.getPdfObject()) != null) {
                for (PdfOutline outline : this.pagesWithOutlines.get(page.getPdfObject())) {
                    outline.removeOutline();
                }
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void addRootOutline(PdfOutline outline) {
        if (this.outlineMode && this.pagesWithOutlines.size() == 0) {
            put(PdfName.Outlines, outline.getContent());
        }
    }

    /* access modifiers changed from: package-private */
    public PdfDestination copyDestination(PdfObject dest, Map<PdfPage, PdfPage> page2page, PdfDocument toDocument) {
        PdfObject pageObject;
        PdfObject pdfObject = dest;
        PdfDocument pdfDocument = toDocument;
        PdfDestination d = null;
        if (dest.isArray()) {
            PdfObject pageObject2 = ((PdfArray) pdfObject).get(0);
            Iterator<PdfPage> it = page2page.keySet().iterator();
            while (true) {
                if (it.hasNext()) {
                    if (it.next().getPdfObject() == pageObject2) {
                        d = new PdfExplicitDestination((PdfArray) pdfObject.copyTo(pdfDocument, false));
                        break;
                    }
                } else {
                    break;
                }
            }
        } else if (dest.isString() || dest.isName()) {
            Map<String, PdfObject> dests = getNameTree(PdfName.Dests).getNames();
            String srcDestName = dest.isString() ? ((PdfString) pdfObject).toUnicodeString() : ((PdfName) pdfObject).getValue();
            PdfArray srcDestArray = (PdfArray) dests.get(srcDestName);
            if (srcDestArray != null) {
                PdfObject pageObject3 = srcDestArray.get(0);
                if (pageObject3 instanceof PdfNumber) {
                    pageObject = getDocument().getPage(((PdfNumber) pageObject3).intValue() + 1).getPdfObject();
                } else {
                    pageObject = pageObject3;
                }
                for (PdfPage oldPage : page2page.keySet()) {
                    if (oldPage.getPdfObject() == pageObject) {
                        PdfDestination d2 = new PdfStringDestination(srcDestName);
                        if (!isEqualSameNameDestExist(page2page, toDocument, srcDestName, srcDestArray, oldPage)) {
                            PdfArray copiedArray = (PdfArray) srcDestArray.copyTo(pdfDocument, false);
                            copiedArray.set(0, page2page.get(oldPage).getPdfObject());
                            pdfDocument.addNamedDestination(srcDestName, copiedArray);
                        } else {
                            Map<PdfPage, PdfPage> map = page2page;
                        }
                        return d2;
                    }
                    Map<PdfPage, PdfPage> map2 = page2page;
                }
                Map<PdfPage, PdfPage> map3 = page2page;
                return null;
            }
            Map<PdfPage, PdfPage> map4 = page2page;
            return null;
        }
        Map<PdfPage, PdfPage> map5 = page2page;
        return d;
    }

    /* access modifiers changed from: package-private */
    public PdfDictionary fillAndGetOcPropertiesDictionary() {
        PdfOCProperties pdfOCProperties = this.ocProperties;
        if (pdfOCProperties != null) {
            pdfOCProperties.fillDictionary(false);
            ((PdfDictionary) getPdfObject()).put(PdfName.OCProperties, this.ocProperties.getPdfObject());
            this.ocProperties = null;
        }
        if (((PdfDictionary) getPdfObject()).getAsDictionary(PdfName.OCProperties) == null) {
            PdfDictionary pdfDictionary = new PdfDictionary();
            pdfDictionary.makeIndirect(getDocument());
            ((PdfDictionary) getDocument().getCatalog().getPdfObject()).put(PdfName.OCProperties, pdfDictionary);
        }
        return ((PdfDictionary) getPdfObject()).getAsDictionary(PdfName.OCProperties);
    }

    private boolean isEqualSameNameDestExist(Map<PdfPage, PdfPage> page2page, PdfDocument toDocument, String srcDestName, PdfArray srcDestArray, PdfPage oldPage) {
        PdfArray sameNameDest = (PdfArray) toDocument.getCatalog().getNameTree(PdfName.Dests).getNames().get(srcDestName);
        boolean equalSameNameDestExists = false;
        if (!(sameNameDest == null || sameNameDest.getAsDictionary(0) == null)) {
            boolean z = sameNameDest.getAsDictionary(0).getIndirectReference().equals(((PdfDictionary) page2page.get(oldPage).getPdfObject()).getIndirectReference()) && sameNameDest.size() == srcDestArray.size();
            equalSameNameDestExists = z;
            if (z) {
                for (int i = 1; i < sameNameDest.size(); i++) {
                    equalSameNameDestExists = equalSameNameDestExists && sameNameDest.get(i).equals(srcDestArray.get(i));
                }
            }
        }
        return equalSameNameDestExists;
    }

    private void addOutlineToPage(PdfOutline outline, Map<String, PdfObject> names) {
        PdfObject pageObj = outline.getDestination().getDestinationPage(names);
        if (pageObj instanceof PdfNumber) {
            pageObj = getDocument().getPage(((PdfNumber) pageObj).intValue() + 1).getPdfObject();
        }
        if (pageObj != null) {
            List<PdfOutline> outs = this.pagesWithOutlines.get(pageObj);
            if (outs == null) {
                outs = new ArrayList<>();
                this.pagesWithOutlines.put(pageObj, outs);
            }
            outs.add(outline);
        }
    }

    private PdfDictionary getNextOutline(PdfDictionary first, PdfDictionary next, PdfDictionary parent) {
        if (first != null) {
            return first;
        }
        if (next != null) {
            return next;
        }
        return getParentNextOutline(parent);
    }

    private PdfDictionary getParentNextOutline(PdfDictionary parent) {
        if (parent == null) {
            return null;
        }
        PdfDictionary current = null;
        while (current == null) {
            current = parent.getAsDictionary(PdfName.Next);
            if (current == null && (parent = parent.getAsDictionary(PdfName.Parent)) == null) {
                return null;
            }
        }
        return current;
    }

    private void addOutlineToPage(PdfOutline outline, PdfDictionary item, Map<String, PdfObject> names) {
        PdfObject destObject;
        PdfObject dest = item.get(PdfName.Dest);
        if (dest != null) {
            outline.setDestination(PdfDestination.makeDestination(dest));
            addOutlineToPage(outline, names);
            return;
        }
        PdfDictionary action = item.getAsDictionary(PdfName.f1287A);
        if (action != null) {
            if (PdfName.GoTo.equals(action.getAsName(PdfName.f1385S)) && (destObject = action.get(PdfName.f1312D)) != null) {
                outline.setDestination(PdfDestination.makeDestination(destObject));
                addOutlineToPage(outline, names);
            }
        }
    }

    private void constructOutlines(PdfDictionary outlineRoot, Map<String, PdfObject> names) {
        if (outlineRoot != null) {
            PdfDictionary current = outlineRoot.getAsDictionary(PdfName.First);
            HashMap<PdfDictionary, PdfOutline> parentOutlineMap = new HashMap<>();
            this.outlines = new PdfOutline(OutlineRoot, outlineRoot, getDocument());
            parentOutlineMap.put(outlineRoot, this.outlines);
            while (current != null) {
                PdfDictionary first = current.getAsDictionary(PdfName.First);
                PdfDictionary next = current.getAsDictionary(PdfName.Next);
                PdfDictionary parent = current.getAsDictionary(PdfName.Parent);
                PdfOutline parentOutline = parentOutlineMap.get(parent);
                PdfOutline currentOutline = new PdfOutline(current.getAsString(PdfName.Title).toUnicodeString(), current, parentOutline);
                addOutlineToPage(currentOutline, current, names);
                parentOutline.getAllChildren().add(currentOutline);
                if (first != null) {
                    parentOutlineMap.put(current, currentOutline);
                }
                current = getNextOutline(first, next, parent);
            }
        }
    }
}
