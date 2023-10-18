package androidx.core.content.res;

import androidx.core.graphics.ColorUtils;

class CamColor {
    private static final float CHROMA_SEARCH_ENDPOINT = 0.4f;
    private static final float DE_MAX = 1.0f;
    private static final float DL_MAX = 0.2f;
    private static final float LIGHTNESS_SEARCH_ENDPOINT = 0.01f;
    private final float mAstar;
    private final float mBstar;
    private final float mChroma;
    private final float mHue;

    /* renamed from: mJ */
    private final float f1031mJ;
    private final float mJstar;

    /* renamed from: mM */
    private final float f1032mM;

    /* renamed from: mQ */
    private final float f1033mQ;

    /* renamed from: mS */
    private final float f1034mS;

    /* access modifiers changed from: package-private */
    public float getHue() {
        return this.mHue;
    }

    /* access modifiers changed from: package-private */
    public float getChroma() {
        return this.mChroma;
    }

    /* access modifiers changed from: package-private */
    public float getJ() {
        return this.f1031mJ;
    }

    /* access modifiers changed from: package-private */
    public float getQ() {
        return this.f1033mQ;
    }

    /* access modifiers changed from: package-private */
    public float getM() {
        return this.f1032mM;
    }

    /* access modifiers changed from: package-private */
    public float getS() {
        return this.f1034mS;
    }

    /* access modifiers changed from: package-private */
    public float getJStar() {
        return this.mJstar;
    }

    /* access modifiers changed from: package-private */
    public float getAStar() {
        return this.mAstar;
    }

    /* access modifiers changed from: package-private */
    public float getBStar() {
        return this.mBstar;
    }

    CamColor(float hue, float chroma, float j, float q, float m, float s, float jStar, float aStar, float bStar) {
        this.mHue = hue;
        this.mChroma = chroma;
        this.f1031mJ = j;
        this.f1033mQ = q;
        this.f1032mM = m;
        this.f1034mS = s;
        this.mJstar = jStar;
        this.mAstar = aStar;
        this.mBstar = bStar;
    }

    static int toColor(float hue, float chroma, float lStar) {
        return toColor(hue, chroma, lStar, ViewingConditions.DEFAULT);
    }

    static CamColor fromColor(int color) {
        return fromColorInViewingConditions(color, ViewingConditions.DEFAULT);
    }

    static CamColor fromColorInViewingConditions(int color, ViewingConditions viewingConditions) {
        float f;
        float[] xyz = CamUtils.xyzFromInt(color);
        float[][] matrix = CamUtils.XYZ_TO_CAM16RGB;
        float rT = (xyz[0] * matrix[0][0]) + (xyz[1] * matrix[0][1]) + (xyz[2] * matrix[0][2]);
        float gT = (xyz[0] * matrix[1][0]) + (xyz[1] * matrix[1][1]) + (xyz[2] * matrix[1][2]);
        float bT = (xyz[0] * matrix[2][0]) + (xyz[1] * matrix[2][1]) + (xyz[2] * matrix[2][2]);
        float rD = viewingConditions.getRgbD()[0] * rT;
        float gD = viewingConditions.getRgbD()[1] * gT;
        float bD = viewingConditions.getRgbD()[2] * bT;
        double fl = (double) (viewingConditions.getFl() * Math.abs(rD));
        Double.isNaN(fl);
        float rAF = (float) Math.pow(fl / 100.0d, 0.42d);
        double fl2 = (double) (viewingConditions.getFl() * Math.abs(gD));
        Double.isNaN(fl2);
        float gAF = (float) Math.pow(fl2 / 100.0d, 0.42d);
        double fl3 = (double) (viewingConditions.getFl() * Math.abs(bD));
        Double.isNaN(fl3);
        float bAF = (float) Math.pow(fl3 / 100.0d, 0.42d);
        float rA = ((Math.signum(rD) * 400.0f) * rAF) / (rAF + 27.13f);
        float gA = ((Math.signum(gD) * 400.0f) * gAF) / (gAF + 27.13f);
        float bA = ((Math.signum(bD) * 400.0f) * bAF) / (27.13f + bAF);
        float[] fArr = xyz;
        float[][] fArr2 = matrix;
        double d = (double) rA;
        Double.isNaN(d);
        float f2 = rD;
        float f3 = rT;
        double d2 = (double) gA;
        Double.isNaN(d2);
        double d3 = (d * 11.0d) + (d2 * -12.0d);
        double d4 = (double) bA;
        Double.isNaN(d4);
        float a = ((float) (d3 + d4)) / 11.0f;
        double d5 = (double) (rA + gA);
        double d6 = (double) bA;
        Double.isNaN(d6);
        Double.isNaN(d5);
        float b = ((float) (d5 - (d6 * 2.0d))) / 9.0f;
        float u = (((rA * 20.0f) + (gA * 20.0f)) + (21.0f * bA)) / 20.0f;
        float p2 = (((40.0f * rA) + (gA * 20.0f)) + bA) / 20.0f;
        float gD2 = gD;
        float f4 = bD;
        float f5 = gT;
        float f6 = bT;
        float atan2 = (float) Math.atan2((double) b, (double) a);
        float atanDegrees = (atan2 * 180.0f) / 3.1415927f;
        if (atanDegrees < 0.0f) {
            f = atanDegrees + 360.0f;
        } else {
            f = atanDegrees >= 360.0f ? atanDegrees - 360.0f : atanDegrees;
        }
        float hue = f;
        float f7 = gD2;
        float gD3 = hue;
        float hueRadians = (gD3 * 3.1415927f) / 180.0f;
        float ac = viewingConditions.getNbb() * p2;
        float f8 = atan2;
        float f9 = atanDegrees;
        float f10 = ac;
        float f11 = rAF;
        float f12 = gAF;
        float j = ((float) Math.pow((double) (ac / viewingConditions.getAw()), (double) (viewingConditions.getC() * viewingConditions.getZ()))) * 100.0f;
        float q = (4.0f / viewingConditions.getC()) * ((float) Math.sqrt((double) (j / 100.0f))) * (viewingConditions.getAw() + 4.0f) * viewingConditions.getFlRoot();
        double d7 = (double) (((double) gD3) < 20.14d ? gD3 + 360.0f : gD3);
        Double.isNaN(d7);
        float eHue = ((float) (Math.cos(((d7 * 3.141592653589793d) / 180.0d) + 2.0d) + 3.8d)) * 0.25f;
        float p1 = 3846.1538f * eHue * viewingConditions.getNc() * viewingConditions.getNcb();
        float f13 = a;
        float f14 = b;
        float t = (((float) Math.sqrt((double) ((a * a) + (b * b)))) * p1) / (0.305f + u);
        float f15 = eHue;
        float f16 = p1;
        float f17 = bA;
        float t2 = t;
        float f18 = gA;
        float alpha = ((float) Math.pow(1.64d - Math.pow(0.29d, (double) viewingConditions.getN()), 0.73d)) * ((float) Math.pow((double) t, 0.9d));
        double d8 = (double) j;
        Double.isNaN(d8);
        float c = ((float) Math.sqrt(d8 / 100.0d)) * alpha;
        float m = viewingConditions.getFlRoot() * c;
        float jstar = (1.7f * j) / ((0.007f * j) + 1.0f);
        float f19 = u;
        float f20 = t2;
        float f21 = bAF;
        float mstar = ((float) Math.log((double) ((0.0228f * m) + 1.0f))) * 43.85965f;
        float f22 = rA;
        return new CamColor(gD3, c, j, q, m, ((float) Math.sqrt((double) ((viewingConditions.getC() * alpha) / (viewingConditions.getAw() + 4.0f)))) * 50.0f, jstar, ((float) Math.cos((double) hueRadians)) * mstar, ((float) Math.sin((double) hueRadians)) * mstar);
    }

    private static CamColor fromJch(float j, float c, float h) {
        return fromJchInFrame(j, c, h, ViewingConditions.DEFAULT);
    }

    private static CamColor fromJchInFrame(float j, float c, float h, ViewingConditions viewingConditions) {
        float f = j;
        double d = (double) f;
        Double.isNaN(d);
        float q = (4.0f / viewingConditions.getC()) * ((float) Math.sqrt(d / 100.0d)) * (viewingConditions.getAw() + 4.0f) * viewingConditions.getFlRoot();
        float m = c * viewingConditions.getFlRoot();
        double d2 = (double) f;
        Double.isNaN(d2);
        float s = ((float) Math.sqrt((double) ((viewingConditions.getC() * (c / ((float) Math.sqrt(d2 / 100.0d)))) / (viewingConditions.getAw() + 4.0f)))) * 50.0f;
        float hueRadians = (3.1415927f * h) / 180.0f;
        float jstar = (1.7f * f) / ((0.007f * f) + 1.0f);
        double d3 = (double) m;
        Double.isNaN(d3);
        float mstar = ((float) Math.log((d3 * 0.0228d) + 1.0d)) * 43.85965f;
        return new CamColor(h, c, j, q, m, s, jstar, mstar * ((float) Math.cos((double) hueRadians)), mstar * ((float) Math.sin((double) hueRadians)));
    }

    /* access modifiers changed from: package-private */
    public float distance(CamColor other) {
        float dJ = getJStar() - other.getJStar();
        float dA = getAStar() - other.getAStar();
        float dB = getBStar() - other.getBStar();
        return (float) (Math.pow(Math.sqrt((double) ((dJ * dJ) + (dA * dA) + (dB * dB))), 0.63d) * 1.41d);
    }

    /* access modifiers changed from: package-private */
    public int viewedInSrgb() {
        return viewed(ViewingConditions.DEFAULT);
    }

    /* access modifiers changed from: package-private */
    public int viewed(ViewingConditions viewingConditions) {
        float alpha;
        if (((double) getChroma()) == 0.0d || ((double) getJ()) == 0.0d) {
            alpha = 0.0f;
        } else {
            float chroma = getChroma();
            double j = (double) getJ();
            Double.isNaN(j);
            alpha = chroma / ((float) Math.sqrt(j / 100.0d));
        }
        double d = (double) alpha;
        double pow = Math.pow(1.64d - Math.pow(0.29d, (double) viewingConditions.getN()), 0.73d);
        Double.isNaN(d);
        float t = (float) Math.pow(d / pow, 1.1111111111111112d);
        float hRad = (getHue() * 3.1415927f) / 180.0f;
        double d2 = (double) hRad;
        Double.isNaN(d2);
        float eHue = ((float) (Math.cos(d2 + 2.0d) + 3.8d)) * 0.25f;
        float aw = viewingConditions.getAw();
        double j2 = (double) getJ();
        Double.isNaN(j2);
        double c = (double) viewingConditions.getC();
        Double.isNaN(c);
        double d3 = 1.0d / c;
        double z = (double) viewingConditions.getZ();
        Double.isNaN(z);
        float ac = aw * ((float) Math.pow(j2 / 100.0d, d3 / z));
        float p1 = 3846.1538f * eHue * viewingConditions.getNc() * viewingConditions.getNcb();
        float p2 = ac / viewingConditions.getNbb();
        float hSin = (float) Math.sin((double) hRad);
        float hCos = (float) Math.cos((double) hRad);
        float gamma = (((0.305f + p2) * 23.0f) * t) / (((23.0f * p1) + ((11.0f * t) * hCos)) + ((108.0f * t) * hSin));
        float a = gamma * hCos;
        float b = gamma * hSin;
        float rA = (((p2 * 460.0f) + (451.0f * a)) + (288.0f * b)) / 1403.0f;
        float gA = (((p2 * 460.0f) - (891.0f * a)) - (261.0f * b)) / 1403.0f;
        float bA = (((460.0f * p2) - (220.0f * a)) - (6300.0f * b)) / 1403.0f;
        double abs = (double) Math.abs(rA);
        Double.isNaN(abs);
        float f = alpha;
        float f2 = t;
        double abs2 = (double) Math.abs(rA);
        Double.isNaN(abs2);
        float rCBase = (float) Math.max(0.0d, (abs * 27.13d) / (400.0d - abs2));
        float f3 = p1;
        float f4 = p2;
        float rC = Math.signum(rA) * (100.0f / viewingConditions.getFl()) * ((float) Math.pow((double) rCBase, 2.380952380952381d));
        double abs3 = (double) Math.abs(gA);
        Double.isNaN(abs3);
        double abs4 = (double) Math.abs(gA);
        Double.isNaN(abs4);
        float gCBase = (float) Math.max(0.0d, (abs3 * 27.13d) / (400.0d - abs4));
        float f5 = eHue;
        float f6 = ac;
        float gC = Math.signum(gA) * (100.0f / viewingConditions.getFl()) * ((float) Math.pow((double) gCBase, 2.380952380952381d));
        double abs5 = (double) Math.abs(bA);
        Double.isNaN(abs5);
        double abs6 = (double) Math.abs(bA);
        Double.isNaN(abs6);
        float bCBase = (float) Math.max(0.0d, (abs5 * 27.13d) / (400.0d - abs6));
        float f7 = hRad;
        float f8 = bCBase;
        float bC = Math.signum(bA) * (100.0f / viewingConditions.getFl()) * ((float) Math.pow((double) bCBase, 2.380952380952381d));
        float rF = rC / viewingConditions.getRgbD()[0];
        float gF = gC / viewingConditions.getRgbD()[1];
        float bF = bC / viewingConditions.getRgbD()[2];
        float[][] matrix = CamUtils.CAM16RGB_TO_XYZ;
        float x = (matrix[0][0] * rF) + (matrix[0][1] * gF) + (matrix[0][2] * bF);
        float f9 = rCBase;
        float f10 = rC;
        float f11 = gCBase;
        float f12 = gC;
        float f13 = rF;
        float f14 = x;
        float f15 = bC;
        return ColorUtils.XYZToColor((double) x, (double) ((matrix[1][0] * rF) + (matrix[1][1] * gF) + (matrix[1][2] * bF)), (double) ((matrix[2][0] * rF) + (matrix[2][1] * gF) + (matrix[2][2] * bF)));
    }

    static int toColor(float hue, float chroma, float lstar, ViewingConditions viewingConditions) {
        if (((double) chroma) < 1.0d || ((double) Math.round(lstar)) <= 0.0d || ((double) Math.round(lstar)) >= 100.0d) {
            return CamUtils.intFromLStar(lstar);
        }
        float f = 0.0f;
        if (hue >= 0.0f) {
            f = Math.min(360.0f, hue);
        }
        float hue2 = f;
        float high = chroma;
        float mid = chroma;
        float low = 0.0f;
        boolean isFirstLoop = true;
        CamColor answer = null;
        while (Math.abs(low - high) >= CHROMA_SEARCH_ENDPOINT) {
            CamColor possibleAnswer = findCamByJ(hue2, mid, lstar);
            if (!isFirstLoop) {
                if (possibleAnswer == null) {
                    high = mid;
                } else {
                    answer = possibleAnswer;
                    low = mid;
                }
                mid = low + ((high - low) / 2.0f);
            } else if (possibleAnswer != null) {
                return possibleAnswer.viewed(viewingConditions);
            } else {
                isFirstLoop = false;
                mid = low + ((high - low) / 2.0f);
            }
        }
        if (answer == null) {
            return CamUtils.intFromLStar(lstar);
        }
        return answer.viewed(viewingConditions);
    }

    private static CamColor findCamByJ(float hue, float chroma, float lstar) {
        float low = 0.0f;
        float high = 100.0f;
        float bestdL = 1000.0f;
        float bestdE = 1000.0f;
        CamColor bestCam = null;
        while (Math.abs(low - high) > LIGHTNESS_SEARCH_ENDPOINT) {
            float mid = low + ((high - low) / 2.0f);
            int clipped = fromJch(mid, chroma, hue).viewedInSrgb();
            float clippedLstar = CamUtils.lStarFromInt(clipped);
            float dL = Math.abs(lstar - clippedLstar);
            if (dL < 0.2f) {
                CamColor camClipped = fromColor(clipped);
                float dE = camClipped.distance(fromJch(camClipped.getJ(), camClipped.getChroma(), hue));
                if (dE <= 1.0f) {
                    bestdL = dL;
                    bestdE = dE;
                    bestCam = camClipped;
                }
            }
            if (bestdL == 0.0f && bestdE == 0.0f) {
                break;
            } else if (clippedLstar < lstar) {
                low = mid;
            } else {
                high = mid;
            }
        }
        return bestCam;
    }
}
