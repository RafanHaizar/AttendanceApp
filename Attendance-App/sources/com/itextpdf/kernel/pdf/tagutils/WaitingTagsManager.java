package com.itextpdf.kernel.pdf.tagutils;

import com.itextpdf.kernel.pdf.PdfDictionary;
import com.itextpdf.kernel.pdf.tagging.IStructureNode;
import com.itextpdf.kernel.pdf.tagging.PdfStructElem;
import java.util.HashMap;
import java.util.Map;

public class WaitingTagsManager {
    private Map<Object, PdfStructElem> associatedObjToWaitingTag = new HashMap();
    private Map<PdfDictionary, Object> waitingTagToAssociatedObj = new HashMap();

    WaitingTagsManager() {
    }

    public Object assignWaitingState(TagTreePointer pointerToTag, Object associatedObj) {
        if (associatedObj != null) {
            return saveAssociatedObjectForWaitingTag(associatedObj, pointerToTag.getCurrentStructElem());
        }
        throw new IllegalArgumentException("Passed associated object can not be null.");
    }

    public boolean isObjectAssociatedWithWaitingTag(Object obj) {
        if (obj != null) {
            return this.associatedObjToWaitingTag.containsKey(obj);
        }
        throw new IllegalArgumentException("Passed associated object can not be null.");
    }

    public boolean tryMovePointerToWaitingTag(TagTreePointer tagPointer, Object associatedObject) {
        PdfStructElem waitingStructElem;
        if (associatedObject == null || (waitingStructElem = this.associatedObjToWaitingTag.get(associatedObject)) == null) {
            return false;
        }
        tagPointer.setCurrentStructElem(waitingStructElem);
        return true;
    }

    public boolean removeWaitingState(Object associatedObject) {
        if (associatedObject == null) {
            return false;
        }
        PdfStructElem structElem = this.associatedObjToWaitingTag.remove(associatedObject);
        removeWaitingStateAndFlushIfParentFlushed(structElem);
        if (structElem != null) {
            return true;
        }
        return false;
    }

    public void removeAllWaitingStates() {
        for (PdfStructElem structElem : this.associatedObjToWaitingTag.values()) {
            removeWaitingStateAndFlushIfParentFlushed(structElem);
        }
        this.associatedObjToWaitingTag.clear();
    }

    /* access modifiers changed from: package-private */
    public PdfStructElem getStructForObj(Object associatedObj) {
        return this.associatedObjToWaitingTag.get(associatedObj);
    }

    /* access modifiers changed from: package-private */
    public Object getObjForStructDict(PdfDictionary structDict) {
        return this.waitingTagToAssociatedObj.get(structDict);
    }

    /* access modifiers changed from: package-private */
    public Object saveAssociatedObjectForWaitingTag(Object associatedObj, PdfStructElem structElem) {
        this.associatedObjToWaitingTag.put(associatedObj, structElem);
        return this.waitingTagToAssociatedObj.put(structElem.getPdfObject(), associatedObj);
    }

    /* access modifiers changed from: package-private */
    public IStructureNode flushTag(PdfStructElem tagStruct) {
        Object associatedObj = this.waitingTagToAssociatedObj.remove(tagStruct.getPdfObject());
        if (associatedObj != null) {
            this.associatedObjToWaitingTag.remove(associatedObj);
        }
        IStructureNode parent = tagStruct.getParent();
        flushStructElementAndItKids(tagStruct);
        return parent;
    }

    private void flushStructElementAndItKids(PdfStructElem elem) {
        if (!this.waitingTagToAssociatedObj.containsKey(elem.getPdfObject())) {
            for (IStructureNode kid : elem.getKids()) {
                if (kid instanceof PdfStructElem) {
                    flushStructElementAndItKids((PdfStructElem) kid);
                }
            }
            elem.flush();
        }
    }

    private void removeWaitingStateAndFlushIfParentFlushed(PdfStructElem structElem) {
        if (structElem != null) {
            this.waitingTagToAssociatedObj.remove(structElem.getPdfObject());
            IStructureNode parent = structElem.getParent();
            if ((parent instanceof PdfStructElem) && ((PdfStructElem) parent).isFlushed()) {
                flushStructElementAndItKids(structElem);
            }
        }
    }
}
