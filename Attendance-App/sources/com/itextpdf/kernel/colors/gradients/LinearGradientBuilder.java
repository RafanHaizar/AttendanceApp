package com.itextpdf.kernel.colors.gradients;

import com.itextpdf.kernel.geom.AffineTransform;
import com.itextpdf.kernel.geom.Point;
import com.itextpdf.kernel.geom.Rectangle;

public class LinearGradientBuilder extends AbstractLinearGradientBuilder {
    private final Point[] coordinates = {new Point(), new Point()};
    private AffineTransform transformation = null;

    public LinearGradientBuilder setGradientVector(double x0, double y0, double x1, double y1) {
        this.coordinates[0].setLocation(x0, y0);
        this.coordinates[1].setLocation(x1, y1);
        return this;
    }

    public LinearGradientBuilder setCurrentSpaceToGradientVectorSpaceTransformation(AffineTransform transformation2) {
        this.transformation = transformation2;
        return this;
    }

    public Point[] getGradientVector(Rectangle targetBoundingBox, AffineTransform contextTransform) {
        return new Point[]{this.coordinates[0].getLocation(), this.coordinates[1].getLocation()};
    }

    public AffineTransform getCurrentSpaceToGradientVectorSpaceTransformation(Rectangle targetBoundingBox, AffineTransform contextTransform) {
        return this.transformation;
    }
}
