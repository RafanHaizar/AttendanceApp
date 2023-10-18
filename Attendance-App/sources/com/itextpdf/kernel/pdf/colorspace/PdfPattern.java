package com.itextpdf.kernel.pdf.colorspace;

import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfArray;
import com.itextpdf.kernel.pdf.PdfDictionary;
import com.itextpdf.kernel.pdf.PdfName;
import com.itextpdf.kernel.pdf.PdfNumber;
import com.itextpdf.kernel.pdf.PdfObjectWrapper;
import com.itextpdf.kernel.pdf.PdfResources;
import com.itextpdf.kernel.pdf.PdfStream;

public abstract class PdfPattern extends PdfObjectWrapper<PdfDictionary> {
    private static final long serialVersionUID = -6771280634868639993L;

    protected PdfPattern(PdfDictionary pdfObject) {
        super(pdfObject);
    }

    public static PdfPattern getPatternInstance(PdfDictionary pdfObject) {
        PdfNumber type = pdfObject.getAsNumber(PdfName.PatternType);
        if (type.intValue() == 1 && (pdfObject instanceof PdfStream)) {
            return new Tiling((PdfStream) pdfObject);
        }
        if (type.intValue() == 2) {
            return new Shading(pdfObject);
        }
        throw new IllegalArgumentException("pdfObject");
    }

    public PdfArray getMatrix() {
        return ((PdfDictionary) getPdfObject()).getAsArray(PdfName.Matrix);
    }

    public void setMatrix(PdfArray matrix) {
        ((PdfDictionary) getPdfObject()).put(PdfName.Matrix, matrix);
        setModified();
    }

    public void flush() {
        super.flush();
    }

    /* access modifiers changed from: protected */
    public boolean isWrappedObjectMustBeIndirect() {
        return true;
    }

    public static class Tiling extends PdfPattern {
        private static final long serialVersionUID = 1450379837955897673L;
        private PdfResources resources;

        public static class PaintType {
            public static final int COLORED = 1;
            public static final int UNCOLORED = 2;
        }

        public static class TilingType {
            public static final int CONSTANT_SPACING = 1;
            public static final int CONSTANT_SPACING_AND_FASTER_TILING = 3;
            public static final int NO_DISTORTION = 2;
        }

        public Tiling(PdfStream pdfObject) {
            super(pdfObject);
            this.resources = null;
        }

        public Tiling(float width, float height) {
            this(width, height, true);
        }

        public Tiling(float width, float height, boolean colored) {
            this(new Rectangle(width, height), colored);
        }

        public Tiling(Rectangle bbox) {
            this(bbox, true);
        }

        public Tiling(Rectangle bbox, boolean colored) {
            this(bbox, bbox.getWidth(), bbox.getHeight(), colored);
        }

        public Tiling(float width, float height, float xStep, float yStep) {
            this(width, height, xStep, yStep, true);
        }

        public Tiling(float width, float height, float xStep, float yStep, boolean colored) {
            this(new Rectangle(width, height), xStep, yStep, colored);
        }

        public Tiling(Rectangle bbox, float xStep, float yStep) {
            this(bbox, xStep, yStep, true);
        }

        public Tiling(Rectangle bbox, float xStep, float yStep, boolean colored) {
            super(new PdfStream());
            this.resources = null;
            ((PdfDictionary) getPdfObject()).put(PdfName.Type, PdfName.Pattern);
            ((PdfDictionary) getPdfObject()).put(PdfName.PatternType, new PdfNumber(1));
            ((PdfDictionary) getPdfObject()).put(PdfName.PaintType, new PdfNumber(colored ? 1 : 2));
            ((PdfDictionary) getPdfObject()).put(PdfName.TilingType, new PdfNumber(1));
            ((PdfDictionary) getPdfObject()).put(PdfName.BBox, new PdfArray(bbox));
            ((PdfDictionary) getPdfObject()).put(PdfName.XStep, new PdfNumber((double) xStep));
            ((PdfDictionary) getPdfObject()).put(PdfName.YStep, new PdfNumber((double) yStep));
            this.resources = new PdfResources();
            ((PdfDictionary) getPdfObject()).put(PdfName.Resources, this.resources.getPdfObject());
        }

        public boolean isColored() {
            return ((PdfDictionary) getPdfObject()).getAsNumber(PdfName.PaintType).intValue() == 1;
        }

        public void setColored(boolean colored) {
            ((PdfDictionary) getPdfObject()).put(PdfName.PaintType, new PdfNumber(colored ? 1 : 2));
            setModified();
        }

        public int getTilingType() {
            return ((PdfDictionary) getPdfObject()).getAsNumber(PdfName.TilingType).intValue();
        }

        public void setTilingType(int tilingType) {
            if (tilingType == 1 || tilingType == 2 || tilingType == 3) {
                ((PdfDictionary) getPdfObject()).put(PdfName.TilingType, new PdfNumber(tilingType));
                setModified();
                return;
            }
            throw new IllegalArgumentException("tilingType");
        }

        public Rectangle getBBox() {
            return ((PdfDictionary) getPdfObject()).getAsArray(PdfName.BBox).toRectangle();
        }

        public void setBBox(Rectangle bbox) {
            ((PdfDictionary) getPdfObject()).put(PdfName.BBox, new PdfArray(bbox));
            setModified();
        }

        public float getXStep() {
            return ((PdfDictionary) getPdfObject()).getAsNumber(PdfName.XStep).floatValue();
        }

        public void setXStep(float xStep) {
            ((PdfDictionary) getPdfObject()).put(PdfName.XStep, new PdfNumber((double) xStep));
            setModified();
        }

        public float getYStep() {
            return ((PdfDictionary) getPdfObject()).getAsNumber(PdfName.YStep).floatValue();
        }

        public void setYStep(float yStep) {
            ((PdfDictionary) getPdfObject()).put(PdfName.YStep, new PdfNumber((double) yStep));
            setModified();
        }

        public PdfResources getResources() {
            if (this.resources == null) {
                PdfDictionary resourcesDict = ((PdfDictionary) getPdfObject()).getAsDictionary(PdfName.Resources);
                if (resourcesDict == null) {
                    resourcesDict = new PdfDictionary();
                    ((PdfDictionary) getPdfObject()).put(PdfName.Resources, resourcesDict);
                }
                this.resources = new PdfResources(resourcesDict);
            }
            return this.resources;
        }

        public void flush() {
            this.resources = null;
            PdfPattern.super.flush();
        }
    }

    public static class Shading extends PdfPattern {
        private static final long serialVersionUID = -4289411438737403786L;

        public Shading(PdfDictionary pdfObject) {
            super(pdfObject);
        }

        public Shading(PdfShading shading) {
            super(new PdfDictionary());
            ((PdfDictionary) getPdfObject()).put(PdfName.Type, PdfName.Pattern);
            ((PdfDictionary) getPdfObject()).put(PdfName.PatternType, new PdfNumber(2));
            ((PdfDictionary) getPdfObject()).put(PdfName.Shading, shading.getPdfObject());
        }

        public PdfDictionary getShading() {
            return (PdfDictionary) ((PdfDictionary) getPdfObject()).get(PdfName.Shading);
        }

        public void setShading(PdfShading shading) {
            ((PdfDictionary) getPdfObject()).put(PdfName.Shading, shading.getPdfObject());
            setModified();
        }

        public void setShading(PdfDictionary shading) {
            ((PdfDictionary) getPdfObject()).put(PdfName.Shading, shading);
            setModified();
        }
    }
}
