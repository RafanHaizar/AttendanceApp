package com.itextpdf.kernel;

import java.lang.reflect.InvocationTargetException;
import org.slf4j.Marker;

public final class Version {
    private static final String AGPL = " (AGPL-version)";
    private static final String iTextProductName = "iText®";
    private static final String producerLine = "iText® 7.1.13 ©2000-2020 iText Group NV";
    private static final String release = "7.1.13";
    private static final Object staticLock = new Object();
    private static volatile Version version = null;
    private boolean expired;
    private final VersionInfo info;

    @Deprecated
    public Version() {
        this.info = new VersionInfo(iTextProductName, release, producerLine, (String) null);
    }

    Version(VersionInfo info2, boolean expired2) {
        this.info = info2;
        this.expired = expired2;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:17:?, code lost:
        r3 = getLicenseeInfoFromLicenseKey(release);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0020, code lost:
        if (r3 == null) goto L_0x00d0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x0025, code lost:
        if (r3[3] == null) goto L_0x0037;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x0031, code lost:
        if (r3[3].trim().length() <= 0) goto L_0x0037;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x0033, code lost:
        r0 = r3[3];
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x0037, code lost:
        r0 = "Trial version ";
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x003d, code lost:
        if (r3[5] != null) goto L_0x0055;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x003f, code lost:
        r0 = r0 + "unauthorised";
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x0055, code lost:
        r0 = r0 + r3[5];
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x006b, code lost:
        if (r3.length <= 6) goto L_0x0082;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x006f, code lost:
        if (r3[6] == null) goto L_0x0082;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x007b, code lost:
        if (r3[6].trim().length() <= 0) goto L_0x0082;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:0x007d, code lost:
        checkLicenseVersion(release, r3[6]);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:36:0x0086, code lost:
        if (r3[4] == null) goto L_0x009c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:38:0x0092, code lost:
        if (r3[4].trim().length() <= 0) goto L_0x009c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:39:0x0094, code lost:
        r2 = initVersion(r3[4], r0, false);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:41:0x009f, code lost:
        if (r3[2] == null) goto L_0x00b4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:43:0x00ab, code lost:
        if (r3[2].trim().length() <= 0) goto L_0x00b4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:44:0x00ad, code lost:
        r2 = initDefaultLicensedVersion(r3[2], r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:46:0x00b6, code lost:
        if (r3[0] == null) goto L_0x00cb;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:48:0x00c2, code lost:
        if (r3[0].trim().length() <= 0) goto L_0x00cb;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:49:0x00c4, code lost:
        r2 = initDefaultLicensedVersion(r3[0], r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:50:0x00cb, code lost:
        r2 = initAGPLVersion((java.lang.Throwable) null, r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:51:0x00d0, code lost:
        r2 = initAGPLVersion((java.lang.Throwable) null, (java.lang.String) null);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:52:0x00d5, code lost:
        r1 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:54:0x00da, code lost:
        if (r1.getCause() == null) goto L_0x00fb;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:60:0x00fa, code lost:
        throw new com.itextpdf.kernel.LicenseVersionException(com.itextpdf.kernel.LicenseVersionException.NO_I_TEXT7_LICENSE_IS_LOADED_BUT_AN_I_TEXT5_LICENSE_IS_LOADED);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:61:0x00fb, code lost:
        r2 = initAGPLVersion(r1.getCause(), (java.lang.String) null);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:63:0x0105, code lost:
        r2 = initAGPLVersion((java.lang.Throwable) null, (java.lang.String) null);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:66:0x010f, code lost:
        r1 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:67:0x0110, code lost:
        throw r1;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static com.itextpdf.kernel.Version getInstance() {
        /*
            java.lang.Object r0 = staticLock
            monitor-enter(r0)
            com.itextpdf.kernel.Version r1 = version     // Catch:{ all -> 0x0111 }
            r2 = 0
            if (r1 == 0) goto L_0x0018
            licenseScheduledCheck()     // Catch:{ Exception -> 0x000c }
            goto L_0x0014
        L_0x000c:
            r1 = move-exception
            com.itextpdf.kernel.Version r2 = initAGPLVersion(r1, r2)     // Catch:{ all -> 0x0111 }
            atomicSetVersion(r2)     // Catch:{ all -> 0x0111 }
        L_0x0014:
            com.itextpdf.kernel.Version r1 = version     // Catch:{ all -> 0x0111 }
            monitor-exit(r0)     // Catch:{ all -> 0x0111 }
            return r1
        L_0x0018:
            monitor-exit(r0)     // Catch:{ all -> 0x0111 }
            r0 = 0
            java.lang.String r1 = "7.1.13"
            java.lang.String[] r3 = getLicenseeInfoFromLicenseKey(r1)     // Catch:{ LicenseVersionException -> 0x010f, ClassNotFoundException -> 0x0104, Exception -> 0x00d5 }
            if (r3 == 0) goto L_0x00d0
            r4 = 3
            r5 = r3[r4]     // Catch:{ LicenseVersionException -> 0x010f, ClassNotFoundException -> 0x0104, Exception -> 0x00d5 }
            if (r5 == 0) goto L_0x0037
            r5 = r3[r4]     // Catch:{ LicenseVersionException -> 0x010f, ClassNotFoundException -> 0x0104, Exception -> 0x00d5 }
            java.lang.String r5 = r5.trim()     // Catch:{ LicenseVersionException -> 0x010f, ClassNotFoundException -> 0x0104, Exception -> 0x00d5 }
            int r5 = r5.length()     // Catch:{ LicenseVersionException -> 0x010f, ClassNotFoundException -> 0x0104, Exception -> 0x00d5 }
            if (r5 <= 0) goto L_0x0037
            r4 = r3[r4]     // Catch:{ LicenseVersionException -> 0x010f, ClassNotFoundException -> 0x0104, Exception -> 0x00d5 }
            r0 = r4
            goto L_0x0069
        L_0x0037:
            java.lang.String r4 = "Trial version "
            r0 = r4
            r4 = 5
            r5 = r3[r4]     // Catch:{ LicenseVersionException -> 0x010f, ClassNotFoundException -> 0x0104, Exception -> 0x00d5 }
            if (r5 != 0) goto L_0x0055
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ LicenseVersionException -> 0x010f, ClassNotFoundException -> 0x0104, Exception -> 0x00d5 }
            r4.<init>()     // Catch:{ LicenseVersionException -> 0x010f, ClassNotFoundException -> 0x0104, Exception -> 0x00d5 }
            java.lang.StringBuilder r4 = r4.append(r0)     // Catch:{ LicenseVersionException -> 0x010f, ClassNotFoundException -> 0x0104, Exception -> 0x00d5 }
            java.lang.String r5 = "unauthorised"
            java.lang.StringBuilder r4 = r4.append(r5)     // Catch:{ LicenseVersionException -> 0x010f, ClassNotFoundException -> 0x0104, Exception -> 0x00d5 }
            java.lang.String r4 = r4.toString()     // Catch:{ LicenseVersionException -> 0x010f, ClassNotFoundException -> 0x0104, Exception -> 0x00d5 }
            r0 = r4
            goto L_0x0069
        L_0x0055:
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ LicenseVersionException -> 0x010f, ClassNotFoundException -> 0x0104, Exception -> 0x00d5 }
            r5.<init>()     // Catch:{ LicenseVersionException -> 0x010f, ClassNotFoundException -> 0x0104, Exception -> 0x00d5 }
            java.lang.StringBuilder r5 = r5.append(r0)     // Catch:{ LicenseVersionException -> 0x010f, ClassNotFoundException -> 0x0104, Exception -> 0x00d5 }
            r4 = r3[r4]     // Catch:{ LicenseVersionException -> 0x010f, ClassNotFoundException -> 0x0104, Exception -> 0x00d5 }
            java.lang.StringBuilder r4 = r5.append(r4)     // Catch:{ LicenseVersionException -> 0x010f, ClassNotFoundException -> 0x0104, Exception -> 0x00d5 }
            java.lang.String r4 = r4.toString()     // Catch:{ LicenseVersionException -> 0x010f, ClassNotFoundException -> 0x0104, Exception -> 0x00d5 }
            r0 = r4
        L_0x0069:
            int r4 = r3.length     // Catch:{ LicenseVersionException -> 0x010f, ClassNotFoundException -> 0x0104, Exception -> 0x00d5 }
            r5 = 6
            if (r4 <= r5) goto L_0x0082
            r4 = r3[r5]     // Catch:{ LicenseVersionException -> 0x010f, ClassNotFoundException -> 0x0104, Exception -> 0x00d5 }
            if (r4 == 0) goto L_0x0082
            r4 = r3[r5]     // Catch:{ LicenseVersionException -> 0x010f, ClassNotFoundException -> 0x0104, Exception -> 0x00d5 }
            java.lang.String r4 = r4.trim()     // Catch:{ LicenseVersionException -> 0x010f, ClassNotFoundException -> 0x0104, Exception -> 0x00d5 }
            int r4 = r4.length()     // Catch:{ LicenseVersionException -> 0x010f, ClassNotFoundException -> 0x0104, Exception -> 0x00d5 }
            if (r4 <= 0) goto L_0x0082
            r4 = r3[r5]     // Catch:{ LicenseVersionException -> 0x010f, ClassNotFoundException -> 0x0104, Exception -> 0x00d5 }
            checkLicenseVersion(r1, r4)     // Catch:{ LicenseVersionException -> 0x010f, ClassNotFoundException -> 0x0104, Exception -> 0x00d5 }
        L_0x0082:
            r4 = 4
            r5 = r3[r4]     // Catch:{ LicenseVersionException -> 0x010f, ClassNotFoundException -> 0x0104, Exception -> 0x00d5 }
            r6 = 0
            if (r5 == 0) goto L_0x009c
            r5 = r3[r4]     // Catch:{ LicenseVersionException -> 0x010f, ClassNotFoundException -> 0x0104, Exception -> 0x00d5 }
            java.lang.String r5 = r5.trim()     // Catch:{ LicenseVersionException -> 0x010f, ClassNotFoundException -> 0x0104, Exception -> 0x00d5 }
            int r5 = r5.length()     // Catch:{ LicenseVersionException -> 0x010f, ClassNotFoundException -> 0x0104, Exception -> 0x00d5 }
            if (r5 <= 0) goto L_0x009c
            r4 = r3[r4]     // Catch:{ LicenseVersionException -> 0x010f, ClassNotFoundException -> 0x0104, Exception -> 0x00d5 }
            com.itextpdf.kernel.Version r2 = initVersion(r4, r0, r6)     // Catch:{ LicenseVersionException -> 0x010f, ClassNotFoundException -> 0x0104, Exception -> 0x00d5 }
            goto L_0x0109
        L_0x009c:
            r4 = 2
            r5 = r3[r4]     // Catch:{ LicenseVersionException -> 0x010f, ClassNotFoundException -> 0x0104, Exception -> 0x00d5 }
            if (r5 == 0) goto L_0x00b4
            r5 = r3[r4]     // Catch:{ LicenseVersionException -> 0x010f, ClassNotFoundException -> 0x0104, Exception -> 0x00d5 }
            java.lang.String r5 = r5.trim()     // Catch:{ LicenseVersionException -> 0x010f, ClassNotFoundException -> 0x0104, Exception -> 0x00d5 }
            int r5 = r5.length()     // Catch:{ LicenseVersionException -> 0x010f, ClassNotFoundException -> 0x0104, Exception -> 0x00d5 }
            if (r5 <= 0) goto L_0x00b4
            r4 = r3[r4]     // Catch:{ LicenseVersionException -> 0x010f, ClassNotFoundException -> 0x0104, Exception -> 0x00d5 }
            com.itextpdf.kernel.Version r2 = initDefaultLicensedVersion(r4, r0)     // Catch:{ LicenseVersionException -> 0x010f, ClassNotFoundException -> 0x0104, Exception -> 0x00d5 }
            goto L_0x0109
        L_0x00b4:
            r4 = r3[r6]     // Catch:{ LicenseVersionException -> 0x010f, ClassNotFoundException -> 0x0104, Exception -> 0x00d5 }
            if (r4 == 0) goto L_0x00cb
            r4 = r3[r6]     // Catch:{ LicenseVersionException -> 0x010f, ClassNotFoundException -> 0x0104, Exception -> 0x00d5 }
            java.lang.String r4 = r4.trim()     // Catch:{ LicenseVersionException -> 0x010f, ClassNotFoundException -> 0x0104, Exception -> 0x00d5 }
            int r4 = r4.length()     // Catch:{ LicenseVersionException -> 0x010f, ClassNotFoundException -> 0x0104, Exception -> 0x00d5 }
            if (r4 <= 0) goto L_0x00cb
            r4 = r3[r6]     // Catch:{ LicenseVersionException -> 0x010f, ClassNotFoundException -> 0x0104, Exception -> 0x00d5 }
            com.itextpdf.kernel.Version r2 = initDefaultLicensedVersion(r4, r0)     // Catch:{ LicenseVersionException -> 0x010f, ClassNotFoundException -> 0x0104, Exception -> 0x00d5 }
            goto L_0x0109
        L_0x00cb:
            com.itextpdf.kernel.Version r2 = initAGPLVersion(r2, r0)     // Catch:{ LicenseVersionException -> 0x010f, ClassNotFoundException -> 0x0104, Exception -> 0x00d5 }
            goto L_0x0109
        L_0x00d0:
            com.itextpdf.kernel.Version r2 = initAGPLVersion(r2, r0)     // Catch:{ LicenseVersionException -> 0x010f, ClassNotFoundException -> 0x0104, Exception -> 0x00d5 }
            goto L_0x0109
        L_0x00d5:
            r1 = move-exception
            java.lang.Throwable r2 = r1.getCause()
            if (r2 == 0) goto L_0x00fb
            java.lang.Throwable r2 = r1.getCause()
            java.lang.String r2 = r2.getMessage()
            java.lang.String r3 = "License file not loaded."
            boolean r2 = r2.equals(r3)
            if (r2 == 0) goto L_0x00fb
            boolean r2 = isiText5licenseLoaded()
            if (r2 != 0) goto L_0x00f3
            goto L_0x00fb
        L_0x00f3:
            com.itextpdf.kernel.LicenseVersionException r2 = new com.itextpdf.kernel.LicenseVersionException
            java.lang.String r3 = "No iText7 License is loaded but an iText5 license is loaded."
            r2.<init>((java.lang.String) r3)
            throw r2
        L_0x00fb:
            java.lang.Throwable r2 = r1.getCause()
            com.itextpdf.kernel.Version r2 = initAGPLVersion(r2, r0)
            goto L_0x010a
        L_0x0104:
            r1 = move-exception
            com.itextpdf.kernel.Version r2 = initAGPLVersion(r2, r0)
        L_0x0109:
        L_0x010a:
            com.itextpdf.kernel.Version r1 = atomicSetVersion(r2)
            return r1
        L_0x010f:
            r1 = move-exception
            throw r1
        L_0x0111:
            r1 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x0111 }
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.kernel.Version.getInstance():com.itextpdf.kernel.Version");
    }

    public static boolean isAGPLVersion() {
        return getInstance().isAGPL();
    }

    public static boolean isExpired() {
        return getInstance().expired;
    }

    public String getProduct() {
        return this.info.getProduct();
    }

    public String getRelease() {
        return this.info.getRelease();
    }

    public String getVersion() {
        return this.info.getVersion();
    }

    public String getKey() {
        return this.info.getKey();
    }

    public VersionInfo getInfo() {
        return this.info;
    }

    static String[] parseVersionString(String version2) {
        String[] split = version2.split("\\.");
        if (split.length != 0) {
            String major = split[0];
            String minor = "0";
            if (split.length > 1) {
                minor = split[1].substring(0);
            }
            if (!isVersionNumeric(major)) {
                throw new LicenseVersionException(LicenseVersionException.MAJOR_VERSION_IS_NOT_NUMERIC);
            } else if (isVersionNumeric(minor)) {
                return new String[]{major, minor};
            } else {
                throw new LicenseVersionException(LicenseVersionException.MINOR_VERSION_IS_NOT_NUMERIC);
            }
        } else {
            throw new LicenseVersionException(LicenseVersionException.VERSION_STRING_IS_EMPTY_AND_CANNOT_BE_PARSED);
        }
    }

    static boolean isVersionNumeric(String version2) {
        try {
            if (Integer.parseInt(version2) < 0 || version2.contains(Marker.ANY_NON_NULL_MARKER)) {
                return false;
            }
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /* access modifiers changed from: package-private */
    public boolean isAGPL() {
        return getVersion().indexOf(AGPL) > 0;
    }

    private static Version initDefaultLicensedVersion(String ownerName, String key) {
        String producer;
        String producer2 = "iText® 7.1.13 ©2000-2020 iText Group NV (" + ownerName;
        if (!key.toLowerCase().startsWith("trial")) {
            producer = producer2 + "; licensed version)";
        } else {
            producer = producer2 + "; " + key + ")";
        }
        return initVersion(producer, key, false);
    }

    private static Version initAGPLVersion(Throwable cause, String key) {
        return initVersion("iText® 7.1.13 ©2000-2020 iText Group NV (AGPL-version)", key, (cause == null || cause.getMessage() == null || !cause.getMessage().contains("expired")) ? false : true);
    }

    private static Version initVersion(String producer, String key, boolean expired2) {
        return new Version(new VersionInfo(iTextProductName, release, producer, key), expired2);
    }

    private static Class<?> getLicenseKeyClass() throws ClassNotFoundException {
        return getClassFromLicenseKey("com.itextpdf.licensekey.LicenseKey");
    }

    private static Class<?> getClassFromLicenseKey(String classFullName) throws ClassNotFoundException {
        return Class.forName(classFullName);
    }

    private static void checkLicenseVersion(String coreVersionString, String licenseVersionString) {
        String[] coreVersions = parseVersionString(coreVersionString);
        String[] licenseVersions = parseVersionString(licenseVersionString);
        int coreMajor = Integer.parseInt(coreVersions[0]);
        int coreMinor = Integer.parseInt(coreVersions[1]);
        int licenseMajor = Integer.parseInt(licenseVersions[0]);
        int licenseMinor = Integer.parseInt(licenseVersions[1]);
        if (licenseMajor < coreMajor) {
            throw new LicenseVersionException(LicenseVersionException.f1235x9b479215).setMessageParams(Integer.valueOf(licenseMajor), Integer.valueOf(coreMajor));
        } else if (licenseMajor > coreMajor) {
            throw new LicenseVersionException(LicenseVersionException.f1234x66dec3ad).setMessageParams(Integer.valueOf(licenseMajor), Integer.valueOf(coreMajor));
        } else if (licenseMinor < coreMinor) {
            throw new LicenseVersionException(LicenseVersionException.f1237x72491995).setMessageParams(Integer.valueOf(licenseMinor), Integer.valueOf(coreMinor));
        }
    }

    private static String[] getLicenseeInfoFromLicenseKey(String validatorKey) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InstantiationException, InvocationTargetException {
        Class<?> klass = getLicenseKeyClass();
        if (klass == null) {
            return null;
        }
        return (String[]) klass.getMethod("getLicenseeInfoForVersion", new Class[]{String.class}).invoke(klass.newInstance(), new Object[]{validatorKey});
    }

    private static boolean isiText5licenseLoaded() {
        try {
            String[] licenseeInfoFromLicenseKey = getLicenseeInfoFromLicenseKey("5");
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private static Version atomicSetVersion(Version newVersion) {
        Version version2;
        synchronized (staticLock) {
            version = newVersion;
            version2 = version;
        }
        return version2;
    }

    private static void licenseScheduledCheck() {
        if (!version.isAGPL()) {
            try {
                getLicenseKeyClass().getMethod("scheduledCheck", new Class[]{getClassFromLicenseKey("com.itextpdf.licensekey.LicenseKeyProduct")}).invoke((Object) null, new Object[]{null});
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage(), e);
            }
        }
    }
}
