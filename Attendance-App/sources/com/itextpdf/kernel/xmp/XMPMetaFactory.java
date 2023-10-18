package com.itextpdf.kernel.xmp;

import com.itextpdf.kernel.xmp.impl.XMPMetaImpl;
import com.itextpdf.kernel.xmp.impl.XMPMetaParser;
import com.itextpdf.kernel.xmp.impl.XMPSchemaRegistryImpl;
import com.itextpdf.kernel.xmp.impl.XMPSerializerHelper;
import com.itextpdf.kernel.xmp.options.ParseOptions;
import com.itextpdf.kernel.xmp.options.SerializeOptions;
import java.io.InputStream;
import java.io.OutputStream;

public final class XMPMetaFactory {
    private static XMPSchemaRegistry schema = new XMPSchemaRegistryImpl();
    private static final Object staticLock = new Object();
    private static XMPVersionInfo versionInfo = null;

    private XMPMetaFactory() {
    }

    public static XMPSchemaRegistry getSchemaRegistry() {
        return schema;
    }

    public static XMPMeta create() {
        return new XMPMetaImpl();
    }

    public static XMPMeta parse(InputStream in) throws XMPException {
        return parse(in, (ParseOptions) null);
    }

    public static XMPMeta parse(InputStream in, ParseOptions options) throws XMPException {
        return XMPMetaParser.parse(in, options);
    }

    public static XMPMeta parseFromString(String packet) throws XMPException {
        return parseFromString(packet, (ParseOptions) null);
    }

    public static XMPMeta parseFromString(String packet, ParseOptions options) throws XMPException {
        return XMPMetaParser.parse(packet, options);
    }

    public static XMPMeta parseFromBuffer(byte[] buffer) throws XMPException {
        return parseFromBuffer(buffer, (ParseOptions) null);
    }

    public static XMPMeta parseFromBuffer(byte[] buffer, ParseOptions options) throws XMPException {
        return XMPMetaParser.parse(buffer, options);
    }

    public static void serialize(XMPMeta xmp, OutputStream out) throws XMPException {
        serialize(xmp, out, (SerializeOptions) null);
    }

    public static void serialize(XMPMeta xmp, OutputStream out, SerializeOptions options) throws XMPException {
        assertImplementation(xmp);
        XMPSerializerHelper.serialize((XMPMetaImpl) xmp, out, options);
    }

    public static byte[] serializeToBuffer(XMPMeta xmp, SerializeOptions options) throws XMPException {
        assertImplementation(xmp);
        return XMPSerializerHelper.serializeToBuffer((XMPMetaImpl) xmp, options);
    }

    public static String serializeToString(XMPMeta xmp, SerializeOptions options) throws XMPException {
        assertImplementation(xmp);
        return XMPSerializerHelper.serializeToString((XMPMetaImpl) xmp, options);
    }

    private static void assertImplementation(XMPMeta xmp) {
        if (!(xmp instanceof XMPMetaImpl)) {
            throw new UnsupportedOperationException("The serializing service works onlywith the XMPMeta implementation of this library");
        }
    }

    public static void reset() {
        schema = new XMPSchemaRegistryImpl();
    }

    public static XMPVersionInfo getVersionInfo() {
        XMPVersionInfo xMPVersionInfo;
        synchronized (staticLock) {
            if (versionInfo == null) {
                try {
                    versionInfo = new XMPVersionInfo() {
                        public int getMajor() {
                            return 5;
                        }

                        public int getMinor() {
                            return 1;
                        }

                        public int getMicro() {
                            return 0;
                        }

                        public boolean isDebug() {
                            return false;
                        }

                        public int getBuild() {
                            return 3;
                        }

                        public String getMessage() {
                            return "Adobe XMP Core 5.1.0-jc003";
                        }

                        public String toString() {
                            return "Adobe XMP Core 5.1.0-jc003";
                        }
                    };
                } catch (Throwable e) {
                    System.out.println(e);
                }
            }
            xMPVersionInfo = versionInfo;
        }
        return xMPVersionInfo;
    }
}
