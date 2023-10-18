package com.itextpdf.kernel.numbering;

import com.itextpdf.barcodes.Barcode128;
import com.itextpdf.p026io.font.constants.FontWeights;

public class RomanNumbering {
    private static final RomanDigit[] ROMAN_DIGITS = {new RomanDigit('m', 1000, false), new RomanDigit(Barcode128.CODE_AC_TO_B, FontWeights.MEDIUM, false), new RomanDigit(Barcode128.CODE_AB_TO_C, 100, true), new RomanDigit('l', 50, false), new RomanDigit('x', 10, true), new RomanDigit('v', 5, false), new RomanDigit(Barcode128.START_C, 1, true)};

    public static String toRomanLowerCase(int number) {
        return convert(number);
    }

    public static String toRomanUpperCase(int number) {
        return convert(number).toUpperCase();
    }

    public static String toRoman(int number, boolean upperCase) {
        return upperCase ? toRomanUpperCase(number) : toRomanLowerCase(number);
    }

    protected static String convert(int index) {
        RomanDigit[] romanDigitArr;
        StringBuilder buf = new StringBuilder();
        if (index < 0) {
            buf.append('-');
            index = -index;
        }
        if (index >= 4000) {
            buf.append('|');
            buf.append(convert(index / 1000));
            buf.append('|');
            index -= (index / 1000) * 1000;
        }
        int pos = 0;
        while (true) {
            RomanDigit dig = ROMAN_DIGITS[pos];
            while (index >= dig.value) {
                buf.append(dig.digit);
                index -= dig.value;
            }
            if (index <= 0) {
                return buf.toString();
            }
            int j = pos;
            do {
                romanDigitArr = ROMAN_DIGITS;
                j++;
            } while (!romanDigitArr[j].pre);
            if (romanDigitArr[j].value + index >= dig.value) {
                buf.append(romanDigitArr[j].digit).append(dig.digit);
                index -= dig.value - romanDigitArr[j].value;
            }
            pos++;
        }
    }

    private static class RomanDigit {
        public char digit;
        public boolean pre;
        public int value;

        RomanDigit(char digit2, int value2, boolean pre2) {
            this.digit = digit2;
            this.value = value2;
            this.pre = pre2;
        }
    }
}
