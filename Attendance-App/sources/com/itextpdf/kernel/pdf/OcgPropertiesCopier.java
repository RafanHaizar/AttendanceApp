package com.itextpdf.kernel.pdf;

import com.itextpdf.kernel.pdf.annot.PdfAnnotation;
import com.itextpdf.p026io.LogMessageConstant;
import com.itextpdf.p026io.font.PdfEncodings;
import com.itextpdf.p026io.util.MessageFormatUtil;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

final class OcgPropertiesCopier {
    private static final Logger LOGGER = LoggerFactory.getLogger((Class<?>) OcgPropertiesCopier.class);

    private OcgPropertiesCopier() {
    }

    public static void copyOCGProperties(PdfDocument fromDocument, PdfDocument toDocument, Map<PdfPage, PdfPage> page2page) {
        try {
            Set<PdfIndirectReference> fromOcgsToCopy = getAllUsedNonFlushedOCGs(page2page, ((PdfDictionary) toDocument.getCatalog().getPdfObject()).getAsDictionary(PdfName.OCProperties));
            if (!fromOcgsToCopy.isEmpty()) {
                PdfDictionary toOcProperties = toDocument.getCatalog().fillAndGetOcPropertiesDictionary();
                PdfDictionary fromOcProperties = ((PdfDictionary) fromDocument.getCatalog().getPdfObject()).getAsDictionary(PdfName.OCProperties);
                copyOCGs(fromOcgsToCopy, toOcProperties, toDocument);
                copyDDictionary(fromOcgsToCopy, fromOcProperties.getAsDictionary(PdfName.f1312D), toOcProperties, toDocument);
            }
        } catch (Exception ex) {
            LOGGER.error(MessageFormatUtil.format(LogMessageConstant.OCG_COPYING_ERROR, ex.toString()));
        }
    }

    private static Set<PdfIndirectReference> getAllUsedNonFlushedOCGs(Map<PdfPage, PdfPage> page2page, PdfDictionary toOcProperties) {
        PdfDictionary pdfDictionary = toOcProperties;
        Set<PdfIndirectReference> fromUsedOcgs = new LinkedHashSet<>();
        PdfPage[] fromPages = (PdfPage[]) page2page.keySet().toArray(new PdfPage[0]);
        PdfPage[] toPages = (PdfPage[]) page2page.values().toArray(new PdfPage[0]);
        for (int i = 0; i < toPages.length; i++) {
            PdfPage fromPage = fromPages[i];
            PdfPage toPage = toPages[i];
            List<PdfAnnotation> toAnnotations = toPage.getAnnotations();
            List<PdfAnnotation> fromAnnotations = fromPage.getAnnotations();
            for (int j = 0; j < toAnnotations.size(); j++) {
                if (!toAnnotations.get(j).isFlushed()) {
                    PdfDictionary toAnnotDict = (PdfDictionary) toAnnotations.get(j).getPdfObject();
                    PdfDictionary fromAnnotDict = (PdfDictionary) fromAnnotations.get(j).getPdfObject();
                    PdfAnnotation toAnnot = toAnnotations.get(j);
                    PdfAnnotation fromAnnot = fromAnnotations.get(j);
                    if (!toAnnotDict.isFlushed()) {
                        getUsedNonFlushedOCGsFromOcDict(toAnnotDict.getAsDictionary(PdfName.f1362OC), fromAnnotDict.getAsDictionary(PdfName.f1362OC), fromUsedOcgs, pdfDictionary);
                        getUsedNonFlushedOCGsFromXObject(toAnnot.getNormalAppearanceObject(), fromAnnot.getNormalAppearanceObject(), fromUsedOcgs, pdfDictionary);
                        getUsedNonFlushedOCGsFromXObject(toAnnot.getRolloverAppearanceObject(), fromAnnot.getRolloverAppearanceObject(), fromUsedOcgs, pdfDictionary);
                        getUsedNonFlushedOCGsFromXObject(toAnnot.getDownAppearanceObject(), fromAnnot.getDownAppearanceObject(), fromUsedOcgs, pdfDictionary);
                    }
                }
            }
            getUsedNonFlushedOCGsFromResources(((PdfDictionary) toPage.getPdfObject()).getAsDictionary(PdfName.Resources), ((PdfDictionary) fromPage.getPdfObject()).getAsDictionary(PdfName.Resources), fromUsedOcgs, pdfDictionary);
        }
        return fromUsedOcgs;
    }

    private static void getUsedNonFlushedOCGsFromResources(PdfDictionary toResources, PdfDictionary fromResources, Set<PdfIndirectReference> fromUsedOcgs, PdfDictionary toOcProperties) {
        if (toResources != null && !toResources.isFlushed()) {
            PdfDictionary toProperties = toResources.getAsDictionary(PdfName.Properties);
            PdfDictionary fromProperties = fromResources.getAsDictionary(PdfName.Properties);
            if (toProperties != null && !toProperties.isFlushed()) {
                for (PdfName name : toProperties.keySet()) {
                    getUsedNonFlushedOCGsFromOcDict(toProperties.get(name), fromProperties.get(name), fromUsedOcgs, toOcProperties);
                }
            }
            getUsedNonFlushedOCGsFromXObject(toResources.getAsDictionary(PdfName.XObject), fromResources.getAsDictionary(PdfName.XObject), fromUsedOcgs, toOcProperties);
        }
    }

    private static void getUsedNonFlushedOCGsFromXObject(PdfDictionary toXObject, PdfDictionary fromXObject, Set<PdfIndirectReference> fromUsedOcgs, PdfDictionary toOcProperties) {
        if (toXObject != null && !toXObject.isFlushed()) {
            if (!toXObject.isStream() || toXObject.isFlushed()) {
                for (PdfName name : toXObject.keySet()) {
                    PdfObject toCurrObj = toXObject.get(name);
                    PdfObject fromCurrObj = fromXObject.get(name);
                    if (toCurrObj.isStream() && !toCurrObj.isFlushed()) {
                        getUsedNonFlushedOCGsFromXObject((PdfStream) toCurrObj, (PdfStream) fromCurrObj, fromUsedOcgs, toOcProperties);
                    }
                }
                return;
            }
            PdfStream toStream = (PdfStream) toXObject;
            PdfStream fromStream = (PdfStream) fromXObject;
            getUsedNonFlushedOCGsFromOcDict(toStream.getAsDictionary(PdfName.f1362OC), fromStream.getAsDictionary(PdfName.f1362OC), fromUsedOcgs, toOcProperties);
            getUsedNonFlushedOCGsFromResources(toStream.getAsDictionary(PdfName.Resources), fromStream.getAsDictionary(PdfName.Resources), fromUsedOcgs, toOcProperties);
        }
    }

    private static void getUsedNonFlushedOCGsFromOcDict(PdfObject toObj, PdfObject fromObj, Set<PdfIndirectReference> fromUsedOcgs, PdfDictionary toOcProperties) {
        if (toObj != null && toObj.isDictionary() && !toObj.isFlushed()) {
            PdfDictionary toCurrDict = (PdfDictionary) toObj;
            PdfDictionary fromCurrDict = (PdfDictionary) fromObj;
            PdfName typeName = toCurrDict.getAsName(PdfName.Type);
            if (PdfName.OCG.equals(typeName) && !ocgAlreadyInOCGs(toCurrDict.getIndirectReference(), toOcProperties)) {
                fromUsedOcgs.add(fromCurrDict.getIndirectReference());
            } else if (PdfName.OCMD.equals(typeName)) {
                PdfArray toOcgs = null;
                PdfArray fromOcgs = null;
                if (toCurrDict.getAsDictionary(PdfName.OCGs) != null) {
                    toOcgs = new PdfArray();
                    toOcgs.add(toCurrDict.getAsDictionary(PdfName.OCGs));
                    fromOcgs = new PdfArray();
                    fromOcgs.add(fromCurrDict.getAsDictionary(PdfName.OCGs));
                } else if (toCurrDict.getAsArray(PdfName.OCGs) != null) {
                    toOcgs = toCurrDict.getAsArray(PdfName.OCGs);
                    fromOcgs = fromCurrDict.getAsArray(PdfName.OCGs);
                }
                if (toOcgs != null && !toOcgs.isFlushed()) {
                    for (int i = 0; i < toOcgs.size(); i++) {
                        getUsedNonFlushedOCGsFromOcDict(toOcgs.get(i), fromOcgs.get(i), fromUsedOcgs, toOcProperties);
                    }
                }
            }
        }
    }

    private static void copyOCGs(Set<PdfIndirectReference> fromOcgsToCopy, PdfDictionary toOcProperties, PdfDocument toDocument) {
        Set<String> layerNames = new HashSet<>();
        if (toOcProperties.getAsArray(PdfName.OCGs) != null) {
            Iterator<PdfObject> it = toOcProperties.getAsArray(PdfName.OCGs).iterator();
            while (it.hasNext()) {
                PdfObject toOcgObj = it.next();
                if (toOcgObj.isDictionary()) {
                    layerNames.add(((PdfDictionary) toOcgObj).getAsString(PdfName.Name).toUnicodeString());
                }
            }
        }
        boolean hasConflictingNames = false;
        for (PdfIndirectReference fromOcgRef : fromOcgsToCopy) {
            PdfDictionary toOcg = (PdfDictionary) fromOcgRef.getRefersTo().copyTo(toDocument, false);
            String currentLayerName = toOcg.getAsString(PdfName.Name).toUnicodeString();
            if (layerNames.contains(currentLayerName)) {
                hasConflictingNames = true;
                int i = 0;
                while (layerNames.contains(currentLayerName + "_" + i)) {
                    i++;
                }
                toOcg.put(PdfName.Name, new PdfString(currentLayerName + "_" + i, PdfEncodings.UNICODE_BIG));
            }
            if (toOcProperties.getAsArray(PdfName.OCGs) == null) {
                toOcProperties.put(PdfName.OCGs, new PdfArray());
            }
            toOcProperties.getAsArray(PdfName.OCGs).add(toOcg);
        }
        if (hasConflictingNames) {
            LOGGER.warn(LogMessageConstant.DOCUMENT_HAS_CONFLICTING_OCG_NAMES);
        }
    }

    private static boolean ocgAlreadyInOCGs(PdfIndirectReference toOcgRef, PdfDictionary toOcProperties) {
        PdfArray toOcgs;
        if (!(toOcProperties == null || (toOcgs = toOcProperties.getAsArray(PdfName.OCGs)) == null)) {
            Iterator<PdfObject> it = toOcgs.iterator();
            while (it.hasNext()) {
                if (toOcgRef.equals(it.next().getIndirectReference())) {
                    return true;
                }
            }
        }
        return false;
    }

    private static void copyDDictionary(Set<PdfIndirectReference> fromOcgsToCopy, PdfDictionary fromDDict, PdfDictionary toOcProperties, PdfDocument toDocument) {
        if (toOcProperties.getAsDictionary(PdfName.f1312D) == null) {
            toOcProperties.put(PdfName.f1312D, new PdfDictionary());
        }
        PdfDictionary toDDict = toOcProperties.getAsDictionary(PdfName.f1312D);
        toDDict.remove(PdfName.Creator);
        copyDArrayField(PdfName.f1364ON, fromOcgsToCopy, fromDDict, toDDict, toDocument);
        copyDArrayField(PdfName.OFF, fromOcgsToCopy, fromDDict, toDDict, toDocument);
        copyDArrayField(PdfName.Order, fromOcgsToCopy, fromDDict, toDDict, toDocument);
        copyDArrayField(PdfName.RBGroups, fromOcgsToCopy, fromDDict, toDDict, toDocument);
        copyDArrayField(PdfName.Locked, fromOcgsToCopy, fromDDict, toDDict, toDocument);
    }

    private static void attemptToAddObjectToArray(Set<PdfIndirectReference> fromOcgsToCopy, PdfObject fromObj, PdfArray toArray, PdfDocument toDocument) {
        PdfIndirectReference fromObjRef = fromObj.getIndirectReference();
        if (fromObjRef != null && fromOcgsToCopy.contains(fromObjRef)) {
            toArray.add(fromObj.copyTo(toDocument, false));
        }
    }

    private static void copyDArrayField(PdfName fieldToCopy, Set<PdfIndirectReference> fromOcgsToCopy, PdfDictionary fromDict, PdfDictionary toDict, PdfDocument toDocument) {
        PdfArray toArray;
        Set<PdfIndirectReference> toOcgsToCopy;
        PdfArray toArray2;
        PdfName pdfName = fieldToCopy;
        Set<PdfIndirectReference> set = fromOcgsToCopy;
        PdfDictionary pdfDictionary = fromDict;
        PdfDictionary pdfDictionary2 = toDict;
        PdfDocument pdfDocument = toDocument;
        if (pdfDictionary.getAsArray(pdfName) != null) {
            PdfArray fromArray = pdfDictionary.getAsArray(pdfName);
            if (pdfDictionary2.getAsArray(pdfName) == null) {
                pdfDictionary2.put(pdfName, new PdfArray());
            }
            PdfArray toArray3 = pdfDictionary2.getAsArray(pdfName);
            Set<PdfIndirectReference> hashSet = new HashSet<>();
            for (PdfIndirectReference fromRef : fromOcgsToCopy) {
                hashSet.add(fromRef.getRefersTo().copyTo(pdfDocument, false).getIndirectReference());
            }
            if (PdfName.Order.equals(pdfName)) {
                List<Integer> arrayList = new ArrayList<>();
                for (int i = 0; i < toArray3.size(); i++) {
                    if (orderBranchContainsSetElements(toArray3.get(i), toArray3, i, hashSet, (PdfArray) null, (PdfDocument) null)) {
                        arrayList.add(Integer.valueOf(i));
                    }
                }
                for (int i2 = arrayList.size() - 1; i2 > -1; i2--) {
                    toArray3.remove(((Integer) arrayList.get(i2)).intValue());
                }
                PdfArray toOcgs = ((PdfDictionary) toDocument.getCatalog().getPdfObject()).getAsDictionary(PdfName.OCProperties).getAsArray(PdfName.OCGs);
                int i3 = 0;
                while (i3 < fromArray.size()) {
                    PdfObject fromOrderItem = fromArray.get(i3);
                    List<Integer> removeIndex = arrayList;
                    HashSet hashSet2 = hashSet;
                    PdfArray toArray4 = toArray3;
                    if (orderBranchContainsSetElements(fromOrderItem, fromArray, i3, fromOcgsToCopy, toOcgs, toDocument)) {
                        toArray2 = toArray4;
                        toArray2.add(fromOrderItem.copyTo(pdfDocument, false));
                    } else {
                        toArray2 = toArray4;
                    }
                    i3++;
                    PdfDictionary pdfDictionary3 = fromDict;
                    toArray3 = toArray2;
                    arrayList = removeIndex;
                    hashSet = hashSet2;
                }
                List<Integer> removeIndex2 = arrayList;
                toArray = toArray3;
                HashSet hashSet3 = hashSet;
            } else {
                Set<PdfIndirectReference> toOcgsToCopy2 = hashSet;
                toArray = toArray3;
                if (PdfName.RBGroups.equals(pdfName)) {
                    int i4 = toArray.size() - 1;
                    while (i4 > -1) {
                        Iterator<PdfObject> it = ((PdfArray) toArray.get(i4)).iterator();
                        while (true) {
                            if (!it.hasNext()) {
                                toOcgsToCopy = toOcgsToCopy2;
                                break;
                            }
                            toOcgsToCopy = toOcgsToCopy2;
                            if (toOcgsToCopy.contains(it.next().getIndirectReference())) {
                                toArray.remove(i4);
                                break;
                            }
                            toOcgsToCopy2 = toOcgsToCopy;
                        }
                        i4--;
                        toOcgsToCopy2 = toOcgsToCopy;
                    }
                    Iterator<PdfObject> it2 = fromArray.iterator();
                    while (it2.hasNext()) {
                        PdfArray fromRbGroup = (PdfArray) it2.next();
                        Iterator<PdfObject> it3 = fromRbGroup.iterator();
                        while (true) {
                            if (it3.hasNext()) {
                                if (set.contains(it3.next().getIndirectReference())) {
                                    toArray.add(fromRbGroup.copyTo(pdfDocument, false));
                                    break;
                                }
                            } else {
                                break;
                            }
                        }
                    }
                } else {
                    Iterator<PdfObject> it4 = fromArray.iterator();
                    while (it4.hasNext()) {
                        attemptToAddObjectToArray(set, it4.next(), toArray, pdfDocument);
                    }
                }
            }
            if (toArray.isEmpty()) {
                pdfDictionary2.remove(pdfName);
            }
        }
    }

    private static boolean orderBranchContainsSetElements(PdfObject arrayObj, PdfArray array, int currentIndex, Set<PdfIndirectReference> ocgs, PdfArray toOcgs, PdfDocument toDocument) {
        PdfObject pdfObject = arrayObj;
        PdfArray pdfArray = array;
        int i = currentIndex;
        Set<PdfIndirectReference> set = ocgs;
        PdfArray pdfArray2 = toOcgs;
        if (!arrayObj.isDictionary()) {
            PdfDocument pdfDocument = toDocument;
            if (arrayObj.isArray()) {
                PdfArray arrayItem = (PdfArray) pdfObject;
                for (int i2 = 0; i2 < arrayItem.size(); i2++) {
                    if (orderBranchContainsSetElements(arrayItem.get(i2), arrayItem, i2, ocgs, toOcgs, toDocument)) {
                        return true;
                    }
                }
                if (!arrayItem.isEmpty() && !arrayItem.get(0).isString() && i > 0 && pdfArray.get(i - 1).isDictionary()) {
                    return set.contains(((PdfDictionary) pdfArray.get(i - 1)).getIndirectReference());
                }
            }
        } else if (set.contains(arrayObj.getIndirectReference())) {
            return true;
        } else {
            if (i >= array.size() - 1 || !pdfArray.get(i + 1).isArray()) {
                PdfDocument pdfDocument2 = toDocument;
            } else {
                PdfArray nextArray = pdfArray.getAsArray(i + 1);
                if (!nextArray.get(0).isString()) {
                    boolean result = orderBranchContainsSetElements(nextArray, array, i + 1, ocgs, toOcgs, toDocument);
                    if (!result || pdfArray2 == null || set.contains(arrayObj.getIndirectReference())) {
                        PdfDocument pdfDocument3 = toDocument;
                    } else {
                        pdfArray2.add(pdfObject.copyTo(toDocument, false));
                    }
                    return result;
                }
                PdfDocument pdfDocument4 = toDocument;
            }
        }
        return false;
    }
}
