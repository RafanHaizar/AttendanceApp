package androidx.core.content.res;

import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.Base64;
import android.util.TypedValue;
import android.util.Xml;
import androidx.core.C0672R;
import androidx.core.provider.FontRequest;
import java.io.IOException;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public class FontResourcesParserCompat {
    private static final int DEFAULT_TIMEOUT_MILLIS = 500;
    public static final int FETCH_STRATEGY_ASYNC = 1;
    public static final int FETCH_STRATEGY_BLOCKING = 0;
    public static final int INFINITE_TIMEOUT_VALUE = -1;
    private static final int ITALIC = 1;
    private static final int NORMAL_WEIGHT = 400;

    @Retention(RetentionPolicy.SOURCE)
    public @interface FetchStrategy {
    }

    public interface FamilyResourceEntry {
    }

    public static final class ProviderResourceEntry implements FamilyResourceEntry {
        private final FontRequest mRequest;
        private final int mStrategy;
        private final String mSystemFontFamilyName;
        private final int mTimeoutMs;

        public ProviderResourceEntry(FontRequest request, int strategy, int timeoutMs, String systemFontFamilyName) {
            this.mRequest = request;
            this.mStrategy = strategy;
            this.mTimeoutMs = timeoutMs;
            this.mSystemFontFamilyName = systemFontFamilyName;
        }

        public ProviderResourceEntry(FontRequest request, int strategy, int timeoutMs) {
            this(request, strategy, timeoutMs, (String) null);
        }

        public FontRequest getRequest() {
            return this.mRequest;
        }

        public int getFetchStrategy() {
            return this.mStrategy;
        }

        public int getTimeout() {
            return this.mTimeoutMs;
        }

        public String getSystemFontFamilyName() {
            return this.mSystemFontFamilyName;
        }
    }

    public static final class FontFileResourceEntry {
        private final String mFileName;
        private final boolean mItalic;
        private final int mResourceId;
        private final int mTtcIndex;
        private final String mVariationSettings;
        private final int mWeight;

        public FontFileResourceEntry(String fileName, int weight, boolean italic, String variationSettings, int ttcIndex, int resourceId) {
            this.mFileName = fileName;
            this.mWeight = weight;
            this.mItalic = italic;
            this.mVariationSettings = variationSettings;
            this.mTtcIndex = ttcIndex;
            this.mResourceId = resourceId;
        }

        public String getFileName() {
            return this.mFileName;
        }

        public int getWeight() {
            return this.mWeight;
        }

        public boolean isItalic() {
            return this.mItalic;
        }

        public String getVariationSettings() {
            return this.mVariationSettings;
        }

        public int getTtcIndex() {
            return this.mTtcIndex;
        }

        public int getResourceId() {
            return this.mResourceId;
        }
    }

    public static final class FontFamilyFilesResourceEntry implements FamilyResourceEntry {
        private final FontFileResourceEntry[] mEntries;

        public FontFamilyFilesResourceEntry(FontFileResourceEntry[] entries) {
            this.mEntries = entries;
        }

        public FontFileResourceEntry[] getEntries() {
            return this.mEntries;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:6:0x000f  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0014  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static androidx.core.content.res.FontResourcesParserCompat.FamilyResourceEntry parse(org.xmlpull.v1.XmlPullParser r3, android.content.res.Resources r4) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        /*
        L_0x0001:
            int r0 = r3.next()
            r1 = r0
            r2 = 2
            if (r0 == r2) goto L_0x000d
            r0 = 1
            if (r1 == r0) goto L_0x000d
            goto L_0x0001
        L_0x000d:
            if (r1 != r2) goto L_0x0014
            androidx.core.content.res.FontResourcesParserCompat$FamilyResourceEntry r0 = readFamilies(r3, r4)
            return r0
        L_0x0014:
            org.xmlpull.v1.XmlPullParserException r0 = new org.xmlpull.v1.XmlPullParserException
            java.lang.String r2 = "No start tag found"
            r0.<init>(r2)
            goto L_0x001d
        L_0x001c:
            throw r0
        L_0x001d:
            goto L_0x001c
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.core.content.res.FontResourcesParserCompat.parse(org.xmlpull.v1.XmlPullParser, android.content.res.Resources):androidx.core.content.res.FontResourcesParserCompat$FamilyResourceEntry");
    }

    private static FamilyResourceEntry readFamilies(XmlPullParser parser, Resources resources) throws XmlPullParserException, IOException {
        parser.require(2, (String) null, "font-family");
        if (parser.getName().equals("font-family")) {
            return readFamily(parser, resources);
        }
        skip(parser);
        return null;
    }

    private static FamilyResourceEntry readFamily(XmlPullParser parser, Resources resources) throws XmlPullParserException, IOException {
        TypedArray array = resources.obtainAttributes(Xml.asAttributeSet(parser), C0672R.styleable.FontFamily);
        String authority = array.getString(C0672R.styleable.FontFamily_fontProviderAuthority);
        String providerPackage = array.getString(C0672R.styleable.FontFamily_fontProviderPackage);
        String query = array.getString(C0672R.styleable.FontFamily_fontProviderQuery);
        int certsId = array.getResourceId(C0672R.styleable.FontFamily_fontProviderCerts, 0);
        int strategy = array.getInteger(C0672R.styleable.FontFamily_fontProviderFetchStrategy, 1);
        int timeoutMs = array.getInteger(C0672R.styleable.FontFamily_fontProviderFetchTimeout, 500);
        String systemFontFamilyName = array.getString(C0672R.styleable.FontFamily_fontProviderSystemFontFamily);
        array.recycle();
        if (authority == null || providerPackage == null || query == null) {
            List<FontFileResourceEntry> fonts = new ArrayList<>();
            while (parser.next() != 3) {
                if (parser.getEventType() == 2) {
                    if (parser.getName().equals("font")) {
                        fonts.add(readFont(parser, resources));
                    } else {
                        skip(parser);
                    }
                }
            }
            if (fonts.isEmpty()) {
                return null;
            }
            return new FontFamilyFilesResourceEntry((FontFileResourceEntry[]) fonts.toArray(new FontFileResourceEntry[0]));
        }
        while (parser.next() != 3) {
            skip(parser);
        }
        return new ProviderResourceEntry(new FontRequest(authority, providerPackage, query, readCerts(resources, certsId)), strategy, timeoutMs, systemFontFamilyName);
    }

    private static int getType(TypedArray typedArray, int index) {
        if (Build.VERSION.SDK_INT >= 21) {
            return Api21Impl.getType(typedArray, index);
        }
        TypedValue tv = new TypedValue();
        typedArray.getValue(index, tv);
        return tv.type;
    }

    public static List<List<byte[]>> readCerts(Resources resources, int certsId) {
        if (certsId == 0) {
            return Collections.emptyList();
        }
        TypedArray typedArray = resources.obtainTypedArray(certsId);
        try {
            if (typedArray.length() == 0) {
                return Collections.emptyList();
            }
            List<List<byte[]>> result = new ArrayList<>();
            if (getType(typedArray, 0) == 1) {
                for (int i = 0; i < typedArray.length(); i++) {
                    int certId = typedArray.getResourceId(i, 0);
                    if (certId != 0) {
                        result.add(toByteArrayList(resources.getStringArray(certId)));
                    }
                }
            } else {
                result.add(toByteArrayList(resources.getStringArray(certsId)));
            }
            typedArray.recycle();
            return result;
        } finally {
            typedArray.recycle();
        }
    }

    private static List<byte[]> toByteArrayList(String[] stringArray) {
        List<byte[]> result = new ArrayList<>();
        for (String item : stringArray) {
            result.add(Base64.decode(item, 0));
        }
        return result;
    }

    private static FontFileResourceEntry readFont(XmlPullParser parser, Resources resources) throws XmlPullParserException, IOException {
        int weightAttr;
        int styleAttr;
        int i;
        int variationSettingsAttr;
        int i2;
        TypedArray array = resources.obtainAttributes(Xml.asAttributeSet(parser), C0672R.styleable.FontFamilyFont);
        if (array.hasValue(C0672R.styleable.FontFamilyFont_fontWeight)) {
            weightAttr = C0672R.styleable.FontFamilyFont_fontWeight;
        } else {
            weightAttr = C0672R.styleable.FontFamilyFont_android_fontWeight;
        }
        int weight = array.getInt(weightAttr, 400);
        if (array.hasValue(C0672R.styleable.FontFamilyFont_fontStyle)) {
            styleAttr = C0672R.styleable.FontFamilyFont_fontStyle;
        } else {
            styleAttr = C0672R.styleable.FontFamilyFont_android_fontStyle;
        }
        boolean isItalic = 1 == array.getInt(styleAttr, 0);
        if (array.hasValue(C0672R.styleable.FontFamilyFont_ttcIndex)) {
            i = C0672R.styleable.FontFamilyFont_ttcIndex;
        } else {
            i = C0672R.styleable.FontFamilyFont_android_ttcIndex;
        }
        int ttcIndexAttr = i;
        if (array.hasValue(C0672R.styleable.FontFamilyFont_fontVariationSettings)) {
            variationSettingsAttr = C0672R.styleable.FontFamilyFont_fontVariationSettings;
        } else {
            variationSettingsAttr = C0672R.styleable.FontFamilyFont_android_fontVariationSettings;
        }
        String variationSettings = array.getString(variationSettingsAttr);
        int ttcIndex = array.getInt(ttcIndexAttr, 0);
        if (array.hasValue(C0672R.styleable.FontFamilyFont_font)) {
            i2 = C0672R.styleable.FontFamilyFont_font;
        } else {
            i2 = C0672R.styleable.FontFamilyFont_android_font;
        }
        int resourceAttr = i2;
        int resourceId = array.getResourceId(resourceAttr, 0);
        String filename = array.getString(resourceAttr);
        array.recycle();
        while (parser.next() != 3) {
            skip(parser);
        }
        int i3 = resourceAttr;
        return new FontFileResourceEntry(filename, weight, isItalic, variationSettings, ttcIndex, resourceId);
    }

    private static void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
        int depth = 1;
        while (depth > 0) {
            switch (parser.next()) {
                case 2:
                    depth++;
                    break;
                case 3:
                    depth--;
                    break;
            }
        }
    }

    private FontResourcesParserCompat() {
    }

    static class Api21Impl {
        private Api21Impl() {
        }

        static int getType(TypedArray typedArray, int index) {
            return typedArray.getType(index);
        }
    }
}
