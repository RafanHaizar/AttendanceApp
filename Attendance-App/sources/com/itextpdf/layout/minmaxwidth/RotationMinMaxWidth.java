package com.itextpdf.layout.minmaxwidth;

import com.itextpdf.kernel.geom.Rectangle;

public class RotationMinMaxWidth extends MinMaxWidth {
    private double maxWidthHeight;
    private double maxWidthOrigin;
    private double minWidthHeight;
    private double minWidthOrigin;

    public RotationMinMaxWidth(double minWidth, double maxWidth, double minWidthOrigin2, double maxWidthOrigin2, double minWidthHeight2, double maxWidthHeight2) {
        super((float) minWidth, (float) maxWidth, 0.0f);
        this.maxWidthOrigin = maxWidthOrigin2;
        this.minWidthOrigin = minWidthOrigin2;
        this.minWidthHeight = minWidthHeight2;
        this.maxWidthHeight = maxWidthHeight2;
    }

    public double getMinWidthOrigin() {
        return this.minWidthOrigin;
    }

    public double getMaxWidthOrigin() {
        return this.maxWidthOrigin;
    }

    public double getMinWidthHeight() {
        return this.minWidthHeight;
    }

    public double getMaxWidthHeight() {
        return this.maxWidthHeight;
    }

    public static RotationMinMaxWidth calculate(double angle, double area, MinMaxWidth elementMinMaxWidth) {
        return calculate(new WidthFunction(angle, area), (double) elementMinMaxWidth.getMinWidth(), (double) elementMinMaxWidth.getMaxWidth());
    }

    public static RotationMinMaxWidth calculate(double angle, double area, MinMaxWidth elementMinMaxWidth, double availableWidth) {
        WidthFunction function = new WidthFunction(angle, area);
        WidthFunction.Interval validArguments = function.getValidOriginalWidths(availableWidth);
        if (validArguments == null) {
            return null;
        }
        double xMin = Math.max((double) elementMinMaxWidth.getMinWidth(), validArguments.getMin());
        double xMax = Math.min((double) elementMinMaxWidth.getMaxWidth(), validArguments.getMax());
        if (xMax >= xMin) {
            return calculate(function, xMin, xMax);
        }
        double rotatedWidth = function.getRotatedWidth(xMin);
        double rotatedHeight = function.getRotatedHeight(xMin);
        return new RotationMinMaxWidth(rotatedWidth, rotatedWidth, xMin, xMin, rotatedHeight, rotatedHeight);
    }

    public static double calculateRotatedWidth(Rectangle area, double angle) {
        double width = (double) area.getWidth();
        double cos = cos(angle);
        Double.isNaN(width);
        double d = width * cos;
        double height = (double) area.getHeight();
        double sin = sin(angle);
        Double.isNaN(height);
        return d + (height * sin);
    }

    private static RotationMinMaxWidth calculate(WidthFunction func, double xMin, double xMax) {
        double maxWidthOrigin2;
        double minWidthOrigin2;
        WidthFunction widthFunction = func;
        double d = xMax;
        double x0 = func.getWidthDerivativeZeroPoint();
        if (x0 < xMin) {
            minWidthOrigin2 = xMin;
            maxWidthOrigin2 = xMax;
        } else if (x0 > d) {
            minWidthOrigin2 = xMax;
            maxWidthOrigin2 = xMin;
        } else {
            minWidthOrigin2 = x0;
            maxWidthOrigin2 = widthFunction.getRotatedWidth(d) > func.getRotatedWidth(xMin) ? d : xMin;
        }
        return new RotationMinMaxWidth(widthFunction.getRotatedWidth(minWidthOrigin2), widthFunction.getRotatedWidth(maxWidthOrigin2), minWidthOrigin2, maxWidthOrigin2, widthFunction.getRotatedHeight(minWidthOrigin2), widthFunction.getRotatedHeight(maxWidthOrigin2));
    }

    /* access modifiers changed from: private */
    public static double sin(double angle) {
        return correctSinCos(Math.abs(Math.sin(angle)));
    }

    /* access modifiers changed from: private */
    public static double cos(double angle) {
        return correctSinCos(Math.abs(Math.cos(angle)));
    }

    private static double correctSinCos(double value) {
        if (MinMaxWidthUtils.isEqual(value, 0.0d)) {
            return 0.0d;
        }
        if (MinMaxWidthUtils.isEqual(value, 1.0d)) {
            return 1.0d;
        }
        return value;
    }

    private static class WidthFunction {
        private double area;
        private double cos;
        private double sin;

        public WidthFunction(double angle, double area2) {
            this.sin = RotationMinMaxWidth.sin(angle);
            this.cos = RotationMinMaxWidth.cos(angle);
            this.area = area2;
        }

        public double getRotatedWidth(double x) {
            return (this.cos * x) + ((this.area * this.sin) / x);
        }

        public double getRotatedHeight(double x) {
            return (this.sin * x) + ((this.area * this.cos) / x);
        }

        public Interval getValidOriginalWidths(double availableWidth) {
            double maxWidth;
            double minWidth;
            double d = this.cos;
            if (d == 0.0d) {
                minWidth = (this.area * this.sin) / availableWidth;
                maxWidth = (double) MinMaxWidthUtils.getInfWidth();
            } else {
                double d2 = this.sin;
                if (d2 == 0.0d) {
                    double d3 = availableWidth / d;
                    minWidth = 0.0d;
                    maxWidth = d3;
                } else {
                    double D = (availableWidth * availableWidth) - (((this.area * 4.0d) * d2) * d);
                    if (D < 0.0d) {
                        return null;
                    }
                    minWidth = (availableWidth - Math.sqrt(D)) / (this.cos * 2.0d);
                    maxWidth = (availableWidth + Math.sqrt(D)) / (this.cos * 2.0d);
                }
            }
            return new Interval(minWidth, maxWidth);
        }

        public double getWidthDerivativeZeroPoint() {
            return Math.sqrt((this.area * this.sin) / this.cos);
        }

        public static class Interval {
            private double max;
            private double min;

            public Interval(double min2, double max2) {
                this.min = min2;
                this.max = max2;
            }

            public double getMin() {
                return this.min;
            }

            public double getMax() {
                return this.max;
            }
        }
    }
}
