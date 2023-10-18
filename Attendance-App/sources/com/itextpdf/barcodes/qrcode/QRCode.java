package com.itextpdf.barcodes.qrcode;

final class QRCode {
    public static final int NUM_MASK_PATTERNS = 8;
    private ErrorCorrectionLevel ecLevel = null;
    private int maskPattern = -1;
    private ByteMatrix matrix = null;
    private int matrixWidth = -1;
    private Mode mode = null;
    private int numDataBytes = -1;
    private int numECBytes = -1;
    private int numRSBlocks = -1;
    private int numTotalBytes = -1;
    private int version = -1;

    public Mode getMode() {
        return this.mode;
    }

    public ErrorCorrectionLevel getECLevel() {
        return this.ecLevel;
    }

    public int getVersion() {
        return this.version;
    }

    public int getMatrixWidth() {
        return this.matrixWidth;
    }

    public int getMaskPattern() {
        return this.maskPattern;
    }

    public int getNumTotalBytes() {
        return this.numTotalBytes;
    }

    public int getNumDataBytes() {
        return this.numDataBytes;
    }

    public int getNumECBytes() {
        return this.numECBytes;
    }

    public int getNumRSBlocks() {
        return this.numRSBlocks;
    }

    public ByteMatrix getMatrix() {
        return this.matrix;
    }

    /* renamed from: at */
    public int mo25315at(int x, int y) {
        int value = this.matrix.get(x, y);
        if (value == 0 || value == 1) {
            return value;
        }
        throw new RuntimeException("Bad value");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:22:0x0034, code lost:
        r0 = r3.matrix;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:8:0x0011, code lost:
        r0 = r3.maskPattern;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean isValid() {
        /*
            r3 = this;
            com.itextpdf.barcodes.qrcode.Mode r0 = r3.mode
            if (r0 == 0) goto L_0x0050
            com.itextpdf.barcodes.qrcode.ErrorCorrectionLevel r0 = r3.ecLevel
            if (r0 == 0) goto L_0x0050
            int r0 = r3.version
            r1 = -1
            if (r0 == r1) goto L_0x0050
            int r0 = r3.matrixWidth
            if (r0 == r1) goto L_0x0050
            int r0 = r3.maskPattern
            if (r0 == r1) goto L_0x0050
            int r2 = r3.numTotalBytes
            if (r2 == r1) goto L_0x0050
            int r2 = r3.numDataBytes
            if (r2 == r1) goto L_0x0050
            int r2 = r3.numECBytes
            if (r2 == r1) goto L_0x0050
            int r2 = r3.numRSBlocks
            if (r2 == r1) goto L_0x0050
            boolean r0 = isValidMaskPattern(r0)
            if (r0 == 0) goto L_0x0050
            int r0 = r3.numTotalBytes
            int r1 = r3.numDataBytes
            int r2 = r3.numECBytes
            int r1 = r1 + r2
            if (r0 != r1) goto L_0x0050
            com.itextpdf.barcodes.qrcode.ByteMatrix r0 = r3.matrix
            if (r0 == 0) goto L_0x0050
            int r1 = r3.matrixWidth
            int r0 = r0.getWidth()
            if (r1 != r0) goto L_0x0050
            com.itextpdf.barcodes.qrcode.ByteMatrix r0 = r3.matrix
            int r0 = r0.getWidth()
            com.itextpdf.barcodes.qrcode.ByteMatrix r1 = r3.matrix
            int r1 = r1.getHeight()
            if (r0 != r1) goto L_0x0050
            r0 = 1
            goto L_0x0051
        L_0x0050:
            r0 = 0
        L_0x0051:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.barcodes.qrcode.QRCode.isValid():boolean");
    }

    public String toString() {
        StringBuffer result = new StringBuffer(200);
        result.append("<<\n");
        result.append(" mode: ");
        result.append(this.mode);
        result.append("\n ecLevel: ");
        result.append(this.ecLevel);
        result.append("\n version: ");
        result.append(this.version);
        result.append("\n matrixWidth: ");
        result.append(this.matrixWidth);
        result.append("\n maskPattern: ");
        result.append(this.maskPattern);
        result.append("\n numTotalBytes: ");
        result.append(this.numTotalBytes);
        result.append("\n numDataBytes: ");
        result.append(this.numDataBytes);
        result.append("\n numECBytes: ");
        result.append(this.numECBytes);
        result.append("\n numRSBlocks: ");
        result.append(this.numRSBlocks);
        if (this.matrix == null) {
            result.append("\n matrix: null\n");
        } else {
            result.append("\n matrix:\n");
            result.append(this.matrix.toString());
        }
        result.append(">>\n");
        return result.toString();
    }

    public void setMode(Mode value) {
        this.mode = value;
    }

    public void setECLevel(ErrorCorrectionLevel value) {
        this.ecLevel = value;
    }

    public void setVersion(int value) {
        this.version = value;
    }

    public void setMatrixWidth(int value) {
        this.matrixWidth = value;
    }

    public void setMaskPattern(int value) {
        this.maskPattern = value;
    }

    public void setNumTotalBytes(int value) {
        this.numTotalBytes = value;
    }

    public void setNumDataBytes(int value) {
        this.numDataBytes = value;
    }

    public void setNumECBytes(int value) {
        this.numECBytes = value;
    }

    public void setNumRSBlocks(int value) {
        this.numRSBlocks = value;
    }

    public void setMatrix(ByteMatrix value) {
        this.matrix = value;
    }

    public static boolean isValidMaskPattern(int maskPattern2) {
        return maskPattern2 >= 0 && maskPattern2 < 8;
    }
}
