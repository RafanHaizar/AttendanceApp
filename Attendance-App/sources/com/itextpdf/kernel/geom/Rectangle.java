package com.itextpdf.kernel.geom;

import com.itextpdf.kernel.PdfException;
import com.itextpdf.kernel.pdf.PdfArray;
import com.itextpdf.kernel.pdf.PdfPage;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Rectangle implements Cloneable, Serializable {
    static float EPS = 1.0E-4f;
    private static final long serialVersionUID = 8025677415569233446L;
    protected float height;
    protected float width;

    /* renamed from: x */
    protected float f1282x;

    /* renamed from: y */
    protected float f1283y;

    public Rectangle(float x, float y, float width2, float height2) {
        this.f1282x = x;
        this.f1283y = y;
        this.width = width2;
        this.height = height2;
    }

    public Rectangle(float width2, float height2) {
        this(0.0f, 0.0f, width2, height2);
    }

    public Rectangle(Rectangle rect) {
        this(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());
    }

    public static Rectangle getCommonRectangle(Rectangle... rectangles) {
        float ury = -3.4028235E38f;
        float llx = Float.MAX_VALUE;
        float lly = Float.MAX_VALUE;
        float urx = -3.4028235E38f;
        for (Rectangle rectangle : rectangles) {
            if (rectangle != null) {
                Rectangle rec = rectangle.clone();
                if (rec.getY() < lly) {
                    lly = rec.getY();
                }
                if (rec.getX() < llx) {
                    llx = rec.getX();
                }
                if (rec.getY() + rec.getHeight() > ury) {
                    ury = rec.getY() + rec.getHeight();
                }
                if (rec.getX() + rec.getWidth() > urx) {
                    urx = rec.getX() + rec.getWidth();
                }
            }
        }
        return new Rectangle(llx, lly, urx - llx, ury - lly);
    }

    public static Rectangle getRectangleOnRotatedPage(Rectangle rect, PdfPage page) {
        Rectangle resultRect = rect;
        int rotation = page.getRotation();
        if (rotation == 0) {
            return resultRect;
        }
        Rectangle pageSize = page.getPageSize();
        switch ((rotation / 90) % 4) {
            case 1:
                return new Rectangle(pageSize.getWidth() - resultRect.getTop(), resultRect.getLeft(), resultRect.getHeight(), resultRect.getWidth());
            case 2:
                return new Rectangle(pageSize.getWidth() - resultRect.getRight(), pageSize.getHeight() - resultRect.getTop(), resultRect.getWidth(), resultRect.getHeight());
            case 3:
                return new Rectangle(resultRect.getLeft(), pageSize.getHeight() - resultRect.getRight(), resultRect.getHeight(), resultRect.getWidth());
            default:
                return resultRect;
        }
    }

    public static Rectangle calculateBBox(List<Point> points) {
        List<Double> xs = new ArrayList<>();
        List<Double> ys = new ArrayList<>();
        for (Point point : points) {
            xs.add(Double.valueOf(point.getX()));
            ys.add(Double.valueOf(point.getY()));
        }
        double left = ((Double) Collections.min(xs)).doubleValue();
        double bottom = ((Double) Collections.min(ys)).doubleValue();
        return new Rectangle((float) left, (float) bottom, (float) (((Double) Collections.max(xs)).doubleValue() - left), (float) (((Double) Collections.max(ys)).doubleValue() - bottom));
    }

    public Point[] toPointsArray() {
        return new Point[]{new Point((double) this.f1282x, (double) this.f1283y), new Point((double) (this.f1282x + this.width), (double) this.f1283y), new Point((double) (this.f1282x + this.width), (double) (this.f1283y + this.height)), new Point((double) this.f1282x, (double) (this.f1283y + this.height))};
    }

    public Rectangle getIntersection(Rectangle rect) {
        float llx = Math.max(this.f1282x, rect.f1282x);
        float lly = Math.max(this.f1283y, rect.f1283y);
        float urx = Math.min(getRight(), rect.getRight());
        float ury = Math.min(getTop(), rect.getTop());
        float width2 = urx - llx;
        if (Math.abs(width2) < EPS) {
            width2 = 0.0f;
        }
        float height2 = ury - lly;
        if (Math.abs(height2) < EPS) {
            height2 = 0.0f;
        }
        if (Float.compare(width2, 0.0f) < 0 || Float.compare(height2, 0.0f) < 0) {
            return null;
        }
        if (Float.compare(width2, 0.0f) < 0) {
            width2 = 0.0f;
        }
        if (Float.compare(height2, 0.0f) < 0) {
            height2 = 0.0f;
        }
        return new Rectangle(llx, lly, width2, height2);
    }

    public boolean contains(Rectangle rect) {
        float llx = getX();
        float lly = getY();
        float urx = getWidth() + llx;
        float ury = getHeight() + lly;
        float rllx = rect.getX();
        float rlly = rect.getY();
        float rurx = rect.getWidth() + rllx;
        float rury = rect.getHeight() + rlly;
        float f = EPS;
        return llx - f <= rllx && lly - f <= rlly && rurx <= urx + f && rury <= f + ury;
    }

    public boolean overlaps(Rectangle rect) {
        return overlaps(rect, -EPS);
    }

    public boolean overlaps(Rectangle rect, float epsilon) {
        if (getX() + getWidth() >= rect.getX() + epsilon && getX() + epsilon <= rect.getX() + rect.getWidth() && getY() + getHeight() >= rect.getY() + epsilon && getY() + epsilon <= rect.getY() + rect.getHeight()) {
            return true;
        }
        return false;
    }

    public Rectangle setBbox(float llx, float lly, float urx, float ury) {
        if (llx > urx) {
            float temp = llx;
            llx = urx;
            urx = temp;
        }
        if (lly > ury) {
            float temp2 = lly;
            lly = ury;
            ury = temp2;
        }
        this.f1282x = llx;
        this.f1283y = lly;
        this.width = urx - llx;
        this.height = ury - lly;
        return this;
    }

    public float getX() {
        return this.f1282x;
    }

    public Rectangle setX(float x) {
        this.f1282x = x;
        return this;
    }

    public float getY() {
        return this.f1283y;
    }

    public Rectangle setY(float y) {
        this.f1283y = y;
        return this;
    }

    public float getWidth() {
        return this.width;
    }

    public Rectangle setWidth(float width2) {
        this.width = width2;
        return this;
    }

    public float getHeight() {
        return this.height;
    }

    public Rectangle setHeight(float height2) {
        this.height = height2;
        return this;
    }

    public Rectangle increaseHeight(float extra) {
        this.height += extra;
        return this;
    }

    public Rectangle decreaseHeight(float extra) {
        this.height -= extra;
        return this;
    }

    public Rectangle increaseWidth(float extra) {
        this.width += extra;
        return this;
    }

    public Rectangle decreaseWidth(float extra) {
        this.width -= extra;
        return this;
    }

    public float getLeft() {
        return this.f1282x;
    }

    public float getRight() {
        return this.f1282x + this.width;
    }

    public float getTop() {
        return this.f1283y + this.height;
    }

    public float getBottom() {
        return this.f1283y;
    }

    public Rectangle moveDown(float move) {
        this.f1283y -= move;
        return this;
    }

    public Rectangle moveUp(float move) {
        this.f1283y += move;
        return this;
    }

    public Rectangle moveRight(float move) {
        this.f1282x += move;
        return this;
    }

    public Rectangle moveLeft(float move) {
        this.f1282x -= move;
        return this;
    }

    public Rectangle applyMargins(float topIndent, float rightIndent, float bottomIndent, float leftIndent, boolean reverse) {
        int i = -1;
        this.f1282x += ((float) (reverse ? -1 : 1)) * leftIndent;
        this.width -= (leftIndent + rightIndent) * ((float) (reverse ? -1 : 1));
        this.f1283y += ((float) (reverse ? -1 : 1)) * bottomIndent;
        float f = this.height;
        float f2 = topIndent + bottomIndent;
        if (!reverse) {
            i = 1;
        }
        this.height = f - (f2 * ((float) i));
        return this;
    }

    public boolean intersectsLine(float x1, float y1, float x2, float y2) {
        float f = x1;
        float f2 = y1;
        float f3 = x2;
        float f4 = y2;
        double rx1 = (double) getX();
        double ry1 = (double) getY();
        double width2 = (double) getWidth();
        Double.isNaN(rx1);
        Double.isNaN(width2);
        double rx2 = rx1 + width2;
        double height2 = (double) getHeight();
        Double.isNaN(ry1);
        Double.isNaN(height2);
        double ry2 = ry1 + height2;
        if ((rx1 > ((double) f) || ((double) f) > rx2 || ry1 > ((double) f2) || ((double) f2) > ry2) && (rx1 > ((double) f3) || ((double) f3) > rx2 || ry1 > ((double) f4) || ((double) f4) > ry2)) {
            double ry12 = ry1;
            double rx12 = rx1;
            if (!linesIntersect(rx1, ry1, rx2, ry2, (double) f, (double) f2, (double) f3, (double) f4)) {
                return linesIntersect(rx2, ry12, rx12, ry2, (double) f, (double) y1, (double) x2, (double) f4);
            }
            float f5 = y1;
            float f6 = x2;
        } else {
            double d = ry1;
            double d2 = rx1;
        }
    }

    public String toString() {
        return "Rectangle: " + getWidth() + 'x' + getHeight();
    }

    public Rectangle clone() {
        try {
            return (Rectangle) super.clone();
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }

    public boolean equalsWithEpsilon(Rectangle that) {
        return equalsWithEpsilon(that, EPS);
    }

    public boolean equalsWithEpsilon(Rectangle that, float eps) {
        return Math.abs(this.f1282x - that.f1282x) < eps && Math.abs(this.f1283y - that.f1283y) < eps && Math.abs(this.width - that.width) < eps && Math.abs(this.height - that.height) < eps;
    }

    private static boolean linesIntersect(double x1, double y1, double x2, double y2, double x3, double y3, double x4, double y4) {
        double x22 = x2 - x1;
        double y22 = y2 - y1;
        double x32 = x3 - x1;
        double y32 = y3 - y1;
        double x42 = x4 - x1;
        double y42 = y4 - y1;
        double AvB = (x22 * y32) - (x32 * y22);
        double AvC = (x22 * y42) - (x42 * y22);
        if (AvB != 0.0d || AvC != 0.0d) {
            double BvC = (x32 * y42) - (x42 * y32);
            if (AvB * AvC > 0.0d || ((AvB + BvC) - AvC) * BvC > 0.0d) {
                return false;
            }
            return true;
        } else if (x22 != 0.0d) {
            if (x42 * x32 <= 0.0d) {
                return true;
            }
            if (x32 * x22 >= 0.0d) {
                if (x22 > 0.0d) {
                    if (x32 <= x22 || x42 <= x22) {
                        return true;
                    }
                } else if (x32 >= x22 || x42 >= x22) {
                    return true;
                }
            }
            return false;
        } else if (y22 == 0.0d) {
            return false;
        } else {
            if (y42 * y32 <= 0.0d) {
                return true;
            }
            if (y32 * y22 >= 0.0d) {
                if (y22 > 0.0d) {
                    if (y32 <= y22 || y42 <= y22) {
                        return true;
                    }
                } else if (y32 >= y22 || y42 >= y22) {
                    return true;
                }
            }
            return false;
        }
    }

    public static List<Rectangle> createBoundingRectanglesFromQuadPoint(PdfArray quadPoints) throws PdfException {
        List<Rectangle> boundingRectangles = new ArrayList<>();
        if (quadPoints.size() % 8 == 0) {
            for (int i = 0; i < quadPoints.size(); i += 8) {
                boundingRectangles.add(createBoundingRectangleFromQuadPoint(new PdfArray(Arrays.copyOfRange(quadPoints.toFloatArray(), i, i + 8))));
            }
            return boundingRectangles;
        }
        throw new PdfException(PdfException.QuadPointArrayLengthIsNotAMultipleOfEight);
    }

    public static Rectangle createBoundingRectangleFromQuadPoint(PdfArray quadPoints) throws PdfException {
        if (quadPoints.size() % 8 == 0) {
            float llx = Float.MAX_VALUE;
            float lly = Float.MAX_VALUE;
            float urx = -3.4028235E38f;
            float ury = -3.4028235E38f;
            for (int j = 0; j < 8; j += 2) {
                float x = quadPoints.getAsNumber(j).floatValue();
                float y = quadPoints.getAsNumber(j + 1).floatValue();
                if (x < llx) {
                    llx = x;
                }
                if (x > urx) {
                    urx = x;
                }
                if (y < lly) {
                    lly = y;
                }
                if (y > ury) {
                    ury = y;
                }
            }
            return new Rectangle(llx, lly, urx - llx, ury - lly);
        }
        throw new PdfException(PdfException.QuadPointArrayLengthIsNotAMultipleOfEight);
    }
}
