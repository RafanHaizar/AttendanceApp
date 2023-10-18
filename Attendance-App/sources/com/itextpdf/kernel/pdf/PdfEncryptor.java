package com.itextpdf.kernel.pdf;

import com.itextpdf.kernel.counter.event.IMetaInfo;
import java.io.OutputStream;
import java.security.PrivateKey;
import java.util.Map;
import org.bouncycastle.cms.CMSException;
import org.bouncycastle.cms.RecipientInformation;
import org.bouncycastle.cms.jcajce.JceKeyTransEnvelopedRecipient;

public final class PdfEncryptor {
    private IMetaInfo metaInfo;
    private EncryptionProperties properties;

    public static void encrypt(PdfReader reader, OutputStream os, EncryptionProperties properties2, Map<String, String> newInfo) {
        new PdfEncryptor().setEncryptionProperties(properties2).encrypt(reader, os, newInfo);
    }

    public static void encrypt(PdfReader reader, OutputStream os, EncryptionProperties properties2) {
        encrypt(reader, os, properties2, (Map<String, String>) null);
    }

    public static String getPermissionsVerbose(int permissions) {
        StringBuilder buf = new StringBuilder("Allowed:");
        if ((permissions & EncryptionConstants.ALLOW_PRINTING) == 2052) {
            buf.append(" Printing");
        }
        if ((permissions & 8) == 8) {
            buf.append(" Modify contents");
        }
        if ((permissions & 16) == 16) {
            buf.append(" Copy");
        }
        if ((permissions & 32) == 32) {
            buf.append(" Modify annotations");
        }
        if ((permissions & 256) == 256) {
            buf.append(" Fill in");
        }
        if ((permissions & 512) == 512) {
            buf.append(" Screen readers");
        }
        if ((permissions & 1024) == 1024) {
            buf.append(" Assembly");
        }
        if ((permissions & 4) == 4) {
            buf.append(" Degraded printing");
        }
        return buf.toString();
    }

    public static boolean isPrintingAllowed(int permissions) {
        return (permissions & EncryptionConstants.ALLOW_PRINTING) == 2052;
    }

    public static boolean isModifyContentsAllowed(int permissions) {
        return (permissions & 8) == 8;
    }

    public static boolean isCopyAllowed(int permissions) {
        return (permissions & 16) == 16;
    }

    public static boolean isModifyAnnotationsAllowed(int permissions) {
        return (permissions & 32) == 32;
    }

    public static boolean isFillInAllowed(int permissions) {
        return (permissions & 256) == 256;
    }

    public static boolean isScreenReadersAllowed(int permissions) {
        return (permissions & 512) == 512;
    }

    public static boolean isAssemblyAllowed(int permissions) {
        return (permissions & 1024) == 1024;
    }

    public static boolean isDegradedPrintingAllowed(int permissions) {
        return (permissions & 4) == 4;
    }

    public static byte[] getContent(RecipientInformation recipientInfo, PrivateKey certificateKey, String certificateKeyProvider) throws CMSException {
        return recipientInfo.getContent(new JceKeyTransEnvelopedRecipient(certificateKey).setProvider(certificateKeyProvider));
    }

    public PdfEncryptor setEventCountingMetaInfo(IMetaInfo metaInfo2) {
        this.metaInfo = metaInfo2;
        return this;
    }

    public PdfEncryptor setEncryptionProperties(EncryptionProperties properties2) {
        this.properties = properties2;
        return this;
    }

    public void encrypt(PdfReader reader, OutputStream os, Map<String, String> newInfo) {
        WriterProperties writerProperties = new WriterProperties();
        writerProperties.encryptionProperties = this.properties;
        PdfWriter writer = new PdfWriter(os, writerProperties);
        StampingProperties stampingProperties = new StampingProperties();
        stampingProperties.setEventCountingMetaInfo(this.metaInfo);
        PdfDocument document = new PdfDocument(reader, writer, stampingProperties);
        document.getDocumentInfo().setMoreInfo(newInfo);
        document.close();
    }

    public void encrypt(PdfReader reader, OutputStream os) {
        Map map = null;
        encrypt(reader, os, (Map<String, String>) null);
    }
}
