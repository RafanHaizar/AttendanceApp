package com.itextpdf.p026io.font.woff2;

import com.itextpdf.barcodes.Barcode128;
import org.bouncycastle.pqc.math.linearalgebra.Matrix;

/* renamed from: com.itextpdf.io.font.woff2.TableTags */
class TableTags {
    public static final int kCffTableTag = 1128678944;
    public static final int kDsigTableTag = 1146308935;
    public static final int kGlyfTableTag = 1735162214;
    public static final int kHeadTableTag = 1751474532;
    public static final int kHheaTableTag = 1751672161;
    public static final int kHmtxTableTag = 1752003704;
    public static int[] kKnownTags = {tag(Barcode128.CODE_AB_TO_C, 'm', 'a', 'p'), tag(Barcode128.START_B, Barcode128.CODE_BC_TO_A, 'a', Barcode128.CODE_AC_TO_B), tag(Barcode128.START_B, Barcode128.START_B, Barcode128.CODE_BC_TO_A, 'a'), tag(Barcode128.START_B, 'm', 't', 'x'), tag('m', 'a', 'x', 'p'), tag('n', 'a', 'm', Barcode128.CODE_BC_TO_A), tag('O', 'S', '/', '2'), tag('p', 'o', 's', 't'), tag(Barcode128.CODE_AB_TO_C, 'v', 't', ' '), tag(Barcode128.FNC1_INDEX, 'p', Barcode128.START_A, 'm'), tag(Barcode128.START_A, 'l', 'y', Barcode128.FNC1_INDEX), tag('l', 'o', Barcode128.CODE_AB_TO_C, 'a'), tag('p', 'r', Barcode128.CODE_BC_TO_A, 'p'), tag('C', 'F', 'F', ' '), tag('V', 'O', Matrix.MATRIX_TYPE_RANDOM_REGULAR, 'G'), tag('E', 'B', 'D', 'T'), tag('E', 'B', Matrix.MATRIX_TYPE_RANDOM_LT, 'C'), tag(Barcode128.START_A, 'a', 's', 'p'), tag(Barcode128.START_B, Barcode128.CODE_AC_TO_B, 'm', 'x'), tag('k', Barcode128.CODE_BC_TO_A, 'r', 'n'), tag(Matrix.MATRIX_TYPE_RANDOM_LT, 'T', 'S', 'H'), tag('P', 'C', Matrix.MATRIX_TYPE_RANDOM_LT, 'T'), tag('V', 'D', 'M', 'X'), tag('v', Barcode128.START_B, Barcode128.CODE_BC_TO_A, 'a'), tag('v', 'm', 't', 'x'), tag('B', 'A', 'S', 'E'), tag('G', 'D', 'E', 'F'), tag('G', 'P', 'O', 'S'), tag('G', 'S', Matrix.MATRIX_TYPE_RANDOM_UT, 'B'), tag('E', 'B', 'S', 'C'), tag('J', 'S', 'T', 'F'), tag('M', 'A', 'T', 'H'), tag('C', 'B', 'D', 'T'), tag('C', 'B', Matrix.MATRIX_TYPE_RANDOM_LT, 'C'), tag('C', 'O', Matrix.MATRIX_TYPE_RANDOM_LT, Matrix.MATRIX_TYPE_RANDOM_REGULAR), tag('C', 'P', 'A', Matrix.MATRIX_TYPE_RANDOM_LT), tag('S', 'V', 'G', ' '), tag('s', 'b', Barcode128.START_C, 'x'), tag('a', Barcode128.CODE_AB_TO_C, 'n', 't'), tag('a', 'v', 'a', 'r'), tag('b', Barcode128.CODE_AC_TO_B, 'a', 't'), tag('b', 'l', 'o', Barcode128.CODE_AB_TO_C), tag('b', 's', 'l', 'n'), tag(Barcode128.CODE_AB_TO_C, 'v', 'a', 'r'), tag(Barcode128.FNC1_INDEX, Barcode128.CODE_AC_TO_B, 's', Barcode128.CODE_AB_TO_C), tag(Barcode128.FNC1_INDEX, Barcode128.CODE_BC_TO_A, 'a', 't'), tag(Barcode128.FNC1_INDEX, 'm', 't', 'x'), tag(Barcode128.FNC1_INDEX, 'v', 'a', 'r'), tag(Barcode128.START_A, 'v', 'a', 'r'), tag(Barcode128.START_B, 's', 't', 'y'), tag('j', 'u', 's', 't'), tag('l', Barcode128.CODE_AB_TO_C, 'a', 'r'), tag('m', 'o', 'r', 't'), tag('m', 'o', 'r', 'x'), tag('o', 'p', 'b', Barcode128.CODE_AC_TO_B), tag('p', 'r', 'o', 'p'), tag('t', 'r', 'a', 'k'), tag(Matrix.MATRIX_TYPE_ZERO, 'a', 'p', Barcode128.FNC1_INDEX), tag('S', Barcode128.START_C, 'l', Barcode128.FNC1_INDEX), tag('G', 'l', 'a', 't'), tag('G', 'l', 'o', Barcode128.CODE_AB_TO_C), tag('F', Barcode128.CODE_BC_TO_A, 'a', 't'), tag('S', Barcode128.START_C, 'l', 'l')};
    public static final int kLocaTableTag = 1819239265;
    public static final int kMaxpTableTag = 1835104368;

    TableTags() {
    }

    private static int tag(char a, char b, char c, char d) {
        return (a << 24) | (b << 16) | (c << 8) | d;
    }
}
