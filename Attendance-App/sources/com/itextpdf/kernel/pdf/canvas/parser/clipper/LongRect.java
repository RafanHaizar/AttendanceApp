package com.itextpdf.kernel.pdf.canvas.parser.clipper;

public class LongRect {
    public long bottom;
    public long left;
    public long right;
    public long top;

    public LongRect() {
    }

    public LongRect(long l, long t, long r, long b) {
        this.left = l;
        this.top = t;
        this.right = r;
        this.bottom = b;
    }

    public LongRect(LongRect ir) {
        this.left = ir.left;
        this.top = ir.top;
        this.right = ir.right;
        this.bottom = ir.bottom;
    }
}
