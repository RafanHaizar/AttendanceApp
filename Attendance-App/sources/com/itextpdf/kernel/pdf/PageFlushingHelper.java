package com.itextpdf.kernel.pdf;

import com.itextpdf.kernel.PdfException;
import com.itextpdf.kernel.events.PdfDocumentEvent;
import com.itextpdf.kernel.pdf.layer.PdfLayer;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public class PageFlushingHelper {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final DeepFlushingContext pageContext = initPageFlushingContext();
    private HashSet<PdfObject> currNestedObjParents = new HashSet<>();
    private Set<PdfIndirectReference> layersRefs = new HashSet();
    private PdfDocument pdfDoc;
    private boolean release;

    public PageFlushingHelper(PdfDocument pdfDoc2) {
        this.pdfDoc = pdfDoc2;
    }

    public void unsafeFlushDeep(int pageNum) {
        if (this.pdfDoc.getWriter() != null) {
            this.release = false;
            flushPage(pageNum);
            return;
        }
        throw new IllegalArgumentException(PdfException.FlushingHelperFLushingModeIsNotForDocReadingMode);
    }

    public void releaseDeep(int pageNum) {
        this.release = true;
        flushPage(pageNum);
    }

    public void appendModeFlush(int pageNum) {
        if (this.pdfDoc.getWriter() != null) {
            PdfPage page = this.pdfDoc.getPage(pageNum);
            if (!page.isFlushed()) {
                page.getDocument().dispatchEvent(new PdfDocumentEvent(PdfDocumentEvent.END_PAGE, page));
                boolean pageWasModified = ((PdfDictionary) page.getPdfObject()).isModified();
                page.setModified();
                this.release = true;
                boolean pageWasModified2 = flushPage(pageNum) || pageWasModified;
                PdfArray annots = ((PdfDictionary) page.getPdfObject()).getAsArray(PdfName.Annots);
                if (annots != null && !annots.isFlushed()) {
                    arrayFlushIfModified(annots);
                }
                flushIfModified(((PdfDictionary) page.getPdfObject()).get(PdfName.Thumb, false));
                PdfObject contents = ((PdfDictionary) page.getPdfObject()).get(PdfName.Contents, false);
                if (contents instanceof PdfIndirectReference) {
                    if (contents.checkState(8) && !contents.checkState(1)) {
                        PdfObject contentsDirectObj = ((PdfIndirectReference) contents).getRefersTo();
                        if (contentsDirectObj.isArray()) {
                            arrayFlushIfModified((PdfArray) contentsDirectObj);
                        } else {
                            contentsDirectObj.flush();
                        }
                    }
                } else if (contents instanceof PdfArray) {
                    arrayFlushIfModified((PdfArray) contents);
                } else if (contents instanceof PdfStream) {
                    flushIfModified(contents);
                }
                if (!pageWasModified2) {
                    ((PdfDictionary) page.getPdfObject()).getIndirectReference().clearState(8);
                    this.pdfDoc.getCatalog().getPageTree().releasePage(pageNum);
                    page.unsetForbidRelease();
                    ((PdfDictionary) page.getPdfObject()).release();
                    return;
                }
                page.releaseInstanceFields();
                ((PdfDictionary) page.getPdfObject()).flush();
                return;
            }
            return;
        }
        throw new IllegalArgumentException(PdfException.FlushingHelperFLushingModeIsNotForDocReadingMode);
    }

    /* JADX WARNING: type inference failed for: r5v32, types: [com.itextpdf.kernel.pdf.PdfObject] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private boolean flushPage(int r8) {
        /*
            r7 = this;
            com.itextpdf.kernel.pdf.PdfDocument r0 = r7.pdfDoc
            com.itextpdf.kernel.pdf.PdfPage r0 = r0.getPage((int) r8)
            boolean r1 = r0.isFlushed()
            r2 = 0
            if (r1 == 0) goto L_0x000e
            return r2
        L_0x000e:
            r1 = 0
            boolean r3 = r7.release
            if (r3 != 0) goto L_0x0024
            com.itextpdf.kernel.pdf.PdfDocument r3 = r7.pdfDoc
            com.itextpdf.kernel.events.PdfDocumentEvent r4 = new com.itextpdf.kernel.events.PdfDocumentEvent
            java.lang.String r5 = "EndPdfPage"
            r4.<init>((java.lang.String) r5, (com.itextpdf.kernel.pdf.PdfPage) r0)
            r3.dispatchEvent(r4)
            com.itextpdf.kernel.pdf.PdfDocument r3 = r7.pdfDoc
            r7.initCurrentLayers(r3)
        L_0x0024:
            com.itextpdf.kernel.pdf.PdfObject r3 = r0.getPdfObject()
            com.itextpdf.kernel.pdf.PdfDictionary r3 = (com.itextpdf.kernel.pdf.PdfDictionary) r3
            com.itextpdf.kernel.pdf.PdfDictionary r4 = r0.initResources(r2)
            com.itextpdf.kernel.pdf.PdfResources r2 = r0.getResources(r2)
            if (r2 == 0) goto L_0x0054
            boolean r5 = r2.isModified()
            if (r5 == 0) goto L_0x0054
            boolean r5 = r2.isReadOnly()
            if (r5 != 0) goto L_0x0054
            com.itextpdf.kernel.pdf.PdfObject r5 = r2.getPdfObject()
            r4 = r5
            com.itextpdf.kernel.pdf.PdfDictionary r4 = (com.itextpdf.kernel.pdf.PdfDictionary) r4
            com.itextpdf.kernel.pdf.PdfName r5 = com.itextpdf.kernel.pdf.PdfName.Resources
            com.itextpdf.kernel.pdf.PdfObject r6 = r2.getPdfObject()
            r3.put(r5, r6)
            r3.setModified()
            r1 = 1
        L_0x0054:
            boolean r5 = r4.isFlushed()
            if (r5 != 0) goto L_0x0061
            r5 = 0
            r7.flushDictRecursively(r4, r5)
            r7.flushOrRelease(r4)
        L_0x0061:
            com.itextpdf.kernel.pdf.PageFlushingHelper$DeepFlushingContext r5 = pageContext
            r7.flushDictRecursively(r3, r5)
            boolean r5 = r7.release
            if (r5 == 0) goto L_0x0090
            com.itextpdf.kernel.pdf.PdfObject r5 = r0.getPdfObject()
            com.itextpdf.kernel.pdf.PdfDictionary r5 = (com.itextpdf.kernel.pdf.PdfDictionary) r5
            boolean r5 = r5.isModified()
            if (r5 != 0) goto L_0x00e2
            com.itextpdf.kernel.pdf.PdfDocument r5 = r7.pdfDoc
            com.itextpdf.kernel.pdf.PdfCatalog r5 = r5.getCatalog()
            com.itextpdf.kernel.pdf.PdfPagesTree r5 = r5.getPageTree()
            r5.releasePage(r8)
            r0.unsetForbidRelease()
            com.itextpdf.kernel.pdf.PdfObject r5 = r0.getPdfObject()
            com.itextpdf.kernel.pdf.PdfDictionary r5 = (com.itextpdf.kernel.pdf.PdfDictionary) r5
            r5.release()
            goto L_0x00e2
        L_0x0090:
            com.itextpdf.kernel.pdf.PdfDocument r5 = r7.pdfDoc
            boolean r5 = r5.isTagged()
            if (r5 == 0) goto L_0x00a7
            com.itextpdf.kernel.pdf.PdfDocument r5 = r7.pdfDoc
            com.itextpdf.kernel.pdf.tagging.PdfStructTreeRoot r5 = r5.getStructTreeRoot()
            boolean r5 = r5.isFlushed()
            if (r5 != 0) goto L_0x00a7
            r0.tryFlushPageTags()
        L_0x00a7:
            com.itextpdf.kernel.pdf.PdfDocument r5 = r7.pdfDoc
            boolean r5 = r5.isAppendMode()
            if (r5 == 0) goto L_0x00d6
            com.itextpdf.kernel.pdf.PdfObject r5 = r0.getPdfObject()
            com.itextpdf.kernel.pdf.PdfDictionary r5 = (com.itextpdf.kernel.pdf.PdfDictionary) r5
            boolean r5 = r5.isModified()
            if (r5 == 0) goto L_0x00bc
            goto L_0x00d6
        L_0x00bc:
            com.itextpdf.kernel.pdf.PdfDocument r5 = r7.pdfDoc
            com.itextpdf.kernel.pdf.PdfCatalog r5 = r5.getCatalog()
            com.itextpdf.kernel.pdf.PdfPagesTree r5 = r5.getPageTree()
            r5.releasePage(r8)
            r0.unsetForbidRelease()
            com.itextpdf.kernel.pdf.PdfObject r5 = r0.getPdfObject()
            com.itextpdf.kernel.pdf.PdfDictionary r5 = (com.itextpdf.kernel.pdf.PdfDictionary) r5
            r5.release()
            goto L_0x00e2
        L_0x00d6:
            r0.releaseInstanceFields()
            com.itextpdf.kernel.pdf.PdfObject r5 = r0.getPdfObject()
            com.itextpdf.kernel.pdf.PdfDictionary r5 = (com.itextpdf.kernel.pdf.PdfDictionary) r5
            r5.flush()
        L_0x00e2:
            java.util.Set<com.itextpdf.kernel.pdf.PdfIndirectReference> r5 = r7.layersRefs
            r5.clear()
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.kernel.pdf.PageFlushingHelper.flushPage(int):boolean");
    }

    private void initCurrentLayers(PdfDocument pdfDoc2) {
        if (pdfDoc2.getCatalog().isOCPropertiesMayHaveChanged()) {
            for (PdfLayer layer : pdfDoc2.getCatalog().getOCProperties(false).getLayers()) {
                this.layersRefs.add(((PdfDictionary) layer.getPdfObject()).getIndirectReference());
            }
        }
    }

    private void flushObjectRecursively(PdfObject obj, DeepFlushingContext context) {
        if (obj != null) {
            boolean avoidReleaseForIndirectObjInstance = false;
            if (obj.isIndirectReference()) {
                PdfIndirectReference indRef = (PdfIndirectReference) obj;
                if (indRef.refersTo != null && !indRef.checkState(1)) {
                    obj = indRef.getRefersTo();
                } else {
                    return;
                }
            } else if (!obj.isFlushed()) {
                if (this.release && obj.isIndirect()) {
                    if (obj.isReleaseForbidden() || obj.getIndirectReference() == null) {
                        avoidReleaseForIndirectObjInstance = true;
                    } else {
                        throw new AssertionError();
                    }
                }
            } else {
                return;
            }
            if (!this.pdfDoc.isDocumentFont(obj.getIndirectReference()) && !this.layersRefs.contains(obj.getIndirectReference())) {
                if (obj.isDictionary() || obj.isStream()) {
                    if (this.currNestedObjParents.add(obj)) {
                        flushDictRecursively((PdfDictionary) obj, context);
                        this.currNestedObjParents.remove(obj);
                    } else {
                        return;
                    }
                } else if (obj.isArray()) {
                    if (this.currNestedObjParents.add(obj)) {
                        PdfArray array = (PdfArray) obj;
                        for (int i = 0; i < array.size(); i++) {
                            flushObjectRecursively(array.get(i, false), context);
                        }
                        this.currNestedObjParents.remove(obj);
                    } else {
                        return;
                    }
                }
                if (!avoidReleaseForIndirectObjInstance) {
                    flushOrRelease(obj);
                }
            }
        }
    }

    private void flushDictRecursively(PdfDictionary dict, DeepFlushingContext context) {
        for (PdfName key : dict.keySet()) {
            DeepFlushingContext innerContext = null;
            if (context != null) {
                if (!context.isKeyInBlackList(key)) {
                    innerContext = context.getInnerContextFor(key);
                }
            }
            flushObjectRecursively(dict.get(key, false), innerContext);
        }
    }

    private void flushOrRelease(PdfObject obj) {
        if (!this.release) {
            makeIndirectIfNeeded(obj);
            if (!this.pdfDoc.isAppendMode() || obj.isModified()) {
                obj.flush();
            } else if (!obj.isReleaseForbidden()) {
                obj.release();
            }
        } else if (!obj.isReleaseForbidden()) {
            obj.release();
        }
    }

    private void flushIfModified(PdfObject o) {
        if (o != null && !(o instanceof PdfIndirectReference)) {
            makeIndirectIfNeeded(o);
            o = o.getIndirectReference();
        }
        if (o != null && o.checkState(8) && !o.checkState(1)) {
            ((PdfIndirectReference) o).getRefersTo().flush();
        }
    }

    private void arrayFlushIfModified(PdfArray contentsArr) {
        for (int i = 0; i < contentsArr.size(); i++) {
            flushIfModified(contentsArr.get(i, false));
        }
    }

    private void makeIndirectIfNeeded(PdfObject o) {
        if (o.checkState(64)) {
            o.makeIndirect(this.pdfDoc);
        }
    }

    private static DeepFlushingContext initPageFlushingContext() {
        Map<PdfName, DeepFlushingContext> NO_INNER_CONTEXTS = Collections.emptyMap();
        DeepFlushingContext actionContext = new DeepFlushingContext(new LinkedHashSet(Arrays.asList(new PdfName[]{PdfName.f1312D, PdfName.f1387SD, PdfName.f1319Dp, PdfName.f1293B, PdfName.Annotation, PdfName.f1391T, PdfName.f1290AN, PdfName.f1392TA})), NO_INNER_CONTEXTS);
        DeepFlushingContext aaContext = new DeepFlushingContext(actionContext);
        LinkedHashMap<PdfName, DeepFlushingContext> annotInnerContexts = new LinkedHashMap<>();
        DeepFlushingContext annotsContext = new DeepFlushingContext(new LinkedHashSet(Arrays.asList(new PdfName[]{PdfName.f1367P, PdfName.Popup, PdfName.Dest, PdfName.Parent, PdfName.f1406V})), annotInnerContexts);
        annotInnerContexts.put(PdfName.f1287A, actionContext);
        annotInnerContexts.put(PdfName.f1368PA, actionContext);
        annotInnerContexts.put(PdfName.f1288AA, aaContext);
        DeepFlushingContext sepInfoContext = new DeepFlushingContext(new LinkedHashSet(Collections.singletonList(PdfName.Pages)), NO_INNER_CONTEXTS);
        DeepFlushingContext bContext = new DeepFlushingContext((Set<PdfName>) null, NO_INNER_CONTEXTS);
        LinkedHashMap<PdfName, DeepFlushingContext> presStepsInnerContexts = new LinkedHashMap<>();
        DeepFlushingContext presStepsContext = new DeepFlushingContext(new LinkedHashSet(Collections.singletonList(PdfName.Prev)), presStepsInnerContexts);
        presStepsInnerContexts.put(PdfName.f1358NA, actionContext);
        presStepsInnerContexts.put(PdfName.f1368PA, actionContext);
        LinkedHashMap<PdfName, DeepFlushingContext> pageInnerContexts = new LinkedHashMap<>();
        DeepFlushingContext pageContext2 = new DeepFlushingContext(new LinkedHashSet(Arrays.asList(new PdfName[]{PdfName.Parent, PdfName.DPart})), pageInnerContexts);
        pageInnerContexts.put(PdfName.Annots, annotsContext);
        pageInnerContexts.put(PdfName.f1293B, bContext);
        pageInnerContexts.put(PdfName.f1288AA, aaContext);
        pageInnerContexts.put(PdfName.SeparationInfo, sepInfoContext);
        pageInnerContexts.put(PdfName.PresSteps, presStepsContext);
        return pageContext2;
    }

    private static class DeepFlushingContext {
        Set<PdfName> blackList;
        Map<PdfName, DeepFlushingContext> innerContexts;
        DeepFlushingContext unconditionalInnerContext;

        public DeepFlushingContext(Set<PdfName> blackList2, Map<PdfName, DeepFlushingContext> innerContexts2) {
            this.blackList = blackList2;
            this.innerContexts = innerContexts2;
        }

        public DeepFlushingContext(DeepFlushingContext unconditionalInnerContext2) {
            this.blackList = Collections.emptySet();
            this.innerContexts = null;
            this.unconditionalInnerContext = unconditionalInnerContext2;
        }

        public boolean isKeyInBlackList(PdfName key) {
            Set<PdfName> set = this.blackList;
            return set == null || set.contains(key);
        }

        public DeepFlushingContext getInnerContextFor(PdfName key) {
            Map<PdfName, DeepFlushingContext> map = this.innerContexts;
            return map == null ? this.unconditionalInnerContext : map.get(key);
        }
    }
}
