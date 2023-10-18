package com.itextpdf.kernel.xmp.impl;

import com.itextpdf.kernel.xmp.XMPConst;
import com.itextpdf.kernel.xmp.XMPException;
import com.itextpdf.kernel.xmp.XMPMeta;

class ParameterAsserts implements XMPConst {
    private ParameterAsserts() {
    }

    public static void assertArrayName(String arrayName) throws XMPException {
        if (arrayName == null || arrayName.length() == 0) {
            throw new XMPException("Empty array name", 4);
        }
    }

    public static void assertPropName(String propName) throws XMPException {
        if (propName == null || propName.length() == 0) {
            throw new XMPException("Empty property name", 4);
        }
    }

    public static void assertSchemaNS(String schemaNS) throws XMPException {
        if (schemaNS == null || schemaNS.length() == 0) {
            throw new XMPException("Empty schema namespace URI", 4);
        }
    }

    public static void assertPrefix(String prefix) throws XMPException {
        if (prefix == null || prefix.length() == 0) {
            throw new XMPException("Empty prefix", 4);
        }
    }

    public static void assertSpecificLang(String specificLang) throws XMPException {
        if (specificLang == null || specificLang.length() == 0) {
            throw new XMPException("Empty specific language", 4);
        }
    }

    public static void assertStructName(String structName) throws XMPException {
        if (structName == null || structName.length() == 0) {
            throw new XMPException("Empty array name", 4);
        }
    }

    public static void assertNotNull(Object param) throws XMPException {
        if (param == null) {
            throw new XMPException("Parameter must not be null", 4);
        } else if ((param instanceof String) && ((String) param).length() == 0) {
            throw new XMPException("Parameter must not be null or empty", 4);
        }
    }

    public static void assertImplementation(XMPMeta xmp) throws XMPException {
        if (xmp == null) {
            throw new XMPException("Parameter must not be null", 4);
        } else if (!(xmp instanceof XMPMetaImpl)) {
            throw new XMPException("The XMPMeta-object is not compatible with this implementation", 4);
        }
    }
}
