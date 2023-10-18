package com.itextpdf.pdfa.checker;

import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfTrueTypeFont;
import com.itextpdf.kernel.pdf.PdfAConformanceLevel;
import com.itextpdf.kernel.pdf.PdfArray;
import com.itextpdf.kernel.pdf.PdfCatalog;
import com.itextpdf.kernel.pdf.PdfDictionary;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfName;
import com.itextpdf.kernel.pdf.PdfNumber;
import com.itextpdf.kernel.pdf.PdfObject;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfStream;
import com.itextpdf.kernel.pdf.PdfString;
import com.itextpdf.kernel.pdf.PdfXrefTable;
import com.itextpdf.kernel.pdf.canvas.CanvasGraphicsState;
import com.itextpdf.kernel.pdf.colorspace.PdfColorSpace;
import com.itextpdf.p026io.colors.IccProfile;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public abstract class PdfAChecker implements Serializable {
    public static final String ICC_COLOR_SPACE_CMYK = "CMYK";
    public static final String ICC_COLOR_SPACE_GRAY = "GRAY";
    public static final String ICC_COLOR_SPACE_RGB = "RGB ";
    public static final String ICC_DEVICE_CLASS_MONITOR_PROFILE = "mntr";
    public static final String ICC_DEVICE_CLASS_OUTPUT_PROFILE = "prtr";
    public static final int maxGsStackDepth = 28;
    private static final long serialVersionUID = -9138950508285715228L;
    protected Set<PdfObject> checkedObjects = new HashSet();
    protected Map<PdfObject, PdfColorSpace> checkedObjectsColorspace = new HashMap();
    protected boolean cmykIsUsed = false;
    protected PdfAConformanceLevel conformanceLevel;
    private boolean fullCheckMode = false;
    protected boolean grayIsUsed = false;
    protected int gsStackDepth = 0;
    protected String pdfAOutputIntentColorSpace;
    protected boolean rgbIsUsed = false;

    /* access modifiers changed from: protected */
    public abstract void checkAction(PdfDictionary pdfDictionary);

    /* access modifiers changed from: protected */
    public abstract void checkAnnotation(PdfDictionary pdfDictionary);

    public abstract void checkCanvasStack(char c);

    /* access modifiers changed from: protected */
    public abstract void checkCatalogValidEntries(PdfDictionary pdfDictionary);

    @Deprecated
    public abstract void checkColor(Color color, PdfDictionary pdfDictionary, Boolean bool);

    public abstract void checkColorSpace(PdfColorSpace pdfColorSpace, PdfDictionary pdfDictionary, boolean z, Boolean bool);

    /* access modifiers changed from: protected */
    public abstract void checkColorsUsages();

    @Deprecated
    public abstract void checkExtGState(CanvasGraphicsState canvasGraphicsState);

    /* access modifiers changed from: protected */
    public abstract void checkFileSpec(PdfDictionary pdfDictionary);

    public abstract void checkFont(PdfFont pdfFont);

    /* access modifiers changed from: protected */
    public abstract void checkForm(PdfDictionary pdfDictionary);

    /* access modifiers changed from: protected */
    public abstract void checkFormXObject(PdfStream pdfStream);

    /* access modifiers changed from: protected */
    public abstract void checkImage(PdfStream pdfStream, PdfDictionary pdfDictionary);

    public abstract void checkInlineImage(PdfStream pdfStream, PdfDictionary pdfDictionary);

    /* access modifiers changed from: protected */
    public abstract void checkLogicalStructure(PdfDictionary pdfDictionary);

    /* access modifiers changed from: protected */
    public abstract void checkMetaData(PdfDictionary pdfDictionary);

    /* access modifiers changed from: protected */
    public abstract void checkNonSymbolicTrueTypeFont(PdfTrueTypeFont pdfTrueTypeFont);

    /* access modifiers changed from: protected */
    public abstract void checkOutputIntents(PdfDictionary pdfDictionary);

    /* access modifiers changed from: protected */
    public abstract void checkPageObject(PdfDictionary pdfDictionary, PdfDictionary pdfDictionary2);

    /* access modifiers changed from: protected */
    public abstract void checkPageSize(PdfDictionary pdfDictionary);

    /* access modifiers changed from: protected */
    public abstract void checkPdfArray(PdfArray pdfArray);

    /* access modifiers changed from: protected */
    public abstract void checkPdfDictionary(PdfDictionary pdfDictionary);

    /* access modifiers changed from: protected */
    public abstract void checkPdfName(PdfName pdfName);

    /* access modifiers changed from: protected */
    public abstract void checkPdfNumber(PdfNumber pdfNumber);

    /* access modifiers changed from: protected */
    public abstract void checkPdfStream(PdfStream pdfStream);

    /* access modifiers changed from: protected */
    public abstract void checkPdfString(PdfString pdfString);

    public abstract void checkRenderingIntent(PdfName pdfName);

    /* access modifiers changed from: protected */
    public abstract void checkSymbolicTrueTypeFont(PdfTrueTypeFont pdfTrueTypeFont);

    /* access modifiers changed from: protected */
    public abstract void checkTrailer(PdfDictionary pdfDictionary);

    public abstract void checkXrefTable(PdfXrefTable pdfXrefTable);

    /* access modifiers changed from: protected */
    public abstract Set<PdfName> getAllowedNamedActions();

    /* access modifiers changed from: protected */
    public abstract Set<PdfName> getForbiddenActions();

    /* access modifiers changed from: protected */
    public abstract long getMaxNumberOfIndirectObjects();

    protected PdfAChecker(PdfAConformanceLevel conformanceLevel2) {
        this.conformanceLevel = conformanceLevel2;
    }

    public void checkDocument(PdfCatalog catalog) {
        PdfDictionary catalogDict = (PdfDictionary) catalog.getPdfObject();
        setPdfAOutputIntentColorSpace(catalogDict);
        checkOutputIntents(catalogDict);
        checkMetaData(catalogDict);
        checkCatalogValidEntries(catalogDict);
        checkTrailer(catalog.getDocument().getTrailer());
        checkLogicalStructure(catalogDict);
        checkForm(catalogDict.getAsDictionary(PdfName.AcroForm));
        checkOutlines(catalogDict);
        checkPages(catalog.getDocument());
        checkOpenAction(catalogDict.get(PdfName.OpenAction));
        checkColorsUsages();
    }

    public void checkSinglePage(PdfPage page) {
        checkPage(page);
    }

    public void checkPdfObject(PdfObject obj) {
        switch (obj.getType()) {
            case 1:
                PdfArray array = (PdfArray) obj;
                checkPdfArray(array);
                checkArrayRecursively(array);
                return;
            case 3:
                PdfDictionary dict = (PdfDictionary) obj;
                if (PdfName.Filespec.equals(dict.getAsName(PdfName.Type))) {
                    checkFileSpec(dict);
                }
                checkPdfDictionary(dict);
                checkDictionaryRecursively(dict);
                return;
            case 6:
                checkPdfName((PdfName) obj);
                return;
            case 8:
                checkPdfNumber((PdfNumber) obj);
                return;
            case 9:
                PdfStream stream = (PdfStream) obj;
                checkPdfStream(stream);
                checkDictionaryRecursively(stream);
                return;
            case 10:
                checkPdfString((PdfString) obj);
                return;
            default:
                return;
        }
    }

    public PdfAConformanceLevel getConformanceLevel() {
        return this.conformanceLevel;
    }

    public boolean isFullCheckMode() {
        return this.fullCheckMode;
    }

    public void setFullCheckMode(boolean fullCheckMode2) {
        this.fullCheckMode = fullCheckMode2;
    }

    public boolean objectIsChecked(PdfObject object) {
        return this.checkedObjects.contains(object);
    }

    public void checkTagStructureElement(PdfObject obj) {
        this.checkedObjects.add(obj);
    }

    public void checkColor(Color color, PdfDictionary currentColorSpaces, Boolean fill, PdfStream contentStream) {
    }

    public void checkExtGState(CanvasGraphicsState extGState, PdfStream contentStream) {
    }

    public void checkFontGlyphs(PdfFont font, PdfStream contentStream) {
    }

    /* access modifiers changed from: protected */
    public void checkPageTransparency(PdfDictionary pageDict, PdfDictionary pageResources) {
    }

    /* access modifiers changed from: protected */
    public void checkContentStream(PdfStream contentStream) {
    }

    /* access modifiers changed from: protected */
    public void checkContentStreamObject(PdfObject object) {
    }

    /* access modifiers changed from: protected */
    public void checkResources(PdfDictionary resources) {
        if (resources != null) {
            PdfDictionary xObjects = resources.getAsDictionary(PdfName.XObject);
            PdfDictionary shadings = resources.getAsDictionary(PdfName.Shading);
            PdfDictionary patterns = resources.getAsDictionary(PdfName.Pattern);
            if (xObjects != null) {
                Iterator<PdfObject> it = xObjects.values().iterator();
                while (it.hasNext()) {
                    PdfStream xObjStream = (PdfStream) it.next();
                    PdfObject subtype = null;
                    boolean isFlushed = xObjStream.isFlushed();
                    if (!isFlushed) {
                        subtype = xObjStream.get(PdfName.Subtype);
                    }
                    if (PdfName.Image.equals(subtype) || isFlushed) {
                        checkImage(xObjStream, resources.getAsDictionary(PdfName.ColorSpace));
                    } else if (PdfName.Form.equals(subtype)) {
                        checkFormXObject(xObjStream);
                    }
                }
            }
            if (shadings != null) {
                Iterator<PdfObject> it2 = shadings.values().iterator();
                while (it2.hasNext()) {
                    PdfDictionary shadingDict = (PdfDictionary) it2.next();
                    if (!isAlreadyChecked(shadingDict)) {
                        checkColorSpace(PdfColorSpace.makeColorSpace(shadingDict.get(PdfName.ColorSpace)), resources.getAsDictionary(PdfName.ColorSpace), true, (Boolean) null);
                    }
                }
            }
            if (patterns != null) {
                for (PdfObject p : patterns.values()) {
                    if (p.isStream()) {
                        PdfStream pStream = (PdfStream) p;
                        if (!isAlreadyChecked(pStream)) {
                            checkResources(pStream.getAsDictionary(PdfName.Resources));
                        }
                    }
                }
            }
        }
    }

    protected static boolean checkFlag(int flags, int flag) {
        return (flags & flag) != 0;
    }

    protected static boolean checkStructure(PdfAConformanceLevel conformanceLevel2) {
        return conformanceLevel2 == PdfAConformanceLevel.PDF_A_1A || conformanceLevel2 == PdfAConformanceLevel.PDF_A_2A || conformanceLevel2 == PdfAConformanceLevel.PDF_A_3A;
    }

    protected static boolean isContainsTransparencyGroup(PdfDictionary dictionary) {
        return dictionary.containsKey(PdfName.Group) && PdfName.Transparency.equals(dictionary.getAsDictionary(PdfName.Group).getAsName(PdfName.f1385S));
    }

    /* access modifiers changed from: protected */
    public boolean isAlreadyChecked(PdfDictionary dictionary) {
        if (this.checkedObjects.contains(dictionary)) {
            return true;
        }
        this.checkedObjects.add(dictionary);
        return false;
    }

    /* access modifiers changed from: protected */
    public void checkResourcesOfAppearanceStreams(PdfDictionary appearanceStreamsDict) {
        checkResourcesOfAppearanceStreams(appearanceStreamsDict, new HashSet());
    }

    /* access modifiers changed from: protected */
    public void checkAppearanceStream(PdfStream appearanceStream) {
        if (!isAlreadyChecked(appearanceStream)) {
            checkResources(appearanceStream.getAsDictionary(PdfName.Resources));
        }
    }

    private void checkResourcesOfAppearanceStreams(PdfDictionary appearanceStreamsDict, Set<PdfObject> checkedObjects2) {
        if (!checkedObjects2.contains(appearanceStreamsDict)) {
            checkedObjects2.add(appearanceStreamsDict);
            for (PdfObject val : appearanceStreamsDict.values()) {
                if (val instanceof PdfDictionary) {
                    PdfDictionary ap = (PdfDictionary) val;
                    if (ap.isDictionary()) {
                        checkResourcesOfAppearanceStreams(ap, checkedObjects2);
                    } else if (ap.isStream()) {
                        checkAppearanceStream((PdfStream) ap);
                    }
                }
            }
        }
    }

    private void checkArrayRecursively(PdfArray array) {
        for (int i = 0; i < array.size(); i++) {
            PdfObject object = array.get(i, false);
            if (object != null && !object.isIndirect()) {
                checkPdfObject(object);
            }
        }
    }

    private void checkDictionaryRecursively(PdfDictionary dictionary) {
        for (PdfName name : dictionary.keySet()) {
            checkPdfName(name);
            PdfObject object = dictionary.get(name, false);
            if (object != null && !object.isIndirect()) {
                checkPdfObject(object);
            }
        }
    }

    private void checkPages(PdfDocument document) {
        for (int i = 1; i <= document.getNumberOfPages(); i++) {
            checkPage(document.getPage(i));
        }
    }

    private void checkPage(PdfPage page) {
        PdfDictionary pageDict = (PdfDictionary) page.getPdfObject();
        if (!isAlreadyChecked(pageDict)) {
            checkPageObject(pageDict, (PdfDictionary) page.getResources().getPdfObject());
            checkResources((PdfDictionary) page.getResources().getPdfObject());
            checkAnnotations(pageDict);
            checkPageSize(pageDict);
            checkPageTransparency(pageDict, (PdfDictionary) page.getResources().getPdfObject());
            int contentStreamCount = page.getContentStreamCount();
            for (int j = 0; j < contentStreamCount; j++) {
                PdfStream contentStream = page.getContentStream(j);
                checkContentStream(contentStream);
                this.checkedObjects.add(contentStream);
            }
        }
    }

    private void checkOpenAction(PdfObject openAction) {
        if (openAction != null && openAction.isDictionary()) {
            checkAction((PdfDictionary) openAction);
        }
    }

    private void checkAnnotations(PdfDictionary page) {
        PdfArray annots = page.getAsArray(PdfName.Annots);
        if (annots != null) {
            for (int i = 0; i < annots.size(); i++) {
                PdfDictionary annot = annots.getAsDictionary(i);
                checkAnnotation(annot);
                PdfDictionary action = annot.getAsDictionary(PdfName.f1287A);
                if (action != null) {
                    checkAction(action);
                }
            }
        }
    }

    private void checkOutlines(PdfDictionary catalogDict) {
        PdfDictionary outlines = catalogDict.getAsDictionary(PdfName.Outlines);
        if (outlines != null) {
            for (PdfDictionary outline : getOutlines(outlines)) {
                PdfDictionary action = outline.getAsDictionary(PdfName.f1287A);
                if (action != null) {
                    checkAction(action);
                }
            }
        }
    }

    private List<PdfDictionary> getOutlines(PdfDictionary item) {
        List<PdfDictionary> outlines = new ArrayList<>();
        outlines.add(item);
        PdfDictionary processItem = item.getAsDictionary(PdfName.First);
        if (processItem != null) {
            outlines.addAll(getOutlines(processItem));
        }
        PdfDictionary processItem2 = item.getAsDictionary(PdfName.Next);
        if (processItem2 != null) {
            outlines.addAll(getOutlines(processItem2));
        }
        return outlines;
    }

    private void setPdfAOutputIntentColorSpace(PdfDictionary catalog) {
        PdfArray outputIntents = catalog.getAsArray(PdfName.OutputIntents);
        if (outputIntents != null) {
            setCheckerOutputIntent(getPdfAOutputIntent(outputIntents));
        }
    }

    private PdfDictionary getPdfAOutputIntent(PdfArray outputIntents) {
        for (int i = 0; i < outputIntents.size(); i++) {
            if (PdfName.GTS_PDFA1.equals(outputIntents.getAsDictionary(i).getAsName(PdfName.f1385S))) {
                return outputIntents.getAsDictionary(i);
            }
        }
        return null;
    }

    private void setCheckerOutputIntent(PdfDictionary outputIntent) {
        PdfStream destOutputProfile;
        if (outputIntent != null && (destOutputProfile = outputIntent.getAsStream(PdfName.DestOutputProfile)) != null) {
            this.pdfAOutputIntentColorSpace = IccProfile.getIccColorSpaceName(destOutputProfile.getBytes());
        }
    }
}
