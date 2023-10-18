package org.bouncycastle.asn1;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Vector;

public class BEROctetString extends ASN1OctetString {
    private static final int DEFAULT_LENGTH = 1000;
    private final int chunkSize;
    /* access modifiers changed from: private */
    public final ASN1OctetString[] octs;

    public BEROctetString(byte[] bArr) {
        this(bArr, 1000);
    }

    public BEROctetString(byte[] bArr, int i) {
        this(bArr, (ASN1OctetString[]) null, i);
    }

    private BEROctetString(byte[] bArr, ASN1OctetString[] aSN1OctetStringArr, int i) {
        super(bArr);
        this.octs = aSN1OctetStringArr;
        this.chunkSize = i;
    }

    public BEROctetString(ASN1OctetString[] aSN1OctetStringArr) {
        this(aSN1OctetStringArr, 1000);
    }

    public BEROctetString(ASN1OctetString[] aSN1OctetStringArr, int i) {
        this(toBytes(aSN1OctetStringArr), aSN1OctetStringArr, i);
    }

    static BEROctetString fromSequence(ASN1Sequence aSN1Sequence) {
        int size = aSN1Sequence.size();
        ASN1OctetString[] aSN1OctetStringArr = new ASN1OctetString[size];
        for (int i = 0; i < size; i++) {
            aSN1OctetStringArr[i] = ASN1OctetString.getInstance(aSN1Sequence.getObjectAt(i));
        }
        return new BEROctetString(aSN1OctetStringArr);
    }

    private Vector generateOcts() {
        Vector vector = new Vector();
        int i = 0;
        while (i < this.string.length) {
            int length = (this.chunkSize + i > this.string.length ? this.string.length : this.chunkSize + i) - i;
            byte[] bArr = new byte[length];
            System.arraycopy(this.string, i, bArr, 0, length);
            vector.addElement(new DEROctetString(bArr));
            i += this.chunkSize;
        }
        return vector;
    }

    private static byte[] toBytes(ASN1OctetString[] aSN1OctetStringArr) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        int i = 0;
        while (i != aSN1OctetStringArr.length) {
            try {
                byteArrayOutputStream.write(aSN1OctetStringArr[i].getOctets());
                i++;
            } catch (ClassCastException e) {
                throw new IllegalArgumentException(aSN1OctetStringArr[i].getClass().getName() + " found in input should only contain DEROctetString");
            } catch (IOException e2) {
                throw new IllegalArgumentException("exception converting octets " + e2.toString());
            }
        }
        return byteArrayOutputStream.toByteArray();
    }

    public void encode(ASN1OutputStream aSN1OutputStream) throws IOException {
        aSN1OutputStream.writeObject((ASN1Primitive) this);
    }

    /* access modifiers changed from: package-private */
    public void encode(ASN1OutputStream aSN1OutputStream, boolean z) throws IOException {
        aSN1OutputStream.writeEncodedIndef(z, 36, getObjects());
    }

    /* access modifiers changed from: package-private */
    public int encodedLength() throws IOException {
        Enumeration objects = getObjects();
        int i = 0;
        while (objects.hasMoreElements()) {
            i += ((ASN1Encodable) objects.nextElement()).toASN1Primitive().encodedLength();
        }
        return i + 2 + 2;
    }

    public Enumeration getObjects() {
        return this.octs == null ? generateOcts().elements() : new Enumeration() {
            int counter = 0;

            public boolean hasMoreElements() {
                return this.counter < BEROctetString.this.octs.length;
            }

            public Object nextElement() {
                ASN1OctetString[] access$000 = BEROctetString.this.octs;
                int i = this.counter;
                this.counter = i + 1;
                return access$000[i];
            }
        };
    }

    public byte[] getOctets() {
        return this.string;
    }

    /* access modifiers changed from: package-private */
    public boolean isConstructed() {
        return true;
    }
}
