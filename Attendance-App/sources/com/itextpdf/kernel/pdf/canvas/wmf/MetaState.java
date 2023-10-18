package com.itextpdf.kernel.pdf.canvas.wmf;

import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.geom.Point;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class MetaState {
    public static final int ALTERNATE = 1;
    public static final int OPAQUE = 2;
    public static final int TA_BASELINE = 24;
    public static final int TA_BOTTOM = 8;
    public static final int TA_CENTER = 6;
    public static final int TA_LEFT = 0;
    public static final int TA_NOUPDATECP = 0;
    public static final int TA_RIGHT = 2;
    public static final int TA_TOP = 0;
    public static final int TA_UPDATECP = 1;
    public static final int TRANSPARENT = 1;
    public static final int WINDING = 2;
    public List<MetaObject> MetaObjects;
    public int backgroundMode;
    public Color currentBackgroundColor;
    public MetaBrush currentBrush;
    public MetaFont currentFont;
    public MetaPen currentPen;
    public Point currentPoint;
    public Color currentTextColor;
    public int extentWx;
    public int extentWy;
    public int lineJoin;
    public int offsetWx;
    public int offsetWy;
    public int polyFillMode;
    public Stack<MetaState> savedStates;
    public float scalingX;
    public float scalingY;
    public int textAlign;

    public MetaState() {
        this.currentBackgroundColor = ColorConstants.WHITE;
        this.currentTextColor = ColorConstants.BLACK;
        this.backgroundMode = 2;
        this.polyFillMode = 1;
        this.lineJoin = 1;
        this.savedStates = new Stack<>();
        this.MetaObjects = new ArrayList();
        this.currentPoint = new Point(0, 0);
        this.currentPen = new MetaPen();
        this.currentBrush = new MetaBrush();
        this.currentFont = new MetaFont();
    }

    public MetaState(MetaState state) {
        this.currentBackgroundColor = ColorConstants.WHITE;
        this.currentTextColor = ColorConstants.BLACK;
        this.backgroundMode = 2;
        this.polyFillMode = 1;
        this.lineJoin = 1;
        setMetaState(state);
    }

    public void setMetaState(MetaState state) {
        this.savedStates = state.savedStates;
        this.MetaObjects = state.MetaObjects;
        this.currentPoint = state.currentPoint;
        this.currentPen = state.currentPen;
        this.currentBrush = state.currentBrush;
        this.currentFont = state.currentFont;
        this.currentBackgroundColor = state.currentBackgroundColor;
        this.currentTextColor = state.currentTextColor;
        this.backgroundMode = state.backgroundMode;
        this.polyFillMode = state.polyFillMode;
        this.textAlign = state.textAlign;
        this.lineJoin = state.lineJoin;
        this.offsetWx = state.offsetWx;
        this.offsetWy = state.offsetWy;
        this.extentWx = state.extentWx;
        this.extentWy = state.extentWy;
        this.scalingX = state.scalingX;
        this.scalingY = state.scalingY;
    }

    public void addMetaObject(MetaObject object) {
        for (int k = 0; k < this.MetaObjects.size(); k++) {
            if (this.MetaObjects.get(k) == null) {
                this.MetaObjects.set(k, object);
                return;
            }
        }
        this.MetaObjects.add(object);
    }

    public void selectMetaObject(int index, PdfCanvas cb) {
        MetaObject obj = this.MetaObjects.get(index);
        if (obj != null) {
            switch (obj.getType()) {
                case 1:
                    MetaPen metaPen = (MetaPen) obj;
                    this.currentPen = metaPen;
                    int style = metaPen.getStyle();
                    if (style != 5) {
                        cb.setStrokeColor(this.currentPen.getColor());
                        cb.setLineWidth(Math.abs((((float) this.currentPen.getPenWidth()) * this.scalingX) / ((float) this.extentWx)));
                        switch (style) {
                            case 1:
                                cb.setLineDash(18.0f, 6.0f, 0.0f);
                                return;
                            case 2:
                                cb.setLineDash(3.0f, 0.0f);
                                return;
                            case 3:
                                cb.writeLiteral("[9 6 3 6]0 d\n");
                                return;
                            case 4:
                                cb.writeLiteral("[9 3 3 3 3 3]0 d\n");
                                return;
                            default:
                                cb.setLineDash(0.0f);
                                return;
                        }
                    } else {
                        return;
                    }
                case 2:
                    MetaBrush metaBrush = (MetaBrush) obj;
                    this.currentBrush = metaBrush;
                    int style2 = metaBrush.getStyle();
                    if (style2 == 0) {
                        cb.setFillColor(this.currentBrush.getColor());
                        return;
                    } else if (style2 == 2) {
                        cb.setFillColor(this.currentBackgroundColor);
                        return;
                    } else {
                        return;
                    }
                case 3:
                    this.currentFont = (MetaFont) obj;
                    return;
                default:
                    return;
            }
        }
    }

    public void deleteMetaObject(int index) {
        this.MetaObjects.set(index, (Object) null);
    }

    public void saveState(PdfCanvas cb) {
        cb.saveState();
        this.savedStates.push(new MetaState(this));
    }

    public void restoreState(int index, PdfCanvas cb) {
        int pops;
        if (index < 0) {
            pops = Math.min(-index, this.savedStates.size());
        } else {
            pops = Math.max(this.savedStates.size() - index, 0);
        }
        if (pops != 0) {
            MetaState state = null;
            while (true) {
                int pops2 = pops - 1;
                if (pops != 0) {
                    cb.restoreState();
                    state = this.savedStates.pop();
                    pops = pops2;
                } else {
                    setMetaState(state);
                    return;
                }
            }
        }
    }

    public void cleanup(PdfCanvas cb) {
        int k = this.savedStates.size();
        while (true) {
            int k2 = k - 1;
            if (k > 0) {
                cb.restoreState();
                k = k2;
            } else {
                return;
            }
        }
    }

    public float transformX(int x) {
        return ((((float) x) - ((float) this.offsetWx)) * this.scalingX) / ((float) this.extentWx);
    }

    public float transformY(int y) {
        return (1.0f - ((((float) y) - ((float) this.offsetWy)) / ((float) this.extentWy))) * this.scalingY;
    }

    public void setScalingX(float scalingX2) {
        this.scalingX = scalingX2;
    }

    public void setScalingY(float scalingY2) {
        this.scalingY = scalingY2;
    }

    public void setOffsetWx(int offsetWx2) {
        this.offsetWx = offsetWx2;
    }

    public void setOffsetWy(int offsetWy2) {
        this.offsetWy = offsetWy2;
    }

    public void setExtentWx(int extentWx2) {
        this.extentWx = extentWx2;
    }

    public void setExtentWy(int extentWy2) {
        this.extentWy = extentWy2;
    }

    public float transformAngle(float angle) {
        double d;
        float ta = this.scalingY < 0.0f ? -angle : angle;
        if (this.scalingX < 0.0f) {
            double d2 = (double) ta;
            Double.isNaN(d2);
            d = 3.141592653589793d - d2;
        } else {
            d = (double) ta;
        }
        return (float) d;
    }

    public void setCurrentPoint(Point p) {
        this.currentPoint = p;
    }

    public Point getCurrentPoint() {
        return this.currentPoint;
    }

    public MetaBrush getCurrentBrush() {
        return this.currentBrush;
    }

    public MetaPen getCurrentPen() {
        return this.currentPen;
    }

    public MetaFont getCurrentFont() {
        return this.currentFont;
    }

    public Color getCurrentBackgroundColor() {
        return this.currentBackgroundColor;
    }

    public void setCurrentBackgroundColor(Color currentBackgroundColor2) {
        this.currentBackgroundColor = currentBackgroundColor2;
    }

    public Color getCurrentTextColor() {
        return this.currentTextColor;
    }

    public void setCurrentTextColor(Color currentTextColor2) {
        this.currentTextColor = currentTextColor2;
    }

    public int getBackgroundMode() {
        return this.backgroundMode;
    }

    public void setBackgroundMode(int backgroundMode2) {
        this.backgroundMode = backgroundMode2;
    }

    public int getTextAlign() {
        return this.textAlign;
    }

    public void setTextAlign(int textAlign2) {
        this.textAlign = textAlign2;
    }

    public int getPolyFillMode() {
        return this.polyFillMode;
    }

    public void setPolyFillMode(int polyFillMode2) {
        this.polyFillMode = polyFillMode2;
    }

    public void setLineJoinRectangle(PdfCanvas cb) {
        if (this.lineJoin != 0) {
            this.lineJoin = 0;
            cb.setLineJoinStyle(0);
        }
    }

    public void setLineJoinPolygon(PdfCanvas cb) {
        if (this.lineJoin == 0) {
            this.lineJoin = 1;
            cb.setLineJoinStyle(1);
        }
    }

    public boolean getLineNeutral() {
        return this.lineJoin == 0;
    }
}
