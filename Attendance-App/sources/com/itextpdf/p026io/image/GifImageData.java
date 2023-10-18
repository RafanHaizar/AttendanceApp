package com.itextpdf.p026io.image;

import com.itextpdf.p026io.source.ByteArrayOutputStream;
import com.itextpdf.p026io.util.StreamUtil;
import com.itextpdf.p026io.util.UrlUtil;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/* renamed from: com.itextpdf.io.image.GifImageData */
public class GifImageData {
    private byte[] data;
    private List<ImageData> frames = new ArrayList();
    private float logicalHeight;
    private float logicalWidth;
    private URL url;

    protected GifImageData(URL url2) {
        this.url = url2;
    }

    protected GifImageData(byte[] data2) {
        this.data = data2;
    }

    public float getLogicalHeight() {
        return this.logicalHeight;
    }

    public void setLogicalHeight(float logicalHeight2) {
        this.logicalHeight = logicalHeight2;
    }

    public float getLogicalWidth() {
        return this.logicalWidth;
    }

    public void setLogicalWidth(float logicalWidth2) {
        this.logicalWidth = logicalWidth2;
    }

    public List<ImageData> getFrames() {
        return this.frames;
    }

    /* access modifiers changed from: protected */
    public byte[] getData() {
        return this.data;
    }

    /* access modifiers changed from: protected */
    public URL getUrl() {
        return this.url;
    }

    /* access modifiers changed from: protected */
    public void addFrame(ImageData frame) {
        this.frames.add(frame);
    }

    /* access modifiers changed from: package-private */
    public void loadData() throws IOException {
        InputStream input = null;
        try {
            input = UrlUtil.openStream(this.url);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            StreamUtil.transferBytes(UrlUtil.openStream(this.url), (OutputStream) stream);
            this.data = stream.toByteArray();
        } finally {
            if (input != null) {
                input.close();
            }
        }
    }
}
