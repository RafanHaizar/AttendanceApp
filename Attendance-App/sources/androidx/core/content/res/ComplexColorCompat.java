package androidx.core.content.res;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Shader;
import android.util.Log;

public final class ComplexColorCompat {
    private static final String LOG_TAG = "ComplexColorCompat";
    private int mColor;
    private final ColorStateList mColorStateList;
    private final Shader mShader;

    private ComplexColorCompat(Shader shader, ColorStateList colorStateList, int color) {
        this.mShader = shader;
        this.mColorStateList = colorStateList;
        this.mColor = color;
    }

    static ComplexColorCompat from(Shader shader) {
        return new ComplexColorCompat(shader, (ColorStateList) null, 0);
    }

    static ComplexColorCompat from(ColorStateList colorStateList) {
        return new ComplexColorCompat((Shader) null, colorStateList, colorStateList.getDefaultColor());
    }

    static ComplexColorCompat from(int color) {
        return new ComplexColorCompat((Shader) null, (ColorStateList) null, color);
    }

    public Shader getShader() {
        return this.mShader;
    }

    public int getColor() {
        return this.mColor;
    }

    public void setColor(int color) {
        this.mColor = color;
    }

    public boolean isGradient() {
        return this.mShader != null;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:2:0x0004, code lost:
        r0 = r1.mColorStateList;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean isStateful() {
        /*
            r1 = this;
            android.graphics.Shader r0 = r1.mShader
            if (r0 != 0) goto L_0x0010
            android.content.res.ColorStateList r0 = r1.mColorStateList
            if (r0 == 0) goto L_0x0010
            boolean r0 = r0.isStateful()
            if (r0 == 0) goto L_0x0010
            r0 = 1
            goto L_0x0011
        L_0x0010:
            r0 = 0
        L_0x0011:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.core.content.res.ComplexColorCompat.isStateful():boolean");
    }

    public boolean onStateChanged(int[] stateSet) {
        if (!isStateful()) {
            return false;
        }
        ColorStateList colorStateList = this.mColorStateList;
        int colorForState = colorStateList.getColorForState(stateSet, colorStateList.getDefaultColor());
        if (colorForState == this.mColor) {
            return false;
        }
        this.mColor = colorForState;
        return true;
    }

    public boolean willDraw() {
        return isGradient() || this.mColor != 0;
    }

    public static ComplexColorCompat inflate(Resources resources, int resId, Resources.Theme theme) {
        try {
            return createFromXml(resources, resId, theme);
        } catch (Exception e) {
            Log.e(LOG_TAG, "Failed to inflate ComplexColor.", e);
            return null;
        }
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0033, code lost:
        if (r2.equals("gradient") != false) goto L_0x0037;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static androidx.core.content.res.ComplexColorCompat createFromXml(android.content.res.Resources r7, int r8, android.content.res.Resources.Theme r9) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
        /*
            android.content.res.XmlResourceParser r0 = r7.getXml(r8)
            android.util.AttributeSet r1 = android.util.Xml.asAttributeSet(r0)
        L_0x0008:
            int r2 = r0.next()
            r3 = r2
            r4 = 1
            r5 = 2
            if (r2 == r5) goto L_0x0014
            if (r3 == r4) goto L_0x0014
            goto L_0x0008
        L_0x0014:
            if (r3 != r5) goto L_0x006d
            java.lang.String r2 = r0.getName()
            int r5 = r2.hashCode()
            switch(r5) {
                case 89650992: goto L_0x002d;
                case 1191572447: goto L_0x0022;
                default: goto L_0x0021;
            }
        L_0x0021:
            goto L_0x0036
        L_0x0022:
            java.lang.String r4 = "selector"
            boolean r4 = r2.equals(r4)
            if (r4 == 0) goto L_0x0021
            r4 = 0
            goto L_0x0037
        L_0x002d:
            java.lang.String r5 = "gradient"
            boolean r5 = r2.equals(r5)
            if (r5 == 0) goto L_0x0021
            goto L_0x0037
        L_0x0036:
            r4 = -1
        L_0x0037:
            switch(r4) {
                case 0: goto L_0x0064;
                case 1: goto L_0x005b;
                default: goto L_0x003a;
            }
        L_0x003a:
            org.xmlpull.v1.XmlPullParserException r4 = new org.xmlpull.v1.XmlPullParserException
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            java.lang.String r6 = r0.getPositionDescription()
            java.lang.StringBuilder r5 = r5.append(r6)
            java.lang.String r6 = ": unsupported complex color tag "
            java.lang.StringBuilder r5 = r5.append(r6)
            java.lang.StringBuilder r5 = r5.append(r2)
            java.lang.String r5 = r5.toString()
            r4.<init>(r5)
            throw r4
        L_0x005b:
            android.graphics.Shader r4 = androidx.core.content.res.GradientColorInflaterCompat.createFromXmlInner(r7, r0, r1, r9)
            androidx.core.content.res.ComplexColorCompat r4 = from((android.graphics.Shader) r4)
            return r4
        L_0x0064:
            android.content.res.ColorStateList r4 = androidx.core.content.res.ColorStateListInflaterCompat.createFromXmlInner(r7, r0, r1, r9)
            androidx.core.content.res.ComplexColorCompat r4 = from((android.content.res.ColorStateList) r4)
            return r4
        L_0x006d:
            org.xmlpull.v1.XmlPullParserException r2 = new org.xmlpull.v1.XmlPullParserException
            java.lang.String r4 = "No start tag found"
            r2.<init>(r4)
            goto L_0x0076
        L_0x0075:
            throw r2
        L_0x0076:
            goto L_0x0075
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.core.content.res.ComplexColorCompat.createFromXml(android.content.res.Resources, int, android.content.res.Resources$Theme):androidx.core.content.res.ComplexColorCompat");
    }
}
