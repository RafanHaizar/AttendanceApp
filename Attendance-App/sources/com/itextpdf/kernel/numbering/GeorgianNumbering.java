package com.itextpdf.kernel.numbering;

import androidx.vectordrawable.graphics.drawable.PathInterpolatorCompat;
import com.itextpdf.p026io.font.constants.FontWeights;

public class GeorgianNumbering {
    private static final GeorgianDigit[] DIGITS = {new GeorgianDigit(4304, 1), new GeorgianDigit(4305, 2), new GeorgianDigit(4306, 3), new GeorgianDigit(4307, 4), new GeorgianDigit(4308, 5), new GeorgianDigit(4309, 6), new GeorgianDigit(4310, 7), new GeorgianDigit(4337, 8), new GeorgianDigit(4311, 9), new GeorgianDigit(4312, 10), new GeorgianDigit(4313, 20), new GeorgianDigit(4314, 30), new GeorgianDigit(4315, 40), new GeorgianDigit(4316, 50), new GeorgianDigit(4338, 60), new GeorgianDigit(4317, 70), new GeorgianDigit(4318, 80), new GeorgianDigit(4319, 90), new GeorgianDigit(4320, 100), new GeorgianDigit(4321, 200), new GeorgianDigit(4322, 300), new GeorgianDigit(4339, FontWeights.NORMAL), new GeorgianDigit(4324, FontWeights.MEDIUM), new GeorgianDigit(4325, 600), new GeorgianDigit(4326, 700), new GeorgianDigit(4327, FontWeights.EXTRA_BOLD), new GeorgianDigit(4328, 900), new GeorgianDigit(4329, 1000), new GeorgianDigit(4330, 2000), new GeorgianDigit(4331, PathInterpolatorCompat.MAX_NUM_POINTS), new GeorgianDigit(4332, 4000), new GeorgianDigit(4333, 5000), new GeorgianDigit(4334, 6000), new GeorgianDigit(4340, 7000), new GeorgianDigit(4335, 8000), new GeorgianDigit(4336, 9000), new GeorgianDigit(4341, 10000)};

    private GeorgianNumbering() {
    }

    public static String toGeorgian(int number) {
        StringBuilder result = new StringBuilder();
        for (int i = DIGITS.length - 1; i >= 0; i--) {
            GeorgianDigit curDigit = DIGITS[i];
            while (number >= curDigit.value) {
                result.append(curDigit.digit);
                number -= curDigit.value;
            }
        }
        return result.toString();
    }

    private static class GeorgianDigit {
        char digit;
        int value;

        GeorgianDigit(char digit2, int value2) {
            this.digit = digit2;
            this.value = value2;
        }
    }
}
