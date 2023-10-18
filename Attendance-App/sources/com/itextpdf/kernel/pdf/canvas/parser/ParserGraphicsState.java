package com.itextpdf.kernel.pdf.canvas.parser;

import com.itextpdf.kernel.geom.Matrix;
import com.itextpdf.kernel.geom.Path;
import com.itextpdf.kernel.geom.ShapeTransformUtil;
import com.itextpdf.kernel.pdf.canvas.CanvasGraphicsState;
import com.itextpdf.kernel.pdf.canvas.parser.clipper.ClipperBridge;
import com.itextpdf.kernel.pdf.canvas.parser.clipper.DefaultClipper;
import com.itextpdf.kernel.pdf.canvas.parser.clipper.IClipper;
import com.itextpdf.kernel.pdf.canvas.parser.clipper.PolyTree;

public class ParserGraphicsState extends CanvasGraphicsState {
    private static final long serialVersionUID = 5402909016194922120L;
    private Path clippingPath;

    ParserGraphicsState() {
    }

    ParserGraphicsState(ParserGraphicsState source) {
        super(source);
        if (source.clippingPath != null) {
            this.clippingPath = new Path(source.clippingPath);
        }
    }

    public void updateCtm(Matrix newCtm) {
        super.updateCtm(newCtm);
        if (this.clippingPath != null) {
            transformClippingPath(newCtm);
        }
    }

    public void clip(Path path, int fillingRule) {
        Path path2 = this.clippingPath;
        if (path2 != null && !path2.isEmpty()) {
            Path pathCopy = new Path(path);
            pathCopy.closeAllSubpaths();
            IClipper clipper = new DefaultClipper();
            ClipperBridge.addPath(clipper, this.clippingPath, IClipper.PolyType.SUBJECT);
            ClipperBridge.addPath(clipper, pathCopy, IClipper.PolyType.CLIP);
            PolyTree resultTree = new PolyTree();
            clipper.execute(IClipper.ClipType.INTERSECTION, resultTree, IClipper.PolyFillType.NON_ZERO, ClipperBridge.getFillType(fillingRule));
            this.clippingPath = ClipperBridge.convertToPath(resultTree);
        }
    }

    public Path getClippingPath() {
        return this.clippingPath;
    }

    public void setClippingPath(Path clippingPath2) {
        Path pathCopy = new Path(clippingPath2);
        pathCopy.closeAllSubpaths();
        this.clippingPath = pathCopy;
    }

    private void transformClippingPath(Matrix newCtm) {
        this.clippingPath = ShapeTransformUtil.transformPath(this.clippingPath, newCtm);
    }
}
