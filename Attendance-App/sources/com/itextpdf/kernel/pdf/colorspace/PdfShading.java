package com.itextpdf.kernel.pdf.colorspace;

import com.itextpdf.kernel.PdfException;
import com.itextpdf.kernel.pdf.PdfArray;
import com.itextpdf.kernel.pdf.PdfDictionary;
import com.itextpdf.kernel.pdf.PdfName;
import com.itextpdf.kernel.pdf.PdfNumber;
import com.itextpdf.kernel.pdf.PdfObject;
import com.itextpdf.kernel.pdf.PdfObjectWrapper;
import com.itextpdf.kernel.pdf.PdfStream;
import com.itextpdf.kernel.pdf.colorspace.PdfSpecialCs;
import com.itextpdf.kernel.pdf.function.PdfFunction;

public abstract class PdfShading extends PdfObjectWrapper<PdfDictionary> {
    private static final long serialVersionUID = 4781809723744243508L;

    static final class ShadingType {
        public static final int AXIAL = 2;
        public static final int COONS_PATCH_MESH = 6;
        public static final int FREE_FORM_GOURAUD_SHADED_TRIANGLE_MESH = 4;
        public static final int FUNCTION_BASED = 1;
        public static final int LATTICE_FORM_GOURAUD_SHADED_TRIANGLE_MESH = 5;
        public static final int RADIAL = 3;
        public static final int TENSOR_PRODUCT_PATCH_MESH = 7;

        private ShadingType() {
        }
    }

    public static PdfShading makeShading(PdfDictionary shadingDictionary) {
        if (!shadingDictionary.containsKey(PdfName.ShadingType)) {
            throw new PdfException(PdfException.ShadingTypeNotFound);
        } else if (shadingDictionary.containsKey(PdfName.ColorSpace)) {
            switch (shadingDictionary.getAsNumber(PdfName.ShadingType).intValue()) {
                case 1:
                    return new FunctionBased(shadingDictionary);
                case 2:
                    return new Axial(shadingDictionary);
                case 3:
                    return new Radial(shadingDictionary);
                case 4:
                    if (shadingDictionary.isStream()) {
                        return new FreeFormGouraudShadedTriangleMesh((PdfStream) shadingDictionary);
                    }
                    throw new PdfException(PdfException.UnexpectedShadingType);
                case 5:
                    if (shadingDictionary.isStream()) {
                        return new LatticeFormGouraudShadedTriangleMesh((PdfStream) shadingDictionary);
                    }
                    throw new PdfException(PdfException.UnexpectedShadingType);
                case 6:
                    if (shadingDictionary.isStream()) {
                        return new CoonsPatchMesh((PdfStream) shadingDictionary);
                    }
                    throw new PdfException(PdfException.UnexpectedShadingType);
                case 7:
                    if (shadingDictionary.isStream()) {
                        return new TensorProductPatchMesh((PdfStream) shadingDictionary);
                    }
                    throw new PdfException(PdfException.UnexpectedShadingType);
                default:
                    throw new PdfException(PdfException.UnexpectedShadingType);
            }
        } else {
            throw new PdfException(PdfException.ColorSpaceNotFound);
        }
    }

    protected PdfShading(PdfDictionary pdfObject) {
        super(pdfObject);
    }

    protected PdfShading(PdfDictionary pdfObject, int type, PdfColorSpace colorSpace) {
        super(pdfObject);
        ((PdfDictionary) getPdfObject()).put(PdfName.ShadingType, new PdfNumber(type));
        if (!(colorSpace instanceof PdfSpecialCs.Pattern)) {
            ((PdfDictionary) getPdfObject()).put(PdfName.ColorSpace, colorSpace.getPdfObject());
            return;
        }
        throw new IllegalArgumentException("colorSpace");
    }

    public int getShadingType() {
        return ((PdfDictionary) getPdfObject()).getAsInt(PdfName.ShadingType).intValue();
    }

    public PdfObject getColorSpace() {
        return ((PdfDictionary) getPdfObject()).get(PdfName.ColorSpace);
    }

    public PdfObject getFunction() {
        return ((PdfDictionary) getPdfObject()).get(PdfName.Function);
    }

    public void setFunction(PdfFunction function) {
        ((PdfDictionary) getPdfObject()).put(PdfName.Function, function.getPdfObject());
        setModified();
    }

    public void setFunction(PdfFunction[] functions) {
        PdfArray arr = new PdfArray();
        for (PdfFunction func : functions) {
            arr.add(func.getPdfObject());
        }
        ((PdfDictionary) getPdfObject()).put(PdfName.Function, arr);
        setModified();
    }

    public void flush() {
        super.flush();
    }

    /* access modifiers changed from: protected */
    public boolean isWrappedObjectMustBeIndirect() {
        return true;
    }

    public static class FunctionBased extends PdfShading {
        private static final long serialVersionUID = -4459197498902558052L;

        protected FunctionBased(PdfDictionary pdfDictionary) {
            super(pdfDictionary);
        }

        public FunctionBased(PdfColorSpace colorSpace, PdfFunction function) {
            this(colorSpace.getPdfObject(), function);
        }

        public FunctionBased(PdfObject colorSpace, PdfFunction function) {
            super(new PdfDictionary(), 1, PdfColorSpace.makeColorSpace(colorSpace));
            setFunction(function);
        }

        public PdfArray getDomain() {
            return ((PdfDictionary) getPdfObject()).getAsArray(PdfName.Domain);
        }

        public void setDomain(float xmin, float xmax, float ymin, float ymax) {
            setDomain(new PdfArray(new float[]{xmin, xmax, ymin, ymax}));
        }

        public void setDomain(PdfArray domain) {
            ((PdfDictionary) getPdfObject()).put(PdfName.Domain, domain);
            setModified();
        }

        public PdfArray getMatrix() {
            PdfArray matrix = ((PdfDictionary) getPdfObject()).getAsArray(PdfName.Matrix);
            if (matrix != null) {
                return matrix;
            }
            PdfArray matrix2 = new PdfArray(new float[]{1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f});
            setMatrix(matrix2);
            return matrix2;
        }

        public void setMatrix(float[] matrix) {
            setMatrix(new PdfArray(matrix));
        }

        public void setMatrix(PdfArray matrix) {
            ((PdfDictionary) getPdfObject()).put(PdfName.Matrix, matrix);
            setModified();
        }
    }

    public static class Axial extends PdfShading {
        private static final long serialVersionUID = 5504688740677023792L;

        protected Axial(PdfDictionary pdfDictionary) {
            super(pdfDictionary);
        }

        public Axial(PdfColorSpace cs, float x0, float y0, float[] color0, float x1, float y1, float[] color1) {
            super(new PdfDictionary(), 2, cs);
            setCoords(x0, y0, x1, y1);
            setFunction((PdfFunction) new PdfFunction.Type2(new PdfArray(new float[]{0.0f, 1.0f}), (PdfArray) null, new PdfArray(color0), new PdfArray(color1), new PdfNumber(1)));
        }

        public Axial(PdfColorSpace cs, float x0, float y0, float[] color0, float x1, float y1, float[] color1, boolean[] extend) {
            this(cs, x0, y0, color0, x1, y1, color1);
            if (extend == null || extend.length != 2) {
                throw new IllegalArgumentException("extend");
            }
            setExtend(extend[0], extend[1]);
        }

        public Axial(PdfColorSpace cs, PdfArray coords, PdfFunction function) {
            this(cs, coords, (PdfArray) null, function);
        }

        public Axial(PdfColorSpace cs, PdfArray coords, PdfArray domain, PdfFunction function) {
            super(new PdfDictionary(), 2, cs);
            setCoords(coords);
            if (domain != null) {
                setDomain(domain);
            }
            setFunction(function);
        }

        public PdfArray getCoords() {
            return ((PdfDictionary) getPdfObject()).getAsArray(PdfName.Coords);
        }

        public void setCoords(float x0, float y0, float x1, float y1) {
            setCoords(new PdfArray(new float[]{x0, y0, x1, y1}));
        }

        public void setCoords(PdfArray coords) {
            ((PdfDictionary) getPdfObject()).put(PdfName.Coords, coords);
            setModified();
        }

        public PdfArray getDomain() {
            PdfArray domain = ((PdfDictionary) getPdfObject()).getAsArray(PdfName.Domain);
            if (domain != null) {
                return domain;
            }
            PdfArray domain2 = new PdfArray(new float[]{0.0f, 1.0f});
            setDomain(domain2);
            return domain2;
        }

        public void setDomain(float t0, float t1) {
            setDomain(new PdfArray(new float[]{t0, t1}));
        }

        public void setDomain(PdfArray domain) {
            ((PdfDictionary) getPdfObject()).put(PdfName.Domain, domain);
            setModified();
        }

        public PdfArray getExtend() {
            PdfArray extend = ((PdfDictionary) getPdfObject()).getAsArray(PdfName.Extend);
            if (extend != null) {
                return extend;
            }
            PdfArray extend2 = new PdfArray(new boolean[]{false, false});
            setExtend(extend2);
            return extend2;
        }

        public void setExtend(boolean extendStart, boolean extendEnd) {
            setExtend(new PdfArray(new boolean[]{extendStart, extendEnd}));
        }

        public void setExtend(PdfArray extend) {
            ((PdfDictionary) getPdfObject()).put(PdfName.Extend, extend);
            setModified();
        }
    }

    public static class Radial extends PdfShading {
        private static final long serialVersionUID = -5012819396006804845L;

        protected Radial(PdfDictionary pdfDictionary) {
            super(pdfDictionary);
        }

        public Radial(PdfColorSpace cs, float x0, float y0, float r0, float[] color0, float x1, float y1, float r1, float[] color1) {
            super(new PdfDictionary(), 3, cs);
            setCoords(x0, y0, r0, x1, y1, r1);
            setFunction((PdfFunction) new PdfFunction.Type2(new PdfArray(new float[]{0.0f, 1.0f}), (PdfArray) null, new PdfArray(color0), new PdfArray(color1), new PdfNumber(1)));
        }

        public Radial(PdfColorSpace cs, float x0, float y0, float r0, float[] color0, float x1, float y1, float r1, float[] color1, boolean[] extend) {
            this(cs, x0, y0, r0, color0, x1, y1, r1, color1);
            if (extend == null || extend.length != 2) {
                throw new IllegalArgumentException("extend");
            }
            setExtend(extend[0], extend[1]);
        }

        public Radial(PdfColorSpace cs, PdfArray coords, PdfFunction function) {
            super(new PdfDictionary(), 3, cs);
            setCoords(coords);
            setFunction(function);
        }

        public PdfArray getCoords() {
            return ((PdfDictionary) getPdfObject()).getAsArray(PdfName.Coords);
        }

        public void setCoords(float x0, float y0, float r0, float x1, float y1, float r1) {
            setCoords(new PdfArray(new float[]{x0, y0, r0, x1, y1, r1}));
        }

        public void setCoords(PdfArray coords) {
            ((PdfDictionary) getPdfObject()).put(PdfName.Coords, coords);
            setModified();
        }

        public PdfArray getDomain() {
            PdfArray domain = ((PdfDictionary) getPdfObject()).getAsArray(PdfName.Domain);
            if (domain != null) {
                return domain;
            }
            PdfArray domain2 = new PdfArray(new float[]{0.0f, 1.0f});
            setDomain(domain2);
            return domain2;
        }

        public void setDomain(float t0, float t1) {
            setDomain(new PdfArray(new float[]{t0, t1}));
        }

        public void setDomain(PdfArray domain) {
            ((PdfDictionary) getPdfObject()).put(PdfName.Domain, domain);
            setModified();
        }

        public PdfArray getExtend() {
            PdfArray extend = ((PdfDictionary) getPdfObject()).getAsArray(PdfName.Extend);
            if (extend != null) {
                return extend;
            }
            PdfArray extend2 = new PdfArray(new boolean[]{false, false});
            setExtend(extend2);
            return extend2;
        }

        public void setExtend(boolean extendStart, boolean extendEnd) {
            setExtend(new PdfArray(new boolean[]{extendStart, extendEnd}));
        }

        public void setExtend(PdfArray extend) {
            ((PdfDictionary) getPdfObject()).put(PdfName.Extend, extend);
            setModified();
        }
    }

    public static class FreeFormGouraudShadedTriangleMesh extends PdfShading {
        private static final long serialVersionUID = -2690557760051875972L;

        protected FreeFormGouraudShadedTriangleMesh(PdfStream pdfStream) {
            super(pdfStream);
        }

        public FreeFormGouraudShadedTriangleMesh(PdfColorSpace cs, int bitsPerCoordinate, int bitsPerComponent, int bitsPerFlag, float[] decode) {
            this(cs, bitsPerCoordinate, bitsPerComponent, bitsPerFlag, new PdfArray(decode));
        }

        public FreeFormGouraudShadedTriangleMesh(PdfColorSpace cs, int bitsPerCoordinate, int bitsPerComponent, int bitsPerFlag, PdfArray decode) {
            super(new PdfStream(), 4, cs);
            setBitsPerCoordinate(bitsPerCoordinate);
            setBitsPerComponent(bitsPerComponent);
            setBitsPerFlag(bitsPerFlag);
            setDecode(decode);
        }

        public int getBitsPerCoordinate() {
            return ((PdfDictionary) getPdfObject()).getAsInt(PdfName.BitsPerCoordinate).intValue();
        }

        public void setBitsPerCoordinate(int bitsPerCoordinate) {
            ((PdfDictionary) getPdfObject()).put(PdfName.BitsPerCoordinate, new PdfNumber(bitsPerCoordinate));
            setModified();
        }

        public int getBitsPerComponent() {
            return ((PdfDictionary) getPdfObject()).getAsInt(PdfName.BitsPerComponent).intValue();
        }

        public void setBitsPerComponent(int bitsPerComponent) {
            ((PdfDictionary) getPdfObject()).put(PdfName.BitsPerComponent, new PdfNumber(bitsPerComponent));
            setModified();
        }

        public int getBitsPerFlag() {
            return ((PdfDictionary) getPdfObject()).getAsInt(PdfName.BitsPerFlag).intValue();
        }

        public void setBitsPerFlag(int bitsPerFlag) {
            ((PdfDictionary) getPdfObject()).put(PdfName.BitsPerFlag, new PdfNumber(bitsPerFlag));
            setModified();
        }

        public PdfArray getDecode() {
            return ((PdfDictionary) getPdfObject()).getAsArray(PdfName.Decode);
        }

        public void setDecode(float[] decode) {
            setDecode(new PdfArray(decode));
        }

        public void setDecode(PdfArray decode) {
            ((PdfDictionary) getPdfObject()).put(PdfName.Decode, decode);
            setModified();
        }
    }

    public static class LatticeFormGouraudShadedTriangleMesh extends PdfShading {
        private static final long serialVersionUID = -8776232978423888214L;

        protected LatticeFormGouraudShadedTriangleMesh(PdfStream pdfStream) {
            super(pdfStream);
        }

        public LatticeFormGouraudShadedTriangleMesh(PdfColorSpace cs, int bitsPerCoordinate, int bitsPerComponent, int verticesPerRow, float[] decode) {
            this(cs, bitsPerCoordinate, bitsPerComponent, verticesPerRow, new PdfArray(decode));
        }

        public LatticeFormGouraudShadedTriangleMesh(PdfColorSpace cs, int bitsPerCoordinate, int bitsPerComponent, int verticesPerRow, PdfArray decode) {
            super(new PdfStream(), 5, cs);
            setBitsPerCoordinate(bitsPerCoordinate);
            setBitsPerComponent(bitsPerComponent);
            setVerticesPerRow(verticesPerRow);
            setDecode(decode);
        }

        public int getBitsPerCoordinate() {
            return ((PdfDictionary) getPdfObject()).getAsInt(PdfName.BitsPerCoordinate).intValue();
        }

        public void setBitsPerCoordinate(int bitsPerCoordinate) {
            ((PdfDictionary) getPdfObject()).put(PdfName.BitsPerCoordinate, new PdfNumber(bitsPerCoordinate));
            setModified();
        }

        public int getBitsPerComponent() {
            return ((PdfDictionary) getPdfObject()).getAsInt(PdfName.BitsPerComponent).intValue();
        }

        public void setBitsPerComponent(int bitsPerComponent) {
            ((PdfDictionary) getPdfObject()).put(PdfName.BitsPerComponent, new PdfNumber(bitsPerComponent));
            setModified();
        }

        public int getVerticesPerRow() {
            return ((PdfDictionary) getPdfObject()).getAsInt(PdfName.VerticesPerRow).intValue();
        }

        public void setVerticesPerRow(int verticesPerRow) {
            ((PdfDictionary) getPdfObject()).put(PdfName.VerticesPerRow, new PdfNumber(verticesPerRow));
            setModified();
        }

        public PdfArray getDecode() {
            return ((PdfDictionary) getPdfObject()).getAsArray(PdfName.Decode);
        }

        public void setDecode(float[] decode) {
            setDecode(new PdfArray(decode));
        }

        public void setDecode(PdfArray decode) {
            ((PdfDictionary) getPdfObject()).put(PdfName.Decode, decode);
            setModified();
        }
    }

    public static class CoonsPatchMesh extends PdfShading {
        private static final long serialVersionUID = 7296891352801419708L;

        protected CoonsPatchMesh(PdfStream pdfStream) {
            super(pdfStream);
        }

        public CoonsPatchMesh(PdfColorSpace cs, int bitsPerCoordinate, int bitsPerComponent, int bitsPerFlag, float[] decode) {
            this(cs, bitsPerCoordinate, bitsPerComponent, bitsPerFlag, new PdfArray(decode));
        }

        public CoonsPatchMesh(PdfColorSpace cs, int bitsPerCoordinate, int bitsPerComponent, int bitsPerFlag, PdfArray decode) {
            super(new PdfStream(), 6, cs);
            setBitsPerCoordinate(bitsPerCoordinate);
            setBitsPerComponent(bitsPerComponent);
            setBitsPerFlag(bitsPerFlag);
            setDecode(decode);
        }

        public int getBitsPerCoordinate() {
            return ((PdfDictionary) getPdfObject()).getAsInt(PdfName.BitsPerCoordinate).intValue();
        }

        public void setBitsPerCoordinate(int bitsPerCoordinate) {
            ((PdfDictionary) getPdfObject()).put(PdfName.BitsPerCoordinate, new PdfNumber(bitsPerCoordinate));
            setModified();
        }

        public int getBitsPerComponent() {
            return ((PdfDictionary) getPdfObject()).getAsInt(PdfName.BitsPerComponent).intValue();
        }

        public void setBitsPerComponent(int bitsPerComponent) {
            ((PdfDictionary) getPdfObject()).put(PdfName.BitsPerComponent, new PdfNumber(bitsPerComponent));
            setModified();
        }

        public int getBitsPerFlag() {
            return ((PdfDictionary) getPdfObject()).getAsInt(PdfName.BitsPerFlag).intValue();
        }

        public void setBitsPerFlag(int bitsPerFlag) {
            ((PdfDictionary) getPdfObject()).put(PdfName.BitsPerFlag, new PdfNumber(bitsPerFlag));
            setModified();
        }

        public PdfArray getDecode() {
            return ((PdfDictionary) getPdfObject()).getAsArray(PdfName.Decode);
        }

        public void setDecode(float[] decode) {
            setDecode(new PdfArray(decode));
        }

        public void setDecode(PdfArray decode) {
            ((PdfDictionary) getPdfObject()).put(PdfName.Decode, decode);
            setModified();
        }
    }

    public static class TensorProductPatchMesh extends PdfShading {
        private static final long serialVersionUID = -2750695839303504742L;

        protected TensorProductPatchMesh(PdfStream pdfStream) {
            super(pdfStream);
        }

        public TensorProductPatchMesh(PdfColorSpace cs, int bitsPerCoordinate, int bitsPerComponent, int bitsPerFlag, float[] decode) {
            this(cs, bitsPerCoordinate, bitsPerComponent, bitsPerFlag, new PdfArray(decode));
        }

        public TensorProductPatchMesh(PdfColorSpace cs, int bitsPerCoordinate, int bitsPerComponent, int bitsPerFlag, PdfArray decode) {
            super(new PdfStream(), 7, cs);
            setBitsPerCoordinate(bitsPerCoordinate);
            setBitsPerComponent(bitsPerComponent);
            setBitsPerFlag(bitsPerFlag);
            setDecode(decode);
        }

        public int getBitsPerCoordinate() {
            return ((PdfDictionary) getPdfObject()).getAsInt(PdfName.BitsPerCoordinate).intValue();
        }

        public void setBitsPerCoordinate(int bitsPerCoordinate) {
            ((PdfDictionary) getPdfObject()).put(PdfName.BitsPerCoordinate, new PdfNumber(bitsPerCoordinate));
            setModified();
        }

        public int getBitsPerComponent() {
            return ((PdfDictionary) getPdfObject()).getAsInt(PdfName.BitsPerComponent).intValue();
        }

        public void setBitsPerComponent(int bitsPerComponent) {
            ((PdfDictionary) getPdfObject()).put(PdfName.BitsPerComponent, new PdfNumber(bitsPerComponent));
            setModified();
        }

        public int getBitsPerFlag() {
            return ((PdfDictionary) getPdfObject()).getAsInt(PdfName.BitsPerFlag).intValue();
        }

        public void setBitsPerFlag(int bitsPerFlag) {
            ((PdfDictionary) getPdfObject()).put(PdfName.BitsPerFlag, new PdfNumber(bitsPerFlag));
            setModified();
        }

        public PdfArray getDecode() {
            return ((PdfDictionary) getPdfObject()).getAsArray(PdfName.Decode);
        }

        public void setDecode(float[] decode) {
            setDecode(new PdfArray(decode));
        }

        public void setDecode(PdfArray decode) {
            ((PdfDictionary) getPdfObject()).put(PdfName.Decode, decode);
            setModified();
        }
    }
}
