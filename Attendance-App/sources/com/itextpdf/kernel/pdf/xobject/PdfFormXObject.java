package com.itextpdf.kernel.pdf.xobject;

import com.itextpdf.kernel.PdfException;
import com.itextpdf.kernel.geom.Matrix;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.geom.Vector;
import com.itextpdf.kernel.pdf.PdfArray;
import com.itextpdf.kernel.pdf.PdfDictionary;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfName;
import com.itextpdf.kernel.pdf.PdfObject;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfResources;
import com.itextpdf.kernel.pdf.PdfStream;
import com.itextpdf.kernel.pdf.PdfString;
import com.itextpdf.kernel.pdf.canvas.wmf.WmfImageData;
import com.itextpdf.kernel.pdf.canvas.wmf.WmfImageHelper;

public class PdfFormXObject extends PdfXObject {
    private static final long serialVersionUID = 467500482711722178L;
    protected PdfResources resources;

    public PdfFormXObject(Rectangle bBox) {
        super(new PdfStream());
        this.resources = null;
        ((PdfStream) getPdfObject()).put(PdfName.Type, PdfName.XObject);
        ((PdfStream) getPdfObject()).put(PdfName.Subtype, PdfName.Form);
        if (bBox != null) {
            ((PdfStream) getPdfObject()).put(PdfName.BBox, new PdfArray(bBox));
        }
    }

    public PdfFormXObject(PdfStream pdfStream) {
        super(pdfStream);
        this.resources = null;
        if (!((PdfStream) getPdfObject()).containsKey(PdfName.Subtype)) {
            ((PdfStream) getPdfObject()).put(PdfName.Subtype, PdfName.Form);
        }
    }

    public PdfFormXObject(PdfPage page) {
        this(page.getCropBox());
        ((PdfStream) getPdfObject()).getOutputStream().writeBytes(page.getContentBytes());
        this.resources = new PdfResources((PdfDictionary) ((PdfDictionary) page.getResources().getPdfObject()).clone());
        ((PdfStream) getPdfObject()).put(PdfName.Resources, this.resources.getPdfObject());
    }

    public PdfFormXObject(WmfImageData image, PdfDocument pdfDocument) {
        this((PdfStream) new WmfImageHelper(image).createFormXObject(pdfDocument).getPdfObject());
    }

    public static Rectangle calculateBBoxMultipliedByMatrix(PdfFormXObject form) {
        float[] matrixArray;
        PdfArray pdfArrayBBox = ((PdfStream) form.getPdfObject()).getAsArray(PdfName.BBox);
        if (pdfArrayBBox != null) {
            float[] bBoxArray = pdfArrayBBox.toFloatArray();
            PdfArray pdfArrayMatrix = ((PdfStream) form.getPdfObject()).getAsArray(PdfName.Matrix);
            if (pdfArrayMatrix == null) {
                matrixArray = new float[]{1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f};
            } else {
                matrixArray = pdfArrayMatrix.toFloatArray();
            }
            Matrix matrix = new Matrix(matrixArray[0], matrixArray[1], matrixArray[2], matrixArray[3], matrixArray[4], matrixArray[5]);
            Vector bBoxMin = new Vector(bBoxArray[0], bBoxArray[1], 1.0f);
            Vector bBoxMax = new Vector(bBoxArray[2], bBoxArray[3], 1.0f);
            Vector bBoxMinByMatrix = bBoxMin.cross(matrix);
            Vector bBoxMaxByMatrix = bBoxMax.cross(matrix);
            return new Rectangle(bBoxMinByMatrix.get(0), bBoxMinByMatrix.get(1), bBoxMaxByMatrix.get(0) - bBoxMinByMatrix.get(0), bBoxMaxByMatrix.get(1) - bBoxMinByMatrix.get(1));
        }
        throw new PdfException(PdfException.PdfFormXobjectHasInvalidBbox);
    }

    public PdfResources getResources() {
        if (this.resources == null) {
            PdfDictionary resourcesDict = ((PdfStream) getPdfObject()).getAsDictionary(PdfName.Resources);
            if (resourcesDict == null) {
                resourcesDict = new PdfDictionary();
                ((PdfStream) getPdfObject()).put(PdfName.Resources, resourcesDict);
            }
            this.resources = new PdfResources(resourcesDict);
        }
        return this.resources;
    }

    public PdfArray getBBox() {
        return ((PdfStream) getPdfObject()).getAsArray(PdfName.BBox);
    }

    public PdfFormXObject setBBox(PdfArray bBox) {
        return put(PdfName.BBox, bBox);
    }

    public PdfFormXObject setGroup(PdfTransparencyGroup transparency) {
        return put(PdfName.Group, transparency.getPdfObject());
    }

    public float getWidth() {
        if (getBBox() == null) {
            return 0.0f;
        }
        return getBBox().getAsNumber(2).floatValue() - getBBox().getAsNumber(0).floatValue();
    }

    public float getHeight() {
        if (getBBox() == null) {
            return 0.0f;
        }
        return getBBox().getAsNumber(3).floatValue() - getBBox().getAsNumber(1).floatValue();
    }

    public void flush() {
        this.resources = null;
        if (((PdfStream) getPdfObject()).get(PdfName.BBox) != null) {
            super.flush();
            return;
        }
        throw new PdfException(PdfException.FormXObjectMustHaveBbox);
    }

    public PdfFormXObject setProcessColorModel(PdfName model) {
        return put(PdfName.PCM, model);
    }

    public PdfName getProcessColorModel() {
        return ((PdfStream) getPdfObject()).getAsName(PdfName.PCM);
    }

    public PdfFormXObject setSeparationColorNames(PdfArray colorNames) {
        return put(PdfName.SeparationColorNames, colorNames);
    }

    public PdfArray getSeparationColorNames() {
        return ((PdfStream) getPdfObject()).getAsArray(PdfName.SeparationColorNames);
    }

    public PdfFormXObject setTrapRegions(PdfArray regions) {
        return put(PdfName.TrapRegions, regions);
    }

    public PdfArray getTrapRegions() {
        return ((PdfStream) getPdfObject()).getAsArray(PdfName.TrapRegions);
    }

    public PdfFormXObject setTrapStyles(PdfString trapStyles) {
        return put(PdfName.TrapStyles, trapStyles);
    }

    public PdfString getTrapStyles() {
        return ((PdfStream) getPdfObject()).getAsString(PdfName.TrapStyles);
    }

    public PdfFormXObject setMarkStyle(PdfString markStyle) {
        return put(PdfName.MarkStyle, markStyle);
    }

    public PdfString getMarkStyle() {
        return ((PdfStream) getPdfObject()).getAsString(PdfName.MarkStyle);
    }

    public PdfFormXObject put(PdfName key, PdfObject value) {
        ((PdfStream) getPdfObject()).put(key, value);
        setModified();
        return this;
    }
}
