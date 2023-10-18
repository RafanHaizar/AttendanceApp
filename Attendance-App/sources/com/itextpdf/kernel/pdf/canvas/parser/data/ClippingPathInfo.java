package com.itextpdf.kernel.pdf.canvas.parser.data;

import com.itextpdf.kernel.geom.Matrix;
import com.itextpdf.kernel.geom.Path;
import com.itextpdf.kernel.pdf.canvas.CanvasGraphicsState;

public class ClippingPathInfo extends AbstractRenderInfo {
    private Matrix ctm;
    private Path path;

    public ClippingPathInfo(CanvasGraphicsState gs, Path path2, Matrix ctm2) {
        super(gs);
        this.path = path2;
        this.ctm = ctm2;
    }

    public Path getClippingPath() {
        return this.path;
    }

    public Matrix getCtm() {
        return this.ctm;
    }
}
