package org.bouncycastle.tsp;

import java.io.IOException;
import java.io.OutputStream;
import org.bouncycastle.asn1.tsp.PartialHashtree;
import org.bouncycastle.operator.DigestCalculator;
import org.bouncycastle.util.Arrays;

public class PartialHashTreeProcessor {
    private final byte[][] values;

    public PartialHashTreeProcessor(PartialHashtree partialHashtree) {
        this.values = partialHashtree.getValues();
    }

    public boolean containsHash(byte[] bArr) {
        int i = 1;
        while (true) {
            byte[][] bArr2 = this.values;
            if (i == bArr2.length) {
                return false;
            }
            if (Arrays.areEqual(bArr, bArr2[i])) {
                return true;
            }
            i++;
        }
    }

    public byte[] getHash(DigestCalculator digestCalculator) {
        byte[][] bArr = this.values;
        int i = 1;
        if (bArr.length == 1) {
            return bArr[0];
        }
        try {
            OutputStream outputStream = digestCalculator.getOutputStream();
            while (true) {
                byte[][] bArr2 = this.values;
                if (i == bArr2.length) {
                    return digestCalculator.getDigest();
                }
                outputStream.write(bArr2[i]);
                i++;
            }
        } catch (IOException e) {
            throw new IllegalStateException("calculator failed: " + e.getMessage());
        }
    }

    public void verifyContainsHash(byte[] bArr) throws PartialHashTreeVerificationException {
        if (!containsHash(bArr)) {
            throw new PartialHashTreeVerificationException("calculated hash is not present in partial hash tree");
        }
    }
}
