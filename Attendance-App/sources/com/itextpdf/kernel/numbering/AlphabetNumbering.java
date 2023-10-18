package com.itextpdf.kernel.numbering;

public class AlphabetNumbering {
    public static String toAlphabetNumber(int number, char[] alphabet) {
        if (number >= 1) {
            int cardinality = alphabet.length;
            int number2 = number - 1;
            int bytes = 1;
            int start = 0;
            for (int symbols = cardinality; number2 >= symbols + start; symbols *= cardinality) {
                bytes++;
                start += symbols;
            }
            int c = number2 - start;
            char[] value = new char[bytes];
            while (bytes > 0) {
                bytes--;
                value[bytes] = alphabet[c % cardinality];
                c /= cardinality;
            }
            return new String(value);
        }
        throw new IllegalArgumentException("The parameter must be a positive integer");
    }
}
