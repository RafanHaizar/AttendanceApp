package com.itextpdf.p026io.font.woff2;

/* renamed from: com.itextpdf.io.font.woff2.Woff2MemoryOut */
class Woff2MemoryOut implements Woff2Out {
    private byte[] buf_;
    private int buf_size_;
    private int offset_ = 0;

    public Woff2MemoryOut(byte[] buf_2, int buf_size_2) {
        this.buf_ = buf_2;
        this.buf_size_ = buf_size_2;
    }

    public void write(byte[] buf, int buff_offset, int n) {
        write(buf, buff_offset, this.offset_, n);
    }

    public void write(byte[] buf, int buff_offset, int offset, int n) {
        int i = this.buf_size_;
        if (offset > i || n > i - offset) {
            throw new FontCompressionException(FontCompressionException.WRITE_FAILED);
        }
        System.arraycopy(buf, buff_offset, this.buf_, offset, n);
        this.offset_ = Math.max(this.offset_, offset + n);
    }

    public int size() {
        return this.offset_;
    }
}
