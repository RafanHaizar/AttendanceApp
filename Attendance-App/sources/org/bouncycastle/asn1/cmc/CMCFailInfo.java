package org.bouncycastle.asn1.cmc;

import java.util.HashMap;
import java.util.Map;
import org.bouncycastle.asn1.ASN1Integer;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1Primitive;

public class CMCFailInfo extends ASN1Object {
    public static final CMCFailInfo authDataFail;
    public static final CMCFailInfo badAlg;
    public static final CMCFailInfo badCertId;
    public static final CMCFailInfo badIdentity;
    public static final CMCFailInfo badMessageCheck;
    public static final CMCFailInfo badRequest;
    public static final CMCFailInfo badTime;
    public static final CMCFailInfo internalCAError;
    public static final CMCFailInfo mustArchiveKeys;
    public static final CMCFailInfo noKeyReuse;
    public static final CMCFailInfo popFailed;
    public static final CMCFailInfo popRequired;
    private static Map range;
    public static final CMCFailInfo tryLater;
    public static final CMCFailInfo unsupportedExt;
    private final ASN1Integer value;

    static {
        CMCFailInfo cMCFailInfo = new CMCFailInfo(new ASN1Integer(0));
        badAlg = cMCFailInfo;
        CMCFailInfo cMCFailInfo2 = new CMCFailInfo(new ASN1Integer(1));
        badMessageCheck = cMCFailInfo2;
        CMCFailInfo cMCFailInfo3 = new CMCFailInfo(new ASN1Integer(2));
        badRequest = cMCFailInfo3;
        CMCFailInfo cMCFailInfo4 = new CMCFailInfo(new ASN1Integer(3));
        badTime = cMCFailInfo4;
        CMCFailInfo cMCFailInfo5 = new CMCFailInfo(new ASN1Integer(4));
        badCertId = cMCFailInfo5;
        CMCFailInfo cMCFailInfo6 = new CMCFailInfo(new ASN1Integer(5));
        unsupportedExt = cMCFailInfo6;
        CMCFailInfo cMCFailInfo7 = new CMCFailInfo(new ASN1Integer(6));
        mustArchiveKeys = cMCFailInfo7;
        CMCFailInfo cMCFailInfo8 = new CMCFailInfo(new ASN1Integer(7));
        badIdentity = cMCFailInfo8;
        CMCFailInfo cMCFailInfo9 = new CMCFailInfo(new ASN1Integer(8));
        popRequired = cMCFailInfo9;
        CMCFailInfo cMCFailInfo10 = new CMCFailInfo(new ASN1Integer(9));
        popFailed = cMCFailInfo10;
        CMCFailInfo cMCFailInfo11 = new CMCFailInfo(new ASN1Integer(10));
        noKeyReuse = cMCFailInfo11;
        CMCFailInfo cMCFailInfo12 = new CMCFailInfo(new ASN1Integer(11));
        internalCAError = cMCFailInfo12;
        CMCFailInfo cMCFailInfo13 = new CMCFailInfo(new ASN1Integer(12));
        tryLater = cMCFailInfo13;
        CMCFailInfo cMCFailInfo14 = cMCFailInfo12;
        CMCFailInfo cMCFailInfo15 = new CMCFailInfo(new ASN1Integer(13));
        authDataFail = cMCFailInfo15;
        HashMap hashMap = new HashMap();
        range = hashMap;
        hashMap.put(cMCFailInfo.value, cMCFailInfo);
        range.put(cMCFailInfo2.value, cMCFailInfo2);
        range.put(cMCFailInfo3.value, cMCFailInfo3);
        range.put(cMCFailInfo4.value, cMCFailInfo4);
        range.put(cMCFailInfo5.value, cMCFailInfo5);
        range.put(cMCFailInfo9.value, cMCFailInfo9);
        range.put(cMCFailInfo6.value, cMCFailInfo6);
        range.put(cMCFailInfo7.value, cMCFailInfo7);
        range.put(cMCFailInfo8.value, cMCFailInfo8);
        range.put(cMCFailInfo9.value, cMCFailInfo9);
        range.put(cMCFailInfo10.value, cMCFailInfo10);
        range.put(cMCFailInfo5.value, cMCFailInfo5);
        range.put(cMCFailInfo9.value, cMCFailInfo9);
        range.put(cMCFailInfo11.value, cMCFailInfo11);
        range.put(cMCFailInfo14.value, cMCFailInfo14);
        CMCFailInfo cMCFailInfo16 = cMCFailInfo13;
        range.put(cMCFailInfo16.value, cMCFailInfo16);
        range.put(cMCFailInfo15.value, cMCFailInfo15);
    }

    private CMCFailInfo(ASN1Integer aSN1Integer) {
        this.value = aSN1Integer;
    }

    public static CMCFailInfo getInstance(Object obj) {
        if (obj instanceof CMCFailInfo) {
            return (CMCFailInfo) obj;
        }
        if (obj == null) {
            return null;
        }
        CMCFailInfo cMCFailInfo = (CMCFailInfo) range.get(ASN1Integer.getInstance(obj));
        if (cMCFailInfo != null) {
            return cMCFailInfo;
        }
        throw new IllegalArgumentException("unknown object in getInstance(): " + obj.getClass().getName());
    }

    public ASN1Primitive toASN1Primitive() {
        return this.value;
    }
}
