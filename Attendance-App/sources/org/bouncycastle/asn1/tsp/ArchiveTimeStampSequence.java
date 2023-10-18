package org.bouncycastle.asn1.tsp;

import java.util.Enumeration;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.DERSequence;

public class ArchiveTimeStampSequence extends ASN1Object {
    private ASN1Sequence archiveTimeStampChains;

    private ArchiveTimeStampSequence(ASN1Sequence aSN1Sequence) throws IllegalArgumentException {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector(aSN1Sequence.size());
        Enumeration objects = aSN1Sequence.getObjects();
        while (objects.hasMoreElements()) {
            aSN1EncodableVector.add(ArchiveTimeStampChain.getInstance(objects.nextElement()));
        }
        this.archiveTimeStampChains = new DERSequence(aSN1EncodableVector);
    }

    public ArchiveTimeStampSequence(ArchiveTimeStampChain archiveTimeStampChain) {
        this.archiveTimeStampChains = new DERSequence((ASN1Encodable) archiveTimeStampChain);
    }

    public ArchiveTimeStampSequence(ArchiveTimeStampChain[] archiveTimeStampChainArr) {
        this.archiveTimeStampChains = new DERSequence((ASN1Encodable[]) archiveTimeStampChainArr);
    }

    public static ArchiveTimeStampSequence getInstance(Object obj) {
        if (obj instanceof ArchiveTimeStampChain) {
            return (ArchiveTimeStampSequence) obj;
        }
        if (obj != null) {
            return new ArchiveTimeStampSequence(ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    public ArchiveTimeStampSequence append(ArchiveTimeStampChain archiveTimeStampChain) {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector(this.archiveTimeStampChains.size() + 1);
        for (int i = 0; i != this.archiveTimeStampChains.size(); i++) {
            aSN1EncodableVector.add(this.archiveTimeStampChains.getObjectAt(i));
        }
        aSN1EncodableVector.add(archiveTimeStampChain);
        return new ArchiveTimeStampSequence((ASN1Sequence) new DERSequence(aSN1EncodableVector));
    }

    public ArchiveTimeStampChain[] getArchiveTimeStampChains() {
        int size = this.archiveTimeStampChains.size();
        ArchiveTimeStampChain[] archiveTimeStampChainArr = new ArchiveTimeStampChain[size];
        for (int i = 0; i != size; i++) {
            archiveTimeStampChainArr[i] = ArchiveTimeStampChain.getInstance(this.archiveTimeStampChains.getObjectAt(i));
        }
        return archiveTimeStampChainArr;
    }

    public int size() {
        return this.archiveTimeStampChains.size();
    }

    public ASN1Primitive toASN1Primitive() {
        return this.archiveTimeStampChains;
    }
}
