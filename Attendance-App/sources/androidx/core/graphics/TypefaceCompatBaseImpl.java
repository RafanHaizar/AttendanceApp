package androidx.core.graphics;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.CancellationSignal;
import android.util.Log;
import androidx.core.content.res.FontResourcesParserCompat;
import androidx.core.provider.FontsContractCompat;
import com.itextpdf.p026io.font.constants.FontWeights;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.concurrent.ConcurrentHashMap;

class TypefaceCompatBaseImpl {
    private static final int INVALID_KEY = 0;
    private static final String TAG = "TypefaceCompatBaseImpl";
    private ConcurrentHashMap<Long, FontResourcesParserCompat.FontFamilyFilesResourceEntry> mFontFamilies = new ConcurrentHashMap<>();

    private interface StyleExtractor<T> {
        int getWeight(T t);

        boolean isItalic(T t);
    }

    TypefaceCompatBaseImpl() {
    }

    private static <T> T findBestFont(T[] fonts, int style, StyleExtractor<T> extractor) {
        return findBestFont(fonts, (style & 1) == 0 ? FontWeights.NORMAL : 700, (style & 2) != 0, extractor);
    }

    private static <T> T findBestFont(T[] fonts, int targetWeight, boolean isTargetItalic, StyleExtractor<T> extractor) {
        T best = null;
        int bestScore = Integer.MAX_VALUE;
        for (T font : fonts) {
            int score = (Math.abs(extractor.getWeight(font) - targetWeight) * 2) + (extractor.isItalic(font) == isTargetItalic ? 0 : 1);
            if (best == null || bestScore > score) {
                best = font;
                bestScore = score;
            }
        }
        return best;
    }

    private static long getUniqueKey(Typeface typeface) {
        if (typeface == null) {
            return 0;
        }
        try {
            Field field = Typeface.class.getDeclaredField("native_instance");
            field.setAccessible(true);
            return ((Number) field.get(typeface)).longValue();
        } catch (NoSuchFieldException e) {
            Log.e(TAG, "Could not retrieve font from family.", e);
            return 0;
        } catch (IllegalAccessException e2) {
            Log.e(TAG, "Could not retrieve font from family.", e2);
            return 0;
        }
    }

    /* access modifiers changed from: protected */
    public FontsContractCompat.FontInfo findBestInfo(FontsContractCompat.FontInfo[] fonts, int style) {
        return (FontsContractCompat.FontInfo) findBestFont(fonts, style, new StyleExtractor<FontsContractCompat.FontInfo>() {
            public int getWeight(FontsContractCompat.FontInfo info) {
                return info.getWeight();
            }

            public boolean isItalic(FontsContractCompat.FontInfo info) {
                return info.isItalic();
            }
        });
    }

    /* access modifiers changed from: protected */
    public Typeface createFromInputStream(Context context, InputStream is) {
        File tmpFile = TypefaceCompatUtil.getTempFile(context);
        if (tmpFile == null) {
            return null;
        }
        try {
            if (!TypefaceCompatUtil.copyToFile(tmpFile, is)) {
                return null;
            }
            Typeface createFromFile = Typeface.createFromFile(tmpFile.getPath());
            tmpFile.delete();
            return createFromFile;
        } catch (RuntimeException e) {
            return null;
        } finally {
            tmpFile.delete();
        }
    }

    public Typeface createFromFontInfo(Context context, CancellationSignal cancellationSignal, FontsContractCompat.FontInfo[] fonts, int style) {
        if (fonts.length < 1) {
            return null;
        }
        InputStream is = null;
        try {
            is = context.getContentResolver().openInputStream(findBestInfo(fonts, style).getUri());
            return createFromInputStream(context, is);
        } catch (IOException e) {
            return null;
        } finally {
            TypefaceCompatUtil.closeQuietly(is);
        }
    }

    private FontResourcesParserCompat.FontFileResourceEntry findBestEntry(FontResourcesParserCompat.FontFamilyFilesResourceEntry entry, int style) {
        return (FontResourcesParserCompat.FontFileResourceEntry) findBestFont(entry.getEntries(), style, new StyleExtractor<FontResourcesParserCompat.FontFileResourceEntry>() {
            public int getWeight(FontResourcesParserCompat.FontFileResourceEntry entry) {
                return entry.getWeight();
            }

            public boolean isItalic(FontResourcesParserCompat.FontFileResourceEntry entry) {
                return entry.isItalic();
            }
        });
    }

    private FontResourcesParserCompat.FontFileResourceEntry findBestEntry(FontResourcesParserCompat.FontFamilyFilesResourceEntry entry, int weight, boolean italic) {
        return (FontResourcesParserCompat.FontFileResourceEntry) findBestFont(entry.getEntries(), weight, italic, new StyleExtractor<FontResourcesParserCompat.FontFileResourceEntry>() {
            public int getWeight(FontResourcesParserCompat.FontFileResourceEntry entry) {
                return entry.getWeight();
            }

            public boolean isItalic(FontResourcesParserCompat.FontFileResourceEntry entry) {
                return entry.isItalic();
            }
        });
    }

    public Typeface createFromFontFamilyFilesResourceEntry(Context context, FontResourcesParserCompat.FontFamilyFilesResourceEntry entry, Resources resources, int style) {
        FontResourcesParserCompat.FontFileResourceEntry best = findBestEntry(entry, style);
        if (best == null) {
            return null;
        }
        Typeface typeface = TypefaceCompat.createFromResourcesFontFile(context, resources, best.getResourceId(), best.getFileName(), 0, style);
        addFontFamily(typeface, entry);
        return typeface;
    }

    /* access modifiers changed from: package-private */
    public Typeface createFromFontFamilyFilesResourceEntry(Context context, FontResourcesParserCompat.FontFamilyFilesResourceEntry entry, Resources resources, int weight, boolean italic) {
        FontResourcesParserCompat.FontFileResourceEntry best = findBestEntry(entry, weight, italic);
        if (best == null) {
            return null;
        }
        Typeface typeface = TypefaceCompat.createFromResourcesFontFile(context, resources, best.getResourceId(), best.getFileName(), 0, 0);
        addFontFamily(typeface, entry);
        return typeface;
    }

    public Typeface createFromResourcesFontFile(Context context, Resources resources, int id, String path, int style) {
        File tmpFile = TypefaceCompatUtil.getTempFile(context);
        if (tmpFile == null) {
            return null;
        }
        try {
            if (!TypefaceCompatUtil.copyToFile(tmpFile, resources, id)) {
                return null;
            }
            Typeface createFromFile = Typeface.createFromFile(tmpFile.getPath());
            tmpFile.delete();
            return createFromFile;
        } catch (RuntimeException e) {
            return null;
        } finally {
            tmpFile.delete();
        }
    }

    /* access modifiers changed from: package-private */
    public Typeface createWeightStyle(Context context, Typeface base, int weight, boolean italic) {
        Typeface out = null;
        try {
            out = WeightTypefaceApi14.createWeightStyle(this, context, base, weight, italic);
        } catch (RuntimeException e) {
        }
        if (out == null) {
            return base;
        }
        return out;
    }

    /* access modifiers changed from: package-private */
    public FontResourcesParserCompat.FontFamilyFilesResourceEntry getFontFamily(Typeface typeface) {
        long key = getUniqueKey(typeface);
        if (key == 0) {
            return null;
        }
        return this.mFontFamilies.get(Long.valueOf(key));
    }

    private void addFontFamily(Typeface typeface, FontResourcesParserCompat.FontFamilyFilesResourceEntry entry) {
        long key = getUniqueKey(typeface);
        if (key != 0) {
            this.mFontFamilies.put(Long.valueOf(key), entry);
        }
    }
}
