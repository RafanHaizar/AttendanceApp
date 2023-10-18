package com.itextpdf.kernel.crypto.securityhandler;

import com.itextpdf.kernel.PdfException;
import com.itextpdf.kernel.crypto.AESCipherCBCnoPad;
import com.itextpdf.kernel.crypto.AesDecryptor;
import com.itextpdf.kernel.crypto.BadPasswordException;
import com.itextpdf.kernel.crypto.IDecryptor;
import com.itextpdf.kernel.crypto.IVGenerator;
import com.itextpdf.kernel.crypto.OutputStreamAesEncryption;
import com.itextpdf.kernel.crypto.OutputStreamEncryption;
import com.itextpdf.kernel.pdf.PdfBoolean;
import com.itextpdf.kernel.pdf.PdfDictionary;
import com.itextpdf.kernel.pdf.PdfLiteral;
import com.itextpdf.kernel.pdf.PdfName;
import com.itextpdf.kernel.pdf.PdfNumber;
import com.itextpdf.kernel.pdf.PdfVersion;
import com.itextpdf.p026io.LogMessageConstant;
import com.itextpdf.p026io.util.StreamUtil;
import java.io.OutputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import kotlin.UByte;
import org.slf4j.LoggerFactory;

public class StandardHandlerUsingAes256 extends StandardSecurityHandler {
    private static final int KEY_SALT_OFFSET = 40;
    private static final int SALT_LENGTH = 8;
    private static final int VALIDATION_SALT_OFFSET = 32;
    private static final long serialVersionUID = -8365943606887257386L;
    protected boolean encryptMetadata;
    private boolean isPdf2;

    public StandardHandlerUsingAes256(PdfDictionary encryptionDictionary, byte[] userPassword, byte[] ownerPassword, int permissions, boolean encryptMetadata2, boolean embeddedFilesOnly, PdfVersion version) {
        this.isPdf2 = version != null && version.compareTo(PdfVersion.PDF_2_0) >= 0;
        initKeyAndFillDictionary(encryptionDictionary, userPassword, ownerPassword, permissions, encryptMetadata2, embeddedFilesOnly);
    }

    public StandardHandlerUsingAes256(PdfDictionary encryptionDictionary, byte[] password) {
        initKeyAndReadDictionary(encryptionDictionary, password);
    }

    public boolean isEncryptMetadata() {
        return this.encryptMetadata;
    }

    public void setHashKeyForNextObject(int objNumber, int objGeneration) {
    }

    public OutputStreamEncryption getEncryptionStream(OutputStream os) {
        return new OutputStreamAesEncryption(os, this.nextObjectKey, 0, this.nextObjectKeySize);
    }

    public IDecryptor getDecryptor() {
        return new AesDecryptor(this.nextObjectKey, 0, this.nextObjectKeySize);
    }

    private void initKeyAndFillDictionary(PdfDictionary encryptionDictionary, byte[] userPassword, byte[] ownerPassword, int permissions, boolean encryptMetadata2, boolean embeddedFilesOnly) {
        byte[] userPassword2;
        byte[] ownerPassword2;
        byte[] userValAndKeySalt;
        byte[] ueKey;
        byte[] userKey;
        byte[] ownerKey;
        byte[] oeKey;
        byte[] aes256Perms;
        byte[] userPassword3 = userPassword;
        boolean z = encryptMetadata2;
        byte[] ownerPassword3 = generateOwnerPasswordIfNullOrEmpty(ownerPassword);
        int permissions2 = (permissions | -3904) & -4;
        if (userPassword3 == null) {
            try {
                userPassword2 = new byte[0];
            } catch (Exception e) {
                ex = e;
                PdfDictionary pdfDictionary = encryptionDictionary;
                byte[] bArr = userPassword3;
                int i = permissions2;
                throw new PdfException(PdfException.PdfEncryption, (Throwable) ex);
            }
        } else {
            try {
                if (userPassword3.length > 127) {
                    userPassword2 = Arrays.copyOf(userPassword3, 127);
                } else {
                    userPassword2 = userPassword3;
                }
            } catch (Exception e2) {
                ex = e2;
                PdfDictionary pdfDictionary2 = encryptionDictionary;
                int i2 = permissions2;
                byte[] bArr2 = userPassword3;
                throw new PdfException(PdfException.PdfEncryption, (Throwable) ex);
            }
        }
        try {
            if (ownerPassword3.length > 127) {
                try {
                    ownerPassword2 = Arrays.copyOf(ownerPassword3, 127);
                } catch (Exception e3) {
                    ex = e3;
                    PdfDictionary pdfDictionary3 = encryptionDictionary;
                    byte[] bArr3 = userPassword2;
                    int i3 = permissions2;
                    throw new PdfException(PdfException.PdfEncryption, (Throwable) ex);
                }
            } else {
                ownerPassword2 = ownerPassword3;
            }
            try {
                userValAndKeySalt = IVGenerator.getIV(16);
                byte[] ownerValAndKeySalt = IVGenerator.getIV(16);
                this.nextObjectKey = IVGenerator.getIV(32);
                this.nextObjectKeySize = 32;
                byte[] userKey2 = Arrays.copyOf(computeHash(userPassword2, userValAndKeySalt, 0, 8), 48);
                System.arraycopy(userValAndKeySalt, 0, userKey2, 32, 16);
                byte[] hash = computeHash(userPassword2, userValAndKeySalt, 8, 8);
                AESCipherCBCnoPad ac = new AESCipherCBCnoPad(true, hash);
                ueKey = ac.processBlock(this.nextObjectKey, 0, this.nextObjectKey.length);
                AESCipherCBCnoPad aESCipherCBCnoPad = ac;
                byte[] bArr4 = hash;
                userKey = userKey2;
                byte[] hash2 = computeHash(ownerPassword2, ownerValAndKeySalt, 0, 8, userKey);
                ownerKey = Arrays.copyOf(hash2, 48);
                System.arraycopy(ownerValAndKeySalt, 0, ownerKey, 32, 16);
                byte[] bArr5 = hash2;
                oeKey = new AESCipherCBCnoPad(true, computeHash(ownerPassword2, ownerValAndKeySalt, 8, 8, userKey)).processBlock(this.nextObjectKey, 0, this.nextObjectKey.length);
                byte[] permsp = IVGenerator.getIV(16);
                permsp[0] = (byte) permissions2;
                permsp[1] = (byte) (permissions2 >> 8);
                permsp[2] = (byte) (permissions2 >> 16);
                permsp[3] = (byte) (permissions2 >> 24);
                permsp[4] = -1;
                permsp[5] = -1;
                permsp[6] = -1;
                permsp[7] = -1;
                permsp[8] = z ? (byte) 84 : 70;
                permsp[9] = 97;
                permsp[10] = 100;
                permsp[11] = 98;
                byte[] bArr6 = ownerValAndKeySalt;
                aes256Perms = new AESCipherCBCnoPad(true, this.nextObjectKey).processBlock(permsp, 0, permsp.length);
                this.permissions = (long) permissions2;
                this.encryptMetadata = z;
            } catch (Exception e4) {
                ex = e4;
                PdfDictionary pdfDictionary4 = encryptionDictionary;
                byte[] bArr7 = userPassword2;
                int i4 = permissions2;
                byte[] bArr8 = ownerPassword2;
                throw new PdfException(PdfException.PdfEncryption, (Throwable) ex);
            }
            try {
                setStandardHandlerDicEntries(encryptionDictionary, userKey, ownerKey);
                byte[] bArr9 = userValAndKeySalt;
                byte[] bArr10 = userPassword2;
                int i5 = permissions2;
                try {
                    setAES256DicEntries(encryptionDictionary, oeKey, ueKey, aes256Perms, encryptMetadata2, embeddedFilesOnly);
                } catch (Exception e5) {
                    ex = e5;
                    byte[] bArr11 = ownerPassword2;
                }
            } catch (Exception e6) {
                ex = e6;
                byte[] bArr12 = userPassword2;
                int i6 = permissions2;
                byte[] bArr13 = ownerPassword2;
                throw new PdfException(PdfException.PdfEncryption, (Throwable) ex);
            }
        } catch (Exception e7) {
            ex = e7;
            PdfDictionary pdfDictionary5 = encryptionDictionary;
            byte[] bArr14 = userPassword2;
            int i7 = permissions2;
            throw new PdfException(PdfException.PdfEncryption, (Throwable) ex);
        }
    }

    private void setAES256DicEntries(PdfDictionary encryptionDictionary, byte[] oeKey, byte[] ueKey, byte[] aes256Perms, boolean encryptMetadata2, boolean embeddedFilesOnly) {
        encryptionDictionary.put(PdfName.f1363OE, new PdfLiteral(StreamUtil.createEscapedString(oeKey)));
        encryptionDictionary.put(PdfName.f1404UE, new PdfLiteral(StreamUtil.createEscapedString(ueKey)));
        encryptionDictionary.put(PdfName.Perms, new PdfLiteral(StreamUtil.createEscapedString(aes256Perms)));
        encryptionDictionary.put(PdfName.f1376R, new PdfNumber(this.isPdf2 ? 6 : 5));
        encryptionDictionary.put(PdfName.f1406V, new PdfNumber(5));
        PdfDictionary stdcf = new PdfDictionary();
        stdcf.put(PdfName.Length, new PdfNumber(32));
        if (!encryptMetadata2) {
            encryptionDictionary.put(PdfName.EncryptMetadata, PdfBoolean.FALSE);
        }
        if (embeddedFilesOnly) {
            stdcf.put(PdfName.AuthEvent, PdfName.EFOpen);
            encryptionDictionary.put(PdfName.EFF, PdfName.StdCF);
            encryptionDictionary.put(PdfName.StrF, PdfName.Identity);
            encryptionDictionary.put(PdfName.StmF, PdfName.Identity);
        } else {
            stdcf.put(PdfName.AuthEvent, PdfName.DocOpen);
            encryptionDictionary.put(PdfName.StrF, PdfName.StdCF);
            encryptionDictionary.put(PdfName.StmF, PdfName.StdCF);
        }
        stdcf.put(PdfName.CFM, PdfName.AESV3);
        PdfDictionary cf = new PdfDictionary();
        cf.put(PdfName.StdCF, stdcf);
        encryptionDictionary.put(PdfName.f1304CF, cf);
    }

    private void initKeyAndReadDictionary(PdfDictionary encryptionDictionary, byte[] password) {
        byte[] password2;
        String str;
        byte b;
        byte[] hash;
        int i;
        PdfDictionary pdfDictionary = encryptionDictionary;
        byte[] password3 = password;
        boolean encryptMetadata2 = false;
        if (password3 == null) {
            try {
                password2 = new byte[0];
            } catch (BadPasswordException e) {
                ex = e;
                byte[] bArr = password3;
                throw ex;
            } catch (Exception e2) {
                ex = e2;
                byte[] bArr2 = password3;
                throw new PdfException(PdfException.PdfEncryption, (Throwable) ex);
            }
        } else if (password3.length > 127) {
            password2 = Arrays.copyOf(password3, 127);
        } else {
            password2 = password3;
        }
        try {
            this.isPdf2 = pdfDictionary.getAsNumber(PdfName.f1376R).getValue() == 6.0d;
            byte[] oValue = getIsoBytes(pdfDictionary.getAsString(PdfName.f1361O));
            byte[] uValue = getIsoBytes(pdfDictionary.getAsString(PdfName.f1403U));
            byte[] oeValue = getIsoBytes(pdfDictionary.getAsString(PdfName.f1363OE));
            byte[] ueValue = getIsoBytes(pdfDictionary.getAsString(PdfName.f1404UE));
            byte[] perms = getIsoBytes(pdfDictionary.getAsString(PdfName.Perms));
            this.permissions = ((PdfNumber) pdfDictionary.get(PdfName.f1367P)).longValue();
            byte[] hash2 = computeHash(password2, oValue, 32, 8, uValue);
            this.usedOwnerPassword = compareArray(hash2, oValue, 32);
            if (this.usedOwnerPassword) {
                byte[] bArr3 = password2;
                b = 8;
                byte[] bArr4 = oValue;
                str = PdfException.BadUserPassword;
                byte[] bArr5 = hash2;
                try {
                    byte[] hash3 = computeHash(bArr3, bArr4, 40, 8, uValue);
                    this.nextObjectKey = new AESCipherCBCnoPad(false, hash3).processBlock(oeValue, 0, oeValue.length);
                    hash = hash3;
                    i = 32;
                } catch (BadPasswordException e3) {
                    ex = e3;
                    byte[] bArr6 = password2;
                    throw ex;
                } catch (Exception e4) {
                    ex = e4;
                    byte[] bArr7 = password2;
                    throw new PdfException(PdfException.PdfEncryption, (Throwable) ex);
                }
            } else {
                str = PdfException.BadUserPassword;
                byte[] bArr8 = hash2;
                b = 8;
                i = 32;
                if (compareArray(computeHash(password2, uValue, 32, 8), uValue, 32)) {
                    hash = computeHash(password2, uValue, 40, 8);
                    this.nextObjectKey = new AESCipherCBCnoPad(false, hash).processBlock(ueValue, 0, ueValue.length);
                } else {
                    byte[] bArr9 = password2;
                    throw new BadPasswordException(str);
                }
            }
            this.nextObjectKeySize = i;
            AESCipherCBCnoPad ac = new AESCipherCBCnoPad(false, this.nextObjectKey);
            byte[] decPerms = ac.processBlock(perms, 0, perms.length);
            if (decPerms[9] == 97 && decPerms[10] == 100 && decPerms[11] == 98) {
                int permissionsDecoded = (decPerms[0] & 255) | ((decPerms[1] & UByte.MAX_VALUE) << b) | ((decPerms[2] & UByte.MAX_VALUE) << Tnaf.POW_2_WIDTH) | ((decPerms[3] & UByte.MAX_VALUE) << 24);
                if (decPerms[b] == 84) {
                    encryptMetadata2 = true;
                }
                Boolean encryptMetadataEntry = pdfDictionary.getAsBool(PdfName.EncryptMetadata);
                AESCipherCBCnoPad aESCipherCBCnoPad = ac;
                byte[] bArr10 = hash;
                byte[] bArr11 = password2;
                try {
                    if (!(((long) permissionsDecoded) == this.permissions && (encryptMetadataEntry == null || encryptMetadata2 == encryptMetadataEntry.booleanValue()))) {
                        LoggerFactory.getLogger((Class<?>) StandardHandlerUsingAes256.class).error(LogMessageConstant.f1189x3e742566);
                    }
                    this.permissions = (long) permissionsDecoded;
                    this.encryptMetadata = encryptMetadata2;
                } catch (BadPasswordException e5) {
                    ex = e5;
                    throw ex;
                } catch (Exception e6) {
                    ex = e6;
                    throw new PdfException(PdfException.PdfEncryption, (Throwable) ex);
                }
            } else {
                AESCipherCBCnoPad aESCipherCBCnoPad2 = ac;
                byte[] bArr12 = hash;
                byte[] bArr13 = password2;
                throw new BadPasswordException(str);
            }
        } catch (BadPasswordException e7) {
            ex = e7;
            byte[] bArr14 = password2;
            throw ex;
        } catch (Exception e8) {
            ex = e8;
            byte[] bArr15 = password2;
            throw new PdfException(PdfException.PdfEncryption, (Throwable) ex);
        }
    }

    private byte[] computeHash(byte[] password, byte[] salt, int saltOffset, int saltLen) throws NoSuchAlgorithmException {
        return computeHash(password, salt, saltOffset, saltLen, (byte[]) null);
    }

    private byte[] computeHash(byte[] password, byte[] salt, int saltOffset, int saltLen, byte[] userKey) throws NoSuchAlgorithmException {
        MessageDigest md;
        byte[] bArr = password;
        byte[] bArr2 = userKey;
        MessageDigest mdSha256 = MessageDigest.getInstance("SHA-256");
        mdSha256.update(bArr);
        mdSha256.update(salt, saltOffset, saltLen);
        if (bArr2 != null) {
            mdSha256.update(bArr2);
        }
        byte[] k = mdSha256.digest();
        if (!this.isPdf2) {
            return k;
        }
        MessageDigest mdSha384 = MessageDigest.getInstance("SHA-384");
        MessageDigest mdSha512 = MessageDigest.getInstance("SHA-512");
        int i = 0;
        int userKeyLen = bArr2 != null ? bArr2.length : 0;
        int passAndUserKeyLen = bArr.length + userKeyLen;
        int roundNum = 0;
        while (true) {
            int k1RepLen = k.length + passAndUserKeyLen;
            byte[] k1 = new byte[(k1RepLen * 64)];
            System.arraycopy(bArr, i, k1, i, bArr.length);
            System.arraycopy(k, i, k1, bArr.length, k.length);
            if (bArr2 != null) {
                System.arraycopy(bArr2, i, k1, bArr.length + k.length, userKeyLen);
            }
            for (int i2 = 1; i2 < 64; i2++) {
                System.arraycopy(k1, i, k1, k1RepLen * i2, k1RepLen);
            }
            AESCipherCBCnoPad cipher = new AESCipherCBCnoPad(true, Arrays.copyOf(k, 16), Arrays.copyOfRange(k, 16, 32));
            byte[] e = cipher.processBlock(k1, 0, k1.length);
            switch (new BigInteger(1, Arrays.copyOf(e, 16)).remainder(BigInteger.valueOf(3)).intValue()) {
                case 0:
                    md = mdSha256;
                    break;
                case 1:
                    md = mdSha384;
                    break;
                case 2:
                    md = mdSha512;
                    break;
                default:
                    md = null;
                    break;
            }
            k = md.digest(e);
            roundNum++;
            MessageDigest messageDigest = md;
            if (roundNum > 63) {
                AESCipherCBCnoPad aESCipherCBCnoPad = cipher;
                if ((e[e.length - 1] & 255) <= roundNum - 32) {
                    return k.length == 32 ? k : Arrays.copyOf(k, 32);
                }
            } else {
                AESCipherCBCnoPad aESCipherCBCnoPad2 = cipher;
            }
            bArr = password;
            byte[] bArr3 = salt;
            int i3 = saltOffset;
            bArr2 = userKey;
            i = 0;
        }
    }

    private static boolean compareArray(byte[] a, byte[] b, int len) {
        for (int k = 0; k < len; k++) {
            if (a[k] != b[k]) {
                return false;
            }
        }
        return true;
    }
}
