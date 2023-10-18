package com.itextpdf.kernel.pdf;

import com.itextpdf.kernel.PdfException;

public class PdfViewerPreferences extends PdfObjectWrapper<PdfDictionary> {
    private static final long serialVersionUID = -6885879361985241602L;

    public enum PdfViewerPreferencesConstants {
        USE_NONE,
        USE_OUTLINES,
        USE_THUMBS,
        USE_OC,
        LEFT_TO_RIGHT,
        RIGHT_TO_LEFT,
        MEDIA_BOX,
        CROP_BOX,
        BLEED_BOX,
        TRIM_BOX,
        ART_BOX,
        VIEW_AREA,
        VIEW_CLIP,
        PRINT_AREA,
        PRINT_CLIP,
        NONE,
        APP_DEFAULT,
        SIMPLEX,
        DUPLEX_FLIP_SHORT_EDGE,
        DUPLEX_FLIP_LONG_EDGE
    }

    public PdfViewerPreferences() {
        this(new PdfDictionary());
    }

    public PdfViewerPreferences(PdfDictionary pdfObject) {
        super(pdfObject);
    }

    public PdfViewerPreferences setHideToolbar(boolean hideToolbar) {
        return put(PdfName.HideToolbar, PdfBoolean.valueOf(hideToolbar));
    }

    public PdfViewerPreferences setHideMenubar(boolean hideMenubar) {
        return put(PdfName.HideMenubar, PdfBoolean.valueOf(hideMenubar));
    }

    public PdfViewerPreferences setHideWindowUI(boolean hideWindowUI) {
        return put(PdfName.HideWindowUI, PdfBoolean.valueOf(hideWindowUI));
    }

    public PdfViewerPreferences setFitWindow(boolean fitWindow) {
        return put(PdfName.FitWindow, PdfBoolean.valueOf(fitWindow));
    }

    public PdfViewerPreferences setCenterWindow(boolean centerWindow) {
        return put(PdfName.CenterWindow, PdfBoolean.valueOf(centerWindow));
    }

    public PdfViewerPreferences setDisplayDocTitle(boolean displayDocTitle) {
        return put(PdfName.DisplayDocTitle, PdfBoolean.valueOf(displayDocTitle));
    }

    public PdfViewerPreferences setNonFullScreenPageMode(PdfViewerPreferencesConstants nonFullScreenPageMode) {
        switch (C14291.f1428xe46edd1f[nonFullScreenPageMode.ordinal()]) {
            case 1:
                put(PdfName.NonFullScreenPageMode, PdfName.UseNone);
                break;
            case 2:
                put(PdfName.NonFullScreenPageMode, PdfName.UseOutlines);
                break;
            case 3:
                put(PdfName.NonFullScreenPageMode, PdfName.UseThumbs);
                break;
            case 4:
                put(PdfName.NonFullScreenPageMode, PdfName.UseOC);
                break;
        }
        return this;
    }

    public PdfViewerPreferences setDirection(PdfViewerPreferencesConstants direction) {
        switch (direction) {
            case LEFT_TO_RIGHT:
                put(PdfName.Direction, PdfName.L2R);
                break;
            case RIGHT_TO_LEFT:
                put(PdfName.Direction, PdfName.R2L);
                break;
        }
        return this;
    }

    public PdfViewerPreferences setViewArea(PdfViewerPreferencesConstants pageBoundary) {
        return setPageBoundary(PdfViewerPreferencesConstants.VIEW_AREA, pageBoundary);
    }

    public PdfViewerPreferences setViewClip(PdfViewerPreferencesConstants pageBoundary) {
        return setPageBoundary(PdfViewerPreferencesConstants.VIEW_CLIP, pageBoundary);
    }

    public PdfViewerPreferences setPrintArea(PdfViewerPreferencesConstants pageBoundary) {
        return setPageBoundary(PdfViewerPreferencesConstants.PRINT_AREA, pageBoundary);
    }

    public PdfViewerPreferences setPrintClip(PdfViewerPreferencesConstants pageBoundary) {
        return setPageBoundary(PdfViewerPreferencesConstants.PRINT_CLIP, pageBoundary);
    }

    public PdfViewerPreferences setPrintScaling(PdfViewerPreferencesConstants printScaling) {
        switch (printScaling) {
            case NONE:
                put(PdfName.PrintScaling, PdfName.None);
                break;
            case APP_DEFAULT:
                put(PdfName.PrintScaling, PdfName.AppDefault);
                break;
        }
        return this;
    }

    public PdfViewerPreferences setDuplex(PdfViewerPreferencesConstants duplex) {
        switch (duplex) {
            case SIMPLEX:
                put(PdfName.Duplex, PdfName.Simplex);
                break;
            case DUPLEX_FLIP_SHORT_EDGE:
                put(PdfName.Duplex, PdfName.DuplexFlipShortEdge);
                break;
            case DUPLEX_FLIP_LONG_EDGE:
                put(PdfName.Duplex, PdfName.DuplexFlipLongEdge);
                break;
        }
        return this;
    }

    public PdfViewerPreferences setPickTrayByPDFSize(boolean pickTrayByPdfSize) {
        return put(PdfName.PickTrayByPDFSize, PdfBoolean.valueOf(pickTrayByPdfSize));
    }

    public PdfViewerPreferences setPrintPageRange(int[] printPageRange) {
        return put(PdfName.PrintPageRange, new PdfArray(printPageRange));
    }

    public PdfViewerPreferences setNumCopies(int numCopies) {
        return put(PdfName.NumCopies, new PdfNumber(numCopies));
    }

    public PdfViewerPreferences setEnforce(PdfArray enforce) {
        PdfName curPrintScaling;
        int i = 0;
        while (i < enforce.size()) {
            PdfName curEnforce = enforce.getAsName(i);
            if (curEnforce == null) {
                throw new IllegalArgumentException("Enforce array shall contain PdfName entries");
            } else if (!PdfName.PrintScaling.equals(curEnforce) || ((curPrintScaling = ((PdfDictionary) getPdfObject()).getAsName(PdfName.PrintScaling)) != null && !PdfName.AppDefault.equals(curPrintScaling))) {
                i++;
            } else {
                throw new PdfException(PdfException.PrintScalingEnforceEntryInvalid);
            }
        }
        return put(PdfName.Enforce, enforce);
    }

    public PdfArray getEnforce() {
        return ((PdfDictionary) getPdfObject()).getAsArray(PdfName.Enforce);
    }

    public PdfViewerPreferences put(PdfName key, PdfObject value) {
        ((PdfDictionary) getPdfObject()).put(key, value);
        setModified();
        return this;
    }

    /* access modifiers changed from: protected */
    public boolean isWrappedObjectMustBeIndirect() {
        return false;
    }

    private PdfViewerPreferences setPageBoundary(PdfViewerPreferencesConstants viewerPreferenceType, PdfViewerPreferencesConstants pageBoundary) {
        PdfName type = null;
        switch (viewerPreferenceType) {
            case VIEW_AREA:
                type = PdfName.ViewArea;
                break;
            case VIEW_CLIP:
                type = PdfName.ViewClip;
                break;
            case PRINT_AREA:
                type = PdfName.PrintArea;
                break;
            case PRINT_CLIP:
                type = PdfName.PrintClip;
                break;
        }
        if (type != null) {
            switch (pageBoundary) {
                case MEDIA_BOX:
                    put(type, PdfName.MediaBox);
                    break;
                case CROP_BOX:
                    put(type, PdfName.CropBox);
                    break;
                case BLEED_BOX:
                    put(type, PdfName.BleedBox);
                    break;
                case TRIM_BOX:
                    put(type, PdfName.TrimBox);
                    break;
                case ART_BOX:
                    put(type, PdfName.ArtBox);
                    break;
            }
        }
        return this;
    }
}
