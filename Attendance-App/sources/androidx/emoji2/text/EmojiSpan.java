package androidx.emoji2.text;

import android.graphics.Paint;
import android.text.style.ReplacementSpan;
import androidx.core.util.Preconditions;

public abstract class EmojiSpan extends ReplacementSpan {
    private short mHeight = -1;
    private final EmojiMetadata mMetadata;
    private float mRatio = 1.0f;
    private final Paint.FontMetricsInt mTmpFontMetrics = new Paint.FontMetricsInt();
    private short mWidth = -1;

    EmojiSpan(EmojiMetadata metadata) {
        Preconditions.checkNotNull(metadata, "metadata cannot be null");
        this.mMetadata = metadata;
    }

    public int getSize(Paint paint, CharSequence text, int start, int end, Paint.FontMetricsInt fm) {
        paint.getFontMetricsInt(this.mTmpFontMetrics);
        this.mRatio = (((float) Math.abs(this.mTmpFontMetrics.descent - this.mTmpFontMetrics.ascent)) * 1.0f) / ((float) this.mMetadata.getHeight());
        this.mHeight = (short) ((int) (((float) this.mMetadata.getHeight()) * this.mRatio));
        this.mWidth = (short) ((int) (((float) this.mMetadata.getWidth()) * this.mRatio));
        if (fm != null) {
            fm.ascent = this.mTmpFontMetrics.ascent;
            fm.descent = this.mTmpFontMetrics.descent;
            fm.top = this.mTmpFontMetrics.top;
            fm.bottom = this.mTmpFontMetrics.bottom;
        }
        return this.mWidth;
    }

    public final EmojiMetadata getMetadata() {
        return this.mMetadata;
    }

    /* access modifiers changed from: package-private */
    public final int getWidth() {
        return this.mWidth;
    }

    public final int getHeight() {
        return this.mHeight;
    }

    /* access modifiers changed from: package-private */
    public final float getRatio() {
        return this.mRatio;
    }

    public final int getId() {
        return getMetadata().getId();
    }
}
