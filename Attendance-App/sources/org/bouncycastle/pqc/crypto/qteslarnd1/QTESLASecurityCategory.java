package org.bouncycastle.pqc.crypto.qteslarnd1;

public class QTESLASecurityCategory {
    public static final int HEURISTIC_I = 0;
    public static final int HEURISTIC_III_SIZE = 1;
    public static final int HEURISTIC_III_SPEED = 2;
    public static final int PROVABLY_SECURE_I = 3;
    public static final int PROVABLY_SECURE_III = 4;

    private QTESLASecurityCategory() {
    }

    public static String getName(int i) {
        switch (i) {
            case 0:
                return "qTESLA-I";
            case 1:
                return "qTESLA-III-size";
            case 2:
                return "qTESLA-III-speed";
            case 3:
                return "qTESLA-p-I";
            case 4:
                return "qTESLA-p-III";
            default:
                throw new IllegalArgumentException("unknown security category: " + i);
        }
    }

    static int getPrivateSize(int i) {
        switch (i) {
            case 0:
                return Polynomial.PRIVATE_KEY_I;
            case 1:
                return Polynomial.PRIVATE_KEY_III_SIZE;
            case 2:
                return 2368;
            case 3:
                return Polynomial.PRIVATE_KEY_I_P;
            case 4:
                return Polynomial.PRIVATE_KEY_III_P;
            default:
                throw new IllegalArgumentException("unknown security category: " + i);
        }
    }

    static int getPublicSize(int i) {
        switch (i) {
            case 0:
                return Polynomial.PUBLIC_KEY_I;
            case 1:
                return Polynomial.PUBLIC_KEY_III_SIZE;
            case 2:
                return Polynomial.PUBLIC_KEY_III_SPEED;
            case 3:
                return Polynomial.PUBLIC_KEY_I_P;
            case 4:
                return Polynomial.PUBLIC_KEY_III_P;
            default:
                throw new IllegalArgumentException("unknown security category: " + i);
        }
    }

    static int getSignatureSize(int i) {
        switch (i) {
            case 0:
                return Polynomial.SIGNATURE_I;
            case 1:
                return Polynomial.SIGNATURE_III_SIZE;
            case 2:
            case 3:
                return 2848;
            case 4:
                return Polynomial.SIGNATURE_III_P;
            default:
                throw new IllegalArgumentException("unknown security category: " + i);
        }
    }

    static void validate(int i) {
        switch (i) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
                return;
            default:
                throw new IllegalArgumentException("unknown security category: " + i);
        }
    }
}
