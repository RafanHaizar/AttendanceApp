package com.itextpdf.barcodes.qrcode;

import java.util.ArrayList;
import java.util.List;

final class ReedSolomonEncoder {
    private final List<GF256Poly> cachedGenerators;
    private final GF256 field;

    public ReedSolomonEncoder(GF256 field2) {
        if (GF256.QR_CODE_FIELD.equals(field2)) {
            this.field = field2;
            ArrayList arrayList = new ArrayList();
            this.cachedGenerators = arrayList;
            arrayList.add(new GF256Poly(field2, new int[]{1}));
            return;
        }
        throw new UnsupportedOperationException("Only QR Code is supported at this time");
    }

    private GF256Poly buildGenerator(int degree) {
        if (degree >= this.cachedGenerators.size()) {
            List<GF256Poly> list = this.cachedGenerators;
            GF256Poly lastGenerator = list.get(list.size() - 1);
            for (int d = this.cachedGenerators.size(); d <= degree; d++) {
                GF256 gf256 = this.field;
                GF256Poly nextGenerator = lastGenerator.multiply(new GF256Poly(gf256, new int[]{1, gf256.exp(d - 1)}));
                this.cachedGenerators.add(nextGenerator);
                lastGenerator = nextGenerator;
            }
        }
        return this.cachedGenerators.get(degree);
    }

    public void encode(int[] toEncode, int ecBytes) {
        if (ecBytes != 0) {
            int dataBytes = toEncode.length - ecBytes;
            if (dataBytes > 0) {
                GF256Poly generator = buildGenerator(ecBytes);
                int[] infoCoefficients = new int[dataBytes];
                System.arraycopy(toEncode, 0, infoCoefficients, 0, dataBytes);
                int[] coefficients = new GF256Poly(this.field, infoCoefficients).multiplyByMonomial(ecBytes, 1).divide(generator)[1].getCoefficients();
                int numZeroCoefficients = ecBytes - coefficients.length;
                for (int i = 0; i < numZeroCoefficients; i++) {
                    toEncode[dataBytes + i] = 0;
                }
                System.arraycopy(coefficients, 0, toEncode, dataBytes + numZeroCoefficients, coefficients.length);
                return;
            }
            throw new IllegalArgumentException("No data bytes provided");
        }
        throw new IllegalArgumentException("No error correction bytes");
    }
}
