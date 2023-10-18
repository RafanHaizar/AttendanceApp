package com.itextpdf.barcodes.qrcode;

final class MaskUtil {
    private MaskUtil() {
    }

    public static int applyMaskPenaltyRule1(ByteMatrix matrix) {
        return applyMaskPenaltyRule1Internal(matrix, true) + applyMaskPenaltyRule1Internal(matrix, false);
    }

    public static int applyMaskPenaltyRule2(ByteMatrix matrix) {
        int penalty = 0;
        byte[][] array = matrix.getArray();
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        for (int y = 0; y < height - 1; y++) {
            for (int x = 0; x < width - 1; x++) {
                byte value = array[y][x];
                if (value == array[y][x + 1] && value == array[y + 1][x] && value == array[y + 1][x + 1]) {
                    penalty += 3;
                }
            }
        }
        return penalty;
    }

    public static int applyMaskPenaltyRule3(ByteMatrix matrix) {
        int penalty = 0;
        byte[][] array = matrix.getArray();
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (x + 6 < width && array[y][x] == 1 && array[y][x + 1] == 0 && array[y][x + 2] == 1 && array[y][x + 3] == 1 && array[y][x + 4] == 1 && array[y][x + 5] == 0 && array[y][x + 6] == 1 && ((x + 10 < width && array[y][x + 7] == 0 && array[y][x + 8] == 0 && array[y][x + 9] == 0 && array[y][x + 10] == 0) || (x - 4 >= 0 && array[y][x - 1] == 0 && array[y][x - 2] == 0 && array[y][x - 3] == 0 && array[y][x - 4] == 0))) {
                    penalty += 40;
                }
                if (y + 6 < height && array[y][x] == 1 && array[y + 1][x] == 0 && array[y + 2][x] == 1 && array[y + 3][x] == 1 && array[y + 4][x] == 1 && array[y + 5][x] == 0 && array[y + 6][x] == 1 && ((y + 10 < height && array[y + 7][x] == 0 && array[y + 8][x] == 0 && array[y + 9][x] == 0 && array[y + 10][x] == 0) || (y - 4 >= 0 && array[y - 1][x] == 0 && array[y - 2][x] == 0 && array[y - 3][x] == 0 && array[y - 4][x] == 0))) {
                    penalty += 40;
                }
            }
        }
        return penalty;
    }

    public static int applyMaskPenaltyRule4(ByteMatrix matrix) {
        int numDarkCells = 0;
        byte[][] array = matrix.getArray();
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (array[y][x] == 1) {
                    numDarkCells++;
                }
            }
        }
        double d = (double) numDarkCells;
        double height2 = (double) (matrix.getHeight() * matrix.getWidth());
        Double.isNaN(d);
        Double.isNaN(height2);
        return (Math.abs((int) ((100.0d * (d / height2)) - 50.0d)) / 5) * 10;
    }

    public static boolean getDataMaskBit(int maskPattern, int x, int y) {
        int intermediate;
        if (QRCode.isValidMaskPattern(maskPattern)) {
            switch (maskPattern) {
                case 0:
                    intermediate = (y + x) & 1;
                    break;
                case 1:
                    intermediate = y & 1;
                    break;
                case 2:
                    intermediate = x % 3;
                    break;
                case 3:
                    intermediate = (y + x) % 3;
                    break;
                case 4:
                    intermediate = ((y >>> 1) + (x / 3)) & 1;
                    break;
                case 5:
                    int temp = y * x;
                    intermediate = (temp & 1) + (temp % 3);
                    break;
                case 6:
                    int temp2 = y * x;
                    intermediate = ((temp2 & 1) + (temp2 % 3)) & 1;
                    break;
                case 7:
                    intermediate = (((y * x) % 3) + ((y + x) & 1)) & 1;
                    break;
                default:
                    throw new IllegalArgumentException("Invalid mask pattern: " + maskPattern);
            }
            if (intermediate == 0) {
                return true;
            }
            return false;
        }
        throw new IllegalArgumentException("Invalid mask pattern");
    }

    private static int applyMaskPenaltyRule1Internal(ByteMatrix matrix, boolean isHorizontal) {
        int numSameBitCells;
        int penalty = 0;
        int numSameBitCells2 = 0;
        int prevBit = -1;
        int iLimit = isHorizontal ? matrix.getHeight() : matrix.getWidth();
        int jLimit = isHorizontal ? matrix.getWidth() : matrix.getHeight();
        byte[][] array = matrix.getArray();
        for (int i = 0; i < iLimit; i++) {
            for (int j = 0; j < jLimit; j++) {
                int bit = isHorizontal ? array[i][j] : array[j][i];
                if (bit == prevBit) {
                    numSameBitCells++;
                    if (numSameBitCells == 5) {
                        penalty += 3;
                    } else if (numSameBitCells > 5) {
                        penalty++;
                    }
                } else {
                    numSameBitCells = 1;
                    prevBit = bit;
                }
            }
            numSameBitCells2 = 0;
        }
        return penalty;
    }
}
