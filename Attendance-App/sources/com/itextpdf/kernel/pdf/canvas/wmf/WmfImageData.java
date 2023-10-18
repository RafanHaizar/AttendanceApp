package com.itextpdf.kernel.pdf.canvas.wmf;

import com.itextpdf.kernel.PdfException;
import com.itextpdf.p026io.image.ImageData;
import com.itextpdf.p026io.image.ImageType;
import com.itextpdf.p026io.util.UrlUtil;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;

public class WmfImageData extends ImageData {
    private static final byte[] wmf = {-41, -51};

    public WmfImageData(String fileName) throws MalformedURLException {
        this(UrlUtil.toURL(fileName));
    }

    public WmfImageData(URL url) {
        super(url, ImageType.WMF);
        if (!imageTypeIs(readImageType(url), wmf)) {
            throw new PdfException(PdfException.NotAWmfImage);
        }
    }

    public WmfImageData(byte[] bytes) {
        super(bytes, ImageType.WMF);
        if (!imageTypeIs(readImageType(bytes), wmf)) {
            throw new PdfException(PdfException.NotAWmfImage);
        }
    }

    private static boolean imageTypeIs(byte[] imageType, byte[] compareWith) {
        for (int i = 0; i < compareWith.length; i++) {
            if (imageType[i] != compareWith[i]) {
                return false;
            }
        }
        return true;
    }

    private static byte[] readImageType(URL source) {
        InputStream is = null;
        try {
            is = source.openStream();
            byte[] bytes = new byte[8];
            is.read(bytes);
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                }
            }
            return bytes;
        } catch (IOException e2) {
            throw new PdfException("I/O exception.", (Throwable) e2);
        } catch (Throwable th) {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e3) {
                }
            }
            throw th;
        }
    }

    private static byte[] readImageType(byte[] bytes) {
        return Arrays.copyOfRange(bytes, 0, 8);
    }
}
