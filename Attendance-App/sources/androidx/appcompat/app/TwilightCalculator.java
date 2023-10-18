package androidx.appcompat.app;

class TwilightCalculator {
    private static final float ALTIDUTE_CORRECTION_CIVIL_TWILIGHT = -0.10471976f;

    /* renamed from: C1 */
    private static final float f974C1 = 0.0334196f;

    /* renamed from: C2 */
    private static final float f975C2 = 3.49066E-4f;

    /* renamed from: C3 */
    private static final float f976C3 = 5.236E-6f;
    public static final int DAY = 0;
    private static final float DEGREES_TO_RADIANS = 0.017453292f;

    /* renamed from: J0 */
    private static final float f977J0 = 9.0E-4f;
    public static final int NIGHT = 1;
    private static final float OBLIQUITY = 0.4092797f;
    private static final long UTC_2000 = 946728000000L;
    private static TwilightCalculator sInstance;
    public int state;
    public long sunrise;
    public long sunset;

    TwilightCalculator() {
    }

    static TwilightCalculator getInstance() {
        if (sInstance == null) {
            sInstance = new TwilightCalculator();
        }
        return sInstance;
    }

    public void calculateTwilight(long time, double latitude, double longitude) {
        float daysSince2000 = ((float) (time - UTC_2000)) / 8.64E7f;
        float meanAnomaly = (0.01720197f * daysSince2000) + 6.24006f;
        double d = (double) meanAnomaly;
        Double.isNaN(d);
        double trueAnomaly = d + (Math.sin((double) meanAnomaly) * 0.03341960161924362d) + (Math.sin((double) (2.0f * meanAnomaly)) * 3.4906598739326E-4d) + (Math.sin((double) (3.0f * meanAnomaly)) * 5.236000106378924E-6d);
        double solarLng = 1.796593063d + trueAnomaly + 3.141592653589793d;
        double arcLongitude = (-longitude) / 360.0d;
        double d2 = (double) (daysSince2000 - f977J0);
        Double.isNaN(d2);
        float n = (float) Math.round(d2 - arcLongitude);
        double d3 = (double) (f977J0 + n);
        Double.isNaN(d3);
        double d4 = trueAnomaly;
        double solarTransitJ2000 = d3 + arcLongitude + (Math.sin((double) meanAnomaly) * 0.0053d) + (Math.sin(2.0d * solarLng) * -0.0069d);
        double solarDec = Math.asin(Math.sin(solarLng) * Math.sin(0.4092797040939331d));
        double latRad = 0.01745329238474369d * latitude;
        double cosHourAngle = (Math.sin(-0.10471975803375244d) - (Math.sin(latRad) * Math.sin(solarDec))) / (Math.cos(latRad) * Math.cos(solarDec));
        float f = n;
        if (cosHourAngle >= 1.0d) {
            this.state = 1;
            this.sunset = -1;
            this.sunrise = -1;
        } else if (cosHourAngle <= -1.0d) {
            this.state = 0;
            this.sunset = -1;
            this.sunrise = -1;
        } else {
            float hourAngle = (float) (Math.acos(cosHourAngle) / 6.283185307179586d);
            float f2 = daysSince2000;
            float f3 = meanAnomaly;
            double d5 = (double) hourAngle;
            Double.isNaN(d5);
            this.sunset = Math.round((d5 + solarTransitJ2000) * 8.64E7d) + UTC_2000;
            double d6 = (double) hourAngle;
            Double.isNaN(d6);
            long round = Math.round((solarTransitJ2000 - d6) * 8.64E7d) + UTC_2000;
            this.sunrise = round;
            if (round >= time || this.sunset <= time) {
                this.state = 1;
            } else {
                this.state = 0;
            }
        }
    }
}
