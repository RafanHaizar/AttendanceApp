package org.bouncycastle.mime;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import org.bouncycastle.mime.smime.SMimeParserContext;

public class CanonicalOutputStream extends FilterOutputStream {
    protected static byte[] newline;
    private final boolean is7Bit;
    protected int lastb = -1;

    static {
        byte[] bArr = new byte[2];
        newline = bArr;
        bArr[0] = 13;
        bArr[1] = 10;
    }

    public CanonicalOutputStream(SMimeParserContext sMimeParserContext, Headers headers, OutputStream outputStream) {
        super(outputStream);
        this.is7Bit = headers.getContentType() != null ? headers.getContentType() != null && !headers.getContentType().equals("binary") : sMimeParserContext.getDefaultContentTransferEncoding().equals("7bit");
    }

    public void write(int i) throws IOException {
        if (this.is7Bit) {
            if (i == 13) {
                this.out.write(newline);
            } else if (i == 10) {
                if (this.lastb != 13) {
                    this.out.write(newline);
                }
            }
            this.lastb = i;
        }
        this.out.write(i);
        this.lastb = i;
    }

    public void write(byte[] bArr) throws IOException {
        write(bArr, 0, bArr.length);
    }

    public void write(byte[] bArr, int i, int i2) throws IOException {
        for (int i3 = i; i3 != i + i2; i3++) {
            write((int) bArr[i3]);
        }
    }

    public void writeln() throws IOException {
        this.out.write(newline);
    }
}
