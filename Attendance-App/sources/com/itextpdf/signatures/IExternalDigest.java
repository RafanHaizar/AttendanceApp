package com.itextpdf.signatures;

import java.security.GeneralSecurityException;
import java.security.MessageDigest;

public interface IExternalDigest {
    MessageDigest getMessageDigest(String str) throws GeneralSecurityException;
}
