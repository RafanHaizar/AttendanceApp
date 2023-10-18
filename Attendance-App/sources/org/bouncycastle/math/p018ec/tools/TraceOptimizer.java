package org.bouncycastle.math.p018ec.tools;

import java.io.PrintStream;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.TreeSet;
import org.bouncycastle.asn1.p008x9.ECNamedCurveTable;
import org.bouncycastle.asn1.p008x9.X9ECParameters;
import org.bouncycastle.crypto.p012ec.CustomNamedCurves;
import org.bouncycastle.math.p018ec.ECAlgorithms;
import org.bouncycastle.math.p018ec.ECCurve;
import org.bouncycastle.math.p018ec.ECFieldElement;
import org.bouncycastle.util.Integers;

/* renamed from: org.bouncycastle.math.ec.tools.TraceOptimizer */
public class TraceOptimizer {
    private static final BigInteger ONE = BigInteger.valueOf(1);

    /* renamed from: R */
    private static final SecureRandom f842R = new SecureRandom();

    private static int calculateTrace(ECFieldElement eCFieldElement) {
        int fieldSize = eCFieldElement.getFieldSize();
        int numberOfLeadingZeros = 31 - Integers.numberOfLeadingZeros(fieldSize);
        int i = 1;
        ECFieldElement eCFieldElement2 = eCFieldElement;
        while (numberOfLeadingZeros > 0) {
            eCFieldElement2 = eCFieldElement2.squarePow(i).add(eCFieldElement2);
            numberOfLeadingZeros--;
            i = fieldSize >>> numberOfLeadingZeros;
            if ((i & 1) != 0) {
                eCFieldElement2 = eCFieldElement2.square().add(eCFieldElement);
            }
        }
        if (eCFieldElement2.isZero()) {
            return 0;
        }
        if (eCFieldElement2.isOne()) {
            return 1;
        }
        throw new IllegalStateException("Internal error in trace calculation");
    }

    private static ArrayList enumToList(Enumeration enumeration) {
        ArrayList arrayList = new ArrayList();
        while (enumeration.hasMoreElements()) {
            arrayList.add(enumeration.nextElement());
        }
        return arrayList;
    }

    public static void implPrintNonZeroTraceBits(X9ECParameters x9ECParameters) {
        StringBuilder sb;
        PrintStream printStream;
        ECCurve curve = x9ECParameters.getCurve();
        int fieldSize = curve.getFieldSize();
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < fieldSize; i++) {
            if ((i & 1) != 0 || i == 0) {
                if (calculateTrace(curve.fromBigInteger(ONE.shiftLeft(i))) != 0) {
                    arrayList.add(Integers.valueOf(i));
                    printStream = System.out;
                    sb = new StringBuilder();
                }
            } else if (arrayList.contains(Integers.valueOf(i >>> 1))) {
                arrayList.add(Integers.valueOf(i));
                printStream = System.out;
                sb = new StringBuilder();
            }
            printStream.print(sb.append(" ").append(i).toString());
        }
        System.out.println();
        int i2 = 0;
        while (i2 < 1000) {
            BigInteger bigInteger = new BigInteger(fieldSize, f842R);
            int calculateTrace = calculateTrace(curve.fromBigInteger(bigInteger));
            int i3 = 0;
            for (int i4 = 0; i4 < arrayList.size(); i4++) {
                if (bigInteger.testBit(((Integer) arrayList.get(i4)).intValue())) {
                    i3 ^= 1;
                }
            }
            if (calculateTrace == i3) {
                i2++;
            } else {
                throw new IllegalStateException("Optimized-trace sanity check failed");
            }
        }
    }

    public static void main(String[] strArr) {
        TreeSet<String> treeSet = new TreeSet<>(enumToList(ECNamedCurveTable.getNames()));
        treeSet.addAll(enumToList(CustomNamedCurves.getNames()));
        for (String str : treeSet) {
            X9ECParameters byName = CustomNamedCurves.getByName(str);
            if (byName == null) {
                byName = ECNamedCurveTable.getByName(str);
            }
            if (byName != null && ECAlgorithms.isF2mCurve(byName.getCurve())) {
                System.out.print(str + ":");
                implPrintNonZeroTraceBits(byName);
            }
        }
    }

    public static void printNonZeroTraceBits(X9ECParameters x9ECParameters) {
        if (ECAlgorithms.isF2mCurve(x9ECParameters.getCurve())) {
            implPrintNonZeroTraceBits(x9ECParameters);
            return;
        }
        throw new IllegalArgumentException("Trace only defined over characteristic-2 fields");
    }
}
