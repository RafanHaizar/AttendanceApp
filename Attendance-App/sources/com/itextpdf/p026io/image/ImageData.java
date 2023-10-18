package com.itextpdf.p026io.image;

import com.itextpdf.p026io.IOException;
import com.itextpdf.p026io.LogMessageConstant;
import com.itextpdf.p026io.colors.IccProfile;
import com.itextpdf.p026io.source.ByteArrayOutputStream;
import com.itextpdf.p026io.source.RandomAccessFileOrArray;
import com.itextpdf.p026io.source.RandomAccessSourceFactory;
import com.itextpdf.p026io.util.StreamUtil;
import java.io.OutputStream;
import java.net.URL;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* renamed from: com.itextpdf.io.image.ImageData */
public abstract class ImageData {
    private static long serialId = 0;
    private static final Object staticLock = new Object();
    protected float XYRatio = 0.0f;
    protected int bpc = 1;
    protected int colorSpace = -1;
    protected int colorTransform = 1;
    protected byte[] data;
    protected float[] decode;
    protected Map<String, Object> decodeParms;
    protected boolean deflated;
    protected int dpiX = 0;
    protected int dpiY = 0;
    protected String filter;
    protected float height;
    protected Map<String, Object> imageAttributes;
    protected ImageData imageMask;
    protected int imageSize;
    protected boolean interpolation;
    protected boolean inverted = false;
    protected boolean mask = false;
    protected Long mySerialId = getSerialId();
    protected ImageType originalType;
    protected IccProfile profile;
    protected float rotation;
    protected int[] transparency;
    protected URL url;
    protected float width;

    protected ImageData(URL url2, ImageType type) {
        this.url = url2;
        this.originalType = type;
    }

    protected ImageData(byte[] bytes, ImageType type) {
        this.data = bytes;
        this.originalType = type;
    }

    public boolean isRawImage() {
        return false;
    }

    public URL getUrl() {
        return this.url;
    }

    public void setUrl(URL url2) {
        this.url = url2;
    }

    public int[] getTransparency() {
        return this.transparency;
    }

    public void setTransparency(int[] transparency2) {
        this.transparency = transparency2;
    }

    public boolean isInverted() {
        return this.inverted;
    }

    public void setInverted(boolean inverted2) {
        this.inverted = inverted2;
    }

    public float getRotation() {
        return this.rotation;
    }

    public void setRotation(float rotation2) {
        this.rotation = rotation2;
    }

    public IccProfile getProfile() {
        return this.profile;
    }

    public void setProfile(IccProfile profile2) {
        this.profile = profile2;
    }

    public int getDpiX() {
        return this.dpiX;
    }

    public int getDpiY() {
        return this.dpiY;
    }

    public void setDpi(int dpiX2, int dpiY2) {
        this.dpiX = dpiX2;
        this.dpiY = dpiY2;
    }

    public int getColorTransform() {
        return this.colorTransform;
    }

    public void setColorTransform(int colorTransform2) {
        this.colorTransform = colorTransform2;
    }

    public boolean isDeflated() {
        return this.deflated;
    }

    public void setDeflated(boolean deflated2) {
        this.deflated = deflated2;
    }

    public ImageType getOriginalType() {
        return this.originalType;
    }

    public int getColorSpace() {
        return this.colorSpace;
    }

    public void setColorSpace(int colorSpace2) {
        this.colorSpace = colorSpace2;
    }

    public byte[] getData() {
        return this.data;
    }

    public boolean canBeMask() {
        if ((!isRawImage() || this.bpc <= 255) && this.colorSpace != 1) {
            return false;
        }
        return true;
    }

    public boolean isMask() {
        return this.mask;
    }

    public ImageData getImageMask() {
        return this.imageMask;
    }

    public void setImageMask(ImageData imageMask2) {
        if (this.mask) {
            throw new IOException(IOException.ImageMaskCannotContainAnotherImageMask);
        } else if (imageMask2.mask) {
            this.imageMask = imageMask2;
        } else {
            throw new IOException(IOException.ImageIsNotMaskYouMustCallImageDataMakeMask);
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:2:0x0004, code lost:
        r0 = r3.bpc;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean isSoftMask() {
        /*
            r3 = this;
            boolean r0 = r3.mask
            if (r0 == 0) goto L_0x000e
            int r0 = r3.bpc
            r1 = 1
            if (r0 <= r1) goto L_0x000e
            r2 = 8
            if (r0 > r2) goto L_0x000e
            goto L_0x000f
        L_0x000e:
            r1 = 0
        L_0x000f:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.p026io.image.ImageData.isSoftMask():boolean");
    }

    public void makeMask() {
        if (canBeMask()) {
            this.mask = true;
            return;
        }
        throw new IOException(IOException.ThisImageCanNotBeAnImageMask);
    }

    public float getWidth() {
        return this.width;
    }

    public void setWidth(float width2) {
        this.width = width2;
    }

    public float getHeight() {
        return this.height;
    }

    public void setHeight(float height2) {
        this.height = height2;
    }

    public int getBpc() {
        return this.bpc;
    }

    public void setBpc(int bpc2) {
        this.bpc = bpc2;
    }

    public boolean isInterpolation() {
        return this.interpolation;
    }

    public void setInterpolation(boolean interpolation2) {
        this.interpolation = interpolation2;
    }

    public float getXYRatio() {
        return this.XYRatio;
    }

    public void setXYRatio(float XYRatio2) {
        this.XYRatio = XYRatio2;
    }

    public Map<String, Object> getImageAttributes() {
        return this.imageAttributes;
    }

    public void setImageAttributes(Map<String, Object> imageAttributes2) {
        this.imageAttributes = imageAttributes2;
    }

    public String getFilter() {
        return this.filter;
    }

    public void setFilter(String filter2) {
        this.filter = filter2;
    }

    public Map<String, Object> getDecodeParms() {
        return this.decodeParms;
    }

    public float[] getDecode() {
        return this.decode;
    }

    public void setDecode(float[] decode2) {
        this.decode = decode2;
    }

    public boolean canImageBeInline() {
        Logger logger = LoggerFactory.getLogger((Class<?>) ImageData.class);
        if (this.imageSize > 4096) {
            logger.warn(LogMessageConstant.IMAGE_SIZE_CANNOT_BE_MORE_4KB);
            return false;
        } else if (this.imageMask == null) {
            return true;
        } else {
            logger.warn(LogMessageConstant.IMAGE_HAS_MASK);
            return false;
        }
    }

    /* access modifiers changed from: package-private */
    public void loadData() throws java.io.IOException {
        RandomAccessFileOrArray raf = new RandomAccessFileOrArray(new RandomAccessSourceFactory().createSource(this.url));
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        StreamUtil.transferBytes(raf, (OutputStream) stream);
        raf.close();
        this.data = stream.toByteArray();
    }

    private static Long getSerialId() {
        Long valueOf;
        synchronized (staticLock) {
            long j = serialId + 1;
            serialId = j;
            valueOf = Long.valueOf(j);
        }
        return valueOf;
    }
}
