package com.google.android.material.color.utilities;

public class Blend {
    private Blend() {
    }

    public static int harmonize(int designColor, int sourceColor) {
        Hct fromHct = Hct.fromInt(designColor);
        Hct toHct = Hct.fromInt(sourceColor);
        return Hct.from(MathUtils.sanitizeDegreesDouble(fromHct.getHue() + (MathUtils.rotationDirection(fromHct.getHue(), toHct.getHue()) * Math.min(0.5d * MathUtils.differenceDegrees(fromHct.getHue(), toHct.getHue()), 15.0d))), fromHct.getChroma(), fromHct.getTone()).toInt();
    }

    public static int hctHue(int from, int to, double amount) {
        return Hct.from(Cam16.fromInt(cam16Ucs(from, to, amount)).getHue(), Cam16.fromInt(from).getChroma(), ColorUtils.lstarFromArgb(from)).toInt();
    }

    public static int cam16Ucs(int from, int to, double amount) {
        Cam16 fromCam = Cam16.fromInt(from);
        Cam16 toCam = Cam16.fromInt(to);
        double fromJ = fromCam.getJstar();
        double fromA = fromCam.getAstar();
        double fromB = fromCam.getBstar();
        double toJ = toCam.getJstar();
        return Cam16.fromUcs(((toJ - fromJ) * amount) + fromJ, fromA + ((toCam.getAstar() - fromA) * amount), fromB + ((toCam.getBstar() - fromB) * amount)).toInt();
    }
}
