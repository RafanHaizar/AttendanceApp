package com.itextpdf.kernel.xmp;

import com.itextpdf.kernel.xmp.impl.Base64;
import com.itextpdf.kernel.xmp.impl.ISO8601Converter;
import com.itextpdf.kernel.xmp.impl.XMPUtilsImpl;
import com.itextpdf.kernel.xmp.options.PropertyOptions;
import com.itextpdf.svg.SvgConstants;
import kotlinx.coroutines.DebugKt;

public final class XMPUtils {
    private XMPUtils() {
    }

    public static String catenateArrayItems(XMPMeta xmp, String schemaNS, String arrayName, String separator, String quotes, boolean allowCommas) throws XMPException {
        return XMPUtilsImpl.catenateArrayItems(xmp, schemaNS, arrayName, separator, quotes, allowCommas);
    }

    public static void separateArrayItems(XMPMeta xmp, String schemaNS, String arrayName, String catedStr, PropertyOptions arrayOptions, boolean preserveCommas) throws XMPException {
        XMPUtilsImpl.separateArrayItems(xmp, schemaNS, arrayName, catedStr, arrayOptions, preserveCommas);
    }

    public static void removeProperties(XMPMeta xmp, String schemaNS, String propName, boolean doAllProperties, boolean includeAliases) throws XMPException {
        XMPUtilsImpl.removeProperties(xmp, schemaNS, propName, doAllProperties, includeAliases);
    }

    public static void appendProperties(XMPMeta source, XMPMeta dest, boolean doAllProperties, boolean replaceOldValues) throws XMPException {
        appendProperties(source, dest, doAllProperties, replaceOldValues, false);
    }

    public static void appendProperties(XMPMeta source, XMPMeta dest, boolean doAllProperties, boolean replaceOldValues, boolean deleteEmptyValues) throws XMPException {
        XMPUtilsImpl.appendProperties(source, dest, doAllProperties, replaceOldValues, deleteEmptyValues);
    }

    public static boolean convertToBoolean(String value) throws XMPException {
        if (value == null || value.length() == 0) {
            throw new XMPException("Empty convert-string", 5);
        }
        String value2 = value.toLowerCase();
        try {
            return Integer.parseInt(value2) != 0;
        } catch (NumberFormatException e) {
            if ("true".equals(value2) || SvgConstants.Attributes.PATH_DATA_REL_SHORTHAND_CURVE_TO.equals(value2) || DebugKt.DEBUG_PROPERTY_VALUE_ON.equals(value2) || "yes".equals(value2)) {
                return true;
            }
            return false;
        }
    }

    public static String convertFromBoolean(boolean value) {
        return value ? XMPConst.TRUESTR : XMPConst.FALSESTR;
    }

    public static int convertToInteger(String rawValue) throws XMPException {
        if (rawValue != null) {
            try {
                if (rawValue.length() != 0) {
                    if (rawValue.startsWith("0x")) {
                        return Integer.parseInt(rawValue.substring(2), 16);
                    }
                    return Integer.parseInt(rawValue);
                }
            } catch (NumberFormatException e) {
                throw new XMPException("Invalid integer string", 5);
            }
        }
        throw new XMPException("Empty convert-string", 5);
    }

    public static String convertFromInteger(int value) {
        return String.valueOf(value);
    }

    public static long convertToLong(String rawValue) throws XMPException {
        if (rawValue != null) {
            try {
                if (rawValue.length() != 0) {
                    if (rawValue.startsWith("0x")) {
                        return Long.parseLong(rawValue.substring(2), 16);
                    }
                    return Long.parseLong(rawValue);
                }
            } catch (NumberFormatException e) {
                throw new XMPException("Invalid long string", 5);
            }
        }
        throw new XMPException("Empty convert-string", 5);
    }

    public static String convertFromLong(long value) {
        return String.valueOf(value);
    }

    public static double convertToDouble(String rawValue) throws XMPException {
        if (rawValue != null) {
            try {
                if (rawValue.length() != 0) {
                    return Double.parseDouble(rawValue);
                }
            } catch (NumberFormatException e) {
                throw new XMPException("Invalid double string", 5);
            }
        }
        throw new XMPException("Empty convert-string", 5);
    }

    public static String convertFromDouble(double value) {
        return String.valueOf(value);
    }

    public static XMPDateTime convertToDate(String rawValue) throws XMPException {
        if (rawValue != null && rawValue.length() != 0) {
            return ISO8601Converter.parse(rawValue);
        }
        throw new XMPException("Empty convert-string", 5);
    }

    public static String convertFromDate(XMPDateTime value) {
        return ISO8601Converter.render(value);
    }

    public static String encodeBase64(byte[] buffer) {
        return new String(Base64.encode(buffer));
    }

    public static byte[] decodeBase64(String base64String) throws XMPException {
        try {
            return Base64.decode(base64String.getBytes());
        } catch (Throwable e) {
            throw new XMPException("Invalid base64 string", 5, e);
        }
    }
}
