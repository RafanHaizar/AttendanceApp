package com.itextpdf.barcodes.qrcode;

final class BlockPair {
    private final ByteArray dataBytes;
    private final ByteArray errorCorrectionBytes;

    BlockPair(ByteArray data, ByteArray errorCorrection) {
        this.dataBytes = data;
        this.errorCorrectionBytes = errorCorrection;
    }

    public ByteArray getDataBytes() {
        return this.dataBytes;
    }

    public ByteArray getErrorCorrectionBytes() {
        return this.errorCorrectionBytes;
    }
}
