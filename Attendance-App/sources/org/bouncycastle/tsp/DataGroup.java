package org.bouncycastle.tsp;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;
import kotlin.UByte;
import org.bouncycastle.operator.DigestCalculator;
import org.bouncycastle.util.Arrays;

public class DataGroup {
    private List<byte[]> dataObjects;
    private byte[] groupHash;
    private TreeSet<byte[]> hashes;

    private class ByteArrayComparator implements Comparator {
        private ByteArrayComparator() {
        }

        public int compare(Object obj, Object obj2) {
            byte[] bArr = (byte[]) obj;
            byte[] bArr2 = (byte[]) obj2;
            int length = bArr.length < bArr2.length ? bArr.length : bArr2.length;
            for (int i = 0; i != length; i++) {
                byte b = bArr[i] & UByte.MAX_VALUE;
                byte b2 = bArr2[i] & UByte.MAX_VALUE;
                if (b != b2) {
                    return b - b2;
                }
            }
            return bArr.length - bArr2.length;
        }
    }

    public DataGroup(List<byte[]> list) {
        this.dataObjects = list;
    }

    public DataGroup(byte[] bArr) {
        ArrayList arrayList = new ArrayList();
        this.dataObjects = arrayList;
        arrayList.add(bArr);
    }

    static byte[] calcDigest(DigestCalculator digestCalculator, byte[] bArr) {
        try {
            OutputStream outputStream = digestCalculator.getOutputStream();
            outputStream.write(bArr);
            outputStream.close();
            return digestCalculator.getDigest();
        } catch (IOException e) {
            throw new IllegalStateException("digest calculator failure: " + e.getMessage());
        }
    }

    private TreeSet<byte[]> getHashes(DigestCalculator digestCalculator, byte[] bArr) {
        if (this.hashes == null) {
            this.hashes = new TreeSet<>(new ByteArrayComparator());
            for (int i = 0; i != this.dataObjects.size(); i++) {
                TreeSet<byte[]> treeSet = this.hashes;
                byte[] calcDigest = calcDigest(digestCalculator, this.dataObjects.get(i));
                if (bArr != null) {
                    treeSet.add(calcDigest(digestCalculator, Arrays.concatenate(calcDigest, bArr)));
                } else {
                    treeSet.add(calcDigest);
                }
            }
        }
        return this.hashes;
    }

    public byte[] getHash(DigestCalculator digestCalculator) {
        if (this.groupHash == null) {
            TreeSet<byte[]> hashes2 = getHashes(digestCalculator);
            if (hashes2.size() > 1) {
                byte[] bArr = new byte[0];
                Iterator<byte[]> it = hashes2.iterator();
                while (it.hasNext()) {
                    bArr = Arrays.concatenate(bArr, it.next());
                }
                this.groupHash = calcDigest(digestCalculator, bArr);
            } else {
                this.groupHash = hashes2.first();
            }
        }
        return this.groupHash;
    }

    public TreeSet<byte[]> getHashes(DigestCalculator digestCalculator) {
        return getHashes(digestCalculator, (byte[]) null);
    }
}
