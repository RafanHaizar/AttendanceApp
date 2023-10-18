package com.itextpdf.kernel.pdf.canvas.wmf;

import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.p026io.util.StreamUtil;
import java.io.IOException;
import java.io.InputStream;

public class InputMeta {

    /* renamed from: in */
    InputStream f1497in;
    int length;

    public InputMeta(InputStream in) {
        this.f1497in = in;
    }

    public int readWord() throws IOException {
        this.length += 2;
        int k1 = this.f1497in.read();
        if (k1 < 0) {
            return 0;
        }
        return ((this.f1497in.read() << 8) + k1) & 65535;
    }

    public int readShort() throws IOException {
        int k = readWord();
        if (k > 32767) {
            return k - 65536;
        }
        return k;
    }

    public int readInt() throws IOException {
        this.length += 4;
        int k1 = this.f1497in.read();
        if (k1 < 0) {
            return 0;
        }
        return k1 + (this.f1497in.read() << 8) + (this.f1497in.read() << 16) + (this.f1497in.read() << 24);
    }

    public int readByte() throws IOException {
        this.length++;
        return this.f1497in.read() & 255;
    }

    public void skip(int len) throws IOException {
        this.length += len;
        StreamUtil.skip(this.f1497in, (long) len);
    }

    public int getLength() {
        return this.length;
    }

    public Color readColor() throws IOException {
        int red = readByte();
        int green = readByte();
        int blue = readByte();
        readByte();
        return new DeviceRgb(red, green, blue);
    }
}
