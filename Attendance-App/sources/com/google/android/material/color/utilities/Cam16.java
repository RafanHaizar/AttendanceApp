package com.google.android.material.color.utilities;

public final class Cam16 {
    static final double[][] CAM16RGB_TO_XYZ = {new double[]{1.8620678d, -1.0112547d, 0.14918678d}, new double[]{0.38752654d, 0.62144744d, -0.00897398d}, new double[]{-0.0158415d, -0.03412294d, 1.0499644d}};
    static final double[][] XYZ_TO_CAM16RGB = {new double[]{0.401288d, 0.650173d, -0.051461d}, new double[]{-0.250268d, 1.204414d, 0.045854d}, new double[]{-0.002079d, 0.048952d, 0.953127d}};
    private final double astar;
    private final double bstar;
    private final double chroma;
    private final double hue;

    /* renamed from: j */
    private final double f1100j;
    private final double jstar;

    /* renamed from: m */
    private final double f1101m;

    /* renamed from: q */
    private final double f1102q;

    /* renamed from: s */
    private final double f1103s;

    /* access modifiers changed from: package-private */
    public double distance(Cam16 other) {
        double dJ = getJstar() - other.getJstar();
        double dA = getAstar() - other.getAstar();
        double dB = getBstar() - other.getBstar();
        return Math.pow(Math.sqrt((dJ * dJ) + (dA * dA) + (dB * dB)), 0.63d) * 1.41d;
    }

    public double getHue() {
        return this.hue;
    }

    public double getChroma() {
        return this.chroma;
    }

    public double getJ() {
        return this.f1100j;
    }

    public double getQ() {
        return this.f1102q;
    }

    public double getM() {
        return this.f1101m;
    }

    public double getS() {
        return this.f1103s;
    }

    public double getJstar() {
        return this.jstar;
    }

    public double getAstar() {
        return this.astar;
    }

    public double getBstar() {
        return this.bstar;
    }

    private Cam16(double hue2, double chroma2, double j, double q, double m, double s, double jstar2, double astar2, double bstar2) {
        this.hue = hue2;
        this.chroma = chroma2;
        this.f1100j = j;
        this.f1102q = q;
        this.f1101m = m;
        this.f1103s = s;
        this.jstar = jstar2;
        this.astar = astar2;
        this.bstar = bstar2;
    }

    public static Cam16 fromInt(int argb) {
        return fromIntInViewingConditions(argb, ViewingConditions.DEFAULT);
    }

    static Cam16 fromIntInViewingConditions(int argb, ViewingConditions viewingConditions) {
        double hue2;
        int i = argb;
        int red = (16711680 & i) >> 16;
        int green = (65280 & i) >> 8;
        int blue = i & 255;
        double redL = ColorUtils.linearized(red);
        double greenL = ColorUtils.linearized(green);
        double blueL = ColorUtils.linearized(blue);
        double x = (0.41233895d * redL) + (0.35762064d * greenL) + (0.18051042d * blueL);
        double y = (0.2126d * redL) + (0.7152d * greenL) + (0.0722d * blueL);
        double z = (0.01932141d * redL) + (0.11916382d * greenL) + (0.95034478d * blueL);
        double[][] matrix = XYZ_TO_CAM16RGB;
        double rT = (matrix[0][0] * x) + (matrix[0][1] * y) + (matrix[0][2] * z);
        double gT = (matrix[1][0] * x) + (matrix[1][1] * y) + (matrix[1][2] * z);
        double bT = (matrix[2][0] * x) + (matrix[2][1] * y) + (matrix[2][2] * z);
        double rD = viewingConditions.getRgbD()[0] * rT;
        double gD = viewingConditions.getRgbD()[1] * gT;
        double bD = viewingConditions.getRgbD()[2] * bT;
        int i2 = red;
        int i3 = green;
        int i4 = blue;
        double rAF = Math.pow((viewingConditions.getFl() * Math.abs(rD)) / 100.0d, 0.42d);
        double d = redL;
        double gAF = Math.pow((viewingConditions.getFl() * Math.abs(gD)) / 100.0d, 0.42d);
        double d2 = greenL;
        double bAF = Math.pow((viewingConditions.getFl() * Math.abs(bD)) / 100.0d, 0.42d);
        double rA = ((Math.signum(rD) * 400.0d) * rAF) / (rAF + 27.13d);
        double gA = ((Math.signum(gD) * 400.0d) * gAF) / (gAF + 27.13d);
        double bA = ((Math.signum(bD) * 400.0d) * bAF) / (bAF + 27.13d);
        double d3 = rAF;
        double a = (((rA * 11.0d) + (-12.0d * gA)) + bA) / 11.0d;
        double d4 = bAF;
        double b = ((rA + gA) - (bA * 2.0d)) / 9.0d;
        double u = (((rA * 20.0d) + (gA * 20.0d)) + (21.0d * bA)) / 20.0d;
        double p2 = (((40.0d * rA) + (gA * 20.0d)) + bA) / 20.0d;
        double atanDegrees = Math.toDegrees(Math.atan2(b, a));
        if (atanDegrees < 0.0d) {
            hue2 = atanDegrees + 360.0d;
        } else {
            hue2 = atanDegrees >= 360.0d ? atanDegrees - 360.0d : atanDegrees;
        }
        double hueRadians = Math.toRadians(hue2);
        double d5 = gAF;
        double d6 = rA;
        double j = Math.pow((p2 * viewingConditions.getNbb()) / viewingConditions.getAw(), viewingConditions.getC() * viewingConditions.getZ()) * 100.0d;
        double q = (4.0d / viewingConditions.getC()) * Math.sqrt(j / 100.0d) * (viewingConditions.getAw() + 4.0d) * viewingConditions.getFlRoot();
        double d7 = a;
        double d8 = b;
        double d9 = blueL;
        double alpha = Math.pow(1.64d - Math.pow(0.29d, viewingConditions.getN()), 0.73d) * Math.pow((Math.hypot(a, b) * (((3846.153846153846d * ((Math.cos(Math.toRadians(hue2 < 20.14d ? hue2 + 360.0d : hue2) + 2.0d) + 3.8d) * 0.25d)) * viewingConditions.getNc()) * viewingConditions.getNcb())) / (u + 0.305d), 0.9d);
        double c = Math.sqrt(j / 100.0d) * alpha;
        double m = viewingConditions.getFlRoot() * c;
        double mstar = Math.log1p(0.0228d * m) * 43.859649122807014d;
        return new Cam16(hue2, c, j, q, m, Math.sqrt((viewingConditions.getC() * alpha) / (viewingConditions.getAw() + 4.0d)) * 50.0d, (1.7000000000000002d * j) / ((0.007d * j) + 1.0d), mstar * Math.cos(hueRadians), mstar * Math.sin(hueRadians));
    }

    static Cam16 fromJch(double j, double c, double h) {
        return fromJchInViewingConditions(j, c, h, ViewingConditions.DEFAULT);
    }

    private static Cam16 fromJchInViewingConditions(double j, double c, double h, ViewingConditions viewingConditions) {
        double d = c;
        double d2 = h;
        double m = c * viewingConditions.getFlRoot();
        double alpha = c / Math.sqrt(j / 100.0d);
        double hueRadians = Math.toRadians(h);
        double mstar = Math.log1p(0.0228d * m) * 43.859649122807014d;
        return new Cam16(d2, d, j, (4.0d / viewingConditions.getC()) * Math.sqrt(j / 100.0d) * (viewingConditions.getAw() + 4.0d) * viewingConditions.getFlRoot(), m, Math.sqrt((viewingConditions.getC() * alpha) / (viewingConditions.getAw() + 4.0d)) * 50.0d, (1.7000000000000002d * j) / ((0.007d * j) + 1.0d), mstar * Math.cos(hueRadians), mstar * Math.sin(hueRadians));
    }

    public static Cam16 fromUcs(double jstar2, double astar2, double bstar2) {
        return fromUcsInViewingConditions(jstar2, astar2, bstar2, ViewingConditions.DEFAULT);
    }

    public static Cam16 fromUcsInViewingConditions(double jstar2, double astar2, double bstar2, ViewingConditions viewingConditions) {
        double h;
        double c = (Math.expm1(Math.hypot(astar2, bstar2) * 0.0228d) / 0.0228d) / viewingConditions.getFlRoot();
        double h2 = Math.atan2(bstar2, astar2) * 57.29577951308232d;
        if (h2 < 0.0d) {
            h = h2 + 360.0d;
        } else {
            h = h2;
        }
        return fromJchInViewingConditions(jstar2 / (1.0d - ((jstar2 - 100.0d) * 0.007d)), c, h, viewingConditions);
    }

    public int toInt() {
        return viewed(ViewingConditions.DEFAULT);
    }

    /* access modifiers changed from: package-private */
    public int viewed(ViewingConditions viewingConditions) {
        double alpha = (getChroma() == 0.0d || getJ() == 0.0d) ? 0.0d : getChroma() / Math.sqrt(getJ() / 100.0d);
        double t = Math.pow(alpha / Math.pow(1.64d - Math.pow(0.29d, viewingConditions.getN()), 0.73d), 1.1111111111111112d);
        double hRad = Math.toRadians(getHue());
        double ac = viewingConditions.getAw() * Math.pow(getJ() / 100.0d, (1.0d / viewingConditions.getC()) / viewingConditions.getZ());
        double p1 = 3846.153846153846d * (Math.cos(2.0d + hRad) + 3.8d) * 0.25d * viewingConditions.getNc() * viewingConditions.getNcb();
        double p2 = ac / viewingConditions.getNbb();
        double hSin = Math.sin(hRad);
        double hCos = Math.cos(hRad);
        double gamma = (((p2 + 0.305d) * 23.0d) * t) / (((23.0d * p1) + ((11.0d * t) * hCos)) + ((108.0d * t) * hSin));
        double a = gamma * hCos;
        double b = gamma * hSin;
        double rA = (((p2 * 460.0d) + (451.0d * a)) + (288.0d * b)) / 1403.0d;
        double gA = (((p2 * 460.0d) - (891.0d * a)) - (261.0d * b)) / 1403.0d;
        double bA = (((460.0d * p2) - (220.0d * a)) - (6300.0d * b)) / 1403.0d;
        double d = alpha;
        double rCBase = Math.max(0.0d, (Math.abs(rA) * 27.13d) / (400.0d - Math.abs(rA)));
        double rC = Math.signum(rA) * (100.0d / viewingConditions.getFl()) * Math.pow(rCBase, 2.380952380952381d);
        double d2 = rCBase;
        double gCBase = Math.max(0.0d, (Math.abs(gA) * 27.13d) / (400.0d - Math.abs(gA)));
        double signum = Math.signum(gA) * (100.0d / viewingConditions.getFl());
        double d3 = p1;
        double d4 = gCBase;
        double d5 = t;
        double bC = Math.signum(bA) * (100.0d / viewingConditions.getFl()) * Math.pow(Math.max(0.0d, (Math.abs(bA) * 27.13d) / (400.0d - Math.abs(bA))), 2.380952380952381d);
        double rF = rC / viewingConditions.getRgbD()[0];
        double gF = (signum * Math.pow(gCBase, 2.380952380952381d)) / viewingConditions.getRgbD()[1];
        double bF = bC / viewingConditions.getRgbD()[2];
        double[][] matrix = CAM16RGB_TO_XYZ;
        return ColorUtils.argbFromXyz((matrix[0][0] * rF) + (matrix[0][1] * gF) + (matrix[0][2] * bF), (matrix[1][0] * rF) + (matrix[1][1] * gF) + (matrix[1][2] * bF), (matrix[2][0] * rF) + (matrix[2][1] * gF) + (matrix[2][2] * bF));
    }
}
