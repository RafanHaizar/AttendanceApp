package androidx.core.view.animation;

import android.graphics.Path;
import android.graphics.PathMeasure;
import android.view.animation.Interpolator;

class PathInterpolatorApi14 implements Interpolator {
    private static final float PRECISION = 0.002f;

    /* renamed from: mX */
    private final float[] f1043mX;

    /* renamed from: mY */
    private final float[] f1044mY;

    PathInterpolatorApi14(Path path) {
        PathMeasure pathMeasure = new PathMeasure(path, false);
        float pathLength = pathMeasure.getLength();
        int numPoints = ((int) (pathLength / 0.002f)) + 1;
        this.f1043mX = new float[numPoints];
        this.f1044mY = new float[numPoints];
        float[] position = new float[2];
        for (int i = 0; i < numPoints; i++) {
            pathMeasure.getPosTan((((float) i) * pathLength) / ((float) (numPoints - 1)), position, (float[]) null);
            this.f1043mX[i] = position[0];
            this.f1044mY[i] = position[1];
        }
    }

    PathInterpolatorApi14(float controlX, float controlY) {
        this(createQuad(controlX, controlY));
    }

    PathInterpolatorApi14(float controlX1, float controlY1, float controlX2, float controlY2) {
        this(createCubic(controlX1, controlY1, controlX2, controlY2));
    }

    public float getInterpolation(float t) {
        if (t <= 0.0f) {
            return 0.0f;
        }
        if (t >= 1.0f) {
            return 1.0f;
        }
        int startIndex = 0;
        int endIndex = this.f1043mX.length - 1;
        while (endIndex - startIndex > 1) {
            int midIndex = (startIndex + endIndex) / 2;
            if (t < this.f1043mX[midIndex]) {
                endIndex = midIndex;
            } else {
                startIndex = midIndex;
            }
        }
        float[] fArr = this.f1043mX;
        float f = fArr[endIndex];
        float f2 = fArr[startIndex];
        float xRange = f - f2;
        if (xRange == 0.0f) {
            return this.f1044mY[startIndex];
        }
        float[] fArr2 = this.f1044mY;
        float startY = fArr2[startIndex];
        return ((fArr2[endIndex] - startY) * ((t - f2) / xRange)) + startY;
    }

    private static Path createQuad(float controlX, float controlY) {
        Path path = new Path();
        path.moveTo(0.0f, 0.0f);
        path.quadTo(controlX, controlY, 1.0f, 1.0f);
        return path;
    }

    private static Path createCubic(float controlX1, float controlY1, float controlX2, float controlY2) {
        Path path = new Path();
        path.moveTo(0.0f, 0.0f);
        path.cubicTo(controlX1, controlY1, controlX2, controlY2, 1.0f, 1.0f);
        return path;
    }
}
