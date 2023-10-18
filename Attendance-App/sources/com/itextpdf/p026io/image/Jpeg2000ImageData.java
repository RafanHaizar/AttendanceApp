package com.itextpdf.p026io.image;

import com.itextpdf.p026io.LogMessageConstant;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.LoggerFactory;

/* renamed from: com.itextpdf.io.image.Jpeg2000ImageData */
public class Jpeg2000ImageData extends ImageData {
    protected Parameters parameters;

    /* renamed from: com.itextpdf.io.image.Jpeg2000ImageData$Parameters */
    public static class Parameters {
        public byte[] bpcBoxData;
        public List<ColorSpecBox> colorSpecBoxes = null;
        public boolean isJp2 = false;
        public boolean isJpxBaseline = false;
        public int numOfComps;
    }

    /* renamed from: com.itextpdf.io.image.Jpeg2000ImageData$ColorSpecBox */
    public static class ColorSpecBox extends ArrayList<Integer> {
        private static final long serialVersionUID = -6008490897027025733L;
        private byte[] colorProfile;

        public int getMeth() {
            return ((Integer) get(0)).intValue();
        }

        public int getPrec() {
            return ((Integer) get(1)).intValue();
        }

        public int getApprox() {
            return ((Integer) get(2)).intValue();
        }

        public int getEnumCs() {
            return ((Integer) get(3)).intValue();
        }

        public byte[] getColorProfile() {
            return this.colorProfile;
        }

        /* access modifiers changed from: package-private */
        public void setColorProfile(byte[] colorProfile2) {
            this.colorProfile = colorProfile2;
        }
    }

    protected Jpeg2000ImageData(URL url) {
        super(url, ImageType.JPEG2000);
    }

    protected Jpeg2000ImageData(byte[] bytes) {
        super(bytes, ImageType.JPEG2000);
    }

    public boolean canImageBeInline() {
        LoggerFactory.getLogger((Class<?>) ImageData.class).warn(LogMessageConstant.IMAGE_HAS_JPXDECODE_FILTER);
        return false;
    }

    public Parameters getParameters() {
        return this.parameters;
    }
}
