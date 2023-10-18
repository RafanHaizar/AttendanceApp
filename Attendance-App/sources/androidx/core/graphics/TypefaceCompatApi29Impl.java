package androidx.core.graphics;

import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.graphics.fonts.Font;
import android.graphics.fonts.FontFamily;
import android.graphics.fonts.FontStyle;
import android.os.CancellationSignal;
import android.os.ParcelFileDescriptor;
import androidx.core.content.res.FontResourcesParserCompat;
import androidx.core.provider.FontsContractCompat;
import com.itextpdf.p026io.font.constants.FontWeights;
import java.io.IOException;
import java.io.InputStream;

public class TypefaceCompatApi29Impl extends TypefaceCompatBaseImpl {
    private static int getMatchScore(FontStyle o1, FontStyle o2) {
        return (Math.abs(o1.getWeight() - o2.getWeight()) / 100) + (o1.getSlant() == o2.getSlant() ? 0 : 2);
    }

    private Font findBaseFont(FontFamily family, int style) {
        int i;
        int i2;
        if ((style & 1) != 0) {
            i = 700;
        } else {
            i = FontWeights.NORMAL;
        }
        if ((style & 2) != 0) {
            i2 = 1;
        } else {
            i2 = 0;
        }
        FontStyle desiredStyle = new FontStyle(i, i2);
        Font bestFont = family.getFont(0);
        int bestScore = getMatchScore(desiredStyle, bestFont.getStyle());
        for (int i3 = 1; i3 < family.getSize(); i3++) {
            Font candidate = family.getFont(i3);
            int score = getMatchScore(desiredStyle, candidate.getStyle());
            if (score < bestScore) {
                bestFont = candidate;
                bestScore = score;
            }
        }
        return bestFont;
    }

    /* access modifiers changed from: protected */
    public FontsContractCompat.FontInfo findBestInfo(FontsContractCompat.FontInfo[] fonts, int style) {
        throw new RuntimeException("Do not use this function in API 29 or later.");
    }

    /* access modifiers changed from: protected */
    public Typeface createFromInputStream(Context context, InputStream is) {
        throw new RuntimeException("Do not use this function in API 29 or later.");
    }

    public Typeface createFromFontInfo(Context context, CancellationSignal cancellationSignal, FontsContractCompat.FontInfo[] fonts, int style) {
        ParcelFileDescriptor pfd;
        int i;
        FontFamily.Builder familyBuilder = null;
        ContentResolver resolver = context.getContentResolver();
        try {
            for (FontsContractCompat.FontInfo font : fonts) {
                try {
                    pfd = resolver.openFileDescriptor(font.getUri(), "r", cancellationSignal);
                    if (pfd != null) {
                        Font.Builder weight = new Font.Builder(pfd).setWeight(font.getWeight());
                        if (font.isItalic()) {
                            i = 1;
                        } else {
                            i = 0;
                        }
                        Font platformFont = weight.setSlant(i).setTtcIndex(font.getTtcIndex()).build();
                        if (familyBuilder == null) {
                            familyBuilder = new FontFamily.Builder(platformFont);
                        } else {
                            familyBuilder.addFont(platformFont);
                        }
                        if (pfd != null) {
                            pfd.close();
                        }
                    } else if (pfd != null) {
                        pfd.close();
                    }
                } catch (IOException e) {
                } catch (Throwable th) {
                    th.addSuppressed(th);
                }
            }
            if (familyBuilder == null) {
                return null;
            }
            FontFamily family = familyBuilder.build();
            return new Typeface.CustomFallbackBuilder(family).setStyle(findBaseFont(family, style).getStyle()).build();
            throw th;
        } catch (Exception e2) {
            return null;
        }
    }

    public Typeface createFromFontFamilyFilesResourceEntry(Context context, FontResourcesParserCompat.FontFamilyFilesResourceEntry familyEntry, Resources resources, int style) {
        int i;
        FontFamily.Builder familyBuilder = null;
        try {
            for (FontResourcesParserCompat.FontFileResourceEntry entry : familyEntry.getEntries()) {
                try {
                    Font.Builder weight = new Font.Builder(resources, entry.getResourceId()).setWeight(entry.getWeight());
                    if (entry.isItalic()) {
                        i = 1;
                    } else {
                        i = 0;
                    }
                    Font platformFont = weight.setSlant(i).setTtcIndex(entry.getTtcIndex()).setFontVariationSettings(entry.getVariationSettings()).build();
                    if (familyBuilder == null) {
                        familyBuilder = new FontFamily.Builder(platformFont);
                    } else {
                        familyBuilder.addFont(platformFont);
                    }
                } catch (IOException e) {
                }
            }
            if (familyBuilder == null) {
                return null;
            }
            FontFamily family = familyBuilder.build();
            return new Typeface.CustomFallbackBuilder(family).setStyle(findBaseFont(family, style).getStyle()).build();
        } catch (Exception e2) {
            return null;
        }
    }

    public Typeface createFromResourcesFontFile(Context context, Resources resources, int id, String path, int style) {
        try {
            Font font = new Font.Builder(resources, id).build();
            return new Typeface.CustomFallbackBuilder(new FontFamily.Builder(font).build()).setStyle(font.getStyle()).build();
        } catch (Exception e) {
            return null;
        }
    }

    /* access modifiers changed from: package-private */
    public Typeface createWeightStyle(Context context, Typeface base, int weight, boolean italic) {
        return Typeface.create(base, weight, italic);
    }
}
