package com.itextpdf.barcodes.dmcode;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Placement {
    private static final Map<Integer, short[]> cache = new ConcurrentHashMap();
    private final short[] array;
    private final int ncol;
    private final int nrow;

    private Placement(int nrow2, int ncol2) {
        this.nrow = nrow2;
        this.ncol = ncol2;
        this.array = new short[(nrow2 * ncol2)];
    }

    public static short[] doPlacement(int nrow2, int ncol2) {
        int key = (nrow2 * 1000) + ncol2;
        Map<Integer, short[]> map = cache;
        short[] pc = map.get(Integer.valueOf(key));
        if (pc != null) {
            return pc;
        }
        Placement p = new Placement(nrow2, ncol2);
        p.ecc200();
        map.put(Integer.valueOf(key), p.array);
        return p.array;
    }

    private void module(int row, int col, int chr, int bit) {
        if (row < 0) {
            int i = this.nrow;
            row += i;
            col += 4 - ((i + 4) % 8);
        }
        if (col < 0) {
            int i2 = this.ncol;
            col += i2;
            row += 4 - ((i2 + 4) % 8);
        }
        this.array[(this.ncol * row) + col] = (short) ((chr * 8) + bit);
    }

    private void utah(int row, int col, int chr) {
        module(row - 2, col - 2, chr, 0);
        module(row - 2, col - 1, chr, 1);
        module(row - 1, col - 2, chr, 2);
        module(row - 1, col - 1, chr, 3);
        module(row - 1, col, chr, 4);
        module(row, col - 2, chr, 5);
        module(row, col - 1, chr, 6);
        module(row, col, chr, 7);
    }

    private void corner1(int chr) {
        module(this.nrow - 1, 0, chr, 0);
        module(this.nrow - 1, 1, chr, 1);
        module(this.nrow - 1, 2, chr, 2);
        module(0, this.ncol - 2, chr, 3);
        module(0, this.ncol - 1, chr, 4);
        module(1, this.ncol - 1, chr, 5);
        module(2, this.ncol - 1, chr, 6);
        module(3, this.ncol - 1, chr, 7);
    }

    private void corner2(int chr) {
        module(this.nrow - 3, 0, chr, 0);
        module(this.nrow - 2, 0, chr, 1);
        module(this.nrow - 1, 0, chr, 2);
        module(0, this.ncol - 4, chr, 3);
        module(0, this.ncol - 3, chr, 4);
        module(0, this.ncol - 2, chr, 5);
        module(0, this.ncol - 1, chr, 6);
        module(1, this.ncol - 1, chr, 7);
    }

    private void corner3(int chr) {
        module(this.nrow - 3, 0, chr, 0);
        module(this.nrow - 2, 0, chr, 1);
        module(this.nrow - 1, 0, chr, 2);
        module(0, this.ncol - 2, chr, 3);
        module(0, this.ncol - 1, chr, 4);
        module(1, this.ncol - 1, chr, 5);
        module(2, this.ncol - 1, chr, 6);
        module(3, this.ncol - 1, chr, 7);
    }

    private void corner4(int chr) {
        module(this.nrow - 1, 0, chr, 0);
        module(this.nrow - 1, this.ncol - 1, chr, 1);
        module(0, this.ncol - 3, chr, 2);
        module(0, this.ncol - 2, chr, 3);
        module(0, this.ncol - 1, chr, 4);
        module(1, this.ncol - 3, chr, 5);
        module(1, this.ncol - 2, chr, 6);
        module(1, this.ncol - 1, chr, 7);
    }

    private void ecc200() {
        int i;
        int i2;
        Arrays.fill(this.array, 0);
        int chr = 1;
        int row = 4;
        int col = 0;
        while (true) {
            if (row == this.nrow && col == 0) {
                corner1(chr);
                chr++;
            }
            if (row == this.nrow - 2 && col == 0 && this.ncol % 4 != 0) {
                corner2(chr);
                chr++;
            }
            if (row == this.nrow - 2 && col == 0 && this.ncol % 8 == 4) {
                corner3(chr);
                chr++;
            }
            if (row == this.nrow + 4 && col == 2 && this.ncol % 8 == 0) {
                corner4(chr);
                chr++;
            }
            do {
                if (row < this.nrow && col >= 0 && this.array[(this.ncol * row) + col] == 0) {
                    utah(row, col, chr);
                    chr++;
                }
                row -= 2;
                col += 2;
                if (row < 0 || col >= this.ncol) {
                    int row2 = row + 1;
                    int col2 = col + 3;
                }
                utah(row, col, chr);
                chr++;
                row -= 2;
                col += 2;
                break;
            } while (col >= this.ncol);
            int row22 = row + 1;
            int col22 = col + 3;
            do {
                if (row22 >= 0) {
                    int i3 = this.ncol;
                    if (col22 < i3 && this.array[(i3 * row22) + col22] == 0) {
                        utah(row22, col22, chr);
                        chr++;
                    }
                }
                row22 += 2;
                col22 -= 2;
                i = this.nrow;
                if (row22 >= i) {
                    break;
                }
            } while (col22 >= 0);
            row = row22 + 3;
            col = col22 + 1;
            if (row >= i && col >= (i2 = this.ncol)) {
                break;
            }
        }
        short[] sArr = this.array;
        if (sArr[(i * i2) - 1] == 0) {
            sArr[((i * i2) - i2) - 2] = 1;
            sArr[(i * i2) - 1] = 1;
        }
    }
}
