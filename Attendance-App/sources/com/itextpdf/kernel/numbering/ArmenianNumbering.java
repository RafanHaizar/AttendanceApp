package com.itextpdf.kernel.numbering;

import androidx.vectordrawable.graphics.drawable.PathInterpolatorCompat;
import com.itextpdf.p026io.font.constants.FontWeights;

public class ArmenianNumbering {
    private static final ArmenianDigit[] DIGITS = {new ArmenianDigit(1329, 1), new ArmenianDigit(1330, 2), new ArmenianDigit(1331, 3), new ArmenianDigit(1332, 4), new ArmenianDigit(1333, 5), new ArmenianDigit(1334, 6), new ArmenianDigit(1335, 7), new ArmenianDigit(1336, 8), new ArmenianDigit(1337, 9), new ArmenianDigit(1338, 10), new ArmenianDigit(1339, 20), new ArmenianDigit(1340, 30), new ArmenianDigit(1341, 40), new ArmenianDigit(1342, 50), new ArmenianDigit(1343, 60), new ArmenianDigit(1344, 70), new ArmenianDigit(1345, 80), new ArmenianDigit(1346, 90), new ArmenianDigit(1347, 100), new ArmenianDigit(1348, 200), new ArmenianDigit(1349, 300), new ArmenianDigit(1350, FontWeights.NORMAL), new ArmenianDigit(1351, FontWeights.MEDIUM), new ArmenianDigit(1352, 600), new ArmenianDigit(1353, 700), new ArmenianDigit(1354, FontWeights.EXTRA_BOLD), new ArmenianDigit(1355, 900), new ArmenianDigit(1356, 1000), new ArmenianDigit(1357, 2000), new ArmenianDigit(1358, PathInterpolatorCompat.MAX_NUM_POINTS), new ArmenianDigit(1359, 4000), new ArmenianDigit(1360, 5000), new ArmenianDigit(1361, 6000), new ArmenianDigit(1362, 7000), new ArmenianDigit(1363, 8000), new ArmenianDigit(1364, 9000)};

    private ArmenianNumbering() {
    }

    public static String toArmenian(int number) {
        StringBuilder result = new StringBuilder();
        for (int i = DIGITS.length - 1; i >= 0; i--) {
            ArmenianDigit curDigit = DIGITS[i];
            while (number >= curDigit.value) {
                result.append(curDigit.digit);
                number -= curDigit.value;
            }
        }
        return result.toString();
    }

    private static class ArmenianDigit {
        char digit;
        int value;

        ArmenianDigit(char digit2, int value2) {
            this.digit = digit2;
            this.value = value2;
        }
    }
}
