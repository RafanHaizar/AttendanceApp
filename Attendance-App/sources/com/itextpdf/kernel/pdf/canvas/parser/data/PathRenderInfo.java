package com.itextpdf.kernel.pdf.canvas.parser.data;

import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.geom.Matrix;
import com.itextpdf.kernel.geom.Path;
import com.itextpdf.kernel.pdf.PdfArray;
import com.itextpdf.kernel.pdf.canvas.CanvasGraphicsState;
import com.itextpdf.kernel.pdf.canvas.CanvasTag;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

public class PathRenderInfo extends AbstractRenderInfo {
    public static final int FILL = 2;
    public static final int NO_OP = 0;
    public static final int STROKE = 1;
    private List<CanvasTag> canvasTagHierarchy;
    private int clippingRule;
    private boolean isClip;
    private int operation;
    private Path path;
    private int rule;

    public PathRenderInfo(Stack<CanvasTag> canvasTagHierarchy2, CanvasGraphicsState gs, Path path2, int operation2, int rule2, boolean isClip2, int clipRule) {
        super(gs);
        this.canvasTagHierarchy = Collections.unmodifiableList(new ArrayList(canvasTagHierarchy2));
        this.path = path2;
        this.operation = operation2;
        this.rule = rule2;
        this.isClip = isClip2;
        this.clippingRule = clipRule;
    }

    public PathRenderInfo(Stack<CanvasTag> canvasTagHierarchy2, CanvasGraphicsState gs, Path path2, int operation2) {
        this(canvasTagHierarchy2, gs, path2, operation2, 1, false, 1);
    }

    public Path getPath() {
        return this.path;
    }

    public int getOperation() {
        return this.operation;
    }

    public int getRule() {
        return this.rule;
    }

    public boolean isPathModifiesClippingPath() {
        return this.isClip;
    }

    public int getClippingRule() {
        return this.clippingRule;
    }

    public Matrix getCtm() {
        checkGraphicsState();
        return this.f1495gs.getCtm();
    }

    public float getLineWidth() {
        checkGraphicsState();
        return this.f1495gs.getLineWidth();
    }

    public int getLineCapStyle() {
        checkGraphicsState();
        return this.f1495gs.getLineCapStyle();
    }

    public int getLineJoinStyle() {
        checkGraphicsState();
        return this.f1495gs.getLineJoinStyle();
    }

    public float getMiterLimit() {
        checkGraphicsState();
        return this.f1495gs.getMiterLimit();
    }

    public PdfArray getLineDashPattern() {
        checkGraphicsState();
        return this.f1495gs.getDashPattern();
    }

    public Color getStrokeColor() {
        checkGraphicsState();
        return this.f1495gs.getStrokeColor();
    }

    public Color getFillColor() {
        checkGraphicsState();
        return this.f1495gs.getFillColor();
    }

    public List<CanvasTag> getCanvasTagHierarchy() {
        return this.canvasTagHierarchy;
    }

    public int getMcid() {
        for (CanvasTag tag : this.canvasTagHierarchy) {
            if (tag.hasMcid()) {
                return tag.getMcid();
            }
        }
        return -1;
    }

    public boolean hasMcid(int mcid) {
        return hasMcid(mcid, false);
    }

    public boolean hasMcid(int mcid, boolean checkTheTopmostLevelOnly) {
        int infoMcid;
        if (!checkTheTopmostLevelOnly) {
            for (CanvasTag tag : this.canvasTagHierarchy) {
                if (tag.hasMcid() && tag.getMcid() == mcid) {
                    return true;
                }
            }
        } else if (this.canvasTagHierarchy == null || (infoMcid = getMcid()) == -1 || infoMcid != mcid) {
            return false;
        } else {
            return true;
        }
        return false;
    }
}
