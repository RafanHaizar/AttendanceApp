package com.itextpdf.kernel.pdf.tagging;

import com.itextpdf.kernel.PdfException;
import com.itextpdf.kernel.pdf.IsoKey;
import com.itextpdf.kernel.pdf.PdfArray;
import com.itextpdf.kernel.pdf.PdfDictionary;
import com.itextpdf.kernel.pdf.PdfIndirectReference;
import com.itextpdf.kernel.pdf.PdfName;
import com.itextpdf.kernel.pdf.PdfNull;
import com.itextpdf.kernel.pdf.PdfNumTree;
import com.itextpdf.kernel.pdf.PdfNumber;
import com.itextpdf.kernel.pdf.PdfObject;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfStream;
import com.itextpdf.p026io.LogMessageConstant;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.NavigableMap;
import java.util.Set;
import java.util.TreeMap;
import org.slf4j.LoggerFactory;

class ParentTreeHandler implements Serializable {
    private static final long serialVersionUID = 1593883864288316473L;
    private Map<PdfIndirectReference, PageMcrsContainer> pageToPageMcrs;
    private Map<PdfIndirectReference, Integer> pageToStructParentsInd;
    private PdfNumTree parentTree;
    private PdfStructTreeRoot structTreeRoot;
    private Map<PdfIndirectReference, Integer> xObjectToStructParentsInd = new HashMap();

    ParentTreeHandler(PdfStructTreeRoot structTreeRoot2) {
        this.structTreeRoot = structTreeRoot2;
        this.parentTree = new PdfNumTree(structTreeRoot2.getDocument().getCatalog(), PdfName.ParentTree);
        registerAllMcrs();
        this.pageToStructParentsInd = new HashMap();
    }

    public PageMcrsContainer getPageMarkedContentReferences(PdfPage page) {
        return this.pageToPageMcrs.get(((PdfDictionary) page.getPdfObject()).getIndirectReference());
    }

    public PdfMcr findMcrByMcid(PdfDictionary pageDict, int mcid) {
        PageMcrsContainer pageMcrs = this.pageToPageMcrs.get(pageDict.getIndirectReference());
        if (pageMcrs != null) {
            return (PdfMcr) pageMcrs.getPageContentStreamsMcrs().get(Integer.valueOf(mcid));
        }
        return null;
    }

    public PdfObjRef findObjRefByStructParentIndex(PdfDictionary pageDict, int structParentIndex) {
        PageMcrsContainer pageMcrs = this.pageToPageMcrs.get(pageDict.getIndirectReference());
        if (pageMcrs != null) {
            return (PdfObjRef) pageMcrs.getObjRefs().get(Integer.valueOf(structParentIndex));
        }
        return null;
    }

    public int getNextMcidForPage(PdfPage page) {
        PageMcrsContainer pageMcrs = getPageMarkedContentReferences(page);
        if (pageMcrs == null || pageMcrs.getPageContentStreamsMcrs().size() == 0) {
            return 0;
        }
        return pageMcrs.getPageContentStreamsMcrs().lastEntry().getKey().intValue() + 1;
    }

    public void createParentTreeEntryForPage(PdfPage page) {
        PageMcrsContainer mcrs = getPageMarkedContentReferences(page);
        if (mcrs != null) {
            this.pageToPageMcrs.remove(((PdfDictionary) page.getPdfObject()).getIndirectReference());
            if (updateStructParentTreeEntries(page, mcrs)) {
                this.structTreeRoot.setModified();
            }
        }
    }

    public void savePageStructParentIndexIfNeeded(PdfPage page) {
        PdfIndirectReference indRef = ((PdfDictionary) page.getPdfObject()).getIndirectReference();
        if (!page.isFlushed() && this.pageToPageMcrs.get(indRef) != null) {
            if (this.pageToPageMcrs.get(indRef).getPageContentStreamsMcrs().size() > 0 || this.pageToPageMcrs.get(indRef).getPageResourceXObjects().size() > 0) {
                this.pageToStructParentsInd.put(indRef, Integer.valueOf(getOrCreatePageStructParentIndex(page)));
            }
        }
    }

    public PdfDictionary buildParentTree() {
        return (PdfDictionary) this.parentTree.buildTree().makeIndirect(this.structTreeRoot.getDocument());
    }

    public void registerMcr(PdfMcr mcr) {
        registerMcr(mcr, false);
    }

    private void registerMcr(PdfMcr mcr, boolean registeringOnInit) {
        PdfStream xObjectStream;
        PdfIndirectReference stmIndRef;
        PdfIndirectReference mcrPageIndRef = mcr.getPageIndirectReference();
        Class<ParentTreeHandler> cls = ParentTreeHandler.class;
        if (mcrPageIndRef == null || (!(mcr instanceof PdfObjRef) && mcr.getMcid() < 0)) {
            LoggerFactory.getLogger((Class<?>) cls).error(LogMessageConstant.ENCOUNTERED_INVALID_MCR);
            return;
        }
        PageMcrsContainer pageMcrs = this.pageToPageMcrs.get(mcrPageIndRef);
        if (pageMcrs == null) {
            pageMcrs = new PageMcrsContainer();
            this.pageToPageMcrs.put(mcrPageIndRef, pageMcrs);
        }
        PdfObject stm = getStm(mcr);
        PdfObject stm2 = stm;
        if (stm != null) {
            if (stm2 instanceof PdfIndirectReference) {
                stmIndRef = (PdfIndirectReference) stm2;
                xObjectStream = (PdfStream) stmIndRef.getRefersTo();
            } else {
                if (stm2.getIndirectReference() == null) {
                    stm2.makeIndirect(this.structTreeRoot.getDocument());
                }
                stmIndRef = stm2.getIndirectReference();
                xObjectStream = (PdfStream) stm2;
            }
            Integer structParent = xObjectStream.getAsInt(PdfName.StructParents);
            if (structParent != null) {
                this.xObjectToStructParentsInd.put(stmIndRef, structParent);
            } else {
                LoggerFactory.getLogger((Class<?>) cls).error(LogMessageConstant.XOBJECT_HAS_NO_STRUCT_PARENTS);
            }
            pageMcrs.putXObjectMcr(stmIndRef, mcr);
            if (registeringOnInit) {
                xObjectStream.release();
            }
        } else if (mcr instanceof PdfObjRef) {
            PdfDictionary obj = ((PdfDictionary) mcr.getPdfObject()).getAsDictionary(PdfName.Obj);
            if (obj == null || obj.isFlushed()) {
                throw new PdfException(PdfException.f1248x4ac180a9);
            }
            PdfNumber n = obj.getAsNumber(PdfName.StructParent);
            if (n != null) {
                pageMcrs.putObjectReferenceMcr(n.intValue(), mcr);
            } else {
                throw new PdfException(PdfException.StructParentIndexNotFoundInTaggedObject);
            }
        } else {
            pageMcrs.putPageContentStreamMcr(mcr.getMcid(), mcr);
        }
        if (!registeringOnInit) {
            this.structTreeRoot.setModified();
        }
    }

    public void unregisterMcr(PdfMcr mcrToUnregister) {
        PdfNumber n;
        PdfIndirectReference xObjectReference;
        PdfDictionary pageDict = mcrToUnregister.getPageObject();
        if (pageDict != null) {
            if (!pageDict.isFlushed()) {
                PageMcrsContainer pageMcrs = this.pageToPageMcrs.get(pageDict.getIndirectReference());
                if (pageMcrs != null) {
                    PdfObject stm = getStm(mcrToUnregister);
                    PdfObject stm2 = stm;
                    if (stm != null) {
                        if (stm2 instanceof PdfIndirectReference) {
                            xObjectReference = (PdfIndirectReference) stm2;
                        } else {
                            xObjectReference = stm2.getIndirectReference();
                        }
                        pageMcrs.getPageResourceXObjects().get(xObjectReference).remove(Integer.valueOf(mcrToUnregister.getMcid()));
                        if (pageMcrs.getPageResourceXObjects().get(xObjectReference).isEmpty()) {
                            pageMcrs.getPageResourceXObjects().remove(xObjectReference);
                            this.xObjectToStructParentsInd.remove(xObjectReference);
                        }
                        this.structTreeRoot.setModified();
                    } else if (mcrToUnregister instanceof PdfObjRef) {
                        PdfDictionary obj = ((PdfDictionary) mcrToUnregister.getPdfObject()).getAsDictionary(PdfName.Obj);
                        if (obj == null || obj.isFlushed() || (n = obj.getAsNumber(PdfName.StructParent)) == null) {
                            for (Map.Entry<Integer, PdfMcr> entry : pageMcrs.getObjRefs().entrySet()) {
                                if (entry.getValue().getPdfObject() == mcrToUnregister.getPdfObject()) {
                                    pageMcrs.getObjRefs().remove(entry.getKey());
                                    this.structTreeRoot.setModified();
                                    return;
                                }
                            }
                            return;
                        }
                        pageMcrs.getObjRefs().remove(Integer.valueOf(n.intValue()));
                        this.structTreeRoot.setModified();
                    } else {
                        pageMcrs.getPageContentStreamsMcrs().remove(Integer.valueOf(mcrToUnregister.getMcid()));
                        this.structTreeRoot.setModified();
                    }
                }
            } else {
                throw new PdfException(PdfException.f1239x7c8643be);
            }
        }
    }

    private void registerAllMcrs() {
        this.pageToPageMcrs = new HashMap();
        Map<Integer, PdfObject> parentTreeEntries = new PdfNumTree(this.structTreeRoot.getDocument().getCatalog(), PdfName.ParentTree).getNumbers();
        Set<PdfDictionary> mcrParents = new LinkedHashSet<>();
        int maxStructParentIndex = -1;
        for (Map.Entry<Integer, PdfObject> entry : parentTreeEntries.entrySet()) {
            if (entry.getKey().intValue() > maxStructParentIndex) {
                maxStructParentIndex = entry.getKey().intValue();
            }
            PdfObject entryValue = entry.getValue();
            if (entryValue.isDictionary()) {
                mcrParents.add((PdfDictionary) entryValue);
            } else if (entryValue.isArray()) {
                PdfArray parentsArray = (PdfArray) entryValue;
                for (int i = 0; i < parentsArray.size(); i++) {
                    PdfDictionary parent = parentsArray.getAsDictionary(i);
                    if (parent != null) {
                        mcrParents.add(parent);
                    }
                }
            }
        }
        ((PdfDictionary) this.structTreeRoot.getPdfObject()).put(PdfName.ParentTreeNextKey, new PdfNumber(maxStructParentIndex + 1));
        for (PdfDictionary pdfStructElem : mcrParents) {
            for (IStructureNode kid : new PdfStructElem(pdfStructElem).getKids()) {
                if (kid instanceof PdfMcr) {
                    registerMcr((PdfMcr) kid, true);
                }
            }
        }
    }

    private boolean updateStructParentTreeEntries(PdfPage page, PageMcrsContainer mcrs) {
        int pageStructParentIndex;
        boolean res = false;
        for (Map.Entry<Integer, PdfMcr> entry : mcrs.getObjRefs().entrySet()) {
            PdfDictionary parentObj = (PdfDictionary) ((PdfStructElem) entry.getValue().getParent()).getPdfObject();
            if (parentObj.isIndirect()) {
                this.parentTree.addEntry(entry.getKey().intValue(), parentObj);
                res = true;
            }
        }
        for (Map.Entry<PdfIndirectReference, TreeMap<Integer, PdfMcr>> entry2 : mcrs.getPageResourceXObjects().entrySet()) {
            PdfIndirectReference xObjectRef = entry2.getKey();
            if (this.xObjectToStructParentsInd.containsKey(xObjectRef)) {
                if (updateStructParentTreeForContentStreamEntries(entry2.getValue(), this.xObjectToStructParentsInd.remove(xObjectRef).intValue())) {
                    res = true;
                }
            }
        }
        if (page.isFlushed()) {
            PdfIndirectReference pageRef = ((PdfDictionary) page.getPdfObject()).getIndirectReference();
            if (!this.pageToStructParentsInd.containsKey(pageRef)) {
                return res;
            }
            pageStructParentIndex = this.pageToStructParentsInd.remove(pageRef).intValue();
        } else {
            pageStructParentIndex = getOrCreatePageStructParentIndex(page);
        }
        if (updateStructParentTreeForContentStreamEntries(mcrs.getPageContentStreamsMcrs(), pageStructParentIndex)) {
            return true;
        }
        return res;
    }

    private boolean updateStructParentTreeForContentStreamEntries(Map<Integer, PdfMcr> mcrsOfContentStream, int pageStructParentIndex) {
        int currentMcid;
        PdfArray parentsOfMcrs = new PdfArray();
        int currentMcid2 = 0;
        for (Map.Entry<Integer, PdfMcr> entry : mcrsOfContentStream.entrySet()) {
            PdfMcr mcr = entry.getValue();
            PdfDictionary parentObj = (PdfDictionary) ((PdfStructElem) mcr.getParent()).getPdfObject();
            if (parentObj.isIndirect()) {
                while (true) {
                    currentMcid = currentMcid2 + 1;
                    if (currentMcid2 >= mcr.getMcid()) {
                        break;
                    }
                    parentsOfMcrs.add(PdfNull.PDF_NULL);
                    currentMcid2 = currentMcid;
                }
                parentsOfMcrs.add(parentObj);
                currentMcid2 = currentMcid;
            }
        }
        if (parentsOfMcrs.isEmpty()) {
            return false;
        }
        parentsOfMcrs.makeIndirect(this.structTreeRoot.getDocument());
        this.parentTree.addEntry(pageStructParentIndex, parentsOfMcrs);
        this.structTreeRoot.getDocument().checkIsoConformance(parentsOfMcrs, IsoKey.TAG_STRUCTURE_ELEMENT);
        parentsOfMcrs.flush();
        return true;
    }

    private int getOrCreatePageStructParentIndex(PdfPage page) {
        int structParentIndex = page.getStructParentIndex();
        if (structParentIndex >= 0) {
            return structParentIndex;
        }
        int structParentIndex2 = page.getDocument().getNextStructParentIndex();
        ((PdfDictionary) page.getPdfObject()).put(PdfName.StructParents, new PdfNumber(structParentIndex2));
        return structParentIndex2;
    }

    private static PdfObject getStm(PdfMcr mcr) {
        if (mcr instanceof PdfMcrDictionary) {
            return ((PdfDictionary) mcr.getPdfObject()).get(PdfName.Stm, false);
        }
        return null;
    }

    static class PageMcrsContainer implements Serializable {
        private static final long serialVersionUID = 8739394375814645643L;
        Map<Integer, PdfMcr> objRefs = new LinkedHashMap();
        NavigableMap<Integer, PdfMcr> pageContentStreams = new TreeMap();
        Map<PdfIndirectReference, TreeMap<Integer, PdfMcr>> pageResourceXObjects = new LinkedHashMap();

        PageMcrsContainer() {
        }

        /* access modifiers changed from: package-private */
        public void putObjectReferenceMcr(int structParentIndex, PdfMcr mcr) {
            this.objRefs.put(Integer.valueOf(structParentIndex), mcr);
        }

        /* access modifiers changed from: package-private */
        public void putPageContentStreamMcr(int mcid, PdfMcr mcr) {
            this.pageContentStreams.put(Integer.valueOf(mcid), mcr);
        }

        /* access modifiers changed from: package-private */
        public void putXObjectMcr(PdfIndirectReference xObjectIndRef, PdfMcr mcr) {
            if (this.pageResourceXObjects.get(xObjectIndRef) == null) {
                this.pageResourceXObjects.put(xObjectIndRef, new TreeMap());
            }
            this.pageResourceXObjects.get(xObjectIndRef).put(Integer.valueOf(mcr.getMcid()), mcr);
        }

        /* access modifiers changed from: package-private */
        public NavigableMap<Integer, PdfMcr> getPageContentStreamsMcrs() {
            return this.pageContentStreams;
        }

        /* access modifiers changed from: package-private */
        public Map<Integer, PdfMcr> getObjRefs() {
            return this.objRefs;
        }

        /* access modifiers changed from: package-private */
        public Map<PdfIndirectReference, TreeMap<Integer, PdfMcr>> getPageResourceXObjects() {
            return this.pageResourceXObjects;
        }

        /* access modifiers changed from: package-private */
        public Collection<PdfMcr> getAllMcrsAsCollection() {
            Collection<PdfMcr> collection = new ArrayList<>();
            collection.addAll(this.objRefs.values());
            collection.addAll(this.pageContentStreams.values());
            for (Map.Entry<PdfIndirectReference, TreeMap<Integer, PdfMcr>> entry : this.pageResourceXObjects.entrySet()) {
                collection.addAll(entry.getValue().values());
            }
            return collection;
        }
    }
}
