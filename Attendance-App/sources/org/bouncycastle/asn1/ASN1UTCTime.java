package org.bouncycastle.asn1;

import com.itextpdf.svg.SvgConstants;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.SimpleTimeZone;
import org.bouncycastle.util.Arrays;
import org.bouncycastle.util.Strings;

public class ASN1UTCTime extends ASN1Primitive {
    private byte[] time;

    public ASN1UTCTime(String str) {
        this.time = Strings.toByteArray(str);
        try {
            getDate();
        } catch (ParseException e) {
            throw new IllegalArgumentException("invalid date string: " + e.getMessage());
        }
    }

    public ASN1UTCTime(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyMMddHHmmss'Z'", DateUtil.EN_Locale);
        simpleDateFormat.setTimeZone(new SimpleTimeZone(0, SvgConstants.Attributes.PATH_DATA_CLOSE_PATH));
        this.time = Strings.toByteArray(simpleDateFormat.format(date));
    }

    public ASN1UTCTime(Date date, Locale locale) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyMMddHHmmss'Z'", locale);
        simpleDateFormat.setTimeZone(new SimpleTimeZone(0, SvgConstants.Attributes.PATH_DATA_CLOSE_PATH));
        this.time = Strings.toByteArray(simpleDateFormat.format(date));
    }

    ASN1UTCTime(byte[] bArr) {
        if (bArr.length >= 2) {
            this.time = bArr;
            if (!isDigit(0) || !isDigit(1)) {
                throw new IllegalArgumentException("illegal characters in UTCTime string");
            }
            return;
        }
        throw new IllegalArgumentException("UTCTime string too short");
    }

    public static ASN1UTCTime getInstance(Object obj) {
        if (obj == null || (obj instanceof ASN1UTCTime)) {
            return (ASN1UTCTime) obj;
        }
        if (obj instanceof byte[]) {
            try {
                return (ASN1UTCTime) fromByteArray((byte[]) obj);
            } catch (Exception e) {
                throw new IllegalArgumentException("encoding error in getInstance: " + e.toString());
            }
        } else {
            throw new IllegalArgumentException("illegal object in getInstance: " + obj.getClass().getName());
        }
    }

    public static ASN1UTCTime getInstance(ASN1TaggedObject aSN1TaggedObject, boolean z) {
        ASN1Primitive object = aSN1TaggedObject.getObject();
        return (z || (object instanceof ASN1UTCTime)) ? getInstance(object) : new ASN1UTCTime(ASN1OctetString.getInstance(object).getOctets());
    }

    /* JADX WARNING: Code restructure failed: missing block: B:2:0x0005, code lost:
        r3 = r0[r3];
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private boolean isDigit(int r3) {
        /*
            r2 = this;
            byte[] r0 = r2.time
            int r1 = r0.length
            if (r1 <= r3) goto L_0x0011
            byte r3 = r0[r3]
            r0 = 48
            if (r3 < r0) goto L_0x0011
            r0 = 57
            if (r3 > r0) goto L_0x0011
            r3 = 1
            goto L_0x0012
        L_0x0011:
            r3 = 0
        L_0x0012:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: org.bouncycastle.asn1.ASN1UTCTime.isDigit(int):boolean");
    }

    /* access modifiers changed from: package-private */
    public boolean asn1Equals(ASN1Primitive aSN1Primitive) {
        if (!(aSN1Primitive instanceof ASN1UTCTime)) {
            return false;
        }
        return Arrays.areEqual(this.time, ((ASN1UTCTime) aSN1Primitive).time);
    }

    /* access modifiers changed from: package-private */
    public void encode(ASN1OutputStream aSN1OutputStream, boolean z) throws IOException {
        aSN1OutputStream.writeEncoded(z, 23, this.time);
    }

    /* access modifiers changed from: package-private */
    public int encodedLength() {
        int length = this.time.length;
        return StreamUtil.calculateBodyLength(length) + 1 + length;
    }

    public Date getAdjustedDate() throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmssz");
        simpleDateFormat.setTimeZone(new SimpleTimeZone(0, SvgConstants.Attributes.PATH_DATA_CLOSE_PATH));
        return DateUtil.epochAdjust(simpleDateFormat.parse(getAdjustedTime()));
    }

    public String getAdjustedTime() {
        StringBuilder sb;
        String str;
        String time2 = getTime();
        if (time2.charAt(0) < '5') {
            sb = new StringBuilder();
            str = "20";
        } else {
            sb = new StringBuilder();
            str = "19";
        }
        return sb.append(str).append(time2).toString();
    }

    public Date getDate() throws ParseException {
        return DateUtil.epochAdjust(new SimpleDateFormat("yyMMddHHmmssz").parse(getTime()));
    }

    public String getTime() {
        StringBuilder append;
        String substring;
        StringBuilder append2;
        StringBuilder append3;
        String str;
        String fromByteArray = Strings.fromByteArray(this.time);
        if (fromByteArray.indexOf(45) >= 0 || fromByteArray.indexOf(43) >= 0) {
            int indexOf = fromByteArray.indexOf(45);
            if (indexOf < 0) {
                indexOf = fromByteArray.indexOf(43);
            }
            if (indexOf == fromByteArray.length() - 3) {
                fromByteArray = fromByteArray + "00";
            }
            if (indexOf == 10) {
                append = new StringBuilder().append(fromByteArray.substring(0, 10)).append("00GMT").append(fromByteArray.substring(10, 13)).append(":");
                substring = fromByteArray.substring(13, 15);
            } else {
                append = new StringBuilder().append(fromByteArray.substring(0, 12)).append("GMT").append(fromByteArray.substring(12, 15)).append(":");
                substring = fromByteArray.substring(15, 17);
            }
            append2 = append.append(substring);
        } else {
            if (fromByteArray.length() == 11) {
                append3 = new StringBuilder().append(fromByteArray.substring(0, 10));
                str = "00GMT+00:00";
            } else {
                append3 = new StringBuilder().append(fromByteArray.substring(0, 12));
                str = "GMT+00:00";
            }
            append2 = append3.append(str);
        }
        return append2.toString();
    }

    public int hashCode() {
        return Arrays.hashCode(this.time);
    }

    /* access modifiers changed from: package-private */
    public boolean isConstructed() {
        return false;
    }

    public String toString() {
        return Strings.fromByteArray(this.time);
    }
}
