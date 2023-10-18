package com.itextpdf.p026io.font;

import com.itextpdf.p026io.font.constants.FontResources;
import com.itextpdf.p026io.font.constants.StandardFonts;
import com.itextpdf.p026io.source.RandomAccessFileOrArray;
import com.itextpdf.p026io.source.RandomAccessSourceFactory;
import com.itextpdf.p026io.util.ResourceUtil;
import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

/* renamed from: com.itextpdf.io.font.Type1Parser */
class Type1Parser implements Serializable {
    private static final String AFM_HEADER = "StartFontMetrics";
    private static final long serialVersionUID = -8484541242371901414L;
    private byte[] afmData;
    private String afmPath;
    private boolean isBuiltInFont;
    private byte[] pfbData;
    private String pfbPath;
    private RandomAccessSourceFactory sourceFactory = new RandomAccessSourceFactory();

    public Type1Parser(String metricsPath, String binaryPath, byte[] afm, byte[] pfb) {
        this.afmData = afm;
        this.pfbData = pfb;
        this.afmPath = metricsPath;
        this.pfbPath = binaryPath;
    }

    public RandomAccessFileOrArray getMetricsFile() throws IOException {
        this.isBuiltInFont = false;
        if (StandardFonts.isStandardFont(this.afmPath)) {
            this.isBuiltInFont = true;
            byte[] buf = new byte[1024];
            InputStream resource = null;
            try {
                String resourcePath = FontResources.AFMS + this.afmPath + ".afm";
                resource = ResourceUtil.getResourceStream(resourcePath);
                if (resource != null) {
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    while (true) {
                        int read = resource.read(buf);
                        int read2 = read;
                        if (read < 0) {
                            break;
                        }
                        stream.write(buf, 0, read2);
                    }
                    return new RandomAccessFileOrArray(this.sourceFactory.createSource(stream.toByteArray()));
                }
                throw new com.itextpdf.p026io.IOException("{0} was not found as resource.").setMessageParams(resourcePath);
            } finally {
                if (resource != null) {
                    try {
                        resource.close();
                    } catch (Exception e) {
                    }
                }
            }
        } else {
            String str = this.afmPath;
            if (str != null) {
                if (str.toLowerCase().endsWith(".afm")) {
                    return new RandomAccessFileOrArray(this.sourceFactory.createBestSource(this.afmPath));
                }
                if (this.afmPath.toLowerCase().endsWith(".pfm")) {
                    ByteArrayOutputStream ba = new ByteArrayOutputStream();
                    RandomAccessFileOrArray rf = new RandomAccessFileOrArray(this.sourceFactory.createBestSource(this.afmPath));
                    Pfm2afm.convert(rf, ba);
                    rf.close();
                    return new RandomAccessFileOrArray(this.sourceFactory.createSource(ba.toByteArray()));
                }
                throw new com.itextpdf.p026io.IOException(com.itextpdf.p026io.IOException._1IsNotAnAfmOrPfmFontFile).setMessageParams(this.afmPath);
            } else if (this.afmData != null) {
                RandomAccessFileOrArray rf2 = new RandomAccessFileOrArray(this.sourceFactory.createSource(this.afmData));
                if (isAfmFile(rf2)) {
                    return rf2;
                }
                ByteArrayOutputStream ba2 = new ByteArrayOutputStream();
                try {
                    Pfm2afm.convert(rf2, ba2);
                    rf2.close();
                    return new RandomAccessFileOrArray(this.sourceFactory.createSource(ba2.toByteArray()));
                } catch (Exception e2) {
                    throw new com.itextpdf.p026io.IOException("Invalid afm or pfm font file.");
                } catch (Throwable th) {
                    rf2.close();
                    throw th;
                }
            } else {
                throw new com.itextpdf.p026io.IOException("Invalid afm or pfm font file.");
            }
        }
    }

    public RandomAccessFileOrArray getPostscriptBinary() throws IOException {
        if (this.pfbData != null) {
            return new RandomAccessFileOrArray(this.sourceFactory.createSource(this.pfbData));
        }
        String str = this.pfbPath;
        if (str != null && str.toLowerCase().endsWith(".pfb")) {
            return new RandomAccessFileOrArray(this.sourceFactory.createBestSource(this.pfbPath));
        }
        StringBuilder sb = new StringBuilder();
        String str2 = this.afmPath;
        this.pfbPath = sb.append(str2.substring(0, str2.length() - 3)).append("pfb").toString();
        return new RandomAccessFileOrArray(this.sourceFactory.createBestSource(this.pfbPath));
    }

    public boolean isBuiltInFont() {
        return this.isBuiltInFont;
    }

    public String getAfmPath() {
        return this.afmPath;
    }

    private boolean isAfmFile(RandomAccessFileOrArray raf) throws IOException {
        StringBuilder builder = new StringBuilder(AFM_HEADER.length());
        int i = 0;
        while (i < AFM_HEADER.length()) {
            try {
                builder.append((char) raf.readByte());
                i++;
            } catch (EOFException e) {
                raf.seek(0);
                return false;
            }
        }
        raf.seek(0);
        return AFM_HEADER.equals(builder.toString());
    }
}
