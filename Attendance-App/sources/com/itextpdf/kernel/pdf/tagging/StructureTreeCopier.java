package com.itextpdf.kernel.pdf.tagging;

import com.itextpdf.kernel.PdfException;
import com.itextpdf.kernel.pdf.PdfArray;
import com.itextpdf.kernel.pdf.PdfDictionary;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfName;
import com.itextpdf.kernel.pdf.PdfNumber;
import com.itextpdf.kernel.pdf.PdfObject;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.p026io.LogMessageConstant;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.slf4j.LoggerFactory;

class StructureTreeCopier {
    private static List<PdfName> ignoreKeysForClone = new ArrayList();
    private static List<PdfName> ignoreKeysForCopy = new ArrayList();

    StructureTreeCopier() {
    }

    static {
        ignoreKeysForCopy.add(PdfName.f1344K);
        ignoreKeysForCopy.add(PdfName.f1367P);
        ignoreKeysForCopy.add(PdfName.f1374Pg);
        ignoreKeysForCopy.add(PdfName.Obj);
        ignoreKeysForCopy.add(PdfName.f1360NS);
        ignoreKeysForClone.add(PdfName.f1344K);
        ignoreKeysForClone.add(PdfName.f1367P);
    }

    public static void copyTo(PdfDocument destDocument, Map<PdfPage, PdfPage> page2page, PdfDocument callingDocument) {
        if (destDocument.isTagged()) {
            copyTo(destDocument, page2page, callingDocument, false);
        }
    }

    public static void copyTo(PdfDocument destDocument, int insertBeforePage, Map<PdfPage, PdfPage> page2page, PdfDocument callingDocument) {
        if (destDocument.isTagged()) {
            copyTo(destDocument, insertBeforePage, page2page, callingDocument, false);
        }
    }

    public static void move(PdfDocument document, PdfPage from, int insertBefore) {
        int fromNum;
        int currStruct;
        if (document.isTagged() && insertBefore >= 1 && insertBefore <= document.getNumberOfPages() + 1 && (fromNum = document.getPageNumber(from)) != 0 && fromNum != insertBefore && fromNum + 1 != insertBefore) {
            if (fromNum > insertBefore) {
                currStruct = separateStructure(document, 1, insertBefore, 0);
                int currStruct2 = separateStructure(document, fromNum, fromNum + 1, separateStructure(document, insertBefore, fromNum, currStruct));
            } else {
                int separateStructure = separateStructure(document, fromNum + 1, insertBefore, separateStructure(document, fromNum, fromNum + 1, separateStructure(document, 1, fromNum, 0)));
                int currStruct3 = separateStructure;
                currStruct = separateStructure;
            }
            Set<PdfDictionary> topsToMove = new HashSet<>();
            Collection<PdfMcr> mcrs = document.getStructTreeRoot().getPageMarkedContentReferences(from);
            if (mcrs != null) {
                for (PdfMcr mcr : mcrs) {
                    PdfDictionary top = getTopmostParent(mcr);
                    if (top != null) {
                        if (!top.isFlushed()) {
                            topsToMove.add(top);
                        } else {
                            throw new PdfException(PdfException.CannotMoveFlushedTag);
                        }
                    }
                }
            }
            List<PdfDictionary> orderedTopsToMove = new ArrayList<>();
            PdfArray tops = document.getStructTreeRoot().getKidsObject();
            for (int i = 0; i < tops.size(); i++) {
                PdfDictionary top2 = tops.getAsDictionary(i);
                if (topsToMove.contains(top2)) {
                    orderedTopsToMove.add(top2);
                    tops.remove(i);
                    if (i < currStruct) {
                        currStruct--;
                    }
                }
            }
            for (PdfDictionary top3 : orderedTopsToMove) {
                document.getStructTreeRoot().addKidObject(currStruct, top3);
                currStruct++;
            }
        }
    }

    private static int separateStructure(PdfDocument document, int beforePage) {
        return separateStructure(document, 1, beforePage, 0);
    }

    private static int separateStructure(PdfDocument document, int startPage, int beforePage, int startPageStructTopIndex) {
        if (!document.isTagged() || 1 > startPage || startPage > beforePage || beforePage > document.getNumberOfPages() + 1) {
            return -1;
        }
        if (beforePage == startPage) {
            return startPageStructTopIndex;
        }
        if (beforePage == document.getNumberOfPages() + 1) {
            return document.getStructTreeRoot().getKidsObject().size();
        }
        Set<PdfObject> firstPartElems = new HashSet<>();
        for (int i = startPage; i < beforePage; i++) {
            Collection<PdfMcr> pageMcrs = document.getStructTreeRoot().getPageMarkedContentReferences(document.getPage(i));
            if (pageMcrs != null) {
                for (PdfMcr mcr : pageMcrs) {
                    firstPartElems.add(mcr.getPdfObject());
                    PdfDictionary top = addAllParentsToSet(mcr, firstPartElems);
                    if (top != null && top.isFlushed()) {
                        throw new PdfException(PdfException.TagFromTheExistingTagStructureIsFlushedCannotAddCopiedPageTags);
                    }
                }
                continue;
            }
        }
        List<PdfDictionary> clonedTops = new ArrayList<>();
        PdfArray tops = document.getStructTreeRoot().getKidsObject();
        int lastTopBefore = startPageStructTopIndex - 1;
        for (int i2 = 0; i2 < tops.size(); i2++) {
            PdfDictionary top2 = tops.getAsDictionary(i2);
            if (firstPartElems.contains(top2)) {
                lastTopBefore = i2;
                LastClonedAncestor lastCloned = new LastClonedAncestor();
                lastCloned.ancestor = top2;
                PdfDictionary topClone = top2.clone(ignoreKeysForClone);
                topClone.put(PdfName.f1367P, document.getStructTreeRoot().getPdfObject());
                lastCloned.clone = topClone;
                separateKids(top2, firstPartElems, lastCloned, document);
                if (topClone.containsKey(PdfName.f1344K)) {
                    topClone.makeIndirect(document);
                    clonedTops.add(topClone);
                }
            }
        }
        for (int i3 = 0; i3 < clonedTops.size(); i3++) {
            document.getStructTreeRoot().addKidObject(lastTopBefore + 1 + i3, clonedTops.get(i3));
        }
        return lastTopBefore + 1;
    }

    private static void copyTo(PdfDocument destDocument, int insertBeforePage, Map<PdfPage, PdfPage> page2page, PdfDocument callingDocument, boolean copyFromDestDocument) {
        int insertIndex;
        if (destDocument.isTagged() && (insertIndex = separateStructure(destDocument, insertBeforePage)) > 0) {
            copyTo(destDocument, page2page, callingDocument, copyFromDestDocument, insertIndex);
        }
    }

    private static void copyTo(PdfDocument destDocument, Map<PdfPage, PdfPage> page2page, PdfDocument callingDocument, boolean copyFromDestDocument) {
        copyTo(destDocument, page2page, callingDocument, copyFromDestDocument, -1);
    }

    private static void copyTo(PdfDocument destDocument, Map<PdfPage, PdfPage> page2page, PdfDocument callingDocument, boolean copyFromDestDocument, int insertIndex) {
        CopyStructureResult copiedStructure = copyStructure(destDocument, page2page, callingDocument, copyFromDestDocument);
        PdfStructTreeRoot destStructTreeRoot = destDocument.getStructTreeRoot();
        destStructTreeRoot.makeIndirect(destDocument);
        for (PdfDictionary copied : copiedStructure.getTopsList()) {
            destStructTreeRoot.addKidObject(insertIndex, copied);
            if (insertIndex > -1) {
                insertIndex++;
            }
        }
        if (!copyFromDestDocument) {
            if (!copiedStructure.getCopiedNamespaces().isEmpty()) {
                destStructTreeRoot.getNamespacesObject().addAll((Collection<PdfObject>) copiedStructure.getCopiedNamespaces());
            }
            PdfDictionary srcRoleMap = callingDocument.getStructTreeRoot().getRoleMap();
            PdfDictionary destRoleMap = destStructTreeRoot.getRoleMap();
            for (Map.Entry<PdfName, PdfObject> mappingEntry : srcRoleMap.entrySet()) {
                if (!destRoleMap.containsKey(mappingEntry.getKey())) {
                    destRoleMap.put(mappingEntry.getKey(), mappingEntry.getValue());
                } else if (!mappingEntry.getValue().equals(destRoleMap.get(mappingEntry.getKey()))) {
                    LoggerFactory.getLogger((Class<?>) StructureTreeCopier.class).warn(MessageFormat.format(LogMessageConstant.ROLE_MAPPING_FROM_SOURCE_IS_NOT_COPIED_ALREADY_EXIST, new Object[]{mappingEntry.getKey() + " -> " + mappingEntry.getValue(), mappingEntry.getKey() + " -> " + destRoleMap.get(mappingEntry.getKey())}));
                }
            }
        }
    }

    private static CopyStructureResult copyStructure(PdfDocument destDocument, Map<PdfPage, PdfPage> page2page, PdfDocument callingDocument, boolean copyFromDestDocument) {
        PdfDocument fromDocument = copyFromDestDocument ? destDocument : callingDocument;
        Map<PdfDictionary, PdfDictionary> topsToFirstDestPage = new HashMap<>();
        Set<PdfObject> objectsToCopy = new HashSet<>();
        Map<PdfDictionary, PdfDictionary> page2pageDictionaries = new HashMap<>();
        for (Map.Entry<PdfPage, PdfPage> page : page2page.entrySet()) {
            page2pageDictionaries.put(page.getKey().getPdfObject(), page.getValue().getPdfObject());
            Collection<PdfMcr> mcrs = fromDocument.getStructTreeRoot().getPageMarkedContentReferences(page.getKey());
            if (mcrs != null) {
                for (PdfMcr mcr : mcrs) {
                    if ((mcr instanceof PdfMcrDictionary) || (mcr instanceof PdfObjRef)) {
                        objectsToCopy.add(mcr.getPdfObject());
                    }
                    PdfDictionary top = addAllParentsToSet(mcr, objectsToCopy);
                    if (top != null) {
                        if (top.isFlushed()) {
                            throw new PdfException(PdfException.CannotCopyFlushedTag);
                        } else if (!topsToFirstDestPage.containsKey(top)) {
                            topsToFirstDestPage.put(top, page.getValue().getPdfObject());
                        }
                    }
                }
                continue;
            }
        }
        List<PdfDictionary> topsInOriginalOrder = new ArrayList<>();
        for (IStructureNode kid : fromDocument.getStructTreeRoot().getKids()) {
            if (kid != null) {
                PdfDictionary kidObject = (PdfDictionary) ((PdfStructElem) kid).getPdfObject();
                if (topsToFirstDestPage.containsKey(kidObject)) {
                    topsInOriginalOrder.add(kidObject);
                }
            }
        }
        StructElemCopyingParams structElemCopyingParams = new StructElemCopyingParams(objectsToCopy, destDocument, page2pageDictionaries, copyFromDestDocument);
        destDocument.getStructTreeRoot().makeIndirect(destDocument);
        List<PdfDictionary> copiedTops = new ArrayList<>();
        for (PdfDictionary top2 : topsInOriginalOrder) {
            copiedTops.add(copyObject(top2, topsToFirstDestPage.get(top2), false, structElemCopyingParams));
        }
        return new CopyStructureResult(copiedTops, structElemCopyingParams.getCopiedNamespaces());
    }

    private static PdfDictionary copyObject(PdfDictionary source, PdfDictionary destPage, boolean parentChangePg, StructElemCopyingParams copyingParams) {
        PdfDictionary copied;
        if (copyingParams.isCopyFromDestDocument()) {
            copied = source.clone(ignoreKeysForClone);
            if (source.isIndirect()) {
                copied.makeIndirect(copyingParams.getToDocument());
            }
            PdfDictionary pg = source.getAsDictionary(PdfName.f1374Pg);
            if (pg != null && copyingParams.isCopyFromDestDocument()) {
                if (pg != destPage) {
                    copied.put(PdfName.f1374Pg, destPage);
                    parentChangePg = true;
                } else {
                    parentChangePg = false;
                }
            }
        } else {
            copied = source.copyTo(copyingParams.getToDocument(), ignoreKeysForCopy, true);
            PdfDictionary obj = source.getAsDictionary(PdfName.Obj);
            if (obj != null) {
                copied.put(PdfName.Obj, obj.copyTo(copyingParams.getToDocument(), Arrays.asList(new PdfName[]{PdfName.f1367P}), false));
            }
            PdfDictionary nsDict = source.getAsDictionary(PdfName.f1360NS);
            if (nsDict != null) {
                copied.put(PdfName.f1360NS, copyNamespaceDict(nsDict, copyingParams));
            }
            PdfDictionary pg2 = source.getAsDictionary(PdfName.f1374Pg);
            if (pg2 != null) {
                PdfDictionary pageAnalog = copyingParams.getPage2page().get(pg2);
                if (pageAnalog == null) {
                    pageAnalog = destPage;
                    parentChangePg = true;
                } else {
                    parentChangePg = false;
                }
                copied.put(PdfName.f1374Pg, pageAnalog);
            }
        }
        PdfObject k = source.get(PdfName.f1344K);
        if (k != null) {
            if (k.isArray()) {
                PdfArray kArr = (PdfArray) k;
                PdfArray newArr = new PdfArray();
                for (int i = 0; i < kArr.size(); i++) {
                    PdfObject copiedKid = copyObjectKid(kArr.get(i), copied, destPage, parentChangePg, copyingParams);
                    if (copiedKid != null) {
                        newArr.add(copiedKid);
                    }
                }
                if (newArr.isEmpty() == 0) {
                    if (newArr.size() == 1) {
                        copied.put(PdfName.f1344K, newArr.get(0));
                    } else {
                        copied.put(PdfName.f1344K, newArr);
                    }
                }
            } else {
                PdfObject copiedKid2 = copyObjectKid(k, copied, destPage, parentChangePg, copyingParams);
                if (copiedKid2 != null) {
                    copied.put(PdfName.f1344K, copiedKid2);
                }
            }
        }
        return copied;
    }

    private static PdfObject copyObjectKid(PdfObject kid, PdfDictionary copiedParent, PdfDictionary destPage, boolean parentChangePg, StructElemCopyingParams copyingParams) {
        PdfMcr mcr;
        if (kid.isNumber()) {
            if (!parentChangePg) {
                copyingParams.getToDocument().getStructTreeRoot().getParentTreeHandler().registerMcr(new PdfMcrNumber((PdfNumber) kid, new PdfStructElem(copiedParent)));
                return kid;
            }
        } else if (kid.isDictionary()) {
            PdfDictionary kidAsDict = (PdfDictionary) kid;
            if (copyingParams.getObjectsToCopy().contains(kidAsDict)) {
                boolean hasParent = kidAsDict.containsKey(PdfName.f1367P);
                PdfDictionary copiedKid = copyObject(kidAsDict, destPage, parentChangePg, copyingParams);
                if (hasParent) {
                    copiedKid.put(PdfName.f1367P, copiedParent);
                } else {
                    if (copiedKid.containsKey(PdfName.Obj)) {
                        mcr = new PdfObjRef(copiedKid, new PdfStructElem(copiedParent));
                        PdfDictionary contentItemObject = copiedKid.getAsDictionary(PdfName.Obj);
                        if (PdfName.Link.equals(contentItemObject.getAsName(PdfName.Subtype)) && !contentItemObject.containsKey(PdfName.f1367P)) {
                            return null;
                        }
                        contentItemObject.put(PdfName.StructParent, new PdfNumber(copyingParams.getToDocument().getNextStructParentIndex()));
                    } else {
                        mcr = new PdfMcrDictionary(copiedKid, new PdfStructElem(copiedParent));
                    }
                    copyingParams.getToDocument().getStructTreeRoot().getParentTreeHandler().registerMcr(mcr);
                }
                return copiedKid;
            }
        }
        return null;
    }

    private static PdfDictionary copyNamespaceDict(PdfDictionary srcNsDict, StructElemCopyingParams copyingParams) {
        PdfObject copiedMapping;
        List<PdfName> excludeKeys = Collections.singletonList(PdfName.RoleMapNS);
        PdfDocument toDocument = copyingParams.getToDocument();
        PdfDictionary copiedNsDict = srcNsDict.copyTo(toDocument, excludeKeys, false);
        copyingParams.addCopiedNamespace(copiedNsDict);
        PdfDictionary srcRoleMapNs = srcNsDict.getAsDictionary(PdfName.RoleMapNS);
        PdfDictionary copiedRoleMap = copiedNsDict.getAsDictionary(PdfName.RoleMapNS);
        if (srcRoleMapNs != null && copiedRoleMap == null) {
            PdfDictionary copiedRoleMap2 = new PdfDictionary();
            copiedNsDict.put(PdfName.RoleMapNS, copiedRoleMap2);
            for (Map.Entry<PdfName, PdfObject> entry : srcRoleMapNs.entrySet()) {
                if (entry.getValue().isArray()) {
                    PdfArray srcMappingArray = (PdfArray) entry.getValue();
                    if (srcMappingArray.size() <= 1 || !srcMappingArray.get(1).isDictionary()) {
                        LoggerFactory.getLogger((Class<?>) StructureTreeCopier.class).warn(MessageFormat.format(LogMessageConstant.ROLE_MAPPING_FROM_SOURCE_IS_NOT_COPIED_INVALID, new Object[]{entry.getKey().toString()}));
                    } else {
                        PdfArray copiedMappingArray = new PdfArray();
                        copiedMappingArray.add(srcMappingArray.get(0).copyTo(toDocument));
                        copiedMappingArray.add(copyNamespaceDict(srcMappingArray.getAsDictionary(1), copyingParams));
                        copiedMapping = copiedMappingArray;
                    }
                } else {
                    copiedMapping = entry.getValue().copyTo(toDocument);
                }
                copiedRoleMap2.put((PdfName) entry.getKey().copyTo(toDocument), copiedMapping);
            }
        }
        return copiedNsDict;
    }

    private static void separateKids(PdfDictionary structElem, Set<PdfObject> firstPartElems, LastClonedAncestor lastCloned, PdfDocument document) {
        PdfMcr mcr;
        PdfObject k = structElem.get(PdfName.f1344K);
        if (k.isArray()) {
            PdfArray kids = (PdfArray) k;
            int i = 0;
            while (i < kids.size()) {
                PdfObject kid = kids.get(i);
                PdfDictionary dictKid = null;
                if (kid.isDictionary()) {
                    dictKid = (PdfDictionary) kid;
                }
                if (dictKid == null || !PdfStructElem.isStructElem(dictKid)) {
                    if (!firstPartElems.contains(kid)) {
                        cloneParents(structElem, lastCloned, document);
                        if (dictKid == null) {
                            mcr = new PdfMcrNumber((PdfNumber) kid, new PdfStructElem(lastCloned.clone));
                        } else if (dictKid.get(PdfName.Type).equals(PdfName.MCR)) {
                            mcr = new PdfMcrDictionary(dictKid, new PdfStructElem(lastCloned.clone));
                        } else {
                            mcr = new PdfObjRef(dictKid, new PdfStructElem(lastCloned.clone));
                        }
                        kids.remove(i);
                        PdfStructElem.addKidObject(lastCloned.clone, -1, kid);
                        document.getStructTreeRoot().getParentTreeHandler().registerMcr(mcr);
                        i--;
                    }
                } else if (firstPartElems.contains(kid)) {
                    separateKids((PdfDictionary) kid, firstPartElems, lastCloned, document);
                } else if (dictKid.isFlushed()) {
                    throw new PdfException(PdfException.TagFromTheExistingTagStructureIsFlushedCannotAddCopiedPageTags);
                } else if (dictKid.containsKey(PdfName.f1344K)) {
                    cloneParents(structElem, lastCloned, document);
                    kids.remove(i);
                    PdfStructElem.addKidObject(lastCloned.clone, -1, kid);
                    i--;
                }
                i++;
            }
        } else if (k.isDictionary() && PdfStructElem.isStructElem((PdfDictionary) k)) {
            separateKids((PdfDictionary) k, firstPartElems, lastCloned, document);
        }
        if (lastCloned.ancestor == structElem) {
            lastCloned.ancestor = lastCloned.ancestor.getAsDictionary(PdfName.f1367P);
            lastCloned.clone = lastCloned.clone.getAsDictionary(PdfName.f1367P);
        }
    }

    private static void cloneParents(PdfDictionary structElem, LastClonedAncestor lastCloned, PdfDocument document) {
        if (lastCloned.ancestor != structElem) {
            PdfDictionary structElemClone = (PdfDictionary) structElem.clone(ignoreKeysForClone).makeIndirect(document);
            PdfDictionary currClone = structElemClone;
            PdfDictionary currElem = structElem;
            while (currElem.get(PdfName.f1367P) != lastCloned.ancestor) {
                PdfDictionary parent = currElem.getAsDictionary(PdfName.f1367P);
                PdfDictionary parentClone = (PdfDictionary) parent.clone(ignoreKeysForClone).makeIndirect(document);
                currClone.put(PdfName.f1367P, parentClone);
                parentClone.put(PdfName.f1344K, currClone);
                currClone = parentClone;
                currElem = parent;
            }
            PdfStructElem.addKidObject(lastCloned.clone, -1, currClone);
            lastCloned.clone = structElemClone;
            lastCloned.ancestor = structElem;
        }
    }

    private static PdfDictionary addAllParentsToSet(PdfMcr mcr, Set<PdfObject> set) {
        List<PdfDictionary> allParents = retrieveParents(mcr, true);
        set.addAll(allParents);
        if (allParents.isEmpty()) {
            return null;
        }
        return allParents.get(allParents.size() - 1);
    }

    private static PdfDictionary getTopmostParent(PdfMcr mcr) {
        return retrieveParents(mcr, false).get(0);
    }

    private static List<PdfDictionary> retrieveParents(PdfMcr mcr, boolean all) {
        List<PdfDictionary> parents = new ArrayList<>();
        IStructureNode firstParent = mcr.getParent();
        PdfDictionary previous = null;
        PdfDictionary current = firstParent instanceof PdfStructElem ? (PdfDictionary) ((PdfStructElem) firstParent).getPdfObject() : null;
        while (current != null && !PdfName.StructTreeRoot.equals(current.getAsName(PdfName.Type))) {
            if (all) {
                parents.add(current);
            }
            previous = current;
            current = previous.isFlushed() ? null : previous.getAsDictionary(PdfName.f1367P);
        }
        if (!all) {
            parents.add(previous);
        }
        return parents;
    }

    static class LastClonedAncestor {
        PdfDictionary ancestor;
        PdfDictionary clone;

        LastClonedAncestor() {
        }
    }

    private static class StructElemCopyingParams {
        private final Set<PdfObject> copiedNamespaces = new LinkedHashSet();
        private final boolean copyFromDestDocument;
        private final Set<PdfObject> objectsToCopy;
        private final Map<PdfDictionary, PdfDictionary> page2page;
        private final PdfDocument toDocument;

        public StructElemCopyingParams(Set<PdfObject> objectsToCopy2, PdfDocument toDocument2, Map<PdfDictionary, PdfDictionary> page2page2, boolean copyFromDestDocument2) {
            this.objectsToCopy = objectsToCopy2;
            this.toDocument = toDocument2;
            this.page2page = page2page2;
            this.copyFromDestDocument = copyFromDestDocument2;
        }

        public Set<PdfObject> getObjectsToCopy() {
            return this.objectsToCopy;
        }

        public PdfDocument getToDocument() {
            return this.toDocument;
        }

        public Map<PdfDictionary, PdfDictionary> getPage2page() {
            return this.page2page;
        }

        public boolean isCopyFromDestDocument() {
            return this.copyFromDestDocument;
        }

        public void addCopiedNamespace(PdfDictionary copiedNs) {
            this.copiedNamespaces.add(copiedNs);
        }

        public Set<PdfObject> getCopiedNamespaces() {
            return this.copiedNamespaces;
        }
    }

    private static class CopyStructureResult {
        private final Set<PdfObject> copiedNamespaces;
        private final List<PdfDictionary> topsList;

        public CopyStructureResult(List<PdfDictionary> topsList2, Set<PdfObject> copiedNamespaces2) {
            this.topsList = topsList2;
            this.copiedNamespaces = copiedNamespaces2;
        }

        public Set<PdfObject> getCopiedNamespaces() {
            return this.copiedNamespaces;
        }

        public List<PdfDictionary> getTopsList() {
            return this.topsList;
        }
    }
}
