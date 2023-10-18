package com.itextpdf.kernel.numbering;

import com.itextpdf.barcodes.Barcode128;
import org.bouncycastle.pqc.math.linearalgebra.Matrix;

public class GreekAlphabetNumbering {
    protected static final int ALPHABET_LENGTH = 24;
    protected static final char[] ALPHABET_LOWERCASE = new char[24];
    protected static final char[] ALPHABET_UPPERCASE = new char[24];

    static {
        int i = 0;
        while (i < 24) {
            int i2 = 0;
            ALPHABET_LOWERCASE[i] = (char) (i + 945 + (i > 16 ? 1 : 0));
            char[] cArr = ALPHABET_UPPERCASE;
            int i3 = i + 913;
            if (i > 16) {
                i2 = 1;
            }
            cArr[i] = (char) (i3 + i2);
            i++;
        }
    }

    public static String toGreekAlphabetNumberLowerCase(int number) {
        return AlphabetNumbering.toAlphabetNumber(number, ALPHABET_LOWERCASE);
    }

    public static String toGreekAlphabetNumberUpperCase(int number) {
        return AlphabetNumbering.toAlphabetNumber(number, ALPHABET_UPPERCASE);
    }

    public static String toGreekAlphabetNumber(int number, boolean upperCase) {
        return toGreekAlphabetNumber(number, upperCase, false);
    }

    public static String toGreekAlphabetNumber(int number, boolean upperCase, boolean symbolFont) {
        String result = upperCase ? toGreekAlphabetNumberUpperCase(number) : toGreekAlphabetNumberLowerCase(number);
        if (!symbolFont) {
            return result;
        }
        StringBuilder symbolFontStr = new StringBuilder();
        for (int i = 0; i < result.length(); i++) {
            symbolFontStr.append(getSymbolFontChar(result.charAt(i)));
        }
        return symbolFontStr.toString();
    }

    private static char getSymbolFontChar(char unicodeChar) {
        switch (unicodeChar) {
            case 913:
                return 'A';
            case 914:
                return 'B';
            case 915:
                return 'G';
            case 916:
                return 'D';
            case 917:
                return 'E';
            case 918:
                return Matrix.MATRIX_TYPE_ZERO;
            case 919:
                return 'H';
            case 920:
                return 'Q';
            case 921:
                return 'I';
            case 922:
                return 'K';
            case 923:
                return Matrix.MATRIX_TYPE_RANDOM_LT;
            case 924:
                return 'M';
            case 925:
                return 'N';
            case 926:
                return 'X';
            case 927:
                return 'O';
            case 928:
                return 'P';
            case 929:
                return Matrix.MATRIX_TYPE_RANDOM_REGULAR;
            case 931:
                return 'S';
            case 932:
                return 'T';
            case 933:
                return Matrix.MATRIX_TYPE_RANDOM_UT;
            case 934:
                return 'F';
            case 935:
                return 'C';
            case 936:
                return 'Y';
            case 937:
                return 'W';
            case 945:
                return 'a';
            case 946:
                return 'b';
            case 947:
                return Barcode128.START_A;
            case 948:
                return Barcode128.CODE_AC_TO_B;
            case 949:
                return Barcode128.CODE_BC_TO_A;
            case 950:
                return 'z';
            case 951:
                return Barcode128.START_B;
            case 952:
                return 'q';
            case 953:
                return Barcode128.START_C;
            case 954:
                return 'k';
            case 955:
                return 'l';
            case 956:
                return 'm';
            case 957:
                return 'n';
            case 958:
                return 'x';
            case 959:
                return 'o';
            case 960:
                return 'p';
            case 961:
                return 'r';
            case 962:
                return 'V';
            case 963:
                return 's';
            case 964:
                return 't';
            case 965:
                return 'u';
            case 966:
                return Barcode128.FNC1_INDEX;
            case 967:
                return Barcode128.CODE_AB_TO_C;
            case 968:
                return 'y';
            case 969:
                return 'w';
            default:
                return ' ';
        }
    }
}
