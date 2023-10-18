package com.itextpdf.barcodes.qrcode;

import java.util.Map;

public final class QRCodeWriter {
    private static final int QUIET_ZONE_SIZE = 4;

    public ByteMatrix encode(String contents, int width, int height) throws WriterException {
        return encode(contents, width, height, (Map<EncodeHintType, Object>) null);
    }

    public ByteMatrix encode(String contents, int width, int height, Map<EncodeHintType, Object> hints) throws WriterException {
        ErrorCorrectionLevel requestedECLevel;
        if (contents == null || contents.length() == 0) {
            throw new IllegalArgumentException("Found empty contents");
        } else if (width < 0 || height < 0) {
            throw new IllegalArgumentException("Requested dimensions are too small: " + width + 'x' + height);
        } else {
            ErrorCorrectionLevel errorCorrectionLevel = ErrorCorrectionLevel.f1182L;
            if (!(hints == null || (requestedECLevel = (ErrorCorrectionLevel) hints.get(EncodeHintType.ERROR_CORRECTION)) == null)) {
                errorCorrectionLevel = requestedECLevel;
            }
            QRCode code = new QRCode();
            Encoder.encode(contents, errorCorrectionLevel, hints, code);
            return renderResult(code, width, height);
        }
    }

    private static ByteMatrix renderResult(QRCode code, int width, int height) {
        ByteMatrix input = code.getMatrix();
        int inputWidth = input.getWidth();
        int inputHeight = input.getHeight();
        int qrWidth = inputWidth + 8;
        int qrHeight = inputHeight + 8;
        int outputWidth = Math.max(width, qrWidth);
        int outputHeight = Math.max(height, qrHeight);
        int multiple = Math.min(outputWidth / qrWidth, outputHeight / qrHeight);
        int leftPadding = (outputWidth - (inputWidth * multiple)) / 2;
        int topPadding = (outputHeight - (inputHeight * multiple)) / 2;
        ByteMatrix output = new ByteMatrix(outputWidth, outputHeight);
        byte[][] outputArray = output.getArray();
        byte[] row = new byte[outputWidth];
        int y = 0;
        while (true) {
            int qrWidth2 = qrWidth;
            if (y >= topPadding) {
                break;
            }
            setRowColor(outputArray[y], (byte) -1);
            y++;
            qrWidth = qrWidth2;
            qrHeight = qrHeight;
        }
        byte[][] inputArray = input.getArray();
        int y2 = 0;
        while (y2 < inputHeight) {
            for (int x = 0; x < leftPadding; x++) {
                row[x] = -1;
            }
            int offset = leftPadding;
            ByteMatrix input2 = input;
            int x2 = 0;
            while (x2 < inputWidth) {
                byte[][] inputArray2 = inputArray;
                byte value = inputArray[y2][x2] == 1 ? (byte) 0 : -1;
                for (int z = 0; z < multiple; z++) {
                    row[offset + z] = value;
                }
                offset += multiple;
                x2++;
                int i = width;
                inputArray = inputArray2;
            }
            byte[][] inputArray3 = inputArray;
            for (int x3 = (inputWidth * multiple) + leftPadding; x3 < outputWidth; x3++) {
                row[x3] = -1;
            }
            int offset2 = (y2 * multiple) + topPadding;
            for (int z2 = 0; z2 < multiple; z2++) {
                System.arraycopy(row, 0, outputArray[offset2 + z2], 0, outputWidth);
            }
            y2++;
            int i2 = width;
            input = input2;
            inputArray = inputArray3;
        }
        byte[][] bArr = inputArray;
        for (int y3 = (inputHeight * multiple) + topPadding; y3 < outputHeight; y3++) {
            setRowColor(outputArray[y3], (byte) -1);
        }
        return output;
    }

    private static void setRowColor(byte[] row, byte value) {
        for (int x = 0; x < row.length; x++) {
            row[x] = value;
        }
    }
}
