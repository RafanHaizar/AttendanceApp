package com.itextpdf.barcodes.qrcode;

import org.bouncycastle.crypto.tls.CipherSuite;

final class MatrixUtil {
    private static final int[][] HORIZONTAL_SEPARATION_PATTERN = {new int[]{0, 0, 0, 0, 0, 0, 0, 0}};
    private static final int[][] POSITION_ADJUSTMENT_PATTERN = {new int[]{1, 1, 1, 1, 1}, new int[]{1, 0, 0, 0, 1}, new int[]{1, 0, 1, 0, 1}, new int[]{1, 0, 0, 0, 1}, new int[]{1, 1, 1, 1, 1}};
    private static final int[][] POSITION_ADJUSTMENT_PATTERN_COORDINATE_TABLE = {new int[]{-1, -1, -1, -1, -1, -1, -1}, new int[]{6, 18, -1, -1, -1, -1, -1}, new int[]{6, 22, -1, -1, -1, -1, -1}, new int[]{6, 26, -1, -1, -1, -1, -1}, new int[]{6, 30, -1, -1, -1, -1, -1}, new int[]{6, 34, -1, -1, -1, -1, -1}, new int[]{6, 22, 38, -1, -1, -1, -1}, new int[]{6, 24, 42, -1, -1, -1, -1}, new int[]{6, 26, 46, -1, -1, -1, -1}, new int[]{6, 28, 50, -1, -1, -1, -1}, new int[]{6, 30, 54, -1, -1, -1, -1}, new int[]{6, 32, 58, -1, -1, -1, -1}, new int[]{6, 34, 62, -1, -1, -1, -1}, new int[]{6, 26, 46, 66, -1, -1, -1}, new int[]{6, 26, 48, 70, -1, -1, -1}, new int[]{6, 26, 50, 74, -1, -1, -1}, new int[]{6, 30, 54, 78, -1, -1, -1}, new int[]{6, 30, 56, 82, -1, -1, -1}, new int[]{6, 30, 58, 86, -1, -1, -1}, new int[]{6, 34, 62, 90, -1, -1, -1}, new int[]{6, 28, 50, 72, 94, -1, -1}, new int[]{6, 26, 50, 74, 98, -1, -1}, new int[]{6, 30, 54, 78, 102, -1, -1}, new int[]{6, 28, 54, 80, 106, -1, -1}, new int[]{6, 32, 58, 84, 110, -1, -1}, new int[]{6, 30, 58, 86, 114, -1, -1}, new int[]{6, 34, 62, 90, 118, -1, -1}, new int[]{6, 26, 50, 74, 98, 122, -1}, new int[]{6, 30, 54, 78, 102, 126, -1}, new int[]{6, 26, 52, 78, 104, 130, -1}, new int[]{6, 30, 56, 82, 108, CipherSuite.TLS_DH_RSA_WITH_CAMELLIA_256_CBC_SHA, -1}, new int[]{6, 34, 60, 86, 112, CipherSuite.TLS_PSK_WITH_RC4_128_SHA, -1}, new int[]{6, 30, 58, 86, 114, CipherSuite.TLS_DHE_PSK_WITH_RC4_128_SHA, -1}, new int[]{6, 34, 62, 90, 118, CipherSuite.TLS_RSA_PSK_WITH_RC4_128_SHA, -1}, new int[]{6, 30, 54, 78, 102, 126, CipherSuite.TLS_RSA_WITH_SEED_CBC_SHA}, new int[]{6, 24, 50, 76, 102, 128, CipherSuite.TLS_DHE_RSA_WITH_SEED_CBC_SHA}, new int[]{6, 28, 54, 80, 106, CipherSuite.TLS_RSA_WITH_CAMELLIA_256_CBC_SHA, CipherSuite.TLS_DHE_RSA_WITH_AES_128_GCM_SHA256}, new int[]{6, 32, 58, 84, 110, 136, CipherSuite.TLS_DHE_DSS_WITH_AES_128_GCM_SHA256}, new int[]{6, 26, 54, 82, 110, CipherSuite.TLS_PSK_WITH_RC4_128_SHA, CipherSuite.TLS_DH_anon_WITH_AES_128_GCM_SHA256}, new int[]{6, 30, 58, 86, 114, CipherSuite.TLS_DHE_PSK_WITH_RC4_128_SHA, CipherSuite.TLS_DHE_PSK_WITH_AES_128_GCM_SHA256}};
    private static final int[][] POSITION_DETECTION_PATTERN = {new int[]{1, 1, 1, 1, 1, 1, 1}, new int[]{1, 0, 0, 0, 0, 0, 1}, new int[]{1, 0, 1, 1, 1, 0, 1}, new int[]{1, 0, 1, 1, 1, 0, 1}, new int[]{1, 0, 1, 1, 1, 0, 1}, new int[]{1, 0, 0, 0, 0, 0, 1}, new int[]{1, 1, 1, 1, 1, 1, 1}};
    private static final int[][] TYPE_INFO_COORDINATES = {new int[]{8, 0}, new int[]{8, 1}, new int[]{8, 2}, new int[]{8, 3}, new int[]{8, 4}, new int[]{8, 5}, new int[]{8, 7}, new int[]{8, 8}, new int[]{7, 8}, new int[]{5, 8}, new int[]{4, 8}, new int[]{3, 8}, new int[]{2, 8}, new int[]{1, 8}, new int[]{0, 8}};
    private static final int TYPE_INFO_MASK_PATTERN = 21522;
    private static final int TYPE_INFO_POLY = 1335;
    private static final int VERSION_INFO_POLY = 7973;
    private static final int[][] VERTICAL_SEPARATION_PATTERN = {new int[]{0}, new int[]{0}, new int[]{0}, new int[]{0}, new int[]{0}, new int[]{0}, new int[]{0}};

    private MatrixUtil() {
    }

    public static void clearMatrix(ByteMatrix matrix) {
        matrix.clear((byte) -1);
    }

    public static void buildMatrix(BitVector dataBits, ErrorCorrectionLevel ecLevel, int version, int maskPattern, ByteMatrix matrix) throws WriterException {
        clearMatrix(matrix);
        embedBasicPatterns(version, matrix);
        embedTypeInfo(ecLevel, maskPattern, matrix);
        maybeEmbedVersionInfo(version, matrix);
        embedDataBits(dataBits, maskPattern, matrix);
    }

    public static void embedBasicPatterns(int version, ByteMatrix matrix) throws WriterException {
        embedPositionDetectionPatternsAndSeparators(matrix);
        embedDarkDotAtLeftBottomCorner(matrix);
        maybeEmbedPositionAdjustmentPatterns(version, matrix);
        embedTimingPatterns(matrix);
    }

    public static void embedTypeInfo(ErrorCorrectionLevel ecLevel, int maskPattern, ByteMatrix matrix) throws WriterException {
        BitVector typeInfoBits = new BitVector();
        makeTypeInfoBits(ecLevel, maskPattern, typeInfoBits);
        for (int i = 0; i < typeInfoBits.size(); i++) {
            int bit = typeInfoBits.mo25260at((typeInfoBits.size() - 1) - i);
            int[] iArr = TYPE_INFO_COORDINATES[i];
            matrix.set(iArr[0], iArr[1], bit);
            if (i < 8) {
                matrix.set((matrix.getWidth() - i) - 1, 8, bit);
            } else {
                matrix.set(8, (matrix.getHeight() - 7) + (i - 8), bit);
            }
        }
    }

    public static void maybeEmbedVersionInfo(int version, ByteMatrix matrix) throws WriterException {
        if (version >= 7) {
            BitVector versionInfoBits = new BitVector();
            makeVersionInfoBits(version, versionInfoBits);
            int bitIndex = 17;
            for (int i = 0; i < 6; i++) {
                for (int j = 0; j < 3; j++) {
                    int bit = versionInfoBits.mo25260at(bitIndex);
                    bitIndex--;
                    matrix.set(i, (matrix.getHeight() - 11) + j, bit);
                    matrix.set((matrix.getHeight() - 11) + j, i, bit);
                }
            }
        }
    }

    public static void embedDataBits(BitVector dataBits, int maskPattern, ByteMatrix matrix) throws WriterException {
        int bit;
        int bitIndex = 0;
        int direction = -1;
        int x = matrix.getWidth() - 1;
        int y = matrix.getHeight() - 1;
        while (x > 0) {
            if (x == 6) {
                x--;
            }
            while (y >= 0 && y < matrix.getHeight()) {
                for (int i = 0; i < 2; i++) {
                    int xx = x - i;
                    if (isEmpty(matrix.get(xx, y))) {
                        if (bitIndex < dataBits.size()) {
                            bit = dataBits.mo25260at(bitIndex);
                            bitIndex++;
                        } else {
                            bit = 0;
                        }
                        if (maskPattern != -1 && MaskUtil.getDataMaskBit(maskPattern, xx, y)) {
                            bit ^= 1;
                        }
                        matrix.set(xx, y, bit);
                    }
                }
                y += direction;
            }
            direction = -direction;
            y += direction;
            x -= 2;
        }
        if (bitIndex != dataBits.size()) {
            throw new WriterException("Not all bits consumed: " + bitIndex + '/' + dataBits.size());
        }
    }

    public static int findMSBSet(int value) {
        int numDigits = 0;
        while (value != 0) {
            value >>>= 1;
            numDigits++;
        }
        return numDigits;
    }

    public static int calculateBCHCode(int value, int poly) {
        int msbSetInPoly = findMSBSet(poly);
        int value2 = value << (msbSetInPoly - 1);
        while (findMSBSet(value2) >= msbSetInPoly) {
            value2 ^= poly << (findMSBSet(value2) - msbSetInPoly);
        }
        return value2;
    }

    public static void makeTypeInfoBits(ErrorCorrectionLevel ecLevel, int maskPattern, BitVector bits) throws WriterException {
        if (QRCode.isValidMaskPattern(maskPattern)) {
            int typeInfo = (ecLevel.getBits() << 3) | maskPattern;
            bits.appendBits(typeInfo, 5);
            bits.appendBits(calculateBCHCode(typeInfo, TYPE_INFO_POLY), 10);
            BitVector maskBits = new BitVector();
            maskBits.appendBits(TYPE_INFO_MASK_PATTERN, 15);
            bits.xor(maskBits);
            if (bits.size() != 15) {
                throw new WriterException("should not happen but we got: " + bits.size());
            }
            return;
        }
        throw new WriterException("Invalid mask pattern");
    }

    public static void makeVersionInfoBits(int version, BitVector bits) throws WriterException {
        bits.appendBits(version, 6);
        bits.appendBits(calculateBCHCode(version, VERSION_INFO_POLY), 12);
        if (bits.size() != 18) {
            throw new WriterException("should not happen but we got: " + bits.size());
        }
    }

    private static boolean isEmpty(byte value) {
        return value == -1;
    }

    private static boolean isValidValue(byte value) {
        return value == -1 || value == 0 || value == 1;
    }

    private static void embedTimingPatterns(ByteMatrix matrix) throws WriterException {
        int i = 8;
        while (i < matrix.getWidth() - 8) {
            int bit = (i + 1) % 2;
            if (isValidValue(matrix.get(i, 6))) {
                if (isEmpty(matrix.get(i, 6))) {
                    matrix.set(i, 6, bit);
                }
                if (isValidValue(matrix.get(6, i))) {
                    if (isEmpty(matrix.get(6, i))) {
                        matrix.set(6, i, bit);
                    }
                    i++;
                } else {
                    throw new WriterException();
                }
            } else {
                throw new WriterException();
            }
        }
    }

    private static void embedDarkDotAtLeftBottomCorner(ByteMatrix matrix) throws WriterException {
        if (matrix.get(8, matrix.getHeight() - 8) != 0) {
            matrix.set(8, matrix.getHeight() - 8, 1);
            return;
        }
        throw new WriterException();
    }

    private static void embedHorizontalSeparationPattern(int xStart, int yStart, ByteMatrix matrix) throws WriterException {
        int[][] iArr = HORIZONTAL_SEPARATION_PATTERN;
        if (iArr[0].length == 8 && iArr.length == 1) {
            int x = 0;
            while (x < 8) {
                if (isEmpty(matrix.get(xStart + x, yStart))) {
                    matrix.set(xStart + x, yStart, HORIZONTAL_SEPARATION_PATTERN[0][x]);
                    x++;
                } else {
                    throw new WriterException();
                }
            }
            return;
        }
        throw new WriterException("Bad horizontal separation pattern");
    }

    private static void embedVerticalSeparationPattern(int xStart, int yStart, ByteMatrix matrix) throws WriterException {
        int[][] iArr = VERTICAL_SEPARATION_PATTERN;
        if (iArr[0].length == 1 && iArr.length == 7) {
            int y = 0;
            while (y < 7) {
                if (isEmpty(matrix.get(xStart, yStart + y))) {
                    matrix.set(xStart, yStart + y, VERTICAL_SEPARATION_PATTERN[y][0]);
                    y++;
                } else {
                    throw new WriterException();
                }
            }
            return;
        }
        throw new WriterException("Bad vertical separation pattern");
    }

    private static void embedPositionAdjustmentPattern(int xStart, int yStart, ByteMatrix matrix) throws WriterException {
        int[][] iArr = POSITION_ADJUSTMENT_PATTERN;
        if (iArr[0].length == 5 && iArr.length == 5) {
            for (int y = 0; y < 5; y++) {
                int x = 0;
                while (x < 5) {
                    if (isEmpty(matrix.get(xStart + x, yStart + y))) {
                        matrix.set(xStart + x, yStart + y, POSITION_ADJUSTMENT_PATTERN[y][x]);
                        x++;
                    } else {
                        throw new WriterException();
                    }
                }
            }
            return;
        }
        throw new WriterException("Bad position adjustment");
    }

    private static void embedPositionDetectionPattern(int xStart, int yStart, ByteMatrix matrix) throws WriterException {
        int[][] iArr = POSITION_DETECTION_PATTERN;
        if (iArr[0].length == 7 && iArr.length == 7) {
            for (int y = 0; y < 7; y++) {
                int x = 0;
                while (x < 7) {
                    if (isEmpty(matrix.get(xStart + x, yStart + y))) {
                        matrix.set(xStart + x, yStart + y, POSITION_DETECTION_PATTERN[y][x]);
                        x++;
                    } else {
                        throw new WriterException();
                    }
                }
            }
            return;
        }
        throw new WriterException("Bad position detection pattern");
    }

    private static void embedPositionDetectionPatternsAndSeparators(ByteMatrix matrix) throws WriterException {
        int pdpWidth = POSITION_DETECTION_PATTERN[0].length;
        embedPositionDetectionPattern(0, 0, matrix);
        embedPositionDetectionPattern(matrix.getWidth() - pdpWidth, 0, matrix);
        embedPositionDetectionPattern(0, matrix.getWidth() - pdpWidth, matrix);
        int hspWidth = HORIZONTAL_SEPARATION_PATTERN[0].length;
        embedHorizontalSeparationPattern(0, hspWidth - 1, matrix);
        embedHorizontalSeparationPattern(matrix.getWidth() - hspWidth, hspWidth - 1, matrix);
        embedHorizontalSeparationPattern(0, matrix.getWidth() - hspWidth, matrix);
        int vspSize = VERTICAL_SEPARATION_PATTERN.length;
        embedVerticalSeparationPattern(vspSize, 0, matrix);
        embedVerticalSeparationPattern((matrix.getHeight() - vspSize) - 1, 0, matrix);
        embedVerticalSeparationPattern(vspSize, matrix.getHeight() - vspSize, matrix);
    }

    private static void maybeEmbedPositionAdjustmentPatterns(int version, ByteMatrix matrix) throws WriterException {
        if (version >= 2) {
            int index = version - 1;
            int[][] iArr = POSITION_ADJUSTMENT_PATTERN_COORDINATE_TABLE;
            int[] coordinates = iArr[index];
            int numCoordinates = iArr[index].length;
            for (int i = 0; i < numCoordinates; i++) {
                for (int j = 0; j < numCoordinates; j++) {
                    int y = coordinates[i];
                    int x = coordinates[j];
                    if (!(x == -1 || y == -1 || !isEmpty(matrix.get(x, y)))) {
                        embedPositionAdjustmentPattern(x - 2, y - 2, matrix);
                    }
                }
            }
        }
    }
}
