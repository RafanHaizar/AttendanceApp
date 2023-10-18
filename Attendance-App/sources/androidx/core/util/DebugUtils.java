package androidx.core.util;

public class DebugUtils {
    /* JADX WARNING: Code restructure failed: missing block: B:6:0x0019, code lost:
        r0 = r3.getClass().getName();
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void buildShortClassTag(java.lang.Object r3, java.lang.StringBuilder r4) {
        /*
            if (r3 != 0) goto L_0x0009
            java.lang.String r0 = "null"
            r4.append(r0)
            goto L_0x0042
        L_0x0009:
            java.lang.Class r0 = r3.getClass()
            java.lang.String r0 = r0.getSimpleName()
            if (r0 == 0) goto L_0x0019
            int r1 = r0.length()
            if (r1 > 0) goto L_0x002f
        L_0x0019:
            java.lang.Class r1 = r3.getClass()
            java.lang.String r0 = r1.getName()
            r1 = 46
            int r1 = r0.lastIndexOf(r1)
            if (r1 <= 0) goto L_0x002f
            int r2 = r1 + 1
            java.lang.String r0 = r0.substring(r2)
        L_0x002f:
            r4.append(r0)
            r1 = 123(0x7b, float:1.72E-43)
            r4.append(r1)
            int r1 = java.lang.System.identityHashCode(r3)
            java.lang.String r1 = java.lang.Integer.toHexString(r1)
            r4.append(r1)
        L_0x0042:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.core.util.DebugUtils.buildShortClassTag(java.lang.Object, java.lang.StringBuilder):void");
    }

    private DebugUtils() {
    }
}
