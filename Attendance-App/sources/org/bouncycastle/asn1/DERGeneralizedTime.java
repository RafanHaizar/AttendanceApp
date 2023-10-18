package org.bouncycastle.asn1;

import java.io.IOException;
import java.util.Date;
import org.bouncycastle.util.Strings;

public class DERGeneralizedTime extends ASN1GeneralizedTime {
    public DERGeneralizedTime(String str) {
        super(str);
    }

    public DERGeneralizedTime(Date date) {
        super(date);
    }

    public DERGeneralizedTime(byte[] bArr) {
        super(bArr);
    }

    private byte[] getDERTime() {
        if (this.time[this.time.length - 1] != 90) {
            return this.time;
        }
        if (!hasMinutes()) {
            byte[] bArr = new byte[(this.time.length + 4)];
            System.arraycopy(this.time, 0, bArr, 0, this.time.length - 1);
            System.arraycopy(Strings.toByteArray("0000Z"), 0, bArr, this.time.length - 1, 5);
            return bArr;
        } else if (!hasSeconds()) {
            byte[] bArr2 = new byte[(this.time.length + 2)];
            System.arraycopy(this.time, 0, bArr2, 0, this.time.length - 1);
            System.arraycopy(Strings.toByteArray("00Z"), 0, bArr2, this.time.length - 1, 3);
            return bArr2;
        } else if (!hasFractionalSeconds()) {
            return this.time;
        } else {
            int length = this.time.length - 2;
            while (length > 0 && this.time[length] == 48) {
                length--;
            }
            if (this.time[length] == 46) {
                byte[] bArr3 = new byte[(length + 1)];
                System.arraycopy(this.time, 0, bArr3, 0, length);
                bArr3[length] = 90;
                return bArr3;
            }
            byte[] bArr4 = new byte[(length + 2)];
            int i = length + 1;
            System.arraycopy(this.time, 0, bArr4, 0, i);
            bArr4[i] = 90;
            return bArr4;
        }
    }

    /* access modifiers changed from: package-private */
    public void encode(ASN1OutputStream aSN1OutputStream, boolean z) throws IOException {
        aSN1OutputStream.writeEncoded(z, 24, getDERTime());
    }

    /* access modifiers changed from: package-private */
    public int encodedLength() {
        int length = getDERTime().length;
        return StreamUtil.calculateBodyLength(length) + 1 + length;
    }

    /* access modifiers changed from: package-private */
    public ASN1Primitive toDERObject() {
        return this;
    }

    /* access modifiers changed from: package-private */
    public ASN1Primitive toDLObject() {
        return this;
    }
}
