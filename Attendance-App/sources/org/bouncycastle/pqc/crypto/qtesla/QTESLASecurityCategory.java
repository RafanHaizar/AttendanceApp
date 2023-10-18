package org.bouncycastle.pqc.crypto.qtesla;

public class QTESLASecurityCategory {
    public static final int PROVABLY_SECURE_I = 5;
    public static final int PROVABLY_SECURE_III = 6;

    private QTESLASecurityCategory() {
    }

    public static String getName(int i) {
        switch (i) {
            case 5:
                return "qTESLA-p-I";
            case 6:
                return "qTESLA-p-III";
            default:
                throw new IllegalArgumentException("unknown security category: " + i);
        }
    }

    static int getPrivateSize(int i) {
        switch (i) {
            case 5:
                return Polynomial.PRIVATE_KEY_I_P;
            case 6:
                return Polynomial.PRIVATE_KEY_III_P;
            default:
                throw new IllegalArgumentException("unknown security category: " + i);
        }
    }

    static int getPublicSize(int i) {
        switch (i) {
            case 5:
                return Polynomial.PUBLIC_KEY_I_P;
            case 6:
                return 38432;
            default:
                throw new IllegalArgumentException("unknown security category: " + i);
        }
    }

    static int getSignatureSize(int i) {
        switch (i) {
            case 5:
                return 2592;
            case 6:
                return 5664;
            default:
                throw new IllegalArgumentException("unknown security category: " + i);
        }
    }

    static void validate(int i) {
        switch (i) {
            case 5:
            case 6:
                return;
            default:
                throw new IllegalArgumentException("unknown security category: " + i);
        }
    }
}
