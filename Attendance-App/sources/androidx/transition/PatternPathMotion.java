package androidx.transition;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Matrix;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.util.AttributeSet;
import androidx.core.content.res.TypedArrayUtils;
import androidx.core.graphics.PathParser;
import org.xmlpull.v1.XmlPullParser;

public class PatternPathMotion extends PathMotion {
    private Path mOriginalPatternPath;
    private final Path mPatternPath;
    private final Matrix mTempMatrix;

    public PatternPathMotion() {
        Path path = new Path();
        this.mPatternPath = path;
        this.mTempMatrix = new Matrix();
        path.lineTo(1.0f, 0.0f);
        this.mOriginalPatternPath = path;
    }

    public PatternPathMotion(Context context, AttributeSet attrs) {
        this.mPatternPath = new Path();
        this.mTempMatrix = new Matrix();
        TypedArray a = context.obtainStyledAttributes(attrs, Styleable.PATTERN_PATH_MOTION);
        try {
            String pathData = TypedArrayUtils.getNamedString(a, (XmlPullParser) attrs, "patternPathData", 0);
            if (pathData != null) {
                setPatternPath(PathParser.createPathFromPathData(pathData));
                return;
            }
            throw new RuntimeException("pathData must be supplied for patternPathMotion");
        } finally {
            a.recycle();
        }
    }

    public PatternPathMotion(Path patternPath) {
        this.mPatternPath = new Path();
        this.mTempMatrix = new Matrix();
        setPatternPath(patternPath);
    }

    public Path getPatternPath() {
        return this.mOriginalPatternPath;
    }

    public void setPatternPath(Path patternPath) {
        Path path = patternPath;
        PathMeasure pathMeasure = new PathMeasure(path, false);
        float length = pathMeasure.getLength();
        float[] pos = new float[2];
        pathMeasure.getPosTan(length, pos, (float[]) null);
        float endX = pos[0];
        float endY = pos[1];
        pathMeasure.getPosTan(0.0f, pos, (float[]) null);
        float startX = pos[0];
        float startY = pos[1];
        if (startX == endX && startY == endY) {
            throw new IllegalArgumentException("pattern must not end at the starting point");
        }
        this.mTempMatrix.setTranslate(-startX, -startY);
        float dx = endX - startX;
        float dy = endY - startY;
        float scale = 1.0f / distance(dx, dy);
        this.mTempMatrix.postScale(scale, scale);
        PathMeasure pathMeasure2 = pathMeasure;
        float f = startX;
        float f2 = length;
        float[] fArr = pos;
        this.mTempMatrix.postRotate((float) Math.toDegrees(-Math.atan2((double) dy, (double) dx)));
        path.transform(this.mTempMatrix, this.mPatternPath);
        this.mOriginalPatternPath = path;
    }

    public Path getPath(float startX, float startY, float endX, float endY) {
        float dx = endX - startX;
        float dy = endY - startY;
        float length = distance(dx, dy);
        double angle = Math.atan2((double) dy, (double) dx);
        this.mTempMatrix.setScale(length, length);
        this.mTempMatrix.postRotate((float) Math.toDegrees(angle));
        this.mTempMatrix.postTranslate(startX, startY);
        Path path = new Path();
        this.mPatternPath.transform(this.mTempMatrix, path);
        return path;
    }

    private static float distance(float x, float y) {
        return (float) Math.sqrt((double) ((x * x) + (y * y)));
    }
}
