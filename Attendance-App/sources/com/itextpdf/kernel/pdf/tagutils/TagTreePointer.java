package com.itextpdf.kernel.pdf.tagutils;

import com.itextpdf.kernel.PdfException;
import com.itextpdf.kernel.pdf.PdfArray;
import com.itextpdf.kernel.pdf.PdfDictionary;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfIndirectReference;
import com.itextpdf.kernel.pdf.PdfName;
import com.itextpdf.kernel.pdf.PdfNumber;
import com.itextpdf.kernel.pdf.PdfObject;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfStream;
import com.itextpdf.kernel.pdf.annot.PdfAnnotation;
import com.itextpdf.kernel.pdf.tagging.IStructureNode;
import com.itextpdf.kernel.pdf.tagging.PdfMcr;
import com.itextpdf.kernel.pdf.tagging.PdfMcrDictionary;
import com.itextpdf.kernel.pdf.tagging.PdfMcrNumber;
import com.itextpdf.kernel.pdf.tagging.PdfNamespace;
import com.itextpdf.kernel.pdf.tagging.PdfObjRef;
import com.itextpdf.kernel.pdf.tagging.PdfStructElem;
import com.itextpdf.kernel.pdf.tagging.PdfStructTreeRoot;
import com.itextpdf.p026io.LogMessageConstant;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.LoggerFactory;

public class TagTreePointer {
    private static final String MCR_MARKER = "MCR";
    private PdfStream contentStream;
    private PdfNamespace currentNamespace;
    private PdfPage currentPage;
    private PdfStructElem currentStructElem;
    private int nextNewKidIndex = -1;
    private TagStructureContext tagStructureContext;

    public TagTreePointer(PdfDocument document) {
        TagStructureContext tagStructureContext2 = document.getTagStructureContext();
        this.tagStructureContext = tagStructureContext2;
        setCurrentStructElem(tagStructureContext2.getRootTag());
        setNamespaceForNewTags(this.tagStructureContext.getDocumentDefaultNamespace());
    }

    public TagTreePointer(TagTreePointer tagPointer) {
        this.tagStructureContext = tagPointer.tagStructureContext;
        setCurrentStructElem(tagPointer.getCurrentStructElem());
        this.currentPage = tagPointer.currentPage;
        this.contentStream = tagPointer.contentStream;
        this.currentNamespace = tagPointer.currentNamespace;
    }

    TagTreePointer(PdfStructElem structElem, PdfDocument document) {
        this.tagStructureContext = document.getTagStructureContext();
        setCurrentStructElem(structElem);
    }

    public TagTreePointer setPageForTagging(PdfPage page) {
        if (!page.isFlushed()) {
            this.currentPage = page;
            return this;
        }
        throw new PdfException(PdfException.PageAlreadyFlushed);
    }

    public PdfPage getCurrentPage() {
        return this.currentPage;
    }

    public TagTreePointer setContentStreamForTagging(PdfStream contentStream2) {
        this.contentStream = contentStream2;
        return this;
    }

    public PdfStream getCurrentContentStream() {
        return this.contentStream;
    }

    public TagStructureContext getContext() {
        return this.tagStructureContext;
    }

    public PdfDocument getDocument() {
        return this.tagStructureContext.getDocument();
    }

    public TagTreePointer setNamespaceForNewTags(PdfNamespace namespace) {
        this.currentNamespace = namespace;
        return this;
    }

    public PdfNamespace getNamespaceForNewTags() {
        return this.currentNamespace;
    }

    public TagTreePointer addTag(String role) {
        addTag(-1, role);
        return this;
    }

    public TagTreePointer addTag(int index, String role) {
        this.tagStructureContext.throwExceptionIfRoleIsInvalid(role, this.currentNamespace);
        setNextNewKidIndex(index);
        setCurrentStructElem(addNewKid(role));
        return this;
    }

    public TagTreePointer addTag(AccessibilityProperties properties) {
        addTag(-1, properties);
        return this;
    }

    public TagTreePointer addTag(int index, AccessibilityProperties properties) {
        this.tagStructureContext.throwExceptionIfRoleIsInvalid(properties, this.currentNamespace);
        setNextNewKidIndex(index);
        setCurrentStructElem(addNewKid(properties));
        return this;
    }

    public TagTreePointer addAnnotationTag(PdfAnnotation annotation) {
        throwExceptionIfCurrentPageIsNotInited();
        PdfObjRef kid = new PdfObjRef(annotation, getCurrentStructElem(), getDocument().getNextStructParentIndex());
        if (!ensureElementPageEqualsKidPage(getCurrentStructElem(), (PdfDictionary) this.currentPage.getPdfObject())) {
            ((PdfDictionary) kid.getPdfObject()).put(PdfName.f1374Pg, ((PdfDictionary) this.currentPage.getPdfObject()).getIndirectReference());
        }
        addNewKid((PdfMcr) kid);
        return this;
    }

    public TagTreePointer setNextNewKidIndex(int nextNewKidIndex2) {
        if (nextNewKidIndex2 > -1) {
            this.nextNewKidIndex = nextNewKidIndex2;
        }
        return this;
    }

    public TagTreePointer removeTag() {
        PdfStructElem currentStructElem2 = getCurrentStructElem();
        IStructureNode parentElem = currentStructElem2.getParent();
        if (!(parentElem instanceof PdfStructTreeRoot)) {
            List<IStructureNode> kids = currentStructElem2.getKids();
            PdfStructElem parent = (PdfStructElem) parentElem;
            if (!parent.isFlushed()) {
                this.tagStructureContext.getWaitingTagsManager().removeWaitingState(this.tagStructureContext.getWaitingTagsManager().getObjForStructDict((PdfDictionary) currentStructElem2.getPdfObject()));
                int removedKidIndex = parent.removeKid((IStructureNode) currentStructElem2);
                PdfIndirectReference indRef = ((PdfDictionary) currentStructElem2.getPdfObject()).getIndirectReference();
                if (indRef != null) {
                    indRef.setFree();
                }
                for (IStructureNode kid : kids) {
                    if (kid instanceof PdfStructElem) {
                        parent.addKid(removedKidIndex, (PdfStructElem) kid);
                        removedKidIndex++;
                    } else {
                        parent.addKid(removedKidIndex, prepareMcrForMovingToNewParent((PdfMcr) kid, parent));
                        removedKidIndex++;
                    }
                }
                ((PdfDictionary) currentStructElem2.getPdfObject()).clear();
                setCurrentStructElem(parent);
                return this;
            }
            throw new PdfException(PdfException.CannotRemoveTagBecauseItsParentIsFlushed);
        }
        throw new PdfException(PdfException.CannotRemoveDocumentRootTag);
    }

    public TagTreePointer relocateKid(int kidIndex, TagTreePointer pointerToNewParent) {
        if (getDocument() != pointerToNewParent.getDocument()) {
            throw new PdfException(PdfException.TagCannotBeMovedToTheAnotherDocumentsTagStructure);
        } else if (!getCurrentStructElem().isFlushed()) {
            if (isPointingToSameTag(pointerToNewParent)) {
                int i = pointerToNewParent.nextNewKidIndex;
                if (kidIndex == i) {
                    return this;
                }
                if (kidIndex < i) {
                    pointerToNewParent.setNextNewKidIndex(i - 1);
                }
            }
            if (getCurrentStructElem().getKids().get(kidIndex) != null) {
                IStructureNode removedKid = getCurrentStructElem().removeKid(kidIndex, true);
                if (removedKid instanceof PdfStructElem) {
                    pointerToNewParent.addNewKid((PdfStructElem) removedKid);
                } else if (removedKid instanceof PdfMcr) {
                    pointerToNewParent.addNewKid(prepareMcrForMovingToNewParent((PdfMcr) removedKid, pointerToNewParent.getCurrentStructElem()));
                }
                return this;
            }
            throw new PdfException(PdfException.CannotRelocateTagWhichIsAlreadyFlushed);
        } else {
            throw new PdfException(PdfException.CannotRelocateTagWhichParentIsAlreadyFlushed);
        }
    }

    public TagTreePointer relocate(TagTreePointer pointerToNewParent) {
        if (getCurrentStructElem().getPdfObject() == this.tagStructureContext.getRootTag().getPdfObject()) {
            throw new PdfException(PdfException.CannotRelocateRootTag);
        } else if (!getCurrentStructElem().isFlushed()) {
            int i = getIndexInParentKidsList();
            if (i >= 0) {
                new TagTreePointer(this).moveToParent().relocateKid(i, pointerToNewParent);
                return this;
            }
            throw new PdfException(PdfException.CannotRelocateTagWhichParentIsAlreadyFlushed);
        } else {
            throw new PdfException(PdfException.CannotRelocateTagWhichIsAlreadyFlushed);
        }
    }

    public TagReference getTagReference() {
        return getTagReference(-1);
    }

    public TagReference getTagReference(int index) {
        return new TagReference(getCurrentElemEnsureIndirect(), this, index);
    }

    public TagTreePointer moveToRoot() {
        setCurrentStructElem(this.tagStructureContext.getRootTag());
        return this;
    }

    public TagTreePointer moveToParent() {
        if (getCurrentStructElem().getPdfObject() != this.tagStructureContext.getRootTag().getPdfObject()) {
            PdfStructElem parent = (PdfStructElem) getCurrentStructElem().getParent();
            if (parent.isFlushed()) {
                LoggerFactory.getLogger((Class<?>) TagTreePointer.class).warn(LogMessageConstant.ATTEMPT_TO_MOVE_TO_FLUSHED_PARENT);
                moveToRoot();
            } else {
                setCurrentStructElem(parent);
            }
            return this;
        }
        throw new PdfException(PdfException.CannotMoveToParentCurrentElementIsRoot);
    }

    public TagTreePointer moveToKid(int kidIndex) {
        IStructureNode kid = getCurrentStructElem().getKids().get(kidIndex);
        if (kid instanceof PdfStructElem) {
            setCurrentStructElem((PdfStructElem) kid);
            return this;
        } else if (kid instanceof PdfMcr) {
            throw new PdfException(PdfException.CannotMoveToMarkedContentReference);
        } else {
            throw new PdfException(PdfException.CannotMoveToFlushedKid);
        }
    }

    public TagTreePointer moveToKid(String role) {
        moveToKid(0, role);
        return this;
    }

    public TagTreePointer moveToKid(int n, String role) {
        if (!MCR_MARKER.equals(role)) {
            List<IStructureNode> descendants = new ArrayList<>(getCurrentStructElem().getKids());
            int k = 0;
            for (int i = 0; i < descendants.size(); i++) {
                if (descendants.get(i) != null && !(descendants.get(i) instanceof PdfMcr)) {
                    if (descendants.get(i).getRole().getValue().equals(role)) {
                        int k2 = k + 1;
                        if (k == n) {
                            setCurrentStructElem((PdfStructElem) descendants.get(i));
                            return this;
                        }
                        k = k2;
                    }
                    descendants.addAll(descendants.get(i).getKids());
                }
            }
            throw new PdfException(PdfException.NoKidWithSuchRole);
        }
        throw new PdfException(PdfException.CannotMoveToMarkedContentReference);
    }

    public List<String> getKidsRoles() {
        List<String> roles = new ArrayList<>();
        for (IStructureNode kid : getCurrentStructElem().getKids()) {
            if (kid == null) {
                roles.add((Object) null);
            } else if (kid instanceof PdfStructElem) {
                roles.add(kid.getRole().getValue());
            } else {
                roles.add(MCR_MARKER);
            }
        }
        return roles;
    }

    public TagTreePointer flushTag() {
        if (getCurrentStructElem().getPdfObject() != this.tagStructureContext.getRootTag().getPdfObject()) {
            IStructureNode parent = this.tagStructureContext.getWaitingTagsManager().flushTag(getCurrentStructElem());
            if (parent != null) {
                setCurrentStructElem((PdfStructElem) parent);
            } else {
                setCurrentStructElem(this.tagStructureContext.getRootTag());
            }
            return this;
        }
        throw new PdfException(PdfException.CannotFlushDocumentRootTagBeforeDocumentIsClosed);
    }

    public TagTreePointer flushParentsIfAllKidsFlushed() {
        getContext().flushParentIfBelongsToPage(getCurrentStructElem(), (PdfPage) null);
        return this;
    }

    public AccessibilityProperties getProperties() {
        return new BackedAccessibilityProperties(this);
    }

    public String getRole() {
        return getCurrentStructElem().getRole().getValue();
    }

    public TagTreePointer setRole(String role) {
        getCurrentStructElem().setRole(PdfStructTreeRoot.convertRoleToPdfName(role));
        return this;
    }

    public int getIndexInParentKidsList() {
        if (getCurrentStructElem().getPdfObject() == this.tagStructureContext.getRootTag().getPdfObject()) {
            return -1;
        }
        PdfStructElem parent = (PdfStructElem) getCurrentStructElem().getParent();
        if (parent.isFlushed()) {
            return -1;
        }
        PdfObject k = parent.getK();
        if (k == getCurrentStructElem().getPdfObject()) {
            return 0;
        }
        if (k.isArray()) {
            return ((PdfArray) k).indexOf(getCurrentStructElem().getPdfObject());
        }
        return -1;
    }

    public TagTreePointer moveToPointer(TagTreePointer tagTreePointer) {
        this.currentStructElem = tagTreePointer.currentStructElem;
        return this;
    }

    public boolean isPointingToSameTag(TagTreePointer otherPointer) {
        return ((PdfDictionary) getCurrentStructElem().getPdfObject()).equals(otherPointer.getCurrentStructElem().getPdfObject());
    }

    /* access modifiers changed from: package-private */
    public int createNextMcidForStructElem(PdfStructElem elem, int index) {
        PdfMcr mcr;
        throwExceptionIfCurrentPageIsNotInited();
        if (markedContentNotInPageStream() || !ensureElementPageEqualsKidPage(elem, (PdfDictionary) this.currentPage.getPdfObject())) {
            mcr = new PdfMcrDictionary(this.currentPage, elem);
            if (markedContentNotInPageStream()) {
                ((PdfDictionary) mcr.getPdfObject()).put(PdfName.Stm, this.contentStream);
            }
        } else {
            mcr = new PdfMcrNumber(this.currentPage, elem);
        }
        elem.addKid(index, mcr);
        return mcr.getMcid();
    }

    /* access modifiers changed from: package-private */
    public TagTreePointer setCurrentStructElem(PdfStructElem structElem) {
        if (structElem.getParent() != null) {
            this.currentStructElem = structElem;
            return this;
        }
        throw new PdfException(PdfException.StructureElementShallContainParentObject);
    }

    /* access modifiers changed from: package-private */
    public PdfStructElem getCurrentStructElem() {
        if (!this.currentStructElem.isFlushed()) {
            PdfIndirectReference indRef = ((PdfDictionary) this.currentStructElem.getPdfObject()).getIndirectReference();
            if (indRef == null || !indRef.isFree()) {
                return this.currentStructElem;
            }
            throw new PdfException(PdfException.f1246x67c488aa);
        }
        throw new PdfException(PdfException.f1245xc7bb476d);
    }

    private int getNextNewKidPosition() {
        int nextPos = this.nextNewKidIndex;
        this.nextNewKidIndex = -1;
        return nextPos;
    }

    private PdfStructElem addNewKid(String role) {
        PdfStructElem kid = new PdfStructElem(getDocument(), PdfStructTreeRoot.convertRoleToPdfName(role));
        processKidNamespace(kid);
        return addNewKid(kid);
    }

    private PdfStructElem addNewKid(AccessibilityProperties properties) {
        PdfStructElem kid = new PdfStructElem(getDocument(), PdfStructTreeRoot.convertRoleToPdfName(properties.getRole()));
        AccessibilityPropertiesToStructElem.apply(properties, kid);
        processKidNamespace(kid);
        return addNewKid(kid);
    }

    private void processKidNamespace(PdfStructElem kid) {
        PdfNamespace kidNamespace = kid.getNamespace();
        PdfNamespace pdfNamespace = this.currentNamespace;
        if (pdfNamespace != null && kidNamespace == null) {
            kid.setNamespace(pdfNamespace);
            kidNamespace = this.currentNamespace;
        }
        this.tagStructureContext.ensureNamespaceRegistered(kidNamespace);
    }

    private PdfStructElem addNewKid(PdfStructElem kid) {
        return getCurrentElemEnsureIndirect().addKid(getNextNewKidPosition(), kid);
    }

    private PdfMcr addNewKid(PdfMcr kid) {
        return getCurrentElemEnsureIndirect().addKid(getNextNewKidPosition(), kid);
    }

    private PdfStructElem getCurrentElemEnsureIndirect() {
        PdfStructElem currentStructElem2 = getCurrentStructElem();
        if (((PdfDictionary) currentStructElem2.getPdfObject()).getIndirectReference() == null) {
            currentStructElem2.makeIndirect(getDocument());
        }
        return currentStructElem2;
    }

    private PdfMcr prepareMcrForMovingToNewParent(PdfMcr mcrKid, PdfStructElem newParent) {
        PdfObject mcrObject = mcrKid.getPdfObject();
        PdfDictionary mcrPage = mcrKid.getPageObject();
        PdfDictionary mcrDict = null;
        if (!mcrObject.isNumber()) {
            mcrDict = (PdfDictionary) mcrObject;
        }
        if ((mcrDict == null || !mcrDict.containsKey(PdfName.f1374Pg)) && !ensureElementPageEqualsKidPage(newParent, mcrPage)) {
            if (mcrDict == null) {
                mcrDict = new PdfDictionary();
                mcrDict.put(PdfName.Type, PdfName.MCR);
                mcrDict.put(PdfName.MCID, mcrKid.getPdfObject());
            }
            mcrDict.put(PdfName.f1374Pg, mcrPage.getIndirectReference());
        }
        if (mcrDict == null) {
            return new PdfMcrNumber((PdfNumber) mcrObject, newParent);
        }
        if (PdfName.MCR.equals(mcrDict.get(PdfName.Type))) {
            return new PdfMcrDictionary(mcrDict, newParent);
        }
        if (PdfName.OBJR.equals(mcrDict.get(PdfName.Type))) {
            return new PdfObjRef(mcrDict, newParent);
        }
        return mcrKid;
    }

    private boolean ensureElementPageEqualsKidPage(PdfStructElem elem, PdfDictionary kidPage) {
        PdfObject pageObject = ((PdfDictionary) elem.getPdfObject()).get(PdfName.f1374Pg);
        if (pageObject == null) {
            pageObject = kidPage;
            elem.put(PdfName.f1374Pg, kidPage.getIndirectReference());
        }
        return kidPage.equals(pageObject);
    }

    private boolean markedContentNotInPageStream() {
        return this.contentStream != null;
    }

    private void throwExceptionIfCurrentPageIsNotInited() {
        if (this.currentPage == null) {
            throw new PdfException(PdfException.PageIsNotSetForThePdfTagStructure);
        }
    }
}
