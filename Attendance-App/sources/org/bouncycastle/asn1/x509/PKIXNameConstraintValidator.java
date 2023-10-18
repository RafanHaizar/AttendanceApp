package org.bouncycastle.asn1.x509;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import kotlin.UByte;
import org.bouncycastle.asn1.ASN1OctetString;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.DERIA5String;
import org.bouncycastle.asn1.x500.RDN;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x500.style.IETFUtils;
import org.bouncycastle.asn1.x500.style.RFC4519Style;
import org.bouncycastle.util.Arrays;
import org.bouncycastle.util.Integers;
import org.bouncycastle.util.Strings;
import org.bouncycastle.util.encoders.Hex;

public class PKIXNameConstraintValidator implements NameConstraintValidator {
    private Set excludedSubtreesDN = new HashSet();
    private Set excludedSubtreesDNS = new HashSet();
    private Set excludedSubtreesEmail = new HashSet();
    private Set excludedSubtreesIP = new HashSet();
    private Set excludedSubtreesOtherName = new HashSet();
    private Set excludedSubtreesURI = new HashSet();
    private Set permittedSubtreesDN;
    private Set permittedSubtreesDNS;
    private Set permittedSubtreesEmail;
    private Set permittedSubtreesIP;
    private Set permittedSubtreesOtherName;
    private Set permittedSubtreesURI;

    private final void addLine(StringBuilder sb, String str) {
        sb.append(str).append(Strings.lineSeparator());
    }

    private void checkExcludedDN(Set set, ASN1Sequence aSN1Sequence) throws NameConstraintValidatorException {
        if (!set.isEmpty()) {
            Iterator it = set.iterator();
            while (it.hasNext()) {
                if (withinDNSubtree(aSN1Sequence, (ASN1Sequence) it.next())) {
                    throw new NameConstraintValidatorException("Subject distinguished name is from an excluded subtree");
                }
            }
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:6:0x0011  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void checkExcludedDNS(java.util.Set r3, java.lang.String r4) throws org.bouncycastle.asn1.x509.NameConstraintValidatorException {
        /*
            r2 = this;
            boolean r0 = r3.isEmpty()
            if (r0 == 0) goto L_0x0007
            return
        L_0x0007:
            java.util.Iterator r3 = r3.iterator()
        L_0x000b:
            boolean r0 = r3.hasNext()
            if (r0 == 0) goto L_0x002c
            java.lang.Object r0 = r3.next()
            java.lang.String r0 = (java.lang.String) r0
            boolean r1 = r2.withinDomain(r4, r0)
            if (r1 != 0) goto L_0x0024
            boolean r0 = r4.equalsIgnoreCase(r0)
            if (r0 != 0) goto L_0x0024
            goto L_0x000b
        L_0x0024:
            org.bouncycastle.asn1.x509.NameConstraintValidatorException r3 = new org.bouncycastle.asn1.x509.NameConstraintValidatorException
            java.lang.String r4 = "DNS is from an excluded subtree."
            r3.<init>(r4)
            throw r3
        L_0x002c:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.bouncycastle.asn1.x509.PKIXNameConstraintValidator.checkExcludedDNS(java.util.Set, java.lang.String):void");
    }

    private void checkExcludedEmail(Set set, String str) throws NameConstraintValidatorException {
        if (!set.isEmpty()) {
            Iterator it = set.iterator();
            while (it.hasNext()) {
                if (emailIsConstrained(str, (String) it.next())) {
                    throw new NameConstraintValidatorException("Email address is from an excluded subtree.");
                }
            }
        }
    }

    private void checkExcludedIP(Set set, byte[] bArr) throws NameConstraintValidatorException {
        if (!set.isEmpty()) {
            Iterator it = set.iterator();
            while (it.hasNext()) {
                if (isIPConstrained(bArr, (byte[]) it.next())) {
                    throw new NameConstraintValidatorException("IP is from an excluded subtree.");
                }
            }
        }
    }

    private void checkExcludedOtherName(Set set, OtherName otherName) throws NameConstraintValidatorException {
        if (!set.isEmpty()) {
            for (Object instance : set) {
                if (otherNameIsConstrained(otherName, OtherName.getInstance(instance))) {
                    throw new NameConstraintValidatorException("OtherName is from an excluded subtree.");
                }
            }
        }
    }

    private void checkExcludedURI(Set set, String str) throws NameConstraintValidatorException {
        if (!set.isEmpty()) {
            Iterator it = set.iterator();
            while (it.hasNext()) {
                if (isUriConstrained(str, (String) it.next())) {
                    throw new NameConstraintValidatorException("URI is from an excluded subtree.");
                }
            }
        }
    }

    private void checkPermittedDN(Set set, ASN1Sequence aSN1Sequence) throws NameConstraintValidatorException {
        if (set != null) {
            if (!set.isEmpty() || aSN1Sequence.size() != 0) {
                Iterator it = set.iterator();
                while (it.hasNext()) {
                    if (withinDNSubtree(aSN1Sequence, (ASN1Sequence) it.next())) {
                        return;
                    }
                }
                throw new NameConstraintValidatorException("Subject distinguished name is not from a permitted subtree");
            }
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:5:0x000d  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void checkPermittedDNS(java.util.Set r4, java.lang.String r5) throws org.bouncycastle.asn1.x509.NameConstraintValidatorException {
        /*
            r3 = this;
            if (r4 != 0) goto L_0x0003
            return
        L_0x0003:
            java.util.Iterator r0 = r4.iterator()
        L_0x0007:
            boolean r1 = r0.hasNext()
            if (r1 == 0) goto L_0x0020
            java.lang.Object r1 = r0.next()
            java.lang.String r1 = (java.lang.String) r1
            boolean r2 = r3.withinDomain(r5, r1)
            if (r2 != 0) goto L_0x001f
            boolean r1 = r5.equalsIgnoreCase(r1)
            if (r1 == 0) goto L_0x0007
        L_0x001f:
            return
        L_0x0020:
            int r5 = r5.length()
            if (r5 != 0) goto L_0x002d
            int r4 = r4.size()
            if (r4 != 0) goto L_0x002d
            return
        L_0x002d:
            org.bouncycastle.asn1.x509.NameConstraintValidatorException r4 = new org.bouncycastle.asn1.x509.NameConstraintValidatorException
            java.lang.String r5 = "DNS is not from a permitted subtree."
            r4.<init>(r5)
            goto L_0x0036
        L_0x0035:
            throw r4
        L_0x0036:
            goto L_0x0035
        */
        throw new UnsupportedOperationException("Method not decompiled: org.bouncycastle.asn1.x509.PKIXNameConstraintValidator.checkPermittedDNS(java.util.Set, java.lang.String):void");
    }

    private void checkPermittedEmail(Set set, String str) throws NameConstraintValidatorException {
        if (set != null) {
            Iterator it = set.iterator();
            while (it.hasNext()) {
                if (emailIsConstrained(str, (String) it.next())) {
                    return;
                }
            }
            if (str.length() != 0 || set.size() != 0) {
                throw new NameConstraintValidatorException("Subject email address is not from a permitted subtree.");
            }
        }
    }

    private void checkPermittedIP(Set set, byte[] bArr) throws NameConstraintValidatorException {
        if (set != null) {
            Iterator it = set.iterator();
            while (it.hasNext()) {
                if (isIPConstrained(bArr, (byte[]) it.next())) {
                    return;
                }
            }
            if (bArr.length != 0 || set.size() != 0) {
                throw new NameConstraintValidatorException("IP is not from a permitted subtree.");
            }
        }
    }

    private void checkPermittedOtherName(Set set, OtherName otherName) throws NameConstraintValidatorException {
        if (set != null) {
            Iterator it = set.iterator();
            while (it.hasNext()) {
                if (otherNameIsConstrained(otherName, (OtherName) it.next())) {
                    return;
                }
            }
            throw new NameConstraintValidatorException("Subject OtherName is not from a permitted subtree.");
        }
    }

    private void checkPermittedURI(Set set, String str) throws NameConstraintValidatorException {
        if (set != null) {
            Iterator it = set.iterator();
            while (it.hasNext()) {
                if (isUriConstrained(str, (String) it.next())) {
                    return;
                }
            }
            if (str.length() != 0 || set.size() != 0) {
                throw new NameConstraintValidatorException("URI is not from a permitted subtree.");
            }
        }
    }

    private boolean collectionsAreEqual(Collection collection, Collection collection2) {
        boolean z;
        if (collection == collection2) {
            return true;
        }
        if (collection == null || collection2 == null || collection.size() != collection2.size()) {
            return false;
        }
        for (Object next : collection) {
            Iterator it = collection2.iterator();
            while (true) {
                if (it.hasNext()) {
                    if (equals(next, it.next())) {
                        z = true;
                        continue;
                        break;
                    }
                } else {
                    z = false;
                    continue;
                    break;
                }
            }
            if (!z) {
                return false;
            }
        }
        return true;
    }

    private static int compareTo(byte[] bArr, byte[] bArr2) {
        if (Arrays.areEqual(bArr, bArr2)) {
            return 0;
        }
        return Arrays.areEqual(max(bArr, bArr2), bArr) ? 1 : -1;
    }

    private boolean emailIsConstrained(String str, String str2) {
        String substring = str.substring(str.indexOf(64) + 1);
        if (str2.indexOf(64) != -1) {
            return str.equalsIgnoreCase(str2) || substring.equalsIgnoreCase(str2.substring(1));
        }
        if (str2.charAt(0) != '.') {
            if (substring.equalsIgnoreCase(str2)) {
                return true;
            }
        } else if (withinDomain(substring, str2)) {
            return true;
        }
    }

    private boolean equals(Object obj, Object obj2) {
        if (obj == obj2) {
            return true;
        }
        if (obj == null || obj2 == null) {
            return false;
        }
        return (!(obj instanceof byte[]) || !(obj2 instanceof byte[])) ? obj.equals(obj2) : Arrays.areEqual((byte[]) obj, (byte[]) obj2);
    }

    private static String extractHostFromURL(String str) {
        String substring = str.substring(str.indexOf(58) + 1);
        if (substring.indexOf("//") != -1) {
            substring = substring.substring(substring.indexOf("//") + 2);
        }
        if (substring.lastIndexOf(58) != -1) {
            substring = substring.substring(0, substring.lastIndexOf(58));
        }
        String substring2 = substring.substring(substring.indexOf(58) + 1);
        String substring3 = substring2.substring(substring2.indexOf(64) + 1);
        return substring3.indexOf(47) != -1 ? substring3.substring(0, substring3.indexOf(47)) : substring3;
    }

    private byte[][] extractIPsAndSubnetMasks(byte[] bArr, byte[] bArr2) {
        int length = bArr.length / 2;
        byte[] bArr3 = new byte[length];
        byte[] bArr4 = new byte[length];
        System.arraycopy(bArr, 0, bArr3, 0, length);
        System.arraycopy(bArr, length, bArr4, 0, length);
        byte[] bArr5 = new byte[length];
        byte[] bArr6 = new byte[length];
        System.arraycopy(bArr2, 0, bArr5, 0, length);
        System.arraycopy(bArr2, length, bArr6, 0, length);
        return new byte[][]{bArr3, bArr4, bArr5, bArr6};
    }

    private String extractNameAsString(GeneralName generalName) {
        return DERIA5String.getInstance(generalName.getName()).getString();
    }

    private int hashCollection(Collection collection) {
        int i = 0;
        if (collection == null) {
            return 0;
        }
        for (Object next : collection) {
            i += next instanceof byte[] ? Arrays.hashCode((byte[]) next) : next.hashCode();
        }
        return i;
    }

    private Set intersectDN(Set set, Set set2) {
        HashSet hashSet = new HashSet();
        Iterator it = set2.iterator();
        while (it.hasNext()) {
            ASN1Sequence instance = ASN1Sequence.getInstance(((GeneralSubtree) it.next()).getBase().getName().toASN1Primitive());
            if (set != null) {
                Iterator it2 = set.iterator();
                while (it2.hasNext()) {
                    ASN1Sequence aSN1Sequence = (ASN1Sequence) it2.next();
                    if (withinDNSubtree(instance, aSN1Sequence)) {
                        hashSet.add(instance);
                    } else if (withinDNSubtree(aSN1Sequence, instance)) {
                        hashSet.add(aSN1Sequence);
                    }
                }
            } else if (instance != null) {
                hashSet.add(instance);
            }
        }
        return hashSet;
    }

    private Set intersectDNS(Set set, Set set2) {
        HashSet hashSet = new HashSet();
        Iterator it = set2.iterator();
        while (it.hasNext()) {
            String extractNameAsString = extractNameAsString(((GeneralSubtree) it.next()).getBase());
            if (set != null) {
                Iterator it2 = set.iterator();
                while (it2.hasNext()) {
                    String str = (String) it2.next();
                    if (withinDomain(str, extractNameAsString)) {
                        hashSet.add(str);
                    } else if (withinDomain(extractNameAsString, str)) {
                        hashSet.add(extractNameAsString);
                    }
                }
            } else if (extractNameAsString != null) {
                hashSet.add(extractNameAsString);
            }
        }
        return hashSet;
    }

    private Set intersectEmail(Set set, Set set2) {
        HashSet hashSet = new HashSet();
        Iterator it = set2.iterator();
        while (it.hasNext()) {
            String extractNameAsString = extractNameAsString(((GeneralSubtree) it.next()).getBase());
            if (set != null) {
                Iterator it2 = set.iterator();
                while (it2.hasNext()) {
                    intersectEmail(extractNameAsString, (String) it2.next(), hashSet);
                }
            } else if (extractNameAsString != null) {
                hashSet.add(extractNameAsString);
            }
        }
        return hashSet;
    }

    private void intersectEmail(String str, String str2, Set set) {
        if (str.indexOf(64) != -1) {
            String substring = str.substring(str.indexOf(64) + 1);
            if (str2.indexOf(64) != -1) {
                if (!str.equalsIgnoreCase(str2)) {
                    return;
                }
            } else if (str2.startsWith(".")) {
                if (!withinDomain(substring, str2)) {
                    return;
                }
            } else if (!substring.equalsIgnoreCase(str2)) {
                return;
            }
        } else {
            if (str.startsWith(".")) {
                if (str2.indexOf(64) != -1) {
                    if (!withinDomain(str2.substring(str.indexOf(64) + 1), str)) {
                        return;
                    }
                } else if (str2.startsWith(".")) {
                    if (!withinDomain(str, str2) && !str.equalsIgnoreCase(str2)) {
                        if (!withinDomain(str2, str)) {
                            return;
                        }
                    }
                } else if (!withinDomain(str2, str)) {
                    return;
                }
            } else if (str2.indexOf(64) != -1) {
                if (!str2.substring(str2.indexOf(64) + 1).equalsIgnoreCase(str)) {
                    return;
                }
            } else if (str2.startsWith(".")) {
                if (!withinDomain(str, str2)) {
                    return;
                }
            } else if (!str.equalsIgnoreCase(str2)) {
                return;
            }
            set.add(str2);
            return;
        }
        set.add(str);
    }

    private Set intersectIP(Set set, Set set2) {
        HashSet hashSet = new HashSet();
        Iterator it = set2.iterator();
        while (it.hasNext()) {
            byte[] octets = ASN1OctetString.getInstance(((GeneralSubtree) it.next()).getBase().getName()).getOctets();
            if (set != null) {
                Iterator it2 = set.iterator();
                while (it2.hasNext()) {
                    hashSet.addAll(intersectIPRange((byte[]) it2.next(), octets));
                }
            } else if (octets != null) {
                hashSet.add(octets);
            }
        }
        return hashSet;
    }

    private Set intersectIPRange(byte[] bArr, byte[] bArr2) {
        if (bArr.length != bArr2.length) {
            return Collections.EMPTY_SET;
        }
        byte[][] extractIPsAndSubnetMasks = extractIPsAndSubnetMasks(bArr, bArr2);
        byte[] bArr3 = extractIPsAndSubnetMasks[0];
        byte[] bArr4 = extractIPsAndSubnetMasks[1];
        byte[] bArr5 = extractIPsAndSubnetMasks[2];
        byte[] bArr6 = extractIPsAndSubnetMasks[3];
        byte[][] minMaxIPs = minMaxIPs(bArr3, bArr4, bArr5, bArr6);
        return compareTo(max(minMaxIPs[0], minMaxIPs[2]), min(minMaxIPs[1], minMaxIPs[3])) == 1 ? Collections.EMPTY_SET : Collections.singleton(ipWithSubnetMask(m22or(minMaxIPs[0], minMaxIPs[2]), m22or(bArr4, bArr6)));
    }

    private Set intersectOtherName(Set set, Set set2) {
        HashSet hashSet = new HashSet();
        for (Object next : set2) {
            if (set != null) {
                Iterator it = set.iterator();
                while (it.hasNext()) {
                    intersectOtherName(next, (String) it.next(), hashSet);
                }
            } else if (next != null) {
                hashSet.add(next);
            }
        }
        return hashSet;
    }

    private void intersectOtherName(Object obj, Object obj2, Set set) {
        if (obj.equals(obj2)) {
            set.add(obj);
        }
    }

    private Set intersectURI(Set set, Set set2) {
        HashSet hashSet = new HashSet();
        Iterator it = set2.iterator();
        while (it.hasNext()) {
            String extractNameAsString = extractNameAsString(((GeneralSubtree) it.next()).getBase());
            if (set != null) {
                Iterator it2 = set.iterator();
                while (it2.hasNext()) {
                    intersectURI((String) it2.next(), extractNameAsString, hashSet);
                }
            } else if (extractNameAsString != null) {
                hashSet.add(extractNameAsString);
            }
        }
        return hashSet;
    }

    private void intersectURI(String str, String str2, Set set) {
        if (str.indexOf(64) != -1) {
            String substring = str.substring(str.indexOf(64) + 1);
            if (str2.indexOf(64) != -1) {
                if (!str.equalsIgnoreCase(str2)) {
                    return;
                }
            } else if (str2.startsWith(".")) {
                if (!withinDomain(substring, str2)) {
                    return;
                }
            } else if (!substring.equalsIgnoreCase(str2)) {
                return;
            }
        } else {
            if (str.startsWith(".")) {
                if (str2.indexOf(64) != -1) {
                    if (!withinDomain(str2.substring(str.indexOf(64) + 1), str)) {
                        return;
                    }
                } else if (str2.startsWith(".")) {
                    if (!withinDomain(str, str2) && !str.equalsIgnoreCase(str2)) {
                        if (!withinDomain(str2, str)) {
                            return;
                        }
                    }
                } else if (!withinDomain(str2, str)) {
                    return;
                }
            } else if (str2.indexOf(64) != -1) {
                if (!str2.substring(str2.indexOf(64) + 1).equalsIgnoreCase(str)) {
                    return;
                }
            } else if (str2.startsWith(".")) {
                if (!withinDomain(str, str2)) {
                    return;
                }
            } else if (!str.equalsIgnoreCase(str2)) {
                return;
            }
            set.add(str2);
            return;
        }
        set.add(str);
    }

    private byte[] ipWithSubnetMask(byte[] bArr, byte[] bArr2) {
        int length = bArr.length;
        byte[] bArr3 = new byte[(length * 2)];
        System.arraycopy(bArr, 0, bArr3, 0, length);
        System.arraycopy(bArr2, 0, bArr3, length, length);
        return bArr3;
    }

    private boolean isIPConstrained(byte[] bArr, byte[] bArr2) {
        int length = bArr.length;
        if (length != bArr2.length / 2) {
            return false;
        }
        byte[] bArr3 = new byte[length];
        System.arraycopy(bArr2, length, bArr3, 0, length);
        byte[] bArr4 = new byte[length];
        byte[] bArr5 = new byte[length];
        for (int i = 0; i < length; i++) {
            bArr4[i] = (byte) (bArr2[i] & bArr3[i]);
            bArr5[i] = (byte) (bArr[i] & bArr3[i]);
        }
        return Arrays.areEqual(bArr4, bArr5);
    }

    private boolean isUriConstrained(String str, String str2) {
        String extractHostFromURL = extractHostFromURL(str);
        return !str2.startsWith(".") ? extractHostFromURL.equalsIgnoreCase(str2) : withinDomain(extractHostFromURL, str2);
    }

    private static byte[] max(byte[] bArr, byte[] bArr2) {
        for (int i = 0; i < bArr.length; i++) {
            if ((bArr[i] & UByte.MAX_VALUE) > (65535 & bArr2[i])) {
                return bArr;
            }
        }
        return bArr2;
    }

    private static byte[] min(byte[] bArr, byte[] bArr2) {
        for (int i = 0; i < bArr.length; i++) {
            if ((bArr[i] & UByte.MAX_VALUE) < (65535 & bArr2[i])) {
                return bArr;
            }
        }
        return bArr2;
    }

    private byte[][] minMaxIPs(byte[] bArr, byte[] bArr2, byte[] bArr3, byte[] bArr4) {
        int length = bArr.length;
        byte[] bArr5 = new byte[length];
        byte[] bArr6 = new byte[length];
        byte[] bArr7 = new byte[length];
        byte[] bArr8 = new byte[length];
        for (int i = 0; i < length; i++) {
            bArr5[i] = (byte) (bArr[i] & bArr2[i]);
            byte b = bArr[i];
            byte b2 = bArr2[i];
            bArr6[i] = (byte) ((b & b2) | (b2 ^ -1));
            bArr7[i] = (byte) (bArr3[i] & bArr4[i]);
            byte b3 = bArr3[i];
            byte b4 = bArr4[i];
            bArr8[i] = (byte) ((b3 & b4) | (b4 ^ -1));
        }
        return new byte[][]{bArr5, bArr6, bArr7, bArr8};
    }

    /* renamed from: or */
    private static byte[] m22or(byte[] bArr, byte[] bArr2) {
        byte[] bArr3 = new byte[bArr.length];
        for (int i = 0; i < bArr.length; i++) {
            bArr3[i] = (byte) (bArr[i] | bArr2[i]);
        }
        return bArr3;
    }

    private boolean otherNameIsConstrained(OtherName otherName, OtherName otherName2) {
        return otherName2.equals(otherName);
    }

    private String stringifyIP(byte[] bArr) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bArr.length / 2; i++) {
            if (sb.length() > 0) {
                sb.append(".");
            }
            sb.append(Integer.toString(bArr[i] & UByte.MAX_VALUE));
        }
        sb.append("/");
        boolean z = true;
        for (int length = bArr.length / 2; length < bArr.length; length++) {
            if (z) {
                z = false;
            } else {
                sb.append(".");
            }
            sb.append(Integer.toString(bArr[length] & UByte.MAX_VALUE));
        }
        return sb.toString();
    }

    private String stringifyIPCollection(Set set) {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        Iterator it = set.iterator();
        while (it.hasNext()) {
            if (sb.length() > 1) {
                sb.append(",");
            }
            sb.append(stringifyIP((byte[]) it.next()));
        }
        sb.append("]");
        return sb.toString();
    }

    private String stringifyOtherNameCollection(Set set) {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (Object instance : set) {
            if (sb.length() > 1) {
                sb.append(",");
            }
            OtherName instance2 = OtherName.getInstance(instance);
            sb.append(instance2.getTypeID().getId());
            sb.append(":");
            try {
                sb.append(Hex.toHexString(instance2.getValue().toASN1Primitive().getEncoded()));
            } catch (IOException e) {
                sb.append(e.toString());
            }
        }
        sb.append("]");
        return sb.toString();
    }

    private Set unionDN(Set set, ASN1Sequence aSN1Sequence) {
        if (!set.isEmpty()) {
            HashSet hashSet = new HashSet();
            Iterator it = set.iterator();
            while (it.hasNext()) {
                ASN1Sequence aSN1Sequence2 = (ASN1Sequence) it.next();
                if (withinDNSubtree(aSN1Sequence, aSN1Sequence2)) {
                    hashSet.add(aSN1Sequence2);
                } else {
                    if (!withinDNSubtree(aSN1Sequence2, aSN1Sequence)) {
                        hashSet.add(aSN1Sequence2);
                    }
                    hashSet.add(aSN1Sequence);
                }
            }
            return hashSet;
        } else if (aSN1Sequence == null) {
            return set;
        } else {
            set.add(aSN1Sequence);
            return set;
        }
    }

    private Set unionDNS(Set set, String str) {
        if (!set.isEmpty()) {
            HashSet hashSet = new HashSet();
            Iterator it = set.iterator();
            while (it.hasNext()) {
                String str2 = (String) it.next();
                if (!withinDomain(str2, str)) {
                    boolean withinDomain = withinDomain(str, str2);
                    hashSet.add(str2);
                    if (withinDomain) {
                    }
                }
                hashSet.add(str);
            }
            return hashSet;
        } else if (str == null) {
            return set;
        } else {
            set.add(str);
            return set;
        }
    }

    private Set unionEmail(Set set, String str) {
        if (!set.isEmpty()) {
            HashSet hashSet = new HashSet();
            Iterator it = set.iterator();
            while (it.hasNext()) {
                unionEmail((String) it.next(), str, hashSet);
            }
            return hashSet;
        } else if (str == null) {
            return set;
        } else {
            set.add(str);
            return set;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0052, code lost:
        if (withinDomain(r6.substring(r5.indexOf(64) + 1), r5) != false) goto L_0x00a0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x006c, code lost:
        if (withinDomain(r6, r5) != false) goto L_0x00a0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x0073, code lost:
        if (withinDomain(r6, r5) != false) goto L_0x00a0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x008a, code lost:
        if (r6.substring(r5.indexOf(64) + 1).equalsIgnoreCase(r5) != false) goto L_0x00a0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:0x0097, code lost:
        if (withinDomain(r5, r6) != false) goto L_0x00a7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:37:0x009e, code lost:
        if (r5.equalsIgnoreCase(r6) != false) goto L_0x00a0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:5:0x001f, code lost:
        if (r5.equalsIgnoreCase(r6) != false) goto L_0x00a0;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void unionEmail(java.lang.String r5, java.lang.String r6, java.util.Set r7) {
        /*
            r4 = this;
            r0 = 64
            int r1 = r5.indexOf(r0)
            java.lang.String r2 = "."
            r3 = -1
            if (r1 == r3) goto L_0x0038
            int r1 = r5.indexOf(r0)
            int r1 = r1 + 1
            java.lang.String r1 = r5.substring(r1)
            int r0 = r6.indexOf(r0)
            if (r0 == r3) goto L_0x0023
            boolean r0 = r5.equalsIgnoreCase(r6)
            if (r0 == 0) goto L_0x00a4
            goto L_0x00a0
        L_0x0023:
            boolean r0 = r6.startsWith(r2)
            if (r0 == 0) goto L_0x0031
            boolean r0 = r4.withinDomain(r1, r6)
            if (r0 == 0) goto L_0x00a4
            goto L_0x0099
        L_0x0031:
            boolean r0 = r1.equalsIgnoreCase(r6)
            if (r0 == 0) goto L_0x00a4
            goto L_0x0099
        L_0x0038:
            boolean r1 = r5.startsWith(r2)
            if (r1 == 0) goto L_0x0076
            int r1 = r6.indexOf(r0)
            if (r1 == r3) goto L_0x0055
            int r0 = r5.indexOf(r0)
            int r0 = r0 + 1
            java.lang.String r0 = r6.substring(r0)
            boolean r0 = r4.withinDomain(r0, r5)
            if (r0 == 0) goto L_0x00a4
            goto L_0x00a0
        L_0x0055:
            boolean r0 = r6.startsWith(r2)
            if (r0 == 0) goto L_0x006f
            boolean r0 = r4.withinDomain(r5, r6)
            if (r0 != 0) goto L_0x00a7
            boolean r0 = r5.equalsIgnoreCase(r6)
            if (r0 == 0) goto L_0x0068
            goto L_0x00a7
        L_0x0068:
            boolean r0 = r4.withinDomain(r6, r5)
            if (r0 == 0) goto L_0x00a4
            goto L_0x00a0
        L_0x006f:
            boolean r0 = r4.withinDomain(r6, r5)
            if (r0 == 0) goto L_0x00a4
            goto L_0x00a0
        L_0x0076:
            int r1 = r6.indexOf(r0)
            if (r1 == r3) goto L_0x008d
            int r0 = r5.indexOf(r0)
            int r0 = r0 + 1
            java.lang.String r0 = r6.substring(r0)
            boolean r0 = r0.equalsIgnoreCase(r5)
            if (r0 == 0) goto L_0x00a4
            goto L_0x00a0
        L_0x008d:
            boolean r0 = r6.startsWith(r2)
            if (r0 == 0) goto L_0x009a
            boolean r0 = r4.withinDomain(r5, r6)
            if (r0 == 0) goto L_0x00a4
        L_0x0099:
            goto L_0x00a7
        L_0x009a:
            boolean r0 = r5.equalsIgnoreCase(r6)
            if (r0 == 0) goto L_0x00a4
        L_0x00a0:
            r7.add(r5)
            goto L_0x00aa
        L_0x00a4:
            r7.add(r5)
        L_0x00a7:
            r7.add(r6)
        L_0x00aa:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.bouncycastle.asn1.x509.PKIXNameConstraintValidator.unionEmail(java.lang.String, java.lang.String, java.util.Set):void");
    }

    private Set unionIP(Set set, byte[] bArr) {
        if (!set.isEmpty()) {
            HashSet hashSet = new HashSet();
            Iterator it = set.iterator();
            while (it.hasNext()) {
                hashSet.addAll(unionIPRange((byte[]) it.next(), bArr));
            }
            return hashSet;
        } else if (bArr == null) {
            return set;
        } else {
            set.add(bArr);
            return set;
        }
    }

    private Set unionIPRange(byte[] bArr, byte[] bArr2) {
        HashSet hashSet = new HashSet();
        boolean areEqual = Arrays.areEqual(bArr, bArr2);
        hashSet.add(bArr);
        if (!areEqual) {
            hashSet.add(bArr2);
        }
        return hashSet;
    }

    private Set unionOtherName(Set set, OtherName otherName) {
        HashSet hashSet;
        if (set == null) {
            hashSet = new HashSet();
        }
        hashSet.add(otherName);
        return hashSet;
    }

    private Set unionURI(Set set, String str) {
        if (!set.isEmpty()) {
            HashSet hashSet = new HashSet();
            Iterator it = set.iterator();
            while (it.hasNext()) {
                unionURI((String) it.next(), str, hashSet);
            }
            return hashSet;
        } else if (str == null) {
            return set;
        } else {
            set.add(str);
            return set;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0052, code lost:
        if (withinDomain(r6.substring(r5.indexOf(64) + 1), r5) != false) goto L_0x00a0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x006c, code lost:
        if (withinDomain(r6, r5) != false) goto L_0x00a0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x0073, code lost:
        if (withinDomain(r6, r5) != false) goto L_0x00a0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x008a, code lost:
        if (r6.substring(r5.indexOf(64) + 1).equalsIgnoreCase(r5) != false) goto L_0x00a0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:0x0097, code lost:
        if (withinDomain(r5, r6) != false) goto L_0x00a7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:37:0x009e, code lost:
        if (r5.equalsIgnoreCase(r6) != false) goto L_0x00a0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:5:0x001f, code lost:
        if (r5.equalsIgnoreCase(r6) != false) goto L_0x00a0;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void unionURI(java.lang.String r5, java.lang.String r6, java.util.Set r7) {
        /*
            r4 = this;
            r0 = 64
            int r1 = r5.indexOf(r0)
            java.lang.String r2 = "."
            r3 = -1
            if (r1 == r3) goto L_0x0038
            int r1 = r5.indexOf(r0)
            int r1 = r1 + 1
            java.lang.String r1 = r5.substring(r1)
            int r0 = r6.indexOf(r0)
            if (r0 == r3) goto L_0x0023
            boolean r0 = r5.equalsIgnoreCase(r6)
            if (r0 == 0) goto L_0x00a4
            goto L_0x00a0
        L_0x0023:
            boolean r0 = r6.startsWith(r2)
            if (r0 == 0) goto L_0x0031
            boolean r0 = r4.withinDomain(r1, r6)
            if (r0 == 0) goto L_0x00a4
            goto L_0x0099
        L_0x0031:
            boolean r0 = r1.equalsIgnoreCase(r6)
            if (r0 == 0) goto L_0x00a4
            goto L_0x0099
        L_0x0038:
            boolean r1 = r5.startsWith(r2)
            if (r1 == 0) goto L_0x0076
            int r1 = r6.indexOf(r0)
            if (r1 == r3) goto L_0x0055
            int r0 = r5.indexOf(r0)
            int r0 = r0 + 1
            java.lang.String r0 = r6.substring(r0)
            boolean r0 = r4.withinDomain(r0, r5)
            if (r0 == 0) goto L_0x00a4
            goto L_0x00a0
        L_0x0055:
            boolean r0 = r6.startsWith(r2)
            if (r0 == 0) goto L_0x006f
            boolean r0 = r4.withinDomain(r5, r6)
            if (r0 != 0) goto L_0x00a7
            boolean r0 = r5.equalsIgnoreCase(r6)
            if (r0 == 0) goto L_0x0068
            goto L_0x00a7
        L_0x0068:
            boolean r0 = r4.withinDomain(r6, r5)
            if (r0 == 0) goto L_0x00a4
            goto L_0x00a0
        L_0x006f:
            boolean r0 = r4.withinDomain(r6, r5)
            if (r0 == 0) goto L_0x00a4
            goto L_0x00a0
        L_0x0076:
            int r1 = r6.indexOf(r0)
            if (r1 == r3) goto L_0x008d
            int r0 = r5.indexOf(r0)
            int r0 = r0 + 1
            java.lang.String r0 = r6.substring(r0)
            boolean r0 = r0.equalsIgnoreCase(r5)
            if (r0 == 0) goto L_0x00a4
            goto L_0x00a0
        L_0x008d:
            boolean r0 = r6.startsWith(r2)
            if (r0 == 0) goto L_0x009a
            boolean r0 = r4.withinDomain(r5, r6)
            if (r0 == 0) goto L_0x00a4
        L_0x0099:
            goto L_0x00a7
        L_0x009a:
            boolean r0 = r5.equalsIgnoreCase(r6)
            if (r0 == 0) goto L_0x00a4
        L_0x00a0:
            r7.add(r5)
            goto L_0x00aa
        L_0x00a4:
            r7.add(r5)
        L_0x00a7:
            r7.add(r6)
        L_0x00aa:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.bouncycastle.asn1.x509.PKIXNameConstraintValidator.unionURI(java.lang.String, java.lang.String, java.util.Set):void");
    }

    private static boolean withinDNSubtree(ASN1Sequence aSN1Sequence, ASN1Sequence aSN1Sequence2) {
        if (aSN1Sequence2.size() < 1 || aSN1Sequence2.size() > aSN1Sequence.size()) {
            return false;
        }
        RDN instance = RDN.getInstance(aSN1Sequence2.getObjectAt(0));
        int i = 0;
        int i2 = 0;
        while (true) {
            if (i >= aSN1Sequence.size()) {
                i = i2;
                break;
            } else if (RDN.getInstance(aSN1Sequence.getObjectAt(i)).equals(instance)) {
                break;
            } else {
                i2 = i;
                i++;
            }
        }
        if (aSN1Sequence2.size() > aSN1Sequence.size() - i) {
            return false;
        }
        for (int i3 = 0; i3 < aSN1Sequence2.size(); i3++) {
            RDN instance2 = RDN.getInstance(aSN1Sequence2.getObjectAt(i3));
            RDN instance3 = RDN.getInstance(aSN1Sequence.getObjectAt(i + i3));
            if (instance2.size() != instance3.size() || !instance2.getFirst().getType().equals((ASN1Primitive) instance3.getFirst().getType())) {
                return false;
            }
            if (instance2.size() != 1 || !instance2.getFirst().getType().equals((ASN1Primitive) RFC4519Style.serialNumber)) {
                if (!IETFUtils.rDNAreEqual(instance2, instance3)) {
                    return false;
                }
            } else if (!instance3.getFirst().getValue().toString().startsWith(instance2.getFirst().getValue().toString())) {
                return false;
            }
        }
        return true;
    }

    private boolean withinDomain(String str, String str2) {
        if (str2.startsWith(".")) {
            str2 = str2.substring(1);
        }
        String[] split = Strings.split(str2, '.');
        String[] split2 = Strings.split(str, '.');
        if (split2.length <= split.length) {
            return false;
        }
        int length = split2.length - split.length;
        for (int i = -1; i < split.length; i++) {
            if (i == -1) {
                if (split2[i + length].equals("")) {
                    return false;
                }
            } else if (!split[i].equalsIgnoreCase(split2[i + length])) {
                return false;
            }
        }
        return true;
    }

    public void addExcludedSubtree(GeneralSubtree generalSubtree) {
        GeneralName base = generalSubtree.getBase();
        switch (base.getTagNo()) {
            case 0:
                this.excludedSubtreesOtherName = unionOtherName(this.excludedSubtreesOtherName, OtherName.getInstance(base.getName()));
                return;
            case 1:
                this.excludedSubtreesEmail = unionEmail(this.excludedSubtreesEmail, extractNameAsString(base));
                return;
            case 2:
                this.excludedSubtreesDNS = unionDNS(this.excludedSubtreesDNS, extractNameAsString(base));
                return;
            case 4:
                this.excludedSubtreesDN = unionDN(this.excludedSubtreesDN, (ASN1Sequence) base.getName().toASN1Primitive());
                return;
            case 6:
                this.excludedSubtreesURI = unionURI(this.excludedSubtreesURI, extractNameAsString(base));
                return;
            case 7:
                this.excludedSubtreesIP = unionIP(this.excludedSubtreesIP, ASN1OctetString.getInstance(base.getName()).getOctets());
                return;
            default:
                throw new IllegalStateException("Unknown tag encountered: " + base.getTagNo());
        }
    }

    public void checkExcluded(GeneralName generalName) throws NameConstraintValidatorException {
        switch (generalName.getTagNo()) {
            case 0:
                checkExcludedOtherName(this.excludedSubtreesOtherName, OtherName.getInstance(generalName.getName()));
                return;
            case 1:
                checkExcludedEmail(this.excludedSubtreesEmail, extractNameAsString(generalName));
                return;
            case 2:
                checkExcludedDNS(this.excludedSubtreesDNS, DERIA5String.getInstance(generalName.getName()).getString());
                return;
            case 4:
                checkExcludedDN(X500Name.getInstance(generalName.getName()));
                return;
            case 6:
                checkExcludedURI(this.excludedSubtreesURI, DERIA5String.getInstance(generalName.getName()).getString());
                return;
            case 7:
                checkExcludedIP(this.excludedSubtreesIP, ASN1OctetString.getInstance(generalName.getName()).getOctets());
                return;
            default:
                return;
        }
    }

    public void checkExcludedDN(X500Name x500Name) throws NameConstraintValidatorException {
        checkExcludedDN(this.excludedSubtreesDN, ASN1Sequence.getInstance(x500Name));
    }

    public void checkPermitted(GeneralName generalName) throws NameConstraintValidatorException {
        switch (generalName.getTagNo()) {
            case 0:
                checkPermittedOtherName(this.permittedSubtreesOtherName, OtherName.getInstance(generalName.getName()));
                return;
            case 1:
                checkPermittedEmail(this.permittedSubtreesEmail, extractNameAsString(generalName));
                return;
            case 2:
                checkPermittedDNS(this.permittedSubtreesDNS, DERIA5String.getInstance(generalName.getName()).getString());
                return;
            case 4:
                checkPermittedDN(X500Name.getInstance(generalName.getName()));
                return;
            case 6:
                checkPermittedURI(this.permittedSubtreesURI, DERIA5String.getInstance(generalName.getName()).getString());
                return;
            case 7:
                checkPermittedIP(this.permittedSubtreesIP, ASN1OctetString.getInstance(generalName.getName()).getOctets());
                return;
            default:
                return;
        }
    }

    public void checkPermittedDN(X500Name x500Name) throws NameConstraintValidatorException {
        checkPermittedDN(this.permittedSubtreesDN, ASN1Sequence.getInstance(x500Name.toASN1Primitive()));
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof PKIXNameConstraintValidator)) {
            return false;
        }
        PKIXNameConstraintValidator pKIXNameConstraintValidator = (PKIXNameConstraintValidator) obj;
        return collectionsAreEqual(pKIXNameConstraintValidator.excludedSubtreesDN, this.excludedSubtreesDN) && collectionsAreEqual(pKIXNameConstraintValidator.excludedSubtreesDNS, this.excludedSubtreesDNS) && collectionsAreEqual(pKIXNameConstraintValidator.excludedSubtreesEmail, this.excludedSubtreesEmail) && collectionsAreEqual(pKIXNameConstraintValidator.excludedSubtreesIP, this.excludedSubtreesIP) && collectionsAreEqual(pKIXNameConstraintValidator.excludedSubtreesURI, this.excludedSubtreesURI) && collectionsAreEqual(pKIXNameConstraintValidator.excludedSubtreesOtherName, this.excludedSubtreesOtherName) && collectionsAreEqual(pKIXNameConstraintValidator.permittedSubtreesDN, this.permittedSubtreesDN) && collectionsAreEqual(pKIXNameConstraintValidator.permittedSubtreesDNS, this.permittedSubtreesDNS) && collectionsAreEqual(pKIXNameConstraintValidator.permittedSubtreesEmail, this.permittedSubtreesEmail) && collectionsAreEqual(pKIXNameConstraintValidator.permittedSubtreesIP, this.permittedSubtreesIP) && collectionsAreEqual(pKIXNameConstraintValidator.permittedSubtreesURI, this.permittedSubtreesURI) && collectionsAreEqual(pKIXNameConstraintValidator.permittedSubtreesOtherName, this.permittedSubtreesOtherName);
    }

    public int hashCode() {
        return hashCollection(this.excludedSubtreesDN) + hashCollection(this.excludedSubtreesDNS) + hashCollection(this.excludedSubtreesEmail) + hashCollection(this.excludedSubtreesIP) + hashCollection(this.excludedSubtreesURI) + hashCollection(this.excludedSubtreesOtherName) + hashCollection(this.permittedSubtreesDN) + hashCollection(this.permittedSubtreesDNS) + hashCollection(this.permittedSubtreesEmail) + hashCollection(this.permittedSubtreesIP) + hashCollection(this.permittedSubtreesURI) + hashCollection(this.permittedSubtreesOtherName);
    }

    public void intersectEmptyPermittedSubtree(int i) {
        switch (i) {
            case 0:
                this.permittedSubtreesOtherName = new HashSet();
                return;
            case 1:
                this.permittedSubtreesEmail = new HashSet();
                return;
            case 2:
                this.permittedSubtreesDNS = new HashSet();
                return;
            case 4:
                this.permittedSubtreesDN = new HashSet();
                return;
            case 6:
                this.permittedSubtreesURI = new HashSet();
                return;
            case 7:
                this.permittedSubtreesIP = new HashSet();
                return;
            default:
                throw new IllegalStateException("Unknown tag encountered: " + i);
        }
    }

    public void intersectPermittedSubtree(GeneralSubtree generalSubtree) {
        intersectPermittedSubtree(new GeneralSubtree[]{generalSubtree});
    }

    public void intersectPermittedSubtree(GeneralSubtree[] generalSubtreeArr) {
        HashMap hashMap = new HashMap();
        for (int i = 0; i != generalSubtreeArr.length; i++) {
            GeneralSubtree generalSubtree = generalSubtreeArr[i];
            Integer valueOf = Integers.valueOf(generalSubtree.getBase().getTagNo());
            if (hashMap.get(valueOf) == null) {
                hashMap.put(valueOf, new HashSet());
            }
            ((Set) hashMap.get(valueOf)).add(generalSubtree);
        }
        for (Map.Entry entry : hashMap.entrySet()) {
            int intValue = ((Integer) entry.getKey()).intValue();
            switch (intValue) {
                case 0:
                    this.permittedSubtreesOtherName = intersectOtherName(this.permittedSubtreesOtherName, (Set) entry.getValue());
                    break;
                case 1:
                    this.permittedSubtreesEmail = intersectEmail(this.permittedSubtreesEmail, (Set) entry.getValue());
                    break;
                case 2:
                    this.permittedSubtreesDNS = intersectDNS(this.permittedSubtreesDNS, (Set) entry.getValue());
                    break;
                case 4:
                    this.permittedSubtreesDN = intersectDN(this.permittedSubtreesDN, (Set) entry.getValue());
                    break;
                case 6:
                    this.permittedSubtreesURI = intersectURI(this.permittedSubtreesURI, (Set) entry.getValue());
                    break;
                case 7:
                    this.permittedSubtreesIP = intersectIP(this.permittedSubtreesIP, (Set) entry.getValue());
                    break;
                default:
                    throw new IllegalStateException("Unknown tag encountered: " + intValue);
            }
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        addLine(sb, "permitted:");
        if (this.permittedSubtreesDN != null) {
            addLine(sb, "DN:");
            addLine(sb, this.permittedSubtreesDN.toString());
        }
        if (this.permittedSubtreesDNS != null) {
            addLine(sb, "DNS:");
            addLine(sb, this.permittedSubtreesDNS.toString());
        }
        if (this.permittedSubtreesEmail != null) {
            addLine(sb, "Email:");
            addLine(sb, this.permittedSubtreesEmail.toString());
        }
        if (this.permittedSubtreesURI != null) {
            addLine(sb, "URI:");
            addLine(sb, this.permittedSubtreesURI.toString());
        }
        if (this.permittedSubtreesIP != null) {
            addLine(sb, "IP:");
            addLine(sb, stringifyIPCollection(this.permittedSubtreesIP));
        }
        if (this.permittedSubtreesOtherName != null) {
            addLine(sb, "OtherName:");
            addLine(sb, stringifyOtherNameCollection(this.permittedSubtreesOtherName));
        }
        addLine(sb, "excluded:");
        if (!this.excludedSubtreesDN.isEmpty()) {
            addLine(sb, "DN:");
            addLine(sb, this.excludedSubtreesDN.toString());
        }
        if (!this.excludedSubtreesDNS.isEmpty()) {
            addLine(sb, "DNS:");
            addLine(sb, this.excludedSubtreesDNS.toString());
        }
        if (!this.excludedSubtreesEmail.isEmpty()) {
            addLine(sb, "Email:");
            addLine(sb, this.excludedSubtreesEmail.toString());
        }
        if (!this.excludedSubtreesURI.isEmpty()) {
            addLine(sb, "URI:");
            addLine(sb, this.excludedSubtreesURI.toString());
        }
        if (!this.excludedSubtreesIP.isEmpty()) {
            addLine(sb, "IP:");
            addLine(sb, stringifyIPCollection(this.excludedSubtreesIP));
        }
        if (!this.excludedSubtreesOtherName.isEmpty()) {
            addLine(sb, "OtherName:");
            addLine(sb, stringifyOtherNameCollection(this.excludedSubtreesOtherName));
        }
        return sb.toString();
    }
}
