package androidx.constraintlayout.core.motion.utils;

public class VelocityMatrix {
    private static String TAG = "VelocityMatrix";
    float mDRotate;
    float mDScaleX;
    float mDScaleY;
    float mDTranslateX;
    float mDTranslateY;
    float mRotate;

    public void clear() {
        this.mDRotate = 0.0f;
        this.mDTranslateY = 0.0f;
        this.mDTranslateX = 0.0f;
        this.mDScaleY = 0.0f;
        this.mDScaleX = 0.0f;
    }

    public void setRotationVelocity(SplineSet rot, float position) {
        if (rot != null) {
            this.mDRotate = rot.getSlope(position);
            this.mRotate = rot.get(position);
        }
    }

    public void setTranslationVelocity(SplineSet trans_x, SplineSet trans_y, float position) {
        if (trans_x != null) {
            this.mDTranslateX = trans_x.getSlope(position);
        }
        if (trans_y != null) {
            this.mDTranslateY = trans_y.getSlope(position);
        }
    }

    public void setScaleVelocity(SplineSet scale_x, SplineSet scale_y, float position) {
        if (scale_x != null) {
            this.mDScaleX = scale_x.getSlope(position);
        }
        if (scale_y != null) {
            this.mDScaleY = scale_y.getSlope(position);
        }
    }

    public void setRotationVelocity(KeyCycleOscillator osc_r, float position) {
        if (osc_r != null) {
            this.mDRotate = osc_r.getSlope(position);
        }
    }

    public void setTranslationVelocity(KeyCycleOscillator osc_x, KeyCycleOscillator osc_y, float position) {
        if (osc_x != null) {
            this.mDTranslateX = osc_x.getSlope(position);
        }
        if (osc_y != null) {
            this.mDTranslateY = osc_y.getSlope(position);
        }
    }

    public void setScaleVelocity(KeyCycleOscillator osc_sx, KeyCycleOscillator osc_sy, float position) {
        if (osc_sx != null) {
            this.mDScaleX = osc_sx.getSlope(position);
        }
        if (osc_sy != null) {
            this.mDScaleY = osc_sy.getSlope(position);
        }
    }

    public void applyTransform(float locationX, float locationY, int width, int height, float[] mAnchorDpDt) {
        int i = width;
        int i2 = height;
        float dx = mAnchorDpDt[0];
        float dy = mAnchorDpDt[1];
        float offx = (locationX - 0.5f) * 2.0f;
        float offy = (locationY - 0.5f) * 2.0f;
        float dx2 = dx + this.mDTranslateX;
        float dy2 = dy + this.mDTranslateY;
        float dx3 = dx2 + (this.mDScaleX * offx);
        float dy3 = dy2 + (this.mDScaleY * offy);
        float r = (float) Math.toRadians((double) this.mRotate);
        float dr = (float) Math.toRadians((double) this.mDRotate);
        double d = (double) (((float) (-i)) * offx);
        double sin = Math.sin((double) r);
        Double.isNaN(d);
        double d2 = d * sin;
        double d3 = (double) (((float) i2) * offy);
        double cos = Math.cos((double) r);
        Double.isNaN(d3);
        float dx4 = dx3 + (((float) (d2 - (d3 * cos))) * dr);
        double d4 = (double) (((float) i) * offx);
        double cos2 = Math.cos((double) r);
        Double.isNaN(d4);
        double d5 = d4 * cos2;
        double d6 = (double) (((float) i2) * offy);
        double sin2 = Math.sin((double) r);
        Double.isNaN(d6);
        mAnchorDpDt[0] = dx4;
        mAnchorDpDt[1] = dy3 + (((float) (d5 - (d6 * sin2))) * dr);
    }
}
