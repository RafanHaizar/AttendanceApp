package com.itextpdf.kernel.xmp.impl;

import com.itextpdf.kernel.xmp.XMPException;
import com.itextpdf.kernel.xmp.options.SerializeOptions;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

public class XMPSerializerHelper {
    public static void serialize(XMPMetaImpl xmp, OutputStream output, SerializeOptions options) throws XMPException {
        SerializeOptions options2 = options != null ? options : new SerializeOptions();
        if (options2.getSort()) {
            xmp.sort();
        }
        new XMPSerializerRDF().serialize(xmp, output, options2);
    }

    public static String serializeToString(XMPMetaImpl xmp, SerializeOptions options) throws XMPException {
        SerializeOptions options2 = options != null ? options : new SerializeOptions();
        options2.setEncodeUTF16BE(true);
        ByteArrayOutputStream output = new ByteArrayOutputStream(2048);
        serialize(xmp, output, options2);
        try {
            return output.toString(options2.getEncoding());
        } catch (UnsupportedEncodingException e) {
            return output.toString();
        }
    }

    public static byte[] serializeToBuffer(XMPMetaImpl xmp, SerializeOptions options) throws XMPException {
        ByteArrayOutputStream out = new ByteArrayOutputStream(2048);
        serialize(xmp, out, options);
        return out.toByteArray();
    }
}
