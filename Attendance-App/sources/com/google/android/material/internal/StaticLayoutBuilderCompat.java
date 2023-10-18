package com.google.android.material.internal;

import android.os.Build;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextDirectionHeuristic;
import android.text.TextDirectionHeuristics;
import android.text.TextPaint;
import android.text.TextUtils;
import androidx.core.util.Preconditions;
import java.lang.reflect.Constructor;

final class StaticLayoutBuilderCompat {
    static final int DEFAULT_HYPHENATION_FREQUENCY = (Build.VERSION.SDK_INT >= 23 ? 1 : 0);
    static final float DEFAULT_LINE_SPACING_ADD = 0.0f;
    static final float DEFAULT_LINE_SPACING_MULTIPLIER = 1.0f;
    private static final String TEXT_DIRS_CLASS = "android.text.TextDirectionHeuristics";
    private static final String TEXT_DIR_CLASS = "android.text.TextDirectionHeuristic";
    private static final String TEXT_DIR_CLASS_LTR = "LTR";
    private static final String TEXT_DIR_CLASS_RTL = "RTL";
    private static Constructor<StaticLayout> constructor;
    private static boolean initialized;
    private static Object textDirection;
    private Layout.Alignment alignment;
    private TextUtils.TruncateAt ellipsize;
    private int end;
    private int hyphenationFrequency;
    private boolean includePad;
    private boolean isRtl;
    private float lineSpacingAdd;
    private float lineSpacingMultiplier;
    private int maxLines;
    private final TextPaint paint;
    private CharSequence source;
    private int start = 0;
    private StaticLayoutBuilderConfigurer staticLayoutBuilderConfigurer;
    private final int width;

    private StaticLayoutBuilderCompat(CharSequence source2, TextPaint paint2, int width2) {
        this.source = source2;
        this.paint = paint2;
        this.width = width2;
        this.end = source2.length();
        this.alignment = Layout.Alignment.ALIGN_NORMAL;
        this.maxLines = Integer.MAX_VALUE;
        this.lineSpacingAdd = 0.0f;
        this.lineSpacingMultiplier = 1.0f;
        this.hyphenationFrequency = DEFAULT_HYPHENATION_FREQUENCY;
        this.includePad = true;
        this.ellipsize = null;
    }

    public static StaticLayoutBuilderCompat obtain(CharSequence source2, TextPaint paint2, int width2) {
        return new StaticLayoutBuilderCompat(source2, paint2, width2);
    }

    public StaticLayoutBuilderCompat setAlignment(Layout.Alignment alignment2) {
        this.alignment = alignment2;
        return this;
    }

    public StaticLayoutBuilderCompat setIncludePad(boolean includePad2) {
        this.includePad = includePad2;
        return this;
    }

    public StaticLayoutBuilderCompat setStart(int start2) {
        this.start = start2;
        return this;
    }

    public StaticLayoutBuilderCompat setEnd(int end2) {
        this.end = end2;
        return this;
    }

    public StaticLayoutBuilderCompat setMaxLines(int maxLines2) {
        this.maxLines = maxLines2;
        return this;
    }

    public StaticLayoutBuilderCompat setLineSpacing(float spacingAdd, float lineSpacingMultiplier2) {
        this.lineSpacingAdd = spacingAdd;
        this.lineSpacingMultiplier = lineSpacingMultiplier2;
        return this;
    }

    public StaticLayoutBuilderCompat setHyphenationFrequency(int hyphenationFrequency2) {
        this.hyphenationFrequency = hyphenationFrequency2;
        return this;
    }

    public StaticLayoutBuilderCompat setEllipsize(TextUtils.TruncateAt ellipsize2) {
        this.ellipsize = ellipsize2;
        return this;
    }

    public StaticLayoutBuilderCompat setStaticLayoutBuilderConfigurer(StaticLayoutBuilderConfigurer staticLayoutBuilderConfigurer2) {
        this.staticLayoutBuilderConfigurer = staticLayoutBuilderConfigurer2;
        return this;
    }

    public StaticLayout build() throws StaticLayoutBuilderCompatException {
        TextDirectionHeuristic textDirectionHeuristic;
        if (this.source == null) {
            this.source = "";
        }
        int availableWidth = Math.max(0, this.width);
        CharSequence textToDraw = this.source;
        if (this.maxLines == 1) {
            textToDraw = TextUtils.ellipsize(this.source, this.paint, (float) availableWidth, this.ellipsize);
        }
        this.end = Math.min(textToDraw.length(), this.end);
        if (Build.VERSION.SDK_INT >= 23) {
            if (this.isRtl && this.maxLines == 1) {
                this.alignment = Layout.Alignment.ALIGN_OPPOSITE;
            }
            StaticLayout.Builder builder = StaticLayout.Builder.obtain(textToDraw, this.start, this.end, this.paint, availableWidth);
            builder.setAlignment(this.alignment);
            builder.setIncludePad(this.includePad);
            if (this.isRtl) {
                textDirectionHeuristic = TextDirectionHeuristics.RTL;
            } else {
                textDirectionHeuristic = TextDirectionHeuristics.LTR;
            }
            builder.setTextDirection(textDirectionHeuristic);
            TextUtils.TruncateAt truncateAt = this.ellipsize;
            if (truncateAt != null) {
                builder.setEllipsize(truncateAt);
            }
            builder.setMaxLines(this.maxLines);
            float f = this.lineSpacingAdd;
            if (!(f == 0.0f && this.lineSpacingMultiplier == 1.0f)) {
                builder.setLineSpacing(f, this.lineSpacingMultiplier);
            }
            if (this.maxLines > 1) {
                builder.setHyphenationFrequency(this.hyphenationFrequency);
            }
            StaticLayoutBuilderConfigurer staticLayoutBuilderConfigurer2 = this.staticLayoutBuilderConfigurer;
            if (staticLayoutBuilderConfigurer2 != null) {
                staticLayoutBuilderConfigurer2.configure(builder);
            }
            return builder.build();
        }
        createConstructorWithReflection();
        try {
            return (StaticLayout) ((Constructor) Preconditions.checkNotNull(constructor)).newInstance(new Object[]{textToDraw, Integer.valueOf(this.start), Integer.valueOf(this.end), this.paint, Integer.valueOf(availableWidth), this.alignment, Preconditions.checkNotNull(textDirection), Float.valueOf(1.0f), Float.valueOf(0.0f), Boolean.valueOf(this.includePad), null, Integer.valueOf(availableWidth), Integer.valueOf(this.maxLines)});
        } catch (Exception cause) {
            throw new StaticLayoutBuilderCompatException(cause);
        }
    }

    private void createConstructorWithReflection() throws StaticLayoutBuilderCompatException {
        if (!initialized) {
            try {
                Class<TextDirectionHeuristic> cls = TextDirectionHeuristic.class;
                textDirection = this.isRtl && Build.VERSION.SDK_INT >= 23 ? TextDirectionHeuristics.RTL : TextDirectionHeuristics.LTR;
                Constructor<StaticLayout> declaredConstructor = StaticLayout.class.getDeclaredConstructor(new Class[]{CharSequence.class, Integer.TYPE, Integer.TYPE, TextPaint.class, Integer.TYPE, Layout.Alignment.class, cls, Float.TYPE, Float.TYPE, Boolean.TYPE, TextUtils.TruncateAt.class, Integer.TYPE, Integer.TYPE});
                constructor = declaredConstructor;
                declaredConstructor.setAccessible(true);
                initialized = true;
            } catch (Exception cause) {
                throw new StaticLayoutBuilderCompatException(cause);
            }
        }
    }

    public StaticLayoutBuilderCompat setIsRtl(boolean isRtl2) {
        this.isRtl = isRtl2;
        return this;
    }

    static class StaticLayoutBuilderCompatException extends Exception {
        StaticLayoutBuilderCompatException(Throwable cause) {
            super("Error thrown initializing StaticLayout " + cause.getMessage(), cause);
        }
    }
}
