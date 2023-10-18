package com.itextpdf.kernel.pdf.xobject;

import com.itextpdf.kernel.PdfException;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfArray;
import com.itextpdf.kernel.pdf.PdfDictionary;
import com.itextpdf.kernel.pdf.PdfName;
import com.itextpdf.kernel.pdf.PdfObjectWrapper;
import com.itextpdf.kernel.pdf.PdfStream;
import com.itextpdf.kernel.pdf.filespec.PdfFileSpec;
import com.itextpdf.kernel.pdf.layer.IPdfOCG;
import com.itextpdf.p026io.LogMessageConstant;
import org.slf4j.LoggerFactory;

public abstract class PdfXObject extends PdfObjectWrapper<PdfStream> {
    private static final long serialVersionUID = -480702872582809954L;

    protected PdfXObject(PdfStream pdfObject) {
        super(pdfObject);
    }

    public static PdfXObject makeXObject(PdfStream stream) {
        if (PdfName.Form.equals(stream.getAsName(PdfName.Subtype))) {
            return new PdfFormXObject(stream);
        }
        if (PdfName.Image.equals(stream.getAsName(PdfName.Subtype))) {
            return new PdfImageXObject(stream);
        }
        throw new UnsupportedOperationException(PdfException.UnsupportedXObjectType);
    }

    public static Rectangle calculateProportionallyFitRectangleWithWidth(PdfXObject xObject, float x, float y, float width) {
        if (xObject instanceof PdfFormXObject) {
            Rectangle bBox = PdfFormXObject.calculateBBoxMultipliedByMatrix((PdfFormXObject) xObject);
            return new Rectangle(x, y, width, (width / bBox.getWidth()) * bBox.getHeight());
        } else if (xObject instanceof PdfImageXObject) {
            PdfImageXObject imageXObject = (PdfImageXObject) xObject;
            return new Rectangle(x, y, width, (width / imageXObject.getWidth()) * imageXObject.getHeight());
        } else {
            throw new IllegalArgumentException("PdfFormXObject or PdfImageXObject expected.");
        }
    }

    public static Rectangle calculateProportionallyFitRectangleWithHeight(PdfXObject xObject, float x, float y, float height) {
        if (xObject instanceof PdfFormXObject) {
            Rectangle bBox = PdfFormXObject.calculateBBoxMultipliedByMatrix((PdfFormXObject) xObject);
            return new Rectangle(x, y, (height / bBox.getHeight()) * bBox.getWidth(), height);
        } else if (xObject instanceof PdfImageXObject) {
            PdfImageXObject imageXObject = (PdfImageXObject) xObject;
            return new Rectangle(x, y, (height / imageXObject.getHeight()) * imageXObject.getWidth(), height);
        } else {
            throw new IllegalArgumentException("PdfFormXObject or PdfImageXObject expected.");
        }
    }

    public void setLayer(IPdfOCG layer) {
        ((PdfStream) getPdfObject()).put(PdfName.f1362OC, layer.getIndirectReference());
    }

    public float getWidth() {
        throw new UnsupportedOperationException();
    }

    public float getHeight() {
        throw new UnsupportedOperationException();
    }

    public void addAssociatedFile(PdfFileSpec fs) {
        if (((PdfDictionary) fs.getPdfObject()).get(PdfName.AFRelationship) == null) {
            LoggerFactory.getLogger((Class<?>) PdfXObject.class).error(LogMessageConstant.ASSOCIATED_FILE_SPEC_SHALL_INCLUDE_AFRELATIONSHIP);
        }
        PdfArray afArray = ((PdfStream) getPdfObject()).getAsArray(PdfName.f1289AF);
        if (afArray == null) {
            afArray = new PdfArray();
            ((PdfStream) getPdfObject()).put(PdfName.f1289AF, afArray);
        }
        afArray.add(fs.getPdfObject());
    }

    public PdfArray getAssociatedFiles(boolean create) {
        PdfArray afArray = ((PdfStream) getPdfObject()).getAsArray(PdfName.f1289AF);
        if (afArray != null || !create) {
            return afArray;
        }
        PdfArray afArray2 = new PdfArray();
        ((PdfStream) getPdfObject()).put(PdfName.f1289AF, afArray2);
        return afArray2;
    }

    /* access modifiers changed from: protected */
    public boolean isWrappedObjectMustBeIndirect() {
        return true;
    }
}
