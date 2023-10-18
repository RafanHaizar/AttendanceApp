package com.itextpdf.kernel.colors.gradients;

import com.itextpdf.kernel.geom.AffineTransform;
import com.itextpdf.kernel.geom.Point;
import com.itextpdf.kernel.geom.Rectangle;

public class StrategyBasedLinearGradientBuilder extends AbstractLinearGradientBuilder {
    private GradientStrategy gradientStrategy = GradientStrategy.TO_BOTTOM;
    private boolean isCentralRotationAngleStrategy = false;
    private double rotateVectorAngle = 0.0d;

    public enum GradientStrategy {
        TO_BOTTOM,
        TO_BOTTOM_LEFT,
        TO_BOTTOM_RIGHT,
        TO_LEFT,
        TO_RIGHT,
        TO_TOP,
        TO_TOP_LEFT,
        TO_TOP_RIGHT
    }

    public StrategyBasedLinearGradientBuilder setGradientDirectionAsCentralRotationAngle(double radians) {
        this.rotateVectorAngle = radians;
        this.isCentralRotationAngleStrategy = true;
        return this;
    }

    public StrategyBasedLinearGradientBuilder setGradientDirectionAsStrategy(GradientStrategy gradientStrategy2) {
        this.gradientStrategy = gradientStrategy2 != null ? gradientStrategy2 : GradientStrategy.TO_BOTTOM;
        this.isCentralRotationAngleStrategy = false;
        return this;
    }

    public double getRotateVectorAngle() {
        return this.rotateVectorAngle;
    }

    public GradientStrategy getGradientStrategy() {
        return this.gradientStrategy;
    }

    public boolean isCentralRotationAngleStrategy() {
        return this.isCentralRotationAngleStrategy;
    }

    /* access modifiers changed from: protected */
    public Point[] getGradientVector(Rectangle targetBoundingBox, AffineTransform contextTransform) {
        if (targetBoundingBox == null) {
            return null;
        }
        if (this.isCentralRotationAngleStrategy) {
            return buildCentralRotationCoordinates(targetBoundingBox, this.rotateVectorAngle);
        }
        return buildCoordinatesWithGradientStrategy(targetBoundingBox, this.gradientStrategy);
    }

    private static Point[] buildCoordinatesWithGradientStrategy(Rectangle targetBoundingBox, GradientStrategy gradientStrategy2) {
        double xCenter = (double) (targetBoundingBox.getX() + (targetBoundingBox.getWidth() / 2.0f));
        double yCenter = (double) (targetBoundingBox.getY() + (targetBoundingBox.getHeight() / 2.0f));
        switch (C14221.f1249xf6216dee[gradientStrategy2.ordinal()]) {
            case 1:
                return createCoordinates(xCenter, (double) targetBoundingBox.getBottom(), xCenter, (double) targetBoundingBox.getTop());
            case 2:
                return createCoordinates((double) targetBoundingBox.getRight(), yCenter, (double) targetBoundingBox.getLeft(), yCenter);
            case 3:
                return createCoordinates((double) targetBoundingBox.getLeft(), yCenter, (double) targetBoundingBox.getRight(), yCenter);
            case 4:
                return buildToCornerCoordinates(targetBoundingBox, new Point((double) targetBoundingBox.getRight(), (double) targetBoundingBox.getTop()));
            case 5:
                return buildToCornerCoordinates(targetBoundingBox, new Point((double) targetBoundingBox.getRight(), (double) targetBoundingBox.getBottom()));
            case 6:
                return buildToCornerCoordinates(targetBoundingBox, new Point((double) targetBoundingBox.getLeft(), (double) targetBoundingBox.getBottom()));
            case 7:
                return buildToCornerCoordinates(targetBoundingBox, new Point((double) targetBoundingBox.getLeft(), (double) targetBoundingBox.getTop()));
            default:
                return createCoordinates(xCenter, (double) targetBoundingBox.getTop(), xCenter, (double) targetBoundingBox.getBottom());
        }
    }

    /* renamed from: com.itextpdf.kernel.colors.gradients.StrategyBasedLinearGradientBuilder$1 */
    static /* synthetic */ class C14221 {

        /* renamed from: $SwitchMap$com$itextpdf$kernel$colors$gradients$StrategyBasedLinearGradientBuilder$GradientStrategy */
        static final /* synthetic */ int[] f1249xf6216dee;

        static {
            int[] iArr = new int[GradientStrategy.values().length];
            f1249xf6216dee = iArr;
            try {
                iArr[GradientStrategy.TO_TOP.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                f1249xf6216dee[GradientStrategy.TO_LEFT.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                f1249xf6216dee[GradientStrategy.TO_RIGHT.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                f1249xf6216dee[GradientStrategy.TO_TOP_LEFT.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                f1249xf6216dee[GradientStrategy.TO_TOP_RIGHT.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                f1249xf6216dee[GradientStrategy.TO_BOTTOM_RIGHT.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
            try {
                f1249xf6216dee[GradientStrategy.TO_BOTTOM_LEFT.ordinal()] = 7;
            } catch (NoSuchFieldError e7) {
            }
            try {
                f1249xf6216dee[GradientStrategy.TO_BOTTOM.ordinal()] = 8;
            } catch (NoSuchFieldError e8) {
            }
        }
    }

    private static Point[] buildCentralRotationCoordinates(Rectangle targetBoundingBox, double angle) {
        return buildCoordinates(targetBoundingBox, AffineTransform.getRotateInstance(angle, (double) (targetBoundingBox.getX() + (targetBoundingBox.getWidth() / 2.0f)), (double) (targetBoundingBox.getY() + (targetBoundingBox.getHeight() / 2.0f))));
    }

    private static Point[] buildToCornerCoordinates(Rectangle targetBoundingBox, Point gradientCenterLineRightCorner) {
        return buildCoordinates(targetBoundingBox, buildToCornerTransform(new Point((double) (targetBoundingBox.getX() + (targetBoundingBox.getWidth() / 2.0f)), (double) (targetBoundingBox.getY() + (targetBoundingBox.getHeight() / 2.0f))), gradientCenterLineRightCorner));
    }

    private static AffineTransform buildToCornerTransform(Point center, Point gradientCenterLineRightCorner) {
        double scale = 1.0d / center.distance(gradientCenterLineRightCorner);
        double sin = (gradientCenterLineRightCorner.getY() - center.getY()) * scale;
        double cos = (gradientCenterLineRightCorner.getX() - center.getX()) * scale;
        double d = -1.0d;
        if (Math.abs(cos) < 1.0E-10d) {
            cos = 0.0d;
            if (sin > 0.0d) {
                d = 1.0d;
            }
            sin = d;
        } else if (Math.abs(sin) < 1.0E-10d) {
            sin = 0.0d;
            if (cos > 0.0d) {
                d = 1.0d;
            }
            cos = d;
        }
        return new AffineTransform(cos, sin, -sin, cos, (center.getX() * (1.0d - cos)) + (center.getY() * sin), (center.getY() * (1.0d - cos)) - (center.getX() * sin));
    }

    private static Point[] buildCoordinates(Rectangle targetBoundingBox, AffineTransform transformation) {
        double xCenter = (double) (targetBoundingBox.getX() + (targetBoundingBox.getWidth() / 2.0f));
        Point[] baseVector = {transformation.transform(new Point(xCenter, (double) targetBoundingBox.getBottom()), (Point) null), transformation.transform(new Point(xCenter, (double) targetBoundingBox.getTop()), (Point) null)};
        return createCoordinatesForNewDomain(evaluateCoveringDomain(baseVector, targetBoundingBox), baseVector);
    }

    private static Point[] createCoordinates(double x1, double y1, double x2, double y2) {
        return new Point[]{new Point(x1, y1), new Point(x2, y2)};
    }
}
